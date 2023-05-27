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

import com.example.adminvansales.Adapters.CashReportAdapter;
import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.ItemReport;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.RequstNotifaction;
import com.example.adminvansales.model.CashReportModel;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.example.adminvansales.model.Payment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.cashReportList;

import com.example.adminvansales.model.LocaleAppUtils;
public class CashReport extends AppCompatActivity {

    TextView fromDate, toDate;
    ImageButton excelConvert, pdfConvert, share, backBtn;
    GlobelFunction globelFunction;
    String toDay;
    CashReportAdapter payMentReportAdapter;
    ListView listCashReport;

    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    List<CashReportModel> TempReports;
    com.example.adminvansales.model.SettingModel SettingModel;
    DataBaseHandler databaseHandler;
    TextView  total,cashtotal;
    TextView  TOTAL_cash_sale,TOTAL_credit_sales,TOTAL_net_sales,TOTAL_paymentCash,TOTAL_paymentCheque,TOTAL_netpayment,
            TOTAL_credit_value,TOTAL_total_cash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(CashReport.this);
        setContentView(R.layout.cash_report);

        initial();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initial() {
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
        total=findViewById(R.id.total);
        cashtotal=findViewById(R.id.cashtotal);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        listCashReport = findViewById(R.id.listCashReport);
        previewButton = findViewById(R.id.previewButton);
        salesNameSpinner = findViewById(R.id.salesNameSpinner);
        excelConvert = findViewById(R.id.excelConvert);
        pdfConvert = findViewById(R.id.pdfConvert);
        share = findViewById(R.id.share);
        globelFunction = new GlobelFunction(CashReport.this);
        toDay = globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);

        TempReports = new ArrayList<>();
        importData = new ImportData(CashReport.this);
        databaseHandler = new DataBaseHandler(CashReport.this);
        SettingModel = databaseHandler.getAllSetting();
        if(salesManNameList.size()==0)  globelFunction.getSalesManInfo(CashReport.this,6);

        fillSalesManSpinner();
        try {
            int positionSales = salesNameSpinner.getSelectedItemPosition();
            String no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
            if (SettingModel.getImport_way().equals("0"))
                importData.getCashReport(CashReport.this, toDay, toDay);
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIS_getCashReport(CashReport.this, toDay, toDay, no,1);
        } catch (Exception e) {

        }
        //
        TOTAL_cash_sale=findViewById(R.id.TOTAL_cash_sale);
        TOTAL_credit_sales=findViewById(R.id.TOTAL_credit_sales);
        TOTAL_net_sales=findViewById(R.id.TOTAL_net_sales);
        TOTAL_paymentCash=findViewById(R.id.TOTAL_paymentCash);
        TOTAL_paymentCheque=findViewById(R.id.TOTAL_paymentCheque);
        TOTAL_netpayment =findViewById(R.id.TOTAL_netpayment);
         TOTAL_credit_value=findViewById(R.id.TOTAL_credit_value);
        TOTAL_total_cash=findViewById(R.id.TOTAL_total_cash);
        //


