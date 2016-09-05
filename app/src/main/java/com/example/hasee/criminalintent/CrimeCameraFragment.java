package com.example.hasee.criminalintent;

import android.content.pm.PackageManager;
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

import java.io.IOException;
import java.util.List;

/**
 * Created by hasee on 2016/9/4.
 */
public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";
    SurfaceView mSurfaceView;
    Button mTakeBtn;
    Camera mCamera;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);
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
                getActivity().finish();
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
