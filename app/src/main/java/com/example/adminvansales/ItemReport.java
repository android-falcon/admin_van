package com.example.adminvansales;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Model.ItemReportModel;
import com.example.adminvansales.Report.CashReport;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class ItemReport extends AppCompatActivity {

    TextView fromDate,toDate;
    Spinner salesManSpinner;
    Button previewButton;
    ListView listItemReport;
   public static List<ItemReportModel> itemReportModelsList;
    ItemReportAdapter itemReportAdapter;
    ImportData importData;
    GlobelFunction globelFunction;
    ArrayAdapter<String>salesNameSpinnerAdapter ;
   static TextView total_item_qty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_report_activity);

        initial();

    }

    void initial() {


        total_item_qty=findViewById(R.id.total_itemqty_text);

        globelFunction = new GlobelFunction(ItemReport.this);
        itemReportModelsList = new ArrayList<>();
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        salesManSpinner = findViewById(R.id.salesManSpinner);
        previewButton = findViewById(R.id.previewButton);
        listItemReport = findViewById(R.id.listItemReport);

        importData = new ImportData(ItemReport.this);
        importData.getItemReport(ItemReport.this,fromDate.getText().toString(),toDate.getText().toString(),"-1");

        fromDate.setText(globelFunction.DateInToday());
        toDate.setText(globelFunction.DateInToday());

        fromDate.setOnClickListener(onClickListener);
        toDate.setOnClickListener(onClickListener);
        fillSalesManSpinner();
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int positionSales = salesManSpinner.getSelectedItemPosition();
//

                String salesNo;
                if (positionSales == 0 || positionSales == -1) {
                    salesNo = "-1";
                    Log.e("salesNo-1", "" + salesNo + "  ");
                    importData.getItemReport(ItemReport.this,fromDate.getText().toString(),toDate.getText().toString(),"-1");

                } else {
                    salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
                    Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
                    importData.getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), salesNo);

                }


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

        public void fillItemAdapter () {


            itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
            listItemReport.setAdapter(itemReportAdapter);

        }


        public void fillSalesManSpinner () {

            salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
            salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
            salesManSpinner.setAdapter(salesNameSpinnerAdapter);

        }

        public static void totalqty(){
            total_item_qty.setText("0");
           int sum=0;
            for (int i=0;i<itemReportModelsList.size();i++)
                sum+=Integer.parseInt(itemReportModelsList.get(i).getQty());
            total_item_qty.setText(sum+"");

        }








    }



