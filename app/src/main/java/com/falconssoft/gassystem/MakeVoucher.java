package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
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
import com.falconssoft.gassystem.Modle.MaxSerial;
import com.falconssoft.gassystem.Modle.Remarks;
import com.falconssoft.gassystem.Modle.Voucher;
import com.falconssoft.gassystem.Modle.VoucherModle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MakeVoucher extends AppCompatActivity {

    Button note, save, search, yes, no, done, cancel, cancelButton, searchCu, barCode;
    LinearLayout black, black2, dialog, noteDialog, linear, linearmain, total_linear;
    EditText counterNo, currentRead, gasReturn, serviceReturn, previousRead;
    TextView custNo, consuming, consumingValue, previousPalance, taxService, net, tax, noteRemark,
            currentConsuming, lastValue, noteTextView, barCodTextTemp;
    DatabaseHandler DHandler;
    double gasPressure = 0;
    double gasPrice = 0;
    double consumingV = 0;

    private Animation animation;
    DecimalFormat threeDForm;
    String noteText = "";
    private Toolbar toolbar;
    public static VoucherModle voucherGas;
    ArrayList<String> filteredList = new ArrayList<>();
    ArrayList<String> currentList;
    ArrayList<Customer> customerList;

    ArrayAdapter<String> itemsAdapter;
    public static String selectedCounter = "";

    public static Customer customer;
    ListAdapterCustomerName listAdapterCustomerName;
    GlobelFunction globelFunction;

    Button editButton;
    String intentReSend = "";
    LinearLayout editVoucherNoLinear;
    EditText editTextVoucherNo;
    VoucherModle voucherModleEdit;
    ListAdapterCounterSearch listAdapterCounterSearch;

    String maxSerialVoucher = "0";

    Button searchCounter;
    ListAdapterNOTE listAdapterNOTE;
    List<Remarks> RemarkList;
    TextView voucherNo;
    LinearLayout voucherNoLinear;

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_voucher_new);
        init();
        globelFunction = new GlobelFunction();

//        setSupportActionBar(toolbar);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_down);
        linearmain.startAnimation(animation);
        total_linear.startAnimation(animation);

//        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp); // Set the icon
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MakeVoucher.this, MainActivity.class);
//                startActivity(i);
//            }
//        });

        threeDForm = new DecimalFormat("#.###");

        DHandler = new DatabaseHandler(MakeVoucher.this);
        globelFunction.GlobelFunctionSetting(DHandler);
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
                Intent intent = new Intent(MakeVoucher.this, MainActivityOn.class);
                startActivity(intent);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatCall();
                Save();

            }
        });

        MaxSerial maxSerial = DHandler.getMaxSerialTable();
        List<VoucherModle> voucherModles = DHandler.getAllVouchers();
        if (voucherModles.size() == 0 && TextUtils.isEmpty(maxSerial.getColomMax())) {
            DHandler.addMaxSerialTable(new MaxSerial(globelFunction.serialVoucher, globelFunction.serialRec));
        } else if (voucherModles.size() == 0) {
            DHandler.updateMaxVoucher(globelFunction.serialVoucher);
        }

        maxSerial = DHandler.getMaxSerialTable();
        if (!TextUtils.isEmpty(maxSerial.getSerialMax())) {
            maxSerialVoucher = maxSerial.getSerialMax();
        } else {

            maxSerialVoucher = globelFunction.serialVoucher;
            DHandler.addMaxSerialTable(new MaxSerial(globelFunction.serialVoucher, globelFunction.serialRec));
        }

        voucherNo.setText("" + maxSerialVoucher);


        searchCu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCustomerDialog(custNo);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!counterNo.getText().toString().equals("")) {

                    Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                    if (customer.getCounterNo() != null) {

                        custNo.setText(customer.getCustName());
                        previousRead.setText(globelFunction.DecimalFormat("" + customer.getLastRead()));
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
//

        RemarkList = DHandler.getAllRemark();


//        currentRead.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
//
////                     customer = DHandler.getCustomer(counterNo.getText().toString());
//                    calculatCall();
//
//                }
//
//
//                return false;
//            }
//        });


        currentRead.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    calculatCall();

                }

            }
        });

        previousRead.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    calculatCall();

                }

            }
        });


