package com.example.adminvansales.Adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.R;
import com.example.adminvansales.model.OfferGroupModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.adminvansales.ImportData.offerGroupModels;
import static com.example.adminvansales.Report.OfferseReport.fillAdapter;

public  class OffersGroupAdapter extends RecyclerView.Adapter<OffersGroupAdapter.OffersGroupViewHolder> {

    private List<OfferGroupModel> list;
    Context context;
    public  static Dialog dialog1;
    Calendar myCalendar;
    private List<OfferGroupModel> AllItems=new ArrayList<>();
    int pos=0;
    final List<OfferGroupModel> offers = new ArrayList<>();
    public OffersGroupAdapter(List<OfferGroupModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offersgroupsrecycler, parent, false);
        return new  OffersGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OffersGroupViewHolder holder, final int position) {
        holder.groupnum.setText(list.get(position).getGroupid());
        holder.fromdate.setText(list.get(position).getFromDate());
        holder.fromdate.setTag(position);
        holder.todate.setText(list.get(position).getToDate());


        holder.dropdown.setVisibility(View.GONE);


        offers.clear();
        for (int i = 0; i < offerGroupModels.size(); i++) {
            if (offerGroupModels.get(i).getGroupid().equals(list.get(position).getGroupid()))
                offers.add(offerGroupModels.get(i));

        }

        OffersDetailAdapter offersDetailAdapter=new OffersDetailAdapter( offers,context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);

        holder.memeber.setLayoutManager(linearLayoutManager);
        holder.memeber.setAdapter(offersDetailAdapter);








        holder .update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          dialog1 = new Dialog(context);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.itemof_offersrecycle);
                dialog1.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog1.getWindow().getAttributes());
                Button save= dialog1.findViewById(R.id.savechangs);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       fillAdapter(context);
                        dialog1.dismiss();

                    }
                });
              EditText  tot=dialog1.findViewById(R.id.tot);
                tot.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                          if(s.length()!=0){

                              updatetotal(list.get(position).getGroupid(), tot.getText().toString().trim() );

                          }
                    }
                });
                Button cancel= dialog1.findViewById(R.id.cancelchangs);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                lp.gravity = Gravity.CENTER;
                dialog1.show();
                AllItems.clear();
                Log.e("size", AllItems.size() + "");
                final RecyclerView Recycl = dialog1.findViewById(R.id.memeberRec);
                for (int i = 0; i < offerGroupModels.size(); i++) {
                    if (offerGroupModels.get(i).getGroupid().equals(list.get(position).getGroupid()))
                        AllItems.add(offerGroupModels.get(i));
                    Recycl.setLayoutManager(new LinearLayoutManager(context));
                    OffersDetailAdapter2 adapter1 = new OffersDetailAdapter2(AllItems, context);
                    Recycl.setAdapter(adapter1);
                }
            }
        });
        holder.totalDec.setText(list.get(position).getDiscount());
        if(list.get(position).ActiveOffers.equals("0"))
        {  holder.activestate.setChecked(false);
            holder.activestate.setBackgroundColor(Color.RED);

        }

        else
        { holder.activestate.setChecked(true);
            holder.activestate.setBackgroundColor(Color.GREEN);

        }
       // holder.activestate

    //child row



        holder. dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter of child


            //    fillAdapter(context);

                if(offers.size()==0)
                {   offers.clear();

                for (int i = 0; i < offerGroupModels.size(); i++) {
                    if (offerGroupModels.get(i).getGroupid().equals(list.get(position).getGroupid()))
                        offers.add(offerGroupModels.get(i));

                }

                OffersDetailAdapter offersDetailAdapter=new OffersDetailAdapter( offers,context);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);

                holder.memeber.setLayoutManager(linearLayoutManager);
                holder.memeber.setAdapter(offersDetailAdapter);
            }
            else{
                Log.e("here==","here");
                    offers.clear();
                    OffersDetailAdapter offersDetailAdapter=new OffersDetailAdapter( offers,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    holder.memeber.setLayoutManager(linearLayoutManager);
                    holder.memeber.setAdapter(offersDetailAdapter);
            }
            }  });



        holder.  fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, openDatePickerDialog(0,position,holder), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                Log.e("fromdate===",list.get(position).getFromDate());
               // OffersGroupViewHolder(holder,position);
            }
        });
        holder.    todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, openDatePickerDialog(1,position,holder), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        holder.activestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("activestate===",holder.activestate.getText().toString());
                if( holder.activestate.getText().equals("ON")) {

                    list.get(position).setActiveOffers("1");

               updateactive(list.get(position).getGroupid(),"1");

                }
                else
                {
                    list.get(position).setActiveOffers("0");
                    updateactive(list.get(position).getGroupid(),"0");

                }
            }
        });


