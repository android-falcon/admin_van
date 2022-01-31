package com.example.adminvansales;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.example.adminvansales.ImportData.listCustomerInfo;

import static java.lang.Math.max;
import static java.lang.Math.min;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.Adapters.CustomerAdapter;
import com.example.adminvansales.Adapters.SelectedCustomerAdapterPlan;
import com.example.adminvansales.Report.CashReport;
import com.example.adminvansales.model.AreaModel;
import com.example.adminvansales.model.CustomerInfo;
import com.example.adminvansales.model.Plan_SalesMan_model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlanSalesMan extends AppCompatActivity {
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;
    TextView fromDate;
    List<String> listAreaName;
    Map<String, List<CustomerInfo>> map;
    public  static TextView  fillPlan,customerSearch;
    GlobelFunction globelFunction;
    List<String> listSelectedArea=new ArrayList<>();
    String toDay;
    RecyclerView customer_recycler,selectedCustomer_recycler;
    Button saveButton,updatButton,areaButton;
    public  static  ArrayList<Plan_SalesMan_model> listPlan=new ArrayList<>();
    RadioGroup orderd_typeGroup;
    ImportData importData;
    public  ArrayList<CustomerInfo> listCustomer_filtered = new ArrayList<CustomerInfo>();
    public  ArrayList<CustomerInfo> listSelectedCustomer = new ArrayList<CustomerInfo>();

    public  ArrayList<AreaModel>listOfArea=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_sales_man);
        initialView();
        fillSalesManSpinner();
        fillArea();
        fillMainList();

    }

    private void initialView() {
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        fromDate=findViewById(R.id.from_date_r);
        globelFunction=new GlobelFunction(PlanSalesMan.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        fromDate.setOnClickListener(onClick);
        customer_recycler=findViewById(R.id.customer_recycler);
        selectedCustomer_recycler=findViewById(R.id.customer_recycler_toOrderd);
        fillRecyclerCustomer(listCustomer);
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v->{

            checkData();
        });
        updatButton=findViewById(R.id.updateButton);
        updatButton.setOnClickListener(v->{

            getDataToSelectedRecycler();
        });
        areaButton=findViewById(R.id.areaButton);
        areaButton.setOnClickListener(v -> {
            openSelectAreaDialog();
        });
        orderd_typeGroup=findViewById(R.id.orderd_typeGroup);
        importData=new ImportData(this);
        fillPlan=findViewById(R.id.fillPlan);
        fillPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0)
                {
                    Log.e("afterTextChanged",""+s.toString());
                    if(s.toString().equals("fill"))
                    {
                        setSelectedDefault();

                        fillCustomerList();
                    }else if(s.toString().contains("No Data Found")){
                    setSelectedDefault();

                    }
                }

            }
        });
        customerSearch=findViewById(R.id.customerSearch);
        customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0){
                    if(!s.toString().equals(""))
                    {
                        filterListcustomer(s.toString());
                    }else {
                        fillRecyclerCustomer(listCustomer);
                    }
                }

            }
        });

    }

    private void fillMainList() {

       map = new HashMap<>();
        for (int k=0;k<listAreaName.size();k++){
            List<CustomerInfo> list = new ArrayList<>();
            map.put(listAreaName.get(k),list);
        }
        Log.e("fillMainList","map="+map.size());
        for(int i=0;i<listCustomer.size();i++){

            String area=listCustomer.get(i).getAreaName();
            List<CustomerInfo> listCustomerLoc = map.get(area);
            Log.e("area","1=="+area+"\t"+listCustomerLoc.size());
            listCustomerLoc.add(listCustomer.get(i));
            Log.e("area","2=="+area+"\t"+listCustomerLoc.size());



        }
        for (Map.Entry<String, List<CustomerInfo>> entry : map.entrySet()) {
            List<CustomerInfo> listCustomerLoc22 = entry.getValue();
            Log.e("finalmap=","getKey="+entry.getKey());
            Log.e("finalmap=","listCustomerLoc="+listCustomerLoc22.size());
        }
    }

    void fillArea(){
        listOfArea.clear();
        listAreaName=new ArrayList<>();
        AreaModel areaModel ;
        areaModel =new AreaModel();
        areaModel.setAreaId(1);
        areaModel.setAreaName("العبدلي");
        areaModel.setSelect(0);
        listAreaName.add(areaModel.getAreaName());

        listOfArea.add(areaModel);
        areaModel =new AreaModel();
        areaModel.setAreaId(2);
        areaModel.setAreaName("جبل الحسين");
        areaModel.setSelect(0);

        listOfArea.add(areaModel);
        listAreaName.add(areaModel.getAreaName());
        areaModel =new AreaModel();
        areaModel.setAreaId(3);
        areaModel.setAreaName("ماركا");
        areaModel.setSelect(0);

        listOfArea.add(areaModel);
        listAreaName.add(areaModel.getAreaName());

    }
    private void openSelectAreaDialog() {


        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.select_area_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        ListView listview_area=dialog.findViewById(R.id.listview_area);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_multiple_choice,  listAreaName);
        listview_area.setAdapter(adapter);
        listview_area.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        for(int i=0;i<listOfArea.size();i++)
        {
            if(listOfArea.get(i).getSelect()==1)
                listview_area.setItemChecked(i,true);
        }

        listview_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub

                updateListSelectArea(position);
