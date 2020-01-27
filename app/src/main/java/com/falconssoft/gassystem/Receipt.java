package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Voucher;

import java.util.ArrayList;
import java.util.List;

public class Receipt extends AppCompatActivity {

    LinearLayout black, black2, black3, dialog, noteDialog, custDialog, linear;
    private Animation animation;
    EditText receiptNo, custNo, project, lastBalance, accountNo, counterNo, value, noteTextView;
    Button note, save, search, yes, no, done, cancel;
    ListView custList;

    DatabaseHandler DHandler;
    ArrayList<Customer> customerList;

    String noteText = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);


        init();

        DHandler = new DatabaseHandler(Receipt.this);

        customerList = DHandler.getAllCustomers();


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
        linear.startAnimation(animation);

        note.setOnTouchListener(onTouchListener);
        done.setOnTouchListener(onTouchListener);
        cancel.setOnTouchListener(onTouchListener);

        save.setOnTouchListener(onTouchListener);
        yes.setOnTouchListener(onTouchListener);
        no.setOnTouchListener(onTouchListener);

        search.setOnTouchListener(onTouchListener);

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
                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav));

                        if (!receiptNo.getText().toString().equals("")) {
                            if (!custNo.getText().toString().equals("")) {
                                if (!value.getText().toString().equals("")) {

                                    black.setVisibility(View.VISIBLE);
                                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
                                    black.startAnimation(animation);

                                    dialog.setVisibility(View.VISIBLE);
                                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
                                    animation2.setStartTime(300);
                                    dialog.startAnimation(animation2);

                                } else
                                    value.setError("Required!");
                            } else
                                custNo.setError("Required!");
                        } else
                            receiptNo.setError("Required!");

                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav_hover));
                    }
                    break;

                case R.id.search:
                    if (event.getAction() == MotionEvent.ACTION_UP) {

//                        Log.e("here   " , ""+customerList.size());


                        CustomersAdapter customersAdapter = new CustomersAdapter(Receipt.this, customerList);
                        custList.setAdapter(customersAdapter);

                        custList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                custNo.setText(customerList.get(position).getCustName());
                                project.setText(customerList.get(position).getProjectName());
                                lastBalance.setText("" + customerList.get(position).getCredet());
                                accountNo.setText(customerList.get(position).getAccNo());
                                counterNo.setText(customerList.get(position).getCounterNo());

                                black3.setVisibility(View.INVISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                                black3.startAnimation(animation);

                                custDialog.setVisibility(View.INVISIBLE);
                                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
                                animation2.setStartTime(300);
                                custDialog.startAnimation(animation2);
                            }
                        });

                        black3.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
                        black3.startAnimation(animation);

                        custDialog.setVisibility(View.VISIBLE);
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
                        animation2.setStartTime(300);
                        custDialog.startAnimation(animation2);

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

                        DHandler.addReceipt(new Receipts(
                                receiptNo.getText().toString(),
                                custNo.getText().toString(),
                                project.getText().toString(),
                                lastBalance.getText().toString(),
                                accountNo.getText().toString(),
                                counterNo.getText().toString(),
                                Double.parseDouble(value.getText().toString()),
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

        black = findViewById(R.id.black);
        dialog = findViewById(R.id.dialog);
        linear = findViewById(R.id.linear);

        black2 = findViewById(R.id.black2);
        noteDialog = findViewById(R.id.noteDialog);

        black3 = findViewById(R.id.black3);
        custDialog = findViewById(R.id.custDialog);

        linear = findViewById(R.id.linear);

        receiptNo = findViewById(R.id.receipt_no);
        custNo = findViewById(R.id.cust_no);
        project = findViewById(R.id.project);
        lastBalance = findViewById(R.id.last_balance);
        accountNo = findViewById(R.id.account_no);
        counterNo = findViewById(R.id.counter_no);
        value = findViewById(R.id.value);

        custList = findViewById(R.id.cust_list);

    }
}
