package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import com.example.adminvansales.model.Flag_Settingss;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.model.SettingModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.androidgamesdk.gametextinput.Settings;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static com.example.adminvansales.GlobelFunction.adminId;
import static com.example.adminvansales.GlobelFunction.adminName;
import static com.example.adminvansales.ImportData.listId;


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
    String typeimport="0";
    List<Flag_Settingss> flag_settingsList;
    com.example.adminvansales.model.SettingModel settingModel;
    public static int typaImport=0;//0---- mySql   1-----IIs

    public  static int rawahneh=0;// 1= EXPORT STOCK TABLES
    public  static    int getMaxVoucherServer=0;

    public  static  int passwordSettingAdmin=0;//0 ---> static password   1 ----->password from admin
    public  static  int makeOrders=0;// 1= just orders app
    public  static    int getTotalBalanceInActivities=0;
    public  static    int voucherReturn_spreat=1;
    public  static   int  talaatLayoutAndPassowrd=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Log.e("importDataMasaterrrr","importData;;;");


        settingModel=new com.example.adminvansales.model.SettingModel ();

        initView();
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(4); //set scroll delay in seconds :
        setSliderViews();
    }
    private void initView() {
        settingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(LogIn.this);

        sliderLayout = findViewById(R.id.imageSlider_2);
        setting_floatingBtn=findViewById(R.id.setting_floatingBtn);

        settingModelList=new SettingModel();
        try {
            settingModelList = databaseHandler.getAllSetting();
            ipAddress = settingModelList.getIpAddress();
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

               Authentication();

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
      //  startservice();
        finish();
        Intent i = new Intent(LogIn.this, HomeActivity.class);
        startActivity(i);
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
//            new LocaleAppUtils().changeLayot(context);
        final Dialog dialog = new Dialog(LogIn.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.setting_dialog);
        TextView more=dialog.findViewById( R.id. more);
        Button saveSetting=dialog.findViewById(R.id.saveSetting);
        final EditText editTextIp=dialog.findViewById(R.id.setindEditText);
        final EditText  portSetting=dialog.findViewById(R.id.portSetting);
RadioButton RB_mysql= dialog.findViewById(R.id.RB_mysql);
        RB_mysql.setChecked(true);
        RadioButton RB_iis= dialog.findViewById(R.id.RB_iis);
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
                if(import_way.equals("0"))
                    RB_mysql.setChecked(true);
                else
                    RB_iis.setChecked(true);
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
                ipAddress= editTextIp.getText().toString();
                portSettings=portSetting.getText().toString();
                Cono=CoNo.getText().toString();
                databaseHandler.addSetting(ipAddress,portSettings,"1",Cono);
               /* if(RB_mysql.isChecked()==true)
                    //0 to mysql
                databaseHandler.addSetting(ipAddress,portSettings,"0",Cono);
                else
                    //1 to IIs
                    databaseHandler.addSetting(ipAddress,portSettings,"1",Cono);*/
                dialog.dismiss();

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
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(moreDialog.getWindow().getAttributes());
        lp.width = (int)(getResources().getDisplayMetrics().widthPixels/1.15);
        moreDialog.getWindow().setAttributes(lp);
        moreDialog.show();

        Button okBtn = moreDialog.findViewById(R.id.okBtn);

        Button cancelBtn = moreDialog.findViewById(R.id.cancelBtn);

        RadioGroup radioGrpData = moreDialog.findViewById(R.id.radioGrpData);
//            RadioButton radioBtnSQL = moreDialog.findViewById(R.id.radioBtnSQL);
//            RadioButton radioBtnIIS = moreDialog.findViewById(R.id.radioBtnIIS);

        Switch swExport, swMax, swOrder, swPassword, swTotal, swReturn;
        swExport = moreDialog.findViewById(R.id.swExport);
        swMax = moreDialog.findViewById(R.id.swMax);
        swOrder = moreDialog.findViewById(R.id.swOrder);
        swPassword = moreDialog.findViewById(R.id.swPassword);
        swTotal = moreDialog.findViewById(R.id.swTotal);
        swReturn = moreDialog.findViewById(R.id.swReturn);

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

            swExport.setChecked((flag_settingsList.get(0).getExport_Stock() == 1));
            swMax.setChecked((flag_settingsList.get(0).getMax_Voucher() == 1));
            swOrder.setChecked((flag_settingsList.get(0).getMake_Order() == 1));
            swPassword.setChecked((flag_settingsList.get(0).getAdmin_Password() == 1));
            swTotal.setChecked((flag_settingsList.get(0).getTotal_Balance() == 1));
            swReturn.setChecked((flag_settingsList.get(0).getVoucher_Return() == 1));

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

            rawahneh = swExport.isChecked() ? 1 : 0;
            getMaxVoucherServer = swMax.isChecked() ? 1 : 0;
            makeOrders = swOrder.isChecked() ? 1 : 0;
            passwordSettingAdmin = swPassword.isChecked() ? 1 : 0;
            getTotalBalanceInActivities = swTotal.isChecked() ? 1 : 0;
            voucherReturn_spreat = swReturn.isChecked() ? 1 : 0;
            if(flag_settingsList.size()==0)
            {
               databaseHandler.insertFlagSettings(new Flag_Settingss(dataType1, rawahneh, getMaxVoucherServer,
                        makeOrders, passwordSettingAdmin, getTotalBalanceInActivities, voucherReturn_spreat));
            }else {
                databaseHandler.updateFlagSettings(dataType1, rawahneh, getMaxVoucherServer,
                        makeOrders, passwordSettingAdmin, getTotalBalanceInActivities, voucherReturn_spreat);
            }




            moreDialog.dismiss();

        });

        cancelBtn.setOnClickListener(v12 -> moreDialog.dismiss());
    }
}
