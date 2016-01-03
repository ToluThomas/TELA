package com.aun.tela.alphabets.application.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;

import java.util.List;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:48 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class GenericRecyclerViewItemAdapter<T, ViewHolderImpl extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolderImpl> {

    List<T> items;
    DoubleRetriever<Long, T, Integer> idRetriever;
    QuatroConsumer<ViewHolderImpl, T, Integer, Boolean> viewConsumer;
    DoubleRetriever<ViewHolderImpl, ViewGroup, Integer> viewRetriever;

    public GenericRecyclerViewItemAdapter<T, ViewHolderImpl> setIdRetriever(DoubleRetriever<Long, T, Integer> idRetriever){
        this.idRetriever = idRetriever; return this;
    }

    public GenericRecyclerViewItemAdapter<T, ViewHolderImpl> setViewConsumer(QuatroConsumer<ViewHolderImpl, T, Integer, Boolean> viewConsumer){
        this.viewConsumer = viewConsumer; return this;
    }

    public GenericRecyclerViewItemAdapter<T, ViewHolderImpl> setViewRetriever(DoubleRetriever<ViewHolderImpl, ViewGroup, Integer> viewRetriever){
        this.viewRetriever = viewRetriever; return this;
    }

    public static <T, ViewHolderImpl extends RecyclerView.ViewHolder> GenericRecyclerViewItemAdapter<T, ViewHolderImpl> getInstance(){
        return new GenericRecyclerViewItemAdapter<>();
    }

    public GenericRecyclerViewItemAdapter<T, ViewHolderImpl> setItems(List<T> items){this.items = items; refresh(); return this;}

    public int getCount() {
        return Value.EMPTY(items) ? 0 : items.size();
    }


    public T getItem(int position) {
        return Value.EMPTY(getItems()) ? null : getItems().get(position);
    }

    @Override
    public ViewHolderImpl onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewRetriever.retrieve(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolderImpl holder, int position) {
        viewConsumer.consume(holder, getItem(position), position, Value.Same.INTEGER(position, getCount() - 1));
    }

    @Override
    public long getItemId(int position) {
        return idRetriever.retrieve(getItem(position), position);
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    List<T> getItems(){
        return items;
    }

}
