package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashReportModel {
//  "SalesManNo": "00100",
//          "SalesManName": "ذمم متجاوزة",
//          "totalCash": null,
//          "totalCredite": null,
//          "PtotalCash": null,
//          "PtotalCredite": null,
//          "PtotalCrediteCard": null

    @SerializedName("SalesManNo")
    private String SalesManNo;

    @SerializedName("SalesManName")
    private String SalesManName;

    @SerializedName("totalCash")
    private String totalCash;

    @SerializedName("totalCredite")
    private String totalCredite;
    @SerializedName("PtotalCash")
    private String PtotalCash;

    @SerializedName("PtotalCredite")
    private String PtotalCredite;

    @SerializedName("PtotalCrediteCard")
    private String PtotalCrediteCard;

//    @SerializedName("netSale")
    private String netSale;

//    @SerializedName("netPay")
    private String netPay;

//    @SerializedName("SalesManNo")
    private String netCash;
    private String   RETURNDCASH;
    private String  RETURNDCREDITE;

    public String getRETURNDCASH() {
        return RETURNDCASH;
    }

    public void setRETURNDCASH(String RETURNDCASH) {
        this.RETURNDCASH = RETURNDCASH;
    }

    public String getRETURNDCREDITE() {
        return RETURNDCREDITE;
    }

    public void setRETURNDCREDITE(String RETURNDCREDITE) {
        this.RETURNDCREDITE = RETURNDCREDITE;
    }

    @SerializedName("CASHREPORT")
    List<CashReportModel>CASHREPORT;

    public CashReportModel() {

    }

    public String getSalesManNo() {
        return SalesManNo;
    }

    public void setSalesManNo(String salesManNo) {
        SalesManNo = salesManNo;
    }

    public String getSalesManName() {
        return SalesManName;
    }

    public void setSalesManName(String salesManName) {
        SalesManName = salesManName;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getTotalCredite() {
        return totalCredite;
    }

    public void setTotalCredite(String totalCredite) {
        this.totalCredite = totalCredite;
    }

    public String getPtotalCash() {
        return PtotalCash;
    }

    public void setPtotalCash(String ptotalCash) {
        PtotalCash = ptotalCash;
    }

    public String getPtotalCredite() {
        return PtotalCredite;
    }

    public void setPtotalCredite(String ptotalCredite) {
        PtotalCredite = ptotalCredite;
    }

    public String getPtotalCrediteCard() {
        return PtotalCrediteCard;
    }

    public void setPtotalCrediteCard(String ptotalCrediteCard) {
        PtotalCrediteCard = ptotalCrediteCard;
    }

    public String getNetSale() {
        return netSale;
    }

    public void setNetSale(String netSale) {
        this.netSale = netSale;
    }

    public String getNetPay() {
        return netPay;
    }

    public void setNetPay(String netPay) {
        this.netPay = netPay;
    }

    public String getNetCash() {
        return netCash;
    }

    public void setNetCash(String netCash) {
        this.netCash = netCash;
    }

    public List<CashReportModel> getCASHREPORT() {
        return CASHREPORT;
    }

    public void setCASHREPORT(List<CashReportModel> CASHREPORT) {
        this.CASHREPORT = CASHREPORT;
    }
}
