package com.nbcuni.test.cms.backend.chiller.block.contenttype.post;

public enum BlurbMedia {

    UPLOAD("", "Upload"),
    YOUTUBE("https://www.youtube.com/watch?v=wZZ7oFKsKzY", "Web"),
    MPX_VIDEO("", "MPX Videos"),
    IMAGE("", "View Library");

    private String value;
    private String tabName;

    private BlurbMedia(String value, String tabName) {
        this.value = value;
        this.tabName = tabName;
    }

    public String getValue() {
        return value;
    }

    public String getTabName() {
        return tabName;
    }

}
