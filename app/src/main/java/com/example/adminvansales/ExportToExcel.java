package com.example.adminvansales;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.adminvansales.Model.AnalyzeAccountModel;
import com.example.adminvansales.Model.CashReportModel;
import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.ListPriceOffer;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.Model.Payment;
import com.example.adminvansales.Report.CustomerLogReport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ExportToExcel {
    private static ExportToExcel instance;
    Context context;
    File pathFile;

    public static ExportToExcel getInstance() {
        if (instance == null)
            instance = new ExportToExcel();

        return instance;
    }

    public File createExcelFile(Context context, String fileName, int report, List<?> list) {
//        public void createExcelFile(Context context, String fileName, int report, List<?> list, List<?> listDetail)
//        this.context = context;
//        final String fileName = "planned_packing_list_report.xls";

        //Saving file in external storage
        this.context=context;
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/VanSalesExcelReport");

        if (!directory.isDirectory()) {//create directory if not exist
            directory.mkdirs();
        }

        File file = new File(directory, fileName);//file path
        pathFile=file;
//        WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = null;//, wbSettings);
        try {
            workbook = Workbook.createWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (report) {

            case 1:
                workbook = customerLogReport(workbook, (List<CustomerLogReportModel>) list);
                break;
            case 2:
                workbook = paymentReport(workbook, (List<PayMentReportModel>) list);
                break;

            case 3:
                workbook = cashReport(workbook , (List<CashReportModel>) list );
                break;
            case 4:
                workbook = listReport(workbook , (List<ListPriceOffer>) list );
                break;
            case 5:
                workbook = unCollectedReport(workbook , (List<Payment>) list );
                break;
            case 6:
                workbook = analyzeAccountReport(workbook , (List<AnalyzeAccountModel>) list );
                break;
        }

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/blackseawood/";
//        file = new File(directory_path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String targetPdf = directory_path + fileName;
//        File path = new File(targetPdf);
//
//        try {
//            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", path);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(uri, "application/vnd.ms-excel");//intent.setDataAndType(Uri.fromFile(path), "application/pdf");
//        }catch (Exception e){}
//        try{
//            context.startActivity(intent);
//        }catch (Exception e){
//            Toast.makeText(context, "Excel program needed!", Toast.LENGTH_SHORT).show();
//        }
        return pathFile;
    }


//*********************************************************************

    WritableWorkbook customerLogReport(WritableWorkbook workbook, List<CustomerLogReportModel> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {
                sheet.addCell(new Label(0, 0,context.getString(R.string.SALES_MAN_ID) )); // column and row
                sheet.addCell(new Label(2, 0,  context.getString(R.string.sales_man_name)));
                sheet.addCell(new Label(3, 0, context.getResources().getString(R.string.CUS_CODE)));
                sheet.addCell(new Label(5, 0, context.getString(R.string.CUS_NAME)));
                sheet.addCell(new Label(6, 0, context.getString(R.string.CHECK_IN_DATE)));
                sheet.addCell(new Label(8, 0, context.getString(R.string.CHECK_IN_TIME) ));
                sheet.addCell(new Label(9, 0, context.getString(R.string.CHECK_OUT_DATE)));
                sheet.addCell(new Label(12, 0, context.getString(R.string.CHECK_OUT_TIME)));
//                sheet.addCell(new Label(10, 0, "Bundles"));
//                sheet.addCell(new Label(11, 0, "Cubic"));
                sheet.mergeCells(0,0, 1, 0);// col , row, to col , to row
                sheet.mergeCells(3,0, 4, 0);// col , row, to col , to row
                sheet.mergeCells(6,0, 7, 0);// col , row, to col , to row
                sheet.mergeCells(11,0, 12, 0);// col , row, to col , to row

                sheet.mergeCells(0,1, 1, 1);// col , row, to col , to row
                sheet.mergeCells(3,1, 4, 1);// col , row, to col , to row
                sheet.mergeCells(6,1, 7, 1);// col , row, to col , to row
                sheet.mergeCells(11,1, 12, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getSALES_MAN_ID()+""));
                    sheet.addCell(new Label(2, i + 2, list.get(i).getSalesmanname()));
                    sheet.addCell(new Label(3, i + 2, list.get(i).getCUS_CODE()));
                    sheet.addCell(new Label(5, i + 2, list.get(i).getCUS_NAME()));
                    sheet.addCell(new Label(6, i + 2, list.get(i).getCHECK_IN_DATE()));
                    sheet.addCell(new Label(8, i + 2, list.get(i).getCHECK_IN_TIME()));
                    sheet.addCell(new Label(9, i + 2, "" + list.get(i).getCHECK_OUT_DATE()));
                    sheet.addCell(new Label(12, i + 2, "" + list.get(i).getCHECK_OUT_TIME()));
//                    sheet.addCell(new Label(10, i + 2, "" + list.get(i).getStatus()));
//                    sheet.addCell(new Label(11, i + 2, "" +  String.format("%.3f", (list.get(i).getCubic()))));
                    sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row
                    sheet.mergeCells(3,i + 2, 4, i + 2);// col , row, to col , to row
                    sheet.mergeCells(6,i + 2, 7, i + 2);// col , row, to col , to row
                    sheet.mergeCells(11,i + 2, 12, i + 2);// col , row, to col , to row
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }



    WritableWorkbook cashReport(WritableWorkbook workbook, List<CashReportModel> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {
                sheet.addCell(new Label(0, 0, context.getString(R.string.sale_man_number)            )); // column and row
                sheet.addCell(new Label(2, 0, context.getString(R.string.sales_man_name   )                          )  );
                sheet.addCell(new Label(3, 0, context.getResources().getString(R.string.cash_sale  )  ) );
                sheet.addCell(new Label(4, 0, context.getResources().getString(R.string.credit_sales )   ));
                sheet.addCell(new Label(5, 0, context.getResources().getString(R.string.total_sales     )  ));
                sheet.addCell(new Label(6, 0, context.getResources().getString(R.string.cash     )  ));
                sheet.addCell(new Label(7, 0, context.getResources().getString(R.string.app_cheque     )  ));
                sheet.addCell(new Label(8, 0, context.getResources().getString(R.string.netpayment     )  ));
                sheet.addCell(new Label(9, 0, context.getResources().getString(R.string.app_creditCard     )  ));
                sheet.addCell(new Label(10, 0, context.getResources().getString(R.string.total_cash     )  ));

                sheet.mergeCells(0,1, 1, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getSalesManNo()+""));
                    sheet.addCell(new Label(2, i + 2,      list.get(i).getSalesManName()));
                    sheet.addCell(new Label(3, i + 2,  list.get(i).getTotalCash()+""));
                    sheet.addCell(new Label(4, i + 2,  list.get(i).getTotalCredite()+""));
                    sheet.addCell(new Label(5, i + 2,      list.get(i).getNetSale()));
                    sheet.addCell(new Label(6, i + 2,  list.get(i).getPtotalCash()+""));

                    sheet.addCell(new Label(7, i + 2,  list.get(i).getPtotalCredite()+""));
                    sheet.addCell(new Label(8, i + 2,  list.get(i).getNetPay()+""));
                    sheet.addCell(new Label(9, i + 2,  list.get(i).getPtotalCrediteCard()+""));
                    sheet.addCell(new Label(10, i + 2,  list.get(i).getNetCash()+""));

                    sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row

                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }
    WritableWorkbook listReport(WritableWorkbook workbook, List<ListPriceOffer> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {
                sheet.addCell(new Label(0, 0, context.getString(R.string.ListNo)            )); // column and row
                sheet.addCell(new Label(2, 0, context.getString(R.string.ListName   )                          )  );
                sheet.addCell(new Label(3, 0, context.getResources().getString(R.string.ListType  )  ) );
                sheet.addCell(new Label(4, 0, context.getResources().getString(R.string.from_date )   ));
                sheet.addCell(new Label(5, 0, context.getResources().getString(R.string.to_date     )  ));

                sheet.mergeCells(0,1, 1, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getPO_LIST_NO()+""));
                    sheet.addCell(new Label(2, i + 2,      list.get(i).getPO_LIST_NAME()));

                    switch (list.get(i).getPO_LIST_TYPE()){
                        case "0":
                            sheet.addCell(new Label(3, i + 2,  "Price BY Customer"));

                            break;
                        case "1":
                            sheet.addCell(new Label(3, i + 2,  "Price Only"));

                            break;
                        case "2":
                            sheet.addCell(new Label(3, i + 2,  "OFFer"));

                            break;
                    }

                    sheet.addCell(new Label(4, i + 2,  list.get(i).getFROM_DATE()+""));
                    sheet.addCell(new Label(5, i + 2,      list.get(i).getTO_DATE()));

                    sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row

                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }

    WritableWorkbook paymentReport(WritableWorkbook workbook, List<PayMentReportModel> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {
                sheet.addCell(new Label(0, 0, context.getString(R.string.voucher_number)            )); // column and row
                sheet.addCell(new Label(2, 0, context.getString(R.string.pay_date   )) );

                sheet.addCell(new Label(2, 0, context.getString(R.string.pay_date   )) );
                sheet.addCell(new Label(2, 0, context.getString(R.string.pay_date   )) );


                sheet.addCell(new Label(3, 0, context.getResources().getString(R.string.CUS_NAME  )  ) );
                sheet.addCell(new Label(4, 0, context.getResources().getString(R.string.amount_word )   ));
                sheet.addCell(new Label(5, 0, context.getResources().getString(R.string.remark     )  ));
                sheet.addCell(new Label(6, 0, context.getResources().getString(R.string.sale_man_number     )  ));
                sheet.addCell(new Label(7, 0, context.getResources().getString(R.string.pay_method     )  ));

                sheet.mergeCells(0,1, 1, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getVouNo()+""));
                    sheet.addCell(new Label(2, i + 2,      list.get(i).getPaymentDate()));
                    sheet.addCell(new Label(3, i + 2,  list.get(i).getCustomerNo()+""));
                    sheet.addCell(new Label(4, i + 2,  list.get(i).getAmount()+""));
                    sheet.addCell(new Label(5, i + 2,      list.get(i).getNotes()));
                    sheet.addCell(new Label(6, i + 2,  list.get(i).getSalesmanNo()+""));

                    if(list.get(i).getPAYKIND().equals("1")){
                        sheet.addCell(new Label(7, i + 2,   context.getResources().getString(R.string.cash )));
                    }else  if(list.get(i).getPAYKIND().equals("0")){
                        sheet.addCell(new Label(7, i + 2,   context.getResources().getString(R.string.app_cheque) ));
                    }else  if(list.get(i).getPAYKIND().equals("2")) {
                        sheet.addCell(new Label(7, i + 2,   context.getResources().getString(R.string.credit )));
                    }



                    sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row

                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }

    WritableWorkbook unCollectedReport(WritableWorkbook workbook, List<Payment> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {
                sheet.addCell(new Label(0, 0, context.getString(R.string.app_bank_name)            )); // column and row
                sheet.addCell(new Label(1, 0, context.getString(R.string.check_number   )) );

                sheet.addCell(new Label(2, 0, context.getString(R.string.chaequeDate   )) );
                sheet.addCell(new Label(3, 0, context.getString(R.string.app_amount   )) );

                sheet.mergeCells(0,1, 3, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getBank()+""));
                    sheet.addCell(new Label(1, i + 2,      list.get(i).getCheckNumber()+""));
                    sheet.addCell(new Label(2, i + 2,  list.get(i).getDueDate()+""));
                    sheet.addCell(new Label(3, i + 2,  list.get(i).getAmount()+""));


                    //sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row

                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }
    WritableWorkbook analyzeAccountReport(WritableWorkbook workbook, List<AnalyzeAccountModel> list) {

        try {
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);//Excel sheet name. 0 represents first sheet

            try {


                sheet.addCell(new Label(0, 0, context.getString( R.string.customerNo   )            )); // column and row
                sheet.addCell(new Label(1, 0, context.getString( R.string.customerName   )) );

                sheet.addCell(new Label(2, 0, context.getString( R.string.debit        ) ));
                sheet.addCell(new Label(3, 0, context.getString((R.string.credit       )) ));
                sheet.addCell(new Label(4, 0, context.getString((R.string.df           )) ));
                sheet.addCell(new Label(5, 0, context.getString((R.string.cf           )) ));
                sheet.addCell(new Label(6, 0, context.getString((R.string.totalDebit   )) ));
                sheet.addCell(new Label(7, 0, context.getString((R.string.totalCredit  )) ));
                sheet.addCell(new Label(8, 0, context.getString((R.string.TotalDebitF  )) ));
                sheet.addCell(new Label(9, 0, context.getString((R.string.TotalCreditF )) ));

                sheet.mergeCells(0,1, 9, 1);// col , row, to col , to row

                for (int i = 0; i < list.size(); i++) {
                    sheet.addCell(new Label(0, i + 2, list.get(i).getAccCode()+""));
                    sheet.addCell(new Label(1, i + 2,      list.get(i).getAccNameA()+""));
                    sheet.addCell(new Label(2, i + 2,  list.get(i).getPREVD()+""));
                    sheet.addCell(new Label(3, i + 2,  list.get(i).getPREVC()+""));
                    sheet.addCell(new Label(4, i + 2,  list.get(i).getPREVDF()+""));
                    sheet.addCell(new Label(5, i + 2,  list.get(i).getPREVCF()+""));
                    sheet.addCell(new Label(6, i + 2,  list.get(i).getTDEB()+""));
                    sheet.addCell(new Label(7, i + 2,  list.get(i).getTCRE()+""));
                    sheet.addCell(new Label(8, i + 2,  list.get(i).getTDEBF()+""));
                    sheet.addCell(new Label(9, i + 2,  list.get(i).getTCREF()+""));



                    //sheet.mergeCells(0,i + 2, 1, i + 2);// col , row, to col , to row

                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;

    }
}
