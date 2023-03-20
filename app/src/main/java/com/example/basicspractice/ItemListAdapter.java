package com.example.basicspractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<Integer> itemList;

    public ItemListAdapter(List<Integer> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int item = itemList.get(position);
        holder.itemImageView.setImageResource(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(int item) {
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.inventory_item);
        }
    }

}

