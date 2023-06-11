package com.example.adminvansales.Interface;

import android.content.Context;
import android.util.Log;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.model.NewAddedCustomer;
import com.example.adminvansales.model.RequstTest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public  class CustomerDao {
    private DatabaseReference databaseReference;
    Context context;
    DataBaseHandler mHandler;
    FirebaseDatabase dbroot;
    String ipAddress="";
    public static String  Firebase_ipAddress;
    public CustomerDao(Context context) {
        this.mHandler = new DataBaseHandler(context);
        this.context = context;
        dbroot = FirebaseDatabase.getInstance();
        if(mHandler.getAllSetting()!= null) {
            ipAddress = mHandler.getAllSetting().getIpAddress();
            Firebase_ipAddress= ipAddress.replace(".", "_");
            if(Firebase_ipAddress.contains(":"))    Firebase_ipAddress= Firebase_ipAddress.substring(0, Firebase_ipAddress.indexOf(":"));
            Log.e("ipAddress==",Firebase_ipAddress);
        }

        databaseReference = dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress);

    }
    public void  deleteRequst(String RequstID){
        //Log.e("deleteRequst", "deleteRequst:deleteRequst");
        dbroot.getReference(NewAddedCustomer.class.getSimpleName()).child(Firebase_ipAddress)
                .   child(RequstID)
                .removeValue();
    }
}
