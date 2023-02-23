package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.adminvansales.R;
import com.example.adminvansales.Report.PaymentDetailsReport;
import com.example.adminvansales.Report.UnCollectedData;
import com.example.adminvansales.model.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

public class CustomersListAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<CustomerInfo> custList;
    int activity;
    public CustomersListAdapter(int activity ,Context context, ArrayList<CustomerInfo> custList) {
        this.context = context;
        this.activity=activity;
        this.custList = custList;
    }


    @Override
    public int getCount() {
        return custList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.customer_row, null);

        holder.linearLayout = (LinearLayout) view.findViewById(R.id.LinearLayout01);

        holder.custAccountTextView = (TextView) view.findViewById(R.id.cusnum);
        holder.custNameTextView = (TextView) view.findViewById(R.id.cusname);
        holder.custNameTextView.setText(custList.get(i).getCustomerName());
        holder.custAccountTextView.setText(custList.get(i).getCustomerNumber());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity==1)
                {   UnCollectedData.customerAccountNo.setText(custList.get(i).getCustomerNumber());
                UnCollectedData. customerAccountname.setText(custList.get(i).getCustomerName());
                UnCollectedData.dialoglist.dismiss();}
                else
                {   PaymentDetailsReport.customerAccountNo.setText(custList.get(i).getCustomerNumber());
                    PaymentDetailsReport. customerAccountname.setText(custList.get(i).getCustomerName());
                    PaymentDetailsReport.dialoglist.dismiss();}
            }
        });
        return view;
    }
    private class ViewHolder {
        LinearLayout linearLayout;
        TextView custAccountTextView;
        TextView custNameTextView;

    }
}
