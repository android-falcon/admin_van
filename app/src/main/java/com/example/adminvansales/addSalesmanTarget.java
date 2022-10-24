package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.Adapters.TargetAdapter;
import com.example.adminvansales.model.TargetDetalis;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class addSalesmanTarget extends AppCompatActivity {
    public static int highligtedItemPosition = -1;
    public static  boolean  fabClick=true;
    public static int highligtedItemPosition2 = -1;
    public static TextView colorlastrow, colorData;
    RadioGroup TargettypeRG;
  int  TargetType=0;
    ImportData importData;
    EditText NetsaleTargetEditText;
    int pos;
   String salman_selection="";
   int salman_pos;
    Spinner dateEdt;
    public static final int REQUEST_Camera_Barcode = 1;
    private TextInputLayout salman_textInput;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    public static   TargetAdapter targetAdapter;

    Calendar myCalendar;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    private AutoCompleteTextView salmanTv;
public static    RecyclerView targetrec;
   GlobelFunction globelFunction;
   AppCompatButton saveButton;
    public static  EditText itemcodeedt;
    public  String SalmanNo;
    TextView scanItemCode,writeitemno;
    String Month;
    ExportData exportData;
    LinearLayout netsalLin,itemslin,searchlin;
    public static ArrayList<TargetDetalis>targetDetalisList=new ArrayList<>();
    public static ArrayList<TargetDetalis>targetList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salesman_target);

        init();
        try {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    importData.fetchItemMaster();
                }

            }, 100);


        } catch (Exception e) {

        }

    }
    void init(){
        importData = new ImportData(addSalesmanTarget.this);
        targetrec = findViewById(R.id.targetrec);
        NetsaleTargetEditText=findViewById(R.id. NetsaleTarget);
        exportData=new ExportData(addSalesmanTarget.this);
        netsalLin=findViewById(R.id.netsalLin);
        itemslin=findViewById(R.id.itemslin);
        searchlin=findViewById(R.id.searchlin);
        writeitemno=findViewById(R.id.writeitemno);
        //NetsaleTargetEditText.setEnabled(false);

        fabClick=false;


        writeitemno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        colorlastrow = findViewById(R.id.colorlastrow);
        colorlastrow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() != 0) {
                    Log.e("colorlastrow", "" + editable.toString().trim());
                    int position = Integer.parseInt(editable.toString().trim());
                    colorlastrow3((position));

                }

            }
        });
        LinearLayout linearMain=findViewById(R.id.linearMain);
        try{
            if(LogIn.languagelocalApp.equals("ar"))
            {
                linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            else{
                if(LogIn.languagelocalApp.equals("en"))
                {
                    linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch ( Exception e)
        {
            linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        scanItemCode=findViewById(R.id.scanItemCode);
        scanItemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBarcode(4);

            }
        });

        itemcodeedt=findViewById(R.id.itemcodeedt);
        itemcodeedt.setEnabled(false);
        itemcodeedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("")) {
                    if (CheckIsExistsINLocalList(itemcodeedt.getText().toString())) {
                        targetDetalisList.add(0, targetDetalisList.get(pos));
                        targetDetalisList.remove(pos + 1);
                        colorlastrow.setText(0 + "");
                     targetAdapter=       new TargetAdapter(targetDetalisList, addSalesmanTarget.this);

                        targetrec.setAdapter(targetAdapter);
                    }
                }
            }
        });
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (salman_selection != null && !salman_selection.equals("")) {
                    Month=dateEdt.getSelectedItem().toString();
                    if (TargetType == 1) { //item target
                        for (int i = 0; i < targetList.size(); i++) {
                            Log.e("ItemTarget==", targetList.get(i).getItemTarget() + "");

                            targetList.get(i).setTargetType(TargetType);
                            targetList.get(i).setDate( Month.substring(0, Month.indexOf(" ")));
                            try {
                                targetList.get(i).setSalManNo(Integer.parseInt(SalmanNo)+"");
                            }
                            catch (Exception e){
                                targetList.get(i).setSalManNo(SalmanNo);
                            }
                            targetList.get(i).setSalManName(salman_selection);

                            Log.e("targetDetalisList==", targetList.size() + "");
                        }

                            if (targetList.size() == 0)
                                globelFunction.showSweetDialog(addSalesmanTarget.this, 3, getResources().getString(R.string.filllist), "");

                            else

                                exportData.SaveNetsaleTarget2(targetList, addSalesmanTarget.this);




                    } else       //netsal target
                    {
                        Log.e("TargetType==", "2");
                        if (!NetsaleTargetEditText.getText().toString().equals("")) {
                            String no = globelFunction.getsalesmanNum(salman_selection);

                            targetDetalisList.clear();
                            TargetDetalis targetDetalis = new TargetDetalis();
                            targetDetalis.setTargetType(0);
                            targetDetalis.setSalManNo(no);
                            targetDetalis.setSalManName(salman_selection);
                            targetDetalis.setTargetNetSale(NetsaleTargetEditText.getText().toString().trim());
                            targetDetalis.setDate( Month.substring(0, Month.indexOf(" ")));
                            targetDetalisList.add(targetDetalis);
                            exportData.SaveNetsaleTarget(targetDetalisList, addSalesmanTarget.this);
                        } else {
                            GlobelFunction.showSweetDialog(addSalesmanTarget.this, 0, getResources().getString(R.string.fillthenetsaletarget), "");

                            NetsaleTargetEditText.setError("");
                        }

                    }
                    NetsaleTargetEditText.setText("");

                    targetList.clear();
                    targetDetalisList.clear();
                    fillAdapter(addSalesmanTarget.this);

                } else {
                    salman_textInput.setError("");
                    globelFunction.showSweetDialog(addSalesmanTarget.this, 3, getResources().getString(R.string.selectsalman), "");
                }
            }

        });
        globelFunction=new GlobelFunction(addSalesmanTarget.this);
        importData = new ImportData(addSalesmanTarget.this);

        targetrec .setLayoutManager(new LinearLayoutManager(this));
        salman_textInput = findViewById(R.id.saleman_textInput);
        salmanTv = findViewById(R.id.salemanTv);
        dateEdt=findViewById(R.id.dateEdt);
        Month=dateEdt.getSelectedItem().toString();
