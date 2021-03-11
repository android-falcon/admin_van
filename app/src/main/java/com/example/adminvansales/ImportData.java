package com.example.adminvansales;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.adminvansales.Model.Account__Statment_Model;
import com.example.adminvansales.Model.AnalyzeAccountModel;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.Model.ItemReportModel;
import com.example.adminvansales.Model.ListPriceOffer;
import com.example.adminvansales.Model.LogHistoryDetail;
import com.example.adminvansales.Model.LogHistoryModel;
import com.example.adminvansales.Model.OfferListModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.Model.CustomerInfo;
import com.example.adminvansales.Model.Payment;
import com.example.adminvansales.Model.Request;
import com.example.adminvansales.Model.SalesManInfo;
import com.example.adminvansales.Model.SettingModel;
import com.example.adminvansales.Model.UnCollect_Modell;
import com.example.adminvansales.Model.customerInfoModel;
import com.example.adminvansales.Report.AnalyzeAccounts;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ListOfferReport;
import com.example.adminvansales.Report.LogHistoryReport;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.*;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.adminvansales.AccountStatment.getAccountList_text;
import static com.example.adminvansales.EditSalesMan.AdminInfoList;
import static com.example.adminvansales.GlobelFunction.LatLngListMarker;
import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.HomeActivity.waitList;
import static com.example.adminvansales.ItemReport.itemReportModelsList;
import static com.example.adminvansales.ListOfferReportAdapter.controlText;
import static com.example.adminvansales.GlobelFunction.adminId;
import static com.example.adminvansales.GlobelFunction.adminName;
import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.LogIn.portSettings;
import static com.example.adminvansales.MainActivity.isListUpdated;
import static com.example.adminvansales.OfferPriceList.ItemCardList;
import static com.example.adminvansales.OfferPriceList.customerList;
import static com.example.adminvansales.OfferPriceList.customerListFoundInOtherList;
import static com.example.adminvansales.Report.AnalyzeAccounts.analyzeAccountModelArrayList;
import static com.example.adminvansales.Report.CashReport.cashReportList;
import static com.example.adminvansales.Report.CustomerLogReport.customerLogReportList;
import static com.example.adminvansales.Report.ListOfferReport.control;
import static com.example.adminvansales.Report.ListOfferReport.listPriceOffers;
import static com.example.adminvansales.Report.ListOfferReport.selectCustomerIfClose;
import static com.example.adminvansales.Report.ListOfferReport.selectItemIfUpdate;
import static com.example.adminvansales.Report.LogHistoryReport.logHistoryDetail;
import static com.example.adminvansales.Report.LogHistoryReport.logHistoryList;
import static com.example.adminvansales.Report.PaymentDetailsReport.payMentReportList;
import static com.example.adminvansales.Report.UnCollectedData.resultData;
import static com.example.adminvansales.ShowNotifications.showNotification;

public class ImportData {
    private DataBaseHandler databaseHandler;

    private String URL_TO_HIT;
    SweetAlertDialog pdValidation,pdPayments,pdAnalyze,pdAccountStatment;
    SweetAlertDialog pdValidationCustomer, pdValidationSerial, pdValidationItem,getPdValidationItemCard,getPdValidationLogHistory
            ,pdAuthentication,getPdValidationItemReport;

    public static ArrayList<UnCollect_Modell> unCollectlList=new ArrayList<>();
    public static ArrayList<Payment> paymentChequesList=new ArrayList<>();
    Context main_context;
    public static ArrayList<Integer> listId;

    public static ArrayList<Request> listRequest = new ArrayList<Request>();
    public static ArrayList<SalesManInfo> listSalesMan = new ArrayList<SalesManInfo>();
    public static ArrayList<Account__Statment_Model> listCustomerInfo = new ArrayList<Account__Statment_Model>();
    public static ArrayList<CustomerInfo> listCustomer = new ArrayList<CustomerInfo>();
    public static List<String> customername = new ArrayList<>();
    GlobelFunction globelFunction;

    public ImportData(Context context) {
        databaseHandler = new DataBaseHandler(context);
        this.main_context = context;
       // globelFunction=new GlobelFunction(context);
        listId = new ArrayList<>();

    }

    public void getUnCollectedCheques(String customerId) {

        try {

            SettingModel settingModel =new SettingModel();
            settingModel=databaseHandler.getAllSetting();
            ipAddress=settingModel.getIpAddress();
            if(ipAddress.contains(":"))
            {
                int ind=ipAddress.indexOf(":");
                ipAddress=ipAddress.substring(0,ind);
                Log.e("ipAddress",""+ipAddress);
            }
            portSettings=settingModel.getPort();

        } catch (Exception e) {

        }

        if (!ipAddress.equals("")&&!portSettings.equals("")) {

            Log.e("getUnCollectedCheques", "*****");
            new JSONTask_UncollectedCheques(customerId).execute();

        }
        else {
            Toast.makeText(main_context, "Check Ip Address", Toast.LENGTH_SHORT).show();
        }

    }

    public void getAllcheques(String customerId) {
        try {

            SettingModel settingModel =new SettingModel();
            settingModel=databaseHandler.getAllSetting();
            ipAddress=settingModel.getIpAddress();
            if(ipAddress.contains(":"))
            {
                int ind=ipAddress.indexOf(":");
                ipAddress=ipAddress.substring(0,ind);
                Log.e("ipAddress",""+ipAddress);
            }
            portSettings=settingModel.getPort();

        } catch (Exception e) {

        }

        if (!ipAddress.equals("")&&!portSettings.equals("")) {

            Log.e("getUnCollectedCheques", "*****");
            new JSONTask_GetAllCheques(customerId).execute();
            //  new SyncRemark().execute();
        }
        else {
            Toast.makeText(main_context, "Check Ip Address", Toast.LENGTH_SHORT).show();
        }
    }

    public void getListRequest() {
        new JSONTask_checkStateRequest().execute();
    }

    public void getAllList(Context context) {
        new JSONTaskGetList(context).execute();
    }

    public void getSalesMan(Context context, int flag) {
        new JSONTaskGetSalesMan(context, flag).execute();
    }

    public void getAdmin(Context context, int flag) {
        new JSONTaskGetAdmin(context, flag).execute();
    }

    public void getItemCard(Context context) {
        new JSONTaskGetItem(context).execute();
    }
    public void getItemCustomerByListNo(Context context,String listNo ,String UpClose) {
        new JSONTaskGetItemCustomerByListNo(context,listNo,UpClose).execute();
    }

    public void getItemUpdateClosOpenList(Context context,String listNo,String closeSwitch,String listType) {
        new JSONTaskGetUpdateCloseOpenList(context,listNo,closeSwitch,listType).execute();
    }

    public void getItemUpdateActivateList(Context context,String listNo) {
        new JSONTaskGetUpdateActivateList(context,listNo).execute();
    }

    public void getItemUpdateActivateList(Context context,String serialNo,String listNo , String listType ) {
        new JSONTaskGetLogHistoryDetail(context,serialNo,listNo ,listType).execute();
    }

    public void getLogHistory(Context context){
       new  JSONTaskGetLogHistory(context).execute();
    }

    public void getCustomer(Context context) {
        new JSONTaskGetCustomer(context).execute();
    }

    public void getMaxNo(Context context) {
        new JSONTaskMaxSerial(context).execute();
    }
    public void getItemReport(Context context,String fromDate,String toDate,String salesNo) {
        Log.e("getItemReport","fromDate="+fromDate+"toDate="+toDate+"salesNo="+salesNo);
        new JSONTaskGetItemReport(context,fromDate,toDate,salesNo).execute();
    }

