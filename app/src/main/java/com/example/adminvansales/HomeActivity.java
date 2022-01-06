package com.example.adminvansales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.adminvansales.Report.OfferseReport;
import com.example.adminvansales.model.Password;
import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.Report.AnalyzeAccounts;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ListOfferReport;
import com.example.adminvansales.Report.LogHistoryReport;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.example.adminvansales.Report.UnCollectedData;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listSalesMan;

public class HomeActivity extends AppCompatActivity {
    public List<SalesManInfo> picforbar;
    public CarouselLayoutManager layoutManagerd;
    public RecyclerView recyclerViews;
    public static TextView waitList, addVanSales;
    RelativeLayout notifyLayout, accountLayout;
    GlobelFunction globelFunction;
    Button locationButton, ReportButton, offerButton,group_offerButton;
    LinearLayout ReportLinear;
    public static EditText editPassword;
    TextView offersReport,customerLogReport, paymentReport, cashReport, offerReport,LogReport,unCollectedCheques,
            analyzeAcountsReport,ItemReport,addPlan_Sales_man;
    com.example.adminvansales.model.SettingModel settingModel;
    DataBaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initalView();
        globelFunction = new GlobelFunction(HomeActivity.this);
        settingModel=new com.example.adminvansales.model.SettingModel ();
        ImportData  importData=new ImportData(HomeActivity.this);

        databaseHandler=new DataBaseHandler(HomeActivity.this);






     //   if( settingModel.getImport_way().equals("0"))
        globelFunction.getSalesManInfo(HomeActivity.this, 1);
      //  else if( settingModel.getImport_way().equals("1"))
      //      importData.  IIs_getSalesMan(HomeActivity.this, 1);

    }

    private void initalView() {
        offersReport=findViewById(R.id.offersReport);
        notifyLayout = findViewById(R.id.notifyLayout);
        addVanSales = findViewById(R.id.addVanSales);
        accountLayout = findViewById(R.id.accountLayout);


        addVanSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();

                if(salesManInfoAdmin.getAddAdmin()==1 || salesManInfoAdmin.getAddSalesMen()==1  ) {
                    Intent intentEditSales = new Intent(HomeActivity.this, EditSalesMan.class);
                    startActivity(intentEditSales);
                }else {
                   globelFunction. AuthenticationMessage();
                }
            }
        });

        offerButton = findViewById(R.id.offerButton);
        group_offerButton=findViewById(R.id.group_offerButton);
        group_offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offerIntent = new Intent(HomeActivity.this, GroupOffer.class);
                startActivity(offerIntent);
            }
        });
        locationButton = findViewById(R.id.LocationButton);
        ReportButton = findViewById(R.id.ReportButton);
        recyclerViews = findViewById(R.id.res);
        waitList = findViewById(R.id.waitList);
        ReportLinear = findViewById(R.id.ReportLinear);
        ReportLinear.setVisibility(View.GONE);
        customerLogReport = findViewById(R.id.customerLogReport);
        paymentReport = findViewById(R.id.paymentReport);
        cashReport = findViewById(R.id.cashReport);
        unCollectedCheques= findViewById(R.id.unCollectedChequesReport);
        analyzeAcountsReport= findViewById(R.id.analyzeAcountsReport);
        offerReport = findViewById(R.id.offerReport);
        LogReport=findViewById(R.id.LogReport);
        ItemReport=findViewById(R.id.ItemReport);
        addPlan_Sales_man=findViewById(R.id.addPlan_Sales_man);
        addPlan_Sales_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,PlanSalesMan.class);
                startActivity(intent);
            }
        });

        waitList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("1")) {

               Log.e(" salesManInfosList==", salesManInfosList.size()+"");

                    if (  salesManInfosList.size() != 0) {
                        Log.e(" salesManInfosList2==", salesManInfosList.size()+"");
                        showAllSalesManData(salesManInfosList);
                    }
                  /*  if (listSalesMan.size() != 0) {
                        showAllSalesManData(listSalesMan);
                    }*/
                }

            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getSalesManLocation()==1) {
                    Intent locationIntent = new Intent(HomeActivity.this, SalesmanMapsActivity.class);
                    startActivity(locationIntent);
                }else {
                    globelFunction.AuthenticationMessage();
                }
            }
        });


        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getMakeOffer()==1) {
                    Intent offerIntent = new Intent(HomeActivity.this, OfferPriceList.class);
                    startActivity(offerIntent);
                }else {


                    globelFunction. AuthenticationMessage();

                }
            }

        });

        ReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReportLinear.getVisibility() == View.VISIBLE) {
                    ReportLinear.setVisibility(View.GONE);
                } else {
                    ReportLinear.setVisibility(View.VISIBLE);
                }
            }
        });

        customerLogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getCustomerReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent locationIntent = new Intent(HomeActivity.this, CustomerLogReport.class);
                    startActivity(locationIntent);
                }else {
                    globelFunction. AuthenticationMessage();

                }

            }
        });

        offersReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(HomeActivity.this, OfferseReport.class);
                startActivity(locationIntent);
            }
        });


        paymentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getPaymentReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent locationIntent = new Intent(HomeActivity.this, PaymentDetailsReport.class);
                    startActivity(locationIntent);
                }else {
                    globelFunction.AuthenticationMessage();

                }
            }
        });
        cashReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getCashReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent locationIntent = new Intent(HomeActivity.this, CashReport.class);
                    startActivity(locationIntent);

                }else {
                    globelFunction.  AuthenticationMessage();

                }
            }
        });
        offerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getOfferReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent listIntent = new Intent(HomeActivity.this, ListOfferReport.class);
                    startActivity(listIntent);
                }else {
                    globelFunction. AuthenticationMessage();

                }
            }
        });
        LogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getLogHistoryReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent listIntent = new Intent(HomeActivity.this, LogHistoryReport.class);
                    startActivity(listIntent);
                }else {
                    globelFunction.AuthenticationMessage();

                }
            }
        });
        unCollectedCheques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getUnCollectChequeReport()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent intent = new Intent(HomeActivity.this, UnCollectedData.class);
                    startActivity(intent);
                }else {
                    globelFunction.   AuthenticationMessage();

                }
            }
        });
        analyzeAcountsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getAnalyzeCustomer()==1) {
                    ReportLinear.setVisibility(View.GONE);
                    Intent intent = new Intent(HomeActivity.this, AnalyzeAccounts.class);
                    startActivity(intent);
                }else {
                    globelFunction. AuthenticationMessage();

                }
            }
        });
