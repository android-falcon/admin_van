package com.example.adminvansales.Report;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.PlansReportAdapter;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.HomeActivity;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.R;
import com.example.adminvansales.model.Plan_SalesMan_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlansReport extends AppCompatActivity {

    public static TextView fillPlan2;
    EditText dateEdt, custNameSearch;
    RadioGroup orderRG;
    Spinner salesManSP;
    RecyclerView plans_recycler;
    String currDate;
    GlobelFunction globelFunction;
    public static List<Plan_SalesMan_model> allPlans;
    List<Plan_SalesMan_model> searchPlanList;
    ImportData importData;
    PlansReportAdapter plansReportAdapter;
    LinearLayout noDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans_report);
        setTitle("Plans Report");

        init();

    }

    private void init() {

        dateEdt = findViewById(R.id.dateEdt);
        custNameSearch = findViewById(R.id.custNameSearch);
        orderRG = findViewById(R.id.orderRG);
        salesManSP = findViewById(R.id.salesManSP);
        plans_recycler = findViewById(R.id.plans_recycler);
        fillPlan2 = findViewById(R.id.fillPlan2);
        noDataLayout = findViewById(R.id.noDataLayout);

        importData = new ImportData(PlansReport.this);
        allPlans = new ArrayList<>();
        searchPlanList = new ArrayList<>();
        globelFunction = new GlobelFunction(PlansReport.this);

        currDate = globelFunction.DateInToday();
        dateEdt.setText(currDate);

//        importData.getPlan(salesManSP.getSelectedItem().toString(), dateEdt.getText().toString(), 1);

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                globelFunction.DateClick((TextView) dateEdt);

            }
        });
        dateEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                String salesNum = salesManInfosList.get(salesManSP.getSelectedItemPosition()).getSalesManNo();

                Log.e("Date_Changed", "" + editable);
                importData.getPlan(salesNum, editable.toString(), 1);

            }
        });

        fillPlan2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("fillPlan_Report", "" + editable);

                    if (editable.toString().equals("fill")) {

//                        Log.e("Plans_" + dateEdt.getText() + "_" + salesManSP.getSelectedItem(), allPlans.toString());

                        searchPlanList.clear();

                        String custName = custNameSearch.getText().toString().trim().toLowerCase();

                        for (int p = 0; p < allPlans.size(); p++) {

                            if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {
                                if (allPlans.get(p).getType_orderd() == 1 && orderRG.getCheckedRadioButtonId() == R.id.locationRBtn)
                                    searchPlanList.add(allPlans.get(p));

                                else if (allPlans.get(p).getType_orderd() == 0 && orderRG.getCheckedRadioButtonId() == R.id.manualRBtn)
                                    searchPlanList.add(allPlans.get(p));
                            }

                        }

                        plansReportAdapter = new PlansReportAdapter(PlansReport.this, searchPlanList);
                        plans_recycler.setAdapter(plansReportAdapter);
                        noDataLayout.setVisibility(View.GONE);

                    } else if (editable.toString().contains("No Data Found")) {

                        allPlans.clear();
                        plansReportAdapter = new PlansReportAdapter(PlansReport.this, allPlans);
                        plans_recycler.setAdapter(plansReportAdapter);
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        orderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                searchPlanList.clear();

                String custName = custNameSearch.getText().toString().trim().toLowerCase();

                if (i == R.id.locationRBtn) {

                    for (int p = 0; p < allPlans.size(); p++) {

                        if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {
                            if (allPlans.get(p).getType_orderd() == 1) {

                                searchPlanList.add(allPlans.get(p));

                            }
                        }

                    }

                } else {

                    for (int p = 0; p < allPlans.size(); p++) {

                        if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {
                            if (allPlans.get(p).getType_orderd() == 0) {

                                searchPlanList.add(allPlans.get(p));

                            }
                        }

                    }

                }
                plansReportAdapter = new PlansReportAdapter(PlansReport.this, searchPlanList);
                plans_recycler.setAdapter(plansReportAdapter);

            }
        });

        custNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                searchPlanList.clear();

                for (int p = 0; p < allPlans.size(); p++) {

                    if (allPlans.get(p).getCustomerName().toLowerCase().contains(editable.toString().trim().toLowerCase()) &&
                            ((allPlans.get(p).getType_orderd() == 1 && orderRG.getCheckedRadioButtonId() == R.id.locationRBtn) ||
                                    (allPlans.get(p).getType_orderd() == 0 && orderRG.getCheckedRadioButtonId() == R.id.manualRBtn))) {

                        searchPlanList.add(allPlans.get(p));

                    }

                }

                plansReportAdapter = new PlansReportAdapter(PlansReport.this, searchPlanList);

                plans_recycler.setAdapter(plansReportAdapter);


            }
        });

//        globelFunction.getSalesManInfo(PlansReport.this, 3);

        ArrayAdapter<String> salesManSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, salesManNameList);
        salesManSP.setAdapter(salesManSpinnerAdapter);
        salesManSP.setSelection(0);
        Log.e("salesManNameList", salesManNameList.toString());

        salesManSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String salesNum = salesManInfosList.get(i).getSalesManNo();

                Log.e("onItemSelected", "" + salesNum);
                importData.getPlan(salesNum, dateEdt.getText().toString(), 1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}