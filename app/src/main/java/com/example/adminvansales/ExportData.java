package com.example.adminvansales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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

import static com.example.adminvansales.LogIn.ipAddress;
import static com.example.adminvansales.MainActivity.isListUpdated;

public class ExportData {
    private DataBaseHandler databaseHandler;
    private JSONArray jsonArrayRequest;
    private String URL_TO_HIT ;
    public static  SweetAlertDialog pd,pdValidation,pdValidationDialog;

    Context main_context;
    int flag=0;
    ArrayList<Integer> listIdUpdate=new ArrayList();
    public ExportData(Context context) {
        databaseHandler = new DataBaseHandler(context);
        this.main_context=context;


    }
    public  void updateRowState(String rowId ,String state){
        flag=2;
        jsonArrayRequest = new JSONArray();

        Log.e("rowId",""+rowId+"\t"+state);

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

                ipAddress=databaseHandler.getAllSetting();
                if(!ipAddress.equals(""))
                {
                    URL_TO_HIT = "http://" + ipAddress + "/VANSALES_WEB_SERVICE/index.php";
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
}
