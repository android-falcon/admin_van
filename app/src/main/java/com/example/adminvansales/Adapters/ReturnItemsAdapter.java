package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.EditSalesMan;
import com.example.adminvansales.R;
import com.example.adminvansales.model.SalesManInfo;

import java.util.List;

class ReturnItemsAdapter extends BaseAdapter {
    private Context context;
    List<String> itemsList;

    public ReturnItemsAdapter(Context context, List<String> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }



    public void setItemsList(List<String> itemsList) {
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
        TextView itemName, price;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ReturnItemsAdapter.ViewHolder holder = new ReturnItemsAdapter.ViewHolder();
        view = View.inflate(context, R.layout.itemsrow, null);

        holder.itemName = view.findViewById(R.id.itemName);
        holder.price = view.findViewById(R.id.price);
        try {
            holder.itemName.setText(itemsList.get(i));

            holder.price.setText(adapterrequst.Pricelist.get(i));
        }catch (Exception exception){

        }



        return view;
    }
}
