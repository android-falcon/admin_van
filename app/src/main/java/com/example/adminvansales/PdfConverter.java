package com.example.adminvansales;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.adminvansales.Model.AnalyzeAccountModel;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.ListPriceOffer;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.Model.Payment;
import com.example.adminvansales.Report.CustomerLogReport;
import com.itextpdf.text.BaseColor;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import jxl.write.Label;

import static com.itextpdf.text.Element.ALIGN_CENTER;

public class PdfConverter {
    private Context context;
    Document doc;
    File file;
    PdfWriter docWriter = null;
    //    PDFView pdfView;
    File pdfFileName;
    BaseFont base;
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
    }

    public File  exportListToPdf(List<?> list, String headerDate, String date, int report ) {

        PdfPTable pdfPTable= new PdfPTable(1);
        PdfPTable pdfPTableHeader= new PdfPTable(1);
        pdfPTable = getContentTable(  list,report);

        pdfPTableHeader = getHeaderTable(list,report,headerDate,date);

        try {
//

            doc.add(  pdfPTableHeader);
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
        PdfPTable tableContent= new PdfPTable(1);
        switch (reportType)
        {
            case 1:
                 tableContent=creatTableCustomerLog((List<CustomerLogReportModel>) list);
                break;
            case 2:
                tableContent=createCashReport((List<CashReportModel>) list);
                break;
            case 3:
                tableContent=createPaymentReport((List<PayMentReportModel>) list);
                break;
            case 4:
                tableContent=createListOfferPrice((List<ListPriceOffer>) list);
                break;
            case 5:
                tableContent=createunCollectedReport((List<Payment>) list);
                break;
            case 6:
                tableContent=createAnalyzeAcountReport((List<AnalyzeAccountModel>) list);
                break;
        }
        return  tableContent;
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
        return  pdfPTable;

    }

    private PdfPTable createCashReport(List<CashReportModel> list)
    {
        createPDF("Cash_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(10);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(R.string.sales_man_no)                    , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.sales_man_name      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.cash_sale      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.credit_sales   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.total_sales  )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.cash      ) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.credit) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.netpayment) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.app_creditCard) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.total_cash) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);


        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesManNo() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesManName())       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTotalCash()       ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTotalCredite()         ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getNetSale()    )       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i) .getPtotalCash()   )       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i)  .getPtotalCredite() )               , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i)  .getNetPay() )               , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i)  .getPtotalCrediteCard() )               , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i)  .getNetCash() )               , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);




        }
        return pdfPTable;

    }

    private PdfPTable createPaymentReport(List<PayMentReportModel> list)
    {
        createPDF("Payment_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(8);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(R.string.voucher_number)                    , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.sales_man_name      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.pay_date      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.CUS_NAME   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.amount_word  )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.remark      ) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.sale_man_number) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.pay_method) , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);







        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getVouNo() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getSalesmanname())       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPaymentDate()       ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCustomerNo()         ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAmount()          )       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i) .getNotes()   )       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i)  .getSalesmanNo() )               , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            if(list.get(i).getPAYKIND().equals("0")){
                insertCell(pdfPTable, context.getResources().getString(R.string.app_cheque), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            }else if(list.get(i).getPAYKIND().equals("1")){
                insertCell(pdfPTable, context.getResources().getString(R.string.cash), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            }else if(list.get(i).getPAYKIND().equals("2")){
                insertCell(pdfPTable, context.getResources().getString(R.string.credit), ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            }



        }
        return pdfPTable;

    }

    private PdfPTable createListOfferPrice(List<ListPriceOffer> list)
    {
        createPDF("ListOfferPrice" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(8);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(R.string.ListNo)                    , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.ListName      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.ListType      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.from_date   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.to_date  )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getPO_LIST_NO() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPO_LIST_NAME()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            switch (list.get(i).getPO_LIST_TYPE()){
                case "0":
                    insertCell(pdfPTable, "Price BY Customer" , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
                case "1":
                    insertCell(pdfPTable,"Price Only" , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
                case "2":
                    insertCell(pdfPTable, "OFFer", ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    break;
            }
            insertCell(pdfPTable, String.valueOf(list.get(i).getFROM_DATE()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTO_DATE()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }

    private PdfPTable createAnalyzeAcountReport(List<AnalyzeAccountModel> list)
    {
        createPDF("AnalyzeAcount" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(10);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(               R.string.customerNo        )                    , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(               R.string.customerName      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(               R.string.debit           )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.credit       )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.df            )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.cf              )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.totalDebit      )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.totalCredit    )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.TotalDebitF    )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        insertCell(pdfPTable,context.getResources().getString(R.string.TotalCreditF    )  , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getAccCode() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAccNameA()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVD()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVC()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVDF()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getPREVCF()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTDEB()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTCRE()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getTDEBF()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            insertCell(pdfPTable, String.valueOf(list.get(i).getTCREF()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        }
        return pdfPTable;

    }
    private PdfPTable createunCollectedReport(List<Payment> list)
    {
        createPDF("UnCollectedCheques_Report" + ".pdf");
        PdfPTable pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTable,context.getString(R.string.app_bank_name)                    , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.check_number      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getString(R.string.chaequeDate      )                        , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);
        insertCell(pdfPTable,context.getResources().getString(R.string.app_amount   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

        pdfPTable.setHeaderRows(1);
        for (int i = 0; i < list.size(); i++) {
            insertCell(pdfPTable, String.valueOf(list.get(i).getBank() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getCheckNumber())       , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getDueDate()       ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable, String.valueOf(list.get(i).getAmount()         ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);




        }
        return pdfPTable;

    }

    private PdfPTable getHeaderTable(List<?> list, int reportType , String headerDate, String date) {
        PdfPTable pdfPTableHeader = new PdfPTable(7);
        pdfPTableHeader.setWidthPercentage(100f);
        pdfPTableHeader.setSpacingAfter(20);
        pdfPTableHeader.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        insertCell(pdfPTableHeader, headerDate, ALIGN_CENTER, 7, arabicFontHeader, BaseColor.BLACK);
        insertCell(pdfPTableHeader, context.getString(R.string.date) + " : " + date, Element.ALIGN_LEFT, 7, arabicFontHeader, BaseColor.BLACK);
        return  pdfPTableHeader;
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

    void showPdf(File path) {
        Log.e("showPdf",""+path);

//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", path);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            //intent.setDataAndType(uri, "application/pdf");//intent.setDataAndType(Uri.fromFile(path), "application/pdf");
//            intent.setDataAndType(Uri.fromFile(path), "application/pdf");
//            context.startActivity(intent);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(context, "Not able to open flder", Toast.LENGTH_SHORT).show();
//        }

       // File file = new File("mnt/sdcard.test.pdf");
//        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(path));
        intent.setType("application/pdf");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
