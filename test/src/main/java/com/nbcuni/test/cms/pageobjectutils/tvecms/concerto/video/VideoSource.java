package com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.video;

public enum VideoSource {
    ORIGINAL_IMAGE("1920x1080", "Original Image", "field_ios_original_image");

    private String size;
    private String assetType;
    private String machineName;

    VideoSource(String size, String assetType, String machineName) {
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