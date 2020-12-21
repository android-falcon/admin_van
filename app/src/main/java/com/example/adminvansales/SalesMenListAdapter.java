package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.SalesManInfo;

import java.util.List;


public class SalesMenListAdapter extends BaseAdapter {
    private Context context;
    List<SalesManInfo> itemsList;


    public SalesMenListAdapter(Context context, List<SalesManInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public SalesMenListAdapter() {

    }

    public void setItemsList(List<SalesManInfo> itemsList) {
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
        TextView salesName, salesNo,password;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_sales_man_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);
        holder.salesName = view.findViewById(R.id.salesName);
        holder.salesNo = view.findViewById(R.id.salesNo);
        holder.password = view.findViewById(R.id.password);
        holder.salesName.setText(itemsList.get(i).getSalesName());
        holder.salesNo.setText(itemsList.get(i).getSalesManNo());
        holder.password.setText(itemsList.get(i).getSalesPassword());

        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    EditSalesMan editSalesMan = (EditSalesMan) context;
                editSalesMan.fillEditText(itemsList.get(i));

            }
        });

        return view;
    }

}