package com.example.adminvansales.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LogHistoryModel {
//
//    {
//        "ALL_LOG_FILE": [
//        {
//            "ADMIN_NAME": "admin",
//                "ADMIN_ID": "100",
//                "LIST_TYPE": "0",
//                "LIST_NO": "7",
//                "SERIAL": "1",
//                "DATE_OF_UPDATE": "01/02/2021",
//                "UPDATE_TYPE": "2"
//        }
//        ]
//    }
    @SerializedName("ADMIN_NAME")
    private String ADMIN_NAME;
    @SerializedName("ADMIN_ID")
    private String ADMIN_ID;
    @SerializedName("LIST_TYPE")
    private String LIST_TYPE;
    @SerializedName("LIST_NO")
    private String LIST_NO;
    @SerializedName("SERIAL")
    private String SERIAL;
    @SerializedName("DATE_OF_UPDATE")
    private String DATE_OF_UPDATE;
    @SerializedName("UPDATE_TYPE")
    private  String UPDATE_TYPE;
    @SerializedName("ALL_LOG_FILE")
    private List<LogHistoryModel> ALL_LOG_FILE;


    public String getADMIN_NAME() {
        return ADMIN_NAME;
    }

    public void setADMIN_NAME(String ADMIN_NAME) {
        this.ADMIN_NAME = ADMIN_NAME;
    }

    public String getADMIN_ID() {
        return ADMIN_ID;
    }

    public void setADMIN_ID(String ADMIN_ID) {
        this.ADMIN_ID = ADMIN_ID;
    }

    public String getLIST_TYPE() {
        return LIST_TYPE;
    }

    public void setLIST_TYPE(String LIST_TYPE) {
        this.LIST_TYPE = LIST_TYPE;
    }

    public String getLIST_NO() {
        return LIST_NO;
    }

    public void setLIST_NO(String LIST_NO) {
        this.LIST_NO = LIST_NO;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getDATE_OF_UPDATE() {
        return DATE_OF_UPDATE;
    }

    public void setDATE_OF_UPDATE(String DATE_OF_UPDATE) {
        this.DATE_OF_UPDATE = DATE_OF_UPDATE;
    }

    public String getUPDATE_TYPE() {
        return UPDATE_TYPE;
    }

    public void setUPDATE_TYPE(String UPDATE_TYPE) {
        this.UPDATE_TYPE = UPDATE_TYPE;
    }

    public List<LogHistoryModel> getALL_LOG_FILE() {
        return ALL_LOG_FILE;
    }

    public void setALL_LOG_FILE(List<LogHistoryModel> ALL_LOG_FILE) {
        this.ALL_LOG_FILE = ALL_LOG_FILE;
    }
}
