package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.arthi.traders.controller.ProductAdapter;
import com.arthi.traders.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductScreen extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back;
    TextView title;
    RecyclerView product_recycler;
    ProductAdapter productAdapter;
    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    String category_id,subcategory_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("Products");
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

        Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        category_id=Helper.sharedpreferences.getString("category_ID","");
        subcategory_id=Helper.sharedpreferences.getString("subcategory_ID","");


        product_recycler=findViewById(R.id.product_recycler);
        product_recycler.setHasFixedSize(true);
        product_recycler.setLayoutManager(new LinearLayoutManager(ProductScreen.this, LinearLayoutManager.VERTICAL, false));

        getProducts();

    }

    @Override
    public void onRefresh() {
        getProducts();
    }


    public void getProducts() {
        Helper.loading(ProductScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.product_url,
                response -> {

                    Helper.mProgressBarHandler.hide();
                    Log.e("getProducts", response+".");
                    try {
                        JSONArray jarr = new JSONArray(response);
                        Log.e("jarr ",jarr.length()+".");
                        if(jarr.length()!=0) {
                            List<Product> items = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String dol_id = obj.getString("dol_id");
                                String dol_category = obj.getString("dol_category");
                                String dol_sub_category = obj.getString("dol_sub_category");
                                String dol_product_name = obj.getString("dol_product_name");
                                String dol_product_description = obj.getString("dol_product_description");
                                String dol_product_code = obj.getString("dol_product_code");
                                String dol_product_pack = obj.getString("dol_product_pack");
                                String dol_product_weight = obj.getString("dol_product_weight");
                                String dol_product_image = obj.getString("dol_product_image");
                                String dol_product_quantity = obj.getString("dol_product_quantity");
                                String dol_product_price = obj.getString("dol_product_price");
                                String dol_created_at = obj.getString("dol_created_at");
                                String dol_updated_at = obj.getString("dol_updated_at");
                                Product shocategory = new Product();
                                shocategory.setDol_id(dol_id);
                                shocategory.setDol_category(dol_category);
                                shocategory.setDol_sub_category(dol_sub_category);
                                shocategory.setDol_product_name(dol_product_name);
                                shocategory.setDol_product_description(dol_product_description);
                                shocategory.setDol_product_code(dol_product_code);
                                shocategory.setDol_product_pack(dol_product_pack);
                                shocategory.setDol_product_weight(dol_product_weight);
                                shocategory.setDol_product_image(dol_product_image);
                                shocategory.setDol_product_quantity(dol_product_quantity);
                                shocategory.setDol_product_price(dol_product_price);
                                shocategory.setDol_created_at(dol_created_at);
                                shocategory.setDol_updated_at(dol_updated_at);
                                items.add(shocategory);
                            }
                            if(items.size()!=0) {
                                //set data and list adapter
                                productAdapter=new ProductAdapter(this,items);
                                product_recycler.setAdapter(productAdapter);
                               /* productAdapter.setOnItemClickListener((view, obj, position) -> {
                                    DashboardActivity.goToFragment(new ProductFullViewFragment(items.get(position)), false);
                                });*/
                            }
                            else{
                                Toast.makeText(ProductScreen.this, "There is no record.", Toast.LENGTH_SHORT).show();
                                product_recycler.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);
                                Helper.mProgressBarHandler.hide();
                            }
                        }
                        else{
                            Toast.makeText(ProductScreen.this, "There is no record.", Toast.LENGTH_SHORT).show();
                            product_recycler.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                            Helper.mProgressBarHandler.hide();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Helper.mProgressBarHandler.hide();
                    }

                },
                error -> {
                    Toast.makeText(ProductScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Helper.mProgressBarHandler.hide();
                    Log.e("err", error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", category_id);
                params.put("subcat_id", subcategory_id);
                Log.e("par ",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}