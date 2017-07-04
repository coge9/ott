package com.nbcuni.test.cms.pageobjectutils.tvecms;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PageNames {

    HOME("HOMEPAGE");

    private static final Random RANDOM = new Random();
    private static final List<PageNames> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageNames.values().length;
    private String name;

    private PageNames(String name) {
        this.name = name;
    }

    public static PageNames randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getName() {
        return name;
    }
}
