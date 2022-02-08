package com.example.adminvansales;

import static com.example.adminvansales.ImportData.listCustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.adminvansales.Adapters.CustomerAdapter;
import com.example.adminvansales.Adapters.CustomerLocationAdapter;
import com.example.adminvansales.model.CustomerInfo;

import java.util.ArrayList;

public class AddCustomerLocation extends AppCompatActivity {
    RecyclerView customer_recycler;
//    implements  CustomerLocationAdapter.onCustomerClickListener
    Button saveButton;
    GlobelFunction globelFunction;
    public ArrayList<CustomerInfo> listCustomer_filtered = new ArrayList<CustomerInfo>();
    public  static  TextView updateLocationPosi,customerSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_location);
        initialView();
    }

    private void initialView() {
        customer_recycler=findViewById(R.id.customer_recycler);
        fillRecyclerCustomer(listCustomer);
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v->{


        });
        updateLocationPosi=findViewById(R.id.updateLocationPosi);
        updateLocationPosi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0)
                {
                    if(s.toString().equals("updateLoc"))
                    {
                        getPositionUpdated();
                    }
                }

            }
        });
        customerSearch=findViewById(R.id.customerSearch);
        customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0){
                    if(!s.toString().equals(""))
                    {
                        filterListcustomer(s.toString());
                    }else {
                        fillRecyclerCustomer(listCustomer);
                    }
                }

            }
        });
    }

    private void filterListcustomer(String name) {
        listCustomer_filtered.clear();
        for (int i=0;i<listCustomer.size();i++){
            if(listCustomer.get(i).getCustomerName().contains(name)){
                listCustomer_filtered.add(listCustomer.get(i));
            }

        }
        Log.e("listCustomer_filtered",""+listCustomer_filtered.size());
        if(listCustomer_filtered.size()!=0){
            fillRecyclerCustomer(listCustomer_filtered);

        }else {
            fillRecyclerCustomer(listCustomer);
        }
    }
    private void getPositionUpdated() {

        ExportData exportData=new ExportData(this);
//        int selected=CustomerLocationAdapter
        int selected = ((CustomerLocationAdapter) customer_recycler.getAdapter()).selectedCustomerPosition;
        String custNo= ((CustomerLocationAdapter) customer_recycler.getAdapter()).custNo_selected;
        CustomerInfo customerInfo=getCustomer(custNo);
        String latitSelected= customerInfo.getLatit_customer();
        String longSelected= customerInfo.getLong_customer();
        Log.e("getPositionUpdated","selected"+selected+"\t"+latitSelected+"\t"+longSelected);
        if(selected!=-1)
        exportData.updateCustomerLocatio(custNo,latitSelected
                ,longSelected);

    }
    public  CustomerInfo getCustomer(String cusNumber){
        for(int i=0;i<listCustomer.size();i++){
            if(listCustomer.get(i).getCustomerNumber().equals(cusNumber))
            {
                return  listCustomer.get(i);


            }
        }
        return null;
    }

    private void fillRecyclerCustomer(ArrayList<CustomerInfo> listCustomer) {
        if (listCustomer.size() != 0)
        {
            Log.e("fillRecyclerCustomer", "" + listCustomer.size());
            final LinearLayoutManager layoutManager;
            layoutManager = new LinearLayoutManager(AddCustomerLocation.this);
            customer_recycler.setLayoutManager(layoutManager);
            CustomerLocationAdapter adapter = new CustomerLocationAdapter(listCustomer, AddCustomerLocation.this);
            customer_recycler.setAdapter(adapter);

        }
    }

//    @Override
//    public void onCustomerClick(int position) {
//        Log.e("onCustomerClick","1="+position);
//
//        Intent intent =new Intent(this,CustomerLocationSelect.class);
//        startActivity(intent);
//    }
}