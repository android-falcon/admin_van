package com.example.adminvansales.retrofit;

import com.example.adminvansales.model.ErrorHandler;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlanApi {
    @FormUrlEncoded
    @POST("ADMDeletePlan")
    Observable<ErrorHandler> deletePlan(@Field("CONO") int com, @Field("JSONSTR") JSONObject jsonObject);
}
