package com.nbcuni.test.cms.pageobjectutils.tvecms;

/**
 * Created by Alena_Aukhukova on 7/11/2016.
 */

public enum ObjectLabel {

    NODE_VIDEO("Node (TVE Video)"),
    NODE_PROGRAM("Node (TVE Program)"),
    FILE_IMAGE("File (Image)"),
    COLLECTIONS("Queue (C"),
    TVE_PAGE("TVE Page"),
    TVE_MODULE("Module"),
    TAXONOMY_TERM("Taxonomy term"),
    NODE_EPISODE("Node (TVE Episode)"),
    NODE_EVENT("Node (TVE Event)"),
    NODE_SERIES("Node (TVE Series)"),
    NODE_SEASON("Node (TVE Season)"),
    NODE_POST("Node (TVE Post)"),
    NODE_MEDIA_GALLERY("Node (TVE Media Gallery)"),
    NODE_ROLE("Node (TVE Role)"),
    NODE_PERSON("Node (TVE Person)"),
    PROMO("TVE Promo"),
    PLATFORM("Platform");

    private String name;

    ObjectLabel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
