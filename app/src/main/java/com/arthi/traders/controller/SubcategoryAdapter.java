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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Category;
import com.arthi.traders.view.ProductScreen;
import com.arthi.traders.view.SubcategoryScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder> {
    private Context context;
    private List<Category> sub_cat;

    public SubcategoryAdapter(Context context, List<Category> sub_cat) {
        this.context = context;
        this.sub_cat = sub_cat;
    }

    @NonNull
    @Override
    public SubcategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new SubcategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sub_cat_txt.setText(sub_cat.get(position).getSubcategoryname());
        Log.e("Category","Subcat"+sub_cat.get(position).getSubcategoryname());
        Picasso.get().load(sub_cat.get(position).getSubcategoryimage()).into(holder.sub_cat_img);
        holder.subcategory_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductScreen.class);
                intent.putExtra("Subcategory", sub_cat.get(position).getSubcategoryname());
                intent.putExtra("Subcategory_ID", sub_cat.get(position).getSubcategoryid());

                Helper.sharedpreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                ed.putString("subcategory_ID", sub_cat.get(position).getSubcategoryid());
                ed.apply();

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sub_cat.size();
    }

    public class SubcategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout subcategory_lin;
        private AppCompatTextView sub_cat_txt;
        private ImageView sub_cat_img;
        public SubcategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            subcategory_lin = itemView.findViewById(R.id.category_lin);
            sub_cat_txt = itemView.findViewById(R.id.cat_txt);
            sub_cat_img = itemView.findViewById(R.id.cat_img);
        }
    }
}
