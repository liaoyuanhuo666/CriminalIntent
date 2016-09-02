package com.example.hasee.criminalintent;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_SOLVED = "solved";

    public Crime() {
        mCrimeId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws Exception {
        mCrimeId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mCrimeTitle = json.getString(JSON_TITLE);
        }
        mDate = new Date(json.getLong(JSON_DATE));
        mSolved = json.getBoolean(JSON_SOLVED);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_ID, mCrimeId.toString());
        json.put(JSON_TITLE, mCrimeTitle);
        json.put(JSON_SOLVED, mSolved);
        return json;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getCrimeId() {
        return mCrimeId;
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

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    @Override
    public String toString() {
        return mCrimeTitle;
    }
}
