package com.example.adminvansales.Report;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.ListOfferReportAdapter;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.model.ListPriceOffer;
import com.example.adminvansales.model.OfferListModel;
import com.example.adminvansales.model.customerInfoModel;
import com.example.adminvansales.OfferPriceList;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.adminvansales.model.LocaleAppUtils;
public class ListOfferReport extends AppCompatActivity {

    TextView fromDate,toDate,excelConvert,pdfConvert,share;
    public  static TextView control;
    GlobelFunction globelFunction;
    String toDay;
    ListOfferReportAdapter listOfferReportAdapter;
    ListView listCashReport;
    public static List<ListPriceOffer> listPriceOffers;

    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    List<ListPriceOffer> TempReports;
    ListPriceOffer listTempClose;
    static public List<customerInfoModel> selectCustomerIfClose;
    static public List<OfferListModel> selectItemIfUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(ListOfferReport.this);
        setContentView(R.layout.list_price_offer);
        initial();

        control.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("closeList","lll9 "+control.getText().toString());

                if(control.getText().toString().equals("close")) {
                    Log.e("closeList", "lll3");
                    Intent intent = new Intent(ListOfferReport.this, OfferPriceList.class);
                    intent.putExtra("ListIntent", "CloseList");
                    intent.putExtra("listClose", listTempClose);
                    control.setText("0");
                    startActivity(intent);
                }else      if(control.getText().toString().equals("update")){
                    Log.e("UpdateList","lll3");
                    Intent intent=new Intent(ListOfferReport.this, OfferPriceList.class);
                    intent.putExtra("ListIntent","UpdateList");
                    intent.putExtra("listUpdate",listTempClose);
                    Log.e("ListType",""+listTempClose.getPO_LIST_NO());
                    control.setText("0");
                    startActivity(intent);

                }else  if(control.getText().toString().equals("main")){
                    importData.getAllList(ListOfferReport.this);
                    control.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void initial() {
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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        fromDate=findViewById(R.id.from_date_r);
        control=findViewById(R.id.control);
        toDate=findViewById(R.id.to_date_r);
        listCashReport=findViewById(R.id.listCashReport);
        previewButton=findViewById(R.id.previewButton);
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        globelFunction=new GlobelFunction(ListOfferReport.this);
        toDay=globelFunction.DateInToday();
        listPriceOffers=new ArrayList<>();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        TempReports=new ArrayList<>();
        listTempClose=new ListPriceOffer();
        selectCustomerIfClose=new ArrayList<>();
        importData=new ImportData(ListOfferReport.this);
        importData.getAllList(ListOfferReport.this);

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);


//        fillSalesManSpinner();

    }

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.previewButton :
                    previewFunction();
                    break;

                case R.id.from_date_r :
                    globelFunction.DateClick(fromDate);
                    break;

                case R.id.to_date_r :
                    globelFunction.DateClick(toDate);
                    break;
                case R.id.excelConvert :
                    convertToExcel();
                    break;
                case R.id.pdfConvert :
                    convertToPdf();
                    break;
                case R.id.share:
                    shareWhatsApp();
                    break;

            }

        }
    };

    private File convertToPdf() {
        PdfConverter pdf =new PdfConverter(ListOfferReport.this);
        File file=pdf.exportListToPdf(TempReports,"Cash Report",toDay,4);
        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel=new ExportToExcel();
       exportToExcel.createExcelFile(ListOfferReport.this,"ListOffer.xls",4,TempReports);

    }
//    private File convertToExcel() {
//
//        ExportToExcel exportToExcel=new ExportToExcel();
//        File file= exportToExcel.createExcelFile(ListOfferReport.this,"ListOffer.xls",4,TempReports);
//        return file;
//    }

    public void shareWhatsApp(){
       globelFunction.shareWhatsAppA(convertToPdf(),1);
    }


public void getItem(ListPriceOffer listOfferReports){
    listTempClose=listOfferReports;
}


    public void previewFunction(){


       importData.getAllList(ListOfferReport.this);

    }


    public void fillCashAdapter(){


        String payKind="-1";
        int listType=-1;


        int positionSales=salesNameSpinner.getSelectedItemPosition();

        if (positionSales == 0 || positionSales==-1) {
            listType = 0;
            Log.e("salesNo-1", "" + listType +"  ");

        } else{
            listType=positionSales;
        }
        TempReports.clear();

        if(listType!=0) {
            for (int i = 0; i < listPriceOffers.size(); i++) {
                if (listPriceOffers.get(i).getPO_LIST_TYPE().equals("" + (listType-1))) {
                    ListPriceOffer listPriceOffer = new ListPriceOffer();
                    listPriceOffer = listPriceOffers.get(i);
                    TempReports.add(listPriceOffer);
                }
            }
        }

        if(TempReports.size()==0){
            TempReports=new ArrayList<>(listPriceOffers);
        }
        listOfferReportAdapter = new ListOfferReportAdapter(ListOfferReport.this, TempReports);
        listCashReport.setAdapter(listOfferReportAdapter);
    }

}
