package com.example.adminvansales.RepositoryData;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ItemVisibility;
import com.example.adminvansales.R;
import com.example.adminvansales.model.ErrorHandler;
import com.example.adminvansales.model.ItemInfo;
import com.example.adminvansales.retrofit.ApiItem;
import com.example.adminvansales.retrofit.RetrofitInstance;

import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ItemVisibleRepository {

//    String BASE_URL = "http://10.0.0.22/",port="";
    String BASE_URL = "",port="";

    Retrofit retrofit = RetrofitInstance.getInstance(BASE_URL);
    ApiItem myAPI = retrofit.create(ApiItem.class);
    SweetAlertDialog pdValidation;
    ErrorHandler itemInfoHandler=new ErrorHandler();
    DataBaseHandler dataBaseHandler;

    public ItemVisibleRepository(Application aplication ) {
        dataBaseHandler=new DataBaseHandler(aplication.getApplicationContext());
        BASE_URL=dataBaseHandler.getAllSetting().getIpAddress();
        port=dataBaseHandler.getAllSetting().getPort();
        Log.e("ItemVisibleRepository",""+BASE_URL+"\t"+port);
    }

    public MutableLiveData<List<ItemInfo>> getItemInfoRepo() {
        final MutableLiveData<List<ItemInfo>> mutableLiveData = new MutableLiveData<>();
        Observable<List<ItemInfo>> issueObservable = myAPI.gatItem(295);
        issueObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(itemInfos -> itemInfos)    //get issues and map to issues list
                .subscribe(new Observer<List<ItemInfo>>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "" + e.getMessage().toString());
//                        pdValidation.dismissWithAnimation();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "finish");
//                        pdValidation.dismissWithAnimation();
                    }


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        pdValidation = new SweetAlertDialog(ItemVisibility.this, SweetAlertDialog.PROGRESS_TYPE);
//                        pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//                        pdValidation.setTitleText(ItemVisibility.this.getResources().getString(R.string.process));
//                        pdValidation.setCancelable(false);
//                        pdValidation.show();
                        Log.e("onSubscribe", "" + d.toString());
                    }

                    @Override
                    public void onNext(List<ItemInfo> itemInfos) {
                        Log.e("onNext", "" + itemInfos.size());
                        mutableLiveData.setValue(itemInfos);
//                        allItemsList.clear();
//                        allItemsList.addAll(itemInfos);
//                        displayData(allItemsList);

                    }
                });
        return mutableLiveData;
    }
public   ErrorHandler AddItemVisible(JSONObject addsalesmanobject) {
    itemInfoHandler=new ErrorHandler();
    Observable<ErrorHandler> issueObservable = myAPI.addItemVisibleList(295, addsalesmanobject);
    issueObservable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map(itemInfos -> itemInfos)    //get issues and map to issues list
            .subscribe(new Observer<ErrorHandler>() {

                @Override
                public void onError(Throwable e) {
                    Log.e("onError", "" + e.getMessage().toString());
                    pdValidation.dismissWithAnimation();
                }

                @Override
                public void onComplete() {
                    Log.e("onComplete", "finish");
                    pdValidation.dismissWithAnimation();
                }


                @Override
                public void onSubscribe(@NonNull Disposable d) {

                    Log.e("onSubscribe", "" + d.toString());
                }

                @Override
                public void onNext(ErrorHandler itemInfos) {
                    Log.e("onNext", "" + itemInfos.toString());

                    itemInfoHandler= itemInfos;
//                    sweetSaved(itemInfos.getErrorDics());
//                    if (itemInfos.getErrorDics().contains("Saved")) {
//                        clearSelected();
//                    }


                }
            });
    return  itemInfoHandler;
}
}
