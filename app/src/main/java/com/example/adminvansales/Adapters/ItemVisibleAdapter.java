package com.example.adminvansales.Adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.ImportData;
import com.example.adminvansales.R;
import com.example.adminvansales.databinding.ItemVisiblLayoutBinding;
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
        if  (  inventorylist.get(holder.getAdapterPosition()).getVISIBLE()==1)
            holder.itemInfoBinding.selectCustomerCheckbox.setChecked(true);
        else  if (  inventorylist.get(holder.getAdapterPosition()).getVISIBLE()==0)
            holder.itemInfoBinding.selectCustomerCheckbox.setChecked(false);

        holder.itemInfoBinding.setItemInfoModel(inventorylist.get(position));

        if(ImportData.itemVisiblelsList.size()!=0) {
            if (ifvisabil(inventorylist.get(position).getItemOcode()) == true)
                holder.itemInfoBinding.selectCustomerCheckbox.setChecked(false);
            else

                holder.itemInfoBinding.selectCustomerCheckbox.setChecked(true);

        }
//        holder.select_customer_checkbox.setVisibility(View.VISIBLE);





        if(  holder.itemInfoBinding.selectCustomerCheckbox.isChecked()==true)
            inventorylist.get(holder.getAdapterPosition()).setVISIBLE(1);
        else inventorylist.get(holder.getAdapterPosition()).setVISIBLE(0);
        holder.itemInfoBinding.selectCustomerCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("onCheckedChanged",""+b);
                if(b)
                {
                    inventorylist.get(holder.getAdapterPosition()).setVISIBLE(1);
                }else {
                    inventorylist.get(holder.getAdapterPosition()).setVISIBLE(0);
                }
//                holder.itemInfoBinding.setItemInfoModel(inventorylist.get(holder.getAdapterPosition()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return inventorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        CheckBox select_customer_checkbox;
        ItemVisiblLayoutBinding itemInfoBinding;

        public ViewHolder( ItemVisiblLayoutBinding itemInfoBinding) {
            super(itemInfoBinding.getRoot());
            this.itemInfoBinding=itemInfoBinding;
//            select_customer_checkbox=itemView.findViewById(R.id.select_customer_checkbox);
            Log.e("", "ViewHolder const");

        }
    }
    boolean ifvisabil(String Itemnu)
    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//            try { //using stream
//
//                ItemInfo info = ImportData.itemVisiblelsList.stream()
//                        .filter(ItemInfo -> Itemnu.equals(ItemInfo.getItemOcode()))
//                        .findAny()
//                        .orElse(null);
//                if (info != null)
//                    return info.getVISIBLE() == 0;
//                else return false;
//            } catch (Exception e) {
//                return false;
//            }
//        }else {
            for(int i=0;i<  ImportData.itemVisiblelsList.size();i++)

                if( ImportData.itemVisiblelsList.get(i).getItemOcode().equals(Itemnu))
                {
                    if (ImportData.itemVisiblelsList.get(i) != null)
                        return ImportData.itemVisiblelsList.get(i).getVISIBLE() == 0;
                    else return false;
                }

            return false;
        }
    }



