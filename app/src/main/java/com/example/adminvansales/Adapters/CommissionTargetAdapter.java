package com.example.adminvansales.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.AddCommissionTarget;
import com.example.adminvansales.R;
import com.example.adminvansales.AddCommissionTarget;
import com.example.adminvansales.model.CommissionTarget;
import com.example.adminvansales.model.TargetDetalis;

import java.util.List;

public  class CommissionTargetAdapter extends RecyclerView.Adapter<CommissionTargetAdapter.ViewHolder> {

    List<CommissionTarget> list;
    int pos;
    Context context;

    public CommissionTargetAdapter(List<CommissionTarget> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CommissionTargetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.comm_targetrow, parent, false);

        return new CommissionTargetAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CommissionTargetAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(list.get(position).getItemName());
        holder.itemNo.setText(list.get(position).getItemNo());
        holder.target.setText(list.get(position).getItemTarget() + "");
        holder.   qty.setText(list.get(position).getQty() + "");
        holder.target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    Log.e("  holder.target", AddCommissionTarget.targetList.size()+"");
                    list.get(holder.getAdapterPosition()).setItemTarget(Double.parseDouble(holder.target.getText().toString()));
                    if(       list.get(holder.getAdapterPosition()).getItemTarget()!=0)
                    {
                        if (!CheckIsExistsINLocalList(    AddCommissionTarget.targetDetalisList.get(holder.getAdapterPosition()).getItemNo()))
                            AddCommissionTarget.targetList.add(   list.get(holder.getAdapterPosition()));
                        else
                            AddCommissionTarget.targetList.get(pos).setItemTarget(  list.get(holder.getAdapterPosition()).getItemTarget());
                    }else
                    {
                        if (CheckIsExistsINLocalList(   list.get(holder.getAdapterPosition()).getItemNo()))
                            AddCommissionTarget.targetList.remove(pos);
                    }
                }    else
                    AddCommissionTarget.targetDetalisList.get(holder.getAdapterPosition()).setItemTarget(0);
            }
        });
        if (position == AddCommissionTarget.highligtedItemPosition2) {
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else {

            Log.e("dddd", "dddd");
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName,itemNo,target,qty;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            itemName =itemView.findViewById(R.id.itemName);
            itemNo=itemView.findViewById(R.id.itemNo);
            target=itemView.findViewById(R.id.target);
            linearLayout=itemView.findViewById(R.id.Row_lin);
            qty=itemView.findViewById(R.id.qty);


        }


    }
    private boolean CheckIsExistsINLocalList(String barcode) {


        boolean flag = false;
        if (list.size() != 0)
            for (int i = 0; i < AddCommissionTarget.targetList.size(); i++) {

                if (
                        AddCommissionTarget.targetList.get(i).getItemNo().equals(barcode)

                ) {
                    pos = i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }    }