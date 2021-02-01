package com.example.adminvansales;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.Model.ListPriceOffer;
import com.example.adminvansales.Report.ListOfferReport;
import com.google.gson.internal.$Gson$Preconditions;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.adminvansales.Report.ListOfferReport.control;


public class ListOfferReportAdapter extends BaseAdapter {
    private ListOfferReport context;
    List<ListPriceOffer> itemsList;
    GlobelFunction globelFunction;

    ImportData importData;
    ExportData  exportData;
    static TextView controlText = null;

    public ListOfferReportAdapter(ListOfferReport context, List<ListPriceOffer> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        globelFunction=new GlobelFunction(context);
        importData=new ImportData(context);
        exportData = new ExportData(context);
    }

    public ListOfferReportAdapter() {

    }

    public void setItemsList(List<ListPriceOffer> itemsList) {
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
        TextView listNO, listName, listType, fromDate, toDate,closeOpenList,intentText,updateList,activateList;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_list_offer_price_adapter, null);

        holder.tableRow = view.findViewById(R.id.table);

        holder.listNO = view.findViewById(R.id.salesNo);
        holder.listName = view.findViewById(R.id.salesManName);
        holder.listType = view.findViewById(R.id.cashSales);
        holder.fromDate = view.findViewById(R.id.creditSale);
        holder.toDate = view.findViewById(R.id.netSales);
        holder.closeOpenList=view.findViewById(R.id.closeList);
        holder.intentText=view.findViewById(R.id.intentText);
        holder.updateList=view.findViewById(R.id.updateList);
        holder.activateList=view.findViewById(R.id.activateList);

        try {
            if(itemsList.get(i).getACTIVATE_LIST().equals("0")) {
                holder.activateList.setVisibility(View.INVISIBLE);
                holder.updateList.setVisibility(View.GONE);
                if (itemsList.get(i).getCLOSE_OPEN_LIST().equals("1") ||  itemsList.get(i).getPO_LIST_TYPE().equals("1")) {

                    holder.closeOpenList.setVisibility(View.INVISIBLE);

                } else {
                    holder.closeOpenList.setVisibility(View.VISIBLE);
                }

                if( itemsList.get(i).getPO_LIST_TYPE().equals("1")){
                    holder.closeOpenList.setVisibility(View.GONE);
                    holder.updateList.setVisibility(View.VISIBLE);
                }

            }else  if(itemsList.get(i).getACTIVATE_LIST().equals("1")) {
                holder.activateList.setVisibility(View.VISIBLE);
                holder.closeOpenList.setVisibility(View.GONE);
                if (itemsList.get(i).getCLOSE_OPEN_LIST().equals("0") ) {

                    holder.updateList.setVisibility(View.VISIBLE);

                } else {
                    holder.updateList.setVisibility(View.GONE);
                    holder.closeOpenList.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception e){
            holder.closeOpenList.setVisibility(View.VISIBLE);
        }


        holder.activateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               importData.getItemUpdateActivateList(context,itemsList.get(i).getPO_LIST_NO());
            }
        });

        holder.listNO.setText(itemsList.get(i).getPO_LIST_NO());
        holder.listName.setText(itemsList.get(i).getPO_LIST_NAME());
        holder.fromDate.setText(itemsList.get(i).getFROM_DATE());
        holder.toDate.setText(itemsList.get(i).getTO_DATE());

        switch (itemsList.get(i).getPO_LIST_TYPE()){
            case "0":
                holder.listType.setText("Price BY Customer");

                break;
            case "1":
                holder.listType.setText("Price Only");

                break;
            case "2":
                holder.listType.setText("OFFer");

                break;

        }
        holder.closeOpenList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("closeList","lll"+itemsList.get(i).getPO_LIST_TYPE());
                if(!itemsList.get(i).getPO_LIST_TYPE().equals("1")){
                    Log.e("closeList","lll2");
                    context.getItem(itemsList.get(i));
                    importData.getItemCustomerByListNo(context,itemsList.get(i).getPO_LIST_NO() ,"0");

                }

            }
        });

        holder.updateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("updateList","lll"+itemsList.get(i).getPO_LIST_TYPE());
                if(!itemsList.get(i).getPO_LIST_TYPE().equals("1")){
                    Log.e("updateList","lll2");
                    context.getItem(itemsList.get(i));
                    importData.getItemCustomerByListNo(context,itemsList.get(i).getPO_LIST_NO(),"1" );

                }else {
                    context.getItem(itemsList.get(i));
                    control.setText("update");
                }

            }
        });


        holder.tableRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //dialogToUpdateDate(itemsList.get(i));

                return false;
            }
        });

        return view;
    }


    @SuppressLint("SetTextI18n")
    void dialogToUpdateDate(final ListPriceOffer listPriceOffer){

        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_list_date_dialog);
        dialog.setCancelable(true);
        final TextView listName,listNO,listType,fromDate,toDate,update,cancel,updateText;

        listName=dialog.findViewById(R.id.listName);
        listNO=dialog.findViewById(R.id.listNo);
        listType=dialog.findViewById(R.id.listType);
        fromDate=dialog.findViewById(R.id.fromDate);
        toDate=dialog.findViewById(R.id.toDate);
        update=dialog.findViewById(R.id.update);
        cancel=dialog.findViewById(R.id.cancel);
        updateText=dialog.findViewById(R.id.updateText);
        controlText=updateText;
        updateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(updateText.getText().toString().equals("1")){

                    listPriceOffer.setPO_LIST_NAME(listName.getText().toString());
                    listPriceOffer.setFROM_DATE(fromDate.getText().toString());
                    listPriceOffer.setTO_DATE(toDate.getText().toString());

                   // exportData.updateList(context,listPriceOffer.getJsonObjectList());
                    Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                }else   if(updateText.getText().toString().equals("2")){
                    context.previewFunction();
                    dialog.dismiss();
                    updateText.setText("0");

                }

                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.DateClick(fromDate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globelFunction.DateClick(toDate);
            }
        });

        listName.setText(""+listPriceOffer.getPO_LIST_NAME());
        listNO.setText(""+listPriceOffer.getPO_LIST_NO());
        listType.setText(""+listPriceOffer.getPO_LIST_TYPE());
        fromDate.setText(""+listPriceOffer.getFROM_DATE());
        toDate.setText(""+listPriceOffer.getTO_DATE());


        switch (listPriceOffer.getPO_LIST_TYPE()){
            case "0":
                listType.setText("Price BY Customer");

                break;
            case "1":
                listType.setText("Price Only");

                break;
            case "2":
                listType.setText("OFFer");

                break;

        }

       // importData.ifBetweenDate(OfferPriceList.this, fromDate.getText().toString(), toDate.getText().toString(),""+position,"0",listNo.getText().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listName.getText().toString().equals("")) {

                    importData.ifBetweenDate(context, fromDate.getText().toString(), toDate.getText().toString(),""+listPriceOffer.getPO_LIST_TYPE(),"1",listPriceOffer.getPO_LIST_NO(),null);

                } else {
                    listName.setError("Required!");
                }


            }
        });

        dialog.show();

    }

}