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

import com.example.adminvansales.ItemVisibility;
import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.ItemInfo;

import java.text.DecimalFormat;
import java.util.List;

public class ItemVisibleAdapter extends RecyclerView.Adapter<ItemVisibleAdapter.ViewHolder> {
    static List<ItemInfo> inventorylist;
    public double totalBalance = 0;

    Context context;
    private DecimalFormat decimalFormat;


    public ItemVisibleAdapter(List<ItemInfo> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visibl_layout, parent, false);
        Log.e("", "onCreateViewHolder");
        return new ViewHolder(view);

    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(ItemVisibleAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.customerName.setText(inventorylist.get(holder.getAdapterPosition()).getItemNameA());
        holder.customer_number.setText(inventorylist.get(holder.getAdapterPosition()).getItemOcode());

        holder.customerLocat.setVisibility(View.GONE);
        holder.select_customer_checkbox.setVisibility(View.VISIBLE);
        // holder.orderd_customer.setText(holder.getAdapterPosition()+"");
        if  (  inventorylist.get(holder.getAdapterPosition()).getSelect()==1)
            holder.select_customer_checkbox.setChecked(true);
        else  if (  inventorylist.get(holder.getAdapterPosition()).getSelect()==0)
            holder.select_customer_checkbox.setChecked(false);
        holder.select_customer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("select_customer_","bb"+b);
                if(b)
                {
                    inventorylist.get(holder.getAdapterPosition()).setSelect(1);
//                    updateqty(holder, list.get(holder.getAdapterPosition()).getItemNo());

                }else {
                    inventorylist.get(holder.getAdapterPosition()).setSelect(0);
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
            Log.e("", "ViewHolder const");

        }
    }

}


