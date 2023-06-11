package com.example.adminvansales.Report;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import androidx.appcompat.widget.AppCompatButton;

import com.example.adminvansales.Adapters.CustomersListAdapter;
import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.RequstNotifaction;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.PayMentReportModel;
import com.example.adminvansales.Adapters.PayMentReportAdapter;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.customername;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.paymentChequesList;

import com.example.adminvansales.model.LocaleAppUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentDetailsReport extends AppCompatActivity  {
    TextView fromDate,toDate;
    ImageButton excelConvert,pdfConvert,share, backBtn;
    GlobelFunction globelFunction;
    String toDay;
    PayMentReportAdapter payMentReportAdapter;
    ListView listPaymentReport;
    public static  List<PayMentReportModel> payMentReportList;
    public   List<PayMentReportModel> FilterdpayMentReportList=new ArrayList<>();
    ImportData importData;
    Button previewButton;
    Spinner payKindSpinner,salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    com.example.adminvansales.model.SettingModel SettingModel;
   DataBaseHandler databaseHandler;
    private BottomNavigationView bottom_navigation;
    TextView total;
    TextView Payments_count;
    TextView cust_select;
    String customerId = "";
    public static Dialog dialoglist,    dialog;
    public  static   TextView customerAccountNo,customerAccountname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(PaymentDetailsReport.this);
        setContentView(R.layout.payment_details_report);
        SettingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(PaymentDetailsReport.this);
        initial();
        findViewById(R.id.cust_selectclear)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cust_select.setText(getResources().getString(R.string.selectcust));
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
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
        Payments_count=findViewById(R.id.Payments_count);
        total=findViewById(R.id.total);
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
        cust_select=findViewById(R.id.cust_select);
        cust_select.setOnClickListener(onClick);

        payMentReportList=new ArrayList<>();
        importData=new ImportData(PaymentDetailsReport.this);
        if(salesManNameList.size()==0)
            globelFunction.  getSalesManInfo(PaymentDetailsReport.this,7);

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


        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_plan:

                                startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));
                                overridePendingTransition(0, 0);

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

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.previewButton :
                    customerId = getCusromerNUM(cust_select.getText().toString());
                    Log.e("customerId", customerId+"");


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
                case R.id.  cust_select:
                    opencust_selectDailog();
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
            Collections.sort(FilterdpayMentReportList,PayMentReportModel.comparedate);
            Collections.sort(payMentReportList,PayMentReportModel.comparedate);
            PdfConverter pdf = new PdfConverter(PaymentDetailsReport.this);
            if(FilterdpayMentReportList.size()!=0)   file = pdf.exportListToPdf(FilterdpayMentReportList, "Payment Report", toDay, 3);
        else     file = pdf.exportListToPdf(payMentReportList, "Payment Report", toDay, 3);
        }catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

       return file;
    }

    private void convertToExcel() {
        try {
            Collections.sort(FilterdpayMentReportList,PayMentReportModel.comparedate);
            Collections.sort(payMentReportList,PayMentReportModel.comparedate);

            ExportToExcel exportToExcel = new ExportToExcel();
            if(FilterdpayMentReportList.size()!=0)      exportToExcel.createExcelFile(PaymentDetailsReport.this, "PaymentReport.xls", 2, FilterdpayMentReportList);
else
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
        if(position==0){
            payKind="3";   //ALL
        }
       else if(position==1||position==-1){
            payKind="1";
        }else if(position==2){
            payKind="0";
        }
        else if(position==3){
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
            String no;
            if (positionSales != 0)
             no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
else  no = "9999999999";

            if (SettingModel.getImport_way().equals("0"))
                importData.getPaymentsReport(PaymentDetailsReport.this, "-1", fromDate.getText().toString(), toDate.getText().toString(), payKind);
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIS_getPaymentsReport(PaymentDetailsReport.this, no, fromDate.getText().toString(), toDate.getText().toString(), payKind);
        } else {

            Toast.makeText(PaymentDetailsReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

        }

    }

    public void fillSalesManSpinner(){

        List<String> salesManNameListWithAll=new ArrayList<>();

        salesManNameListWithAll.addAll(salesManNameList);
        salesManNameListWithAll.add(0,getResources().getString(R.string.ALL));


         salesNameSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, salesManNameListWithAll);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillPaymentAdapter(){
        if(FilterdpayMentReportList!=null)   FilterdpayMentReportList.clear();


        if(customerAccountNo!=null)
        {     Log.e("customerAccountNo==",customerAccountNo.getText().toString()+"case");
            if(!cust_select.getText().toString().equals(getResources().getString(R.string.selectcust))) {

            for (int i = 0; i < payMentReportList.size(); i++)
                if (payMentReportList.get(i).getCustomerNo().equals(customerAccountNo.getText().toString()))

                    FilterdpayMentReportList.add(payMentReportList.get(i));

Log.e("case1","case");
                Collections.sort(FilterdpayMentReportList,PayMentReportModel.comparedate);
            payMentReportAdapter = new PayMentReportAdapter(PaymentDetailsReport.this, FilterdpayMentReportList);
            listPaymentReport.setAdapter(payMentReportAdapter);

            total.setText(globelFunction.convertToEnglish(String.format("%.3f", (FilterdpayMentReportList.stream().map(PayMentReportModel::getAmount).mapToDouble(Double::parseDouble).sum()))));
            Payments_count.setText(globelFunction.convertToEnglish(FilterdpayMentReportList.size() + ""));
        }
        else {
                Collections.sort(payMentReportList,PayMentReportModel.comparedate);
            payMentReportAdapter = new PayMentReportAdapter(PaymentDetailsReport.this, payMentReportList);
            listPaymentReport.setAdapter(payMentReportAdapter);

            total.setText(globelFunction.convertToEnglish(String.format("%.3f", (payMentReportList.stream().map(PayMentReportModel::getAmount).mapToDouble(Double::parseDouble).sum()))));
            Payments_count.setText(globelFunction.convertToEnglish(payMentReportList.size() + ""));
        }  }else
        {
            Collections.sort(payMentReportList,PayMentReportModel.comparedate);
            payMentReportAdapter = new PayMentReportAdapter(PaymentDetailsReport.this, payMentReportList);
            listPaymentReport.setAdapter(payMentReportAdapter);

            total.setText(globelFunction.convertToEnglish(String.format("%.3f", (payMentReportList.stream().map(PayMentReportModel::getAmount).mapToDouble(Double::parseDouble).sum()))));
            Payments_count.setText(globelFunction.convertToEnglish(payMentReportList.size() + ""));
        }

    }
    private void  opencust_selectDailog(){
        dialog = new Dialog(PaymentDetailsReport.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cust_select_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        customerAccountNo = dialog.findViewById(R.id.customerAccountNo);
        customerAccountname = dialog.findViewById(R.id.customerAccountname);
        AppCompatButton ok= dialog.findViewById(R.id.okButton);
        AppCompatButton    cancel= dialog.findViewById(R.id.cancelButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!customerAccountname.getText().toString().equals(""))
                cust_select.setText(customerAccountname.getText());
                else
                {
                    cust_select.setText(getResources().getString(R.string.selectcust));
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cust_select.setText(getResources().getString(R.string.selectcust));
                dialog.dismiss();
            }
        });
        ImageView find_img_button=dialog.findViewById(R.id.find_img_button);
        find_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCustomer.size()!=0)
                {
                    dialoglist = new Dialog(PaymentDetailsReport.this);
                dialoglist.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialoglist.setCancelable(true);
                dialoglist.setContentView(R.layout.customerlist_dailog);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialoglist.getWindow().getAttributes());
                lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
                dialoglist.getWindow().setAttributes(lp);
                dialoglist.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText customerNameTextView =dialoglist.findViewById(R.id.customerNameTextView);
                ListView customersList =dialoglist.findViewById(R.id.customersList);
                customerNameTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!editable.toString().equals(""))
                        {
                            ArrayList<CustomerInfo> filterdlist = new ArrayList<CustomerInfo>();

                            for (int i=0;i<listCustomer.size();i++)
                            {
                                if(listCustomer.get(i).getCustomerName().contains(customerNameTextView.getText().toString())
                                        ||listCustomer.get(i).getCustomerNumber().contains(customerNameTextView.getText().toString()) )

                                    filterdlist.add(listCustomer.get(i));
                                customersList.setAdapter(new CustomersListAdapter(2,PaymentDetailsReport.this,filterdlist));
                            }

                        }else
                        {
                            customersList.setAdapter(new CustomersListAdapter(2,PaymentDetailsReport.this,listCustomer));
                        }
                    }
                });

                customersList.setAdapter(new CustomersListAdapter(2,PaymentDetailsReport.this,listCustomer));
                dialoglist.show();

                }else
                {
                    GlobelFunction.showSweetDialog(PaymentDetailsReport.this,3,"",getResources().getString(R.string.emptylist));
                }
            }
        });
        dialog.show();

    }
    private String getCusromerNUM(String name) {

        for (int i = 0; i < listCustomer.size(); i++){
            Log.e("here",listCustomer.get(i).getCustomerName());
            if (name.equals(listCustomer.get(i).getCustomerName())) {

                return listCustomer.get(i).getCustomerNumber();
            }

        }

        return "";
    }
//    private void fillCustomerSpenner() {
//        Collections.sort(customername, String.CASE_INSENSITIVE_ORDER);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, customername);
//
//        customers_list.setAdapter(adapter);
//    }
}
