package com.aun.tela.alphabets.application.gui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.gui.fragments.MainFragment;

import io.meengle.util.Value;

public class Activity extends AppCompatActivity {

    private static Activity INSTANCE = null;

    {
        INSTANCE = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(new MainFragment());
    }

    public static void setColor(Integer color){
        FrameLayout view = (FrameLayout) getInstance().findViewById(R.id.background);
        view.setBackgroundColor(color);
    }

    public static Activity getInstance(){
        if(Value.NULL(INSTANCE)) throw new RuntimeException("Activity instance not set");
        return INSTANCE;
    }

    public static void add(Fragment fragment){
        getInstance().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public static void replace(Fragment fragment){
        getInstance().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }
}