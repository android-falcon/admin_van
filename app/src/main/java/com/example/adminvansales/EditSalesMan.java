package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
TextView salesNo,clearText;
EditText salesName,password,searchSalesMan;
Button addButton,updateButton;
CheckBox activeCheck;

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
        addButton.setEnabled(false);
        salesName.setText(salesManInfo.getSalesName());
        password.setText(salesManInfo.getSalesPassword());
        salesNo.setText(salesManInfo.getSalesManNo());
        if(salesManInfo.getActive().equals("0")) {
            activeCheck.setChecked(false);
        }else {
            activeCheck.setChecked(true);
        }
    }

    private void initial() {
        salesManList=findViewById(R.id.salesManList);
        salesMenListAdapter=new SalesMenListAdapter();

        salesName=findViewById(R.id.salesName);
        password=findViewById(R.id.password);
        salesNo=findViewById(R.id.salesNo);
        searchSalesMan=findViewById(R.id.searchSalesMan);
        clearText=findViewById(R.id.clearText);
        addButton=findViewById(R.id.addButton);
        updateButton=findViewById(R.id.updateButton);
        activeCheck=findViewById(R.id.ActiveCheck);
        clearText.setOnClickListener(onClick);
        addButton.setOnClickListener(onClick);
        updateButton.setOnClickListener(onClick);
        searchSalesMan.addTextChangedListener(textWatcher);
    }


    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            searchSalesMan();

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
                    addSalesMan();
                    break;
                case R.id.updateButton:
                    updateSaleMan();
                    break;
            }

        }
    };

    void addSalesMan(){

        if(!salesName.getText().toString().equals("")) {
            if(!password.getText().toString().equals("")) {

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
                                if(activeCheck.isChecked()) {
                                    salesManInfo.setActive("1");
                                }else {
                                    salesManInfo.setActive("0");
                                }
                                ExportData exportData = new ExportData(EditSalesMan.this);
                                exportData.AddSales(EditSalesMan.this, salesManInfo.getJsonObject());
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
    }

    public void searchSalesMan() {
        List<SalesManInfo> searchList = new ArrayList<>();
        searchList.clear();

        if (!searchSalesMan.getText().toString().equals("")) {
            for (int i = 0; i < salesManInfosList.size(); i++) {
                if (salesManInfosList.get(i).getSalesName().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase())||
                        salesManInfosList.get(i).getSalesManNo().toUpperCase().contains(searchSalesMan.getText().toString().toUpperCase())) {
                    SalesManInfo salesManInfo=new SalesManInfo();
                    salesManInfo=salesManInfosList.get(i);
                    searchList.add(salesManInfo);
                }
            }

            fillList(searchList);

        }else {
            fillList(salesManInfosList);
        }
    }


}
