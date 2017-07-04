package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan on 06.04.2016.
 */
public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17"),
    UNRATED("Unrated"),
    ALLCHILDREN("All Children"),
    OLDERCHILDREN("Directed to Older Children"),
    GENERALAUDIENCE("General Audience"),
    FANTASYVIOLENCE("Directed to Older Children - Fantasy Violence"),
    PARENTALGUIDANCE("Parental Guidance Suggested"),
    PARENTSTRONGLY("Parents Strongly Cautioned"),
    MATUREAUDIENCES("Mature Audiences Only");

    private static final Random RANDOM = new Random();
    private static final List<Rating> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = Rating.values().length;
    private final String value;
    Rating(String value) {
        this.value = value;
    }

    public static Rating randomValue() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String

    getValue() {
        return value;
    }
}
