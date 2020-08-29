package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivityOn extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerViews;
    private List<String> picforbar;
    private List<String> pic;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    SliderLayout sliderLayout;
    private CarouselLayoutManager layoutManagerd;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design);
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



        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        setSliderViews();
        setSliderViews();

        showAllDataAccount();

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
            sliderView.setDescription("The Gas System produst by Falcons Soft Companey." +
                    "  " + (i + 1));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivityOn.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

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
        recyclerViews.scrollToPosition(4);
        recyclerViews.requestFocus();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_add_voucher:
                Intent AddVocher= new Intent(MainActivityOn.this,MakeVoucher.class);
                startActivity(AddVocher);
                break;
            case R.id.menu_edit_voucher:
                break;
            case R.id.menu_print_voucher:
                break;
            case R.id.menu_add_receipt_voucher:
                Intent receipt= new Intent(MainActivityOn.this, Receipt.class);
                startActivity(receipt);
                break;
            case R.id.menu_edit_receipt_voucher:
                break;
            case R.id.menu_print_receipt_voucher:
                break;
            case R.id.menu_import:
                break;
            case R.id.menu_export:
                break;
            case R.id.menu_settings:
                Intent SettingIntent= new Intent(MainActivityOn.this, AppSetting.class);
                startActivity(SettingIntent);
                break;
        }
        return false;
    }


    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView ItemName;
        ImageView itemImage;
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
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                    break;
                case 1:
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    break;
                case 2:
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_print_black_24dp));
                    break;
                case 3:
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                    break;
                case 4:
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    break;
                case 5:
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_print_black_24dp));
                    break;

                case 6:
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

                            Intent editIntent=new Intent(MainActivityOn.this,MakeVoucher.class);
                            editIntent.putExtra("EDIT_VOUCHER","EDIT_VOUCHER");
                            // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                            startActivity(editIntent);
                            break;
                        case 1://اضافة فاتورة
                            Intent AddVocher= new Intent(MainActivityOn.this,MakeVoucher.class);
                            startActivity(AddVocher);
                            break;
                        case 2://طباعة فاتورة
                            Intent PrintVoucherIntent= new Intent(MainActivityOn.this, PrintVoucher.class);
                            startActivity(PrintVoucherIntent);
                            break;
                        case 3://تعديل سند قبض
                            Intent editRecIntent=new Intent(MainActivityOn.this,Receipt.class);
                            editRecIntent.putExtra("EDIT_REC","EDIT_REC");
                            // ChequeInfo
//                            editeIntent.putExtra("ChequeInfo",chequeInfo);
                            startActivity(editRecIntent);
                            break;
                        case 4://اضافة سند قبض
                            Intent receipt= new Intent(MainActivityOn.this, Receipt.class);
                            startActivity(receipt);
                            break;
                        case 5://طباعة سند قبض
                            Intent PrintRecCashIntent= new Intent(MainActivityOn.this, PrintRecCash.class);
                            startActivity(PrintRecCashIntent);
                            break;

                        case 6://اعدادات
                            Intent SettingIntent= new Intent(MainActivityOn.this, AppSetting.class);
                            startActivity(SettingIntent);
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
