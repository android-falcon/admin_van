package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.AnalyzeAdapter;
import com.example.adminvansales.CashReportAdapter;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.ListAdapterPriceOnly;
import com.example.adminvansales.Model.AnalyzeAccountModel;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.OfferPriceList;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;

public class AnalyzeAccounts extends AppCompatActivity {
    TextView fromDate,toDate,excelConvert,pdfConvert,share;
    GlobelFunction globelFunction;
    String toDay;
    AnalyzeAdapter analyzeAdapter;
    ListView listAnalyzeReport;
    public static List<AnalyzeAccountModel> cashReportList;
    ImportData importData;
    Button previewButton;
    private Calendar myCalendar;
    List<AnalyzeAccountModel> TempReports;
    String toda="";
    public  static EditText listSearch;
    public  static  List<AnalyzeAccountModel> analyzeAccountModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_accounts);
        initial();
    }
    private void initial() {

        fromDate=findViewById(R.id.from_date_r);
        toDate=findViewById(R.id.to_date_r);
        listAnalyzeReport=findViewById(R.id.listAnalyzeReport);
        previewButton=findViewById(R.id.previewButton);
        myCalendar = Calendar.getInstance();
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        globelFunction=new GlobelFunction(AnalyzeAccounts.this);
        toDay=globelFunction.DateInToday();


        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String today = df.format(currentTimeAndDate);
        toda= globelFunction.convertToEnglish(today);
        fromDate.setText(toda);
        toDate.setText(toda);
        cashReportList=new ArrayList<>();
        Log.e("toda",""+toda);
        importData=new ImportData(AnalyzeAccounts.this);
        importData.getAnalyzeReport(AnalyzeAccounts.this,toda,toda);
        TempReports=new ArrayList<>();
        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);
        analyzeAccountModelArrayList=new ArrayList<>();
        listSearch=findViewById(R.id.listSearch);

        listSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(analyzeAccountModelArrayList.size()!=0)
                {
                    searchInPriceOnly();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    void searchInPriceOnly(){
        List<AnalyzeAccountModel> searchRec;
        if (!listSearch.getText().toString().equals("")) {
        searchRec = new ArrayList<>();
            searchRec.clear();
            for (int i = 0; i < analyzeAccountModelArrayList.size(); i++) {
                if (analyzeAccountModelArrayList.get(i).getAccNameA().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||analyzeAccountModelArrayList.get(i).getAccNameA().toLowerCase().contains(listSearch.getText().toString().toLowerCase())
                       ) {
                    searchRec.add(analyzeAccountModelArrayList.get(i));
                }
            }

            analyzeAdapter = new AnalyzeAdapter(AnalyzeAccounts.this, searchRec);
            listAnalyzeReport.setAdapter(analyzeAdapter);



        } else {

            analyzeAdapter = new AnalyzeAdapter(AnalyzeAccounts.this, analyzeAccountModelArrayList);
            listAnalyzeReport.setAdapter(analyzeAdapter);
        }
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.previewButton :
                    previewFunction();
                    break;

                case R.id.from_date_r :
                   DateClick(fromDate);
                    break;

                case R.id.to_date_r :
                    DateClick(toDate);
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
    public void previewFunction(){

        importData.getAnalyzeReport(AnalyzeAccounts.this,fromDate.getText().toString(),toDate.getText().toString());

    }
    public void DateClick(TextView dateText){

        new DatePickerDialog(AnalyzeAccounts.this, openDatePickerDialog(dateText), myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final TextView DateText) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy";


                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                DateText.setText(sdf.format(myCalendar.getTime()));
            }

        };
        return date;
    }
    private File convertToPdf() {
        PdfConverter pdf =new PdfConverter(AnalyzeAccounts.this);
        File file=pdf.exportListToPdf(TempReports,"AnalyzeAccountReport",toDay,6);
        return file;
    }

    private File convertToExcel() {

        ExportToExcel exportToExcel=new ExportToExcel();
        File file= exportToExcel.createExcelFile(AnalyzeAccounts.this,"AnalyzeAccountReport.xls",6,TempReports);
      //Log.e("convertToExcel",""+file);
        if(file!=null){
          Toast.makeText(this, "Exported To Excel ", Toast.LENGTH_SHORT).show();
      }
        return file;
    }

    public void shareWhatsApp(){
        globelFunction.shareWhatsAppA(convertToPdf(),1);
    }

    public void fillCashAdapter() {
        String salesNo;
        TempReports.clear();
            TempReports=new ArrayList<>(analyzeAccountModelArrayList);
            analyzeAdapter = new AnalyzeAdapter(AnalyzeAccounts.this, analyzeAccountModelArrayList);
        listAnalyzeReport.setAdapter(analyzeAdapter);

    }
}
