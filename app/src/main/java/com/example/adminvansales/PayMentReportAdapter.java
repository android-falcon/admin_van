package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.model.PayMentReportModel;

import java.util.List;


public class PayMentReportAdapter extends BaseAdapter {
    private Context context;
    List<PayMentReportModel> itemsList;


    public PayMentReportAdapter(Context context, List<PayMentReportModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public PayMentReportAdapter() {

    }

    public void setItemsList(List<PayMentReportModel> itemsList) {
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
        TextView voucherNo,payDate,customerName,amount,salesManName
                ,remark,sales,pay;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_payment_report_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.voucherNo = view.findViewById(R.id.voucherNo);
        holder.payDate = view.findViewById(R.id.payDate);
        holder.customerName = view.findViewById(R.id.customerName);
        holder.amount = view.findViewById(R.id.amount);
        holder.remark = view.findViewById(R.id.remark);
        holder.sales = view.findViewById(R.id.sales);
        holder.pay = view.findViewById(R.id.pay);
        holder.salesManName = view.findViewById(R.id.salesManName);



        holder.voucherNo .setText(itemsList.get(i).getVouNo());
        holder.payDate .setText(itemsList.get(i).getPaymentDate());
        holder.customerName .setText(itemsList.get(i).getCustomerNo());
        holder.amount .setText(itemsList.get(i).getAmount());
        holder.remark  .setText(itemsList.get(i).getNotes());
        holder.sales  .setText(itemsList.get(i).getSalesmanNo());
        if(itemsList.get(i).getPAYKIND().equals("1")){
            holder.pay.setText(context.getResources().getString(R.string.cash));
        }else  if(itemsList.get(i).getPAYKIND().equals("0")){
            holder.pay.setText(context.getResources().getString(R.string.app_cheque));
        }else  if(itemsList.get(i).getPAYKIND().equals("2")) {
            holder.pay.setText(context.getResources().getString(R.string.credit));
        }
        holder.salesManName.setText(itemsList.get(i).getSalesmanname());

//        holder.tableRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    EditSalesMan editSalesMan = (EditSalesMan) context;
//                editSalesMan.fillEditText(itemsList.get(i));
//
//            }
//        });

        return view;
    }

}