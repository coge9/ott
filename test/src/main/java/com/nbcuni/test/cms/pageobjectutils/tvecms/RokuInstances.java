package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum RokuInstances {
    DEV("dev"), QA("qa"), STAGE("stage");

    private static final List<RokuInstances> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private String value;

    private RokuInstances(String value) {
        this.value = value;
    }

    public static RokuInstances randomInstance() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String get() {
        return value;
    }
}