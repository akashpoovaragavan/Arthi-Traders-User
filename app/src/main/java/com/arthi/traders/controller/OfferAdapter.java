package com.arthi.traders.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.model.Offers;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder>{
    private List<Offers> of;
    private Context context;

    public OfferAdapter(List<Offers> of, Context context) {
        this.of = of;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Picasso.get().load(of.get(position).getBanner()).into(holder.offer_img);

    }

    @Override
    public int getItemCount() {
        return of.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        private ImageView offer_img;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            offer_img=itemView.findViewById(R.id.offer_img);
        }
    }
}
