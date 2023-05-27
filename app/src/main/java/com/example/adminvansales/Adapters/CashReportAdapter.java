package com.example.adminvansales.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.R;
import com.example.adminvansales.model.CashReportModel;

import java.util.List;


public class CashReportAdapter extends BaseAdapter {
    private Context context;
    List<CashReportModel> itemsList;


    public CashReportAdapter(Context context, List<CashReportModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public CashReportAdapter() {

    }

    public void setItemsList(List<CashReportModel> itemsList) {
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
        TextView salesNo,salesManName,cashSales,creditSale,netSales
                ,paymentCash,sales,net,credit,netCash;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_cash_report_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.salesNo = view.findViewById(R.id.salesNo);
        holder.salesManName = view.findViewById(R.id.salesManName);
        holder.cashSales = view.findViewById(R.id.cashSales);
        holder.creditSale = view.findViewById(R.id.creditSale);
        holder.netSales = view.findViewById(R.id.netSales);
        holder.paymentCash = view.findViewById(R.id.paymentCash);
        holder.sales = view.findViewById(R.id.sales);
        holder.net = view.findViewById(R.id.net);
        holder.credit= view.findViewById(R.id.credit);
        holder.netCash= view.findViewById(R.id.netCash);


        holder.salesNo .setText(itemsList.get(i).getSalesManNo());
        holder.salesManName .setText(itemsList.get(i).getSalesManName());

        String totalCash= itemsList.get(i).getTotalCash();
        String totalCredit= itemsList.get(i).getTotalCredite();
        String totalPCash= itemsList.get(i).getPtotalCash();
        String totalPCredit= itemsList.get(i).getPtotalCredite();
        String totalPCreditCard= itemsList.get(i).getPtotalCrediteCard();

//        if(!TextUtils.isEmpty(totalCash)) {
//            totalCash= itemsList.get(i).getTotalCash();
//            Log.e("totalCash",""+totalCash);
//        }else {
//            totalCash= "0";
//            Log.e("totalCash0",""+totalCash);
//        }

//        Log.e("totalCashsize",""+itemsList.size());
//
//        try{
//           Double.parseDouble(totalCash);
//        }catch(Exception e){
//            totalCash="0.0";
//            Log.e("totalCashs",""+totalCash);
//        }



        if(TextUtils.isEmpty(totalCash)) {
            totalCash= "0.0";
            Log.e("totalCash",""+totalCash);
        }

        if(TextUtils.isEmpty(totalCredit)) {
            totalCredit= "0.0";
            Log.e("totalCredit",""+totalCredit);
        }


        if(TextUtils.isEmpty(totalPCash)) {
            totalPCash= "0.0";
            Log.e("totalPCash",""+totalPCash);
        }


        if(TextUtils.isEmpty(totalPCredit)) {
            totalPCredit= "0.0";
            Log.e("totalPCredit",""+totalPCredit);
        }


        if(TextUtils.isEmpty(totalPCreditCard)) {
            totalPCreditCard= "0.0";
            Log.e("totalPCreditCard",""+totalPCreditCard);
        }



        holder.cashSales .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble(totalCash))));
        holder.creditSale .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble(totalCredit))));

        double netSalesT=Double.parseDouble(totalCash) + Double.parseDouble(totalCredit);
        Log.e("netSalesT",""+netSalesT);
        holder.netSales.setText("" +GlobelFunction.convertToEnglish(String.format("%.3f",netSalesT)));
        itemsList.get(i).setNetSale(""+netSalesT);
        holder.paymentCash  .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble(totalPCash))));
        holder.sales  .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble(totalPCredit))));
        holder.net  .setText(GlobelFunction.convertToEnglish(String.format("%.3f",(Double.parseDouble(totalPCash)+Double.parseDouble(totalPCredit)))));
        itemsList.get(i).setNetPay(""+(Double.parseDouble(totalPCash)+Double.parseDouble(totalPCredit)));
        holder.credit  .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble(totalPCreditCard))));
       // double netTotalCash=Double.parseDouble(totalPCreditCard)+Double.parseDouble(totalCash)+Double.parseDouble(totalPCash);

        double netTotalCash=Double.parseDouble(totalCash)+Double.parseDouble(totalPCash);
       // holder.amount .setText(GlobelFunction.convertToEnglish(String.format("%.3f",Double.parseDouble( itemsList.get(i).getAmount()))));

        holder.netCash  .setText(""+GlobelFunction.convertToEnglish(String.format("%.3f",netTotalCash)));
        itemsList.get(i).setNetCash(""+netTotalCash);
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