package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.arthi.traders.R;
import com.arthi.traders.controller.OnboardAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class OnBoardScreen extends AppCompatActivity {
    ViewPager mSLideViewPager;
    Button skipbtn;
    OnboardAdapter onboardAdapter;
    DotsIndicator dotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSLideViewPager = findViewById(R.id.slideViewPager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        skipbtn = findViewById(R.id.skipButton);
        onboardAdapter=new OnboardAdapter(this);
        mSLideViewPager.setAdapter(onboardAdapter);
        dotsIndicator.setViewPager(mSLideViewPager);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(OnBoardScreen.this, LoginScreen.class));
                overridePendingTransition(R.anim.right_enter,R.anim.left_out);
                finish();
            }
        });
    }
}