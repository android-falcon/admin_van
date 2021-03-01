package com.example.adminvansales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.adminvansales.Model.SalesManInfo;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GlobelFunction {
    ImportData importData;
    Context context;
    private Calendar myCalendar;
    public static  List<SalesManInfo> salesManInfosList=new ArrayList<>();
    public static  List<String> salesManNameList=new ArrayList<>();

    public static  List<LatLng> LatLngListMarker=new ArrayList<>();
    
    public static String adminId="",adminName="";
    public GlobelFunction(Context context) {
        importData=new ImportData(context);
        this.context =context;
        myCalendar = Calendar.getInstance();
    }

    public void getSalesManInfo(Context context,int flag){

        if(flag!=90) {
            importData.getSalesMan(context, flag);
        }else {
            importData.getAdmin(context, 0);
        }

    }



    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public  String DateInToday(){

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        return convertToEnglish(today);
    }

    public void DateClick(TextView dateText){

        new DatePickerDialog(context, openDatePickerDialog(dateText), myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final TextView DateText) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                DateText.setText(sdf.format(myCalendar.getTime()));
            }

        };
        return date;
    }

    public void shareWhatsAppA(File pdfFile,int pdfExcel){
try {
    Uri uri = Uri.fromFile(pdfFile);
    Intent sendIntent = new Intent();
    if (pdfFile.exists()) {
        if (pdfExcel == 1) {
            sendIntent.setType("application/excel");
        } else if (pdfExcel == 2) {
            sendIntent.setType("application/pdf");//46.185.208.4
        }
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(uri)));

        sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                pdfFile.getName() + " Sharing File...");
        sendIntent.putExtra(Intent.EXTRA_TEXT, pdfFile.getName() + " Sharing File");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }
}catch (Exception e){
    Toast.makeText(context, "Storage Permission", Toast.LENGTH_SHORT).show();
}

    }

}
