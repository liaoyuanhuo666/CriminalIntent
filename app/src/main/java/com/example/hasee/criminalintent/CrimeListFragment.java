package com.example.hasee.criminalintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeListFragment extends ListFragment {
private static final String TAG = "CrimeListFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Crime> mCrimes = CrimeLab.getInstanse(getActivity()).getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(),CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID,crime.getCrimeId());
        startActivity(intent);
    }

    class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, parent, false);
            }
            // configure the view for this Crime
            Crime c = getItem(position);

            TextView titleTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getCrimeTitle());
            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(Util.dateSdf.format(c.getDate()));
            CheckBox solvedCheckBox =
                    (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}
