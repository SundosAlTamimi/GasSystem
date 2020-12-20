package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.VoucherModle;

import java.util.ArrayList;

import static com.falconssoft.gassystem.MakeVoucher.voucherGas;

public class PrintVoucher extends AppCompatActivity {

    TextView counterNo, currentRead, gasReturn, serviceReturn,previousRead;
    TextView custNo, consuming, consumingValue, previousPalance, taxService, net, tax,noteRemark,
            currentConsuming, lastValue, noteTextView,barCodTextTemp;
    EditText voucherNo;
    VoucherModle voucherModle;
    DatabaseHandler databaseHandler;
    Button cancel,print;
    boolean PrintOn=false;

    GlobelFunction globelFunction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.print_voucher_new);
        init();
//        getVoucherByVoucherNo
        globelFunction=new GlobelFunction();
        globelFunction.GlobelFunctionSetting(databaseHandler);
        voucherNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    PrintOn=false;
                    if(!TextUtils.isEmpty(voucherNo.getText().toString())) {
                        voucherModle = databaseHandler.getVoucherByVoucherNo(voucherNo.getText().toString());
                        if(!TextUtils.isEmpty(voucherModle.getAccNo())){
                            fillDataInLayout(voucherModle);
                            PrintOn=true;
                        }else{
                            clear();
                            PrintOn=false;
                            Toast.makeText(PrintVoucher.this, "الفاتوره غير موجوده", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        voucherNo.setError("Required!");
                    }
                }


                return false;
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PrintVoucher.this,MainActivityOn.class);
                startActivity(intent);
                finish();

            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(PrintOn){
                    if(globelFunction.printType.equals("0")) {
                        voucherGas=voucherModle;
                        Intent printExport=new Intent(PrintVoucher.this,BluetoothConnectMenu.class);
                        printExport.putExtra("printKey", "0");
                        startActivity(printExport);
                        Toast.makeText(PrintVoucher.this, "الطباعه ...", Toast.LENGTH_SHORT).show();
                    }else {
                        voucherGas=voucherModle;
                        Intent printExportEsc=new Intent(PrintVoucher.this,bMITP.class);
                        printExportEsc.putExtra("printKey", "0");
                        startActivity(printExportEsc);
                        Toast.makeText(PrintVoucher.this, "الطباعه ...", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(PrintVoucher.this, "لا يوجد فاتوره لطباعتها", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void fillDataInLayout(VoucherModle voucherModle) {

        counterNo.setText(voucherModle.getCounterNo());
        custNo .setText(voucherModle.getCustomerName());
        previousRead .setText(voucherModle.getLastReader());
        currentRead .setText(voucherModle.getCurrentReader());
        consuming.setText(voucherModle.getCCost());
        consumingValue .setText(voucherModle.getcCostVal());
        previousPalance .setText(voucherModle.getCredit());
        gasReturn.setText(voucherModle.getGret());
        serviceReturn .setText(voucherModle.getService());
        double taxSer=Double.parseDouble(globelFunction.DecimalFormat(""+((Double.parseDouble(voucherModle.getGret())+Double.parseDouble(voucherModle.getService())+ Double.parseDouble(voucherModle.getTaxValue())))));
        taxService .setText(String.valueOf(taxSer));
        net .setText(voucherModle.getNetValue());
        tax.setText(voucherModle.getTaxValue());
        currentConsuming .setText(voucherModle.getConsumption());
        lastValue .setText(voucherModle.getReQalValue());
        noteRemark.setText(voucherModle.getRemarks());

    }

    private void clear() {

        counterNo.setText("");
        custNo .setText("");
        previousRead .setText("");
        currentRead .setText("");
        consuming.setText("");
        consumingValue .setText("");
        previousPalance .setText("");
        gasReturn.setText("");
        serviceReturn .setText("");
        taxService .setText("");
        net .setText("");
        tax.setText("");
        currentConsuming .setText("");
        lastValue .setText("");
        noteRemark.setText("");

    }

    void init() {

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
        voucherNo=findViewById(R.id.voucherNo);
        voucherModle=new VoucherModle();
        databaseHandler=new DatabaseHandler(PrintVoucher.this);
        cancel=findViewById(R.id.cancelPrintVoucherButton);
        print=findViewById(R.id.PrintVoucherButton);
        noteRemark=findViewById(R.id.noteRemark);


    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PrintVoucher.this,MainActivityOn.class);
        startActivity(intent);
        finish();
    }


}
