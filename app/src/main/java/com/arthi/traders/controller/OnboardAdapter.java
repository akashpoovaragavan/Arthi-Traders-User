package com.arthi.traders.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arthi.traders.R;


public class OnboardAdapter extends PagerAdapter {
    Context context;
    int[] images ={
            R.drawable.on1,
            R.drawable.on2,
            R.drawable.on3,
            R.drawable.on4
    };
    int[] heading ={
            R.string.heading1,
            R.string.heading2,
            R.string.heading3,
            R.string.heading4
    };
    int[] description={
            R.string.Desc1,
            R.string.Desc2,
            R.string.Desc3,
            R.string.Desc4
    };

    public OnboardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider,container,false);
        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.titleImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.texttitle);
        TextView slideDesciption = (TextView) view.findViewById(R.id.textdeccription);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(heading[position]);
        slideDesciption.setText(description[position]);

        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }
}
