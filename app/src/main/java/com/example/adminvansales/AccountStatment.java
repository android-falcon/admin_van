package com.example.adminvansales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.AccountStatmentAdapter;
import com.example.adminvansales.Adapters.CustomersListAdapter;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.ReportsPopUpClass;
import com.example.adminvansales.model.Account__Statment_Model;
import com.example.adminvansales.model.CustomerInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.customername;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.listCustomerInfo;
import com.example.adminvansales.model.LocaleAppUtils;
public class AccountStatment extends AppCompatActivity {

    List<CustomerInfo> customerInfoList = new ArrayList<>();
    ArrayList<Account__Statment_Model> listAccountBalance;
    RecyclerView recyclerView_report;
    LinearLayoutManager layoutManager;
    public static TextView getAccountList_text;
    Button preview_button_account;
    TextView cust_select;

    String customerId = "";
    public static TextView total_qty_text;
    public EditText listSearch;
    ImportData importData;
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel SettingModel;

    BottomNavigationView bottom_navigation;
    public static Dialog dialoglist,    dialog;
    TextView fromDate, toDate;
    String toDay;
    GlobelFunction globelFunction;
   Spinner itemKindspinner;
    public  static   TextView customerAccountNo,customerAccountname;
    List<Account__Statment_Model> matchingObjects=new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(AccountStatment.this);
        setContentView(R.layout.activity_account_statment);
        databaseHandler = new DataBaseHandler(AccountStatment.this);
//        importData.getCustomerInfo();
        initialView();

        findViewById(R.id.excelConvert)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToExcel();
            }
        });

        findViewById(R.id.pdfConvert)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToPdf();
            }
        });
        findViewById(R.id.cust_selectclear)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cust_select.setText(getResources().getString(R.string.selectcust));
            }
        });
        importData.IIs_getCustomerInfo(1);
        preview_button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchingObjects.clear();
                    importData = new ImportData(AccountStatment.this);


                        customerId = getCusromerNUM(cust_select.getText().toString());
                if (!customerId.equals(""))    importData.getCustomerAccountStatment(customerId);
                    else

                    new SweetAlertDialog(AccountStatment.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(getString(R.string.noCustomerSelected))
                            .show();

//
            }
        });

        if (customername.size() != 0) {
            Log.e("customername", "" + customername.size());
            fillCustomerSpenner();
        } else {
            SettingModel = databaseHandler.getAllSetting();
            if (SettingModel.getImport_way().equals("0"))
                importData.getCustomerInfo(1);
            else if (SettingModel.getImport_way().equals("1"))
                importData.IIs_getCustomerInfo(1);


        }


        getAccountList_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().equals("1")) {
                    Log.e("customername====", customername.size() + "");
                    // if(customername.size()!=0)
                    {
                        Log.e("getAccountList_text====", "getAccountList_text");
                        fillCustomerSpenner();
                    }
                }

                if (s.toString().equals("2")) {
                    if (listCustomerInfo.size() != 0) {
                        fillAdapter();
                    }
                }
                if (s.toString().equals("Nodata")) {
                    Log.e("listCustomerInfo", "" + listCustomerInfo.size());
                    fillAdapter();
                    showDialogNoData();


                }
            }
        });

//        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                customerId = listCustomer.get(position).getCustomerNumber();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_plan:
                                finish();
                                startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));
                                overridePendingTransition(0, 0);

                                return true;

                            case R.id.action_reports:

                                ReportsPopUpClass popUpClass = new ReportsPopUpClass();
                                popUpClass.showPopupWindow(item.getActionView(), AccountStatment.this);

                                return true;

                            case R.id.action_location:

                                {      finish();
                                    startActivity(new Intent(getApplicationContext(), SalesmanMaps_FirebaseActivity.class));
                                    overridePendingTransition(0, 0);
                                }
                                    return true;

                            case R.id.action_notifications:

                                finish();
                                startActivity(new Intent(getApplicationContext(), RequstNotifaction.class));
                                overridePendingTransition(0, 0);

                                return true;
                        }
                        return false;
                    }
                });




    }

    private void showDialogNoData() {
        SweetAlertDialog sweet = new SweetAlertDialog(AccountStatment.this, SweetAlertDialog.NORMAL_TYPE);
        sweet.setTitleText("No Data For This Customer");

        sweet.show();
    }

    @SuppressLint("WrongConstant")
    private void initialView() {

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
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        globelFunction = new GlobelFunction(AccountStatment.this);
        itemKindspinner= findViewById(R.id.itemKindspinner);


        itemKindspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              if(i!=0)
              {     matchingObjects = ImportData.listCustomerInfo.stream().
                        filter(Account__Statment_Model -> Account__Statment_Model.getTranseNmae().equals(itemKindspinner.getSelectedItem().toString())).
                        collect(Collectors.toList());
                AccountStatmentAdapter adapter = new AccountStatmentAdapter(matchingObjects, AccountStatment.this);
                recyclerView_report.setAdapter(adapter);}
              else
              {
                  AccountStatmentAdapter adapter = new AccountStatmentAdapter(ImportData.listCustomerInfo, AccountStatment.this);
                  recyclerView_report.setAdapter(adapter);
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        toDay = globelFunction.DateInToday();

        fromDate.setText(toDay);
        toDate.setText(toDay);

        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
  //  cust_select=findViewById(R.id.cust_select);
        recyclerView_report = findViewById(R.id.recyclerView_report);
        getAccountList_text = findViewById(R.id.getAccountList_text);
        preview_button_account = findViewById(R.id.preview_button_account);
        cust_select = findViewById(R.id.cat);
        importData = new ImportData(AccountStatment.this);
        listAccountBalance = new ArrayList<>();

        layoutManager = new LinearLayoutManager(AccountStatment.this);
        layoutManager.setOrientation(VERTICAL);
        recyclerView_report.setLayoutManager(layoutManager);
        total_qty_text = findViewById(R.id.total_qty_text);
        listSearch = findViewById(R.id.listSearch);
        cust_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencust_selectDailog();
            }
        });