//        holder. totalDec.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().length() != 0) {
//                    try {
//
//
//                      //  ((InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);
//
//                        String newtotal = editable.toString();
//
//                        list.get(position).setDiscount(newtotal);
//
//                        updatetotal(list.get(position).getGroupid(), list.get(position).getDiscount());
//
//                    }
//                    catch (Exception e){}
//
//
//                }
//            }});


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OffersGroupViewHolder extends RecyclerView.ViewHolder{
        TextView groupnum,fromdate,todate,dropdown,   update;
        ToggleButton activestate;
        EditText totalDec;
       RecyclerView memeber;
        public OffersGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            dropdown = itemView.findViewById(R.id.  dropdown );
            groupnum = itemView.findViewById(R.id.groupnum);
            fromdate = itemView.findViewById(R.id.fromdate);
            memeber=itemView.findViewById(R.id.memeber);
            todate = (TextView) itemView.findViewById(R.id.todate);
            totalDec = itemView.findViewById(R.id.totalDec);
            activestate=itemView.findViewById(R.id.activestate);

            update=itemView.findViewById(R.id.   update);



            Log.e("pos====",pos+"");
            activestate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked)
                        buttonView.setBackgroundColor(Color.GREEN);
                    else buttonView.setBackgroundColor(Color.RED);
                }
            });

            myCalendar = Calendar.getInstance();


        }
        }
    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag, final int pos,OffersGroupAdapter.OffersGroupViewHolder holder) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel(flag,pos,holder);
            }

        };
        return date;
    }
    private void updateLabel(int flag,int pos,OffersGroupAdapter.OffersGroupViewHolder holder) {
        String myFormat = "dd/MM/yyyy";
        Log.e("pos====",pos+"");
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
        {
            Log.e("Calendar====",sdf.format(myCalendar.getTime())+"");
            list.get(pos).setFromDate(sdf.format(myCalendar.getTime()));

            updatefromdate(list.get(pos).getGroupid(),list.get(pos).getFromDate());

            //updateoffersGroupAdapter();
        }

        else
        {

            list.get(pos).setToDate(sdf.format(myCalendar.getTime()));

            updatetodate(list.get(pos).getGroupid(),list.get(pos).getToDate());

         //   updateoffersGroupAdapter();
        }
        yourMethodName(holder,pos);

    }
    private void updatefromdate(String id,String fromdate){
Log.e("updatefromdate==","updatefromdate");
        Log.e("id==",id);
        Log.e("fromdate==",fromdate);
        for(int i=0;i<offerGroupModels.size();i++){


if(offerGroupModels.get(i).getGroupid().equals(id))
{  offerGroupModels.get(i).setFromDate(fromdate);

}


        }



    }

    private void updatetodate(String id,String todate){

        Log.e(" updatetodate=="," updatetodate");
        Log.e("id==",id);
        Log.e("todate==",todate);
        for(int i=0;i<offerGroupModels.size();i++){
            if(offerGroupModels.get(i).getGroupid().equals(id))
            {   offerGroupModels.get(i).setToDate(todate);


            }
        }



    }
    private void updateactive(String id,String state){

        Log.e("updateactive","updateactive");
        Log.e("id==",id);
        Log.e("state==",state);
        for(int i=0;i<offerGroupModels.size();i++){
            if(offerGroupModels.get(i).getGroupid().equals(id))
            {   offerGroupModels.get(i).setActiveOffers(state);


            }
        }

    }
    private void updatetotal(String id,String tot){

        Log.e("updateactive","updateactive");
        Log.e("id==",id);
        Log.e("state==",tot);
        for(int i=0;i<offerGroupModels.size();i++){
            if(offerGroupModels.get(i).getGroupid().equals(id))
            {
                offerGroupModels.get(i).setDiscount(tot);


            }
        }

    }

    private void yourMethodName(final OffersGroupAdapter.OffersGroupViewHolder holder,final int position)
    {
        holder.fromdate.setText(list.get(position).getFromDate());
        holder.todate.setText(list.get(position).getToDate());
    }
}