//        dateEdt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(addSalesmanTarget.this, openDatePickerDialog(0), myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        myCalendar = Calendar.getInstance();
        String today = df.format(currentTimeAndDate);
        GlobelFunction globelFunction=new GlobelFunction(addSalesmanTarget.this);
//        dateEdt.setText(globelFunction.convertToEnglish(today));
        TargettypeRG=findViewById(R.id.TargettypeRG);
        TargettypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = TargettypeRG.findViewById(checkedId);
                int index = TargettypeRG.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button  netsale
                        NetsaleTargetEditText.setEnabled(true);
                        TargetType = 0;

                        fabClick=false;
                        final Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(targetAdapter!=null)  fillAdapter(addSalesmanTarget.this);
                            }

                        }, 100);

                        //     Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                    case 1: // secondbutton items
                        TargetType = 1;
                        fabClick=true;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(targetAdapter!=null)  fillAdapter(addSalesmanTarget.this);
                            }

                        }, 100);
                        NetsaleTargetEditText.setEnabled(false);

                        //      Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                }
            }
        });
        fillSalesManSpinner();

        salmanTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
               salman_selection = (String) parent.getItemAtPosition(position);
                SalmanNo = globelFunction.getsalesmanNum(salman_selection);

                salman_pos = position;
                Log.e("salman_selection==", salman_selection);
                salman_textInput.setError(null);
                salman_textInput.clearFocus();






            }
        });
    }
    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanItemCode:
                readBarcode(4);

                break;




        }
    }
    public void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }
    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(addSalesmanTarget.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(addSalesmanTarget.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(addSalesmanTarget.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(addSalesmanTarget.this, ScanActivity.class);
                i.putExtra("key", type);
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(addSalesmanTarget.this, ScanActivity.class);
            i.putExtra("key", type + "");

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
    }
    public void fillSalesManSpinner() {

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, salesManNameList);

        salmanTv.setAdapter(salesNameSpinnerAdapter);

    }
    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(flag);
            }

        };
        return date;
    }
    private void updateLabel(int flag) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//        if (flag == 0)
//            dateEdt.setText(sdf.format(myCalendar.getTime()));
//        else
//            todate.setText(sdf.format(myCalendar.getTime()));

    }
 public static void fillAdapter(Context context){
     targetDetalisList.clear();
     for(int i=0;i<ImportData.listAllItemReportModels.size();i++) {
         TargetDetalis targetDetalis = new TargetDetalis();
         targetDetalis.setItemTarget(0);
         targetDetalis.setItemNo(ImportData.listAllItemReportModels.get(i).getItemNo());
         targetDetalis.setItemName(ImportData.listAllItemReportModels.get(i).getName());
         targetDetalisList.add(targetDetalis);
     }
     targetAdapter=new TargetAdapter(targetDetalisList,context);
        targetrec.setAdapter(targetAdapter);

    }
    private boolean CheckIsExistsINLocalList(String barcode) {


        boolean flag = false;
        if (targetDetalisList.size() != 0)
            for (int i = 0; i < targetDetalisList.size(); i++) {

                if (
                        globelFunction.convertToEnglish(targetDetalisList.get(i).getItemNo()).equals(globelFunction.convertToEnglish(barcode))

                ) {
                    pos = i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }
    private void colorlastrow3(int pos) {

        Log.e("colorlastrow", "" + pos);

        highligtedItemPosition2 = pos;

       if (targetAdapter != null) targetAdapter.notifyDataSetChanged();

        targetrec.smoothScrollToPosition(pos);

    }
    private void colorRecycle(int pos) {

        Log.e("colorRecycle", "" + pos);
        highligtedItemPosition2 = -5;
        highligtedItemPosition = pos;

        targetAdapter.notifyDataSetChanged();
        //     replacmentRecycler.scrollToPosition(pos);
        targetrec.smoothScrollToPosition(pos);

    }
    private void openDialog() {

        final Dialog dialog = new Dialog(addSalesmanTarget.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.write_itemnum);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button saveButton = (Button) dialog.findViewById(R.id.ok);

     EditText   itemnumEdit = (EditText) dialog.findViewById(R.id.itemnumEdit);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemnumEdit.getText().toString().equals("")) {
                    itemcodeedt.setText( itemnumEdit.getText().toString().trim());
                    dialog.dismiss();
                } else {
                    itemnumEdit.setError("Required");
                }


            }
        });


        dialog.show();
    }
}