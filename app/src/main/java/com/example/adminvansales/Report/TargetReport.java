package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.Adapters.ItemsTargetAdapter;
import com.example.adminvansales.Adapters.NetsaleTargetAdapter;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.R;
import com.example.adminvansales.addSalesmanTarget;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class TargetReport extends AppCompatActivity {
    Spinner dateEdt;
    String Month;
    RecyclerView NteSal_targetrec,itemtargetrec;
    RadioGroup TargettypeRG;
    int  TargetType=0;
    private TextInputLayout salman_textInput;
    private AutoCompleteTextView salmanTv;
    String salman_selection="";
    int salman_pos;
    String  SalmanNo;
    ImportData importData;
    public  static TextView NetsalTargetRespon,itemTargetRespon;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    RecyclerView targetrec,targetItemsrec;
    GlobelFunction globelFunction;
    AppCompatButton previewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_report);

        init();

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Month = dateEdt.getSelectedItem().toString();
                if (salman_selection != null && !salman_selection.equals("")) {
                    {
                        if (TargetType == 0) {
                            importData.salesGoalsList.clear();
                            importData.ItemsGoalsList.clear();
                            fillAdapterNetsalTarget();
                            itemtargetrec.setAdapter(new ItemsTargetAdapter(importData.ItemsGoalsList,TargetReport.this));
                            importData.getSalesmanGoal(TargetReport.this, Integer.parseInt(SalmanNo)+"", Month.substring(0, Month.indexOf(" ")));
                                     } else {
                            importData.ItemsGoalsList.clear();
                            importData.salesGoalsList.clear();
                            fillAdapterItemsTarget();
                            NteSal_targetrec.setAdapter(new NetsaleTargetAdapter(importData.salesGoalsList,TargetReport.this));
                            importData.getSaleGoalItems(TargetReport.this, Integer.parseInt(SalmanNo)+"", Month.substring(0, Month.indexOf(" ")));
                        }
                    }
                } else {
                    salman_textInput.setError("");
                    globelFunction.showSweetDialog(TargetReport.this, 3, getResources().getString(R.string.selectsalman), "");
                }
            }
        });

        globelFunction=new GlobelFunction(TargetReport.this);
        Month=dateEdt.getSelectedItem().toString();
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
    public void fillSalesManSpinner() {

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, salesManNameList);

        salmanTv.setAdapter(salesNameSpinnerAdapter);

    }
    void init(){
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
        salman_textInput= findViewById(R.id.saleman_textInput);
     importData=new ImportData(TargetReport.this);
        targetrec = findViewById(R.id.targetrec);
        previewButton= findViewById(R.id.    previewButton);
        itemtargetrec= findViewById(R.id.itemtargetrec);
        NteSal_targetrec= findViewById(R.id.NteSal_targetrec);
        NteSal_targetrec.setLayoutManager(new LinearLayoutManager(TargetReport.this));
        itemtargetrec.setLayoutManager(new LinearLayoutManager(TargetReport.this));

        salmanTv = findViewById(R.id.salemanTv);
        dateEdt=findViewById(R.id.dateEdt);
        TargettypeRG=findViewById(R.id.TargettypeRG);
        itemTargetRespon=findViewById(R.id.itemTargetRespon);
        NetsalTargetRespon=findViewById(R.id.NetsalTargetRespon);
        itemTargetRespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().equals(""))
                {
                    fillAdapterItemsTarget();
                }
            }
        });
        NetsalTargetRespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().equals(""))
                {
                    fillAdapterNetsalTarget();
                }
            }
        });
        TargettypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = TargettypeRG.findViewById(checkedId);
                int index = TargettypeRG.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0:  //  items

                        TargetType = 1;


                        //     Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                    case 1://  netsale
                        TargetType = 0;

                        //      Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                }
            }
        });
    }
    void  fillAdapterNetsalTarget(){

        Log.e("fillAdapterNetsalTarget","fillAdapterNetsalTarget"+ImportData.salesGoalsList.size());
        NteSal_targetrec.setAdapter(new NetsaleTargetAdapter(ImportData.salesGoalsList,TargetReport.this));

    }
    void  fillAdapterItemsTarget(){

        Log.e("fillAdapterItemsTarget","fillAdapterItemsTarget"+ImportData.ItemsGoalsList.size());
        itemtargetrec.setAdapter(new ItemsTargetAdapter(ImportData.ItemsGoalsList,TargetReport.this));

    }
}