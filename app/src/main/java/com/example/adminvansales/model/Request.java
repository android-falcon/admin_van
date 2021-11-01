package com.example.adminvansales.model;

public class Request {
    private  String salesmanName;
    private  String salesmanNo;
    private  String customerName;
    private String customer_no;
    private  String requestType;
    private  String amountValue;
    private  String voucherNo;
    private  String totalVoucher;

    private  String keyValidation;

    private String date;
    private String time ;
    private String note ;
    private  String statuse;// 0 ===> new request | 1 ====>accepted | 2 ====> rejected
    private  String seenRow; // when see the
    private  String itemName;
    private  String rowId;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getTotalVoucher() {
        return totalVoucher;
    }

    public void setTotalVoucher(String totalVoucher) {
        this.totalVoucher = totalVoucher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKeyValidation() {
        return keyValidation;
    }

    public void setKeyValidation(String keyValidation) {
        this.keyValidation = keyValidation;
    }

    public String getStatuse() {
        return statuse;
    }

    public void setStatuse(String statuse) {
        this.statuse = statuse;
    }

    public String getSeenRow() {
        return seenRow;
    }

    public void setSeenRow(String seenRow) {
        this.seenRow = seenRow;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Request() {
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(String amountValue) {
        this.amountValue = amountValue;
    }
}
