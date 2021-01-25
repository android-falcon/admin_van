package com.example.adminvansales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.adminvansales.Model.SalesManInfo;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ListOfferReport;
import com.example.adminvansales.Report.PaymentDetailsReport;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.ImportData.listSalesMan;

public class HomeActivity extends AppCompatActivity {
    public List<SalesManInfo> picforbar;
    public CarouselLayoutManager layoutManagerd;
    public RecyclerView recyclerViews;
    public static TextView waitList, addVanSales;
    RelativeLayout notifyLayout, accountLayout;
    GlobelFunction globelFunction;
    Button locationButton, ReportButton, offerButton;
    LinearLayout ReportLinear;
    public static EditText editPassword;
    TextView customerLogReport, paymentReport, cashReport, offerReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initalView();
        globelFunction = new GlobelFunction(HomeActivity.this);
        globelFunction.getSalesManInfo(HomeActivity.this, 1);


    }

    private void initalView() {
        notifyLayout = findViewById(R.id.notifyLayout);
        addVanSales = findViewById(R.id.addVanSales);
        accountLayout = findViewById(R.id.accountLayout);


        addVanSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEditSales = new Intent(HomeActivity.this, EditSalesMan.class);
                startActivity(intentEditSales);
            }
        });

        offerButton = findViewById(R.id.offerButton);
        locationButton = findViewById(R.id.LocationButton);
        ReportButton = findViewById(R.id.ReportButton);
        recyclerViews = findViewById(R.id.res);
        waitList = findViewById(R.id.waitList);
        ReportLinear = findViewById(R.id.ReportLinear);
        ReportLinear.setVisibility(View.GONE);
        customerLogReport = findViewById(R.id.customerLogReport);
        paymentReport = findViewById(R.id.paymentReport);
        cashReport = findViewById(R.id.cashReport);
        offerReport = findViewById(R.id.offerReport);

        waitList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("1")) {
                    if (listSalesMan.size() != 0) {
                        showAllSalesManData(listSalesMan);
                    }
                }

            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent locationIntent = new Intent(HomeActivity.this, SalesmanMapsActivity.class);
                startActivity(locationIntent);

            }
        });


        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent offerIntent = new Intent(HomeActivity.this, OfferPriceList.class);
                startActivity(offerIntent);

            }
        });

        ReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReportLinear.getVisibility() == View.VISIBLE) {
                    ReportLinear.setVisibility(View.GONE);
                } else {
                    ReportLinear.setVisibility(View.VISIBLE);
                }
            }
        });

        customerLogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent = new Intent(HomeActivity.this, CustomerLogReport.class);
                startActivity(locationIntent);

            }
        });

        paymentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent = new Intent(HomeActivity.this, PaymentDetailsReport.class);
                startActivity(locationIntent);

            }
        });
        cashReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent locationIntent = new Intent(HomeActivity.this, CashReport.class);
                startActivity(locationIntent);

            }
        });
        offerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportLinear.setVisibility(View.GONE);
                Intent listIntent = new Intent(HomeActivity.this, ListOfferReport.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changePassowrd) {
            openchangePasswordDialog();

        } else if (id == R.id.button_notif) {
            finish();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.button_account) {
            finish();
            Intent i = new Intent(HomeActivity.this, AccountStatment.class);
            startActivity(i);
        }
        return super.

                onOptionsItemSelected(item);
    }

    private void openchangePasswordDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_setting);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);

        editPassword = (EditText) dialog.findViewById(R.id.passowrdEdit);
        getPassword();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editPassword.getText().toString().equals("")) {
                    savePassowrdSetting(editPassword.getText().toString());
                    dialog.dismiss();
                } else {
                    editPassword.setError("Required");
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getPassword() {
        ExportData exportData = new ExportData(HomeActivity.this);
        exportData.getPassowrdSetting();
    }

    private void getCurentPassowrd(EditText editPassword) {
        editPassword.setText("303090");
    }

    private void savePassowrdSetting(String passowrd) {
        ExportData exportData = new ExportData(HomeActivity.this);
        exportData.savePassowrdSetting(passowrd);
    }


}
