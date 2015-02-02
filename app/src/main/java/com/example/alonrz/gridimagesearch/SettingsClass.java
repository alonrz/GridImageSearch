package com.example.alonrz.gridimagesearch;

import java.io.Serializable;

/**
 * Created by alonrz on 1/30/15.
 */
public class SettingsClass implements Serializable {
    private String imageSize, colorFilter, imageType, siteFilter;
    private static SettingsClass mSettings;

    private SettingsClass() {
        imageSize = colorFilter = imageType = siteFilter = "";
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
        this.imageSize = (imageSize.toLowerCase().equals("any"))? "" : imageSize;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = (colorFilter.toLowerCase().equals("any"))? "":colorFilter;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType  = (imageType.toLowerCase().equals("any"))?"": imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter.replaceAll("\\s+", "");
    }

    @Override
    public String toString() {
        return "Settings --> image size: " + getImageSize() +
                ",color filter: " + getColorFilter() +
                ",image type: " + getImageType() +
                ",site filter: " + getSiteFilter();
    }
}
