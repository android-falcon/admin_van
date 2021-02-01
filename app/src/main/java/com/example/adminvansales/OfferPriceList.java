package com.example.adminvansales;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.Model.ListPriceOffer;
import com.example.adminvansales.Model.OfferListModel;
import com.example.adminvansales.Model.customerInfoModel;
import com.example.adminvansales.Report.CashReport;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.Report.ListOfferReport.selectCustomerIfClose;
import static com.example.adminvansales.Report.ListOfferReport.selectItemIfUpdate;

public class OfferPriceList extends AppCompatActivity {
    public static List<ItemMaster> ItemCardList;
    public static List<customerInfoModel> customerList;
    public static List<customerInfoModel> customerListFoundInOtherList;
    public static List<OfferListModel>mainListOffer;
    public  List<ItemMaster> tempList;
    static public List<OfferListModel> itemSelectList;
    public static List<customerInfoModel>customerSelectTemp;
    public static List<customerInfoModel>customerSelect;

    List<ItemMaster> listItemPrice;
    List<customerInfoModel>listCustomerD;
    ItemCardAdapter itemCardAdapter;
    CustomerSelectAdapter customerSelectAdapter;
    ListView itemList,itemPriceList,customerListView;
    ImportData importData;
    TextView fromDate,toDate,listNo,selectItem,selectCustomer,addList,cancelList;
    GlobelFunction globelFunction;
    public  static  EditText listSearch,listName,otherDiscount,cashDiscount,price;
    LinearLayout qtyPriceLinear,listOfferLinear,qtyLinear,priceOfferLinear,priceOnlyLinear,dateLinear,listDateType;
    Spinner listTypeSpinner;
    ListAdapterSearchVoucher listAdapterSearchVoucher;
    ListAdapterSearchCustomer listAdapterSearchCustomer;
    ListAdapterPriceOnly listAdapterPrice;
    List<String>custIdList;
    ListView listViewCustomer;
    public  static  EditText priceOnly ;
    RadioButton openRadio,closeRadio;
    RadioGroup dateClose;
    CheckBox itemCheckBox;
    int listTypes=0;
    JSONArray array;
    String intentReSend ="";
    ListPriceOffer listSelectForClose;
    String listNoClos="",listNoS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_price_list_activity);
        intentReSend = getIntent().getStringExtra("ListIntent");
        initial();

