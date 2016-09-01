package com.example.hasee.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private Context mApplicationContext;
    public static CrimeLab sCrimeLab;

    private CrimeLab(Context context) {
        mApplicationContext = context;
        mCrimes = new ArrayList<>();
    }

    public static CrimeLab getInstanse(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime : mCrimes) {
            if (crime.getCrimeId().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }

    public void add(Crime crime){
        mCrimes.add(crime);
    }
}
