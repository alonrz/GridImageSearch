package com.example.alonrz.gridimagesearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alonrz on 1/27/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImageUrls;
    public ImageAdapter(Context c, ArrayList<String> imagesUrls) {
        mContext = c;
        mImageUrls = imagesUrls;
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
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mImageUrls.get(position))
                .resize(mContext.getResources().getDisplayMetrics().widthPixels/3, 200)
                .into(imageView);


        return imageView;
    }

}
