package com.example.adminvansales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.adminvansales.Model.Account__Statment_Model;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.Model.CustomerInfo;
import com.example.adminvansales.Model.Request;
import com.example.adminvansales.Model.SalesManInfo;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.AccountStatment.getAccountList_text;
import static com.example.adminvansales.GlobelFunction.LatLngListMarker;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.HomeActivity.waitList;
import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.MainActivity.isListUpdated;
import static com.example.adminvansales.Report.CashReport.cashReportList;
import static com.example.adminvansales.Report.CustomerLogReport.customerLogReportList;
import static com.example.adminvansales.Report.PaymentDetailsReport.payMentReportList;
import static com.example.adminvansales.ShowNotifications.showNotification;

public class ImportData {
    private DataBaseHandler databaseHandler;

    private String URL_TO_HIT;
    SweetAlertDialog pdValidation;
    Context main_context;
    public  static ArrayList<Integer> listId;

    public static ArrayList<Request> listRequest = new ArrayList<Request>();
    public static ArrayList<SalesManInfo> listSalesMan = new ArrayList<SalesManInfo>();
    public static ArrayList<Account__Statment_Model> listCustomerInfo = new ArrayList<Account__Statment_Model>();
    public static ArrayList<CustomerInfo> listCustomer = new ArrayList<CustomerInfo>();
    public static List<String> customername=new ArrayList<>();
    public ImportData(Context context) {
        databaseHandler = new DataBaseHandler(context);
        this.main_context = context;
        listId = new ArrayList<>();

    }

    public void getListRequest() {
        new JSONTask_checkStateRequest().execute();
    }


    public void getSalesMan(Context context,int flag) {
        new JSONTaskGetSalesMan(context,flag).execute();
    }

    public void getCustomerAccountStatment(String CustomerId) {
       new JSONTask_AccountStatment(CustomerId).execute();
    }
    public void  getCustomerInfo() {
        Log.e("getCustomerInfo", "*****");
        new JSONTask_CustomerInfo().execute();
    }

        public void getPaymentsReport (Context context, String SalesNo, String fromDate, String
        toDate, String payKind){
            new JSONTaskGetPaymentReport(context, SalesNo, fromDate, toDate, payKind).execute();
        }

        public void getCashReport (Context context, String fromDate, String toDate){
            new JSONTaskGetCashReport(context, fromDate, toDate).execute();
        }

        public void getCustomerLogReport (Context context, String SalesNo, String fromDate, String
        toDate){
            new JSONTaskGetCustomerLogReport(context, SalesNo, fromDate, toDate).execute();
        }

        public void getCustomerAccountStatment () {
            new JSONTask_AccountStatment().execute();
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
                        URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/index.php";
                    }
                } catch (Exception e) {

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
                    Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

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
                                Intent i = new Intent(main_context, MainActivity.class);
                                main_context.startActivity(i);
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
                        URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin_oracle.php";
                    }
                } catch (Exception e) {

                }

                try {

                    String JsonResponse = null;
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(URL_TO_HIT));

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("FLAG", "2"));
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
                    Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

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
                                salesManInfosList.add(salesManInfo);
                                salesManNameList.add(salesManInfo.getSalesName());
                                LatLngListMarker.add(new LatLng(Double.parseDouble(jsonObject1.getString("LATITUDE")), Double.parseDouble(jsonObject1.getString("LONGITUDE"))));
                            }

                            if (flag == 0) {
                                EditSalesMan editSalesMan = (EditSalesMan) context;
                                editSalesMan.searchSalesMan();
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

                        CashReport cashReport = (CashReport) context;
                        cashReport.fillCashAdapter();
                        pdValidation.dismissWithAnimation();

                    } else {
                        cashReportList.clear();
                        CashReport cashReport = (CashReport) context;
                        cashReport.fillCashAdapter();
                        pdValidation.dismissWithAnimation();
                    }
//                progressDialog.dismiss();
                } else {
                    pdValidation.dismissWithAnimation();
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


        private void ShowNotifi_detail (String type,int state, String contentMessage){
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

            Log.e("messgaeBody", "" + messgaeBody);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


//                show_Notification(title);
                showNotification(main_context, title, messgaeBody);

            } else {
                showNotification(main_context, title, messgaeBody);

//            notificationShow(title);
            }


        }


        public void getSalesManInfo () {
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
                String do_ = "my";

            }

            @Override
            protected String doInBackground(String... params) {

                try {


                    if (!ipAddress.equals("")) {
                        URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin_oracle.php";
                    }
                } catch (Exception e) {

                }

                try {

                    String JsonResponse = null;
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(URL_TO_HIT));

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    Log.e("BasicNameValuePair", "" + custId);
                    nameValuePairs.add(new BasicNameValuePair("FLAG", "1"));
                    nameValuePairs.add(new BasicNameValuePair("customerNo", custId));


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
                    Log.e("tag_Customer", "JsonResponse\t" + JsonResponse);

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
                    if (s.contains("CUSTOMER_Account_Statment")) {
                        // Log.e("CUSTOMER_INFO","onPostExecute\t"+s.toString());
                        //{"CUSTOMER_INFO":[{"VHFNo":"0","TransName":"ÞíÏ ÇÝÊÊÇÍí","VHFDATE":"31-DEC-19","DEBIT":"0","Credit":"16194047.851"}

                        try {
                            result = new JSONObject(s);
                            Account__Statment_Model requestDetail;


                            JSONArray requestArray = null;
                            listCustomerInfo = new ArrayList<>();

                            requestArray = result.getJSONArray("CUSTOMER_Account_Statment");
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


                                listCustomerInfo.add(requestDetail);
                                Log.e("listRequest", "listCustomerInfo" + listCustomerInfo.size());


                            }
                            getAccountList_text.setText("2");

                        } catch (JSONException e) {
//                        progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    } else Log.e("onPostExecute", "" + s.toString());
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


                    if (!ipAddress.equals("")) {
                        URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin_oracle.php";
                    }
                } catch (Exception e) {

                }

                try {

                    String JsonResponse = null;
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(URL_TO_HIT));

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("FLAG", "7"));

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
                    Log.e("tag_CustomerInfo", "JsonResponse\t" + JsonResponse);

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
                                requestDetail.setCustomerNumber(infoDetail.get("CUSTID").toString());
                                requestDetail.setCustomerName(infoDetail.get("CUSTNAME").toString());


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

}
