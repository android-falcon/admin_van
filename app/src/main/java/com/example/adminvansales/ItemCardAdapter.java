package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.ItemMaster;
import com.example.adminvansales.Model.OfferListModel;
import com.example.adminvansales.Model.SalesManInfo;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.OfferPriceList.mainListOffer;


public class ItemCardAdapter extends BaseAdapter {
    private OfferPriceList context;
    List<OfferListModel> itemsList;


    public ItemCardAdapter(OfferPriceList context, List<OfferListModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
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
        TextView itemNo,itemName,categoryId,price ,customer,cashDiscount,otherDiscount;
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
        holder.customer=view.findViewById(R.id.customer);
        holder.cashDiscount=view.findViewById(R.id.cashDiscount);
        holder.otherDiscount=view.findViewById(R.id.otherDiscount);


        holder.itemNo .setText(itemsList.get(i).getItemNo());
        holder.itemName .setText(itemsList.get(i).getItemName());
//        holder.categoryId.setText(itemsList.get(i).get());
        holder.price .setText(itemsList.get(i).getPrice());

        holder.customer .setText(itemsList.get(i).getCustomerName());
        holder.cashDiscount .setText(itemsList.get(i).getCashOffer());
        holder.otherDiscount .setText(itemsList.get(i).getOtherOffer());

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

                                mainListOffer.remove(i);
                                context.fillItemCard();
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