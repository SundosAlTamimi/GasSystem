package com.falconssoft.gassystem;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.sewoo.jpos.printer.CPCLPrinter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;


public class CPCLSample2 {
    private CPCLPrinter cpclPrinter = new CPCLPrinter();
    private int paperType = 2;

    private DecimalFormat decimalFormat;
    Context context;


    public CPCLSample2(Context context) {
        this.context = context;
//        obj = new DatabaseHandler(context);
        decimalFormat = new DecimalFormat("##.000");
    }

    public void selectGapPaper() {
        this.paperType = 0;
    }

    public void selectBlackMarkPaper() {
        this.paperType = 1;
    }

    public void selectContinuousPaper() {
        this.paperType = 2;
    }

    public void barcodeTest(int count) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
        this.cpclPrinter.printCPCLBarcode(0, "CODABAR", 2, 0, 30, 19, 45, "A37859B", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 19, 18, "CODABAR", 0);
        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
        this.cpclPrinter.printCPCLBarcode(0, "39", 2, 1, 30, 19, 130, "0123456", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 21, 103, "CODE 39", 0);
        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
        this.cpclPrinter.printCPCLBarcode(0, "93", 2, 1, 30, 19, 215, "0123456", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 21, 180, "CODE 93", 0);
        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
        this.cpclPrinter.printCPCLBarcode(0, "128", 2, 1, 30, 19, 300, "A37859B", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 21, 270, "CODE 128", 0);
        this.cpclPrinter.printForm();
    }

    public void profile2(int count) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printCPCLText(0, 5, 1, 1, 1, "SEWOO TECH CO.,LTD.", 0);
        this.cpclPrinter.printCPCLText(0, 0, 2, 1, 70, "Global leader in the mini-printer industry.", 0);
        this.cpclPrinter.printCPCLText(0, 0, 2, 1, 110, "Total Printing Solution", 0);
        this.cpclPrinter.printCPCLText(0, 0, 2, 1, 150, "Diverse innovative and reliable products", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 1, 200, "TEL : 82-31-459-8200", 0);
        this.cpclPrinter.printCPCL2DBarCode(0, "QRCODE", 0, 250, 4, 0, 1, 0, "http://www.miniprinter.com");
        this.cpclPrinter.printCPCLText(0, 7, 0, 130, 250, "www.miniprinter.com", 0);
        this.cpclPrinter.printCPCLText(0, 1, 0, 130, 300, "<-- Check This.", 0);
        this.cpclPrinter.printForm();
    }

    public void barcode2DTest(int count) throws IOException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printBitmap("//sdcard//temp//test//logo_s.jpg", 1, 1);
        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
        this.cpclPrinter.printCPCLBarcode(0, "128", 2, 1, 30, 19, 125, "12345690AB", 0);
        this.cpclPrinter.printCPCL2DBarCode(0, "PDF-417", 80, 180, 2, 7, 2, 1, "SEWOO TECH\r\nLK-P11");
        this.cpclPrinter.printCPCL2DBarCode(0, "QRCODE", 30, 260, 4, 0, 1, 0, "LK-P11");
        this.cpclPrinter.printCPCLText(0, 0, 2, 130, 280, "SEWOO TECH", 0);
        this.cpclPrinter.printCPCLText(0, 0, 2, 130, 300, "LK-P11", 0);
        this.cpclPrinter.printForm();
    }

    public void imageTestArabic(int count, Bitmap testB) throws IOException {//
        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight()+30 , count);
        this.cpclPrinter.setMedia(this.paperType);

        this.cpclPrinter.printBitmap(testB, 550, 0);//x=400

//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_3.jpg", 100, 200);
//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_4.jpg", 120, 245);
        this.cpclPrinter.printForm();
    }

    public void imageTestEnglish(int count, Bitmap testB) throws IOException {//
        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight() , count);
        this.cpclPrinter.setMedia(this.paperType);

        this.cpclPrinter.printBitmap(testB, 0, 0);//x=400

//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_3.jpg", 100, 200);
//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_4.jpg", 120, 245);
        this.cpclPrinter.printForm();
    }


    public void imageTestEnglishBarcode(int count, Bitmap testB) throws IOException {//
        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight()+70 , count);
        this.cpclPrinter.setMedia(this.paperType);

        this.cpclPrinter.printBitmap(testB, 0, 0);//x=400

//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_3.jpg", 100, 200);
//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_4.jpg", 120, 245);
        this.cpclPrinter.printForm();
    }

    public void imageTestEnglishReport(int count, Bitmap testB) throws IOException {//
        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight() , count);
        this.cpclPrinter.setMedia(this.paperType);

        this.cpclPrinter.printBitmap(testB, 0, 0);//x=400

