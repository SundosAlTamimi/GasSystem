package com.falconssoft.gassystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.MaxSerial;
import com.falconssoft.gassystem.Modle.VoucherModle;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddCustomer extends AppCompatActivity {
    EditText counterNo, customerNo, customerName, gasPru, gasPrice, preReader, preBalance, serviceReturn, projectName;
    DatabaseHandler databaseHandler;
    TextView addCustomer, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_activaty);
        initial();


    }

    public void save() {

        if (!TextUtils.isEmpty(counterNo.getText().toString())) {
            if (!TextUtils.isEmpty(customerName.getText().toString())) {
                if (!TextUtils.isEmpty(customerNo.getText().toString())) {
                    if (!TextUtils.isEmpty(gasPru.getText().toString())) {
                        if (!TextUtils.isEmpty(gasPrice.getText().toString())) {
                            if (!TextUtils.isEmpty(preReader.getText().toString())) {

                                if (!TextUtils.isEmpty(preBalance.getText().toString())) {

                                    if (!TextUtils.isEmpty(serviceReturn.getText().toString())) {
                                        if (!TextUtils.isEmpty(projectName.getText().toString())) {
                                            if (!gasPru.getText().toString().equals(".")) {
                                                if (!gasPrice.getText().toString().equals(".")) {
                                                    if (!preReader.getText().toString().equals(".")) {
                                                        if (!preBalance.getText().toString().equals(".")) {
                                                            if (!serviceReturn.getText().toString().equals(".")) {

                                                                Customer customerOn = databaseHandler.getCustomer(counterNo.getText().toString());
                                                                String counterInCustomer = customerOn.getCounterNo();
                                                                if (TextUtils.isEmpty(counterInCustomer)) {
                                                                    Customer customer = new Customer();
                                                                    customer.setCounterNo(counterNo.getText().toString());
                                                                    customer.setCustName(customerName.getText().toString());
                                                                    customer.setAccNo(customerNo.getText().toString());
                                                                    customer.setGasPressure(Double.parseDouble(gasPru.getText().toString()));
                                                                    customer.setgPrice(Double.parseDouble(gasPrice.getText().toString()));
                                                                    customer.setCredet(Double.parseDouble(preBalance.getText().toString()));
                                                                    customer.setLastRead(Double.parseDouble(preReader.getText().toString()));
                                                                    customer.setProjectName(projectName.getText().toString());
                                                                    customer.setBadalVal(Double.parseDouble(serviceReturn.getText().toString()));
                                                                    customer.setIsPer(1);
                                                                    customer.setAddFromIn(1);
                                                                    customer.setIsExport(0);
                                                                    databaseHandler.addCustomer(customer);
                                                                    clear();
                                                                    Toast.makeText(this, "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show();

                                                                } else {

                                                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AddCustomer.this, SweetAlertDialog.WARNING_TYPE);
                                                                    sweetAlertDialog.setTitleText("");
                                                                    sweetAlertDialog.setContentText("لم يتم الحفظ , رقم العداد(" + customerOn.getCounterNo() + ") لانه للعميل (" + customerOn.getCustName() + ") الرجاء تعديل رقم العداد ؟ ");
                                                                    sweetAlertDialog.setConfirmText("الغاء");
                                                                    sweetAlertDialog.showCancelButton(true);
                                                                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                        @Override
                                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                            sweetAlertDialog.dismissWithAnimation();
                                                                        }
                                                                    });
                                                                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                                                                    sweetAlertDialog.show();
                                                                }
                                                            } else {
                                                                serviceReturn.setError("Dot!");
                                                            }
                                                        } else {
                                                            preBalance.setError("Dot!");
                                                        }
                                                    } else {
                                                        preReader.setError("Dot!");
                                                    }
                                                } else {
                                                    gasPrice.setError("Dot!");
                                                }
                                            } else {
                                                gasPru.setError("Dot!");
                                            }
                                        } else {
                                            projectName.setError("Required!");
                                        }
                                    } else {
                                        serviceReturn.setError("Required!");
                                    }

                                } else {
                                    preBalance.setError("Required!");
                                }


                            } else {
                                preReader.setError("Required!");
                            }
                        } else {
                            gasPrice.setError("Required!");
                        }
                    } else {
                        gasPru.setError("Required!");
                    }
                } else {
                    customerNo.setError("Required!");
                }
            } else {
                customerName.setError("Required!");
            }
        } else {
            counterNo.setError("Required!");
        }
    }

    void initial() {
        counterNo = findViewById(R.id.counter_no);
        customerNo = findViewById(R.id.custNo);
        customerName = findViewById(R.id.cust_name);
        gasPru = findViewById(R.id.gasPressure);
        gasPrice = findViewById(R.id.gasPrice);
        preReader = findViewById(R.id.pre_read);
        preBalance = findViewById(R.id.previous_balance);
        serviceReturn = findViewById(R.id.service_return);
        projectName = findViewById(R.id.projectName);
        addCustomer = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        databaseHandler = new DatabaseHandler(AddCustomer.this);

        cancel.setOnClickListener(onClickCustomer);
        addCustomer.setOnClickListener(onClickCustomer);
    }

    View.OnClickListener onClickCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.save:
                    save();
                    break;
                case R.id.cancel:
                    finish();
                    break;
            }

        }
    };


    void clear() {
        counterNo.setText("");
        customerNo.setText("");
        customerName.setText("");
        gasPru.setText("");
        gasPrice.setText("");
        preReader.setText("");
        preBalance.setText("");
        serviceReturn.setText("0.0");
        projectName.setText("");

    }


}