//        gasReturn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
//
////                     customer = DHandler.getCustomer(counterNo.getText().toString());
//                    calculatCall();
//
//
//                }
//
//
//                return false;
//            }
//        });

        gasReturn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    calculatCall();

                }

            }
        });

        serviceReturn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

//                    Customer customer = DHandler.getCustomer(counterNo.getText().toString());
                    calculatCall();


                }


                return false;
            }
        });


//        counterNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
//                    clearText();
//                    if (!counterNo.getText().toString().equals("")) {
//
//                         customer = DHandler.getCustomer(counterNo.getText().toString());
//                        if (customer.getCounterNo() != null) {
//
//                            custNo.setText(customer.getCustName());
//                            previousRead.setText("" + customer.getLastRead());
//                            previousPalance.setText("" + customer.getCredet());
//                            serviceReturn.setText("" + customer.getBadalVal());
//
//                            gasPressure = customer.getGasPressure();
//                            gasPrice = customer.getgPrice();
//                            currentRead.requestFocus();
//
////                            new Handler().post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    currentRead.requestFocus();
////                                }
////                            });
//
//
//                        } else {
//                            Toast.makeText(MakeVoucher.this, "رقم العداد غير موجود", Toast.LENGTH_LONG).show();
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    counterNo.requestFocus();
//                                }
//                            });
//                        }
//                    } else {
//                        Toast.makeText(MakeVoucher.this, "ادخل رقم العداد", Toast.LENGTH_LONG).show();
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                counterNo.requestFocus();
//                            }
//                        });
//
//                    }
//                }
//
//
//                return false;
//            }
//        });


        counterNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    clearText();
                    if (!counterNo.getText().toString().equals("")) {

                        customer = DHandler.getCustomer(counterNo.getText().toString());
                        if (customer.getCounterNo() != null) {

                            custNo.setText(customer.getCustName());
                            previousRead.setText(globelFunction.DecimalFormat("" + customer.getLastRead()));
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

            }
        });


        barCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readBarCode(counterNo, 0);

            }
        });


        intentReSend = getIntent().getStringExtra("EDIT_VOUCHER");

        if (intentReSend != null && intentReSend.equals("EDIT_VOUCHER")) {
            editButton.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            editVoucherNoLinear.setVisibility(View.VISIBLE);
            counterNo.setEnabled(false);
            barCode.setVisibility(View.GONE);
            searchCounter.setVisibility(View.GONE);
            editTextVoucherNo.requestFocus();
            voucherNoLinear.setVisibility(View.GONE);

        } else {
            editButton.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
            editVoucherNoLinear.setVisibility(View.GONE);
            barCode.setVisibility(View.VISIBLE);
            counterNo.setEnabled(true);
            searchCounter.setVisibility(View.VISIBLE);
            counterNo.requestFocus();
            voucherNoLinear.setVisibility(View.VISIBLE);

        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                update
                calculatCall();
                Update();

            }
        });


        editTextVoucherNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    PrintOn=false;
                    if (!TextUtils.isEmpty(editTextVoucherNo.getText().toString())) {
                        voucherModleEdit = new VoucherModle();
                        voucherModleEdit = DHandler.getVoucherByVoucherNo(editTextVoucherNo.getText().toString());
                        String counterNo = "";
                        try {
                            counterNo = voucherModleEdit.getCounterNo();
                        } catch (Exception e) {
                            counterNo = "";
                        }

                        if (!TextUtils.isEmpty(counterNo)) {
                            fillDataInLayout(voucherModleEdit);
//                            PrintOn=true;
                        } else {
                            clear();
//                            PrintOn=false;
                            Toast.makeText(MakeVoucher.this, "الفاتوره غير موجوده", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        editTextVoucherNo.setError("Required!");
                    }
                }


                return false;
            }
        });


        searchCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counterNo.requestFocus();
                clearText();
                ShowCustomerCounterSerchDialog(counterNo);

            }
        });


        noteRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNoteDialog(noteRemark);
            }
        });


    }

    public void ShowCustomerCounterSerchDialog(final TextView textView) {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.counter_customer_serch_dialog);
        dialog.setCancelable(true);

        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
        final ListView ListNote = dialog.findViewById(R.id.ListNote);


        customerList = DHandler.getAllCustomers();


        listAdapterCounterSearch = new ListAdapterCounterSearch(MakeVoucher.this, customerList, textView, dialog, currentRead);
        ListNote.setAdapter(listAdapterCounterSearch);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noteSearch.getText().toString().equals("")) {
                    List<Customer> searchCounter = new ArrayList<>();
                    searchCounter.clear();
                    for (int i = 0; i < customerList.size(); i++) {
                        if (customerList.get(i).getCounterNo().contains(noteSearch.getText().toString()) || customerList.get(i).getCustName().contains(noteSearch.getText().toString())) {
                            searchCounter.add(customerList.get(i));

                        }
                    }

                    listAdapterCounterSearch = new ListAdapterCounterSearch(MakeVoucher.this, searchCounter, textView, dialog, currentRead);
                    ListNote.setAdapter(listAdapterCounterSearch);

                } else {
                    listAdapterCounterSearch = new ListAdapterCounterSearch(MakeVoucher.this, customerList, textView, dialog, currentRead);
                    ListNote.setAdapter(listAdapterCounterSearch);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }


    void Update() {

        if (!TextUtils.isEmpty(voucherModleEdit.getCounterNo())) {
            if (!TextUtils.isEmpty(counterNo.getText().toString())) {
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

                                                                    voucherGas = new VoucherModle();
                                                                    voucherGas.setCounterNo(counterNo.getText().toString());
                                                                    voucherGas.setCustomerName(custNo.getText().toString());
                                                                    voucherGas.setLastReader(previousRead.getText().toString());
                                                                    voucherGas.setAccNo(voucherModleEdit.getAccNo());
                                                                    voucherGas.setGasPressure("" + voucherModleEdit.getGasPressure());
                                                                    voucherGas.setGasPrice("" + voucherModleEdit.getGasPrice());
                                                                    voucherGas.setProjectName(voucherModleEdit.getProjectName());
                                                                    voucherGas.setPrameter("1");
                                                                    voucherGas.setCurrentReader(currentRead.getText().toString());
                                                                    voucherGas.setCCost(consuming.getText().toString());
                                                                    voucherGas.setcCostVal(consumingValue.getText().toString());
                                                                    voucherGas.setService(serviceReturn.getText().toString());
                                                                    voucherGas.setReQalValue(lastValue.getText().toString());
                                                                    voucherGas.setReaderDate(voucherModleEdit.getReaderDate());
                                                                    voucherGas.setInvoiceType("501");
                                                                    voucherGas.setInvoiceNo("" + voucherModleEdit.getInvoiceNo());//serial
                                                                    voucherGas.setNetValue(net.getText().toString());
                                                                    voucherGas.setTaxValue(tax.getText().toString());
                                                                    voucherGas.setGret(gasReturn.getText().toString());
                                                                    voucherGas.setRemarks(noteRemark.getText().toString());//***importAdd
                                                                    voucherGas.setConsumption(currentConsuming.getText().toString());
                                                                    voucherGas.setCredit(previousPalance.getText().toString());
                                                                    voucherGas.setIsPost("0");
                                                                    voucherGas.setIsPer("0");
                                                                    voucherGas.setAllowance("0");
                                                                    voucherGas.setIsExport("0");
                                                                    voucherGas.setSerial("" + voucherModleEdit.getSerial());

                                                                    DHandler.deleteVoucher(voucherModleEdit.getSerial(), voucherModleEdit.getInvoiceNo());
                                                                    DHandler.addVouchers(voucherGas);
                                                                    DHandler.updateVoucherStatusBackUP(voucherModleEdit.getSerial(), voucherModleEdit.getInvoiceNo());
                                                                    counterNo.setText("");
                                                                    Toast.makeText(this, "تم التعديل بنجاح ", Toast.LENGTH_SHORT).show();
                                                                    clearText();
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
            } else {
                counterNo.setError("Required!");
            }


        } else {


        }

    }

    void calculatCall() {

        if (intentReSend != null && intentReSend.equals("EDIT_VOUCHER")) {


            String counterNoV = "";
            try {
                counterNoV = voucherModleEdit.getCounterNo();
            } catch (Exception e) {
                counterNoV = "";
            }

            if (!TextUtils.isEmpty(counterNoV)) {


                calculateFunction(Double.parseDouble(voucherModleEdit.getGasPressure()), 1, Double.parseDouble(voucherModleEdit.getGasPrice()));

            }

        } else {
            String counterNo = "";
            try {
                counterNo = customer.getCounterNo();
            } catch (Exception e) {
                counterNo = "";
            }
            if (!TextUtils.isEmpty(counterNo)) {

                calculateFunction(customer.getGasPressure(), 1, customer.getgPrice());

            }

        }

    }

