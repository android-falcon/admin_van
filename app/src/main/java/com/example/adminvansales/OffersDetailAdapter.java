package com.example.adminvansales;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.model.OfferListModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.ImportData.offerGroupModels;
import static com.example.adminvansales.Report.OfferseReport.fillAdapter;
import static com.example.adminvansales.Report.OfferseReport.offersGroupAdapter;
import static com.example.adminvansales.Report.OfferseReport.updateAdapter;

public class OffersDetailAdapter extends RecyclerView.Adapter<OffersDetailAdapter.OffersDetailViewHolder>{
    private List<com.example.adminvansales.model.OfferGroupModel> list;
    Context context;
    private String newqty;

    public OffersDetailAdapter(List<com.example.adminvansales.model.OfferGroupModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offersdetailrecycler, parent, false);
        return new OffersDetailAdapter.OffersDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersDetailViewHolder holder, int position) {
        holder.itemname.setText(list.get(position).getName());
        holder.qty.setText(list.get(position).getQtyItem());
        holder. itemno.setText(list.get(position).getItemNo());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  OffersDetailViewHolder extends RecyclerView.ViewHolder{
        TextView itemname ,dec,itemno;
EditText qty;
        TextView remove;
        public  OffersDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            itemno = itemView.findViewById(R.id.itemno);
            itemname = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);

            qty.setEnabled(false);
                    remove = itemView.findViewById(R.id.remove);

            remove.setVisibility(View.GONE);




        }
    }
}
