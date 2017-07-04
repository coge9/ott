package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import com.nbcuni.test.webdriver.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan on 06.04.2016.
 */
public enum Genre {
    ADVENTURE("Adventure"),
    ACTION("Action"),
    ANTHOLOGY("Anthology"),
    ANIMATED("Animated"),
    CHILDREN("Children's"),
    COMEDY("Comedy"),
    STANDUP("Comedy, Stand-up"),
    CRIME("Crime"),
    DAYTIME("Daytime"),
    DOCUMENTARY("Documentary"),
    DOCUDRAMA("Docudrama"),
    EDUCATIONAL("Educational"),
    DRAMALITY("Dramality"),
    DRAMA("Drama"),
    COURTROOM("Drama, Courtroom"),
    MEDICAL("Drama, Medical"),
    FACTUAL("Factual"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HORROR("Horror");

    private static final Random RANDOM = new Random();
    private static final List<Genre> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = Genre.values().length;
    private final String value;
    Genre(String value) {
        this.value = value;
    }

    public static Genre randomValue() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Genre getValue(String value) {
        for (Genre genre : VALUES) {
            if (genre.getValue().equalsIgnoreCase(value)) {
                return genre;
            }
        }
        Utilities.logWarningMessage("There is no Genre enum value to be matched with in-come: " + value);
        return null;
    }

    public String getValue() {
        return value;
    }
}
