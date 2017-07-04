package com.nbcuni.test.cms.utils.thumbnails.chiller;

/**
 * Created by Aleksandra_Lishaeva on 4/26/16.
 */
public interface ThumbnailsCutInterface {

    String getRequiredAppSize();

    int getRequiredAppWidth();

    int getRequiredAppHeight();

    String getAssetTypes();

    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    boolean isCutOffHeight();

    void setCutOffHeight(boolean cutOffHeight);

    int getPixelsToCut();

    void setPixelsToCut(int pixelsToCut);

    String getImageName();

    String getMpxSourceSize();

    String getImageNameAtMpx();
}
