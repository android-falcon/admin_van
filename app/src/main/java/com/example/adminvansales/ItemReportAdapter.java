package com.example.adminvansales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.ItemReportModel;
import com.example.adminvansales.Model.SalesManInfo;

import java.util.List;


public class ItemReportAdapter extends BaseAdapter {
    private ItemReport context;
    List<ItemReportModel> itemsList;
    GlobelFunction globelFunction;

    ImportData importData;
    ExportData  exportData;
    static TextView controlText = null;

    public ItemReportAdapter(ItemReport context, List<ItemReportModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        globelFunction=new GlobelFunction(context);
        importData=new ImportData(context);
        exportData = new ExportData(context);
    }

    public ItemReportAdapter() {

    }

    public void setItemsList(List<ItemReportModel> itemsList) {
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
        TextView adminNo, itemName, salesName, date, qty,price,total,itemNo;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_item_report, null);

        holder.tableRow = view.findViewById(R.id.table);
        holder.itemName = view.findViewById(R.id.itemName);
        holder.date = view.findViewById(R.id.date);
        holder.salesName = view.findViewById(R.id.salesManName);
        holder.qty = view.findViewById(R.id.qty);
        holder.price = view.findViewById(R.id.price);
        holder.total=view.findViewById(R.id.total);
        holder.itemNo =view.findViewById(R.id.itemNo);


        holder.itemName .setText(""+itemsList.get(i).getName());
        holder.date .setText(""+itemsList.get(i).getVoucherDate());
        holder.salesName .setText(""+itemsList.get(i).getSalesName());
        holder.qty .setText(""+itemsList.get(i).getQty());
        holder.price .setText(""+itemsList.get(i).getUnitPrice());
        holder.total.setText(""+itemsList.get(i).getTotal());

        holder.itemNo.setText(""+itemsList.get(i).getItemNo());

        return view;
    }

}