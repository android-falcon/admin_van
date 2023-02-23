package com.example.adminvansales.retrofit;

import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.LogIn.portSettings;

import android.content.Context;
import android.util.Log;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.model.SettingModel;

public class ApiUrl {
    private DataBaseHandler databaseHandler;
public  String headerDll="/Falcons/VAN.dll/";
//public  String headerDll="";
    private    String ipAddress="",portSettings="",import_way="",CONO="";
    private static  String BASE_URL = "";
    public Context main_context;
    public  void fillIp(){
        try {
            databaseHandler = new DataBaseHandler(main_context);
            headerDll="";

            SettingModel settingModel = new SettingModel();
            settingModel = databaseHandler.getAllSetting();
            ipAddress = settingModel.getIpAddress();
            if (ipAddress.contains(":")) {
                int ind = ipAddress.indexOf(":");
                ipAddress = ipAddress.substring(0, ind);
                Log.e("ipAddress", "" + ipAddress);
            }
            portSettings = settingModel.getPort();
            portSettings=settingModel.getPort();
            CONO = settingModel.getCono();
            BASE_URL= "http://" + ipAddress +":"+portSettings +headerDll;


        } catch (Exception e) {

        }
    }

   public ApiUrl(Context main_context) {
        this.main_context = main_context;
    }
   public  String getBaseUrl(){
        fillIp();
        return BASE_URL;

    }

}
