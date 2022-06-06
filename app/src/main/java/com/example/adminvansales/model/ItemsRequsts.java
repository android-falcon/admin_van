package com.example.adminvansales.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemsRequsts {
    private  String salesmanName;
    private  String salesmanNo;
    private  String requestType;

    private  String itemname;
    private  String itemnumber;

    private  String qty;
    private  double approvedqty;
private String date;
    private String RNO;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getApprovedqty() {
        return approvedqty;
    }

    public void setApprovedqty(double approvedqty) {
        this.approvedqty = approvedqty;
    }

    public String getRNO() {
        return RNO;
    }

    public void setRNO(String RNO) {
        this.RNO = RNO;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanNo() {
        return salesmanNo;
    }

    public void setSalesmanNo(String salesmanNo) {
        this.salesmanNo = salesmanNo;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("LOADTYPE", "2");
            obj.put("VANCODE", salesmanNo);
            obj.put("LOADQTY", approvedqty);
            obj.put("ITEMCODE", itemnumber);
            obj.put("NETQTY", approvedqty);
            obj.put("EXPORTED", "0");
            obj.put("LOADDATE", date);



        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

    public JSONObject getJSONObject2() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("RNO", RNO);




        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
