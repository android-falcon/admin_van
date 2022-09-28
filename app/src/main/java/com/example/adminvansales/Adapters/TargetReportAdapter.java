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

import com.example.adminvansales.R;
import com.example.adminvansales.addSalesmanTarget;
import com.example.adminvansales.model.TargetDetalis;

import java.util.List;

public class TargetReportAdapter extends RecyclerView.Adapter<TargetReportAdapter.ViewHolder> {

   List<TargetDetalis> list;

   Context context;

   public TargetReportAdapter(List<TargetDetalis> list, Context context) {
      this.list = list;
      this.context = context;
   }

   @NonNull
   @Override
   public TargetReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(context)
              .inflate(R.layout.targetreportrow, parent, false);

      return new TargetReportAdapter.ViewHolder(view);
   }
   @Override
   public void onBindViewHolder(@NonNull TargetReportAdapter.ViewHolder holder, int position) {
      holder.Perceofunfulfilledgoal.setText("");
      holder.goalachievementrate.setText("");
      holder.net_sales2.setText(list.get(position).getItemNo());
      holder.target.setText(list.get(position).getItemTarget() + "");

   }
   @Override
   public int getItemCount() {
      return list.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      TextView target,net_sales2,Perceofunfulfilledgoal,goalachievementrate;
      LinearLayout linearLayout;
      public ViewHolder(View itemView) {
         super(itemView);

         target =itemView.findViewById(R.id.target);
         net_sales2=itemView.findViewById(R.id.net_sales2);
         goalachievementrate=itemView.findViewById(R.id.goalachievementrate);
         Perceofunfulfilledgoal=itemView.findViewById(R.id.Perceofunfulfilledgoal);



      }


   }
}
