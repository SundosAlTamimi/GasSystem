package com.falconssoft.gassystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.MaxSerial;
import com.falconssoft.gassystem.Modle.RecCash;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Remarks;
import com.falconssoft.gassystem.Modle.Users;
import com.falconssoft.gassystem.Modle.Voucher;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 5;
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

    private static final String RECNO = "RECNO";
    private static final String ACCNAME = "ACCNAME";
    private static final String ACCNO = "ACCNO";
    private static final String CASH = "CASH";
    private static final String REMARKS = "REMARKS";
    private static final String RECDATE = "RECDATE";
    private static final String IS_POST = "IS_POST";
    private static final String PRJNAME = "PRJNAME";

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
                + RECNO + " TEXT,"
                + ACCNAME + " TEXT,"
                + ACCNO + " TEXT,"
                + CASH + " TEXT,"
                + REMARKS + " TEXT,"
                + RECDATE + " TEXT,"
                + IS_POST + " TEXT" + ")";
        db.execSQL(CREATE_RECCASH_TABLE);


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

        values.put(RECNO, receipt.getRECNO());
        values.put(ACCNAME, receipt.getACCNAME());
        values.put(ACCNO, receipt.getACCNO());
        values.put(CASH, receipt.getCASH());
        values.put(REMARKS, receipt.getREMARKS());
        values.put(RECDATE, receipt.getRECDATE());
        values.put(IS_POST, receipt.getIS_POST());
        values.put(PRJNAME, receipt.getPRJNAME());

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
                recCash.setRECNO(cursor.getString(0));
                recCash.setACCNAME(cursor.getString(1));
                recCash.setACCNO(cursor.getString(2));
                recCash.setCASH(cursor.getString(3));
                recCash.setREMARKS(cursor.getString(4));
                recCash.setRECDATE(cursor.getString(5));
                recCash.setIS_POST(cursor.getString(6));
                recCash.setPRJNAME(cursor.getString(7));

            } while (cursor.moveToNext());
        }
        return recCashList;
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

}
