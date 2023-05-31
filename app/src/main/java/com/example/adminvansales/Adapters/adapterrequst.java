package com.example.adminvansales.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.EditSalesMan;
import com.example.adminvansales.ExportData;
import com.example.adminvansales.Interface.DaoRequsts;
import com.example.adminvansales.R;
import com.example.adminvansales.model.Request;
import com.example.adminvansales.model.RequstTest;
import com.example.adminvansales.model.SettingModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.adminvansales.MainActivity.isListUpdated;

public class adapterrequst extends RecyclerView.Adapter<adapterrequst.ViewHolder> {
    Context context;
    private DatabaseReference databaseReference,databaseReference2;
    //    List<notification> notificationList;
    List<RequstTest> requestList;
    //    public PhotoView photoView,photoDetail;
    CircleImageView circleImageView;
    Bitmap serverPicBitmap;
    public static int row_index = -1;
    DataBaseHandler databaseHandler;
    String requestState = "0", amountArabic = "";
    //    LoginINFO infoUser;
//    DatabaseHandler databaseHandler;
    public static String languagelocalApp = "";
    public static String acc = "", bankN = "", branch = "", mobileNo = "";
    int typeDiscountItem=0;
    public static   List<String> Pricelist;

    public adapterrequst(List<RequstTest> itemsList, Context context) {
        this.requestList = itemsList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_request, viewGroup, false);

        return new adapterrequst.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



        viewHolder.timeRequest.setText(requestList.get(i).getTime());
        viewHolder.date_request.setText(requestList.get(i).getDate());
        viewHolder.customerName.setText(requestList.get(i).getCustomer_name());
        viewHolder.amountValue.setText(requestList.get(i).getAmount_value());
        Log.e("total_voucher", "onBindViewHolder" + requestList.get(i).getTotal_voucher());


        if (requestList.get(i).getRequest_type().equals("1"))
        {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalDiscount));
            viewHolder.amountValue.setText( requestList.get(i).getAmount_value()+"\t%");

        }
        else if (requestList.get(i).getRequest_type().equals("10")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalDiscount));
            viewHolder.amountValue.setText( requestList.get(i).getAmount_value()+"\tJD");
        }
        else if (requestList.get(i).getRequest_type().equals("3")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalBonus));
        } else if (requestList.get(i).getRequest_type().equals("0")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.itemDiscount));
            try {
                typeDiscountItem=Integer.parseInt(requestList.get(i).getNote().substring(0,1));
            }
            catch (Exception e)
            {
                typeDiscountItem=2;
            }


            if(typeDiscountItem==1)
            {
                viewHolder.amountValue.setText( requestList.get(i).getAmount_value()+"\t%");
            }
            else {
                viewHolder.amountValue.setText( requestList.get(i).getAmount_value()+"\tJD");
            }

        } else if (requestList.get(i).getRequest_type().equals("2")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.itemBonus));
        } else if (requestList.get(i).getRequest_type().equals("100")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.OverflowDebtlimit));
            viewHolder.requestType.setTextColor(context.getResources().getColor(R.color.light_red));
            viewHolder.amountValue.setText( context.getResources().getString(R.string.limit)+"\t"+requestList.get(i).getAmount_value() +"\tJD");
        }else if (requestList.get(i).getRequest_type().equals("506")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.returnvoch_per));

        }else if (requestList.get(i).getRequest_type().equals("5")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.log_in_cus_outrange));

        }


        viewHolder.salesManName.setText(requestList.get(i).getSalesman_name());
        viewHolder.lineardetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = i;
                viewHolder.linearCheckInfo.setBackground(context.getResources().getDrawable((R.drawable.btn_sighn_up)));
                Log.e("showDetails", "" + requestList.get(i).getTotal_voucher() );
                viewHolder.showDetails(requestList.get(i).getTotal_voucher());
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView timeRequest, date_request ,customerName ,amountValue ,requestType, salesManName ;

        ImageView image_check;
        LinearLayout linearCheckInfo, mainLinearAdapter, divider, lineardetail, rowStatus;
        CircleImageView acceptImg, rejectImg, reciveNew, checkimage_state;
        SharedPreferences loginPrefs;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            timeRequest = itemView.findViewById(R.id.timeRequest);
            date_request = itemView.findViewById(R.id.date_request);
            amountValue = itemView.findViewById(R.id.amountValue);
            customerName = itemView.findViewById(R.id.customerName);
            requestType = itemView.findViewById(R.id.requestType);
            salesManName = itemView.findViewById(R.id.salesManName);
            lineardetail= itemView.findViewById(R.id.lineardetail);
            linearCheckInfo= itemView.findViewById(R.id.linearCheckInfo);

        }

        public String convertToArabic(String value) {
            String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
            Log.e("convertToArabic", value + "      " + newValue);
            return newValue;
        }

