package com.example.adminvansales.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerInfo {
    private  String customerName;
    private  String customerNumber;
    private  String latit_customer;
    private  String long_customer;
    private  int isSelected;
    private  String areaName;
    private  int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getLatit_customer() {
        return latit_customer;
    }

    public void setLatit_customer(String latit_customer) {
        this.latit_customer = latit_customer;
    }

    public String getLong_customer() {
        return long_customer;
    }

    public void setLong_customer(String long_customer) {
        this.long_customer = long_customer;
    }

    public CustomerInfo() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }


    public JSONObject getJsonObject2(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("CUSTNO", customerNumber);
            jsonObject.put("LA", latit_customer);
            jsonObject.put("LO", long_customer);




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
