package com.example.adminvansales;

import static com.example.adminvansales.AddCustomerLocation.updateLocationPosi;
import static com.example.adminvansales.GlobelFunction.LatLngListMarker;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.ImportData.listCustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CustomerLocationSelect extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private LatLngBounds.Builder builder;
    LatLngBounds bounds;
    private Timer timer;
    private OnMapReadyCallback callback;
    boolean mLocationPermissionGranted=false;

    Handler locationHandler;
    final static long REFRESH = 1 * 1000;
    final static int SUBJECT = 0;
    boolean flag=false;
    GlobelFunction globelFunction;
    double latit=0,longtud=0;
    public  static  int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
    TextView customer_name_save,customer_name_text;
    String customerName="",cusNumber="";
    ExportData exportData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location_customer);
        try {
            String latitude= getIntent().getStringExtra("latit");
            String longtude= getIntent().getStringExtra("longtude");
             customerName= getIntent().getStringExtra("cusName");
            cusNumber= getIntent().getStringExtra("cusNumber");


            Log.e("latitude","==="+latitude+"\tlongtude"+longtude+"\t"+customerName);
            customer_name_save=findViewById(R.id.customer_name_save);
            customer_name_text=findViewById(R.id.customer_name_text);
            exportData=new ExportData(this);
            customer_name_text.setText(customerName);
            customer_name_save.setOnClickListener(v -> {
                Log.e("latitude","clickd==="+latitude+"\tlongtude"+longtude);
                updateListCustomer(cusNumber,latit+"",longtud+"");
//                exportData.updateCustomerLocatio(cusNumber,latitude,longtude);
            });

            if(!latitude.equals("")&& !longtude.equals(""))
            {
                try {
                    latit=Double.parseDouble(latitude);
                    longtud=Double.parseDouble(longtude);
                }catch (Exception e){
                    Log.e("empty Location","e"+e.getMessage());
                    latit=31.9695985;
                    longtud=35.9138707;
                }

            }else {
                Log.e("empty Location","");
                latit=31.9695985;
                longtud=35.9138707;
            }


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            builder = new LatLngBounds.Builder();
            globelFunction=new GlobelFunction(this);

            getLocationPermission();
            // Enable the zoom controls for the map



            // Obtain the SupportMapFragment and get notified when the map is ready to be used.



//getpicker();


        }catch (Exception e){
            Log.e("mapException",e.getMessage());
        }
    }

    private void updateListCustomer(String cusNumber, String latitude, String longtude) {
        for(int i=0;i<listCustomer.size();i++){
            if(listCustomer.get(i).getCustomerNumber().equals(cusNumber))
            {
                Log.e("updateListCustomer",""+cusNumber);
                listCustomer.get(i).setLatit_customer(latitude);
                listCustomer.get(i).setLong_customer(longtude);
                Log.e("updateListCustomer",""+   listCustomer.get(i).getLong_customer());
                break;
            }
        }
        finish();
        updateLocationPosi.setText("updateLoc");

    }

    private void getpicker() {
        //
//            int PLACE_PICKER_REQUEST = 1;
//            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//
//            try {
//                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
//            } catch (GooglePlayServicesRepairableException e) {
//                e.printStackTrace();
//            } catch (GooglePlayServicesNotAvailableException e) {
//                e.printStackTrace();
//            }
//            mClient = new GoogleApiClient
//                    .Builder(this)
//                    .addApi(Places.GEO_DATA_API)
//                    .addApi(Places.PLACE_DETECTION_API)
//                    .build();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        location(0);
    }
    public void location(int move) {
        if(move==1){
            mMap.clear();
        }
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latit, longtud))
                .title("cool place")

                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latit, longtud), 15f));
        mMap.setOnMapClickListener(this);
//        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//            }
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//
//                LatLng latLng = marker.getPosition();
//                Log.e("mmmmmmlatitude", "locationCall"+latLng.latitude);
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                try {
//                    android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
//                    Log.e("address",""+address);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        // Add a marker in Sydney and move the camera
        Log.e("mmmmmm", "locationCall");
        LatLng sydney = null;

    }

    Bitmap iconSize(){
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.van_blue);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        return smallMarker;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
//            }
//        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        Log.e("onMapClick","latLng"+latLng.latitude);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude,latLng.longitude)) .title("cool place")

                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        latit=latLng.latitude;
        longtud=latLng.longitude;
    }
}