package com.example.adminvansales.Report;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Adapters.CashReportAdapter;
import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.model.CashReportModel;
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
    com.example.adminvansales.model.SettingModel SettingModel;
    DataBaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_report);

        initial();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initial() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
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
        databaseHandler=new DataBaseHandler(CashReport.this);
        SettingModel=databaseHandler.getAllSetting();
        fillSalesManSpinner();
        try {
            int positionSales=salesNameSpinner.getSelectedItemPosition();
            String no= globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
            if( SettingModel.getImport_way().equals("0"))
                importData.getCashReport(CashReport.this,toDay,toDay);
            else   if( SettingModel.getImport_way().equals("1"))
                importData.  IIS_getCashReport(CashReport.this,toDay,toDay,no);
        }
       catch (Exception e){

       }
        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);




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

    private void convertToExcel() {

        ExportToExcel exportToExcel=new ExportToExcel();
     exportToExcel.createExcelFile(CashReport.this,"CashReport.xls",3,TempReports);
       // return file;
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
        int positionSales=salesNameSpinner.getSelectedItemPosition();
        String no="";
if(salesNameSpinner.getSelectedItem()!=null) {
    no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
    if (SettingModel.getImport_way().equals("0"))
        importData.getCashReport(CashReport.this, fromDate.getText().toString(), toDate.getText().toString());
    else if (SettingModel.getImport_way().equals("1"))
        importData.IIS_getCashReport(CashReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);
}  }

    public void fillSalesManSpinner(){

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillCashAdapter() {
        try {


            int positionSales = salesNameSpinner.getSelectedItemPosition();
//
            String salesNo;
            TempReports.clear();
//        if (positionSales == 0 || positionSales==-1) {
//            salesNo = "-1";
//            Log.e("salesNo-1", "" + salesNo +"  ");
//            TempReports=new ArrayList<>(cashReportList);
//            payMentReportAdapter = new CashReportAdapter(CashReport.this, cashReportList);
//            listCashReport.setAdapter(payMentReportAdapter);
//        }
            // else
            {
                salesNo = salesManInfosList.get(positionSales).getSalesManNo();
                Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales).getSalesName() + "    " + positionSales);
                for (int i = 0; i < cashReportList.size(); i++) {
                    if (cashReportList.get(i).getSalesManNo().equals(salesNo)) {
                        TempReports.add(cashReportList.get(i));
                        break;
                    }
                }

                payMentReportAdapter = new CashReportAdapter(CashReport.this, TempReports);
                listCashReport.setAdapter(payMentReportAdapter);

            }



    }catch(Exception exception){}
}}