//        intentReSend = getIntent().getStringExtra("ListIntent");
//       //intentReSend = getIntent().getStringExtra("UpdateList");
//        Log.e("listType",""+intentReSend.toString());

              if (intentReSend != null &&intentReSend.equals("CloseList")) {
                       listSelectForClose=(ListPriceOffer) getIntent().getSerializableExtra("listClose");
                       Log.e("ListNoClose=",""+listSelectForClose.getPO_LIST_NO());
                  listNoClos=listSelectForClose.getPO_LIST_NO();
                       customerSelect=new ArrayList<>(selectCustomerIfClose);
                       listTypeSpinner.setSelection(Integer.parseInt(listSelectForClose.getPO_LIST_TYPE()));
                  listTypeSpinner.setEnabled(false);
                  }else  if (intentReSend != null &&intentReSend.equals("UpdateList")) {
                  listSelectForClose=(ListPriceOffer) getIntent().getSerializableExtra("listUpdate");
                  Log.e("ListNoUpdate =",""+listSelectForClose.getPO_LIST_NO());

                  listNoClos=listSelectForClose.getPO_LIST_NO();
                  Log.e("ListType",""+listSelectForClose.getPO_LIST_NO());

                  if(!listSelectForClose.getPO_LIST_TYPE().equals("1")) {
                      customerSelect = new ArrayList<>(selectCustomerIfClose);
                      itemSelectList = new ArrayList<>(selectItemIfUpdate);
                  }
                  listTypeSpinner.setSelection(Integer.parseInt(listSelectForClose.getPO_LIST_TYPE()));
                  listTypeSpinner.setEnabled(false);
              }
        listTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //alter
                listTypes=position;
                goneVisibleLinear(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int position =listTypeSpinner.getSelectedItemPosition();

                if(position!=1){
                  //  searchInMainOfferListPrice();
                }else{
                    searchInPriceOnly();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateClose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(closeRadio.isChecked()){
                    toDate.setText(globelFunction.DateInToday());
                    toDate.setEnabled(true);
                }else if(openRadio.isChecked()){
                    toDate.setText("--");
                    toDate.setEnabled(false);
                }


            }
        });

        itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){



                }else{



                }

            }
        });


    }

    public void notifyCustomerSelectAdapter(){

        customerSelectAdapter.notifyDataSetChanged();
    }

    void searchInMainOfferListPrice(){
        if (!listSearch.getText().toString().equals("")) {
            List<OfferListModel> searchRec = new ArrayList<>();
            searchRec.clear();
            for (int i = 0; i < mainListOffer.size(); i++) {
                if (mainListOffer.get(i).getItemNo().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||mainListOffer.get(i).getItemName().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||mainListOffer.get(i).getCustomerName().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||mainListOffer.get(i).getCustomerNo().toUpperCase().contains(listSearch.getText().toString().toUpperCase())) {
                    searchRec.add(mainListOffer.get(i));

                }
            }


            itemCardAdapter = new ItemCardAdapter(OfferPriceList.this, searchRec,listTypes);

            itemList.setAdapter(itemCardAdapter);


        } else {
            itemCardAdapter = new ItemCardAdapter(OfferPriceList.this, mainListOffer,listTypes);

            itemList.setAdapter(itemCardAdapter);


        }
    }
    void searchInPriceOnly(){
        if (!listSearch.getText().toString().equals("")) {
            List<ItemMaster> searchRec = new ArrayList<>();
            searchRec.clear();
            for (int i = 0; i < listItemPrice.size(); i++) {
                if (listItemPrice.get(i).getItemNo().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||listItemPrice.get(i).getName().toUpperCase().contains(listSearch.getText().toString().toUpperCase())
                        ||listItemPrice.get(i).getF_D().toUpperCase().contains(listSearch.getText().toString().toUpperCase())) {
                    searchRec.add(listItemPrice.get(i));
                }
            }
            listAdapterPrice = new ListAdapterPriceOnly(OfferPriceList.this, searchRec,0);
            itemPriceList.setAdapter(listAdapterPrice);
        } else {
            listAdapterPrice = new ListAdapterPriceOnly(OfferPriceList.this, listItemPrice,0);
            itemPriceList.setAdapter(listAdapterPrice);
        }
    }
    void fillCustomerListId(){
        for(int i=0;i<customerList.size();i++){
            String cusId=customerList.get(i).getCustID();
            custIdList.add(cusId);
        }
    }
    void fillItemListPrice(){

        for(int i=0;i<ItemCardList.size();i++){
            String f_d=ItemCardList.get(i).getF_D();
            ItemCardList.get(i).setPrice(f_d);
        }
        if (intentReSend != null && intentReSend.equals("UpdateList")) {

            fillItemPriceInList(0);
        }
    }


    void selectItemFoundInOtherList(){
        for(int i=0;i<customerListFoundInOtherList.size();i++){
            for(int k=0;k<customerSelect.size();k++) {
                if (customerListFoundInOtherList.get(i).getCustID().equals(customerSelect.get(k).getCustID())) {

                    customerSelect.get(k).setCheckedItem(true);
                    break;
                }
            }
        }
        customerSelectAdapter.notifyDataSetChanged();
    }

    public void ShowSearchDialog() {
        final Dialog dialogSearch = new Dialog(this, R.style.Theme_Dialog);
        dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSearch.setContentView(R.layout.search_dialog_show_);
        dialogSearch.setCancelable(true);
        final EditText noteSearch = dialogSearch.findViewById(R.id.noteSearch);
        final ListView ListNote = dialogSearch.findViewById(R.id.ListNote);
        Button cancelButton,addButton;
        cancelButton=dialogSearch.findViewById(R.id.cancelButton);
        addButton=dialogSearch.findViewById(R.id.addButton);
        CheckBox itemCheckAll=dialogSearch.findViewById(R.id.itemCheckAll);

        listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, ItemCardList, dialogSearch, 1, OfferPriceList.this);
        ListNote.setAdapter(listAdapterSearchVoucher);


        itemCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    itemSelectList.clear();
                    List<OfferListModel>masterItemCard=new ArrayList<>();
                    for (int i=0;i<ItemCardList.size();i++){
                        masterItemCard.add(returnOfferListModel(ItemCardList.get(i)));
                    }
                    itemSelectList=new ArrayList<>(masterItemCard);
                }else {
                    itemSelectList.clear();
                }
            }
        });

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
                        if (ItemCardList.get(i).getName().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())
                                ||ItemCardList.get(i).getItemNo().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())
                                ||ItemCardList.get(i).getF_D().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())) {
                            searchRec.add(ItemCardList.get(i));

                        }
                    }


                    listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, searchRec, dialogSearch, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchVoucher);


                } else {
                    listAdapterSearchVoucher = new ListAdapterSearchVoucher(OfferPriceList.this, ItemCardList, dialogSearch, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchVoucher);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearch.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mainListOfferALLsi",""+mainListOffer.size());
//                for(int i=0;i<mainListOffer.size();i++){
//
//                    Log.e("mainListOfferALL",""+mainListOffer.size()+"   \n"+mainListOffer.get(i).getCustomerName()+"   "+
//                            mainListOffer.get(i).getItemName()+"    "+mainListOffer.get(i).getListNo());
//
//                }

                fillItemSelect();
                dialogSearch.dismiss();

            }
        });

        dialogSearch.show();

    }

