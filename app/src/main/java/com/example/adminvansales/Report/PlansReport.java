package com.example.adminvansales.Report;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.PlansReportAdapter;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.HomeActivity;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.ItemReport;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.R;
import com.example.adminvansales.model.Plan_SalesMan_model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.adminvansales.model.LocaleAppUtils;
public class PlansReport extends AppCompatActivity {

    public static TextView fillPlan2;
    TextView dateEdt;
    EditText custNameSearch;
    RadioGroup orderRG;
    Spinner salesManSP;
    RecyclerView plans_recycler;
    String currDate;
    GlobelFunction globelFunction;
    public static List<Plan_SalesMan_model> allPlans;
    List<Plan_SalesMan_model> searchPlanList;
    ImportData importData;
    PlansReportAdapter plansReportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(PlansReport.this);
        setContentView(R.layout.activity_plans_report);
        setTitle("Plans Report");

        init();

    }

    private void init() {
        RelativeLayout linearMain=findViewById(R.id.linearMain);
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
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setSelectedItemId(R.id.action_reports);

        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_plan:

                                startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));
                                overridePendingTransition(0, 0);

                                return true;

                            case R.id.action_reports:

                                ReportsPopUpClass popUpClass = new ReportsPopUpClass();
                                popUpClass.showPopupWindow(item.getActionView(), PlansReport.this);

                                return true;

                            case R.id.action_location:

                                return true;

                            case R.id.action_notifications:

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(0, 0);

                                return true;
                        }
                        return false;
                    }
                });

        dateEdt = findViewById(R.id.dateEdt);
        custNameSearch = findViewById(R.id.custNameSearch);
        orderRG = findViewById(R.id.orderRG);
        salesManSP = findViewById(R.id.salesManSP);
        plans_recycler = findViewById(R.id.plans_recycler);
        fillPlan2 = findViewById(R.id.fillPlan2);

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

                globelFunction.DateClick(dateEdt);

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

                        if (allPlans.get(0).getType_orderd() == 1)
                            orderRG.check(R.id.locationRBtn);
                        else
                            orderRG.check(R.id.manualRBtn);

                        for (int p = 0; p < allPlans.size(); p++) {


                            if (!custName.equals("")) {
                                if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {

                                    searchPlanList.add(allPlans.get(p));

                                }
                            } else
                                searchPlanList.add(allPlans.get(p));

                        }

                        Log.e("All_Plans_size", allPlans.size()+"");

                        Log.e("searchPlanList", searchPlanList.toString());
                        Log.e("searchPlanList_size", searchPlanList.size()+"");

                        plansReportAdapter = new PlansReportAdapter(PlansReport.this, searchPlanList);
                        plans_recycler.setAdapter(plansReportAdapter);

                    } else if (editable.toString().contains("No Data Found")) {

                        allPlans.clear();
                        plansReportAdapter = new PlansReportAdapter(PlansReport.this, allPlans);
                        plans_recycler.setAdapter(plansReportAdapter);
                    }
                }

            }
        });

//        orderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                searchPlanList.clear();
//
//                String custName = custNameSearch.getText().toString().trim().toLowerCase();
//
//                if (i == R.id.locationRBtn) {
//
//                    for (int p = 0; p < allPlans.size(); p++) {
//
//                        if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {
//                            if (allPlans.get(p).getType_orderd() == 1) {
//
//                                searchPlanList.add(allPlans.get(p));
//
//                            }
//                        }
//
//                    }
//
//                } else {
//
//                    for (int p = 0; p < allPlans.size(); p++) {
//
//                        if (allPlans.get(p).getCustomerName().toLowerCase().contains(custName)) {
//                            if (allPlans.get(p).getType_orderd() == 0) {
//
//                                searchPlanList.add(allPlans.get(p));
//
//                            }
//                        }
//
//                    }
//
//                }
//                plansReportAdapter = new PlansReportAdapter(PlansReport.this, searchPlanList);
//                plans_recycler.setAdapter(plansReportAdapter);
//
//            }
//        });

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

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
    }

}