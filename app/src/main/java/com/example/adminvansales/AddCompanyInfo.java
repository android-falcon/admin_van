package com.example.adminvansales;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.model.CompanyInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddCompanyInfo extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioTop;
    public  static  boolean openDialog=false;
    Bitmap itemBitmapPic = null;
    RadioButton radioBottom;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    int position = 0;
    public final int PICK_IMAGE = 1;
EditText name, tel,
            tax, noteInvoice,
            salesman_car, salesman_id, salesman_name;
    String salesName;
    ImageView logo;
    Button okButton;
    Button cancelButton;
    public static String languagelocalApp = "";
    Bitmap visitPic = null;
    ImageView visitPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_info);




        radioGroup = (RadioGroup) findViewById(R.id.radioGrp);
        radioTop = findViewById(R.id.radioTop);
        radioBottom = findViewById(R.id.radioBottom);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup,
                                         int radioButtonID) {
                switch (radioButtonID) {
                    case R.id.radioTop:
                        position = 0;
                        break;
                    case R.id.radioBottom:
                        position = 1;
                        break;

                }
            }
        });

        LinearLayout mainLinear = findViewById(R.id.linearCompany);
        try {
            if (LogIn.languagelocalApp.equals("ar")) {
                mainLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                if (LogIn.languagelocalApp.equals("en")) {
                    mainLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        } catch (Exception e) {
            mainLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        name = (EditText) findViewById(R.id.com_name);
        tel = (EditText) findViewById(R.id.com_tel);
        tax = (EditText) findViewById(R.id.tax_no);
        noteInvoice = (EditText) findViewById(R.id.notes);
        salesman_car = (EditText) findViewById(R.id.salesman_car);
        salesman_id = (EditText) findViewById(R.id.salesman_id);
        salesman_name = (EditText)findViewById(R.id.salesman_name);



        final TextView savecompanyLocation = (TextView) findViewById(R.id.savecompanyLocation);

        logo = (ImageView) findViewById(R.id.logo);

        okButton = (Button) findViewById(R.id.okBut);
        cancelButton = (Button) findViewById(R.id.cancelBut);



        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("") && !tel.getText().toString().equals("") && !tax.getText().toString().equals("")) {
                    String comName = name.getText().toString().trim();
                    String comTel = "0", car_no = "", nationalId = "";
                    int taxNo = 0;
                    try {
                        comTel = tel.getText().toString();
                        taxNo = Integer.parseInt(tax.getText().toString());
                        String companyNote = noteInvoice.getText().toString();

                        car_no = salesman_car.getText().toString().trim();
                        nationalId = salesman_id.getText().toString().trim();

                        if (itemBitmapPic != null) {
                            itemBitmapPic = getResizedBitmap(itemBitmapPic, 150, 150);
                        }
                            CompanyInfo companyInfo=new CompanyInfo();
                        companyInfo.setCompanyName(comName);
                        companyInfo.setcompanyTel(comTel);
                        companyInfo.setTaxNo(taxNo);
                        companyInfo.setLogo(itemBitmapPic);
                        companyInfo.setNational_id(nationalId);
                        companyInfo.setNoteForPrint(companyNote);
                        companyInfo.setNotePosition(position+"");
                        companyInfo.setCarNo(car_no);
                        Log.e("addCompanyInfo", comName + " " + comTel + " " + taxNo + " " + itemBitmapPic + " ");
                        //mDbHandler.addCompanyInfo(comName, comTel, taxNo, itemBitmapPic, companyNote, 0, 0, position, nationalId, car_no);

                    } catch (NumberFormatException e) {
                        if (comTel.equals("0")) {
                            tel.setError("Invalid No");
                        }
                        if (taxNo == 0) {
                            tax.setError("Invalid Tax");
                        }
                    }


                } else
                    Toast.makeText(AddCompanyInfo.this, "Please ensure your inputs", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
cleartext();
startActivity(new Intent(AddCompanyInfo.this,HomeActivity.class));
            }
        });


    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        if (bm != null) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            return resizedBitmap;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("startVoiceInput2","requestCode"+requestCode+"\t"+data+"\t"+resultCode);

        if(requestCode == 3333 && resultCode == RESULT_OK ){
            Log.e("aya","requestCode"+requestCode+"\t"+data+"\t"+resultCode);
            Uri content_describer = data.getData();
            String src = content_describer.getPath();

            Log.e("aya","src=="+src);

            Log.e("content_describer","content_describer=="+src);
            // String directory_path="/storage/emulated/0/Documents/VanSalesDatabase";


        }

        else

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                itemBitmapPic = bitmap;
                logo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                visitPic = extras.getParcelable("data");
                visitPicture.setImageDrawable(new BitmapDrawable(getResources(), visitPic));
            }
        }
        //************************************************************
        if(requestCode== REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                Log.e("startVoiceInput2","result="+result);
            }

        }

        //************************************************************
        Log.e("MainActivity", ""+requestCode);
//        if (requestCode == 0x0000c0de) {
//        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (Result != null) {
//            if (Result.getContents() == null) {
//                Log.e("MainActivity", "cancelled scan");
//                Toast.makeText(MainActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
//            } else {
//
//                Log.e("MainActivity", "" + Result.getContents());
////                    Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
////                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
////                barCodTextTemp.setText(Result.getContents() + "");
////                openEditerCheck();
//
//                String serialBarcode = Result.getContents();
//
//
//            }
//        }

        switch (requestCode) {
            case 10001:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();

                        openDialog=true;
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();

                        openDialog=false;
                        break;
                    default:
                        break;
                }
                break;
        }
    }
  void  cleartext(){
      name.setText("");
      tel.setText("");
              tax.setText("");
              noteInvoice.setText("");
              salesman_car.setText("");
              salesman_id.setText("");
              salesman_name.setText("");
    }
}