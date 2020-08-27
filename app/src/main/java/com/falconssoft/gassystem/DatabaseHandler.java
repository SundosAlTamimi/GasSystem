package com.falconssoft.gassystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.MaxSerial;
import com.falconssoft.gassystem.Modle.RecCash;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Remarks;
import com.falconssoft.gassystem.Modle.SettingModle;
import com.falconssoft.gassystem.Modle.Users;
import com.falconssoft.gassystem.Modle.Voucher;
import com.falconssoft.gassystem.Modle.VoucherModle;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "GasDatabase";
    static SQLiteDatabase db;

    //******************************************************************
    private static final String VOUCHERS_TABLE = "VOUCHERS";

    private static final String COUNT_NO = "COUNTER_NO";
    private static final String CUST_NAME = "CUST_NAME";
    private static final String PREVIOUS_READ = "PREVIOUS_READ";
    private static final String CURRENT_READ = "CURRENT_READ";
    private static final String CONSUMING = "CONSUMING";
    private static final String CONSUMING_VALUE = "CONSUMING_VALUE";
    private static final String PREVIOUS_PALANCE = "PREVIOUS_PALANCE";
    private static final String BADAL_GAS = "BADAL_GAS";
    private static final String BADAL_SERVICES = "BADAL_SERVICES";
    private static final String SERVICE_NO_TAX = "SERVICE_NO_TAX";
    private static final String NET = "NET";
    private static final String TAX = "TAX";
    private static final String CURRENT_CONSUMING = "CURRENT_CONSUMING";
    private static final String LAST_VALUE = "LAST_VALUE";
    private static final String NOTE = "NOTE";

    //******************************************************************
    private static final String VOUCHERS = "VOUCHERS_TABLE";

    private static final String COUNT_NO1 = "COUNTER_NO";
    private static final String CUST_NAME1 = "CUST_NAME";
    private static final String last_READ1 = "last_READ";
    private static final String ACC_NO1 = "ACC_NO";
    private static final String GAS_PRESSURE1 = "GAS_PRESSURE";
    private static final String PROJECT_NAME1= "PROJECT_NAME";
    private static final String PARAMETER1 = "PARAMETER";
    private static final String CURRENT_READER1 = "CURRENT_READER";
    private static final String C_COST1 = "C_COST";
    private static final String C_COST_VAL1 = "C_COST_VAL";
    private static final String SERVICE1 = "SERVICE";
    private static final String REQ_VALUE1 = "REQ_VALUE";
    private static final String READER_DATE1 = "READER_DATE";
    private static final String INV_TYPE1 = "INV_TYPE";
    private static final String INV_NO1 = "INV_NO";

    private static final String NET_VAL1 = "NET_VAL";
    private static final String TAX_VAL1 = "TAX_VAL";
    private static final String GRET1 = "GRET";
    private static final String REMARKS1 = "REMARKS";
    private static final String CONSUMPTION1 = "CONSUMPTION";
    private static final String CREDIT1 = "CREDIT";
    private static final String IS_POST1 = "IS_POST";
    private static final String IS_PER1 = "IS_PER";

    private static final String BDLVAL1 = "BDLVAL";
    private static final String IS_EXPORT1 = "IS_EXPORT";

    //******************************************************************
    private static final String RECEIPT_TABLE = "RECEIPT";

    private static final String RECEIPT_NO2 = "RECEIPT_NO";
    private static final String CUST_NAME2 = "CUST_NAME";
    private static final String PROJECT_NAME2 = "PROJECT_NAME";
    private static final String CREDIT2 = "CREDIT";
    private static final String ACCOUNT_NO2 = "ACCOUNT_NO";
    private static final String COUNTER_NO2 = "COUNTER_NO";
    private static final String VALUE2 = "VALUE";
    private static final String NOTE2 = "NOTE";

    //******************************************************************

    private static final String CUSTOMER_TABLE = "CUSTOMER";

    private static final String COUNTER_NO = "COUNTER_NO";
    private static final String ACC_NO = "ACC_NO";
    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String LAST_READER = "LAST_READER";
    private static final String GAS_PRESSURE = "GAS_PRESSURE";
    private static final String CREDIT = "CREDIT";
    private static final String G_PRICE = "G_PRICE";
    private static final String PRJECT_NAME = "PRJECT_NAME";
    private static final String IS_PER = "IS_PER";
    private static final String BDLVAL = "BDLVAL";
    private static final String CUSTSTS = "CUSTSTS";

    //******************************************************************

    private static final String USERS_TABLE = "USERS";

    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";

    //******************************************************************

    private static final String REMARKS_TABLE = "REMARKS";

    private static final String TITLE = "TITLE";
    private static final String BODY = "BODY";

    //******************************************************************
    private static final String RECCASH_TABLE = "RECCASH";

    private static final String RECNO2 = "RECNO";
    private static final String ACCNAME2 = "ACCNAME";
    private static final String ACCNO2 = "ACCNO";
    private static final String CASH2 = "CASH";
    private static final String REMARKS2 = "REMARKS";
    private static final String RECDATE2 = "RECDATE";
    private static final String IS_POST2 = "IS_POST";
    private static final String PRJNAME2 = "PRJNAME";
    private static final String IS_EXPORT2 = "IS_EXPORT";


    //******************************************************************
    private static final String SETTING_TABLE = "SETTING_TABLE";

    private static final String IP_ADDRESS3 = "IP_ADDRESS";
    private static final String COMPANY_NAME3 = "COMPANY_NAME";
    private static final String ACC_NO3 = "ACC_NO";
    private static final String TAX_NO3 = "TAX_NO";
    private static final String LOGO3 = "LOGO";

    //******************************************************************
    private static final String SERIAL_TABLE = "MAXSERIAL";

    private static final String SERIAL_COLMAX = "COLMAX";
    private static final String SERIAL_MAXVAL = "MAXVAL";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SERIAL_TABLE = "CREATE TABLE " + SERIAL_TABLE + "("
                + SERIAL_COLMAX + " TEXT,"
                + SERIAL_MAXVAL + " INTEGER"
                + ")";
        db.execSQL(CREATE_SERIAL_TABLE);

        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + CUSTOMER_TABLE + "("
                + COUNTER_NO + " TEXT,"
                + ACC_NO + " TEXT,"
                + CUSTOMER_NAME + " TEXT,"
                + LAST_READER + " INTEGER,"
                + GAS_PRESSURE + " INTEGER,"
                + CREDIT + " INTEGER,"
                + G_PRICE + " INTEGER,"
                + PRJECT_NAME + " TEXT,"
                + IS_PER + " INTEGER,"
                + BDLVAL + " INTEGER,"
                + CUSTSTS + " INTEGER" + ")";
        db.execSQL(CREATE_CUSTOMER_TABLE);

        String CREATE_VOUCHER_TABLE = "CREATE TABLE " + VOUCHERS_TABLE + "("
                + COUNT_NO + " TEXT,"
                + CUST_NAME + " TEXT,"
                + PREVIOUS_READ + " INTEGER,"
                + CURRENT_READ + " INTEGER,"
                + CONSUMING + " INTEGER,"
                + CONSUMING_VALUE + " INTEGER,"
                + PREVIOUS_PALANCE + " INTEGER,"
                + BADAL_GAS + " INTEGER,"
                + BADAL_SERVICES + " INTEGER,"
                + SERVICE_NO_TAX + " INTEGER,"
                + NET + " INTEGER,"
                + TAX + " INTEGER,"
                + CURRENT_CONSUMING + " INTEGER,"
                + LAST_VALUE + " INTEGER,"
                + NOTE + " TEXT" + ")";
        db.execSQL(CREATE_VOUCHER_TABLE);

        String CREATE_VOUCHERS_TABLE = "CREATE TABLE " + VOUCHERS + "("
                + COUNT_NO1 + " TEXT,"
                + CUST_NAME1 + " TEXT,"
                + last_READ1 + " TEXT,"
                + ACC_NO1 + " TEXT,"
                + GAS_PRESSURE1 + " TEXT,"
                + PROJECT_NAME1 + " TEXT,"
                + PARAMETER1 + " TEXT,"
                + CURRENT_READER1 + " TEXT,"
                + C_COST1 + " TEXT,"
                + C_COST_VAL1 + " TEXT,"
                + SERVICE1 + " TEXT,"
                + REQ_VALUE1 + " TEXT,"
                + READER_DATE1 + " TEXT,"
                + INV_TYPE1 + " TEXT,"
                + INV_NO1 + " TEXT,"
                + NET_VAL1 + " TEXT,"
                + TAX_VAL1 + " TEXT,"
                + GRET1 + " TEXT,"
                + REMARKS1 + " TEXT,"
                + CONSUMPTION1 + " TEXT,"
                + CREDIT1 + " TEXT,"
                + IS_POST1 + " TEXT,"
                + IS_PER1 + " TEXT,"
                + BDLVAL1 + " TEXT,"
                + IS_EXPORT1 + " TEXT" + ")";
        db.execSQL(CREATE_VOUCHERS_TABLE);




        String CREATE_RECEIPT_TABLE = "CREATE TABLE " + RECEIPT_TABLE + "("
                + RECEIPT_NO2 + " TEXT,"
                + CUST_NAME2 + " TEXT,"
                + PROJECT_NAME2 + " TEXT,"
                + CREDIT2 + " TEXT,"
                + ACCOUNT_NO2 + " TEXT,"
                + COUNTER_NO2 + " TEXT,"
                + VALUE2 + " INTEGER,"
                + NOTE2 + " TEXT" + ")";
        db.execSQL(CREATE_RECEIPT_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + "("
                + USER_NAME + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_REMARKS_TABLE = "CREATE TABLE " + REMARKS_TABLE + "("
                + TITLE + " TEXT,"
                + BODY + " TEXT" + ")";
        db.execSQL(CREATE_REMARKS_TABLE);


        String CREATE_RECCASH_TABLE = "CREATE TABLE " + RECCASH_TABLE + "("
                + RECNO2 + " TEXT,"
                + ACCNAME2 + " TEXT,"
                + ACCNO2 + " TEXT,"
                + CASH2 + " TEXT,"
                + REMARKS2 + " TEXT,"
                + RECDATE2 + " TEXT,"
                + IS_POST2 + " TEXT,"
                + IS_EXPORT2 + " TEXT,"
                + PRJNAME2 + " TEXT" + ")";
        db.execSQL(CREATE_RECCASH_TABLE);


        String CREATE_SETTING_TABLE = "CREATE TABLE " + SETTING_TABLE + "("
                + IP_ADDRESS3 + " TEXT,"
                + COMPANY_NAME3 + " TEXT,"
                + ACC_NO3 + " TEXT,"
                + TAX_NO3 + " TEXT,"
                + LOGO3 + " BLOB" + ")";
        db.execSQL(CREATE_SETTING_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            String CREATE_SERIAL_TABLE = "CREATE TABLE " + SERIAL_TABLE + "("
                    + SERIAL_COLMAX + " TEXT,"
                    + SERIAL_MAXVAL + " INTEGER"
                    + ")";
            db.execSQL(CREATE_SERIAL_TABLE);

            String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + "("
                    + USER_NAME + " TEXT,"
                    + PASSWORD + " TEXT" + ")";
            db.execSQL(CREATE_USERS_TABLE);

            String CREATE_REMARKS_TABLE = "CREATE TABLE " + REMARKS_TABLE + "("
                    + TITLE + " TEXT,"
                    + BODY + " TEXT" + ")";
            db.execSQL(CREATE_REMARKS_TABLE);



        }catch (Exception e)
        {
            Log.e("upgrade","BUNDLE Barcode");
        }


        try{
            db.execSQL("ALTER TABLE RECCASH ADD " + IS_EXPORT2 + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade","Ex ... REC_CASH");
        }

        try{
            db.execSQL("ALTER TABLE RECCASH ADD " + PRJNAME2 + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade","Ex ... REC_CASH");
        }

        try{
            String CREATE_VOUCHERS_TABLE = "CREATE TABLE " + VOUCHERS + "("
                    + COUNT_NO1 + " TEXT,"
                    + CUST_NAME1 + " TEXT,"
                    + last_READ1 + " TEXT,"
                    + ACC_NO1 + " TEXT,"
                    + GAS_PRESSURE1 + " TEXT,"
                    + PROJECT_NAME1 + " TEXT,"
                    + PARAMETER1 + " TEXT,"
                    + CURRENT_READER1 + " TEXT,"
                    + C_COST1 + " TEXT,"
                    + C_COST_VAL1 + " TEXT,"
                    + SERVICE1 + " TEXT,"
                    + REQ_VALUE1 + " TEXT,"
                    + READER_DATE1 + " TEXT,"
                    + INV_TYPE1 + " TEXT,"
                    + INV_NO1 + " TEXT,"
                    + NET_VAL1 + " TEXT,"
                    + TAX_VAL1 + " TEXT,"
                    + GRET1 + " TEXT,"
                    + REMARKS1 + " TEXT,"
                    + CONSUMPTION1 + " TEXT,"
                    + CREDIT1 + " TEXT,"
                    + IS_POST1 + " TEXT,"
                    + IS_PER1 + " TEXT,"
                    + BDLVAL1 + " TEXT,"
                    + IS_EXPORT1 + " TEXT" + ")";
            db.execSQL(CREATE_VOUCHERS_TABLE);

        }catch (Exception e){
            Log.e("upgrade","Ex ... VOUCHER TABLE");
        }

        try{

            String CREATE_SETTING_TABLE = "CREATE TABLE " + SETTING_TABLE + "("
                    + IP_ADDRESS3 + " TEXT,"
                    + COMPANY_NAME3 + " TEXT,"
                    + ACC_NO3 + " TEXT,"
                    + TAX_NO3 + " TEXT,"
                    + LOGO3 + " BLOB" + ")";
            db.execSQL(CREATE_SETTING_TABLE);

        }catch(Exception ex){
            Log.e("upgrade","Ex ... SETTING_TABLE");
        }


    }

    // **************************************************** Adding ****************************************************

    public void addCustomer(Customer customer) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNTER_NO, customer.getCounterNo());
        contentValues.put(ACC_NO, customer.getAccNo());
        contentValues.put(CUSTOMER_NAME, customer.getCustName());
        contentValues.put(LAST_READER, customer.getLastRead());
        contentValues.put(GAS_PRESSURE, customer.getGasPressure());
        //contentValues.put(CREDIT, customer.getCredet());
        contentValues.put(G_PRICE, customer.getgPrice());
        contentValues.put(PRJECT_NAME, customer.getProjectName());
        contentValues.put(IS_PER, customer.getIsPer());
        contentValues.put(BDLVAL, customer.getBadalVal());
        contentValues.put(CUSTSTS, customer.getCustSts());

        db.insert(CUSTOMER_TABLE, null, contentValues);
        db.close();
    }


    public void addVoucher(Voucher voucher) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNT_NO, voucher.getCounterNo());
        contentValues.put(CUST_NAME, voucher.getCustName());
        contentValues.put(PREVIOUS_READ, voucher.getPreviousRead());
        contentValues.put(CURRENT_READ, voucher.getCurrentRead());
        contentValues.put(CONSUMING, voucher.getConsuming());
        contentValues.put(CONSUMING_VALUE, voucher.getConsumingValue());
        contentValues.put(PREVIOUS_PALANCE, voucher.getPreviousPalance());
        contentValues.put(BADAL_GAS, voucher.getBadalGas());
        contentValues.put(BADAL_SERVICES, voucher.getBadalService());
        contentValues.put(SERVICE_NO_TAX, voucher.getServiceNoTax());
        contentValues.put(NET, voucher.getNet());
        contentValues.put(TAX, voucher.getTax());
        contentValues.put(CURRENT_CONSUMING, voucher.getCurrentConsuming());
        contentValues.put(LAST_VALUE, voucher.getLastValue());
        contentValues.put(NOTE, voucher.getNote());

        db.insert(VOUCHERS_TABLE, null, contentValues);
        db.close();
    }


    public void addVouchers(VoucherModle voucher) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNT_NO1, voucher.getCounterNo());
        contentValues.put(CUST_NAME1, voucher.getCustomerName());
        contentValues.put(last_READ1, voucher.getLastReader());
        contentValues.put(ACC_NO1, voucher.getAccNo());
        contentValues.put(GAS_PRESSURE1, voucher.getGasPressure());
        contentValues.put(PROJECT_NAME1, voucher.getProjectName());
        contentValues.put(PARAMETER1, voucher.getRemarks());
        contentValues.put(CURRENT_READER1, voucher.getCurrentReader());
        contentValues.put(C_COST1, voucher.getCCost());
        contentValues.put(C_COST_VAL1, voucher.getcCostVal());
        contentValues.put(SERVICE1, voucher.getService());
        contentValues.put(REQ_VALUE1, voucher.getReQalValue());
        contentValues.put(READER_DATE1, voucher.getReaderDate());
        contentValues.put(INV_TYPE1, voucher.getInvoiceType());
        contentValues.put(INV_NO1, voucher.getInvoiceNo());
        contentValues.put(NET_VAL1, voucher.getNetValue());
        contentValues.put(TAX_VAL1, voucher.getTaxValue());
        contentValues.put(GRET1, voucher.getGret());
        contentValues.put(REMARKS1, voucher.getRemarks());
        contentValues.put(CONSUMPTION1, voucher.getConsumption());
        contentValues.put(CREDIT1, voucher.getCredit());
        contentValues.put(IS_POST1, voucher.getIsPost());
        contentValues.put(IS_PER1, voucher.getIsPer());
        contentValues.put(BDLVAL1, voucher.getAllowance());
        contentValues.put(IS_EXPORT1, voucher.getIsExport());

        db.insert(VOUCHERS, null, contentValues);
        db.close();
    }

    public void addReceipt(Receipts receipt) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECEIPT_NO2, receipt.getReceiptNo());
        contentValues.put(CUST_NAME2, receipt.getCustName());
        contentValues.put(PROJECT_NAME2, receipt.getProjectName());
        contentValues.put(CREDIT2, receipt.getCredit());
        contentValues.put(ACCOUNT_NO2, receipt.getAccNo());
        contentValues.put(COUNTER_NO2, receipt.getCounterNo());
        contentValues.put(VALUE2, receipt.getValue());
        contentValues.put(NOTE2, receipt.getNote());

        db.insert(RECEIPT_TABLE, null, contentValues);
        db.close();
    }



    public void addRecCash(RecCash receipt ) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(RECNO2, receipt.getResNo());
        values.put(ACCNAME2, receipt.getAccName());
        values.put(ACCNO2, receipt.getAccNo());
        values.put(CASH2, receipt.getCash());
        values.put(REMARKS2, receipt.getRemarks());
        values.put(RECDATE2, receipt.getRecDate());
        values.put(IS_POST2, receipt.getIs_Post());
        values.put(PRJNAME2, receipt.getProjectName());
        values.put(IS_EXPORT2, receipt.getProjectName());

        db.insert(RECCASH_TABLE, null, values);
        db.close();
    }

    public void addMaxSerial(MaxSerial maxSerial) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SERIAL_MAXVAL, maxSerial.getSerialMax());
        contentValues.put(SERIAL_COLMAX, maxSerial.getColomMax());

        db.insert(SERIAL_TABLE, null, contentValues);
        db.close();
    }

