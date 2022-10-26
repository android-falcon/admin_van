package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.CommissionTarget;
import com.example.adminvansales.model.TargetDetalis;

import java.util.List;

public class CommTargetReportAdapter extends RecyclerView.Adapter<CommTargetReportAdapter.ViewHolder> {

    List<CommissionTarget> list;

    Context context;

    public CommTargetReportAdapter(List<CommissionTarget> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CommTargetReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.comm_targetrow, parent, false);

        return new CommTargetReportAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CommTargetReportAdapter.ViewHolder holder, int position) {
     //   holder.Perceofunfulfilledgoal.setText(list.get(position).getPERC());
        holder.goalachievementrate.setText(list.get(position).getOrignalNetSale());
        holder.item_number.setText(list.get(position).getItemNo());
        holder.item_name.setText(list.get(position).getItemName());
        holder.target.setText(list.get(position).getItemTarget() + "");
        holder.perc.setText(list.get(position).getPERC() + "");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_name,item_number,target,net_sales2,Perceofunfulfilledgoal,goalachievementrate,perc;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            item_name =itemView.findViewById(R.id.item_name);
            item_number   =itemView.findViewById(R.id.item_number);
            target =itemView.findViewById(R.id.target);
            net_sales2=itemView.findViewById(R.id.net_sales2);
            goalachievementrate=itemView.findViewById(R.id.goalachievementrate);
            //Perceofunfulfilledgoal=itemView.findViewById(R.id.Perceofunfulfilledgoal);
            perc=itemView.findViewById(R.id.perc);


        }


    }
}
