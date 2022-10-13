package com.example.adminvansales;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.Report.RequstReport;
import com.example.adminvansales.model.LocaleAppUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.adminvansales.Adapters.SalesManAdapter;
import com.example.adminvansales.Report.OfferseReport;
import com.example.adminvansales.Report.PlansReport;
import com.example.adminvansales.Report.ReportsPopUpClass;
import com.example.adminvansales.model.Password;
import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.Report.AnalyzeAccounts;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ListOfferReport;
import com.example.adminvansales.Report.LogHistoryReport;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.example.adminvansales.Report.UnCollectedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.LogIn.hidePlan;

public class HomeActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener{
    public List<SalesManInfo> picforbar;

    LinearLayout  salesLin;
    public CarouselLayoutManager layoutManagerd;
    public RecyclerView recyclerViews;
    public static TextView waitList, addVanSales;
    RelativeLayout notifyLayout, accountLayout;
    GlobelFunction globelFunction;
    Button locationButton, ReportButton, offerButton,group_offerButton,addPlan_Sales_man;
    LinearLayout ReportLinear;
    public static EditText editPassword;
    TextView offersReport,customerLogReport, paymentReport, cashReport, offerReport,LogReport,unCollectedCheques,
            analyzeAcountsReport,ItemReport, plansReport;
    com.example.adminvansales.model.SettingModel settingModel;
    DataBaseHandler databaseHandler;
   BottomNavigationView bottom_navigation;
   TextView menuBtn,acc_statments;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(HomeActivity.this);
        setContentView(R.layout.activity_home);
        initalView();
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        acc_statments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountStatment.class));

            }
        });
        globelFunction = new GlobelFunction(HomeActivity.this);
        settingModel=new com.example.adminvansales.model.SettingModel ();
        ImportData  importData=new ImportData(HomeActivity.this);

        databaseHandler=new DataBaseHandler(HomeActivity.this);






     //   if( settingModel.getImport_way().equals("0"))
        globelFunction.getSalesManInfo(HomeActivity.this, 1);
      //  else if( settingModel.getImport_way().equals("1"))
      //      importData.  IIs_getSalesMan(HomeActivity.this, 1);


        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                                popUpClass.showPopupWindow(item.getActionView(), HomeActivity.this);

                                return true;

                            case R.id.action_location:
                              startActivity(new Intent(getApplicationContext(), SalesmanMapsActivity.class));
                                overridePendingTransition(0, 0);
                                   return true;

                            case R.id.action_notifications:

                                startActivity(new Intent(getApplicationContext(), RequstNotifaction.class));
                                overridePendingTransition(0, 0);

                                return true;
                            case R.id.RequestReport:

                                startActivity(new Intent(getApplicationContext(), RequstReport.class));
                                overridePendingTransition(0, 0);

                                return true;
                            case R.id.addtraget:

                                startActivity(new Intent(getApplicationContext(), addSalesmanTarget.class));
                                overridePendingTransition(0, 0);

                                return true;

                        }
                        return false;
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("onOptionsItemSelected " ,"onOptionsItemSelected");
        if (toggle.onOptionsItemSelected(item)) {
            Log.e("iditem", "onOptionsItemSelected " + item.getItemId());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Log.e("id", "onNavigationItemSelected " + id);
        switch (id) {

            case R.id.pass_setting: {
                openchangePasswordDialog();
                drawerLayout.closeDrawer(navigationView);
                return true;

            }

            case R.id.add_cust: {
              startActivity(new Intent(HomeActivity.this,AddCustomerLocation.class));
                drawerLayout.closeDrawer(navigationView);
                return true;
            }

            case R.id.itemvible: {
                startActivity(new Intent(HomeActivity.this,ItemVisibility.class));

                drawerLayout.closeDrawer(navigationView);
                return true;
            }



        }

        return true;

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initalView() {
        Log.e("initalView", "initalView " );
        drawerLayout = findViewById(R.id.main_drawerLayout);
    //    LinearLayout linearMain=findViewById(R.id.main_drawerLayout);
        try{
            if(LogIn.languagelocalApp.equals("ar"))
            {
                drawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            else{
                if(LogIn.languagelocalApp.equals("en"))
                {
                    drawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch ( Exception e)
        {
            drawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        bottom_navigation=findViewById(    R.id.bottom_navigation);
        menuBtn=findViewById(    R.id.menuBtn);
        acc_statments=findViewById(    R.id.acc_statments);

        salesLin= findViewById(R.id.salesLin);
        salesLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,EditSalesMan.class));
            }
        });
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
        plansReport = findViewById(R.id.plansReport);
        if(hidePlan==1)
        plansReport.setVisibility(View.GONE);
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

        plansReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent intent = new Intent(HomeActivity.this, PlansReport.class);
                startActivity(intent);

            }
        });

    }


    public void showAllSalesManData(List<SalesManInfo> listSalesMan) {
//        picforbar = dbHandler.getAllAcCount();
//        new GetAllAccount().execute();

//        try {
//            layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
//
//            recyclerViews.setLayoutManager(layoutManagerd);
//            recyclerViews.setHasFixedSize(true);
//            recyclerViews.addOnScrollListener(new CenterScrollListener());
//            layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//
//            recyclerViews.setAdapter(new SalesManAdapter(this, listSalesMan));
//            recyclerViews.requestFocus();
//            recyclerViews.scrollToPosition(2);
//            recyclerViews.requestFocus();
//
//        }catch (Exception e){
//            Log.e("mapException",e.getMessage());
//        }

//    RecyclerView.LayoutManager   layoutManager = new LinearLayoutManager(
//            HomeActivity.this,LinearLayoutManager.VERTICAL,false);
        // recyclerViews.setLayoutManager(layoutManager);
        recyclerViews.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));


