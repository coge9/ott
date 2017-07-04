package com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum LinkUsage {

    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    IMAGE("Image"),
    TRAILING("Trialing"),
    EXTERNAL("External"),
    NONE("- None -");

    private static List<LinkUsage> allItems = Arrays.asList(LinkUsage.values());
    private String usage;

    LinkUsage(String usage) {
        this.usage = usage;
    }

    public static LinkUsage getRandomUsage() {
        return allItems.get(new Random().nextInt(allItems.size()));
    }

    public static LinkUsage getUsageByValue(String value) {
        for (LinkUsage linkUsage : allItems) {
            if (linkUsage.getUsage().equals(value)) {
                return linkUsage;
            }
        }
        throw new RuntimeException("Usage with value '" + value + "' not found");
    }

    public String getUsage() {
        return usage;
    }

    public String getValueForPublishing() {
        if (this.equals(NONE)) {
            return null;
        } else {
            return this.getUsage();
        }
    }

}
