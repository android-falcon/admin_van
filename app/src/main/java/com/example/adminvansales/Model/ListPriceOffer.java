package com.example.adminvansales.Model;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListPriceOffer {
//
//    {
//        "PO_LIST_NO": "34",
//            "PO_LIST_NAME": "٥٦٦",
//            "PO_LIST_TYPE": "0",
//            "FROM_DATE": "17/01/2021",
//            "TO_DATE": "17/01/2021"
//    }


    @SerializedName("PO_LIST_NO")
    private String PO_LIST_NO;

    @SerializedName("PO_LIST_NAME")
    private String PO_LIST_NAME;

    @SerializedName("PO_LIST_TYPE")
    private String PO_LIST_TYPE;

    @SerializedName("FROM_DATE")
    private String FROM_DATE;

    @SerializedName("TO_DATE")
    private String TO_DATE;

    @SerializedName("ALL_LIST")
    private List<ListPriceOffer>ALL_LIST;



    public ListPriceOffer( ) {
    }

    public String getPO_LIST_NO() {
        return PO_LIST_NO;
    }

    public void setPO_LIST_NO(String PO_LIST_NO) {
        this.PO_LIST_NO = PO_LIST_NO;
    }

    public String getPO_LIST_NAME() {
        return PO_LIST_NAME;
    }

    public void setPO_LIST_NAME(String PO_LIST_NAME) {
        this.PO_LIST_NAME = PO_LIST_NAME;
    }

    public String getPO_LIST_TYPE() {
        return PO_LIST_TYPE;
    }

    public void setPO_LIST_TYPE(String PO_LIST_TYPE) {
        this.PO_LIST_TYPE = PO_LIST_TYPE;
    }

    public String getFROM_DATE() {
        return FROM_DATE;
    }

    public void setFROM_DATE(String FROM_DATE) {
        this.FROM_DATE = FROM_DATE;
    }

    public String getTO_DATE() {
        return TO_DATE;
    }

    public void setTO_DATE(String TO_DATE) {
        this.TO_DATE = TO_DATE;
    }

    public List<ListPriceOffer> getALL_LIST() {
        return ALL_LIST;
    }

    public void setALL_LIST(List<ListPriceOffer> ALL_LIST) {
        this.ALL_LIST = ALL_LIST;
    }

    public JSONObject getJsonObjectList(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("ListNo", PO_LIST_NO);
            jsonObject.put("ListName", PO_LIST_NAME);
            jsonObject.put("ListType", PO_LIST_TYPE);
            jsonObject.put("FromDate", FROM_DATE);
            jsonObject.put("ToDate", TO_DATE);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
