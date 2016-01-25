package com.aun.tela.alphabets.application.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.aun.tela.alphabets.application.Application;

import io.meengle.util.Value;

/**
 * Database implementation based on the AndroidUtil library by meengle.io.
 * Implemented statically since only one instance is usually needed and is threadsafe. In the case
 * of multithreading issues, please make the static instance 'volatile'
 */
public class Database extends SQLiteOpenHelper {

    private static /*volatile*/ Database INSTANCE = null; //our database instance.

    public static final class Constants {
        public static final String NAME = "TELABase"; //the name with which the database would be saved locally.
        public static final int VERSION = 1; //the version of the current database
    }

    {
        INSTANCE = this; //initialization block, anytime a new database instance is created, it would be set as the current database instance.
    }

    /**
     * Initialize a global instance for the application. You can call this method as many times without any
     * issues but is usually called once, usually from your {@link android.app.Application} subclass
     * @param context the context to be used in initializing the database.
     * @see Database
     */
    public static void init(Context context){
        new Database(Value.NULL(context) ? Application.getInstance() : context, Constants.NAME, null, Constants.VERSION);
    }

    /**
     *  Intialize the database by creating a new instance. This instance would be set as the default static instance.
     * Call this method once when your application is first created (usually from your {@link android.app.Application} subclass).
     * Calling this method more than once would have no adverse effects theoretically.
     * @param context the context to be used in initializing the database.
     * @param name the name of the database
     * @param factory the cursor factory to use for this instance. Usually devs pass in null.
     * @param version the version of the database
     */
    public Database(@NonNull Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*create tables here using the passed in SqliteDatabase, db
            like this : db.execSQL(sqlStatement); where sqlStatement is a String SQLite statement
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
            Never used this before. Read up on how to use it if you want to.
         */
    }

    /**
     * Create a resource Id based on a resources name and it's path
     * @param name the name of the resource in question
     * @param path the path of the resource in question
     * @param context the context to be used in creating the resourceId. This is required
     * @return the created resource Id.
     */
    public static int getResPathFromName(String name, String path, @NonNull Context context){
        return context.getResources().getIdentifier(name, path, context.getPackageName());
    }

    /**
     * This method returns the current {@link Database} instance. If there is none currently, one would be created.
     * @return the current {@link Database} instance
     */
    public static Database getInstance(){
        if(Value.NULL(INSTANCE))
            init(null);
        return INSTANCE;
    }
}