//    public void ShowSearchCustomerDialog(final ItemMaster itemMaster) {
//        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.search_dialog_show_customer);
//        dialog.setCancelable(true);
//
//        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
//        final ListView ListNote = dialog.findViewById(R.id.ListNote);
//        Button cancelButton,addButton;
//        cancelButton=dialog.findViewById(R.id.cancelButton);
//        addButton=dialog.findViewById(R.id.addButton);
//        listCustomerD.clear();
//        listCustomerD=new ArrayList<>(customerList);
//        listViewCustomer=ListNote;
//        List<String> custListItemRemove = getAllCustomerByItem(itemMaster.getItemNo());
//        for (int m = 0; m < custListItemRemove.size(); m++) {
//            int pos =custIdList.indexOf(custListItemRemove.get(m));
//            Log.e("poAaaaaa", "" + pos);
//            if(pos!=-1){
//                Log.e("poAaaaaa", "" + listCustomerD.get(pos).getCustName()+"     "+listCustomerD.get(pos).getCustID());
//                listCustomerD.remove(pos);
//
//            }
//
//        }
//
//
//
//        listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, listCustomerD, dialog, 1, OfferPriceList.this);
//        ListNote.setAdapter(listAdapterSearchCustomer);
//        Log.e("customerList",""+listCustomerD.size());
//
//
//        noteSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (!noteSearch.getText().toString().equals("")) {
//                    List<customerInfoModel> searchRec = new ArrayList<>();
//                    searchRec.clear();
//                    for (int i = 0; i < listCustomerD.size(); i++) {
//                        if (listCustomerD.get(i).getCustID().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())
//                                ||listCustomerD.get(i).getCustName().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())) {
//                            Log.e("customerList",""+listCustomerD.get(i).getCustName()+"     "+i);
//                            searchRec.add(listCustomerD.get(i));
//
//                        }
//                    }
//
//
//                    listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, searchRec, dialog, 1, OfferPriceList.this);
//                    ListNote.setAdapter(listAdapterSearchCustomer);
//
//
//                } else {
//                    listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, listCustomerD, dialog, 1, OfferPriceList.this);
//                    ListNote.setAdapter(listAdapterSearchCustomer);
//
//
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.e("mainListOffer","size customerSelectTemp = "+customerSelectTemp.size());
//                for(int i=0;i<customerSelectTemp.size();i++){
//
//                    OfferListModel offerListModel=new OfferListModel();
//                    offerListModel.setListNo(listNo.getText().toString());
//
//                    offerListModel.setListName(listName.getText().toString());
//                    try {
//                        String position= String.valueOf(listTypeSpinner.getSelectedItemPosition());
//                        offerListModel.setListType(position);
//                    }catch (Exception e){
//                        offerListModel.setListType("0");
//                    }
//                    offerListModel.setFromDate(fromDate.getText().toString());
//                    offerListModel.setToDate(toDate.getText().toString());
//                    offerListModel.setPrice(price.getText().toString());
//                    offerListModel.setCashOffer(cashDiscount.getText().toString());
//                    offerListModel.setOtherOffer(otherDiscount.getText().toString());
//                    offerListModel.setItemNo(itemMaster.getItemNo());
//                    offerListModel.setItemName(itemMaster.getName());
//                    offerListModel.setCustomerNo(customerSelectTemp.get(i).getCustID());
//                    offerListModel.setCustomerName(customerSelectTemp.get(i).getCustName());
//                    offerListModel.setIsSelectCustomer(1);
//
//
//
//                    mainListOffer.add(offerListModel);
//                    Log.e("mainListOffer","size = "+mainListOffer.size()+"       "+itemMaster.getName()+"-->"+customerSelectTemp.get(i).getCustName());
//
//                }
//
//                Toast.makeText(OfferPriceList.this, "Add Success", Toast.LENGTH_SHORT).show();
//
//                customerSelectTemp.clear();
//                Log.e("mainListOffer","size customerSelectTemp2 = "+customerSelectTemp.size());
//
//
//                dialog.dismiss();
//
//            }
//        });
//
//
//        dialog.show();
//
//    }

    public void ShowSearchCustomerDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog_show_customer);
        dialog.setCancelable(true);

        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
        final ListView ListNote = dialog.findViewById(R.id.ListNote);
        Button cancelButton,addButton;
        CheckBox customerCheckAll=dialog.findViewById(R.id.customerCheckAll);
        cancelButton=dialog.findViewById(R.id.cancelButton);
        addButton=dialog.findViewById(R.id.addButton);
        listCustomerD.clear();
        listCustomerD=new ArrayList<>(customerList);
        listViewCustomer=ListNote;

        for (int m = 0; m < customerSelect.size(); m++) {
            int pos =custIdList.indexOf(customerSelect.get(m).getCustID());
            Log.e("poAaaaaa", "" + pos);
            if(pos!=-1){
            try {
                Log.e("poAaaaaa", "" + listCustomerD.get(pos).getCustName() + "     " + listCustomerD.get(pos).getCustID());
                listCustomerD.remove(pos);
            }catch (Exception e){
                Log.e("poAaaaaaError", "" + pos);

            }

            }

        }


        customerCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    customerSelect.clear();
                    customerSelect=new ArrayList<>(customerList);
                }else {
                customerSelect.clear();
                }
            }
        });

        listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, listCustomerD, dialog, 1, OfferPriceList.this);
        ListNote.setAdapter(listAdapterSearchCustomer);
        Log.e("customerList",""+listCustomerD.size());


        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noteSearch.getText().toString().equals("")) {
                    List<customerInfoModel> searchRec = new ArrayList<>();
                    searchRec.clear();
                    for (int i = 0; i < listCustomerD.size(); i++) {
                        if (listCustomerD.get(i).getCustID().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())
                                ||listCustomerD.get(i).getCustName().toUpperCase().contains(noteSearch.getText().toString().toUpperCase())) {
                            Log.e("customerList",""+listCustomerD.get(i).getCustName()+"     "+i);
                            searchRec.add(listCustomerD.get(i));

                        }
                    }


                    listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, searchRec, dialog, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchCustomer);


                } else {
                    listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, listCustomerD, dialog, 1, OfferPriceList.this);
                    ListNote.setAdapter(listAdapterSearchCustomer);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.e("mainListOffer","size customerSelectTemp = "+customerSelectTemp.size());
