package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Usage {

    ALL_CAST("All cast"),
    BACKGROUND("Background"),
    LARGE_PROMO("Large Promo"),
    CLIP("Clip"),
    EPISODE("Episode"),
    CAST_DETAIL("All cast"),
    FULL_BODY("Full body"),
    HEADSHOT("Headshot"),
    PROMO("Promo");

    private static List<Usage> allItems = Arrays.asList(Usage.values());
    private String usage;

    Usage(String usage) {
        this.usage = usage;
    }

    public static Usage getRandomUsage() {
        return allItems.get(new Random().nextInt(allItems.size()));
    }

    public String getUsage() {
        return usage;
    }

}
