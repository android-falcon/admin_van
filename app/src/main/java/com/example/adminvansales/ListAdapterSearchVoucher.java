package com.example.adminvansales;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.Model.customerInfoModel;

import java.util.ArrayList;
import java.util.List;


public class ListAdapterSearchVoucher extends BaseAdapter {
    CheckBox checkPriceed;
    private OfferPriceList context;
    private Context contexts;
    TextView customerText;
    List<ItemMaster> itemsList;
    int flag;
    String phoneNo, language;
    Dialog dialog;

    public ListAdapterSearchVoucher(OfferPriceList context, List<ItemMaster> itemsList, Dialog dialog, int flag, Context contexts) {
        this.context = context;
        this.itemsList = itemsList;
        this.dialog = dialog;
        this.flag = flag;

        this.contexts = contexts;

        // this.customerText=customerText;
    }

    public ListAdapterSearchVoucher() {

    }

    public void setItemsList(List<ItemMaster> itemsList) {
        this.itemsList = itemsList;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView accNo, voucherNo, customerName;
        CheckBox itemCheckBox;

        Button customerButton;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_search_adaptir, null);

        holder.tableRow = view.findViewById(R.id.table);
        holder.accNo = view.findViewById(R.id.accNo);
        holder.voucherNo = view.findViewById(R.id.voNo);
        holder.customerName = view.findViewById(R.id.custName);
        holder.itemCheckBox = view.findViewById(R.id.itemCheckBox);
        holder.customerButton=view.findViewById(R.id.customerButton);


//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.accNo.setText(itemsList.get(i).getItemNo());
        holder.voucherNo.setText(itemsList.get(i).getF_D());
        holder.customerName.setText(itemsList.get(i).getName());
        holder.itemCheckBox.setChecked(itemsList.get(i).isCheckedItem());
        holder.customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.ShowSearchCustomerDialog(itemsList.get(i));

            }
        });

        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    itemsList.get(i).setCheckedItem(true);

                }else{
                    itemsList.get(i).setCheckedItem(false);
                }

            }
        });

        return view;
    }


//    public void ShowSearchCustomerDialog() {
//        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.search_dialog_show_);
//        dialog.setCancelable(true);
//
//        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
//        final ListView ListNote = dialog.findViewById(R.id.ListNote);
//        Button cancelButton,addButton;
//        cancelButton=dialog.findViewById(R.id.cancelButton);
//        addButton=dialog.findViewById(R.id.addButton);
//
//
//        listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, customerList, dialog, 1, OfferPriceList.this);
//        ListNote.setAdapter(listAdapterSearchCustomer);
//        Log.e("customerList",""+customerList.size());
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
//                    for (int i = 0; i < customerList.size(); i++) {
//                        if (customerList.get(i).getCustID().contains(noteSearch.getText().toString())
//                                ||customerList.get(i).getCustName().contains(noteSearch.getText().toString())) {
//                            Log.e("customerList",""+customerList.get(i).getCustName());
//                            searchRec.add(customerList.get(i));
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
//                    listAdapterSearchCustomer = new ListAdapterSearchCustomer(OfferPriceList.this, customerList, dialog, 1, OfferPriceList.this);
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
////                for(int i=0;i<ItemCardList.size();i++){
////                    if(){
////
////                    }
////                }
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//
//    }

}