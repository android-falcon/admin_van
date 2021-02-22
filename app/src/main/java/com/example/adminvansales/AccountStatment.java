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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Model.Account__Statment_Model;
import com.example.adminvansales.Model.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.customername;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.listCustomerInfo;
import static com.example.adminvansales.ImportData.listSalesMan;

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
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statment);

//        importData.getCustomerInfo();
        initialView();
        Log.e("customername",""+customername.size());

        preview_button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!customerId.equals(""))
                {
                    ImportData importData= new ImportData(AccountStatment.this);
                    importData.getCustomerAccountStatment(customerId);
                }
//
            }
        });
        if(customername.size()!=0)
        {
            Log.e("customername",""+customername.size());
            fillCustomerSpenner();
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
                    if(customername.size()!=0)
                    {

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
            }
        });

        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                customerId= listCustomer.get(position).getCustomerNumber();
                Log.e("onItemSelected",""+customerId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("WrongConstant")
    private void initialView() {
        recyclerView_report=findViewById(R.id.recyclerView_report);
        getAccountList_text=findViewById(R.id.getAccountList_text);
        preview_button_account=findViewById(R.id.preview_button_account);
        customerSpinner = (Spinner) findViewById(R.id.cat);
        listAccountBalance=new ArrayList<>();
        layoutManager = new LinearLayoutManager(AccountStatment.this);
        layoutManager.setOrientation(VERTICAL);
        recyclerView_report.setLayoutManager(layoutManager);
        total_qty_text=findViewById(R.id.total_qty_text);
    }

    private void fillCustomerSpenner() {
        final ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customername);
        customerSpinner.setAdapter(ad);
    }

    private void fillAdapter() {


//        Account__Statment_Model accBalance=new Account__Statment_Model();
//        accBalance.setVoucherNo("1002");
//        accBalance.setTranseNmae("sale");
//        accBalance.setDate_voucher("17/12");
//        accBalance.setDebit(50);
//        accBalance.setCredit(0);
//        accBalance.setBalance(0);
//        listAccountBalance.add(accBalance);
//
//        accBalance=new Account__Statment_Model();
//        accBalance.setVoucherNo("1003");
//        accBalance.setTranseNmae("sale");
//        accBalance.setDate_voucher("17/12");
//        accBalance.setDebit(100);
//        accBalance.setCredit(0);
//        accBalance.setBalance(0);
//        listAccountBalance.add(accBalance);
//
//        accBalance=new Account__Statment_Model();
//        accBalance.setVoucherNo("1003");
//        accBalance.setTranseNmae("payment");
//        accBalance.setDate_voucher("17/12");
//        accBalance.setDebit(0);
//        accBalance.setCredit(100);
//        accBalance.setBalance(0);
//        listAccountBalance.add(accBalance);
//        accBalance=new Account__Statment_Model();
//        accBalance.setVoucherNo("1003");
//        accBalance.setTranseNmae("payment");
//        accBalance.setDate_voucher("17/12");
//        accBalance.setDebit(0);
//        accBalance.setCredit(50);
//        accBalance.setBalance(0);
//        listAccountBalance.add(accBalance);
        AccountStatmentAdapter adapter = new AccountStatmentAdapter(listCustomerInfo, AccountStatment.this);
        recyclerView_report.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i=new Intent(AccountStatment.this,HomeActivity.class);
        startActivity(i);
    }
}
