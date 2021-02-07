package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminvansales.Model.SettingModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import static com.example.adminvansales.GlobelFunction.adminId;
import static com.example.adminvansales.GlobelFunction.adminName;

public class LogIn extends AppCompatActivity {
    SliderLayout sliderLayout;
    FloatingActionButton setting_floatingBtn;
    Button button_logIn,button_sighnup;
    public  static String ipAddress="",portSettings="";
    SettingModel settingModelList;
    private DataBaseHandler databaseHandler;
    EditText userName_edit,password_edit;
    GlobelFunction globelFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(4); //set scroll delay in seconds :
        setSliderViews();
    }
    private void initView() {
        sliderLayout = findViewById(R.id.imageSlider_2);
        setting_floatingBtn=findViewById(R.id.setting_floatingBtn);
        databaseHandler = new DataBaseHandler(LogIn.this);
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

        globelFunction=new GlobelFunction(LogIn.this);
        ImportData importData=new ImportData(LogIn.this);
        importData.getListRequest();
        Log.e("importData","11111");
        importData.getCustomerInfo();

        button_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userName_edit.getText().toString().equals("admin"))
                {
                    if(password_edit.getText().toString().equals("100"))
                    {
                        goToMain();
                    }
                    else {
                        password_edit.setError("Required");
                    }
                }
                else {
                    userName_edit.setError("Required");
                }

//

//
            }
        });


    }
    private void goToMain() {
        adminName=userName_edit.getText().toString();
        adminId=password_edit.getText().toString();
        finish();
        Intent i = new Intent(LogIn.this, HomeActivity.class);
        startActivity(i);
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
        Button saveSetting=dialog.findViewById(R.id.saveSetting);
        final EditText editTextIp=dialog.findViewById(R.id.setindEditText);
        final EditText  portSetting=dialog.findViewById(R.id.portSetting);
        ipAddress="";
        portSettings="";
        SettingModel settingModels=new SettingModel();
        try {

           settingModels =databaseHandler.getAllSetting();
           ipAddress=settingModels.getIpAddress();
            if(!ipAddress.equals(""))
            {
                portSettings=settingModels.getPort();
                editTextIp.setText(ipAddress);
                portSetting.setText(portSettings);
            }
        }
        catch (Exception e)
        {

        }
        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipAddress= editTextIp.getText().toString();
                portSettings=portSetting.getText().toString();
                databaseHandler.addSetting(ipAddress,portSettings);
                dialog.dismiss();

            }
        });
        dialog.show();

    }
}
