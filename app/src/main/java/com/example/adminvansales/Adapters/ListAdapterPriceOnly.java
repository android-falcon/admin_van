package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.OfferPriceList;
import com.example.adminvansales.R;
import com.example.adminvansales.model.ItemMaster;

import java.util.List;


public class ListAdapterPriceOnly extends BaseAdapter {
    private OfferPriceList context;
    private Context contexts;
    List<ItemMaster> itemsList;
    int clear=0;
    public ListAdapterPriceOnly(OfferPriceList context, List<ItemMaster> itemsList,int clear) {
        this.context = context;
        this.itemsList = itemsList;


        this.contexts = contexts;

       // this.customerText=customerText;
    }

    public ListAdapterPriceOnly() {

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
        TextView itemNo, itemName;
        EditText price;
        CheckBox itemCheckBox;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_price_only_adaptir, null);

        holder.tableRow=  view.findViewById(R.id.table);
        holder.itemNo =  view.findViewById(R.id.itemNo);
        holder.itemName =  view.findViewById(R.id.itemName);
        holder.price =  view.findViewById(R.id.price);
        holder.itemCheckBox=view.findViewById(R.id.itemCheckBox);


//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.itemNo.setText(itemsList.get(i).getItemNo());
        holder.itemName.setText(itemsList.get(i).getName());
        if(clear==1){
           itemsList.get(i).setCheckedItem(false);
           itemsList.get(i).setPrice(itemsList.get(i).getF_D());
        }


//        itemsList.get(i).setPrice(itemsList.get(i).getF_D());
        holder.price.setText(itemsList.get(i).getPrice());
        holder.itemCheckBox.setChecked(itemsList.get(i).isCheckedItem());
//        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(!priceOnly.getText().toString().equals("")) {
//                    if (isChecked) {
//                        itemsList.get(i).setCheckedItem(true);
//                        itemsList.get(i).setPrice(""+Double.parseDouble(priceOnly.getText().toString()));
//
//                        holder.price.setText(""+Double.parseDouble(priceOnly.getText().toString()));
//                    } else {
//                        itemsList.get(i).setCheckedItem(false);
//                        itemsList.get(i).setPrice(itemsList.get(i).getF_D());
//
//                        holder.price.setText(itemsList.get(i).getF_D());
//                    }
//                }else{
//                    priceOnly.setError("Required!");
//                }
//
//            }
//        });

        holder.price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if(!holder.price.getText().toString().equals("")) {
                        // itemsList.get(i).setCheckedItem(true);
                        itemsList.get(i).setPrice("" + Double.parseDouble(holder.price.getText().toString()));

                    }else{
                        holder.price.setError("Required!");
                    }
                }

            }
        });


        return view;
    }

        }