package com.example.adminvansales;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Adapters.ItemReportAdapter;
import com.example.adminvansales.Report.CustomerLogReport;
import com.example.adminvansales.Report.ReportsPopUpClass;
import com.example.adminvansales.Report.UnCollectedData;
import com.example.adminvansales.model.ItemReportModel;
import com.example.adminvansales.model.ItemsRequsts;
import com.example.adminvansales.model.Payment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import com.example.adminvansales.model.LocaleAppUtils;
public class ItemReport extends AppCompatActivity {

    TextView fromDate, toDate;
    Spinner salesManSpinner;
    Button previewButton;
    ListView listItemReport;
    public static List<ItemReportModel> itemReportModelsList;
    TextView total;
    ItemReportAdapter itemReportAdapter;
    ImportData importData;
    GlobelFunction globelFunction;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    static TextView total_item_qty;
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel settingModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(ItemReport.this);
        setContentView(R.layout.item_report_activity);
        settingModel = new com.example.adminvansales.model.SettingModel();
        databaseHandler = new DataBaseHandler(ItemReport.this);

        initial();


    }

    void initial() {
        LinearLayout linearMain=findViewById(R.id.linearMain);
        try{
            if(LogIn.languagelocalApp.equals("ar"))
            {
                linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            else{
                if(LogIn.languagelocalApp.equals("en"))
                {
                    linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch ( Exception e)
        {
            linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        total=findViewById(R.id.total);
        total_item_qty = findViewById(R.id.total_itemqty_text);

        globelFunction = new GlobelFunction(ItemReport.this);
        itemReportModelsList = new ArrayList<>();
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        salesManSpinner = findViewById(R.id.salesManSpinner);
        previewButton = findViewById(R.id.previewButton);
        listItemReport = findViewById(R.id.listItemReport);

        fromDate.setText(globelFunction.DateInToday());
        toDate.setText(globelFunction.DateInToday());
        importData = new ImportData(ItemReport.this);
        settingModel = databaseHandler.getAllSetting();
        if(salesManNameList.size()==0)    globelFunction. getSalesManInfo (ItemReport.this,9);

        fillSalesManSpinner();
        try {
            String no = globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());

            if (settingModel.getImport_way().equals("0"))
            //    importData.getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), "-1");
                importData.  fetchItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), "-1");
            else if (settingModel.getImport_way().equals("1"))
            //    importData.IIS_getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);
            importData.  fetchItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);
        } catch (Exception e) {
        }

        fromDate.setOnClickListener(onClickListener);
        toDate.setOnClickListener(onClickListener);

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int positionSales = salesManSpinner.getSelectedItemPosition();
//

                String salesNo;
//                if (positionSales == 0 || positionSales == -1) {
//                    salesNo = "-1";
////                    Log.e("salesNo-1", "" + salesNo + "  ");
//
//
//                      String no= globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());
//
//                    if( settingModel.getImport_way().equals("0"))
//                        importData.getItemReport(ItemReport.this,fromDate.getText().toString(),toDate.getText().toString(),"-1");
//                    else if( settingModel.getImport_way().equals("1"))
//                        importData. IIS_getItemReport(ItemReport.this,fromDate.getText().toString(),toDate.getText().toString(),no);
//
//
//                }
//                else {

                if (positionSales != -1) {

                    salesNo = salesManInfosList.get(positionSales).getSalesManNo();
                    // Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales).getSalesName() + "    " + positionSales);


                    String no = globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());

                    if (settingModel.getImport_way().equals("0"))
                        importData.getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), salesNo);
                    else if (settingModel.getImport_way().equals("1"))
                        importData.IIS_getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);

                } else {

                    Toast.makeText(ItemReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

                }

//                }


            }
        });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setSelectedItemId(R.id.action_reports);

        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_plan:

                                startActivity(new Intent(getApplicationContext(), PlanSalesMan.class));
                                overridePendingTransition(0, 0);

                                return true;

                            case R.id.action_reports:

                                ReportsPopUpClass popUpClass = new ReportsPopUpClass();
                                popUpClass.showPopupWindow(item.getActionView(), ItemReport.this);

                                return true;

                            case R.id.action_location:

                                {   startActivity(new Intent(getApplicationContext(), SalesmanMaps_FirebaseActivity.class));
                                    overridePendingTransition(0, 0);}

                                return true;

                            case R.id.action_notifications:

                                startActivity(new Intent(getApplicationContext(), RequstNotifaction.class));
                                overridePendingTransition(0, 0);

                                return true;
                        }
                        return false;
                    }
                });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.from_date_r:
                    globelFunction.DateClick(fromDate);
                    break;
                case R.id.to_date_r:
                    globelFunction.DateClick(toDate);
                    break;

            }

        }
    };

    public void fillItemAdapter() {


        itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
        listItemReport.setAdapter(itemReportAdapter);

        total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));


    }


    public void fillSalesManSpinner() {
         salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesManSpinner.setAdapter(salesNameSpinnerAdapter);

    }

    public static void totalqty() {
        total_item_qty.setText("0");
        int sum = 0;
        for (int i = 0; i < itemReportModelsList.size(); i++)
            sum += Double.parseDouble(itemReportModelsList.get(i).getQty());
        total_item_qty.setText(sum + "");

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
    }

}



