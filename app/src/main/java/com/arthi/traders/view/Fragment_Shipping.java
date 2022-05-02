package com.arthi.traders.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.controller.OrderAdapter;
import com.arthi.traders.model.Order;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Shipping extends Fragment {
    View view;
    RecyclerView order_recycler;
    OrderAdapter orderAdapter;
    List<Order> or;
    View emptyLaout;
    String status;
    JSONArray jarr;

    public Fragment_Shipping(String status, List<Order> or, JSONArray jarr) {
        this.status=status;
        this.or = or;
        this.jarr=jarr;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shipping, container, false);
        order_recycler=view.findViewById(R.id.orders_recycler);
        emptyLaout=view.findViewById(R.id.emptyLayout);


        order_recycler.setHasFixedSize(true);
        order_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Log.e("or ",or.size()+"");
        if(or.size()==0){
            order_recycler.setVisibility(View.GONE);
            emptyLaout.setVisibility(View.VISIBLE);
        }else{
            orderAdapter=new OrderAdapter(getContext(),or,"shipping",jarr);
            order_recycler.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();
        }
        return view;
    }
}
