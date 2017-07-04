package com.nbcuni.test.cms.utils.webdriver;

import org.openqa.selenium.Platform;

public enum PlatformName {

    ANY(Platform.ANY), WIN8(Platform.WIN8), WINDOWS(Platform.WINDOWS), ANDROID(Platform.ANDROID), MAC(Platform.MAC),
    LINUX(Platform.LINUX), UNIX(Platform.UNIX), VISTA(Platform.VISTA);

    private Platform platform;

    PlatformName(final Platform platform) {
        this.platform = platform;
    }

    public static Platform getPlatform(String name) {
        for (PlatformName device : PlatformName.values())
            if (name.equalsIgnoreCase(device.toString())) {
                return device.platform;
            }
        throw new RuntimeException("Cant find Platform with name: [" + name + "].");
    }

}