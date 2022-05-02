package com.arthi.traders.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.arthi.traders.controller.OrderAdapter;
import com.arthi.traders.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fragment_Pending extends Fragment {
    View view;
    RecyclerView order_recycler;
    OrderAdapter orderAdapter;
    List<Order> or;
    String status;
    JSONArray jarr;
    View emptyLaout;

    public Fragment_Pending(String status, List<Order> or, JSONArray jarr) {
        this.status=status;
        this.or = or;
        this.jarr=jarr;
        Log.e("pend ",or.size()+"");


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending, container, false);
        order_recycler=view.findViewById(R.id.orders_recycler);
        emptyLaout=view.findViewById(R.id.emptyLayout);


        order_recycler.setHasFixedSize(true);
        order_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Log.e("or ",or.size()+"");
        if(or.size()==0){
            order_recycler.setVisibility(View.GONE);
            emptyLaout.setVisibility(View.VISIBLE);
        }else{
            orderAdapter=new OrderAdapter(getContext(),or,"pending",jarr);
            order_recycler.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();
        }
        return view;
    }


}
