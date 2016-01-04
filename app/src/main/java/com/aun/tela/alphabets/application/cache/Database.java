package com.aun.tela.alphabets.application.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static Database INSTANCE = null;

    public static final class Constants {
        public static final String NAME = "TELABase";
        public static final int VERSION = 1;
    }

    {
        INSTANCE = this;
    }

    public static void init(Context context){
        new Database(context, Constants.NAME, null, Constants.VERSION);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static int getResPathFromName(String name, Context context){
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}