//    public void addRecCash(RecCash receipt) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//
//        contentValues.put(RECNO, receipt.getRECNO());
//        contentValues.put(ACCNAME, receipt.getACCNAME());
//        contentValues.put(ACCNO, receipt.getACCNO());
//        contentValues.put(CASH, receipt.getCASH());
//        contentValues.put(REMARKS, receipt.getREMARKS());
//        contentValues.put(RECDATE, receipt.getRECDATE());
//        contentValues.put(IS_POST, receipt.getIS_POST());
//        contentValues.put(PRJNAME, receipt.getPRJNAME());
//
//        db.insert(RECCASH_TABLE, null, contentValues);
//        db.close();
//    }

    public void addUser(Users user) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, user.getUserName());
        contentValues.put(PASSWORD, user.getPassword());

        db.insert(USERS_TABLE, null, contentValues);
        db.close();
    }

    public void addRemark(Remarks remark) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, remark.getTitle());
        contentValues.put(BODY, remark.getBody());

        db.insert(REMARKS_TABLE, null, contentValues);
        db.close();
    }


    public void addSetting(SettingModle settingModle) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        byte[] byteImage = {};
        if (settingModle.getLogo() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            settingModle.getLogo().compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteImage = stream.toByteArray();
        }

        contentValues.put(IP_ADDRESS3, settingModle.getIpAddress());
        contentValues.put(COMPANY_NAME3, settingModle.getCompanyName());
        contentValues.put(ACC_NO3, settingModle.getAccNo());
        contentValues.put(TAX_NO3, settingModle.getTaxNo());
        contentValues.put(LOGO3, byteImage);


        db.insert(SETTING_TABLE, null, contentValues);
        db.close();
    }



    // **************************************************** Getting ****************************************************

