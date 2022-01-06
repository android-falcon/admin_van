package com.example.adminvansales;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlanSalesMan extends AppCompatActivity {
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_sales_man);
        initialView();
        fillSalesManSpinner();
    }

    private void initialView() {
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
    }

    private void fillSalesManSpinner() {
        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);
    }
}