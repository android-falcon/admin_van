package com.example.adminvansales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.adminvansales.model.Flag_Settingss;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.model.SettingModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import static com.example.adminvansales.GlobelFunction.adminId;
import static com.example.adminvansales.GlobelFunction.adminName;
import static com.example.adminvansales.ImportData.listId;
import com.example.adminvansales.model.LocaleAppUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogIn extends AppCompatActivity {
    SliderLayout sliderLayout;
//    FloatingActionButton setting_floatingBtn;
    TextView setting_floatingBtn;
    Button button_logIn,button_sighnup;
    public  static String ipAddress="",portSettings="",import_way="",Cono="";
    SettingModel settingModelList;
    private DataBaseHandler databaseHandler;
    EditText userName_edit,password_edit;
    GlobelFunction globelFunction;
    Timer timer;
    ImportData importData;
    public static String languagelocalApp = "";
    String typeimport="0";
    List<Flag_Settingss> flag_settingsList;
    List<Flag_Settingss> flagsettingsList;

    com.example.adminvansales.model.SettingModel settingModel;
    public static int typaImport=1;//0---- mySql   1-----IIs
    public static int LANGUAGE=0;//0---- EN   1-----AR
    public  static int rawahneh=0;// 1= EXPORT STOCK TABLES
    public  static int locationtrackerFlage=0;
    public  static    int getMaxVoucherServer=0;
    public  static    int PlanTYPE;
    public  static  int passwordSettingAdmin=0;//0 ---> static password   1 ----->password from admin
    public  static  int makeOrders=0;// 1= just orders app
    public  static    int getTotalBalanceInActivities=0;
    public  static    int voucherReturn_spreat=1;
    public  static   int  talaatLayoutAndPassowrd=0;
    public static  int hidePlan=1;

    public static Context contextG;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(LogIn.this);
        setContentView(R.layout.activity_log_in);
        Log.e("importDataMasaterrrr","importData;;;");


        settingModel=new com.example.adminvansales.model.SettingModel ();

        initView();
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(4); //set scroll delay in seconds :
        setSliderViews();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        LinearLayout linearMain=findViewById(R.id.linearMain);
        try{
            if(languagelocalApp.equals("ar"))
            {
                linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            else{
                if(languagelocalApp.equals("en"))
                {
                    linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch ( Exception e)
        {
            linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        settingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(LogIn.this);
        flagsettingsList =databaseHandler.getFlagSettings();
      if(flagsettingsList.size()!=0) {

    //      locationtrackerFlage= flagsettingsList.get(0).getLocationtracker();
      }
        sliderLayout = findViewById(R.id.imageSlider_2);
        setting_floatingBtn=findViewById(R.id.setting_floatingBtn);

        settingModelList=new SettingModel();
        try {
            settingModelList = databaseHandler.getAllSetting();
            PlanTYPE=settingModelList.getPlan_Type();
//            Log.e("settingModelList",""+settingModelList.getIpAddress().trim().length());
            if(settingModelList.getIpAddress().trim().length()!=0)
            {
                ipAddress = settingModelList.getIpAddress();

                Log.e("GETPlanTYPE==",PlanTYPE+"");
            }else {
                settingDialog();
            }
            locationtrackerFlage= settingModelList.getLocationtracker();
        }catch (Exception t){

            Log.e("ipAddress","error");
        }
        setting_floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingDialog();
            }
        });
        button_logIn=findViewById(R.id.button_logIn);
        password_edit=findViewById(R.id.password_edit);
        userName_edit=findViewById(R.id.userName_edit);
        importData=new ImportData(LogIn.this);
        Log.e("importData","importData");

         try {
             typeimport=settingModel.getImport_way();
             if(typeimport==null){
                 typeimport="1";
             }



         }catch (Exception e)
         {
             typeimport="1";

         }
        typeimport="1";
        Log.e("importData","importData="+typeimport);
        if (typeimport.equals("0"))
            importData.getCustomerInfo(0);
        else if (typeimport.equals("1"))
            importData.IIs_getCustomerInfo(0);


        globelFunction=new GlobelFunction(LogIn.this);
        timer = new Timer();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        // update ui here
                        if (isNetworkAvailable()) {
//                            getData();
//                            if(listId.size()!=0)
//                            {
//
//                                fillData(LogIn.this);
////                                updateSeenOfRow();
//                            }
                        }

                    }
                });
            }

        }, 0, 3000);


        Log.e("importData","11111");


        button_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingModel settingModels;
                try {

                    settingModels =databaseHandler.getAllSetting();

             if(!settingModels.getIpAddress().equals(""))

             {
                 Log.e("button_logIn","button_logIn");
                 Authentication();

             }



                else
             {
                 GlobelFunction.showSweetDialog(LogIn.this,3,getResources().getString(R.string.fillip),"");
             }

                }catch (Exception e){

                }

