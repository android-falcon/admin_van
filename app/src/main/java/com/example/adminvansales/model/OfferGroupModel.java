package com.example.adminvansales.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OfferGroupModel {

    @SerializedName("ALL_ITEMS")
    private List<OfferGroupModel> ALL_ITEMS;

    @SerializedName("ItemNo")
    public   String   ItemNo;

    @SerializedName("Name")
    public  String   Name;

    public String fromDate;

    public String toDate;
    @SerializedName("ActiveOffers")
    public String ActiveOffers;

    String groupid;


    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setALL_ITEMS(List<OfferGroupModel> ALL_ITEMS) {
        this.ALL_ITEMS = ALL_ITEMS;
    }

    public String getActiveOffers() {
        return ActiveOffers;
    }

    public void setActiveOffers(String activeOffers) {
        ActiveOffers = activeOffers;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getQtyItem() {
        return qtyItem;
    }

    public void setQtyItem(String qtyItem) {
        this.qtyItem = qtyItem;
    }

    @SerializedName("F_D")
    public String price;
    public  String discount;
    public  String discountType;
    public  String qtyItem="0";



    public List<OfferGroupModel> getALL_ITEMS() {
        return ALL_ITEMS;
    }


    public JSONObject getJsonObject() {

        JSONObject obj = new JSONObject();
        try {

            obj.put("ItemNo", ItemNo);
            obj.put("Name", Name);
            obj.put("fromDate", fromDate);// store
            obj.put("toDate", toDate);
            obj.put("discount", discount);
            obj.put("discountType", discountType);
            obj.put("qtyItem", qtyItem);
            obj.put("GroupId", groupid);
            obj.put("activeOffer", ActiveOffers);

        } catch (JSONException e) {
            Log.e("TagserialModel" , "JSONException"+e.getMessage());
        }
        return obj;
    }

    @Override
    public String toString() {
        return "OfferGroupModel{" +
                "ALL_ITEMS=" + ALL_ITEMS +
                ", ItemNo='" + ItemNo + '\'' +
                ", Name='" + Name + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", ActiveOffers='" + ActiveOffers + '\'' +
                ", groupid='" + groupid + '\'' +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                ", discountType='" + discountType + '\'' +
                ", qtyItem='" + qtyItem + '\'' +
                '}';
    }
}
