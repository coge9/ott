package com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

/**
 * Enum for Dynamic Module functionality.
 * All - use all types of videos (full and short)
 * FULL_EPISODE - use only full episodes
 * CLIPS - use only short episodes
 */

public enum VideoType {
    ALL("All"),
    FULL_EPISODE("Full episodes"),
    CLIPS("Clips");

    private final String type;

    VideoType(String type) {
        this.type = type;
    }

    public static VideoType getVideoTypeByText(String videoType) {
        for (VideoType type : values()) {
            if (type.getType().equals(videoType)) {
                return type;
            }
        }
        throw new TestRuntimeException("Video type with text \"" + videoType + "is not exists");
    }

    public String getType() {
        return type;
    }
}
