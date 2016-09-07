package com.example.hasee.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/9/2.
 */
public class CriminalIntentJSONSerializer {

    private Context mContext;
    private String mFileName;

    public CriminalIntentJSONSerializer(Context context, String fileName) {
        mFileName = fileName;
        mContext = context;
    }



    public void saveCrimes(ArrayList<Crime> crimes) throws IOException, JSONException {

        JSONArray array = new JSONArray();
        for (Crime crime : crimes) {
            array.put(crime.toJson());
        }

        Writer writer = null;
        try {
            OutputStream outs = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outs);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Crime> loadCrimes() throws Exception {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;

        InputStream ins = mContext.openFileInput(mFileName);
        reader = new BufferedReader(new InputStreamReader(ins));
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }

        JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
        for (int i = 0; i < array.length(); i++) {
            JSONObject job = array.getJSONObject(i);
            Crime crime = new Crime(job);
            crimes.add(new Crime(array.getJSONObject(i)));
        }
        return crimes;
    }
}
