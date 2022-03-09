package com.example.adminvansales.modelView;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.adminvansales.RepositoryData.ItemVisibleRepository;
import com.example.adminvansales.model.ErrorHandler;
import com.example.adminvansales.model.ItemInfo;

import org.json.JSONObject;

import java.util.List;

public class ItemVisibleViewModel extends ViewModel {


    ItemVisibleRepository itemVisibleRepository;
    MutableLiveData<List<ItemInfo>> mutableLiveDataItem;


    public ItemVisibleViewModel(Application app) {
       itemVisibleRepository=new ItemVisibleRepository(app);
    }
    public LiveData<List<ItemInfo>> getItemInfoList(){
        if(mutableLiveDataItem==null){
           mutableLiveDataItem=itemVisibleRepository.getItemInfoRepo();
        }
        return  mutableLiveDataItem;
    }


    public ErrorHandler addItemVisibile(JSONObject addsalesmanobject) {

       return itemVisibleRepository.AddItemVisible(addsalesmanobject);
    }

}
