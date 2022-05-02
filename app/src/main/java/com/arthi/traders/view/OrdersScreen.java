package com.arthi.traders.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.VolleySingleton;
import com.arthi.traders.model.Order;
import com.honglei.jstablayout.JSTabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrdersScreen extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back;
    TextView title;
    ViewPager viewPager;
    JSTabLayout tabLayout;
    List<String> titles = new ArrayList<>();
    List<Order>  items,itemsshipping,itemspending,itemscanceled,itemsprocessing;

    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    String dol_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("Orders");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tablayout);

        swipelayout=findViewById(R.id.swipelayout);
        emptyLayout=findViewById(R.id.emptyLayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id=Helper.sharedpreferences.getString("dol_user_id","");


        titles.add("Pending");
        titles.add("Processing");
        titles.add("Shipping");
        titles.add("Delivered");
        titles.add("Cancelled");

        getOrderHistory();
    }


    @Override
    public void onRefresh() {
        getOrderHistory();
    }

    public void getOrderHistory() {
        Helper.loading(OrdersScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.orderhistory_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("orderhistory", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            items = new ArrayList<>();
                            itemsshipping = new ArrayList<>();
                            itemspending = new ArrayList<>();
                            itemscanceled = new ArrayList<>();
                            itemsprocessing = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String msg = obj.getString("message");
                                if (!msg.trim().equals("failed")) {
                                    emptyLayout.setVisibility(View.GONE);
                                    /*tb2.setVisibility(View.VISIBLE);
                                    vp2.setVisibility(View.VISIBLE);*/
                                    String dol_order_id = obj.getString("dol_order_id");
                                    String dol_order_user_id = obj.getString("dol_order_user_id");
                                    String dol_order_product_code = obj.getString("dol_order_product_code");
                                    String dol_order_product_qty = obj.getString("dol_order_product_qty");
                                    String dol_order_product_price = obj.getString("dol_order_product_price");
                                    String dol_order_total = obj.getString("dol_order_total");
                                    String dol_order_sum = obj.getString("dol_order_sum");
                                    String dol_order_shipping = obj.getString("dol_order_shipping");
                                    String dol_order_status = obj.getString("dol_order_status");
                                    String dol_order_created_at = obj.getString("dol_order_created_at");
                                    String dol_order_updated_at = obj.getString("dol_order_updated_at");
                                    String dol_category = obj.getString("dol_category");
                                    String dol_sub_category = obj.getString("dol_sub_category");
                                    String dol_product_name = obj.getString("dol_product_name");
                                    String dol_product_description = obj.getString("dol_product_description");
                                    String dol_product_pack = obj.getString("dol_product_pack");
                                    String dol_product_weight = obj.getString("dol_product_weight");
                                    String dol_product_image = obj.getString("dol_product_image");
                                    String dol_product_confirmed = obj.getString("confirmed");
                                    String dol_product_pending = obj.getString("pending");


                                    Order orderHistory = new Order();
                                    orderHistory.setOrderId(dol_order_id);
                                    orderHistory.setUserid(dol_order_user_id);
                                    orderHistory.setProductordercode(dol_order_product_code);
                                    orderHistory.setProductorderquantity(dol_order_product_qty);
                                    orderHistory.setProductorderprice(dol_order_product_price);
                                    orderHistory.setProductordertotal(dol_order_total);
                                    orderHistory.setProductordersum(dol_order_sum);
                                    orderHistory.setProductordershipping(dol_order_shipping);
                                    orderHistory.setOrderstatus(dol_order_status);
                                    orderHistory.setProductordercreatedate(dol_order_created_at);
                                    orderHistory.setProductorderupdatedate(dol_order_updated_at);
                                    orderHistory.setProductordercategory(dol_category);
                                    orderHistory.setProductordersubcategory(dol_sub_category);
                                    orderHistory.setProductordername(dol_product_name);
                                    orderHistory.setProductorderdesc(dol_product_description);
                                    orderHistory.setProductorderpack(dol_product_pack);
                                    orderHistory.setProductorderweight(dol_product_weight);
                                    orderHistory.setProductorderimage(dol_product_image);
                                    orderHistory.setProductconfirmed(dol_product_confirmed);
                                    orderHistory.setProductpending(dol_product_pending);

                                    if (dol_order_status.trim().equals("delivered"))
                                        items.add(orderHistory);
                                    else if (dol_order_status.trim().equals("pending"))
                                        itemspending.add(orderHistory);
                                    else if (dol_order_status.trim().equals("shipping"))
                                        itemsshipping.add(orderHistory);
                                    else if (dol_order_status.trim().equals("canceled"))
                                        itemscanceled.add(orderHistory);
                                    else if (dol_order_status.trim().equals("processing"))
                                        itemsprocessing.add(orderHistory);
                                }
                                else{
                                    emptyLayout.setVisibility(View.VISIBLE);
                                   /* tb2.setVisibility(View.GONE);
                                    vp2.setVisibility(View.GONE);*/
                                }

                            }
                            if (items.size() != 0 || itemspending.size() != 0 ||itemsshipping.size() != 0 ||itemscanceled.size() != 0 ||itemsprocessing.size() != 0 ) {
                                Set<Order> set = new HashSet<>(items);
                                items.clear();
                                items.addAll(set);
                                Set<Order> set1 = new HashSet<>(itemspending);
                                itemspending.clear();
                                itemspending.addAll(set1);
                                Set<Order> set2 = new HashSet<>(itemsshipping);
                                itemsshipping.clear();
                                itemsshipping.addAll(set2);
                                Set<Order> set3 = new HashSet<>(itemscanceled);
                                itemscanceled.clear();
                                itemscanceled.addAll(set3);
                                Set<Order> set4 = new HashSet<>(itemsprocessing);
                                itemsprocessing.clear();
                                itemsprocessing.addAll(set4);

                                Collections.sort(items, byDate);
                                Collections.sort(itemspending, byDate);
                                Collections.sort(itemsshipping, byDate);
                                Collections.sort(itemscanceled, byDate);
                                Collections.sort(itemsprocessing, byDate);

                                inittb2(items,itemspending,itemsshipping,itemscanceled,itemsprocessing,jarr);

                            } else {
                                emptyLayout.setVisibility(View.VISIBLE);
                               /* tb2.setVisibility(View.GONE);
                                vp2.setVisibility(View.GONE);*/
                            }
                        }
                        else{
                            emptyLayout.setVisibility(View.VISIBLE);
                            /*tb2.setVisibility(View.GONE);
                            vp2.setVisibility(View.GONE);*/
                        }

                    } catch (Exception e) {
                        Helper.mProgressBarHandler.hide();
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(OrdersScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Helper.mProgressBarHandler.hide();
                        Log.e("err", error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dol_order_user_id", dol_user_id);
                return params;
            }
        };
        VolleySingleton.getInstance(OrdersScreen.this).addToRequestQueue(stringRequest);
    }

    static final Comparator<Order> byDate = new Comparator<Order>() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        public int compare(Order ord1, Order ord2) {
            Date d1 = null;
            Date d2 = null;
            try {
                if(ord1.getProductordercreatedate().contains(":") && ord2.getProductordercreatedate().contains(":") ) {
                    d1 = sdf.parse(ord1.getProductordercreatedate());
                    d2 = sdf.parse(ord2.getProductordercreatedate());
                }
                else{
                    d1 = sdf1.parse(ord1.getProductordercreatedate());
                    d2 = sdf1.parse(ord2.getProductordercreatedate());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
            //  return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
        }
    };

    @SuppressWarnings("deprecation")
    public void inittb2(List<Order> items, List<Order> itemspending, List<Order> itemsshipping, List<Order> itemscanceled, List<Order> itemsprocessing, JSONArray jarr) {
        try {
            Log.e("data--","Count"+itemspending.size());
            FragmentStatePagerAdapter fragmentPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        case 1:
                            return new Fragment_Processing("processing",itemsprocessing,jarr);
                        case 2:
                            return new Fragment_Shipping("shipping",itemsshipping,jarr);
                        case 3:
                            return new Fragment_Delivered("delivered",items,jarr);
                        case 4:
                            return new Fragment_Cancelled("canceled",itemscanceled,jarr);
                    }
                    return new Fragment_Pending("pending",itemspending,jarr);
                }

                @Override
                public int getCount() {
                    return titles.size();
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return titles.get(position);
                }
            };
            viewPager.setAdapter(fragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}