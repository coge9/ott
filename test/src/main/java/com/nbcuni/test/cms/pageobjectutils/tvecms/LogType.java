package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksandra_lishaeva on 9/25/15.
 */
public enum LogType {
    OTT_DELIVER_PAGE("ott_delivery: page"),
    OTT_DELIVER_MODULE("ott_delivery: module"),
    OTT_DELIVER_LIST("ott_delivery: list"),
    OTT_PUBLISH_PAGE("OTT Publishing [ott_page]");

    private static final Random RANDOM = new Random();
    private static final List<LogType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageNames.values().length;
    private String name;

    private LogType(String name) {
        this.name = name;
    }

    public static LogType randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getName() {
        return name;
    }
}
