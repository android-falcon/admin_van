package com.example.adminvansales.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerLogReportModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CustomerLogReportAdapter extends BaseAdapter {
    private Context context;
    List<CustomerLogReportModel> itemsList;


    public CustomerLogReportAdapter(Context context, List<CustomerLogReportModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public CustomerLogReportAdapter() {

    }

    public void setItemsList(List<CustomerLogReportModel> itemsList) {
        this.itemsList = itemsList;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView salesId,salesManName,customerCode,customerName,checkInD,checkInT
                ,checkOutD,checkOutT,duration,VOUCHERCOUNT;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_customer_log_report_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.salesId = view.findViewById(R.id.salesId);
        holder.salesManName = view.findViewById(R.id.salesManName);
        holder.customerCode = view.findViewById(R.id.customerCode);
        holder.checkInD = view.findViewById(R.id.checkInD);
        holder.checkInT = view.findViewById(R.id.checkInT);
        holder.checkOutD = view.findViewById(R.id.checkOutD);
        holder.checkOutT = view.findViewById(R.id.checkOutT);
        holder.customerName= view.findViewById(R.id.customerName);
        holder.duration= view.findViewById(R.id.duration);
        holder.  VOUCHERCOUNT= view.findViewById(R.id.VOUCHERCOUNT);
        holder.salesId .setText(itemsList.get(i).getSALES_MAN_ID());
        holder.salesManName .setText(itemsList.get(i).getSalesmanname());
        holder.customerCode .setText(itemsList.get(i).getCUS_CODE());
        holder.customerName.setText(itemsList.get(i).getCUS_NAME());
        holder.checkInD .setText(itemsList.get(i).getCHECK_IN_DATE());
        holder.  VOUCHERCOUNT.setText(itemsList.get(i).getVOUCHERCOUNT()+"");
        SimpleDateFormat dateFormat, timeformat;

        timeformat = new SimpleDateFormat("hh:mm");
        Date date1,date2;
        String  dateCurent="",dateTime1="",dateTime2="";
try {
     date1=new SimpleDateFormat("hh:mm").parse(itemsList.get(i).getCHECK_IN_TIME());
    date2=new SimpleDateFormat("hh:mm").parse(itemsList.get(i).getCHECK_OUT_TIME());



    long difference = date2.getTime() - date1.getTime();
int    days = (int) (difference / (1000*60*60*24));
    int    hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
    int  min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
    hours = (hours < 0 ? -hours : hours);
if (hours==0)
    holder.duration.setText(min +" min");
else
    holder.duration.setText(hours+" hours"+ "  "+min +" min");

    Log.e("======= Hours"," :: "+hours);
    Log.e("======= min",min+"");
      dateCurent = timeformat.format(date1);
       dateTime1=convertToEnglish(dateCurent);
}catch (Exception exception){
    Log.e("exception",exception.getMessage());
}


        holder.checkInT  .setText(dateTime1);
        holder.checkOutD  .setText(itemsList.get(i).getCHECK_OUT_DATE());
        holder.checkOutT.setText(itemsList.get(i).getCHECK_OUT_TIME());

        return view;
    }
    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }
}