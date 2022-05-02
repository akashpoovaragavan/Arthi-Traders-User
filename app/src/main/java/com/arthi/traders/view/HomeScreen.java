package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arthi.traders.R;
import com.arthi.traders.constant.DBHelper;
import com.arthi.traders.model.Product;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Objects;

public class HomeScreen extends AppCompatActivity {
   public static ChipNavigationBar chip_bottom;
    public static int pendingCartCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        chip_bottom = findViewById(R.id.chip_bottom);
        chip_bottom.setItemEnabled(R.id.home_nav,true);
        chip_bottom.setItemSelected(R.id.home_nav,true);
        chip_bottom.requestFocus(R.id.home_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();

        /*setbadge count**/
        DBHelper db = new DBHelper(HomeScreen.this);
        ArrayList<Product> array_list = db.getAllOrders();
        HomeScreen.pendingCartCount = array_list.size();
        Log.e("pend ",pendingCartCount+".");
        if(pendingCartCount !=0)
            chip_bottom.showBadge(R.id.cart_nav,pendingCartCount);


        chip_bottom.setOnItemSelectedListener(id -> {
            Fragment selectedfrag = null;
            switch (id) {
                case R.id.home_nav:
                    selectedfrag = new Fragment_Home();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfrag).commit();
                    break;
                case R.id.cart_nav:
                    selectedfrag = new Fragment_Cart();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfrag).commit();
                    break;
                case R.id.notification_nav:
                    selectedfrag = new Fragment_Notification();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfrag).commit();
                    break;
                case R.id.profile_nav:
                    selectedfrag = new Fragment_Profile();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfrag).commit();
                    break;
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}