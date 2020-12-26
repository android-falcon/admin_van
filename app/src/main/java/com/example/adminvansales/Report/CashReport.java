package com.example.adminvansales.Report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.CashReportAdapter;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.PayMentReportAdapter;
import com.example.adminvansales.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class CashReport extends AppCompatActivity {

    TextView fromDate,toDate;
    GlobelFunction globelFunction;
    String toDay;
    CashReportAdapter payMentReportAdapter;
    ListView listCashReport;
    public static List<CashReportModel> cashReportList;
    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_report);
        initial();
    }


    private void initial() {

        fromDate=findViewById(R.id.from_date_r);
        toDate=findViewById(R.id.to_date_r);
        listCashReport=findViewById(R.id.listCashReport);
        previewButton=findViewById(R.id.previewButton);
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        globelFunction=new GlobelFunction(CashReport.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        cashReportList=new ArrayList<>();
        importData=new ImportData(CashReport.this);
        importData.getCashReport(CashReport.this,toDay,toDay);

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);

        fillSalesManSpinner();

    }

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.previewButton :
                    previewFunction();
                    break;

                case R.id.from_date_r :
                    globelFunction.DateClick(fromDate);
                    break;

                case R.id.to_date_r :
                    globelFunction.DateClick(toDate);
                    break;

            }

        }
    };


    public void previewFunction(){
//
//        String payKind="-1";
//        String salesNo="-1";
//
//
//        int positionSales=salesNameSpinner.getSelectedItemPosition();
//
//        if (positionSales == 0 || positionSales==-1) {
//            salesNo = "-1";
//            Log.e("salesNo-1", "" + salesNo +"  ");
//
//        } else {
//            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
//            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
//        }


        importData.getCashReport(CashReport.this,fromDate.getText().toString(),toDate.getText().toString());

    }

    public void fillSalesManSpinner(){

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillCashAdapter(){

        int positionSales=salesNameSpinner.getSelectedItemPosition();
//
        String salesNo;
        List<CashReportModel> TempReports=new ArrayList<>();
        if (positionSales == 0 || positionSales==-1) {
            salesNo = "-1";
            Log.e("salesNo-1", "" + salesNo +"  ");
            payMentReportAdapter = new CashReportAdapter(CashReport.this, cashReportList);
            listCashReport.setAdapter(payMentReportAdapter);
        } else {
            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
            for (int i=0;i<cashReportList.size();i++){
                if(cashReportList.get(i).getSalesManNo().equals(salesNo)){
                    TempReports.add(cashReportList.get(i));
                    break;
                }
            }

            payMentReportAdapter = new CashReportAdapter(CashReport.this, TempReports);
            listCashReport.setAdapter(payMentReportAdapter);

        }


    }

}
