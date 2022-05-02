package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.controller.SubcategoryAdapter;
import com.arthi.traders.model.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubcategoryScreen extends AppCompatActivity {
    ImageView back;
    TextView title;
    RecyclerView subcategory_recycler;
    SubcategoryAdapter subcategoryAdapter;
    List<Category> subcategory;
    String subcategorylist;
    List<Category> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_screen);

        Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        subcategorylist              = Helper.sharedpreferences.getString("subcategorylist","");

        String jsonCart = "";


        Gson gson = new Gson();
        jsonCart = subcategorylist;
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        items = gson.fromJson(jsonCart, type);

        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        Intent intent=getIntent();
        String main_category=intent.getExtras().getString("Category");
        String cat_id=intent.getExtras().getString("ID");
        title.setText(main_category);
        back.setOnClickListener(v -> {
            overridePendingTransition(R.anim.left_enter,R.anim.right_out);
            finish();
        });

        //add_Subcategory(cat_id);

        getSub_Category();
    }


    //// Sub category

    private void add_Subcategory(String cat_id) {
        subcategory_recycler=findViewById(R.id.subcategory_recycler);
        subcategory_recycler.setHasFixedSize(true);
        subcategory_recycler.setNestedScrollingEnabled(false);
        subcategory_recycler.setLayoutManager(new GridLayoutManager(SubcategoryScreen.this,2,GridLayoutManager.VERTICAL, false));

        subcategory=new ArrayList<>();
        // check the main category type with sub categories
        /*switch (cat_id){
            case "Tractor1":
                subcategory.add(new Category(cat_id+"-Engine oil",R.drawable.subcat1,"Engine oil"));
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                subcategory.add(new Category(cat_id+"-Engine oil",R.drawable.subcat1,"Engine oil"));
                break;
            case "Truck2":
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                subcategory.add(new Category(cat_id+"-Engine oil",R.drawable.subcat1,"Engine oil"));
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                subcategory.add(new Category(cat_id+"-Engine oil",R.drawable.subcat1,"Engine oil"));
                break;
            case "Two Wheeler3":
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                subcategory.add(new Category(cat_id+"-Engine oil",R.drawable.subcat1,"Engine oil"));
                break;
            case "Three Wheeler4":
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                subcategory.add(new Category(cat_id+"-Gear oil",R.drawable.subcat2,"Gear oil"));
                break;
            default:
                break;
*/

        }
    //// end



    //}

    /// end

    public void getSub_Category() {
        try {
            if(items.size()!=0) {
                subcategory_recycler = findViewById(R.id.subcategory_recycler);
                subcategory_recycler.setHasFixedSize(true);
                subcategory_recycler.setNestedScrollingEnabled(false);
                subcategory_recycler.setLayoutManager(new GridLayoutManager(SubcategoryScreen.this, 2, GridLayoutManager.VERTICAL, false));

                subcategoryAdapter = new SubcategoryAdapter(SubcategoryScreen.this, items);
                subcategory_recycler.setAdapter(subcategoryAdapter);
                subcategoryAdapter.notifyDataSetChanged();
            }
            else{
                //nodata layout
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}