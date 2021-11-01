package com.example.adminvansales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.model.LogHistoryModel;
import com.example.adminvansales.Report.LogHistoryReport;

import java.util.List;


public class LogHistoryReportAdapter extends BaseAdapter {
    private LogHistoryReport context;
    List<LogHistoryModel> itemsList;
    GlobelFunction globelFunction;

    ImportData importData;
    ExportData  exportData;
    static TextView controlText = null;

    public LogHistoryReportAdapter(LogHistoryReport context, List<LogHistoryModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        globelFunction=new GlobelFunction(context);
        importData=new ImportData(context);
        exportData = new ExportData(context);
    }

    public LogHistoryReportAdapter() {

    }

    public void setItemsList(List<LogHistoryModel> itemsList) {
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
        TextView adminNo, adminName, listType, listNo, date,updateType;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_log_history_adaptir, null);

        holder.tableRow = view.findViewById(R.id.table);
        holder.adminNo = view.findViewById(R.id.adminNo);
        holder.adminName = view.findViewById(R.id.adminName);
        holder.listType = view.findViewById(R.id.listType);
        holder.listNo = view.findViewById(R.id.listNo);
        holder.date = view.findViewById(R.id.date);
        holder.updateType=view.findViewById(R.id.updateType);


        holder.adminName.setText(itemsList.get(i).getADMIN_NAME());
        holder.adminNo.setText(itemsList.get(i).getADMIN_ID());
        if(itemsList.get(i).getLIST_TYPE().equals("0")) {
            holder.listType.setText("Price By Customer");
        }else  if(itemsList.get(i).getLIST_TYPE().equals("1")) {
            holder.listType.setText("Price Only");
        }else  if(itemsList.get(i).getLIST_TYPE().equals("2")) {
            holder.listType.setText("OFFer");
        }
        holder.listNo.setText(itemsList.get(i).getLIST_NO());
        holder.date.setText(itemsList.get(i).getDATE_OF_UPDATE());

        if(itemsList.get(i).getUPDATE_TYPE().equals("1")) {
            holder.updateType.setText("Cancel List");
        }else if(itemsList.get(i).getUPDATE_TYPE().equals("2")) {
            holder.updateType.setText("Update List");
        }


        holder.tableRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

            importData.getItemUpdateActivateList(context , itemsList.get(i).getSERIAL(),itemsList.get(i).getLIST_NO(),itemsList.get(i).getLIST_TYPE());

                return false;
            }
        });

        return view;
    }

}