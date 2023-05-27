package com.example.adminvansales.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.EditSalesMan;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.R;
import com.example.adminvansales.Report.NewCashReport;
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

cViewHolder.setIsRecyclable(false);

//        if(i==(list.size()-1)) {
//            cViewHolder.itemImage.setImageDrawable(context.getDrawable(R.drawable.addimg));
//            cViewHolder. itemImage.getLayoutParams().height = 100;
//
//            cViewHolder.   salesLin.setBackground(context.getDrawable(R.drawable.whitesales_mancard_style));
//            cViewHolder. text_name.setTextColor(context.getColor(R.color.bule3));
//        }

//            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
        cViewHolder.layBar.setTag("" + i);
        cViewHolder.layBar.setEnabled(true);
        final boolean[] longIsOpen = {false};
        cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getAddSalesMen()==1)
                {
                    Intent LogHistoryIntent = new Intent(context, NewCashReport.class);
                    LogHistoryIntent.putExtra("FillData", "FillData");
                    LogHistoryIntent.putExtra("SalesManInfoL", list.get(i));
                    context.startActivity(LogHistoryIntent);
                }else {
                    globelFunction.AuthenticationMessage();
                }

                //

//                if(i==(list.size()-1)){
//                    context.startActivity(new Intent(context,EditSalesMan.class));
//                }
            }


        });
        cViewHolder.layBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getAddSalesMen()==1)
                {
                    Intent LogHistoryIntent = new Intent(context, EditSalesMan.class);
                    LogHistoryIntent.putExtra("FillData", "FillData");
                    LogHistoryIntent.putExtra("SalesManInfoL", list.get(i));
                    context.startActivity(LogHistoryIntent);
                }else {
                    globelFunction.AuthenticationMessage();
                }
                return false;
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//        cViewHolder.layBar.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                longIsOpen[0] = true;
//                new SweetAlertDialog(Context, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("WARNING")
//                        .setContentText(Context.getResources().getString(R.string.deleteAccount) + " ( " + list.get(i).getAccountNo() + " ) !")
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
     ImageView itemImage;
        LinearLayout layBar;
        LinearLayout  salesLin;


        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            layBar = itemView.findViewById(R.id.layBar);
          itemImage = itemView.findViewById(R.id.imgbar);
            salesLin= itemView.findViewById(R.id.salesLin);
//            AccType=itemView.findViewById(R.id.AccType);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
//            return Integer.MAX_VALUE;
    }
}
