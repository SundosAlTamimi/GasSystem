package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Voucher;

import java.text.DecimalFormat;
import java.util.List;

public class MakeVoucher extends AppCompatActivity {

    Button note, save, search, yes, no, done, cancel, cancelButton;
    LinearLayout black, black2, dialog, noteDialog, linear, linearmain, total_linear;
    EditText counterNo, currentRead, gasReturn, serviceReturn;
    TextView custNo, previousRead, consuming, consumingValue, previousPalance, taxService, net, tax,
            currentConsuming, lastValue, noteTextView;
    DatabaseHandler DHandler;
    double gasPressure = 0;
    double gasPrice = 0;
    double consumingV = 0;

    private Animation animation;
    DecimalFormat threeDForm;
    String noteText = "";
    private Toolbar toolbar;
    public  static Voucher voucherGas;
    public static Customer customer;

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_voucher_new);
        init();

        setSupportActionBar(toolbar);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
        linearmain.startAnimation(animation);
        total_linear.startAnimation(animation);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp); // Set the icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeVoucher.this, MainActivity.class);
                startActivity(i);
            }
        });

        threeDForm = new DecimalFormat("#.###");

        DHandler = new DatabaseHandler(MakeVoucher.this);
//        DHandler.addCustomer(new Customer("24458","2225","ahmad",215,0.254,20,10,"al-yas",1,0,1));
//        DHandler.addCustomer(new Customer("2441","2225","ahmad",215,0.254,20,10,"al-yas",1,0,1));
//        DHandler.addCustomer(new Customer("2442","2221","ahmad",211,0.254,20,10,"al-yas",1,0,1));
//        DHandler.addCustomer(new Customer("2443","2223","ahmad",2233,0.254,20,10,"al-yas",1,0,1));
//        DHandler.addCustomer(new Customer("2444","2224","ahmad",2054,0.254,20,10,"al-yas",1,0,1));

