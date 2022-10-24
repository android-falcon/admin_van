package com.example.adminvansales;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.adminvansales.Report.RequstReport;
import com.example.adminvansales.model.CommissionTarget;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.ItemsRequsts;
import com.example.adminvansales.model.Password;
import com.example.adminvansales.model.Plan_SalesMan_model;
import com.example.adminvansales.model.Request;
import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.model.SettingModel;
import com.example.adminvansales.model.TargetDetalis;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.GroupOffer.addList;
import static com.example.adminvansales.HomeActivity.editPassword;
import static com.example.adminvansales.ImportData.offerGroupModels;
import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.LogIn.portSettings;
import static com.example.adminvansales.Report.ListOfferReport.control;

public class ExportData {
    SweetAlertDialog pdRepRev,pdSweetAlertDialog,pdSweetAlertDialog2;
    private JSONArray jsonArrayComm_Target,jsonArrayTarget,jsonArraysalesman,jsonArrayadmins,passwordjsonArray,RequstsjsonArray,VanRequstsjsonArray,UpdateloadvanArray;
    JSONObject Comm_Targetobject,Targetobject,addsalesmanobject,addadminsmanobject,passwordobject,Requstobject,VanRequstsjsonobject,Updateloadvanobject;
    private DataBaseHandler databaseHandler;
    private JSONArray jsonArrayRequest;
    private String URL_TO_HIT ;
    public static  SweetAlertDialog pd,pdValidation,pdValidationDialog;
    GlobelFunction globelFunction;
    SweetAlertDialog pdValidationAdd;
    SweetAlertDialog pdValidationUpdate;
   // public  String headerDll="/Falcons/VAN.dll";
 public  String headerDll="";
    public  String CONO="";
    Context main_context;
    int flag=0;
    ArrayList<Integer> listIdUpdate=new ArrayList();
    public ExportData(Context context) {
        databaseHandler = new DataBaseHandler(context);
        this.main_context=context;
        globelFunction=new GlobelFunction(context);
        getCONO();

    }
    public  void updateRowState(String rowId ,String state){
        flag=2;
        jsonArrayRequest = new JSONArray();

        Log.e("rowId","updateRowState"+rowId+"\t"+state);

            JSONObject obj = new JSONObject();
            try {
                obj.put("statuse", state);
                obj.put("row_id", rowId);
                jsonArrayRequest.put(obj);

            } catch (JSONException e) {
                Log.e("TagserialModel" , "JSONException"+e.getMessage());
            }

        new JSONTask().execute();

    }
    public void getCONO(){


        SettingModel settingModel = new SettingModel();
        settingModel = databaseHandler.getAllSetting();
        portSettings=settingModel.getPort();
        CONO = settingModel.getCono();

    }
    public  void updateRowSeen(ArrayList list){
        this.listIdUpdate=list;
        Log.e("listIdUpdate" , ""+listIdUpdate.size());
        flag=1;
        jsonArrayRequest = new JSONArray();
        for (int i=0;i<listIdUpdate.size();i++)
        {

          JSONObject obj = new JSONObject();
            try {
                obj.put("Row_Id", listIdUpdate.get(i));
                jsonArrayRequest.put(obj);

            } catch (JSONException e) {
                Log.e("TagserialModel" , "JSONException"+e.getMessage());
            }
        }
        new JSONTask().execute();

    }
    private void  getRequstObject(List<Request>requestList) {
        jsonArrayRequest = new JSONArray();
        for (int i = 0; i < requestList.size(); i++)
        {

            jsonArrayRequest.put(requestList.get(i).getJsonObject());

        }
        try {
            Requstobject =new JSONObject();
            Requstobject.put("JSN", jsonArrayRequest);
            Log.e("Object",""+ Requstobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public  void   IIs_updateRowSeen(List<Request>requestList){
     getCONO();
        getRequstObject(requestList);
        Log.e("requestList",requestList.size()+"  "+requestList.get(0).getRowId());
        new  JSONTask_IIsupdateRequst(requestList).execute();

    }
    public void AddSales(Context context,JSONObject jsonObject){
        new JSONTaskAddSales(context,jsonObject).execute();
    }
    private void  getAddSalesObject(List<SalesManInfo>salesManInfos) {
   jsonArraysalesman = new JSONArray();
        for (int i = 0; i < salesManInfos.size(); i++)
        {

            jsonArraysalesman.put(salesManInfos.get(i).getJsonObject2());

        }
        try {
            addsalesmanobject =new JSONObject();
            addsalesmanobject.put("JSN", jsonArraysalesman);
            Log.e("Object",""+ addsalesmanobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  getAddTargetObject(List<TargetDetalis>targetDetalisList) {
        jsonArrayTarget = new JSONArray();
        for (int i = 0; i < targetDetalisList.size(); i++)
        {

            jsonArrayTarget.put(targetDetalisList.get(i).getJsonObject());

        }
        try {
            Targetobject =new JSONObject();
            Targetobject.put("JSN", jsonArrayTarget);
            Log.e("Targetobject",""+ Targetobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void  getAddTargetObject2(List<TargetDetalis>targetDetalisList) {
        jsonArrayTarget = new JSONArray();
        for (int i = 0; i < targetDetalisList.size(); i++)
        {

            jsonArrayTarget.put(targetDetalisList.get(i).getJsonObject2());

        }
        try {
            Targetobject =new JSONObject();
            Targetobject.put("JSN", jsonArrayTarget);
            Log.e("Targetobject",""+ Targetobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void  getCommissionTargetObject(List<CommissionTarget>targetDetalisList) {
        jsonArrayComm_Target = new JSONArray();
        for (int i = 0; i < targetDetalisList.size(); i++)
        {

            jsonArrayComm_Target.put(targetDetalisList.get(i).getJsonObject2());

        }
        try {
            Comm_Targetobject =new JSONObject();
            Comm_Targetobject.put("JSN", jsonArrayComm_Target);
            Log.e("Targetobject",""+ Comm_Targetobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void  getAddPlanObject(List<Plan_SalesMan_model>salesManInfos) {
        jsonArraysalesman = new JSONArray();
        for (int i = 0; i < salesManInfos.size(); i++)
        {

            jsonArraysalesman.put(salesManInfos.get(i).getJsonObject2());

        }
        try {
            addsalesmanobject =new JSONObject();
            addsalesmanobject.put("JSN", jsonArraysalesman);
            Log.e("Object","getAddPlanObject=="+ addsalesmanobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void  getaddpasswordObject(List<Password>passwordList) {
        passwordjsonArray = new JSONArray();
        for (int i = 0; i < passwordList.size(); i++)
        {

            passwordjsonArray.put(passwordList.get(i).getJsonObject());

        }
        try {
          passwordobject =new JSONObject();
            passwordobject.put("JSN",  passwordjsonArray);
            Log.e("Object",""+  passwordjsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void  getAddadminObject(List<SalesManInfo>salesManInfos) {
        jsonArrayadmins = new JSONArray();
        for (int i = 0; i < salesManInfos.size(); i++)
        {

            jsonArrayadmins.put(salesManInfos.get(i).getJsonObjectAdmin2());

        }
        try {
            addadminsmanobject =new JSONObject();
            addadminsmanobject.put("JSN", jsonArrayadmins);
            Log.e("Object",""+ jsonArrayadmins.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  getVanRequstObject(List<ItemsRequsts>itemsRequsts) {
        VanRequstsjsonArray = new JSONArray();
        for (int i = 0; i < itemsRequsts.size(); i++)
        {

            VanRequstsjsonArray.put(itemsRequsts.get(i).getJSONObject());

        }
        try {

                    VanRequstsjsonobject =new JSONObject();
            VanRequstsjsonobject.put("JSN", VanRequstsjsonArray);
            Log.e("Object",""+ VanRequstsjsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  getVanUpdateObject(List<ItemsRequsts>itemsRequsts) {
        UpdateloadvanArray = new JSONArray();
        for (int i = 0; i < itemsRequsts.size(); i++)
        {

            UpdateloadvanArray.put(itemsRequsts.get(i).getJSONObject2());

        }
        try {

            Updateloadvanobject =new JSONObject();
            Updateloadvanobject.put("JSN", UpdateloadvanArray);
            Log.e("Object",""+ UpdateloadvanArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void IIs_AddSales(List<SalesManInfo>salesManInfos,EditSalesMan context){
        getCONO();

        getAddSalesObject(salesManInfos);

        new JSONTaskIIs_AddSales(context,salesManInfos).execute();
    }

    public void SaveNetsaleTarget(List<TargetDetalis>targetDetalisList,Context context){
        getCONO();


        getAddTargetObject(targetDetalisList);

        new JSONTask_AddTarget(context,targetDetalisList).execute();
    }

    public void SaveNetsaleTarget2(List<TargetDetalis>targetDetalisList,Context context){
        getCONO();


         getAddTargetObject2(targetDetalisList);
        new JSONTask_AddTarget2(context,targetDetalisList).execute();
    }
    public void getCommissionTarget(List<CommissionTarget>targetDetalisList,Context context){
        getCONO();


        getCommissionTargetObject(targetDetalisList);
        new JSONTask_AddCommissionTarget(context,targetDetalisList).execute();
    }

    public void IIs_AddPlan(List<Plan_SalesMan_model>salesManInfos, Context context){
        getCONO();
        getAddPlanObject(salesManInfos);
        new JSONTaskIIs_DeletePlan(context,salesManInfos).execute();


    }
    public void IIs_UpdateSales(List<SalesManInfo>salesManInfos,EditSalesMan context){
        getCONO();

        getAddSalesObject(salesManInfos);

        new JSONTaskIIs_UpdateSales(context,salesManInfos).execute();
    }

    public void AddAdmins(Context context,JSONObject jsonObject){

        new JSONTaskAddAdmin(context,jsonObject).execute();
    }
    public void IIs_AddAdmins(List<SalesManInfo>salesManInfos,EditSalesMan context){
        getCONO();
        getAddadminObject(salesManInfos);
        new JSONTaskIIs_AddAdmin(context,salesManInfos).execute();
    }
    public void updateList(Context context,JSONObject jsonObject , JSONArray jsonArray){
        new JSONTaskUpdateList(context,jsonObject, jsonArray).execute();
    }

    public void UpdateSales(Context context,JSONObject jsonObject){
        new JSONTaskUpdateSales(context,jsonObject).execute();
    }

    public void UpdateAdmin(Context context,JSONObject jsonObject){
        new JSONTaskUpdateAdmin(context,jsonObject).execute();
    }
    public void IIs_UpdateAdmin(List<SalesManInfo>salesManInfos,EditSalesMan context){
        getCONO();
        getAddadminObject(salesManInfos);
        new JSONTaskIIs_UpdateAdmin(context,salesManInfos).execute();

    }

    public void IIs_SaveVanRequst(List<ItemsRequsts>itemsRequsts){
        getCONO();
        getVanRequstObject(itemsRequsts);
        new JSONTaskIIs_SaveVanRequst(itemsRequsts).execute();

    }

    public void IIs_UpdateVanRequst(List<ItemsRequsts>itemsRequsts){
        getCONO();
        getVanUpdateObject(itemsRequsts);
        new JSONTaskIIs_UpdateVanRequst(itemsRequsts).execute();

    }



    public void addToList(Context context, JSONArray jsonArray,JSONObject jsonObject){
        new JSONTaskAddOffer(context,jsonArray,jsonObject).execute();
    }
    public void UpdateGrpupList(Context context, JSONArray jsonArray,JSONObject jsonObject){
        new JSONTaskUpdateOffer(context,jsonArray,jsonObject).execute();
    }
    public void addOfferGroupItem(Context context, JSONArray jsonArray){
        new JSONTaskAddGroupItemOffer(context,jsonArray).execute();
    }

    public void savePassowrdSetting(String passowrd) {

        new JSONTask_savePassword(passowrd).execute();
    }
    public void IIs_savePassowrdSetting(List< Password >Password) {
          getCONO();

        getaddpasswordObject(Password);
        new JSONTask_IIssavePassowrdSetting(Password).execute();
    }

    public void getPassowrdSetting() {
        new JSONTask_getPassword().execute();
    }
    public void IIs_getPassowrdSetting() {
        getCONO();
        new JSONTask_IIsgetPassword().execute();
    }

    public void updateCustomerLocatio(String cusNumber, String latitude, String longtude) {
        CustomerInfo customerInfo=new CustomerInfo();
        customerInfo.setLatit_customer(latitude);
        customerInfo.setLong_customer(longtude);
        customerInfo.setCustomerNumber(cusNumber);
        getJsonInfo(customerInfo);
        new JSONTask_IIsUpdateCustomerLocation(customerInfo).execute();
    }

    private void getJsonInfo(CustomerInfo customerInfo) {
        jsonArrayadmins = new JSONArray();

            jsonArrayadmins.put(customerInfo.getJsonObject2());


        try {
            addadminsmanobject =new JSONObject();
            addadminsmanobject.put("JSN", jsonArrayadmins);
            Log.e("Object",""+ jsonArrayadmins.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class JSONTask extends AsyncTask<String, String, String> {

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

            try {
                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                if( flag==1)
                {
                    nameValuePairs.add(new BasicNameValuePair("_ID", "8"));
                    nameValuePairs.add(new BasicNameValuePair("Update_SeenRow", jsonArrayRequest.toString().trim()));
                }
                else {
                    Log.e("rowId","BasicNameValuePair"+jsonArrayRequest.get(0).toString());
                    nameValuePairs.add(new BasicNameValuePair("_ID", "9"));
                    nameValuePairs.add(new BasicNameValuePair("Update_StateRow", jsonArrayRequest.toString().trim()));
                }

                Log.e("Update_SeenRow", "" + jsonArrayRequest.getString(0).toString());


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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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
            Log.e("tag", "onPostExecute"+s.toString());
            String impo = "";
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("SUCCESS")) {


                    Toast.makeText(main_context, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "****Success"+s.toString());


                }

            }
        }

    }

    private class JSONTask_IIsupdateRequst extends AsyncTask<String, String, String> {

        public  List<Request>requestList1;
        public  JSONTask_IIsupdateRequst(List<Request>requestList){
            this.requestList1=requestList;
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

            try {
                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {


                    URL_TO_HIT = "http://" + ipAddress+":" +portSettings.trim() + headerDll.trim()  +"/ADMUpdateAdmin_Request?CONO="
                            +CONO+"&STATUS="+requestList1.get(0).getStatuse()+"&ROW_ID="+requestList1.get(0).getRowId();
                    Log.e("URL_TO_HIT",URL_TO_HIT+"");


                           }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

               //    if( flag==1)
                {
                    Log.e("nameValuePairs",""+  requestList1.get(0));
//                    Log.e("nameValuePairs",""+  jsonArrayRequest.get(1));
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//                    nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
//                    nameValuePairs.add(new BasicNameValuePair("STATUS", requestList1.get(0).getStatuse()+""));
//                    nameValuePairs.add(new BasicNameValuePair("ROW_ID", requestList1.get(1).getRowId()+""));

//                    request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                }
//                else {
//                    Log.e("rowId","BasicNameValuePair"+jsonArrayRequest.get(0).toString());
//                    nameValuePairs.add(new BasicNameValuePair("_ID", "9"));
//                    nameValuePairs.add(new BasicNameValuePair("Update_StateRow", jsonArrayRequest.toString().trim()));
//                }






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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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
//            Log.e("tag", "onPostExecute"+s.toString());
            String impo = "";
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Saved Successfully")) {


                    Toast.makeText(main_context, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "****Success"+s.toString());


                }

            }
        }

    }

    private class JSONTaskAddSales extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        public JSONTaskAddSales(Context context,JSONObject jsonObject) {
            this.context= (EditSalesMan) context;
            this.jsonObject=jsonObject;
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

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "3"));
                nameValuePairs.add(new BasicNameValuePair("ADD_SALES_MAN",jsonObject.toString()));


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
                if (s.contains("ADD_SALES_MAN_SUCCESS")) {
                    Log.e("salesManInfo", "ADD_SALES_MAN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "ADD SALES MAN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,0);

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_AddSales extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        List<SalesManInfo>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;



        public JSONTaskIIs_AddSales(   EditSalesMan context, List<SalesManInfo> salesManInfos) {
            this.context = context;
            this.salesManInfos = salesManInfos;
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
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMAddSalesMan";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addsalesmanobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfo", "ADD_SALES_MAN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "ADD SALES MAN SUCCESS", Toast.LENGTH_SHORT).show();

                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,0);

                }else{
                    Toast.makeText(context, "Sales Man not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_DeletePlan extends AsyncTask<String, String, String> {
        Context  context;
        List<Plan_SalesMan_model>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;
        String datePlan="",salesNo="";



        public JSONTaskIIs_DeletePlan(   Context context, List<Plan_SalesMan_model> salesManInfos) {
            this.context = context;
            this.salesManInfos = salesManInfos;
            datePlan=salesManInfos.get(0).getPlan_date();
            salesNo=salesManInfos.get(0).getSalesNo();
            Log.e("savePlan","JSONTaskIIs_DeletePlan-2-");

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("salesManInfos","onPreExecute="+datePlan);
            pd = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(main_context.getResources().getString(R.string.update));
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                if (!ipAddress.equals("")) {
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMDeletePlan";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {
                pd.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Log.e("salesManInfos","doInBackground="+datePlan);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addsalesmanobject.toString().trim()));


                Log.e("JSONSTR","ADMADDPLAN="+addsalesmanobject.toString());
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
            Log.e("savePlan","JSONTaskIIs_DeletePlan-3-");
            pd.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfos",""+salesManInfos.size());
                    new JSONTaskIIs_AddPlan(context,datePlan,salesNo).execute();
                    Log.e("salesManInfo", "ADD_SALES_MAN_SUCCESS\t" + s.toString());


                }else{
                    Toast.makeText(context, "Plan  not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_AddPlan extends AsyncTask<String, String, String> {
        Context  context;
        List<Plan_SalesMan_model>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;
        String datePlan="",salesNo="";



        public JSONTaskIIs_AddPlan(   Context context,String dateP,String salesNumber) {
            this.context = context;
            this.salesManInfos = salesManInfos;
            datePlan=dateP;
            salesNo=salesNumber;
            Log.e("savePlan","JSONTaskIIs_AddPlan-4-");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(main_context.getResources().getString(R.string.update));
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                if (!ipAddress.equals("")) {
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMADDPLAN";


                Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {
                pd.dismissWithAnimation();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
               nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addsalesmanobject.toString().trim()));


                Log.e("JSONSTR","ADMADDPLAN="+addsalesmanobject.toString());
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
            Log.e("savePlan","JSONTaskIIs_AddPlan-5-");
            pd.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfo", "ADD_SALES_MAN_SUCCESS\t" + s.toString());
                    showMessageSucsess("Add Plan Successful");
                    ImportData importData =new ImportData(context);
                    importData.getPlan(salesNo,datePlan,0);


                }else{
                    Toast.makeText(context, "Plan  not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }

    private void showMessageSucsess(String add_plan_successful) {
        SweetAlertDialog pd = new SweetAlertDialog(main_context, SweetAlertDialog.SUCCESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pd.setTitleText(add_plan_successful);
        pd.setCancelable(true);
        pd.show();
    }

    private class JSONTaskIIs_UpdateSales extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        List<SalesManInfo>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;



        public JSONTaskIIs_UpdateSales(   EditSalesMan context, List<SalesManInfo> salesManInfos) {
            this.context = context;
            this.salesManInfos = salesManInfos;
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
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMUpdateSalesMan";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addsalesmanobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfo", "ADD_SALES_MAN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "Update SALES MAN SUCCESS", Toast.LENGTH_SHORT).show();

                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,0);

                }else{
                    Toast.makeText(context, "Sales Man not Updated", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }


    private class JSONTaskAddAdmin extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        public JSONTaskAddAdmin(Context context,JSONObject jsonObject) {
            this.context= (EditSalesMan) context;
            this.jsonObject=jsonObject;
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

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "32"));
                nameValuePairs.add(new BasicNameValuePair("ADD_ADMIN",jsonObject.toString()));


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
                if (s.contains("ADD_ADMIN_SUCCESS")) {
                    Log.e("salesManInfo", "ADD_ADMIN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "ADD ADMIN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,90);

                }
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTaskIIs_AddAdmin extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        List<SalesManInfo>salesManInfos=new ArrayList<>();

        public JSONTaskIIs_AddAdmin(EditSalesMan context,List<SalesManInfo> salesManInfos) {
            this.context = context;

            this.salesManInfos = salesManInfos;
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
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMAddAdmin";
                      Log.e("URL_TO_HIT",URL_TO_HIT);
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));
//
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addadminsmanobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfo", "ADD_ADMIN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "ADD ADMIN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,90);

                }
                else {
                    Toast.makeText(context, "ADMIN not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_UpdateAdmin extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        List<SalesManInfo>salesManInfos=new ArrayList<>();

        public JSONTaskIIs_UpdateAdmin(EditSalesMan context,List<SalesManInfo> salesManInfos) {
            this.context = context;

            this.salesManInfos = salesManInfos;
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
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMUpdateAdmin";
                    Log.e("URL_TO_HIT",URL_TO_HIT);
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));
//
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",addadminsmanobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                    Log.e("salesManInfo", "ADD_ADMIN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "Update ADMIN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,90);

                }
                else {
                    Toast.makeText(context, "ADMIN not Updated", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_SaveVanRequst extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        List<ItemsRequsts>itemsRequsts=new ArrayList<>();

        public JSONTaskIIs_SaveVanRequst(List<ItemsRequsts> itemsRequsts) {
            this.itemsRequsts = itemsRequsts;
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
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ExportTempLoadVan";
                    Log.e("URL_TO_HIT",URL_TO_HIT);
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));
//
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",VanRequstsjsonobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                   RequstReport. exportrespon.setText("Saved Successfully");

                }
                else {

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskIIs_UpdateVanRequst extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        List<ItemsRequsts>itemsRequsts=new ArrayList<>();

        public JSONTaskIIs_UpdateVanRequst(List<ItemsRequsts> itemsRequsts) {
            this.itemsRequsts = itemsRequsts;
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
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/UpdateExportedTempLoadVan";
                    Log.e("URL_TO_HIT",URL_TO_HIT);
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));
//
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",Updateloadvanobject.toString().trim()));


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
                if (s.contains("Saved Successfully")) {
                    Log.e("onPostExecute","Successfully");
                    RequstReport. UpdateRespon.setText("Saved Successfully");

                }
                else {

                }
//                progressDialog.dismiss();
            }
        }

    }



    private class JSONTaskUpdateSales extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        public JSONTaskUpdateSales(Context context,JSONObject jsonObject) {
            this.context= (EditSalesMan) context;
            this.jsonObject=jsonObject;
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

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "4"));
                nameValuePairs.add(new BasicNameValuePair("UPDATE_SALES_MAN",jsonObject.toString()));


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
                if (s.contains("UPDATE_SALES_MAN_SUCCESS")) {
                    Log.e("salesManInfo", "UPDATE_SALES_MAN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "UPDATE SALES MAN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,0);

                }
//                progressDialog.dismiss();
            }
        }

    }
    private class JSONTaskUpdateAdmin extends AsyncTask<String, String, String> {
        EditSalesMan  context;
        JSONObject jsonObject;
        public JSONTaskUpdateAdmin(Context context,JSONObject jsonObject) {
            this.context= (EditSalesMan) context;
            this.jsonObject=jsonObject;
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

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("_ID", "33"));
                nameValuePairs.add(new BasicNameValuePair("UPDATE_ADMIN",jsonObject.toString()));


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
                if (s.contains("UPDATE_ADMIN_SUCCESS")) {
                    Log.e("salesManInfo", "UPDATE_ADMIN_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "UPDATE ADMIN SUCCESS", Toast.LENGTH_SHORT).show();
                    context.clearTextFun();
                    globelFunction.getSalesManInfo(context,90);
                    globelFunction.updateAutho();
                }
//                progressDialog.dismiss();
            }
        }

    }

    private class JSONTaskAddOffer extends AsyncTask<String, String, String> {
        OfferPriceList  context;
        JSONArray jsonObject;
        JSONObject jsonObjectList;
        public JSONTaskAddOffer(Context context,JSONArray jsonObject,JSONObject jsonObjectList) {
            this.context= (OfferPriceList) context;
            this.jsonObject=jsonObject;
            this.jsonObjectList=jsonObjectList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationAdd = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationAdd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationAdd.setTitleText(main_context.getResources().getString(R.string.process)+"1");
            pdValidationAdd.setCancelable(false);
            pdValidationAdd.show();

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

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("_ID", "19"));
                nameValuePairs.add(new BasicNameValuePair("ADD_OFFER_PRICE",jsonObject.toString()));
                nameValuePairs.add(new BasicNameValuePair("ADD_LIST",jsonObjectList.toString()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("positionSaveIn","1222");

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("positionSaveIn", "JsonResponse\t" + JsonResponse);

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
                Log.e("positionSaveIn",""+s.toString());
                if (s.contains("SUCCESS")) {
                    Log.e("salesManInfo", "SUCCESS\t" + s.toString());
                    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                    OfferPriceList offerPriceList=(OfferPriceList) context;
                    offerPriceList.clearList();
                    //context.clearTextFun();
                   // globelFunction.getSalesManInfo(context,0);

                    pdValidationAdd.dismissWithAnimation();
                }
                else {
                    pdValidationAdd.dismissWithAnimation();
                }

//                progressDialog.dismiss();
            }else{
                Log.e("positionSaveInnull","null");
                pdValidationAdd.dismissWithAnimation();
            }
        }

    }



    private class JSONTaskUpdateOffer extends AsyncTask<String, String, String> {
        Context  context;
        JSONArray jsonObject;
        JSONObject jsonObjectList;

        public JSONTaskUpdateOffer(Context context,JSONArray jsonObject,JSONObject jsonObjectList) {
            this.context=  context;
            this.jsonObject=jsonObject;
            this.jsonObjectList=jsonObjectList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationUpdate = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationUpdate.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationUpdate.setTitleText(main_context.getResources().getString(R.string.process)+"1");
            pdValidationUpdate.setCancelable(false);
            pdValidationUpdate.show();

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

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("_ID", "38"));
                nameValuePairs.add(new BasicNameValuePair("UPDATE_OFFER_GROUP",jsonObject.toString()));
                nameValuePairs.add(new BasicNameValuePair("ADD_LIST",jsonObjectList.toString()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("positionSaveIn","1222");

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("positionSaveIn", "JsonResponse\t" + JsonResponse);

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
            ImportData importData=new ImportData(context);
            if (s != null) {
                Log.e("positionSaveIn",""+s.toString());
                if (s.contains("SUCCESS")) {

                    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                    offerGroupModels.clear();

                    importData.getAllOffers();
                    //context.clearTextFun();
                    // globelFunction.getSalesManInfo(context,0);

                    pdValidationUpdate.dismissWithAnimation();
                }
                else {
                    pdValidationUpdate.dismissWithAnimation();

                    offerGroupModels.clear();
                    importData.getAllOffers();
                }

//                progressDialog.dismiss();
            }else{
                Log.e("positionSaveInnull","null");
                pdValidationUpdate.dismissWithAnimation();

                offerGroupModels.clear();
                importData.getAllOffers();
            }
        }

    }

    private class JSONTaskUpdateList extends AsyncTask<String, String, String> {
        OfferPriceList context;
        JSONObject jsonObject;
        JSONArray jsonArray;
        public JSONTaskUpdateList(Context context,JSONObject jsonObject ,JSONArray jsonArray) {
            this.context= (OfferPriceList) context;
            this.jsonObject=jsonObject;
            this.jsonArray=jsonArray;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(main_context.getResources().getString(R.string.update));
            pd.setCancelable(false);
            pd.show();

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
                nameValuePairs.add(new BasicNameValuePair("_ID", "23"));
                nameValuePairs.add(new BasicNameValuePair("UPDATE_LIST_OFFER_PRICE",jsonObject.toString()));
                nameValuePairs.add(new BasicNameValuePair("UPDATE_OFFER_PRICE",jsonArray.toString()));


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
                Log.e("UPDATE_LIST_SUCCESS", "JsonResponse\t" + JsonResponse);

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
                    Log.e("salesManInfo", "UPDATE_LIST_SUCCESS\t" + s.toString());
                    Toast.makeText(context, "UPDATE LIST SUCCESS", Toast.LENGTH_SHORT).show();
                    context.finishLayout();
                    control.setText("main");
                    pd.dismissWithAnimation();
                }else {
                    pd.dismissWithAnimation();
                }
//                progressDialog.dismiss();
            }else {
                pd.dismissWithAnimation();
            }
        }

    }

    private class JSONTask_savePassword extends AsyncTask<String, String, String> {

        public  String passwordValue="";
        public  JSONTask_savePassword(String password_key){
            this.passwordValue=password_key;
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

            try {

                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                    Log.e("rowId","BasicNameValuePair"+passwordValue);
                    nameValuePairs.add(new BasicNameValuePair("_ID", "24"));

                nameValuePairs.add(new BasicNameValuePair("PasswordType", "1"));
                nameValuePairs.add(new BasicNameValuePair("passwordKey", passwordValue.toString()));


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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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
            Log.e("tag", "onPostExecute"+s.toString());
            String impo = "";
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("UPDATE_PASSWORD_SUCCESS")) {


                    Toast.makeText(main_context, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "****Success"+s.toString());


                }

            }
        }

    }
    private class  JSONTask_IIssavePassowrdSetting extends AsyncTask<String, String, String> {
        List <  Password>passwords;

        public JSONTask_IIssavePassowrdSetting(List<Password> passwords) {
            this.passwords = passwords;
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

            try {

                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMSavePassword";


                 Log.e("  URL_TO_HIT",  URL_TO_HIT);
                }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));


              //  Log.e("rowId","BasicNameValuePair"+passwordValue);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",passwordobject.toString().trim()));
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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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
            Log.e("tag", "onPostExecute"+s.toString());
            String impo = "";
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Saved Successfully")) {


                    Toast.makeText(main_context, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "****Success"+s.toString());


                }

            }
        }

    }
    private class JSONTask_getPassword extends AsyncTask<String, String, String> {

        public  String passwordValue="";

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

            try {

                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/admin.php";
                }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                Log.e("rowId","BasicNameValuePair"+passwordValue);
                nameValuePairs.add(new BasicNameValuePair("_ID", "25"));

                nameValuePairs.add(new BasicNameValuePair("PasswordType", "1"));


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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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

            String impo = "";
            JSONObject result=null;
            pdValidation.dismissWithAnimation();
            if (s != null) {
                if (s.contains("PasswordKeyValue")) {
                        try {
                            result = new JSONObject(s);
                            JSONArray notificationInfo = null;
                            notificationInfo = result.getJSONArray("PasswordKeyValue");
                            JSONObject infoDetail=null;
                            infoDetail = notificationInfo.getJSONObject(0);

                                Log.e("infoDetail",""+infoDetail.get("passwordKey").toString());
                            editPassword.setText(infoDetail.get("passwordKey").toString());







                        } catch (JSONException e) {
//                        progressDialog.dismiss();
                            e.printStackTrace();
                        }



                }

            }
        }

    }

    private class JSONTask_IIsgetPassword extends AsyncTask<String, String, String> {

        public  String passwordValue="";

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

            try {

                SettingModel settingModels=new SettingModel();

                settingModels =databaseHandler.getAllSetting();
                ipAddress=settingModels.getIpAddress();
                if(!ipAddress.equals(""))
                {  URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/ADMGetPassword?CONO="+CONO+"&PASSWORDTYPE=1";

                   Log.e("URL_TO_HIT",URL_TO_HIT);
                }
            }
            catch (Exception e)
            {

            }
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new  HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//
//                Log.e("rowId","BasicNameValuePair"+passwordValue);
//                nameValuePairs.add(new BasicNameValuePair("_ID", "25"));
//
//                nameValuePairs.add(new BasicNameValuePair("PasswordType", "1"));
//
//
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
                Log.e("tagUpdate", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }
            //org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();


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
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);

            String impo = "";
            JSONObject result=null;
            JSONObject jsonObject1 = null;
            pdValidation.dismissWithAnimation();
            if (respon!= null) {
                Log.e("respon",respon);
                if (respon.contains("PASSWORDTYPE")) {
                    try {


                        JSONArray requestArray = null;
                        requestArray = new JSONArray(respon);

                        for (int i = 0; i < requestArray.length(); i++) {

                            Password password = new Password();
                            jsonObject1 = requestArray.getJSONObject(i);
                            password.setPASSWORDTYPE(jsonObject1.getString("PASSWORDTYPE"));
                            password.setUSER_PASSWORD(jsonObject1.getString("PASSWORDKEY"));

                            editPassword.setText(jsonObject1.get("PASSWORDKEY").toString());
                        /*
                        result = new JSONObject(s);
                        JSONArray notificationInfo = null;
                        notificationInfo = result.getJSONArray("PasswordKeyValue");
                        JSONObject infoDetail=null;
                        infoDetail = notificationInfo.getJSONObject(0);

                        Log.e("infoDetail",""+infoDetail.get("passwordKey").toString());
                        editPassword.setText(infoDetail.get("passwordKey").toString());


*/


                        }
                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }



                }

            }
        }

    }

    private class JSONTask_IIsUpdateCustomerLocation extends AsyncTask<String, String, String> {

        public  CustomerInfo customerInfo;
        public  JSONTask_IIsUpdateCustomerLocation(CustomerInfo myCustomer){
            this.customerInfo=myCustomer;

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

            try {

                SettingModel settingModels = new SettingModel();

                settingModels = databaseHandler.getAllSetting();
                ipAddress = settingModels.getIpAddress();
                if (!ipAddress.equals(""))// CUSTNO ,LA,LO
                {
//                    URL_TO_HIT = "http://" + ipAddress + ":" + portSettings + headerDll.trim() + "/ADMUpdateCustLocation?CONO=" + CONO + "&CUSTNO=" + customerInfo.getCustomerNumber() +
//                            "&LA=" + customerInfo.getLatit_customer() + "&LO=" + customerInfo.getLong_customer();
                    URL_TO_HIT = "http://" + ipAddress + ":" + portSettings + headerDll.trim() + "/ADMUpdateCustLocation";
                    Log.e("URL_TO_HIT", "updateLocation" + URL_TO_HIT);
                }
            } catch (Exception e) {
                pdValidation.dismissWithAnimation();
            }

            String JsonResponse = null;
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            try {
                request.setURI(new URI(URL_TO_HIT));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
            nameValuePairs.add(new BasicNameValuePair("JSONSTR", addadminsmanobject.toString().trim()));
            Log.e("URL_TO_HIT", "CONO" + CONO);

            try {
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer("");
            try {

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(response.getEntity().getContent()));


            String line = "";

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

            in.close();
        }catch (Exception e){

            }

            JsonResponse = sb.toString();
            Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

            return JsonResponse;
        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);

            String impo = "";
            JSONObject result=null;
            JSONObject jsonObject1 = null;
            pdValidation.dismissWithAnimation();
            if (respon!= null) {
                Log.e("respon",respon);
                if (respon.contains("PASSWORDTYPE")) {




                }

            }
        }

    }
    private class JSONTaskAddGroupItemOffer extends AsyncTask<String, String, String> {
        GroupOffer  context;
        JSONArray jsonArrayData;
        JSONObject jsonObjectList;
        public JSONTaskAddGroupItemOffer(Context context,JSONArray jsonObject) {
            this.context= (GroupOffer) context;
            this.jsonArrayData=jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdValidationAdd = new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationAdd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationAdd.setTitleText(main_context.getResources().getString(R.string.process)+"1");
            pdValidationAdd.setCancelable(false);
            pdValidationAdd.show();

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
                        pdValidationAdd.dismissWithAnimation();
                        addList.setEnabled(true);
                    }
                });



            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("_ID", "36"));
                nameValuePairs.add(new BasicNameValuePair("ADD_OFFER_GROUP",jsonArrayData.toString()));


                Log.e("jsonArraySerial","get5="+jsonArrayData);
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("positionSaveIn","1222");

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("positionSaveIn", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();


                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        addList.setEnabled(true);

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                        pdValidationAdd.dismissWithAnimation();
                    }
                });


                return null;
            } catch (Exception e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdValidationAdd.dismissWithAnimation();
                        addList.setEnabled(true);
                    }
                });
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                Log.e("positionSaveIn",""+s.toString());
                if (s.contains("SUCCESS")) {
                    Log.e("salesManInfo", "SUCCESS\t" + s.toString());
                    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                    GroupOffer offerPriceList=(GroupOffer) context;
                    offerPriceList.clearList();
                    //context.clearTextFun();
                    // globelFunction.getSalesManInfo(context,0);

                    pdValidationAdd.dismissWithAnimation();
                }
                else {
                    pdValidationAdd.dismissWithAnimation();
                }

//                progressDialog.dismiss();
            }else{
                addList.setEnabled(true);
                Log.e("positionSaveInnull","null");
                pdValidationAdd.dismissWithAnimation();
            }
        }

    }

    private class JSONTask_AddTarget extends AsyncTask<String, String, String> {
        Context  context;
        List<SalesManInfo>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;



        public JSONTask_AddTarget(   Context context, List<TargetDetalis> targetDetalisList) {
            this.context = context;
            this.salesManInfos = salesManInfos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdSweetAlertDialog2= new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdSweetAlertDialog2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdSweetAlertDialog2.setTitleText(main_context.getResources().getString(R.string.process));
            pdSweetAlertDialog2.setCancelable(false);
            pdSweetAlertDialog2.show();
        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/SaveTaget";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {
                pdSweetAlertDialog2.dismiss();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    pdSweetAlertDialog2.dismiss();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",Targetobject.toString().trim()));


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
                pdSweetAlertDialog2.dismiss();
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
                pdSweetAlertDialog2.dismiss();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdSweetAlertDialog2.dismiss();
            Log.e("onPostExecute", "==\t" + s);
            if (s != null) {
                if (s.contains("Saved Successfully")) {
                    GlobelFunction.showSweetDialog(context,1,context.getResources().getString(R.string.saveSuccessfuly),"");

                }else{
                    Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }else{
                Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

            }
        }

    }
    private class JSONTask_AddTarget2 extends AsyncTask<String, String, String> {
        Context  context;
        List<SalesManInfo>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;



        public JSONTask_AddTarget2(   Context context, List<TargetDetalis> targetDetalisList) {
            this.context = context;
            this.salesManInfos = salesManInfos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdSweetAlertDialog= new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdSweetAlertDialog.setTitleText(main_context.getResources().getString(R.string.process));
            pdSweetAlertDialog.setCancelable(false);
            pdSweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/SaveItemsTarget";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {
                pdSweetAlertDialog.dismiss();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    pdSweetAlertDialog.dismiss();
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",Targetobject.toString().trim()));


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
                pdSweetAlertDialog.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                pdSweetAlertDialog.dismiss();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdSweetAlertDialog.dismiss();
            Log.e("onPostExecute", "==\t" + s);
            if (s != null) {
                if (s.contains("Saved Successfully")) {
                    GlobelFunction.showSweetDialog(context,1,context.getResources().getString(R.string.saveSuccessfuly),"");

                }else{
                    Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }else{
                Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

            }
        }

    }
    private class JSONTask_AddCommissionTarget extends AsyncTask<String, String, String> {
        Context  context;
        List<SalesManInfo>salesManInfos=new ArrayList<>();
        JSONObject jsonObject;



        public JSONTask_AddCommissionTarget(   Context context, List<CommissionTarget> targetDetalisList) {
            this.context = context;
            this.salesManInfos = salesManInfos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            pdSweetAlertDialog= new SweetAlertDialog(main_context, SweetAlertDialog.PROGRESS_TYPE);
            pdSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdSweetAlertDialog.setTitleText(main_context.getResources().getString(R.string.process));
            pdSweetAlertDialog.setCancelable(false);
            pdSweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

//            ipAddress = "";
            try {


                if (!ipAddress.equals("")) {
                    http://localhost:8085/ADMAddSalesMan?CONO=295
                    URL_TO_HIT = "http://" + ipAddress+":"+portSettings +  headerDll.trim() +"/SaveItemsTarget";


                    Log.e("URL_TO_HI",URL_TO_HIT);


                }


            } catch (Exception e) {
                pdSweetAlertDialog.dismiss();
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    pdSweetAlertDialog.dismiss();
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR",Comm_Targetobject.toString().trim()));


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
                pdSweetAlertDialog.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(main_context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                pdSweetAlertDialog.dismiss();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdSweetAlertDialog.dismiss();
            Log.e("onPostExecute", "==\t" + s);
            if (s != null) {
                if (s.contains("Saved Successfully")) {
                    GlobelFunction.showSweetDialog(context,1,context.getResources().getString(R.string.saveSuccessfuly),"");

                }else{
                    Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.dismiss();
            }else{
                Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
