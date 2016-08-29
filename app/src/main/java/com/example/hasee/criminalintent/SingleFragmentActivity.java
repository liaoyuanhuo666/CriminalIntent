package com.example.hasee.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by hasee on 2016/8/29.
 */
public abstract  class SingleFragmentActivity extends FragmentActivity{
    abstract Fragment createFragmetn();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment==null) {
            fragment = createFragmetn();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
    }



}