//    public List<Customer> getAllCustomers() {
//        List<Customer> customerList = new ArrayList<>();
//
//        String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE ;
//        db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Customer customer = new Customer();
//
//                customer.setCounterNo(cursor.getString(0));
//                customer.setAccNo(cursor.getString(1));
//                customer.setCustName(cursor.getString(2));
//                customer.setLastRead(Double.parseDouble(cursor.getString(3)));
//                customer.setGasPressure(Double.parseDouble(cursor.getString(4)));
//                customer.setCredet(Double.parseDouble(cursor.getString(5)));
//                customer.setgPrice(Double.parseDouble(cursor.getString(6)));
//                customer.setProjectName(cursor.getString(7));
//                customer.setIsPer(Integer.parseInt(cursor.getString(8)));
//                customer.setBadalVal(Double.parseDouble(cursor.getString(9)));
//                customer.setCustSts(Integer.parseInt(cursor.getString(10)));
//
//                customerList.add(customer);
//            } while (cursor.moveToNext());
//        }
//        return customerList;
//    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customerList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setCounterNo(cursor.getString(0));
                customer.setAccNo(cursor.getString(1));
                customer.setCustName(cursor.getString(2));
                customer.setLastRead(Double.parseDouble(cursor.getString(3)));
                customer.setGasPressure(Double.parseDouble(cursor.getString(4)));
                try {
                    customer.setCredet(Double.parseDouble(cursor.getString(5)));
                }catch (Exception e){
                    customer.setCredet(0.0);
                }
                customer.setgPrice(Double.parseDouble(cursor.getString(6)));
                customer.setProjectName(cursor.getString(7));
                customer.setIsPer(Integer.parseInt(cursor.getString(8)));
                customer.setBadalVal(Double.parseDouble(cursor.getString(9)));
                customer.setCustSts(Integer.parseInt(cursor.getString(10)));

                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        return customerList;
    }


    public ArrayList<VoucherModle> getAllVouchers() {
        ArrayList<VoucherModle> customerList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + VOUCHERS ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                VoucherModle voucherModle = new VoucherModle();


                voucherModle.setCounterNo(cursor.getString(0));
                voucherModle.setCustomerName(cursor.getString(1));
                voucherModle.setLastReader(cursor.getString(2));
                voucherModle.setAccNo(cursor.getString(3));
                voucherModle.setGasPressure(cursor.getString(4));
                voucherModle.setProjectName(cursor.getString(5));
                voucherModle.setPrameter(cursor.getString(6));
                voucherModle.setCurrentReader(cursor.getString(7));
                voucherModle.setCCost(cursor.getString(8));
                voucherModle.setcCostVal(cursor.getString(9));
                voucherModle.setService(cursor.getString(10));

                voucherModle.setReQalValue(cursor.getString(11));
                voucherModle.setReaderDate(cursor.getString(12));
                voucherModle.setInvoiceType(cursor.getString(13));
                voucherModle.setInvoiceNo(cursor.getString(14));
                voucherModle.setNetValue(cursor.getString(15));
                voucherModle.setTaxValue(cursor.getString(16));
                voucherModle.setGret(cursor.getString(17));
                voucherModle.setRemarks(cursor.getString(18));
                voucherModle.setConsumption(cursor.getString(19));
                voucherModle.setCredit(cursor.getString(20));
                voucherModle.setIsPost(cursor.getString(21));
                voucherModle.setIsPer(cursor.getString(22));
                voucherModle.setAllowance(cursor.getString(23));
                voucherModle.setIsExport(cursor.getString(24));


                customerList.add(voucherModle);
            } while (cursor.moveToNext());
        }
        return customerList;
    }

    public Customer getCustomer(String counterNo) {
        Customer customer = new Customer();;

        String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE + " where COUNTER_NO = '" + counterNo + "'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                customer.setCounterNo(cursor.getString(0));
                customer.setAccNo(cursor.getString(1));
                customer.setCustName(cursor.getString(2));
                customer.setLastRead(Double.parseDouble(cursor.getString(3)));
                customer.setGasPressure(Double.parseDouble(cursor.getString(4)));
                try {
                    customer.setCredet(Double.parseDouble(cursor.getString(5)));
                }catch (Exception e){
                    customer.setCredet(0.0);
                }
                customer.setgPrice(Double.parseDouble(cursor.getString(6)));
                customer.setProjectName(cursor.getString(7));
                customer.setIsPer(Integer.parseInt(cursor.getString(8)));
                customer.setBadalVal(Double.parseDouble(cursor.getString(9)));
                customer.setCustSts(Integer.parseInt(cursor.getString(10)));

            } while (cursor.moveToNext());
        }
        return customer;
    }

    public SettingModle getSetting() {
        SettingModle settingModle = new SettingModle();;

        String selectQuery = "SELECT  * FROM " + SETTING_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                settingModle.setIpAddress(cursor.getString(0));
                settingModle.setCompanyName(cursor.getString(1));
                settingModle.setAccNo(cursor.getString(2));
                settingModle.setTaxNo(cursor.getString(3));

                try {
                    settingModle.setLogo(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
                }catch (Exception e){
                    Log.e("SettingLogo","Exception"+e);
                }

            } while (cursor.moveToNext());
        }
        return settingModle;
    }

    public List<MaxSerial> getMaxSerial() {
        List<MaxSerial> customerList=new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SERIAL_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MaxSerial customer = new MaxSerial();
                customer.setColomMax(cursor.getString(0));
                customer.setSerialMax(cursor.getString(1));

                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        return customerList;
    }




    public List<RecCash> getRecCash() {
        List<RecCash> recCashList=new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + RECCASH_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                RecCash recCash = new RecCash();
                recCash.setResNo(cursor.getString(0));
                recCash.setAccName(cursor.getString(1));
                recCash.setAccNo(cursor.getString(2));
                recCash.setCash(cursor.getString(3));
                recCash.setRemarks(cursor.getString(4));
                recCash.setRecDate(cursor.getString(5));
                recCash.setIs_Post(cursor.getString(6));
                recCash.setIsExport(cursor.getString(7));
                recCash.setProjectName(cursor.getString(8));

            } while (cursor.moveToNext());
        }
        return recCashList;
    }


    public List<Remarks> getAllRemark() {
        List<Remarks> remarksList=new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + REMARKS_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Remarks remarks = new Remarks();
                remarks.setTitle(cursor.getString(0));
                remarks.setBody(cursor.getString(1));

                remarksList.add(remarks);
            } while (cursor.moveToNext());
        }
        return remarksList;
    }


    public void updateTableBundles(String bundleNo) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//
