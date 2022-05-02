
package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
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
import com.arthi.traders.controller.PaymentAdapter;
import com.arthi.traders.model.Payment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentHistoryScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back;
    TextView title;
    RecyclerView payment_recycler;
    PaymentAdapter paymentAdapter;
    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    String dol_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("Payment History");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                finish();
            }
        });
        swipelayout=findViewById(R.id.swipelayout);
        emptyLayout=findViewById(R.id.emptyLayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        Helper.sharedpreferences = getApplication().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id=Helper.sharedpreferences.getString("dol_user_id","");


        payment_recycler=findViewById(R.id.payment_recycler);
        payment_recycler.setHasFixedSize(true);
        payment_recycler.setLayoutManager(new LinearLayoutManager(PaymentHistoryScreen.this, LinearLayoutManager.VERTICAL, false));

        getPaymentHistory();

    }

    @Override
    public void onRefresh() {
        getPaymentHistory();
    }

    public void getPaymentHistory() {
        Helper.loading(PaymentHistoryScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.paymenthistory_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("getPaymentHistory", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            List<Payment> items = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String msg = obj.getString("message");
                                if (!msg.trim().equals("failed")) {
                                    String dol_payment_user_id = obj.getString("user_id");
                                    String dol_payment_amt = obj.getString("payments_amt");
                                    String dol_payment_date = obj.getString("payments_date");
                                    String payments_mode = obj.getString("payments_mode");
                                    String payments_image = obj.getString("payments_image");
                                    String payments_status = obj.getString("payments_status");
                                    String trans_id = obj.getString("trans_id");


                                    Payment paymentHistory = new Payment();
                                    paymentHistory.setUserid(dol_payment_user_id);
                                    paymentHistory.setAmount(dol_payment_amt);
                                    paymentHistory.setPaymentdate(dol_payment_date);
                                    paymentHistory.setTrandsactionmode(payments_mode);
                                    paymentHistory.setPaymentimage(payments_image);
                                    paymentHistory.setStatus(payments_status);
                                    paymentHistory.setTrandsactionid(trans_id);


                                    items.add(paymentHistory);
                                }
                                else{
                                    payment_recycler.setVisibility(View.GONE);
                                    emptyLayout.setVisibility(View.VISIBLE);
                                  /*  fab.setVisibility(View.GONE);
                                    shop.setVisibility(View.GONE);*/
                                }
                            }
                            if (items.size() != 0) {
                                //set data and list adapter
                                paymentAdapter = new PaymentAdapter(getApplicationContext(), items);
                                payment_recycler.setAdapter(paymentAdapter);

                                // on item list clicked
                               /* paymentAdapter.setOnItemClickListener((view1, obj, position) -> {
                                    Toast.makeText(getApplicationContext(),"Item " + obj.getTrandsactionid() + " clicked",Toast.LENGTH_LONG).show();
                                });*/
                            } else {
                                payment_recycler.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);
                                /*fab.setVisibility(View.GONE);
                                shop.setVisibility(View.GONE);*/
                                //Toast.makeText(getApplicationContext(), "There is no record", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            payment_recycler.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                           /* shop.setVisibility(View.GONE);
                            fab.setVisibility(View.GONE);*/
                        }


                    } catch (Exception e) {
                        Helper.mProgressBarHandler.hide();
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                Log.e("para ",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}