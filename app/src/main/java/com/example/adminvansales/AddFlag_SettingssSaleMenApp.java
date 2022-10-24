package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adminvansales.model.Flag_SettingssSaleMenApp;

public class AddFlag_SettingssSaleMenApp extends AppCompatActivity {
    Switch swExport, swMax, swOrder, swPassword, swTotal, swReturn,plan,Trips,activePos,
            cakeshopSW, qasionSW, talaatSW,maxvochServer,purchaseOrder_switch,noTax_Sw;
    public       int passwordSettingAdmin=0;
    ImageButton cakeshopInfo, qasionInfo, talaatInfo;
    TextView cakeExpandedText, qasionExpandedText, talaatExpandedText;
    Button cancelBtn,okBtn;
    LinearLayout plan_linear;
    RadioGroup radioGrpData;
    public   int OfferCakeShop=0;// if 0 calck offer many times
    public   int  DayofweekPlan=1;// 1 if saleman plan based on day of week ,, 0 if saleman plan based on date
    public      int  offerTalaat=0;
    public      int  offerQasion=0;
    public   int getMaxVoucherServer=0;
    public      int   SalsManPlanFlage=0;
    public      int   SalsManTripFlage=0;
    public      int   POS_ACTIVE=0;
    public      int   Plan_ACTIVE=1;
    public   int makeOrders=0;// 1= just orders app
    public    int gone_noTax_totalDisc=0;
    public  int getTotalBalanceInActivities=1;
    public   int typaImport=1;//0---- mySql   1-----IIs
    public   int Purchase_Order=0;
    public   int rawahneh=0;// 1= EXPORT STOCK TABLES
    public   int rawahneh_getMaxVouchFromServer =0;
    public      int  voucherReturn_spreat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flag_settingss_sale_men_app);


         okBtn = findViewById(R.id.okBtn);
         plan_linear= findViewById(R.id.plan_linear);

         cancelBtn = findViewById(R.id.cancelBtn);
cancelBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        cleartext();
        startActivity(new Intent(AddFlag_SettingssSaleMenApp.this,HomeActivity.class));
    }
});
       radioGrpData = findViewById(R.id.radioGrpData);




        noTax_Sw=findViewById(R.id.noTax);

        purchaseOrder_switch=findViewById(R.id.purchaseOrder_switch);
