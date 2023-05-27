package com.example.adminvansales.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
    public  static Comparator<PayMentReportModel> comparedate=new Comparator<PayMentReportModel>() {
        @Override
        public int compare(PayMentReportModel model, PayMentReportModel t1) {
            Log.e("comparedate","comparedate");
            return (dateInMillis(model.getPaymentDate()) - dateInMillis(t1.getPaymentDate()));
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
}
