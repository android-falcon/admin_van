package com.example.adminvansales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listCustomerInfo;

import com.example.adminvansales.model.LocaleAppUtils;
public class ItemReport extends AppCompatActivity {
    String Today="";
    TextView fromDate, toDate;
    Spinner salesManSpinner;
    Button previewButton;
    ListView listItemReport;
    public static List<ItemReportModel> itemReportModelsList;
    public static List<ItemReportModel> Filterd_itemReportList=new ArrayList<>();
    public  List<ItemReportModel>Searched_itemReportList=new ArrayList<>();
    public static List<String> ItemsKindlist=new ArrayList<>();
    public static List<String> ItemsCatglist=new ArrayList<>();
    TextView total;
    ItemReportAdapter itemReportAdapter;
    ImportData importData;
    GlobelFunction globelFunction;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    static TextView total_item_qty;
    DataBaseHandler databaseHandler;
    com.example.adminvansales.model.SettingModel settingModel;
    public static Spinner  spinner_kind_item, spinner_catg_item;
    public static  ArrayAdapter arrayAdapter, arrayAdapter2;
  EditText ItemnameTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(ItemReport.this);
        setContentView(R.layout.item_report_activity);
        settingModel = new com.example.adminvansales.model.SettingModel();
        databaseHandler = new DataBaseHandler(ItemReport.this);

