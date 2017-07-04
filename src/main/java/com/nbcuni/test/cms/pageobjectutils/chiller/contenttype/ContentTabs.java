package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

/**
 * Created by alekca on 15.05.2016.
 */
public enum ContentTabs {
    ASSOCIATION("Association"),
    ASSOCIATIONS("Associations"),
    PROMOTIONAL("Promotional"),
    MEDIA("Media"),
    URL_PATH_SETTINGS("URL path settings"),
    LINKS("Links"),
    MPX_INFO("MPX Info"),
    ANDROID_IMAGES("Android Images"),
    ROKU_IMAGES("Roku Images"),
    ROKU_IMAGES_SQS("Roku Images"),
    REVISION_INFORMATION("Revision information"),
    AUTHORING_INFORMATION("Authoring information"),
    PUBLISHING_OPTIONS("Publishing options"),
    GENERAL_INFO("General Info "),
    BASIC("Basic"),
    LIST("List"),
    PROGRAMMING("Programming"),
    IOS_IMAGES("iOS Images"),
    APPLETV_IMAGES("AppleTV Images"),
    FIRETV_IMAGES("FireTV Images"),
    XBOXONE_IMAGES("Xbox One Images"),
    ALL_IMAGES("Images");

    private String name;

    ContentTabs(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
