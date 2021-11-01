package com.example.adminvansales;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.adminvansales.model.SettingModel;

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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.GroupOffer.addList;
import static com.example.adminvansales.HomeActivity.editPassword;
import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.Report.ListOfferReport.control;

public class ExportData {
    private DataBaseHandler databaseHandler;
    private JSONArray jsonArrayRequest;
    private String URL_TO_HIT ;
    public static  SweetAlertDialog pd,pdValidation,pdValidationDialog;
    GlobelFunction globelFunction;
    SweetAlertDialog pdValidationAdd;


    Context main_context;
    int flag=0;
    ArrayList<Integer> listIdUpdate=new ArrayList();
    public ExportData(Context context) {
        databaseHandler = new DataBaseHandler(context);
        this.main_context=context;
        globelFunction=new GlobelFunction(context);

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

    public void AddSales(Context context,JSONObject jsonObject){
        new JSONTaskAddSales(context,jsonObject).execute();
    }

    public void AddAdmins(Context context,JSONObject jsonObject){
        new JSONTaskAddAdmin(context,jsonObject).execute();
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

    public void addToList(Context context, JSONArray jsonArray,JSONObject jsonObject){
        new JSONTaskAddOffer(context,jsonArray,jsonObject).execute();
    }
    public void addOfferGroupItem(Context context, JSONArray jsonArray){
        new JSONTaskAddGroupItemOffer(context,jsonArray).execute();
    }

    public void savePassowrdSetting(String passowrd) {

        new JSONTask_savePassword(passowrd).execute();
    }

    public void getPassowrdSetting() {
        new JSONTask_getPassword().execute();
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



}
