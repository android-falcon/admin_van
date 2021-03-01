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

    private int addSalesMen;
    private int addAdmin;
    private int makeOffer;
    private int offerReport;
    private int accountReport;
    private int paymentReport;
    private int customerReport;
    private int cashReport;
    private int salesManLocation;
    private int unCollectChequeReport;
    private int analyzeCustomer;
    private int logHistoryReport;


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

    public int getAddSalesMen() {
        return addSalesMen;
    }

    public void setAddSalesMen(int addSalesMen) {
        this.addSalesMen = addSalesMen;
    }

    public int getAddAdmin() {
        return addAdmin;
    }

    public void setAddAdmin(int addAdmin) {
        this.addAdmin = addAdmin;
    }

    public int getMakeOffer() {
        return makeOffer;
    }

    public void setMakeOffer(int makeOffer) {
        this.makeOffer = makeOffer;
    }

    public int getOfferReport() {
        return offerReport;
    }

    public void setOfferReport(int offerReport) {
        this.offerReport = offerReport;
    }

    public int getAccountReport() {
        return accountReport;
    }

    public void setAccountReport(int accountReport) {
        this.accountReport = accountReport;
    }

    public int getPaymentReport() {
        return paymentReport;
    }

    public void setPaymentReport(int paymentReport) {
        this.paymentReport = paymentReport;
    }

    public int getCustomerReport() {
        return customerReport;
    }

    public void setCustomerReport(int customerReport) {
        this.customerReport = customerReport;
    }

    public int getCashReport() {
        return cashReport;
    }

    public void setCashReport(int cashReport) {
        this.cashReport = cashReport;
    }

    public int getSalesManLocation() {
        return salesManLocation;
    }

    public void setSalesManLocation(int salesManLocation) {
        this.salesManLocation = salesManLocation;
    }

    public int getUnCollectChequeReport() {
        return unCollectChequeReport;
    }

    public void setUnCollectChequeReport(int unCollectChequeReport) {
        this.unCollectChequeReport = unCollectChequeReport;
    }

    public int getAnalyzeCustomer() {
        return analyzeCustomer;
    }

    public void setAnalyzeCustomer(int analyzeCustomer) {
        this.analyzeCustomer = analyzeCustomer;
    }

    public int getLogHistoryReport() {
        return logHistoryReport;
    }

    public void setLogHistoryReport(int logHistoryReport) {
        this.logHistoryReport = logHistoryReport;
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


    public JSONObject getJsonObjectAdmin(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("USER_NO", salesManNo);
            jsonObject.put("USER_NAME", salesName);
            jsonObject.put("PASSWORD", salesPassword);
            jsonObject.put("ACTIVATE", active);

            jsonObject.put("ADD_SALESMEN", addSalesMen);
            jsonObject.put("ADD_ADDMIN", addAdmin);
            jsonObject.put("ACCOUNT_REPORT", accountReport);
            jsonObject.put("PAYMENT_REPORT", paymentReport);
            jsonObject.put("CASH_REPORT", cashReport);
            jsonObject.put("UN_COLLECTED_REPORT", unCollectChequeReport);
            jsonObject.put("OFFER_REPORT", offerReport);
            jsonObject.put("ANALYZE_REPORT", analyzeCustomer);
            jsonObject.put("LOG_HISTORY_REPORT", logHistoryReport);
            jsonObject.put("SALES_LOCATION", salesManLocation);
            jsonObject.put("MAKE_OFFER", makeOffer);
            jsonObject.put("CUSTOMER_LOG_REPORT", customerReport);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
