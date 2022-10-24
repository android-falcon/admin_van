package com.example.adminvansales.retrofit;

import com.example.adminvansales.model.ErrorHandler;
import com.example.adminvansales.model.ItemInfo;
import com.example.adminvansales.model.ItemMaster;
import com.example.adminvansales.model.ItemReportModel;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiItem {
    //    @GET("IrGetAllItems/{CONO}")
//    Observable<List<ItemInfo>> gatItem(    @Query("CONO") int companyNo);
    @GET("IrGetAllItems")
    Observable<List<ItemInfo>> gatItem(@Query("CONO") String  ComNo);
    @GET("/IrGetAllItems")
    Call<List<ItemInfo>> gatItemInfo(@Query("CONO") int ComNo);
//    @FormUrlEncoded
//    @POST("ADMAddItem_Visibilty")
//    Call<String> addItemVisibleList(@Field("CONO") int com, @Field("JSTR"));

    @FormUrlEncoded
    @POST("ADMAddITEM_VISIBILATY")
    Observable<ErrorHandler> addItemVisibleList(@Field("CONO") int com, @Field("JSONSTR")JSONObject jsonObject);

     @GET("IrGetAllItems")
    Call<List<ItemMaster>> gatItemInfoDetail(@Query("CONO") String ComNo);
    @GET("ADMItemReport")
    Call<List<ItemReportModel>> gatItemReport(@Query("CONO") String ComNo,@Query("SALESMANNO") String SALESMANNO,@Query("FROMDATE") String FROMDATE,@Query("TODATE") String TODATE);
}

