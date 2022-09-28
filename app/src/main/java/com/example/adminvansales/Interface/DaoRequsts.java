package com.example.adminvansales.Interface;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.model.RequstTest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaoRequsts {
    private DatabaseReference databaseReference,databaseReference2;
    Context context;
    DataBaseHandler mHandler;

    String ipAddress="";
  public static String  Firebase_ipAddress;
    public DaoRequsts(Context context) {
        this.mHandler = new DataBaseHandler(context);
        this.context = context;
        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();



        if(mHandler.getAllSetting()!= null) {
            ipAddress = mHandler.getAllSetting().getIpAddress();
            Firebase_ipAddress= ipAddress.replace(".", "_");
            Log.e("ipAddress==",Firebase_ipAddress);
        }

        databaseReference2 = dbroot.getReference(RequstTest.class.getSimpleName()).child(Firebase_ipAddress);




        Log.e("addRequst==","addRequst");



    }
    public boolean ChildIsExists(String value) {
        Log.e("ChildIsExists","ChildIsExists");
        final boolean[] flage = {false};
        databaseReference2.child(Firebase_ipAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(value)) {
                    {
                        Log.e("true","true");
                        flage[0] =true;
                    }
                } else {
                    Log.e("false","false");
                    flage[0] =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return flage[0];  }

    public void addRequst(RequstTest requsts){
       if(!ChildIsExists(requsts.getKey_validation())) {
           databaseReference2.child(requsts.getKey_validation()).setValue(requsts).addOnSuccessListener(new OnSuccessListener<Void>() {

               @Override
               public void onSuccess(Void aVoid) {
                   Toast.makeText(context, "New requst Data stored successfully", Toast.LENGTH_SHORT).show();
                   Log.e("onSuccess==", "add");
               }
           });
           databaseReference2.child(requsts.getKey_validation()).setValue(requsts).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Log.e("Exception==", e.getMessage() + "");
               }
           });

       }
        else
        Toast.makeText(context, "child is exsits", Toast.LENGTH_SHORT).show();

    }

}
