package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemMaster {

//            "ComapnyNo": "291",
//                    "ItemNo": "10100003",
//                    "Name": "ORANGE E-CHARGE NET",
//                    "CateogryID": "البطاقات",
//                    "Barcode": "10100003",
//                    "IsSuspended": null,
//                    "ItemL": "0",
//                    "MINPRICE": "0",
//                    "F_D": "0",
//                    "ItemK": "اورنج",
//                    "ITEMHASSERIAL": "0",
//                    "ITEMPICSPATH": null




//    @SerializedName("ComapnyNo")
    private  String   ComapnyNo;

    @SerializedName("ItemOCode")
    public   String   ItemNo;

    @SerializedName("ItemNameA")
    public   String   Name;
    public   String    ItemG;
//    @SerializedName("CateogryID")
    private  String   CateogryID;

//    @SerializedName("Barcode")
    private  String   Barcode;
//    @SerializedName("IsSuspended")
    private  String   IsSuspended;
//    @SerializedName("ItemL")
    private  String   ItemL;
//    @SerializedName("MINPRICE")
    private  String   MINPRICE;
    @SerializedName("F_D")
    public   String   F_D;
//    @SerializedName("ItemK")
    private  String   ItemK;
//    @SerializedName("ITEMHASSERIAL")
    private  String   ITEMHASSERIAL;
//    @SerializedName("ITEMPICSPATH")
    private  String   ITEMPICSPATH;

    @SerializedName("ALL_ITEMS")
    private List<ItemMaster> ALL_ITEMS;

    private boolean checkedItem;
    private  String price;


    public String getComapnyNo() {
        return ComapnyNo;
    }

    public void setComapnyNo(String comapnyNo) {
        ComapnyNo = comapnyNo;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getCateogryID() {
        return CateogryID;
    }

    public void setCateogryID(String cateogryID) {
        CateogryID = cateogryID;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getIsSuspended() {
        return IsSuspended;
    }

    public void setIsSuspended(String isSuspended) {
        IsSuspended = isSuspended;
    }

    public String getItemL() {
        return ItemL;
    }

    public void setItemL(String itemL) {
        ItemL = itemL;
    }

    public String getMINPRICE() {
        return MINPRICE;
    }

    public void setMINPRICE(String MINPRICE) {
        this.MINPRICE = MINPRICE;
    }

    public String getF_D() {
        return F_D;
    }

    public void setF_D(String f_D) {
        F_D = f_D;
    }

    public String getItemK() {
        return ItemK;
    }

    public void setItemK(String itemK) {
        ItemK = itemK;
    }

    public String getITEMHASSERIAL() {
        return ITEMHASSERIAL;
    }

    public void setITEMHASSERIAL(String ITEMHASSERIAL) {
        this.ITEMHASSERIAL = ITEMHASSERIAL;
    }

    public String getITEMPICSPATH() {
        return ITEMPICSPATH;
    }

    public void setITEMPICSPATH(String ITEMPICSPATH) {
        this.ITEMPICSPATH = ITEMPICSPATH;
    }

    public List<ItemMaster> getALL_ITEMS() {
        return ALL_ITEMS;
    }

    public void setALL_ITEMS(List<ItemMaster> ALL_ITEMS) {
        this.ALL_ITEMS = ALL_ITEMS;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isCheckedItem() {
        return checkedItem;
    }

    public void setCheckedItem(boolean checkedItem) {
        this.checkedItem = checkedItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemG() {
        return ItemG;
    }

    public void setItemG(String itemG) {
        ItemG = itemG;
    }
}
