package com.example.adminvansales.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PayMentReportModel implements Comparable {

    public static DateFormat primaryFormat = new SimpleDateFormat("h:mm a");
    public static DateFormat secondaryFormat = new SimpleDateFormat("H:mm");
    @SerializedName("ComapnyNo")
    private String ComapnyNo;

    @SerializedName("VouYear")
    private String VouYear;

    @SerializedName("VouNo")
    private String VouNo;

    @SerializedName("PaymentDate")
    private String PaymentDate;

    @SerializedName("CustomerNo")
    private String CustomerNo;

    @SerializedName("Amount")
    private String Amount;

    @SerializedName("Notes")
    private String Notes;

    @SerializedName("SalesmanNo")
    private String SalesmanNo;

    @SerializedName("ISPOSTED")
    private String ISPOSTED;

    @SerializedName("PAYKIND")
    private String PAYKIND;

    @SerializedName("Salesmanname")
    private String Salesmanname;

    @SerializedName("PaymentsTable")
    private List<PayMentReportModel> PaymentsTable;
    public PayMentReportModel(){}
    public PayMentReportModel(String comapnyNo, String vouYear, String vouNo, String paymentDate, String customerNo, String amount,
                              String notes, String salesmanNo, String ISPOSTED, String PAYKIND, String salesmanname) {
        ComapnyNo = comapnyNo;
        VouYear = vouYear;
        VouNo = vouNo;
        PaymentDate = paymentDate;
        CustomerNo = customerNo;
        Amount = amount;
        Notes = notes;
        SalesmanNo = salesmanNo;
        this.ISPOSTED = ISPOSTED;
        this.PAYKIND = PAYKIND;
        Salesmanname = salesmanname;
    }

    public String getComapnyNo() {
        return ComapnyNo;
    }

    public void setComapnyNo(String comapnyNo) {
        ComapnyNo = comapnyNo;
    }

    public String getVouYear() {
        return VouYear;
    }

    public void setVouYear(String vouYear) {
        VouYear = vouYear;
    }

    public String getVouNo() {
        return VouNo;
    }

    public void setVouNo(String vouNo) {
        VouNo = vouNo;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getSalesmanNo() {
        return SalesmanNo;
    }

    public void setSalesmanNo(String salesmanNo) {
        SalesmanNo = salesmanNo;
    }

    public String getISPOSTED() {
        return ISPOSTED;
    }

    public void setISPOSTED(String ISPOSTED) {
        this.ISPOSTED = ISPOSTED;
    }

    public String getPAYKIND() {
        return PAYKIND;
    }

    public void setPAYKIND(String PAYKIND) {
        this.PAYKIND = PAYKIND;
    }

    public String getSalesmanname() {
        return Salesmanname;
    }

    public void setSalesmanname(String salesmanname) {
        Salesmanname = salesmanname;
    }

    public List<PayMentReportModel> getPaymentsTable() {
        return PaymentsTable;
    }

    public void setPaymentsTable(List<PayMentReportModel> paymentsTable) {
        PaymentsTable = paymentsTable;
    }
//    public  static Comparator<PayMentReportModel> comparedate=new Comparator<PayMentReportModel>() {
//        @Override
//        public int compare(PayMentReportModel model, PayMentReportModel t1) {
//
//            return (dateInMillis(model.getPaymentDate()) - dateInMillis(t1.getPaymentDate()));
//        }
//    };

    public  static Comparator<PayMentReportModel> comparedate=new Comparator<PayMentReportModel>() {
        @Override
        public int compare(PayMentReportModel model, PayMentReportModel t1) {



            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date parse1 = sdf.parse(model.getPaymentDate());
                Date parse2 = sdf.parse(t1.getPaymentDate());
                Calendar c = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                c.setTime(parse1);
                c2.setTime(parse2);
                String str1=  String.valueOf(c.get(Calendar.YEAR))+String.valueOf(c.get(Calendar.MONTH))+String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                String str2=  String.valueOf(c2.get(Calendar.YEAR))+String.valueOf(c2.get(Calendar.MONTH))+String.valueOf(c2.get(Calendar.DAY_OF_MONTH));
             Log.e("comparedate","  "+c.get(Calendar.MONTH) +"  "+ c.get(Calendar.DATE) +" "+ c.get(Calendar.YEAR));

                Log.e("diff==","  "+ (Integer.parseInt(str2)-Integer.parseInt(str1)));

                return Integer.parseInt(str2)-Integer.parseInt(str1)  ;
            }
           catch ( Exception exception){
               Log.e("exception==",exception.getMessage());
               return 0;
           }

             // Log.e("comparedate","comparedate=="+"  " +date.getYear()+"  "+date.getMonthOfYear()+" "+date.getDayOfMonth());



        }
    };

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public static  int dateInMillis(String time){
        return dateInMillis(time, primaryFormat);
    }
    private static int dateInMillis(String time, DateFormat format) {
        // you may need more advanced logic here when parsing the time if some times have am/pm and others don't.
        try{
            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(time);
            //Date date = format.parse(time);
            return (int)date.getDate();
        }catch(ParseException e){
            try{
                if(format != secondaryFormat){
                    return timeInMillis(time, secondaryFormat);
                }else{
                    throw e;
                }
            }catch(ParseException ce){


                Log.e("ParseException","ParseException"+ce.getMessage());
            }
        }
        return 1;}
    private static int timeInMillis(String time, DateFormat format) {
        // you may need more advanced logic here when parsing the time if some times have am/pm and others don't.
        try{
            Date date = format.parse(time);
            return (int)date.getTime();
        }catch(ParseException e){
            try{
                if(format != secondaryFormat){
                    return timeInMillis(time, secondaryFormat);
                }else{
                    throw e;
                }
            }catch(ParseException ce){}
        }
        return 1;}
    public static String reverse(String str)
    {
        // return if the string is null or empty
        if (str == null || str.equals("")) {
            return str;
        }

        // create an empty list of characters and push every
        // character of the given string into it
        List<Character> list = new ArrayList<Character>();
        for (char c: str.toCharArray()) {
            list.add(c);
        }

        // reverse list using `java.util.Collections` `reverse()`
        Collections.reverse(list);

        // list.toString() is [t, h, g, i, l, e, D,  , e, i, h, c, e, T]

        // convert `list.toString()` to valid string
        return list.toString()
                .replaceAll("[,\\[\\]]", "")    // t h g i l e D   e i h c e T
                .replaceAll("  ", "@")          // t h g i l e D@ e i h c e T
                .replaceAll(" ", "")            // thgileD@eihceT
                .replaceAll("@", " ");          // !em esreveR
    }

}
