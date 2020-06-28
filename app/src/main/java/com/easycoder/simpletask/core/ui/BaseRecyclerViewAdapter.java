package com.easycoder.simpletask.core.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    public int layout_id;
    protected Context parentContext;
    protected List<?> dataList = new ArrayList<>();

    @Override
    public void onClick(View view) {
        onClickViews(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        bindView(holder, i);
    }

    public void notifyList(List<?> filterdNames) {
        this.dataList = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (dataList.size() == 0)
            return 5;
        else
            return dataList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        return new MyViewHolder(itemView);
    }

    public abstract MyViewHolder bindView(MyViewHolder holder, int position);

    public abstract void onClickViews(View view);

    public abstract void declareViews(View view, MyViewHolder holder);

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View view) {
            super(view);
            declareViews(view, this);
        }
    }

}