package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences onboardscreen;
    ImageView logo;
    boolean login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        logo=findViewById(R.id.logo);
        logo.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onboardscreen=getSharedPreferences("Onboardingscreen",MODE_PRIVATE);
                boolean isfirsttime= onboardscreen.getBoolean("First time",true);
                if (isfirsttime) {
                    SharedPreferences.Editor editor=onboardscreen.edit();
                    editor.putBoolean("First time",false);
                    editor.commit();
                    Intent intent= new Intent(SplashScreen.this, OnBoardScreen.class);
                    overridePendingTransition(R.anim.right_enter,R.anim.left_out);
                    startActivity(intent);
                }else{
                    Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
                    login=Helper.sharedpreferences.getBoolean("FIRSTTIME_LOGIN",false);
                    Intent intent;
                    if(!login){
                        intent = new Intent(SplashScreen.this, LoginScreen.class);
                    }else{
                        intent = new Intent(SplashScreen.this, HomeScreen.class);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_enter,R.anim.left_out);
                }
                finish();
            }
        },3000);
    }
}