package com.example.adminvansales.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportData;
import com.example.adminvansales.Interface.CustomerDao;
import com.example.adminvansales.R;
import com.example.adminvansales.model.NewAddedCustomer;
import com.example.adminvansales.model.RequstTest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.adminvansales.LogIn.contextG;
import static com.example.adminvansales.MainActivity.isListUpdated;

public class AddedCustomerAdapter  extends RecyclerView.Adapter<AddedCustomerAdapter.ViewHolder> {
    Context context;
     Dialog dialog;
    private DatabaseReference databaseReference,databaseReference2;
    List<NewAddedCustomer> requestList;
    DataBaseHandler databaseHandler;
    public static String languagelocalApp = "";
    public static String acc = "", bankN = "", branch = "", mobileNo = "";
    int typeDiscountItem=0;
    double latu=0;
    double longi=0;
    ProgressDialog dialog_progress;
     TextView  Addrss_map;
    LocationListener locationListener;
    public   LocationManager locationManager;
    double cutmer_lat =0;
    double  cutmer_long=0;
    private LatLngBounds.Builder builder;
    LatLngBounds bounds;
    public  static  double latitude_main, longitude_main;
    MapView mMapView;
    String   add="";


    public AddedCustomerAdapter(List<NewAddedCustomer> itemsList, Context context) {
        this.requestList = itemsList;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addedcus_row, parent, false);

        return new AddedCustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder . timeRequest.setText(requestList.get(i).getDate()+"");
        holder. date_request.setText(requestList.get(i).getTime()+"");
        holder.  customerName.setText(requestList.get(i).getCustName()+"");
        holder.    salesManName .setText(requestList.get(i).getSalesMan()+"");
        holder.   lineardetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                New_openAddCustomerDialog(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView timeRequest, date_request ,customerName ,salesManName;
     LinearLayout   lineardetail;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            timeRequest = itemView.findViewById(R.id.timeRequest);
            date_request = itemView.findViewById(R.id.date_request);
            customerName = itemView.findViewById(R.id.customerName);
            salesManName = itemView.findViewById(R.id.salesManName);
            lineardetail= itemView.findViewById(R.id.lineardetail);


        }

