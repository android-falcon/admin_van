package com.example.adminvansales;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;
import static com.example.adminvansales.ImportData.listAllArea;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlanSalesMan extends AppCompatActivity {
    Spinner salesNameSpinner;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    TextView fromDate, filterAsceding;
    List<String> listAreaName;
    Map<String, List<CustomerInfo>> map;
    public static TextView fillPlan, customerSearch;
    GlobelFunction globelFunction;
    List<String> listSelectedArea = new ArrayList<>();
    String toDay;
    RecyclerView customer_recycler, selectedCustomer_recycler;
    Button saveButton, updatButton, areaButton;
    public static ArrayList<Plan_SalesMan_model> listPlan = new ArrayList<>();
    RadioGroup orderd_typeGroup;
    ImportData importData;
    public ArrayList<CustomerInfo> listCustomer_filtered = new ArrayList<CustomerInfo>();
    public ArrayList<CustomerInfo> listSelectedCustomer = new ArrayList<CustomerInfo>();
    public ArrayList<CustomerInfo> listCustomer_SalesNo = new ArrayList<CustomerInfo>();
    public ArrayList<CustomerInfo> listCustomer_spair = new ArrayList<CustomerInfo>();

    public ArrayList<AreaModel> listOfArea = new ArrayList<>();
    public static int orderType = 0;
    public StringBuilder allAreaPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_sales_man);
        initialView();
        if (salesManInfosList.size() != 0)
            fillSalesManSpinner();
        else {
            globelFunction.getSalesManInfo(this, 4);
        }
        fillArea();
        fillMainList();
        orderType = getTypeOrder();

    }

    private void initialView() {
        importData = new ImportData(this);
        allAreaPlan = new StringBuilder("");
        salesNameSpinner = findViewById(R.id.salesNameSpinner);
        fromDate = findViewById(R.id.from_date_r);
        filterAsceding = findViewById(R.id.filterAsceding);
        filterAsceding.setOnClickListener(v -> {
            sortData(listSelectedCustomer);
            fillSelectedRecycler();
        });
        globelFunction = new GlobelFunction(PlanSalesMan.this);
        toDay = globelFunction.DateInToday();
        fromDate.setText(toDay);
        fromDate.setOnClickListener(onClick);
        fromDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0) {
                    getData();
                }

            }
        });
        customer_recycler = findViewById(R.id.customer_recycler);
        selectedCustomer_recycler = findViewById(R.id.customer_recycler_toOrderd);
        if (listCustomer.size() != 0)
        {
            listCustomer_spair.clear();
            listCustomer_spair.addAll(listCustomer);
            if(salesManInfosList.size()!=0)
            filterbySalesNo();
            fillRecyclerCustomer(listCustomer);
        }
        else {

            importData.IIs_getCustomerInfo(0);
        }
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {

            chechOrder();
//            checkData();
        });
        updatButton = findViewById(R.id.updateButton);
        updatButton.setOnClickListener(v -> {

            getDataToSelectedRecycler();
        });
        areaButton = findViewById(R.id.areaButton);
        areaButton.setOnClickListener(v -> {
            openSelectAreaDialog();
        });
        orderd_typeGroup = findViewById(R.id.orderd_typeGroup);

        fillPlan = findViewById(R.id.fillPlan);
        fillPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    Log.e("afterTextChanged", "rew" + s.toString());
                    if (s.toString().equals("fill")) {
                        fillAllCustomerList();
                        setSelectedDefault();
                        fillCustomerList_Plan();
                        fillMainList();

                        getDataToSelectedRecycler();
                    } else if (s.toString().contains("No Data Found")) {

                        fillAllCustomerList();
                        setSelectedDefault();
                        fillCustomerList_Plan();
                        fillMainList();

                        getDataToSelectedRecycler();


//                        fillAllCustomerList();
//                        setSelectedDefault();
//                        fillMainList();
//
//                        filterbySalesNo();
//                        fillCustomerList_Plan();
//                        getDataToSelectedRecycler();

                    }
                }

            }
        });
        customerSearch = findViewById(R.id.customerSearch);
        customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    if (!s.toString().equals("")) {
                        filterListcustomer(s.toString());
                    } else {
                        fillRecyclerCustomer(listCustomer);
                    }
                }

            }
        });
        orderd_typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.manual_RadioButton) {
                    orderType = 0;
                } else orderType = 1;
                fillSelectedRecycler();
            }
        });
    }

    private void fillAllCustomerList() {
        listCustomer.clear();
        listCustomer.addAll(listCustomer_spair);
    }

    private void chechOrder() {
        Set<Integer> set = new HashSet<Integer>();
        List<Integer> listNotValid = new ArrayList<>();
        for (int i = 0; i < listSelectedCustomer.size(); i++) {
            if (orderType == 0) {
                if (!set.add(listSelectedCustomer.get(i).getOrder())) {
                    listNotValid.add(listSelectedCustomer.get(i).getOrder());
                    listSelectedCustomer.get(i).setDuplicat(1);
                } else listSelectedCustomer.get(i).setDuplicat(0);
            } else {
                listSelectedCustomer.get(i).setOrder(i);
                listSelectedCustomer.get(i).setDuplicat(0);
            }

        }
        if (listNotValid.size() != 0 && orderType == 0) {
            showMessage(this.getString(R.string.haveDuplicateOrder));
            fillSelectedRecycler();
        } else {
            checkData();
        }
    }

    private void showMessage(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }


    private void fillMainList() {
        map = new HashMap<>();
        for (int k = 0; k < listAreaName.size(); k++) {
            List<CustomerInfo> list = new ArrayList<>();
            map.put(listAreaName.get(k), list);
        }
        if (map.size() != 0) {
            for (int i = 0; i < listCustomer.size(); i++) {
                String area = listCustomer.get(i).getAreaName();
                List<CustomerInfo> listCustomerLoc = map.get(area);
                try {
                    listCustomerLoc.add(listCustomer.get(i));
                } catch (Exception e) {
                }


            }
        }
    }


    void fillArea() {
        listAreaName = new ArrayList<>();
        listOfArea.clear();
        listAreaName.addAll(listAllArea);
        for (int i = 0; i < listAreaName.size(); i++) {
            AreaModel areaModel;
            areaModel = new AreaModel();
            areaModel.setAreaId(i);
            areaModel.setAreaName(listAreaName.get(i));
            areaModel.setSelect(0);
            listOfArea.add(areaModel);
        }


    }

    private void openSelectAreaDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.select_area_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        ListView listview_area = dialog.findViewById(R.id.listview_area);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_multiple_choice, listAreaName);
        listview_area.setAdapter(adapter);
        listview_area.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        for (int i = 0; i < listOfArea.size(); i++) {
            if (listOfArea.get(i).getSelect() == 1)
                listview_area.setItemChecked(i, true);
        }

        listview_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                updateListSelectArea(position);

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
        allAreaPlan = new StringBuilder("");

        for (int k = 0; k < listOfArea.size(); k++) {
            if (listOfArea.get(k).getSelect() == 1) {
                listSelectedArea.add(listOfArea.get(k).getAreaName().trim());
                allAreaPlan.append(listOfArea.get(k).getAreaName().trim());
                if (k != listOfArea.size() - 1)
                    allAreaPlan.append(",");
            }

        }
        Log.e("listSelectedArea=","1="+listSelectedArea.size());

        for (int i = 0; i < listSelectedArea.size(); i++) {
//            map
            String area = listSelectedArea.get(i);
            List<CustomerInfo> listCustomerLoc = map.get(area);
            Log.e("listSelectedArea=","listCustomerLoc="+listCustomerLoc.size());
            Log.e("listSelectedArea=","map2="+map.toString());
            listCustomer_filtered.addAll(listCustomerLoc);
        }
        Log.e("listSelectedArea=","2="+listCustomer_filtered.size()+"\tlistCustomer="+listCustomer.size());
        if (listCustomer_filtered.size() != 0)
            fillRecyclerCustomer(listCustomer_filtered);
        else fillRecyclerCustomer(listCustomer);

