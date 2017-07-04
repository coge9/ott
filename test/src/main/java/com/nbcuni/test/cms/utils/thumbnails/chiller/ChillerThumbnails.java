package com.nbcuni.test.cms.utils.thumbnails.chiller;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 4/25/16.
 */
public enum ChillerThumbnails implements ThumbnailsCutInterface {

    WMAX1200_HMAX800_SCALE("1200x800", "wmax1200_hmax800_scale", true, false),
    WMAX1600_HMAX900_SCALE("1600x900", "wmax1600_hmax900_scale", true, false),
    WMAX1920_HMAX1080_SCALE("1920x1080", "wmax1920_hmax1080_scale", true, false),
    WMAX400_HMAX200_SCALE("400x200", "wmax400_hmax200_scale", true, false),
    W1024_FOCALPOINT_4_1("1024x256", "w1024_focalpoint_4_1", true, true),
    W1024_FOCALPOINT_4_3("1024x768", "w1024_focalpoint_4_3", true, true),
    W1024_FOCALPOINT_16_9("1024x576", "w1024_focalpoint_16_9", true, true),
    W1200_FOCALPOINT_4_1("1200x300", "w1200_focalpoint_4_1", true, true),
    W1200_FOCALPOINT_4_3("1200x900", "w1200_focalpoint_4_3", true, true),
    W1200_FOCALPOINT_16_9("1200x675", "w1200_focalpoint_16_9", true, true),
    W1600_FOCALPOINT_4_1("1600x400", "w1600_focalpoint_4_1", true, true),
    W1600_FOCALPOINT_4_3("1600x1200", "w1600_focalpoint_4_3", true, true),
    W1600_FOCALPOINT_16_9("1600x900", "w1600_focalpoint_16_9", true, true),
    W1920_FOCALPOINT_4_1("1920x480", "w1920_focalpoint_4_1", true, true),
    W1920_FOCALPOINT_4_3("1920x1440", "w1920_focalpoint_4_3", true, true),
    W1920_FOCALPOINT_16_9("1920x1080", "w1920_focalpoint_16_9", true, true),
    W2560_FOCALPOINT_4_1("2560x640", "w2560_focalpoint_4_1", true, true),
    W2560_FOCALPOINT_16_9("2560x1440", "w2560_focalpoint_16_9", true, true),
    W900_FOCALPOINT_5_8("900x1440", "w900_focalpoint_5_8", true, true),
    W800_FOCALPOINT_1_1("800x800", "w800_focalpoint_1_1", true, true),
    W800_FOCALPOINT_COMPRESSIVE_2_1("1600x800", "w800_focalpoint_compressive_2_1", true, true),
    W800_FOCALPOINT_COMPRESSIVE_3_1("1600x533", "w800_focalpoint_compressive_3_1", true, true),
    W800_FOCALPOINT_COMPRESSIVE_4_1("1600x400", "w800_focalpoint_compressive_4_1", true, true),
    W800_FOCALPOINT_COMPRESSIVE_16_9("1600x900", "w800_focalpoint_compressive_16_9", true, true),
    W800_FOCALPOINT_COMPRESSIVE_4_3("1600x1200", "w800_focalpoint_compressive_4_3", true, true),
    W800_FOCALPOINT_COMPRESSIVE_5_3("1600x960", "w800_focalpoint_compressive_5_3", true, true),
    W600_FOCALPOINT_COMPRESSIVE_1_1("1200x1200", "w600_focalpoint_compressive_1_1", true, true),
    W600_FOCALPOINT_COMPRESSIVE_2_1("1200x600", "w600_focalpoint_compressive_2_1", true, true),
    W600_FOCALPOINT_COMPRESSIVE_3_1("1200x400", "w600_focalpoint_compressive_3_1", true, true),
    W600_FOCALPOINT_COMPRESSIVE_4_1("1200x300", "w600_focalpoint_compressive_4_1", true, true),
    W600_FOCALPOINT_COMPRESSIVE_4_3("1200x900", "w600_focalpoint_compressive_4_3", true, true),
    W600_FOCALPOINT_COMPRESSIVE_5_3("1200x720", "w600_focalpoint_compressive_5_3", true, true),
    WMAX600_HMAX300_SCALE_COMPRESSIVE("1200x600", "wmax600_hmax300_scale_compressive", false, false),
    W400_FOCALPOINT_COMPRESSIVE_1_1("800x800", "w400_focalpoint_compressive_1_1", false, true),
    W400_FOCALPOINT_COMPRESSIVE_2_1("800x400", "w400_focalpoint_compressive_2_1", false, true),
    W400_FOCALPOINT_COMPRESSIVE_4_5("800x1000", "w400_focalpoint_compressive_4_5", false, true),
    W400_FOCALPOINT_COMPRESSIVE_16_9("800x450", "w400_focalpoint_compressive_16_9", false, true),
    W200_FOCALPOINT_COMPRESSIVE_5_8("400x640", "w200_focalpoint_compressive_5_8", false, true),
    W50_FOCALPOINT_COMPRESSIVE_1_1("100x100", "w50_focalpoint_compressive_1_1", false, true),
    ORIGINAL("", "Original Image", false, true);

    private static List<ChillerThumbnails> allThumbnails = Arrays.asList(ChillerThumbnails.values());
    private String requiredAppSize;
    private String assetTypes;
    private Boolean scaling;
    private Boolean upScaling;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    ChillerThumbnails(String requiredAppSize, String assetTypes, boolean scaling, boolean upScaling) {
        this.assetTypes = assetTypes;
        this.requiredAppSize = requiredAppSize;
        this.scaling = scaling;
        this.upScaling = upScaling;
    }

    public static List<ChillerThumbnails> getAllTumbnails() {
        return allThumbnails;
    }

    @Override
    public int getRequiredAppWidth() {
        return Integer.parseInt(requiredAppSize.split("x")[0]);
    }

    @Override
    public int getRequiredAppHeight() {
        return Integer.parseInt(requiredAppSize.split("x")[1]);
    }

    @Override
    public String getRequiredAppSize() {
        return requiredAppSize;
    }

    public void setRequiredAppSize(String requiredAppSize) {
        this.requiredAppSize = requiredAppSize;
    }

    @Override
    public String getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(String assetTypes) {
        this.assetTypes = assetTypes;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isCutOffHeight() {
        return cutOffHeight;
    }

    @Override
    public void setCutOffHeight(boolean cutOffHeight) {
        this.cutOffHeight = cutOffHeight;
    }

    @Override
    public int getPixelsToCut() {
        return pixelsToCut;
    }

    @Override
    public void setPixelsToCut(int pixelsToCut) {
        this.pixelsToCut = pixelsToCut;
    }

    public Boolean getScaling() {
        return scaling;
    }

    public void setScaling(Boolean scaling) {
        this.scaling = scaling;
    }

    public Boolean getUpScaling() {
        return upScaling;
    }

    public void setUpScaling(Boolean upScaling) {
        this.upScaling = upScaling;
    }

    @Override
    public String getImageName() {
        return this.name();
    }

    @Override
    public String getMpxSourceSize() {
        return requiredAppSize;
    }

    @Override
    public String getImageNameAtMpx() {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass().getName());
    }

}
