package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
    public  static  TextView waitList;
    RelativeLayout notifyLayout,accountLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initalView();
        ImportData importData=new ImportData(HomeActivity.this);
        importData.getSalesManInfo();


    }

    private void initalView() {
        notifyLayout=findViewById(R.id.notifyLayout);
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