    public void ifBetweenDate(Context context, String fromDate, String toDate, String postion, String upAdd, String listNo,JSONArray listOfCustomer) {
        new JSONTaskIfDateBetween(context, fromDate, toDate, postion, upAdd, listNo,listOfCustomer).execute();
    }

    public void getCustomerAccountStatment(String CustomerId) {
        new JSONTask_AccountStatment(CustomerId).execute();
    }

    public void getCustomerInfo() {
        Log.e("getCustomerInfo", "*****");
        new JSONTask_CustomerInfo().execute();
    }

    public void getPaymentsReport(Context context, String SalesNo, String fromDate, String
            toDate, String payKind) {
        new JSONTaskGetPaymentReport(context, SalesNo, fromDate, toDate, payKind).execute();
    }

    public void getCashReport(Context context, String fromDate, String toDate) {
        new JSONTaskGetCashReport(context, fromDate, toDate).execute();
    }
    public void getAnalyzeReport(Context context, String fromDate, String toDate) {
        try {
            Log.e("toda","fromDate="+fromDate+"\ttoDate"+toDate);
            SettingModel settingModel =new SettingModel();
            settingModel=databaseHandler.getAllSetting();
            ipAddress=settingModel.getIpAddress();
            if(ipAddress.contains(":"))
            {
                int ind=ipAddress.indexOf(":");
                ipAddress=ipAddress.substring(0,ind);
                Log.e("ipAddress",""+ipAddress);
            }
            portSettings=settingModel.getPort();

        } catch (Exception e) {

        }

        if (!ipAddress.equals("")&&!portSettings.equals("")) {
            new JSONTaskGetAnalyzeReport(context, fromDate, toDate).execute();
        }
        else {
            Toast.makeText(main_context, "Check Ip Address", Toast.LENGTH_SHORT).show();
        }

    }

    public void getCustomerLogReport(Context context, String SalesNo, String fromDate, String
            toDate) {
        new JSONTaskGetCustomerLogReport(context, SalesNo, fromDate, toDate).execute();
    }

    public void GetAuthentication (Context context, String userName, String password){

        new JSONTaskGetAuthentication(context, userName, password).execute();

    }

    private class JSONTask_checkStateRequest extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {


                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("JcheckStateRequest","Exception"+e.getMessage());


            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "7"));
