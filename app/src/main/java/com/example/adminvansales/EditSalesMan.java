package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adminvansales.Model.SalesManInfo;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;

public class EditSalesMan extends AppCompatActivity {
ListView salesManList;
SalesMenListAdapter salesMenListAdapter;
GlobelFunction globelFunction;
TextView salesNo,clearText,sales,addAdmin;
EditText salesName,password,searchSalesMan,fVSerial,tVSerial,tRSerial,fRSerial,fSSerial,tSSerial;
Button addButton,updateButton;
CheckBox activeCheck;
int AdminSales=1;//1 sales 2 ADMIN
    public  static  List<SalesManInfo> AdminInfoList;
    LinearLayout validityLinear;

    CheckBox AddSalesCheckBox,AddAdminCheckBox,makeOfferCheckBox,
            offerReportCheckBox,accountRCheckBox,paymentRCheckBox,CustomerCheckBox,
            CashRCheckBox,LocationCheckBox,UncollectedRCheckBox,analyzeRCheckBox,
            LogHistoryRCheckBox,AllChecked;
int isUpdate=0;
    String intentEdit;
    SalesManInfo SalesManInfoFill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales_man);
        initial();
        SalesManInfoFill=new SalesManInfo();
        intentEdit = getIntent().getStringExtra("FillData");
        SalesManInfoFill = (SalesManInfo) getIntent().getSerializableExtra("SalesManInfoL");
        if (intentEdit != null && intentEdit.equals("FillData")) {
            fillEditText(SalesManInfoFill);
        }


        globelFunction=new GlobelFunction(EditSalesMan.this);
        globelFunction.getSalesManInfo(EditSalesMan.this,0);

    }

    public void  fillList(List <SalesManInfo>adapterList){
        salesMenListAdapter=new SalesMenListAdapter(EditSalesMan.this,adapterList);
        salesManList.setAdapter(salesMenListAdapter);
    }
    public void fillEditText(SalesManInfo salesManInfo){
        isUpdate=1;
        ChangeBetweenAdminSales(AdminSales);
        addButton.setEnabled(false);
        salesName.setText(salesManInfo.getSalesName());
        password.setText(salesManInfo.getSalesPassword());
        salesNo.setText(salesManInfo.getSalesManNo());
//        fVSerial.setText(salesManInfo.getfVoucherSerial());
//        tVSerial.setText(salesManInfo.gettVoucherSerial());
//        tRSerial.setText(salesManInfo.gettReturnSerial());
//        fRSerial.setText(salesManInfo.getfReturnSerial());
//        fSSerial.setText(salesManInfo.getFstockSerial());
//        tSSerial.setText(salesManInfo.gettStockSerial());

        if(salesManInfo.getActive().equals("0")) {
            activeCheck.setChecked(false);
        }else {
            activeCheck.setChecked(true);
        }
    }

    private void initial() {
        salesManList=findViewById(R.id.salesManList);
        salesMenListAdapter=new SalesMenListAdapter();
        AdminInfoList=new ArrayList<>();
        salesName=findViewById(R.id.salesName);
        password=findViewById(R.id.password);
        salesNo=findViewById(R.id.salesNo);
        searchSalesMan=findViewById(R.id.searchSalesMan);
        clearText=findViewById(R.id.clearText);
        addButton=findViewById(R.id.addButton);
        updateButton=findViewById(R.id.updateButton);
        activeCheck=findViewById(R.id.ActiveCheck);
        fVSerial=findViewById(R.id.FVserial);
        tVSerial=findViewById(R.id.TVserial);
        tRSerial=findViewById(R.id.FRserial);
        fRSerial=findViewById(R.id.TRserial);
        fSSerial=findViewById(R.id.FstockSerial);
        tSSerial=findViewById(R.id.TstockSerial);
        sales=findViewById(R.id.sales);
        addAdmin=findViewById(R.id.addAdmin);
        validityLinear=findViewById(R.id.validityLinear);

        AddSalesCheckBox = findViewById(R.id.AddSalesCheckBox);
        AddAdminCheckBox = findViewById(R.id.AddAdminCheckBox);
        makeOfferCheckBox = findViewById(R.id.makeOfferCheckBox);
        offerReportCheckBox = findViewById(R.id.offerReportCheckBox);
        accountRCheckBox = findViewById(R.id.accountRCheckBox);
        paymentRCheckBox = findViewById(R.id.paymentRCheckBox);
        CustomerCheckBox = findViewById(R.id.CustomerCheckBox);
        CashRCheckBox = findViewById(R.id.CashRCheckBox);
        LocationCheckBox = findViewById(R.id.LocationCheckBox);
        UncollectedRCheckBox = findViewById(R.id.UncollectedRCheckBox);
        analyzeRCheckBox = findViewById(R.id.analyzeRCheckBox);
        LogHistoryRCheckBox = findViewById(R.id.LogHistoryRCheckBox);
        AllChecked=findViewById(R.id.AllChecked);
        AllChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AllCheckedValidity();
            }
        });
        clearText.setOnClickListener(onClick);
        addButton.setOnClickListener(onClick);
        updateButton.setOnClickListener(onClick);
        addAdmin.setOnClickListener(onClick);
        sales.setOnClickListener(onClick);
        searchSalesMan.addTextChangedListener(textWatcher);
        ChangeBetweenAdminSales(AdminSales);
    }


    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            searchSalesMan(AdminSales);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.clearText:
                    clearTextFun();
                    break;
                case R.id.addButton:
                    String s="";
                    if(AdminSales==1){
                        addSalesMan();

                    }else if (AdminSales==2){
                        addAdmin();
                    }
                    break;
                case R.id.updateButton:
                    if(AdminSales==1){
                        updateSaleMan();

                    }else if (AdminSales==2){
                        updateAdmin();
                    }
                    break;
                case R.id.sales:

                    alertMessage(1);
                    break;
                case R.id.addAdmin:

                    alertMessage(2);
                    break;
            }

        }
    };

    void alertMessage(final int adminSales){
        new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("This Change clear all text ")
                .setContentText("")
                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ChangeBetweenAdminSales(adminSales);
                        sweetAlertDialog.dismissWithAnimation();

                    }
                })
                .show();



    }

    void ChangeBetweenAdminSales(final int adminSales){

                        clearTextFun();
                        if(adminSales==1){
                            AdminSales=1;
                            fillList(salesManInfosList);
                            validityLinear.setVisibility(View.GONE);
                            sales.setBackground(EditSalesMan.this.getResources().getDrawable(R.drawable.activite_bac_button));
                            addAdmin.setBackground(null);

                        }else if(adminSales==2){
                            AdminSales=2;
                            fillList(AdminInfoList);
                            ImportData importData=new ImportData(EditSalesMan.this);
                            importData.getAdmin(EditSalesMan.this,0);
                            validityLinear.setVisibility(View.VISIBLE);
                            addAdmin.setBackground(EditSalesMan.this.getResources().getDrawable(R.drawable.activite_bac_button_ri));
                            sales.setBackground(null);

                        }

    }

    void addSalesMan(){

        if(!salesName.getText().toString().equals("")) {
            if(!password.getText().toString().equals("")) {
                if(!fVSerial.getText().toString().equals("")) {
                    if(!tVSerial.getText().toString().equals("")) {
                        if(!fRSerial.getText().toString().equals("")) {
                            if(!tRSerial.getText().toString().equals("")) {
                                if(!fSSerial.getText().toString().equals("")) {
                                    if(!tSSerial.getText().toString().equals("")) {

                                        new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Add Sales Men")
                                                .setContentText("")
                                                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        SalesManInfo salesManInfo = new SalesManInfo();
                                                        salesManInfo.setSalesPassword(password.getText().toString());
                                                        salesManInfo.setSalesName(salesName.getText().toString());
                                                        salesManInfo.setSalesManNo(salesNo.getText().toString());
                                                        salesManInfo.setfVoucherSerial(fVSerial.getText().toString());
                                                        salesManInfo.settVoucherSerial(tVSerial.getText().toString());
                                                        salesManInfo.setfReturnSerial(fRSerial.getText().toString());
                                                        salesManInfo.settReturnSerial(tRSerial.getText().toString());
                                                        salesManInfo.setFstockSerial(fSSerial.getText().toString());
                                                        salesManInfo.settStockSerial(tSSerial.getText().toString());

                                                        if (activeCheck.isChecked()) {
                                                            salesManInfo.setActive("1");
                                                        } else {
                                                            salesManInfo.setActive("0");
                                                        }
                                                        ExportData exportData = new ExportData(EditSalesMan.this);
                                                        exportData.AddSales(EditSalesMan.this, salesManInfo.getJsonObject());
                                                        sweetAlertDialog.dismissWithAnimation();


                                                    }
                                                })
                                                .show();
                                    }else{
                                        tSSerial.setError("Required!");
                                }   }else{
                                    fSSerial.setError("Required!");
                                }   }else{
                                tRSerial.setError("Required!");
                            }   }else{
                            fRSerial.setError("Required!");
                        }   }else{
                        tVSerial.setError("Required!");
                    }   }else{
                    fVSerial.setError("Required!");
                }
            }else {
                password.setError("Required!");
            }
        }else {
            salesName.setError("Required!");
        }

    }

    void addAdmin(){

        if(!salesName.getText().toString().equals("")) {
            if(!password.getText().toString().equals("")) {


                                        new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Add Admin")
                                                .setContentText("")
                                                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        SalesManInfo salesManInfo = new SalesManInfo();
                                                        salesManInfo.setSalesPassword(password.getText().toString());
                                                        salesManInfo.setSalesName(salesName.getText().toString());
                                                        salesManInfo.setSalesManNo(salesNo.getText().toString());


                                                        if (activeCheck.isChecked()) {
                                                            salesManInfo.setActive("1");
                                                        } else {
                                                            salesManInfo.setActive("0");
                                                        }

                                                        if (AddAdminCheckBox.isChecked()) salesManInfo.setAddAdmin(1); else  salesManInfo.setAddAdmin(0);
                                                        if (AddSalesCheckBox.isChecked()) salesManInfo.setAddSalesMen(1); else  salesManInfo.setAddSalesMen(0);
                                                        if (makeOfferCheckBox.isChecked()) salesManInfo.setMakeOffer(1); else  salesManInfo.setMakeOffer(0);
                                                        if (offerReportCheckBox.isChecked()) salesManInfo.setOfferReport(1); else  salesManInfo.setOfferReport(0);
                                                        if (accountRCheckBox.isChecked()) salesManInfo.setAccountReport(1); else  salesManInfo.setAccountReport(0);
                                                        if (paymentRCheckBox.isChecked()) salesManInfo.setPaymentReport(1); else  salesManInfo.setPaymentReport(0);
                                                        if (CustomerCheckBox.isChecked()) salesManInfo.setCustomerReport(1); else  salesManInfo.setCustomerReport(0);
                                                        if (CashRCheckBox.isChecked()) salesManInfo.setCashReport(1); else  salesManInfo.setCashReport(0);
                                                        if (LocationCheckBox.isChecked()) salesManInfo.setSalesManLocation(1); else  salesManInfo.setSalesManLocation(0);
                                                        if (UncollectedRCheckBox.isChecked()) salesManInfo.setUnCollectChequeReport(1); else  salesManInfo.setUnCollectChequeReport(0);
                                                        if (analyzeRCheckBox.isChecked()) salesManInfo.setAnalyzeCustomer(1); else  salesManInfo.setAnalyzeCustomer(0);
                                                        if (LogHistoryRCheckBox.isChecked()) salesManInfo.setLogHistoryReport(1); else  salesManInfo.setLogHistoryReport(0);


                                                        ExportData exportData = new ExportData(EditSalesMan.this);
                                                        exportData.AddAdmins(EditSalesMan.this, salesManInfo.getJsonObjectAdmin());
                                                        sweetAlertDialog.dismissWithAnimation();


                                                    }
                                                })
                                                .show();
            }else {
                password.setError("Required!");
            }
        }else {
            salesName.setError("Required!");
        }

    }

    void updateSaleMan(){
        if(!salesName.getText().toString().equals("")) {
            if(!password.getText().toString().equals("")) {
                if(!fVSerial.getText().toString().equals("")) {
                    if(!tVSerial.getText().toString().equals("")) {
                        if(!fRSerial.getText().toString().equals("")) {
                            if(!tRSerial.getText().toString().equals("")) {
                                if(!fSSerial.getText().toString().equals("")) {
                                    if(!tSSerial.getText().toString().equals("")) {
                new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Update Sales Men")
                        .setContentText("")
                        .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                SalesManInfo salesManInfo = new SalesManInfo();
                                salesManInfo.setSalesPassword(password.getText().toString());
                                salesManInfo.setSalesName(salesName.getText().toString());
                                salesManInfo.setSalesManNo(salesNo.getText().toString());
                                salesManInfo.setfVoucherSerial(fVSerial.getText().toString());
                                salesManInfo.settVoucherSerial(tVSerial.getText().toString());
                                salesManInfo.setfReturnSerial(fRSerial.getText().toString());
                                salesManInfo.settReturnSerial(tRSerial.getText().toString());
                                salesManInfo.setFstockSerial(fSSerial.getText().toString());
                                salesManInfo.settStockSerial(tSSerial.getText().toString());

                                if(activeCheck.isChecked()) {
                                    salesManInfo.setActive("1");
                                }else {
                                    salesManInfo.setActive("0");
                                }
                                ExportData exportData = new ExportData(EditSalesMan.this);
                                exportData.UpdateSales(EditSalesMan.this, salesManInfo.getJsonObject());
                                sweetAlertDialog.dismissWithAnimation();


                            }
                        })
                        .show();

                                    }else{
                                        tSSerial.setError("Required!");
                                    }   }else{
                                    fSSerial.setError("Required!");
                                }   }else{
                                tRSerial.setError("Required!");
                            }   }else{
                            fRSerial.setError("Required!");
                        }   }else{
                        tVSerial.setError("Required!");
                    }   }else{
                    fVSerial.setError("Required!");
                }
            }else {
                password.setError("Required!");
            }
        }else {
            salesName.setError("Required!");
        }
    }

    void updateAdmin(){
        if(!salesName.getText().toString().equals("")) {
            if(!password.getText().toString().equals("")) {

                                        new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Update Admin")
                                                .setContentText("")
                                                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        SalesManInfo salesManInfo = new SalesManInfo();
                                                        salesManInfo.setSalesPassword(password.getText().toString());
                                                        salesManInfo.setSalesName(salesName.getText().toString());
                                                        salesManInfo.setSalesManNo(salesNo.getText().toString());

                                                        if (AddAdminCheckBox.isChecked()) salesManInfo.setAddAdmin(1); else  salesManInfo.setAddAdmin(0);
                                                        if (AddSalesCheckBox.isChecked()) salesManInfo.setAddSalesMen(1); else  salesManInfo.setAddSalesMen(0);
                                                        if (makeOfferCheckBox.isChecked()) salesManInfo.setMakeOffer(1); else  salesManInfo.setMakeOffer(0);
                                                        if (offerReportCheckBox.isChecked()) salesManInfo.setOfferReport(1); else  salesManInfo.setOfferReport(0);
                                                        if (accountRCheckBox.isChecked()) salesManInfo.setAccountReport(1); else  salesManInfo.setAccountReport(0);
                                                        if (paymentRCheckBox.isChecked()) salesManInfo.setPaymentReport(1); else  salesManInfo.setPaymentReport(0);
                                                        if (CustomerCheckBox.isChecked()) salesManInfo.setCustomerReport(1); else  salesManInfo.setCustomerReport(0);
                                                        if (CashRCheckBox.isChecked()) salesManInfo.setCashReport(1); else  salesManInfo.setCashReport(0);
                                                        if (LocationCheckBox.isChecked()) salesManInfo.setSalesManLocation(1); else  salesManInfo.setSalesManLocation(0);
                                                        if (UncollectedRCheckBox.isChecked()) salesManInfo.setUnCollectChequeReport(1); else  salesManInfo.setUnCollectChequeReport(0);
                                                        if (analyzeRCheckBox.isChecked()) salesManInfo.setAnalyzeCustomer(1); else  salesManInfo.setAnalyzeCustomer(0);
                                                        if (LogHistoryRCheckBox.isChecked()) salesManInfo.setLogHistoryReport(1); else  salesManInfo.setLogHistoryReport(0);


                                                        if(activeCheck.isChecked()) {
                                                            salesManInfo.setActive("1");
                                                        }else {
                                                            salesManInfo.setActive("0");
                                                        }
                                                        ExportData exportData = new ExportData(EditSalesMan.this);
                                                        exportData.UpdateAdmin(EditSalesMan.this, salesManInfo.getJsonObjectAdmin());
                                                        sweetAlertDialog.dismissWithAnimation();


                                                    }
                                                })
                                                .show();
            }else {
                password.setError("Required!");
            }
        }else {
            salesName.setError("Required!");
        }
    }
    void clearTextFun(){
        isUpdate=0;
        addButton.setEnabled(true);
        salesName.setText("");
        password.setText("");
        salesNo.setText("00000");
        activeCheck.setChecked(false);
        fVSerial.setText("0");
        tVSerial.setText("0");
        tRSerial.setText("0");
        fRSerial.setText("0");
        fSSerial.setText("0");
        tSSerial.setText("0");

        AddSalesCheckBox.setChecked(false);
        AddAdminCheckBox.setChecked(false);
        makeOfferCheckBox.setChecked(false);
        offerReportCheckBox.setChecked(false);
        accountRCheckBox.setChecked(false);
        paymentRCheckBox.setChecked(false);
        CustomerCheckBox.setChecked(false);
        CashRCheckBox.setChecked(false);
        LocationCheckBox.setChecked(false);
        UncollectedRCheckBox.setChecked(false);
        analyzeRCheckBox.setChecked(false);
        LogHistoryRCheckBox.setChecked(false);
    }


    void AllCheckedValidity(){

        if(AllChecked.isChecked()){
            AddSalesCheckBox.setChecked(true);
            AddAdminCheckBox.setChecked(true);
            makeOfferCheckBox.setChecked(true);
            offerReportCheckBox.setChecked(true);
            accountRCheckBox.setChecked(true);
            paymentRCheckBox.setChecked(true);
            CustomerCheckBox.setChecked(true);
            CashRCheckBox.setChecked(true);
            LocationCheckBox.setChecked(true);
            UncollectedRCheckBox.setChecked(true);
            analyzeRCheckBox.setChecked(true);
            LogHistoryRCheckBox.setChecked(true);
        }else {
            AddSalesCheckBox.setChecked(false);
            AddAdminCheckBox.setChecked(false);
            makeOfferCheckBox.setChecked(false);
            offerReportCheckBox.setChecked(false);
            accountRCheckBox.setChecked(false);
            paymentRCheckBox.setChecked(false);
            CustomerCheckBox.setChecked(false);
            CashRCheckBox.setChecked(false);
            LocationCheckBox.setChecked(false);
            UncollectedRCheckBox.setChecked(false);
            analyzeRCheckBox.setChecked(false);
            LogHistoryRCheckBox.setChecked(false);
        }

    }

    public void searchSalesMan(int adminSales) {
        List<SalesManInfo> searchList = new ArrayList<>();
        searchList.clear();

        if (adminSales == 1) {

            if (!searchSalesMan.getText().toString().equals("")) {
                for (int i = 0; i < salesManInfosList.size(); i++) {
                    if (salesManInfosList.get(i).getSalesName().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase()) ||
                            salesManInfosList.get(i).getSalesManNo().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase())) {
                        SalesManInfo salesManInfo = new SalesManInfo();
                        salesManInfo = salesManInfosList.get(i);
                        searchList.add(salesManInfo);
                    }
                }

                fillList(searchList);

            } else {
                fillList(salesManInfosList);
            }
        }else {

            if (!searchSalesMan.getText().toString().equals("")) {
                for (int i = 0; i < AdminInfoList.size(); i++) {
                    if (AdminInfoList.get(i).getSalesName().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase()) ||
                            AdminInfoList.get(i).getSalesManNo().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase())) {
                        SalesManInfo salesManInfo = new SalesManInfo();
                        salesManInfo = AdminInfoList.get(i);
                        searchList.add(salesManInfo);
                    }
                }

                fillList(searchList);

            } else {
                fillList(AdminInfoList);
            }


        }

    }

}
