package com.arthi.traders.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.arthi.traders.controller.CartAdapter;
import com.arthi.traders.controller.NotificationAdapter;
import com.arthi.traders.model.Cart;
import com.arthi.traders.model.Notification;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Notification extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ImageView back;
    TextView title;
    RecyclerView notification_recycler;
    NotificationAdapter notificationAdapter;
    List<Notification> nt;
    SwipeRefreshLayout swipelayout;
    View emptyLayout;
    String dol_user_id;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        title=view.findViewById(R.id.title);
        back=view.findViewById(R.id.back);
        title.setText("Notification");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeScreen.class));
            }
        });
        swipelayout=view.findViewById(R.id.swipelayout);
        emptyLayout=view.findViewById(R.id.emptyLayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        Helper.sharedpreferences = getActivity().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        dol_user_id=Helper.sharedpreferences.getString("dol_user_id","");

        notification_recycler=view.findViewById(R.id.notification_recycler);
        notification_recycler.setHasFixedSize(true);
        notification_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        getNotification();


        return view;
    }
    @Override
    public void onRefresh() {
        getNotification();
    }

    public void getNotification() {
        Helper.loading(getActivity());
        swipelayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.notificationlist_url,
                response -> {
                    Helper.mProgressBarHandler.hide();
                    Log.e("getPaymentHistory", response);
                    try {
                        JSONObject job = new JSONObject(response);
                        JSONArray jarr = job.getJSONArray("data");
                        if(jarr.length()!=0) {
                            List<Notification> items = new ArrayList<>();
                            for (int v = 0; v < jarr.length(); v++) {
                                JSONObject obj = jarr.getJSONObject(v);
                                String msg = obj.getString("message");
                                if (!msg.trim().equals("failed")) {
                                    String title = obj.getString("title");
                                    String message = obj.getString("message");
                                    String amount = obj.getString("amt");
                                    String dol_payment_date = obj.getString("date");
                                    String dol_orderid = obj.getString("order_id");

                                    Notification notifications = new Notification();
                                    notifications.setMessage(message);
                                    notifications.setTitle(title);
                                    notifications.setAmount(amount);
                                    notifications.setDate(dol_payment_date);
                                    notifications.setOrderid(dol_orderid);

                                    items.add(notifications);
                                }
                                else {
                                    notification_recycler.setVisibility(View.GONE);
                                    emptyLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            if (items.size() != 0) {
                                //set data and list adapter
                                notificationAdapter = new NotificationAdapter(getActivity(), items);
                                notification_recycler.setAdapter(notificationAdapter);

                                // on item list clicked
                               // notificationAdapter.setOnItemClickListener((view1, obj, position) -> Snackbar.make(parent_view, "Item " + obj.getTitle() + " clicked", Snackbar.LENGTH_SHORT).show());
                            } else {
                                notification_recycler.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            notification_recycler.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
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
                params.put("dol_order_user_id", dol_user_id);
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
