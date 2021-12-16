package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;



import com.example.adminvansales.databinding.ActivityGroupOfferBinding;
import com.example.adminvansales.databinding.RowOfferListAdapterBinding;
import com.example.adminvansales.model.ItemMaster;
import com.example.adminvansales.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GroupOffer extends AppCompatActivity {
    ListView itemList;
    ImportData importData;
    GlobelFunction globelFunction;
    TextView fromDate,toDate,listNo,selectItem,selectCustomer,cancelList;
    public  static  TextView addList;
    ActivityGroupOfferBinding myBinding;
    ItemGroupOfferAdapter itemGroupAdapter;
    public  static  ArrayList<OfferGroupModel> listOfferItem;
    RadioGroup discountTypeGroup;
    int discType=0;
    private OfferGroupModel offerGroupModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_group_offer);
        myBinding=DataBindingUtil.setContentView(this,R.layout.activity_group_offer);
//        offerGroupModel.discount=myBinding.



        initial();
    }

    private void initial() {
       // offerGroupModel = new OfferGroupModel();
        listOfferItem=new ArrayList<>();
        globelFunction =new GlobelFunction(GroupOffer.this);
        toDate=findViewById(R.id.toDate);
        addList=findViewById(R.id.addList);
        fromDate=findViewById(R.id.fromDate);
        myBinding.setFromDate(globelFunction.DateInToday());
        myBinding.setToDate(globelFunction.DateInToday());
       myBinding.fromDateTextView.setOnClickListener(onClickListener);
       myBinding.toDateTextView.setOnClickListener(onClickListener);
        myBinding.addList.setOnClickListener(onClickListener);
        importData=new ImportData(GroupOffer.this);
        importData.getItemCard(GroupOffer.this,2);
        discountTypeGroup=findViewById(R.id.discountType);
      // filList();
    }

    public void filList() {
        Log.e("getItemCard","flag="+listOfferItem.size());

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(GroupOffer.this);
        myBinding.itemListV.setLayoutManager(layoutManager);
        itemGroupAdapter = new ItemGroupOfferAdapter(GroupOffer.this, listOfferItem);

        myBinding.itemListV.setAdapter(itemGroupAdapter)        ;

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.fromDate_textView:
                    globelFunction.DateClick(myBinding.fromDateTextView);
                    break;
                case R.id.toDate_textView:
                    globelFunction.DateClick(myBinding.toDateTextView);
                    break;
                case R.id.selectItem:
                    //dialogShow();

                    break;
                case R.id.selectCustomer:
                    //ShowSearchCustomerDialog();
                    break;
                case R.id.addList:
                    save();
                    break;
                case R.id.cancelList:
                    finish();
                    break;
            }
        }
    };

    private void save() {
       if( validateData()){
           addList.setEnabled(false);
           discType=getDiscType();
         //  Log.e("save","discType"+discType);
         //  Log.e("listOfferItem",""+listOfferItem.size());
           for(int i=0; i<listOfferItem.size();i++)
           {
               if(!listOfferItem.get(i).qtyItem.equals("0"))
               {
                   listOfferItem.get(i).fromDate=myBinding.fromDateTextView.getText().toString().trim();
                   listOfferItem.get(i).toDate=myBinding.toDateTextView.getText().toString().trim();
                   listOfferItem.get(i).discount=myBinding.totalDiscopuntValue.getText().toString().trim();
                   listOfferItem.get(i).discountType=discType+"";
                  // Log.e("listOfferItem",""+listOfferItem.get(i).discountType);
                  // Log.e("listOfferItem",""+listOfferItem.get(i).discount);
               }
               else {
                   listOfferItem.remove(i);
                   i--;
               }

           }
           if(listOfferItem.size()!=0)
           {
               saveOffer(listOfferItem);
           }
           else {

               new SweetAlertDialog(GroupOffer.this, SweetAlertDialog.WARNING_TYPE)
                       .setTitleText("fill your List !!!!")
                       .setContentText("")

                       .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sweetAlertDialog) {

                               sweetAlertDialog.dismissWithAnimation();
                               addList.setEnabled(true);
                               importData.getItemCard(GroupOffer.this,2);

                           }
                       })
                       .show();



           }


       }




    }

    private void saveOffer(ArrayList<OfferGroupModel> listOfferItem) {

        JSONArray jsonArray  =getArrayJson(listOfferItem);
        Log.e("jsonArraySerial","get4="+jsonArray);
        ExportData export =new ExportData(GroupOffer.this);
        export.addOfferGroupItem(GroupOffer.this,jsonArray);
    }

    private JSONArray getArrayJson(ArrayList<OfferGroupModel> listOfferItem) {

        JSONArray jsonArraySerial = new JSONArray();
        Gson gson = new Gson();
//        String json = gson.toJson(myObj);
        try {

          //  Log.e("serialModelList",""+listOfferItem.size());
            for (int i = 0; i < listOfferItem.size(); i++)
            {
                Log.e("jsonArraySerial","get="+listOfferItem.get(i));
                if(!listOfferItem.get(i).qtyItem.equals("0")){
                    jsonArraySerial.put(listOfferItem.get(i).getJsonObject());
                  //  jsonArraySerial.put(gson.toJson(listOfferItem.get(i)));
                }
                Log.e("jsonArraySerial","get="+jsonArraySerial.get(i));
            }
            Log.e("jsonArraySerial","get3="+jsonArraySerial);


        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){}
        return  jsonArraySerial;
    }

    private int getDiscType() {
        if(discountTypeGroup.getCheckedRadioButtonId()==R.id.percentRadio){
            return 1;
        }
        else return 0;
    }

    private boolean validateData() {
        if (!myBinding.fromDateTextView.getText().toString().equals(""))
        {
            if (!myBinding.toDateTextView.getText().toString().equals(""))
            {
                if (myBinding.totalDiscopuntValue.getText().toString().trim().length()!=0)
                {

                    return true;
                }else {
                    myBinding.totalDiscopuntValue.setError("Empty");
                    return  false;
                }


            }else {
                myBinding.toDateTextView.setError("Empty");
                return  false;
            }
        }else {
            myBinding.fromDateTextView.setError("Empty");
            return  false;
        }

    }

    public void clearList() {
        listOfferItem.clear();
        myBinding.totalDiscopuntValue.setText("");
        addList.setEnabled(true);
        finish();


    }
}
