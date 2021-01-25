package com.example.adminvansales;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.Model.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.adminvansales.ImportData.listId;
import static com.example.adminvansales.MainActivity.isListUpdated;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    //    RecyclerView.Adapter<engineer_adapter.ViewHolder>
    Context context;
    //    List<notification> notificationList;
    List<Request> requestList;
    //    public PhotoView photoView,photoDetail;
    CircleImageView circleImageView;
    Bitmap serverPicBitmap;
    public static int row_index = -1;

    String requestState = "0", amountArabic = "";
    //    LoginINFO infoUser;
//    DatabaseHandler databaseHandler;
    public static String languagelocalApp = "";
    public static String acc = "", bankN = "", branch = "", mobileNo = "";
    int typeDiscountItem=0;


    public RequestAdapter(Context context, List<Request> notifications) {
        this.context = context;
        this.requestList = notifications;
//        databaseHandler=new DatabaseHandler(context);
        Log.e("Requestadapter", "" + notifications.size());


    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        new LocaleAppUtils().changeLayot(context);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_request, viewGroup, false);


        return new RequestAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RequestAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.timeRequest.setText(requestList.get(i).getTime());
        viewHolder.date_request.setText(requestList.get(i).getDate());
        viewHolder.customerName.setText(requestList.get(i).getCustomerName());
        viewHolder.amountValue.setText(requestList.get(i).getAmountValue());
        Log.e("total_voucher", "onBindViewHolder" + requestList.get(i).getTotalVoucher());


        if (requestList.get(i).getRequestType().equals("1"))
        {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalDiscount));
            viewHolder.amountValue.setText( requestList.get(i).getAmountValue()+"\t%");

        }
        else if (requestList.get(i).getRequestType().equals("10")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalDiscount));
            viewHolder.amountValue.setText( requestList.get(i).getAmountValue()+"\tJD");
        }
        else if (requestList.get(i).getRequestType().equals("3")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.totalBonus));
        } else if (requestList.get(i).getRequestType().equals("0")) {
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
                viewHolder.amountValue.setText( requestList.get(i).getAmountValue()+"\t%");
            }
            else {
                viewHolder.amountValue.setText( requestList.get(i).getAmountValue()+"\tJD");
            }

        } else if (requestList.get(i).getRequestType().equals("2")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.itemBonus));
        } else if (requestList.get(i).getRequestType().equals("100")) {
            viewHolder.requestType.setText(context.getResources().getString(R.string.OverflowDebtlimit));
            viewHolder.requestType.setTextColor(context.getResources().getColor(R.color.light_red));
            viewHolder.amountValue.setText( context.getResources().getString(R.string.limit)+"\t"+requestList.get(i).getAmountValue()+"\tJD");
        }


        viewHolder.salesManName.setText(requestList.get(i).getSalesmanName());
        viewHolder.lineardetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = i;
                viewHolder.linearCheckInfo.setBackground(context.getResources().getDrawable((R.drawable.btn_sighn_up)));
                Log.e("showDetails", "" + requestList.get(i).getTotalVoucher());
                viewHolder.showDetails(requestList.get(i).getTotalVoucher());
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeRequest, date_request ,customerName ,amountValue ,requestType, salesManName ;

        ImageView image_check;
        LinearLayout linearCheckInfo, mainLinearAdapter, divider, lineardetail, rowStatus;
        CircleImageView acceptImg, rejectImg, reciveNew, checkimage_state;
        SharedPreferences loginPrefs;

        public ViewHolder(View itemView) {
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
            LinearLayout linearresone,linear_buttons;
            LinearLayout linearLayout = dialog.findViewById(R.id.mainLinearDetail);
            linear_buttons  = dialog.findViewById(R.id.linearButtons);
            TextView textnote,textTime,textDate,total_voucher,voucherNo,textAmountNo,requestType,customer_name,Sales_name,titleNote;

            LinearLayout rowNote,rowcompany;
            textnote=dialog.findViewById(R.id.textnote);
            rowNote=dialog.findViewById(R.id.rowNote);
            textTime=dialog.findViewById(R.id.textTime);//
            textDate= dialog.findViewById(R.id.textDate);
            total_voucher=dialog.findViewById(R.id.total_voucher);
            voucherNo = dialog.findViewById(R.id.voucherNo);
            textAmountNo= dialog.findViewById(R.id.textAmountNo);
            requestType = dialog.findViewById(R.id.requestType);
            customer_name = dialog.findViewById(R.id.customer_name);
            Sales_name= dialog.findViewById(R.id.Sales_name);
            titleNote= dialog.findViewById(R.id.titleNote);

            //*************** fill Data *********************************************
            Sales_name.setText(requestList.get(row_index).getSalesmanName());
            customer_name.setText(requestList.get(row_index).getCustomerName());
            if(requestList.get(row_index).getRequestType().equals("1"))
            {
                requestType.setText(context.getResources().getString(R.string.totalDiscount));
                textAmountNo.setText(requestList.get(row_index).getAmountValue()+ " % ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            if(requestList.get(row_index).getRequestType().equals("10"))
            {
                requestType.setText(context.getResources().getString(R.string.totalDiscount));
                textAmountNo.setText(requestList.get(row_index).getAmountValue()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            else if(requestList.get(row_index).getRequestType().equals("3"))
            {
                requestType.setText(context.getResources().getString(R.string.totalBonus));
                textAmountNo.setText(requestList.get(row_index).getAmountValue()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }
            else
            if(requestList.get(row_index).getRequestType().equals("0"))
            {
                requestType.setText(context.getResources().getString(R.string.itemDiscount));
                typeDiscountItem=Integer.parseInt(requestList.get(row_index).getNote().substring(0,1));
                if(typeDiscountItem==1)
                {
                    textAmountNo.setText(requestList.get(row_index).getAmountValue()+"\t%");
                }
                else {
                    textAmountNo.setText(requestList.get(row_index).getAmountValue()+"\tJD");
                }
                titleNote.setText(context.getResources().getString(R.string.itemName));
            }
            else
            if(requestList.get(row_index).getRequestType().equals("2"))
            {
                requestType.setText(context.getResources().getString(R.string.itemBonus));
                textAmountNo.setText(requestList.get(row_index).getAmountValue()+ " JD ");
                titleNote.setText(context.getResources().getString(R.string.itemName));
            }
            else
            if(requestList.get(row_index).getRequestType().equals("100"))
            {
                requestType.setText(context.getResources().getString(R.string.OverflowDebtlimit));
                textAmountNo.setText( context.getResources().getString(R.string.limit)+"\t"+requestList.get(row_index).getAmountValue()+"\tJD");
                titleNote.setText(context.getResources().getString(R.string.Note));
            }



            voucherNo.setText(requestList.get(row_index).getVoucherNo());
            Log.e("total_voucher",""+requestList.get(row_index).getTotalVoucher());
            total_voucher.setText(total);
            textDate.setText(requestList.get(row_index).getDate());
            textTime.setText(requestList.get(row_index).getTime());
            if(requestList.get(row_index).getNote().equals(""))
            {
                rowNote.setVisibility(View.GONE);
            }
            if(requestList.get(row_index).getRequestType().equals("0"))
            {
                String note=requestList.get(row_index).getNote();
                textnote.setText(note.substring(1,note.length()));
            }
            else {
                textnote.setText(requestList.get(row_index).getNote());
            }



            final Button reject = (Button) dialog.findViewById(R.id.RejectButton);
            final Button accept = (Button) dialog.findViewById(R.id.AcceptButton);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestState = "1";
                    updateRequestState(requestList.get(row_index).getRowId(),requestState);
                    dialog.dismiss();



                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestState = "2";
                    updateRequestState(requestList.get(row_index).getRowId(),requestState);
                    dialog.dismiss();



                }
            });


        }
    }

    private void updateRequestState(String rowId,String state) {
        Log.e("updateRequestState",""+isListUpdated);
        requestList.remove(row_index);
        notifyDataSetChanged();
        ExportData exportData=new ExportData(context);
        exportData.updateRowState(rowId,state);

    }
}
