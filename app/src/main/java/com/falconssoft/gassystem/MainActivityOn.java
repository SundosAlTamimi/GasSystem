package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.facebook.stetho.Stetho;
import com.falconssoft.gassystem.Modle.PrintSetting;
import com.falconssoft.gassystem.Modle.RecCash;
import com.falconssoft.gassystem.Modle.VoucherModle;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityOn extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerViews;
    private List<String> picforbar;
    private List<String> pic;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    SliderLayout sliderLayout;
    GlobelFunction globelFunction;
    private CarouselLayoutManager layoutManagerd;
    String ipAddress="noSetting";
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design);
        Stetho.initializeWithDefaults(this);
        recyclerViews = (RecyclerView) findViewById(R.id.res);
        sliderLayout = findViewById(R.id.imageSlider_2);
        picforbar=new ArrayList<>();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.main_nav_view);
        drawerLayout = findViewById(R.id.main_drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        picforbar.add("تعديل فاتورة");
        picforbar.add("اضافة فاتورة");
        picforbar.add("طباعة فاتورة");

        picforbar.add(" تعديل سند قبض");
        picforbar.add("اضافة سند قبض");
        picforbar.add("طباعة سند قبض");

        picforbar.add("اعدادات");


        widthFun();

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        setSliderViews();
        setSliderViews();

        showAllDataAccount();
        databaseHandler=new DatabaseHandler(MainActivityOn.this);

        globelFunction=new GlobelFunction();
        ipAddress=globelFunction.GlobelFunctionSetting(databaseHandler);

    }

    private void widthFun() {

        Display display = getWindowManager().getDefaultDisplay();
        int SCREEN_WIDTH = display.getWidth();
        int SCREEN_HEIGHT = display.getHeight();

        DisplayMetrics metrics = new DisplayMetrics();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("screenWidth", Integer.valueOf(metrics.widthPixels));
        map.put("screenHeight", Integer.valueOf(metrics.heightPixels));
        map.put("screenDensity", Integer.valueOf(metrics.densityDpi));

        Log.e("size",""+SCREEN_WIDTH+"    "+SCREEN_HEIGHT+"\n"+map.get("screenDensity"));
    }


    private void setSliderViews() {
        for (int i = 0; i < 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);
//            sliderView.setImageScaleType(ImageView.ScaleType.FIT_END);
            switch (i) {
                case 0:

                    sliderView.setImageDrawable(R.drawable.gas_bac);

                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.gas_pic);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.gasrr);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.images_gas);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescriptionTextColor(getResources().getColor(R.color.Orange));
            sliderView.setDescription("The Gas System Product By Falcons Soft Company." +
                    "  " + (i + 1));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(MainActivityOn.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }


    void showAllDataAccount() {

        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);

        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViews.setAdapter(new TestAdapterForbar(this, picforbar));
        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition(6);
        recyclerViews.requestFocus();


    }


    void importDataToServer(){



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_add_voucher:
                finish();
                Intent AddVocher= new Intent(MainActivityOn.this,MakeVoucher.class);
                startActivity(AddVocher);
                break;
            case R.id.menu_edit_voucher:
                finish();

                Intent editIntent=new Intent(MainActivityOn.this,MakeVoucher.class);
                editIntent.putExtra("EDIT_VOUCHER","EDIT_VOUCHER");
                // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                startActivity(editIntent);
                break;
            case R.id.menu_print_voucher:
                finish();
                Intent PrintVoucherIntent= new Intent(MainActivityOn.this, PrintVoucher.class);
                startActivity(PrintVoucherIntent);
                break;
            case R.id.menu_add_receipt_voucher:
                finish();
                Intent receipt= new Intent(MainActivityOn.this, Receipt.class);
                startActivity(receipt);
                break;
            case R.id.menu_edit_receipt_voucher:
                finish();
                Intent editRecIntent=new Intent(MainActivityOn.this,Receipt.class);
                editRecIntent.putExtra("EDIT_REC","EDIT_REC");
                // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                startActivity(editRecIntent);
                break;
            case R.id.menu_print_receipt_voucher:
                finish();
                Intent PrintRecCashIntent= new Intent(MainActivityOn.this, PrintRecCash.class);
                startActivity(PrintRecCashIntent);

                break;
            case R.id.menu_import:
                ipAddress=globelFunction.GlobelFunctionSetting(databaseHandler);
                Log.e("ipAddress","globelFunction"+ipAddress);
                if (!ipAddress.equals("noSetting")) {

                    importJson sendCloud = new importJson(MainActivityOn.this, 1);
                    sendCloud.startSending("GET_CUSTOMER");

                } else {

                    new SweetAlertDialog(MainActivityOn.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("الاعدادات " + "!")
                            .setContentText("الرجاء اضافة الاعدادات ")
                            .setConfirmText("الغاء")
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(null).show();


                }

                break;
            case R.id.menu_export:
                exportDataToServer();
                break;
            case R.id.menu_settings:

                passwordForSetting(2);
                break;

            case R.id.menu_printer_settings:

                passwordForSetting(1);

                break;
        }
        return false;
    }


    void exportDataToServer(){
        boolean isExported=false,isExportedRec=false;

        List<VoucherModle> voucherModleList=new ArrayList<>();
        List<RecCash> recCashes=new ArrayList<>();

        voucherModleList=databaseHandler.getAllVouchersExport();
        recCashes=databaseHandler.getRecCashExport();
        JSONArray obj2 = new JSONArray();
        for (int i = 0; i < voucherModleList.size(); i++) {

                obj2.put(voucherModleList.get(i).getJSONObjectVoucher());
                isExported = true;

        }


        JSONObject obj = new JSONObject();
        try {
            obj.put("INV", obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

if(isExported) {
    ExportJeson sendCloud = new ExportJeson(MainActivityOn.this, obj);
    sendCloud.startSending("ExportVoucher");
}else{

    Toast.makeText(this, "جميع الفواتير تم تصديرها مسبقا", Toast.LENGTH_SHORT).show();

}


//__________________________________________________________________
        JSONArray ArrayRec = new JSONArray();
        for (int i = 0; i < recCashes.size(); i++) {

            ArrayRec.put(recCashes.get(i).getJSONObjectRecCash());
            isExportedRec = true;

        }


        JSONObject objRec = new JSONObject();
        try {
            objRec.put("REC", ArrayRec);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(isExportedRec) {
            ExportJeson sendCloud = new ExportJeson(MainActivityOn.this, objRec);
            sendCloud.startSending("ExportRecCash");
        }else{

            Toast.makeText(this, "جميع السندات تم تصديرها مسبقا ", Toast.LENGTH_SHORT).show();

        }


    }

public void passwordForSetting(final int flagSetting){
    final EditText editText = new EditText(MainActivityOn.this);
    final TextView textView = new TextView(MainActivityOn.this);
    editText.setHint("ادخل كلمة السر ");
    editText.setTextColor(Color.BLACK);
    textView.setTextColor(Color.RED);
    if (SweetAlertDialog.DARK_STYLE) {
        editText.setTextColor(Color.BLACK);
    }
    final LinearLayout linearLayout = new LinearLayout(getApplicationContext());
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.addView(editText);
    linearLayout.addView(textView);

    final SweetAlertDialog dialog = new SweetAlertDialog(MainActivityOn.this, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("كلمة السر ")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    String password=editText.getText().toString();
                    textView.setText("");
                    if(!password.equals("")) {

                        if (flagSetting==1) {

                            if (password.equals("112020")) {

                                textView.setText("");
                                printerSettingDialog();

                                sweetAlertDialog.dismissWithAnimation();

                            } else {
                                textView.setText("كلمة السر خطا ");
                            }

                        }else if ( flagSetting==2){

                            if (password.equals("112020")) {

                                textView.setText("");
                                finish();
                                Intent SettingIntent= new Intent(MainActivityOn.this, AppSetting.class);
                                startActivity(SettingIntent);

                                sweetAlertDialog.dismissWithAnimation();

                            } else {
                                textView.setText("كلمة السر خطا ");
                            }

                        }
                    }

                }
            });
//                        .hideConfirmButton();

    dialog.setCustomView(linearLayout);
    dialog.show();

}
    public void printerSettingDialog(){
        final Dialog dialog = new Dialog(this,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.setting_printer_layout);
        dialog.setCancelable(true);

        final RadioButton greenGass,smartPhone,cpclPrinter,EscPosPrinter;


        greenGass=dialog.findViewById(R.id.GreenGas);
        smartPhone=dialog.findViewById(R.id.smartPhone);
        cpclPrinter=dialog.findViewById(R.id.cpclPrinter);
        EscPosPrinter=dialog.findViewById(R.id.escPrinter);


        Button save,cancel;

        save=dialog.findViewById(R.id.save_);
        cancel=dialog.findViewById(R.id.cancel_btn);

        PrintSetting printSetting=databaseHandler.getPrinterSetting();
        String printTypes=printSetting.getPrintType();
        String formTypes=printSetting.getFormType();

        if(!TextUtils.isEmpty(printTypes)){

            if(printTypes.equals("1")){
                EscPosPrinter.setChecked(true);

            }else  if(printTypes.equals("0")){
                cpclPrinter.setChecked(true);
            }


            if(formTypes.equals("1")){
                smartPhone.setChecked(true);

            }else  if(formTypes.equals("0")){
                greenGass.setChecked(true);
            }

        }else {

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String printType="0";
                String formType="0";
                if(cpclPrinter.isChecked()){
                    printType="0";//sewo big printer
                }else if(EscPosPrinter.isChecked()) {
                    printType="1";//mopile printer
                }

                if(greenGass.isChecked()){
                    formType="0";
                }else if(smartPhone.isChecked()) {
                    formType="1";
                }

                databaseHandler.deleteAllPrintSetting();
                databaseHandler.addPrintSettingTable(new PrintSetting(printType,formType));

                Toast.makeText(MainActivityOn.this, "Save Successful", Toast.LENGTH_SHORT).show();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 dialog.dismiss();

            }
        });

        dialog.show();

    }


    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView ItemName;
        CircleImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForbar( View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.textbar);
            layBar = itemView.findViewById(R.id.layBar);
            itemImage = itemView.findViewById(R.id.imgbar);
        }
    }

    class TestAdapterForbar extends RecyclerView.Adapter<CViewHolderForbar> {
        Context context;
        List<String> list;
//DatabaseHandler db;

        public TestAdapterForbar(Context context, List<String> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }


        @Override
        public CViewHolderForbar onCreateViewHolder( ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.bar_item, viewGroup, false);
            return new CViewHolderForbar(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder( final CViewHolderForbar cViewHolder, final int i) {
            cViewHolder.ItemName.setText(list.get(i));
            cViewHolder.layBar.setTag("" + i);

            switch (i){

                case 0:

//                    DrawableCompat.setTint(
//                            DrawableCompat.wrap(cViewHolder.itemImage.getDrawable()),
//                            ContextCompat.getColor(context, R.color.Orange)
//                    );

                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.Orange));
                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.Orange));

                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_edit_black_24dp));

                    break;
                case 1:
                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.Orange));
                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.Orange));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    break;
                case 2:
                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.Orange));
                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.Orange));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_print_black_24dp));
                    break;
                case 3:
