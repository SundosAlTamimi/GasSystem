package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Remarks;
import com.falconssoft.gassystem.Modle.Users;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
//*******************************
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderView;
//*******************************

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout voucher , rePrint , receipt , edit , settings,linearVoucher,linearRecipt;

    ImageView voucherImg , rePrintImg , receiptImg , editImg , settingsImg;

    private ScaleAnimation scale;
    private List<Customer> customers;
    private List<Users> users;
    private List<Remarks> remarks;
    private Toolbar toolbar;
    SliderLayout sliderLayout;
    FloatingActionButton add_voucher,add_recipt;
    Animation animation,animation2;
    DatabaseHandler databaseHandler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        init();

        setSupportActionBar(toolbar);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_left);
        linearVoucher.startAnimation(animation);
        linearRecipt.startAnimation(animation2);
        databaseHandler=new DatabaseHandler(MainActivity.this);

        add_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,MakeVoucher.class);
                startActivity(i);
            }
        });

        add_recipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Receipt.class);
                startActivity(i);
            }
        });
//        sliderLayout =(SliderLayout)findViewById(R.id.imageSlider_2);
//        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
        setSliderViews();
        setSliderViews();


        customers = new ArrayList<>();
        users = new ArrayList<>();
        remarks = new ArrayList<>();
        new JSONTask().execute();
        new JSONTask2().execute();
//

//        animate(voucher , 700 , 0.8f);
//        animate(rePrint , 600 , 0.5f);
//        animate(receipt , 800 , 0.7f);
//        animate(edit , 800 , 0.7f);
//        animate(settings , 700 , 0.8f);
//
//        voucher.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    voucherImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.voucher));
//                    Intent intent = new Intent(MainActivity.this , MakeVoucher.class);
//                    startActivity(intent);
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    voucherImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.voucher_hover));
//                }
//                return true;
//            }
//        });
//
//        rePrint.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    rePrintImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.printer));
//                    Intent intent = new Intent(MainActivity.this , Reprint.class);
//                    startActivity(intent);
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    rePrintImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.printer_hover));
//                }
//                return true;
//            }
//        });
//
//        receipt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    receiptImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recipt));
//                    Intent intent = new Intent(MainActivity.this , Receipt.class);
//                    startActivity(intent);
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    receiptImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recept_hover));
//                }
//                return true;
//            }
//        });
//
//        edit.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    editImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit));
//                    Intent intent = new Intent(MainActivity.this , Edit.class);
//                    startActivity(intent);
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    editImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_hover));
//                }
//                return true;
//            }
//        });
//
//        settings.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    settingsImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.setting));
//                    Intent intent = new Intent(MainActivity.this , Setting.class);
//                    startActivity(intent);
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    settingsImg.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.setting_hover));
//                }
//                return true;
//            }
//        });
//
//
//    }

//    public void animate(View view , int duration , float pivotXValue){
//        scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.INFINITE, pivotXValue, ScaleAnimation.RELATIVE_TO_SELF, .8f);
//        scale.setStartOffset(400);
//        scale.setDuration(duration);
//        scale.setInterpolator(new OvershootInterpolator());
//        view.startAnimation(scale);
    }
//
    private class JSONTask extends AsyncTask<String, String, List<Customer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Customer> doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL("http://10.0.0.214/GAS_WEB_SERVICE/import.php?FLAG=1");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String finalJson = sb.toString();
                Log.e("finalJson*********", finalJson);

                JSONObject parentObject = new JSONObject(finalJson);

                try {
                    JSONArray parentArrayOrders = parentObject.getJSONArray("CUSTOMER_GAS");
                    customers.clear();
                    databaseHandler.deleteCustomer();
                    for (int i = 0; i < parentArrayOrders.length(); i++) {
                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);

                        Customer customer = new Customer();
                        customer.setCounterNo(finalObject.getString("COUNTERNO"));
                        customer.setAccNo(finalObject.getString("ACCNO"));
                        customer.setCustName(ReturnArabic(finalObject.getString("CUSTOMERNAME")));
                        customer.setGasPressure(finalObject.getDouble("GASPRESSURE"));
                        customer.setgPrice(finalObject.getDouble("GPRICE"));
                        customer.setProjectName(ReturnArabic(finalObject.getString("PRJECTNAME")));
                        customer.setIsPer(finalObject.getInt("IS_PER"));
                        customer.setBadalVal(finalObject.getDouble("BDLVAL"));
                        //customer.set(finalObject.getDouble("CREDIT"));
                        customer.setCustSts(finalObject.getInt("CUSTSTS"));
                        customer.setLastRead(finalObject.getDouble("LASTREADER"));

                        Log.e("*****", customer.getCustName());
                        customers.add(customer);
                        databaseHandler.addCustomer(customer);
                    }


                } catch (JSONException e) {
                    Log.e("Import Data1", e.getMessage().toString());
                }

                /*try {
                    JSONArray parentArrayOrders = parentObject.getJSONArray("GAS_USERS");
                    users.clear();
                    for (int i = 0; i < parentArrayOrders.length(); i++) {
                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);

                        Users user = new Users();
                        user.setUserName(finalObject.getString("USER_NAME"));
                        user.setPassword(finalObject.getString("PASSWORD"));

                        users.add(user);
                    }
                } catch (JSONException e) {
                    Log.e("Import Data2", e.getMessage().toString());
                }

                 */

