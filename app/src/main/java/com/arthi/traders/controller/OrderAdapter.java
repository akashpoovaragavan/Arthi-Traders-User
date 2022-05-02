package com.arthi.traders.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Order;
import com.arthi.traders.view.OrderDetails;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orders;
    private String order_title;
    private JSONArray jarr;
    public OrderAdapter(Context context, List<Order> orders,String order_title,JSONArray jarr) {
        this.context = context;
        this.orders = orders;
        this.order_title=order_title;
        this.jarr=jarr;
        Log.e("orders",orders.size()+"");
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.e("Data:","Recieved"+orders.get(position).getOrderId());

        holder.order_id.setText(orders.get(position).getOrderId());
        holder.order_date.setText(orders.get(position).getProductordercreatedate());
        holder.order_status.setText(orders.get(position).getOrderstatus());
        holder.order_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.sharedpreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);

                try {
                    Gson gson = new Gson();
                    List<Order> orderitems = new ArrayList<>();
                    String orderid = orders.get(position).getOrderId();
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject objj = jarr.getJSONObject(i);
                        if(orderid.equals(objj.getString("dol_order_id"))){
                            String dol_order_id = objj.getString("dol_order_id");
                            String dol_order_user_id = objj.getString("dol_order_user_id");
                            String dol_order_product_code = objj.getString("dol_order_product_code");
                            String dol_product_name = objj.getString("dol_product_name");
                            String dol_order_product_qty = objj.getString("dol_order_product_qty");
                            String dol_order_product_price = objj.getString("dol_order_product_price");
                            String dol_order_total = objj.getString("dol_order_total");
                            String dol_order_sum = objj.getString("dol_order_sum");
                            String dol_order_shipping = objj.getString("dol_order_shipping");
                            String dol_order_status = objj.getString("dol_order_status");
                            String dol_order_created_at = objj.getString("dol_order_created_at");
                            String dol_order_updated_at = objj.getString("dol_order_updated_at");
                            String dol_product_image = objj.getString("dol_product_image");
                            String dol_product_confirmed = objj.getString("confirmed");
                            String dol_product_pending = objj.getString("pending");
                            if(order_title.trim().equals(dol_order_status)) {
                                Order orderHistory = new Order();
                                orderHistory.setOrderId(dol_order_id);
                                orderHistory.setUserid(dol_order_user_id);
                                orderHistory.setProductordercode(dol_order_product_code);
                                orderHistory.setProductordername(dol_product_name);
                                orderHistory.setProductorderquantity(dol_order_product_qty);
                                orderHistory.setProductorderprice(dol_order_product_price);
                                orderHistory.setProductordertotal(dol_order_total);
                                orderHistory.setProductordersum(dol_order_sum);
                                orderHistory.setProductordershipping(dol_order_shipping);
                                orderHistory.setOrderstatus(dol_order_status);
                                orderHistory.setProductordercreatedate(dol_order_created_at);
                                orderHistory.setProductorderupdatedate(dol_order_updated_at);
                                orderHistory.setProductorderimage(dol_product_image);
                                orderHistory.setProductconfirmed(dol_product_confirmed);
                                orderHistory.setProductpending(dol_product_pending);
                                orderitems.add(orderHistory);
                            }
                        }
                    }

                    String str = gson.toJson(orderitems);

                    SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                    ed.putString("order_id",orders.get(position).getOrderId());
                    ed.putString("order_products",str);
                    ed.apply();
                    context.startActivity(new Intent(context, OrderDetails.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView order_id,order_date,order_status;
        LinearLayout order_lin;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id=itemView.findViewById(R.id.order_id);
            order_date=itemView.findViewById(R.id.order_date);
            order_status=itemView.findViewById(R.id.order_status);
            order_lin=itemView.findViewById(R.id.order_lin);
        }
    }
}
