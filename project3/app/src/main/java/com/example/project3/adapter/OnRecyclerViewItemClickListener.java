package com.example.project3.adapter;

import android.view.View;

import com.example.project3.entity.CategoryEntity;


public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, CategoryEntity data);
}
