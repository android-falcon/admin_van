package com.example.adminvansales.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adminvansales.OfferPriceList;
import com.example.adminvansales.R;
import com.example.adminvansales.model.customerInfoModel;

import java.util.List;

import static com.example.adminvansales.OfferPriceList.customerSelect;


public class ListAdapterSearchCustomer extends BaseAdapter {
    CheckBox checkPriceed;
    private OfferPriceList context;
    private Context contexts;
    TextView customerText;
    List<customerInfoModel> itemsList;
    int flag;
    String phoneNo,language;
    Dialog dialog;
    public ListAdapterSearchCustomer(OfferPriceList context, List<customerInfoModel> itemsList, Dialog dialog, int flag, Context contexts) {
        this.context = context;
        this.itemsList = itemsList;
this.dialog=dialog;
this.flag=flag;

        this.contexts = contexts;

       // this.customerText=customerText;
    }

    public ListAdapterSearchCustomer() {

    }

    public void setItemsList(List<customerInfoModel> itemsList) {
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
        TextView accNo,voucherNo,customerName;
        CheckBox itemCheckBox;
        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_search_customer_adaptir, null);

        holder.tableRow=  view.findViewById(R.id.table);
        holder.accNo =  view.findViewById(R.id.accNo);
        holder.customerName =  view.findViewById(R.id.custName);
        holder.itemCheckBox=view.findViewById(R.id.itemCheckBox);


//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.accNo.setText(itemsList.get(i).getCustID());
        holder.customerName.setText(itemsList.get(i).getCustName());
//        holder.customerName.setText(itemsList.get(i).getF_D());
//        holder.tableRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              // customerText.setText(""+itemsList.get(i).getCustName());
//                if(flag==1) {
//                    context.fillDataInLayout(itemsList.get(i));
//                }
//
//                 dialog.dismiss();
//            }
//        });

        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean found=false;
                int deletePos=-1;
                if (customerSelect.size() != 0){
                    for (int k = 0; k < customerSelect.size(); k++) {
                        Log.e("mainListOffer12", "" + customerSelect.size());
                        if (itemsList.get(i).getCustID().equals(customerSelect.get(k).getCustID())) {
                            deletePos=k;
                            found=true;
                            break;

                        }

                    }


                    if(found){
                        if (isChecked) {

                        } else {
                            customerSelect.remove(deletePos);
                        }
                    }else{
                        customerSelect.add(itemsList.get(i));
                    }


            }else {
                    customerSelect.add(itemsList.get(i));

            }


            }
        });

        return view;
    }

        }