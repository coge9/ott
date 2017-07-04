package com.nbcuni.test.cms.pageobjectutils.tve;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

public enum ContentType {
    // @formatter:off

    TVE_SEASON("TVE Season", ""),
    TVE_EPISODE("TVE Episode", ""),
    TVE_PROGRAM("TVE Program", "ott_program"),
    TVE_PERSON("TVE Person", ""),
    TVE_ROLE("TVE Role", ""),
    TVE_VIDEO("TVE Video", "ott_video"),
    PROMO("Promo", "promo"),
    FILTERED_ASSET_LIST_MODULE("Filtered Asset List Module", ""),
    FAQ("FAQ", ""),
    DYNAMIC_LEAD_TEMPLATE("Dynamic Lead Template", ""),
    CHARACTER_PROFILE("Character Profile", ""),
    ANY("- Any -", ""),
    TVE_MEDIA_GALLERY("TVE Media Gallery", ""),
    TVE_SERIES("TVE Series", ""),
    TVE_EVENT("TVE Event", ""),
    TVE_POST("TVE Post", ""),
    TVE_PROMO("TVE Promo", "promo");

    /**
     * Name for Content page filters
     */
    private String name;

    /**
     * Names for NodeApi types
     */
    private String nodeApiName;

    ContentType(final String contentTypeName, final String nodeApiName) {
        name = contentTypeName;
        this.nodeApiName = nodeApiName;
    }

    public static ContentType getContentByText(String stringType) {
        for (ContentType type : values()) {
            if (type.get().equals(stringType)) {
                return type;
            }
        }
        throw new TestRuntimeException("Content type with text \"" + stringType + " is not exists");
    }

    public String get() {
        return name;
    }

    public String getNodeApiName() {
        return nodeApiName;
    }
}