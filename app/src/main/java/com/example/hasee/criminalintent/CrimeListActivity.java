package com.example.hasee.criminalintent;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.CallBacks, CrimeFragment.CallBacks {
    @Override
    Fragment createFragmetn() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }



    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getCrimeId());
            startActivity(i);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment oldFragment = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newFragment = CrimeFragment.getInstanse(crime.getCrimeId());
            if (oldFragment != null) {
                ft.remove(oldFragment);
            }
            ft.add(R.id.detailFragmentContainer, newFragment).commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment fragment = (CrimeListFragment) fm.findFragmentById(R.id.fragmentContainer);
        fragment.updateUI();
    }
}
