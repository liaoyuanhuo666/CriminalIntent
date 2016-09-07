package com.example.hasee.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by hasee on 2016/9/7.
 */
public class PictureUtils {

    public static BitmapDrawable getScaleDrawable(Activity activity, String path) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        float desWidth = display.getWidth();
        float desHeight = display.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > desHeight || srcWidth > desWidth) {
            if (srcHeight > srcWidth) {
                inSampleSize = Math.round(srcWidth / desWidth);
            } else {
                inSampleSize = Math.round(srcHeight / desHeight);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(activity.getResources(), bitmap);
    }

    public static void cleanImageView(ImageView imageView) {
        if (!(imageView.getDrawable() instanceof BitmapDrawable)) {
            return;
        }
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        drawable.getBitmap().recycle();
        imageView.setImageDrawable(null);
    }
}
