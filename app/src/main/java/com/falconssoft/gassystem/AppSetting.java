package com.falconssoft.gassystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.SettingModle;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class AppSetting extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    EditText ip,companyName,taxNo,accNo;
    Button save,cancel;
    SettingModle settingModles;
    ImageView imageView;
    private static final int SELECT_IMAGE = 3;
    private Uri fileUri;
    Bitmap serverPicBitmap;
    TextView upload;


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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                flag=1;
                openGallery();

            }
        });

    }


    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"),SELECT_IMAGE);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    fileUri = data.getData(); //added this line
                    try {
                        serverPicBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                        imageView.setImageBitmap(serverPicBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Cancelled",     Toast.LENGTH_SHORT).show();
            }


    }

    private void fillDataInSetting(SettingModle settingModles) {

        if(!TextUtils.isEmpty(settingModles.getIpAddress())) {
            ip.setText(settingModles.getIpAddress());
            companyName.setText(settingModles.getCompanyName());
            taxNo.setText(settingModles.getTaxNo());
            accNo.setText(settingModles.getAccNo());
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

                        if(serverPicBitmap!=null) {
                            settingModle.setLogo(serverPicBitmap);
                        }else
                        {

                            String ip="";
                            try {
                                ip = settingModles.getIpAddress();
                            }catch (Exception e){
                                ip="";
                            }
                           if(!TextUtils.isEmpty(ip)) {
                               settingModle.setLogo(settingModles.getLogo());
                           }else {
                               settingModle.setLogo(null);
                           }
                        }
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
         upload=findViewById(R.id.upload);
    }
}
