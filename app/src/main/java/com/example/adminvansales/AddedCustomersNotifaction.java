package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.adminvansales.Adapters.AddedCustomerAdapter;
import com.example.adminvansales.Adapters.adapterrequst;
import com.example.adminvansales.model.NewAddedCustomer;
import com.example.adminvansales.model.RequstTest;
import com.example.adminvansales.model.SettingModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddedCustomersNotifaction extends AppCompatActivity {
    RecyclerView reqrec;
    private DatabaseReference databaseReference;
    ArrayList<NewAddedCustomer> requstsArrayAdapter=new ArrayList<>();
    AddedCustomerAdapter adapterrequst1;
    public static String  Firebase_ipAddress;
    public  static String ipAddress="";
    private DataBaseHandler databaseHandler;
    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_customers_notifaction);
        init ();
        if(isNetworkAvailable(AddedCustomersNotifaction.this))
            Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show();

        else      Toast.makeText(this, "Network not Available", Toast.LENGTH_SHORT).show();

        reqrec=findViewById(R.id.rec);
        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        Log.e("Firebase_ipAddress==", Firebase_ipAddress+"");
        databaseReference = dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                NewAddedCustomer post = dataSnapshot.getValue(NewAddedCustomer.class);
                //   Log.e("post",post.getKey_validation()+"");
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("databaseError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child(Firebase_ipAddress).addValueEventListener(postListener);
        databaseReference.keepSynced(true);//Keeping Data Fresh

        Log.e("getRoot==", databaseReference.getRoot()+""+ databaseReference.getPath());



        getlistofdata("");

//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String key = ds.getKey();
//                    Log.e("Customerkey==",key+"");
//                    getlistofdata(key);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        databaseReference.addListenerForSingleValueEvent(valueEventListener);




    }

    void init (){
        databaseHandler = new DataBaseHandler(AddedCustomersNotifaction.this);
        requstsArrayAdapter.clear();
        SettingModel settingModel = new SettingModel();
        settingModel = databaseHandler.getAllSetting();
        ipAddress = settingModel.getIpAddress();
        Firebase_ipAddress= ipAddress.replace(".", "_");
    }

    private void filladapter() {
        Log.e("requstsArrayAdapter", "requstsArrayAdapter:" +requstsArrayAdapter.size());
//                       for(int i=0;i<requstsArrayAdapter.size();i++)
//                           for(int j=0;j<requstsArrayAdapter.size();j++)
//                          if(requstsArrayAdapter.get(i).getTELEPHONE().equals(requstsArrayAdapter.get(j).getTELEPHONE())) {
//                              requstsArrayAdapter.remove(i);
//                              i--;
//                          }

        reqrec.setLayoutManager(new LinearLayoutManager(AddedCustomersNotifaction.this));

        adapterrequst1=new AddedCustomerAdapter(requstsArrayAdapter,AddedCustomersNotifaction.this);
        reqrec.setAdapter(adapterrequst1);

    }
    private void getlistofdata(String key) {
        Log.e("Addedcustomergetlistofdata==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, add it to the displayed list
                try {

                    NewAddedCustomer addedCustomer = dataSnapshot.getValue(NewAddedCustomer.class);
                    if(addedCustomer!=null) {

                        requstsArrayAdapter.add(addedCustomer);
                            filladapter();
                            GlobelFunction globelFunction = new GlobelFunction(AddedCustomersNotifaction.this);
                            String salmanname = addedCustomer.getSalesMan();

                            //displayNotification(AddedCustomersNotifaction.this, "New Requst From" + salmanname, "");



                    }
                }catch (Exception e){
                    Log.e("Exception", "Exception:" + e.getMessage());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());


                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                try {
                    NewAddedCustomer requstTest = dataSnapshot.getValue(NewAddedCustomer.class);
                    String commentKey = dataSnapshot.getKey();
                    for(int i=0;i<requstsArrayAdapter.size();i++)
                        if (requstsArrayAdapter.get(i).getTELEPHONE().equals(requstTest.getTELEPHONE()))
                            requstsArrayAdapter.remove(i);

                  filladapter();
                }catch (Exception e){
                    Log.e("onChildRemoved", "Exception:" + e.getMessage());
                }


                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
//                RequstTest movedComment = dataSnapshot.getValue(RequstTest.class);
//                String commentKey = dataSnapshot.getKey();
//                requstsArrayAdapter.add(movedComment);
//                filladapter();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onChildAdded", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(AddedCustomersNotifaction.this, "Failed to load comments.", Toast.LENGTH_LONG).show();

            }
        };
        FirebaseDatabase.getInstance().getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress).addChildEventListener(childEventListener);
    }
    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //create channel for API >= 26
    public static void displayNotification(Context context,String title,String body){


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorblue_dark))
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))

                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                // .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +context.getPackageName()+"/"+R.raw.messege))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat =  NotificationManagerCompat.from(context);
        managerCompat.notify(1,mBuilder.build());
    }



}