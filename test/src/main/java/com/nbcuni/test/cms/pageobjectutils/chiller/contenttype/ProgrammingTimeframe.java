package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan on 06.04.2016.
 */
public enum ProgrammingTimeframe {
    //for Chiller
    LATE_NIGHT("Late Night"),
    DAYTIME("Daytime"),
    PRIMETIME("Primetime"),
    //only for MPX
    LATE_NIGHT_MPX("Latenight"),
    VINTAGE("Vintage"),
    WEBEXCLUSIVE("WebExclusive"),
    ORIGINALS("Originals"),
    CLASSICS("Classics"),
    OTHERS("Others"),
    NEWS("News"),
    SPORTS("Sports");

    private static final Random RANDOM = new Random();
    private static final List<ProgrammingTimeframe> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = ProgrammingTimeframe.values().length;
    private final String value;
    ProgrammingTimeframe(String value) {
        this.value = value;
    }

    public static ProgrammingTimeframe randomValue() {
        List<ProgrammingTimeframe> chillerValues = Arrays.asList(LATE_NIGHT, DAYTIME, PRIMETIME);
        return chillerValues.get(RANDOM.nextInt(chillerValues.size()));
    }

    public static ProgrammingTimeframe getValueByName(String name) {
        for (ProgrammingTimeframe value : VALUES) {
            if (value.getValue().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