//

//
            }
        });


    }

    void Authentication (){
//        if(userName_edit.getText().toString().equals("admin"))
//        {
//            if(password_edit.getText().toString().equals("100"))
//            {
//                goToMain();
//            }
//            else {
//                password_edit.setError("Required");
//            }
//        }
//        else {
//            userName_edit.setError("Required");
//        }


        if(!userName_edit.getText().toString().trim().equals("")){
            if(!password_edit.getText().toString().trim().equals("")){
                if(userName_edit.getText().toString().trim().equals("admin")&&password_edit.getText().toString().trim().equals("100")) {

                    SalesManInfo salesManInfo = new SalesManInfo();

                    salesManInfo.setAddSalesMen(1);
                    salesManInfo.setAddAdmin(1);
                    salesManInfo.setAccountReport(1);
                    salesManInfo.setPaymentReport(1);

                    salesManInfo.setCashReport(1);
                    salesManInfo.setUnCollectChequeReport(1);
                    salesManInfo.setAnalyzeCustomer(1);
                    salesManInfo.setLogHistoryReport(1);
                    salesManInfo.setOfferReport(1);
                    salesManInfo.setSalesManLocation(1);
                    salesManInfo.setMakeOffer(1);
                    salesManInfo.setCustomerReport(1);

                    saveAuth(salesManInfo);
                        goToMain();
                }else {


               ImportData importData = new ImportData(LogIn.this);
               importData.GetAuthentication(LogIn.this,userName_edit.getText().toString(),password_edit.getText().toString(),0);

                }
            }else {
                password_edit.setError("Required");
            }
        } else {
            userName_edit.setError("Required");
        }


    }

    private void getData() {
        importData=new ImportData(LogIn.this);

        try {
            if( settingModel.getImport_way().equals("0"))
                importData.getListRequest();
        }catch (Exception e){

        }


  //  else if( settingModel.getImport_way().equals("1"))
    //importData.IIS_getListRequest();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void saveAuth(SalesManInfo salesManInfo){
        globelFunction.saveValidition(salesManInfo);
    }

    public void goToMain() {

        adminName=userName_edit.getText().toString();
        adminId=password_edit.getText().toString();
    if(LogIn.locationtrackerFlage!=0)
    {  startservice();
        finish();
        Intent i = new Intent(LogIn.this, HomeActivity.class);
        startActivity(i);
    }else
    {
        Intent i = new Intent(LogIn.this, HomeActivity.class);
        startActivity(i);
        stopSer();
    }
    }

    private void startservice() {
        Log.e("startservice","onCreate");
        Intent i = new Intent(LogIn.this, LocationService.class);
        i.putExtra("extraService","adminName");
        startService(i);
//        ContextCompat.startForegroundService(this,i);

    }
    void stopSer(){
        Intent i = new Intent(LogIn.this, LocationService.class);

        stopService(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setSliderViews() {
        for (int i = 0; i < 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);
//            sliderView.setImageScaleType(ImageView.ScaleType.FIT_END);
            switch (i) {
                case 0:

                    sliderView.setImageDrawable(R.drawable.discount);
                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                    sliderView.setDescription(" To manage Offers and Discount" );
                    break;
                case 1:
                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//                    sliderView.setDescription("Plutus App  Enables you to safely deal in all parts of the country and with all people." );
                    sliderView.setImageDrawable(R.drawable.transactionicone);
                    break;
                case 2:
//                    sliderView.setDescription("Plutus App  Enables you to withdraw cash bu your phone  at any time and anywhere." );
                    sliderView.setImageDrawable(R.drawable.wayes_to_offer);

                    break;

            }

//            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//            sliderView.setDescription("The Gas System produst by Falcons Soft Companey." +
//                    "  " + (i + 1));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(LogIn.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
    public  void settingDialog(){
        Log.e("settingDialogPlanTYPE==",PlanTYPE+"");
//            new LocaleAppUtils().changeLayot(context);
        final Dialog dialog = new Dialog(LogIn.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.setting_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics dm = new DisplayMetrics();
        LogIn.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = (int) (width / 1.3);
        dialog.getWindow().setAttributes(layoutParams);
        TextView more=dialog.findViewById( R.id. more);
        Button saveSetting=dialog.findViewById(R.id.saveSetting);
        SwitchCompat   locationtracker= dialog.findViewById(R.id.locationtracker);
        final EditText editTextIp=dialog.findViewById(R.id.setindEditText);
        final EditText  portSetting=dialog.findViewById(R.id.portSetting);
        RadioGroup plansetting=dialog.findViewById(R.id.plansetting);
     RadioButton   week_plan=dialog.findViewById(R.id.week_plan);
        RadioButton  month_plan=dialog.findViewById(R.id.month_plan);
        plansetting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                switch(radioButtonID) {
                    case R.id.week_plan:
                        PlanTYPE=1;
                        Log.e("week_plan,PlanTYPE==",PlanTYPE+"");
                        break;
                    case R.id.month_plan:
                        PlanTYPE=0;
                        Log.e("month_plan,PlanTYPE==",PlanTYPE+"");
                        break;

                }
            }
        });
//RadioButton RB_mysql= dialog.findViewById(R.id.RB_mysql);
//        RB_mysql.setChecked(true);
//        RadioButton RB_iis= dialog.findViewById(R.id.RB_iis);
        final EditText  CoNo=dialog.findViewById(R.id.cono);
        ipAddress="";
        portSettings="";
        SettingModel settingModels=new SettingModel();
        try {

           settingModels =databaseHandler.getAllSetting();
           ipAddress=settingModels.getIpAddress();
            if(!ipAddress.equals(""))
            {
                portSettings=settingModels.getPort();
                import_way=settingModels.getImport_way();
                Cono=settingModels.getCono();
                locationtracker.setChecked((settingModels.getLocationtracker() == 1));
                locationtrackerFlage=settingModels.getLocationtracker();
             if   (settingModels.getPlan_Type()==1 ){
                 month_plan.setChecked(false );
                 week_plan.setChecked(true );
             }
             else{   week_plan.setChecked(false );
                 month_plan.setChecked(true );
             }
//                if(import_way.equals("0"))
//                    RB_mysql.setChecked(true);
//                else
//                    RB_iis.setChecked(true);
                editTextIp.setText(ipAddress);

                portSetting.setText(portSettings);

                CoNo.setText(Cono);
            }
        }
        catch (Exception e)
        {

        }
        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    databaseHandler.delete
                ipAddress= editTextIp.getText().toString().trim();
                portSettings=portSetting.getText().toString().trim();
                Cono=CoNo.getText().toString().trim();
                int loc_trac=0;
                if(locationtracker.isChecked())loc_trac=1;
                if(ipAddress.trim().length()!=0)
                {
                    if(portSettings.trim().length()!=0) {

                        if(Cono.trim().length()!=0) {
                            Log.e("PlanTYPE==",PlanTYPE+"");
                            databaseHandler.addSetting(ipAddress,portSettings,"1",Cono,loc_trac,PlanTYPE);
                            dialog.dismiss();
                            locationtrackerFlage=loc_trac;

                        }else {
                            CoNo.setError("*Required");
                        }


                    }else {
                        portSetting.setError("*Required");
                    }


                }else {
                    editTextIp.setError("*Required");
                }


            }
        });
        more.setOnClickListener(v -> {
            showMoreSettingDialog();



        });
        dialog.show();

    }
    private void showMoreSettingDialog() {

        final Dialog moreDialog = new Dialog(LogIn.this);
        moreDialog.setCancelable(false);
        moreDialog.setContentView(R.layout.more_settings_dialog);
        moreDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(moreDialog.getWindow().getAttributes());
        lp.width = (int)(getResources().getDisplayMetrics().widthPixels/1.15);
        lp.height = (int)(getResources().getDisplayMetrics().heightPixels/1.25);
        moreDialog.getWindow().setAttributes(lp);
        moreDialog.show();

        Button okBtn = moreDialog.findViewById(R.id.okBtn);

        Button cancelBtn = moreDialog.findViewById(R.id.cancelBtn);

        RadioGroup radioGrpData = moreDialog.findViewById(R.id.radioGrpData);
        RadioGroup radioGrpLang = moreDialog.findViewById(R.id.radioGrplang);
        RadioButton radioBtnEN= moreDialog.findViewById(R.id.radioBtnEN);

//            RadioButton radioBtnSQL = moreDialog.findViewById(R.id.radioBtnSQL);
//            RadioButton radioBtnIIS = moreDialog.findViewById(R.id.radioBtnIIS);

        SwitchCompat swExport;
        SwitchCompat swMax, swOrder, swPassword, swTotal, swReturn,locationtracker;
        swExport = moreDialog.findViewById(R.id.swExport);
        swMax = moreDialog.findViewById(R.id.swMax);
        swOrder = moreDialog.findViewById(R.id.swOrder);
        swPassword = moreDialog.findViewById(R.id.swPassword);
        swTotal = moreDialog.findViewById(R.id.swTotal);
        swReturn = moreDialog.findViewById(R.id.swReturn);
        locationtracker= moreDialog.findViewById(R.id.locationtracker);
        RadioButton radioBtnAR= moreDialog.findViewById(R.id.radioBtnAR);
        flag_settingsList =databaseHandler.getFlagSettings();

        if (flag_settingsList.size() != 0) {

            if (flag_settingsList.get(0).getData_Type().equals("mysql")) {
//                    radioBtnSQL.setChecked(true);
//                    radioBtnIIS.setChecked(false);
                radioGrpData.check(R.id.radioBtnSQL);
            } else {
//                    radioBtnSQL.setChecked(false);
//                    radioBtnIIS.setChecked(true);
                radioGrpData.check(R.id.radioBtnIIS);
            }
            if (flag_settingsList.get(0).getArabic_language()==1) {
Log.e("gggggg","aaaaa");
            //    radioGrpData.check(R.id.radioBtnEN);
                radioBtnEN .setChecked(true);
            } else {
                Log.e("bbbbb","aaaaa");

                radioBtnAR .setChecked(true);
              //  radioGrpData.check(R.id.radioBtnAR);
            }
            swExport.setChecked((flag_settingsList.get(0).getExport_Stock() == 1));
            swMax.setChecked((flag_settingsList.get(0).getMax_Voucher() == 1));
            swOrder.setChecked((flag_settingsList.get(0).getMake_Order() == 1));
            swPassword.setChecked((flag_settingsList.get(0).getAdmin_Password() == 1));
            swTotal.setChecked((flag_settingsList.get(0).getTotal_Balance() == 1));
            swReturn.setChecked((flag_settingsList.get(0).getVoucher_Return() == 1));
            locationtracker.setChecked((flag_settingsList.get(0).getLocationtracker() == 1));
        }

        okBtn.setOnClickListener(v1 -> {

            //update flag_settings
            //update variables
            String dataType1;
            if (radioGrpData.getCheckedRadioButtonId() == R.id.radioBtnSQL) {
                typaImport = 0;
                dataType1 = "mysql";
            } else {
                typaImport = 1;
                dataType1 = "iis";
            }
            if (radioGrpLang.getCheckedRadioButtonId() == R.id.radioBtnAR) {
                LANGUAGE=1;
            } else {
                LANGUAGE=0;
            }
            rawahneh = swExport.isChecked() ? 1 : 0;
            getMaxVoucherServer = swMax.isChecked() ? 1 : 0;
            makeOrders = swOrder.isChecked() ? 1 : 0;
            passwordSettingAdmin = swPassword.isChecked() ? 1 : 0;
            getTotalBalanceInActivities = swTotal.isChecked() ? 1 : 0;
            voucherReturn_spreat = swReturn.isChecked() ? 1 : 0;
            locationtrackerFlage= locationtracker.isChecked() ? 1 : 0;
            if(flag_settingsList.size()==0)
            {
               databaseHandler.insertFlagSettings(new Flag_Settingss(dataType1, rawahneh, getMaxVoucherServer,
                        makeOrders, passwordSettingAdmin, getTotalBalanceInActivities, voucherReturn_spreat,LANGUAGE,locationtrackerFlage));
            }else {
                databaseHandler.updateFlagSettings(dataType1, rawahneh, getMaxVoucherServer,
                        makeOrders, passwordSettingAdmin, getTotalBalanceInActivities, voucherReturn_spreat,LANGUAGE,locationtrackerFlage);
            }




            moreDialog.dismiss();

        });

        cancelBtn.setOnClickListener(v12 -> moreDialog.dismiss());
    }
}
