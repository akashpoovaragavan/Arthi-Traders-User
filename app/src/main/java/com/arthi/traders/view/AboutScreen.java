package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthi.traders.R;
import com.google.android.material.textfield.TextInputEditText;

public class AboutScreen extends AppCompatActivity {
    ImageView back;
    TextView title;
    TextView about_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("About Us");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                finish();
            }
        });
        about_txt=findViewById(R.id.about_txt);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            about_txt.setText(Html.fromHtml(getResources().getString(R.string.about),Html.FROM_HTML_MODE_LEGACY));
        } else {
            about_txt.setText(Html.fromHtml(getResources().getString(R.string.about)));
        }
    }
}