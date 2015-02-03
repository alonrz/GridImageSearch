package com.example.alonrz.gridimagesearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.squareup.picasso.Transformation;

public class SameRatioTransformation implements Transformation {
    int numOfCol = 1;
    Context mContext;
    WindowManager wm;
    Display display;

    public SameRatioTransformation(int numOfColumnsToDisplay, Context c)
    {
        mContext = c;
        numOfCol = numOfColumnsToDisplay;
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
    }

    @Override
    public Bitmap transform(Bitmap source) {

        Point size = new Point();
        display.getSize(size);
        int targetWidth = size.x/numOfCol;

        double ratio = (double)source.getHeight() / (double)source.getWidth();

        int targetHeight = (int) (targetWidth * ratio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }
        return result;

    }

    @Override
    public String key() {
        return null;
    }
}
