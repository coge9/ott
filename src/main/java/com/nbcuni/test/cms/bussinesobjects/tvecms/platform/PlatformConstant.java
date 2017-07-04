package com.nbcuni.test.cms.bussinesobjects.tvecms.platform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alena_Aukhukova on 12/24/2015.
 */
public class PlatformConstant {

    public static final String NAME = "AQA platform ";
    public static final String MACHINE_NAME = "aqa_machine_name_";
    public static final String SETTINGS = "{\"id\": \"%s\"}";
    public static final String BRAND_NAME = "brandName";
    public static final String MVPD_ENTITLEMENT_SERVICE_URL = "http://mvpd-admin.nbcuni.com/mvpd/service/v2/entitlements?brand=usa&instance=prod&platform=Roku";
    public static final String WRONG_MVPD_ENTITLEMENT_SERVICE_URL = "http://google.com";
    //images
    public static final String GLOBAL_HEADER_BRAND_LOGO_IMAGE_NAME = "119x30x.png";
    //image Extension
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_PNG = ".png";
    //viewports
    public static final Map<String, Boolean> VIEWPORT_MAP;

    static {
        Map<String, Boolean> viewportMap = new HashMap<String, Boolean>();
        viewportMap.put("Android Mobile Portrait", false);
        viewportMap.put("Android Mobile Landscape", true);
        viewportMap.put("Android Tablet Portrait", false);
        viewportMap.put("Android Tablet Landscape", false);
        viewportMap.put("Roku Default", true);
        viewportMap.put("iOS Mobile Landscape", false);
        viewportMap.put("XboxOne Default", true);
        viewportMap.put("AppleTV Default", false);
        viewportMap.put("FireTV Default", true);
        viewportMap.put("iOS Tablet Landscape", false);
        VIEWPORT_MAP = Collections.unmodifiableMap(viewportMap);
    }

}
