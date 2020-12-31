package com.falconssoft.gassystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.SettingModle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AppSetting extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    EditText ip, companyName, taxNo, accNo, serialVoucherTextSetting, serialRecTextSetting;
    Button save, cancel;
    SettingModle settingModles;
    ImageView imageView;
    private static final int SELECT_IMAGE = 3;
    private Uri fileUri;
    Bitmap serverPicBitmap;
    TextView upload, serialButton;
    CheckBox saveCheckBox;
    GlobelFunction globelFunction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_new);
        init();

        settingModles = databaseHandler.getSetting();
        fillDataInSetting(settingModles);
        globelFunction = new GlobelFunction();
        globelFunction.GlobelFunctionSetting(databaseHandler);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSetting.this, MainActivityOn.class);
                startActivity(intent);
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                flag=1;
                openGallery();

            }
        });

        serialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordForSetting();


            }
        });

    }

    public void passwordForSetting() {
        final EditText editText = new EditText(AppSetting.this);
        final TextView textView = new TextView(AppSetting.this);
        editText.setHint("  كلمة السر ");
        editText.setTextColor(Color.BLACK);
        textView.setTextColor(Color.RED);
        if (SweetAlertDialog.DARK_STYLE) {
            editText.setTextColor(Color.BLACK);
        }
        final LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);
        linearLayout.addView(textView);

        final SweetAlertDialog dialog = new SweetAlertDialog(AppSetting.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(" هذا قد يغير تسلسل الفواتير وسندات القبض اذا كنت تريد المتابعة ادخل كلمة السر ")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        String password = editText.getText().toString();
                        textView.setText("");
                        if (!password.equals("")) {


                            if (password.equals("112020")) {

                                textView.setText("");

                                int maxRec = databaseHandler.getMaxNo("RECCASH", 0);
                                int maxVou = databaseHandler.getMaxNo("VOUCHERS_TABLE", 1);

                                Log.e("RECCASH",""+maxRec+"   "+maxVou );

                                if (maxRec < Integer.parseInt(serialRecTextSetting.getText().toString())) {
                                    if (maxVou < Integer.parseInt(serialVoucherTextSetting.getText().toString())) {
                                        Log.e("RECCASH","true" );

                                        if (!serialRecTextSetting.getText().toString().equals("-1") && !serialVoucherTextSetting.getText().toString().equals("-1")) {
                                            save();
                                            databaseHandler.updateMaxVoucher(serialVoucherTextSetting.getText().toString());
                                            databaseHandler.updateMaxRec(serialRecTextSetting.getText().toString());

                                    } else {
                                        Toast.makeText(AppSetting.this, "No Data Found in setting", Toast.LENGTH_SHORT).show();
                                    }
                                    sweetAlertDialog.dismissWithAnimation();
                                    }else{
                                        Toast.makeText(AppSetting.this, "الرجاء تغير تسلسل الفاتوره ", Toast.LENGTH_SHORT).show();
                                        serialVoucherTextSetting.setError("Required!");
                                    }
                                } else {
                                    Toast.makeText(AppSetting.this, "الرجاء تغير تسلسل سند القبض ", Toast.LENGTH_SHORT).show();

                                    serialRecTextSetting.setError("Required!");
                                }
                            } else {
                                textView.setText("كلمة السر خطا ");
                            }


                        }


                    }
                });
//                        .hideConfirmButton();

        dialog.setCustomView(linearLayout);
        dialog.show();

    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                fileUri = data.getData(); //added this line
                try {
                    serverPicBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                    imageView.setImageBitmap(serverPicBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }


    }

    private void fillDataInSetting(SettingModle settingModles) {

        if (!TextUtils.isEmpty(settingModles.getIpAddress())) {
            ip.setText(settingModles.getIpAddress());
            companyName.setText(settingModles.getCompanyName());
            taxNo.setText(settingModles.getTaxNo());
            accNo.setText(settingModles.getAccNo());
            serialRecTextSetting.setText(settingModles.getRecSerial());
            serialVoucherTextSetting.setText(settingModles.getVoucherSerial());
            imageView.setImageBitmap(settingModles.getLogo());
            if (settingModles.getSavePrint() == 1) {
                saveCheckBox.setChecked(true);
            } else {
                saveCheckBox.setChecked(false);
            }

        } else {

        }
    }

    void save() {

        if (!TextUtils.isEmpty(ip.getText().toString())) {
            if (!TextUtils.isEmpty(companyName.getText().toString())) {
                if (!TextUtils.isEmpty(taxNo.getText().toString())) {
                    if (!TextUtils.isEmpty(accNo.getText().toString())) {
                        if (!TextUtils.isEmpty(serialVoucherTextSetting.getText().toString())) {
                            if (!TextUtils.isEmpty(serialRecTextSetting.getText().toString())) {
                                //save in DB
                                SettingModle settingModle = new SettingModle();

                                settingModle.setIpAddress(ip.getText().toString());
                                settingModle.setCompanyName(companyName.getText().toString());
                                settingModle.setTaxNo(taxNo.getText().toString());
                                settingModle.setAccNo(accNo.getText().toString());
                                settingModle.setVoucherSerial(serialVoucherTextSetting.getText().toString());
                                settingModle.setRecSerial(serialRecTextSetting.getText().toString());

                                if (saveCheckBox.isChecked()) {
                                    settingModle.setSavePrint(1);
                                } else {
                                    settingModle.setSavePrint(0);
                                }

                                if (serverPicBitmap != null) {
                                    settingModle.setLogo(serverPicBitmap);
                                } else {

                                    String ip = "";
                                    try {
                                        ip = settingModles.getIpAddress();
                                    } catch (Exception e) {
                                        ip = "";
                                    }
                                    if (!TextUtils.isEmpty(ip)) {
                                        settingModle.setLogo(settingModles.getLogo());
                                    } else {
                                        settingModle.setLogo(null);
                                    }
                                }
                                databaseHandler.deleteAllSetting();
                                databaseHandler.addSetting(settingModle);
                                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                serialRecTextSetting.setError("Required!");
                            }
                        } else {
                            serialVoucherTextSetting.setError("Required!");
                        }
                    } else {
                        accNo.setError("Required!");
                    }
                } else {
                    taxNo.setError("Required!");
                }
            } else {
                companyName.setError("Required!");
            }
        } else {
            ip.setError("Required!");
        }

    }

    void init() {

        ip = findViewById(R.id.ipTextSetting);
        companyName = findViewById(R.id.companyNameTextSetting);
        taxNo = findViewById(R.id.taxNoTextSetting);
        accNo = findViewById(R.id.accNoTextSetting);
        serialVoucherTextSetting = findViewById(R.id.serialVoucherTextSetting);
        serialRecTextSetting = findViewById(R.id.serialRecTextSetting);
        save = findViewById(R.id.saveButtonSetting);
        cancel = findViewById(R.id.cancelButtonSetting);
        imageView = findViewById(R.id.logoImageSetting);
        databaseHandler = new DatabaseHandler(AppSetting.this);
        upload = findViewById(R.id.upload);
        saveCheckBox = findViewById(R.id.saveCheckBox);
        serialButton = findViewById(R.id.serialButton);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AppSetting.this, MainActivityOn.class);
        startActivity(intent);
        finish();
    }

}
