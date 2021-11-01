package com.example.adminvansales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.model.OfferListModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ItemCardAdapter extends BaseAdapter {
    private OfferPriceList context;
    List<OfferListModel> itemsList;

    int flag;

    public ItemCardAdapter(OfferPriceList context, List<OfferListModel> itemsList,int flag) {
        this.context = context;
        this.itemsList = itemsList;
        this.flag=flag;
    }

    public ItemCardAdapter() {

    }

    public void setItemsList(List<OfferListModel> itemsList) {
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
        TextView itemNo,itemName,categoryId /*,customer*/;
        EditText price ,cashDiscount,otherDiscount;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_offer_list_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.itemNo = view.findViewById(R.id.itemNo);
        holder.itemName = view.findViewById(R.id.itemName);
//        holder.categoryId = view.findViewById(R.id.categId);
        holder.price = view.findViewById(R.id.price);
        //holder.customer=view.findViewById(R.id.customer);
        holder.cashDiscount=view.findViewById(R.id.cashDiscount);
        holder.otherDiscount=view.findViewById(R.id.otherDiscount);


        holder.itemNo .setText(itemsList.get(i).getItemNo());
        holder.itemName .setText(itemsList.get(i).getItemName());
//        holder.categoryId.setText(itemsList.get(i).get());
        holder.price .setText(itemsList.get(i).getPrice());

       // holder.customer .setText(itemsList.get(i).getCustomerName());
        holder.cashDiscount .setText(itemsList.get(i).getCashOffer());
        holder.otherDiscount .setText(itemsList.get(i).getOtherOffer());
//        if(flag==0){
//            holder.price.setEnabled(true);
//            holder.otherDiscount.setEnabled(false);
//            holder.cashDiscount.setEnabled(false);
//        }else if(flag==2){
            holder.price.setEnabled(true);
            holder.otherDiscount.setEnabled(true);
            holder.cashDiscount.setEnabled(true);
//        }


        holder.price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if(!holder.price.getText().toString().equals("")) {
                        if(!holder.price.getText().toString().equals(".")) {
                            try {

                                itemsList.get(i).setPrice("" + Double.parseDouble(holder.price.getText().toString()));
                            }catch (Exception e){

                            }
                        }else {
                            holder.price.setError("Dot!");
                        }
                    }else{
                        holder.price.setError("Required!");
                    }
                }

            }
        });

        holder.otherDiscount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if(!holder.otherDiscount.getText().toString().equals("")) {
                        if(!holder.otherDiscount.getText().toString().equals(".")) {
                            try {
                                itemsList.get(i).setOtherOffer("" + Double.parseDouble(holder.otherDiscount.getText().toString()));
                            }catch (Exception e){

                            }
                        }else {
                            holder.otherDiscount.setError("Dot!");
                        }
                    }else{
                        holder.otherDiscount.setError("Required!");
                    }
                }

            }
        });
        holder.cashDiscount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if(!holder.cashDiscount.getText().toString().equals("")) {
                        if(!holder.cashDiscount.getText().toString().equals(".")) {
                            try {
                                itemsList.get(i).setCashOffer("" + Double.parseDouble(holder.cashDiscount.getText().toString()));
                            }catch (Exception e){

                            }

                        }else {
                            holder.cashDiscount.setError("Dot!");
                        }
                    }else{
                        holder.cashDiscount.setError("Required!");
                    }
                }

            }
        });

        holder.tableRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

               // itemsList.remove(i);

                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Delete Item ")
                        .setContentText("")
                        .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

//                                mainListOffer.remove(i);
//                                context.fillItemCard();
                                sweetAlertDialog.dismissWithAnimation();


                            }
                        })
                        .show();





                return false;
            }
        });

        return view;
    }

}