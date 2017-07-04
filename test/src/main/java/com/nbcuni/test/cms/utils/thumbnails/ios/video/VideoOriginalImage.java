package com.nbcuni.test.cms.utils.thumbnails.ios.video;

import com.nbcuni.test.cms.utils.thumbnails.rokuimages.TileSource;

/**
 * Created by Aliaksei_Klimenka1 on 8/19/2016.
 */
public enum VideoOriginalImage implements TileSource {

    USA("747160643947", "747163715629", "2560x1440", "Original Image"),
    BRAVO("", "", "2560x1440", "Original Image"),
    EONLINE("747169347534", "747169347535", "2560x1440", "Original Image"),
    SYFY("", "", "2560x1440", "Original Image"),
    TELEMUNDO("", "", "2560x1440", "Original Image");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;

    private VideoOriginalImage(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                               String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static VideoOriginalImage getSourcePerBrand(String brand) {
        for (VideoOriginalImage entry : VideoOriginalImage.values()) {
            if (entry.name().equalsIgnoreCase(brand)) {
                return entry;
            }
        }
        throw new RuntimeException("There is no brand with name [" + brand + "]");
    }

    @Override
    public String getTestSourceMpxId() {
        return testSourceMpxId;
    }

    @Override
    public String getSecondSourceMpxId() {
        return secondSourceMpxId;
    }

    @Override
    public String getDimensions() {
        return dimensions;
    }

    @Override
    public String[] getAssetTypes() {
        return assetTypes.split(",");
    }
}
