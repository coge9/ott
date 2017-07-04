package com.nbcuni.test.cms.pageobjectutils.mvpd;

public enum MenuItem {
    // MVPD ADMIN
    MVPD_ADMIN("//ul[@id='admin-menu-menu']//a[@href='/admin/mvpd']"),
    MVPD_MANAGEMENT("//a[@href='/admin/mvpd/mvpd-management']"),
    BRAND_MANAGEMENT("//a[@href='/admin/mvpd/brand-management']"),
    ADD_BRAND("//a[@href='/admin/mvpd/brand-management/brand']"),
    ALL_BRANDS("//a[@href='/admin/mvpd/brand-management/all']"),
    FEATURED_MVPDS("//a[@href='/admin/mvpd/mvpd-management/manage_featured']"),
    PEOPLE("//a[@href='/admin/people']"),
    REPORST("//a[@href='/admin/reports']"),
    PUBLISH_CHANGES("//a[@href='/admin/mvpd_cache']"),
    COMMUNICATIONS("//a[@href='/admin/mvpd_cache/communications']"),

    // P7 ADMIN
    CONTENT_REVISIONS("//a[@href='/admin/content/content-revision']"),
    LOGOUT("//a[@href='/user/logout']"),

    // P7 ADMIN - CONTENT
    CONTENT("//ul[@id='admin-menu-menu']//a[@href='/admin/content']"),
    ADD_CONTENT("//a[@href='/node/add']"),
    MPX_MEDIA("//ul[@id='admin-menu-menu']//a[@href='/admin/content/file/mpxmedia']"),

    // P7 ADMIN - PEOPLE
    ADD_USER("//ul[@id='admin-menu-menu']//a[@href='/admin/people/create']"),

    // P7 Admin - TVE
    CONTENT_CREATION("//ul[@id='admin-menu-menu']//a[@href='/admin/tve/mpx/mpx_conversion']"),
    MPX_UPDATER("//ul[@id='admin-menu-menu']//a[@href='/admin/tve/mpx/mpx_updater']"),

    //p7 ADMIN - CONFIGURATION - DEVELOPMENT
    PERFORMANCE("//ul[@id='admin-menu-menu']//a[@href='/admin/config/development/performance']"),

    // P7 ADMIN - CONFIGURATION - MEDIA
    CRON("//ul[@id='admin-menu-menu']//a[@href='/admin/config/system/cron']"),
    THE_PLATFORM_MPX_SETTINGS("//ul[@id='admin-menu-menu']//a[@href='/admin/config/media/theplatform']"),;

    private String locator;

    private MenuItem(final String path) {
        this.locator = path;
    }

    public String getXPath() {
        return locator;
    }
}