//        DHandler.addCustomer(new Customer("4444","244454","GAS",90,1.037,0,538.880,"al-yas",1,0,1));

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
                clearText();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!counterNo.getText().toString().equals("")) {

                    Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                    if (customer.getCounterNo() != null) {

                        custNo.setText(customer.getCustName());
                        previousRead.setText("" + customer.getLastRead());
                        previousPalance.setText("" + customer.getCredet());
                        serviceReturn.setText("" + customer.getBadalVal());

                        gasPressure = customer.getGasPressure();
                        gasPrice = customer.getgPrice();

                    } else
                        Toast.makeText(MakeVoucher.this, "رقم العداد غير موجود", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(MakeVoucher.this, "ادخل رقم العداد", Toast.LENGTH_LONG).show();
            }
        });

        note.setOnTouchListener(onTouchListener);
        done.setOnTouchListener(onTouchListener);
        cancel.setOnTouchListener(onTouchListener);

        yes.setOnTouchListener(onTouchListener);
        no.setOnTouchListener(onTouchListener);
        counterNo.requestFocus();

        currentRead.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                    Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                    String customName = customer.getCustName();
                    if (!TextUtils.isEmpty(customName)) {

                        calculateFunction(customer.getGasPressure(), 1, customer.getgPrice());

                    }

                }


                return false;
            }
        });


        gasReturn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                     customer = DHandler.getCustomer(counterNo.getText().toString());
                    String customName = customer.getCustName();
                    if (!TextUtils.isEmpty(customName)) {

                        calculateFunction(customer.getGasPressure(), 1, customer.getgPrice());

                    }

                }


                return false;
            }
        });
        serviceReturn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                    Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                    String customName = customer.getCustName();
                    if (!TextUtils.isEmpty(customName)) {

                        calculateFunction(customer.getGasPressure(), 1, customer.getgPrice());

                    }

                }


                return false;
            }
        });

        counterNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    clearText();
                    if (!counterNo.getText().toString().equals("")) {

                        Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                        if (customer.getCounterNo() != null) {

                            custNo.setText(customer.getCustName());
                            previousRead.setText("" + customer.getLastRead());
                            previousPalance.setText("" + customer.getCredet());
                            serviceReturn.setText("" + customer.getBadalVal());

                            gasPressure = customer.getGasPressure();
                            gasPrice = customer.getgPrice();
                            currentRead.requestFocus();

//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    currentRead.requestFocus();
//                                }
//                            });


                        } else {
                            Toast.makeText(MakeVoucher.this, "رقم العداد غير موجود", Toast.LENGTH_LONG).show();
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    counterNo.requestFocus();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(MakeVoucher.this, "ادخل رقم العداد", Toast.LENGTH_LONG).show();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                counterNo.requestFocus();
                            }
                        });

                    }
                }


                return false;
            }
        });

    }


    public void Save() {


        if (!TextUtils.isEmpty(currentRead.getText().toString())) {
            if (!TextUtils.isEmpty(gasReturn.getText().toString())) {
                if (!TextUtils.isEmpty(serviceReturn.getText().toString())) {
                    if (!TextUtils.isEmpty(custNo.getText().toString())) {
                        if (!TextUtils.isEmpty(previousRead.getText().toString())) {
                            if (!TextUtils.isEmpty(consuming.getText().toString())) {
                                if (!TextUtils.isEmpty(consumingValue.getText().toString())) {
                                    if (!TextUtils.isEmpty(previousPalance.getText().toString())) {
                                        if (!TextUtils.isEmpty(taxService.getText().toString())) {
                                            if (!TextUtils.isEmpty(net.getText().toString())) {
                                                if (!TextUtils.isEmpty(tax.getText().toString())) {
                                                    if (!TextUtils.isEmpty(currentConsuming.getText().toString())) {
                                                        if (!TextUtils.isEmpty(lastValue.getText().toString())) {


                                                                 voucherGas=new Voucher(
                                                                        counterNo.getText().toString(),
                                                                        custNo.getText().toString(),
                                                                        Double.parseDouble(previousRead.getText().toString()),
                                                                        Double.parseDouble(currentRead.getText().toString()),
                                                                        Double.parseDouble(consuming.getText().toString()),
                                                                        Double.parseDouble(consumingValue.getText().toString()),
                                                                        Double.parseDouble(previousPalance.getText().toString()),
                                                                        Double.parseDouble(gasReturn.getText().toString()),
                                                                        Double.parseDouble(serviceReturn.getText().toString()),
                                                                        Double.parseDouble(taxService.getText().toString()),
                                                                        Double.parseDouble(net.getText().toString()),
                                                                        Double.parseDouble(tax.getText().toString()),
                                                                        Double.parseDouble(currentConsuming.getText().toString()),
                                                                        Double.parseDouble(lastValue.getText().toString()),
                                                                        ""
                                                                );

                                                              SavePrint();

                                                        } else {
                                                            lastValue.setError("Required!");
                                                        }
                                                    } else {
                                                        currentConsuming.setError("Required!");
                                                    }
                                                } else {
                                                    tax.setError("Required!");
                                                }
                                            } else {
                                                net.setError("Required!");
                                            }
                                        } else {
                                            taxService.setError("Required!");
                                        }
                                    } else {
                                        previousPalance.setError("Required!");
                                    }
                                } else {
                                    consumingValue.setError("Required!");
                                }
                            } else {
                                consuming.setError("Required!");
                            }
                        } else {
                            previousRead.setError("Required!");
                        }
                    } else {
                        custNo.setError("Required!");
                    }
                } else {
                    serviceReturn.setError("Required!");
                }
            } else {
                gasReturn.setError("Required!");
            }
        } else {
            currentRead.setError("Required!");
        }


        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();


    }

    private void SavePrint() {

        DHandler.addVoucher(voucherGas);

        counterNo.setText("");
        Intent printExport=new Intent(MakeVoucher.this,BluetoothConnectMenu.class);
        printExport.putExtra("printKey", "0");
        startActivity(printExport);
        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
    }

    private void clearText() {
        currentRead.setText("");
        gasReturn.setText("0.0");
        serviceReturn.setText("0.0");
        custNo.setText("");
        previousRead.setText("");
        consuming.setText("");
        consumingValue.setText("");
        previousPalance.setText("");
        taxService.setText("");
        net.setText("");
        tax.setText("");
        currentConsuming.setText("");
        lastValue.setText("");
//        noteTextView.setText("");

    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (v.getId()) {

                case R.id.note:
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        note.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.note));

                        black2.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
                        black2.startAnimation(animation);

                        noteDialog.setVisibility(View.VISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
                        animation2.setStartTime(300);
                        noteDialog.startAnimation(animation2);

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        note.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.note_hover));
                    }
                    break;

