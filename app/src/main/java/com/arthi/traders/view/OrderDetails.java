package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.VolleySingleton;
import com.arthi.traders.controller.OrderDetalisAdapter;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Order;
import com.arthi.traders.model.OrderStatus;
import com.arthi.traders.model.Product;
import com.baoyachi.stepview.VerticalStepView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {
    ImageView back;
    public static TextView title,order_date,order_number,subtotal,order_grand_total;
    VerticalStepView track_line;
    RecyclerView cart_recycler;
    OrderDetalisAdapter orderDetalisAdapter;
    List<Order> order_item;
    LinearLayout product_detail_lin;
    String order_id,item_str;

    View emptyLayout;
    RelativeLayout  const_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        try {
            Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
            order_id=Helper.sharedpreferences.getString("order_id","");
            item_str=Helper.sharedpreferences.getString("order_products","");

            title=findViewById(R.id.title);
            back=findViewById(R.id.back);
            title.setText("Order Details");
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                    finish();
                }
            });

            order_date=findViewById(R.id.order_date);
            order_number=findViewById(R.id.order_number);
            subtotal=findViewById(R.id.subtotal);
            order_grand_total=findViewById(R.id.total);
            emptyLayout=findViewById(R.id.emptyLayout);
            const_layout=findViewById(R.id.const_layout);
            product_detail_lin=findViewById(R.id.product_detail_lin);

            track_line=findViewById(R.id.track_line);
            getStatus(order_id);



            Log.e("Items","VAlue:"+item_str);
            String jsonCart = "";
            Gson gson = new Gson();
            jsonCart = item_str;
            Type type = new TypeToken<List<Order>>() {
            }.getType();

            order_item= gson.fromJson(jsonCart, type);

            cart_recycler=findViewById(R.id.cart_recycler);
            cart_recycler.setHasFixedSize(true);
            cart_recycler.setNestedScrollingEnabled(false);
            cart_recycler.setLayoutManager(new LinearLayoutManager(OrderDetails.this, LinearLayoutManager.VERTICAL, false));
            orderDetalisAdapter = new OrderDetalisAdapter(this, order_item);
            cart_recycler.setAdapter(orderDetalisAdapter);
            orderDetalisAdapter.notifyDataSetChanged();



        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }




    public void getStatus(String orderid) {
        try {

            Helper.loading(OrderDetails.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.orderstatus_url,
                    response -> {
                        Helper.mProgressBarHandler.hide();
                        Log.e("getStatus", response);
                        try {
                            JSONObject job = new JSONObject(response);
                            JSONArray jarr = job.getJSONArray("data");
                            if(jarr.length()!=0) {
                                List<OrderStatus> items = new ArrayList<>();
                                for (int v = 0; v < jarr.length(); v++) {
                                    JSONObject obj = jarr.getJSONObject(v);
                                    String msg = obj.getString("message");
                                    if (!msg.trim().equals("failed")) {
                                        const_layout.setVisibility(View.VISIBLE);
                                        emptyLayout.setVisibility(View.GONE);
                                        String dol_order_id = obj.getString("dol_order_id");
                                        String date_placed = obj.getString("date_placed");
                                        String date_processing = obj.getString("date_processing");
                                        String date_shipping = obj.getString("date_shipping");
                                        String date_delivered = obj.getString("date_delivered");
                                        String date_canceled = obj.getString("date_canceled");

                                        OrderStatus orderstatus = new OrderStatus();
                                        orderstatus.setOrderId(dol_order_id);
                                        orderstatus.setDate_placed(date_placed);
                                        orderstatus.setDate_processing(date_processing);
                                        orderstatus.setDate_shipping(date_shipping);
                                        orderstatus.setDate_delivered(date_delivered);
                                        orderstatus.setDate_canceled(date_canceled);
                                        items.add(orderstatus);

                                        order_number.setText(dol_order_id);
                                        order_date.setText(date_placed);

                                             if(!date_placed.isEmpty() && date_processing.isEmpty() && date_shipping.isEmpty() && date_delivered.isEmpty() && date_canceled.isEmpty())
                                            setTrack(0);
                                        else if(!date_placed.isEmpty() && !date_processing.isEmpty() && date_shipping.isEmpty()&& date_delivered.isEmpty() && date_canceled.isEmpty())
                                            setTrack(1);
                                        else if(!date_placed.isEmpty() && !date_processing.isEmpty() && !date_shipping.isEmpty() && date_delivered.isEmpty() && date_canceled.isEmpty())
                                            setTrack(2);
                                        else if(!date_placed.isEmpty() && !date_processing.isEmpty() && !date_shipping.isEmpty() && !date_delivered.isEmpty()  && date_canceled.isEmpty())
                                            setTrack(3);
                                        /*else if(!date_placed.isEmpty() && !date_processing.isEmpty() && date_shipping.isEmpty() && date_delivered.isEmpty() && !date_canceled.isEmpty())
                                        {
                                            Log.e("Cancelled","Date: "+date_canceled);
                                            setTrackcancel();
                                        }*/
                                        else{
                                                 Log.e("Cancelled","Date: "+date_canceled);
                                                 setTrackcancel();
                                            /*const_layout.setVisibility(View.GONE);
                                            emptyLayout.setVisibility(View.VISIBLE);*/
                                        }


                                        break;
                                    }
                                }

                            }
                            else
                            {
                                const_layout.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Helper.mProgressBarHandler.hide();
                        }

                    },
                    error -> {
                        try {
                            Toast.makeText(OrderDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Helper.mProgressBarHandler.hide();
                            Log.e("err", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", orderid);
                    return params;
                }
            };
            VolleySingleton.getInstance(OrderDetails.this).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ObsoleteSdkInt")
    private void setTrack(int place) {
        Log.e("status ","track"+place);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            track_line.setStepsViewIndicatorComplectingPosition(getList().size())
                    .reverseDraw(false)
                    .setStepViewTexts(getList())
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.blue_green))
                    .setStepViewComplectedTextColor(getColor(R.color.navy))
                    .setStepViewUnComplectedTextColor(getColor(R.color.sub_text_color))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.completed_dot))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.active_dot))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.non_active_dot));

            track_line.setStepsViewIndicatorComplectingPosition(place);
        }
    }
    private List<String> getList(){
        List<String> list=new ArrayList<>();
        list.add("Order Placed ");
        list.add("Order On Process ");
        list.add("Order Shipping ");
        list.add("Order Delivered ");
        return list;
    }

    /// Cancel order

    @SuppressLint("ObsoleteSdkInt")
    private void setTrackcancel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            track_line.setStepsViewIndicatorComplectingPosition(getListcancel().size())
                    .reverseDraw(false)
                    .setStepViewTexts(getListcancel())
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.blue_green))
                    .setStepViewComplectedTextColor(getColor(R.color.navy))
                    .setStepViewUnComplectedTextColor(getColor(R.color.sub_text_color))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.completed_dot))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.active_dot))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.non_active_dot));

            track_line.setStepsViewIndicatorComplectingPosition(3);
            product_detail_lin.setVisibility(View.GONE);
        }
    }
    private List<String> getListcancel(){
        List<String> list=new ArrayList<>();
        list.add("Order Placed ");
        list.add("Order On Process ");
        list.add("Order Shipping ");
        list.add("Order Cancelled ");
        return list;
    }




}