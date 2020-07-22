package com.mas.sampleonlinestore.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.image.SmartImageView;
import com.mas.sampleonlinestore.R;
import com.mas.sampleonlinestore.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductModel> productModelList = new ArrayList<ProductModel>();

    private float mWidth, mHeight;


    public ProductAdapter(List<ProductModel> data, Context context) {
        this.productModelList = data;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels / context.getResources().getDimension(R.dimen.grid_width);
        mHeight = displayMetrics.heightPixels / context.getResources().getDimension(R.dimen.grid_height);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.tvName.setText(productModel.name);
        holder.smartImageView.setImageUrl(productModel.img);
        holder.tvStore.setText(productModel.store);
        holder.tvPrice.setText(_priceFormat("" + productModel.price));
        holder.tvPriceOld.setText(_priceFormat("" + productModel.price_old));
        holder.tvPriceOld.setPaintFlags(holder.tvPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ratingBar.setRating(productModel.rating);
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvPriceOld, tvDiscount, tvStore;
        SmartImageView smartImageView;
        RatingBar ratingBar;

        public ViewHolder(final View view){
            super(view);
            view.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = (int) mHeight;
                    layoutParams.width = (int) mWidth;
                    view.setLayoutParams(layoutParams);
                }
            });
            tvName = view.findViewById(R.id.tvItemProductName);
            tvPrice = view.findViewById(R.id.tvItemProductPrice);
            tvPriceOld = view.findViewById(R.id.tvItemProductPold);
            tvDiscount = view.findViewById(R.id.tvItemProductDesc);
            tvStore = view.findViewById(R.id.tvItemProductStore);
            smartImageView = view.findViewById(R.id.sivItemProduct);
            ratingBar = view.findViewById(R.id.rbItemProduct);
        }
    }

    public static String _priceFormat(String s){
        double parsed = Double.parseDouble(s);
        String formated = NumberFormat.getCurrencyInstance().format(parsed);
        return formated;
    }
}
