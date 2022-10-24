package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminvansales.model.Flag_SettingssSaleMenApp;
import com.example.adminvansales.model.SettingSalesMenApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class AddSettingForSalesMen extends AppCompatActivity {
    CheckBox checkBox, checkBox2;
    String salman_selection="";
    public  String SalmanNo;
    static public TextView mainTextView;
    RadioGroup taxCalc, printMethod;
    int amountOfmaxDiscount = 0;
    RadioButton bluetooth, wifi, exclude, include;
    EditText linkEditText,ip_withPort, cono,numOfCopy, invoicEditText, returnEditText, orderEditText, paymentEditTextCash, paymentEditTextCheque, paymentEditTextCredit,
            salesmanNmae,valueTotalDiscount,storNo_text;
    CheckBox allowMinus, salesManCustomersOnly, minSalePrice, allowOutOfRange;
    CheckBox can_change_price_Returnonly,checkBox_canChangePrice, readDiscount, workOnline, paymetod_check, bonusNotAlowed, noOfferForCredit, customerAuthor,
            passowrdData_checkbox, hideQty_checkbox, lockcash_checkbox, preventNew_checkbox, note_checkbox, ttotalDisc_checkbox, automaticCheck_checkbox, tafqit_checkbox, preventChange_checkbox,
            showCustomerList_checkbox, noReturn_checkbox, workSerial_checkbox,
            showItemImage_checkbox,approveAdmin_checkbox,asaveOnly_checkbox,showSolidQty_checkbox,offerFromAdmin_checkbox,checkQtyServer,dontShowTax_checkbox
            ,continousReading_checkbox,totalDiscount_checkbox,itemUnit_checkBox,dontDuplicateItems_checkbox
            ,sumCurentQty_checkbox,salesoffers_checkbox,checkqtyinorder_checkbox,locationtracker_checkbox,aqaba_tax_exemption_checBox,showCustomerLocation_checBox,Items_unit_checBox,EndTripReport_checBox;
    private AutoCompleteTextView salmanTv;
    ArrayAdapter<String> salesNameSpinnerAdapter;
    private TextInputLayout salman_textInput;
    GlobelFunction globelFunction;
    Button okButton,cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_setting_for_sales_men);
        LinearLayout linearMain=findViewById(R.id.linearMain);
        init();
        globelFunction=new GlobelFunction(AddSettingForSalesMen.this);
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




        okButton = (Button) findViewById(R.id.okBut);
      cancelButton = (Button) findViewById(R.id.cancelBut);


        //getSalesManName();
        invoicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    int vouchNo=Integer.parseInt(charSequence.toString());

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        returnEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {


                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        orderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {


                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        salmanTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                salman_selection = (String) parent.getItemAtPosition(position);
                SalmanNo = globelFunction.getsalesmanNum(salman_selection);


                Log.e("salman_selection==", salman_selection);
                salman_textInput.setError(null);
                salman_textInput.clearFocus();






            }
        });







        noOfferForCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (noOfferForCredit.isChecked()) {
                    openMaxDiscount();
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                saveSetting();


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(AddSettingForSalesMen.this,HomeActivity.class));

            }
        });



    }
     void  init(){
         salmanTv = findViewById(R.id.salemanTv);
         sumCurentQty_checkbox= (CheckBox) findViewById(R.id.sumCurentQty_checkbox);
         dontDuplicateItems_checkbox= (CheckBox) findViewById(R.id.dontDuplicateItems_checkbox);
         continousReading_checkbox = (CheckBox) findViewById(R.id.continousReading_checkbox);
         itemUnit_checkBox= (CheckBox) findViewById(R.id.itemUnit_checkbox);
         totalDiscount_checkbox = (CheckBox) findViewById(R.id.totalDiscount_checkbox);
         valueTotalDiscount = (EditText)findViewById(R.id.valueTotalDiscount);
         salman_textInput = findViewById(R.id.saleman_textInput);



         numOfCopy = (EditText) findViewById(R.id.num_of_copy);
         invoicEditText = (EditText) findViewById(R.id.invoice_serial);
         returnEditText = (EditText) findViewById(R.id.return_serial);
         orderEditText = (EditText) findViewById(R.id.order_serial);
         paymentEditTextCash = (EditText) findViewById(R.id.payments_serial_cash);
         paymentEditTextCheque = (EditText) findViewById(R.id.payments_serial_cheque);
         paymentEditTextCredit = (EditText) findViewById(R.id.payments_serial_creditCard);

         taxCalc = (RadioGroup) findViewById(R.id.taxTalc);

         checkBox = (CheckBox) findViewById(R.id.price_by_cust);
         checkBox2 = (CheckBox) findViewById(R.id.use_weight_case);
         printMethod = (RadioGroup) findViewById(R.id.printMethod);
         bluetooth = (RadioButton) findViewById(R.id.bluetoothRadioButton);
         wifi = (RadioButton) findViewById(R.id.wifiRadioButton);
         allowMinus = (CheckBox) findViewById(R.id.allow_sale_with_minus);
         salesManCustomersOnly = (CheckBox) findViewById(R.id.salesman_customers_only);
         minSalePrice = (CheckBox) findViewById(R.id.min_sale_price);
         allowOutOfRange = (CheckBox) findViewById(R.id.allow_cust_check_out_range);
         exclude = (RadioButton) findViewById(R.id.excludeRadioButton);
         include = (RadioButton) findViewById(R.id.includeRadioButton);
         checkBox_canChangePrice = (CheckBox) findViewById(R.id.can_change_price);
         can_change_price_Returnonly = (CheckBox) findViewById(R.id.can_change_price_Returnonly);
         readDiscount = (CheckBox) findViewById(R.id.read_discount);
         workOnline = (CheckBox) findViewById(R.id.work_online);
         paymetod_check = (CheckBox) findViewById(R.id.checkBox_paymethod_check);
         bonusNotAlowed = (CheckBox) findViewById(R.id.checkBox_bonus_notallowed);
         noOfferForCredit        = (CheckBox) findViewById(R.id.checkBox_NoOffer_forCredit);
         customerAuthor          = (CheckBox) findViewById(R.id.CustomerAuthorize_checkbox);
         passowrdData_checkbox   = (CheckBox) findViewById(R.id.PassowrdData_checkbox);

         hideQty_checkbox     = (CheckBox) findViewById(R.id.hideQty_checkbox);
         lockcash_checkbox    = (CheckBox) findViewById(R.id.lockcash_checkbox);
         preventNew_checkbox   = (CheckBox)findViewById(R.id.preventNewOrder_checkbox);
         note_checkbox         = (CheckBox) findViewById(R.id.note_checkbox);
         ttotalDisc_checkbox   = (CheckBox) findViewById(R.id.preventtotalDisc_checkbox);
         automaticCheck_checkbox   = (CheckBox) findViewById(R.id.automatic_cheque_checkbox);
         tafqit_checkbox            = (CheckBox) findViewById(R.id.tafqit_checkbox);
         preventChange_checkbox     = (CheckBox) findViewById(R.id.preventChangePay_checkbox);
         showCustomerList_checkbox = (CheckBox) findViewById(R.id.showCustomerList_checkbox);
         noReturn_checkbox            = (CheckBox) findViewById(R.id.noReturn_checkbox);
         workSerial_checkbox         = (CheckBox) findViewById(R.id.workSerial_checkbox);
         showItemImage_checkbox        = (CheckBox) findViewById(R.id.showItemImage_checkbox);
         approveAdmin_checkbox= (CheckBox) findViewById(R.id.approveAdmin_checkbox);
         asaveOnly_checkbox= (CheckBox) findViewById(R.id.asaveOnly_checkbox);
         showSolidQty_checkbox= (CheckBox) findViewById(R.id.showSolidQty_checkbox);
         offerFromAdmin_checkbox= (CheckBox) findViewById(R.id.offerFromAdmin_checkbox);
         checkQtyServer= (CheckBox) findViewById(R.id.qtyFromServer_checkbox);
         dontShowTax_checkbox= (CheckBox) findViewById(R.id.dontShowTax_checkbox);
         salesoffers_checkbox= (CheckBox) findViewById(R.id.salesoffers);
         checkqtyinorder_checkbox= (CheckBox) findViewById(R.id.checkqtyinorder);
         locationtracker_checkbox= (CheckBox) findViewById(R.id.locationtracker);
         aqaba_tax_exemption_checBox= (CheckBox) findViewById(R.id.aqaba_tax_exemption_checBox);
         showCustomerLocation_checBox= (CheckBox) findViewById(R.id.showCustomerLocation_checBox);
         Items_unit_checBox= (CheckBox) findViewById(R.id.Items_unit);
         EndTripReport_checBox= (CheckBox) findViewById(R.id.EndTripReport_checBox);
     }
    public void openMaxDiscount() {
        Log.e("openMaxDiscount", "yes");
        final Dialog dialog = new Dialog(AddSettingForSalesMen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.max_discount_credit);

        final EditText amount = (EditText) dialog.findViewById(R.id.amount_discount_cridit);
        Button okButton = (Button) dialog.findViewById(R.id.okBut_discount);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelBut_discount);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amount.getText().toString().equals("")) {
                    amountOfmaxDiscount = Integer.parseInt(amount.getText().toString());
                    Log.e("amountOfmaxDiscount", "" + amountOfmaxDiscount);
                    //  mDbHandler.getAllSettings().get(0).setAmountOfMaxDiscount(amountOfmaxDiscount);
                    dialog.dismiss();
                } else
                    Toast.makeText(AddSettingForSalesMen.this, "Incorrect Input !", Toast.LENGTH_SHORT).show();

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
    private void saveSetting() {

        int numOfCopys=0,invoice=0,return1=0,order=0,paymentCash=0,paymentCheque=0,paymentCredit=0;
        String storeNo="1";


            if ((!numOfCopy.getText().toString().equals("")) && !invoicEditText.getText().toString().equals("") && !returnEditText.getText().toString().equals("") &&
                    !orderEditText.getText().toString().equals("") && !paymentEditTextCash.getText().toString().equals("")
                    && !paymentEditTextCheque.getText().toString().equals("")
                    && !paymentEditTextCredit.getText().toString().equals("")) {

//                           if(validSerial&&validOrder&&validReturn)
//                           {
                if (Integer.parseInt(numOfCopy.getText().toString()) < 5) {

                    try {
                        numOfCopys = Integer.parseInt(numOfCopy.getText().toString());
                        invoice = Integer.parseInt(invoicEditText.getText().toString()) - 1;
                        return1 = Integer.parseInt(returnEditText.getText().toString()) - 1;
                        order = Integer.parseInt(orderEditText.getText().toString()) - 1;
                        paymentCash = Integer.parseInt(paymentEditTextCash.getText().toString()) - 1;
                        paymentCheque = Integer.parseInt(paymentEditTextCheque.getText().toString()) - 1;
                        paymentCredit = Integer.parseInt(paymentEditTextCredit.getText().toString()) - 1;

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(AddSettingForSalesMen.this, "Invalid Input Number", Toast.LENGTH_SHORT).show();
                        Log.e("SettingException",""+e.getMessage());

                    }


                    int taxKind = taxCalc.getCheckedRadioButtonId() == R.id.excludeRadioButton ? 0 : 1;
                    int pprintMethod = printMethod.getCheckedRadioButtonId() == R.id.bluetoothRadioButton ? 0 : 1;
                    int priceByCust = checkBox.isChecked() ? 1 : 0;
                    int useWeightCase = checkBox2.isChecked() ? 1 : 0;
                    int alowMinus = allowMinus.isChecked() ? 1 : 0;
                    int salesManCustomers = salesManCustomersOnly.isChecked() ? 1 : 0;
                    int minSalePric = minSalePrice.isChecked() ? 1 : 0;
                    int alowOutOfRange = allowOutOfRange.isChecked() ? 1 : 0;
                    int canChangPrice = checkBox_canChangePrice.isChecked() ? 1 : 0;
                    int canChangPrice_Returnonly = can_change_price_Returnonly.isChecked() ? 1 : 0;
                    int readDiscountFromoffer = readDiscount.isChecked() ? 1 : 0;
                    int workOnlin = workOnline.isChecked() ? 1 : 0;
                    int paymethodCheck = paymetod_check.isChecked() ? 1 : 0;
                    int bonusNotalow = bonusNotAlowed.isChecked() ? 1 : 0;
                    int noOffer_Credit = noOfferForCredit.isChecked() ? 1 : 0;
                    int Customerauthorized = customerAuthor.isChecked() ? 1 : 0;
                    int passordData = passowrdData_checkbox.isChecked() ? 1 : 0;

                    int hideqty = hideQty_checkbox.isChecked() ? 1 : 0;
                    int lockcashReport = lockcash_checkbox.isChecked() ? 1 : 0;
                    int preventOrder = preventNew_checkbox.isChecked() ? 1 : 0;
                    int requiredNote = note_checkbox.isChecked() ? 1 : 0;
                    int totalDiscPrevent = ttotalDisc_checkbox.isChecked() ? 1 : 0;
                    int automaticCheque = automaticCheck_checkbox.isChecked() ? 1 : 0;
                    int tafqitCheckbox = tafqit_checkbox.isChecked() ? 1 : 0;
                    int preventChangPay = preventChange_checkbox.isChecked() ? 1 : 0;
                    int showCustlist = showCustomerList_checkbox.isChecked() ? 1 : 0;
                    int noReturnInvoice = noReturn_checkbox.isChecked() ? 1 : 0;

                    int workSerial = workSerial_checkbox.isChecked() ? 1 : 0;
                    int showImage=showItemImage_checkbox.isChecked()?1:0;
                    int approveAdm=approveAdmin_checkbox.isChecked()?1:0;
                    int saveOnly=asaveOnly_checkbox.isChecked()?1:0;
                    int showsolidQty= showSolidQty_checkbox.isChecked()?1:0;
                    int offerAdmin= offerFromAdmin_checkbox.isChecked()?1:0;
                    int qtyServer=checkQtyServer.isChecked()?1:0;
                    int continousReading=continousReading_checkbox.isChecked()?1:0;
                    int activeTotalDisc=totalDiscount_checkbox.isChecked()?1:0;
                    int item_unit=itemUnit_checkBox.isChecked()?1:0;

                    int sumCurren_Qty=sumCurentQty_checkbox.isChecked()?1:0;
                    int dontDuplicat_Item=dontDuplicateItems_checkbox.isChecked()?1:0;
                    int salesoffersflage= salesoffers_checkbox.isChecked()?1:0;
                    int checkqtyinorderflage= checkqtyinorder_checkbox.isChecked()?1:0;
                    int locationtrackerflage= locationtracker_checkbox.isChecked()?1:0;
                    int aqapaTax_value=aqaba_tax_exemption_checBox.isChecked()?1:0;
                    int Items_unitflage=   Items_unit_checBox.isChecked()?1:0;;
                    int  EndTripReportflage=   EndTripReport_checBox.isChecked()?1:0;


                    double valueOfTotDisc=0;
                    try {
                        valueOfTotDisc=Double.parseDouble(valueTotalDiscount.getText().toString().trim());
                    }catch (Exception e){
                        valueOfTotDisc=0;
                    }

                    Log.e("valueOfTotDisc","addSetting00"+valueOfTotDisc);


                    int showTax=dontShowTax_checkbox.isChecked()?1:0;

                    Log.e("showsolidQty",""+showsolidQty);


                    int showLocation=showCustomerLocation_checBox.isChecked()?1:0;


                    SettingSalesMenApp settingSalesMenApp=new SettingSalesMenApp();

                    settingSalesMenApp.setTaxClarcKind(taxKind);
                    settingSalesMenApp.setSaleVochserial(invoice);
                    settingSalesMenApp.setReturnVochserial(return1);
                    settingSalesMenApp.setOrderVochserial(order);
                    settingSalesMenApp.setPriceByCust(priceByCust);
                    settingSalesMenApp.setUseWeightCase(useWeightCase);
                    settingSalesMenApp.setAllowMinus(alowMinus);
                    settingSalesMenApp.setNumOfCopy(numOfCopys);
                    settingSalesMenApp.setSalesManCustomers(salesManCustomers);
                    settingSalesMenApp.setMinSalePric(minSalePric);
                    settingSalesMenApp.setPrintMethod(pprintMethod);
                    settingSalesMenApp.setAllowOutOfRange(alowOutOfRange);
                    ///



                    settingSalesMenApp.setPaymentCashserial(paymentCash);
                    settingSalesMenApp.setPaymentChequeserial(paymentCheque);
                    settingSalesMenApp.setPaymentCreditserial(paymentCredit);

                    settingSalesMenApp.setCanChangePrice(canChangPrice);
                    settingSalesMenApp.setCanChangePrice_returnonly(canChangPrice_Returnonly);
                    settingSalesMenApp.setReadDiscountFromOffers(readDiscountFromoffer);
                    settingSalesMenApp.setWorkOnline(workOnlin);
                    settingSalesMenApp.setPaymethodCheck(paymethodCheck);
                    settingSalesMenApp.setBonusNotAlowed(bonusNotalow);
                    settingSalesMenApp.setNoOffer_for_credit(noOffer_Credit);
                    settingSalesMenApp.setAmountOfMaxDiscount(amountOfmaxDiscount);
                    settingSalesMenApp.setCustomer_authorized(Customerauthorized);
                    settingSalesMenApp.setPassowrd_data(passordData);
                     ///
                    settingSalesMenApp.setHide_qty(hideqty);
                    settingSalesMenApp.setLock_cashreport(lockcashReport);
                    settingSalesMenApp.setPriventOrder(preventOrder);
                    settingSalesMenApp.setRequiNote(requiredNote);
                    settingSalesMenApp.setPreventTotalDisc(totalDiscPrevent);
                    settingSalesMenApp.setAutomaticCheque(automaticCheque);
                    settingSalesMenApp.setTafqit(tafqitCheckbox);
                    settingSalesMenApp.setPreventChangPayMeth(preventChangPay);
                    settingSalesMenApp.setShowCustomerList(showCustlist);
                    settingSalesMenApp.setNoReturnInvoice(noReturnInvoice);
                    ///
                    settingSalesMenApp.setWork_serialNo(workSerial);
                    settingSalesMenApp.setShowItemImage(showImage);
                    settingSalesMenApp.setApproveAdmin(approveAdm);
                    settingSalesMenApp.setSaveOnly(saveOnly);
                    settingSalesMenApp.setShow_quantity_sold(showsolidQty);
                    settingSalesMenApp.setReadOfferFromAdmin(offerAdmin);

                    settingSalesMenApp.setQtyServer(qtyServer);
                    settingSalesMenApp.setDontShowtax(showTax);

                    ///
                    settingSalesMenApp.setContinusReading(continousReading);
                    settingSalesMenApp.setActiveTotalDiscount(activeTotalDisc);
                    settingSalesMenApp.setValueOfTotalDiscount(valueOfTotDisc);

                    settingSalesMenApp.setItemUnit(item_unit);
                    settingSalesMenApp.setSumCurrentQty(sumCurren_Qty);
                    settingSalesMenApp.setDontduplicateItem(dontDuplicat_Item);
                    settingSalesMenApp.setOffersJustForSales(salesoffersflage);


                    ///
                    settingSalesMenApp.setCheckQtyinOrder(checkqtyinorderflage);
                    settingSalesMenApp.setLocationtracker(locationtrackerflage);
                    settingSalesMenApp.setAqapaTax(aqapaTax_value);

                    settingSalesMenApp.setShowCustomerLocation(showLocation);
                    settingSalesMenApp.setItems_Unit(Items_unitflage);
                    settingSalesMenApp.setEndTripReport(EndTripReportflage);



//                    mDbHandler.addSetting(link, taxKind,     506, return1,     priceByCust, useWeightCase, alowMinus, numOfCopys, salesManCustomers, minSalePric, pprintMethod, alowOutOfRange, canChangPrice, canChangPrice_Returnonly,readDiscountFromoffer, workOnlin, paymethodCheck, bonusNotalow, noOffer_Credit, amountOfmaxDiscount,Customerauthorized,passordData,arabicLanguage,hideqty,lockcashReport,salesmanname,preventOrder,requiredNote,totalDiscPrevent,automaticCheque,tafqitCheckbox,preventChangPay,showCustlist,noReturnInvoice,workSerial,showImage,approveAdm,saveOnly,showsolidQty,offerAdmin,linkIp,qtyServer,showTax,conoText,continousReading,activeTotalDisc,valueOfTotDisc,storeNo,item_unit,sumCurren_Qty,dontDuplicat_Item,salesoffersflage,checkqtyinorderflage,locationtrackerflage,aqapaTax_value,showLocation,Items_unitflage,EndTripReportflage);
//                    mDbHandler.addSetting(link, taxKind,     508, order,       priceByCust, useWeightCase, alowMinus, numOfCopys, salesManCustomers, minSalePric, pprintMethod, alowOutOfRange, canChangPrice, canChangPrice_Returnonly,readDiscountFromoffer, workOnlin, paymethodCheck, bonusNotalow, noOffer_Credit, amountOfmaxDiscount,Customerauthorized,passordData,arabicLanguage,hideqty,lockcashReport,salesmanname,preventOrder,requiredNote,totalDiscPrevent,automaticCheque,tafqitCheckbox,preventChangPay,showCustlist,noReturnInvoice,workSerial,showImage,approveAdm,saveOnly,showsolidQty,offerAdmin,linkIp,qtyServer,showTax,conoText,continousReading,activeTotalDisc,valueOfTotDisc,storeNo,item_unit,sumCurren_Qty,dontDuplicat_Item,salesoffersflage,checkqtyinorderflage,locationtrackerflage,aqapaTax_value,showLocation,Items_unitflage,EndTripReportflage);
//                    /*cash*/mDbHandler.addSetting(link, taxKind  ,    1    ,    paymentCash, priceByCust, useWeightCase, alowMinus, numOfCopys, salesManCustomers, minSalePric, pprintMethod, alowOutOfRange, canChangPrice,canChangPrice_Returnonly, readDiscountFromoffer, workOnlin, paymethodCheck, bonusNotalow, noOffer_Credit, amountOfmaxDiscount,Customerauthorized,passordData,arabicLanguage,hideqty,lockcashReport,salesmanname,preventOrder,requiredNote,totalDiscPrevent,automaticCheque,tafqitCheckbox,preventChangPay,showCustlist,noReturnInvoice,workSerial,showImage,approveAdm,saveOnly,showsolidQty,offerAdmin,linkIp,qtyServer,showTax,conoText,continousReading,activeTotalDisc,valueOfTotDisc,storeNo,item_unit,sumCurren_Qty,dontDuplicat_Item,salesoffersflage,checkqtyinorderflage,locationtrackerflage,aqapaTax_value,showLocation,Items_unitflage,EndTripReportflage);
//                    /*chequ*/mDbHandler.addSetting(link, taxKind  ,     4,       paymentCheque, priceByCust, useWeightCase, alowMinus, numOfCopys, salesManCustomers, minSalePric, pprintMethod, alowOutOfRange, canChangPrice, canChangPrice_Returnonly,readDiscountFromoffer, workOnlin, paymethodCheck, bonusNotalow, noOffer_Credit, amountOfmaxDiscount,Customerauthorized,passordData,arabicLanguage,hideqty,lockcashReport,salesmanname,preventOrder,requiredNote,totalDiscPrevent,automaticCheque,tafqitCheckbox,preventChangPay,showCustlist,noReturnInvoice,workSerial,showImage,approveAdm,saveOnly,showsolidQty,offerAdmin,linkIp,qtyServer,showTax,conoText,continousReading,activeTotalDisc,valueOfTotDisc,storeNo,item_unit,sumCurren_Qty,dontDuplicat_Item,salesoffersflage,checkqtyinorderflage,locationtrackerflage,aqapaTax_value,showLocation,Items_unitflage,EndTripReportflage);
//                    /*credit card*/mDbHandler.addSetting(link, taxKind   , 2,         paymentCredit, priceByCust, useWeightCase, alowMinus, numOfCopys, salesManCustomers, minSalePric, pprintMethod, alowOutOfRange, canChangPrice, canChangPrice_Returnonly,readDiscountFromoffer, workOnlin, paymethodCheck, bonusNotalow, noOffer_Credit, amountOfmaxDiscount,Customerauthorized,passordData,arabicLanguage,hideqty,lockcashReport,salesmanname,preventOrder,requiredNote,totalDiscPrevent,automaticCheque,tafqitCheckbox,preventChangPay,showCustlist,noReturnInvoice,workSerial,showImage,approveAdm,saveOnly,showsolidQty,offerAdmin,linkIp,qtyServer,showTax,conoText,continousReading,activeTotalDisc,valueOfTotDisc,storeNo,item_unit,sumCurren_Qty,dontDuplicat_Item,salesoffersflage,checkqtyinorderflage,locationtrackerflage,aqapaTax_value,showLocation,Items_unitflage,EndTripReportflage);









                }
                else
                {
                    Toast.makeText(AddSettingForSalesMen.this, "Number of copies must be maximum 4 !", Toast.LENGTH_SHORT).show();

                }



            }
            else {
                Toast.makeText(AddSettingForSalesMen.this, "Please enter All Information Filed", Toast.LENGTH_SHORT).show();
            }


    }
    public void fillSalesManSpinner() {

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, salesManNameList);

        salmanTv.setAdapter(salesNameSpinnerAdapter);

    }
}