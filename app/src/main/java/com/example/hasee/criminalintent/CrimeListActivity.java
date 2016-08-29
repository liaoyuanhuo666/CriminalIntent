package com.example.hasee.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    Fragment createFragmetn() {
        return new CrimeListFragment();
    }
}
