package com.arthi.traders.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arthi.traders.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.ViewHolder> {
    int[] images;
    public BannerAdapter(int[] images){
        this.images=images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.banner,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.banner_image);
        }
    }
}
