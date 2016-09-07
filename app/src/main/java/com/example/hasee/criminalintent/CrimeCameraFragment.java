package com.example.hasee.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by hasee on 2016/9/4.
 */
public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";
    public static final String EXTRA_PHOTO_FILENAME = " com.example.hasee.criminalintent.photo_name";

    SurfaceView mSurfaceView;
    Button mTakeBtn;
    Camera mCamera;
    private FrameLayout progressContainer;
     Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            progressContainer.setVisibility(View.VISIBLE);
        }
    };

     Camera.PictureCallback mJpegPicturtCallbck = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            boolean success = true;
            String photoFileName = UUID.randomUUID().toString() + ".jpg";
            OutputStream os = null;
            try {
                os = getActivity().openFileOutput(photoFileName, Context.MODE_PRIVATE);
                os.write(data);
            } catch (Exception e) {
                e.printStackTrace();
                success =false;
            }finally {
                if (os!=null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        success=false;
                    }
                }
            }
            if (success) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PHOTO_FILENAME,photoFileName);
                getActivity().setResult(Activity.RESULT_OK,intent);
            }else {
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            getActivity().finish();
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);
        progressContainer = (FrameLayout) view.findViewById(R.id.crime_camera_progressContainer);
        progressContainer.setVisibility(View.INVISIBLE);
        mSurfaceView = (SurfaceView) view.findViewById(R.id.camera_surface_view);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mCamera != null) {
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(TAG, "surfaceCreated failed;" + e);
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera == null) {
                    return;
                }
                Camera.Parameters params = mCamera.getParameters();
                Camera.Size size = getBestSupportSize(params.getSupportedPreviewSizes(), width, height);
                params.setPreviewSize(size.width, size.height);
                size = getBestSupportSize(params.getSupportedPictureSizes(),width,height);
                params.setPictureSize(size.width,size.height);
                mCamera.setParameters(params);
                try {
                    mCamera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "surfaceChanged failed;" + e);
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        });


        mTakeBtn = (Button) view.findViewById(R.id.take_camera);
        mTakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().finish();
                if (mCamera!=null) {
                    mCamera.takePicture(shutterCallback,null,mJpegPicturtCallbck);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera = Camera.open(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.Size getBestSupportSize(List<Camera.Size> sizes, int width, int heigth) {
        Camera.Size bestSize = sizes.get(0);
        int bestArea = width * heigth;
        for (Camera.Size size : sizes) {
            int area = size.width * size.height;
            if (area > bestArea) {
                bestArea = area;
                bestSize = size;
            }
        }
        return bestSize;
    }


}
