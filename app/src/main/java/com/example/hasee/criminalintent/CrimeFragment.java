package com.example.hasee.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.example.hasee.criminalintent.crimeId";
    private Crime mCrime;
    private EditText mCrimeTitle;
    private Button mDateBtn;
    private CheckBox mSolvedCb;

    public static CrimeFragment getInstanse(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,crimeId);
        CrimeFragment  fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        if (crimeId != null) {
            mCrime = CrimeLab.getInstanse(getActivity()).getCrime(crimeId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mCrimeTitle = (EditText) view.findViewById(R.id.crime_title);
        mCrimeTitle.setText(mCrime.getCrimeTitle());
        mDateBtn = (Button) view.findViewById(R.id.crime_date);
        mSolvedCb = (CheckBox) view.findViewById(R.id.crime_solved);
        mCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setCrimeTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateBtn.setText(Crime.sdf.format(mCrime.getDate()));
        mDateBtn.setEnabled(false);
        mSolvedCb.setChecked(mCrime.isSolved());
        mSolvedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        Log.i(TAG, container.getClass() + ":" + container.toString());
        return view;
    }


}
