package com.example.adminvansales.model;

import android.graphics.Bitmap;

public class CompanyInfo {
    
    private String companyName;
    private String companyTel;
    private int taxNo;
    private Bitmap logo;
    private String noteForPrint;
    private  double longtudeCompany;
    private  double latitudeCompany;
    private  String notePosition;
    private  String national_id;
    private  String carNo;

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getNotePosition() {
        return notePosition;
    }

    public void setNotePosition(String notePosition) {
        this.notePosition = notePosition;
    }

    public double getLongtudeCompany() {
        return longtudeCompany;
    }

    public void setLongtudeCompany(double longtudeCompany) {
        this.longtudeCompany = longtudeCompany;
    }

    public double getLatitudeCompany() {
        return latitudeCompany;
    }

    public void setLatitudeCompany(double latitudeCompany) {
        this.latitudeCompany = latitudeCompany;
    }

    public CompanyInfo() {
    }

    public CompanyInfo(String companyName, String companyTel, int taxNo, Bitmap logo, String noteForPrint) {
        this.companyName = companyName;
        this.companyTel = companyTel;
        this.taxNo = taxNo;
        this.logo = logo;
        this.noteForPrint = noteForPrint;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getcompanyTel() {
        return companyTel;
    }

    public void setcompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public int getTaxNo() {
        return taxNo;
    }


    public void setTaxNo(int taxNo) {
        this.taxNo = taxNo;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }



    public String getNoteForPrint() {
        return noteForPrint;
    }

    public void setNoteForPrint(String noteForPrint) {
        this.noteForPrint = noteForPrint;
    }
}
