package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.example.adminvansales.model.SalesManInfo;

import java.io.File;

import static com.example.adminvansales.ImportData.cashReportList;

public class NewCashReport extends AppCompatActivity {
    TextView date_editReport_cash,TOdate_editReport_cash;
    String toDay;
    GlobelFunction globelFunction;
    SalesManInfo info;
    ImageButton excelConvert, pdfConvert;
 public static    TextView Respons;
    ImportData importData ;
    TextView cash_sal, credit_sale, total_sale,text_return_cash,text_return_cridt;
    TextView cash_paymenttext, creditPaymenttext, nettext,total_cashtext,creditCard,salesman_name;
   Button previewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cash_report);
        date_editReport_cash=findViewById(R.id.date_editReport_cash);
        salesman_name=findViewById(R.id.salesman_name);
        LinearLayout linearMain=findViewById(R.id.mainLayout);
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

        importData=new ImportData(NewCashReport.this);
        Respons=findViewById(R.id.Respons);
        text_return_cash=findViewById(R.id.text_return_cash);
        text_return_cridt=findViewById(R.id.text_return_cridt);
        TOdate_editReport_cash=findViewById(R.id.TOdate_editReport_cash);
        previewButton = (Button) findViewById(R.id.preview_cash_report);

        cash_sal = (TextView) findViewById(R.id.text_cash_sales);
        credit_sale = (TextView) findViewById(R.id.text_credit_sales);
        total_sale = (TextView) findViewById(R.id.text_total_sales);
        cash_paymenttext = (TextView) findViewById(R.id.text_cash_PaymentReport);
        creditPaymenttext = (TextView) findViewById(R.id.text_cheque_paymentReport);
        nettext = (TextView) findViewById(R.id.text_net_paymentReport);
        creditCard = (TextView) findViewById(R.id.text_credit);
        total_cashtext=(TextView) findViewById(R.id.text_total_cash);

        Respons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             if(editable.length()!=0){
                 if(Respons.getText().toString().equals("fill")){
                     previewFunction();
                 }else
                 {

                 }
             }
            }
        });
      info = (SalesManInfo) getIntent().getSerializableExtra("SalesManInfoL");
        Log.e("SalesManInfoL==", info.getSalesManNo()+"")  ;
        salesman_name.setText(info.getSalesName()+"");
        globelFunction = new GlobelFunction(NewCashReport.this);
        toDay = globelFunction.DateInToday();
        date_editReport_cash.setText(toDay);
        TOdate_editReport_cash.setText(toDay);
        excelConvert = findViewById(R.id.excelConvert);
        pdfConvert = findViewById(R.id.pdfConvert);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        date_editReport_cash.setOnClickListener(onClick);
        TOdate_editReport_cash.setOnClickListener(onClick);
        previewButton.setOnClickListener(onClick);
        importData.IIS_getCashReport(NewCashReport.this, date_editReport_cash.getText().toString(),TOdate_editReport_cash.getText().toString() , info.getSalesManNo(),2);


    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {

                case R.id.preview_cash_report:
                    importData.IIS_getCashReport(NewCashReport.this, date_editReport_cash.getText().toString(),TOdate_editReport_cash.getText().toString() , info.getSalesManNo(),2);

                    break;

                case R.id.date_editReport_cash:
                    globelFunction.DateClick(date_editReport_cash);
                    break;
                case R.id.TOdate_editReport_cash:
                    globelFunction.DateClick(TOdate_editReport_cash);
                    break;

                case R.id.pdfConvert:
                    convertToPdf();
                    break;
                case R.id.excelConvert:
                    convertToExcel();
                    break;
            }

        }
    };
    public void previewFunction() {
        Log.e("previewFunction,==","previewFunction");

    //    importData.IIS_getCashReport(NewCashReport.this, date_editReport_cash.getText().toString(),date_editReport_cash.getText().toString() , info.getSalesManNo(),2);
        try {


            double sum = 0, nettotal = 0, TOTAL_net_salesVal = 0, TOTAL_netpaymentVal = 0, TOTAL_total_cashVal = 0;
            for (int i = 0; i < cashReportList.size(); i++) {
                sum += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());

                nettotal += Double.parseDouble(cashReportList.get(i).getPtotalCredite()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());
                TOTAL_net_salesVal += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getTotalCredite());
                TOTAL_netpaymentVal += Double.parseDouble(cashReportList.get(i).getPtotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCredite());
                TOTAL_total_cashVal += Double.parseDouble(cashReportList.get(i).getTotalCash()) + Double.parseDouble(cashReportList.get(i).getPtotalCash());
                cash_sal.setText(""+cashReportList.get(0).getTotalCash());
                credit_sale.setText(""+cashReportList.get(0).getTotalCredite());
                double returnCash=0;
                double returncridt=0;
                try {
                     returnCash= Double.parseDouble(cashReportList.get(0).getRETURNDCASH());
                     returncridt=Double.parseDouble(cashReportList.get(0).getRETURNDCREDITE());

                }catch (Exception exception){
                    returnCash=0;
                    returncridt=0;
                    Log.e("exception==",exception.getMessage());
                }
                double netSalesT=Double.parseDouble(cashReportList.get(0).getTotalCash()) + Double.parseDouble(cashReportList.get(0).getTotalCredite())
                        -returnCash-returncridt;

                total_sale.setText(""+globelFunction.convertToEnglish(String. format("%.3f",netSalesT)));
                text_return_cash.setText(""+cashReportList.get(0).getRETURNDCASH());
                text_return_cridt.setText(""+cashReportList.get(0).getRETURNDCREDITE());
                cash_paymenttext.setText(""+cashReportList.get(0).getPtotalCash());
                creditPaymenttext.setText(""+cashReportList.get(0).getPtotalCredite());
                nettext.setText(""+TOTAL_netpaymentVal);

                total_cashtext.setText(""+globelFunction.convertToEnglish(String. format("%.3f",TOTAL_total_cashVal)));
                creditCard.setText(""+cashReportList.get(0).getPtotalCrediteCard());

            }
        }catch (Exception exception){
            Log.e("previewFunction,exception==",exception.getMessage());
        }

    }

    private File convertToPdf() {
        PdfConverter pdf = new PdfConverter(NewCashReport.this);
        File file = pdf.exportListToPdf(cashReportList, "NewCashReport", toDay, 9);
        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel = new ExportToExcel();
        exportToExcel.createExcelFile(NewCashReport.this, "NewCashReport.xls", 9, cashReportList);
        // return file;
    }
}