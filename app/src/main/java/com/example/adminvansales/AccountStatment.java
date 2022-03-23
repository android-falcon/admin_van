package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.AccountStatmentAdapter;
import com.example.adminvansales.model.Account__Statment_Model;
import com.example.adminvansales.model.CustomerInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.customername;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.listCustomerInfo;

public class AccountStatment extends AppCompatActivity {

    List<CustomerInfo> customerInfoList=new ArrayList<>();
    ArrayList<Account__Statment_Model> listAccountBalance;
    RecyclerView recyclerView_report;
    LinearLayoutManager layoutManager;
    public  static TextView getAccountList_text;
    Button preview_button_account;
     Spinner customerSpinner;
     String customerId="";
     public  static  TextView total_qty_text;
    public   EditText listSearch;
    ImportData importData;
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel SettingModel;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statment);
        databaseHandler=new DataBaseHandler(AccountStatment.this);
//        importData.getCustomerInfo();
        initialView();
        Log.e("customername",""+customername.size());

        preview_button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!customerId.equals(""))
                {
                    importData= new ImportData(AccountStatment.this);

                    customerId=     getCusromerNUM(customerSpinner.getSelectedItem().toString());
                        importData.getCustomerAccountStatment(customerId);



                }
//
            }
        });

        if(customername.size()!=0)
        {
            Log.e("customername",""+customername.size());
            fillCustomerSpenner();
        }else {
            SettingModel=databaseHandler.getAllSetting();
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

                if(s.toString().equals("1"))
                {
                    Log.e("customername====", customername.size()+"" );
                   // if(customername.size()!=0)
                    {
                        Log.e("getAccountList_text====", "getAccountList_text" );
                        fillCustomerSpenner();
                    }
                }

                if(s.toString().equals("2"))
                {
                    if(listCustomerInfo.size()!=0)
                {
                    fillAdapter();
                }
                }
                if(s.toString().equals("Nodata"))
                {
                    Log.e("listCustomerInfo",""+listCustomerInfo.size());
                    fillAdapter();
                    showDialogNoData();


                }
            }
        });

        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerId= listCustomer.get(position).getCustomerNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        recyclerView_report=findViewById(R.id.recyclerView_report);
        getAccountList_text=findViewById(R.id.getAccountList_text);
        preview_button_account=findViewById(R.id.preview_button_account);
        customerSpinner = (Spinner) findViewById(R.id.cat);
        importData= new ImportData(AccountStatment.this);
        listAccountBalance=new ArrayList<>();

        layoutManager = new LinearLayoutManager(AccountStatment.this);
        layoutManager.setOrientation(VERTICAL);
        recyclerView_report.setLayoutManager(layoutManager);
        total_qty_text=findViewById(R.id.total_qty_text);
        listSearch=findViewById(R.id.listSearch);

        listSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals(""))
                {
                    searchInSpinerCustomer();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fillCustomerSpenner() {
//        customername.sort(String::compareToIgnoreCase);
        Log.e("fillCustomerSpenner","fillCustomerSpenner");
        Collections.sort(customername, String.CASE_INSENSITIVE_ORDER);
        final ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customername);
        customerSpinner.setAdapter(ad);
    }

    private void fillAdapter() {
        AccountStatmentAdapter adapter = new AccountStatmentAdapter(listCustomerInfo, AccountStatment.this);
        recyclerView_report.setAdapter(adapter);
    }
    private void searchInSpinerCustomer() {
        String name="";
        if (!listSearch.getText().toString().equals("")) {
            name=listSearch.getText().toString();
            for (int i=0;i<customername.size();i++)
            {
                if(customername.get(i).toUpperCase().contains(name)||customername.get(i).toLowerCase().contains(name))
                {
                    customerId= listCustomer.get(i).getCustomerNumber();
                    customerSpinner.setSelection(i);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i=new Intent(AccountStatment.this,HomeActivity.class);
        startActivity(i);
    }
    private String getCusromerNUM(String name) {
        for(int i=0;i<listCustomer.size();i++)
            if( name.trim().equals(listCustomer.get(i).getCustomerName()))
            {
                return listCustomer.get(i).getCustomerNumber();}

        return "";
    }
}
