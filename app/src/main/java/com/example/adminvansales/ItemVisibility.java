package com.example.adminvansales;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listCustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.CustomerAdapter;
import com.example.adminvansales.Adapters.ItemVisibleAdapter;
import com.example.adminvansales.databinding.ActivityItemVisibilityBinding;
import com.example.adminvansales.model.ErrorHandler;
import com.example.adminvansales.model.ItemInfo;
import com.example.adminvansales.model.Plan_SalesMan_model;
import com.example.adminvansales.modelView.ItemVisibleViewModel;
import com.example.adminvansales.retrofit.ApiItem;
import com.example.adminvansales.retrofit.ApiUrl;
import com.example.adminvansales.retrofit.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemVisibility extends AppCompatActivity {
    JSONObject addsalesmanobject;
    JSONArray jsonArraysalesman;
    ApiUrl apiUrl;
    String url = "";
    ApiItem myAPI;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SweetAlertDialog pdValidation;

    ArrayAdapter<String> salesNameSpinnerAdapter, itemStateVisiAdapter;
    List<ItemInfo> listFilterItem, allItemsList, listSelectItems;
    List<String> listItemVisib = new ArrayList<>();
    public int stateFilterVisibl = 0;
    ActivityItemVisibilityBinding myBinding;
    public ItemVisibleViewModel itemVisibleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBinding= DataBindingUtil.setContentView(this,R.layout.activity_item_visibility);
        inititView();

//      fetchCallData();
        fetchData();

    }

    private void fetchData() {

//        myBinding.progressBar.setVisibility(View.VISIBLE);
        itemVisibleViewModel=new ItemVisibleViewModel();
        itemVisibleViewModel.getItemInfoList().observe(this, new androidx.lifecycle.Observer<List<ItemInfo>>() {
            @Override
            public void onChanged(List<ItemInfo> itemInfos) {
                if(itemInfos!=null&& !itemInfos.isEmpty())
                {

                    displayData(itemInfos);
                }

            }
        });
//        getRetrofitData();


    }

    private void getRetrofitData() {
        Observable<List<ItemInfo>> issueObservable = myAPI.gatItem(295);
        issueObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(itemInfos -> itemInfos)    //get issues and map to issues list
                .subscribe(new Observer<List<ItemInfo>>() {

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
                        pdValidation = new SweetAlertDialog(ItemVisibility.this, SweetAlertDialog.PROGRESS_TYPE);
                        pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                        pdValidation.setTitleText(ItemVisibility.this.getResources().getString(R.string.process));
                        pdValidation.setCancelable(false);
                        pdValidation.show();
                        Log.e("onSubscribe", "" + d.toString());
                    }

                    @Override
                    public void onNext(List<ItemInfo> itemInfos) {
                        Log.e("onNext", "" + itemInfos.size());
                        allItemsList.clear();
                        allItemsList.addAll(itemInfos);
                        displayData(allItemsList);

                    }
                });
    }

    private void fetchCallData() {

        Call<List<ItemInfo>> myData = myAPI.gatItemInfo(734);

        myData.enqueue(new Callback<List<ItemInfo>>() {
            @Override
            public void onResponse(Call<List<ItemInfo>> call, Response<List<ItemInfo>> response) {
                if (!response.isSuccessful()) {
                    Log.e("onResponse", "not=" + response.message());
                } else {
                    Log.e("onResponse", "=" + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ItemInfo>> call, Throwable throwable) {
                Log.e("onFailure", "=" + throwable.getMessage());
            }
        });
    }

    private void displayData(List<ItemInfo> itemInfos) {
        Log.e("displayData", "" + itemInfos.size());
//        myBinding.progressBar.setVisibility(View.GONE);
//        myBinding.itemRecycler.setVisibility(View.VISIBLE);
        if (itemInfos.size() != 0) {
            myBinding.progressBar.setVisibility(View.GONE);
            ItemVisibleAdapter adapter = new ItemVisibleAdapter(itemInfos, this);
            myBinding.itemRecycler.setAdapter(adapter);

        }
//        myBinding.progressBar.setVisibility(View.GONE);


    }

    private void inititView() {
        apiUrl = new ApiUrl(this);
        url = apiUrl.getBaseUrl();
        Log.e("url", "==" + url);
        Retrofit retrofit = RetrofitInstance.getInstance(url);
        myAPI = retrofit.create(ApiItem.class);
        myBinding.progressBar.setVisibility(View.VISIBLE);
        myBinding.itemRecycler.setHasFixedSize(true);
        myBinding.itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        fillSalesManSpinner();
        fillItemVisibleSpinner();

        myBinding. clearSearch.setOnClickListener(v -> {
            myBinding.customerSearch.setText("");
        });
        listFilterItem = new ArrayList<>();
        allItemsList = new ArrayList<>();
        listSelectItems = new ArrayList<>();
        myBinding.customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    if (!s.toString().equals("")) {
                        filterListcustomer(s.toString().trim());
                    }
                } else {
                    filterListcustomer("");
                }

            }
        });
        myBinding.saveButton.setOnClickListener(v -> {
            checkData();
        });
    }

    private void checkData() {
        addsalesmanobject = new JSONObject();
        addsalesmanobject = getJsonObject();// for test
//        ErrorHandler errorHandler=new ErrorHandler();
//         errorHandler= itemVisibleViewModel.addItemVisibile(addsalesmanobject);
//        sweetSaved(errorHandler.getErrorDics());
//           if (errorHandler.getErrorDics().contains("Saved")) {
//                        clearSelected();
//                    }

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
                        pdValidation = new SweetAlertDialog(ItemVisibility.this, SweetAlertDialog.PROGRESS_TYPE);
                        pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                        pdValidation.setTitleText(ItemVisibility.this.getResources().getString(R.string.process));
                        pdValidation.setCancelable(false);
                        pdValidation.show();
                        Log.e("onSubscribe", "" + d.toString());
                    }

                    @Override
                    public void onNext(ErrorHandler itemInfos) {
                        Log.e("onNext", "" + itemInfos.getErrorDics());

                        sweetSaved(itemInfos.getErrorDics());
                        if (itemInfos.getErrorDics().contains("Saved")) {
                            clearSelected();
                        }


                    }
                });


    }

    private void clearSelected() {
        for (int i = 0; i < allItemsList.size(); i++) {
            allItemsList.get(i).setSelect(0);


        }
        displayData(allItemsList);
    }

    private void sweetSaved(String message) {
        SweetAlertDialog sweet = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweet.setTitleText(message);
        sweet.show();

    }


    private JSONObject getJsonObject() {
        int salesNo = Integer.parseInt(salesManInfosList.get((int) myBinding.salesNameSpinner.getSelectedItemId()).getSalesManNo());

        jsonArraysalesman = new JSONArray();
        for (int i = 0; i < allItemsList.size(); i++) {

            jsonArraysalesman.put(allItemsList.get(i).getJsonObject(salesNo));

        }
        try {
            addsalesmanobject = new JSONObject();
            addsalesmanobject.put("JSN", jsonArraysalesman);
            Log.e("Object", "getAddPlanObject==" + salesNo + "\t" + addsalesmanobject.toString());
            return addsalesmanobject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addsalesmanobject;
    }

    private void filterListcustomer(String name) {
        Log.e("filterListcustomer", "stateFilterVisibl=" + stateFilterVisibl);
        listFilterItem.clear();
        for (int i = 0; i < allItemsList.size(); i++) {
            if (name.length() == 0)// search by visible
            {
                if (stateFilterVisibl == 1)// visible
                {
                    if (allItemsList.get(i).getSelect() == 0) {
                        listFilterItem.add(allItemsList.get(i));
                    }
                } else if (stateFilterVisibl == 2)// visible
                {
                    if (allItemsList.get(i).getSelect() == 1) {
                        listFilterItem.add(allItemsList.get(i));
                    }
                } else listFilterItem.addAll(allItemsList);

            } else {
                if (stateFilterVisibl == 1)
                    if (allItemsList.get(i).getItemNameA().toString().toLowerCase().contains(name.toLowerCase()) &&
                            allItemsList.get(i).getSelect() == 0) {
                        listFilterItem.add(allItemsList.get(i));
                    }

                if (stateFilterVisibl == 2)
                    if (allItemsList.get(i).getItemNameA().toString().toLowerCase().contains(name.toLowerCase()) &&
                            allItemsList.get(i).getSelect() == 1) {
                        listFilterItem.add(allItemsList.get(i));
                    }
                if (stateFilterVisibl == 0)
                    if (allItemsList.get(i).getItemNameA().toString().toLowerCase().contains(name.toLowerCase())) {
                        listFilterItem.add(allItemsList.get(i));
                    }
            }


        }
        Log.e("listCustomer_filtered", "" + listFilterItem.size());
        if (listFilterItem.size() != 0) {
            displayData(listFilterItem);

        } else {
            displayData(allItemsList);
        }
    }

    private void fillSalesManSpinner() {
        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        myBinding.salesNameSpinner.setAdapter(salesNameSpinnerAdapter);
        myBinding. salesNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String salesNum = salesManInfosList.get(position).getSalesManNo();
                Log.e("onItemSelected", "" + salesNum);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void fillItemVisibleSpinner() {
        listItemVisib.add("All");//0
        listItemVisib.add("Visible");
        listItemVisib.add("Hide");
        itemStateVisiAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, listItemVisib);
        itemStateVisiAdapter.setDropDownViewResource(R.layout.spinner_layout);
        myBinding.visiblestateSpinner.setAdapter(itemStateVisiAdapter);
        myBinding.visiblestateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateFilterVisibl = position;
                filterListcustomer("");
                Log.e("fillItemVisibleSpinner", "" + stateFilterVisibl);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}