package com.arthi.traders.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Product;
import com.arthi.traders.view.ProductDetailScreen;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.product_code.setText(products.get(position).getDol_product_code());
        holder.product_name.setText(products.get(position).getDol_product_name());
        Picasso.get().load(Helper.imageurl_url+products.get(position).getDol_product_image()).into(holder.product_img);
        holder.product_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Product product = products.get(position);
                String str = gson.toJson(product);
                Helper.sharedpreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                ed.putString("product", str);
                ed.apply();
                context.startActivity(new Intent(context, ProductDetailScreen.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView product_code,product_name;
        private ImageView product_img;
        private LinearLayout product_lin;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_code=itemView.findViewById(R.id.product_code);
            product_name=itemView.findViewById(R.id.product_name);
            product_img=itemView.findViewById(R.id.product_img);
            product_lin=itemView.findViewById(R.id.product_lin);

        }
    }
}
