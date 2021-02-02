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
    private  String latitudeLocation;
    private  String longitudeLocation;
    private String  fVoucherSerial;
    private String tVoucherSerial;
    private String fReturnSerial;
    private String tReturnSerial;
    private String fstockSerial;
    private String tStockSerial;

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

    public String getLatitudeLocation() {
        return latitudeLocation;
    }

    public void setLatitudeLocation(String latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public String getLongitudeLocation() {
        return longitudeLocation;
    }

    public void setLongitudeLocation(String longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }

    public String getfVoucherSerial() {
        return fVoucherSerial;
    }

    public void setfVoucherSerial(String fVoucherSerial) {
        this.fVoucherSerial = fVoucherSerial;
    }

    public String gettVoucherSerial() {
        return tVoucherSerial;
    }

    public void settVoucherSerial(String tVoucherSerial) {
        this.tVoucherSerial = tVoucherSerial;
    }

    public String getfReturnSerial() {
        return fReturnSerial;
    }

    public void setfReturnSerial(String fReturnSerial) {
        this.fReturnSerial = fReturnSerial;
    }

    public String gettReturnSerial() {
        return tReturnSerial;
    }

    public void settReturnSerial(String tReturnSerial) {
        this.tReturnSerial = tReturnSerial;
    }

    public String getFstockSerial() {
        return fstockSerial;
    }

    public void setFstockSerial(String fstockSerial) {
        this.fstockSerial = fstockSerial;
    }

    public String gettStockSerial() {
        return tStockSerial;
    }

    public void settStockSerial(String tStockSerial) {
        this.tStockSerial = tStockSerial;
    }

    public JSONObject getJsonObject(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("SALESNO", salesManNo);
            jsonObject.put("ACCNAME", salesName);
            jsonObject.put("USER_PASSWORD", salesPassword);
            jsonObject.put("ACTIVE_USER", active);

            jsonObject.put("F_V_SERIAL", fVoucherSerial);
            jsonObject.put("T_V_SERIAL", tVoucherSerial);
            jsonObject.put("F_R_SERIAL", fReturnSerial);
            jsonObject.put("T_R_SERIAL", tReturnSerial);
            jsonObject.put("F_S_SERIAL", fstockSerial);
            jsonObject.put("T_S_SERIAL", tStockSerial);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
