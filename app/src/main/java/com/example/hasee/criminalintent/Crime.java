package com.example.hasee.criminalintent;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class Crime {
    private UUID mCrimeId;
    private String mCrimeTitle;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_PHOTO = "solved";

    public Crime() {
        mCrimeId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws Exception {
        mCrimeId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mCrimeTitle = json.getString(JSON_TITLE);
        }
        if (json.has(JSON_PHOTO)) {
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
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
        if (mPhoto != null) {
            json.put(JSON_PHOTO, mPhoto.toJSON());
        }
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

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }

    @Override
    public String toString() {
        return mCrimeTitle;
    }
}
