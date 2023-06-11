package com.example.adminvansales.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.Interface.CustomerDao;
import com.example.adminvansales.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;

public class MyServicesForNotification  extends Service {
    private static final String TAG = "BackgroundFireServiceS";
    MediaPlayer player;
    FirebaseDatabase db;
    static String id;
    String activity;
    public static final String ServiceIntent = "MyServices";
    public static boolean ServiceWork = true;

    Timer timer;
    DatabaseReference databaseReference;
    DatabaseReference CustomersdatabaseReference;
    double v1,v2;
    public static String  Firebase_ipAddress;
    DataBaseHandler mHandler;
    FirebaseDatabase dbroot;
    String ipAddress="";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind()");
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new DataBaseHandler(MyServicesForNotification.this);
        if(mHandler.getAllSetting()!= null) {
            ipAddress = mHandler.getAllSetting().getIpAddress();
            Firebase_ipAddress= ipAddress.replace(".", "_");
            if(Firebase_ipAddress.contains(":"))    Firebase_ipAddress= Firebase_ipAddress.substring(0, Firebase_ipAddress.indexOf(":"));
            Log.e("ipAddress==",Firebase_ipAddress);
        }

        FirebaseApp.initializeApp(MyServicesForNotification.this);
        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
databaseReference = dbroot.getReference(RequstTest.class.getSimpleName()).child(Firebase_ipAddress);
        CustomersdatabaseReference = dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress);
        allTaskInFireBasewithoutnotify();
        Log.e(TAG, "onCreate() , service started..."+id);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //requestLocationUpdates();

        allTaskInFireBase();
        allCustomersTaskInFireBase();
        onDestroy();
        Log.e(TAG, "onStartCommand() , service started..."+id);

        return START_STICKY;

    }
    @Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onStop() , service Stop..."+id);
        return super.stopService(name);

    }

    public void onPause() {
        Log.e(TAG, "onPause()");
    }

    @Override
    public void onDestroy() {

        allTaskInFireBasewithoutnotify();
        Log.e(TAG, "onCreated() , service stopped..."+id);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory()");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        allTaskInFireBase();
        allCustomersTaskInFireBase();
        Log.e(TAG, "In onTaskRemoved");
    }
    void allTaskInFireBase() {


        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        databaseReference = dbroot.getReference(RequstTest.class.getSimpleName()).child(Firebase_ipAddress);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Log.e("key==",key+"");
        getlistofdata(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


    }
    void allCustomersTaskInFireBase() {
        Log.e(TAG, "allCustomersTaskInFireBase");
        getCustomerslistofdata("");
//        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
//        databaseReference = dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress);
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String key = ds.getKey();
//                    Log.e("key==",key+"");
//                    getCustomerslistofdata(key);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        databaseReference.addListenerForSingleValueEvent(valueEventListener);


    }


    void allTaskInFireBasewithoutnotify() {
        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        databaseReference = dbroot.getReference(RequstTest.class.getSimpleName()).child(Firebase_ipAddress);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Log.e("key==",key+"");
                    getlistofdatawithoutnotify(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


    }
    private void getlistofdata(String key) {
        Log.e("getlistofdata==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                try {
                    RequstTest requstTest = dataSnapshot.getValue(RequstTest.class);
                    if(requstTest!=null) {
                        if (requstTest.getStatus()!=null && requstTest.getStatus().equals("0")) {
                            String salmanname = requstTest.getSalesman_name();

                            displayNotification(MyServicesForNotification.this, "New Requst From " + salmanname, "");


                        }
                    }
                }catch (Exception e){
                    Log.e("Exception", "Exception:" + e.getMessage());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.


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

            }
        };
        databaseReference.child(key).addChildEventListener(childEventListener);
    }
    private void getCustomerslistofdata(String key) {
        Log.e("getCustomerslistofdata==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("getCustomerslistofdata", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                try {
                    NewAddedCustomer addedCustomer = dataSnapshot.getValue(NewAddedCustomer.class);
                    if(addedCustomer!=null) {

                            String salmanname = addedCustomer.getSalesMan();
                        Log.e("displayNotification", "displayNotification:" + dataSnapshot.getKey());
                            displayNotification(MyServicesForNotification.this, "New Added Customer Requst From " + salmanname, "");



                    }
                }catch (Exception e){
                    Log.e("Exception", "Exception:" + e.getMessage());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.


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

            }
        };
        CustomersdatabaseReference.addChildEventListener(childEventListener);
    }
    private void getlistofdatawithoutnotify(String key) {
        Log.e("getlistofdata==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                try {
                    RequstTest requstTest = dataSnapshot.getValue(RequstTest.class);
                    if(requstTest!=null) {
                        if (requstTest.getStatus()!=null && requstTest.getStatus().equals("0")) {
                            String salmanname = requstTest.getSalesman_name();

                        //    displayNotification(MyServicesForNotification.this, "New Requst From " + salmanname, "");


                        }
                    }
                }catch (Exception e){
                    Log.e("Exception", "Exception:" + e.getMessage());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.


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

            }
        };
        databaseReference.child(key).addChildEventListener(childEventListener);
    }
    public static void displayNotification(Context context, String title, String body){
        String channelId="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
          channelId = "Channel_id";

            // The user-visible name of the channel.
            CharSequence channelName = "Application_name";
            // The user-visible description of the channel.
            String channelDescription = "Application_name Alert";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            boolean channelEnableVibrate = true;
//            int channelLockscreenVisibility = Notification.;

            // Initializes NotificationChannel.
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);
//            notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);

            Log.e("Channel_id", "Channel_id:");
        } else {
            // Returns null for pre-O (26) devices.
            Log.e("else", "else:");
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,channelId)
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


        Log.e("end", "end:");


    }


    void allCustomersTaskInFireBasewithoutnotify() {
        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        databaseReference = dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Log.e("key==",key+"");
                    getlistofCustomerdatawithoutnotify(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


    }
    private void getlistofCustomerdatawithoutnotify(String key) {
        Log.e("getlistofCustomerdatawithoutnotify==", "getlistofdata");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                try {
                    NewAddedCustomer addedCustomer = dataSnapshot.getValue(NewAddedCustomer.class);
                    if(addedCustomer!=null) {

                            String salmanname = addedCustomer.getSalesMan();

                    }
                }catch (Exception e){
                    Log.e("Exception", "Exception:" + e.getMessage());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildChanged:" + dataSnapshot.getKey());


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());




                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("onChildAdded", "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onChildAdded", "postComments:onCancelled", databaseError.toException());

            }
        };
        CustomersdatabaseReference.child(key).addChildEventListener(childEventListener);
    }
}
