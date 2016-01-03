package com.aun.tela.alphabets.application.util;

/**
 * Created by Joseph Dalughut on 29/12/15 at 9:30 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Log {

    public static final class Constants {
        public static final String LOG_TAG = "TelaDebug";
    }

    public static void d(String string){
        android.util.Log.d(Constants.LOG_TAG, string);
    }

}
