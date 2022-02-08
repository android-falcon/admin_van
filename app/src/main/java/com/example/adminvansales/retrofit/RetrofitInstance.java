package com.example.adminvansales.retrofit;

import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.LogIn.portSettings;

import android.content.Context;
import android.util.Log;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.model.SettingModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit ourInstance;
    static String BASEURL="http://46.185.161.254:8085/Falcons/VAN.dll/";

    public static Retrofit getInstance(String BASE_URL) {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }
//
    private RetrofitInstance() {

    }

}
