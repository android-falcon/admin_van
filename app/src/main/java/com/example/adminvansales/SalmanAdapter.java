package com.example.adminvansales;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adminvansales.model.SalesManInfo;

import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;

class SalmanAdapter extends BaseAdapter {
    Context context;
    List<SalesManInfo> list;
    //DatabaseHandler db;
    GlobelFunction globelFunction;
    public SalmanAdapter(Context context, List<SalesManInfo> list) {
        this.context = context;
        this.list = list;
//        db=new DatabaseHandler(this.context);
        globelFunction=new GlobelFunction(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.sales_man_row, parent, false);
        }
        LinearLayout layBar;
        TextView text_name,AccType;
        ImageView imageView;
      LinearLayout  salesLin;
        imageView= convertView.findViewById(R.id.imgbar);
        text_name = convertView.findViewById(R.id.text_name);
        salesLin= convertView.findViewById(R.id.salesLin);
        layBar = convertView.findViewById(R.id.layBar);
        Log.e("i===",i+""+"   list==="+list.size());
        text_name.setText(list.get(i).getSalesName());
       if(i==(list.size()-1)) {
           imageView.setImageDrawable(context.getDrawable(R.drawable.addimg));
           imageView.getLayoutParams().height = 100;

           salesLin.setBackground(context.getDrawable(R.drawable.whitesales_mancard_style));
           text_name.setTextColor(context.getColor(R.color.bule3));
       }


   layBar.setTag("" + i);
       layBar.setEnabled(true);
        final boolean[] longIsOpen = {false};
    layBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("onClick","onClick"+i+"\t"+list.size());
                if (i != (list.size() - 1)) {
                    globelFunction.setValidation();
                    if (salesManInfoAdmin.getAddSalesMen() == 1) {
                        Intent LogHistoryIntent = new Intent(context, EditSalesMan.class);
                        LogHistoryIntent.putExtra("FillData", "FillData");
                        LogHistoryIntent.putExtra("SalesManInfoL", list.get(i));
                        context.startActivity(LogHistoryIntent);
                    } else {
                        globelFunction.AuthenticationMessage();
                    }

                }
                else
                {
                    Intent intent=new Intent(context,EditSalesMan.class);
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }
}