//                try {
//                    JSONArray parentArrayOrders = parentObject.getJSONArray("GAS_REMARKS");
//                    remarks.clear();
//                    databaseHandler.deleteRemark();
//                    for (int i = 0; i < parentArrayOrders.length(); i++) {
//                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);
//
//                        Remarks remark = new Remarks();
//                        remark.setTitle(ReturnArabic(finalObject.getString("REMARKTITLE")));
//                        remark.setBody(ReturnArabic(finalObject.getString("REMARKBODY")));
//
//                        remarks.add(remark);
//                        databaseHandler.addRemark(remark);
//                        Log.e("ImportRemark", remark.getBody());
//
//                    }
//                } catch (JSONException e) {
//                    Log.e("Import Data3", e.getMessage().toString());
//                }

            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", "********ex1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("IOException", e.getMessage().toString());
                e.printStackTrace();

            } catch (JSONException e) {
                Log.e("JSONException", "********ex3  " + e.toString());
                e.printStackTrace();
            } finally {
                Log.e("finally", "********finally");
                if (connection != null) {
                    Log.e("connection", "********ex4");
                    // connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return customers;
        }


        @Override
        protected void onPostExecute(final List<Customer> result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.e("result", "*****************" + customers.size());
//                storeInDatabase();
            } else {
                Toast.makeText(MainActivity.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class JSONTask2 extends AsyncTask<String, String, List<Customer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Customer> doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL("http://10.0.0.214/GAS_WEB_SERVICE/import.php?FLAG=3");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String finalJson = sb.toString();
                Log.e("finalJson*********", finalJson);

                JSONObject parentObject = new JSONObject(finalJson);

//                try {
//                    JSONArray parentArrayOrders = parentObject.getJSONArray("CUSTOMER_GAS");
//                    customers.clear();
//                    databaseHandler.deleteCustomer();
//                    for (int i = 0; i < parentArrayOrders.length(); i++) {
//                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);
//
//                        Customer customer = new Customer();
//                        customer.setCounterNo(finalObject.getString("COUNTERNO"));
//                        customer.setAccNo(finalObject.getString("ACCNO"));
//                        customer.setCustName(ReturnArabic(finalObject.getString("CUSTOMERNAME")));
//                        customer.setGasPressure(finalObject.getDouble("GASPRESSURE"));
//                        customer.setgPrice(finalObject.getDouble("GPRICE"));
//                        customer.setProjectName(ReturnArabic(finalObject.getString("PRJECTNAME")));
//                        customer.setIsPer(finalObject.getInt("IS_PER"));
//                        customer.setBadalVal(finalObject.getDouble("BDLVAL"));
//                        //customer.set(finalObject.getDouble("CREDIT"));
//                        customer.setCustSts(finalObject.getInt("CUSTSTS"));
//                        customer.setLastRead(finalObject.getDouble("LASTREADER"));
//
//                        Log.e("*****", customer.getCustName());
//                        customers.add(customer);
//                        databaseHandler.addCustomer(customer);
//                    }
//
//
//                } catch (JSONException e) {
//                    Log.e("Import Data1", e.getMessage().toString());
//                }

                /*try {
                    JSONArray parentArrayOrders = parentObject.getJSONArray("GAS_USERS");
                    users.clear();
                    for (int i = 0; i < parentArrayOrders.length(); i++) {
                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);

                        Users user = new Users();
                        user.setUserName(finalObject.getString("USER_NAME"));
                        user.setPassword(finalObject.getString("PASSWORD"));

                        users.add(user);
                    }
                } catch (JSONException e) {
                    Log.e("Import Data2", e.getMessage().toString());
                }

                 */

                try {
                    JSONArray parentArrayOrders = parentObject.getJSONArray("GAS_REMARKS");
                    remarks.clear();
                    databaseHandler.deleteRemark();
                    for (int i = 0; i < parentArrayOrders.length(); i++) {
                        JSONObject finalObject = parentArrayOrders.getJSONObject(i);

                        Remarks remark = new Remarks();
                        remark.setTitle(ReturnArabic(finalObject.getString("REMARKTITLE")));
                        remark.setBody(ReturnArabic(finalObject.getString("REMARKBODY")));

                        remarks.add(remark);
                        databaseHandler.addRemark(remark);
                        Log.e("ImportRemark", remark.getBody());

                    }
                } catch (JSONException e) {
                    Log.e("Import Data3", e.getMessage().toString());
                }

            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", "********ex1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("IOException", e.getMessage().toString());
                e.printStackTrace();

            } catch (JSONException e) {
                Log.e("JSONException", "********ex3  " + e.toString());
                e.printStackTrace();
            } finally {
                Log.e("finally", "********finally");
                if (connection != null) {
                    Log.e("connection", "********ex4");
                    // connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return customers;
        }


        @Override
        protected void onPostExecute(final List<Customer> result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.e("result", "*****************" + customers.size());
//                storeInDatabase();
            } else {
                Toast.makeText(MainActivity.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void storeInDatabase(){

        DatabaseHandler handler = new DatabaseHandler(MainActivity.this);

        for(int i = 0 ; i<customers.size() ; i++) {
            handler.addCustomer(customers.get(i));
        }

       /* for(int i = 0 ; i<users.size() ; i++) {
            handler.addUser(users.get(i));
        }
*/
        for(int i = 0 ; i<remarks.size() ; i++) {
            handler.addRemark(remarks.get(i));
        }


    }
//
//
    void init(){
        voucher = findViewById(R.id.voucher);
        rePrint = findViewById(R.id.reprint);
        receipt = findViewById(R.id.receipt);
        edit = findViewById(R.id.edit);
        settings = findViewById(R.id.settings);

        voucherImg = findViewById(R.id.voucher_image);
        rePrintImg = findViewById(R.id.reprint_image);
        receiptImg = findViewById(R.id.receipt_image);
        editImg = findViewById(R.id.edit_image);
        settingsImg = findViewById(R.id.settings_image);
        toolbar=findViewById(R.id.toolBar);
        add_voucher=(FloatingActionButton)findViewById(R.id.fab_addVoucher);
        add_recipt=(FloatingActionButton)findViewById(R.id.fab_add_recipt);
        sliderLayout = findViewById(R.id.imageSlider_2);
        linearVoucher=findViewById(R.id.linear_voucher);
        linearRecipt=findViewById(R.id.linear_reccipt);
    }


    private void setSliderViews() {
        for (int i = 0; i < 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);
//            sliderView.setImageScaleType(ImageView.ScaleType.FIT_END);
            switch (i) {
                case 0:

                    sliderView.setImageDrawable(R.drawable.logo_gold);

                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.gasfactory);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.gas);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.images_gasaddad);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("The Gas System produst by Falcons Soft Companey." +
                    "  " + (i + 1));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  true;
    }


    public String ReturnArabic(String PAR)  // By Mohammad Alamawi
    {
        String sresult = "";
        char x;
        int[] t = new int[PAR.length()] ;
        for(int i = 0; i < PAR.length(); i++) {
            x = PAR.charAt(i);
            int z = (int) x;
            t[i] = z;

            if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z'))
            {
                sresult += x ;
            }
            else {
                switch (t[i]) {
                    case 32: {
                        sresult += " ";
                        break;
                    }
                    case 33: {
                        sresult += "!";
                        break;
                    }
                    case 34: {
                        sresult += "\"";
                        break;
                    }
                    case 35: {
                        sresult += "#";
                        break;
                    }
                    case 36: {
                        sresult += "$";
                        break;
                    }
                    case 37: {
                        sresult += "%";
                        break;
                    }
                    case 38: {
                        sresult += "&";
                        break;
                    }
                    case 39: {
                        sresult += "'";
                        break;
                    }
                    case 40: {
                        sresult += "(";
                        break;
                    }
                    case 41: {
                        sresult += ")";
                        break;
                    }
                    case 42: {
                        sresult += "*";
                        break;
                    }
                    case 43: {
                        sresult += "+";
                        break;
                    }
                    case 44: {
                        sresult += ",";
                        break;
                    }
                    case 45: {
                        sresult += "-";
                        break;
                    }
                    case 46: {
                        sresult += ".";
                        break;
                    }
                    case 58: {
                        sresult += ":";
                        break;
                    }
                    case 59: {
                        sresult += ";";
                        break;
                    }
                    case 60: {
                        sresult += "<";
                        break;
                    }
                    case 61: {
                        sresult += "=";
                        break;
                    }
                    case 62: {
                        sresult += ">";
                        break;
                    }
                    case 63: {
                        sresult += "?";
                        break;
                    }
                    case 64: {
                        sresult += "@";
                        break;
                    }
                    case 91: {
                        sresult += "[";
                        break;
                    }
                    case 92: {
                        sresult += "\\";
                        break;
                    }
                    case 93: {
                        sresult += "]";
                        break;
                    }
                    case 94: {
                        sresult += "^";
                        break;
                    }
                    case 95: {
                        sresult += "_";
                        break;
                    }
                    case 96: {
                        sresult += "`";
                        break;
                    }
                    case 215: {
                        sresult += "×";
                        break;
                    }
                    case 247: {
                        sresult += "÷";
                        break;
                    }
                    case 236: {
                        sresult += "ى";
                        break;
                    }
                    case 240: {
                        sresult += "ً";
                        break;
                    }
                    case 241: {
                        sresult += "ٌ";
                        break;
                    }
                    case 242: {
                        sresult += "ٍ";
                        break;
                    }
                    case 243: {
                        sresult += "َ";
                        break;
                    }
                    case 245: {
                        sresult += "ُ";
                        break;
                    }
                    case 246: {
                        sresult += "ِ";
                        break;
                    }
                    case 248: {
                        sresult += "ّ";
                        break;
                    }
                    case 250: {
                        sresult += "ْ";
                        break;
                    }


                    //Number
                    case 48: {
                        sresult += "0";
                        break;
                    }
                    case 49: {
                        sresult += "1";
                        break;
                    }
                    case 50: {
                        sresult += "2";
                        break;
                    }
                    case 51: {
                        sresult += "3";
                        break;
                    }
                    case 52: {
                        sresult += "4";
                        break;
                    }
                    case 53: {
                        sresult += "5";
                        break;
                    }
                    case 54: {
                        sresult += "6";
                        break;
                    }
                    case 55: {
                        sresult += "7";
                        break;
                    }
                    case 56: {
                        sresult += "8";
                        break;
                    }
                    case 57: {
                        sresult += "9";
                        break;
                    }

                    // Arabic
                    case 199: {
                        sresult += "ا";
                        break;
                    }
                    case 200: {
                        sresult += "ب";
                        break;
                    }
                    case 201: {
                        sresult += "ة";
                        break;
                    }
                    case 202: {
                        sresult += "ت";
                        break;
                    }
                    case 203: {
                        sresult += "ث";
                        break;
                    }
                    case 204: {
                        sresult += "ج";
                        break;
                    }
                    case 205: {
                        sresult += "ح";
                        break;
                    }
                    case 206: {
                        sresult += "خ";
                        break;
                    }
                    case 207: {
                        sresult += "د";
                        break;
                    }
                    case 208: {
                        sresult += "ذ";
                        break;
                    }
                    case 209: {
                        sresult += "ر";
                        break;
                    }
                    case 210: {
                        sresult += "ز";
                        break;
                    }
                    case 211: {
                        sresult += "س";
                        break;
                    }
                    case 212: {
                        sresult += "ش";
                        break;
                    }
                    case 213: {
                        sresult += "ص";
                        break;
                    }
                    case 214: {
                        sresult += "ض";
                        break;
                    }
                    case 216: {
                        sresult += "ط";
                        break;
                    }
                    case 217: {
                        sresult += "ظ";
                        break;
                    }
                    case 218: {
                        sresult += "ع";
                        break;
                    }
                    case 219: {
                        sresult += "غ";
                        break;
                    }
                    case 221: {
                        sresult += "ف";
                        break;
                    }
                    case 222: {
                        sresult += "ق";
                        break;
                    }
                    case 223: {
                        sresult += "ك";
                        break;
                    }
                    case 225: {
                        sresult += "ل";
                        break;
                    }
                    case 227: {
                        sresult += "م";
                        break;
                    }
                    case 228: {
                        sresult += "ن";
                        break;
                    }
                    case 229: {
                        sresult += "ه";
                        break;
                    }
                    case 230: {
                        sresult += "و";
                        break;
                    }
                    case 237: {
                        sresult += "ي";
                        break;
                    }
                    case 193: {
                        sresult += "ء";
                        break;
                    }
                    case 194: {
                        sresult += "آ";
                        break;
                    }
                    case 195: {
                        sresult += "أ";
                        break;
                    }
                    case 196: {
                        sresult += "ؤ";
                        break;
                    }
                    case 197: {
                        sresult += "إ";
                        break;
                    }
                    case 198: {
                        sresult += "ئ";
                        break;
                    }
                }
            }
        }
        return  sresult;
    }
}
