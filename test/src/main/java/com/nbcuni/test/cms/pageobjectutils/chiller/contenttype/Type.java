package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan on 06.04.2016.
 */
public enum Type {

    //for Chiller
    TV_SERIES("TV Series"),
    WEB_SERIES("Web Series"),
    //for MPX
    MOVIES("Movies"),
    SHOW("Show"),
    SPECIAL("Special");

    private static final Random RANDOM = new Random();
    private static final List<Type> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private final String value;

    Type(String value) {
        this.value = value;
    }

    public static Type randomValue() {
        List<Type> chillerValues = Arrays.asList(TV_SERIES, WEB_SERIES);
        return chillerValues.get(RANDOM.nextInt(chillerValues.size()));
    }

    public static Type getValueByName(String name) {
        for (Type value : VALUES) {
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
