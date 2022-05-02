package com.arthi.traders.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Order;
import com.arthi.traders.view.OrderDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetalisAdapter extends RecyclerView.Adapter<OrderDetalisAdapter.OrderDetailsViewHolder> {
    private Context context;
    private List<Order> order_items;

    public OrderDetalisAdapter(Context context, List<Order> cart) {
        this.context = context;
        this.order_items = cart;
        Log.e("cart ",cart.size()+"");
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {
        Log.e("Order Product","NAME :"+order_items.get(position).getProductordername());
        holder.product_name.setText(order_items.get(position).getProductordername());
        holder.confirmed.setText(order_items.get(position).getProductconfirmed());
        holder.total.setText(order_items.get(position).getProductordertotal());
        Picasso.get().load(order_items.get(position).getProductorderimage()).into(holder.product_img);

        OrderDetails.subtotal.setText(order_items.get(position).getProductordersum());
        OrderDetails.order_grand_total.setText(order_items.get(position).getProductordersum());
    }

    @Override
    public int getItemCount() {
        return order_items.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name,confirmed,total;
        private ImageView product_img;
        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img=itemView.findViewById(R.id.order_product_img);
            product_name=itemView.findViewById(R.id.order_product_name);
            confirmed=itemView.findViewById(R.id.order_confirmed);
            total=itemView.findViewById(R.id.order_price);
        }
    }
}
