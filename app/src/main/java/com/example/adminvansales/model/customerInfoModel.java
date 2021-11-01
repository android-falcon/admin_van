package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class customerInfoModel implements Serializable {
// "ComapnyNo": "291",
//            "CustID": "1110010002",
//            "CustName": "المعلم بلال",
//            "Address": "",
//            "IsSuspended": "0",
//            "PriceListID": "3",
//            "CashCredit": "-19500",
//            "SalesManNo": "00001",
//            "CreditLimit": "0",
//            "PAYMETHOD": "1",
//            "LATITUDE": "",
//            "LONGITUDE": "",
//            "MAXDISC": "0",
//            "ACCPRC": "3",
//            "HIDE_VAL": "0"
    @SerializedName("ComapnyNo")
    private String ComapnyNo;

    @SerializedName("CustID")
    private String CustID;

    @SerializedName("CustName")
    private String CustName;

    @SerializedName("Address")
    private String Address;


    @SerializedName("IsSuspended")
    private String IsSuspended;

    @SerializedName("PriceListID")
    private String PriceListID;

    @SerializedName("CashCredit")
    private String CashCredit;

    @SerializedName("SalesManNo")
    private String SalesManNo;

    @SerializedName("CreditLimit")
    private String CreditLimit;

    @SerializedName("PAYMETHOD")
    private String PAYMETHOD;

    @SerializedName("LATITUDE")
    private String LATITUDE;

    @SerializedName("LONGITUDE")
    private String LONGITUDE;


    @SerializedName("MAXDISC")
    private String MAXDISC;


    @SerializedName("ACCPRC")
    private String ACCPRC;


    @SerializedName("HIDE_VAL")
    private String HIDE_VAL;

    @SerializedName("ALL_CUSTOMER")
    private List<customerInfoModel> ALL_CUSTOMER;

    private  boolean checkedItem ;

    public customerInfoModel(String custID, String custName) {
        CustID = custID;
        CustName = custName;
    }
    public customerInfoModel() {

    }

    public customerInfoModel(String comapnyNo, String custID, String custName, String address, String isSuspended,
                             String priceListID, String cashCredit, String salesManNo, String creditLimit, String PAYMETHOD,
                             String LATITUDE, String LONGITUDE, String MAXDISC, String ACCPRC, String HIDE_VAL,
                             List<customerInfoModel> ALL_CUSTOMER) {
        this.ComapnyNo = comapnyNo;
        this.CustID = custID;
        this.CustName = custName;
        this.Address = address;
        this.IsSuspended = isSuspended;
        this.PriceListID = priceListID;
        this.CashCredit = cashCredit;
        this.SalesManNo = salesManNo;
        this.CreditLimit = creditLimit;
        this.PAYMETHOD = PAYMETHOD;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.MAXDISC = MAXDISC;
        this.ACCPRC = ACCPRC;
        this.HIDE_VAL = HIDE_VAL;
        this.ALL_CUSTOMER = ALL_CUSTOMER;
    }

    public String getComapnyNo() {
        return ComapnyNo;
    }

    public void setComapnyNo(String comapnyNo) {
        ComapnyNo = comapnyNo;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        CustID = custID;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIsSuspended() {
        return IsSuspended;
    }

    public void setIsSuspended(String isSuspended) {
        IsSuspended = isSuspended;
    }

    public String getPriceListID() {
        return PriceListID;
    }

    public void setPriceListID(String priceListID) {
        PriceListID = priceListID;
    }

    public String getCashCredit() {
        return CashCredit;
    }

    public void setCashCredit(String cashCredit) {
        CashCredit = cashCredit;
    }

    public String getSalesManNo() {
        return SalesManNo;
    }

    public void setSalesManNo(String salesManNo) {
        SalesManNo = salesManNo;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getPAYMETHOD() {
        return PAYMETHOD;
    }

    public void setPAYMETHOD(String PAYMETHOD) {
        this.PAYMETHOD = PAYMETHOD;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getMAXDISC() {
        return MAXDISC;
    }

    public void setMAXDISC(String MAXDISC) {
        this.MAXDISC = MAXDISC;
    }

    public String getACCPRC() {
        return ACCPRC;
    }

    public void setACCPRC(String ACCPRC) {
        this.ACCPRC = ACCPRC;
    }

    public String getHIDE_VAL() {
        return HIDE_VAL;
    }

    public void setHIDE_VAL(String HIDE_VAL) {
        this.HIDE_VAL = HIDE_VAL;
    }

    public List<customerInfoModel> getALL_CUSTOMER() {
        return ALL_CUSTOMER;
    }

    public void setALL_CUSTOMER(List<customerInfoModel> ALL_CUSTOMER) {
        this.ALL_CUSTOMER = ALL_CUSTOMER;
    }

    public boolean isCheckedItem() {
        return checkedItem;
    }

    public void setCheckedItem(boolean checkedItem) {
        this.checkedItem = checkedItem;
    }


    public JSONObject getJsonObjectList(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("CUST_ID", CustID);
            jsonObject.put("CUST_NAME", CustName);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
