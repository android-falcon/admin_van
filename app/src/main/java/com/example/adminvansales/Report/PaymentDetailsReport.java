package com.example.adminvansales.Report;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.model.PayMentReportModel;
import com.example.adminvansales.Adapters.PayMentReportAdapter;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import com.example.adminvansales.model.LocaleAppUtils;
public class PaymentDetailsReport extends AppCompatActivity {
    TextView fromDate,toDate;
    ImageButton excelConvert,pdfConvert,share, backBtn;
    GlobelFunction globelFunction;
    String toDay;
    PayMentReportAdapter payMentReportAdapter;
    ListView listPaymentReport;
    public static  List<PayMentReportModel> payMentReportList;
    ImportData importData;
    Button previewButton;
    Spinner payKindSpinner,salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    com.example.adminvansales.model.SettingModel SettingModel;
   DataBaseHandler databaseHandler;
    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(PaymentDetailsReport.this);
        setContentView(R.layout.payment_details_report);
        SettingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(PaymentDetailsReport.this);
        initial();


    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initial() {
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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        fromDate=findViewById(R.id.from_date_r);
        toDate=findViewById(R.id.to_date_r);
        listPaymentReport=findViewById(R.id.listPaymentReport);
        previewButton=findViewById(R.id.previewButton);
        payKindSpinner=findViewById(R.id.payKindSpinner);
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());
        globelFunction=new GlobelFunction(PaymentDetailsReport.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        payMentReportList=new ArrayList<>();
        importData=new ImportData(PaymentDetailsReport.this);
        fillSalesManSpinner();
        SettingModel=databaseHandler.getAllSetting();
        try {
            String no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
            if (SettingModel.getImport_way().equals("0"))
                importData.getPaymentsReport(PaymentDetailsReport.this, "-1", toDay, toDay, "-1");
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIS_getPaymentsReport(PaymentDetailsReport.this, no, toDay, toDay, "1");
        }catch (Exception e){}

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);

        bottom_navigation = findViewById(R.id.bottom_navigation);

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
                                popUpClass.showPopupWindow(item.getActionView(), PaymentDetailsReport.this);

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
            globelFunction.shareWhatsAppA(convertToPdf(), 1);
        }catch (Exception e){
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }
    private File convertToPdf() {
        File file=null;
        try {
            PdfConverter pdf = new PdfConverter(PaymentDetailsReport.this);
             file = pdf.exportListToPdf(payMentReportList, "Payment Report", toDay, 3);
        }catch (Exception e){
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();

        }

       return file;
    }

    private void convertToExcel() {
        try {
            ExportToExcel exportToExcel = new ExportToExcel();
            exportToExcel.createExcelFile(PaymentDetailsReport.this, "PaymentReport.xls", 2, payMentReportList);

        }catch (Exception e){
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }
    public void previewFunction(){

        String payKind="-1";
        String salesNo="-1";

       int position=payKindSpinner.getSelectedItemPosition();
      /*  if(position==0||position==-1){
            payKind="-1";
        }else if(position==1){
            payKind="1";
        }else if(position==2){
            payKind="0";
        }*/
        if(position==0||position==-1){
            payKind="1";
        }else if(position==1){
            payKind="0";
        }
        else if(position==2){
            payKind="2";
        }

        int positionSales=salesNameSpinner.getSelectedItemPosition();

            if (positionSales == 0 || positionSales==-1) {
                salesNo = "-1";
                Log.e("salesNo-1", "" + salesNo +"  "+position);

            } else {
                salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
                Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
            }

        if (positionSales != -1) {
            String no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());

            if (SettingModel.getImport_way().equals("0"))
                importData.getPaymentsReport(PaymentDetailsReport.this, "-1", fromDate.getText().toString(), toDate.getText().toString(), payKind);
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIS_getPaymentsReport(PaymentDetailsReport.this, no, fromDate.getText().toString(), toDate.getText().toString(), payKind);
        } else {

            Toast.makeText(PaymentDetailsReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

        }

    }

    public void fillSalesManSpinner(){

        salesNameSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, salesManNameList);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillPaymentAdapter(){
        payMentReportAdapter=new PayMentReportAdapter(PaymentDetailsReport.this,payMentReportList);
        listPaymentReport.setAdapter(payMentReportAdapter);

    }

}
