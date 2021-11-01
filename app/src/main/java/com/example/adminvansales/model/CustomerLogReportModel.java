package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerLogReportModel {
   // SELECT `SALES_MAN_ID`, `CUS_CODE`, `CUS_NAME`, `CHECK_IN_DATE`,
            //`CHECK_IN_TIME`, `CHECK_OUT_DATE`, `CHECK_OUT_TIME`

    @SerializedName("SALES_MAN_ID")
    private String SALES_MAN_ID;

    @SerializedName("Salesmanname")
    private String Salesmanname;

    @SerializedName("CUS_CODE")
    private String CUS_CODE;

    @SerializedName("CUS_NAME")
    private String CUS_NAME;

    @SerializedName("CHECK_IN_DATE")
    private String CHECK_IN_DATE;

    @SerializedName("CHECK_IN_TIME")
    private String CHECK_IN_TIME;

    @SerializedName("CHECK_OUT_DATE")
    private String CHECK_OUT_DATE;

    @SerializedName("CHECK_OUT_TIME")
    private String CHECK_OUT_TIME;

    @SerializedName("CustomerLog")
    List<CustomerLogReportModel>CustomerLog;

    public CustomerLogReportModel(String SALES_MAN_ID, String CUS_CODE,
                                  String CUS_NAME, String CHECK_IN_DATE, String CHECK_IN_TIME, String CHECK_OUT_DATE, String CHECK_OUT_TIME) {
        this.SALES_MAN_ID = SALES_MAN_ID;
        this.CUS_CODE = CUS_CODE;
        this.CUS_NAME = CUS_NAME;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_IN_TIME = CHECK_IN_TIME;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        this.CHECK_OUT_TIME = CHECK_OUT_TIME;
    }

    public String getSALES_MAN_ID() {
        return SALES_MAN_ID;
    }

    public void setSALES_MAN_ID(String SALES_MAN_ID) {
        this.SALES_MAN_ID = SALES_MAN_ID;
    }

    public String getCUS_CODE() {
        return CUS_CODE;
    }

    public void setCUS_CODE(String CUS_CODE) {
        this.CUS_CODE = CUS_CODE;
    }

    public String getCUS_NAME() {
        return CUS_NAME;
    }

    public void setCUS_NAME(String CUS_NAME) {
        this.CUS_NAME = CUS_NAME;
    }

    public String getCHECK_IN_DATE() {
        return CHECK_IN_DATE;
    }

    public void setCHECK_IN_DATE(String CHECK_IN_DATE) {
        this.CHECK_IN_DATE = CHECK_IN_DATE;
    }

    public String getCHECK_IN_TIME() {
        return CHECK_IN_TIME;
    }

    public void setCHECK_IN_TIME(String CHECK_IN_TIME) {
        this.CHECK_IN_TIME = CHECK_IN_TIME;
    }

    public String getCHECK_OUT_DATE() {
        return CHECK_OUT_DATE;
    }

    public void setCHECK_OUT_DATE(String CHECK_OUT_DATE) {
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
    }

    public String getCHECK_OUT_TIME() {
        return CHECK_OUT_TIME;
    }

    public void setCHECK_OUT_TIME(String CHECK_OUT_TIME) {
        this.CHECK_OUT_TIME = CHECK_OUT_TIME;
    }

    public List<CustomerLogReportModel> getCustomerLog() {
        return CustomerLog;
    }

    public void setCustomerLog(List<CustomerLogReportModel> customerLog) {
        CustomerLog = customerLog;
    }

    public String getSalesmanname() {
        return Salesmanname;
    }

    public void setSalesmanname(String salesmanname) {
        Salesmanname = salesmanname;
    }
}
