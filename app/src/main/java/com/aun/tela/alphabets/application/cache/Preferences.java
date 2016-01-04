package com.aun.tela.alphabets.application.cache;

import android.content.Context;

import io.meengle.androidutil.cache.SecureSharedPreferences;

public class Preferences extends SecureSharedPreferences {


    private static Preferences INSTANCE = null;

    {
        INSTANCE = this;
    }

    public static final class Constants {
        public static final String NAME = "TELAPrefs";
        public static final String PASSWORD = "giddyfoopity147862for8in8in0missedtroublesor3as08hfzcxvgwgdfa";
    }

    public static void init(Context context){
        new Preferences(context);
    }

    public Preferences(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return Constants.NAME;
    }

    @Override
    public String getPassword() {
        return Constants.PASSWORD;
    }

    public static Preferences getInstance(){
        return INSTANCE;
    }
}