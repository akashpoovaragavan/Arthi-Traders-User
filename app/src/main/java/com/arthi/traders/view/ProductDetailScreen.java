package com.arthi.traders.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arthi.traders.R;
import com.arthi.traders.constant.DBHelper;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.model.Product;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class ProductDetailScreen extends AppCompatActivity {
    ImageView back,product_img;
    TextView title ,product_name,product_code,product_price,product_desc;
    TextInputEditText quantity;
    Button add_cart;
    //GridView gridView;
    String product_str,price_str,quantity_str;
    Product product;

    RecyclerView qnt_recycler;
    QuantityAdapter qnt_adapter;

   /* public GridAdapter gridAdapter;*/
    String sel_price,Qtype;
    int amoutval=0;

    private String[] items=null;
    private String[] Amounts=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        product_img=findViewById(R.id.product_detail_img);
        product_name=findViewById(R.id.detail_name);
        product_code=findViewById(R.id.detail_code);
        product_price=findViewById(R.id.detail_price);
        product_desc=findViewById(R.id.description);
        add_cart=findViewById(R.id.add_cart);
        quantity=findViewById(R.id.quantity);
        title.setText("Product Detail");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter,R.anim.right_out);
                finish();
            }
        });

        Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        product_str=Helper.sharedpreferences.getString("product","");

        String jsonCart = "";


        Gson gson = new Gson();
        jsonCart = product_str;
        Type type = new TypeToken<Product>() {
        }.getType();
        product= gson.fromJson(jsonCart, type);
        product_name.setText(product.getDol_product_name());
        product_code.setText(product.getDol_product_code());
        product_desc.setText(product.getDol_product_description());
        Picasso.get().load(Helper.imageurl_url+product.getDol_product_image()).into(product_img);

        price_str=product.getDol_product_price();
        quantity_str=product.getDol_product_quantity();


        if(quantity_str.contains(","))
            items = quantity_str.split(",");
        else
            items = new String[] {quantity_str};

        if(price_str.contains(","))
            Amounts = price_str.split(",");
        else
            Amounts = new String[] {price_str};

        product_price.setText("₹"+Amounts[0]);
        amoutval = Integer.parseInt(Amounts[0]);

        Qtype = items[0];
        sel_price = Amounts[0];

       /* gridView=findViewById(R.id.grid);
        gridView.setNestedScrollingEnabled(true);
        gridAdapter=new GridAdapter(this,items);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();*/

        qnt_recycler=findViewById(R.id.qnt_recycler);
        qnt_recycler.setHasFixedSize(true);
        qnt_recycler.setNestedScrollingEnabled(false);
        qnt_recycler.setLayoutManager(new GridLayoutManager(ProductDetailScreen.this,3, GridLayoutManager.VERTICAL, false));
        qnt_adapter= new QuantityAdapter(this,items);
        qnt_recycler.setAdapter(qnt_adapter);



        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!quantity.getText().toString().trim().isEmpty()) {
                        String Quantitystr = quantity.getText().toString().replace("L", "").replace("Box", "");
                        DBHelper db = new DBHelper(ProductDetailScreen.this);
                        int total_item = Integer.parseInt(Quantitystr);
                        int total = amoutval * total_item;
                        product_price.setText("₹" + total + "");

                        Log.e("qty ", Qtype + "..");
                        Log.e("sel_price ", sel_price + "..");

                        boolean bb = db.insertOrders(product.getDol_id(), product.getDol_category(), product.getDol_sub_category(), product.getDol_product_name(), product.getDol_product_description(), product.getDol_product_code(), product.getDol_product_pack(), product.getDol_product_weight(), product.getDol_product_image(), quantity.getText().toString() + "@@@" + product.getDol_product_quantity(), product_price.getText().toString().replace("₹", "") + "@@@" + Qtype + "@@@" + sel_price, product.getDol_created_at(), product.getDol_updated_at());
                        if (bb) {
                            Toast.makeText(ProductDetailScreen.this, "Added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProductDetailScreen.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ProductDetailScreen.this, "Provide quantity", Toast.LENGTH_LONG).show();

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }


/*
 class GridAdapter extends BaseAdapter{
    Context context;
    String[] quantity;

    LayoutInflater layoutInflater;
    int index = 0;

    public GridAdapter(Context context, String[] quantity) {
        this.context = context;
        this.quantity = quantity;
    }

    @Override
    public int getCount() {
        return quantity.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.grid,null);
        }
        TextView txt_quantity=convertView.findViewById(R.id.txt_quantity);
        LinearLayout grid_lin= convertView.findViewById(R.id.grid_lin);

        txt_quantity.setText(quantity[position]);
        txt_quantity.setOnClickListener(v -> {
            index = position;
            grid_lin.setBackgroundResource(R.drawable.button_select);
            gridAdapter.notifyDataSetChanged();
            product_price.setText("₹"+Amounts[index]);
            amoutval = Integer.parseInt(Amounts[position]);
            Qtype=quantity[position];
            sel_price=Amounts[index];
            Toast.makeText(v.getContext(), "You Selected "+quantity[position], Toast.LENGTH_SHORT).show();
        });
        if (index == position) {
            grid_lin.setBackgroundResource(R.drawable.button_select);
        }
        else {
            grid_lin.setBackgroundResource(R.drawable.button_false);
        }
        return convertView;
    }
}*/



public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.QuantityViewHolder> {
        Context context;
        String[] quantity;
        int index = 0;
        public QuantityAdapter(Context context, String[] quantity) {
            this.context = context;
            this.quantity = quantity;
        }

        @NonNull
        @Override
        public QuantityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid, parent, false);
            return new QuantityAdapter.QuantityViewHolder(view);
        }


    @Override
        public void onBindViewHolder(@NonNull QuantityAdapter.QuantityViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.txt_quantity.setText(quantity[position]);
            holder.txt_quantity.setOnClickListener(v -> {
                index = position;
                holder.grid_lin.setBackgroundResource(R.drawable.button_select);
                notifyDataSetChanged();
            product_price.setText("₹"+Amounts[index]);
            amoutval = Integer.parseInt(Amounts[position]);
            Qtype=quantity[position];
            sel_price=Amounts[index];
                Toast.makeText(v.getContext(), "You Selected "+quantity[position], Toast.LENGTH_SHORT).show();
            });
            if (index == position) {
                holder.grid_lin.setBackgroundResource(R.drawable.button_select);
            }
            else {
                holder.grid_lin.setBackgroundResource(R.drawable.button_false);
            }
        }

        @Override
        public int getItemCount() {
            return quantity.length;
        }

        public class QuantityViewHolder extends RecyclerView.ViewHolder {
            TextView txt_quantity;
            LinearLayout grid_lin;
            public QuantityViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_quantity=itemView.findViewById(R.id.txt_quantity);
                grid_lin=itemView.findViewById(R.id.grid_lin);
            }
        }
    }


}