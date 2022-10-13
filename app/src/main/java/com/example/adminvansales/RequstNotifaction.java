package com.example.adminvansales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.adminvansales.model.RequstTest;
import com.example.adminvansales.Adapters.adapterrequst;
import com.example.adminvansales.model.SettingModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequstNotifaction extends AppCompatActivity {
    RecyclerView reqrec;
    private DatabaseReference databaseReference;
    ArrayList<RequstTest> requstsArrayAdapter=new ArrayList<>();
    adapterrequst adapterrequst1;
    public static String  Firebase_ipAddress;
    public  static String ipAddress="";
    private DataBaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requst_notifaction);
        databaseHandler = new DataBaseHandler(RequstNotifaction.this);
        SettingModel settingModel = new SettingModel();
        settingModel = databaseHandler.getAllSetting();
        ipAddress = settingModel.getIpAddress();
        Firebase_ipAddress= ipAddress.replace(".", "_");
       if(isNetworkAvailable(RequstNotifaction.this))
           Log.e("NetworkAvailable","NetworkAvailable");
       else     Log.e("NetworkAvailable","not NetworkAvailable");

    reqrec=findViewById(R.id.reqrec);
    FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        Log.e("Firebase_ipAddress==", Firebase_ipAddress+"");
    databaseReference = dbroot.getReference(RequstTest.class.getSimpleName()).child("10_0_0_22");

        databaseReference.keepSynced(true);//Keeping Data Fresh

        Log.e("getRoot==", databaseReference.getRoot()+""+ databaseReference.getPath());
  //getdata();
    getlistofdata();
        databaseReference.child("46523").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RequstTest post = snapshot.getValue(RequstTest.class);
                Log.e("post",post.getKey_validation()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    //filladapter();
}
    private void getdata() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                RequstTest post = dataSnapshot.getValue(RequstTest.class);
                Log.e("post",post.getKey_validation()+"");
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("databaseError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child(Firebase_ipAddress).addValueEventListener(postListener);
    }
    private void filladapter() {
        Log.e("requstsArrayAdapter", "requstsArrayAdapter:" +requstsArrayAdapter.size());
        reqrec.setLayoutManager(new LinearLayoutManager(RequstNotifaction.this));

        adapterrequst1=new adapterrequst(requstsArrayAdapter,RequstNotifaction.this);
        reqrec.setAdapter(adapterrequst1);

    }
    private void getlistofdata() {
        Log.e("getlistofdata==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                RequstTest requstTest = dataSnapshot.getValue(RequstTest.class);
                if(requstTest.getStatus().equals("0"))
                requstsArrayAdapter.add(requstTest);
                filladapter();
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
//
//                mBuilder.setContentTitle("Notification Alert, Click Me!");
//                mBuilder.setContentText("Hi, This is Android Notification Detail!");
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(MainActivity.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//                mNotificationManager.notify(1, mBuilder.build());
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                RequstTest requstTest = dataSnapshot.getValue(RequstTest.class);
                String commentKey = dataSnapshot.getKey();
                if(requstTest.getStatus().equals("0"))    requstsArrayAdapter.add(requstTest);
                filladapter();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                RequstTest requstTest = dataSnapshot.getValue(RequstTest.class);
                String commentKey = dataSnapshot.getKey();
                for(int i=0;i<requstsArrayAdapter.size();i++)
                    if (requstsArrayAdapter.get(i).getKey_validation().equals(requstTest.getKey_validation()))
                        requstsArrayAdapter.remove(i);

                filladapter();

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
                Toast.makeText(RequstNotifaction.this, "Failed to load comments.", Toast.LENGTH_LONG).show();

            }
        };
        databaseReference.addChildEventListener(childEventListener);
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
}