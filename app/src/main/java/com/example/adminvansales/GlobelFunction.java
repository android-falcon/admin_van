package com.example.adminvansales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.model.LocaleAppUtils;
import com.example.adminvansales.model.SalesManInfo;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GlobelFunction {
    ImportData importData;
    Context context;
    private Calendar myCalendar;
    public static  List<SalesManInfo> salesManInfosList=new ArrayList<>();
    public static  List<String> salesManNameList=new ArrayList<>();

    public static  SalesManInfo salesManInfoAdmin=new SalesManInfo();

    public static  List<LatLng> LatLngListMarker=new ArrayList<>();
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel settingModel;
    private DecimalFormat decimalFormat;
    public static String adminId="",adminName="";
    public GlobelFunction(Context context) {
        importData=new ImportData(context);
        this.context =context;
        myCalendar = Calendar.getInstance();
        decimalFormat = new DecimalFormat("00.0000");

    }
    public static void showSweetDialog(Context context, int type, String title, String content) {
        switch (type) {
            case 0://Error Type
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;
            case 1://Success Type
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;
            case 3://warning Type
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;

        }
    }

    public void getSalesManInfo(Context context, int flag) {

        Log.e("getSalesManInfo", "flag=" + flag);
        if (flag != 90) {
            settingModel = new com.example.adminvansales.model.SettingModel();
            databaseHandler = new DataBaseHandler(context);
            settingModel = databaseHandler.getAllSetting();
            Log.e("getSalesManInfo", "getImport_way=" + settingModel.getImport_way());
            if (settingModel.getImport_way().equals("0"))
                importData.getSalesMan(context, flag);
            else if (settingModel.getImport_way().equals("1"))
                importData.IIs_getSalesMan(context, flag);
        } else {
            importData.getAdmin(context, 0);

        }

    }
    public   void setValidation(){
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

        saveValidition(salesManInfo);
    }

    public void saveValidition(SalesManInfo salesManInfo) {
        salesManInfoAdmin.setSalesManNo(salesManInfo.getSalesManNo());
        salesManInfoAdmin.setSalesName(salesManInfo.getSalesName());
        salesManInfoAdmin.setSalesPassword(salesManInfo.getSalesPassword());
        salesManInfoAdmin.setActive(salesManInfo.getActive());
        salesManInfoAdmin.setAddSalesMen(salesManInfo.getAddSalesMen());
        salesManInfoAdmin.setAddAdmin(salesManInfo.getAddAdmin());
        salesManInfoAdmin.setMakeOffer(salesManInfo.getMakeOffer());
        salesManInfoAdmin.setOfferReport(salesManInfo.getOfferReport());
        salesManInfoAdmin.setAccountReport(salesManInfo.getAccountReport());
        salesManInfoAdmin.setPaymentReport(salesManInfo.getPaymentReport());
        salesManInfoAdmin.setCustomerReport(salesManInfo.getCustomerReport());
        salesManInfoAdmin.setCashReport(salesManInfo.getCashReport());
        salesManInfoAdmin.setSalesManLocation(salesManInfo.getSalesManLocation());
        salesManInfoAdmin.setUnCollectChequeReport(salesManInfo.getUnCollectChequeReport());
        salesManInfoAdmin.setAnalyzeCustomer(salesManInfo.getAnalyzeCustomer());
        salesManInfoAdmin.setLogHistoryReport(salesManInfo.getLogHistoryReport());
    }



    public static  String convertToEnglish(String value) {
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

    public  void AuthenticationMessage(){

//        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("You do not have Authority !!!")
//                .setContentText("")
//                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//                    }
//                })
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//
//                    }
//                })
//                .show();

    }

    void updateAutho(){

        ImportData importData = new ImportData(context);
        importData.GetAuthentication(context,adminName,adminId,1);
    }
public String getsalesmanNum(String name){
    Log.e("name==",name);
        for(int i=0;i<salesManInfosList.size();i++){
            if(salesManInfosList.get(i).getSalesName().equals(name)) {
                Log.e("name===",salesManInfosList.get(i).getSalesName());
                Log.e("num===",salesManInfosList.get(i).getSalesManNo());
             return salesManInfosList.get(i).getSalesManNo();

        }}
    return "" ;  }
    public String getsalesmanname(String num){

        for(int i=0;i<salesManInfosList.size();i++){

            Log.e("ManNo===",salesManInfosList.get(i).getSalesManNo()+"   num==="+num);
            if(salesManInfosList.get(i).getSalesManNo().trim().equals(num.trim())) {
                Log.e("name2===",salesManInfosList.get(i).getSalesName());
                Log.e("num3===",salesManInfosList.get(i).getSalesManNo());
                return salesManInfosList.get(i).getSalesName();

            }}
        return "" ;  }
    public  String getDecimal(String number){
        double num=0;
        try {
             num=Double.parseDouble(number);
        }catch (Exception e){num=0;}

       return         decimalFormat.format(num);
    }
}
