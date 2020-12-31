package com.example.adminvansales.Report;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.adminvansales.BuildConfig;
import com.example.adminvansales.CashReportAdapter;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.PayMentReportAdapter;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class CashReport extends AppCompatActivity {

    TextView fromDate,toDate,excelConvert,pdfConvert,share;
    GlobelFunction globelFunction;
    String toDay;
    CashReportAdapter payMentReportAdapter;
    ListView listCashReport;
    public static List<CashReportModel> cashReportList;
    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    List<CashReportModel> TempReports;
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
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        globelFunction=new GlobelFunction(CashReport.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        cashReportList=new ArrayList<>();
        TempReports=new ArrayList<>();
        importData=new ImportData(CashReport.this);
        importData.getCashReport(CashReport.this,toDay,toDay);

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);


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
                case R.id.excelConvert :
                    convertToExcel();
                    break;
                case R.id.pdfConvert :
                    convertToPdf();
                    break;
                case R.id.share:
                    shareWhatsApp();
                    break;

            }

        }
    };

    private File convertToPdf() {
        PdfConverter pdf =new PdfConverter(CashReport.this);
        File file=pdf.exportListToPdf(TempReports,"Cash Report",toDay,2);
        return file;
    }

    private File convertToExcel() {

        ExportToExcel exportToExcel=new ExportToExcel();
        File file= exportToExcel.createExcelFile(CashReport.this,"CashReport.xls",3,TempReports);
        return file;
    }

    public void shareWhatsApp(){
       globelFunction.shareWhatsAppA(convertToPdf(),1);
    }





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
        TempReports.clear();
        if (positionSales == 0 || positionSales==-1) {
            salesNo = "-1";
            Log.e("salesNo-1", "" + salesNo +"  ");
            TempReports=cashReportList;
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
