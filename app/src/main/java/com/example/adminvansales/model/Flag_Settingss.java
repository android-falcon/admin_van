package com.example.adminvansales.model;

public class Flag_Settingss {

    private String Data_Type;
    private int Export_Stock;
    private int Max_Voucher;
    private int Make_Order;
    private int Admin_Password;
    private int Total_Balance;
    private int Voucher_Return;
    private int arabic_language;
    private  int  locationtracker;

    public int getLocationtracker() {
        return locationtracker;
    }

    public void setLocationtracker(int locationtracker) {
        this.locationtracker = locationtracker;
    }

    public int getArabic_language() {
        return arabic_language;
    }

    public void setArabic_language(int arabic_language) {
        this.arabic_language = arabic_language;
    }

    public Flag_Settingss(String data_Type, int export_Stock, int max_Voucher, int make_Order,
                          int admin_Password, int total_Balance, int voucher_Return,int lang,int Locationtracker) {

        Data_Type = data_Type;
        Export_Stock = export_Stock;
        Max_Voucher = max_Voucher;
        Make_Order = make_Order;
        Admin_Password = admin_Password;
        Total_Balance = total_Balance;
        Voucher_Return = voucher_Return;
        lang=arabic_language;
        locationtracker=Locationtracker;

    }

    public String getData_Type() {
        return Data_Type;
    }

    public void setData_Type(String data_Type) {
        Data_Type = data_Type;
    }

    public int getExport_Stock() {
        return Export_Stock;
    }

    public void setExport_Stock(int export_Stock) {
        Export_Stock = export_Stock;
    }

    public int getMax_Voucher() {
        return Max_Voucher;
    }

    public void setMax_Voucher(int max_Voucher) {
        Max_Voucher = max_Voucher;
    }

    public int getMake_Order() {
        return Make_Order;
    }

    public void setMake_Order(int make_Order) {
        Make_Order = make_Order;
    }

    public int getAdmin_Password() {
        return Admin_Password;
    }

    public void setAdmin_Password(int admin_Password) {
        Admin_Password = admin_Password;
    }

    public int getTotal_Balance() {
        return Total_Balance;
    }

    public void setTotal_Balance(int total_Balance) {
        Total_Balance = total_Balance;
    }

    public int getVoucher_Return() {
        return Voucher_Return;
    }

    public void setVoucher_Return(int voucher_Return) {
        Voucher_Return = voucher_Return;
    }

}

