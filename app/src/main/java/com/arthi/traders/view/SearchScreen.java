package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthi.traders.R;

public class SearchScreen extends AppCompatActivity {
    ImageView back;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("Search");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                finish();
            }
        });
    }
}