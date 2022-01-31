package com.example.adminvansales.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.Account__Statment_Model;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.adminvansales.AccountStatment.total_qty_text;

public class AccountStatmentAdapter extends RecyclerView.Adapter<AccountStatmentAdapter.ViewHolder>
{
    static List<Account__Statment_Model> inventorylist;
    public  double totalBalance=0;

    Context context;
    private DecimalFormat decimalFormat;


    public AccountStatmentAdapter(List<Account__Statment_Model> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_row, parent, false);
        Log.e("","onCreateViewHolder");
        return new ViewHolder(view);

    }
    public String[] mColors = {"#ffffff","#E8BCE4F7"};
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        holder.setIsRecyclable(false);
        holder.voucherNo.setText(inventorylist.get(holder.getAdapterPosition()).getVoucherNo());
        holder.transeName.setText(inventorylist.get(holder.getAdapterPosition()).getTranseNmae());
        holder.date_transe.setText(inventorylist.get(holder.getAdapterPosition()).getDate_voucher()+"");

        holder.debit.setText(inventorylist.get(holder.getAdapterPosition()).getDebit()+"");
        holder.credit.setText(inventorylist.get(holder.getAdapterPosition()).getCredit()+"");
        //holder.balance.setText(decimalFormat.format(totalBalance)));

        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        holder.linearLayout.setPadding(5 , 10, 5, 10);

        holder.balance.setText(convertToEnglish(decimalFormat.format(inventorylist.get(holder.getAdapterPosition()).getBalance())));
        if(inventorylist.size()!=0)
        {
            total_qty_text.setText(convertToEnglish(decimalFormat.format(inventorylist.get(inventorylist.size()-1).getBalance())));
        }

    }

    @Override
    public int getItemCount() {

        return inventorylist.size();
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;

        TextView        balance,credit,debit,date_transe,transeName,voucherNo;
        public ViewHolder(View itemView)
        {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.liner_inventory);
            balance = itemView.findViewById(R.id.balance);
            credit = itemView.findViewById(R.id.credit);
            debit = itemView.findViewById(R.id.debit);
            date_transe= itemView.findViewById(R.id.date_transe);
            transeName  = itemView.findViewById(R.id.transeName);
            voucherNo = itemView.findViewById(R.id.voucherNo);
            Log.e("","ViewHolder const");

        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


}