package com.falconssoft.gassystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.RecCash;

import static com.falconssoft.gassystem.Receipt.recCash;


public class PrintRecCash extends AppCompatActivity {
    EditText RecCashNoEditText;
    TextView note,receiptNo, project, lastBalance, accountNo, counterNo,value,custNo;
    DatabaseHandler databaseHandler;
    boolean PrintOn=false;
    RecCash recCashPrint;
    Button print,cancel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.print_receipt_new);
        inti();


        RecCashNoEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    PrintOn=false;
                    recCashPrint=new RecCash();
                    if(!TextUtils.isEmpty(RecCashNoEditText.getText().toString())) {
                        recCashPrint = databaseHandler.getRecCashByRecNo(RecCashNoEditText.getText().toString());
                        if(!TextUtils.isEmpty(recCashPrint.getAccNo())){
                            fillRecCashPrinting(recCashPrint);
                            PrintOn=true;
                        }else{
                            clearText();
                            PrintOn=false;
                            Toast.makeText(PrintRecCash.this, "no RecCash", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        clearText();
                        RecCashNoEditText.setError("Required!");
                    }
                }


                return false;
            }
        });

//        fillRecCashPrinting();


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrintOn){
                    recCash=recCashPrint;
                    Intent printExport=new Intent(PrintRecCash.this,BluetoothConnectMenu.class);
                    printExport.putExtra("printKey", "1");
                    startActivity(printExport);
                    Toast.makeText(PrintRecCash.this, "print Success", Toast.LENGTH_SHORT).show();
                    PrintOn=false;
                    recCashPrint=new RecCash();
                    clearText();
                }else {
                    Toast.makeText(PrintRecCash.this, "No Voucher For Print ", Toast.LENGTH_SHORT).show();

                }

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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
        PrintOn=false;
        recCashPrint=new RecCash();
    }

    void fillRecCashPrinting(RecCash receipt){
            if(!TextUtils.isEmpty(receipt.getAccName())){
                receiptNo.setText(receipt.getResNo());
                custNo.setText(receipt.getAccName());
                project.setText(receipt.getProjectName());
                lastBalance.setText(receipt.getLastBalance());//
                accountNo.setText(receipt.getAccNo());
                counterNo.setText(receipt.getCounterNo());
                value.setText(receipt.getCash());
                note.setText(receipt.getRemarks());

            }else {
            }
    }

    void inti(){
        databaseHandler=new DatabaseHandler(PrintRecCash.this);
        recCashPrint=new RecCash();

        RecCashNoEditText=findViewById(R.id.RecCashNoEditText);
        receiptNo = findViewById(R.id.receipt_no);
        custNo = findViewById(R.id.cust_no);
        project = findViewById(R.id.project);
        lastBalance = findViewById(R.id.last_balance);
        accountNo = findViewById(R.id.account_no);
        counterNo = findViewById(R.id.counter_no);
        value = findViewById(R.id.value);
        note = findViewById(R.id.notes);
        print=findViewById(R.id.printRecCashButton);
        cancel=findViewById(R.id.cancelRecCashButton);
        custNo.setMovementMethod(new ScrollingMovementMethod());


    }
}
