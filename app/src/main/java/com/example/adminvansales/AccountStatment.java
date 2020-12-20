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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Model.Account__Statment_Model;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.listCustomerInfo;
import static com.example.adminvansales.ImportData.listSalesMan;

public class AccountStatment extends AppCompatActivity {
    List<String> customername=new ArrayList<>();
    ArrayList<Account__Statment_Model> listAccountBalance;
    RecyclerView recyclerView_report;
    LinearLayoutManager layoutManager;
    public  static TextView getAccountList_text;
    Button preview_button_account;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statment);
        ImportData importData= new ImportData(AccountStatment.this);
        importData.getCustomerAccountStatment();
        recyclerView_report=findViewById(R.id.recyclerView_report);
        getAccountList_text=findViewById(R.id.getAccountList_text);
        preview_button_account=findViewById(R.id.preview_button_account);
        preview_button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick",""+listCustomerInfo.size());
                if(listCustomerInfo.size()!=0)
                {
                    fillAdapter();
                }
            }
        });

//        getAccountList_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.toString().equals("1"))
//                {
//                    if(listCustomerInfo.size()!=0)
//                    {
//                        fillAdapter();
//                    }
//                }
//            }
//        });
        layoutManager = new LinearLayoutManager(AccountStatment.this);
        layoutManager.setOrientation(VERTICAL);
        recyclerView_report.setLayoutManager(layoutManager);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.cat);

//        List<String> categories = obj.getAllExistingCategories();
        listAccountBalance=new ArrayList<>();


        customername.add("golden meal");
        customername.add("mall al meal");
        customername.add("الذمم المدينة ");
        customername.add("سوبر ماركت السبع ");
        customername.add("سوبر ماركت ابو دية ");
        customername.add("أسواق السلام ");
        customername.add("اسواق الريان ماركا ");

        final ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customername);
        categorySpinner.setAdapter(ad);

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

        Log.e("listAccountBalance","\t"+listCustomerInfo.size());
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
