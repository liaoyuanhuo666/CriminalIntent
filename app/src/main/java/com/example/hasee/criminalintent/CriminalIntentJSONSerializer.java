package com.example.hasee.criminalintent;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
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

    public void saveCrimesToSdcard(ArrayList<Crime> crimes) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for (Crime crime : crimes) {
            array.put(crime.toJson());
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdDir = Environment.getExternalStorageDirectory();
            File targetFile = new File(sdDir.getCanonicalPath() + File.separator + mFileName);
            RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
            raf.seek(targetFile.length());
            raf.write(array.toString().getBytes());
            raf.close();
        }
    }

    public ArrayList<Crime> loadCrimes() throws Exception {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;

        StringBuilder jsonString;
        try {
            InputStream ins = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(ins));
            jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } finally {
            if (reader!=null) {
                reader.close();
            }
        }

        JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
        for (int i = 0; i < array.length(); i++) {
            crimes.add(new Crime(array.getJSONObject(i)));
        }
        return crimes;
    }

    public ArrayList<Crime> loadCrimesFromSdcard() throws Exception {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;
        // 获取SD卡的目录
        StringBuilder jsonString;
        try {
            File sdDirFile = Environment.getExternalStorageDirectory();
            FileInputStream fis = new FileInputStream(sdDirFile.getCanonicalPath() + File.separator + mFileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } finally {
            if (reader!=null) {
                reader.close();
            }
        }

        JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
        for (int i = 0; i < array.length(); i++) {
            crimes.add(new Crime(array.getJSONObject(i)));
        }
        return crimes;
    }
}
