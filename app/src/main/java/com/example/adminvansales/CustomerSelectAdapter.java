package com.example.adminvansales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.model.customerInfoModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.OfferPriceList.customerSelect;


public class CustomerSelectAdapter extends BaseAdapter {
    private OfferPriceList context;
    List<customerInfoModel> itemsList;


    public CustomerSelectAdapter(OfferPriceList context, List<customerInfoModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public CustomerSelectAdapter() {

    }

    public void setItemsList(List<customerInfoModel> itemsList) {
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
        TextView custId,custName;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_customer_selecte_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.custId = view.findViewById(R.id.custNo);
        holder.custName = view.findViewById(R.id.custName);

        holder.custName.setText(itemsList.get(i).getCustName());
        holder.custId.setText(itemsList.get(i).getCustID());

        try{
        if(itemsList.get(i).isCheckedItem()){

            holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.light_red));
        }else {
            holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.NoColor));

        }}catch (Exception e){
            holder.tableRow.setBackgroundColor(context.getResources().getColor(R.color.NoColor));

        }

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

                                customerSelect.remove(i);

                                context.notifyCustomerSelectAdapter();
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