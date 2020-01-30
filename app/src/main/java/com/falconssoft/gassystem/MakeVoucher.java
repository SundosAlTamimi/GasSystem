package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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

    Button note, save, search, yes, no, done, cancel;
    LinearLayout black, black2, dialog, noteDialog, linear,linearmain,total_linear;
    EditText counterNo, custNo, previousRead, currentRead, consuming, consumingValue, previousPalance, gasReturn, serviceReturn, taxService, net, tax,
            currentConsuming, lastValue, noteTextView;

    DatabaseHandler DHandler;
    double gasPressure = 0;
    double gasPrice = 0;
    double consumingV = 0;

    private Animation animation;

    String noteText = "";
    private Toolbar toolbar;

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_voucher_new);
        init();

        setSupportActionBar(toolbar);
        //************************************
//        this.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//       this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//       this.getSupportActionBar().setHomeButtonEnabled(true);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
        linearmain.startAnimation(animation);
        total_linear.startAnimation(animation);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp); // Set the icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MakeVoucher.this,MainActivity.class);
                startActivity(i);
            }
        });

        final DecimalFormat threeDForm = new DecimalFormat("0.000");

        DHandler = new DatabaseHandler(MakeVoucher.this);
//        DHandler.addCustomer(new Customer("","","",0,0,0,0,"",0,0,0));


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
//        linear.startAnimation(animation);

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

        currentRead.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!currentRead.getText().toString().equals("") && !previousRead.getText().toString().equals("")) {

                    double consum = Double.parseDouble(currentRead.getText().toString()) - Double.parseDouble(previousRead.getText().toString());
                    consuming.setText("" + threeDForm.format(consum));

                    consumingV = ((consum * gasPressure * 1 / 0.436) * gasPrice) / 1000;
                    consumingValue.setText("" + threeDForm.format(consumingV));

                    net.setText(serviceReturn.getText().toString());

                    double taxx = Double.parseDouble(serviceReturn.getText().toString()) * 16 / 100;
                    tax.setText("" + threeDForm.format(taxx));

                    taxService.setText("" + threeDForm.format((Double.parseDouble(net.getText().toString()) + taxx)));
                    currentConsuming.setText("" + threeDForm.format((Double.parseDouble(taxService.getText().toString()) + consumingV)));
                    lastValue.setText("" + threeDForm.format((Double.parseDouble(currentConsuming.getText().toString()) + Double.parseDouble(previousPalance.getText().toString()))));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        serviceReturn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!currentRead.getText().toString().equals("") && !previousRead.getText().toString().equals("") && !serviceReturn.getText().toString().equals("")) {

                    net.setText(serviceReturn.getText().toString());

                    double taxx = Double.parseDouble(serviceReturn.getText().toString()) * 16 / 100;
                    tax.setText("" + taxx);

                    taxService.setText("" + (Double.parseDouble(net.getText().toString()) + taxx));
                    currentConsuming.setText("" + (Double.parseDouble(taxService.getText().toString()) + consumingV));
                    lastValue.setText("" + (Double.parseDouble(currentConsuming.getText().toString()) + Double.parseDouble(previousPalance.getText().toString())));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        note.setOnTouchListener(onTouchListener);
        done.setOnTouchListener(onTouchListener);
        cancel.setOnTouchListener(onTouchListener);

        save.setOnTouchListener(onTouchListener);
        yes.setOnTouchListener(onTouchListener);
        no.setOnTouchListener(onTouchListener);

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

                case R.id.save:
                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav));
                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.save_yelow_btn));

                        if (!currentRead.getText().toString().equals("")) {
                            if (!gasReturn.getText().toString().equals("")) {
                                if (!serviceReturn.getText().toString().equals("")) {


                                    black.setVisibility(View.VISIBLE);
                                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
                                    black.startAnimation(animation);

                                    dialog.setVisibility(View.VISIBLE);
                                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
                                    animation2.setStartTime(300);
                                    dialog.startAnimation(animation2);


                                } else
                                    serviceReturn.setError("Required!");
                            } else
                                gasReturn.setError("Required!");
                        } else
                            currentRead.setError("Required!");

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        save.setBackgroundColor(getResources().getColor(R.color.yellow_hover));

//                          save.setBackgroundColor(getResources().getColor(R.color.yellow1));
                    }
                    break;

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
        toolbar=findViewById(R.id.appBar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_voucher_menu_bar,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.search_ic)
        {
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
        return  true;
    }
}
