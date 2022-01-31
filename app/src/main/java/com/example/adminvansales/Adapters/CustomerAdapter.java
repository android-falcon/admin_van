package com.example.adminvansales.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerInfo;

import java.text.DecimalFormat;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    static List<CustomerInfo> inventorylist;
    public double totalBalance = 0;

    Context context;
    private DecimalFormat decimalFormat;


    public CustomerAdapter(List<CustomerInfo> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_info, parent, false);
        Log.e("", "onCreateViewHolder");
        return new ViewHolder(view);

    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.customerName.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerName());
        holder.customer_number.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerNumber());

        holder.customerLocat.setVisibility(View.GONE);
        holder.select_customer_checkbox.setVisibility(View.VISIBLE);
       // holder.orderd_customer.setText(holder.getAdapterPosition()+"");
        if  (  inventorylist.get(holder.getAdapterPosition()).getIsSelected()==1)
            holder.select_customer_checkbox.setChecked(true);
        else  if (  inventorylist.get(holder.getAdapterPosition()).getIsSelected()==0)
            holder.select_customer_checkbox.setChecked(false);
        holder.select_customer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("select_customer_","bb"+b);
                if(b)
                {
                    inventorylist.get(holder.getAdapterPosition()).setIsSelected(1);
//                    updateqty(holder, list.get(holder.getAdapterPosition()).getItemNo());

                }else {
                    inventorylist.get(holder.getAdapterPosition()).setIsSelected(0);
//                    updateqty2(holder, list.get(holder.getAdapterPosition()).getItemNo());
                }
            }
        });



    }

    @Override
    public int getItemCount() {

        return inventorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        TextView customerName,address_customer,customer_number,orderd_customer,customerLocat;
        CheckBox select_customer_checkbox;

        public ViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.liner_inventory);
            customerName = itemView.findViewById(R.id.customerName);
            address_customer = itemView.findViewById(R.id.address_customer);
            customer_number = itemView.findViewById(R.id.customer_number);

            select_customer_checkbox=itemView.findViewById(R.id.select_customer_checkbox);
            orderd_customer=itemView.findViewById(R.id.orderd_customer);
            customerLocat=itemView.findViewById(R.id.customerLocat);
            Log.e("", "ViewHolder const");

        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }
}

