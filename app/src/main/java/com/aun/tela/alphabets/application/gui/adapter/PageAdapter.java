package com.aun.tela.alphabets.application.gui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 20/02/16 at 8:00 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class PageAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public PageAdapter(FragmentManager fm, List<T> f) {
        super(fm);
        this.fragments = f;
    }

    public PageAdapter(FragmentManager fm, List<T> f, List<String> titles) {
        this(fm, f);
        this.titles = titles;
    }

    public static <T extends Fragment> PageAdapter getInstance(FragmentManager fm, List<T> fragments){
        return new PageAdapter<>(fm, fragments);
    }

    public static <T extends Fragment> PageAdapter getInstance(FragmentManager fm, List<T> fragments, List<String> titles){
        return new PageAdapter<>(fm, fragments, titles);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        return fragments.get(index);
    }

    public void removeAt(int pos){
        try{
            this.fragments.remove(pos);
            this.notifyDataSetChanged();
        }catch(Exception e){

        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles== null || titles.isEmpty() ? "" : titles.get(position);
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return fragments.size();
    }

    private static String makeFragmentName(int viewId, int position)
    {
        return "android:switcher:" + viewId + ":" + position;
    }
    @Override public Parcelable saveState() { return null; }

    public void removeThis(Fragtivity fragment) {
        // TODO Auto-generated method stub
        try{
            if(this.fragments.contains(fragment)){
                fragments.remove(fragment);
            }
            this.notifyDataSetChanged();
        }catch(Exception e){

        }
    }
    public void clearObjects(){
        this.fragments.clear();
    }

}