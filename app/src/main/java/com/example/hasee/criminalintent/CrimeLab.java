package com.example.hasee.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private Context mApplicationContext;
    public static CrimeLab sCrimeLab;
    private CriminalIntentJSONSerializer mSerializer;
    private static final String FILENAME = "crime.json";
    private static final String TAG = "CrimeLab";


    private CrimeLab(Context context) {
        mApplicationContext = context;
        mSerializer = new CriminalIntentJSONSerializer(mApplicationContext, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<>();
            Log.i(TAG, "load crimes fail" + e);
        }
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

    public void add(Crime crime) {
        mCrimes.add(crime);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.i(TAG, "save crimes Success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "save crimes fail:" + e);
            return false;
        }

    }

    public void delete(Crime crime) {
        mCrimes.remove(crime);
        saveCrimes();
    }
}
