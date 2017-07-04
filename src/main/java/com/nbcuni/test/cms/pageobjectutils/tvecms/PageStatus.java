package com.nbcuni.test.cms.pageobjectutils.tvecms;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PageStatus {

    UNPUBLISHED, PUBLISHED;
    private static final Random RANDOM = new Random();
    private static final List<PageStatus> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageStatus.values().length;

    public static PageStatus randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static PageStatus getStatus(String status) {
        for (PageStatus value : PageStatus.values()) {
            if (value.toString().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new RuntimeException("Unable identify the status: " + status);

    }
}
