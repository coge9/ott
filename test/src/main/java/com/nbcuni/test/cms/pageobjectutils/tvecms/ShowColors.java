package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksandra_lishaeva on 6/16/17.
 */
public enum ShowColors {

    BLUE("#24329d"),
    YELLOW("#e7e122"),
    ORANGE("#f6ab09"),
    RED("#f60917"),
    GREEN("#32f609"),
    SKY("#09f6ef");

    private static final List<ShowColors> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private String value;

    private ShowColors(String value) {
        this.value = value;
    }

    public static ShowColors randomInstance() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getValue() {
        return value;
    }
}
