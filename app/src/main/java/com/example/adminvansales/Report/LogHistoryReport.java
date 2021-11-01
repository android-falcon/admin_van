package com.example.adminvansales.Report;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogHistoryDetailReportAdapter;
import com.example.adminvansales.LogHistoryReportAdapter;
import com.example.adminvansales.model.LogHistoryDetail;
import com.example.adminvansales.model.LogHistoryModel;
import com.example.adminvansales.R;

import java.util.ArrayList;
import java.util.List;


public class LogHistoryReport extends AppCompatActivity {

   public  static  List<LogHistoryModel> logHistoryList ;
    public  static  List<LogHistoryDetail> logHistoryDetail ;
    LogHistoryReportAdapter logHistoryReportAdapter;
    ListView logHistoryView;
    ImportData importData;
    TextView from_date_r,to_date_r;
    GlobelFunction globelFunction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_history_report_activity);

        initial();

    }

    public void fillLogHistory(){

        logHistoryReportAdapter=new LogHistoryReportAdapter(LogHistoryReport.this,logHistoryList);
        logHistoryView.setAdapter(logHistoryReportAdapter);

    }



    private void initial() {
        logHistoryDetail=new ArrayList<>();
        logHistoryList=new ArrayList<>();
        globelFunction=new GlobelFunction(LogHistoryReport.this);
        logHistoryView=findViewById(R.id.logHistoryView);
        importData=new ImportData(LogHistoryReport.this);
        importData.getLogHistory(LogHistoryReport.this);
        to_date_r=findViewById(R.id.to_date_r);
        from_date_r=findViewById(R.id.from_date_r);
        to_date_r.setText(globelFunction.DateInToday());
        from_date_r.setText(globelFunction.DateInToday());
        from_date_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.DateClick(from_date_r);
            }
        });

        to_date_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.DateClick(to_date_r);
            }
        });
    }
    public void fillDialogLogDetail(){

        final Dialog dialog = new Dialog(LogHistoryReport.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_list_date_dialog);
        dialog.setCancelable(true);
        final TextView listName,listNO,listType,fromDate,toDate,update,cancel,updateText;

        ListView listOfLogDetail=dialog.findViewById(R.id.listOfLogDetail);

        listName=dialog.findViewById(R.id.listName);
        listNO=dialog.findViewById(R.id.listNo);
        listType=dialog.findViewById(R.id.listType);
        fromDate=dialog.findViewById(R.id.fromDate);
        toDate=dialog.findViewById(R.id.toDate);
        update=dialog.findViewById(R.id.update);
        cancel=dialog.findViewById(R.id.cancel);
        updateText=dialog.findViewById(R.id.updateText);
        listOfLogDetail=dialog.findViewById(R.id.listOfLogDetail);
        //controlText=updateText;

        LogHistoryDetailReportAdapter logHistoryDetailReportAdapter=new LogHistoryDetailReportAdapter(LogHistoryReport.this,logHistoryDetail);
        listOfLogDetail.setAdapter(logHistoryDetailReportAdapter);

        updateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(updateText.getText().toString().equals("1")){

                    Toast.makeText(LogHistoryReport.this, "update", Toast.LENGTH_SHORT).show();
                }else   if(updateText.getText().toString().equals("2")){


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                globelFunction.DateClick(fromDate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                globelFunction.DateClick(toDate);
            }
        });

        if(logHistoryDetail.size()!=0) {
//            listName.setText("" + logHistoryDetail.get(0).getlist);
            listNO.setText("" + logHistoryDetail.get(0).getLIST_NO());

            fromDate.setText("" + logHistoryDetail.get(0).getFROM_DATE());
            toDate.setText("" + logHistoryDetail.get(0).getTO_DATE());


            switch (logHistoryDetail.get(0).getLIST_TYPE()) {
                case "0":
                    listType.setText("Price BY Customer");

                    break;
                case "1":
                    listType.setText("Price Only");

                    break;
                case "2":
                    listType.setText("OFFer");

                    break;

            }
        }

        // importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDate.getText().toString(),""+position,"0",listNo.getText().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listName.getText().toString().equals("")) {

                  //  importData.ifBetweenDate(LogHistoryReport.this, fromDate.getText().toString(), toDate.getText().toString(),""+listPriceOffer.getPO_LIST_TYPE(),"1",listPriceOffer.getPO_LIST_NO(),null);

                } else {
                    listName.setError("Required!");
                }


            }
        });

        dialog.show();

    }

}
