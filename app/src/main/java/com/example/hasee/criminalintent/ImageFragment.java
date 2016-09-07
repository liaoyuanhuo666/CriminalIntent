package com.example.hasee.criminalintent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by hasee on 2016/9/7.
 */
public class ImageFragment extends DialogFragment {

    private static final String EXTRA_IMAGE_PATH = "com.example.hasee.criminalintent.image_path";
    private ImageView mImageView;

    public static ImageFragment getInstance(String imagePath) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(args);
        imageFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());
        String path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable drawable = PictureUtils.getScaleDrawable(getActivity(), path);
        mImageView.setImageDrawable(drawable);
        return mImageView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PictureUtils.cleanImageView(mImageView);
    }
}
