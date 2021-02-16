package com.example.adminvansales.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnalyzeAccountModel {
    //{"AccCode":"1101030001","AccNameA":"صندوق المبيعات اليوميه","PREVD":"0","PREVC":"0",
    // "PREVDF":"0","PREVCF":"0","TDEB":"0","TCRE":"500","TDEBF":"0","TCREF":"0"}

    @SerializedName("AccCode")
    private  String AccCode;

    @SerializedName("AccNameA")
    private  String AccNameA;

    @SerializedName("PREVD")
    private  String PREVD;


    @SerializedName("PREVC")
    private  String PREVC;

    @SerializedName("PREVDF")
    private  String PREVDF;

    @SerializedName("PREVCF")
    private  String PREVCF;

    @SerializedName("TDEB")
    private  String TDEB;

    @SerializedName("TCRE")
    private  String TCRE;

    @SerializedName("TDEBF")
    private  String TDEBF;

    @SerializedName("TCREF")
    private  String TCREF;
    @SerializedName("CASHREPORT")
    List<AnalyzeAccountModel> ANALYZEPORT;
    public AnalyzeAccountModel() {
    }

    public String getAccCode() {
        return AccCode;
    }

    public void setAccCode(String accCode) {
        AccCode = accCode;
    }

    public String getAccNameA() {
        return AccNameA;
    }

    public void setAccNameA(String accNameA) {
        AccNameA = accNameA;
    }

    public String getPREVD() {
        return PREVD;
    }

    public void setPREVD(String PREVD) {
        this.PREVD = PREVD;
    }

    public String getPREVC() {
        return PREVC;
    }

    public void setPREVC(String PREVC) {
        this.PREVC = PREVC;
    }

    public String getPREVDF() {
        return PREVDF;
    }

    public void setPREVDF(String PREVDF) {
        this.PREVDF = PREVDF;
    }

    public String getPREVCF() {
        return PREVCF;
    }

    public void setPREVCF(String PREVCF) {
        this.PREVCF = PREVCF;
    }

    public String getTDEB() {
        return TDEB;
    }

    public void setTDEB(String TDEB) {
        this.TDEB = TDEB;
    }

    public String getTCRE() {
        return TCRE;
    }

    public void setTCRE(String TCRE) {
        this.TCRE = TCRE;
    }

    public String getTDEBF() {
        return TDEBF;
    }

    public void setTDEBF(String TDEBF) {
        this.TDEBF = TDEBF;
    }

    public String getTCREF() {
        return TCREF;
    }

    public void setTCREF(String TCREF) {
        this.TCREF = TCREF;
    }
    public List<AnalyzeAccountModel> getANALYZEPORT() {
        return ANALYZEPORT;
    }

    public void setANALYZEPORT(List<CashReportModel> CASHREPORT) {
        this.ANALYZEPORT = ANALYZEPORT;
    }
}
