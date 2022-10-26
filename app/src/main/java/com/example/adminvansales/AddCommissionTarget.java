package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.CommissionTargetAdapter;
import com.example.adminvansales.Adapters.TargetAdapter;
import com.example.adminvansales.model.CommissionTarget;
import com.example.adminvansales.model.TargetDetalis;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class AddCommissionTarget extends AppCompatActivity {
    ArrayAdapter<String> salesNameSpinnerAdapter;
    private AutoCompleteTextView salmanTv;
    private TextInputLayout salman_textInput;
    ImportData importData;
    String Month;
    Spinner dateEdt;
    String salman_selection="";
    GlobelFunction globelFunction;
    AppCompatButton saveButton;
    TextView scanItemCode,writeitemno;
    public static RecyclerView targetrec;
    public static EditText itemcodeedt;
    public  String SalmanNo;
    ExportData exportData;
    public static TextView colorlastrow, colorData;
    public static int highligtedItemPosition = -1;
    public static CommissionTargetAdapter targetAdapter;
    public static int highligtedItemPosition2 = -1;
    public static ArrayList<CommissionTarget> targetDetalisList=new ArrayList<>();
    public static ArrayList<CommissionTarget>targetList=new ArrayList<>();
    int pos;
    public static final int REQUEST_Camera_Barcode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commission_target);
        init();
        fillSalesManSpinner();

        salmanTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                salman_selection = (String) parent.getItemAtPosition(position);
                SalmanNo = globelFunction.getsalesmanNum(salman_selection);


                Log.e("salman_selection==", salman_selection);
                salman_textInput.setError(null);
                salman_textInput.clearFocus();
            }
        });
        try {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    importData = new ImportData(AddCommissionTarget.this);
                    importData.fetchItemMaster(2);
                }

            }, 100);


        } catch (Exception e) {
        }
    }
    void init(){
        exportData=new ExportData(AddCommissionTarget.this);
        salman_textInput = findViewById(R.id.saleman_textInput);
        salmanTv = findViewById(R.id.salemanTv);
        dateEdt=findViewById(R.id.dateEdt);
        Month=dateEdt.getSelectedItem().toString();
        targetrec = findViewById(R.id.targetrec);
        targetrec .setLayoutManager(new LinearLayoutManager(this));
        scanItemCode=findViewById(R.id.scanItemCode);
        scanItemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBarcode(5);

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
                        targetAdapter=       new CommissionTargetAdapter(targetDetalisList, AddCommissionTarget.this);

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
                      //item target
                        for (int i = 0; i < targetList.size(); i++) {
                            Log.e("ItemTarget==", targetList.get(i).getItemTarget() + "");

                            targetList.get(i).setTargetType(1);
                            targetList.get(i).setDate( Month.substring(0, Month.indexOf(" ")));
                            targetList.get(i).setSalManNo(Integer.parseInt(SalmanNo)+"");
                            targetList.get(i).setSalManName(salman_selection);

                            Log.e("targetDetalisList==", targetList.size() + "");
                        }

                        if (targetList.size() == 0)
                            globelFunction.showSweetDialog(AddCommissionTarget.this, 3, getResources().getString(R.string.filllist), "");

                        else

                    exportData.SaveCommissionTarget(targetList, AddCommissionTarget.this);







                    targetList.clear();
                    targetDetalisList.clear();
                    fillAdapter(AddCommissionTarget.this);

                }else {
                    salman_textInput.setError("");
                    globelFunction.showSweetDialog(AddCommissionTarget.this, 3, getResources().getString(R.string.selectsalman), "");
                }
            }

        });
        globelFunction=new GlobelFunction(AddCommissionTarget.this);
        writeitemno=findViewById(R.id.writeitemno);
        //NetsaleTargetEditText.setEnabled(false);




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
    }
    public void fillSalesManSpinner() {

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, salesManNameList);

        salmanTv.setAdapter(salesNameSpinnerAdapter);

    }
    public static void fillAdapter(Context context){
        targetDetalisList.clear();
        for(int i=0;i<ImportData.listAllItemReportModels.size();i++) {
            CommissionTarget targetDetalis = new CommissionTarget();
            targetDetalis.setItemTarget(0);
            targetDetalis.setItemNo(ImportData.listAllItemReportModels.get(i).getItemNo());
            targetDetalis.setItemName(ImportData.listAllItemReportModels.get(i).getName());

            targetDetalisList.add(targetDetalis);
        }
        targetAdapter=new CommissionTargetAdapter(targetDetalisList,context);
        targetrec.setAdapter(targetAdapter);

    }
    private void openDialog() {

        final Dialog dialog = new Dialog(AddCommissionTarget.this, R.style.Theme_Dialog);
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
    private void colorlastrow3(int pos) {

        Log.e("colorlastrow", "" + pos);

        highligtedItemPosition2 = pos;

        if (targetAdapter != null) targetAdapter.notifyDataSetChanged();

        targetrec.smoothScrollToPosition(pos);

    }
    public void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }
    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(AddCommissionTarget.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddCommissionTarget.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(AddCommissionTarget.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(AddCommissionTarget.this, ScanActivity.class);
                i.putExtra("key", type);
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(AddCommissionTarget.this, ScanActivity.class);
            i.putExtra("key", type + "");

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
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

}