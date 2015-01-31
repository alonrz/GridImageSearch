package com.example.alonrz.gridimagesearch;

import java.io.Serializable;

/**
 * Created by alonrz on 1/30/15.
 */
public class SettingsClass implements Serializable {
    private String imageSize, colorFilter, imageType, siteFilter;
    private static SettingsClass mSettings;

    private SettingsClass() {

    }

    public static SettingsClass getInstance() {
        if(mSettings == null)
            mSettings = new SettingsClass();

        return mSettings;
    }
    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }
}
