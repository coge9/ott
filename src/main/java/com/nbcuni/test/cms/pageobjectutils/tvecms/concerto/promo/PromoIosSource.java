package com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo;

/**
 * Created by Ivan_Karnilau on 9/20/2016.
 */
public enum PromoIosSource {

    PROMO_MEDIA("1920x1080", "Promo media", "field_promo_media");

    private String size;
    private String assetType;
    private String machineName;

    PromoIosSource(String size, String assetType, String machineName) {
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
