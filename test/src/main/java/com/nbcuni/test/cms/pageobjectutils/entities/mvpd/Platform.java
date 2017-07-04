package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import com.nbcuni.test.cms.elements.DdlSelectable;
import com.nbcuni.test.webdriver.Utilities;

import java.util.*;

public enum Platform implements DdlSelectable {
    DESKTOP("desktop", "Desktop"),
    MOBILE("mobile", "Mobile"),
    WIN8("win8", "Win8"),
    XBOXONE("xboxone", "XBOX"),
    APPLETV("AppleTV", "Apple TV"),
    IOS("iOs", "iOS"),
    ROKU("Roku", "Roku"),
    ANDROID("Android", "Android"),
    AMAZONFIRETV("AmazonFireTV", "AmazonFireTV");

    private static final List<Platform> VALUES = Collections
            .unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private final String platformName;
    private final String platformInSettingFile;

    Platform(final String name, final String nameInSettings) {
        platformName = name;
        platformInSettingFile = nameInSettings;
    }

    public static Platform randomPlatform() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Platform getPlatformByName(String name) {
        for (Platform platform : VALUES) {
            if (platform.get().equals(name)) {
                return platform;
            }
        }
        return null;
    }

    public static Platform getPlatformByNameInSettings(String name) {
        for (Platform platform : VALUES) {
            if (platform.getNameInSettingsFile().equals(name)) {
                return platform;
            }
        }
        return null;
    }

    public static List<Platform> randomPlatfroms(Platform sourcePlatform) {
        while (true) {
            List<Platform> list = new ArrayList<Platform>();
            for (int i = 0; i < SIZE; i++) {
                if (VALUES.get(i) != sourcePlatform) {
                    boolean status = RANDOM.nextBoolean();
                    if (status) {
                        list.add(VALUES.get(i));
                    }
                }
            }
            if (list.size() > 1) {
                return list;
            }
        }
    }

    public static Platform randomPlatfromExcept(Platform... platforms) {
        Platform selectedPlatform;
        if (platforms.length == SIZE) {
            return null;
        }
        int exitCondition = 0;
        while (true) {
            exitCondition++;
            selectedPlatform = VALUES.get(RANDOM.nextInt(SIZE));
            boolean isPresent = false;
            for (Platform tempPlatform : platforms) {
                if (selectedPlatform.equals(tempPlatform)) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                return selectedPlatform;
            }
            if (exitCondition > 50) {
                Utilities.logSevereMessageThenFail("Unable to select randon platform except provided.");
                return null;
            }
        }
    }

    public static Platform randomPlatfromExcept(Platform platform) {
        Platform selectedPlatform;
        if (SIZE <= 1) {
            return null;
        }
        while (true) {
            selectedPlatform = VALUES.get(RANDOM.nextInt(SIZE));
            if (!platform.equals(selectedPlatform)) {
                return selectedPlatform;
            }
        }
    }

    public static boolean isPlatform(String name) {
        for (Platform platform : Platform.values()) {
            if (platform.get().equals(name) || platform.getNameInSettingsFile().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String get() {
        return platformName;
    }

    public String getNameInSettingsFile() {
        return platformInSettingFile;
    }

    @Override
    public String getValueToSelect() {
        return get();
    }

    @Override
    public DdlSelectable getItemByText(String text) {
        return getPlatformByName(text);
    }
}