//                nameValuePairs.add(new BasicNameValuePair("SalesManNo",discountRequest.getSalesman_no()));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
              //  Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            if (s != null) {
                if (s.contains("RequestAdminData")) {

                    try {
                        result = new JSONObject(s);
                        Request requestDetail;


                        JSONArray requestArray = null;
                        listRequest = new ArrayList<>();
                        listId.clear();

                        requestArray = result.getJSONArray("RequestAdminData");


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new Request();
                            //salesman_name, salesman_no, customer_name, customer_no, request_type, amount_value, " .
                            //                        "voucher_no, total_voucher, status, key_validation ,date, time , note , seen_row
                            requestDetail.setStatuse(infoDetail.get("status").toString());
                            if (requestDetail.getStatuse().equals("0")) {
                                Log.e("infoDetail", "" + infoDetail.get("status").toString());
                                Log.e("key_validation", "" + infoDetail.get("key_validation").toString());
                                requestDetail.setSalesmanName(infoDetail.get("salesman_name").toString());
                                requestDetail.setSalesmanNo(infoDetail.get("salesman_no").toString());
                                requestDetail.setCustomerName(infoDetail.get("customer_name").toString());
                                requestDetail.setCustomer_no(infoDetail.get("customer_no").toString());
                                requestDetail.setRequestType(infoDetail.get("request_type").toString());
                                requestDetail.setAmountValue(infoDetail.get("amount_value").toString());
                                requestDetail.setVoucherNo(infoDetail.get("voucher_no").toString());
                                requestDetail.setTotalVoucher(infoDetail.get("total_voucher").toString());
                                Log.e("requestDetail", "" + requestDetail.getTotalVoucher());
                                requestDetail.setStatuse(infoDetail.get("status").toString());
                                requestDetail.setKeyValidation(infoDetail.get("key_validation").toString());
                                requestDetail.setDate(infoDetail.get("date").toString());
                                requestDetail.setTime(infoDetail.get("time").toString());
                                requestDetail.setNote(infoDetail.get("note").toString());
                                requestDetail.setSeenRow(infoDetail.get("seen_row").toString());
                                requestDetail.setRowId(infoDetail.get("row_id").toString());

                                int rowId = 0;
                                try {
                                    rowId = Integer.parseInt((infoDetail.get("row_id").toString()));
                                    int seen = Integer.parseInt(infoDetail.get("seen_row").toString());
//                                    if (seen == 0) {
                                    int exist = databaseHandler.getRowId(requestDetail.getRowId());
                                    if (exist == 1)// dosent exist
                                    {
                                        databaseHandler.addRoqId(requestDetail.getRowId());
                                        listId.add(rowId);
                                        isListUpdated = true;

                                        ShowNotifi_detail("request", 0, infoDetail.get("salesman_name").toString());
                                    }

//                                    }
                                } catch (Exception e) {
                                }
                                Log.e("listId", "" + listId.size());

                                listRequest.add(requestDetail);
                            }


                        }
                        if (isListUpdated) {
//                            Intent i = new Intent(main_context, MainActivity.class);
//                            main_context.startActivity(i);
                        }


                        Log.e("listRequest", "" + listRequest.size());


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
//                progressDialog.dismiss();
            }
        }
    }

    private class JSONTaskGetSalesMan extends AsyncTask<String, String, String> {
        Object context;
        int flag;

        public JSONTaskGetSalesMan(Object context, int flag) {
            this.flag = flag;
            if (flag == 0) {
                this.context = (EditSalesMan) context;
            } else if (flag == 1) {
                this.context = (HomeActivity) context;
            } else if (flag == 2) {
                this.context = (SalesmanMapsActivity) context;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "2"));
//                nameValuePairs.add(new BasicNameValuePair("SalesManNo",discountRequest.getSalesman_no()));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("salesaaaa", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("SalesManInfo")) {

                    try {
                        salesManInfosList.clear();
                        LatLngListMarker.clear();
                        salesManNameList.clear();
                        salesManNameList.add("All");
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("SalesManInfo");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            SalesManInfo salesManInfo = new SalesManInfo();

                            salesManInfo.setSalesManNo(jsonObject1.getString("SALESNO"));
                            salesManInfo.setSalesName(jsonObject1.getString("ACCNAME"));
                            salesManInfo.setSalesPassword(jsonObject1.getString("USER_PASSWORD"));
                            salesManInfo.setActive(jsonObject1.getString("ACTIVE_USER"));
                            salesManInfo.setLatitudeLocation(jsonObject1.getString("LATITUDE"));
                            salesManInfo.setLongitudeLocation(jsonObject1.getString("LONGITUDE"));

//                            salesManInfo.setfVoucherSerial(jsonObject1.getString("FROM_VOUCHER_SERIAL"));
//                            salesManInfo.settVoucherSerial(jsonObject1.getString("TO_VOUCHER_SERIAL"));
//                            salesManInfo.setfReturnSerial(jsonObject1.getString("FROM_RETURN_SERIAL"));
//                            salesManInfo.settReturnSerial(jsonObject1.getString("TO_RETURN_SERIAL"));
//                            salesManInfo.setFstockSerial(jsonObject1.getString("FROM_STOCK_SERIAL"));
//                            salesManInfo.settStockSerial(jsonObject1.getString("TO_STOCK_SERIAL"));

                            salesManInfosList.add(salesManInfo);
                            salesManNameList.add(salesManInfo.getSalesName());
                            LatLngListMarker.add(new LatLng(Double.parseDouble(jsonObject1.getString("LATITUDE")), Double.parseDouble(jsonObject1.getString("LONGITUDE"))));
                        }

                        if (flag == 0) {
                            EditSalesMan editSalesMan = (EditSalesMan) context;
                            editSalesMan.searchSalesMan(1);
                        } else if (flag == 1) {
                            HomeActivity homeActivity = (HomeActivity) context;
                            homeActivity.showAllSalesManData(salesManInfosList);
                        } else if (flag == 2) {
                            SalesmanMapsActivity salesmanMapsActivity = (SalesmanMapsActivity) context;
                            salesmanMapsActivity.location(1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTaskGetAdmin extends AsyncTask<String, String, String> {
        Object context;
        int flag;

        public JSONTaskGetAdmin(Object context, int flag) {
            this.flag = flag;
            if (flag == 0) {
                this.context = (EditSalesMan) context;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {
                Log.e("JcheckSalesMan","Exception"+e.getMessage());
                Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "31"));
//                nameValuePairs.add(new BasicNameValuePair("SalesManNo",discountRequest.getSalesman_no()));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                // Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("AdminList")) {

                    try {
                        AdminInfoList.clear();

                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("AdminList");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            SalesManInfo salesManInfo = new SalesManInfo();

                            salesManInfo.setSalesManNo(jsonObject1.getString("USER_NO"));
                            salesManInfo.setSalesName(jsonObject1.getString("USER_NAME"));
                            salesManInfo.setSalesPassword(jsonObject1.getString("PASSWORD"));
                            salesManInfo.setActive(jsonObject1.getString("ACTIVATE"));

                            salesManInfo.setAddSalesMen(jsonObject1.getInt("ADD_SALESMEN"));
                            salesManInfo.setAddAdmin(jsonObject1.getInt("ADD_ADDMIN"));
                            salesManInfo.setAccountReport(jsonObject1.getInt("ACCOUNT_REPORT"));
                            salesManInfo.setPaymentReport(jsonObject1.getInt("PAYMENT_REPORT"));

                            salesManInfo.setCashReport(jsonObject1.getInt("CASH_REPORT"));
                            salesManInfo.setUnCollectChequeReport(jsonObject1.getInt("UN_COLLECTED_REPORT"));
                            salesManInfo.setAnalyzeCustomer(jsonObject1.getInt("ANALYZE_REPORT"));
                            salesManInfo.setLogHistoryReport(jsonObject1.getInt("LOG_HISTORY_REPORT"));
                            salesManInfo.setOfferReport(jsonObject1.getInt("OFFER_REPORT"));
                            salesManInfo.setSalesManLocation(jsonObject1.getInt("SALES_LOCATION"));
                            salesManInfo.setMakeOffer(jsonObject1.getInt("MAKE_OFFER"));
                            salesManInfo.setCustomerReport(jsonObject1.getInt("CUSTOMER_LOG_REPORT"));

                            AdminInfoList.add(salesManInfo);
                        }

                            EditSalesMan editSalesMan = (EditSalesMan) context;
                            editSalesMan.searchSalesMan(2);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTaskGetPaymentReport extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String SalesNo, fromDate, toDate, payKind;

        public JSONTaskGetPaymentReport(Object context, String SalesNo, String fromDate, String toDate, String payKind) {
//            this.flag=flag;
            this.context = (PaymentDetailsReport) context;
            this.SalesNo = SalesNo;
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.payKind = payKind;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process));
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "11"));
                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
                nameValuePairs.add(new BasicNameValuePair("PayKind", payKind));
                nameValuePairs.add(new BasicNameValuePair("SalesNo", SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            payMentReportList.clear();
            if (s != null) {
                if (s.contains("ComapnyNo")) {

                    Gson gson = new Gson();

                    PayMentReportModel gsonObj = gson.fromJson(s, PayMentReportModel.class);
                    payMentReportList.clear();
                    payMentReportList.addAll(gsonObj.getPaymentsTable());

                    PaymentDetailsReport paymentDetailsReport = (PaymentDetailsReport) context;
                    paymentDetailsReport.fillPaymentAdapter();
                    pdValidation.dismissWithAnimation();

                } else {
                    payMentReportList.clear();
                    PaymentDetailsReport paymentDetailsReport = (PaymentDetailsReport) context;
                    paymentDetailsReport.fillPaymentAdapter();
                    pdValidation.dismissWithAnimation();
                }
//                progressDialog.dismiss();
            } else {
                payMentReportList.clear();
                PaymentDetailsReport paymentDetailsReport = (PaymentDetailsReport) context;
                paymentDetailsReport.fillPaymentAdapter();
                pdValidation.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetCashReport extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String fromDate, toDate;

        public JSONTaskGetCashReport(Object context, String fromDate, String toDate) {
//            this.flag=flag;
            this.context = (CashReport) context;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process));
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "13"));
                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("SalesManNo")) {

                    Gson gson = new Gson();

                    CashReportModel gsonObj = gson.fromJson(s, CashReportModel.class);
                    cashReportList.clear();
                    cashReportList.addAll(gsonObj.getCASHREPORT());
                    Log.e("totalCashs", "SalesManNo");
                    CashReport cashReport = (CashReport) context;
                    cashReport.fillCashAdapter();
                    pdValidation.dismissWithAnimation();

                } else {
                    cashReportList.clear();
                    CashReport cashReport = (CashReport) context;
                    cashReport.fillCashAdapter();
                    pdValidation.dismissWithAnimation();
                    Log.e("totalCashs", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("totalCashs", "SalesManNo3");
                pdValidation.dismissWithAnimation();
            }
        }

    }
    private class JSONTaskGetAnalyzeReport extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String fromDate, toDate;

        public JSONTaskGetAnalyzeReport(Object context, String fromDate, String toDate) {
//            this.flag=flag;
            this.context = (AnalyzeAccounts) context;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("URL_TO_HIT","GetAccAnalyst+onPreExecute");
            String do_ = "my";
            pdAnalyze = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdAnalyze.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdAnalyze.setTitleText(main_context.getResources().getString(R.string.process));
            pdAnalyze.setCancelable(false);
            pdAnalyze.show();

        }

        @Override
        protected String doInBackground(String... params) {

            String s = "";
            Log.e("URL_TO_HIT","GetAccAnalyst+doInBackground");

            try {

                if (!ipAddress.equals("")) {

                    // URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO="+custId;
                  //  http://10.0.0.22:8082/GetAccAnalyst?ACCNO=1&CONO=295&USERNO=90000&STARTDATE=01/01/2015&UPTODATE=01/03/2021

                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetAccAnalyst?ACCNO=1&CONO=295&USERNO=90000&STARTDATE="+fromDate+"&UPTODATE="+toDate;
                   // URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetAccAnalyst?ACCNO=1&CONO=295&USERNO=90000&STARTDATE="+fromDate+"&UPTODATE="+toDate;
               Log.e("URL_TO_HIT","GetAccAnalyst"+URL_TO_HIT);
                }
            } catch (Exception e) {
                pdAnalyze.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentAnalyze", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdAnalyze.dismissWithAnimation();
                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdAnalyze.dismissWithAnimation();
//{"AccCode":"1101030001","AccNameA":"صندوق المبيعات اليوميه","PREVD":"0","PREVC":"0","PREVDF":"0","PREVCF":"0","TDEB":"0","TCRE":"500","TDEBF":"0","TCREF":"0"}
            if (s != null) {
                if (s.contains("AccCode")) {

                    Gson gson = new Gson();

//                    AnalyzeAccountModel gsonObj = gson.fromJson(s, AnalyzeAccountModel.class);
//                    analyzeAccountModelArrayList.clear();
//                    analyzeAccountModelArrayList.addAll(gsonObj.getANALYZEPORT());
//
//                    AnalyzeAccounts analyzeAccounts = (AnalyzeAccounts) context;
//                    analyzeAccounts.fillCashAdapter();
//                    pdAnalyze.dismissWithAnimation();

                    Type collectionType = new TypeToken<List<AnalyzeAccountModel>>(){}.getType();
                    List<AnalyzeAccountModel> lcs = (List<AnalyzeAccountModel>) new Gson()
                            .fromJson( s , collectionType);
                    Log.e("fromJson",""+lcs.get(0).getAccNameA());
                    analyzeAccountModelArrayList.clear();
                    analyzeAccountModelArrayList.addAll(lcs);
                    AnalyzeAccounts analyzeAccounts = (AnalyzeAccounts) context;
                    analyzeAccounts.fillCashAdapter();
                   // Log.e("analyzeAccountModelArr", "SalesManNo"+analyzeAccountModelArrayList.size());
                    //Log.e("analyzeAccountModelArr", "SalesManNo"+analyzeAccountModelArrayList.get(0).getAccNameA());

                }
            } else {
                Log.e("totalCashs", "SalesManNo3");
                pdAnalyze.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetCustomerLogReport extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String SalesNo, fromDate, toDate;

        public JSONTaskGetCustomerLogReport(Object context, String SalesNo, String fromDate, String toDate) {
//            this.flag=flag;
            this.context = (CustomerLogReport) context;
            this.SalesNo = SalesNo;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process));
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("_ID", "12"));
                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
                nameValuePairs.add(new BasicNameValuePair("SalesNo", SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("SALES_MAN_ID")) {

                    Gson gson = new Gson();

                    CustomerLogReportModel gsonObj = gson.fromJson(s, CustomerLogReportModel.class);
                    customerLogReportList.clear();
                    customerLogReportList.addAll(gsonObj.getCustomerLog());

                    CustomerLogReport customerLogReport = (CustomerLogReport) context;
                    customerLogReport.fillCustomerLogReport();
                    pdValidation.dismissWithAnimation();

                } else {
                    customerLogReportList.clear();
                    CustomerLogReport customerLogReport = (CustomerLogReport) context;
                    customerLogReport.fillCustomerLogReport();
                    pdValidation.dismissWithAnimation();
                }
//                progressDialog.dismiss();
            } else {
                pdValidation.dismissWithAnimation();
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ShowNotifi_detail(String type, int state, String contentMessage) {
        String currentapiVersion = Build.VERSION.RELEASE;
        String title = "", content = "", statuse = "";


//        if (type.equals("check")) {
//            notification_btn.setVisibility(View.VISIBLE);
//
//        }
//        if (type.equals("Request")) {
//            button_request.setVisibility(View.VISIBLE);
//
//        }

        switch (type) {
            case "check":
                title = "Check";
                break;
            case "request":
                title = "Request";
                break;
        }
        String messgaeBody = "";
        switch (state) {
            case 0:
                statuse = "new\t" + title + "\t";
                messgaeBody = "you have " + statuse + "From" + "\t" + contentMessage;
                break;

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



//                show_Notification(title);
            showNotification(main_context, title, messgaeBody);

        } else {
//            show_Notification(title);
            showNotification(main_context, title, messgaeBody);

//            notificationShow(title);
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail) {

        Intent intent = new Intent(main_context, notificationReciver.class);
        intent.putExtra("action", "YES");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(main_context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH);
        Notification notification = new Notification.Builder(main_context, CHANNEL_ID)
                .setContentText("Show Details ......")
                .setContentTitle("Receive New Request, Click To Show Details")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat, "YES", pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setOngoing(true)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager = (NotificationManager)main_context.getSystemService(Context.NOTIFICATION_SERVICE) ;
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);


    }

    public void getSalesManInfo() {
        new JSONTask_SalesManInfo().execute();
    }

    private class JSONTask_SalesManInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String s = "";


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "10"));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_SalesManInfo", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            if (s != null) {
                if (s.contains("SalesManInfo")) {

                    try {
                        result = new JSONObject(s);
                        SalesManInfo requestDetail;


                        JSONArray requestArray = null;
                        listSalesMan = new ArrayList<>();

                        requestArray = result.getJSONArray("SalesManInfo");


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new SalesManInfo();
                            requestDetail.setSalesManNo(infoDetail.get("SalesManNo").toString());
                            requestDetail.setSalesName(infoDetail.get("SalesManName").toString());
                            requestDetail.setActive(infoDetail.get("IsSuspended").toString());
                            Log.e("SalesManName", "" + infoDetail.get("SalesManName").toString());
                            Log.e("SalesManNo", "" + infoDetail.get("SalesManNo").toString());

                            listSalesMan.add(requestDetail);
                            Log.e("listRequest", "" + listSalesMan.size());


                        }
                        waitList.setText("1");

                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTask_AccountStatment extends AsyncTask<String, String, String> {

        private String custId = "";

        public JSONTask_AccountStatment(String customerId) {
            this.custId = customerId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdAccountStatment = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdAccountStatment.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdAccountStatment.setTitleText(main_context.getResources().getString(R.string.process));
            pdAccountStatment.setCancelable(false);
            pdAccountStatment.show();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                SettingModel settingModel =new SettingModel();
                settingModel=databaseHandler.getAllSetting();
                ipAddress=settingModel.getIpAddress();
                if(ipAddress.contains(":"))
                {
                    int ind=ipAddress.indexOf(":");
                    ipAddress=ipAddress.substring(0,ind);
                    Log.e("ipAddress",""+ipAddress);
                }
                portSettings=settingModel.getPort();
                //                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO="+custId;
                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO="+custId;
                    //URL_TO_HIT = "http://" + ipAddress.trim()+":"+portSettings.trim() + "/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO="+custId.trim().toString();

               Log.e("URL_TO_HIT","account="+URL_TO_HIT);
                }
            } catch (Exception e) {
                pdAccountStatment.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                Log.e("BasicNameValuePair", "" + custId);
//                nameValuePairs.add(new BasicNameValuePair("FLAG", "1"));
//                nameValuePairs.add(new BasicNameValuePair("customerNo", custId));


//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_Customer", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdAccountStatment.dismissWithAnimation();
                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                pdAccountStatment.dismissWithAnimation();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdAccountStatment.dismissWithAnimation();
            JSONObject result = null;
            String impo = "";
            listCustomerInfo = new ArrayList<>();
            Log.e("URL_TO_HIT","account"+s.toString());
            if (s != null) {
                if (s.contains("VHFNo")) {
                    // Log.e("CUSTOMER_INFO","onPostExecute\t"+s.toString());
                    //{"CUSTOMER_INFO":[{"VHFNo":"0","TransName":"ÞíÏ ÇÝÊÊÇÍí","VHFDATE":"31-DEC-19","DEBIT":"0","Credit":"16194047.851"}

                    try {
//                        result = new JSONObject(s);
                        Account__Statment_Model requestDetail;


                        JSONArray requestArray = null;
                       // listCustomerInfo = new ArrayList<>();
                        double totalBalance=0;
                        requestArray = new JSONArray(s);
                        Log.e("requestArray", "" + requestArray.length());


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new Account__Statment_Model();
                            requestDetail.setVoucherNo(infoDetail.get("VHFNo").toString());
                            requestDetail.setTranseNmae(infoDetail.get("TransName").toString());
                            requestDetail.setDate_voucher(infoDetail.get("VHFDATE").toString());

                            try {
                                requestDetail.setDebit(Double.parseDouble(infoDetail.get("DEBIT").toString()));
                                requestDetail.setCredit(Double.parseDouble(infoDetail.get("Credit").toString()));
                            } catch (Exception e) {
                                requestDetail.setDebit(0);
                                requestDetail.setCredit(0);
                            }

                            if(requestDetail.getDebit()!=0.0)
                            {
                                totalBalance+=requestDetail.getDebit();
                            }

                            if(requestDetail.getCredit()!=0.0)
                            {

                                totalBalance-=requestDetail.getCredit();

                            }

                            requestDetail.setBalance(totalBalance);
                           // Log.e("onBindViewHolder","=total="+totalBalance);

                            listCustomerInfo.add(requestDetail);
                            Log.e("listRequest", "listCustomerInfo" + listCustomerInfo.size());


                        }
                        getAccountList_text.setText("2");

                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                } else if(s.contains("No Parameter Found")){
                    getAccountList_text.setText("Nodata");

                    Log.e("onPostExecute", "" + s.toString());
                }

//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTask_CustomerInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

              //  ipAddress="10.0.0.185";

                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                    Log.e("importData","importData"+URL_TO_HIT);
                }
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("JcheckStateRequest","Exception"+e.getMessage());


            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "1"));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_salesManInfo", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
