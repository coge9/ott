package com.nbcuni.test.cms.pageobjectutils.entities.rules;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ProgrammingTypeFilter {
    FULLEPISODE("Full Episode"), ENTERVIEW("Interview"), TRAILER("Trailer"), SNEAKPEEK("Sneak Peek"), MUSICVIDEO("Music Video"), OUTTAKE("Outtake"), RECAP("RECAP"), SPECIAL("Special"), TEASERTRAILER("Teaser Trailer"), EXCERPT("Excerpt"), CURRENTPREVIEW("Current Preview"), GAME("Condensed Game"), CONCERT("Concert"), COMMENTARY("Commentary");

    private static final List<ProgrammingTypeFilter> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();
    private static final int SIZE = VALUES.size();
    private String filterType;

    private ProgrammingTypeFilter(final String filterType) {
        this.filterType = filterType;
    }

    public static ProgrammingTypeFilter get(final String value) {
        for (final ProgrammingTypeFilter filterType : ProgrammingTypeFilter.values()) {
            if (filterType.get().equals(value)) return filterType;
        }
        return null;
    }

    public static ProgrammingTypeFilter randomBrandName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static ProgrammingTypeFilter getProgrammingTypeByValue(String type) {
        for (ProgrammingTypeFilter typeFilter : VALUES) {
            if (typeFilter.get().equalsIgnoreCase(type)) {
                return typeFilter;
            }
        }
        throw new TestRuntimeException("There is no any Programming Type filter with value:" + type + " in the Enum");
    }

    public String get() {
        return filterType;
    }
}