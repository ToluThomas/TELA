package com.aun.tela.alphabets.application.util;

public class Log {

    public static final class Constants {
        public static final String LOG_TAG = "TelaDebug";
    }

    public static void d(String string){
        android.util.Log.d(Constants.LOG_TAG, string);
    }

}
