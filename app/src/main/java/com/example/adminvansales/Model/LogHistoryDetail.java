package com.example.adminvansales.Model;

import android.widget.ListView;

import androidx.annotation.StyleableRes;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.PriorityQueue;

public class LogHistoryDetail {
//{"LOG_DETAIL":[{"ItemNo":"10100001","CUSTOMER_NO":"1110010047","price":"3.33148e24","price_before":"8.4444446e19",
// "DISCOUNT":"0","DISCOUNT_before":"0","OTHER_DISCOUNT":"0","OTHER_DISCOUNT_before":"0","FROM_DATE":"31\/01\/2021",
// "TO_DATE":"31\/01\/2021","LIST_NO":"7","LIST_TYPE":"0"
// ,"customer_name":"السيد فتحي سلامة ارشيد الرواحنة المحترم","ITEM_NAME":"ORANGE E-CHARGE","status":"1"}]

  // {"LOG_DETAIL":[{"ItemNo":"10100002","CUSTOMER_NO":"","price":"47890","price_before":"890","DISCOUNT":"0","DISCOUNT_before":
    // "0","OTHER_DISCOUNT":"0","OTHER_DISCOUNT_before":"0","FROM_DATE":"01\/02\/2021","TO_DATE":"01\/01\/9999"
    // ,"LIST_NO":"0","LIST_TYPE":"1","customer_name":"","ITEM_NAME":"ORANGE E-CHARGE NOS","status":"1"}


    @SerializedName("ItemNo")
private String ItemNo;

    @SerializedName("CUSTOMER_NO")
private String CUSTOMER_NO;

    @SerializedName("price")
private  String price;

    @SerializedName("price_before")
private String price_before;

    @SerializedName("DISCOUNT")
private String DISCOUNT;

    @SerializedName("DISCOUNT_before")
private String DISCOUNT_before;

    @SerializedName("OTHER_DISCOUNT")
private String OTHER_DISCOUNT;

    @SerializedName("OTHER_DISCOUNT_before")
private String OTHER_DISCOUNT_before;

    @SerializedName("FROM_DATE")
private String FROM_DATE;

    @SerializedName("TO_DATE")
private String TO_DATE;

    @SerializedName("LIST_NO")
private String LIST_NO;

    @SerializedName("LIST_TYPE")
private String LIST_TYPE;

    @SerializedName("customer_name")
private String customer_name;

    @SerializedName("ITEM_NAME")
    private String ITEM_NAME;

    @SerializedName("status")
    private String status;

    @SerializedName("LOG_DETAIL")
    private List<LogHistoryDetail> LOG_DETAIL;

    public LogHistoryDetail() {

    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getCUSTOMER_NO() {
        return CUSTOMER_NO;
    }

    public void setCUSTOMER_NO(String CUSTOMER_NO) {
        this.CUSTOMER_NO = CUSTOMER_NO;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_before() {
        return price_before;
    }

    public void setPrice_before(String price_before) {
        this.price_before = price_before;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getDISCOUNT_before() {
        return DISCOUNT_before;
    }

    public void setDISCOUNT_before(String DISCOUNT_before) {
        this.DISCOUNT_before = DISCOUNT_before;
    }

    public String getOTHER_DISCOUNT() {
        return OTHER_DISCOUNT;
    }

    public void setOTHER_DISCOUNT(String OTHER_DISCOUNT) {
        this.OTHER_DISCOUNT = OTHER_DISCOUNT;
    }

    public String getOTHER_DISCOUNT_before() {
        return OTHER_DISCOUNT_before;
    }

    public void setOTHER_DISCOUNT_before(String OTHER_DISCOUNT_before) {
        this.OTHER_DISCOUNT_before = OTHER_DISCOUNT_before;
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

    public String getLIST_NO() {
        return LIST_NO;
    }

    public void setLIST_NO(String LIST_NO) {
        this.LIST_NO = LIST_NO;
    }

    public String getLIST_TYPE() {
        return LIST_TYPE;
    }

    public void setLIST_TYPE(String LIST_TYPE) {
        this.LIST_TYPE = LIST_TYPE;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LogHistoryDetail> getLOG_DETAIL() {
        return LOG_DETAIL;
    }

    public void setLOG_DETAIL(List<LogHistoryDetail> LOG_DETAIL) {
        this.LOG_DETAIL = LOG_DETAIL;
    }
}
