package com.arthi.traders.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.VolleySingleton;
import com.arthi.traders.controller.BannerAdapter;
import com.arthi.traders.controller.Category_Adapter;
import com.arthi.traders.controller.OfferAdapter;
import com.arthi.traders.model.Category;
import com.arthi.traders.model.Offers;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SliderView sliderView;
    RecyclerView cat_recycler,offer_recycler;
    Category_Adapter ct_adapter;
    OfferAdapter of_adapter;

    LinearLayout view_all;
    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    public List<Category> noRepeat =null;
    ImageView contact1,contact2,wa1,wa2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view_all=view.findViewById(R.id.view_all);
        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ViewAllScreen.class));
            }
        });

        swipelayout=view.findViewById(R.id.swipelayout);
        emptyLayout=view.findViewById(R.id.emptyLayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        top_Banner(view);


        /// category
        cat_recycler = view.findViewById(R.id.cat_recycler);
        cat_recycler.setHasFixedSize(true);
        cat_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getCategory();

        //end

        /// offers

        offer_recycler = view.findViewById(R.id.offer_recycler);
        offer_recycler.setHasFixedSize(true);
        offer_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getOffers();

        // end

        // bulk enquiry
        String num1="91 9677733769";
        String num2="91 9003933769";
        //String num3="91 7092943798";
        String msg="Arthi Traders Bulk Quantity Enquiry";
        contact1=view.findViewById(R.id.contact1);
        contact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="tel:"+"9677733769";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                requireContext().startActivity(intent);
            }
        });

        contact2=view.findViewById(R.id.contact2);
        contact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="tel:"+"9003933769";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });

        wa1=view.findViewById(R.id.wa1);
        wa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openWhatsApp(num1,msg);
            }
        });

        wa2=view.findViewById(R.id.wa2);
        wa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(num2,msg);
            }
        });

        return view;
    }

    //top banner
    public void top_Banner(View view) {
        int[] images = {R.drawable.banner1, R.drawable.banner2,
                R.drawable.banner3, R.drawable.banner4, R.drawable.banner1};
        sliderView = view.findViewById(R.id.bannerslide);
        BannerAdapter bannerAdapter = new BannerAdapter(images);
        sliderView.setSliderAdapter(bannerAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
    //end


    //recommended product recycler

    @Override
    public void onRefresh() {
        getCategory();
    }
    //end


    /*Category Service*/
    public void getCategory() {
        Helper.loading(getActivity());
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
                            ct_adapter = new Category_Adapter(noRepeat,getActivity(),items);
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    // end

    /// Offers service --------

    private void getOffers()
    {
        //Helper.loading(getActivity());
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.offers_url,
                response -> {
                    // Helper.mProgressBarHandler.hide();
                    Log.e("getOffer", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            List<Offers> items = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String msg = obj.getString("message");
                                if (!msg.trim().equals("failed")) {
                                    String message = obj.getString("message");
                                    String created = obj.getString("created");
                                    JSONArray bannerarr = obj.getJSONArray("banner");

                                    for(int vv=0;vv<bannerarr.length();vv++){
                                        Offers offers = new Offers();
                                        offers.setMessage(message);
                                        offers.setBanner(bannerarr.getString(vv));
                                        offers.setCreated(created);
                                        items.add(offers);
                                    }
                                    of_adapter = new OfferAdapter(items,getContext());
                                    offer_recycler.setAdapter(of_adapter);
                                }
                                else{
                                    offer_recycler.setVisibility(View.GONE);
                                }
                            }
                        }
                        else{
                            offer_recycler.setVisibility(View.GONE);
                            //  Helper.mProgressBarHandler.hide();
                        }
                    } catch (Exception e) {
                        //Helper.mProgressBarHandler.hide();
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  Helper.mProgressBarHandler.hide();
                        // Log.e("err", error.getMessage());
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    /// end ------

    /// whatsapp

    private void openWhatsApp(String num1,String msg){

        try{
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ num1 +"&text=" + URLEncoder.encode(msg, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
                Toast.makeText(getActivity(),"There is no whatsapp", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(getActivity(),"There is no whatsapp", Toast.LENGTH_SHORT).show();
        }

    }

}
