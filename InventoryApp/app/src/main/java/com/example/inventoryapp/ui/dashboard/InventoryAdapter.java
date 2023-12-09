package com.example.inventoryapp.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.databinding.InventoryLayoutBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    ArrayList<InventoryDataItem> list;
    OnEditClick onEditClick;
    public InventoryAdapter(ArrayList<InventoryDataItem> list, OnEditClick onEditClick) {
        this.list = list;
        this.onEditClick = onEditClick;
    }
    InventoryLayoutBinding binding;
    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = InventoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder holder, int position) {
        InventoryDataItem item = list.get(position);
        holder.binding.tvQuantity.setText("Quan: " + item.getQuantity());
        holder.binding.tvCategory.setText(item.getCategory());
        holder.binding.tvTitle.setText(item.getTitle());
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClick.onClick(item);
            }
        });}
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        InventoryLayoutBinding binding;
        public ViewHolder(InventoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }}
    public interface OnEditClick{
        void onClick(InventoryDataItem item);
    }
}
