package com.aun.tela.alphabets.application.cache;

import android.content.Context;

import com.aun.tela.alphabets.application.Application;

import io.meengle.androidutil.cache.SecureSharedPreferences;
import io.meengle.util.Value;

public class Preferences extends SecureSharedPreferences {

    /**
     * Subclass of the Cryptographically safe {@link android.content.SharedPreferences} implementation
     * from AndroidUtil (by Meengle.io).
     * Android's {@link android.content.SharedPreferences} saves your key-value pairs, this class
     * encrypts them for you.
     * <p/>
     * You can create as many instances as you want by passing a name and password to the constructor
     * {@code Preferences(Context context, String name, String password}
     * You can also create a global instance by calling {@code init(Context context} or by calling
     * the static method {@code setInstance(Preferences preferences)}.
     * Access the global instance by calling {@code getInstance()}. If an instance is not available,
     * one would be created for you.
     */

    /*
    Please read through using SharedPreferences on android's developer website. If possible, also read about
    AES encryption, Hashing and cryptography in general.
     */

    private static Preferences INSTANCE = null; //Hold a static global instance. Make this volatile in
    // lieu of multithreading issues

    private String name, password; //the name with which the preferences would be saved. The password
    // to be used as an extra layer of security based on SecureSharedPreferences implementation

    /*
    Default constants (class).
     */
    public static final class Constants {
        public static final String NAME = "TELAPrefs";
        public static final String PASSWORD = "giddyfoopity147862for8in8in0missedtroublesor3as08hfzcx" +
                "vgwgdfa";
    }

    /**
     * Initialize a global instance for the application. You can call this method as many times without any
     * issues but is usually called once, usually from your {@link android.app.Application} subclass
     *
     * @param context the context to be used for initialization.
     */
    public static void init(Context context) {
        setInstance(new Preferences(context, Constants.NAME, Constants.PASSWORD));
    }

    /**
     * Create a new Preferences instance.
     *
     * @param context
     */
    @Deprecated
    public Preferences(Context context) {
        super(context);
    }

    /**
     * Create a new Preferences instance with {@param name} and {@param password}
     *
     * @param context  the context to be used for creation
     * @param name     the name of the preferences file.
     * @param password the password for the preferences file
     */
    public Preferences(Context context, String name, String password) {
        super(context);
        setName(name).setPassword(password);
    }

    /**
     * Set the name with which this {@link Preferences} instance is to be saved
     *
     * @param name a string name with which this {@link Preferences} instance is to be saved
     * @return the {@link Preferences} instance whose method was just called.
     */
    public Preferences setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the password with which this {@link Preferences} instance is to be secured
     *
     * @param password the string password with which this {@link Preferences} instance is to be secured
     * @return the {@link Preferences} instance whose method was just called.
     */
    public Preferences setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * @return the name of this instance
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the password with which this instance is secured
     */
    @Override
    public String getPassword() {
        return Constants.PASSWORD;
    }

    /**
     * Get the current default global {@link Preferences} instance. If there's none, one would be created
     * by default and returned.
     *
     * @return the current default global {@link Preferences} instance
     * @see com.aun.tela.alphabets.application.cache.Preferences.Constants
     */
    public static Preferences getInstance() {
        if (Value.NULL(INSTANCE))
            init(Application.getInstance());
        return INSTANCE;

    }
}