        public String convertToArabic(String value) {
            String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
            Log.e("convertToArabic", value + "      " + newValue);
            return newValue;
        }

        }


    public void New_openAddCustomerDialog(int i) {
          dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.added_customersdaiolg);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);

        Window window = dialog.getWindow();

        GoogleMap googleMap;






        final EditText addCus = (EditText) dialog.findViewById(R.id.custEditText);
        final EditText remark = (EditText) dialog.findViewById(R.id.remarkEditText);
        final EditText address = (EditText) dialog.findViewById(R.id.addressEditText);
        final EditText telephone = (EditText) dialog.findViewById(R.id.phoneEditText);
        final EditText contactPerson = (EditText) dialog.findViewById(R.id.person_contactEditText);
        final EditText MarketName = (EditText) dialog.findViewById(R.id.MarketName);
        final EditText  Maxd= dialog.findViewById(R.id.MaxDEditText);
        final TextView  showMap= dialog.findViewById(R.id.showMap);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog Mapdialog = new Dialog(context);
                Mapdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Mapdialog.setCancelable(true);
                Mapdialog.setContentView(R.layout.locationdailog);
                Mapdialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Mapdialog.getWindow().getAttributes());

                lp.gravity = Gravity.CENTER;
                lp.windowAnimations = R.style.DialogAnimation;
                Mapdialog.getWindow().setAttributes(lp);
                Addrss_map= Mapdialog.findViewById(R.id.Addrss_map);
                Window window = Mapdialog.getWindow();
                Mapdialog.show();
                mMapView = (MapView) dialog.findViewById(R.id.mapView);
                Mapdialog.findViewById(R.id.AcceptButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mapdialog.dismiss();
                    }
                });
                Mapdialog.findViewById(R.id.RejectButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cutmer_lat=  Double.parseDouble(requestList.get(i).getLatitude());
                        cutmer_long= Double.parseDouble(requestList.get(i).getLongtitude());

                        Mapdialog.dismiss();
                    }
                });
                try {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMapView = (MapView) Mapdialog.findViewById(R.id.mapView);
                            mMapView.onCreate(Mapdialog.onSaveInstanceState());
                            mMapView.onResume();// needed to get the map to display immediately

                            MapsInitializer.initialize(context);

                            mMapView.onCreate(Mapdialog.onSaveInstanceState());
                            mMapView.onResume();
                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(final GoogleMap googleMap) {
                                    androidx.appcompat.widget.SearchView searchView =Mapdialog. findViewById(R.id.idSearchView);
                                    searchView.setOnQueryTextListener(new  androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                                        @Override
                                        public boolean onQueryTextSubmit(String query) {
                                            // on below line we are getting the
                                            // location name from search view.
                                            String location = searchView.getQuery().toString();

                                            // below line is to create a list of address
                                            // where we will store the list of all address.
                                            List<Address> addressList = null;

                                            // checking if the entered location is null or not.
                                            if (location != null || location.equals("")) {
                                                // on below line we are creating and initializing a geo coder.
                                                Geocoder geocoder = new Geocoder(context);
                                                try {
                                                    // on below line we are getting location from the
                                                    // location name and adding that location to address list.
                                                    addressList = geocoder.getFromLocationName(location, 1);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                // on below line we are getting the location
                                                // from our list a first position.
                                                Address address = addressList.get(0);

                                                // on below line we are creating a variable for our location
                                                // where we will add our locations latitude and longitude.
                                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                                                // on below line we are adding marker to that position.
                                                googleMap.addMarker(new MarkerOptions().position(latLng).title(location));

                                                // below line is to animate camera to that position.
                                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 500));
                                            }

                                            return false;
                                        }

                                        @Override
                                        public boolean onQueryTextChange(String newText) {
                                            return false;
                                        }
                                    });
                                    //get curent loction
                                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (locationGPS != null) {
                                        latu = locationGPS.getLatitude();
                                        longi = locationGPS.getLongitude();



                                    } else {
                                        Toast.makeText(context, "Unable to find location.", Toast.LENGTH_SHORT).show();
                                    }

                                    latu =  Double.parseDouble(requestList.get(i).getLongtitude());
                                    longi =  Double.parseDouble(requestList.get(i).getLatitude());
                                    com.google.android.gms.maps.model.LatLng posisiabsen = new LatLng(longi,latu); ////your lat lng
                                    Log.e("latu==",latu+","+longi);
                                    googleMap.addMarker(new MarkerOptions().position(posisiabsen).title(""));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                                    //    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                    LatLngBounds bounds;
                                    builder = new LatLngBounds.Builder();
                                    builder.include(new LatLng(longi,latu));
                                    bounds = builder.build();
                                    if(longi!=0&&latu!=0)
                                    getAddress(longi,latu);

                                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
                                    googleMap.animateCamera(cu,500,null);

                                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                        @Override
                                        public void onMapClick(LatLng latLng) {
                                            googleMap.clear();

                                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setContentText( "اعتماد هذا الموقع؟")
                                                    .setConfirmButton(context.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            googleMap.addMarker(new MarkerOptions().position(latLng).title("here"));
                                                         //   Toast.makeText(context, latLng.latitude+","+latLng.longitude, Toast.LENGTH_SHORT).show();
                                                            cutmer_lat= latLng.latitude;
                                                            cutmer_long=latLng.longitude;
                                                            if(cutmer_lat!=0&&cutmer_long!=0)
                                                            getAddress(cutmer_lat,cutmer_long);
                                                            //



                                                            //
                                                            sweetAlertDialog.dismissWithAnimation();
                                                        }
                                                    }).setCancelButton(context.getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            try {
                                                                cutmer_lat=  Double.parseDouble(requestList.get(i).getLatitude());
                                                                cutmer_long= Double.parseDouble(requestList.get(i).getLongtitude());
                                                            }catch (Exception exception){
                                                                cutmer_lat=0;
                                                                cutmer_long=0;
                                                            }
                                                            sweetAlertDialog.dismissWithAnimation();
                                                        }
                                                    })
                                                    .show();

                                        }
                                    });
                                }
                            });
                        }
                    }, 3000);
                }catch (Exception exception){
                    Log.e("Exception",exception.getMessage()+"");
                }







            }
        });
        addCus.setText(requestList.get(i).getCustName()+"");
        remark.setText(requestList.get(i).getRemark()+"");
        address.setText(requestList.get(i).getADRESS_CUSTOMER()+"");
        telephone.setText(requestList.get(i).getTELEPHONE()+"");
        contactPerson.setText(requestList.get(i).getCONTACT_PERSON()+"");
        MarketName.setText(requestList.get(i).getMarketName()+"");
        Log.e("hereadd==",add+"");
        try {
            cutmer_lat=  Double.parseDouble(requestList.get(i).getLatitude());
            cutmer_long= Double.parseDouble(requestList.get(i).getLongtitude());
        }catch (Exception exception){
            cutmer_lat=0;
            cutmer_long=0;
        }

        Maxd.setText(requestList.get(i).getMaxD()+"");


        Button AcceptButton = (Button) dialog.findViewById(R.id.AcceptButton);
        Button RejectButton = (Button) dialog.findViewById(R.id.RejectButton);

        LinearLayout   linear = dialog.findViewById(R.id.linear);
        try {
            if (languagelocalApp.equals("ar")) {
                linear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch (Exception e){
            linear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);}





        AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addCus.getText().toString().equals("")) {
                    int payMethod = 0;


                    CustomerDao customerDao = new CustomerDao(context);


                    NewAddedCustomer addedCustomer = new NewAddedCustomer(
                            addCus.getText().toString(),
                            remark.getText().toString(),
                            cutmer_lat+""
                            ,    cutmer_long+"",
                            requestList.get(i).getSalesMan(),
                            requestList.get(i).getSalesmanNo(),
                            "0",
                            address.getText().toString(),
                            telephone.getText().toString(),
                            contactPerson.getText().toString(),
                            MarketName.getText().toString(),
                            requestList.get(i).getDate(),
                            requestList.get(i).getTime(),
                            Maxd.getText().toString().trim() );

                    dialog.dismiss();

                    ExportData importJason =new ExportData(context);
                    ArrayList<NewAddedCustomer>arrayList=new ArrayList<>();
                    arrayList.clear();
                    arrayList.add(addedCustomer);
                    importJason.startExportCustomer(arrayList, context);


                } else {
                    addCus.setError(context.getResources().getString(R.string.reqired_filled));
                    Toast.makeText(context, "Please add customer name", Toast.LENGTH_SHORT).show();

                }
            }

        });

        RejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerDao customerDao=new CustomerDao(contextG);
                customerDao.deleteRequst( requestList.get(i).getTELEPHONE());
                dialog.dismiss();
            }
        });

        dialog.show();

        locationManager = (LocationManager)context. getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
        ) {

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude_main = location.getLatitude();
                    longitude_main = location.getLongitude();
                    Log.e("onLocationChanged", "" + longitude_main);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);





            dialog.show();
        }
    }
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
               add = obj.getAddressLine(0);
            String  currentAddress = obj.getSubAdminArea() + ","
                    + obj.getAdminArea();
            double   latitude = obj.getLatitude();
            double longitude = obj.getLongitude();
            String currentCity= obj.getSubAdminArea();
            String currentState= obj.getAdminArea();
            if(obj.getCountryName()!=null)
            add = add + "-" + obj.getCountryName();
            if(obj.getCountryCode()!=null)
            add = add + "-" + obj.getCountryCode();
            if(obj.getAdminArea()!=null)
            add = add + "-"+ obj.getAdminArea();
            if(obj.getPostalCode()!=null)
            add = add + "-" + obj.getPostalCode();
            if(obj.getSubAdminArea()!=null)
            add = add + "-" + obj.getSubAdminArea();
            if(obj.getLocality()!=null)
            add = add + "-" + obj.getLocality();
            if(obj.getSubThoroughfare()!=null)
            add = add + "-" + obj.getSubThoroughfare();


            Log.e("obj.getCountryName()",obj.getCountryName()+"");
            Log.e("obj.getCountryCode()",obj.getCountryCode()+"");
            Log.e("obj.getAdminArea()",obj.getAdminArea()+"");
            Log.e("obj.getPostalCode()",obj.getPostalCode()+"");
            Log.e("obj.getSubAdminArea()",obj.getSubAdminArea()+"");
            Log.e("obj.getLocality()",obj.getLocality()+"");
            Log.e("obj.getSubThoroughfare()",obj.getSubThoroughfare()+"");


            Log.e("IGA", "Address" + add);
            Addrss_map.setText("Address: "+add+"");
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }


    class Task extends AsyncTask<String, Integer, String> {
int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        protected String doInBackground(String... strings) {


            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {
                    {
                        final Dialog Mapdialog = new Dialog(context);
                        Mapdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        Mapdialog.setCancelable(true);
                        Mapdialog.setContentView(R.layout.locationdailog);
                        Mapdialog.setCanceledOnTouchOutside(true);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(Mapdialog.getWindow().getAttributes());

                        lp.gravity = Gravity.CENTER;
                        lp.windowAnimations = R.style.DialogAnimation;
                        Mapdialog.getWindow().setAttributes(lp);
                        Addrss_map= Mapdialog.findViewById(R.id.Addrss_map);
                        Window window = Mapdialog.getWindow();
                        Mapdialog.show();
                        mMapView = (MapView) dialog.findViewById(R.id.mapView);
                        Mapdialog.findViewById(R.id.AcceptButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mapdialog.dismiss();
                            }
                        });
                        Mapdialog.findViewById(R.id.RejectButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cutmer_lat=  Double.parseDouble(requestList.get(i).getLatitude());
                                cutmer_long= Double.parseDouble(requestList.get(i).getLongtitude());

                                Mapdialog.dismiss();
                            }
                        });
                        try {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mMapView = (MapView) Mapdialog.findViewById(R.id.mapView);
                                    mMapView.onCreate(Mapdialog.onSaveInstanceState());
                                    mMapView.onResume();// needed to get the map to display immediately

                                    MapsInitializer.initialize(context);

                                    mMapView.onCreate(Mapdialog.onSaveInstanceState());
                                    mMapView.onResume();
                                    mMapView.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(final GoogleMap googleMap) {

                                            //get curent loction
                                            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                            if (locationGPS != null) {
                                                latu = locationGPS.getLatitude();
                                                longi = locationGPS.getLongitude();



                                            } else {
                                                Toast.makeText(context, "Unable to find location.", Toast.LENGTH_SHORT).show();
                                            }

                                            latu =  Double.parseDouble(requestList.get(i).getLongtitude());
                                            longi =  Double.parseDouble(requestList.get(i).getLatitude());
                                            com.google.android.gms.maps.model.LatLng posisiabsen = new LatLng(longi,latu); ////your lat lng
                                            Log.e("latu==",latu+","+longi);
                                            googleMap.addMarker(new MarkerOptions().position(posisiabsen).title(""));
                                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                                            googleMap.getUiSettings().setZoomControlsEnabled(true);
                                            //    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                            LatLngBounds bounds;
                                            builder = new LatLngBounds.Builder();
                                            builder.include(new LatLng(longi,latu));
                                            bounds = builder.build();
                                            if(longi!=0&&latu!=0)
                                            getAddress(longi,latu);

                                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
                                            googleMap.animateCamera(cu,500,null);

                                            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                                @Override
                                                public void onMapClick(LatLng latLng) {
                                                    googleMap.clear();

                                                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                            .setContentText( "اعتماد هذا الموقع؟")
                                                            .setConfirmButton(context.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    googleMap.addMarker(new MarkerOptions().position(latLng).title("here"));
                                                                    Toast.makeText(context, latLng.latitude+","+latLng.longitude, Toast.LENGTH_SHORT).show();
                                                                    cutmer_lat= latLng.latitude;
                                                                    cutmer_long=latLng.longitude;
                                                                    if(cutmer_lat!=0&&cutmer_long!=0)
                                                                    getAddress(cutmer_lat,cutmer_long);
                                                                    //



                                                                    //
                                                                    sweetAlertDialog.dismissWithAnimation();
                                                                }
                                                            }).setCancelButton(context.getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    cutmer_lat=  Double.parseDouble(requestList.get(i).getLatitude());
                                                                    cutmer_long= Double.parseDouble(requestList.get(i).getLongtitude());
                                                                    sweetAlertDialog.dismissWithAnimation();
                                                                }
                                                            })
                                                            .show();

                                                }
                                            });
                                        }
                                    });



                                    // adding on query listener for our search view.


                                            // at last we calling our map fragment to update.
                                           // mMapView.getMapAsync(context);

                                }
                            }, 3000);
                        }catch (Exception exception){
                            Log.e("Exception",exception.getMessage()+"");
                        }







                    }

                }
            });

            return "items";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  dialog_progress.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog_progress = new ProgressDialog(context);
                dialog_progress.setCancelable(false);
                dialog_progress.setMessage("loading...");
                dialog_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                dialog_progress.show();//test
            }
            catch (Exception e){

            }

        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);

            try {
                dialog_progress.dismiss();
            }
            catch (Exception e){}


            if (result != null) {

            } else {
                Toast.makeText(context, "Not able to fetch data ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
