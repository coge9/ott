package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import com.nbcuni.test.webdriver.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan on 06.04.2016.
 */
public enum Status {
    UPCOMING("Upcoming"),
    INSEASON("In-Season"),
    OFFSEASON("Off-Season"),
    ARCHIVED("Archived"),
    CURRENT("Current"),
    CLASSIC("Classic");

    private static final Random RANDOM = new Random();
    private static final List<Status> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = Status.values().length;
    private final String value;
    Status(String value) {
        this.value = value;
    }

    public static Status randomValue() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Status getValue(String value) {
        for (Status status : VALUES) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        Utilities.logWarningMessage("There is no Status enum value to be matched with in-come: " + value);
        return null;
    }

    public String getValue() {
        return value;
    }
}
