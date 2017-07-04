package com.nbcuni.test.cms.utils.mpx.builders;


public class MpxThumbnailUpdateBuilder {

    /**
     * @author Dzianis Kulesh
     *
     *         This class is represent Builder object, wich is bild JSON (body)
     *         of PUT request for sending to MPX. This JSON will update
     *         thumbnail object it MPX
     *
     */

    private final StringBuilder builder;
    private final String updateBody = "{\"id\": \"http://data.media.theplatform.com/media/data/MediaFile/%s\", %s}";
    private String mpxId;

    public MpxThumbnailUpdateBuilder(String mpxId) {
        super();
        this.mpxId = mpxId;
        builder = new StringBuilder();
    }

    public String getMpxId() {
        return mpxId;
    }

    public MpxThumbnailUpdateBuilder updateTitle(String updatedTitle) {
        builder.append("\"title\": \"" + updatedTitle + "\",");
        return this;
    }

    public MpxThumbnailUpdateBuilder updateWidth(String width) {
        builder.append("\"width\": \"" + width + "\",");
        return this;
    }

    public MpxThumbnailUpdateBuilder updateHeight(String height) {
        builder.append("\"height\": \"" + height + "\",");
        return this;
    }

    public MpxThumbnailUpdateBuilder updateAsseTypes(String[] assetTypes) {
        builder.append("\"assetTypes\": [ ");
        for (String assetType : assetTypes) {
            if (!assetType.isEmpty()) {
                builder.append("\"" + assetType + "\",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("],");
        return this;
    }

    /**
     * Return String (update request body) which in future should be passed to
     * MPX API call for put request of updating MPX Thumbnail object
     *
     *
     * @return - String (body of update request)
     *
     */
    public String build() {
        builder.deleteCharAt(builder.length() - 1);
        return String.format(updateBody, mpxId, builder.toString());
    }

}