        //
        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);

        backBtn = findViewById(R.id.backBtn);
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
                                popUpClass.showPopupWindow(item.getActionView(), CashReport.this);

                                return true;

                            case R.id.action_location:

                                return true;

                            case R.id.action_notifications:

                                startActivity(new Intent(getApplicationContext(), RequstNotifaction.class));
                                overridePendingTransition(0, 0);

                                return true;
                        }
                        return false;
                    }
                });


    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {

                case R.id.previewButton:
                    previewFunction();
                    break;

                case R.id.from_date_r:
                    globelFunction.DateClick(fromDate);
                    break;

                case R.id.to_date_r:
                    globelFunction.DateClick(toDate);
                    break;
                case R.id.excelConvert:
                    convertToExcel();
                    break;
                case R.id.pdfConvert:
                    convertToPdf();
                    break;
                case R.id.share:
                    shareWhatsApp();
                    break;

            }

        }
    };

    private File convertToPdf() {
        PdfConverter pdf = new PdfConverter(CashReport.this);
        File file = pdf.exportListToPdf(cashReportList, "Cash Report", toDay, 2);
        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel = new ExportToExcel();
        exportToExcel.createExcelFile(CashReport.this, "CashReport.xls", 3, cashReportList);
        // return file;
    }

    public void shareWhatsApp() {
        globelFunction.shareWhatsAppA(convertToPdf(), 1);
    }


    public void previewFunction() {
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
        int positionSales = salesNameSpinner.getSelectedItemPosition();
        String no = "";
        if (salesNameSpinner.getSelectedItem() != null) {
            if (salesNameSpinner.getSelectedItem().equals(getResources().getString(R.string.ALL)))
                no ="9999999999";
           else no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
            if (SettingModel.getImport_way().equals("0"))
                importData.getCashReport(CashReport.this, fromDate.getText().toString(), toDate.getText().toString());
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIS_getCashReport(CashReport.this, fromDate.getText().toString(), toDate.getText().toString(), no,1);
        } else {

            Toast.makeText(CashReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

        }
    }

    public void fillSalesManSpinner() {

          List<String> salesManNameListWithAll=new ArrayList<>();

        salesManNameListWithAll.addAll(salesManNameList);
        salesManNameListWithAll.add(0,getResources().getString(R.string.ALL));


        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameListWithAll);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }

    public void fillCashAdapter() {
//        try {


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
//              if(positionSales!=0)
//                {   salesNo = salesManInfosList.get(positionSales).getSalesManNo();
//                    Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales).getSalesName() + "    " + positionSales);
//                    for (int i = 0; i < cashReportList.size(); i++) {
//                        if (cashReportList.get(i).getSalesManNo().equals(salesNo)) {
//                            TempReports.add(cashReportList.get(i));
//                            break;
//                        }
//                    }
//                }else
//              {
//                  TempReports.addAll(cashReportList);
//              }

            payMentReportAdapter = new CashReportAdapter(CashReport.this, cashReportList);
            listCashReport.setAdapter(payMentReportAdapter);
            try {


                double sum = 0, nettotal = 0, TOTAL_net_salesVal = 0, TOTAL_netpaymentVal = 0, TOTAL_total_cashVal = 0;
                for (int i = 0; i < cashReportList.size(); i++) {
                    sum += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());

                    nettotal += Double.parseDouble(cashReportList.get(i).getPtotalCredite()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());
                    TOTAL_net_salesVal += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getTotalCredite());
                    TOTAL_netpaymentVal += Double.parseDouble(cashReportList.get(i).getPtotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCredite());
                    TOTAL_total_cashVal += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());
                }

                cashtotal.setText(globelFunction.convertToEnglish(String.format("%.3f", sum)));
                total.setText(globelFunction.convertToEnglish(String.format("%.3f", (nettotal))));

                //
                TOTAL_cash_sale.setText(globelFunction.convertToEnglish(String.format("%.3f", (cashReportList.stream().map(CashReportModel::getTotalCash).mapToDouble(Double::parseDouble).sum()))));
                TOTAL_credit_sales.setText(globelFunction.convertToEnglish(String.format("%.3f", (cashReportList.stream().map(CashReportModel::getTotalCredite).mapToDouble(Double::parseDouble).sum()))));
                TOTAL_net_sales.setText(globelFunction.convertToEnglish(String.format("%.3f", (TOTAL_net_salesVal))));
                TOTAL_paymentCash.setText(globelFunction.convertToEnglish(String.format("%.3f", (cashReportList.stream().map(CashReportModel::getPtotalCash).mapToDouble(Double::parseDouble).sum()))));
                TOTAL_paymentCheque.setText(globelFunction.convertToEnglish(String.format("%.3f", (cashReportList.stream().map(CashReportModel::getPtotalCredite).mapToDouble(Double::parseDouble).sum()))));
                TOTAL_netpayment.setText(globelFunction.convertToEnglish(String.format("%.3f", (TOTAL_netpaymentVal))));
                TOTAL_credit_value.setText(globelFunction.convertToEnglish(String.format("%.3f", (cashReportList.stream().map(CashReportModel::getPtotalCrediteCard).mapToDouble(Double::parseDouble).sum()))));
                TOTAL_total_cash.setText(globelFunction.convertToEnglish(String.format("%.3f", TOTAL_total_cashVal)));

            }catch (Exception exception){

            }
        }


//        } catch (Exception exception) {
//            Log.e("exception==","pr,,"+exception.getMessage());
//        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
    }

}
