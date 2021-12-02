package com.example.adminvansales;

import android.app.Dialog;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.ImportData.offerGroupModels;
import static com.example.adminvansales.Report.OfferseReport.fillAdapter;

public class OffersDetailAdapter2 extends RecyclerView.Adapter<OffersDetailAdapter2.OffersDetailViewHolder2>{
    private List<com.example.adminvansales.model.OfferGroupModel> list;
    Context context;
    private String newqty;

    public OffersDetailAdapter2(List<com.example.adminvansales.model.OfferGroupModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersDetailViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offersdetailrecycler, parent, false);
        return new OffersDetailViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersDetailViewHolder2 holder, int position) {
        holder.itemname.setText(list.get(position).getName());
        holder.qty.setText(list.get(position).getQtyItem());

        holder. itemno.setText(list.get(position).getItemNo());

        holder. itemno.setTag(list.get(position).getItemNo());
        holder.itemname.setTag(list.get(position).getGroupid());

        holder. qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    try {

                        int pos =position;

                        newqty=editable.toString();
                        if(!newqty.trim().equals("0"))
                        {

                            list.get(pos).setQtyItem(newqty);
                            //   Log.e("case1===",s+" pos=== "+pos+"itemcode"+list.get(pos).getItemcode());
                        }




                        else
                        {
                            list.get(pos).setQtyItem(offerGroupModels.get(pos).getQtyItem());
                            notifyDataSetChanged();

                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("")
                                    .setContentText("not valid quantity")
                                    .show();
                        }
                    }catch (Exception e){}


                }
            }});
    }
    public void removeItem(int position,String itemgroub,String itemno) {

        Log.e("removeItemposition===",position+"");
        //  Log.e("removeItemgropid===",list.get(position).getGroupid()+"");
        if(position<list.size()){

            for(int i=0;i<offerGroupModels.size();i++)
                if(String.valueOf(itemgroub).equals(offerGroupModels.get(i).getGroupid())

                        &&String.valueOf(itemno).equals(offerGroupModels.get(i).getItemNo()))
                    offerGroupModels.remove(i);

            //  list.remove(position);
            //   notifyItemRemoved(position);

            //  notifyDataSetChanged();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class OffersDetailViewHolder2 extends RecyclerView.ViewHolder{
        TextView itemname ,dec,itemno;
        EditText qty;
        TextView remove;
        public OffersDetailViewHolder2(@NonNull View itemView) {
            super(itemView);
            itemno = itemView.findViewById(R.id.itemno);
            itemname = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);


            remove = itemView.findViewById(R.id.remove);



            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("removeonClick","onClick");
                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //removeItem(position);
                            // notifyItemRemoved(position);

                            int position=getAdapterPosition();
                            Log.e("rItemno--",itemno.getTag()+"");
                            Log.e("Itemname--",itemname.getTag()+"");
                            Log.e("removeItemposition",position+"");
                            removeItem(position,String.valueOf(itemname.getTag()),String.valueOf(itemno.getTag()));
                           list.remove(position);
                            notifyDataSetChanged();
                            dialog.dismiss();
                         //   fillAdapter(context);
                            // updateAdapter(context);
                            Log.e("removeItemposition",position+"");

                        }
                    });
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }
            });

        }
    }
}
