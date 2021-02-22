package com.example.adminvansales;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class notificationReciver extends BroadcastReceiver {
    private static final String YES_ACTION = "com.example.adminvansales";
    String action = "", extra = "", stateAction = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        action = intent.getAction();
        if (action != null) {
            stateAction = action;

        }
        Log.e("getAction", "" + action);
        extra = intent.getStringExtra("action");
        Log.e("action==", "" + extra);
        if (extra != null) {
            stateAction = extra;
        }
        if (stateAction != null) {
            if (stateAction.equals("YES")) {
                boolean result = isAppOnForeground(context, "com.example.adminvansales");
                if (result) {
                    Intent i = new Intent(context, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    try {
                        // Perform the operation associated with our pendingIntent
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent1 = new Intent(context, LogIn.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    try {
                        // Perform the operation associated with our pendingIntent
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }

                }

            } else {

                Log.e("notificationReciver", "else \t " + action);
            }
        }

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);

    }

    private boolean isAppOnForeground(Context context, String appPackageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = appPackageName;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {

                return true;
            }
        }
        return false;
    }
}
