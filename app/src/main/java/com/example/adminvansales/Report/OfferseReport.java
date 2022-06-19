package com.example.adminvansales.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminvansales.ExportData;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.Adapters.OffersGroupAdapter;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.R;
import com.example.adminvansales.model.OfferGroupModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.adminvansales.ImportData.offerGroupModels;
import com.example.adminvansales.model.LocaleAppUtils;
public class OfferseReport extends AppCompatActivity {
    RecyclerView recyclerView;
    public static TextView offersrespon;
    public static   OffersGroupAdapter offersGroupAdapter;
    ImportData importData;
    public static RecyclerView Recycl;
    EditText from_date_r,to_date_r;
    Calendar myCalendar;
    public static List<OfferGroupModel> offerGroups = new ArrayList<>();

    public static List<OfferGroupModel> dateoffers = new ArrayList<>();

    Button savebtn,PRVIEW;
    private static Spinner GroupidSpinner;
    private static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }

        return dates;
    }
 public static    void updateoffersGroupAdapter(){


        offersGroupAdapter.notifyDataSetChanged();

    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            new LocaleAppUtils().changeLayot(OfferseReport.this);
        setContentView(R.layout.activity_offerse_report);
        init();
            offerGroupModels.clear();
        List<Date> dates = getDates("2012-02-01", "2012-03-01");
       // for(Date date:dates)
         //   System.out.println(date);







        myCalendar = Calendar.getInstance();



        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final String today = df.format(currentTimeAndDate);
        from_date_r.setText(today);
        to_date_r.setText(today);
        from_date_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OfferseReport.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        to_date_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OfferseReport.this, openDatePickerDialog(1), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveOfferList();
                Log.e("finallist",offerGroupModels.size()+"");
                Log.e("finallist",offerGroupModels.toString()+"");
            }
        });
        PRVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateoffers.clear();
                for(int i=0;i<offerGroupModels.size();i++)
                {

//                    if(offerGroupModels.get(i).getFromDate().equals(from_date_r.getText().toString())
//                    &&offerGroupModels.get(i).getToDate().equals(to_date_r.getText().toString())
//                    )

                    if( filters(offerGroupModels.get(i).getFromDate(),offerGroupModels.get(i).getToDate()))

                        if(!GroupidSpinner.getSelectedItem().toString().equals("All")
                    &&offerGroupModels.get(i).getGroupid().equals(GroupidSpinner.getSelectedItem().toString()))

                        {  if(!exists(offerGroupModels.get(i).getGroupid()))
                         dateoffers.add(offerGroupModels.get(i));

                        }


                    else if(GroupidSpinner.getSelectedItem().toString().equals("All")){
                            if(!exists(offerGroupModels.get(i).getGroupid()))
                            {

                                dateoffers.add(offerGroupModels.get(i));
                                Log.e("id===",offerGroupModels.get(i).getGroupid()+"");

                            }
                        }

                }

                Log.e("dateoffers",dateoffers.size()+"");
                Recycl.setLayoutManager(new LinearLayoutManager(OfferseReport.this));
                offersGroupAdapter = new OffersGroupAdapter(dateoffers, OfferseReport.this);
                Recycl.setAdapter(offersGroupAdapter);

            }
        });
        importData.getAllOffers();


    }
    public void saveOfferList(){
        JSONArray jsonArrayList = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        for(int i=0;i<offerGroupModels.size();i++){
            OfferGroupModel offerListModel=new OfferGroupModel();
            offerListModel=offerGroupModels.get(i);
            jsonArrayList.put(offerListModel.getJsonObject());
        }



        ExportData exportData=new ExportData(OfferseReport.this);


            exportData.UpdateGrpupList(OfferseReport.this, jsonArrayList, jsonObject);




    }
    private boolean exists(String groubid) {
        boolean Flage=false;
        for (int j = 0; j < dateoffers.size(); j++) {


            if (dateoffers.get(j).getGroupid().equals(groubid))
            {
                Flage=true;
            break;

            }else{
                Flage=false;
                continue;
            }

        }

   return Flage; }

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(flag);
            }

        };
        return date;
    }
    private void updateLabel(int flag) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
           from_date_r.setText(sdf.format(myCalendar.getTime()));
        else
            to_date_r.setText(sdf.format(myCalendar.getTime()));


       // isDateInBetweenIncludingEndPoints(sdf.format(myCalendar.getTime()),sdf.format(myCalendar.getTime()),);

    }
    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }
    private void init() {
        LinearLayout linearMain=findViewById(R.id.linearMain);
        try{
            if(LogIn.languagelocalApp.equals("ar"))
            {
                linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            else{
                if(LogIn.languagelocalApp.equals("en"))
                {
                    linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
        }
        catch ( Exception e)
        {
            linearMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        GroupidSpinner=findViewById(R.id.  GroupidSpinner);
        PRVIEW=findViewById(R.id.OFFERS_previewButton);
        from_date_r=findViewById(R.id.OFFERS_from_date_r);
  to_date_r=findViewById(R.id.OFFERS_to_date_r);
        savebtn=findViewById(R.id.savebtn);
        Recycl = findViewById(R.id.Recycl);
        importData = new ImportData(OfferseReport.this);
        recyclerView = findViewById(R.id.Recycl);
        offersrespon = findViewById(R.id.respon2);
        offersrespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    if (s.toString().equals("ItemNo")) {
                        Log.e("ItemNo", "ItemNo");

                     fillAdapter(OfferseReport.this);

                    }
                }
            }
        });
    }
    public boolean filters(String Fdate,String Tdate) {

        String fromDate = from_date_r.getText().toString().trim();
        String toDate = to_date_r.getText().toString();

        try {
            if ( (formatDate( Fdate).after(formatDate(fromDate)) || formatDate( Fdate).equals(formatDate(fromDate)) ) &&
                    (formatDate( Tdate).before(formatDate(toDate)) || formatDate( Tdate).equals(formatDate(toDate)))) {

                return true;}
//            else {
//                if (   (formatDate(Fdate).after(formatDate(fromDate)) || formatDate(Fdate).equals(formatDate(fromDate)) ) &&
//                        (formatDate(Fdate).before(formatDate(toDate)) || formatDate(Fdate).equals(formatDate(toDate)))) {
//
//                    return true;}
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Date formatDate(String date) throws ParseException {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d = sdf.parse(date);
        return d;
    }

    private static void fillspinner(Context context) {
  List<String> Groupno=new ArrayList<>();
        Groupno.clear();

        Groupno.add("All");
        for (int i = 0; i < offerGroups.size(); i++) {
            Groupno.add( offerGroups.get(i).getGroupid());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, Groupno);
        GroupidSpinner.setAdapter(adapter);
        GroupidSpinner.setSelection(0);
    }

    public static void fillAdapter(Context context) {
        offerGroups.clear();
        for (int i = 0; i < offerGroupModels.size(); i++) {

            Log.e("offerGr1==", offerGroupModels.get(i).getGroupid() + "");
           // Log.e("offerGr3==", isExists(offerGroupModels.get(i).getGroupid())+ "");
            if (!isExists(offerGroupModels.get(i).getGroupid()))

                offerGroups.add(offerGroupModels.get(i));
        }

        fillspinner(context);
        Log.e("offerGroupModels", offerGroupModels.size() + "");
        Log.e("offerGroups==", offerGroups.size() + "");
        Recycl.setLayoutManager(new LinearLayoutManager(context));
        offersGroupAdapter = new OffersGroupAdapter(offerGroups, context);
        Recycl.setAdapter(offersGroupAdapter);





    }
public  static void updateAdapter(Context context){
    Recycl.setLayoutManager(new LinearLayoutManager(context));
    offersGroupAdapter = new OffersGroupAdapter(offerGroups, context);
    Recycl.setAdapter(offersGroupAdapter);
}
    static boolean isExists(String s) {
        boolean flage= false;

        Log.e("isExists", offerGroupModels.size() + "");
        for (int j = 0; j < offerGroups.size(); j++) {
            if (offerGroups.get(j).getGroupid().equals(s)) {
                flage = true;
                break;

            } else {
                flage = false;
                continue;

            }
        }
        return flage;
    }

}