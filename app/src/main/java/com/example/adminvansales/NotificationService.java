package com.example.adminvansales;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("onBind","onCreate");
     return  null;
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy","onCreate");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String val=intent.getStringExtra("extraService");
        Log.e("onBind",""+val);
//     startForeground();
        return  START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e("onCreate","onCreate");
        super.onCreate();
    }
}