//            Log.e("importData","importData"+s.toString());
            if (s != null) {
                if (s.contains("CUSTOMER_INFO")) {
                    // Log.e("CUSTOMER_INFO","onPostExecute\t"+s.toString());
                    //{"CUSTOMER_INFO":[{"VHFNo":"0","TransName":"ÞíÏ ÇÝÊÊÇÍí","VHFDATE":"31-DEC-19","DEBIT":"0","Credit":"16194047.851"}

                    try {
                        result = new JSONObject(s);
                        CustomerInfo requestDetail;


                        JSONArray requestArray = null;
                        listCustomerInfo = new ArrayList<>();

                        requestArray = result.getJSONArray("CUSTOMER_INFO");
                        Log.e("requestArray", "" + requestArray.length());


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new CustomerInfo();
                            requestDetail.setCustomerNumber(infoDetail.get("CustID").toString());
                            requestDetail.setCustomerName(infoDetail.get("CustName").toString());


                            listCustomer.add(requestDetail);
                            customername.add(requestDetail.getCustomerName());
                            Log.e("listRequest", "CUSTOMER_INFO" + listCustomer.get(i).getCustomerName());


                        }
//                        getAccountList_text.setText("1");

                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                } else Log.e("onPostExecute", "" + s.toString());
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTaskGetItem extends AsyncTask<String, String, String> {
        Object context;
        int flag;


        public JSONTaskGetItem(Object context) {
//            this.flag=flag;
            this.context = (OfferPriceList) context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            getPdValidationItemCard = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            getPdValidationItemCard.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            getPdValidationItemCard.setTitleText(main_context.getResources().getString(R.string.process) + "3");
            getPdValidationItemCard.setCancelable(false);
            getPdValidationItemCard.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "16"));
//                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("ItemNo")) {

                    Gson gson = new Gson();

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();

                    ItemMaster gsonObj = gson.fromJson(s, ItemMaster.class);
                    ItemCardList.clear();
                    ItemCardList.addAll(gsonObj.getALL_ITEMS());
                    Log.e("itemCard", "SalesManNo");
                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    getPdValidationItemCard.dismissWithAnimation();
                    offerPriceList.fillItemListPrice();

                } else {
                    ItemCardList.clear();
                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    getPdValidationItemCard.dismissWithAnimation();
                    Log.e("itemCard", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("itemCard", "SalesManNo3");
                getPdValidationItemCard.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetCustomer extends AsyncTask<String, String, String> {
        Object context;
        int flag;


        public JSONTaskGetCustomer(Object context) {
//            this.flag=flag;
            this.context = (OfferPriceList) context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationCustomer = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationCustomer.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationCustomer.setTitleText(main_context.getResources().getString(R.string.process) + "2");
            pdValidationCustomer.setCancelable(false);
            pdValidationCustomer.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "17"));
//                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("ALL_CUSTOMER")) {

                    Gson gson = new Gson();

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();

                    customerInfoModel gsonObj = gson.fromJson(s, customerInfoModel.class);
                    customerList.clear();
                    customerList.addAll(gsonObj.getALL_CUSTOMER());
                    Log.e("item_customer", "SalesManNo");
                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidationCustomer.dismissWithAnimation();
                    offerPriceList.fillCustomerListId();


                } else {
                    ItemCardList.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidationCustomer.dismissWithAnimation();
                    Log.e("item_customer", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("item_customer", "SalesManNo3");
                pdValidationCustomer.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskIfDateBetween extends AsyncTask<String, String, String> {
        Object context;
        Context contextMaster;
        int flag;
        String fromDate, toDate, listType, UpAddFlag, listNO;
        JSONArray listOfCustomer;


        public JSONTaskIfDateBetween(Object context, String fromDate, String toDate, String listType, String upAddFlag, String listNO,JSONArray listOfCustomer ) {
//            this.flag=flag;
            if (upAddFlag.equals("0")||upAddFlag.equals("2")) {
                this.context = (OfferPriceList) context;
                this.contextMaster = (OfferPriceList) context;
            } else {
                this.context = (ListOfferReport) context;
                this.contextMaster = (ListOfferReport) context;
            }
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.listType = listType;
            this.UpAddFlag = upAddFlag;
            this.listNO = listNO;
            this.listOfCustomer=listOfCustomer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationCustomer = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationCustomer.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationCustomer.setTitleText(main_context.getResources().getString(R.string.cheackDate));
            pdValidationCustomer.setCancelable(false);
            pdValidationCustomer.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "check ip", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair("_ID", "18"));
                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
                nameValuePairs.add(new BasicNameValuePair("PO_LIST_TYPE", listType));
//                if(UpAddFlag.equals("0")) {
                    nameValuePairs.add(new BasicNameValuePair("UPDATE_ADD_FLAG", UpAddFlag));
//                }else{
//                    nameValuePairs.add(new BasicNameValuePair("UPDATE_ADD_FLAG", "1"));
//
//                }
                nameValuePairs.add(new BasicNameValuePair("PO_LIST_NO", listNO));
                nameValuePairs.add(new BasicNameValuePair("LIST_OF_CUSTOMER", listOfCustomer.toString()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                Log.e("ttttaa", "JsonResponse\t" + nameValuePairs.toString());

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                Log.e("ressssss", "JsonResponse\t" + s);
                if (s.contains("CustName")) {

                    if (listType.equals("0")) {
                        SweetAlertDialog sweet = new SweetAlertDialog(contextMaster, SweetAlertDialog.WARNING_TYPE);
                        sweet.setTitleText("Please change Customer");
                        sweet.setContentText(" ( Customer in this List Found in other List )");
                        sweet.setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                        sweet.show();

                        Log.e("sttttb",""+s.toString());
                        s=s.replace("},]","}]");
                        Log.e("stttta",""+s.toString());

                        Gson gson = new Gson();

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();

                        customerInfoModel gsonObj = gson.fromJson(s, customerInfoModel.class);
                        customerListFoundInOtherList.clear();
                        customerListFoundInOtherList.addAll(gsonObj.getALL_CUSTOMER());
                        Log.e("item_customer", "SalesManNo");
                        OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                        pdValidationCustomer.dismissWithAnimation();
                        offerPriceList.selectItemFoundInOtherList();



                    }else {
                        SweetAlertDialog sweet = new SweetAlertDialog(contextMaster, SweetAlertDialog.WARNING_TYPE);
                        sweet.setTitleText("price only list");
                        sweet.setContentText(" ( the system found other price regular only list  )");
                        sweet.setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                        sweet.show();
                    }
                    pdValidationCustomer.dismissWithAnimation();

//                    if (UpAddFlag.equals("0")) {
//                        int s.toString().lastIndexOf(",");
//
//                    }

                } else {
//                    ItemCardList.clear();
                    if (UpAddFlag.equals("0")||UpAddFlag.equals("2")) {
                        OfferPriceList offerPriceList = (OfferPriceList) context;
                        offerPriceList.saveInDB();
//                        if(UpAddFlag.equals("2")){
//                            offerPriceList.finishLayout();
//                        }
                    } else  if (UpAddFlag.equals("1")) {
                        controlText.setText("1");
                    }
                    pdValidationCustomer.dismissWithAnimation();
//                    Log.e("item_customer","SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("item_customer", "SalesManNo3");
                pdValidationCustomer.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskMaxSerial extends AsyncTask<String, String, String> {
        Object context;
        int flag;


        public JSONTaskMaxSerial(Object context) {
//            this.flag=flag;
            this.context = (OfferPriceList) context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationSerial = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationSerial.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationSerial.setTitleText(main_context.getResources().getString(R.string.process) + "1");
            pdValidationSerial.setCancelable(false);
            pdValidationSerial.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "20"));
//                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("serialList")) {
//
//                    Gson gson = new Gson();
//
////                    Gson gson = new GsonBuilder()
////                            .setLenient()
////                            .create();
//
//                    customerInfoModel gsonObj = gson.fromJson(s, customerInfoModel.class);
//                    customerList.clear();
//                    customerList.addAll(gsonObj.getALL_CUSTOMER());
//                    Log.e("item_customer","SalesManNo");
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
////                    offerPriceList.fillItemCard();
//                    pdValidationCustomer.dismissWithAnimation();
//                    offerPriceList.fillCustomerListId();


                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String serial = jsonObject.getString("serialList");
                        OfferPriceList offerPriceList = (OfferPriceList) context;
                        offerPriceList.fillSerial(serial);
                        pdValidationSerial.dismissWithAnimation();
                    } catch (JSONException e) {
                        Log.e("serialmAX", "SalesManNoEX");
                        e.printStackTrace();
                    }


                } else {
//                    ItemCardList.clear();
                    pdValidationSerial.dismissWithAnimation();
                    Log.e("serialmAX", "SalesManNo2");
                }
//                progressDialog.dismiss();
            } else {
                Log.e("serialmAX", "SalesManNo3");
                pdValidationSerial.dismissWithAnimation();
            }
        }

    }


    private class JSONTaskGetList extends AsyncTask<String, String, String> {
        Object context;
        int flag;


        public JSONTaskGetList(Object context) {
//            this.flag=flag;
            this.context = (ListOfferReport) context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationItem = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationItem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationItem.setTitleText(main_context.getResources().getString(R.string.process) + "2");
            pdValidationItem.setCancelable(false);
            pdValidationItem.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "22"));
//                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("ALL_LIST")) {

                    Gson gson = new Gson();

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();

                    ListPriceOffer gsonObj = gson.fromJson(s, ListPriceOffer.class);
                    listPriceOffers.clear();
                    listPriceOffers.addAll(gsonObj.getALL_LIST());
                    Log.e("item_customer", "SalesManNo");
                    ListOfferReport offerPriceList = (ListOfferReport) context;
                    offerPriceList.fillCashAdapter();
                    pdValidationItem.dismissWithAnimation();


                } else {
                    listPriceOffers.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidationItem.dismissWithAnimation();
                    Log.e("item_customer", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("item_customer", "SalesManNo3");
                pdValidationItem.dismissWithAnimation();
            }
        }

    }
    private class JSONTaskGetItemCustomerByListNo extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String listNo;
        String upClose;


        public JSONTaskGetItemCustomerByListNo(Object context,String listNo,String UpClose) {
//            this.flag=flag;
            this.context =  (ListOfferReport)context;
            this.listNo =listNo;
            this.upClose=UpClose;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process) + "3");
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "26"));
                nameValuePairs.add(new BasicNameValuePair("LIST_NO", listNo));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null)  {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);
                Log.e("closeList","lll5");
                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            List<OfferListModel> itemsList=new ArrayList<>();
            List<customerInfoModel> customerList=new ArrayList<>();
            if (s != null) {
                if (s.contains("ALL_CUSTOMER")) {

                    Gson gson = new Gson();

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();


                    OfferListModel gsonObj = gson.fromJson(s, OfferListModel.class);
                    customerInfoModel customerInfoModel=gson.fromJson(s,customerInfoModel.class);
                    itemsList.clear();
                    itemsList.addAll(gsonObj.getALL_LIST());
                    customerList.clear();
                    customerList.addAll(customerInfoModel.getALL_CUSTOMER());
                    Log.e("closeList", "SalesManNo  "+ s.toString());
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();

                    selectCustomerIfClose=customerList;
                    selectItemIfUpdate=itemsList;
                    Log.e("closeList","lll4   "+selectCustomerIfClose.size());
                    pdValidation.dismissWithAnimation();

                    ListOfferReport listOfferReport=(ListOfferReport) context;

                    if(upClose.equals("0")) {
                        listOfferReport.control.setText("close");
                    }else {
                        listOfferReport.control.setText("update");
                    }
//                    offerPriceList.fillItemListPrice();

                } else {
                    itemsList.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidation.dismissWithAnimation();
                    Log.e("itemCard", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("itemCard", "SalesManNo3");
                pdValidation.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetUpdateCloseOpenList extends AsyncTask<String, String, String> {
        Context context;
        int flag;
        String listNo,closeSwitch,listType;


        public JSONTaskGetUpdateCloseOpenList(Context context,String listNo,String closeSwitch,String listType) {
//            this.flag=flag;
            this.context =  context;
            this.listNo =listNo;
            this.closeSwitch=closeSwitch;

            this.listType=listType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process) + "3");
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "27"));
                nameValuePairs.add(new BasicNameValuePair("LIST_NO", listNo));
                nameValuePairs.add(new BasicNameValuePair("LIST_SWITCH", closeSwitch));
                nameValuePairs.add(new BasicNameValuePair("ADMIN_ID",adminId));
                nameValuePairs.add(new BasicNameValuePair("ADMIN_NAME",adminName));
                nameValuePairs.add(new BasicNameValuePair("ListType",listType));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("UPDATE_LIST_SUCCESS")) {

//                    Gson gson = new Gson();
//
////                    Gson gson = new GsonBuilder()
////                            .setLenient()
////                            .create();
//
//
//                    OfferListModel gsonObj = gson.fromJson(s, OfferListModel.class);
//                    customerInfoModel customerInfoModel=gson.fromJson(s,customerInfoModel.class);
//                    itemsList.clear();
//                    itemsList.addAll(gsonObj.getALL_LIST());
//                    customerList.clear();
//                    customerList.addAll(customerInfoModel.getALL_CUSTOMER());
//                    Log.e("itemCard", "SalesManNo");
                    OfferPriceList offerPriceList = (OfferPriceList) context;
                    offerPriceList.finishLayout();

                    control.setText("main");
                    //selectCustomerIfClose=customerList;

                    Log.e("closeList","lll3");

                    pdValidation.dismissWithAnimation();
//                    controlText.setText("close");
//                    offerPriceList.fillItemListPrice();

                } else {
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidation.dismissWithAnimation();
                    Log.e("itemCard", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("itemCard", "SalesManNo3");
                pdValidation.dismissWithAnimation();
            }
        }

    }
    private class JSONTaskGetUpdateActivateList extends AsyncTask<String, String, String> {
        Context context;
        int flag;
        String listNo;


        public JSONTaskGetUpdateActivateList(Context context,String listNo) {
//            this.flag=flag;
            this.context =  context;
            this.listNo =listNo;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process) + "3");
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "28"));
                nameValuePairs.add(new BasicNameValuePair("LIST_NO", listNo));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("UPDATE_LIST_SUCCESS")) {

//                    Gson gson = new Gson();
//
////                    Gson gson = new GsonBuilder()
////                            .setLenient()
////                            .create();
//
//
//                    OfferListModel gsonObj = gson.fromJson(s, OfferListModel.class);
//                    customerInfoModel customerInfoModel=gson.fromJson(s,customerInfoModel.class);
//                    itemsList.clear();
//                    itemsList.addAll(gsonObj.getALL_LIST());
//                    customerList.clear();
//                    customerList.addAll(customerInfoModel.getALL_CUSTOMER());
//                    Log.e("itemCard", "SalesManNo");

                    control.setText("main");
                    //selectCustomerIfClose=customerList;

                    Log.e("closeList","lll3");

                    pdValidation.dismissWithAnimation();
//                    controlText.setText("close");
//                    offerPriceList.fillItemListPrice();

                } else {
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidation.dismissWithAnimation();
                    Log.e("itemCard", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("itemCard", "SalesManNo3");
                pdValidation.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetLogHistory extends AsyncTask<String, String, String> {
        Object context;
        int flag;


        public JSONTaskGetLogHistory(Object context) {
//            this.flag=flag;
            this.context = (LogHistoryReport) context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationItem = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationItem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationItem.setTitleText(main_context.getResources().getString(R.string.process) + "2");
            pdValidationItem.setCancelable(false);
            pdValidationItem.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "29"));
//                nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
//                nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
//                nameValuePairs.add(new BasicNameValuePair("PayKind",payKind));
//                nameValuePairs.add(new BasicNameValuePair("SalesNo",SalesNo));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("ALL_LOG_FILE")) {

                    Gson gson = new Gson();

                    LogHistoryModel gsonObj = gson.fromJson(s, LogHistoryModel.class);
                    logHistoryList.clear();
                    logHistoryList.addAll(gsonObj.getALL_LOG_FILE());
                    Log.e("getALL_LOG_FILE", "getALL_LOG_FILE"+logHistoryList.size());
                    LogHistoryReport logHistoryReport = (LogHistoryReport) context;
                    logHistoryReport.fillLogHistory();
                    pdValidationItem.dismissWithAnimation();


                } else {
                    listPriceOffers.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    pdValidationItem.dismissWithAnimation();
                    Log.e("item_customer", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                Log.e("item_customer", "SalesManNo3");
                pdValidationItem.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetLogHistoryDetail extends AsyncTask<String, String, String> {
        Object context;
        int flag;
        String serialNo,listNo,listType;


        public JSONTaskGetLogHistoryDetail(Object context,String serialNo , String listNo ,String listType) {
//            this.flag=flag;
            this.context = (LogHistoryReport) context;
            this.serialNo =serialNo;
            this.listNo = listNo;
            this .listType=listType;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            getPdValidationLogHistory = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            getPdValidationLogHistory.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            getPdValidationLogHistory.setTitleText(main_context.getResources().getString(R.string.process) + "2");
            getPdValidationLogHistory.setCancelable(false);
            getPdValidationLogHistory.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "30"));
                nameValuePairs.add(new BasicNameValuePair("SERIAL_NO", serialNo));
                nameValuePairs.add(new BasicNameValuePair("LIST_NO", listNo));
                nameValuePairs.add(new BasicNameValuePair("LIST_TYPE",listType));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("LOG_DETAIL")) {

                    Gson gson = new Gson();

                    LogHistoryDetail gsonObj = gson.fromJson(s, LogHistoryDetail.class);
                    logHistoryDetail.clear();
                    logHistoryDetail.addAll(gsonObj.getLOG_DETAIL());
                    Log.e("LOG_DETAIL", "LOG_DETAIL"+logHistoryDetail.size());
                    LogHistoryReport logHistoryReport = (LogHistoryReport) context;
                    logHistoryReport.fillDialogLogDetail();
                    getPdValidationLogHistory.dismissWithAnimation();


                } else {
                    logHistoryDetail.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    getPdValidationLogHistory.dismissWithAnimation();
                    //Log.e("item_customer", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
               // Log.e("item_customer", "SalesManNo3");
                getPdValidationLogHistory.dismissWithAnimation();
            }
        }

    }
    private class JSONTask_UncollectedCheques extends AsyncTask<String, String, String> {

        private String custId = "";

        public JSONTask_UncollectedCheques(String customerId) {
            this.custId = customerId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdValidation = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(main_context.getResources().getString(R.string.process));
            pdValidation.setCancelable(false);
            pdValidation.show();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
//                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/Falcons/VAN.dll/GetTheUnCollectedCheques?ACCNO=1224";

                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetTheUnCollectedCheques?ACCNO="+custId;
                }
            } catch (Exception e) {
                pdValidation.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_CustomerAccount", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdValidation.dismissWithAnimation();
                        Toast.makeText(main_context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("AccCode")) {
                    // Log.e("CUSTOMER_INFO","onPostExecute\t"+s.toString());
                    //{"CUSTOMER_INFO":[{"VHFNo":"0","TransName":"ÞíÏ ÇÝÊÊÇÍí","VHFDATE":"31-DEC-19","DEBIT":"0","Credit":"16194047.851"}

                    try {
                        //[{"AccCode":"1224","RECVD":"3528","PAIDAMT":"0"}]
                        UnCollect_Modell requestDetail;


                        JSONArray requestArray = null;
                        listCustomerInfo = new ArrayList<>();

                        requestArray =  new JSONArray(s);
                        Log.e("requestArray", "" + requestArray.length());


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new UnCollect_Modell();
                            requestDetail.setAccCode(infoDetail.get("AccCode").toString());
                            requestDetail.setRECVD(infoDetail.get("RECVD").toString());
                            requestDetail.setPAIDAMT(infoDetail.get("PAIDAMT").toString());



                            unCollectlList.add(requestDetail);
                            Log.e("listRequest", "listCustomerInfo" + unCollectlList.size());


                        }
                        if(unCollectlList.size()!=0)
                        {
                            resultData.setText("yes");
                        }


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                } else Log.e("onPostExecute", "" + s.toString());
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTask_GetAllCheques extends AsyncTask<String, String, String> {

        private String custId = "";

        public JSONTask_GetAllCheques(String customerId) {
            this.custId = customerId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdPayments = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdPayments.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdPayments.setTitleText(main_context.getResources().getString(R.string.app_amount));
            pdPayments.setCancelable(false);
            pdPayments.show();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if (!ipAddress.equals("")) {

                       // URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO="+custId;

                        //Log.e("URL_TO_HIT","account="+URL_TO_HIT);


                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + portSettings.trim() +"/Falcons/VAN.dll/GetAllTheCheques?ACCNO="+custId;
                }
            } catch (Exception e) {
                pdPayments.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_allcheques", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        pdPayments.dismissWithAnimation();
                        Toast.makeText(main_context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            pdPayments.dismissWithAnimation();
            if (s != null) {
                if (s.contains("VHFNo")) {
                    try {
                        Payment requestDetail;
                        JSONArray requestArray = null;
                        paymentChequesList = new ArrayList<>();

                        requestArray =  new JSONArray(s);
                        //Log.e("requestArray", "" + requestArray.length());


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new Payment();
                            try {
                                requestDetail.setCheckNumber(Integer.parseInt(infoDetail.get("ChequeNo").toString()));
                            }
                            catch (Exception e){ requestDetail.setCheckNumber(111);}
                            //

                            requestDetail.setDueDate(infoDetail.get("DueDate").toString());
                            requestDetail.setBank("Jordan Bank");
                            try {
                                requestDetail.setAmount(Double.parseDouble(infoDetail.get("CAmount").toString()));

                            }catch (Exception e){requestDetail.setAmount(0);}



                            paymentChequesList.add(requestDetail);
                            //Log.e("listRequest", "listCustomerInfo" + unCollectlList.size());


                        }
                        if(paymentChequesList.size()!=0)
                        {
                            resultData.setText("payment");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else Log.e("onPostExecute", "" + s.toString());
            }
        }

    }


    private class JSONTaskGetAuthentication extends AsyncTask<String, String, String> {
        Object context;
        String userName,password;


        public JSONTaskGetAuthentication(Object context,String userName , String password ) {
//            this.flag=flag;
            this.context = (LogIn) context;
            this.userName =userName;
            this.password = password;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdAuthentication = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdAuthentication.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdAuthentication.setTitleText(main_context.getResources().getString(R.string.process) + "2");
            pdAuthentication.setCancelable(false);
            pdAuthentication.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "34"));
                nameValuePairs.add(new BasicNameValuePair("USER_NAME", userName));
                nameValuePairs.add(new BasicNameValuePair("PASSWORD", password));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("Authentication")) {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);

                    JSONArray jsonArray = jsonObject.getJSONArray("Authentication");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        SalesManInfo salesManInfo = new SalesManInfo();

                        salesManInfo.setSalesManNo(jsonObject1.getString("USER_NO"));
                        salesManInfo.setSalesName(jsonObject1.getString("USER_NAME"));
                        salesManInfo.setSalesPassword(jsonObject1.getString("PASSWORD"));
                        salesManInfo.setActive(jsonObject1.getString("ACTIVATE"));

                        salesManInfo.setAddSalesMen(jsonObject1.getInt("ADD_SALESMEN"));
                        salesManInfo.setAddAdmin(jsonObject1.getInt("ADD_ADDMIN"));
                        salesManInfo.setAccountReport(jsonObject1.getInt("ACCOUNT_REPORT"));
                        salesManInfo.setPaymentReport(jsonObject1.getInt("PAYMENT_REPORT"));

                        salesManInfo.setCashReport(jsonObject1.getInt("CASH_REPORT"));
                        salesManInfo.setUnCollectChequeReport(jsonObject1.getInt("UN_COLLECTED_REPORT"));
                        salesManInfo.setAnalyzeCustomer(jsonObject1.getInt("ANALYZE_REPORT"));
                        salesManInfo.setLogHistoryReport(jsonObject1.getInt("LOG_HISTORY_REPORT"));
                        salesManInfo.setOfferReport(jsonObject1.getInt("OFFER_REPORT"));
                        salesManInfo.setSalesManLocation(jsonObject1.getInt("SALES_LOCATION"));
                        salesManInfo.setMakeOffer(jsonObject1.getInt("MAKE_OFFER"));
                        salesManInfo.setCustomerReport(jsonObject1.getInt("CUSTOMER_LOG_REPORT"));

                        salesManInfoAdmin=salesManInfo;
                        LogIn logIn= (LogIn) context;
                        logIn.saveAuth(salesManInfo);
                        logIn.goToMain();

                    }


                    pdAuthentication.dismissWithAnimation();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText((Context) context, "user Name or Password NOT Correct", Toast.LENGTH_SHORT).show();
                    pdAuthentication.dismissWithAnimation();


                }
//                progressDialog.dismiss();
            } else {
                // Log.e("item_customer", "SalesManNo3");
                pdAuthentication.dismissWithAnimation();
            }
        }

    }

    private class JSONTaskGetItemReport extends AsyncTask<String, String, String> {
        Object context;
        int flag;
      //  String serialNo,listNo,listType;

        String fromDate,toDate,salesNo;

        public JSONTaskGetItemReport(Object context,String fromDate , String toDate ,String salesNo) {
//            this.flag=flag;
            this.context = (ItemReport) context;
            this.fromDate =fromDate;
            this.toDate = toDate;
            this .salesNo=salesNo;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            getPdValidationItemReport = new SweetAlertDialog((Context) context, SweetAlertDialog.PROGRESS_TYPE);
            getPdValidationItemReport.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            getPdValidationItemReport.setTitleText(main_context.getResources().getString(R.string.getItemReport) );
            getPdValidationItemReport.setCancelable(false);
            getPdValidationItemReport.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("_ID", "35"));
                nameValuePairs.add(new BasicNameValuePair("FROM_DATE", fromDate));
                nameValuePairs.add(new BasicNameValuePair("TO_DATE", toDate));
                nameValuePairs.add(new BasicNameValuePair("SALES_NO",salesNo));
                Log.e("getItemReport","BasicNameValuePair\t fromDate="+fromDate+"toDate="+toDate+"salesNo="+salesNo);

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));


