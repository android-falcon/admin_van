package com.example.adminvansales.Model;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class OfferListModel {
    private String listNo;
    private String listName;
    private String listType;
    private String fromDate;
    private String toDate;


    private String price;
    private String cashOffer;
    private String otherOffer;

    private String itemNo;
    private String itemName;
    private String customerNo;
    private String customerName;

    private int isSelectCustomer;

    public OfferListModel() {
    }

    public OfferListModel(String listNo, String listName, String listType, String fromDate, String toDate
            , String price, String cashOffer, String otherOffer, String itemNo, String itemName,
                          String customerNo, String customerName) {
        this.listNo = listNo;
        this.listName = listName;
        this.listType = listType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
        this.cashOffer = cashOffer;
        this.otherOffer = otherOffer;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.customerNo = customerNo;
        this.customerName = customerName;
    }

    public String getListNo() {
        return listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCashOffer() {
        return cashOffer;
    }

    public void setCashOffer(String cashOffer) {
        this.cashOffer = cashOffer;
    }

    public String getOtherOffer() {
        return otherOffer;
    }

    public void setOtherOffer(String otherOffer) {
        this.otherOffer = otherOffer;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getIsSelectCustomer() {
        return isSelectCustomer;
    }

    public void setIsSelectCustomer(int isSelectCustomer) {
        this.isSelectCustomer = isSelectCustomer;
    }

    public JSONObject getJsonObject(){

        JSONObject jsonObject=new JSONObject();

        try {
//            jsonObject.put("ListNo", listNo);
//            jsonObject.put("ListName", listName);
//            jsonObject.put("ListType", listType);
//            jsonObject.put("FromDate", fromDate);
//            jsonObject.put("ToDate",toDate);

            jsonObject.put("Price",price);
            jsonObject.put("CashOffer", cashOffer);
            jsonObject.put("OtherOffer", otherOffer);
            jsonObject.put("ItemNo", itemNo);
            jsonObject.put("ItemName", itemName);
            jsonObject.put("CustomerNo",customerNo);
            jsonObject.put("CustomerName",customerName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getJsonObjectList(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("ListNo", listNo);
            jsonObject.put("ListName", listName);
            jsonObject.put("ListType", listType);
            jsonObject.put("FromDate", fromDate);
            jsonObject.put("ToDate",toDate);

//            jsonObject.put("Price",price);
//            jsonObject.put("CashOffer", cashOffer);
//            jsonObject.put("OtherOffer", otherOffer);
//            jsonObject.put("ItemNo", itemNo);
//            jsonObject.put("ItemName", itemName);
//            jsonObject.put("CustomerNo",customerNo);
//            jsonObject.put("CustomerName",customerName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
