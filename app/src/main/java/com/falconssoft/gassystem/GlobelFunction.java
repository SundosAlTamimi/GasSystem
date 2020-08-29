package com.falconssoft.gassystem;

import android.content.Context;
import android.text.TextUtils;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.SettingModle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class GlobelFunction {
    Context context;
    DatabaseHandler databaseHandler;
    public  static String ipAddress;


    public GlobelFunction() {


    }

    public String GlobelFunctionSetting(DatabaseHandler databaseHandler) {

        this.databaseHandler=databaseHandler;
        SettingModle settingModle=databaseHandler.getSetting();
        ipAddress=settingModle.getIpAddress();
        if(TextUtils.isEmpty(ipAddress)){
            return  ipAddress;
        }else{
            return "noSetting";
        }

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public  String DateInToday(){

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        return convertToEnglish(today);
    }




}
