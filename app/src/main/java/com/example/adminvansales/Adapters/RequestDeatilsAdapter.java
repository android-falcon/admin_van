package com.example.adminvansales.Adapters;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.adminvansales.ImportData;
import com.example.adminvansales.R;
import com.example.adminvansales.model.ItemsRequsts;
import com.example.adminvansales.model.SalesManInfo;

import java.util.List;
public class RequestDeatilsAdapter extends  Adapter<RequestDeatilsAdapter.CViewHolderForbar>{

    private List<ItemsRequsts> list;
    Context context;

    public RequestDeatilsAdapter(List<ItemsRequsts> list, Context context) {
        this.list = list;
        this.context = context;
        Log.e("RequestDeatilsAdapter==","RequestDeatilsAdapter");

    }

    @NonNull
    @Override
    public CViewHolderForbar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requstdetailsrow , parent, false);
        return new RequestDeatilsAdapter.CViewHolderForbar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolderForbar holder, int position) {

        Log.e("itemNo==", ImportData.itemsRequsts.size()+"");

        holder.itemNo.setText(list.get(position).getItemnumber());
        holder.Request_qty.setText(list.get(position).getQty());
        holder.itemNo.setText(list.get(position).getItemnumber());

        holder.ApprovedQuantity.setText(list.get(position).getApprovedqty()+"");
        holder.ApprovedQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    try {
                      list.get(holder.getAdapterPosition()).setApprovedqty(  Double.parseDouble(holder.ApprovedQuantity.getText().toString()));

                    }catch (Exception exception)
                    {

                    }
                }
            }
        });
     if(   list.get(holder.getAdapterPosition()).getStatus().equals("1"))
     {   holder.ApprovedStatus.setChecked(true);
         list.get(holder.getAdapterPosition()).setStatus("1");

     }
     else  {
         holder.ApprovedStatus.setChecked(false);
         list.get(holder.getAdapterPosition()).setStatus("0");
     }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView itemName,itemNo,Request_qty;
        EditText ApprovedQuantity;
        SwitchCompat ApprovedStatus;
        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemNo= itemView.findViewById(R.id.itemNo);
            Request_qty= itemView.findViewById(R.id.Request_qty);
            ApprovedQuantity= itemView.findViewById(R.id.ApprovedQuantity);
            ApprovedStatus= itemView.findViewById(R.id.ApprovedStatus);
        }
    }
   }
