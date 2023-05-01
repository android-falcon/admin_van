package com.example.adminvansales;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.adminvansales.Interface.LocationDao;
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
import com.google.maps.android.ui.IconGenerator;

import java.util.Timer;

import static com.example.adminvansales.GlobelFunction.LatLngListMarker;
import static com.example.adminvansales.GlobelFunction.salesManInfosList;

public class SalesmanMaps_FirebaseActivity extends FragmentActivity implements OnMapReadyCallback {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_maps);
       findViewById(R.id.refresh).setVisibility(View.GONE);
        globelFunction = new GlobelFunction(SalesmanMaps_FirebaseActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LocationDao.LatLngListMarker.clear();
        LocationDao.salesManInfos.clear();
            LocationDao locationDao=new LocationDao(SalesmanMaps_FirebaseActivity.this);
            locationDao.allTaskInFireBase();


        if(LocationDao.LatLngListMarker.size()!=0) {

            fillCurentLocation();
        }
        }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        LatLng sydney = new LatLng(31.9695172, 35.9141206);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.9695172, 35.9141206), 15f));
        location2();
    }

    public void location3() {


        LatLng sydney = null;


        for (int i = 0; i < 4; i++) {

            sydney=new LatLng(  31.9695172,35.9141206)   ;
           mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title("dddddd"));
            builder.include(sydney);
        }

            bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
            mMap.animateCamera(cu);




//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
    }
    public void location() {

        builder = new LatLngBounds.Builder();
        LatLng sydney = null;

        Log.e("LocationDao", "LatLngListMarker"+LocationDao.LatLngListMarker.size());
            for (int i = 0; i < LocationDao.LatLngListMarker.size(); i++) {

                        existLocation++;
                Log.e("LocationDao2==", "LatLngListMarker"+LocationDao.LatLngListMarker.get(i));
                    sydney = LocationDao.LatLngListMarker.get(i);

                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title(LocationDao.salesManInfos.get(i).getSalesName()));
                        builder.include(sydney);
                    }


      if(LocationDao.LatLngListMarker.size()!=0) {

          fillCurentLocation();


//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));


          bounds = builder.build();
          CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
          mMap.animateCamera(cu);


          flag = true;
      }
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
    }
    public void location2() {
        mMap.clear();
        builder = new LatLngBounds.Builder();
        LatLng sydney = null;

        Log.e("LocationDao", "LatLngListMarker"+LocationDao.salesManInfos.size());
        for (int i = 0; i < LocationDao.salesManInfos.size(); i++) {

            existLocation++;
            //Log.e("LocationDao2==", "LatLngListMarker"+LocationDao.LatLngListMarker.get(i));
            sydney = new LatLng(Double.parseDouble(LocationDao.salesManInfos.get(i).getLatitudeLocation())
                    ,(Double.parseDouble(LocationDao.salesManInfos.get(i).getLongitudeLocation()))) ;

      //      mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title(LocationDao.salesManInfos.get(i).getSalesName()));
            ///builder.include(sydney);


            Marker marker =    mMap.addMarker(new MarkerOptions() .icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title(LocationDao.salesManInfos.get(i).getLatitudeLocation()+LocationDao.salesManInfos.get(i).getLongitudeLocation()));;
            //  marker.showInfoWindow();

            IconGenerator iconFactory = new IconGenerator(this);
            Marker   mMarkerA = mMap.addMarker(new MarkerOptions().position(sydney));
            mMarkerA.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(LocationDao.salesManInfos.get(i).getSalesName())));


            builder.include(sydney);
        }
        if(LocationDao.LatLngListMarker.size()==0) {

            fillCurentLocation();
        }

        if(LocationDao.salesManInfos.size()!=0) {

            bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
            mMap.animateCamera(cu);


            flag = true;
        }

    }
    private void fillCurentLocation() {
     latit=31.96960599;
       longtud=35.91464715;
        Log.e("fillCurentLocation",latit+" "+longtud);
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