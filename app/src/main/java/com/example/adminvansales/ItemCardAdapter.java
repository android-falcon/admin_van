package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.ItemMaster;

import java.util.List;


public class ItemCardAdapter extends BaseAdapter {
    private Context context;
    List<ItemMaster> itemsList;


    public ItemCardAdapter(Context context, List<ItemMaster> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public ItemCardAdapter() {

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
        TextView itemNo,itemName,categoryId,barcode,price ;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_offer_list_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.itemNo = view.findViewById(R.id.itemNo);
        holder.itemName = view.findViewById(R.id.itemName);
        holder.categoryId = view.findViewById(R.id.categId);
        holder.barcode = view.findViewById(R.id.Barcode);
        holder.price = view.findViewById(R.id.price);


        holder.itemNo .setText(itemsList.get(i).getItemNo());
        holder.itemName .setText(itemsList.get(i).getName());
        holder.categoryId.setText(itemsList.get(i).getCateogryID());
        holder.barcode .setText(itemsList.get(i).getBarcode());
        holder.price .setText(itemsList.get(i).getF_D());

        return view;
    }

}