//                for(int i=0;i<customerSelectTemp.size();i++){
//
//                    OfferListModel offerListModel=new OfferListModel();
//                    offerListModel.setListNo(listNo.getText().toString());
//
//                    offerListModel.setListName(listName.getText().toString());
//                    try {
//                       String position= String.valueOf(listTypeSpinner.getSelectedItemPosition());
//                        offerListModel.setListType(position);
//                    }catch (Exception e){
//                        offerListModel.setListType("0");
//                    }
//                    offerListModel.setFromDate(fromDate.getText().toString());
//                    offerListModel.setToDate(toDate.getText().toString());
//                    offerListModel.setPrice(price.getText().toString());
//                    offerListModel.setCashOffer(cashDiscount.getText().toString());
//                    offerListModel.setOtherOffer(otherDiscount.getText().toString());
//                    offerListModel.setItemNo(itemMaster.getItemNo());
//                    offerListModel.setItemName(itemMaster.getName());
//                    offerListModel.setCustomerNo(customerSelectTemp.get(i).getCustID());
//                    offerListModel.setCustomerName(customerSelectTemp.get(i).getCustName());
//                    offerListModel.setIsSelectCustomer(1);
//
//
//
//                    mainListOffer.add(offerListModel);
//                    Log.e("mainListOffer","size = "+mainListOffer.size()+"       "+itemMaster.getName()+"-->"+customerSelectTemp.get(i).getCustName());
//
//                }
//
//                Toast.makeText(OfferPriceList.this, "Add Success", Toast.LENGTH_SHORT).show();
//
//            customerSelectTemp.clear();
//                Log.e("mainListOffer","size customerSelectTemp2 = "+customerSelectTemp.size());
//

                fillCustomerSelect();

                dialog.dismiss();

            }
        });


        dialog.show();

    }

    List<String> getAllCustomerByItem(String ItemNo){
        List<String>item=new ArrayList<>();

        for(int i=0;i<mainListOffer.size();i++){

            if(mainListOffer.get(i).getItemNo().equals(ItemNo)){
                String custId=mainListOffer.get(i).getCustomerNo();
                item.add(custId);
                Log.e("poAaaaaallll",""+custId);
            }
        }

        return item;
}


    void goneVisibleLinear(int cases){
        Log.e("lisss",""+cases);
        switch (cases){

            case 0:
                priceOfferLinear.setVisibility(View.VISIBLE);
                priceOnlyLinear.setVisibility(View.GONE);
                price.setText("0.0");
                otherDiscount.setText("0.0");
                cashDiscount.setText("0.0");
                qtyLinear.setVisibility(View.VISIBLE);
                listOfferLinear.setVisibility(View.VISIBLE);
                qtyPriceLinear.setVisibility(View.VISIBLE);
                dateLinear.setVisibility(View.VISIBLE);
                listDateType.setVisibility(View.VISIBLE);
                mainListOffer.clear();


                if (intentReSend != null && intentReSend.equals("CloseList")) {
                    listName.setText("");
                    itemSelectList.clear();
                }else  if (intentReSend != null && intentReSend.equals("UpdateList")) {
                    listName.setText(listSelectForClose.getPO_LIST_NAME());
                    listNo.setText(listSelectForClose.getPO_LIST_NO());
                    fromDate.setText(listSelectForClose.getFROM_DATE());
                    toDate.setText(listSelectForClose.getTO_DATE());
                }else{
                    customerSelect.clear();
                    itemSelectList.clear();
                    listName.setText("");
                }

                fillCustomerSelect();
                fillItemSelect();
                fillItemPriceInList(0);


                break;
            case 1:
                if (intentReSend != null && intentReSend.equals("UpdateList")) {

                    listNo.setText(listNoClos);
                }else {
                    fillItemPriceInList(0);
                }
                priceOfferLinear.setVisibility(View.GONE);
                priceOnlyLinear.setVisibility(View.VISIBLE);
                dateLinear.setVisibility(View.GONE);
                listDateType.setVisibility(View.GONE);
                mainListOffer.clear();
                customerSelect.clear();
                itemSelectList.clear();
                fillCustomerSelect();
                fillItemSelect();
                listName.setText("End User Price List 0");

                break;
            case 2:
                priceOfferLinear.setVisibility(View.VISIBLE);
                priceOnlyLinear.setVisibility(View.GONE);
                price.setText("0.0");
                otherDiscount.setText("0.0");
                cashDiscount.setText("0.0");
                qtyLinear.setVisibility(View.VISIBLE);
                listOfferLinear.setVisibility(View.VISIBLE);
                qtyPriceLinear.setVisibility(View.GONE);
                dateLinear.setVisibility(View.VISIBLE);
                listDateType.setVisibility(View.VISIBLE);
                mainListOffer.clear();
                if (intentReSend != null && intentReSend.equals("CloseList")) {
                    listName.setText("");
                    itemSelectList.clear();
                }else  if (intentReSend != null && intentReSend.equals("UpdateList")) {
                    listName.setText(listSelectForClose.getPO_LIST_NAME());
                    listNo.setText(listSelectForClose.getPO_LIST_NO());
                    fromDate.setText(listSelectForClose.getFROM_DATE());
                    toDate.setText(listSelectForClose.getTO_DATE());
                    importData.getItemCard(OfferPriceList.this);
                }else {
                    customerSelect.clear();
                    itemSelectList.clear();
                    listName.setText("");
                }

                fillCustomerSelect();
                fillItemSelect();
               fillItemPriceInList(0);


                break;
        }


    }

    public void clearList(){

        priceOnly.setText("0.0");

        if(listTypes!=1) {
            listName.setText("");
        }else {
            listName.setText("End User Price List 0");
            importData.getItemCard(OfferPriceList.this);
        }
        otherDiscount.setText("0.0");
        cashDiscount.setText("0.0");
        price.setText("0.0");
        fillItemPriceInList(0);
        mainListOffer.clear();
        customerSelect.clear();
        itemSelectList.clear();
        fillItemSelect();
        fillCustomerSelect();
        if (intentReSend != null && (intentReSend.equals("CloseList") ||  intentReSend.equals("UpdateList"))) {
            Log.e("intent22","9009");
            importData.getItemUpdateClosOpenList(OfferPriceList.this,listNoClos,listNoS);


        }else{
//noThing
            Log.e("intent22","9009kkk");
            importData.getMaxNo(OfferPriceList.this);

//            if(listTypes!=1) {
//            }else {
//                importData.getItemCard(OfferPriceList.this);
//            }
        }


    }

    void initial(){
        custIdList=new ArrayList<>();
        customerSelectTemp=new ArrayList<>();
        customerSelect=new ArrayList<>();
        mainListOffer=new ArrayList<>();
        customerList=new ArrayList<>();
        customerListFoundInOtherList=new ArrayList<>();
        ItemCardList=new ArrayList<>();
        tempList=new ArrayList<>();
        itemSelectList=new ArrayList<>();
        listCustomerD=new ArrayList<>(customerList);
        listItemPrice=new ArrayList<>(ItemCardList);
        itemList=findViewById(R.id.itemListV);
        customerListView=findViewById(R.id.customerListView);
        globelFunction =new GlobelFunction(OfferPriceList.this);
        toDate=findViewById(R.id.toDate);
        fromDate=findViewById(R.id.fromDate);
        listSearch=findViewById(R.id.listSearch);
        fromDate.setText(globelFunction.DateInToday());
        toDate.setText(globelFunction.DateInToday());
        importData=new ImportData(OfferPriceList.this);
        importData.getItemCard(OfferPriceList.this);
        importData.getCustomer(OfferPriceList.this);
     if(intentReSend != null && intentReSend.equals("UpdateList")) {
         Log.e("listType","uuuuuu");

     }else {
         Log.e("listType","llll");
         importData.getMaxNo(OfferPriceList.this);
     }
        qtyPriceLinear=findViewById(R.id.qtyPrice);
        listOfferLinear=findViewById(R.id.listOfferLinear);
        qtyLinear=findViewById(R.id.qtyLinear);
        listNo=findViewById(R.id.listNo);
        listName=findViewById(R.id.listName);
        listTypeSpinner=findViewById(R.id.listTypeSpinner);
        selectItem=findViewById(R.id.selectItem);
        selectCustomer=findViewById(R.id.selectCustomer);
        addList=findViewById(R.id.addList);
        cancelList=findViewById(R.id.cancelList);
        otherDiscount=findViewById(R.id.otherDiscount);
        cashDiscount=findViewById(R.id.cashDiscount);
        price=findViewById(R.id.price);
        priceOfferLinear=findViewById(R.id.priceOfferLinear);
        priceOnlyLinear=findViewById(R.id.priceOnlyLinear);
        itemPriceList=findViewById(R.id.itemListVPriceOnly);
        priceOnly=findViewById(R.id.priceOnlyEdit);
        openRadio=findViewById(R.id.openRadio);
        closeRadio=findViewById(R.id.closeRadio);
        dateClose=findViewById(R.id.dateClose);
        itemCheckBox=findViewById(R.id.itemCheckBox);
        dateLinear=findViewById(R.id.dateLinear);
        listDateType=findViewById(R.id.listDateType);

        listTypeSpinner.setEnabled(true);

        fromDate.setOnClickListener(onClickListener);
        toDate.setOnClickListener(onClickListener);
        selectItem.setOnClickListener(onClickListener);
        selectCustomer.setOnClickListener(onClickListener);
        addList.setOnClickListener(onClickListener);
        cancelList.setOnClickListener(onClickListener);

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
                    dialogShow();

                    break;
                case R.id.selectCustomer:
                    ShowSearchCustomerDialog();
                    break;
                case R.id.addList:
                    save();
                    break;
                case R.id.cancelList:
                    finish();
                    break;
            }
        }
    };
    public void dialogShow(){

        int position =listTypeSpinner.getSelectedItemPosition();
        Log.e("positionSave",""+position);
        switch (position){
            case 0:
                if(!price.getText().toString().equals("")) {
//                    if (Double.parseDouble(price.getText().toString()) != 0) {

                        ShowSearchDialog();

//                    }else {
//                        price.setError(" not Zero !");
//                    }
                }else {
                    price.setError("Required!");
                }
                break;
            case 1:



                break;
            case 2:

                if (!otherDiscount.getText().toString().equals("")) {
                    if (!cashDiscount.getText().toString().equals("")) {
                        if ((Double.parseDouble(otherDiscount.getText().toString()) != 0)|| Double.parseDouble(cashDiscount.getText().toString()) != 0) {

                                ShowSearchDialog();

                        } else {
                            otherDiscount.setError("not Zero !");
                            cashDiscount.setError("not Zero !");
                        }

                    } else {
                        cashDiscount.setError("Required!");
                    }
                } else {
                    otherDiscount.setError("Required!");
                }

                break;
            case 3:
                break;
        }
    }

    public void save(){

        int position =listTypeSpinner.getSelectedItemPosition();
        Log.e("positionSave",""+position);
        String toDates="";


        addToMainList();
        listNoS=listNo.getText().toString();
if(position!=1) {
    if (openRadio.isChecked()) {

        toDates = "01/01/9999";
    } else {

        toDates = toDate.getText().toString();
    }
}else {
    toDates = "01/01/9999";
}


        if(position!=1) {
            if (!listName.getText().toString().equals("")) {
                Log.e("positionSaveOffer",""+mainListOffer.size());
                if (itemSelectList.size() != 0) {
                    if (customerSelect.size() != 0) {
                        if (mainListOffer.size() != 0) {
                            if (intentReSend != null && (intentReSend.equals("CloseList") || intentReSend.equals("UpdateList") )) {
                                Log.e("intent22","99");
                                importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDates, "" + position, "2", listNoClos, array);

                            }else{
                                Log.e("intent22","990");
                                importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDates, "" + position, "0", listNo.getText().toString(), array);

                            }
                        } else {
                            Toast.makeText(this, "No Data In List", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please Add Customer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Please Add Item", Toast.LENGTH_SHORT).show();
                }
            } else {
                listName.setError("Required!");
            }

        }else  {
            if (!listName.getText().toString().equals("")) {
                if (listItemPrice.size() != 0) {

                    if (intentReSend != null && (intentReSend.equals("CloseList") || intentReSend.equals("UpdateList") )) {
                        Log.e("intent22","99");
                        importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDates,""+position,"2",listNo.getText().toString(),array);

                    }else{
                        Log.e("intent22","990");
                        importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDates,""+position,"0",listNo.getText().toString(),array);

                    }



                } else {
                    Toast.makeText(this, "No Data In List", Toast.LENGTH_SHORT).show();
                }
            } else {
                listName.setError("Required!");
            }

        }

    }


    private void addToMainList() {
        mainListOffer.clear();
        array=new JSONArray();
        for(int i=0;i<itemSelectList.size();i++){
            for (int k=0;k<customerSelect.size();k++) {
                OfferListModel offerListModel = new OfferListModel();

                offerListModel.setListNo(listNo.getText().toString());

                offerListModel.setListName(listName.getText().toString());
                try {
                    String position = String.valueOf(listTypeSpinner.getSelectedItemPosition());
                    offerListModel.setListType(position);
                } catch (Exception e) {
                    offerListModel.setListType("0");
                }
                offerListModel.setFromDate(fromDate.getText().toString());
                offerListModel.setToDate(toDate.getText().toString());
                offerListModel.setPrice(itemSelectList.get(i).getPrice());
                offerListModel.setCashOffer(itemSelectList.get(i).getCashOffer());
                offerListModel.setOtherOffer(itemSelectList.get(i).getOtherOffer());
                offerListModel.setItemNo(itemSelectList.get(i).getItemNo());
                offerListModel.setItemName(itemSelectList.get(i).getItemName());
                offerListModel.setCustomerNo(customerSelect.get(k).getCustID());
                offerListModel.setCustomerName(customerSelect.get(k).getCustName());
                //offerListModel.setIsSelectCustomer(1);
                mainListOffer.add(offerListModel);
                if(i==0) {

                    customerInfoModel customerInfoModel=new customerInfoModel();

                    customerInfoModel=customerSelect.get(k);
                    array.put(customerInfoModel.getJsonObjectList());

                }
            }

        }


        for(int l=0;l<mainListOffer.size();l++){
//
                    Log.e("mainListOfferALL",""+mainListOffer.size()+"   \n"+mainListOffer.get(l).getCustomerName()+"\t"+
                            mainListOffer.get(l).getItemName()+"\t"+mainListOffer.get(l).getListNo()+"\t"+ mainListOffer.get(l).getPrice()+
                            "\t"+mainListOffer.get(l).getCashOffer()+"\t"+mainListOffer.get(l).getOtherOffer());

                }

        Log.e("mainListSize","ccc = "+mainListOffer.size());

    }


    public void saveInDB(){
        int position =listTypeSpinner.getSelectedItemPosition();
        Log.e("positionSaveIn",""+position);
        switch (position) {
            case 0:
                savePriceOfferList(position);
                break;
            case 1:
                savePriceList(position);
                break;
            case 2:
                savePriceOfferList(position);
                break;
            case 3:
                break;
        }
    }

    public void savePriceOfferList(int position){
        JSONArray jsonArrayList = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        for(int i=0;i<mainListOffer.size();i++){
            OfferListModel offerListModel=new OfferListModel();
            offerListModel=mainListOffer.get(i);
            jsonArrayList.put(offerListModel.getJsonObject());
        }


        OfferListModel oofer=new OfferListModel();
        oofer.setListNo(listNo.getText().toString());
        oofer.setListName(listName.getText().toString());
        oofer.setListType(""+position);
        oofer.setFromDate(fromDate.getText().toString());
        if(closeRadio.isChecked()){
            oofer.setToDate(toDate.getText().toString());
        }else if(openRadio.isChecked()){
            oofer.setToDate("01/01/9999");
        }

        jsonObject=oofer.getJsonObjectList();

        ExportData exportData=new ExportData(OfferPriceList.this);

        if(intentReSend != null && intentReSend.equals("UpdateList")) {
            exportData.updateList(OfferPriceList.this, jsonObject,jsonArrayList);
        }else{
            exportData.addToList(OfferPriceList.this, jsonArrayList, jsonObject);

        }

    }
    public void savePriceList(int position){
        JSONArray jsonArrayList = new JSONArray();
        JSONObject jsonObject = new JSONObject();
Log.e("mmmma","size : "+listItemPrice.size());
        for(int i=0;i<listItemPrice.size();i++){
            OfferListModel offerListModel=new OfferListModel();
            offerListModel.setPrice(listItemPrice.get(i).getPrice());
            offerListModel.setItemName(listItemPrice.get(i).getName());
            offerListModel.setItemNo(listItemPrice.get(i).getItemNo());

            jsonArrayList.put(offerListModel.getJsonObject());
        }

        OfferListModel oofer=new OfferListModel();
        oofer.setListNo(listNo.getText().toString());
//        oofer.setListName(listName.getText().toString());
        oofer.setListName("End User Price List 0");
        oofer.setListType(""+position);
        oofer.setFromDate(fromDate.getText().toString());
//        if(closeRadio.isChecked()){
//            oofer.setToDate(toDate.getText().toString());
//        }else if(openRadio.isChecked()){
            oofer.setToDate("01/01/9999");
//        }
        jsonObject=oofer.getJsonObjectList();

        ExportData exportData=new ExportData(OfferPriceList.this);


        if(intentReSend != null && intentReSend.equals("UpdateList")) {
            exportData.updateList(OfferPriceList.this, jsonObject,jsonArrayList);
        }else{
            exportData.addToList(OfferPriceList.this,jsonArrayList,jsonObject);

        }



    }

    public void finishLayout(){
        finish();
    }

    public void fillSerial(String listNos){
        listNo.setText(listNos);
    }
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

            itemCardAdapter = new ItemCardAdapter(OfferPriceList.this, mainListOffer,listTypes);

            itemList.setAdapter(itemCardAdapter);

        }

    public void fillItemPriceInList(int clear){
        listItemPrice.clear();
        listItemPrice=new ArrayList<>(ItemCardList);
        listAdapterPrice = new ListAdapterPriceOnly(OfferPriceList.this, listItemPrice,clear);
        itemPriceList.setAdapter(listAdapterPrice);

        Log.e("masterrrrr",""+ItemCardList.size()+"  "+listItemPrice.size());

    }

    public void fillItemSelect(){

        itemCardAdapter = new ItemCardAdapter(OfferPriceList.this, itemSelectList,listTypes);

        itemList.setAdapter(itemCardAdapter);

    }
    public void fillCustomerSelect(){

        customerSelectAdapter = new CustomerSelectAdapter(OfferPriceList.this, customerSelect);

        customerListView.setAdapter(customerSelectAdapter);

    }

    public OfferListModel returnOfferListModel(ItemMaster itemMaster){
        OfferListModel offerListModel=new OfferListModel();
        offerListModel.setItemNo(itemMaster.getItemNo());
        offerListModel.setItemName(itemMaster.getName());
        if(Double.parseDouble(price.getText().toString())!=0) {
            offerListModel.setPrice(price.getText().toString());
        }else {
            offerListModel.setPrice(itemMaster.getF_D());

        }
        offerListModel.setCashOffer(cashDiscount.getText().toString());
        offerListModel.setOtherOffer(otherDiscount.getText().toString());
        offerListModel.setItemNo(itemMaster.getItemNo());
        offerListModel.setItemName(itemMaster.getName());

        return  offerListModel;
    }

}



