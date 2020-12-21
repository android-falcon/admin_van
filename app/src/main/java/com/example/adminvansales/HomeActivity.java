package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.adminvansales.Model.SalesManInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.ImportData.listSalesMan;

public class HomeActivity extends AppCompatActivity {
    public List<SalesManInfo> picforbar;
    public CarouselLayoutManager layoutManagerd;
    public RecyclerView  recyclerViews;
    public  static  TextView waitList,addVanSales;
    RelativeLayout notifyLayout,accountLayout;
GlobelFunction globelFunction;
Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initalView();
        globelFunction=new GlobelFunction(HomeActivity.this);
        globelFunction.getSalesManInfo(HomeActivity.this,1);


    }

    private void initalView() {
        notifyLayout=findViewById(R.id.notifyLayout);
        addVanSales=findViewById(R.id.addVanSales);
        notifyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finish();
                    Intent i = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(i);

            }
        });
        accountLayout=findViewById(R.id.accountLayout);
        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(HomeActivity.this, AccountStatment.class);
                startActivity(i);
            }
        });

        addVanSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEditSales=new Intent(HomeActivity.this,EditSalesMan.class);
                startActivity(intentEditSales);
            }
        });

        locationButton=findViewById(R.id.LocationButton);
        recyclerViews=findViewById(R.id.res);
        waitList=findViewById(R.id.waitList);
        waitList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("1"))
                {
                    if(listSalesMan.size()!=0)
                    {
                        showAllSalesManData(listSalesMan);
                    }
                }

            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent locationIntent=new Intent(HomeActivity.this,SalesmanMapsActivity.class);
                startActivity(locationIntent);

            }
        });



    }
   public void showAllSalesManData(List<SalesManInfo> listSalesMan) {
//        picforbar = dbHandler.getAllAcCount();
//        new GetAllAccount().execute();
        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);

        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViews.setAdapter(new SalesManAdapter(this, listSalesMan));
        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition(2);
        recyclerViews.requestFocus();


    }
}
