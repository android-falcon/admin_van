package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.Adapters.RequestDeatilsAdapter;
import com.example.adminvansales.Adapters.RequstAdapter;
import com.example.adminvansales.EditSalesMan;
import com.example.adminvansales.ExportData;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.R;
import com.example.adminvansales.model.ItemsRequsts;
import com.example.adminvansales.model.LocaleAppUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jxl.biff.drawing.CheckBox;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class RequstReport extends AppCompatActivity {
ImportData importData;
    ExportData exportData;
public static TextView requstsRespon,sales_man_name1,date,exportrespon,UpdateRespon;
    GlobelFunction globelFunction;
    Spinner salesManSpinner;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    RecyclerView res;
    RecyclerView.LayoutManager layoutManager;
    android.widget.CheckBox acceptAllcheckBox;
    LinearLayout linearMain;
    Button savebutton,cancel;
    public static List<ItemsRequsts> RNOitemsRequsts=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(RequstReport.this);
        setContentView(R.layout.activity_requst_report);
      linearMain=findViewById(R.id.linearMain);
        layoutManager=new LinearLayoutManager(RequstReport.this,LinearLayoutManager.VERTICAL,false);


         init();

        findViewById(R.id.previewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportData.itemsRequsts.clear();
                RNOitemsRequsts.clear();
                sales_man_name1.setText("");
                date.setText("");
                try {
                    String no = globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());
                    importData.getAllItemsRequsts(no);
                }
             catch (Exception exception){}


            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ImportData.itemsRequsts.size();i++)
               if( ImportData.itemsRequsts.get(i).getStatus().equals("0"))
               {        ImportData.itemsRequsts.remove(i);
                   i--;

               }
                exportData.IIs_SaveVanRequst(ImportData.itemsRequsts);


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    new SweetAlertDialog(RequstReport.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("")
                        .setContentText(getString(R.string.exits))

                        .setConfirmButton(getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                ImportData.itemsRequsts.clear();

                                res.setAdapter(  new RequestDeatilsAdapter(ImportData.itemsRequsts,RequstReport.this));

                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).setCancelButton(getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                        .show();





            }
        });
    }
    public void fillSalesManSpinner() {
         salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesManSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    void init(){
        globelFunction=new GlobelFunction(RequstReport.this);
        exportrespon=findViewById(R.id.exportrespon);
        UpdateRespon=findViewById(R.id.UpdateRespon);
        exportData=new ExportData(RequstReport.this);
        ImportData.itemsRequsts.clear();
        savebutton=findViewById(R.id.saveButton1);
                cancel=findViewById(R.id.cancelButton);
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
        acceptAllcheckBox= findViewById(R.id.acceptAll);



        sales_man_name1=findViewById(R.id.sales_man_name1);
        date=findViewById(R.id.date);
        acceptAllcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptAllcheckBox.isChecked()){
                    for (int i=0;i<ImportData.itemsRequsts.size();i++)
                        ImportData.itemsRequsts.get(i).setStatus("1");
                    res.setAdapter(  new RequestDeatilsAdapter(ImportData.itemsRequsts,RequstReport.this));


                }else {
                    for (int i=0;i<ImportData.itemsRequsts.size();i++)
                    ImportData.itemsRequsts.get(i).setStatus("0");
                    res.setAdapter(  new RequestDeatilsAdapter(ImportData.itemsRequsts,RequstReport.this));

                }
            }
        });
        res=findViewById(R.id.Res);
        res.setLayoutManager(layoutManager);
        salesManSpinner = findViewById(R.id.salesManSpinner);
        if(salesManNameList.size()==0)   globelFunction.  getSalesManInfo(RequstReport.this,8);

        fillSalesManSpinner();


        importData=new ImportData(RequstReport.this);


        requstsRespon=findViewById(R.id.requstsRespon);

        requstsRespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {

              if(s.toString().equals("LOADTYPE")){
                  Log.e("itemsRequsts==",ImportData.itemsRequsts.size()+"");

           //       res.setAdapter(  new RequstAdapter(RequstReport.this,ImportData.itemsRequsts));
                  res.setAdapter(  new RequestDeatilsAdapter(ImportData.itemsRequsts,RequstReport.this));
                  sales_man_name1.setText(salesManSpinner.getSelectedItem().toString());
                  date.setText(ImportData.itemsRequsts.get(0).getDate());
              }
              else{
                  Toast.makeText(RequstReport.this, "No Requsts For this saleman", Toast.LENGTH_SHORT).show();
              }
          }
            }
        });

        exportrespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {

                    if(s.toString().equals("Saved Successfully")){
                        RNOitemsRequsts.clear();
                  for(int i=0;i<ImportData.itemsRequsts.size();i++) {
                      Log.e("itemsRequsts=====",ImportData.itemsRequsts.get(i).getRNO()+"");
                      if(!iscontain(ImportData.itemsRequsts.get(i).getRNO()))
                          {
                              RNOitemsRequsts.add(ImportData.itemsRequsts.get(i));

                          }  }
                      Log.e("RNOitemsRequsts",RNOitemsRequsts.size()+"");
                     exportData.IIs_UpdateVanRequst(  RNOitemsRequsts);
                    }
                }
            }
        });

        UpdateRespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {

                    if(s.toString().equals("Saved Successfully")){
                        ImportData.itemsRequsts.clear();
                        res.setAdapter(  new RequestDeatilsAdapter(ImportData.itemsRequsts,RequstReport.this));
                        saveSuccses();
                    }
                }
            }
        });
    }
    private void saveSuccses() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(this.getString(R.string.saveSuccessfuly))
                .show();
    }
    private boolean    iscontain(String rno) {
       boolean flage=false;
                for (int j = 0; j < RNOitemsRequsts.size(); j++)
                if (RNOitemsRequsts.get(j).getRNO().equals(rno)) {
                    flage=true;
                    Log.e("RNOitemsRequsts====", RNOitemsRequsts.get(j).getRNO() + "");
                }

    return flage;}
}