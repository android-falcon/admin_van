package com.example.adminvansales.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CommissionTarget {
    String SalManNo;
    String SalManName;
    String Date;
    int TargetType;
    String ItemNo;
    String ItemName;
    double qty;
    double ItemTarget;

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getSalManNo() {
        return SalManNo;
    }

    public void setSalManNo(String salManNo) {
        SalManNo = salManNo;
    }

    public String getSalManName() {
        return SalManName;
    }

    public void setSalManName(String salManName) {
        SalManName = salManName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTargetType() {
        return TargetType;
    }

    public void setTargetType(int targetType) {
        TargetType = targetType;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getItemTarget() {
        return ItemTarget;
    }

    public void setItemTarget(double itemTarget) {
        ItemTarget = itemTarget;
    }
    public JSONObject getJsonObject2(){

        JSONObject jsonObject=new JSONObject();

        try {

            jsonObject.put("SALE_MAN_NUMBER", SalManNo);
            jsonObject.put("SALE_MAN_NAME", SalManName);

            jsonObject.put("ITEMOCODE", ItemNo);
            jsonObject.put("ITEMNAME", ItemName);
            jsonObject.put("SMONTH", Date);
            jsonObject.put("TARGET_TYPE", 1);
            jsonObject.put("ITEMTARGET", ItemTarget);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
