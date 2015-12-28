package com.aun.tela.alphabets.application;

import com.aun.tela.alphabets.application.cache.Database;
import com.aun.tela.alphabets.application.cache.Preferences;

public class Application extends android.app.Application {

    private static Application INSTANCE = null;

    {
        /* The application class is always loaded before anything else, keep a static reference so we can access its context anywhere in the app. */
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.init(this);
        Database.init(this);
    }

    public static Application getInstance() throws RuntimeException {
        //if(INSTANCE == null) throw new RuntimeException("Application instance not set"); /* theoretically this should never happen.*/
        return INSTANCE;
    }


}
