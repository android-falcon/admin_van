package com.example.adminvansales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.model.AnalyzeAccountModel;

import java.util.List;

public class AnalyzeAdapter extends BaseAdapter {
    private Context context;
    List<AnalyzeAccountModel> itemsList;

//    DecimalFormat decimalFormat
    public AnalyzeAdapter(Context context, List<AnalyzeAccountModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
//        decimalFormat = new DecimalFormat("##.000");
    }

    public AnalyzeAdapter() {

    }

    public void setItemsList(List<AnalyzeAccountModel> itemsList) {
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
        TextView customerNo,customerName,debit,credit,df,cf,totalDebit,totalCredit,TotalDebitF,TotalCreditF;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final AnalyzeAdapter.ViewHolder holder = new AnalyzeAdapter.ViewHolder();
        view = View.inflate(context, R.layout.analyze_adapter_row, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.customerNo = view.findViewById(R.id.customerNo);
        holder.customerName = view.findViewById(R.id.customerName);
        holder.debit = view.findViewById(R.id.debit);
        holder.credit = view.findViewById(R.id.credit);
        holder.df = view.findViewById(R.id.df);
        holder.cf = view.findViewById(R.id.cf);
        holder.totalDebit = view.findViewById(R.id.totalDebit);
        holder.totalCredit = view.findViewById(R.id.totalCredit);
        holder.TotalDebitF = view.findViewById(R.id.TotalDebitF);
        holder.TotalCreditF = view.findViewById(R.id.TotalCreditF);



        holder.customerNo.setText(itemsList.get(i).getAccCode());
        holder.customerName.setText(itemsList.get(i).getAccNameA());
        holder.debit.setText(itemsList.get(i).getPREVD());
        holder.credit.setText(itemsList.get(i).getPREVC());



        holder.df.setText(itemsList.get(i).getPREVDF());

        holder.cf.setText(itemsList.get(i).getPREVCF());

        holder.totalDebit.setText(itemsList.get(i).getTDEB());

        holder.totalCredit.setText(itemsList.get(i).getTCRE());


        holder.TotalDebitF.setText(itemsList.get(i).getTDEBF());
        holder.TotalCreditF.setText(itemsList.get(i).getTCREF());

        return view;
    }

    {
    }
}
