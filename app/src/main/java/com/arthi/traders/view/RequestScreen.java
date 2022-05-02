package com.arthi.traders.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.VolleySingleton;
import com.arthi.traders.controller.RequestAdapter;

import com.arthi.traders.model.Request_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ImageView back,from_date,to_date;
    TextView title;
    EditText txt_from,txt_to;
    RecyclerView request_recycler;
    RequestAdapter requestAdapter;
    List<Request_model> rq;
    boolean play=false;
    String dol_user_id;
    String start_date,end_date,type="";
    Button request_btn;
    RadioButton bill,statement;

    SwipeRefreshLayout swipelayout;
    View emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_screen);
        title=findViewById(R.id.title);
        back=findViewById(R.id.back);
        title.setText("Request");
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


        from_date=findViewById(R.id.from);
        to_date=findViewById(R.id.to);
        txt_from=findViewById(R.id.select_from_date);
        txt_to=findViewById(R.id.select_to_date);
        request_btn=findViewById(R.id.request_btn);
        bill=findViewById(R.id.bill);
        statement=findViewById(R.id.statement);


        Helper.sharedpreferences = getApplication().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id=Helper.sharedpreferences.getString("dol_user_id","");

        request_recycler = findViewById(R.id.request_recycler);
        request_recycler.setHasFixedSize(true);
        request_recycler.setNestedScrollingEnabled(false);
        request_recycler.setLayoutManager(new LinearLayoutManager(RequestScreen.this, LinearLayoutManager.VERTICAL, false));

        getRequest();

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.isEmpty()){
                    Toast.makeText(RequestScreen.this, "Please select type of request", Toast.LENGTH_SHORT).show();
                }else{
                    if(txt_from.getText().toString().isEmpty()||txt_to.getText().toString().isEmpty()){
                        Toast.makeText(RequestScreen.this, "Please select start and end date", Toast.LENGTH_SHORT).show();
                    }else {
                        RequestEntry();
                    }
                }
            }
        });

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Bill";
            }
        });

        statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Statement";
            }
        });


        /// Date
        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(RequestScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                         start_date=day+"/"+month+"/"+year;
                        txt_from.setText(start_date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(RequestScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        end_date=day+"/"+month+"/"+year;
                        txt_to.setText(end_date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


    }

    ////Request entry

    public void RequestEntry() {
        Helper.loading(RequestScreen.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.requestentry_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("RequestEntry", response);
                    try {
                        JSONArray jarr = new JSONArray(response);
                        if(jarr.length()!=0) {
                            for (int b = 0; b < jarr.length(); b++) {
                                JSONObject jobs = jarr.getJSONObject(b);
                                if(jobs.getString("Message").equals("Success"))
                                {
                                    Toast.makeText(RequestScreen.this, "Success", Toast.LENGTH_SHORT).show();
                                    txt_from.setText(start_date);
                                    txt_to.setText(end_date);
                                    //edtext.setText("");
                                   // type = "";
                                    getRequest();
                                }
                            }
                        }

                        if(jarr.length()!=0) {

                        }
                        else
                        {
                            Toast.makeText(RequestScreen.this, "There is no record", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(RequestScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("from", txt_from.getText().toString().trim());
                params.put("to", txt_to.getText().toString().trim());
                params.put("desc", type.trim());
                return params;
            }
        };
        VolleySingleton.getInstance(RequestScreen.this).addToRequestQueue(stringRequest);
    }
    /// end

    /// get request
    @Override
    public void onRefresh() {
        getRequest();
    }

    public void getRequest() {
        Helper.loading(RequestScreen.this);
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.requestview_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("getRequest", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            List<Request_model> items = new ArrayList<>();
                            for (int b=0;b<jarr.length();b++){
                                Request_model request = new Request_model();
                                JSONObject jobs = jarr.getJSONObject(b);
                                String msg = jobs.getString("message");
                                if (!msg.toString().trim().equals("failed")) {
                                    String requests_docs = jobs.getString("requests_docs");
                                    String requests_status = jobs.getString("requests_status");
                                    String requests_from = jobs.getString("requests_from");
                                    String requests_to = jobs.getString("requests_to");
                                    String requests_id = jobs.getString("requests_id");
                                    String requests_description = jobs.getString("requests_description");
                                    request.setRequests_id(requests_id);
                                    request.setRequests_from(requests_from);
                                    request.setRequests_to(requests_to);
                                    request.setRequests_description(requests_description);
                                    request.setRequests_status(requests_status);
                                    request.setRequests_docs(requests_docs);
                                    items.add(request);
                                }
                                else{
                                    emptyLayout.setVisibility(View.VISIBLE);
                                    request_recycler.setVisibility(View.GONE);
                                }
                            }
                            //set data and list adapter
                            requestAdapter = new RequestAdapter(RequestScreen.this, items);
                            request_recycler.setAdapter(requestAdapter);
                            requestAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            //Toast.makeText(getActivity(), "There is no record", Toast.LENGTH_SHORT).show();
                            emptyLayout.setVisibility(View.VISIBLE);
                            request_recycler.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Helper.mProgressBarHandler.hide();
                    }

                },
                error -> {
                    try {
                        Toast.makeText(RequestScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Helper.mProgressBarHandler.hide();
                        Log.e("err", error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Helper.mProgressBarHandler.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dol_order_user_id", dol_user_id);
                return params;
            }
        };
        VolleySingleton.getInstance(RequestScreen.this).addToRequestQueue(stringRequest);
    }

}