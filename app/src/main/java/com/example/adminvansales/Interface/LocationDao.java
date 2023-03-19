package com.example.adminvansales.Interface;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.SalesmanMaps_FirebaseActivity;
import com.example.adminvansales.model.SalesManInfo;
import com.example.adminvansales.model.SalesMenLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LocationDao {
   private DatabaseReference databaseReference,databaseReference2;
   Context context;
   public static List<SalesManInfo> salesManInfos=new ArrayList<>();

   public static  List<LatLng>LatLngListMarker=new ArrayList<>();
   DataBaseHandler mHandler;
   String ipAddress="";
   public static String  Firebase_ipAddress;
   public LocationDao(Context context) {
      this.mHandler = new DataBaseHandler(context);
      this.context = context;
      FirebaseApp.initializeApp(context);
      FirebaseDatabase dbroot = FirebaseDatabase.getInstance();



      if(mHandler.getAllSetting()!= null) {
         ipAddress = mHandler.getAllSetting().getIpAddress();
         Firebase_ipAddress= ipAddress.replace(".", "_");
         if(Firebase_ipAddress.contains(":"))  Firebase_ipAddress= Firebase_ipAddress.substring(0, Firebase_ipAddress.indexOf(":"));
         Log.e("ipAddress==",Firebase_ipAddress);
      }

      databaseReference = dbroot.getReference(SalesMenLocation.class.getSimpleName());
      databaseReference2= dbroot.getReference(SalesMenLocation.class.getSimpleName()) .  child(Firebase_ipAddress);
   ;






   }
   public void addLocation(SalesMenLocation salesMenLocation){
      Log.e("addLocation==", "addLocation");
      if(!ChildIsExists(salesMenLocation.getSalesmanNo())) {
         databaseReference.child(Firebase_ipAddress).child(salesMenLocation.getSalesmanNo()).setValue(salesMenLocation).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
               Log.e("addLocation==", "databaseReference"+databaseReference.getRoot());
               Log.e("onSuccess==", "New salesMenLocation Data stored successfully");

            }
         });
         databaseReference.child(Firebase_ipAddress).child(salesMenLocation.getSalesmanNo()).setValue(salesMenLocation).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Log.e("Exception==", e.getMessage() + "");
            }
         });

      }
      else
         Toast.makeText(context, "child is exsits", Toast.LENGTH_SHORT).show();

   }
   public boolean ChildIsExists(String value) {
   //   Log.e("ChildIsExists","ChildIsExists");
      final boolean[] flage = {false};
      databaseReference.child(Firebase_ipAddress).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.hasChild(value)) {
               {
               //   Log.e("true","true");
                  flage[0] =true;
               }
            } else {
          //     Log.e("false","false");
               flage[0] =false;
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
      return flage[0];  }
   public void allTaskInFireBase() {
      FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
      databaseReference = dbroot.getReference(SalesMenLocation.class.getSimpleName()).child(Firebase_ipAddress);

      ValueEventListener valueEventListener = new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren()) {
               String key = ds.getKey();

             //  Log.e("LocationDaokey==",key+"");
               getlistofdata(key);
            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {}
      };
      databaseReference.addListenerForSingleValueEvent(valueEventListener);


   }
   public void getlistofdata(String key) {
    //  Log.e("LocationDaogetlistofdata==", "getlistofdata");
    ChildEventListener childEventListener = new ChildEventListener() {
         @Override
         public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
      //      Log.e("LocationDaoonChildAdded", "onChildAdded:" + dataSnapshot.getKey());

            // A new comment has been added, add it to the displayed list




               SalesMenLocation salesMenLocation = dataSnapshot.getValue(SalesMenLocation.class);
               if(salesMenLocation!=null) {
                //  Log.e("salesMenLocation", "salesMenLocation:" + salesMenLocation.getSalesmanName() + "  " + salesMenLocation.getLatitude() + " " + salesMenLocation.getLongitude());
                  SalesManInfo salesManInfo = new SalesManInfo();
                  salesManInfo.setSalesManNo(salesMenLocation.getSalesmanNo());
                  salesManInfo.setSalesName(salesMenLocation.getSalesmanName());
                  salesManInfo.setLatitudeLocation(salesMenLocation.getLatitude());
                  salesManInfo.setLongitudeLocation(salesMenLocation.getLongitude());
                  salesManInfos.add(salesManInfo);
              //    LatLngListMarker.add(new LatLng(Double.parseDouble(salesMenLocation.getLatitude()), Double.parseDouble(salesMenLocation.getLongitude())));
                  SalesmanMaps_FirebaseActivity salesmanMapsActivity = (SalesmanMaps_FirebaseActivity) context;

               salesmanMapsActivity.location2();


               }
            // ...
         }

         @Override
         public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
         //   Log.e("LocationDao,onChildChanged", ":" + dataSnapshot.getKey());

            SalesMenLocation salesMenLocation = dataSnapshot.getValue(SalesMenLocation.class);
            if(salesMenLocation!=null) {
               for (int i=0;i<salesManInfos.size();i++)
                  if (salesManInfos.get(i).getSalesManNo().equals(salesMenLocation.getSalesmanNo())) {
                     salesManInfos.get(i).setLongitudeLocation(salesMenLocation.getLongitude());
                     salesManInfos.get(i).setLatitudeLocation(salesMenLocation.getLatitude());
                    // LatLngListMarker.set(i, new LatLng(Double.parseDouble(salesMenLocation.getLatitude()), Double.parseDouble(salesMenLocation.getLongitude())));
                  }
            }
            SalesmanMaps_FirebaseActivity salesmanMapsActivity = (SalesmanMaps_FirebaseActivity) context;

           salesmanMapsActivity.location2();
         }

         @Override
         public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.e("onChildAdded", "onChildRemoved:" + dataSnapshot.getKey());


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
      databaseReference2.addChildEventListener(childEventListener);
   }
}
