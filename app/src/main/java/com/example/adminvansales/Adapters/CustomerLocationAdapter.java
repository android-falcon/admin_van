package com.example.adminvansales.Adapters;

import static com.example.adminvansales.ImportData.listCustomer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.CustomerLocationSelect;
import com.example.adminvansales.ExportData;
import com.example.adminvansales.OfferPriceList;
import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.ItemMaster;
import com.example.adminvansales.model.OfferListModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerLocationAdapter extends RecyclerView.Adapter<CustomerLocationAdapter.ViewHolder> {
    static List<CustomerInfo> inventorylist;
    public double totalBalance = 0;
    public  int selectedCustomerPosition=-1;
    public  String custNo_selected="",latit_selected="",longtude_selected="";

    Context context;
    private DecimalFormat decimalFormat;
//    onCustomerClickListener myonCustomerClickListener;


    public CustomerLocationAdapter(List<CustomerInfo> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
//        this.myonCustomerClickListener=onCustomerClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_info, parent, false);
        Log.e("", "onCreateViewHolder");
        return new ViewHolder(view);

    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(CustomerLocationAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.customerName.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerName());
        holder.customer_number.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerNumber());

        // holder.orderd_customer.setText(holder.getAdapterPosition()+"");
        holder.select_customer_checkbox.setVisibility(View.GONE);
        Log.e("updateListCustomer",""+   inventorylist.get(position).getLong_customer());
        if(inventorylist.get(holder.getAdapterPosition()).getLatit_customer().equals(""))
        {
            holder.customerLocat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_location_alt_24, 0);
        }else  holder.customerLocat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location_on_black_24dp, 0);








    }

    @Override
    public int getItemCount() {

        return inventorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        LinearLayout linearLayout;

        TextView customerName,address_customer,customer_number,orderd_customer,customerLocat;
        CheckBox select_customer_checkbox;
        onCustomerClickListener myonCustomerClickListener;

        public ViewHolder(View itemView) {
            super(itemView);



            linearLayout = itemView.findViewById(R.id.liner_inventory);
            customerName = itemView.findViewById(R.id.customerName);
            address_customer = itemView.findViewById(R.id.address_customer);
            customer_number = itemView.findViewById(R.id.customer_number);

            select_customer_checkbox=itemView.findViewById(R.id.select_customer_checkbox);
            orderd_customer=itemView.findViewById(R.id.orderd_customer);
            customerLocat=itemView.findViewById(R.id.customerLocat);
            customerLocat.setVisibility(View.VISIBLE);
//            itemView.setOnClickListener(this);
            customerLocat.setOnClickListener(v -> {
                ExportData exportData=new ExportData(context);
                selectedCustomerPosition=getAdapterPosition();
                longtude_selected=inventorylist.get(getAdapterPosition()).getLong_customer();
                latit_selected=inventorylist.get(getAdapterPosition()).getLatit_customer();
                custNo_selected=inventorylist.get(getAdapterPosition()).getCustomerNumber();
                Intent intent =new Intent(context, CustomerLocationSelect.class);
                intent.putExtra("latit",latit_selected);
                intent.putExtra("longtude",longtude_selected);
//                intent.putExtra("latit","31.9695985");
//                intent.putExtra("longtude","35.9138707");
                intent.putExtra("cusName",inventorylist.get(getAdapterPosition()).getCustomerName());
                intent.putExtra("cusNumber",custNo_selected);
                context.startActivity(intent);


//                exportData.updateCustomerLocatio("1110010056","31.9695985","35.9138707");
//
                Log.e("selectedCustomerPos", "="+selectedCustomerPosition);
//                Log.e("customerLocat","name=="+inventorylist.get(getAdapterPosition()).getCustomerName()+"lo=="+inventorylist.get(getAdapterPosition()).getLong_customer());
//
//                openDialogMap(getAdapterPosition(),inventorylist.get(getAdapterPosition()).getCustomerName());
//
//
            });


        }

//        @Override
        public void onClick(View v) {
//            selectedCustomerPosition=getAdapterPosition();
//            onCustomerClickListener.onCustomerClick(getAdapterPosition());
        }
    }
    public  interface  onCustomerClickListener{
        void onCustomerClick(int position);

    }

    private void openDialogMap(int position, String customerName) {

            final Dialog dialogSearch = new Dialog(context, R.style.Theme_Dialog);
            dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSearch.setContentView(R.layout.select_location_customer);
            dialogSearch.setCancelable(true);

            Button cancelButton,addButton;
//            cancelButton=dialogSearch.findViewById(R.id.cancelButton);
//            addButton=dialogSearch.findViewById(R.id.addButton);




//            cancelButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogSearch.dismiss();
//                }
//            });

//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    dialogSearch.dismiss();
//
//                }
//            });

            dialogSearch.show();

        }


    }