//                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.dark_blue));
//                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.dark_blue));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                    break;
                case 4:
//                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.dark_blue));
//                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.dark_blue));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    break;
                case 5:
//                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.dark_blue));
//                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.dark_blue));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_print_black_24dp));
                    break;

                case 6:
                    cViewHolder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.blue_ice));
                    cViewHolder.itemImage.setBorderColor(context.getResources().getColor(R.color.blue_ice));
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_settings_black_24dp));
                    break;

            }


            final boolean[] longIsOpen = {false};
            cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    picforbar.add("تعديل فاتورة");
//                    picforbar.add("اضافة فاتورة");
//                    picforbar.add("طباعة فاتورة");
//
//                    picforbar.add(" تعديل سند قبض");
//                    picforbar.add("اضافة سند قبض");
//                    picforbar.add("طباعة سند قبض");
//
//                    picforbar.add("اعدادات");


                    switch (i){
                        case 0://تعديل فاتورة
                            finish();
                            Intent editIntent=new Intent(MainActivityOn.this,MakeVoucher.class);
                            editIntent.putExtra("EDIT_VOUCHER","EDIT_VOUCHER");
                            // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                            startActivity(editIntent);
                            break;
                        case 1://اضافة فاتورة
                            finish();
                            Intent AddVocher= new Intent(MainActivityOn.this,MakeVoucher.class);
                            startActivity(AddVocher);
                            break;
                        case 2://طباعة فاتورة
                            finish();
                            Intent PrintVoucherIntent= new Intent(MainActivityOn.this, PrintVoucher.class);
                            startActivity(PrintVoucherIntent);
                            break;
                        case 3://تعديل سند قبض
                            finish();
                            Intent editRecIntent=new Intent(MainActivityOn.this,Receipt.class);
                            editRecIntent.putExtra("EDIT_REC","EDIT_REC");
                            // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                            startActivity(editRecIntent);
                            break;
                        case 4://اضافة سند قبض
                            finish();
                            Intent receipt= new Intent(MainActivityOn.this, Receipt.class);
                            startActivity(receipt);
                            break;
                        case 5://طباعة سند قبض
                            finish();
                            Intent PrintRecCashIntent= new Intent(MainActivityOn.this, PrintRecCash.class);
                            startActivity(PrintRecCashIntent);
                            break;

                        case 6://اعدادات
                            passwordForSetting(2);
                            break;
                    }

                }
            });

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
