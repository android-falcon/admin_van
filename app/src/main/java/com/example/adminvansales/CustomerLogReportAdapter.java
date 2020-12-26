package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.Report.CustomerLogReport;

import java.util.List;


public class CustomerLogReportAdapter extends BaseAdapter {
    private Context context;
    List<CustomerLogReportModel> itemsList;


    public CustomerLogReportAdapter(Context context, List<CustomerLogReportModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public CustomerLogReportAdapter() {

    }

    public void setItemsList(List<CustomerLogReportModel> itemsList) {
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
        TextView salesId,salesManName,customerCode,customerName,checkInD,checkInT
                ,checkOutD,checkOutT;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_customer_log_report_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.salesId = view.findViewById(R.id.salesId);
        holder.salesManName = view.findViewById(R.id.salesManName);
        holder.customerCode = view.findViewById(R.id.customerCode);
        holder.checkInD = view.findViewById(R.id.checkInD);
        holder.checkInT = view.findViewById(R.id.checkInT);
        holder.checkOutD = view.findViewById(R.id.checkOutD);
        holder.checkOutT = view.findViewById(R.id.checkOutT);
        holder.customerName= view.findViewById(R.id.customerName);


        holder.salesId .setText(itemsList.get(i).getSALES_MAN_ID());
        holder.salesManName .setText(itemsList.get(i).getSalesmanname());
        holder.customerCode .setText(itemsList.get(i).getCUS_CODE());
        holder.customerName.setText(itemsList.get(i).getCUS_NAME());
        holder.checkInD .setText(itemsList.get(i).getCHECK_IN_DATE());
        holder.checkInT  .setText(itemsList.get(i).getCHECK_IN_TIME());
        holder.checkOutD  .setText(itemsList.get(i).getCHECK_OUT_DATE());
        holder.checkOutT.setText(itemsList.get(i).getCHECK_OUT_TIME());

        return view;
    }

}