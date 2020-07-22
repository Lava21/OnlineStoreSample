package com.mas.sampleonlinestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.sampleonlinestore.R;
import com.mas.sampleonlinestore.model.CategoryModel;

import java.util.List;

public class ItemGridAdapter extends RecyclerView.Adapter<ItemGridAdapter.ViewHolder> {

    List<CategoryModel> categoryModelList;

    public ItemGridAdapter(List<CategoryModel> data){
        this.categoryModelList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.title.setText(categoryModel.title);
        holder.ic_cat.setImageResource(categoryModel.icres);
        if (position % 2 == 1){
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView ic_cat;
        View divider;

        public ViewHolder(View view){
            super(view);

            title = view.findViewById(R.id.tvItemCard);
            ic_cat = view.findViewById(R.id.ivItemCard);
            divider = view.findViewById(R.id.vItemCard);
        }
    }
}
