package com.example.adminvansales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adminvansales.Adapters.SalesMenListAdapter;
import com.example.adminvansales.Report.ReportsPopUpClass;
import com.example.adminvansales.model.LocaleAppUtils;
import com.example.adminvansales.model.SalesManInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import com.example.adminvansales.model.LocaleAppUtils;
public class EditSalesMan extends AppCompatActivity {
    ListView salesManList;
    SalesMenListAdapter salesMenListAdapter;
    GlobelFunction globelFunction;
    TextView salesNo, clearText;
       RadioButton     sales, addAdmin;
    EditText salesName, password, searchSalesMan, fVSerial, tVSerial, tRSerial, fRSerial, fSSerial, tSSerial;
    Button addButton, updateButton;
    com.example.adminvansales.model.SettingModel SettingModel;
    DataBaseHandler databaseHandler;
    Switch activeCheck;
    int AdminSales = 1;//1 sales 2 ADMIN
    public static List<SalesManInfo> AdminInfoList;
    LinearLayout validityLinear;
    RadioGroup newSalandAdmnRG;
    CheckBox AddSalesCheckBox, AddAdminCheckBox, makeOfferCheckBox,
            offerReportCheckBox, accountRCheckBox, paymentRCheckBox, CustomerCheckBox,
            CashRCheckBox, LocationCheckBox, UncollectedRCheckBox, analyzeRCheckBox,
            LogHistoryRCheckBox, AllChecked;
    int isUpdate = 0;
    String intentEdit;
    SalesManInfo SalesManInfoFill;
    BottomNavigationView bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(EditSalesMan.this);
        setContentView(R.layout.activity_edit_sales_man);
        initial();
        newSalandAdmnRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch(checkedId) {
                    case R.id.sales:

                        alertMessage(1);
                        break;
                    case R.id.addAdmin:

                        alertMessage(2);
                        break;
                }
            }
        });
        databaseHandler=new DataBaseHandler(EditSalesMan.this);
        SettingModel=databaseHandler.getAllSetting();


        SalesManInfoFill = new SalesManInfo();
        intentEdit = getIntent().getStringExtra("FillData");
        SalesManInfoFill = (SalesManInfo) getIntent().getSerializableExtra("SalesManInfoL");
        if (intentEdit != null && intentEdit.equals("FillData")) {
            fillEditText(SalesManInfoFill);
        }

        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_plan:
                                startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));

                                //     startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));
                                //    overridePendingTransition(0, 0);

                                return true;

                            case R.id.action_reports:

                                ReportsPopUpClass popUpClass = new ReportsPopUpClass();
                                popUpClass.showPopupWindow(item.getActionView(), EditSalesMan.this);

                                return true;

                            case R.id.action_location:
                                startActivity(new Intent(getApplicationContext(), SalesmanMapsActivity.class));
                                overridePendingTransition(0, 0);
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

    public void fillList(List<SalesManInfo> adapterList) {
        salesMenListAdapter = new SalesMenListAdapter(EditSalesMan.this, adapterList);
        salesManList.setAdapter(salesMenListAdapter);
    }

    public void fillEditText(SalesManInfo salesManInfo) {
        isUpdate = 1;
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

        if (AdminSales == 2) {
            if (salesManInfo.getAddSalesMen() == 1) {
                AddSalesCheckBox.setChecked(true);
            } else {
                AddSalesCheckBox.setChecked(false);
            }

            if (salesManInfo.getMakeOffer() == 1) {
                makeOfferCheckBox.setChecked(true);
            } else {
                makeOfferCheckBox.setChecked(false);
            }

            if (salesManInfo.getOfferReport() == 1) {
                offerReportCheckBox.setChecked(true);
            } else {
                offerReportCheckBox.setChecked(false);
            }
            if (salesManInfo.getAccountReport() == 1) {
                accountRCheckBox.setChecked(true);
            } else {
                accountRCheckBox.setChecked(false);
            }
            if (salesManInfo.getPaymentReport() == 1) {
                paymentRCheckBox.setChecked(true);
            } else {
                paymentRCheckBox.setChecked(false);
            }
            if (salesManInfo.getCustomerReport() == 1) {
                CustomerCheckBox.setChecked(true);
            } else {
                CustomerCheckBox.setChecked(false);
            }
            if (salesManInfo.getCashReport() == 1) {
                CashRCheckBox.setChecked(true);
            } else {
                CashRCheckBox.setChecked(false);
            }
            if (salesManInfo.getSalesManLocation() == 1) {
                LocationCheckBox.setChecked(true);
            } else {
                LocationCheckBox.setChecked(false);
            }
            if (salesManInfo.getUnCollectChequeReport() == 1) {
                UncollectedRCheckBox.setChecked(true);
            } else {
                UncollectedRCheckBox.setChecked(false);
            }

            if (salesManInfo.getAnalyzeCustomer() == 1) {
                analyzeRCheckBox.setChecked(true);
            } else {
                analyzeRCheckBox.setChecked(false);
            }

            if (salesManInfo.getLogHistoryReport() == 1) {
                LogHistoryRCheckBox.setChecked(true);
            } else {
                LogHistoryRCheckBox.setChecked(false);
            }
            if (salesManInfo.getAddAdmin() == 1) {
                AddAdminCheckBox.setChecked(true);
            } else {
                AddAdminCheckBox.setChecked(false);
            }


        }

        if (salesManInfo.getActive().equals("0")) {
            activeCheck.setChecked(false);
        } else {
            activeCheck.setChecked(true);
        }
    }

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
        newSalandAdmnRG=findViewById(R.id.newSalandAdmnRG);
        salesManList = findViewById(R.id.salesManList);
        salesMenListAdapter = new SalesMenListAdapter();
        AdminInfoList = new ArrayList<>();
        globelFunction = new GlobelFunction(EditSalesMan.this);
        globelFunction.getSalesManInfo(EditSalesMan.this, 0);
        salesName = findViewById(R.id.salesName);
        password = findViewById(R.id.password);
        salesNo = findViewById(R.id.salesNo);
        searchSalesMan = findViewById(R.id.searchSalesMan);
        clearText = findViewById(R.id.clearText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        activeCheck = findViewById(R.id.ActiveCheck);
        fVSerial = findViewById(R.id.FVserial);
        tVSerial = findViewById(R.id.TVserial);
        tRSerial = findViewById(R.id.FRserial);
        fRSerial = findViewById(R.id.TRserial);
        fSSerial = findViewById(R.id.FstockSerial);
        tSSerial = findViewById(R.id.TstockSerial);
        sales = findViewById(R.id.sales);
        addAdmin = findViewById(R.id.addAdmin);
        validityLinear = findViewById(R.id.validityLinear);

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
        AllChecked = findViewById(R.id.AllChecked);
        AllChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AllCheckedValidity();
            }
        });
        clearText.setOnClickListener(onClick);
        addButton.setOnClickListener(onClick);
        updateButton.setOnClickListener(onClick);
    //    addAdmin.setOnClickListener(onClick);
        //sales.setOnClickListener(onClick);
        searchSalesMan.addTextChangedListener(textWatcher);
        ChangeBetweenAdminSales(AdminSales);
    }


    TextWatcher textWatcher = new TextWatcher() {
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

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.clearText:
                    clearTextFun();
                    break;
                case R.id.addButton:
                    String s = "";
                    if (AdminSales == 1) {


                        addSalesMan();

                    } else if (AdminSales == 2) {
                        addAdmin();
                    }
                    break;
                case R.id.updateButton:
                    if (AdminSales == 1) {
                        updateSaleMan();

                    } else if (AdminSales == 2) {
                        updateAdmin();
                    }
                    break;

            }

        }
    };

    void alertMessage(final int adminSales) {

        new SweetAlertDialog(EditSalesMan.this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("This will discard ALL unsaved changes. Do you want to continue ?")
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ChangeBetweenAdminSales(adminSales);
                        sweetAlertDialog.dismissWithAnimation();

                    }
                })
                .show();


    }


    void ChangeBetweenAdminSales(final int adminSales) {
        globelFunction.setValidation();

        clearTextFun();
        if (adminSales == 1) {
            if (salesManInfoAdmin.getAddSalesMen() == 1) {
                AdminSales = 1;
                fillList(salesManInfosList);
                validityLinear.setVisibility(View.GONE);
             //   sales.setBackground(EditSalesMan.this.getResources().getDrawable(R.drawable.activite_bac_button));
                addAdmin.setBackground(null);
            } else {
                globelFunction.AuthenticationMessage();
            }
        } else if (adminSales == 2) {
            if (salesManInfoAdmin.getAddAdmin() == 1) {
                AdminSales = 2;
                fillList(AdminInfoList);
                ImportData importData = new ImportData(EditSalesMan.this);
                importData.getAdmin(EditSalesMan.this, 0);
                validityLinear.setVisibility(View.VISIBLE);
             //   addAdmin.setBackground(EditSalesMan.this.getResources().getDrawable(R.drawable.activite_bac_button_ri));
                sales.setBackground(null);
            } else {
                globelFunction.AuthenticationMessage();
            }
        }

    }


    void addSalesMan() {

        if (!salesName.getText().toString().equals("")) {
            if (!password.getText().toString().equals("")) {
                if (!fVSerial.getText().toString().equals("")) {
                    if (!tVSerial.getText().toString().equals("")) {
                        if (!fRSerial.getText().toString().equals("")) {
                            if (!tRSerial.getText().toString().equals("")) {
                                if (!fSSerial.getText().toString().equals("")) {
                                    if (!tSSerial.getText().toString().equals("")) {

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
                                                        ArrayList<SalesManInfo>salesManInfos=new ArrayList<>();
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
                                                        salesManInfos.add(salesManInfo);
                                                        if (activeCheck.isChecked()) {
                                                            salesManInfo.setActive("1");
                                                        } else {
                                                            salesManInfo.setActive("0");
                                                        }
                                                        ExportData exportData = new ExportData(EditSalesMan.this);
                                                        if( SettingModel.getImport_way().equals("0"))
                                                        exportData.AddSales(EditSalesMan.this, salesManInfo.getJsonObject());
                                                        else   if( SettingModel.getImport_way().equals("1"))
                                                            exportData.IIs_AddSales(salesManInfos,EditSalesMan.this);

                                                        sweetAlertDialog.dismissWithAnimation();

                                                    }
                                                })
                                                .show();
                                    } else {
                                        tSSerial.setError("Required!");
                                    }
                                } else {
                                    fSSerial.setError("Required!");
                                }
                            } else {
                                tRSerial.setError("Required!");
                            }
                        } else {
                            fRSerial.setError("Required!");
                        }
                    } else {
                        tVSerial.setError("Required!");
                    }
                } else {
                    fVSerial.setError("Required!");
                }
            } else {
                password.setError("Required!");
            }
        } else {
            salesName.setError("Required!");
        }

    }

    void addAdmin() {

        if (!salesName.getText().toString().equals("")) {
            if (!password.getText().toString().equals("")) {


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
                                ArrayList<SalesManInfo>salesManInfos2=new ArrayList<>();
                                SalesManInfo salesManInfo = new SalesManInfo();
                                salesManInfo.setSalesPassword(password.getText().toString());
                                salesManInfo.setSalesName(salesName.getText().toString());
                                salesManInfo.setSalesManNo(salesNo.getText().toString());


                                if (activeCheck.isChecked()) {
                                    salesManInfo.setActive("1");
                                } else {
                                    salesManInfo.setActive("0");
                                }

                                if (AddAdminCheckBox.isChecked()) salesManInfo.setAddAdmin(1);
                                else salesManInfo.setAddAdmin(0);
                                if (AddSalesCheckBox.isChecked()) salesManInfo.setAddSalesMen(1);
                                else salesManInfo.setAddSalesMen(0);
                                if (makeOfferCheckBox.isChecked()) salesManInfo.setMakeOffer(1);
                                else salesManInfo.setMakeOffer(0);
                                if (offerReportCheckBox.isChecked()) salesManInfo.setOfferReport(1);
                                else salesManInfo.setOfferReport(0);
                                if (accountRCheckBox.isChecked()) salesManInfo.setAccountReport(1);
                                else salesManInfo.setAccountReport(0);
                                if (paymentRCheckBox.isChecked()) salesManInfo.setPaymentReport(1);
                                else salesManInfo.setPaymentReport(0);
                                if (CustomerCheckBox.isChecked()) salesManInfo.setCustomerReport(1);
                                else salesManInfo.setCustomerReport(0);
                                if (CashRCheckBox.isChecked()) salesManInfo.setCashReport(1);
                                else salesManInfo.setCashReport(0);
                                if (LocationCheckBox.isChecked())
                                    salesManInfo.setSalesManLocation(1);
                                else salesManInfo.setSalesManLocation(0);
                                if (UncollectedRCheckBox.isChecked())
                                    salesManInfo.setUnCollectChequeReport(1);
                                else salesManInfo.setUnCollectChequeReport(0);
                                if (analyzeRCheckBox.isChecked())
                                    salesManInfo.setAnalyzeCustomer(1);
                                else salesManInfo.setAnalyzeCustomer(0);
                                if (LogHistoryRCheckBox.isChecked())
                                    salesManInfo.setLogHistoryReport(1);
                                else salesManInfo.setLogHistoryReport(0);

                                salesManInfos2.add(salesManInfo);
                                ExportData exportData = new ExportData(EditSalesMan.this);
                                if( SettingModel.getImport_way().equals("0"))
                                    exportData.AddAdmins(EditSalesMan.this, salesManInfo.getJsonObjectAdmin());
                                else   if( SettingModel.getImport_way().equals("1"))
                                    exportData.IIs_AddAdmins( salesManInfos2,EditSalesMan.this);

                                sweetAlertDialog.dismissWithAnimation();

                                    sweetAlertDialog.dismissWithAnimation();


                            }
                        })
                        .show();
            } else {
                password.setError("Required!");
            }
        } else {
            salesName.setError("Required!");
        }

    }

    void updateSaleMan() {
        if (!salesName.getText().toString().equals("")) {
            if (!password.getText().toString().equals("")) {
                if (!fVSerial.getText().toString().equals("")) {
                    if (!tVSerial.getText().toString().equals("")) {
                        if (!fRSerial.getText().toString().equals("")) {
                            if (!tRSerial.getText().toString().equals("")) {
                                if (!fSSerial.getText().toString().equals("")) {
                                    if (!tSSerial.getText().toString().equals("")) {
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
                                                        ArrayList<SalesManInfo>salesManInfos3=new ArrayList<>();
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
                                                        salesManInfos3.add(salesManInfo);
                                                        ExportData exportData = new ExportData(EditSalesMan.this);

                                                        if( SettingModel.getImport_way().equals("0"))
                                                            exportData.UpdateSales(EditSalesMan.this, salesManInfo.getJsonObject());
                                                        else   if( SettingModel.getImport_way().equals("1"))

                                                            exportData.IIs_UpdateSales( salesManInfos3,EditSalesMan.this);


                                                        sweetAlertDialog.dismissWithAnimation();


                                                    }
                                                })
                                                .show();

                                    } else {
                                        tSSerial.setError("Required!");
                                    }
                                } else {
                                    fSSerial.setError("Required!");
                                }
                            } else {
                                tRSerial.setError("Required!");
                            }
                        } else {
                            fRSerial.setError("Required!");
                        }
                    } else {
                        tVSerial.setError("Required!");
                    }
                } else {
                    fVSerial.setError("Required!");
                }
            } else {
                password.setError("Required!");
            }
        } else {
            salesName.setError("Required!");
        }
    }

    void updateAdmin() {
        if (!salesName.getText().toString().equals("")) {
            if (!password.getText().toString().equals("")) {

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
                                ArrayList<SalesManInfo>salesManInfos4=new ArrayList<>();
                                SalesManInfo salesManInfo = new SalesManInfo();
                                salesManInfo.setSalesPassword(password.getText().toString());
                                salesManInfo.setSalesName(salesName.getText().toString());
                                salesManInfo.setSalesManNo(salesNo.getText().toString());

                                if (AddAdminCheckBox.isChecked()) salesManInfo.setAddAdmin(1);
                                else salesManInfo.setAddAdmin(0);
                                if (AddSalesCheckBox.isChecked()) salesManInfo.setAddSalesMen(1);
                                else salesManInfo.setAddSalesMen(0);
                                if (makeOfferCheckBox.isChecked()) salesManInfo.setMakeOffer(1);
                                else salesManInfo.setMakeOffer(0);
                                if (offerReportCheckBox.isChecked()) salesManInfo.setOfferReport(1);
                                else salesManInfo.setOfferReport(0);
                                if (accountRCheckBox.isChecked()) salesManInfo.setAccountReport(1);
                                else salesManInfo.setAccountReport(0);
                                if (paymentRCheckBox.isChecked()) salesManInfo.setPaymentReport(1);
                                else salesManInfo.setPaymentReport(0);
                                if (CustomerCheckBox.isChecked()) salesManInfo.setCustomerReport(1);
                                else salesManInfo.setCustomerReport(0);
                                if (CashRCheckBox.isChecked()) salesManInfo.setCashReport(1);
                                else salesManInfo.setCashReport(0);
                                if (LocationCheckBox.isChecked())
                                    salesManInfo.setSalesManLocation(1);
                                else salesManInfo.setSalesManLocation(0);
                                if (UncollectedRCheckBox.isChecked())
                                    salesManInfo.setUnCollectChequeReport(1);
                                else salesManInfo.setUnCollectChequeReport(0);
                                if (analyzeRCheckBox.isChecked())
                                    salesManInfo.setAnalyzeCustomer(1);
                                else salesManInfo.setAnalyzeCustomer(0);
                                if (LogHistoryRCheckBox.isChecked())
                                    salesManInfo.setLogHistoryReport(1);
                                else salesManInfo.setLogHistoryReport(0);


                                if (activeCheck.isChecked()) {
                                    salesManInfo.setActive("1");
                                } else {
                                    salesManInfo.setActive("0");
                                }
                                salesManInfos4.add( salesManInfo);
                                ExportData exportData = new ExportData(EditSalesMan.this);
                                if( SettingModel.getImport_way().equals("0"))

                                exportData.UpdateAdmin(EditSalesMan.this, salesManInfo.getJsonObjectAdmin());
                                else   if( SettingModel.getImport_way().equals("1"))
                                    exportData.IIs_UpdateAdmin(  salesManInfos4,EditSalesMan.this);


                                sweetAlertDialog.dismissWithAnimation();

                            }
                        })
                        .show();
            } else {
                password.setError("Required!");
            }
        } else {
            salesName.setError("Required!");
        }
    }



   public void clearTextFun() {
        try {
            isUpdate = 0;
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
catch (Exception e){

}
    }


    void AllCheckedValidity() {

        if (AllChecked.isChecked()) {
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
        } else {
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
        } else {

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
