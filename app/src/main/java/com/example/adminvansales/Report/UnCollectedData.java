package com.example.adminvansales.Report;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.PlanSalesMan;
import com.example.adminvansales.model.Payment;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.ImportData.customername;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.paymentChequesList;
import static com.example.adminvansales.ImportData.unCollectlList;


public class UnCollectedData extends AppCompatActivity {

    public TextView recivedAmount_text, paidAmountText;
    public static TextView resultData;
    ArrayList<Payment> paymentArrayList;
    TableLayout tableCheckData;
    Button preview_button_account;
    ImageButton excelConvert, pdfConvert, share, backBtn;
    Spinner customerSpinner;
    String customerId = "";
    int position = 0;
    ImportData importJason;
    public static EditText listSearch;
    int[] listImageIcone = new int[]{R.drawable.pdf_, R.drawable.excel_e, R.drawable.ic_share_black_24dp};
    GlobelFunction globelFunction;
    String toDay;
    private AutoCompleteTextView customers_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_collected_data);
        initialView();

        importJason = new ImportData(UnCollectedData.this);

        preview_button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerId = getCusromerNUM(customers_list.getText().toString());
                Log.e("customerId", customerId+"");
                if (!customerId.equals("")) {
                    paymentChequesList.clear();
                    paymentArrayList.clear();
                    Log.e(" paymentArrayList3===", paymentArrayList.size() + "");
                    Log.e(" paymentChequesList===", paymentChequesList.size() + "");
                    importJason.getUnCollectedCheques(customerId);
                    importJason.getAllcheques(customerId);
                } else {
                    new SweetAlertDialog(UnCollectedData.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Select Customer")
                            .setContentText("")
                            .show();
                }
//
            }
        });
        if (customername.size() != 0) {
            Log.e("customernameUncollected", "" + customername.size());
            fillCustomerSpenner();
        }
        customers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //    getCusromerNUM( customerSpinner.getSelectedItem().toString());
                customerId=  getCusromerNUM( customers_list.getText().toString());
                Log.e("onItemSelected",""+customerId);
            }
        });
//        inflateBoomMenu();
    }

    private String getCusromerNUM(String name) {
        for (int i = 0; i < listCustomer.size(); i++)
            if (name.trim().equals(listCustomer.get(i).getCustomerName())) {
                return listCustomer.get(i).getCustomerNumber();
            }

        return "";
    }

//    private void inflateBoomMenu() {
//        BoomMenuButton bmb = (BoomMenuButton)findViewById(R.id.bmb);
//
//        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
//        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
//        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_1);
////        SimpleCircleButton.Builder b1 = new SimpleCircleButton.Builder();
//
//
//        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
//            bmb.addBuilder(new SimpleCircleButton.Builder()
//                    .normalImageRes(listImageIcone[i])
//
//                    .listener(new OnBMClickListener() {
//                        @RequiresApi(api = Build.VERSION_CODES.M)
//                        @Override
//                        public void onBoomButtonClick(int index) {
//                            // When the boom-button corresponding this builder is clicked.
//                            switch (index)
//                            {
//                                case 0:
//                                    convertToPdf();
//
//                                    break;
//                                case 1:
//                                    convertToExcel();
//                                    break;
//                                case 2:
//                                    shareWhatsApp();
//                                    break;
//
//                            }
//                        }
//                    }));
////            bmb.addBuilder(builder);
//
//
//        }
//    }

    private File convertToPdf() {
        PdfConverter pdf = new PdfConverter(UnCollectedData.this);
        File file = pdf.exportListToPdf(paymentArrayList, "UncollectedChequesReport", toDay, 5);
        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel = new ExportToExcel();
        exportToExcel.createExcelFile(UnCollectedData.this, "UncollectedChequesReport.xls", 5, paymentArrayList);
//        if(file!=null)
//        {
        Toast.makeText(this, "Exported To Excel ", Toast.LENGTH_SHORT).show();
//        }
//        return file;
    }

    public void shareWhatsApp() {
        globelFunction.shareWhatsAppA(convertToPdf(), 1);
    }

    private void initialView() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        paymentArrayList = new ArrayList<>();

        tableCheckData = (TableLayout) findViewById(R.id.TableCheckData);
        recivedAmount_text = findViewById(R.id.recivedAmount_text);
        paidAmountText = findViewById(R.id.paidAmountText);
        resultData = findViewById(R.id.result);
        resultData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    if (s.toString().equals("yes")) {
                        recivedAmount_text.setText(unCollectlList.get(0).getRECVD().toString());
                        paidAmountText.setText(unCollectlList.get(0).getPAIDAMT().toString());
                    }
                    if (s.toString().equals("payment")) {
                        //    Log.e(" paymentArrayList5===", paymentArrayList.size()+"");
                        paymentArrayList.clear();
                        paymentArrayList.addAll(paymentChequesList);
                        Log.e(" paymentArrayList5===", paymentArrayList.size() + "");
                        fillTable();
                    } else {
                        Log.e(" paymentArrayList1===", paymentArrayList.size() + "");
                        paymentArrayList.clear();
                        Log.e(" paymentArrayList2===", paymentArrayList.size() + "");
                        fillTable();
                    }
                }

            }
        });
        // getPayment();
        preview_button_account = findViewById(R.id.preview_button_account);