//        public void showImageOfCheck(Bitmap bitmap) {
////            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCancelable(true);
//            dialog.setContentView(R.layout.show_image);
//            photoDetail= (PhotoView) dialog.findViewById(R.id.image_check);
////          final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
//            photoDetail.setImageBitmap(bitmap);
//            dialog.show();
//        }

        public void showDetails(String total) {
            isListUpdated=false;
            Log.e("checkState", "" + requestState+"\t"+total);
//            new LocaleAppUtils().changeLayot(context);
            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.request_detail);
            dialog.show();
            LinearLayout linearresone,linear_buttons,VouchernoLin,total_lin,amount_lin,showitemsLin;
            LinearLayout linearLayout = dialog.findViewById(R.id.mainLinearDetail);
            linear_buttons  = dialog.findViewById(R.id.linearButtons);
            TextView showitems,textnote,textTime,textDate,total_voucher,voucherNo,textAmountNo,requestType,customer_name,Sales_name,titleNote;

            LinearLayout rowNote,rowcompany;
            textnote=dialog.findViewById(R.id.textnote);
            rowNote=dialog.findViewById(R.id.rowNote);
            VouchernoLin=dialog.findViewById(R.id.VouchernoLin);
            textTime=dialog.findViewById(R.id.textTime);//
            textDate= dialog.findViewById(R.id.textDate);
            total_voucher=dialog.findViewById(R.id.total_voucher);
            total_lin=dialog.findViewById(R.id.total_lin);
            amount_lin= dialog.findViewById(R.id.amount_lin);
            voucherNo = dialog.findViewById(R.id.voucherNo);
            textAmountNo= dialog.findViewById(R.id.textAmountNo);
            requestType = dialog.findViewById(R.id.requestType);
            customer_name = dialog.findViewById(R.id.customer_name);
            Sales_name= dialog.findViewById(R.id.Sales_name);
            titleNote= dialog.findViewById(R.id.titleNote);
            showitems= dialog.findViewById(R.id.showitems);
            showitemsLin= dialog.findViewById(R.id.showitemsLin);
            //*************** fill Data *********************************************
            if(requestList.get(row_index).getRequest_type().equals("5"))
            {total_lin.setVisibility(View.GONE);
                amount_lin.setVisibility(View.GONE);
              requestType.setText(context.getResources().getString(R.string.log_in_cus_outrange));
              VouchernoLin.setVisibility(View.GONE);
                rowNote.setVisibility(View.GONE);
            }
            if(requestList.get(row_index).getRequest_type().equals("506"))
            {total_lin.setVisibility(View.GONE);
                amount_lin.setVisibility(View.GONE);
                requestType.setText(context.getResources().getString(R.string.returnvoch_per));
                VouchernoLin.setVisibility(View.GONE);
                rowNote.setVisibility(View.GONE);
            }

            Sales_name.setText(requestList.get(row_index).getSalesman_name());
            customer_name.setText(requestList.get(row_index).getCustomer_name());
            if(requestList.get(row_index).getRequest_type().equals("1"))
            {
                requestType.setText(context.getResources().getString(R.string.totalDiscount));
                textAmountNo.setText(requestList.get(row_index).getAmount_value()+ " % ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            if(requestList.get(row_index).getRequest_type().equals("10"))
            {
                requestType.setText(context.getResources().getString(R.string.totalDiscount));
                textAmountNo.setText(requestList.get(row_index).getAmount_value()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            else if(requestList.get(row_index).getRequest_type().equals("3"))
            {
                requestType.setText(context.getResources().getString(R.string.totalBonus));
                textAmountNo.setText(requestList.get(row_index).getAmount_value()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            else
            if(requestList.get(row_index).getRequest_type().equals("0"))
            {
                requestType.setText(context.getResources().getString(R.string.itemDiscount));
                try {
                    typeDiscountItem=Integer.parseInt(requestList.get(row_index).getNote().substring(0,1));
                }
                catch (Exception e)
                {
                    typeDiscountItem=1;

                }

                if(typeDiscountItem==1)
                {
                    textAmountNo.setText(requestList.get(row_index).getAmount_value()+"\t%");
                }
                else {
                    textAmountNo.setText(requestList.get(row_index).getAmount_value()+"\tJD");
                }
                titleNote.setText(context.getResources().getString(R.string.itemName));
            }
            else
            if(requestList.get(row_index).getRequest_type().equals("2"))
            {
                requestType.setText(context.getResources().getString(R.string.itemBonus));
                textAmountNo.setText(requestList.get(row_index).getAmount_value()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.itemName));
            }
            else
            if(requestList.get(row_index).getRequest_type().equals("100"))
            {
                requestType.setText(context.getResources().getString(R.string.OverflowDebtlimit));
                textAmountNo.setText( context.getResources().getString(R.string.limit)+"\t"+requestList.get(row_index).getAmount_value()+"\tJD");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }

            else if (requestList.get(row_index).getRequest_type().equals("506")) {
                requestType.setText(context.getResources().getString(R.string.returnvoch_per));

            }

            voucherNo.setText(requestList.get(row_index).getVoucher_no());
            Log.e("total_voucher",""+requestList.get(row_index).getTotal_voucher());
            total_voucher.setText(total);
            textDate.setText(requestList.get(row_index).getDate());
            textTime.setText(requestList.get(row_index).getTime());
            if(requestList.get(row_index).getNote().equals(""))
            {
                rowNote.setVisibility(View.GONE);
            }
            if(requestList.get(row_index).getRequest_type().equals("0"))
            {
                String note=requestList.get(row_index).getNote();
                textnote.setText(note.substring(1,note.length()));
            }
            else {
                textnote.setText(requestList.get(row_index).getNote());
            }
            if(requestList.get(row_index).getRequest_type().equals("506"))showitemsLin.setVisibility(View.VISIBLE);
       else showitemsLin.setVisibility(View.GONE);

            showitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.showitemsdailog);
                    dialog.show();
                   ListView rec= dialog.findViewById(R.id.rec);

                    String[] splited = requestList.get(row_index).getNote().split(";");
                    List<String> list = Arrays.asList(splited);

                    List<String> Itemsnamelist =new ArrayList<>();
                 Pricelist =new ArrayList<>();

                    for(int i=0;i<list.size();i++) {
                        if(i%2==0)
                        Itemsnamelist.add(list.get(i) + "");
                        else
                        Pricelist.add(list.get(i) + "");


                    }
                    for(int i=0;i<Itemsnamelist.size();i++)
                    Log.e("Pricelist==",Pricelist.get(i)+" , "+Itemsnamelist.get(i));

                    ReturnItemsAdapter itemsAdapter  = new ReturnItemsAdapter(context, Itemsnamelist);

                    rec.setAdapter(itemsAdapter);
                }
            });

            final Button reject = (Button) dialog.findViewById(R.id.RejectButton);
            final Button accept = (Button) dialog.findViewById(R.id.AcceptButton);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestState = "1";
                    updateRequestState(requestList.get(row_index),requestList.get(row_index).getRowId(),requestState);
                    dialog.dismiss();



                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestState = "2";
                    updateRequestState(requestList.get(row_index),requestList.get(row_index).getRowId(),requestState);
                    dialog.dismiss();



                }
            });


        }
    }

    private void updateRequestState(RequstTest request, String rowId, String state) {
        Log.e("updateRequestState",""+isListUpdated);



//        request.setRowId(rowId);
        request.setStatus(state);
        databaseHandler=new DataBaseHandler(context);
//        Log.e("rowId",rowId);
        Log.e("state",state+"");
        Log.e("state", request.getStatus()+"  "+ request.getRowId());

        FirebaseDatabase dbroot = FirebaseDatabase.getInstance();
        databaseReference = dbroot.getReference(RequstTest.class.getSimpleName());

        databaseReference.keepSynced(true);//Keeping Data Fresh
        DaoRequsts daoRequsts=new DaoRequsts(context);

        daoRequsts.addRequst(request);

//        if(    settingModel.getImport_way().equals("0"))
//            exportData.updateRowState(rowId,state);
//        else   if(    settingModel.getImport_way().equals("1"))
//            exportData.IIs_updateRowSeen( arrayList);




    }
}
