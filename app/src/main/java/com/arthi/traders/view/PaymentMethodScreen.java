package com.arthi.traders.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.MultiPartRequest;
import com.arthi.traders.constant.StringParser;
import com.arthi.traders.constant.Template;
import com.arthi.traders.constant.VolleySingleton;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentMethodScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back, attachment, cod_check, cheque_check, online_check;
    TextView title, payment_mode, payment_balance, payment_history, balance_date;
    LinearLayout cod, cheque, online;
    SwipeRefreshLayout swipelayout;
    String dol_user_id, transactionid = "Nill", str_uri="";
    EditText pay_amount;
    Button pay;
    private ArrayList<File> mFile = new ArrayList<File>();
    private RequestQueue mRequest;
    private MultiPartRequest mMultiPartRequest;
    private List<Uri> selectedUriList = new ArrayList<>();
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_screen);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        title.setText("Payment");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_enter, R.anim.right_out);
                finish();
            }
        });
        cod = findViewById(R.id.cod);
        cheque = findViewById(R.id.cheque);
        online = findViewById(R.id.online);
        payment_mode = findViewById(R.id.payment_mode);
        payment_balance = findViewById(R.id.payment_balance);
        attachment = findViewById(R.id.attachment);
        payment_history = findViewById(R.id.payment_history);
        payment_balance = findViewById(R.id.payment_balance);
        balance_date = findViewById(R.id.balance_date);
        cod_check = findViewById(R.id.cod_check);
        cheque_check = findViewById(R.id.cheque_check);
        pay_amount = findViewById(R.id.pay_amount);
        pay = findViewById(R.id.pay);

        online_check = findViewById(R.id.online_check);
        swipelayout = findViewById(R.id.swipelayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));


        Helper.sharedpreferences = getApplication().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id = Helper.sharedpreferences.getString("dol_user_id", "");

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(PaymentMethodScreen.this)
                        .crop()
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode.setText("COD");
                cod_check.setVisibility(View.VISIBLE);
                cheque_check.setVisibility(View.INVISIBLE);
                online_check.setVisibility(View.INVISIBLE);
            }
        });
        cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode.setText("CHEQUE");
                cheque_check.setVisibility(View.VISIBLE);
                cod_check.setVisibility(View.INVISIBLE);
                online_check.setVisibility(View.INVISIBLE);
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode.setText("ONLINE");
                online_check.setVisibility(View.VISIBLE);
                cheque_check.setVisibility(View.INVISIBLE);
                cod_check.setVisibility(View.INVISIBLE);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payment_mode.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentMethodScreen.this, "Please select payment mode", Toast.LENGTH_SHORT).show();
                } else if (pay_amount.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentMethodScreen.this, "Please enter the amount", Toast.LENGTH_SHORT).show();
                } else if (str_uri.isEmpty()) {
                    Toast.makeText(PaymentMethodScreen.this, "Please attach the payment proof", Toast.LENGTH_SHORT).show();
                } else {
                    paymentRequest();
                }
            }
        });

        payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentMethodScreen.this, PaymentHistoryScreen.class));
                overridePendingTransition(R.anim.right_enter, R.anim.left_out);
            }
        });

        balanceService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Uri uri = data.getData();
            str_uri = uri.toString().trim();
            file = new File(uri.getPath());//create path from uri
            if(str_uri!=null)
                attachment.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/// balance

@Override
public void onRefresh() {
    balanceService();
}


public void balanceService() {
        //Checkout.preload(getApplicationContext());
        Helper.loading(PaymentMethodScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.balance_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("balanceService", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarrr = job.getJSONArray("data");
                        if (jarrr.length() != 0) {
                            JSONObject obj = jarrr.getJSONObject(0);
                            String total = obj.getString("total");
                            String paid = obj.getString("paid");
                            String balance = obj.getString("balance");
                            String balancedate = obj.getString("As Per Date");

                            payment_balance.setText("₹ " + balance);
                            balance_date.setText(balancedate);
                        } else {
                            Toast.makeText(PaymentMethodScreen.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(PaymentMethodScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Helper.mProgressBarHandler.hide();
                        Log.e("err", error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dol_order_user_id", dol_user_id);
                Log.e("usrid ", params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(PaymentMethodScreen.this).addToRequestQueue(stringRequest);
    }

    //// End

    /// payment
    private void paymentRequest() {
        try {
            mFile.clear();

            // for(int v = 0; v< selectedUriList.size(); v++){
            //Uri uri = "url";
            File ff = new File(str_uri);
            Log.e("pat ", ff.getPath() + "..");
            mFile.add(file);
            //mFile.add(ff);
            //  }

            Helper.loading(PaymentMethodScreen.this);

            VolleySingleton volleySingleton = new VolleySingleton(PaymentMethodScreen.this);
            mRequest = volleySingleton.getInstance(PaymentMethodScreen.this).getRequestQueue();

            mRequest.start();
            mMultiPartRequest = new MultiPartRequest(error -> {
                setResponse(null, error);
                Helper.mProgressBarHandler.hide();
            }, response -> {
                setResponse(response, null);
                Helper.mProgressBarHandler.hide();

            }, mFile, mFile.size(), pay_amount.getText().toString().trim(), transactionid, payment_mode.getText().toString(), dol_user_id);
            mMultiPartRequest.setTag("MultiRequest");
            mMultiPartRequest.setRetryPolicy(new DefaultRetryPolicy(Template.VolleyRetryPolicy.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequest.add(mMultiPartRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setResponse(Object response, VolleyError error) {
        try {
            if (response == null) {
                Log.e("Error", error + "");
            } else {
                Log.e("res--", response.toString() + "..");
                Log.e("resrrr--", StringParser.getCode(response.toString()) + "..");

                if (StringParser.getCode(response.toString()).equals(Template.Query.VALUE_CODE_SUCCESS)) {
                    Log.e("Success", "Success\n" + StringParser.getMessage(response.toString()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodScreen.this);
                    builder.setTitle("Payment");
                    builder.setMessage(getResources().getString(R.string.successpayment));
                    builder.setPositiveButton("ok", (dialog, which) -> {
                        dialog.cancel();
                        selectedUriList.clear();

                        startActivity(new Intent(PaymentMethodScreen.this, HomeScreen.class));
                    });
                    builder.show();
                } else {
                    selectedUriList.clear();
                    Log.e("Error", "Error\n" + StringParser.getMessage(response.toString()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodScreen.this);
                    builder.setTitle("Upload");
                    builder.setMessage(StringParser.getMessage(response.toString()));
                    builder.setPositiveButton("ok", (dialog, which) -> dialog.cancel());
                    builder.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}