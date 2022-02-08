package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.Plan_SalesMan_model;

import java.util.List;

public class PlansReportAdapter extends RecyclerView.Adapter<PlansReportAdapter.ViewHolder> {

    private Context context;
    private List<Plan_SalesMan_model> planList;

    public PlansReportAdapter(Context context, List<Plan_SalesMan_model> planList) {
        this.context = context;
        this.planList = planList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.plans_report, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.custNameTV.setText(planList.get(position).getCustomerName());
        holder.custNoTV.setText(planList.get(position).getCustomerNumber());
        holder.orderTV.setText(planList.get(position).getOrderd()+"");  //0 --- Manual  1 --- Location

    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView custNameTV, custNoTV, orderTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custNameTV = itemView.findViewById(R.id.custNameTV);
            custNoTV = itemView.findViewById(R.id.custNoTV);
            orderTV = itemView.findViewById(R.id.orderTV);

        }
    }

}