//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_3.jpg", 100, 200);
//        this.cpclPrinter.printBitmap("//sdcard//temp//test//sample_4.jpg", 120, 245);
        this.cpclPrinter.printForm();
    }


    public void imageTestEnglishReports(int count, Bitmap testB,Bitmap testB2 ,Bitmap testB3,Bitmap testB4) throws IOException {//
//        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight(), count);
//        this.cpclPrinter.setMedia(this.paperType);
//        this.cpclPrinter.printBitmap(testB, 0, 0);//x=400
//        this.cpclPrinter.printForm();
//
//        this.cpclPrinter.setForm(0, 0, 0,testB2.getHeight()+20, count);
//        this.cpclPrinter.setMedia(this.paperType);
//        this.cpclPrinter.printBitmap(testB2, 0, 0);//x=400
//        this.cpclPrinter.printForm();
//        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight(), count);
//        this.cpclPrinter.setMedia(this.paperType);
//        this.cpclPrinter.printBitmap(testB3, 0, 0);//x=400
//        this.cpclPrinter.printForm();

//        this.cpclPrinter.printAndroidFont(1,"-----------------ملاحظات-----------------");
        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("-----------ملاحظات------------", 1000, 24, 0, 1);
        this.cpclPrinter.printForm();
        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("يرجى تسديد الفاتوره خلال اسبوع من تاريخ اصدارها\n1254512\n ------------------------------",450, 24, 0, 1);
        this.cpclPrinter.printForm();

        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("يرجى تسديد الفاتوره خلال اسبوع من تاريخ اصدارها\n1254512\n ------------------------------",450, 24, 0, 1);
        this.cpclPrinter.printForm();

        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("رقم الحساب لدى البنك الاردني االكويتي\\n فرع شارع المدينه المنورة", 450, 24, 0, 1);
        this.cpclPrinter.printForm();

        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("545465", 1000, 24, 0, 1);
        this.cpclPrinter.printForm();

        this.cpclPrinter.setForm(0, 0, 0, 20, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printAndroidFont("----------------------------------", 450, 24, 0, 1);
        this.cpclPrinter.printForm();
//        this.cpclPrinter.setForm(0, 0, 0,testB.getHeight(), count);
//        this.cpclPrinter.setMedia(this.paperType);
//        this.cpclPrinter.printBitmap(testB4, 0, 0);//x=400
//        this.cpclPrinter.printForm();
    }

    public void dmStamp(int count, Bitmap bitmap) throws IOException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printBitmap(bitmap, 0, 100);
//        this.cpclPrinter.printBitmap("//sdcard//temp//test//denmark_flag.jpg", 222, 55);
//        this.cpclPrinter.setCPCLBarcode(0, 0, 0);
//        this.cpclPrinter.printCPCLBarcode(0, "128", 2, 1, 30, 19, 290, "0123456", 1);
//        this.cpclPrinter.printCPCLText(0, 0, 1, 21, 345, "Quantity 001", 1);
        this.cpclPrinter.printForm();
    }

    public void fontTest(int count,String data) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 0, 0, 60, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printCPCLText(0, 0, 0, 1, 1, data, 1);
