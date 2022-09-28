package com.example.adminvansales.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.addSalesmanTarget;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.ItemInfo;
import com.example.adminvansales.model.ItemReportModel;
import com.example.adminvansales.model.TargetDetalis;

import java.lang.annotation.Target;
import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> {

   List<TargetDetalis> list;
int pos;
    Context context;

    public TargetAdapter(List<TargetDetalis> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TargetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(context)
            .inflate(R.layout.targetrow, parent, false);

      return new TargetAdapter.ViewHolder(view);
}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(list.get(position).getItemName());
        holder.itemNo.setText(list.get(position).getItemNo());
        holder.target.setText(list.get(position).getItemTarget() + "");
       if(addSalesmanTarget.fabClick)   holder.target.setEnabled(true);
       else holder.target.setEnabled(false);
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

                  Log.e("  holder.target", addSalesmanTarget.targetList.size()+"");
                 list.get(holder.getAdapterPosition()).setItemTarget(Double.parseDouble(holder.target.getText().toString()));
              if(       list.get(holder.getAdapterPosition()).getItemTarget()!=0)
              {
                  if (!CheckIsExistsINLocalList(    addSalesmanTarget.targetDetalisList.get(holder.getAdapterPosition()).getItemNo()))
                      addSalesmanTarget.targetList.add(   list.get(holder.getAdapterPosition()));
                  else
                      addSalesmanTarget.targetList.get(pos).setItemTarget(  list.get(holder.getAdapterPosition()).getItemTarget());
              }else
              {
                  if (CheckIsExistsINLocalList(   list.get(holder.getAdapterPosition()).getItemNo()))
                      addSalesmanTarget.targetList.remove(pos);
              }
                }    else
                    addSalesmanTarget.targetDetalisList.get(holder.getAdapterPosition()).setItemTarget(0);
            }
        });
        if (position == addSalesmanTarget.highligtedItemPosition2) {
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

TextView  itemName,itemNo,target;
LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            itemName =itemView.findViewById(R.id.itemName);
             itemNo=itemView.findViewById(R.id.itemNo);
            target=itemView.findViewById(R.id.target);
            linearLayout=itemView.findViewById(R.id.Row_lin);



        }


    }
    private boolean CheckIsExistsINLocalList(String barcode) {


        boolean flag = false;
        if (list.size() != 0)
            for (int i = 0; i < addSalesmanTarget.targetList.size(); i++) {

                if (
                        addSalesmanTarget.targetList.get(i).getItemNo().equals(barcode)

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


    }
}
