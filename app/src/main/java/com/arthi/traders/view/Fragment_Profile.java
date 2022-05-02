package com.arthi.traders.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;

public class Fragment_Profile extends Fragment {
    ImageView back;
    TextView title,logout,name,email,mobile,address;
    LinearLayout order,request,payment,about;
    String username,str_email,str_mobile,str_address;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        title=view.findViewById(R.id.title);
        back=view.findViewById(R.id.back);
        title.setText("Profile");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeScreen.class));
            }
        });

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        mobile=view.findViewById(R.id.mobile);
        address=view.findViewById(R.id.address);

        Helper.sharedpreferences = getContext().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        username=Helper.sharedpreferences.getString("dol_user_username","");
        str_mobile=Helper.sharedpreferences.getString("dol_user_mobile","");
        str_email=Helper.sharedpreferences.getString("dol_user_email","");
        str_address=Helper.sharedpreferences.getString("dol_user_address","");

        name.setText(username);
        email.setText(str_email);
        mobile.setText(str_mobile);
        address.setText(str_address);

        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                ed.putBoolean("FIRSTTIME_LOGIN", false);
                ed.clear();
                ed.apply();
                startActivity(new Intent(getContext(),LoginScreen.class));
            }
        });

        order=view.findViewById(R.id.my_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),OrdersScreen.class));
            }
        });

        request=view.findViewById(R.id.my_request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RequestScreen.class));
            }
        });
        payment=view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PaymentMethodScreen.class));
            }
        });
        about=view.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AboutScreen.class));
            }
        });

        return view;
    }
}
