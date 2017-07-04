package com.nbcuni.test.cms.pageobjectutils.entities.mvpd.menu;

public enum MpvdMenuName {

    // MVPD ADMIN
    MVPD_ADMIN("MVPD Admin"),
    // ------  Brand Management -------
    BRAND_MANAGEMENT("Brand Management"),
    ADD_BRAND("Add Brand"),
    ALL_BRANDS("All Brands"),
    ENT_SERVICE_CONFIG("Entitlement Service Configuration"),
    // ------  Brand Management End-------

    // ------  Bulk Actions -------
    BULK_ACTIONS("Bulk Action"),
    BULK_ENTITLEMENTS("Bulk Entitlements"),
    CLONE_ENTITLEMENTS("Clone Entitlements"),
    UPLOAD_MVPD_LOGOS("Upload MVPD Logos"),
    IMPORT_ENTITLEMENTS_FILE("Import Entitlements File"),
    // ------  Bulk Actions  End-------

    // ------  MVPD Management -------
    MVPD_MANAGEMENT("MVPD Management"),
    ADD_MVPD("Add MVPD"),
    DEFAULT_MVPD_MESSAGES("Default MVPD Messages"),
    MVPD_SEARCH_AND_DELETE("MVPD Search and Delete"),
    FEATURED_MVPDS("Featured MVPDs"),
    // ------  MVPD Management End-------


    // ------  MVPD Services -------
    MVPD_SERVICES("MVPD Services"),
    DATA_SERVICES("Data Services"),
    IMAGE_SERVICES("Image Services"),
    // ------  MVPD Services-------

    MVPD_LOG_SETTINGS("MVPD Log Settings");

    private String name;

    MpvdMenuName(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