Log.e("onItemClick",""+position);

            }
        });


        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
        TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterListLocation();

                dialog.dismiss();


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void filterListLocation() {
        listSelectedArea.clear();
        listCustomer_filtered.clear();

        for (int k=0;k<listOfArea.size();k++) {
            if (listOfArea.get(k).getSelect() == 1) {


            Log.e("listSelectedArea", "" + listOfArea.get(k).getAreaName());
                listSelectedArea.add( listOfArea.get(k).getAreaName().trim() );
        }

        }
        for(int i=0;i<listSelectedArea.size();i++){
//            map
            String area=listSelectedArea.get(i);
            List<CustomerInfo> listCustomerLoc = map.get(area);
            listCustomer_filtered.addAll(listCustomerLoc);
        }
        if(listCustomer_filtered.size()!=0)
        fillRecyclerCustomer(listCustomer_filtered);
        else fillRecyclerCustomer(listCustomer);
        Log.e("listOfArea","allArea="+listCustomer_filtered.size());
//        fillArea();
    }

    private void updateListSelectArea(int position) {

        if(listOfArea.get(position).getSelect()==0)
            listOfArea.get(position).setSelect(1);
        else  listOfArea.get(position).setSelect(0);
        Log.e("listOfArea",""+listOfArea.get(position).getSelect()+"\t"+listOfArea.get(position).getAreaName());
    }

    private void getDataToSelectedRecycler() {
        // ArrayList<CustomerInfo> listNewCustomerSelected=getDifference(listCustomer,listSelectedCustomer);
//        listSelectedCustomer.clear();
        ArrayList<CustomerInfo> listNewCustomerSelected = new ArrayList<>();
        for (int i = 0; i < listCustomer.size(); i++) {
            if (listCustomer.get(i).getIsSelected() == 1) {
                listNewCustomerSelected.add(listCustomer.get(i));

            }
        }
       if( listSelectedCustomer.size()==0&&listPlan.size()==0){
           for(int i=0;i<listNewCustomerSelected.size();i++){
               listNewCustomerSelected.get(i).setOrder(i);
        }
       }else {
           if (listNewCustomerSelected.size() >= listSelectedCustomer.size()) {

               for (int i = 0; i < listNewCustomerSelected.size(); i++) {
                   for (int j = 0; j < listSelectedCustomer.size(); j++) {
                       if (listNewCustomerSelected.get(i).getCustomerNumber().equals(listSelectedCustomer.get(j).getCustomerNumber())) {

                           listNewCustomerSelected.get(i).setOrder(listSelectedCustomer.get(j).getOrder());
                           Log.e("listNewCustomerSelected", "1=" + i + listNewCustomerSelected.get(i).getCustomerNumber());
//                   if(j<listNewCustomerSelected.size())
//                    Collections.swap(listNewCustomerSelected, i, j);
                           Log.e("listNewCustomerSelected", "2=" + i + listNewCustomerSelected.get(i).getCustomerNumber());
                       }
                   }
               }
           }
           else {
               for (int j = 0; j < listSelectedCustomer.size(); j++) {
                   for (int i = 0; i < listNewCustomerSelected.size(); i++) {

                       if (listNewCustomerSelected.get(i).getCustomerNumber().equals(listSelectedCustomer.get(j).getCustomerNumber())) {

                           listNewCustomerSelected.get(i).setOrder(listSelectedCustomer.get(j).getOrder());
                           Log.e("listNewCustomerSelected", "1=" + i + listNewCustomerSelected.get(i).getCustomerNumber());
//                        if(j<listNewCustomerSelected.size())
//                            Collections.swap(listNewCustomerSelected, i, j);
                           Log.e("listNewCustomerSelected", "2=" + i + listNewCustomerSelected.get(i).getCustomerNumber());
                       }
                   }
               }


           }


       }
        listSelectedCustomer.clear();
        listSelectedCustomer.addAll(listNewCustomerSelected);



//
        fillSelectedRecycler();

    }


    private void fillSelectedRecycler() {
        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(PlanSalesMan.this);
        selectedCustomer_recycler.setLayoutManager(layoutManager);
        SelectedCustomerAdapterPlan adapter = new SelectedCustomerAdapterPlan(listSelectedCustomer, PlanSalesMan.this);
        selectedCustomer_recycler.setAdapter(adapter);
//        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(selectedCustomer_recycler);
    }

    private void filterListcustomer(String name) {
        listCustomer_filtered.clear();
        for (int i=0;i<listCustomer.size();i++){
            if(listCustomer.get(i).getCustomerName().contains(name)){
                listCustomer_filtered.add(listCustomer.get(i));
            }

        }
        Log.e("listCustomer_filtered",""+listCustomer_filtered.size());
        if(listCustomer_filtered.size()!=0){
            fillRecyclerCustomer(listCustomer_filtered);

        }else {
            fillRecyclerCustomer(listCustomer);
        }
    }

    private void fillCustomerList() {
        for(int i=0;i<listCustomer.size();i++)
        {

            for(int k=0;k<listPlan.size();k++){
                if(listCustomer.get(i).getCustomerNumber().equals(listPlan.get(k).getCustomerNumber()))
                {
                    listCustomer.get(i).setIsSelected(1);
                    listCustomer.get(i).setOrder(listPlan.get(k).getOrderd());
                }
            }
        }
        fillRecyclerCustomer(listCustomer);
    }

    private void checkData() {
        listPlan.clear();
        int orderType=getTypeOrder();
        String currentDate=fromDate.getText().toString();
        Log.e("checkData","orderType"+orderType+"\tlistCustomer="+listSelectedCustomer.size()+"listPlan="+listPlan.size());


        for (int i=0;i<listSelectedCustomer.size();i++){
            if(listSelectedCustomer.get(i).getIsSelected()==1)
            {
                Plan_SalesMan_model plan=new Plan_SalesMan_model();
                plan.setPlan_date(currentDate);
                plan.setCustomerName(listSelectedCustomer.get(i).getCustomerName());
                plan.setCustomerNumber(listSelectedCustomer.get(i).getCustomerNumber());
                Log.e("salesNameSpinner",i+"\t"+plan.getCustomerName());
                Log.e("salesNameSpinner","order1=="+listSelectedCustomer.get(i).getOrder());

               String sales= salesManInfosList.get((int) salesNameSpinner.getSelectedItemId()).getSalesManNo();
                plan.setSalesNo(sales);

                plan.setOrderd(listSelectedCustomer.get(i).getOrder());
                Log.e("salesNameSpinner","order2=="+plan.getOrderd());

                plan.setType_orderd(orderType);
                plan.setLatit_customer(listSelectedCustomer.get(i).getLatit_customer());
                plan.setLong_customer(listSelectedCustomer.get(i).getLong_customer());

                listPlan.add(plan);

            }
            
        }
        Log.e("checkData","listPlan"+listPlan.size());
        if(listPlan.size()!=0)
        {
            savePlan();
        }
        else {
            showSelectCustomers();
        }
    }

    private void showSelectCustomers() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(this.getString(R.string.noCustomerSelected))
                .show();
    }

    private void savePlan() {
        //ADMADDPLAN
//       JSONObject= getJsonObjectPlan();
        ExportData exportData =new ExportData(this);
        exportData.IIs_AddPlan(listPlan,this);
        clearData();
    }

    private void clearData() {
        listPlan.clear();
        fromDate.setText(toDay);
        orderd_typeGroup.check(R.id.manual_RadioButton);
        fillRecyclerCustomer(listCustomer);
        setSelectedDefault();
        listSelectedCustomer.clear();
        listCustomer_filtered.clear();
        fillSelectedRecycler();
    }

    private void setSelectedDefault() {
        for(int i=0;i<listCustomer.size();i++){
            listCustomer.get(i).setIsSelected(0);
        }
        fillRecyclerCustomer(listCustomer);
    }

    private int getTypeOrder() {
        if(orderd_typeGroup.getCheckedRadioButtonId()==R.id.manual_RadioButton)
            return 0;
        else return 1;
    }

    private void fillRecyclerCustomer(ArrayList<CustomerInfo> listCustomer) {
        if (listCustomer.size() != 0) {
         //   Log.e("fillRecyclerCustomer", "" + listCustomer.size());
            final LinearLayoutManager layoutManager;
            layoutManager = new LinearLayoutManager(PlanSalesMan.this);
            customer_recycler.setLayoutManager(layoutManager);
            CustomerAdapter adapter = new CustomerAdapter(listCustomer, PlanSalesMan.this);
            customer_recycler.setAdapter(adapter);
//            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
//            itemTouchHelper.attachToRecyclerView(customer_recycler);

        } else {
            fillCustomerLocalTest();

        }
    }
    public  void fillCustomerLocalTest(){
        listCustomer=new ArrayList<>();
        CustomerInfo customerInfo=new CustomerInfo();
        customerInfo.setCustomerName("Ahmed abdulaah");
        customerInfo.setCustomerNumber("1100011122");
        listCustomer.add(customerInfo);
        customerInfo.setCustomerName("عبد الله ");
        customerInfo.setCustomerNumber("1100011122");
        listCustomer.add(customerInfo);
        customerInfo.setCustomerName("mohemmed ali");
        customerInfo.setCustomerNumber("1100011122");
        listCustomer.add(customerInfo);
        customerInfo.setCustomerName("omer ali");
        customerInfo.setCustomerNumber("1100011122");
        listCustomer.add(customerInfo);
        customerInfo.setCustomerName("عبد الله عمر");
        customerInfo.setCustomerNumber("1100011122");
        listCustomer.add(customerInfo);
        Log.e("fillCustomerLocalTest",""+listCustomer.size());

    }


    private void fillSalesManSpinner() {
        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);
        salesNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


             String  salesNum = salesManInfosList.get(position).getSalesManNo();

                Log.e("onItemSelected",""+salesNum);
                importData.getPlan(salesNum,fromDate.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.from_date_r :
                    globelFunction.DateClick(fromDate);
                    break;


            }

        }
    };
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition=viewHolder.getAdapterPosition();
            int toPosition=target.getAdapterPosition();
            Log.e("onMove","fromPosition="+fromPosition+"\t to="+toPosition);
            Collections.swap(listSelectedCustomer,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);

           // recyclerView.getAdapter().notify();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}
//            for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
//
//                String key=entry.getKey();
//                List<Object> listCustomerLoc = entry.getValue();
//                if(key.contains(listCustomer.get(i).getAreaName()))
//                {
//                    listCustomerLoc.add(listCustomer.get(i));
//                }
//              //  Log.e("key","=="+key+"\t"+listCustomerLoc.size());
//
//
//                // Do things with the list
//            }