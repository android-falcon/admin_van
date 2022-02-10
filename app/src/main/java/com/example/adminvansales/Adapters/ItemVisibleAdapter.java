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

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.ItemVisibility;
import com.example.adminvansales.R;
import com.example.adminvansales.databinding.ItemVisiblLayoutBinding;
import com.example.adminvansales.databinding.RowOfferListAdapterBinding;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.ItemInfo;

import java.text.DecimalFormat;
import java.util.List;

public class ItemVisibleAdapter extends RecyclerView.Adapter<ItemVisibleAdapter.ViewHolder> {
    static List<ItemInfo> inventorylist;
    Context context;
    private DecimalFormat decimalFormat;


    public ItemVisibleAdapter(List<ItemInfo> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemVisiblLayoutBinding adapterBinding = DataBindingUtil.inflate(inflater, R.layout.item_visibl_layout,
                parent, false);
        return new ViewHolder(adapterBinding);

    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(ItemVisibleAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        holder.itemInfoBinding.setItemInfoModel(inventorylist.get(position));
        holder.select_customer_checkbox.setVisibility(View.VISIBLE);
        if  (  inventorylist.get(holder.getAdapterPosition()).getSelect()==1)
            holder.select_customer_checkbox.setChecked(true);
        else  if (  inventorylist.get(holder.getAdapterPosition()).getSelect()==0)
            holder.select_customer_checkbox.setChecked(false);
        holder.select_customer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    inventorylist.get(holder.getAdapterPosition()).setSelect(1);
                }else {
                    inventorylist.get(holder.getAdapterPosition()).setSelect(0);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return inventorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox select_customer_checkbox;
        ItemVisiblLayoutBinding itemInfoBinding;

        public ViewHolder( ItemVisiblLayoutBinding itemInfoBinding) {
            super(itemInfoBinding.getRoot());
            this.itemInfoBinding=itemInfoBinding;
            select_customer_checkbox=itemView.findViewById(R.id.select_customer_checkbox);
            Log.e("", "ViewHolder const");

        }
    }

}


