package com.arthi.traders.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.arthi.traders.R;
import com.arthi.traders.constant.DBHelper;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.controller.CartAdapter;
import com.arthi.traders.controller.Category_Adapter;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Category;
import com.arthi.traders.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("JoinDeclarationAndAssignmentJava")
public class Fragment_Cart extends Fragment {
    ImageView back;
    public static TextView title,cart_grand_total;
    Button checkout;
    public static RecyclerView cart_recycler;
    static CartAdapter cartAdapter;
    static List<Cart> cart;
    String dol_user_id,str_address,product_total;
    public static LinearLayout cart_layout;
    public static View emptylayout;
    static Activity activity;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        activity = getActivity();
        title=view.findViewById(R.id.title);
        back=view.findViewById(R.id.back);
        title.setText("Cart");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(),HomeScreen.class));
            }
        });
        cart_layout=view.findViewById(R.id.cart_lin);
        emptylayout=view.findViewById(R.id.emptyLayout);
        cart_grand_total=view.findViewById(R.id.cart_total);
        checkout=view.findViewById(R.id.checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOut();
            }
        });

        Helper.sharedpreferences = getActivity().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id=Helper.sharedpreferences.getString("dol_user_id","");
        str_address= Helper.sharedpreferences.getString("dol_user_address","");

        add_Cart_products(view);

        /*product_total= "₹"+ CartAdapter.grand_total;
        cart_grand_total.setText(product_total);
        Log.e("Cart act","Total:--"+product_total);*/

        return view;
    }
    // cart product
    public void add_Cart_products(View view) {

        cart_recycler=view.findViewById(R.id.cart_recycler);
        cart_recycler.setHasFixedSize(true);
        cart_recycler.setNestedScrollingEnabled(false);
        cart_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        setAdapter();


    }
    //end

    @SuppressLint("NotifyDataSetChanged")
    public static void setAdapter() {
        DBHelper db = new DBHelper(activity);
        ArrayList<Product> array_list = db.getAllOrders();
        if (array_list.size() == 0) {
            cart_layout.setVisibility(View.GONE);
            emptylayout.setVisibility(View.VISIBLE);
            cart_recycler.setVisibility(View.GONE);
        } else {
            cart = new ArrayList<>();
            for (int i = 0; i < array_list.size(); i++) {
                Product product = array_list.get(i);
                String[] ss = product.getDol_product_quantity().trim().split("@@@");
                String str_quantity = ss[0].trim();
                Log.e("str_quantity", str_quantity + "..");

                String[] price = product.getDol_product_price().split("@@@");
                String productQuantity = price[1].trim();
                String productprice = price[0];
                Log.e("ssddf ", product.getDol_product_price() + "..");
                Log.e("prod ", productQuantity + "..");
                Log.e("productprice ", productprice + "..");

                cart.add(new Cart(product.getDol_id(), product.getDol_product_name(), str_quantity, productQuantity, productprice, product.getDol_product_image(), product.getDol_product_description()));
            }
            Log.e("cart ", cart.size() + "..");
            cartAdapter = new CartAdapter(activity, cart);
            cart_recycler.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("NewApi")
    private void checkOut()
    {
        try {
            DBHelper db1 = new DBHelper(getActivity());
            ArrayList<Product> array_list = db1.getAllOrders();
            Log.e("array_list",array_list.size()+"");
            JSONArray orderarr = new JSONArray();
            JSONArray jarr = new JSONArray();
            JSONObject job = new JSONObject();
            for(int v=0;v<array_list.size();v++)
            {
                try {
                    Product ord = array_list.get(v);
                    JSONObject obj = new JSONObject();
                    String dol_product_code = ord.getDol_product_code();
                    String dol_product_quantity = ord.getDol_product_quantity();
                    String dol_product_price = ord.getDol_product_price();

                    List<String> pricelist = null;
                    String[] ss = ord.getDol_product_quantity().trim().split("@@@");
                    List<String> lst = Arrays.asList(ss);
                    //List<String> lst = List.of(ord.getDol_product_quantity().trim().split("@@@"));

                    String quantity = lst.get(0).trim();
                    String productQuantity = lst.get(1).trim();

                    String[] ss1 = ord.getDol_product_price().trim().split("@@@");
                    pricelist = Arrays.asList(ss1);
                    //pricelist = List.of(ord.getDol_product_price().trim().split("@@@"));
                    String Producttotal = pricelist.get(0).trim();
                    String qtype = pricelist.get(1).trim();
                    String prod_price = pricelist.get(2).trim();
                    Log.e("Producttotal ",Producttotal.toString());
                    Log.e("qtype ",qtype.toString());
                    Log.e("prod_price ",prod_price.toString());

                    obj.put("dol_order_product_code",dol_product_code);
                    obj.put("dol_order_product_qty",quantity);
                    obj.put("dol_order_product_price",prod_price);
                    obj.put("dol_order_total",Producttotal);
                    obj.put("qtype",qtype);
                    obj.put("emp","");

                    jarr.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            job.put("dol_order_user_id",dol_user_id);
            job.put("dol_order_sum",cart_grand_total.getText().toString().trim().replace("₹",""));
            job.put("dol_order_shipping",str_address);
            job.put("product", jarr);
            Log.e("job ",job.toString());
            orderarr.put(job);
            Log.e("orderarr ",orderarr.toString());
            try {
                Helper.loading(getActivity());
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Helper.orderentry_url,
                        orderarr,
                        response -> {
                            try{
                                Helper.mProgressBarHandler.hide();
                                Log.e("res ",response.toString());
                                JSONArray obj =  new JSONArray(response.toString());
                                JSONObject jobs = obj.getJSONObject(0);
                                Log.e("jobs ",jobs.toString());
                                if(jobs.getString("Message").equals("Success")){
                                    launchSuccessLottieDialog();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                Helper.mProgressBarHandler.hide();
                            }
                        },
                        error -> {
                            try {
                                Helper.mProgressBarHandler.hide();
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("err ",error.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );

                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void launchSuccessLottieDialog() {
        Button button = new Button(getActivity());
        button.setText("Ok");
        button.setTextColor(Color.WHITE);
        int greenColor = ContextCompat.getColor(getActivity(), R.color.blue_green);
        button.setBackgroundTintList(ColorStateList.valueOf(greenColor));
        button.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), HomeScreen.class));
            DBHelper db = new DBHelper(getActivity());
            db.deleteOrder();
        });

        LottieDialog dialog = new LottieDialog(getActivity())
                .setAnimation(R.raw.success_like)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setDialogBackground(Color.WHITE)
                .setMessage(getResources().getString(R.string.successorder))
                .setMessageColor(greenColor)
                .addActionButton(button);

        dialog.show();
    }
}
