package com.example.alonrz.gridimagesearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by alonrz on 1/27/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImageUrls;
    WindowManager wm;
    Display display;

    public ImageAdapter(Context c, ArrayList<String> imagesUrls) {
        mContext = c;
        mImageUrls = imagesUrls;
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();

    }

    public int getCount() {
        return mImageUrls.size();
    }

    //ignored method
    public Object getItem(int position) {
        return null;
    }

    //ignored method
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(Color.BLACK);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(7, 7, 7, 7);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mImageUrls.get(position))
                .transform(new SameRatioTransformation())
                .placeholder(R.drawable.loading_image)
                .into(imageView);


        return imageView;
    }

    public class SameRatioTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {

            Point size = new Point();
            display.getSize(size);
            int targetWidth = size.x/2;

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

}
