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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Category;
import com.arthi.traders.view.ProductScreen;
import com.arthi.traders.view.SubcategoryScreen;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.CategoryViewHolder> {
    private List<Category> ct,ct1;
    private Context context;

    public Category_Adapter(List<Category> ct, Context context,List<Category> ct1) {
        this.ct = ct;
        this.ct1 = ct1;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cat_txt.setText(ct.get(position).getCategoryname());
        Picasso.get().load(ct.get(position).getCategoryimage()).into(holder.cat_img);
        holder.category_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubcategoryScreen.class);
                intent.putExtra("Category", ct.get(position).getCategoryname());
                intent.putExtra("Category_ID", ct.get(position).getCategoryid());

                try {
                    Log.e("onclick  ","CCCCCC"+ct.get(position).getCategoryid());
                    Gson gson = new Gson();
                    List<Category> subcategorylist = new ArrayList<>();
                    if(ct1.size()!=0) {
                        for (int v = 0; v < ct1.size(); v++) {
                            Category cat_gorys = ct1.get(v);
                            if (ct.get(position).getCategoryid().equals(cat_gorys.getCategoryid())) {
                                Category cat_gory = new Category();
                                if(!cat_gorys.getSubcategoryid().equals("")) {
                                    cat_gory.setCategoryid(cat_gorys.getCategoryid());
                                    cat_gory.setCategoryname(cat_gorys.getCategoryname());
                                    cat_gory.setCategoryimage(cat_gorys.getCategoryimage());
                                    cat_gory.setSubcategoryid(cat_gorys.getSubcategoryid());
                                    cat_gory.setSubcategoryname(cat_gorys.getSubcategoryname());
                                    cat_gory.setSubcategoryimage(cat_gorys.getSubcategoryimage());
                                    subcategorylist.add(cat_gory);
                                }
                            }
                        }
                        Helper.sharedpreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                        ed.putString("category_ID", ct.get(position).getCategoryid());
                        ed.putString("subcategory_ID","");
                        ed.apply();
                        if(subcategorylist.size()!=0)
                        {
                            String str = gson.toJson(subcategorylist);
                            SharedPreferences.Editor ed1 = Helper.sharedpreferences.edit();
                            ed1.putString("subcategorylist", str);
                            ed1.apply();
                            context.startActivity(intent);
                           // DashboardActivity.goToFragment(new SubCategoryFragment(str,n.getCategoryid()), false);
                        }
                        else{
                            Log.e("cat id ",ct.get(position).getCategoryid()+".");

                            context.startActivity(new Intent(context, ProductScreen.class));
                            //DashboardActivity.goToFragment(new ShopProductFragment(n.getCategoryid(),""), false);
                        }

                    }
                    else
                    {
                        Toast.makeText(context,"There is no item " ,Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ct.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout category_lin;
        private AppCompatTextView cat_txt;
        private ImageView cat_img;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_lin = itemView.findViewById(R.id.category_lin);
            cat_txt = itemView.findViewById(R.id.cat_txt);
            cat_img = itemView.findViewById(R.id.cat_img);
        }
    }
}
