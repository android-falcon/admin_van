package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class IIs_PayMentReportModel {

    @SerializedName("COMAPNYNO")
    private String ComapnyNo;

    @SerializedName("VOUYEAR")
    private String VouYear;

    @SerializedName("VOUNO")
    private String VouNo;

    @SerializedName("PAYMENTDATE")
    private String PaymentDate;

    @SerializedName("CUSTOMERNO")
    private String CustomerNo;

    @SerializedName("AMOUNT")
    private String Amount;

    @SerializedName("NOTES")
    private String Notes;

    @SerializedName("SALESMANNO")
    private String SalesmanNo;

    @SerializedName("ISPOSTED")
    private String ISPOSTED;

    @SerializedName("PAYKIND")
    private String PAYKIND;

    @SerializedName("SALESMANNAME")
    private String Salesmanname;

    @SerializedName("PaymentsTable")
    private List<com.example.adminvansales.model.PayMentReportModel> PaymentsTable;

    public IIs_PayMentReportModel(String comapnyNo, String vouYear, String vouNo, String paymentDate, String customerNo, String amount,
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

    public List<com.example.adminvansales.model.PayMentReportModel> getPaymentsTable() {
        return PaymentsTable;
    }

    public void setPaymentsTable(List<com.example.adminvansales.model.PayMentReportModel> paymentsTable) {
        PaymentsTable = paymentsTable;
    }
}