//        fillArea();
    }

    private void updateListSelectArea(int position) {

        if (listOfArea.get(position).getSelect() == 0)
            listOfArea.get(position).setSelect(1);
        else listOfArea.get(position).setSelect(0);
    }

    private void getDataToSelectedRecycler() {
        ArrayList<CustomerInfo> listNewCustomerSelected = new ArrayList<>();
        for (int i = 0; i < listCustomer.size(); i++) {
            if (listCustomer.get(i).getIsSelected() == 1) {
                listNewCustomerSelected.add(listCustomer.get(i));

            }
        }
        if (listSelectedCustomer.size() == 0 && listPlan.size() == 0) {
            for (int i = 0; i < listNewCustomerSelected.size(); i++) {
                listNewCustomerSelected.get(i).setOrder(i);
            }
        } else {
            if (listNewCustomerSelected.size() >= listSelectedCustomer.size()) {

                for (int i = 0; i < listNewCustomerSelected.size(); i++) {
                    for (int j = 0; j < listSelectedCustomer.size(); j++) {
                        if (listNewCustomerSelected.get(i).getCustomerNumber().equals(listSelectedCustomer.get(j).getCustomerNumber())) {

                            listNewCustomerSelected.get(i).setOrder(listSelectedCustomer.get(j).getOrder());

                        }
                    }
                }
            } else {
                for (int j = 0; j < listSelectedCustomer.size(); j++) {
                    for (int i = 0; i < listNewCustomerSelected.size(); i++) {

                        if (listNewCustomerSelected.get(i).getCustomerNumber().equals(listSelectedCustomer.get(j).getCustomerNumber())) {

                            listNewCustomerSelected.get(i).setOrder(listSelectedCustomer.get(j).getOrder());
                        }
                    }
                }


            }


        }
        listSelectedCustomer.clear();
        listSelectedCustomer.addAll(listNewCustomerSelected);
        fillSelectedRecycler();

    }


    private void fillSelectedRecycler() {
//        sortData(listSelectedCustomer);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(PlanSalesMan.this);
        selectedCustomer_recycler.setLayoutManager(layoutManager);
        SelectedCustomerAdapterPlan adapter = new SelectedCustomerAdapterPlan(listSelectedCustomer, PlanSalesMan.this);
        selectedCustomer_recycler.setAdapter(adapter);
    }

    private void filterListcustomer(String name) {
        listCustomer_filtered.clear();
        for (int i = 0; i < listCustomer.size(); i++) {
            if (listCustomer.get(i).getCustomerName().contains(name)) {
                listCustomer_filtered.add(listCustomer.get(i));
            }

        }
        if (listCustomer_filtered.size() != 0) {
            fillRecyclerCustomer(listCustomer_filtered);

        } else {
            fillRecyclerCustomer(listCustomer);
        }
    }

    private void fillCustomerList_Plan() {
        for (int i = 0; i < listCustomer.size(); i++) {

            for (int k = 0; k < listPlan.size(); k++) {
                orderType = listPlan.get(0).getType_orderd();
                if (listCustomer.get(i).getCustomerNumber().equals(listPlan.get(k).getCustomerNumber())) {
                    listCustomer.get(i).setIsSelected(1);
                    listCustomer.get(i).setOrder(listPlan.get(k).getOrderd());
                }
            }
        }
        Log.e("fillCustomerList",""+listCustomer.size());
        filterbySalesNo();
//        fillRecyclerCustomer(listCustomer);
        refreshOrderType();


    }

    private void refreshOrderType() {
        if (orderType == 0)
            orderd_typeGroup.check(R.id.manual_RadioButton);
        else orderd_typeGroup.check(R.id.byLocation_RadioButton);
    }

    private void checkData() {
        listPlan.clear();
        orderType = getTypeOrder();
        String currentDate = fromDate.getText().toString();
        for (int i = 0; i < listSelectedCustomer.size(); i++) {
            if (listSelectedCustomer.get(i).getIsSelected() == 1) {
                Plan_SalesMan_model plan = new Plan_SalesMan_model();
                plan.setPlan_date(currentDate);
                plan.setCustomerName(listSelectedCustomer.get(i).getCustomerName());
                plan.setCustomerNumber(listSelectedCustomer.get(i).getCustomerNumber());
                Log.e("salesNameSpinner", i + "\t" + plan.getCustomerName());
                String sales = salesManInfosList.get((int) salesNameSpinner.getSelectedItemId()).getSalesManNo();
                plan.setSalesNo(sales);
                plan.setOrderd(listSelectedCustomer.get(i).getOrder() + 1);
                plan.setType_orderd(orderType);
                plan.setLatit_customer(listSelectedCustomer.get(i).getLatit_customer());
                plan.setLong_customer(listSelectedCustomer.get(i).getLong_customer());
                plan.setAreaPlan(allAreaPlan.toString());
                listPlan.add(plan);

            }

        }
        if (listPlan.size() != 0) {
            savePlan();
        } else {
            showSelectCustomers();
        }
    }

    public void getData() {
        String salesNum = salesManInfosList.get((int) salesNameSpinner.getSelectedItemId()).getSalesManNo();
        importData.getPlan(salesNum, fromDate.getText().toString(),0);

    }

    private void showSelectCustomers() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(this.getString(R.string.noCustomerSelected))
                .show();
    }

    private void savePlan() {
        Log.e("savePlan","1-");
        ExportData exportData = new ExportData(this);
        exportData.IIs_AddPlan(listPlan, this);
        clearData();
    }

    private void clearData() {
        listPlan.clear();
//        fromDate.setText(toDay);
        orderd_typeGroup.check(R.id.manual_RadioButton);
        fillRecyclerCustomer(listCustomer);
        setSelectedDefault();
        listSelectedCustomer.clear();
        listCustomer_filtered.clear();
        fillSelectedRecycler();
    }

    private void setSelectedDefault() {
        for (int i = 0; i < listCustomer.size(); i++) {
            listCustomer.get(i).setIsSelected(0);
        }
        fillRecyclerCustomer(listCustomer);
    }

    private int getTypeOrder() {
        if (orderd_typeGroup.getCheckedRadioButtonId() == R.id.manual_RadioButton)
            return 0;
        else return 1;
    }

    private void fillRecyclerCustomer(ArrayList<CustomerInfo> listCustomer) {

        if (listCustomer.size() != 0) {
            final LinearLayoutManager layoutManager;
            layoutManager = new LinearLayoutManager(PlanSalesMan.this);
            customer_recycler.setLayoutManager(layoutManager);
            CustomerAdapter adapter = new CustomerAdapter(listCustomer, PlanSalesMan.this);
            customer_recycler.setAdapter(adapter);
//            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
//            itemTouchHelper.attachToRecyclerView(customer_recycler);

        }}

    private void filterbySalesNo() {
        listCustomer_SalesNo.clear();
        String sales = salesManInfosList.get((int) salesNameSpinner.getSelectedItemId()).getSalesManNo();

        for(int i=0;i<listCustomer.size();i++){
            if(listCustomer.get(i).getSalesNo().trim().equals(sales.trim()))
            {
                listCustomer_SalesNo.add(listCustomer.get(i));
            }
        }

            replaceShortListCustomer();

        Log.e("listCustomer_SalesNo",""+listCustomer_SalesNo.size()+"\t"+listCustomer.size());

    }

    private void replaceShortListCustomer() {
        if(listCustomer_SalesNo.size()!=0)
        {

            listCustomer.clear();
            listCustomer.addAll(listCustomer_SalesNo);
        }else {
            listCustomer.clear();
            listCustomer.addAll(listCustomer_spair);

        }

        fillRecyclerCustomer(listCustomer);

    }


    public void fillSalesManSpinner() {
        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);
        salesNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String salesNum = salesManInfosList.get(position).getSalesManNo();
                Log.e("onItemSelected",""+salesNum);
                importData.getPlan(salesNum, fromDate.getText().toString(),0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {

                case R.id.from_date_r:
                    globelFunction.DateClick(fromDate);
                    break;


            }

        }
    };

    private void sortData(ArrayList<CustomerInfo> listCustomer) {
        Collections.sort(listCustomer, new Comparator<CustomerInfo>() {
            @Override
            public int compare(CustomerInfo objec1, CustomerInfo objec2) {
                return objec1.getOrder() - (objec2.getOrder());
            }
        });

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(listSelectedCustomer, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            // recyclerView.getAdapter().notify();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}