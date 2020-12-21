package com.example.adminvansales;

import android.content.Context;
import android.util.Log;

import com.example.adminvansales.Model.SalesManInfo;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GlobelFunction {
    ImportData importData;
    Context context;
    public static  List<SalesManInfo> salesManInfosList=new ArrayList<>();
    public static  List<LatLng> LatLngListMarker=new ArrayList<>();

    public GlobelFunction(Context context) {
        importData=new ImportData(context);
        this.context =context;
    }

    public void getSalesManInfo(Context context,int flag){

        importData.getSalesMan(context,flag);

    }

}
