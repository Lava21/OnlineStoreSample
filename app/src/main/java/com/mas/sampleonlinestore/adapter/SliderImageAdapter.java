package com.mas.sampleonlinestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.sampleonlinestore.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageAdapter.ViewHolder> {

    private Context context;
    private int mCount;

    public SliderImageAdapter(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_myshop, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load("https://laz-img-cdn.alicdn.com/images/ims-web/TB1pHADjBr0gK0jSZFnXXbRRXXa.jpg_1200x1200.jpg")
                        .fitCenter()
                        .into(viewHolder.ivBackgroud);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load("https://laz-img-cdn.alicdn.com/images/ims-web/TB1m8REjrj1gK0jSZFuXXcrHpXa.jpg_1200x1200Q100.jpg")
                        .fitCenter()
                        .into(viewHolder.ivBackgroud);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load("https://laz-img-cdn.alicdn.com/images/ims-web/TB1Hhz4jBr0gK0jSZFnXXbRRXXa.jpg_1200x1200Q100.jpg")
                        .fitCenter()
                        .into(viewHolder.ivBackgroud);
                break;
            case 3:
                Glide.with(viewHolder.itemView)
                        .load("https://laz-img-cdn.alicdn.com/images/ims-web/TB1m8REjrj1gK0jSZFuXXcrHpXa.jpg_1200x1200Q100.jpg")
                        .fitCenter()
                        .into(viewHolder.ivBackgroud);
                break;
        }
    }

    @Override
    public int getCount() {
        return mCount;
    }

    class ViewHolder extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView ivBackgroud, ivGridContainer;
        TextView tvDesc;

        public ViewHolder(View itemView){
            super(itemView);

            ivBackgroud = itemView.findViewById(R.id.ivAutoImageSlider);
            ivGridContainer = itemView.findViewById(R.id.ivGifContainer);
            tvDesc = itemView.findViewById(R.id.tvAutoImageSlider);
            this.itemView = itemView;
        }
    }
}
