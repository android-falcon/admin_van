package com.example.adminvansales.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.DataBaseHandler;
import com.example.adminvansales.ExportData;
import com.example.adminvansales.Interface.CustomerDao;
import com.example.adminvansales.R;
import com.example.adminvansales.model.NewAddedCustomer;
import com.example.adminvansales.model.RequstTest;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.adminvansales.LogIn.contextG;
import static com.example.adminvansales.MainActivity.isListUpdated;

public class AddedCustomerAdapter  extends RecyclerView.Adapter<AddedCustomerAdapter.ViewHolder> {
    Context context;
    private DatabaseReference databaseReference,databaseReference2;
    List<NewAddedCustomer> requestList;
    DataBaseHandler databaseHandler;
    public static String languagelocalApp = "";
    public static String acc = "", bankN = "", branch = "", mobileNo = "";
    int typeDiscountItem=0;

    public AddedCustomerAdapter(List<NewAddedCustomer> itemsList, Context context) {
        this.requestList = itemsList;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addedcus_row, parent, false);

        return new AddedCustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder . timeRequest.setText(requestList.get(i).getDate()+"");
        holder. date_request.setText(requestList.get(i).getTime()+"");
        holder.  customerName.setText(requestList.get(i).getCustName()+"");
        holder.    salesManName .setText(requestList.get(i).getSalesMan()+"");
        holder.   lineardetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                New_openAddCustomerDialog(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView timeRequest, date_request ,customerName ,salesManName ;
     LinearLayout   lineardetail;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            timeRequest = itemView.findViewById(R.id.timeRequest);
            date_request = itemView.findViewById(R.id.date_request);
            customerName = itemView.findViewById(R.id.customerName);
            salesManName = itemView.findViewById(R.id.salesManName);
            lineardetail= itemView.findViewById(R.id.lineardetail);


        }

        public String convertToArabic(String value) {
            String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
            Log.e("convertToArabic", value + "      " + newValue);
            return newValue;
        }

        }


    public void New_openAddCustomerDialog(int i) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.added_customersdaiolg);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);

        Window window = dialog.getWindow();


        final EditText addCus = (EditText) dialog.findViewById(R.id.custEditText);
        final EditText remark = (EditText) dialog.findViewById(R.id.remarkEditText);
        final EditText address = (EditText) dialog.findViewById(R.id.addressEditText);
        final EditText telephone = (EditText) dialog.findViewById(R.id.phoneEditText);
        final EditText contactPerson = (EditText) dialog.findViewById(R.id.person_contactEditText);
        final EditText MarketName = (EditText) dialog.findViewById(R.id.MarketName);
        addCus.setText(requestList.get(i).getCustName()+"");
        remark.setText(requestList.get(i).getRemark()+"");
        address.setText(requestList.get(i).getADRESS_CUSTOMER()+"");
        telephone.setText(requestList.get(i).getTELEPHONE()+"");
        contactPerson.setText(requestList.get(i).getCONTACT_PERSON()+"");
        MarketName.setText(requestList.get(i).getMarketName()+"");




        Button AcceptButton = (Button) dialog.findViewById(R.id.AcceptButton);
        Button RejectButton = (Button) dialog.findViewById(R.id.RejectButton);

        LinearLayout   linear = dialog.findViewById(R.id.linear);
        try {
            if (languagelocalApp.equals("ar")) {
                linear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch (Exception e){
            linear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);}





        AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addCus.getText().toString().equals("")) {
                    int payMethod = 0;


                    CustomerDao customerDao = new CustomerDao(context);


                    NewAddedCustomer addedCustomer = new NewAddedCustomer(
                            addCus.getText().toString(),
                            remark.getText().toString(),
                            requestList.get(i).getLatitude()+""
                            ,    requestList.get(i).getLongtitude()+"",
                            requestList.get(i).getSalesmanNo(),
                            requestList.get(i).getSalesMan(),
                            "0",
                            address.getText().toString(),
                            telephone.getText().toString(),
                            contactPerson.getText().toString(),
                            MarketName.getText().toString(),
                            requestList.get(i).getDate(),
                            requestList.get(i).getTime());

                    dialog.dismiss();

                    ExportData importJason =new ExportData(context);
                    ArrayList<NewAddedCustomer>arrayList=new ArrayList<>();
                    arrayList.clear();
                    arrayList.add(addedCustomer);
                    importJason.startExportCustomer(arrayList, context);


                } else {
                    addCus.setError(context.getResources().getString(R.string.reqired_filled));
                    Toast.makeText(context, "Please add customer name", Toast.LENGTH_SHORT).show();

                }
            }

        });

        RejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerDao customerDao=new CustomerDao(contextG);
                customerDao.deleteRequst( requestList.get(i).getTELEPHONE());
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
