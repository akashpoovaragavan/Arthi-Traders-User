package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Product;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class CartFullViewScreen extends AppCompatActivity {
    ImageView back,cart_product_img;
    TextView title ,cart_product_name,cart_product_code,cart_product_price,cart_product_quantity,cart_product_desc;
    TextInputEditText cart_purchase_quantity;

    String cart_name_str,cart_code_str,cart_image_str,cart_price_str,cart_quantity_str,cart_purchased_qt,cart_description_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_full_view_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        cart_product_img=findViewById(R.id.cart_detail_img);
        cart_product_name=findViewById(R.id.cart_detail_name);
        cart_product_code=findViewById(R.id.cart_detail_code);
        cart_product_price=findViewById(R.id.cart_detail_price);
        cart_product_desc=findViewById(R.id.cart_description);
        cart_product_quantity=findViewById(R.id.cart_purchased_quantity);
        cart_purchase_quantity=findViewById(R.id.cart_quantity);
        title.setText("Product Detail");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                finish();
            }
        });
        Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);

        cart_name_str=Helper.sharedpreferences.getString("cart_product_name","");
        cart_code_str=Helper.sharedpreferences.getString("cart_product_code","");
        cart_image_str=Helper.sharedpreferences.getString("cart_product_image","");
        cart_price_str=Helper.sharedpreferences.getString("cart_product_price","");
        cart_quantity_str=Helper.sharedpreferences.getString("cart_product_quantity","");
        cart_purchased_qt=Helper.sharedpreferences.getString("cart_purchased_quantity","");
        cart_description_str=Helper.sharedpreferences.getString("cart_product_description","");
        Log.e("img",cart_image_str+"..");
        Log.e("cart_price_str",cart_price_str+"..");
        Log.e("cart_quantity_str",cart_quantity_str+"..");
        Log.e("cart_purchased_qt",cart_purchased_qt+"..");
        Log.e("cart_description_str",cart_description_str+"..");

        cart_product_name.setText(cart_name_str);
        cart_product_code.setText(cart_code_str);
        cart_product_price.setText(cart_price_str);
        cart_product_quantity.setText(cart_purchased_qt);
        cart_purchase_quantity.setText(cart_quantity_str);
        cart_product_desc.setText(cart_description_str);
        Picasso.get().load(Helper.imageurl_url+cart_image_str).into(cart_product_img);

    }
}