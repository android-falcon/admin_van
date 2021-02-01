package com.example.adminvansales.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OfferListModel {

    //{
    //    "ALL_LIST": [
    //        {
    //            "ITEMNO": "10100001",
    //            "CUSTOMER_NO": "1110010002",
    //            "PRICE": "90",
    //            "DISCOUNT": "10",
    //            "OTHER_DISCOUNT": "10",
    //            "FROM_DATE": "27/01/2021",
    //            "TO_DATE": "27/01/2021",
    //            "LIST_NO": "21",
    //            "LIST_TYPE": "0",
    //            "CustName": "المعلم بلال"
    //
    //    ],


    @SerializedName("LIST_NO")
    private String listNo;
    private String listName;
    @SerializedName("LIST_TYPE")
    private String listType;
    @SerializedName("FROM_DATE")
    private String fromDate;
    @SerializedName("TO_DATE")
    private String toDate;

    @SerializedName("PRICE")

    private String price;
    @SerializedName("DISCOUNT")
    private String cashOffer;
    @SerializedName("OTHER_DISCOUNT")
    private String otherOffer;
    @SerializedName("ITEMNO")
    private String itemNo;

    @SerializedName("Name")
    private String itemName;

    @SerializedName("CUSTOMER_NO")
    private String customerNo;
    @SerializedName("CustName")
    private String customerName;

    @SerializedName("ALL_LIST")
    private List<OfferListModel> ALL_LIST;

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

    public List<OfferListModel> getALL_LIST() {
        return ALL_LIST;
    }

    public void setALL_LIST(List<OfferListModel> ALL_LIST) {
        this.ALL_LIST = ALL_LIST;
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
