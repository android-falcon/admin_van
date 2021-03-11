package com.example.adminvansales.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemReportModel {
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

    @SerializedName("ComapnyNo")
    private String ComapnyNo;
    @SerializedName("VoucherYear")
    private String VoucherYear;
    @SerializedName("VoucherNo")

    private String VoucherNo;
    @SerializedName("VoucherType")
    private String VoucherType;
    @SerializedName("ItemNo")
    private String ItemNo;
    @SerializedName("Unit")
    private String Unit;
    @SerializedName("Qty")
    private String Qty;
    @SerializedName("Bonus")
    private String Bonus;
    @SerializedName("UnitPrice")
    private String UnitPrice;
    @SerializedName("ItemDiscountValue")
    private String ItemDiscountValue;
    @SerializedName("ItemDiscountPrc")
    private String ItemDiscountPrc;
    @SerializedName("VoucherDiscount")
    private String VoucherDiscount;
    @SerializedName("TaxValue")
    private String TaxValue;
    @SerializedName("TaxPercent")
    private String TaxPercent;
    @SerializedName("ISPOSTED")
    private String ISPOSTED;


    @SerializedName("ITEM_DESCRITION")
    private String ITEM_DESCRITION;
    @SerializedName("SERIAL_CODE")
    private String SERIAL_CODE;
    @SerializedName("ITEM_SERIAL_CODE")
    private String ITEM_SERIAL_CODE;


    @SerializedName("total")
    private String total;
    @SerializedName("SalesManNo")
    private String SalesManNo;
    @SerializedName("Name")
    private String Name;

    @SerializedName("salesName")
    private String salesName;
    @SerializedName("VoucherDate")
    private String VoucherDate;

    @SerializedName("itemReport")
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
}