//        values.put(BUNDLE_INFO_ORDERED, 1);
//        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    // **************************************************** Delete ****************************************************

    public void deleteRemark()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + REMARKS_TABLE);
        db.close();
    }

    public void deleteCustomer()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CUSTOMER_TABLE);
        db.close();
    }

    public void deleteAllSetting() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + SETTING_TABLE);
        db.close();
    }

    public ArrayList<String> getAllCounter() {
        ArrayList<String> contrList=new ArrayList<>();
        String selectQuery = "SELECT  COUNTER_NO FROM " + CUSTOMER_TABLE ;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                contrList.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        Log.e("contrList",""+contrList.size());
        return contrList;

    }
    public ArrayList<Customer> getListCustomer(String counterNo) {
        ArrayList<Customer> customerList = new ArrayList<>();

//      SELECT  * FROM CUSTOMER   where COUNTER_NO like '%11949%'

        String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE + " where COUNTER_NO like +'%'+'" + counterNo + "'+'%' ";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setCounterNo(cursor.getString(0));
                customer.setAccNo(cursor.getString(1));
                customer.setCustName(cursor.getString(2));
                customer.setLastRead(Double.parseDouble(cursor.getString(3)));
                customer.setGasPressure(Double.parseDouble(cursor.getString(4)));
                try {
                    customer.setCredet(Double.parseDouble(cursor.getString(5)));
                }catch (Exception e){
                    customer.setCredet(0.0);
                }
                customer.setgPrice(Double.parseDouble(cursor.getString(6)));
                customer.setProjectName(cursor.getString(7));
                customer.setIsPer(Integer.parseInt(cursor.getString(8)));
                customer.setBadalVal(Double.parseDouble(cursor.getString(9)));
                customer.setCustSts(Integer.parseInt(cursor.getString(10)));
                customerList.add(customer);

            } while (cursor.moveToNext());
        }
        Log.e("customer",""+customerList.size());
        return customerList;
    }
}
