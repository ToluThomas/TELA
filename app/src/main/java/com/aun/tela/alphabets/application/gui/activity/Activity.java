package com.aun.tela.alphabets.application.gui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.gui.fragments.NavigationFragment;

import io.meengle.util.Value;

public class Activity extends AppCompatActivity {

    /**
     * For now, this is the only activity on which the entire application is run.
     */

    private static Activity INSTANCE = null; //hold a static globally visible instance

    {
        INSTANCE = this; //anytime a new instance is created, it's set as the static globally visible instance
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //replace(new SelectFragment());
        replace(new NavigationFragment());
    }

    /**
     * Set the background color for this activity. This has the same effect as changing the status bar color
     * @param color the color to be set as the background for the static globally visible activity
     */
    public static void setColor(Integer color){
        FrameLayout view = (FrameLayout) getInstance().findViewById(R.id.background);
        view.setBackgroundColor(color);
    }

    /**
     * @return the current globally visible instance of type {@link Activity}
     */
    public static Activity getInstance(){
        if(Value.NULL(INSTANCE)) throw new RuntimeException("Activity instance not set");
        return INSTANCE;
    }

    /**
     * Add a fragment to this activity.
     * @param fragment the fragment to be added.
     */
    public static void add(Fragment fragment){
        getInstance().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    /**
     * Replace the current fragment of the activity
     * @param fragment the fragment with which the current activity is to be replaced
     */
    public static void replace(Fragment fragment){
        getInstance().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {

    }
}