//        purchaseOrder_switch.setVisibility(View.GONE);
        swExport = findViewById(R.id.swExport);
        swMax = findViewById(R.id.swMax);
        maxvochServer= findViewById(R.id.maxvochServer);
        swOrder = findViewById(R.id.swOrder);
        swPassword = findViewById(R.id.swPassword);
        swTotal = findViewById(R.id.swTotal);
        swReturn = findViewById(R.id.swReturn);
        plan = findViewById(R.id.SalsManPlan);
        Trips= findViewById(R.id.SalsManTrips);

        activePos= findViewById(R.id.activePos);
        cakeshopSW = findViewById(R.id.cakeshopSW);
        qasionSW = findViewById(R.id.qasionSW);
        talaatSW = findViewById(R.id.talaatSW);
        cakeshopInfo = findViewById(R.id.cakeshopInfo);
        qasionInfo = findViewById(R.id.qasionInfo);
        talaatInfo = findViewById(R.id.talaatInfo);
        cakeExpandedText = findViewById(R.id.cakeExpandedText);
        qasionExpandedText = findViewById(R.id.qasionExpandedText);
        talaatExpandedText = findViewById(R.id.talaatExpandedText);






        View.OnClickListener infoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.cakeshopInfo) {

                    if (cakeExpandedText.getVisibility() == View.GONE)
                        cakeExpandedText.setVisibility(View.VISIBLE);
                    else
                        cakeExpandedText.setVisibility(View.GONE);

                } else if (v.getId() == R.id.talaatInfo) {

                    if (talaatExpandedText.getVisibility() == View.GONE)
                        talaatExpandedText.setVisibility(View.VISIBLE);
                    else
                        talaatExpandedText.setVisibility(View.GONE);

                } else if (v.getId() == R.id.qasionInfo) {

                    if (qasionExpandedText.getVisibility() == View.GONE)
                        qasionExpandedText.setVisibility(View.VISIBLE);
                    else
                        qasionExpandedText.setVisibility(View.GONE);

                }

            }
        };

        cakeshopInfo.setOnClickListener(infoClickListener);
        talaatInfo.setOnClickListener(infoClickListener);
        qasionInfo.setOnClickListener(infoClickListener);

        okBtn.setOnClickListener(v1 -> {
            Log.e("okBtn", "okBtn");
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

            gone_noTax_totalDisc=noTax_Sw.isChecked()?1:0;
            Purchase_Order=purchaseOrder_switch.isChecked()?1:0;
            rawahneh = swExport.isChecked() ? 1 : 0;
            getMaxVoucherServer = swMax.isChecked() ? 1 : 0;
            rawahneh_getMaxVouchFromServer = maxvochServer.isChecked() ? 1 : 0;
            makeOrders = swOrder.isChecked() ? 1 : 0;
            passwordSettingAdmin = swPassword.isChecked() ? 1 : 0;
            getTotalBalanceInActivities = swTotal.isChecked() ? 1 : 0;
            voucherReturn_spreat = swReturn.isChecked() ? 1 : 0;
            SalsManPlanFlage = plan.isChecked() ? 1 : 0;
            SalsManTripFlage= Trips.isChecked() ? 1 : 0;
            POS_ACTIVE = activePos.isChecked() ? 1 : 0;
            OfferCakeShop = cakeshopSW.isChecked() ? 1 : 0;
            offerQasion = qasionSW.isChecked() ? 1 : 0;
            offerTalaat = talaatSW.isChecked() ? 1 : 0;
            Flag_SettingssSaleMenApp flag_settingssSaleMenApp=new Flag_SettingssSaleMenApp();

            flag_settingssSaleMenApp.setData_Type(dataType1);
            flag_settingssSaleMenApp.setExport_Stock(rawahneh);
            flag_settingssSaleMenApp.setMax_Voucher(getMaxVoucherServer);
            flag_settingssSaleMenApp.setMake_Order(makeOrders);
            flag_settingssSaleMenApp.setAdmin_Password(passwordSettingAdmin);


            flag_settingssSaleMenApp.setTotal_Balance(getTotalBalanceInActivities);
            flag_settingssSaleMenApp.setVoucher_Return(voucherReturn_spreat);
            flag_settingssSaleMenApp.setActiveSlasmanPlan(SalsManPlanFlage);
            flag_settingssSaleMenApp.setPos_active(POS_ACTIVE);


            flag_settingssSaleMenApp.setOfferCakeShop(OfferCakeShop);
            flag_settingssSaleMenApp.setOfferTalaat(offerTalaat);
            flag_settingssSaleMenApp.setOfferQasion(offerQasion);
            flag_settingssSaleMenApp.setMaxvochServer(rawahneh_getMaxVouchFromServer);
            flag_settingssSaleMenApp.setPurchaseOrder(Purchase_Order);
            flag_settingssSaleMenApp.setNoTax(gone_noTax_totalDisc);

//
//                        OfferCakeShop, offerTalaat,
//                        offerQasion,SalsManTripFlage,
//                        rawahneh_getMaxVouchFromServer,
//                        Purchase_Order,gone_noTax_totalDisc);






        });

    }
    View.OnClickListener infoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.cakeshopInfo) {

                if (cakeExpandedText.getVisibility() == View.GONE)
                    cakeExpandedText.setVisibility(View.VISIBLE);
                else
                    cakeExpandedText.setVisibility(View.GONE);

            } else if (v.getId() == R.id.talaatInfo) {

                if (talaatExpandedText.getVisibility() == View.GONE)
                    talaatExpandedText.setVisibility(View.VISIBLE);
                else
                    talaatExpandedText.setVisibility(View.GONE);

            } else if (v.getId() == R.id.qasionInfo) {

                if (qasionExpandedText.getVisibility() == View.GONE)
                    qasionExpandedText.setVisibility(View.VISIBLE);
                else
                    qasionExpandedText.setVisibility(View.GONE);

            }

        }
    };
   void cleartext() {

    }
}