//   SalesManInfo salesManInfo =new SalesManInfo();
//
//        salesManInfo.setSalesName("add salesman");
//        listSalesMan.add(salesManInfo);
//        Log.e("listSalesMan===",listSalesMan.size()+"");
        recyclerViews.setAdapter(new    SalesManAdapter(this, listSalesMan));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.changePassowrd) {
//            openchangePasswordDialog();
//
//        } else if (id == R.id.button_notif) {
//            finish();
//            Intent i = new Intent(HomeActivity.this, MainActivity.class);
//            startActivity(i);
//        } else if (id == R.id.button_account) {
//            globelFunction.setValidation();
//            if(salesManInfoAdmin.getAddSalesMen()==1) {
//                finish();
//                Intent i = new Intent(HomeActivity.this, AccountStatment.class);
//                startActivity(i);
//            }else {
//                globelFunction.AuthenticationMessage();
//            }
//        }
//        return super.
//
//                onOptionsItemSelected(item);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.changePassowrd) {
//            openchangePasswordDialog();
//
//        } else if (id == R.id.button_notif) {
//            finish();
//            Intent i = new Intent(HomeActivity.this, MainActivity.class);
//            startActivity(i);
//        } else if (id == R.id.button_account) {
//            globelFunction.setValidation();
//            if(salesManInfoAdmin.getAddSalesMen()==1) {
//                finish();
//                Intent i = new Intent(HomeActivity.this, AccountStatment.class);
//                startActivity(i);
//            }else {
//                globelFunction.AuthenticationMessage();
//            }
//        }
//        else  if (id == R.id.customerLocation)
//        {
////            finish();
//            Intent i = new Intent(HomeActivity.this, AddCustomerLocation.class);
//            startActivity(i);
//        }
//        else  if (id == R.id.itemVisiblity)
//        {
////            finish();
//            Intent i = new Intent(HomeActivity.this, ItemVisibility.class);
//            startActivity(i);
//        }
//
//        return super.
//
//                onOptionsItemSelected(item);
//    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawerLayout.closeDrawer(navigationView);
    }
}
