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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Adapters.CustomerLogReportAdapter;
import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.model.CustomerLogReportModel;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class CustomerLogReport extends AppCompatActivity {

    TextView fromDate,toDate,excelConvert,pdfConvert,share;
    GlobelFunction globelFunction;
    String toDay;
    CustomerLogReportAdapter customerLogReportAdapter;
    ListView listCustomerLogReport;
    public static List<CustomerLogReportModel> customerLogReportList;
    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    com.example.adminvansales.model.SettingModel settingModel;
    DataBaseHandler databaseHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_log_report);
        settingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(CustomerLogReport.this);

        initial();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initial() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        fromDate=findViewById(R.id.from_date_r);
        toDate=findViewById(R.id.to_date_r);
        listCustomerLogReport=findViewById(R.id.listCustomerLogReport);
        previewButton=findViewById(R.id.previewButton);
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        globelFunction=new GlobelFunction(CustomerLogReport.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        customerLogReportList=new ArrayList<>();
        fillSalesManSpinner();
        importData=new ImportData(CustomerLogReport.this);
            try {
                String no= globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());

                settingModel=databaseHandler.getAllSetting();
            if( settingModel.getImport_way().equals("0"))
                importData.getCustomerLogReport(CustomerLogReport.this,"-1",toDay,toDay);
            else if( settingModel.getImport_way().equals("1"))
                importData.  IIS_getCustomerLogReport(CustomerLogReport.this,no,toDay,toDay);

        }catch (Exception e){}

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
                case R.id.share :
                    shareWhatsApp();
                    break;

            }

        }
    };

    public void shareWhatsApp(){
        try {

            globelFunction.shareWhatsAppA(convertToPdf(),1);
        } catch (Exception e) {
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private File convertToPdf() {
        File file=null;
        try {

            PdfConverter pdf =new PdfConverter(CustomerLogReport.this);
             file=     pdf.exportListToPdf(customerLogReportList,"Customer Log Report",toDay,1);
         } catch (Exception e) {
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
   return file;
    }

    private void convertToExcel() {
      try {
            ExportToExcel exportToExcel = new ExportToExcel();
            exportToExcel.createExcelFile(CustomerLogReport.this, "CustomerLogReport.xls", 1, customerLogReportList);
     } catch (Exception e) {
     Log.e(" convertToExcel",e.getMessage());
           Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();

  }

    }


    public void previewFunction(){

        String payKind="-1";
        String salesNo="-1";



        int positionSales=salesNameSpinner.getSelectedItemPosition();

        if (positionSales == 0 || positionSales==-1) {
            salesNo = "-1";
            Log.e("salesNo-1", "" + salesNo +"  ");

        } else {
            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
        }
        String no= globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
        if( settingModel.getImport_way().equals("0"))
        importData.getCustomerLogReport(CustomerLogReport.this,salesNo,fromDate.getText().toString(),toDate.getText().toString());
        else   if( settingModel.getImport_way().equals("1"))
            importData.IIS_getCustomerLogReport(CustomerLogReport.this,no,fromDate.getText().toString(),toDate.getText().toString());
    }

    public void fillSalesManSpinner(){

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillCustomerLogReport(){
        customerLogReportAdapter=new CustomerLogReportAdapter(CustomerLogReport.this,customerLogReportList);
        listCustomerLogReport.setAdapter(customerLogReportAdapter);

    }

}