//    boolean is

    public void ShowCustomerDialog(final TextView textView) {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customer_dialog_show);
        dialog.setCancelable(true);

        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
        final ListView ListNote = dialog.findViewById(R.id.ListNote);

        listAdapterCustomerName = new ListAdapterCustomerName(null, customerList, textView, dialog, 0, MakeVoucher.this);
        ListNote.setAdapter(listAdapterCustomerName);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noteSearch.getText().toString().equals("")) {
                    List<Customer> searchCustomer = new ArrayList<>();
                    searchCustomer.clear();
                    for (int i = 0; i < customerList.size(); i++) {
                        if (customerList.get(i).getCustName().contains(noteSearch.getText().toString())) {
                            searchCustomer.add(customerList.get(i));

                        }
                    }


                    listAdapterCustomerName = new ListAdapterCustomerName(null, searchCustomer, textView, dialog, 0, MakeVoucher.this);
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

        if (!TextUtils.isEmpty(counterNo.getText().toString())) {
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


                                                                voucherGas = new VoucherModle();
//                                                            MaxSerial maxSerial=DHandler.getMaxSerialTable();
//                                                            List<VoucherModle>voucherModles=DHandler.getAllVouchers();
//                                                            if(voucherModles.size()==0 && TextUtils.isEmpty(maxSerial.getColomMax())){
//                                                                DHandler.addMaxSerialTable(new MaxSerial(globelFunction.serialVoucher,globelFunction.serialRec));
//                                                            }else if(voucherModles.size()==0){
//                                                                DHandler.updateMaxVoucher(globelFunction.serialVoucher);
//                                                            }

                                                                MaxSerial maxSerial = DHandler.getMaxSerialTable();
                                                                if (!TextUtils.isEmpty(maxSerial.getSerialMax())) {
                                                                    maxSerialVoucher = maxSerial.getSerialMax();
                                                                } else {

                                                                    maxSerialVoucher = globelFunction.serialVoucher;
                                                                    DHandler.addMaxSerialTable(new MaxSerial(globelFunction.serialVoucher, globelFunction.serialRec));
                                                                }

                                                                int maxVno = DHandler.getMax("VOUCHERS_TABLE") + 1;
                                                                voucherGas.setCounterNo(counterNo.getText().toString());
                                                                voucherGas.setCustomerName(custNo.getText().toString());
                                                                voucherGas.setLastReader(previousRead.getText().toString());
                                                                voucherGas.setAccNo(customer.getAccNo());
                                                                voucherGas.setGasPressure("" + customer.getGasPressure());
                                                                voucherGas.setGasPrice("" + customer.getgPrice());
                                                                voucherGas.setProjectName(customer.getProjectName());
                                                                voucherGas.setPrameter("1");
                                                                voucherGas.setCurrentReader(currentRead.getText().toString());
                                                                voucherGas.setCCost(consuming.getText().toString());
                                                                voucherGas.setcCostVal(consumingValue.getText().toString());
                                                                voucherGas.setService(serviceReturn.getText().toString());
                                                                voucherGas.setReQalValue(lastValue.getText().toString());
                                                                voucherGas.setReaderDate(globelFunction.DateInToday());
                                                                voucherGas.setInvoiceType("501");
                                                                voucherGas.setInvoiceNo(maxSerialVoucher);//serial
                                                                voucherGas.setNetValue(globelFunction.DecimalFormat(net.getText().toString()));
                                                                voucherGas.setTaxValue(tax.getText().toString());
                                                                voucherGas.setGret(gasReturn.getText().toString());
                                                                voucherGas.setRemarks(noteRemark.getText().toString());//***importAdd
                                                                voucherGas.setConsumption(currentConsuming.getText().toString());
                                                                voucherGas.setCredit(previousPalance.getText().toString());
                                                                voucherGas.setIsPost("0");
                                                                voucherGas.setIsPer("0");
                                                                voucherGas.setAllowance("0");
                                                                voucherGas.setIsExport("0");
                                                                voucherGas.setSerial("" + maxVno);

                                                                SavePrint();
                                                                Toast.makeText(this, "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show();

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
        } else {
            counterNo.setError("Required!");
        }


    }

    private void SavePrint() {

        DHandler.addVouchers(voucherGas);
        DHandler.addVouchersBackup(voucherGas);
        DHandler.updateMaxVoucher("" + (Integer.parseInt(maxSerialVoucher) + 1));
        counterNo.setText("");
        clearText();
        voucherNo.setText("" + (Integer.parseInt(maxSerialVoucher) + 1));
//        if(globelFunction.savePrint==1) {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MakeVoucher.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("الطباعة " + "!");
        sweetAlertDialog.setContentText("تم الحفظ , هل تريد طباعة الفاتوره ؟ ");
        sweetAlertDialog.setConfirmText("طباعة");
        sweetAlertDialog.showCancelButton(true);
        sweetAlertDialog.setCancelButton("الغاء", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (globelFunction.printType.equals("0")) {
                    Intent printExport = new Intent(MakeVoucher.this, BluetoothConnectMenu.class);
                    printExport.putExtra("printKey", "0");
                    startActivity(printExport);
                } else {
                    Intent printExportEsc = new Intent(MakeVoucher.this, bMITP.class);
                    printExportEsc.putExtra("printKey", "0");
                    startActivity(printExportEsc);
                }

                sweetAlertDialog.dismissWithAnimation();

            }
        });
       sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();


