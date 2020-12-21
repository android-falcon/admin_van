package com.example.adminvansales.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SalesManInfo implements Serializable {
    private  String salesName;
    private  String salesPassword;
    private  String active;
    private  String telephone;
    private  String salesManNo;

    public String getSalesManNo() {
        return salesManNo;
    }

    public void setSalesManNo(String salesManNo) {
        this.salesManNo = salesManNo;
    }

    public SalesManInfo() {
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getSalesPassword() {
        return salesPassword;
    }

    public void setSalesPassword(String salesPassword) {
        this.salesPassword = salesPassword;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public JSONObject getJsonObject(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("SALESNO", salesManNo);
            jsonObject.put("ACCNAME", salesName);
            jsonObject.put("USER_PASSWORD", salesPassword);
            jsonObject.put("ACTIVE_USER", active);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
