package com.example.adminvansales.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.databinding.RowOfferListAdapterBinding;
import com.example.adminvansales.model.OfferGroupModel;


import java.util.ArrayList;


public class ItemGroupOfferAdapter  extends RecyclerView.Adapter<ItemGroupOfferAdapter.ViewHolder> {
    public ArrayList<OfferGroupModel> listAllOfferGroup;
    RowOfferListAdapterBinding adapterBinding;
    Context mycontext;


    public ItemGroupOfferAdapter( Context context,ArrayList<OfferGroupModel> offerList) {
        this.listAllOfferGroup = offerList;
        this.mycontext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       LayoutInflater inflater = LayoutInflater.from(mycontext);

        RowOfferListAdapterBinding adapterBinding = DataBindingUtil.inflate(inflater, R.layout.row_offer_list_adapter,parent, false);

        return new ViewHolder(adapterBinding);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer_list_adapter, parent, false);
//        return new ViewHolder(view);



    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(final ViewHolder holder, final  int position) {
        holder.setIsRecyclable(false);
        holder.adapterBinding.setItemOffer(listAllOfferGroup.get(position));
        holder.adapterBinding.cashDiscount.setVisibility(View.GONE);
        holder.adapterBinding.otherDiscount.setVisibility(View.GONE);
//        holder.adapterBinding.qtyOffer.setText(listAllOfferGroup.get(position).qtyItem+"");
        holder.adapterBinding.qtyOffer.setTag(position);

    }

    private void updateList(int posi,String qty) {
        Log.e("listAllOfferGroup","position"+posi+"\tqty="+qty+"\t\t"+listAllOfferGroup.get(posi).qtyItem);
        listAllOfferGroup.get(posi).qtyItem=qty.toString().trim();

    }

    @Override
    public int getItemCount() {
        return listAllOfferGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowOfferListAdapterBinding adapterBinding;
        public ViewHolder(final RowOfferListAdapterBinding binding) {

            super(binding.getRoot());
            this.adapterBinding = binding;

           adapterBinding.qtyOffer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().trim().length()!=0)
                    {
                        if(!s.toString().equals("0"))
                        {
                          //  Log.e("posi12","="+adapterBinding.qtyOffer.getTag());

                            //holder.binding.qtyOffer.getTag().toString()
                            int posi= Integer.parseInt(adapterBinding.qtyOffer.getTag().toString());
                             posi= getAdapterPosition();
                            Log.e("posi22020","="+ posi);
                            listAllOfferGroup.get(posi).qtyItem=s.toString().trim();

                           // adapterBinding.executePendingBindings();


                            Log.e("listAllOfferGroup","position"+posi+"\t"+listAllOfferGroup.get(posi).qtyItem);
                        }
                    }

                }
            });

        }
    }
}