//        }else {
//            Toast.makeText(this, "حفظ دون طباعه", Toast.LENGTH_SHORT).show();
//        }


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
        noteRemark.setText("");


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
        voucherNo = findViewById(R.id.voucher_no);
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
//        toolbar = findViewById(R.id.appBar);
        customerList = new ArrayList<>();
        searchCu = findViewById(R.id.searchCu);
        barCode = findViewById(R.id.barcode);
        editButton = findViewById(R.id.editButton);
        editTextVoucherNo = findViewById(R.id.editTextVoucherNo);
        editButton.setVisibility(View.GONE);
        editVoucherNoLinear = findViewById(R.id.editVoucherNoLinear);
        editVoucherNoLinear.setVisibility(View.GONE);
        searchCounter = findViewById(R.id.searchCounter);
        noteRemark = findViewById(R.id.noteRemark);
        voucherNoLinear=findViewById(R.id.voucherNoLinear);
    }

    void calculateFunction(double GP, double COE, double GPRC) {

        double DPR = 0, VOD = 0, DP = 0, NetTotal = 0, TAX = 0, REQVAL = 0, GRV = 0, SRV = 0, TXV = 0, TX = 0, netValue = 0;


        if (!previousRead.getText().toString().equals("") && !currentRead.getText().toString().equals("") && !previousPalance.getText().toString().equals("")) {
            if (!previousRead.getText().toString().equals(".")) {
                if (!currentRead.getText().toString().equals(".")) {
                    if (Double.parseDouble(currentRead.getText().toString()) != 0) {

                        currentRead.setText("" + Double.parseDouble(currentRead.getText().toString()));//this for make number in text double format
                        previousRead.setText(globelFunction.DecimalFormat("" + Double.parseDouble(previousRead.getText().toString())));//this for make number in text double format
                        //_____________________________________الاستهلاك ___________________________________
                        DPR = Double.parseDouble(currentRead.getText().toString()) - Double.parseDouble(previousRead.getText().toString());
                        consuming.setText(globelFunction.DecimalFormat("" + DPR));
                        //_____________________________________قيمة الاستهلاك ___________________________________

                        String vo = convertToEnglish(threeDForm.format(((DPR * (GP + 1) * COE) / 0.436) * (GPRC / 1000)));
                        VOD = Double.parseDouble(vo);
                        consumingValue.setText("" + globelFunction.DecimalFormat("" + VOD));

                        //_____________________________________استهلاك الفتره ___________________________________

                        if (!gasReturn.getText().toString().equals("")) {
                            if (!gasReturn.getText().toString().equals(".")) {
                                gasReturn.setText(globelFunction.DecimalFormat("" + Double.parseDouble(gasReturn.getText().toString())));//this for make number in text double format
                                GRV = Double.parseDouble(gasReturn.getText().toString());
                            } else {
                                gasReturn.setError("DOT!");
                                clearAfterError();
                            }

                        } else {
                            GRV = 0.0;

                        }

                        if (!serviceReturn.getText().toString().equals("")) {
                            if (!serviceReturn.getText().toString().equals(".")) {
                                serviceReturn.setText("" + Double.parseDouble(serviceReturn.getText().toString()));//this for make number in text double format
                                SRV = Double.parseDouble(serviceReturn.getText().toString());
                            } else {
                                serviceReturn.setError("DOT!");
                                clearAfterError();
                            }
                        } else {

                            SRV = 0.0;
                        }

                        netValue = (GRV + SRV);//الصافي
                        net.setText(globelFunction.DecimalFormat("" + netValue));


                        TX = Double.parseDouble(convertToEnglish(threeDForm.format((GRV + SRV) * 0.16))); //الضريبه
                        tax.setText(globelFunction.DecimalFormat(globelFunction.DecimalFormat("" + TX)));

                        TXV = Double.parseDouble(convertToEnglish(threeDForm.format((GRV + SRV) + TX)));//مجموع بدل خدمات
                        taxService.setText(globelFunction.DecimalFormat("" + TXV));

                        DP = Double.parseDouble(convertToEnglish(threeDForm.format(VOD + TXV))); //استهلاك الفتره
                        currentConsuming.setText(globelFunction.DecimalFormat("" + DP));

                        NetTotal = Double.parseDouble(convertToEnglish(threeDForm.format(DP + Double.parseDouble(previousPalance.getText().toString())))); //القيمه المطلوبه
                        lastValue.setText(globelFunction.DecimalFormat("" + NetTotal));
                    } else {
                        currentRead.setError("Zero!");
                        clearAfterError();
                    }
                } else {
                    currentRead.setError("DOT!");
                    clearAfterError();
                }
            } else {
                previousRead.setError("DOT!");
                clearAfterError();
            }
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
            Log.e("getItemId", "here");
            openSearchDialog();
        }
//
//            if (!counterNo.getText().toString().equals("")) {
//
//                Customer customer = DHandler.getCustomer(counterNo.getText().toString());
//                if (customer.getCounterNo() != null) {
//
//                    custNo.setText(customer.getCustName());
//                    previousRead.setText("" + customer.getLastRead());
//                    previousPalance.setText("" + customer.getCredet());
//                    serviceReturn.setText("" + customer.getBadalVal());
//
//                    gasPressure = customer.getGasPressure();
//                    gasPrice = customer.getgPrice();
//
//                } else
//                    Toast.makeText(MakeVoucher.this, "رقم العداد غير موجود", Toast.LENGTH_LONG).show();
//
//            } else
//                Toast.makeText(MakeVoucher.this, "ادخل رقم العداد", Toast.LENGTH_LONG).show();
//        }
        return true;
    }

    private void openSearchDialog() {
        final Dialog dialog = new Dialog(MakeVoucher.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.counter_no_layout);
        dialog.setCancelable(false);

        final ListView listOfCounter = dialog.findViewById(R.id.counterList);
        listOfCounter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
                selectedCounter = parent.getItemAtPosition(position).toString();
                counterNo.setText(selectedCounter + "");
                dialog.dismiss();
                Log.e("selectedCounter", "" + selectedCounter);

            }
        });
        filteredList = DHandler.getAllCounter();

        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filteredList);

        listOfCounter.setAdapter(itemsAdapter);

        TextView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SearchView searchView = dialog.findViewById(R.id.mSearch);

        dialog.show();
        currentList = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (query != null && query.length() > 0) {

//                     ArrayList<Customer> resultCustomer= DHandler.getListCustomer(query);
//                     Log.e("resultCustomer",""+resultCustomer.size());
                    if (!filteredList.equals(null) && filteredList.size() != 0) {
                        currentList.clear();
//                         filteredList.clear();
                        for (int i = 0; i < filteredList.size(); i++) {
                            if (filteredList.get(i).contains(query)) {
                                currentList.add(filteredList.get(i));

                            }

                        }
                        itemsAdapter =
                                new ArrayAdapter<String>(MakeVoucher.this, android.R.layout.simple_list_item_1, currentList);
                        listOfCounter.setAdapter(itemsAdapter);


                    } else {
                        itemsAdapter =
                                new ArrayAdapter<String>(MakeVoucher.this, android.R.layout.simple_list_item_1, filteredList);
                        listOfCounter.setAdapter(itemsAdapter);

                    }


                } else {
                    itemsAdapter =
                            new ArrayAdapter<String>(MakeVoucher.this, android.R.layout.simple_list_item_1, filteredList);
                    listOfCounter.setAdapter(itemsAdapter);


                }
                return false;
            }
        });

    }

    public void readBarCode(TextView itemCodeText, int swBarcode) {

        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(MakeVoucher.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");
//                Toast.makeText(this, getString(R.string.scan) + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
                barCodTextTemp.setText(Result.getContents() + "");
                currentRead.requestFocus();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("SetTextI18n")
    private void fillDataInLayout(VoucherModle voucherModle) {

        counterNo.setText(voucherModle.getCounterNo());
        custNo.setText(voucherModle.getCustomerName());
        previousRead.setText(globelFunction.DecimalFormat(voucherModle.getLastReader()));
        currentRead.setText(voucherModle.getCurrentReader());
        consuming.setText(globelFunction.DecimalFormat(voucherModle.getCCost()));
        consumingValue.setText(globelFunction.DecimalFormat(voucherModle.getcCostVal()));
        previousPalance.setText(voucherModle.getCredit());
        gasReturn.setText(voucherModle.getGret());
        serviceReturn.setText(voucherModle.getService());
        double taxSer = Double.parseDouble(globelFunction.DecimalFormat("" + ((Double.parseDouble(voucherModle.getGret()) + Double.parseDouble(voucherModle.getService()) + Double.parseDouble(voucherModle.getTaxValue())))));
        taxService.setText(String.valueOf(taxSer));
        net.setText(voucherModle.getNetValue());
        tax.setText(voucherModle.getTaxValue());
        currentConsuming.setText(voucherModle.getConsumption());
        lastValue.setText(voucherModle.getReQalValue());
        noteRemark.setText(voucherModle.getRemarks());


    }

    private void clear() {

        counterNo.setText("");
        custNo.setText("");
        previousRead.setText("");
        currentRead.setText("");
        consuming.setText("");
        consumingValue.setText("");
        previousPalance.setText("");
        gasReturn.setText("");
        serviceReturn.setText("");
        taxService.setText("");
        net.setText("");
        tax.setText("");
        currentConsuming.setText("");
        lastValue.setText("");
        noteRemark.setText("");

    }


    private void clearAfterError() {

//        counterNo.setText("");
//        custNo .setText("");
//        previousRead .setText("");
//        currentRead .setText("");
        consuming.setText("");
        consumingValue.setText("");
//        previousPalance .setText("0.0");
//        gasReturn.setText("");
//        serviceReturn .setText("");
        taxService.setText("");
        net.setText("");
        tax.setText("");
        currentConsuming.setText("");
        lastValue.setText("");
        noteRemark.setText("");

    }


    public void ShowNoteDialog(final TextView textView) {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.note_dialog_show);
        dialog.setCancelable(true);

        final EditText noteSearch = dialog.findViewById(R.id.noteSearch);
        final ListView ListNote = dialog.findViewById(R.id.ListNote);

        listAdapterNOTE = new ListAdapterNOTE(MakeVoucher.this, RemarkList, textView, dialog);
        ListNote.setAdapter(listAdapterNOTE);

        noteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noteSearch.getText().toString().equals("")) {
                    List<Remarks> searchRemark = new ArrayList<>();
                    searchRemark.clear();
                    for (int i = 0; i < RemarkList.size(); i++) {
                        if (RemarkList.get(i).getBody().contains(noteSearch.getText().toString()) || RemarkList.get(i).getTitle().contains(noteSearch.getText().toString())) {
                            searchRemark.add(RemarkList.get(i));

                        }
                    }

                    listAdapterNOTE = new ListAdapterNOTE(MakeVoucher.this, searchRemark, textView, dialog);
                    ListNote.setAdapter(listAdapterNOTE);

                } else {
                    listAdapterNOTE = new ListAdapterNOTE(MakeVoucher.this, RemarkList, textView, dialog);
                    ListNote.setAdapter(listAdapterNOTE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MakeVoucher.this, MainActivityOn.class);
        startActivity(intent);
        finish();
    }


}
