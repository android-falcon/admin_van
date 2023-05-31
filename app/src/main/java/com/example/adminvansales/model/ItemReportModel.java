package com.example.adminvansales.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ItemReportModel implements Comparable{
//            "ComapnyNo": "0",
//            "VoucherYear": "2021",
//            "VoucherNo": "1650228",
//            "VoucherType": "504",
//            "ItemNo": "10100002",
//            "Unit": "1",
//            "Qty": "5",
//            "Bonus": "0",
//            "UnitPrice": "2",
//            "ItemDiscountValue": "0",
//            "ItemDiscountPrc": "0",
//            "VoucherDiscount": "0",
//            "TaxValue": "1.37931",
//            "TaxPercent": "16",
//            "ISPOSTED": "0",
//            "ITEM_DESCRITION": "",
//            "SERIAL_CODE": null,
//            "ITEM_SERIAL_CODE": null,
//            "total": "10",
//            "SalesManNo": "00001",
//            "Name": "ORANGE E-CHARGE",
//            "salesName": "AHMADq",
//            "VoucherDate": "02/01/2021"

    public static DateFormat primaryFormat = new SimpleDateFormat("h:mm a");
    public static DateFormat secondaryFormat = new SimpleDateFormat("H:mm");
    @SerializedName("COMAPNYNO")
    private String ComapnyNo;
    @SerializedName("VOUCHERYEAR")
    private String VoucherYear;
    @SerializedName("VOUCHERNO")

    private String VoucherNo;
    @SerializedName("VOUCHERTYPE")
    private String VoucherType;
    @SerializedName("ITEMNO")
    private String ItemNo;
    @SerializedName("UNIT")
    private String Unit;
    @SerializedName("QTY")
    private String Qty;
    @SerializedName("BONUS")
    private String Bonus;
    @SerializedName("UNITPRICE")
    private String UnitPrice;
    @SerializedName("ITEMDISCOUNTVALUE")
    private String ItemDiscountValue;
    @SerializedName("ITEMDISCOUNTPRC")
    private String ItemDiscountPrc;
    @SerializedName("VOUCHERDISCOUNT")
    private String VoucherDiscount;
    @SerializedName("TAXVALUE")
    private String TaxValue;
    @SerializedName("TAXPERCENT")
    private String TaxPercent;
    @SerializedName("ISPOSTED")
    private String ISPOSTED;


    @SerializedName("ITEM_DESCRITION")
    private String ITEM_DESCRITION;
    @SerializedName("SERIAL_CODE")
    private String SERIAL_CODE;
    @SerializedName("ITEM_SERIAL_CODE")
    private String ITEM_SERIAL_CODE;


    @SerializedName("TOTAL")
    private String total;
    @SerializedName("SALESMANNO")
    private String SalesManNo;
    @SerializedName("NAME")
    private String Name;

    @SerializedName("SALESNAME")
    private String salesName;
    @SerializedName("VOUCHERDATE")
    private String VoucherDate;


    private List<ItemReportModel> itemReport;


    public ItemReportModel() {

    }

    public ItemReportModel(String comapnyNo, String voucherYear, String voucherNo, String voucherType, String itemNo
            , String unit, String qty, String bonus, String unitPrice, String itemDiscountValue, String itemDiscountPrc
            , String voucherDiscount, String taxValue, String taxPercent, String ISPOSTED, String ITEM_DESCRITION, String SERIAL_CODE
            , String ITEM_SERIAL_CODE, String total, String salesManNo, String name, String salesName, String voucherDate) {
        ComapnyNo = comapnyNo;
        VoucherYear = voucherYear;
        VoucherNo = voucherNo;
        VoucherType = voucherType;
        ItemNo = itemNo;
        Unit = unit;
        Qty = qty;
        Bonus = bonus;
        UnitPrice = unitPrice;
        ItemDiscountValue = itemDiscountValue;
        ItemDiscountPrc = itemDiscountPrc;
        VoucherDiscount = voucherDiscount;
        TaxValue = taxValue;
        TaxPercent = taxPercent;
        this.ISPOSTED = ISPOSTED;
        this.ITEM_DESCRITION = ITEM_DESCRITION;
        this.SERIAL_CODE = SERIAL_CODE;
        this.ITEM_SERIAL_CODE = ITEM_SERIAL_CODE;
        this.total = total;
        SalesManNo = salesManNo;
        Name = name;
        this.salesName = salesName;
        VoucherDate = voucherDate;
    }

    public String getComapnyNo() {
        return ComapnyNo;
    }

    public void setComapnyNo(String comapnyNo) {
        ComapnyNo = comapnyNo;
    }

    public String getVoucherYear() {
        return VoucherYear;
    }

    public void setVoucherYear(String voucherYear) {
        VoucherYear = voucherYear;
    }

    public String getVoucherNo() {
        return VoucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        VoucherNo = voucherNo;
    }

    public String getVoucherType() {
        return VoucherType;
    }

    public void setVoucherType(String voucherType) {
        VoucherType = voucherType;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getBonus() {
        return Bonus;
    }

    public void setBonus(String bonus) {
        Bonus = bonus;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getItemDiscountValue() {
        return ItemDiscountValue;
    }

    public void setItemDiscountValue(String itemDiscountValue) {
        ItemDiscountValue = itemDiscountValue;
    }

    public String getItemDiscountPrc() {
        return ItemDiscountPrc;
    }

    public void setItemDiscountPrc(String itemDiscountPrc) {
        ItemDiscountPrc = itemDiscountPrc;
    }

    public String getVoucherDiscount() {
        return VoucherDiscount;
    }

    public void setVoucherDiscount(String voucherDiscount) {
        VoucherDiscount = voucherDiscount;
    }

    public String getTaxValue() {
        return TaxValue;
    }

    public void setTaxValue(String taxValue) {
        TaxValue = taxValue;
    }

    public String getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(String taxPercent) {
        TaxPercent = taxPercent;
    }

    public String getISPOSTED() {
        return ISPOSTED;
    }

    public void setISPOSTED(String ISPOSTED) {
        this.ISPOSTED = ISPOSTED;
    }

    public String getITEM_DESCRITION() {
        return ITEM_DESCRITION;
    }

    public void setITEM_DESCRITION(String ITEM_DESCRITION) {
        this.ITEM_DESCRITION = ITEM_DESCRITION;
    }

    public String getSERIAL_CODE() {
        return SERIAL_CODE;
    }

    public void setSERIAL_CODE(String SERIAL_CODE) {
        this.SERIAL_CODE = SERIAL_CODE;
    }

    public String getITEM_SERIAL_CODE() {
        return ITEM_SERIAL_CODE;
    }

    public void setITEM_SERIAL_CODE(String ITEM_SERIAL_CODE) {
        this.ITEM_SERIAL_CODE = ITEM_SERIAL_CODE;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSalesManNo() {
        return SalesManNo;
    }

    public void setSalesManNo(String salesManNo) {
        SalesManNo = salesManNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getVoucherDate() {
        return VoucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        VoucherDate = voucherDate;
    }

    public List<ItemReportModel> getItemReport() {
        return itemReport;
    }

    public void setItemReport(List<ItemReportModel> itemReport) {
        this.itemReport = itemReport;
    }
    public  static Comparator<ItemReportModel> comparedate=new Comparator<ItemReportModel>() {
        @Override
        public int compare(ItemReportModel model, ItemReportModel t1) {
            Log.e("comparedate","comparedate");
            return (dateInMillis(model.getVoucherDate()) - dateInMillis(t1.getVoucherDate()));
        }
    };
    public  static Comparator<ItemReportModel> compareSaleManName=new Comparator<ItemReportModel>() {
        @Override
        public int compare(ItemReportModel model, ItemReportModel t1) {
            Log.e("comparedate","comparedate");
            return ((model.getSalesName()).compareTo( t1.getSalesName()));
        }
    };
    public  static Comparator<ItemReportModel> compareITEMNAME=new Comparator<ItemReportModel>() {
        @Override
        public int compare(ItemReportModel model, ItemReportModel t1) {
            Log.e("comparedate","comparedate");
            return ((model.getName()).compareTo( t1.getName()));
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

    Comparator<ItemReportModel> compareBy
            = Comparator.comparing(ItemReportModel::getVoucherDate)
            .thenComparing(ItemReportModel::getSalesName)
            .thenComparing(ItemReportModel::getName);
}
