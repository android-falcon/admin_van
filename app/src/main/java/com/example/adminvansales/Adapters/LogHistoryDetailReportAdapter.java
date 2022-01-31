package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.LogHistoryDetail;

import java.util.List;


public class LogHistoryDetailReportAdapter extends BaseAdapter {
    private Context context;
    List<LogHistoryDetail> itemsList;


    public LogHistoryDetailReportAdapter(Context context, List<LogHistoryDetail> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public LogHistoryDetailReportAdapter() {

    }

    public void setItemsList(List<LogHistoryDetail> itemsList) {
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
        TextView itemNo, itemName, customerNo,customerName,price,priceB,
                discount,discountB,otherDiscount,otherDiscountB,status;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_log_history_detail, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.itemNo = view.findViewById(R.id.itemNo);
        holder.itemName = view.findViewById(R.id.itemName);
        holder.customerNo = view.findViewById(R.id.customerNo);
        holder.customerName = view.findViewById(R.id.customerName);
        holder.price = view.findViewById(R.id.price);
        holder.priceB = view.findViewById(R.id.priceB);
        holder.discount = view.findViewById(R.id.discount);
        holder.discountB= view.findViewById(R.id.discountB);
        holder.otherDiscount=view.findViewById(R.id.otherDiscount);
        holder.otherDiscountB=view.findViewById(R.id.otherDiscountB);
        holder.status=view.findViewById(R.id.status);

        holder.itemNo.setText(itemsList.get(i).getItemNo());
        holder.itemName.setText(itemsList.get(i).getITEM_NAME());
        holder.customerNo.setText(itemsList.get(i).getCUSTOMER_NO());
        holder.customerName.setText(itemsList.get(i).getCustomer_name());
        holder.price .setText(itemsList.get(i).getPrice());
        holder.priceB  .setText(itemsList.get(i).getPrice_before());
        holder.discount  .setText(itemsList.get(i).getDISCOUNT());
        holder.discountB.setText(itemsList.get(i).getDISCOUNT_before());

        holder.otherDiscount.setText(itemsList.get(i).getOTHER_DISCOUNT());
        holder.otherDiscountB.setText(itemsList.get(i).getOTHER_DISCOUNT_before());

        switch (itemsList.get(i).getStatus()){
            case "1"://update
                holder.status.setText("Update");
                holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.green));
                break;
            case "2"://add
                holder.status.setText("Add");

                holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.peacockBlue));
                break;
            case  "3"://remove
                holder.status.setText("Remove");

                holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.light_red));
                break;

        }

        return view;
    }

}