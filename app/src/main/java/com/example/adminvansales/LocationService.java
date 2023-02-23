package com.example.adminvansales;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.itextpdf.xmp.impl.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;

public class LocationService extends Service {
    private static final String TAG = "GPS SERVICE";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 15000;
    private static final float LOCATION_DISTANCE = 10f;
    Context context;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
//            Log.e(TAG, "onLocationChanged: " + location);
//            Log.e(TAG,"getLatitude"+location.getLatitude());
//            Log.e(TAG,"getLongitude"+location.getLongitude());
//            Toast.makeText(context, "getLatitude="+location.getLatitude()+"\tgetLongitude="+location.getLongitude(), Toast.LENGTH_SHORT).show();

//            try
//            {
//                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "App_Settings", 0);
//                AppSettings appSettings = complexPreferences.getObject("App_Settings", AppSettings.class);
//
//                boolean isMobileDataEnabled = Utils.isMobileDataEnabled(context);
//
//                if (appSettings != null && (isMobileDataEnabled)) {
//
//                    LocationItem locationItem = new LocationItem();
//                    locationItem.DeviceID = appSettings.getDeviceID();
//                    locationItem.Latitude =  Double.toString(location.getLatitude());
//                    locationItem.Longitude = Double.toString(location.getLongitude());
//                    Date d = new Date();
//                    CharSequence timeOfRequest = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
//                    locationItem.TimeOfRequest = timeOfRequest.toString();
//                    locationItem.SerialNumber = appSettings.getSerialNumber();
//
//                    mLastLocation.set(location);
//
//                    Gson gson = new Gson();
//                    String requestObject = gson.toJson(locationItem);
//                    Log.d(TAG, "Формирование URL API сервера");
//                    String url = appSettings.getIpAddress() + "/api/staff/savedata";
//                    makeRequest(url, requestObject, dLocation);
//                }
//            }
//            catch (Exception ex)
//            {
//                Log.d(TAG, "Ошибка: " + ex.getMessage());
//            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }


    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        context = this;
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

//    public void makeRequest(String uri, String json, DLocation dLocation) {
//        HandlerThread handlerThread = new HandlerThread("URLConnection");
//        handlerThread.start();
//        Handler mainHandler = new Handler(handlerThread.getLooper());
//        Runnable myRunnable = createRunnable(uri, json, dLocation);
//        mainHandler.post(myRunnable);
//    }

//    private Runnable createRunnable(final String uri, final String data,final DLocation dLocation){
//
//        Runnable aRunnable = new Runnable(){
//            public void run(){
//                try {
//                    //Connect
//                    HttpURLConnection urlConnection;
//                    urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
//                    urlConnection.setDoOutput(true);
//                    urlConnection.setRequestProperty("Content-Type", "application/json");
//                    urlConnection.setRequestProperty("Accept", "application/json");
//                    urlConnection.setRequestMethod("POST");
//
//                    urlConnection.setConnectTimeout(12000);
//                    urlConnection.setReadTimeout(10000);
//
//                    urlConnection.connect();
//
//                    //Write
//                    OutputStream outputStream = urlConnection.getOutputStream();
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    try {
//                        writer.write(data);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Log.d(TAG,"Ошибка записи в буфер для пережачи по HTTP");
//                    }
//                    writer.close();
//                    outputStream.close();
//
//                    //Read
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//
//                    String line = null;
//                    StringBuilder sb = new StringBuilder();
//
//                    while ((line = bufferedReader.readLine()) != null) {
//                        sb.append(line);
//                    }
//
//                    bufferedReader.close();
//                    String result = sb.toString();
//
//                    Log.d(TAG, result);
//
//                }
//                catch (SocketTimeoutException e){
//                    e.printStackTrace();
//
//                    Log.d(TAG, "Ошибка HTTP " + e.getMessage());
//                }
//                catch( Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        return aRunnable;
//
//    }

}

