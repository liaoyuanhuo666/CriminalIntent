package com.example.hasee.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class Crime {
    private UUID mCrimeId;
    private String mCrimeTitle;
    private Date mDate;
    private boolean mSolved;
    public static SimpleDateFormat sdf = new SimpleDateFormat("E yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    public Crime(){
        mCrimeId =UUID.randomUUID();
        mDate = new Date();
    }

    public String getCrimeTitle() {
        return mCrimeTitle;
    }

    public void setCrimeTitle(String crimeTitle) {
        mCrimeTitle = crimeTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }
}
