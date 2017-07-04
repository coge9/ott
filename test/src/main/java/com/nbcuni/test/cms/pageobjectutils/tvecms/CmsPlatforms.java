package com.nbcuni.test.cms.pageobjectutils.tvecms;

import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.Utilities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alekca on 16.01.16.
 */
public enum CmsPlatforms {
    ROKU("Roku", "%s-roku", false),
    ROKU_SQS("Roku", "%s-roku", true),
    IOS("iOS", "ios", true),
    APPLETV("AppleTV", "appletv", true),
    ANDROID("Android", "%s-android", false),
    FIRETV("FireTV", "firetv", true),
    XBOXONE("XboxOne", "xboxone", true);

    private String appName;
    private String appID;
    private boolean isConcerto;

    CmsPlatforms(String value, String appID, boolean isConcerto) {
        this.appName = value;
        this.appID = appID;
        this.isConcerto = isConcerto;
    }

    public static String getAppIdBYAppName(String appName, String brand) {
        for (CmsPlatforms platforms : values()) {
            if (platforms.appName.equalsIgnoreCase(appName)) {
                return String.format(platforms.appID, RokuBrandNames.getBrandID(brand));
            }
        }
        throw new TestRuntimeException("Could not to find platform name: " + appName);
    }

    public static boolean isPlatform(String name) {
        for (CmsPlatforms platform : CmsPlatforms.values()) {
            if (platform.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static CmsPlatforms getPlatform(String fullStringPlatform) {
        String platformPart = fullStringPlatform;
        if (platformPart.contains("-")) {
            platformPart = fullStringPlatform.substring(fullStringPlatform.indexOf("-"), fullStringPlatform.length() - 1);
        }
        if (fullStringPlatform.contains("AQA")) {
            return null;
        }
        return CmsPlatforms.valueOf(platformPart.toUpperCase());
    }

    public static List<CmsPlatforms> getConcertoPlatforms() {
        List<CmsPlatforms> platforms = new LinkedList<>();
        for (CmsPlatforms platform : values()) {
            if (platform.isConcerto()) {
                platforms.add(platform);
            }
        }
        return platforms;
    }

    public static List<CmsPlatforms> getSerialPlatforms() {
        List<CmsPlatforms> platforms = new LinkedList<>();
        for (CmsPlatforms platform : values()) {
            if (!platform.isConcerto()) {
                platforms.add(platform);
            }
        }
        return platforms;
    }

    public static CmsPlatforms getPlatformByMachineNameAndApiType(String machineName, APIType type) {
        if (machineName.toLowerCase().contains(ROKU.getAppName().toLowerCase())) {
            if (APIType.AMAZON.equals(type)) {
                return ROKU_SQS;
            } else {
                return ROKU;
            }
        }
        for (CmsPlatforms platform : values()) {
            if (machineName.toLowerCase().contains(platform.getAppName().toLowerCase())) {
                return platform;
            }
        }
        Utilities.logInfoMessage("No platform constant by machine name - " + machineName);
        return null;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isConcerto() {
        return isConcerto;
    }

    public String getAppId(String brand) {
        return String.format(appID, RokuBrandNames.getBrandID(brand));
    }
}