//        this.cpclPrinter.printCPCLText(0, 0, 1, 1, 50, "FONT-0-1", 0);
//        this.cpclPrinter.printCPCLText(0, 4, 0, 1, 100, "FONT-4-0", 0);
//        this.cpclPrinter.printCPCLText(0, 4, 1, 1, 150, "FONT-4-1", 0);
//        this.cpclPrinter.printCPCLText(0, 4, 2, 1, 260, "4-2", 0);
        this.cpclPrinter.printForm();
    }

    public void fontTypeTest(int count) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.printCPCLText(0, 0, 0, 1, 1, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 1, 0, 1, 20, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 2, 0, 1, 70, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 4, 0, 1, 100, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 5, 0, 1, 150, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 6, 0, 1, 200, "ABCD1234", 0);
        this.cpclPrinter.printCPCLText(0, 7, 0, 1, 250, "ABCD1234", 0);
        this.cpclPrinter.printForm();
    }

    public void settingTest1(int count) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.setMagnify(2, 2);
        this.cpclPrinter.setJustification(0);
        this.cpclPrinter.printCPCLText(0, 0, 0, 1, 1, "FONT-0-0", 2);
        this.cpclPrinter.setMagnify(1, 1);
        this.cpclPrinter.printCPCLText(0, 0, 1, 1, 50, "FONT-0-1", 0);
        this.cpclPrinter.setJustification(1);
        this.cpclPrinter.printCPCLText(0, 4, 0, 1, 100, "FONT-4-0", 0);
        this.cpclPrinter.setMagnify(2, 1);
        this.cpclPrinter.printCPCLText(0, 4, 1, 1, 150, "FONT-4-1", 0);
        this.cpclPrinter.setMagnify(1, 1);
        this.cpclPrinter.setJustification(2);
        this.cpclPrinter.printCPCLText(0, 4, 2, 1, 260, "4-2", 0);
        this.cpclPrinter.resetMagnify();
        this.cpclPrinter.printForm();
    }

    public void settingTest2(int count) throws UnsupportedEncodingException {
        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.setConcat(0, 75, 75);
        this.cpclPrinter.concatText(4, 2, 5, "$");
        this.cpclPrinter.concatText(4, 3, 0, "12");
        this.cpclPrinter.concatText(4, 2, 5, "34");
        this.cpclPrinter.resetConcat();
        this.cpclPrinter.printForm();
    }

    public void multiLineTest(int count) throws UnsupportedEncodingException {
        String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ;0123456789!@#%^&*\r\n";
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 16; ++i) {
            sb.append(data);
        }

        this.cpclPrinter.setForm(0, 200, 200, 406, count);
        this.cpclPrinter.setMedia(this.paperType);
        this.cpclPrinter.setMultiLine(15);
        this.cpclPrinter.multiLineText(0, 0, 0, 10, 20);
        this.cpclPrinter.multiLineData(sb.toString());
        this.cpclPrinter.resetMultiLine();
        this.cpclPrinter.printForm();
    }

    public String statusCheck() {
        String result = "";
        if (this.cpclPrinter.printerCheck() >= 0) {
            int sts = this.cpclPrinter.status();
            if (sts == 0) {
                return "Normal";
            }

            if ((sts & 1) > 0) {
                result = result + "Busy\r\n";
            }

            if ((sts & 2) > 0) {
                result = result + "Paper empty\r\n";
            }

            if ((sts & 4) > 0) {
                result = result + "Cover open\r\n";
            }

            if ((sts & 8) > 0) {
                result = result + "Battery low\r\n";
            }
        } else {
            result = "Check the printer\r\nNo response";
        }

        return result;
    }

    public void printAndroidFont(int count, String data) throws UnsupportedEncodingException {
        int nLineWidth = 1000;
//        String data = "Receipt";
        Object var4 = null;

        try {
            this.cpclPrinter.setForm(0, 0, 0, 20, count);
            this.cpclPrinter.setMedia(this.paperType);
            this.cpclPrinter.printAndroidFont(data, nLineWidth, 24, 0, 1);
//            this.cpclPrinter.printAndroidFont("Left Alignment", nLineWidth, 24, 120, 0);
//            this.cpclPrinter.printAndroidFont("Center Alignment", nLineWidth, 24, 150, 1);
//            this.cpclPrinter.printAndroidFont("Right Alignment", nLineWidth, 24, 180, 2);
//            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF, "SANS_SERIF : 1234iwIW", nLineWidth, 24, 210, 0);
//            this.cpclPrinter.printAndroidFont(Typeface.SERIF, "SERIF : 1234iwIW", nLineWidth, 24, 240, 0);
//            this.cpclPrinter.printAndroidFont(Typeface.MONOSPACE, "MONOSPACE : 1234iwIW", nLineWidth, 24, 270, 0);
            this.cpclPrinter.printForm();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }






//    public void printMultilingualFont2(int count,Bitmap testB) throws UnsupportedEncodingException {
//        if (count == 0) {
//            voucherforPrint = vouch1;
//            itemforPrint = items;
//        } else {
//            voucherforPrint = voucher;
//            itemforPrint = itemForPrint;
////            Log.e("print sales ..",""+itemsList.get(0).getItemName()+" \n"+itemforPrint.get(0));
//        }
//        int nLineWidth = 1140;
//        String Arabicdata = "";//ArabicDataPrinter();
//        String test="        ";
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 1700, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//
//
//            double total_Qty=0;
//
//            String DataArabic = "";
//            String voucherTyp = "";
//            switch (voucherforPrint.getVoucherType()) {
//                case 504:
//                    voucherTyp = "فاتورة بيع";
//                    break;
//                case 506:
//                    voucherTyp = "فاتورة مرتجعات";
//                    break;
//                case 508:
//                    voucherTyp = "طلب جديد";
//                    break;
//            }
//
//
//            CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//
//            DataArabic += "                             "+companyInfo.getCompanyName() + "                             \n" +
//                    "هاتف : " + companyInfo.getcompanyTel() + "    الرقم الضريبي : " + companyInfo.getTaxNo() + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "رقم الفاتورة : " + voucherforPrint.getVoucherNumber() + "          التاريخ: " + voucherforPrint.getVoucherDate() + "\n" +
//
//                    "اسم العميل   : " + voucherforPrint.getCustName() + "\n" +
//                    "ملاحظة        : " + voucherforPrint.getRemark() + "\n" +
//                    "نوع الفاتورة : " + voucherTyp + "\n" +
//                    "طريقة الدفع  : " + (voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n" +
//
//                    "--------------------------------------------------------------------------------" + "\n";
//            this.cpclPrinter.printAndroidFont(DataArabic, nLineWidth, 18, 360, 0);
//
////            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
////                total_Qty=0;
////
////                DataArabic += " السلعة           " + "العدد      " + "الوزن    " + "سعر الوحدة   " + "المجموع  " + "\n" +
////                        "--------------------------------------------------------------------------------" + "\n";
////
////                for (int i = 0; i < itemforPrint.size(); i++) {
////                    if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
////                        total_Qty+=itemforPrint.get(i).getQty();
////
////                        String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
//////                    amount = convertToEnglish(amount);
////                        if (itemforPrint.get(i).getItemName().length() <= 12) {
////                            String space = itemforPrint.get(i).getItemName();
////                            for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
////                                space = " " + space;
////                            }
//////                            DataArabic += space + "  " + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n";
////                        } else {
////                            String space = itemforPrint.get(i).getItemName().substring(0, 10);
//////                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
//////                            space = " " + space;
//////                        }
////                            String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
//////                            DataArabic += space + "\t\t\t" + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n" + fullString + "\n";
////                        }
////
////
////                    }
////                }
//////           (itemsString + "\n", 0, 2);
////            } else {
////                total_Qty=0;
//////                DataArabic += " السلعة              " + "العدد   " + "سعر الوحدة   " + "المجموع  " + "\n" +
//////                        "--------------------------------------------------------------------------------" + "\n";
////                for (int i = 0; i < itemforPrint.size(); i++) {
////                    if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
////                        total_Qty+=itemforPrint.get(i).getQty();
////                        String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
////
////
////                        if (itemforPrint.get(i).getItemName().length() <= 12) {
////                            String space = itemforPrint.get(i).getItemName();
////                            for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
////                                space = " " + space;
////                            }
////                            DataArabic += space + "  " + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n";
////                        } else {
////                            String space = itemforPrint.get(i).getItemName().substring(0, 10);
//////                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
//////                            space = " " + space;
//////                        }
////                            String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
//////                            DataArabic += space + "\t\t\t"  + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n" + fullString + "\n";
////                        }
////
////
//////                    amount = convertToEnglish(amount);
////                    }
////                }
////
//////            (itemsString2 + "\n", 0, 2);
////            }
//            Arabicdata += "--------------------------------------------------------------------------------" + "\n" +
//                    "اجمالي الكمية  : " + total_Qty + "\n" +
//                    "المجموع  : " + voucherforPrint.getSubTotal() + "\n" +
//                    "الخصم    : " + voucherforPrint.getTotalVoucherDiscount() + "\n" +   Log.e("getTotalVoucherDiscount",""+voucherforPrint.getTotalVoucherDiscount())+
//
//                    "الضريبة  : " + voucherforPrint.getTax() + "\n" +
//                    "الصافي   : " + voucherforPrint.getNetSales() + "\n" +
//                    "استلمت البضاعة كاملة و بحالة جيدة و خالية من " + "\n" +
//                    "اية  عيوب و اتعهد بدفع قيمة هذه الفاتورة." + "\n" +
//                    "المستلم : ________________ التوقيع : __________" + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n";
//
//            Log.e("total_Qty",""+total_Qty);
//
//
//
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 18, 360, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 26, 360, 0);
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//    }
//
//    public void printMultilingualFont(int count,Bitmap testB) throws UnsupportedEncodingException {
//        if (count == 0) {
//            voucherforPrint = vouch1;
//            itemforPrint = items;
//        } else {
//            voucherforPrint = voucher;
//            itemforPrint = itemForPrint;
////            Log.e("print sales ..",""+itemsList.get(0).getItemName()+" \n"+itemforPrint.get(0));
//        }
//        int nLineWidth = 1140;
//        String Arabicdata = ArabicDataPrinter();
//        String test="        ";
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 1700, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 18, 360, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 26, 360, 0);
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//    }
//
////    public void printAndroidFontSalesInvoice(int count,List<Item> itemforPrint,Voucher voucherforPrint) throws UnsupportedEncodingException {
////        int nLineWidth = 550;
//////        String data = "Receipt";
//////        Object var4 = null;
////
////        try {
//////            this.cpclPrinter.setForm(0, 200, 200, 200, count);
//////            this.cpclPrinter.setMedia(this.paperType);
//////            this.cpclPrinter.printAndroidFont(data, nLineWidth, 100, 0, 1);
//////            this.cpclPrinter.printAndroidFont("الاحد", nLineWidth, 24, 120, 0);
//////            this.cpclPrinter.printAndroidFont("Center Alignment", nLineWidth, 24, 150, 1);
//////            this.cpclPrinter.printAndroidFont("Right Alignment", nLineWidth, 24, 180, 2);
//////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF, "SANS_SERIF : 1234iwIW", nLineWidth, 24, 210, 0);
//////            this.cpclPrinter.printAndroidFont(Typeface.SERIF, "SERIF : 1234iwIW", nLineWidth, 24, 240, 0);
//////            this.cpclPrinter.printAndroidFont(Typeface.MONOSPACE, "MONOSPACE : 1234iwIW", nLineWidth, 24, 270, 0);
////
////
////            double total_Qty=0;
////
////
////            String voucherTyp = "Sales Invoice";
////            switch (voucherforPrint.getVoucherType()) {
////                case 504:
////                    voucherTyp = "Sales Invoice";
////                    break;
////                case 506:
////                    voucherTyp = "Return Invoice";
////                    break;
////                case 508:
////                    voucherTyp = "New Order";
////                    break;
////            }
////
//////                posPtr.setAsync(false);
////            CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
////            if(companyInfo.getLogo()!=null) {
//////                    this.cpclPrinter.printBitmap(companyInfo.getLogo(), nLineWidth, 24, 120, 0);
////                Log.e("12222print","pyyy");
////            }
////            this.cpclPrinter.setForm(0, 200, 200, 100, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,companyInfo.getCompanyName()+"\n", nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 100, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Tel : " + companyInfo.getcompanyTel()+"    " + "Tax No: " + companyInfo.getTaxNo() + "\n", nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 100, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Voucher No :" + voucherforPrint.getVoucherNumber()+"    " + "          Date: " + voucherforPrint.getVoucherDate() + "\n"  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 100, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"--------------------------------------------------------------------------------" + "\n" , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 100, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Store No. : " +  Login.salesMan/* + "\n" */  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"SalesMan Name :" +obj.getAllSettings().get(0).getSalesMan_name() /* + "\n" */, nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Customer Name :" + voucherforPrint.getCustName() /* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Remark : " + voucherforPrint.getRemark()/* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Voucher Type : " +voucherTyp/* + "\n" */  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"Pay Method : " +  (voucherforPrint.getPayMethod() == 0 ? "Credit" : "Cash")/* + "\n" */  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"--------------------------------------------------------------------------------" + "\n"  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////
////            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
////                total_Qty=0;
////                this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                this.cpclPrinter.setMedia(this.paperType);
////                this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true," Item No.                          " + "QTY" + "\t\t\t" + "الوزن" + "\t\t\t" +  "Price" + "\t\t\t" +"Total" /* + "\n" */ , nLineWidth, 24, 120, 0);
////                this.cpclPrinter.printForm();
////
////                this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                this.cpclPrinter.setMedia(this.paperType);
////                this.cpclPrinter.printAndroidFont(Typeface.SANS_SERIF,true,"--------------------------------------------------------------------------------" /* + "\n" */  , nLineWidth, 24, 120, 0);
////                this.cpclPrinter.printForm();
////
////                for (int i = 0; i < itemforPrint.size(); i++) {
////                    if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
////                        total_Qty+=itemforPrint.get(i).getQty();
////
////                        String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
//////						if (itemforPrint.get(i).getItemName().length() <= 12) {
//////							String space = itemforPrint.get(i).getItemName();
//////							for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
//////								space =  space+" " ;
//////							}
//////							posPtr.printAndroidFont(null,true,space + "  " + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" +convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount))))+ "\n" , nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);
//////
//////						} else {
//////							String space = itemforPrint.get(i).getItemName().substring(0, 10);
////////                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
////////                            space = " " + space;
////////                        }
//////							String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
//////							posPtr.printAndroidFont( null,true,space + "\t\t\t" + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" +convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount)))) + "\n" + fullString + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);
//////
//////						}
////                        String space = itemforPrint.get(i).getItemNo();
////                        for (int g = 0; g < (20 - itemforPrint.get(i).getItemNo().length()); g++) {
////                            space =  space+" " ;
////                        }
////                        this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                        this.cpclPrinter.setMedia(this.paperType);
////                        this.cpclPrinter.printAndroidFont( Typeface.SANS_SERIF,true,space+ "\t\t\t" + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" +convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount)))) + "\n" + itemforPrint.get(i).getItemName() + "\n", nLineWidth, 24, 120, 0);
////                        this.cpclPrinter.printForm();
////
////
////
////                    }
////                }
////            } else {
////                total_Qty=0;
////                this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                this.cpclPrinter.setMedia(this.paperType);
////                this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true," Item No.                          " + "Qty" + "\t\t\t" + "Price" + "\t\t\t" + "Total" /* + "\n" */ , nLineWidth, 24, 120, 0);
////                this.cpclPrinter.printForm();
////
////                this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                this.cpclPrinter.setMedia(this.paperType);
////                this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true,"--------------------------------------------------------------------------------" /* + "\n" */ , nLineWidth, 24, 120, 0);
////                this.cpclPrinter.printForm();
////
////                for (int i = 0; i < itemforPrint.size(); i++) {
////                    if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
////                        total_Qty+=itemforPrint.get(i).getQty();
////                        String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
//////						posPtr.printBitmap(itemPrint(itemforPrint.get(i).getPrice()+"",convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount)))),itemforPrint.get(i).getQty()+"",itemforPrint.get(i).getItemName()),ESCPOSConst.LK_ALIGNMENT_CENTER,550);
////                        String space = itemforPrint.get(i).getItemNo();
////                        for (int g = 0; g < (20 - itemforPrint.get(i).getItemNo().length()); g++) {
////                            space =  space+" " ;
////                        }
////                        this.cpclPrinter.setForm(0, 200, 200, 50, count);
////                        this.cpclPrinter.setMedia(this.paperType);
////                        this.cpclPrinter.printAndroidFont( null,true,space  + "\t\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" +convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount)))) + "\n" + itemforPrint.get(i).getItemName() + "\n", nLineWidth, 24, 120, 0);
////                        this.cpclPrinter.printForm();
////                    }
////                }
////
////            }
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "--------------------------------------------------------------------------------" + "\n" , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "Total Qty : " + total_Qty/* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "Total     : " + voucherforPrint.getSubTotal()/* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "Discount  : " + voucherforPrint.getTotalVoucherDiscount() /* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "Tax       : " + voucherforPrint.getTax() /* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "Net Total : " + voucherforPrint.getNetSales() /* + "\n" */  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "I received the goods complete and in good condition and free from any defects and I pledge to pay the value of this invoice." /* + "\n" */ , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "" + "\n"  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true,  "The recipient : ____________  Signature : __________" + "\n"  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////
////            this.cpclPrinter.setForm(0, 200, 200, 50, count);
////            this.cpclPrinter.setMedia(this.paperType);
////            this.cpclPrinter.printAndroidFont(  Typeface.SANS_SERIF,true, "--------------------------------------------------------------------------------" + "\n"  , nLineWidth, 24, 120, 0);
////            this.cpclPrinter.printForm();
////        } catch (IOException var6) {
////            var6.printStackTrace();
////        }
////
////    }
//
//    public void printMultilingualFontCash(int count) throws UnsupportedEncodingException {
//
//        int nLineWidth = 1140;
//        String Arabicdata = bankArabic(count);
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 1100, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 26, 360, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//    }
//    public void printMultilingualFontCash_EJABI(int count) throws UnsupportedEncodingException {
//Log.e("printMultilingual","ontCash_EJABI");
//        int nLineWidth = 1140;
//        String Arabicdata = bankEnglish(count);
//        Log.e("printMultilingual",""+Arabicdata);
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
////            imageTestEnglish(0,);
//            this.cpclPrinter.setForm(0, 200, 200, 1100, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 26, 200, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//    }
//
//    public void RSS1(int count) throws UnsupportedEncodingException {
//        String rsscommand = "B RSS-EXPSTACK 2 40 10 10 [01]98898765432106[3202]012345\r\n";
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 1000, count);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.userString(rsscommand);
//            this.cpclPrinter.printForm();
//        } catch (IOException var4) {
//            var4.printStackTrace();
//        }
//
//    }
//
//    public void RSS2(int count) throws UnsupportedEncodingException {
//        String rsscommand = "B RSS-EXPSTACK 2 40 10 10 [01]98898765432106[3920]15000[3101]000015[17]160210[3202]012345[15]991231\r\n";
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 1000, count);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.userString(rsscommand);
//            this.cpclPrinter.printForm();
//        } catch (IOException var4) {
//            var4.printStackTrace();
//        }
//
//    }
//
//    String ArabicDataPrinter() {
//        double total_Qty=0;
//
//        String DataArabic = "";
//        String voucherTyp = "";
//        switch (voucherforPrint.getVoucherType()) {
//            case 504:
//                voucherTyp = "فاتورة بيع";
//                break;
//            case 506:
//                voucherTyp = "فاتورة مرتجعات";
//                break;
//            case 508:
//                voucherTyp = "طلب جديد";
//                break;
//        }
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//
//        DataArabic += "                             "+companyInfo.getCompanyName() + "                             \n" +
//                "هاتف : " + companyInfo.getcompanyTel() + "    الرقم الضريبي : " + companyInfo.getTaxNo() + "\n" +
//                "--------------------------------------------------------------------------------" + "\n" +
//                "رقم الفاتورة : " + voucherforPrint.getVoucherNumber() + "          التاريخ: " + voucherforPrint.getVoucherDate() + "\n" +
//
//                "اسم العميل   : " + voucherforPrint.getCustName() + "\n" +
//                "ملاحظة        : " + voucherforPrint.getRemark() + "\n" +
//                "نوع الفاتورة : " + voucherTyp + "\n" +
//                "طريقة الدفع  : " + (voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n" +
//
//                "--------------------------------------------------------------------------------" + "\n";
//        if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//            total_Qty=0;
//
//            DataArabic += " السلعة           " + "العدد      " + "الوزن    " + "سعر الوحدة   " + "المجموع  " + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n";
//
//            for (int i = 0; i < itemforPrint.size(); i++) {
//                if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
//                    total_Qty+=itemforPrint.get(i).getQty();
//
//                    String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
////                    amount = convertToEnglish(amount);
//                    if (itemforPrint.get(i).getItemName().length() <= 12) {
//                        String space = itemforPrint.get(i).getItemName();
//                        for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
//                            space = " " + space;
//                        }
//                        DataArabic += space + "  " + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n";
//                    } else {
//                        String space = itemforPrint.get(i).getItemName().substring(0, 10);
////                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
////                            space = " " + space;
////                        }
//                        String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
//                        DataArabic += space + "\t\t\t" + itemforPrint.get(i).getUnit() + "\t\t\t\t" + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n" + fullString + "\n";
//                    }
//
//
//                }
//            }
////           (itemsString + "\n", 0, 2);
//        } else {
//            total_Qty=0;
//            DataArabic += " السلعة              " + "العدد   " + "سعر الوحدة   " + "المجموع  " + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n";
//            for (int i = 0; i < itemforPrint.size(); i++) {
//                if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
//                    total_Qty+=itemforPrint.get(i).getQty();
//                    String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
//
//
//                    if (itemforPrint.get(i).getItemName().length() <= 12) {
//                        String space = itemforPrint.get(i).getItemName();
//                        for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
//                            space = " " + space;
//                        }
//                        DataArabic += space + "  " + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n";
//                    } else {
//                        String space = itemforPrint.get(i).getItemName().substring(0, 10);
////                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
////                            space = " " + space;
////                        }
//                        String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
//                        DataArabic += space + "\t\t\t"  + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n" + fullString + "\n";
//                    }
//
//
////                    amount = convertToEnglish(amount);
//                }
//            }
//
////            (itemsString2 + "\n", 0, 2);
//        }
//        DataArabic += "--------------------------------------------------------------------------------" + "\n" +
//                "اجمالي الكمية  : " + total_Qty + "\n" +
//                "المجموع  : " + voucherforPrint.getSubTotal() + "\n" +
//                "الخصم    : " + voucherforPrint.getTotalVoucherDiscount() + "\n" +   Log.e("getTotalVoucherDiscount",""+voucherforPrint.getTotalVoucherDiscount())+
//
//                "الضريبة  : " + voucherforPrint.getTax() + "\n" +
//                "الصافي   : " + voucherforPrint.getNetSales() + "\n" +
//                "استلمت البضاعة كاملة و بحالة جيدة و خالية من " + "\n" +
//                "اية  عيوب و اتعهد بدفع قيمة هذه الفاتورة." + "\n" +
//                "المستلم : ________________ التوقيع : __________" + "\n" +
//                "--------------------------------------------------------------------------------" + "\n";
//
//      Log.e("total_Qty",""+total_Qty);
//        return DataArabic;
//    }
//
//
//    String bankArabic(int count) {
//
//
//            if(count==2){
//                payList=paymentsforPrint;
//                payforBank=ReceiptVoucher.payment;
//
//            }
//            else {
//                payList=paymentPrinter;
//                payforBank=pay1;
//            }
//
//
//        String dataArabic = "";
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        if (payforBank.getPayMethod() == 1) {
//
//            dataArabic = "                                        "+companyInfo.getCompanyName() + "                                        \n" +
//                    "هاتف : " + companyInfo.getcompanyTel() + "    الرقم الضريبي : " + companyInfo.getTaxNo() + "\n" +
//                         "--------------------------------------------------------------------------------" + "\n" +"                                        "+
//                    " سند قبض " + "                                        \n\n" +
//                    "رقم السند: " + payforBank.getVoucherNumber() + "         التاريخ: " + payforBank.getPayDate() + "\n" +
//                    "       " + "\n" +
//                    "وصلني من السيد/السادة: " + "\t" +
//                    payforBank.getCustName() + "\n" +
//                    "ملاحظة: " + payforBank.getRemark() + "\n" +
//                    "المبلغ المقبوض: " + payforBank.getAmount() + "\n" +
//                    "طريقة الدفع: " + (payforBank.getPayMethod() == 1 ? "نقدا" : "شيك") + "\n";
//
//            dataArabic+="\n\n"+
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "المستلم : -------------------            التوقيع : -----------------------      " + "\n" ;
//
//        } else {
//
//            dataArabic = "                                        "+companyInfo.getCompanyName() + "                             \n" +
//                    "هاتف : " + companyInfo.getcompanyTel() + "    الرقم الضريبي : " + companyInfo.getTaxNo() + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +"                                        "+
//                    " سند قبض " + "                                        \n\n" +
//                    "رقم السند: " + payforBank.getVoucherNumber() + "         التاريخ: " + payforBank.getPayDate() + "\n" +
//                    "       " + "\n" +
//                    "وصلني من السيد/السادة: " + "\t" +
//                    payforBank.getCustName() + "\n" +
//                    "ملاحظة: " + payforBank.getRemark() + "\n" +
//                    "المبلغ المقبوض: " + payforBank.getAmount() + "\n" +
//                    "طريقة الدفع: " + (payforBank.getPayMethod() == 1 ? "نقدا" : "شيك") + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n\n" +
//                    "        القيمة     " + "      التاريخ      " + "   رقم الشيك         " + "  البنك    " + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "       " + "\n";
//
//            for (int i = 0; i < payList.size(); i++) {
//
//                if (payList.get(i).getBank().length() <= 12) {
//                    String space = payList.get(i).getBank();
//                    for (int g = 0; g < 12 - payList.get(i).getBank().length(); g++) {
//                        space += "\t";
//                    }//"\t\t\t\t" +
//                    dataArabic +="\t\t"+space+ payList.get(i).getCheckNumber()+"\t\t\t\t"+ payList.get(i).getDueDate()+"\t\t\t" + payList.get(i).getAmount() + "\n";
////                    dataArabic += "\t\t\t\t" + paymentsforPrint.get(i).getAmount() + "\t\t\t\t" + paymentsforPrint.get(i).getDueDate() + "\t\t\t\t" + paymentsforPrint.get(i).getCheckNumber() + "\t\t" + space + "\n";
//                } else {
//                    String space = payList.get(i).getBank().substring(0, 10);
////                    for (int g = 0; g <  paymentsforPrint.get(i).getBank().length()-12; g++) {
////                        space+= "\t" ;
////                    }
//                    String fullString = payList.get(i).getBank().substring(10, payList.get(i).getBank().length() - 1);
//                    dataArabic += "\t\t"+space +"\t\t\t"+ payList.get(i).getCheckNumber() + "\t\t\t\t" + payList.get(i).getDueDate() + "\t\t\t" + payList.get(i).getAmount() + "\n" + fullString + "\n";
////                    dataArabic +=   "\n\t\t\t\t" + paymentsforPrint.get(i).getAmount() + "\t\t\t\t" + paymentsforPrint.get(i).getDueDate() + "\t\t\t\t" + paymentsforPrint.get(i).getCheckNumber() + "\t\t" + space +fullString + "\n";
//                }
//            }
//
//            dataArabic+="\n\n"+
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "المستلم : -------------------            التوقيع : -----------------------      " + "\n" ;
//
//
//        }
//
//
//        return dataArabic;
//
//    }
//
//    String bankEnglish(int count) {
//
//        Log.e("printMultilingual","bankEnglish");
//        if(count==2){
//            payList=paymentsforPrint;
//            payforBank=ReceiptVoucher.payment;
//
//        }
//        else {
//            payList=paymentPrinter;
//            payforBank=pay1;
//        }
//
//
//        String dataArabic = "";
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        String firstName="",LASTnAME="";
//
//        if(payforBank.getCustName().length()<20){
//            firstName=payforBank.getCustName();
//            LASTnAME="";
//        }else{
//            firstName=payforBank.getCustName().substring(0,20);
//            LASTnAME=payforBank.getCustName().substring(20,payforBank.getCustName().length());;
//        }
//
//        if (payforBank.getPayMethod() == 1) {
//
//
//            dataArabic = "                                        "+companyInfo.getCompanyName() + "                                        \n" +
//                    "    Tel No :" + companyInfo.getcompanyTel() + "             Tax No :" + companyInfo.getTaxNo() + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +"                                        "+
//                    "Cash Receipt" + "                                        \n\n" +
//                    "Receipt No:" + payforBank.getVoucherNumber() + "             Date :" + payforBank.getPayDate() + "\n" +
//                    "       " + "\n" +
//                    "I received from Mr. / Messrs:" + "\t" +
//                    firstName + "\n" +LASTnAME+"\n"+
//                    "Remark :" + payforBank.getRemark() + "\n" +
//                    "Amount received:" + payforBank.getAmount() + "\n" +
//                    "Payment Method:" + (payforBank.getPayMethod() == 1 ? "Cash" : "Cheque") + "\n";
//
//            dataArabic+="\n\n"+
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "Recipient : -------------------            Signature : -----------------------      " + "\n" ;
//
//        } else {
//
//            dataArabic = "                                        "+companyInfo.getCompanyName() + "                             \n" +
//                    "Tel No : " + companyInfo.getcompanyTel() + "              Tax No : " + companyInfo.getTaxNo() + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +"                                        "+
//                    "Cash Receipt" + "                                        \n\n" +
//                    "Receipt No:" + payforBank.getVoucherNumber() + "              Date: " + payforBank.getPayDate() + "\n" +
//                    "       " + "\n" +
//                    "I received from Mr. / Messrs: " + "\t" +
//                    firstName + "\n" +LASTnAME+"\n"+
//                    "Remark: " + payforBank.getRemark() + "\n" +
//                    "Amount received: " + payforBank.getAmount() + "\n" +
//                    "Payment Method:" + (payforBank.getPayMethod() == 1 ? "Cash" : "Cheque") + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n\n" +
//                    "        Cheque No        " + "      Date      " + "         Value       "  + "\n" +
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "       " + "\n";
//
////            for (int i = 0; i < payList.size(); i++) {
////
////                if (payList.get(i).getBank().length() <= 12) {
////                    String space = payList.get(i).getBank();
////                    for (int g = 0; g < 12 - payList.get(i).getBank().length(); g++) {
////                        space += "\t";
////                    }//"\t\t\t\t" +
////                    dataArabic +="\t\t"+space+ payList.get(i).getCheckNumber()+"\t\t\t\t"+ payList.get(i).getDueDate()+"\t\t\t" + payList.get(i).getAmount() + "\n";
//////                    dataArabic += "\t\t\t\t" + paymentsforPrint.get(i).getAmount() + "\t\t\t\t" + paymentsforPrint.get(i).getDueDate() + "\t\t\t\t" + paymentsforPrint.get(i).getCheckNumber() + "\t\t" + space + "\n";
////                } else {
////                    String space = payList.get(i).getBank().substring(0, 10);
//////                    for (int g = 0; g <  paymentsforPrint.get(i).getBank().length()-12; g++) {
//////                        space+= "\t" ;
//////                    }
////                    String fullString = payList.get(i).getBank().substring(10, payList.get(i).getBank().length() - 1);
////                    dataArabic += "\t\t"+space +"\t\t\t"+ payList.get(i).getCheckNumber() + "\t\t\t\t" + payList.get(i).getDueDate() + "\t\t\t" + payList.get(i).getAmount() + "\n" + fullString + "\n";
//////                    dataArabic +=   "\n\t\t\t\t" + paymentsforPrint.get(i).getAmount() + "\t\t\t\t" + paymentsforPrint.get(i).getDueDate() + "\t\t\t\t" + paymentsforPrint.get(i).getCheckNumber() + "\t\t" + space +fullString + "\n";
////                }
////            }
//
//            for (int i = 0; i < payList.size(); i++) {
//
//                if ((""+payList.get(i).getCheckNumber()).length() <= 20) {
//                    String space = ""+payList.get(i).getCheckNumber();
//                    for (int g = 0; g < 20 - (""+payList.get(i).getCheckNumber()).length(); g++) {
//                        space += " ";
//                    }//"\t\t\t\t" +
//                    dataArabic+="\t\t"+space+"\t\t\t"+ payList.get(i).getDueDate()+"\t\t\t" + payList.get(i).getAmount() + "\n" +payList.get(i).getBank()+"\n";
//
////                    dataArabic += "\t\t\t\t" + payList.get(i).getAmount() + "\t\t\t\t" + payList.get(i).getDueDate() + "\t\t\t\t" + payList.get(i).getCheckNumber() + "\t\t" + space + "\n";
//                } else {
//                    String space = (""+payList.get(i).getCheckNumber()).substring(0, 20);
////                    for (int g = 0; g <  payList.get(i).getBank().length()-12; g++) {
////                        space+= "\t" ;
////                    }
//                    String fullString = (""+payList.get(i).getCheckNumber()).substring(20, payList.get(i).getBank().length() - 1);
//                    dataArabic+=  "\t\t"+space +"\t\t\t"+ payList.get(i).getDueDate() + "\t\t\t" + payList.get(i).getAmount() + "\n" + fullString + "\n"+ payList.get(i).getBank();
////                    dataArabic +=   "\n\t\t\t\t" + payList.get(i).getAmount() + "\t\t\t\t" + payList.get(i).getDueDate() + "\t\t\t\t" + payList.get(i).getCheckNumber() + "\t\t" + space +fullString + "\n";
//                }
//            }
//
//            dataArabic+="\n\n"+
//                    "--------------------------------------------------------------------------------" + "\n" +
//                    "Recipient : -------------------            Signature : -----------------------      " + "\n" ;
//
//
//        }
//
//
//        return dataArabic;
//
//    }
//
//
//    public void printMultilingualFontCashReport()  throws UnsupportedEncodingException {
//
//        int nLineWidth = 1140;
//        String Arabicdata = CashReport_kArabic();
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 920, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 24, 360, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//
//    }
//    public void printMultilingualFont_AccountReport()  throws UnsupportedEncodingException {
//
//        int nLineWidth = 1140;
//        String Arabicdata = AcountReport_Arabic();
//        int numOfCopy = obj.getAllSettings().get(0).getNumOfCopy();
//
//        try {
//            this.cpclPrinter.setForm(0, 200, 200, 920, numOfCopy);
//            this.cpclPrinter.setMedia(this.paperType);
//            this.cpclPrinter.printAndroidFont(Arabicdata, nLineWidth, 24, 360, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.cpclPrinter.printForm();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//
//    }
//
//    private String CashReport_kArabic() {
//        String companney_name="";
////        decimalFormat = new DecimalFormat("##.00");
//        String dataArabic_Report="";
//        try {
//            total_cash=net+cash-returnCash;
//
//
//            CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//            if (companyInfo.getCompanyName().equals("")) {
//                companney_name = "Companey";
//                //Please fill  the companey name
//                Toast.makeText(context, R.string.fill_name, Toast.LENGTH_SHORT).show();
//            } else {
//                companney_name = companyInfo.getCompanyName();
//            }
//            dataArabic_Report += "                                        " + companney_name + "                            \n \n" +
//                    "  الرقم الضريبي :  " + companyInfo.getTaxNo() + " \n " +
//                    "        -----------------------------------------------------------------------  " + " \n " +
//                    "التاريخ  :     " + date.getText() + " \n " +
//                    "المبيعات نقدا :     " + convertToEnglish(decimalFormat.format((cash - returnCash))) + " \n " +
//                    "المبيعات ذمم :     " + convertToEnglish(decimalFormat.format((credit - returnCridet))) + " \n " +
//                    "إجمالي المبيعات :     " + convertToEnglish(decimalFormat.format(total )) + " \n " +
//                    "\n" +
//                    "        -----------------------------------------------------------------------  " + " \n " +
//                    "الدفع نقدا :     " + convertToEnglish(decimalFormat.format(cashPayment)) + " \n " +
//                    "الدفع شيك :     " + convertToEnglish(decimalFormat.format(creditPayment)) + " \n " +
//                    "الاجمالي :     " + convertToEnglish(decimalFormat.format(net)) + " \n " +
//                    "\n" +
//                    "        -----------------------------------------------------------------------  " + " \n "+
//
//                    "اجمالي المقبوضات :     " + convertToEnglish(decimalFormat.format(total_cash)) + " \n\n \n "+
//                    "                                                                                    " +
//                    "                                                                                    " ;
//
//
//        }catch (Exception e){
//            Toast.makeText(context, R.string.error_companey_info, Toast.LENGTH_SHORT).show();
//        }
//    return  dataArabic_Report;
//
//    }
//
//    private String AcountReport_Arabic()
//    {
//        String companney_name="";
////        decimalFormat = new DecimalFormat("##.00");
//        String dataArabic_Report="";
//        try {
//            total_cash=net+cash-returnCash;
//
//
//            CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//            if (companyInfo.getCompanyName().equals("")) {
//                companney_name = "Companey";
//                //Please fill  the companey name
//                Toast.makeText(context, R.string.fill_name, Toast.LENGTH_SHORT).show();
//            } else {
//                companney_name = companyInfo.getCompanyName();
//            }
//            dataArabic_Report += "                                        " + companney_name + "                            \n \n" +
//                    "  الرقم الضريبي :  " + companyInfo.getTaxNo() + " \n " +
//                    "        -----------------------------------------------------------------------  " + " \n " +
//                    "التاريخ  :     " + date.getText() + " \n " +
//                    "المبيعات نقدا :     " + convertToEnglish(decimalFormat.format((cash - returnCash))) + " \n " +
//                    "المبيعات ذمم :     " + convertToEnglish(decimalFormat.format((credit - returnCridet))) + " \n " +
//                    "إجمالي المبيعات :     " + convertToEnglish(decimalFormat.format(total )) + " \n " +
//                    "\n" +
//                    "        -----------------------------------------------------------------------  " + " \n " +
//                    "الدفع نقدا :     " + convertToEnglish(decimalFormat.format(cashPayment)) + " \n " +
//                    "الدفع شيك :     " + convertToEnglish(decimalFormat.format(creditPayment)) + " \n " +
//                    "الاجمالي :     " + convertToEnglish(decimalFormat.format(net)) + " \n " +
//                    "\n" +
//                    "        -----------------------------------------------------------------------  " + " \n "+
//
//                    "اجمالي المقبوضات :     " + convertToEnglish(decimalFormat.format(total_cash)) + " \n\n \n "+
//                    "                                                                                    " +
//                    "                                                                                    " ;
//
//
//        }catch (Exception e){
//            Toast.makeText(context, R.string.error_companey_info, Toast.LENGTH_SHORT).show();
//        }
//        return  dataArabic_Report;
//
//    }
//
//    public String convertToEnglish(String value) {
//        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
//        return newValue;
//    }
}
/*
     total_Qty=0;
            DataArabic += " السلعة              " + "العدد   " + "سعر الوحدة   " + "المجموع  " + "\n" +
                    "--------------------------------------------------------------------------------" + "\n";
            for (int i = 0; i < itemforPrint.size(); i++) {
                if (voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) {
                    total_Qty+=itemforPrint.get(i).getQty();
                    String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());


                    if (itemforPrint.get(i).getItemName().length() <= 12) {
                        String space = itemforPrint.get(i).getItemName();
                        for (int g = 0; g < 12 - itemforPrint.get(i).getItemName().length(); g++) {
                            space = " " + space;
                        }
                        DataArabic += space + "  " + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n";
                    } else {
                        String space = itemforPrint.get(i).getItemName().substring(0, 10);
//                        for (int g = 0; g < 16 - itemforPrint.get(i).getItemName().length(); g++) {
//                            space = " " + space;
//                        }
                        String fullString = itemforPrint.get(i).getItemName().substring(10, itemforPrint.get(i).getItemName().length() - 1);
                        DataArabic += space + "\t\t\t"  + itemforPrint.get(i).getQty() + "\t\t\t\t" + itemforPrint.get(i).getPrice() + "\t\t\t\t" + amount + "\n" + fullString + "\n";
                    }


//                    amount = convertToEnglish(amount);
                }
            }
            */
