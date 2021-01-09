package com.example.adminvansales;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.ItemMaster;

import java.util.List;


public class ListAdapterSearchVoucher extends BaseAdapter {
    CheckBox checkPriceed;
    private OfferPriceList context;
    private Context contexts;
    TextView customerText;
    List<ItemMaster> itemsList;
    int flag;
 String phoneNo,language;
    Dialog dialog;
    public ListAdapterSearchVoucher(OfferPriceList context, List<ItemMaster> itemsList, Dialog dialog, int flag, Context contexts) {
        this.context = context;
        this.itemsList = itemsList;
this.dialog=dialog;
this.flag=flag;

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
        TextView accNo,voucherNo,customerName;
        CheckBox itemCheckBox;

TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_search_adaptir, null);

        holder.tableRow=  view.findViewById(R.id.table);
        holder.accNo =  view.findViewById(R.id.accNo);
        holder.voucherNo =  view.findViewById(R.id.voNo);
        holder.customerName =  view.findViewById(R.id.custName);
        holder.itemCheckBox=view.findViewById(R.id.itemCheckBox);


//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.accNo.setText(itemsList.get(i).getItemNo());
        holder.voucherNo.setText(itemsList.get(i).getName());
        holder.customerName.setText(itemsList.get(i).getF_D());
//        holder.tableRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              // customerText.setText(""+itemsList.get(i).getCustName());
//                if(flag==1) {
//                    context.fillDataInLayout(itemsList.get(i));
//                }
//
//                 dialog.dismiss();
//            }
//        });

        return view;
    }

        }