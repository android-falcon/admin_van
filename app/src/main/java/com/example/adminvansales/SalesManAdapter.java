package com.example.adminvansales;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.model.SalesManInfo;

import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;

public class SalesManAdapter extends RecyclerView.Adapter<SalesManAdapter.CViewHolderForbar> {
    Context context;
    List<SalesManInfo> list;
//DatabaseHandler db;
    GlobelFunction globelFunction;

    public SalesManAdapter(Context context, List<SalesManInfo> list) {
        this.context = context;
        this.list = list;
//        db=new DatabaseHandler(this.context);
        globelFunction=new GlobelFunction(context);
    }

    @NonNull
    @Override
    public CViewHolderForbar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_man_row, viewGroup, false);
        return new CViewHolderForbar(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CViewHolderForbar cViewHolder, final int i) {
        cViewHolder.text_name.setText(list.get(i).getSalesName());



//            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
        cViewHolder.layBar.setTag("" + i);
        cViewHolder.layBar.setEnabled(false);
        final boolean[] longIsOpen = {false};
        cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getAddSalesMen()==1) {
                    Intent LogHistoryIntent = new Intent(context, EditSalesMan.class);
                    LogHistoryIntent.putExtra("FillData", "FillData");
                    LogHistoryIntent.putExtra("SalesManInfoL", list.get(i));
                    context.startActivity(LogHistoryIntent);
                }else {
                    globelFunction.AuthenticationMessage();
                }

            }
        });

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//        cViewHolder.layBar.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                longIsOpen[0] = true;
//                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("WARNING")
//                        .setContentText(MainActivity.this.getResources().getString(R.string.deleteAccount) + " ( " + list.get(i).getAccountNo() + " ) !")
//                        .setConfirmText("Ok")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
////                                    dbHandler.deleteAccount(list.get(i).getAccountNo());
//                                AccountNoDelete = list.get(i).getAccountNo();
//                                new DelAccount().execute();
//                                sweetAlertDialog.dismissWithAnimation();
//                            }
//                        })
//                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismissWithAnimation();
//                            }
//                        })
//
//                        .show();
//
//
//                return false;
//            }
//        });

    }
    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView text_name,AccType;
//        ImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            layBar = itemView.findViewById(R.id.layBar);
//            itemImage = itemView.findViewById(R.id.imgbar);
//            AccType=itemView.findViewById(R.id.AccType);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
//            return Integer.MAX_VALUE;
    }
}
