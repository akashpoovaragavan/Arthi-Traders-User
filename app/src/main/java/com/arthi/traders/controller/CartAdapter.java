package com.arthi.traders.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.DBHelper;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Product;
import com.arthi.traders.view.CartFullViewScreen;
import com.arthi.traders.view.Fragment_Cart;
import com.arthi.traders.view.ProductDetailScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private Context context;
    private List<Cart> cart;
    public static int grand_total=0;

    public CartAdapter(Context context, List<Cart> cart) {
        this.context = context;
        this.cart = cart;
        grand_total = 0;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.product_name.setText(cart.get(position).getCart_product_name());
        holder.product_qnt.setText(cart.get(position).getCart_purchased_qnt());
        holder.quantity.setText(cart.get(position).getCart_product_quantity());
        holder.price.setText(cart.get(position).getCart_product_total());
        Picasso.get().load(Helper.imageurl_url+cart.get(position).getCart_product_image()).into(holder.product_img);

        Log.e("getCart_product_name",cart.get(position).getCart_product_name());
        Log.e("getCart_purchased_qnt",cart.get(position).getCart_purchased_qnt());
        Log.e("getCart_product_quantity",cart.get(position).getCart_product_quantity());
        Log.e("getCart_product_total",cart.get(position).getCart_product_total());



       // for (int i=0;i< cart.size();i++){
            grand_total=grand_total+ Integer.parseInt(cart.get(position).getCart_product_total());
     //   }
        Log.e("total","value--:"+grand_total);
        Fragment_Cart.cart_grand_total.setText("₹"+grand_total);


        holder.cart_lin.setOnClickListener(v -> {
            Log.e("purc qty",cart.get(position).getCart_product_quantity()+"..");
            Log.e("purc getCart_product_name",cart.get(position).getCart_product_name());
            Log.e("purcgetCart_purchased_qnt",cart.get(position).getCart_purchased_qnt());
            Log.e("purcgetCart_product_quantity",cart.get(position).getCart_product_quantity());
            Log.e("gpurcetCart_product_total",cart.get(position).getCart_product_total());
            Helper.sharedpreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
            ed.putString("cart_product_name",cart.get(position).getCart_product_name());
            ed.putString("cart_product_code",cart.get(position).getCart_product_id());
            ed.putString("cart_product_image",cart.get(position).getCart_product_image());
            ed.putString("cart_product_price",cart.get(position).getCart_product_total());
            ed.putString("cart_product_quantity",cart.get(position).getCart_product_quantity());
            ed.putString("cart_purchased_quantity",cart.get(position).getCart_purchased_qnt());
            ed.putString("cart_product_description",cart.get(position).getCart_product_description());
            ed.apply();
            context.startActivity(new Intent(context, CartFullViewScreen.class));
        });
        holder.remove_lin.setOnClickListener(v -> {
            DBHelper db = new DBHelper(context);
            db.deleteitem(cart.get(position).getCart_product_id());
            Fragment_Cart.setAdapter();
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name,product_qnt,quantity,price;
        private ImageView product_img;
        private LinearLayout cart_lin,remove_lin;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img=itemView.findViewById(R.id.cart_product_img);
            product_name=itemView.findViewById(R.id.cart_product_name);
            product_qnt=itemView.findViewById(R.id.cart_product_quantity);
            quantity=itemView.findViewById(R.id.purchase_qnt);
            price=itemView.findViewById(R.id.price);
            cart_lin=itemView.findViewById(R.id.cart_lin);
            remove_lin=itemView.findViewById(R.id.remove_lin);

        }
    }
}
