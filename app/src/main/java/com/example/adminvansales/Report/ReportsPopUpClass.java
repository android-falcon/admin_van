package com.example.adminvansales.Report;

import static com.example.adminvansales.GlobelFunction.salesManInfoAdmin;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.adminvansales.AddCommissionTarget;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.HomeActivity;
import com.example.adminvansales.ItemReport;
import com.example.adminvansales.LogIn;
import com.example.adminvansales.MainActivity;
import com.example.adminvansales.R;
import com.example.adminvansales.addSalesmanTarget;

public class ReportsPopUpClass {

    public void showPopupWindow(final View view, Context context) {

        GlobelFunction globelFunction = new GlobelFunction(context);
        //Create a View object yourself through inflater
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = LayoutInflater.from(context).inflate(R.layout.reports_dialog_box, null, false);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setElevation(10);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);

        //Initialize the elements of our window, install the handler

        TextView paymentReport, customerLogReport, cashReport, unCollectedCheques,
                itemsReport, plansReport,RequestReport,TargetReport,CommissionTargetReport;

        paymentReport = popupView.findViewById(R.id.paymentReport);
        customerLogReport = popupView.findViewById(R.id.customerLogReport);
        cashReport = popupView.findViewById(R.id.cashReport);
        unCollectedCheques = popupView.findViewById(R.id.unCollectedChequesReport);
        itemsReport = popupView.findViewById(R.id.itemsReport);
        plansReport = popupView.findViewById(R.id.plansReport);
        TargetReport= popupView.findViewById(R.id.TargetReport);
        RequestReport= popupView.findViewById(R.id.RequestReport);
        RequestReport.setVisibility(View.GONE);
        TargetReport.setVisibility(View.GONE);
        CommissionTargetReport= popupView.findViewById(R.id.CommissionTargetReport);
        CommissionTargetReport.setVisibility(View.GONE);
        paymentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if (salesManInfoAdmin.getPaymentReport() == 1) {
                    Intent locationIntent = new Intent(context, PaymentDetailsReport.class);
                    context.startActivity(locationIntent);
                } else {
                    globelFunction.AuthenticationMessage();

                }
            }
        });

        customerLogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getCustomerReport()==1) {
                    Intent locationIntent = new Intent(context, CustomerLogReport.class);
                    context.startActivity(locationIntent);
                }else {
                    globelFunction. AuthenticationMessage();

                }

            }
        });

        cashReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getCashReport()==1) {
                    Intent locationIntent = new Intent(context, CashReport.class);
                    context.startActivity(locationIntent);

                }else {
                    globelFunction.AuthenticationMessage();

                }
            }
        });

        unCollectedCheques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globelFunction.setValidation();
                if(salesManInfoAdmin.getUnCollectChequeReport()==1) {
                    Intent intent = new Intent(context, UnCollectedData.class);
                    context.startActivity(intent);
                }else {
                    globelFunction.   AuthenticationMessage();

                }
            }
        });

        itemsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemReport.class);
                context.startActivity(intent);

            }
        });

        if(LogIn.hideRawahne==1)
        {plansReport.setVisibility(View.GONE);}
        plansReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlansReport.class);
                context.startActivity(intent);

            }
        });
        RequestReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RequstReport.class);
                context.startActivity(intent);

            }
        });
        TargetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LogIn.goldenfalcon==1)
                {      Intent intent = new Intent(context, CommissionTargetReport.class);
                    context.startActivity(intent);
                }
                else

                {
                    Intent intent = new Intent(context, TargetReport.class);
                    context.startActivity(intent);
                }


            }
        });
        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
        CommissionTargetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(context, com.example.adminvansales.Report.CommissionTargetReport.class);
                context. startActivity(locationIntent);
            }
        });
    }


}
