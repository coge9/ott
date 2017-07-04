package com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

/**
 * Enum for Dynamic Module functionality.
 * All - use all published programs
 * Customize - possibility to add programs manually
 */

public enum Programs {
    ALL("All"),
    CUSTOMIZE("Customize");

    private String programs;

    Programs(String programs) {

        this.programs = programs;
    }

    public static Programs getProgramsByText(String stringPrograms) {
        for (Programs program : values()) {
            if (program.getPrograms().equals(stringPrograms)) {
                return program;
            }
        }
        throw new TestRuntimeException("Sort by with text \"" + stringPrograms + "is not exists");
    }

    public String getPrograms() {
        return programs;
    }
}
