package com.example.adminvansales.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.Payment;
import com.example.adminvansales.model.TargetDetalis;

import java.util.List;

public class uncollectAdapter extends RecyclerView.Adapter<uncollectAdapter.ViewHolder>  {


    List<Payment> list;
    Context context;

    public uncollectAdapter(List<Payment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.uncollectdata_row, parent, false);

        return new uncollectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("onBindViewHolder","uncollectAdapter");
        holder.app_bank_name.setText(list.get(position).getBank());
        holder.check_number.setText(list.get(position).getCheckNumber()+"");
        holder.date.setText(list.get(position).getDueDate());
        holder.app_amount2.setText(list.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView app_bank_name,check_number,date,app_amount2;

        public ViewHolder(View itemView) {
            super(itemView);

            app_bank_name =itemView.findViewById(R.id.app_bank_name);
            check_number=itemView.findViewById(R.id.check_number);
            date=itemView.findViewById(R.id.date);
            app_amount2=itemView.findViewById(R.id.app_amount2);



        }


    }

}