        initial();
        findViewById(R.id.excelConvert)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToExcel();
            }
        });

        findViewById(R.id.pdfConvert)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToPdf();
            }
        });
        importData = new ImportData(ItemReport.this);
        try {


            if (ItemsCatglist.size() == 0 || ItemsKindlist.size() == 0) {
                importData.fetchItemMaster(3);
            } else {
                arrayAdapter = new ArrayAdapter<String>(ItemReport.this, R.layout.spinner_layout, ItemsKindlist);

                arrayAdapter2 = new ArrayAdapter<String>(ItemReport.this, R.layout.spinner_layout, ItemsCatglist);

                spinner_kind_item.setAdapter(arrayAdapter);
                spinner_catg_item.setAdapter(arrayAdapter2);

            }
        }catch (Exception exception){

        }
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
        ItemnameTextView=findViewById(R.id.ItemnameTextView);
        ItemnameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Searched_itemReportList.clear();
                if (editable.length() == 0) {


                    if (Filterd_itemReportList.size() == 0) {
                    itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
                    listItemReport.setAdapter(itemReportAdapter);

                    }else {
                        itemReportAdapter = new ItemReportAdapter(ItemReport.this, Filterd_itemReportList);
                        listItemReport.setAdapter(itemReportAdapter);

                    }

                } else {

                    if (Filterd_itemReportList.size() == 0) {
                        for (int i = 0; i < itemReportModelsList.size(); i++)
                            if (itemReportModelsList.get(i).getName().trim().toLowerCase(Locale.ROOT).contains(ItemnameTextView.getText().toString().trim().toLowerCase(Locale.ROOT)))
                            {   Searched_itemReportList.add(itemReportModelsList.get(i));

                                itemReportAdapter = new ItemReportAdapter(ItemReport.this, Searched_itemReportList);
                                listItemReport.setAdapter(itemReportAdapter);
                            }


                    }else {

                        for (int i = 0; i < Filterd_itemReportList.size(); i++)
                            if (Filterd_itemReportList.get(i).getName().trim().contains(ItemnameTextView.getText().toString().trim())) {
                                Searched_itemReportList.add(Filterd_itemReportList.get(i));

                            }

                    }
                    itemReportAdapter = new ItemReportAdapter(ItemReport.this, Searched_itemReportList);
                    listItemReport.setAdapter(itemReportAdapter);
                }
            }
        });
        findViewById(R.id.clear_text) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemnameTextView.setText("");
            }
        });
        total=findViewById(R.id.total);
        total_item_qty = findViewById(R.id.total_itemqty_text);
        spinner_kind_item= findViewById(R.id.spinner_kind_item);
                spinner_catg_item= findViewById(R.id.catg);
        spinner_kind_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int x, long l) {
                Filterd_itemReportList.clear();
                if(x!=0) {
                    if (spinner_catg_item.getSelectedItem().equals(getResources().getString(R.string.ALL))) {


                            for (int j = 0; j < ImportData.listAllItemReportModels.size(); j++)
                                for (int i = 0; i < itemReportModelsList.size(); i++)
                                if (itemReportModelsList.get(i).getItemNo().equals(ImportData.listAllItemReportModels.get(j).getItemNo()))
                                    if (ImportData.listAllItemReportModels.get(j).getItemK().equals(spinner_kind_item.getSelectedItem()))
                                        Filterd_itemReportList.add(itemReportModelsList.get(i));
                        itemReportAdapter = new ItemReportAdapter(ItemReport.this, Filterd_itemReportList);
                        listItemReport.setAdapter(itemReportAdapter);
                        total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
                        total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));

                    }else
                    {

                        Filterd_itemReportList.clear();
                        for (int i = 0; i < itemReportModelsList.size(); i++)
                            for (int j = 0; j < ImportData.listAllItemReportModels.size(); j++)
                                if (itemReportModelsList.get(i).getItemNo().equals(ImportData.listAllItemReportModels.get(j).getItemNo()))
                                    if ( ImportData.listAllItemReportModels.get(j).getItemK().equals(spinner_kind_item.getSelectedItem())
                                            && ImportData.listAllItemReportModels.get(j).getItemG().equals(spinner_catg_item.getSelectedItem()))
                                        Filterd_itemReportList.add(itemReportModelsList.get(i));
                        itemReportAdapter = new ItemReportAdapter(ItemReport.this, Filterd_itemReportList);
                        listItemReport.setAdapter(itemReportAdapter);
                        total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
                        total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));


                    }
                }else {
                    itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
                    listItemReport.setAdapter(itemReportAdapter);
                    total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
                    total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_catg_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int x, long l) {
                spinner_kind_item.setSelection(0);
                Filterd_itemReportList.clear();
             if(x!=0) {
Log.e("spinner_catg_item",x+"");
                 Log.e("listAllItemReportModels==",ImportData.listAllItemReportModels.size()+"");
                 Log.e("itemReportModelsList==",itemReportModelsList.size()+"");
                 for (int j = 0; j < ImportData.listAllItemReportModels.size(); j++)
                     for (int i = 0; i < itemReportModelsList.size(); i++)

                             if (itemReportModelsList.get(i).getItemNo().equals(ImportData.listAllItemReportModels.get(j).getItemNo()))
                                 if (ImportData.listAllItemReportModels.get(j).getItemG().equals(spinner_catg_item.getSelectedItem()))
                                     Filterd_itemReportList.add(itemReportModelsList.get(i));
                     itemReportAdapter = new ItemReportAdapter(ItemReport.this, Filterd_itemReportList);
                     listItemReport.setAdapter(itemReportAdapter);
                 total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
                 total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(Filterd_itemReportList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));



             } else {
                 itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
                 listItemReport.setAdapter(itemReportAdapter);
                 total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
                 total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));


             }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        globelFunction = new GlobelFunction(ItemReport.this);
        itemReportModelsList = new ArrayList<>();
        fromDate = findViewById(R.id.from_date_r);
        toDate = findViewById(R.id.to_date_r);
        salesManSpinner = findViewById(R.id.salesManSpinner);
        previewButton = findViewById(R.id.previewButton);
        listItemReport = findViewById(R.id.listItemReport);
       Today= globelFunction.DateInToday();
        fromDate.setText(globelFunction.DateInToday());
        toDate.setText(globelFunction.DateInToday());
        importData = new ImportData(ItemReport.this);
        settingModel = databaseHandler.getAllSetting();
        if(salesManNameList.size()==0)    globelFunction. getSalesManInfo (ItemReport.this,9);

        fillSalesManSpinner();
        try {   String no="";
            if (salesManSpinner.getSelectedItem()!=null)
             no = globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());
