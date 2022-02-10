package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemInfo {
    @SerializedName("ItemOCode")
    private   String itemOcode;

    @SerializedName("ItemNameE")
    private   String itemNameE;

    @SerializedName("ItemNameA")
    private   String itemNameA;
    @SerializedName("ItemNCode")
    private   String itemNcode;
    private   int select=0;

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public ItemInfo() {
    }

    public String getItemOcode() {
        return itemOcode;
    }

    public void setItemOcode(String itemOcode) {
        this.itemOcode = itemOcode;
    }

    public String getItemNameE() {
        return itemNameE;
    }

    public void setItemNameE(String itemNameE) {
        this.itemNameE = itemNameE;
    }

    public String getItemNameA() {
        return itemNameA;
    }

    public void setItemNameA(String itemNameA) {
        this.itemNameA = itemNameA;
    }

    public String getItemNcode() {
        return itemNcode;
    }

    public void setItemNcode(String itemNcode) {
        this.itemNcode = itemNcode;
    }

    public JSONObject getJsonObject(int salesNo){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("ITEMOCODE", itemOcode);
            jsonObject.put("SALESMANNO", salesNo);
            jsonObject.put("VISIBLE", select);
            jsonObject.put("DEVICEID", "123");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }





}
