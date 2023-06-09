package com.example.adminvansales;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.adminvansales.model.Account__Statment_Model;
import com.example.adminvansales.model.AnalyzeAccountModel;
import com.example.adminvansales.model.CashReportModel;
import com.example.adminvansales.model.CustomerLogReportModel;
import com.example.adminvansales.model.ItemReportModel;
import com.example.adminvansales.model.ListPriceOffer;
import com.example.adminvansales.model.PayMentReportModel;
import com.example.adminvansales.model.Payment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import static com.example.adminvansales.ImportData.cashReportList;
import static com.example.adminvansales.ImportData.listCustomer;
import static com.itextpdf.text.Element.ALIGN_CENTER;

public class PdfConverter {
    private Context context;
    Document doc;
    File file;
    PdfWriter docWriter = null;
    //    PDFView pdfView;
    File pdfFileName;
    BaseFont base;
    GlobelFunction globelFunction;
    private int directionOfHeader = Element.ALIGN_RIGHT;

    {
        try {
            base = BaseFont.createFont("/assets/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Font arabicFont = new Font(base, 10f);
    Font arabicFontHeader = new Font(base, 11f);


    public PdfConverter(Context context) {
        this.context = context;
        globelFunction = new GlobelFunction(context);
    }

    public File exportListToPdf(List<?> list, String headerDate, String date, int report) {

        PdfPTable pdfPTable = new PdfPTable(1);
        PdfPTable pdfPTableHeader = new PdfPTable(1);
        pdfPTable = getContentTable(list, report);

        pdfPTableHeader = getHeaderTable(list, report, headerDate, date);

        try {
//

            doc.add(pdfPTableHeader);
            doc.add(pdfPTable);
            Toast.makeText(context, context.getString(R.string.export_to_pdf), Toast.LENGTH_LONG).show();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        endDocPdf();

        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                showPdf(pdfFileName);
                Log.v("", "Permission is granted");
            } else {

                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(
                        (Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        } else { // permission is automatically granted on sdk<23 upon
            // installation
            showPdf(pdfFileName);
            Log.v("", "Permission is granted");
        }

        return pdfFileName;

    }

    private PdfPTable getContentTable(List<?> list, int reportType) {
        PdfPTable tableContent = new PdfPTable(1);
        switch (reportType) {
            case 1:
                tableContent = creatTableCustomerLog((List<CustomerLogReportModel>) list);
                break;
            case 2:
                tableContent = createCashReport((List<CashReportModel>) list);
                break;
            case 3:
                tableContent = createPaymentReport((List<PayMentReportModel>) list);
                break;
            case 4:
                tableContent = createListOfferPrice((List<ListPriceOffer>) list);
                break;
            case 5:
                tableContent = createunCollectedReport((List<Payment>) list);
                break;
            case 6:
                tableContent = createAnalyzeAcountReport((List<AnalyzeAccountModel>) list);
                break;
            case 7:
                tableContent = createAccountStatmentReport((List<Account__Statment_Model>) list);
                break;
            case 8:
                tableContent = createItemsReport((List<ItemReportModel>) list);
                break;
            case 9:
               tableContent = createNewCashReport((List<CashReportModel>) list);
                break;

        }
        return tableContent;
    }


    private PdfPTable creatTableCustomerLog(List<CustomerLogReportModel> list) {
        createPDF("CustomerLog_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(8);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getResources().getString(R.string.sales_man_no), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.sales_man_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.CUS_CODE), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.customerName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.CHECK_IN_DATE), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.CHECK_IN_TIME), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.CHECK_OUT_DATE), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.CHECK_OUT_TIME), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getSALES_MAN_ID()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesmanname()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCUS_CODE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCUS_NAME()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCHECK_IN_DATE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCHECK_IN_TIME()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCHECK_OUT_DATE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCHECK_OUT_TIME()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }

    private PdfPTable createCashReport(List<CashReportModel> list) {
        createPDF("Cash_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(10);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.sales_man_no), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.sales_man_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.cash_sale), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit_sales), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.net_sales3), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.paymentCash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.paymentCheque), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.netpayment), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit_value), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.total_cash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        double sum = 0, nettotal = 0, TOTAL_net_salesVal = 0, TOTAL_netpaymentVal = 0, TOTAL_total_cashVal = 0;
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesManNo()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesManName()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTotalCash()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTotalCredite()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getNetSale()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPtotalCash()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPtotalCredite()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getNetPay()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPtotalCrediteCard()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getNetCash()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


            nettotal += Double.parseDouble(list.get(i).getPtotalCredite()) + Double.parseDouble(list.get(i).getPtotalCash());
            TOTAL_net_salesVal += Double.parseDouble(list.get(i).getTotalCash()) + Double.parseDouble(list.get(i).getTotalCredite());
            TOTAL_netpaymentVal += Double.parseDouble(list.get(i).getPtotalCash()) + Double.parseDouble(list.get(i).getPtotalCredite());
            TOTAL_total_cashVal += Double.parseDouble(list.get(i).getTotalCash()) + Double.parseDouble(list.get(i).getPtotalCash());


        }
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(CashReportModel::getTotalCash).mapToDouble(Double::parseDouble).sum()))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(CashReportModel::getTotalCredite).mapToDouble(Double::parseDouble).sum()))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (TOTAL_net_salesVal))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(CashReportModel::getPtotalCash).mapToDouble(Double::parseDouble).sum()))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(CashReportModel::getPtotalCredite).mapToDouble(Double::parseDouble).sum()))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (TOTAL_netpaymentVal))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(CashReportModel::getPtotalCrediteCard).mapToDouble(Double::parseDouble).sum()))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, globelFunction.convertToEnglish(String.format("%.3f", TOTAL_total_cashVal)), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        return pdfPTable;

    }
    private PdfPTable createNewCashReport(List<CashReportModel> list) {
        createPDF("NewCash_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.sales_man_no), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, String.valueOf(list.get(0).getSalesManNo()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        Paragraph paragraph=    new Paragraph(   context.getResources().getString(R.string.sales  ), arabicFont);
        paragraph.setAlignment(ALIGN_CENTER);
        PdfPCell cell9 = new PdfPCell(paragraph );
        cell9.setHorizontalAlignment(ALIGN_CENTER);
        cell9. setBackgroundColor(BaseColor.GRAY);
        cell9.setColspan(2);


        Paragraph paragraph2=    new Paragraph(    context.getResources().getString(R.string.payment  ), arabicFont);
        paragraph.setAlignment(ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(paragraph2 );
        cell.setHorizontalAlignment(ALIGN_CENTER);
        cell. setBackgroundColor(BaseColor.GRAY);
        cell.setColspan(2);

        Paragraph paragraph3=    new Paragraph(    context.getResources().getString(R.string.app_creditCard  ), arabicFont);
        paragraph.setAlignment(ALIGN_CENTER);
        PdfPCell cell3 = new PdfPCell(paragraph3 );
        cell3.setHorizontalAlignment(ALIGN_CENTER);
        cell3. setBackgroundColor(BaseColor.GRAY);
        cell3.setColspan(2);

        insertCell(pdfPTable, context.getString(R.string.sales_man_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, String.valueOf(list.get(0).getSalesManName()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        pdfPTable.addCell(cell9);
        insertCell(pdfPTable, context.getString(R.string.cash_sale), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, String.valueOf(list.get(0).getTotalCash()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit_sales), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, String.valueOf(list.get(0).getTotalCredite()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        insertCell(pdfPTable, context.getResources().getString(R.string.returncash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, list.get(0).getRETURNDCASH()+"", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        insertCell(pdfPTable, context.getResources().getString(R.string.returncridt), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, list.get(0).getRETURNDCREDITE()+"", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        double returnCash=0;
        double returncridt=0;
        try {
            returnCash= Double.parseDouble(cashReportList.get(0).getRETURNDCASH());
            returncridt=Double.parseDouble(cashReportList.get(0).getRETURNDCREDITE());

        }catch (Exception exception){
            returnCash=0;
            returncridt=0;
            Log.e("exception==",exception.getMessage());
        }
        double netSalesT=Double.parseDouble(cashReportList.get(0).getTotalCash()) + Double.parseDouble(cashReportList.get(0).getTotalCredite())
                -returnCash-returncridt;


        insertCell(pdfPTable, context.getResources().getString(R.string.total_sales), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,  globelFunction.convertToEnglish(String. format("%.3f",netSalesT)), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.addCell(cell);
        insertCell(pdfPTable, context.getResources().getString(R.string.paymentCash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, list.get(0).getPtotalCash()+"", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.paymentCheque), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,list.get(0).getPtotalCredite(), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit_value), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, list.get(0).getPtotalCrediteCard()+"", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        double  TOTAL_netpaymentVal = Double.parseDouble(cashReportList.get(0).getPtotalCash()) + Double.parseDouble(cashReportList.get(0).getPtotalCredite());

        insertCell(pdfPTable, context.getResources().getString(R.string.netpayment), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,globelFunction.convertToEnglish(String. format("%.3f",TOTAL_netpaymentVal)), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

      //  pdfPTable.addCell( cell3) ;


        double TOTAL_total_cashVal = Double.parseDouble(cashReportList.get(0).getTotalCash()) + Double.parseDouble(cashReportList.get(0).getPtotalCash());

        insertCell(pdfPTable, context.getResources().getString(R.string.total_cash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,globelFunction.convertToEnglish(String. format("%.3f",TOTAL_total_cashVal)), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);




        return pdfPTable;

    }
    private PdfPTable createPaymentReport(List<PayMentReportModel> list) {
        createPDF("Payment_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(7);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.voucher_number), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.sales_man_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.pay_date), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
//        insertCell(pdfPTable,context.getResources().getString(R.string.CUS_NAME   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.amount_word), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.remark), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.sale_man_number), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.pay_method), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getVouNo()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesmanname()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPaymentDate()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
//            insertCell(pdfPTable, String.valueOf(list.get(i).getCustomerNo()         ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAmount()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getNotes()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesmanNo()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            if (list.get(i).getPAYKIND().equals("0")) {
                insertCell(pdfPTable, context.getResources().getString(R.string.app_cheque), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            } else if (list.get(i).getPAYKIND().equals("1")) {
                insertCell(pdfPTable, context.getResources().getString(R.string.cash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            } else if (list.get(i).getPAYKIND().equals("2")) {
                insertCell(pdfPTable, context.getResources().getString(R.string.credit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            }


        }
        String total = globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(PayMentReportModel::getAmount).mapToDouble(Double::parseDouble).sum())));

        insertCellNOborder(pdfPTable, context.getResources().getString(R.string.total) + "     :   " + total, ALIGN_CENTER, 2, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, context.getResources().getString(R.string.Payments_count) + "     :   " + list.size(), ALIGN_CENTER, 2, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        return pdfPTable;

    }

    private PdfPTable createListOfferPrice(List<ListPriceOffer> list) {
        createPDF("ListOfferPrice" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(8);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.ListNo), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.ListName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.ListType), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.from_date), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.to_date), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getPO_LIST_NO()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPO_LIST_NAME()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            switch (list.get(i).getPO_LIST_TYPE()) {
                case "0":
                    insertCell(pdfPTable, "Price BY Customer", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
                case "1":
                    insertCell(pdfPTable, "Price Only", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
                case "2":
                    insertCell(pdfPTable, "OFFer", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
            }
            insertCell(pdfPTable, String.valueOf(list.get(i).getFROM_DATE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTO_DATE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }

    private PdfPTable createAnalyzeAcountReport(List<AnalyzeAccountModel> list) {
        createPDF("AnalyzeAcount" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(10);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.customerNo), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.customerName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.debit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.df), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.cf), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.totalDebit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.totalCredit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.TotalDebitF), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable, context.getResources().getString(R.string.TotalCreditF), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getAccCode()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAccNameA()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVD()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVC()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVDF()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVCF()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTDEB()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTCRE()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTDEBF()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTCREF()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }

    private PdfPTable createAccountStatmentReport(List<Account__Statment_Model> list) {
        createPDF("AccountStatment" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.voucher_number), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.transName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.date_voucher), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.debit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.credit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.balance), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getVoucherNo()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTranseNmae()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getDate_voucher()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getDebit()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getCredit()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getBalance()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }
    private PdfPTable createItemsReport(List<ItemReportModel> list) {
        createPDF("ItemsReport" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable, context.getString(R.string.itemName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.date), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.qty), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.price), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.total), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.sales_man_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {

            insertCell(pdfPTable, String.valueOf(list.get(i).getName()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getVoucherDate()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


            if (Double.parseDouble(list.get(i).getQty()) % 1 != 0)
                insertCell(pdfPTable, globelFunction.convertToEnglish((String.  format("%.3f",Double.parseDouble(list.get(i).getQty())))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
              //  holder.qty .setText(""+globelFunction.convertToEnglish((String.  format("%.3f",Double.parseDouble(itemsList.get(i).getQty())))));
            else
                insertCell(pdfPTable, String.valueOf(list.get(i).getQty()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable,  globelFunction.convertToEnglish((String.  format("%.3f",Double.parseDouble(list.get(i).getUnitPrice())))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable,  globelFunction.convertToEnglish((String.  format("%.3f",Double.parseDouble(list.get(i).getTotal())))), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesName()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }


    private PdfPTable createunCollectedReport(List<Payment> list) {
        createPDF("UnCollectedCheques_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(R.string.customerName), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.app_bank_name), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.check_number), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getString(R.string.chaequeDate), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable, context.getResources().getString(R.string.app_amount), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(getCusromerName(list.get(i).getCustNumber())), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getBank()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCheckNumber()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getDueDate()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAmount()), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        }
        String total = globelFunction.convertToEnglish(String.format("%.3f", (list.stream().map(Payment::getAmount).mapToDouble(Double::parseDouble).sum())));

        insertCellNOborder(pdfPTable, context.getResources().getString(R.string.total) + "     :   " + total, ALIGN_CENTER, 2, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCellNOborder(pdfPTable, "", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);


        return pdfPTable;

    }

    private PdfPTable getHeaderTable(List<?> list, int reportType, String headerDate, String date) {
        PdfPTable pdfPTableHeader = new PdfPTable(7);
        pdfPTableHeader.setWidthPercentage(100f);
        pdfPTableHeader.setSpacingAfter(20);
        pdfPTableHeader.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTableHeader, headerDate, ALIGN_CENTER, 7, arabicFontHeader, BaseColor.BLACK);
        insertCell(pdfPTableHeader, context.getString(R.string.date) + " : " + date, Element.ALIGN_LEFT, 7, arabicFontHeader, BaseColor.BLACK);
        return pdfPTableHeader;
    }

    void createPDF(String fileName) {
        doc = new Document();
        docWriter = null;
        try {


            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/ReportVanSales/";
            file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String targetPdf = directory_path + fileName;

            File path = new File(targetPdf);

            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.setPageSize(PageSize.A4);//size of page
            //open document
            doc.open();
            Paragraph paragraph = new Paragraph();
            paragraph.add("");
            doc.add(paragraph);

            Log.e("path44", "" + targetPdf);
            pdfFileName = path;
            Log.e("pdfFileName", "" + pdfFileName);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void insertCell(PdfPTable table, String text, int align, int colspan, Font font, BaseColor border) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        cell.setBorderColor(border);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); //for make arabic string from right to left ...

//        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        //add the call to the table
        table.addCell(cell);

    }

    public void insertCellNOborder(PdfPTable table, String text, int align, int colspan, Font font, BaseColor border) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        cell.setBorder(0);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); //for make arabic string from right to left ...

//        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        //add the call to the table
        table.addCell(cell);

    }

    void endDocPdf() {

        if (doc != null) {
            //close the document
            doc.close();
        }
        if (docWriter != null) {
            //close the writer
            docWriter.close();
        }
    }

    void showPdf(File path){
        try

    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", path);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");//intent.setDataAndType(Uri.fromFile(path), "application/pdf");
        context.startActivity(intent);
    } catch(
    Exception e)

    {
        Toast.makeText(context, "Not able to open folder", Toast.LENGTH_SHORT).show();
    }

}
//    void showPdf(File path) {
//        Log.e("showPdf",""+path);
//
////        try {
////            Intent intent = new Intent(Intent.ACTION_VIEW);
////           // Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", path);
////            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////            //intent.setDataAndType(uri, "application/pdf");//intent.setDataAndType(Uri.fromFile(path), "application/pdf");
////            intent.setDataAndType(Uri.fromFile(path), "application/pdf");
////            context.startActivity(intent);
////        }
////        catch (Exception e)
////        {
////            Toast.makeText(context, "Not able to open flder", Toast.LENGTH_SHORT).show();
////        }
//
//       // File file = new File("mnt/sdcard.test.pdf");
////        Uri path = Uri.fromFile(file);
//
//        ///*****************************************
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setAction(Intent.ACTION_VIEW);
//
////        intent.setType("application/pdf");
////      intent.setData(Uri.fromFile(path));
//      //  intent.setDataAndType( FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() ,".fileprovider", mediaFile));
//    intent.setDataAndType(Uri.fromFile(path), "application/pdf");
//
//        try {
//            context.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(context, "No application found",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
private String getCusromerName(String num) {
    Log.e("num==",num+"");
    for (int i = 0; i < listCustomer.size(); i++){

        if (num.equals(listCustomer.get(i).getCustomerNumber())) {

            return listCustomer.get(i).getCustomerName();
        }

    }

    return "";
}
}