else
            {
                no ="9999999999";
            }
            if (settingModel.getImport_way().equals("0"))
            //    importData.getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), "-1");
                importData.  fetchItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), "-1");
            else if (settingModel.getImport_way().equals("1"))
            //    importData.IIS_getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);

                importData.  fetchItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), "9999999999");
        } catch (Exception e) {
        }

        fromDate.setOnClickListener(onClickListener);
        toDate.setOnClickListener(onClickListener);

        previewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    spinner_catg_item.setSelection(0);
                    spinner_kind_item.setSelection(0);
                    Filterd_itemReportList.clear();



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
                    String no;
                    if(positionSales!=0)
                     no = globelFunction.getsalesmanNum(salesManSpinner.getSelectedItem().toString());
                    else no="9999999999";
                    if (settingModel.getImport_way().equals("0"))
                        importData.getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), salesNo);
                    else if (settingModel.getImport_way().equals("1"))
                        importData.IIS_getItemReport(ItemReport.this, fromDate.getText().toString(), toDate.getText().toString(), no);

                } else {

                    Toast.makeText(ItemReport.this, "No SalesMan Selected", Toast.LENGTH_SHORT).show();

                }

//                }
                }catch (Exception exception)
                {
Log.e("Exception==",exception.getMessage()+"");
                }

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
    public static void   fillItemFilterSpin(Context  context){
        ItemsKindlist.clear();
        ItemsCatglist.clear();
        for (int i=0; i<ImportData.listAllItemReportModels.size();i++)
        {
           if(!ItemsKindlist.contains(ImportData.listAllItemReportModels.get(i).getItemK())) ItemsKindlist.add(ImportData.listAllItemReportModels.get(i).getItemK());
            if(!ItemsCatglist.contains(ImportData.listAllItemReportModels.get(i).getItemG())&&
                   ! ImportData.listAllItemReportModels.get(i).getItemG().trim().equals("") )   ItemsCatglist.add(ImportData.listAllItemReportModels.get(i).getItemG());
        }
        ItemsKindlist.add(0,context.getResources().getString(R.string.ALL));
        ItemsCatglist.add(0,context.getResources().getString(R.string.ALL));
         arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, ItemsKindlist);

         arrayAdapter2 = new ArrayAdapter<String>(context, R.layout.spinner_layout, ItemsCatglist);

        spinner_kind_item.setAdapter(arrayAdapter);
        spinner_catg_item.setAdapter(arrayAdapter2);

    }
    public void fillItemAdapter() {


        itemReportAdapter = new ItemReportAdapter(ItemReport.this, itemReportModelsList);
        listItemReport.setAdapter(itemReportAdapter);

        total.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getTotal).mapToDouble(Double::parseDouble).sum()))));
        total_item_qty.setText(globelFunction.convertToEnglish(String.  format("%.3f",(itemReportModelsList.stream().map(ItemReportModel::getQty).mapToDouble(Double::parseDouble).sum()))));

    }


    public void fillSalesManSpinner() {
        List<String> salesManNameListWithAll=new ArrayList<>();

        salesManNameListWithAll.addAll(salesManNameList);
        salesManNameListWithAll.add(0,getResources().getString(R.string.ALL));


         salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameListWithAll);
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
    private File convertToPdf() {

        PdfConverter pdf = new PdfConverter(ItemReport.this);
        File file;
    if(Searched_itemReportList.size()!=0)
         file = pdf.exportListToPdf(Searched_itemReportList, "ItemsReport", Today, 8);
    else      if(Filterd_itemReportList.size()!=0)      file = pdf.exportListToPdf(Filterd_itemReportList, "ItemsReport", Today, 8);
       else
        file = pdf.exportListToPdf(itemReportModelsList, "ItemsReport", Today, 8);
        return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel = new ExportToExcel();
        if(Searched_itemReportList.size()!=0)
            exportToExcel.createExcelFile(ItemReport.this, "ItemsReport.xls", 8, Searched_itemReportList);

        else      if(Filterd_itemReportList.size()!=0)
            exportToExcel.createExcelFile(ItemReport.this, "ItemsReport.xls", 8, Filterd_itemReportList);

        else
            exportToExcel.createExcelFile(ItemReport.this, "ItemsReport.xls", 8, itemReportModelsList);
        // return file;
    }

}



