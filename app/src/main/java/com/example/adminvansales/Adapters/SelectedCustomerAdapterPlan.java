package com.example.adminvansales.Adapters;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.PlanSalesMan.orderType;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.CustomerInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectedCustomerAdapterPlan extends RecyclerView.Adapter<SelectedCustomerAdapterPlan.ViewHolder> {
    static List<CustomerInfo> inventorylist;
    public double totalBalance = 0;

    Context context;
    private DecimalFormat decimalFormat;

    public   List<String> orderList=new ArrayList<>();
    public SelectedCustomerAdapterPlan(List<CustomerInfo> inventorylist, Context context) {
        this.inventorylist = inventorylist;
        this.context = context;
        decimalFormat = new DecimalFormat("00.0000");
        orderList=new ArrayList<>();
        for(int i=0;i<inventorylist.size();i++)
        {
            orderList.add((i+1)+"");
        }
    }

    @Override
    public SelectedCustomerAdapterPlan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_info_order_plan, parent, false);
        Log.e("", "onCreateViewHolder");
        return new SelectedCustomerAdapterPlan.ViewHolder(view);

    }

    public String[] mColors = {"#ffffff", "#E8BCE4F7"};

    @Override
    public void onBindViewHolder(SelectedCustomerAdapterPlan.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.customerName.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerName());
        holder.customer_number.setText(inventorylist.get(holder.getAdapterPosition()).getCustomerNumber());

        holder.customerLocat.setVisibility(View.GONE);
        holder.select_customer_checkbox.setVisibility(View.GONE);
        // holder.orderd_customer.setText(holder.getAdapterPosition()+"");
            holder.select_customer_checkbox.setChecked(true);
            Log.e("getOrder","=="+inventorylist.get(position).getOrder()+"\t"+inventorylist.get(position).getCustomerName());
            if(orderType==1)
            {
                holder.orderSpinner.setVisibility(View.GONE);
            }

            if(inventorylist.get(position).getOrder()<inventorylist.size())
            holder.orderSpinner.setSelection(inventorylist.get(position).getOrder());
            else
            {
//                if(position!=0)
//                {
////                    int previusOrder=inventorylist.get(position-1).getOrder();
////                    if(previusOrder<position)
//                    inventorylist.get(position).setOrder(inventorylist.get(position-1).getOrder()+1);
//                }else
                holder.orderSpinner.setSelection(position);
//                holder.orderSpinner.setSelection(inventorylist.get(position-1).getOrder()+1);
            }

            if(inventorylist.get(position).getDuplicat()==1)
            {
                holder.mailLinearInfo.setBackgroundColor(context.getResources().getColor(R.color.darkOrange2));
            }else {
                holder.mailLinearInfo.setBackgroundColor(context.getResources().getColor(R.color.white));
            }





    }

    @Override
    public int getItemCount() {

        return inventorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout,mailLinearInfo;

        TextView customerName,address_customer,customer_number,orderd_customer,customerLocat;
        CheckBox select_customer_checkbox;
        Spinner orderSpinner;
        ArrayAdapter<String>salesNameSpinnerAdapter;


        public ViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.liner_inventory);
            mailLinearInfo = itemView.findViewById(R.id.mailLinearInfo);
            customerName = itemView.findViewById(R.id.customerName);
            address_customer = itemView.findViewById(R.id.address_customer);
            customer_number = itemView.findViewById(R.id.customer_number);

            select_customer_checkbox=itemView.findViewById(R.id.select_customer_checkbox);
            orderd_customer=itemView.findViewById(R.id.orderd_customer);
            customerLocat=itemView.findViewById(R.id.customerLocat);
            orderSpinner=itemView.findViewById(R.id.orderSpinner);


            salesNameSpinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, orderList);
            salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
            orderSpinner.setAdapter(salesNameSpinnerAdapter);
            orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int listposi= getAdapterPosition();
                    updateListOrder(position,listposi);
                    Log.e("onItemSelected",""+position);
                   // importData.getPlan(salesNum,fromDate.getText().toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


        }

    private void updateListOrder(int order,int positionList) {
        boolean exist=false;

        Log.e("exist","updateListOrder"+"\t"+ order+"\tpositionList="+positionList);
        Log.e("exist",""+exist+inventorylist.get(positionList).getOrder());
        inventorylist.get(positionList).setOrder(order);
//        for(int i=0;i<inventorylist.size();i++){
//            if(inventorylist.get(i).getOrder()==order){
//                exist=true;
//                showMessageFalied();
//                break;
//            }
//        }
        Log.e("exist",""+exist+inventorylist.get(positionList).getOrder());
//        if(!exist){
//            Log.e("exist","getOrder"+"\t"+ inventorylist.get(positionList).getOrder());
//            inventorylist.get(positionList).setOrder(order);
//            Log.e("exist","getOrder"+"\t"+ inventorylist.get(positionList).getOrder());
//
//        }
    }

    private void showMessageFalied() {
        Toast.makeText(context, "exist", Toast.LENGTH_SHORT).show();
    }
}





