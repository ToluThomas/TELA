package com.aun.tela.alphabets.application.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;

import java.util.List;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:48 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class GenericItemAdapter<T, ViewHolderImpl extends RecyclerView.ViewHolder> extends BaseAdapter {

    List<T> items;
    DoubleRetriever<Long, T, Integer> idRetriever;
    QuatroConsumer<ViewHolderImpl, T, Integer, Boolean> viewConsumer;
    DoubleRetriever<ViewHolderImpl, ViewGroup, Integer> viewRetriever;

    public GenericItemAdapter<T, ViewHolderImpl> setIdRetriever(DoubleRetriever<Long, T, Integer> idRetriever){
        this.idRetriever = idRetriever; return this;
    }

    public GenericItemAdapter<T, ViewHolderImpl> setViewConsumer(QuatroConsumer<ViewHolderImpl, T, Integer, Boolean> viewConsumer){
        this.viewConsumer = viewConsumer; return this;
    }

    public GenericItemAdapter<T, ViewHolderImpl> setViewRetriever(DoubleRetriever<ViewHolderImpl, ViewGroup, Integer> viewRetriever){
        this.viewRetriever = viewRetriever; return this;
    }

    public static <T, ViewHolderImpl extends RecyclerView.ViewHolder> GenericItemAdapter<T, ViewHolderImpl> getInstance(){
        return new GenericItemAdapter<>();
    }

    public GenericItemAdapter<T, ViewHolderImpl> setItems(List<T> items){this.items = items; return this;}

    @Override
    public int getCount() {
        return Value.EMPTY(items) ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return Value.EMPTY(getItems()) ? null : getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return idRetriever.retrieve(getItem(position), position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderImpl impl;
        if(Value.NULL(convertView)){
            impl = viewRetriever.retrieve(parent, getItemViewType(position));
            convertView = impl.itemView;
            convertView.setTag(impl);
        }else{
            impl = (ViewHolderImpl) convertView.getTag();
        }
        viewConsumer.consume(impl, getItem(position), position, Value.Same.INTEGER(position, getCount() - 1));
        return convertView;
    }

    List<T> getItems(){
        return items;
    }

}