//                case R.id.save:
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
////                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav));
//                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.save_yelow_btn));
//
//                        if (!currentRead.getText().toString().equals("")) {
//                            if (!gasReturn.getText().toString().equals("")) {
//                                if (!serviceReturn.getText().toString().equals("")) {
//
//
//                                    black.setVisibility(View.VISIBLE);
//                                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
//                                    black.startAnimation(animation);
//
//                                    dialog.setVisibility(View.VISIBLE);
//                                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
//                                    animation2.setStartTime(300);
//                                    dialog.startAnimation(animation2);
//
//
//                                } else
//                                    serviceReturn.setError("Required!");
//                            } else
//                                gasReturn.setError("Required!");
//                        } else
//                            currentRead.setError("Required!");
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        save.setBackgroundColor(getResources().getColor(R.color.yellow_hover));
//
////                          save.setBackgroundColor(getResources().getColor(R.color.yellow1));
//                    }
//                    break;

                case R.id.yes:

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        black.setVisibility(View.INVISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                        black.startAnimation(animation);

                        dialog.setVisibility(View.INVISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
                        animation2.setStartTime(300);
                        dialog.startAnimation(animation2);

                        DHandler.addVoucher(new Voucher(
                                counterNo.getText().toString(),
                                custNo.getText().toString(),
                                Double.parseDouble(previousRead.getText().toString()),
                                Double.parseDouble(currentRead.getText().toString()),
                                Double.parseDouble(consuming.getText().toString()),
                                Double.parseDouble(consumingValue.getText().toString()),
                                Double.parseDouble(previousPalance.getText().toString()),
                                Double.parseDouble(gasReturn.getText().toString()),
                                Double.parseDouble(serviceReturn.getText().toString()),
                                Double.parseDouble(taxService.getText().toString()),
                                Double.parseDouble(net.getText().toString()),
                                Double.parseDouble(tax.getText().toString()),
                                Double.parseDouble(currentConsuming.getText().toString()),
                                Double.parseDouble(lastValue.getText().toString()),
                                noteText
                        ));

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    }
                    break;

                case R.id.no:

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        black.setVisibility(View.INVISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                        black.startAnimation(animation);

                        dialog.setVisibility(View.INVISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
                        animation2.setStartTime(300);
                        dialog.startAnimation(animation2);

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    }
                    break;

                case R.id.done:

                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        black2.setVisibility(View.INVISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                        black2.startAnimation(animation);

                        noteDialog.setVisibility(View.INVISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
                        animation2.setStartTime(300);
                        noteDialog.startAnimation(animation2);

                        noteText = noteTextView.getText().toString();

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    }
                    break;

                case R.id.cancel:

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        black2.setVisibility(View.INVISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                        black2.startAnimation(animation);

                        noteDialog.setVisibility(View.INVISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
                        animation2.setStartTime(300);
                        noteDialog.startAnimation(animation2);

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    }
                    break;
            }
            return true;
        }
    };

    void init() {

        note = findViewById(R.id.note);
        save = findViewById(R.id.save);
        search = findViewById(R.id.search);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        done = findViewById(R.id.done);
        cancel = findViewById(R.id.cancel);
        cancelButton = findViewById(R.id.cancel_btn);
        noteTextView = findViewById(R.id.noteText);

        black = findViewById(R.id.black);
        dialog = findViewById(R.id.dialog);
        linearmain = findViewById(R.id.linearmain);
        total_linear = findViewById(R.id.total_layout);

        black2 = findViewById(R.id.black2);
        noteDialog = findViewById(R.id.noteDialog);

        counterNo = findViewById(R.id.counter_no);
        custNo = findViewById(R.id.cust_no);
        previousRead = findViewById(R.id.previous_read);
        currentRead = findViewById(R.id.current_read);
        consuming = findViewById(R.id.consuming);
        consumingValue = findViewById(R.id.consuming_value);
        previousPalance = findViewById(R.id.previous_palance);
        gasReturn = findViewById(R.id.gas_return);
        serviceReturn = findViewById(R.id.service_return);
        taxService = findViewById(R.id.tax_services);
        net = findViewById(R.id.net);
        tax = findViewById(R.id.tax);
        currentConsuming = findViewById(R.id.current_consuming);
        lastValue = findViewById(R.id.last_value);
        toolbar = findViewById(R.id.appBar);

    }

    void calculateFunction(double GP, double COE, double GPRC) {

        double DPR = 0, VOD = 0, DP = 0, NetTotal = 0, TAX = 0, REQVAL = 0, GRV = 0, SRV = 0, TXV = 0, TX = 0, netValue = 0;


        if (!previousRead.getText().toString().equals("") && !currentRead.getText().toString().equals("")) {
            //_____________________________________الاستهلاك ___________________________________
            DPR = Double.parseDouble(currentRead.getText().toString()) - Double.parseDouble(previousRead.getText().toString());
            consuming.setText("" + DPR);
            //_____________________________________قيمة الاستهلاك ___________________________________

            String vo = convertToEnglish(threeDForm.format(((DPR * GP * COE / 0.436) * GPRC) / 1000));
            VOD = Double.parseDouble(vo);
            consumingValue.setText("" + VOD);

            //_____________________________________استهلاك الفتره ___________________________________

            if (!gasReturn.getText().toString().equals("")) {
                GRV = Double.parseDouble(gasReturn.getText().toString());

            } else {
                GRV = 0.0;

            }

            if (!serviceReturn.getText().toString().equals("")) {
                SRV = Double.parseDouble(serviceReturn.getText().toString());
            } else {

                SRV = 0.0;
            }

            netValue = (GRV + SRV);//الصافي
            net.setText("" + netValue);


            TX = Double.parseDouble(convertToEnglish(threeDForm.format((GRV + SRV) * 0.16))); //الضريبه
            tax.setText("" + TX);

            TXV = Double.parseDouble(convertToEnglish(threeDForm.format((GRV + SRV) + TX)));//مجموع بدل خدمات
            taxService.setText("" + TXV);

            DP = Double.parseDouble(convertToEnglish(threeDForm.format(VOD + TXV))); //استهلاك الفتره
            currentConsuming.setText("" + DP);

            NetTotal = Double.parseDouble(convertToEnglish(threeDForm.format(DP + Double.parseDouble(previousPalance.getText().toString())))); //القيمه المطلوبه
            lastValue.setText("" + NetTotal);


        }


    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_voucher_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search_ic) {
            if (!counterNo.getText().toString().equals("")) {

                Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                if (customer.getCounterNo() != null) {

                    custNo.setText(customer.getCustName());
                    previousRead.setText("" + customer.getLastRead());
                    previousPalance.setText("" + customer.getCredet());
                    serviceReturn.setText("" + customer.getBadalVal());

                    gasPressure = customer.getGasPressure();
                    gasPrice = customer.getgPrice();

                } else
                    Toast.makeText(MakeVoucher.this, "رقم العداد غير موجود", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(MakeVoucher.this, "ادخل رقم العداد", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
