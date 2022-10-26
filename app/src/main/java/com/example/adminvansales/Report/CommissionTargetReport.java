package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.CommTargetReportAdapter;
import com.example.adminvansales.Adapters.ItemsTargetAdapter;
import com.example.adminvansales.Adapters.NetsaleTargetAdapter;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class CommissionTargetReport extends AppCompatActivity {
    Spinner dateEdt;
    String Month;
    RecyclerView itemtargetrec;

    int TargetType = 0;
    private TextInputLayout salman_textInput;
    private AutoCompleteTextView salmanTv;
    String salman_selection = "";
    int salman_pos;
    String SalmanNo;
    ImportData importData;
    public static TextView COMMitemTargetRespon;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    RecyclerView targetrec, targetItemsrec;
    GlobelFunction globelFunction;
    AppCompatButton previewButton;
    public static TextView totalgoalachievementrate,totalCommissiontarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_target_report);
        init();

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Month = dateEdt.getSelectedItem().toString();
                if (salman_selection != null && !salman_selection.equals("")) {
                    {



                             importData.getCommGoalItems(CommissionTargetReport.this, Integer.parseInt(SalmanNo) + "", Month.substring(0, Month.indexOf(" ")));

                    }
                } else {
                    salman_textInput.setError("");
                    globelFunction.showSweetDialog(CommissionTargetReport.this, 3, getResources().getString(R.string.selectsalman), "");
                }
            }
        });

        globelFunction = new GlobelFunction(CommissionTargetReport.this);
        Month = dateEdt.getSelectedItem().toString();
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

    void init() {
        LinearLayout linearMain = findViewById(R.id.linearMain);
        try {
            if (LogIn.languagelocalApp.equals("ar")) {
                linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                if (LogIn.languagelocalApp.equals("en")) {
                    linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        } catch (Exception e) {
            linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        salman_textInput = findViewById(R.id.saleman_textInput);
        totalgoalachievementrate= findViewById(R.id.totalgoalachievementrate);
        totalCommissiontarget= findViewById(R.id.totalCommissiontarget);
        importData = new ImportData(CommissionTargetReport.this);
        targetrec = findViewById(R.id.targetrec);
        previewButton = findViewById(R.id.previewButton);
        itemtargetrec = findViewById(R.id.itemtargetrec);

        itemtargetrec.setLayoutManager(new LinearLayoutManager(CommissionTargetReport.this));

        salmanTv = findViewById(R.id.salemanTv);
        dateEdt = findViewById(R.id.dateEdt);

        COMMitemTargetRespon = findViewById(R.id.itemTargetRespon);

        COMMitemTargetRespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().trim().equals("")) {
                    if(COMMitemTargetRespon.getText().toString().equals("NoData")){
                        fillAdapterItemsTarget();
                    }else

                    fillAdapterItemsTarget();
                }

            }
        });


    }

    void fillAdapterItemsTarget() {

        Log.e("fillAdapterItemsTarget", "fillAdapterItemsTarget" + ImportData.ItemsCommGoalsList.size());
        itemtargetrec.setAdapter(new CommTargetReportAdapter(ImportData.ItemsCommGoalsList, CommissionTargetReport.this));
        double total_goalachievementrate=0,total_Commissiontarget=0;
       for(int i=0;i< ImportData.ItemsCommGoalsList.size();i++)
       {
           total_goalachievementrate+=Double.parseDouble(ImportData.ItemsCommGoalsList.get(i).getPERC());

//                   total_Commissiontarget+=ImportData.ItemsCommGoalsList.get(i).getItemTarget();
       }
        totalgoalachievementrate.setText(total_goalachievementrate+"");
       //         totalCommissiontarget.setText(total_Commissiontarget+"");
    }
}