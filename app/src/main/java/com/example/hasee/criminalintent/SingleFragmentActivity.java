package com.example.hasee.criminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by hasee on 2016/8/29.
 */
public abstract  class SingleFragmentActivity extends FragmentActivity{
    abstract Fragment createFragmetn();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment==null) {
            fragment = createFragmetn();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
    }



}