//                JsonReader reader = new JsonReader(new StringReader(myFooString));
//                reader.setLenient(true);
//                new JsonParser().parse(reader);


                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
               // Log.e("tag_paymentReport", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("itemReport")) {

                    Gson gson = new Gson();
                    //Log.e("LOG_DETAIL", "LOG_DETAIL"+ s.toString());
                    ItemReportModel gsonObj = gson.fromJson(s, ItemReportModel.class);
                    itemReportModelsList.clear();
                    itemReportModelsList.addAll(gsonObj.getItemReport());
                    //Log.e("LOG_DETAIL", "LOG_DETAIL"+itemReportModelsList.size());
                    ItemReport itemReport = (ItemReport) context;
                    itemReport.fillItemAdapter();
                    getPdValidationItemReport.dismissWithAnimation();


                } else {
                    itemReportModelsList.clear();
//                    OfferPriceList offerPriceList = (OfferPriceList) context;
//                    offerPriceList.fillItemCard();
                    ItemReport itemReport = (ItemReport) context;
                    itemReport.fillItemAdapter();
                    getPdValidationItemReport.dismissWithAnimation();
                    //Log.e("item_customer", "SalesManNo2");

                }
//                progressDialog.dismiss();
            } else {
                // Log.e("item_customer", "SalesManNo3");
                getPdValidationItemReport.dismissWithAnimation();
            }
        }

    }

}
