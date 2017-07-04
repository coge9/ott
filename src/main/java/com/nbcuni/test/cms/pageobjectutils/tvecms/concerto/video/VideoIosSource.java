package com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.video;

/**
 * Created by Ivan_Karnilau on 08-Aug-16.
 */
public enum VideoIosSource {
    ORIGINAL_IMAGE("1920x1080", "Original Image", "field_ios_original_image");

    private String size;
    private String assetType;
    private String machineName;

    VideoIosSource(String size, String assetType, String machineName) {
        this.size = size;
        this.assetType = assetType;
        this.machineName = machineName;
    }

    public String getSize() {
        return size;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getMachineName() {
        return machineName;
    }
}