//        Analyze a customer's account

        ItemReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent intent = new Intent(HomeActivity.this, ItemReport.class);
                startActivity(intent);

            }
        });

    }


    public void showAllSalesManData(List<SalesManInfo> listSalesMan) {
//        picforbar = dbHandler.getAllAcCount();
//        new GetAllAccount().execute();

        try {
            layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);

            recyclerViews.setLayoutManager(layoutManagerd);
            recyclerViews.setHasFixedSize(true);
            recyclerViews.addOnScrollListener(new CenterScrollListener());
            layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());

            recyclerViews.setAdapter(new SalesManAdapter(this, listSalesMan));
            recyclerViews.requestFocus();
            recyclerViews.scrollToPosition(2);
            recyclerViews.requestFocus();

        }catch (Exception e){
            Log.e("mapException",e.getMessage());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changePassowrd) {
            openchangePasswordDialog();

        } else if (id == R.id.button_notif) {
            finish();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.button_account) {
            globelFunction.setValidation();
            if(salesManInfoAdmin.getAddSalesMen()==1) {
                finish();
                Intent i = new Intent(HomeActivity.this, AccountStatment.class);
                startActivity(i);
            }else {
                globelFunction.AuthenticationMessage();
            }
        }
        return super.

                onOptionsItemSelected(item);
    }

    private void openchangePasswordDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_setting);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);

        editPassword = (EditText) dialog.findViewById(R.id.passowrdEdit);
        getPassword();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editPassword.getText().toString().equals("")) {
                    savePassowrdSetting(editPassword.getText().toString());
                    dialog.dismiss();
                } else {
                    editPassword.setError("Required");
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getPassword() {
        ExportData exportData = new ExportData(HomeActivity.this);



        settingModel=databaseHandler.getAllSetting();
        if(settingModel.getImport_way().equals("0"))
            exportData.getPassowrdSetting();
        else      if(settingModel.getImport_way().equals("1"))
            exportData.   IIs_getPassowrdSetting();
    }

    private void getCurentPassowrd(EditText editPassword) {
        editPassword.setText("303090");
    }

    private void savePassowrdSetting(String passowrd) {
        List <  Password>passwords=new ArrayList<>();
        Password password=new Password();
        password.setUSER_PASSWORD(passowrd);
        password.setPASSWORDTYPE("1");
        passwords.add(   password);
        settingModel=databaseHandler.getAllSetting();
        ExportData exportData = new ExportData(HomeActivity.this);
        if(settingModel.getImport_way().equals("0"))
        exportData.savePassowrdSetting(passowrd);
        else      if(settingModel.getImport_way().equals("1"))
            exportData. IIs_savePassowrdSetting( passwords);
    }




}
