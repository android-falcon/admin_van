package com.example.adminvansales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.ItemsRequsts;
import com.example.adminvansales.model.SalesManInfo;

import java.util.List;

public class RequstAdapter  extends RecyclerView.Adapter<RequstAdapter.CViewHolderForbar>{
    Context context;
    List<ItemsRequsts> list;

    public RequstAdapter(Context context, List<ItemsRequsts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CViewHolderForbar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.requestrow, parent, false);
        return new RequstAdapter.CViewHolderForbar(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolderForbar holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView text_name,req_num,itemcount;



        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            req_num= itemView.findViewById(R.id.req_num);
            itemcount= itemView.findViewById(R.id.itemcount);
        }
    }
}
