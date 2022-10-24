package com.example.adminvansales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.Adapters.TargetReportAdapter;
import com.example.adminvansales.model.CommissionTarget;
import com.example.adminvansales.model.TargetDetalis;

import java.util.List;

class CommissionTargetReportAdapter extends RecyclerView.Adapter<CommissionTargetReportAdapter.ViewHolder> {

   List<CommissionTarget> list;

   Context context;

   public CommissionTargetReportAdapter(List<CommissionTarget> list, Context context) {
      this.list = list;
      this.context = context;
   }

   @NonNull
   @Override
   public CommissionTargetReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(context)
              .inflate(R.layout.targetreportrow, parent, false);

      return new CommissionTargetReportAdapter.ViewHolder(view);
   }
   @Override
   public void onBindViewHolder(@NonNull CommissionTargetReportAdapter.ViewHolder holder, int position) {
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
