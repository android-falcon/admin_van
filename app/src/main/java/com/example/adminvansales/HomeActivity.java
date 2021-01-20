package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.adminvansales.Model.SalesManInfo;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ListOfferReport;
import com.example.adminvansales.Report.PaymentDetailsReport;

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
Button locationButton,ReportButton,offerButton;
LinearLayout ReportLinear;
TextView customerLogReport,paymentReport,cashReport,offerReport;

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

        offerButton=findViewById(R.id.offerButton);
        locationButton=findViewById(R.id.LocationButton);
        ReportButton=findViewById(R.id.ReportButton);
        recyclerViews=findViewById(R.id.res);
        waitList=findViewById(R.id.waitList);
        ReportLinear=findViewById(R.id.ReportLinear);
        ReportLinear.setVisibility(View.GONE);
        customerLogReport=findViewById(R.id.customerLogReport);
        paymentReport=findViewById(R.id.paymentReport);
        cashReport=findViewById(R.id.cashReport);
        offerReport=findViewById(R.id.offerReport);

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


        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent offerIntent=new Intent(HomeActivity.this,OfferPriceList.class);
                startActivity(offerIntent);

            }
        });

        ReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ReportLinear.getVisibility()==View.VISIBLE) {
                    ReportLinear.setVisibility(View.GONE);
                }else {
                    ReportLinear.setVisibility(View.VISIBLE);
                }
            }
        });

        customerLogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent=new Intent(HomeActivity.this, CustomerLogReport.class);
                startActivity(locationIntent);

            }
        });

        paymentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent=new Intent(HomeActivity.this, PaymentDetailsReport.class);
                startActivity(locationIntent);

            }
        });
        cashReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent=new Intent(HomeActivity.this, CashReport.class);
                startActivity(locationIntent);

            }
        });
        offerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent listIntent=new Intent(HomeActivity.this, ListOfferReport.class);
                startActivity(listIntent);

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
