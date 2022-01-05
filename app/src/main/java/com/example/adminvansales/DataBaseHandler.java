package com.example.adminvansales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.adminvansales.model.Flag_Settingss;
import com.example.adminvansales.model.Account__Statment_Model;
import com.example.adminvansales.model.SettingModel;
import com.example.adminvansales.model.Flag_Settingss;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION =14;
    private static final String BD_NAME = "AdminVanSales_DB";

    // ********************************************************************
    private final String TABLE_IDROW = "TABLE_IDROW";
    private final String ROW_ID = "ROW_ID";


    // ********************************************************************
    private static final String Flag_Settings = "Flag_Settings";
    private static final String Data_Type = "Data_Type";
    private static final String Export_Stock = "Export_Stock";
    private static final String Max_Voucher = "Max_Voucher";
    private static final String Make_Order = "Make_Order";
    private static final String Admin_Password = "Admin_Password";
    private static final String Total_Balance = "Total_Balance";
    private static final String Voucher_Return = "Voucher_Return";




    private final String SETTING_TABLE = "SETTING_TABLE";
    private final String SETTING_IP = "SETTING_IP";
    private final String IMPORT_WAY = "IMPORT_WAY";
    private final String CONO = "COMPANYNUM";
    private final String SETTING_PORT = "SETTING_PORT";
    //*********************************************************************
    private final String ACCOUNT_STATMENT_TABLE="ACCOUNT_STATMENT_TABLE";
    private final String VOUCHERNO="VOUCHERNO";
    private final String TRANSENMAE="TRANSENMAE";
    private final String DATE_VOUCHER="DATE_VOUCHER";
    private final String DEBIT="DEBIT";
    private final String CREDIT="CREDIT";
    private final String BALANCE="BALANCE";
    private final String CUSTOMERNO="CUSTOMERNO";


    public DataBaseHandler(@Nullable Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {

            String createTableSetting = "CREATE TABLE " + SETTING_TABLE
                    + " ("
                    + SETTING_IP + " TEXT ,"
                    + SETTING_PORT + " TEXT ,"
                    +IMPORT_WAY+ " TEXT ,"
                    +CONO+ " TEXT "
                    + ")";
            sqLiteDatabase.execSQL(createTableSetting);
        }
        catch (Exception e)
        {

        }
        try {

            String createTableSetting = "CREATE TABLE " + TABLE_IDROW
                    + " ("
                    + ROW_ID + " TEXT "
                    + ")";
            sqLiteDatabase.execSQL(createTableSetting);
        }
        catch (Exception e)
        {

        }
        try {

            String createTableSetting = "CREATE TABLE " + ACCOUNT_STATMENT_TABLE
                    + " ("
                    + VOUCHERNO + " TEXT, "
                    + TRANSENMAE + " TEXT, "
                    + DATE_VOUCHER + " TEXT, "
                    + DEBIT +       " real, "
                    + CREDIT +      " real, "
                    + BALANCE +     " real, "
                    + CUSTOMERNO +  " TEXT "

                    + ")";
            sqLiteDatabase.execSQL(createTableSetting);
        }
        catch (Exception e)
        {

        }
        try {   String CREATE_TABLE_FlAG_SETTINGS = "CREATE TABLE IF NOT EXISTS " + Flag_Settings + "("
                + Data_Type + " TEXT,"
                + Export_Stock + " INTEGER,"
                + Max_Voucher + " INTEGER,"
                + Make_Order + " INTEGER,"
                + Admin_Password + " INTEGER,"
                + Total_Balance + " INTEGER,"
                + Voucher_Return + " INTEGER" + ")";
            sqLiteDatabase.execSQL(CREATE_TABLE_FlAG_SETTINGS);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            String createTableSetting = "CREATE TABLE " + TABLE_IDROW
                    + " ("
                    + ROW_ID + " TEXT "
                    + ")";
            db.execSQL(createTableSetting);
        }
        catch (Exception e)
        {

        }
        try {

            String createTableSetting = "CREATE TABLE " + ACCOUNT_STATMENT_TABLE
                    + " ("
                    + VOUCHERNO + " TEXT, "
                    + TRANSENMAE + " TEXT, "
                    + DATE_VOUCHER + " TEXT, "
                    + DEBIT + " real, "
                    + CREDIT + " real, "
                    + BALANCE + " real, "
                    + CUSTOMERNO + " TEXT "

                    + ")";
            db.execSQL(createTableSetting);
        }
        catch (Exception e)
        {

        }

        try {

            db.execSQL("ALTER TABLE SETTING_TABLE ADD " + SETTING_PORT + " TEXT"+" DEFAULT ''");

        }catch (Exception e){

        }
        try{
            db.execSQL("ALTER TABLE SETTING_TABLE ADD " + IMPORT_WAY + " TEXT");
        }
        catch (Exception e){

        }
        try{
            db.execSQL("ALTER TABLE SETTING_TABLE ADD " + CONO + " TEXT");
        }
        catch (Exception e){

        }
        try {
            String CREATE_TABLE_FlAG_SETTINGS = "CREATE TABLE IF NOT EXISTS " + Flag_Settings + "("
                    + Data_Type + " TEXT,"
                    + Export_Stock + " INTEGER,"
                    + Max_Voucher + " INTEGER,"
                    + Make_Order + " INTEGER,"
                    + Admin_Password + " INTEGER,"
                    + Total_Balance + " INTEGER,"
                    + Voucher_Return + " INTEGER" + ")";
            db.execSQL(CREATE_TABLE_FlAG_SETTINGS);


        } catch (Exception e) {

        }

    }
    public void addSetting(String settingIp,String port,String importway,String cono) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SETTING_IP, settingIp);
        contentValues.put(SETTING_PORT, port);

        contentValues.put(IMPORT_WAY, importway);
        contentValues.put(CONO, cono);
        database.insert(SETTING_TABLE, null, contentValues);
        database.close();

    }
    public void addRoqId(String rowId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROW_ID, rowId);


        database.insert(TABLE_IDROW, null, contentValues);
        database.close();

    }
    public SettingModel getAllSetting() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + SETTING_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        SettingModel ip = new SettingModel();

        if (cursor.moveToFirst()) {
            do {
                SettingModel settingModel=new SettingModel();
                settingModel.setIpAddress(cursor.getString(0));
                settingModel.setPort(cursor.getString(1));
                settingModel.setCono(cursor.getString(3));
                settingModel.setImport_way(cursor.getString(2));
                ip=settingModel;
            } while (cursor.moveToNext());

        }
        return ip;
    }
    public Account__Statment_Model getCustomerAccount(String acc) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + ACCOUNT_STATMENT_TABLE+" where CUSTOMERNO = '"+acc+"'  ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Account__Statment_Model accountCustomer=null;
        if (cursor.moveToFirst()) {
            do {
                accountCustomer=new Account__Statment_Model();

                accountCustomer.setVoucherNo(cursor.getString(0));
                accountCustomer.setTranseNmae(cursor.getString(1));
                accountCustomer.setDate_voucher(cursor.getString(2));
                try {
                    accountCustomer.setDebit((cursor.getDouble(3)));
                    accountCustomer.setCredit(cursor.getDouble(4));
                    accountCustomer.setBalance(cursor.getDouble(5));
                }
                catch (Exception e)
                { accountCustomer.setDebit(0);
                  accountCustomer.setCredit(0);
                  accountCustomer.setBalance(0);
                  Log.e("Exception_ACCOUNT_STAT","accountCustomer");

                }

                accountCustomer.setCustomerNo(cursor.getString(6));

            } while (cursor.moveToNext());
        }
        return accountCustomer;
    }



    public void addCustomerAccount(Account__Statment_Model  accountData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VOUCHERNO,accountData.getVoucherNo());
        contentValues.put(TRANSENMAE,accountData.getTranseNmae());
        contentValues.put(DATE_VOUCHER,accountData.getDate_voucher());
        contentValues.put(DEBIT,accountData.getDebit());
        contentValues.put(CREDIT,accountData.getCredit());
        contentValues.put(BALANCE,accountData.getBalance());
        contentValues.put(CUSTOMERNO,accountData.getCustomerNo());
        contentValues.put(VOUCHERNO,accountData.getVoucherNo());

        database.insert(ACCOUNT_STATMENT_TABLE, null, contentValues);
        database.close();

    }
    public int getRowId(String rowId) {
        int id=0;
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  ROW_ID FROM  TABLE_IDROW  where ROW_ID ='"+rowId+"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        String row = "";
        if (cursor.moveToFirst()) {
            do {

                row=cursor.getString(0);

               // Log.e("getRowId",""+row);
            } while (cursor.moveToNext());
        }
        if(row.equals(""))
        {
            id=1;
        }
        else {
            id=2;
        }

        return id;
    }
    public void insertFlagSettings(Flag_Settingss flag_settings) {
        SQLiteDatabase db = this.getWritableDatabase();
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(Data_Type, flag_settings.getData_Type());
        values.put(Export_Stock, flag_settings.getExport_Stock());
        values.put(Max_Voucher, flag_settings.getMax_Voucher());
        values.put(Make_Order, flag_settings.getMake_Order());
        values.put(Admin_Password, flag_settings.getAdmin_Password());
        values.put(Total_Balance, flag_settings.getTotal_Balance());
        values.put(Voucher_Return, flag_settings.getVoucher_Return());

        db.insert(Flag_Settings, null, values);
        db.close();

    }
    public List<Flag_Settingss> getFlagSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Flag_Settingss> flagSettings = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Flag_Settings;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Flag_Settingss mySettings = new Flag_Settingss(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6)
                );

                flagSettings.add(mySettings);

            } while (cursor.moveToNext());
            cursor.close();
            Log.e("getFlagSettings", "" + flagSettings.size());
        }
        return flagSettings;

    }

    public void updateFlagSettings (String dataType, int export, int max, int order,
                                    int password, int total, int vReturn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Data_Type, dataType);
        values.put(Export_Stock, export);
        values.put(Max_Voucher, max);
        values.put(Make_Order, order);
        values.put(Admin_Password, password);
        values.put(Total_Balance, total);
        values.put(Voucher_Return, vReturn);

        db.update(Flag_Settings, values, null, null);

        Log.e("Flag Settings", "UPDATE");
        db.close();

    }

    // ********************************************************************
}
