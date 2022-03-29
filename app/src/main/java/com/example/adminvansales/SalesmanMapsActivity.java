package com.example.adminvansales;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.adminvansales.GlobelFunction.LatLngListMarker;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;

public class SalesmanMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLngBounds.Builder builder;
    LatLngBounds bounds;
    private Timer timer;
    private OnMapReadyCallback callback;

    Handler locationHandler;
    final static long REFRESH = 1 * 1000;
    final static int SUBJECT = 0;
    int existLocation=0;
    double latit=0,longtud=0;
boolean flag=false;
GlobelFunction globelFunction;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_maps);
        try {


        builder = new LatLngBounds.Builder();
        globelFunction=new GlobelFunction(SalesmanMapsActivity.this);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(flag){
                    globelFunction.getSalesManInfo(SalesmanMapsActivity.this,2);
                }}

        }, 0, 10000);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }catch (Exception e){
            Log.e("mapException",e.getMessage());
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        location(0);


    }


    public void location(int move) {
        if(move==1){
            mMap.clear();
        }
        existLocation=0;
        latit=31.9695985;
        longtud=35.9138707;
        // Add a marker in Sydney and move the camera
        Log.e("mmmmmm", "locationCall");
        LatLng sydney = null;
        Log.e("salesManInfosList==",salesManInfosList.size()+"");
        Log.e("LatLngListMarker==",LatLngListMarker.size()+"");
        if(salesManInfosList.size()!=0)
        {

        for (int i = 0; i < LatLngListMarker.size(); i++) {
             if(LatLngListMarker.get(i)!=null&&salesManInfosList.get(i)!=null)
            if (!salesManInfosList.get(i).getLatitudeLocation().equals("0") && !salesManInfosList.get(i).getLongitudeLocation().equals("0")) {
                existLocation++;
                sydney = LatLngListMarker.get(i);

                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title(salesManInfosList.get(i).getSalesName()+salesManInfosList.get(i).getLatitudeLocation()+salesManInfosList.get(i).getLongitudeLocation()));
                builder.include(sydney);
            }
        }
        }else {


        }
        Log.e("salesManInfosList",""+salesManInfosList.size()+"\t"+existLocation);
        if(existLocation==0||salesManInfosList.size()==0)
        {
            fillCurentLocation();
        }

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        if(move==0) {
            try {
                bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
                mMap.animateCamera(cu);
            }catch (Exception e){

            }
        }
        flag=true;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
    }

    private void fillCurentLocation() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latit, longtud))
                .title("cool place")

                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latit, longtud), 15f));
    }

    Bitmap iconSize(){
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.van_blue);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        return smallMarker;
    }

}
