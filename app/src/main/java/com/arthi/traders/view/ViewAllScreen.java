package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.arthi.traders.controller.Category_Adapter;
import com.arthi.traders.model.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back;
    TextView title;
    RecyclerView cat_recycler;
    Category_Adapter ct_adapter;
    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    public List<Category> noRepeat =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("View All Category");
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
        /// category
        cat_recycler = findViewById(R.id.cat_recycler);
        cat_recycler.setHasFixedSize(true);
        cat_recycler.setLayoutManager(new GridLayoutManager(ViewAllScreen.this,2,GridLayoutManager.VERTICAL, false));

        getCategory();

        //end

    }

    @Override
    public void onRefresh() {

        getCategory();
    }

    /*Category Service*/
    public void getCategory() {
        Helper.loading(ViewAllScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.category_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("getCategory", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            List<Category> items = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String msg = obj.getString("message");
                                if (!msg.toString().trim().equals("failed")) {
                                    String category_id = obj.getString("cat_id");
                                    String category_name = obj.getString("cat");
                                    String category_image = obj.getString("cat_img");
                                    String subcategory_id = obj.getString("subcat_id");
                                    String subcategory_name = obj.getString("subcat");
                                    String subcategory_image = obj.getString("subcat_img");


                                    Category categorys = new Category();
                                    categorys.setCategoryid(category_id);
                                    categorys.setCategoryimage(category_image);
                                    categorys.setCategoryname(category_name);
                                    categorys.setSubcategoryid(subcategory_id);
                                    categorys.setSubcategoryname(subcategory_name);
                                    categorys.setSubcategoryimage(subcategory_image);

                                    items.add(categorys);
                                }
                                else{
                                    cat_recycler.setVisibility(View.GONE);
                                    emptyLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            if (items.size() != 0) {
                                //set data and list adapter
                                List<Category> allEvents = items;
                                noRepeat = new ArrayList<>();

                                for (Category event : allEvents) {
                                    boolean isFound = false;
                                    // check if the event name exists in noRepeat
                                    for (Category e : noRepeat) {
                                        if (e.getCategoryid().equals(event.getCategoryid()) || (e.equals(event))) {
                                            isFound = true;
                                            break;
                                        }
                                    }
                                    if (!isFound) noRepeat.add(event);
                                }
                            }
                            cat_recycler.setVisibility(View.VISIBLE);
                            emptyLayout.setVisibility(View.GONE);
                            ct_adapter = new Category_Adapter(noRepeat,ViewAllScreen.this,items);
                            cat_recycler.setAdapter(ct_adapter);
                            ct_adapter.notifyDataSetChanged();
                        }
                        else{
                            cat_recycler.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                            Helper.mProgressBarHandler.hide();
                        }


                    } catch (Exception e) {
                        Helper.mProgressBarHandler.hide();
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(ViewAllScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Helper.mProgressBarHandler.hide();
                        Log.e("err", error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstance(ViewAllScreen.this).addToRequestQueue(stringRequest);
    }

}