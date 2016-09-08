package com.example.hasee.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hasee on 2016/8/29.
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.example.hasee.criminalintent.crimeId";
    private static final String DIALOG_DATA = "dialog_date";
    private static final String DIALOG_TIME = "dialog_time";
    private static final String DIALOG_IMAGE = "dialog_image";
    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CODE_TIME = 1;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_CONTACT = 3;
    private Crime mCrime;
    private EditText mCrimeTitle;
    private Button mDateBtn;
    private Button mTimeBtn;
    private Button mchooseBtn;
    private Button mReportBtn;
    private Button mSuspectBtn;
    private CheckBox mSolvedCb;
    private ImageButton mCameraImageBtn;
    private ImageView mPhotoImageView;

    public static CrimeFragment getInstanse(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        if (crimeId != null) {
            mCrime = CrimeLab.getInstanse(getActivity()).getCrime(crimeId);
        }
    }

    @TargetApi(11)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mCrimeTitle = (EditText) view.findViewById(R.id.crime_title);
        mCrimeTitle.setText(mCrime.getCrimeTitle());
        mDateBtn = (Button) view.findViewById(R.id.crime_date);
        mTimeBtn = (Button) view.findViewById(R.id.crime_time);
        mchooseBtn = (Button) view.findViewById(R.id.choose_data_or_time);
        mSolvedCb = (CheckBox) view.findViewById(R.id.crime_solved);
        mCameraImageBtn = (ImageButton) view.findViewById(R.id.crime_camera_imagebtn);
        mPhotoImageView = (ImageView) view.findViewById(R.id.crime_imageview);
        mSuspectBtn = (Button) view.findViewById(R.id.crime_suspect_btn);
        mReportBtn = (Button) view.findViewById(R.id.crime_report_btn);

        final Intent reportIntent = getReportIntent();
        final Intent contactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        PackageManager pm = getActivity().getPackageManager();
       List<ResolveInfo> reportActivityes =  pm.queryIntentActivities(reportIntent,0);
       List<ResolveInfo> contactActivityes =  pm.queryIntentActivities(contactIntent,0);
        if (reportActivityes.size()==0) {
            mReportBtn.setEnabled(false);
        }
        if (contactActivityes.size()<=0) {
            mSuspectBtn.setEnabled(false);
        }

        registerForContextMenu(mPhotoImageView);

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
        updateDate();
        //mDateBtn.setEnabled(false);
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.getInstanse(mCrime.getDate());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CODE);
                dialog.show(fm, DIALOG_DATA);
            }
        });
        mTimeBtn.setText(Util.timeSdf.format(mCrime.getDate()));
        mTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.getInstanse(mCrime.getDate());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_CODE_TIME);
                timePickerFragment.show(fm, DIALOG_TIME);
            }
        });
        mchooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("choose")
                        .setPositiveButton("date", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatePickerFragment datePickerFragment = DatePickerFragment.getInstanse(mCrime.getDate());
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_CODE);
                                datePickerFragment.show(fm, DIALOG_DATA);
                            }
                        })
                        .setNegativeButton("time", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TimePickerFragment timePickerFragment = TimePickerFragment.getInstanse(mCrime.getDate());
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_CODE_TIME);
                                timePickerFragment.show(fm, DIALOG_TIME);
                            }
                        })
                        .create().show();
            }
        });
        mSolvedCb.setChecked(mCrime.isSolved());
        mSolvedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mCameraImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });
        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = mCrime.getPhoto();
                if (photo == null) {
                    return;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(photo.getFilename()).getAbsolutePath();
                ImageFragment.getInstance(path).show(fm, DIALOG_IMAGE);
            }
        });

        mReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(reportIntent);
            }
        });

        mSuspectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(contactIntent, REQUEST_CONTACT);
            }
        });

        if (mCrime.getSuspect() != null) {
            mSuspectBtn.setText(mCrime.getSuspect());
        }


        boolean hasCamera = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || Camera.getNumberOfCameras() > 0;
        if (!hasCamera) {
            mCameraImageBtn.setEnabled(false);
        }
        Log.i(TAG, container.getClass() + ":" + container.toString());

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.image_view_item_context, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.image_item_delete:
                deleteOldPhoto();
                mCrime.setPhoto(null);
                mPhotoImageView.setImageDrawable(null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private Intent getReportIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
        intent = Intent.createChooser(intent, getString(R.string.send_report));
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onPause() {
        super.onPause();
        //  CrimeLab.getInstanse(getActivity()).saveCrimesToSdcard();
        CrimeLab.getInstanse(getActivity()).saveCrimes();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoImageView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_list_item_context, menu);

    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.crime_list_item_delete:
                CrimeLab.getInstanse(getActivity()).delete(mCrime);
                mCrimeTitle.setText("");
                mSolvedCb.setChecked(false);
                mTimeBtn.setText("");
                mDateBtn.setText("");
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_CODE_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            mTimeBtn.setText(Util.timeSdf.format(mCrime.getDate()));
        } else if (requestCode == REQUEST_PHOTO) {
            String photoFileName = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (photoFileName != null) {
                deleteOldPhoto();
                Photo photo = new Photo(photoFileName);
                mCrime.setPhoto(photo);
                showPhoto();
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contectUri = data.getData();
            String[] queryContacts = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor = getActivity().getContentResolver().query(contectUri, queryContacts, null, null, null);
            if (cursor==null) {
                cursor.close();
                return;
            }
            cursor.moveToFirst();
            String suspect = cursor.getString(0);
            mCrime.setSuspect(suspect);
            mSuspectBtn.setText(suspect);
            cursor.close();
        }
    }

    private void deleteOldPhoto() {
        Photo oldPhoto = mCrime.getPhoto();
        if (oldPhoto==null) {
            return;
        }
        String oldfilePath = getActivity().getFileStreamPath(oldPhoto.getFilename()).getAbsolutePath();
        File file = new File(oldfilePath);
        if (file.exists()) {
            boolean isDelete = file.delete();
            Log.i(TAG, "old photo delete " + isDelete);
        }
    }


    private void updateDate() {
        mDateBtn.setText(Util.dateSdf.format(mCrime.getDate()));
    }

    private void showPhoto() {
        Photo photo = mCrime.getPhoto();
        BitmapDrawable drawable = null;
        if (photo != null) {
            String filePath = getActivity().getFileStreamPath(photo.getFilename()).getAbsolutePath();
            drawable = PictureUtils.getScaleDrawable(getActivity(), filePath);
        }
        mPhotoImageView.setImageDrawable(drawable);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateString = Util.dateSdf.format(mCrime.getDate());
        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mCrime.getCrimeTitle(), dateString, solvedString, suspect);
        return report;
    }
}
