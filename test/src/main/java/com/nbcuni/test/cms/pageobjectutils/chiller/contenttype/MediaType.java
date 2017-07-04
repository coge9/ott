package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

/**
 * Created by Ivan on 21.04.2016.
 */
public enum MediaType {
    EMBEDED_LINK("Embeded Link"),
    ADD("Add / Upload"),
    UPLOAD("Add / Upload");

    private String name;

    MediaType(String name) {
        this.name = name;
    }

    public static MediaType getMediaTypeByName(String name) {
        for (MediaType mediaType : MediaType.values()) {
            if (mediaType.getName().equals(name)) {
                return mediaType;
            }
        }
        throw new RuntimeException("Media type " + name + " not found!");
    }

    public String getName() {
        return name;
    }
}
