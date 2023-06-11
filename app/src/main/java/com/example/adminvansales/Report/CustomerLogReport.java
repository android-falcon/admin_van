package com.example.adminvansales.Report;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.adminvansales.AccountStatment;
import com.example.adminvansales.Adapters.CustomerLogReportAdapter;
import com.example.adminvansales.Adapters.CustomersListAdapter;
import com.example.adminvansales.AddedCustomersNotifaction;
import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.HomeActivity;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.ItemReport;
import com.example.adminvansales.LocationService;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.RequstNotifaction;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.CustomerLogReportModel;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.example.adminvansales.model.ItemReportModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.listCustomerInfo;

import com.example.adminvansales.model.LocaleAppUtils;
public class CustomerLogReport extends AppCompatActivity {

    TextView fromDate, toDate;
    ImageButton excelConvert, pdfConvert, share, backBtn;
    GlobelFunction globelFunction;
    String toDay;
    CustomerLogReportAdapter customerLogReportAdapter;
    ListView listCustomerLogReport;
    public static List<CustomerLogReportModel> customerLogReportList;
    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    com.example.adminvansales.model.SettingModel settingModel;
    DataBaseHandler databaseHandler;
    private BottomNavigationView bottom_navigation;
    TextView cust_select;
    public  static   TextView customerAccountNo,customerAccountname;
    String customerId = "";
    public static Dialog dialoglist,    dialog;
    Comparator<CustomerLogReportModel> compareBy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(CustomerLogReport.this);
        setContentView(R.layout.customer_log_report);
        settingModel = new com.example.adminvansales.model.SettingModel();
        databaseHandler = new DataBaseHandler(CustomerLogReport.this);

        initial();

        compareBy
                = Comparator.comparing(CustomerLogReportModel::getCHECK_IN_DATE)
                .thenComparing(CustomerLogReportModel::getSalesmanname)
                .thenComparing(CustomerLogReportModel::getCUS_NAME);

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
        cust_select = findViewById(R.id.cat);
        cust_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencust_selectDailog();
            }
        });

        findViewById(R.id.cust_selectclear)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cust_select.setText(getResources().getString(R.string.selectcust2));
            }
        });
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        listCustomerLogReport = findViewById(R.id.listCustomerLogReport);
        previewButton = findViewById(R.id.previewButton);
        salesNameSpinner = findViewById(R.id.salesNameSpinner);
        excelConvert = findViewById(R.id.excelConvert);
        pdfConvert = findViewById(R.id.pdfConvert);
        share = findViewById(R.id.share);
        backBtn = findViewById(R.id.backBtn);
        globelFunction = new GlobelFunction(CustomerLogReport.this);
        toDay = globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        customerLogReportList = new ArrayList<>();
        if(salesManNameList.size()==0)  globelFunction.getSalesManInfo(CustomerLogReport.this,5);
        fillSalesManSpinner();
        importData = new ImportData(CustomerLogReport.this);
        try {
            String no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());

            settingModel = databaseHandler.getAllSetting();
            if (settingModel.getImport_way().equals("0"))
                importData.getCustomerLogReport(CustomerLogReport.this, "-1", toDay, toDay);
            else if (settingModel.getImport_way().equals("1"))
                importData.IIS_getCustomerLogReport(CustomerLogReport.this, no, toDay, toDay);

        } catch (Exception e) {
        }

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);
        backBtn.setOnClickListener(v -> onBackPressed());

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

                            case R.id.action_notifications2:

                                startActivity(new Intent(getApplicationContext(), AddedCustomersNotifaction.class));
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

    public void shareWhatsApp() {
        try {

            globelFunction.shareWhatsAppA(convertToPdf(), 1);
        } catch (Exception e) {
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }
    private void  opencust_selectDailog(){
        dialog = new Dialog(CustomerLogReport.this);
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
                    cust_select.setText(getResources().getString(R.string.selectcust2));
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cust_select.setText(getResources().getString(R.string.selectcust2));
                dialog.dismiss();
            }
        });
        ImageView find_img_button=dialog.findViewById(R.id.find_img_button);
        find_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCustomer.size()!=0)
                {
                    dialoglist = new Dialog(CustomerLogReport.this);
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
                                    customersList.setAdapter(new CustomersListAdapter(4,CustomerLogReport.this,filterdlist));
                                }

                            }else
                            {
                                customersList.setAdapter(new CustomersListAdapter(4,CustomerLogReport.this,listCustomer));
                            }
                        }
                    });

                    customersList.setAdapter(new CustomersListAdapter(4,CustomerLogReport.this,listCustomer));
                    dialoglist.show();

                }else
                {
                    GlobelFunction.showSweetDialog(CustomerLogReport.this,3,"",getResources().getString(R.string.emptylist));
                }
            }
        });
        dialog.show();

    }
    private File convertToPdf() {
        File file = null;
        try {
            customerLogReportList.sort(compareBy);
            PdfConverter pdf = new PdfConverter(CustomerLogReport.this);
            file = pdf.exportListToPdf(customerLogReportList, "Customer Log Report", toDay, 1);
        } catch (Exception e) {
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
        }
        return file;
    }

    private void convertToExcel() {
        try {
            customerLogReportList.sort(compareBy);
            ExportToExcel exportToExcel = new ExportToExcel();
            exportToExcel.createExcelFile(CustomerLogReport.this, "CustomerLogReport.xls", 1, customerLogReportList);
        } catch (Exception e) {
            Log.e(" convertToExcel", e.getMessage());
            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();

        }

    }


    public void previewFunction() {

        String payKind = "-1";
        String salesNo = "-1";


        int positionSales = salesNameSpinner.getSelectedItemPosition();

        if (positionSales == 0 || positionSales == -1) {
            salesNo = "-1";
//            Log.e("salesNo-1", "" + salesNo + "  ");

        } else {
            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
//            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
        }
        String no="";
        if (positionSales != -1) {
            if (positionSales ==0)no ="9999999999";
             else no = globelFunction.getsalesmanNum(salesNameSpinner.getSelectedItem().toString());
            if (settingModel.getImport_way().equals("0"))
                importData.getCustomerLogReport(CustomerLogReport.this, salesNo, fromDate.getText().toString(), toDate.getText().toString());
            else if (settingModel.getImport_way().equals("1"))
                importData.IIS_getCustomerLogReport(CustomerLogReport.this, no, fromDate.getText().toString(), toDate.getText().toString());
        } else {

            Toast.makeText(CustomerLogReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

        }
    }
//
    public void fillSalesManSpinner() {
        List<String> salesManNameListWithAll=new ArrayList<>();

        salesManNameListWithAll.addAll(salesManNameList);
        salesManNameListWithAll.add(0,getResources().getString(R.string.ALL));



        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, salesManNameListWithAll);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);
        salesNameSpinner.setSelection(0);

    }

    public void fillCustomerLogReport() {

        if(!cust_select.getText().toString().equals(getResources().getString(R.string.selectcust2)))
        {for (int i=0;i<customerLogReportList.size();i++)
        {

            try {


                if (!customerLogReportList.get(i).getCUS_NAME().equals(cust_select.getText().toString())) {
                    customerLogReportList.remove(i);
                    i--;

                }
            }catch (Exception e){
                Log.e("Exception=",e.getMessage());
            }
        }}

        customerLogReportList.sort(compareBy);
        customerLogReportAdapter = new CustomerLogReportAdapter(CustomerLogReport.this, customerLogReportList);
        listCustomerLogReport.setAdapter(customerLogReportAdapter);



    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
    }

}