//        customerSpinner = (Spinner) findViewById(R.id.cat);
        globelFunction = new GlobelFunction(UnCollectedData.this);
        toDay = globelFunction.DateInToday();
        excelConvert = findViewById(R.id.excelConvert);
        pdfConvert = findViewById(R.id.pdfConvert);
        share = findViewById(R.id.share);
        customers_list = findViewById(R.id.customers_list);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);


        backBtn = findViewById(R.id.backBtn);
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
                                popUpClass.showPopupWindow(item.getActionView(), UnCollectedData.this);

                                return true;

                            case R.id.action_location:

                                return true;

                            case R.id.action_notifications:

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(0, 0);

                                return true;
                        }
                        return false;
                    }
                });
//        fromDate.setText(toDay);
//        toDate.setText(toDay);
//        listSearch=findViewById(R.id.listSearch);
//
//        listSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(!s.toString().equals(""))
//                {
//                    searchInSpinerCustomer();
//                }
//
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


    }

//    private void searchInSpinerCustomer() {
//        String name="";
//       if (!listSearch.getText().toString().equals("")) {
//           name=listSearch.getText().toString();
//           for (int i=0;i<customername.size();i++)
//           {
//               if(customername.get(i).toUpperCase().contains(name)||customername.get(i).toLowerCase().contains(name))
//               {
//                   customerId= listCustomer.get(i).getCustomerNumber();
//                   customerSpinner.setSelection(i);
//               }
//
//           }
//       }
//    }


    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {

                case R.id.excelConvert:
                    convertToExcel();
                    break;
                case R.id.pdfConvert:
                    convertToPdf();
                    break;
                case R.id.share:
                    shareWhatsApp();
                    break;

            }

        }
    };


    private void fillCustomerSpenner() {
        Collections.sort(customername, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, customername);

        customers_list.setAdapter(adapter);
    }

    private void getPayment() {
        Payment data = new Payment();
        data.setBank("Arab Bank");
        data.setDueDate("13/02/2021");
        data.setCheckNumber(20210210);
        data.setAmount(2500);

        paymentArrayList.add(data);
        data.setBank("Arab Bank");
        data.setDueDate("13/02/2021");
        data.setCheckNumber(20210210);
        data.setAmount(25200);
        paymentArrayList.add(data);
        paymentArrayList.add(data);
        fillTable();

    }

    private void fillTable() {
        Log.e(" fillTable", "fillTable");
        TableRow row = null;
        tableCheckData.removeAllViews();
        //     tableCheckData.removeAllViewsInLayout();
        for (int n = 0; n < paymentArrayList.size(); n++) {
            row = new TableRow(this);
            row.setPadding(12, 5, 12, 5);

            if (n % 2 == 0)
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.layer3));
            else
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.layer7));

            for (int i = 0; i < 4; i++) {

                String[] record = {paymentArrayList.get(n).getBank(), (paymentArrayList.get(n).getCheckNumber() + ""),
                        paymentArrayList.get(n).getDueDate(), (paymentArrayList.get(n).getAmount() + "")};

                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);

                TextView textView = new TextView(this);
                textView.setText(record[i]);
                textView.setTextColor(ContextCompat.getColor(this, R.color.new_dark_blue));
                textView.setGravity(Gravity.CENTER);

                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(120, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                textView.setLayoutParams(lp2);

                row.addView(textView);
            }
            tableCheckData.addView(row);
        }
    }
}
