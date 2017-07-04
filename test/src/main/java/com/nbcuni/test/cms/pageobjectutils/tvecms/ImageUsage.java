package com.nbcuni.test.cms.pageobjectutils.tvecms;

public enum ImageUsage {

    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    LOGO_TERTIARY("Logo-Tertiary"),
    FEATURED("Featured"),
    THUMBNAIL("Thumbnail"),
    FEATURED_3("Featured-3"),
    FEATURED_4("Featured-4"),
    HERO("Hero"),
    HERO_2("Hero-2"),
    ROKU_SMALL("Roku-Small"),
    KEY_ART("Key-Art"),
    KEY_ART2("Key-Art2"),
    KEY_ART3("Key-Art3"),
    DYNAMIC_LEAD("Dynamic-lead"),
    LOGO("Logo"),
    LOGO_SECONDARY("Logo-Secondary");

    private String usage;

    ImageUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}
