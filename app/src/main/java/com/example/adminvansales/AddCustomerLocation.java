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

public class AddCustomerLocation extends AppCompatActivity {
    RecyclerView customer_recycler;
//    implements  CustomerLocationAdapter.onCustomerClickListener
    Button saveButton;
    GlobelFunction globelFunction;
    public  static  TextView updateLocationPosi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_location);
        initialView();
    }

    private void initialView() {
        customer_recycler=findViewById(R.id.customer_recycler);
        fillRecyclerCustomer();
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
    }

    private void getPositionUpdated() {

        ExportData exportData=new ExportData(this);
//        int selected=CustomerLocationAdapter
        int selected = ((CustomerLocationAdapter) customer_recycler.getAdapter()).selectedCustomerPosition;
        Log.e("getPositionUpdated","selected"+selected);
        if(selected!=-1)
        exportData.updateCustomerLocatio(listCustomer.get(selected).getCustomerNumber(),listCustomer.get(selected).getLatit_customer()
                ,listCustomer.get(selected).getLong_customer());

    }

    private void fillRecyclerCustomer() {
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