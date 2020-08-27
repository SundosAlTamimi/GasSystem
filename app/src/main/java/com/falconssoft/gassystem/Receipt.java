package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.RecCash;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Remarks;
import com.falconssoft.gassystem.Modle.Voucher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Receipt extends AppCompatActivity {

    LinearLayout black, black2, black3, dialog, noteDialog, custDialog, linear;
    private Animation animation;
    EditText receiptNo, custNo, project, lastBalance, accountNo, counterNo, value, noteTextView;
    Button  save, search, yes, no, done, cancel,searchs;
    ListView custList;
     TextView note;
    DatabaseHandler DHandler;
    ArrayList<Customer> customerList;
    List<Remarks> RemarkList;
    String today;

    String noteText = "";
//    private Toolbar toolbar;
    public static RecCash recCash;
    ListAdapterNOTE listAdapterNOTE;
    ListAdapterCustomerName listAdapterCustomerName;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_new);
        init();
        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RemarkList=new ArrayList<>();
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp); // Set the icon
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(Receipt.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
        DHandler = new DatabaseHandler(Receipt.this);

        customerList = DHandler.getAllCustomers();
        RemarkList=DHandler.getAllRemark();
        note.setMovementMethod(ScrollingMovementMethod.getInstance());
        custNo.setMovementMethod(ScrollingMovementMethod.getInstance());
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
        linear.startAnimation(animation);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Save();

            }
        });


        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNoteDialog(note);
            }
        });

        searchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCustomerDialog(custNo);
            }
        });

    }

    public void ShowNoteDialog(final TextView textView){
        final Dialog dialog = new Dialog(this,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.note_dialog_show);
        dialog.setCancelable(true);

        final EditText noteSearch=dialog.findViewById(R.id.noteSearch);
final ListView ListNote=dialog.findViewById(R.id.ListNote);

         listAdapterNOTE = new ListAdapterNOTE(Receipt.this, RemarkList,textView,dialog);
        ListNote.setAdapter(listAdapterNOTE);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!noteSearch.getText().toString().equals("")){
                    List<Remarks> searchRemark=new ArrayList<>();
                    searchRemark.clear();
                    for(int i=0;i<RemarkList.size();i++){
                        if(RemarkList.get(i).getBody().contains(noteSearch.getText().toString())||RemarkList.get(i).getTitle().contains(noteSearch.getText().toString())){
                            searchRemark.add(RemarkList.get(i));

                        }
                    }

                     listAdapterNOTE = new ListAdapterNOTE(Receipt.this, searchRemark,textView,dialog);
                    ListNote.setAdapter(listAdapterNOTE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }

    public void ShowCustomerDialog(final TextView textView){
        final Dialog dialog = new Dialog(this,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customer_dialog_show);
        dialog.setCancelable(true);

        final EditText noteSearch=dialog.findViewById(R.id.noteSearch);
        final ListView ListNote=dialog.findViewById(R.id.ListNote);

        listAdapterCustomerName = new ListAdapterCustomerName(Receipt.this, customerList,textView,dialog);
        ListNote.setAdapter(listAdapterCustomerName);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!noteSearch.getText().toString().equals("")){
                    List<Customer> searchCustomer=new ArrayList<>();
                    searchCustomer.clear();
                    for(int i=0;i<customerList.size();i++){
                        if(customerList.get(i).getCustName().contains(noteSearch.getText().toString())){
                            searchCustomer.add(customerList.get(i));

                        }
                    }


                    listAdapterCustomerName = new ListAdapterCustomerName(Receipt.this, searchCustomer,textView,dialog);
                    ListNote.setAdapter(listAdapterCustomerName);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }


    public void Save() {


        if (!TextUtils.isEmpty(receiptNo.getText().toString())) {
            if (!TextUtils.isEmpty(custNo.getText().toString())) {
                if (!TextUtils.isEmpty(project.getText().toString())) {
                    if (!TextUtils.isEmpty(custNo.getText().toString())) {
                        if (!TextUtils.isEmpty(lastBalance.getText().toString())) {
                            if (!TextUtils.isEmpty(accountNo.getText().toString())) {
                                if (!TextUtils.isEmpty(counterNo.getText().toString())) {
                                    if (!TextUtils.isEmpty(value.getText().toString())) {
//

                                        recCash=new RecCash();

                                        recCash.setResNo(receiptNo.getText().toString());
                                        recCash.setAccNo( accountNo.getText().toString());
                                        recCash.setAccName(custNo.getText().toString());
                                        recCash.setCash( value.getText().toString());
                                        recCash.setRemarks(note.getText().toString());
                                        recCash.setRecDate(convertToEnglish(today));
                                        recCash.setIs_Post("0");
                                        recCash.setIsExport("0");
                                        recCash.setProjectName(project.getText().toString());


                                        SavePrint();

                                    }
                                } else {
                                    value.setError("Required!");
                                }
                            } else {
                                counterNo.setError("Required!");
                            }
                        } else {
                            lastBalance.setError("Required!");
                        }
                    } else {
                        custNo.setError("Required!");
                    }
                } else {
                    project.setError("Required!");
                }
            } else {
                custNo.setError("Required!");
            }
        } else {
            receiptNo.setError("Required!");
        }


        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();


    }

    void clearText(){
        receiptNo.setText("");
        custNo.setText("");
        project.setText("");
        lastBalance.setText("");
        accountNo.setText("");
        counterNo.setText("");
        value.setText("");
        note.setText("");
    }

    private void SavePrint() {

        DHandler.addRecCash(recCash);
        clearText();
        Intent printExport=new Intent(Receipt.this,BluetoothConnectMenu.class);
        printExport.putExtra("printKey", "1");
        startActivity(printExport);
        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
    }

//    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            switch (v.getId()) {
//
//                case R.id.note:
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        note.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.note));
//
//                        black2.setVisibility(View.VISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
//                        black2.startAnimation(animation);
//
//                        noteDialog.setVisibility(View.VISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
//                        animation2.setStartTime(300);
//                        noteDialog.startAnimation(animation2);
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        note.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.note_hover));
//                    }
//                    break;
//
//                case R.id.save:
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav));
//
//                        if (!receiptNo.getText().toString().equals("")) {
//                            if (!custNo.getText().toString().equals("")) {
//                                if (!value.getText().toString().equals("")) {
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
//                                } else
//                                    value.setError("Required!");
//                            } else
//                                custNo.setError("Required!");
//                        } else
//                            receiptNo.setError("Required!");
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sav_hover));
//                    }
//                    break;
//
//                case R.id.search:
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//
////                        Log.e("here   " , ""+customerList.size());
//
//
//                        CustomersAdapter customersAdapter = new CustomersAdapter(Receipt.this, customerList);
//                        custList.setAdapter(customersAdapter);
//
//                        custList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                custNo.setText(customerList.get(position).getCustName());
//                                project.setText(customerList.get(position).getProjectName());
//                                lastBalance.setText("" + customerList.get(position).getCredet());
//                                accountNo.setText(customerList.get(position).getAccNo());
//                                counterNo.setText(customerList.get(position).getCounterNo());
//
//                                black3.setVisibility(View.INVISIBLE);
//                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
//                                black3.startAnimation(animation);
//
//                                custDialog.setVisibility(View.INVISIBLE);
//                                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
//                                animation2.setStartTime(300);
//                                custDialog.startAnimation(animation2);
//                            }
//                        });
//
//                        black3.setVisibility(View.VISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.releave);
//                        black3.startAnimation(animation);
//
//                        custDialog.setVisibility(View.VISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
//                        animation2.setStartTime(300);
//                        custDialog.startAnimation(animation2);
//
//                    }
//                    break;
//
//                case R.id.yes:
//
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        black.setVisibility(View.INVISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
//                        black.startAnimation(animation);
//
//                        dialog.setVisibility(View.INVISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
//                        animation2.setStartTime(300);
//                        dialog.startAnimation(animation2);
//
//                        DHandler.addReceipt(new Receipts(
//                                receiptNo.getText().toString(),
//                                custNo.getText().toString(),
//                                project.getText().toString(),
//                                lastBalance.getText().toString(),
//                                accountNo.getText().toString(),
//                                counterNo.getText().toString(),
//                                Double.parseDouble(value.getText().toString()),
//                                noteText
//                        ));
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    }
//                    break;
//
//                case R.id.no:
//
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        black.setVisibility(View.INVISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
//                        black.startAnimation(animation);
//
//                        dialog.setVisibility(View.INVISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
//                        animation2.setStartTime(300);
//                        dialog.startAnimation(animation2);
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    }
//                    break;
//
//                case R.id.done:
//
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                        black2.setVisibility(View.INVISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
//                        black2.startAnimation(animation);
//
//                        noteDialog.setVisibility(View.INVISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
//                        animation2.setStartTime(300);
//                        noteDialog.startAnimation(animation2);
//
//                        noteText = noteTextView.getText().toString();
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    }
//                    break;
//
//                case R.id.cancel:
//
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        black2.setVisibility(View.INVISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
//                        black2.startAnimation(animation);
//
//                        noteDialog.setVisibility(View.INVISIBLE);
//                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_top);
//                        animation2.setStartTime(300);
//                        noteDialog.startAnimation(animation2);
//
//                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    }
//                    break;
//            }
//            return true;
//        }
//    };

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    void init() {

        note = findViewById(R.id.notes);
        save = findViewById(R.id.save_);
        search = findViewById(R.id.search);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        done = findViewById(R.id.done);
        cancel = findViewById(R.id.cancel_btn);

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
        searchs= findViewById(R.id.searchs);

        custList = findViewById(R.id.cust_list);
//        toolbar=findViewById(R.id.appBar);

    }
}
