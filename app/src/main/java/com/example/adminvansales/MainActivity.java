package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.example.adminvansales.Adapters.RequestAdapter;
import com.example.adminvansales.model.Request;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.listId;
import static com.example.adminvansales.ImportData.listRequest;

public class MainActivity extends AppCompatActivity {
    public  static LinearLayoutManager layoutManager;
    public  static RecyclerView recyclerView;
    ArrayList<Request> requestList1;
    public  static  TextView Requstrespon;
    public  static  boolean isListUpdated=false;
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel settingModel;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingModel=new com.example.adminvansales.model.SettingModel ();
        databaseHandler=new DataBaseHandler(MainActivity.this);
        initialView();
        getData();

        fillData(MainActivity.this);
//        getData();

        String s="";
        timer = new Timer();

 //       fillData(MainActivity.this);
  //   updateSeenOfRow();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        // update ui here
                        Log.e("listId==",listId.size()+"");
                        if (isNetworkAvailable()) {
                          getData();
                            if(listId.size()!=0)
                            {

                        fillData(MainActivity.this);
//                                updateSeenOfRow();
                            }
                        }

                    }
                });
            }

        }, 0, 3000);




    }

    private void getData() {
        ImportData importData=new ImportData(MainActivity.this);


        settingModel=databaseHandler.getAllSetting();
        if( settingModel.getImport_way().equals("0"))
            importData.getListRequest();
        else if( settingModel.getImport_way().equals("1"))
           importData.IIS_getListRequest();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void updateSeenOfRow() {

        ExportData exportData=new ExportData(MainActivity.this);

        settingModel=databaseHandler.getAllSetting();

            exportData.updateRowSeen(listId);




    }

    public static  void fillData(Context context) {
        isListUpdated=false;
        fillListNotification(context,listRequest);
    }


    private void initialView() {
//        databaseHandler=new DatabaseHandler(RequestCheque.this);
        recyclerView = findViewById(R.id.recycler);
        Requstrespon=findViewById(R.id.Requstrespon);
        Requstrespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().length() != 0) {

                    if (s.equals("respon")) {
                        if (listId.size() != 0)
                            fillData(MainActivity.this);
                    }
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public static   void fillListNotification(Context context,ArrayList<Request> notifications) {
//        notifiList1.clear();
//        notifiList1 = notifications;
//        Log.e("fillListNotification", "" + .size());
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();


    }
    public  static void runAnimation(RecyclerView recyclerView, int type) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;
        if (type == 0) {
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_filldown);
            RequestAdapter notificationAdapter = new RequestAdapter(context, listRequest);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
    }
}
