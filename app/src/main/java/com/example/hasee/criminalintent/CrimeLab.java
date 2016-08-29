package com.example.hasee.criminalintent;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeLab  {
    private ArrayList<Crime> mCrimes;
    private Context mApplicationContext;
    public  static CrimeLab sCrimeLab;
    private CrimeLab(Context context){
        mApplicationContext = context;
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setCrimeTitle("Crime #"+i);
            crime.setSolved(i%2==0);
            mCrimes.add(crime);
        }
    }

    public static CrimeLab getInstanse(Context context){
        if (sCrimeLab==null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return  sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }
}
