package com.example.adminvansales;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.Report.CashReport;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;

public class OfferPriceList extends AppCompatActivity {
    public static List<ItemMaster> ItemCardList;
    public  List<ItemMaster> tempList;
    ItemCardAdapter itemCardAdapter;
    ListView itemList;
    ImportData importData;
    TextView fromDate,toDate,listNo,selectItem,selectCustomer;
    GlobelFunction globelFunction;
    EditText listSearch,listName;
    LinearLayout qtyPriceLinear,listOfferLinear,qtyLinear;
Spinner listTypeSpinner;
ListAdapterSearchVoucher listAdapterSearchVoucher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_price_list_activity);
        initial();
        listTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goneVisibleLinear(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void ShowSearchDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog_show_);
        dialog.setCancelable(true);

        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
        final ListView ListNote = dialog.findViewById(R.id.ListNote);

        listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, ItemCardList, dialog, 1, OfferPriceList.this);
        ListNote.setAdapter(listAdapterSearchVoucher);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noteSearch.getText().toString().equals("")) {
                    List<ItemMaster> searchRec = new ArrayList<>();
                    searchRec.clear();
                    for (int i = 0; i < ItemCardList.size(); i++) {
                        if (ItemCardList.get(i).getName().contains(noteSearch.getText().toString())
                                ||ItemCardList.get(i).getItemNo().contains(noteSearch.getText().toString())
                                ||ItemCardList.get(i).getF_D().contains(noteSearch.getText().toString())) {
                            searchRec.add(ItemCardList.get(i));

                        }
                    }


                    listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, searchRec, dialog, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchVoucher);


                } else {
                    listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, ItemCardList, dialog, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchVoucher);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }



    void goneVisibleLinear(int cases){
        switch (cases){
            case 0:
                qtyLinear.setVisibility(View.GONE);
                listOfferLinear.setVisibility(View.GONE);
                qtyPriceLinear.setVisibility(View.VISIBLE);
                break;
            case 1:
                qtyLinear.setVisibility(View.VISIBLE);
                listOfferLinear.setVisibility(View.VISIBLE);
                qtyPriceLinear.setVisibility(View.VISIBLE);
                break;
            case 2:
                break;
        }


    }

    void initial(){
        ItemCardList=new ArrayList<>();
        tempList=new ArrayList<>();
        itemList=findViewById(R.id.itemListV);
        globelFunction =new GlobelFunction(OfferPriceList.this);
        toDate=findViewById(R.id.toDate);
        fromDate=findViewById(R.id.fromDate);
        listSearch=findViewById(R.id.listSearch);
        fromDate.setText(globelFunction.DateInToday());
        toDate.setText(globelFunction.DateInToday());
        importData=new ImportData(OfferPriceList.this);
        importData.getItemCard(OfferPriceList.this);
        qtyPriceLinear=findViewById(R.id.qtyPrice);
        listOfferLinear=findViewById(R.id.listOfferLinear);
        qtyLinear=findViewById(R.id.qtyLinear);
        listNo=findViewById(R.id.listNo);
        listName=findViewById(R.id.listName);
        listTypeSpinner=findViewById(R.id.listTypeSpinner);
        selectItem=findViewById(R.id.selectItem);
        selectCustomer=findViewById(R.id.selectCustomer);


        fromDate.setOnClickListener(onClickListener);
        toDate.setOnClickListener(onClickListener);
        selectItem.setOnClickListener(onClickListener);
        selectCustomer.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.fromDate:
                    globelFunction.DateClick(fromDate);
                    break;
                case R.id.toDate:
                    globelFunction.DateClick(toDate);
                    break;
                case R.id.selectItem:
                    ShowSearchDialog();
                    break;
                case R.id.selectCustomer:
                    break;

            }
        }
    };

    public void fillItemCard(){

//        int positionSales=salesNameSpinner.getSelectedItemPosition();
//
        String salesNo;
        tempList.clear();
//        if (positionSales == 0 || positionSales==-1) {
//            salesNo = "-1";
//            Log.e("salesNo-1", "" + salesNo +"  ");
//            TempReports=new ArrayList<>(cashReportList);
//            payMentReportAdapter = new CashReportAdapter(CashReport.this, cashReportList);
//            listCashReport.setAdapter(payMentReportAdapter);
//        } else {
//            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
//            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
//            for (int i=0;i<cashReportList.size();i++){
//                if(cashReportList.get(i).getSalesManNo().equals(salesNo)){
//                    TempReports.add(cashReportList.get(i));
//                    break;
//                }
//            }

            itemCardAdapter = new ItemCardAdapter(OfferPriceList.this, ItemCardList);

            itemList.setAdapter(itemCardAdapter);

        }


    }



