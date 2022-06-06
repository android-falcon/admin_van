package com.example.adminvansales.model;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.example.adminvansales.DataBaseHandler;

import java.util.Locale;

import static com.example.adminvansales.LogIn.contextG;
import static com.example.adminvansales.LogIn.languagelocalApp;

public class LocaleAppUtils {
    private static Locale locale;
    private static DataBaseHandler mHandler;

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        if(locale != null) {
            Locale.setDefault(locale);
        }
    }
    public static void setConfigChange(Context ctx){
        if(locale != null){
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale=locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }
    public static void changeLayot(Context context){
        mHandler = new DataBaseHandler(context);
        contextG=context;
        if (mHandler.getFlagSettings().size() != 0) {
            if (mHandler.getFlagSettings().get(0).getArabic_language() == 0) {
                languagelocalApp="ar";
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(context);

            } else {
                languagelocalApp="en";
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(context);

            }
        }
        else {
            languagelocalApp="ar";
            LocaleAppUtils.setLocale(new Locale("ar"));
            LocaleAppUtils.setConfigChange(context);

        }



    }
}
