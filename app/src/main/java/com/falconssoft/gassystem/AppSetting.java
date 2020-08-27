package com.falconssoft.gassystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.SettingModle;

import java.util.List;


public class AppSetting extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    EditText ip,companyName,taxNo,accNo;
    Button save,cancel;
    SettingModle settingModles;
    ImageView imageView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_new);
        init();

        settingModles= databaseHandler.getSetting();
        fillDataInSetting(settingModles);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }

    private void fillDataInSetting(SettingModle settingModles) {

        if(TextUtils.isEmpty(settingModles.getIpAddress())) {
            ip.setText(settingModles.getIpAddress());
            companyName.setText(settingModles.getIpAddress());
            taxNo.setText(settingModles.getIpAddress());
            accNo.setText(settingModles.getIpAddress());
            imageView.setImageBitmap(settingModles.getLogo());

        }else{

        }
    }

    void save(){

        if (!TextUtils.isEmpty(ip.getText().toString())) {
            if (!TextUtils.isEmpty(companyName.getText().toString())) {
                if (!TextUtils.isEmpty(taxNo.getText().toString())) {
                    if (!TextUtils.isEmpty(accNo.getText().toString())) {
                        //save in DB
                        SettingModle settingModle=new SettingModle();

                        settingModle.setIpAddress(ip.getText().toString());
                        settingModle.setCompanyName(companyName.getText().toString());
                        settingModle.setTaxNo(taxNo.getText().toString());
                        settingModle.setAccNo(accNo.getText().toString());

//                        settingModle.setLogo(ip.getText().toString());
                        databaseHandler.deleteAllSetting();
                        databaseHandler.addSetting(settingModle);
                        Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();

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

         ip=findViewById(R.id.ipTextSetting);
         companyName=findViewById(R.id.companyNameTextSetting);
         taxNo=findViewById(R.id.taxNoTextSetting);
         accNo=findViewById(R.id.accNoTextSetting);
         save=findViewById(R.id.saveButtonSetting);
         cancel=findViewById(R.id.cancelButtonSetting);
         imageView=findViewById(R.id.logoImageSetting);
         databaseHandler=new DatabaseHandler(AppSetting.this);
    }
}
