package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.*;

public enum Alignment {
    TOP("Top"), CENTERED("Centered"), BOTTOM("Bottom"), JUSTIFIED("Justified");

    private static final List<Alignment> VALUES = Collections
            .unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private final String allignmentName;

    Alignment(final String name) {
        allignmentName = name;
    }

    public static Alignment alignmentByValue(String value) {
        for (Alignment alignment : VALUES) {
            if (alignment.get().equals(value)) {
                return alignment;
            }
        }
        return null;
    }

    public static Alignment randomAlignment() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static List<Alignment> randomAlignments(Alignment sourcePlatform) {
        while (true) {
            List<Alignment> list = new ArrayList<Alignment>();
            for (int i = 0; i < SIZE; i++) {
                if (VALUES.get(i) != sourcePlatform) {
                    boolean status = RANDOM.nextBoolean();
                    if (status) {
                        list.add(VALUES.get(i));
                    }
                }
            }
            if (list.size() > 1) {
                return list;
            }
        }
    }

    public static Alignment randomAlignmentExcept(Alignment platform) {
        Alignment selectedPlatform;
        if (SIZE <= 1) {
            return null;
        }
        while (true) {
            selectedPlatform = VALUES.get(RANDOM.nextInt(SIZE));
            if (!platform.equals(selectedPlatform)) {
                return selectedPlatform;
            }
        }
    }

    public String get() {
        return allignmentName;
    }
}