//        listSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (!s.toString().equals("")) {
//                    searchInSpinerCustomer();
//                }
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private void fillCustomerSpenner() {
//        customername.sort(String::compareToIgnoreCase);
        Log.e("fillCustomerSpenner", "fillCustomerSpenner");
        Collections.sort(customername, String.CASE_INSENSITIVE_ORDER);
        final ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customername);
//        customerSpinner.setAdapter(ad);
    }

    private void fillAdapter() {
        String fromDates=  fromDate.getText().toString();
        String  toDates=toDate.getText().toString();
        String fdate="";
        AccountStatmentAdapter adapter = new AccountStatmentAdapter(listCustomerInfo, AccountStatment.this);
        recyclerView_report.setAdapter(adapter);
for (int i=0;i<listCustomerInfo.size();i++)
{
     fdate=listCustomerInfo.get(i).getDate_voucher();
     try {


         if ((formatDate(fdate).after(formatDate(fromDates)) || formatDate(fdate).equals(formatDate(fromDates))) &&
                 (formatDate(fdate).before(formatDate(toDates)) || formatDate(fdate).equals(formatDate(toDates)))) {


         } else {
             listCustomerInfo.remove(i);
             i--;
         }
     }catch (Exception e){
         Log.e("Exception=",e.getMessage());
     }
    }




        ArrayList <String>list=new ArrayList<>();
        list.add(getString(R.string.ALL));
        for(int i=0;i<ImportData.listCustomerInfo.size();i++)
            if(!list.contains(ImportData.listCustomerInfo.get(i).getTranseNmae()))
            list.add(ImportData.listCustomerInfo.get(i).getTranseNmae());

        itemKindspinner.setAdapter(new ArrayAdapter<String>(
                AccountStatment. this, android.R.layout.simple_spinner_item, list));

    }
    public Date formatDate(String date) throws ParseException {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d = sdf.parse(date);
        return d;
    }
    private void searchInSpinerCustomer() {
        String name = "";
        if (!listSearch.getText().toString().equals("")) {
            name = listSearch.getText().toString();
            for (int i = 0; i < customername.size(); i++) {
                if (customername.get(i).toUpperCase().contains(name) || customername.get(i).toLowerCase().contains(name)) {
                    customerId = listCustomer.get(i).getCustomerNumber();
//                    customerSpinner.setSelection(i);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(AccountStatment.this, HomeActivity.class);
        startActivity(i);
    }

    private String getCusromerNUM(String name) {
        for (int i = 0; i < listCustomer.size(); i++)
            if (name.trim().equals(listCustomer.get(i).getCustomerName())) {
                return listCustomer.get(i).getCustomerNumber();
            }

        return "";
    }
    private void  opencust_selectDailog(){
        dialog = new Dialog(AccountStatment.this);
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
                    dialoglist = new Dialog(AccountStatment.this);
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
                                    customersList.setAdapter(new CustomersListAdapter(3,AccountStatment.this,filterdlist));
                                }

                            }else
                            {
                                customersList.setAdapter(new CustomersListAdapter(3,AccountStatment.this,listCustomer));
                            }
                        }
                    });

                    customersList.setAdapter(new CustomersListAdapter(3,AccountStatment.this,listCustomer));
                    dialoglist.show();

                }else
                {
                    GlobelFunction.showSweetDialog(AccountStatment.this,3,"",getResources().getString(R.string.emptylist));
                }
            }
        });
        dialog.show();

    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {


                case R.id.from_date_r:
                    globelFunction.DateClick(fromDate);
                    break;

                case R.id.to_date_r:
                    globelFunction.DateClick(toDate);
                    break;


            }

        }
    };

    private File convertToPdf() {
        PdfConverter pdf = new PdfConverter(AccountStatment.this);
        File file;
        if(matchingObjects.size()!=0)
         file = pdf.exportListToPdf(matchingObjects, "AccountStatmentReport", toDay, 7);
        else
            file = pdf.exportListToPdf(listCustomerInfo, "AccountStatmentReport", toDay, 7);

        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel = new ExportToExcel();
        if(matchingObjects.size()!=0)  exportToExcel.createExcelFile(AccountStatment.this, "AccountStatment.xls", 7, matchingObjects);
             else
            exportToExcel.createExcelFile(AccountStatment.this, "AccountStatment.xls", 7, listCustomerInfo);
        // return file;
    }
}
