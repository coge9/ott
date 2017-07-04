package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public enum EventType {
    MOVIE("Movie"),
    LIVE_EVENT("Live Event");

    private static final Random RANDOM = new Random();
    private static final List<EventType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = EventType.values().length;
    private final String value;
    EventType(String value) {
        this.value = value;
    }

    public static EventType randomValue() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getValue() {
        return value;
    }

}
