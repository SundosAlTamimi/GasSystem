package com.falconssoft.gassystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class SplashGasScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    Animation animation,animation2;

    CircleImageView Falcons;
    TextView Name;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);
        Falcons=findViewById(R.id.Falcon);
        Name=findViewById(R.id.name);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_left);
        Falcons.startAnimation(animation);
        Name.startAnimation(animation2);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashGasScreen.this,MainActivityOn.class);
                SplashGasScreen.this.startActivity(mainIntent);
                SplashGasScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}