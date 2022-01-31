package com.example.adminvansales.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.adminvansales.R;
import com.example.adminvansales.databinding.RowOfferListAdapterBinding;
import com.example.adminvansales.model.ItemMaster;
import com.example.adminvansales.model.OfferGroupModel;

import java.util.ArrayList;

public class ItemGroupAdapter extends BaseAdapter {
    public  ArrayList<OfferGroupModel> listAllOfferGroup;
    RowOfferListAdapterBinding adapterBinding;
    Context mycontext;
    public ItemGroupAdapter(Context context, ArrayList<OfferGroupModel> listOffer) {
        this.listAllOfferGroup=listOffer;
        this.mycontext=context;
    }

    @Override
    public int getCount() {
        return listAllOfferGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        TextView itemNo,itemName,categoryId /*,customer*/;
        EditText price ,cashDiscount,otherDiscount;
        TableRow tableRow;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PlanetViewHolder holder;

//        viewHolder = new ViewHolder();
//        if (convertView == null) {
//            final LayoutInflater inflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        }
//        vi = inflater1.inflate(R.layout.song_listitem_layout, null);



        if (convertView == null) {
            RowOfferListAdapterBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_offer_list_adapter, parent, false);

            holder = new PlanetViewHolder(itemBinding);
            holder.view = itemBinding.getRoot();
//            holder.view.setTag(position) ;
            holder.view.setTag(holder) ;
           // holder.binding.qtyOffer.setTag(position);
          //  Log.e("posi33","="+holder.binding.qtyOffer.getTag());
        }
        else {
           holder = (PlanetViewHolder) convertView.getTag();
        }

        holder.binding.setItemOffer(listAllOfferGroup.get(position));
        holder.binding.cashDiscount.setVisibility(View.GONE);
        holder.binding.otherDiscount.setVisibility(View.GONE);
        holder.binding.qtyOffer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    listAllOfferGroup.get(position).qtyItem=holder.binding.qtyOffer.getText().toString();
                }
            }
        });
//        holder.binding.qtyOffer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.toString().trim().length()!=0)
//                {
//                    if(!s.toString().equals("0"))
//                    {
//                        Log.e("posi12","="+holder.view.getTag());
//
//                        //holder.binding.qtyOffer.getTag().toString()
////                        int posi= Integer.parseInt(holder.binding.qtyOffer.getTag().toString());
//                        Log.e("posi22020","="+ posi);
//                        listAllOfferGroup.get(posi).qtyItem=s.toString().trim();
//                        Log.e("listAllOfferGroup","position"+posi+"\t"+listAllOfferGroup.get(posi).qtyItem);
//                    }
//                }
//
//            }
//        });
//        final ItemGroupAdapter.ViewHolder holder = new ItemGroupAdapter.ViewHolder();
//        convertView = View.inflate(mycontext, R.layout.row_offer_list_adapter, null);
        return holder.view;
    }
    private static class PlanetViewHolder {
        private View view;
        RowOfferListAdapterBinding  binding;

        PlanetViewHolder(RowOfferListAdapterBinding binding) {
            this.view = binding.getRoot();
            this.binding = binding;
        }
    }
}
