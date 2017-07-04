package com.nbcuni.test.cms.utils.mpx.builders;

import com.nbcuni.test.cms.utils.Freemarker;

import java.util.HashMap;
import java.util.Map;

public class MpxAssetUpdateBuilder {

    /**
     * @author Dzianis Kulesh
     *
     *         This class is represent Builder object, wich is bild JSON (body)
     *         of PUT request for sending to MPX. This JSON will update asset
     *         object it MPX
     *
     */

    private final StringBuilder builder;
    private String mpxId;

    private String categoriesUpdate = "[{\"name\": \"Series/%s\", \"scheme\": \"\", \"label\": \"\" }]";

    public MpxAssetUpdateBuilder(String mpxId) {
        super();
        this.mpxId = mpxId;
        builder = new StringBuilder();
    }

    public String getMpxId() {
        return mpxId;
    }

    public MpxAssetUpdateBuilder updateTitle(String updatedTitle) {
        builder.append("\"title\": \"" + updatedTitle + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateDescription(String updatedDescription) {
        builder.append("\"description\": \"" + updatedDescription + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateEpisodeNumber(Integer episodeNumber) {
        builder.append("\"pl1$episodeNumber\": \"" + episodeNumber + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateSeasonNumber(Integer seasonNumber) {
        builder.append("\"pl1$seasonNumber\": \"" + seasonNumber + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateAvailableDate(Long availableDate) {
        builder.append("\"media$availableDate\": \"" + availableDate + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateExpirationDate(Long expirationDate) {
        builder.append("\"media$expirationDate\": \"" + expirationDate + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateAirDate(Long airDate) {
        builder.append("\"pubDate\": \"" + airDate + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateCategories(String categories) {
        if (!(categories.startsWith("[") && categories.endsWith("]"))) {
            categories = String.format(categoriesUpdate, categories);
        }
        builder.append("\"categories\": " + categories + ",");
        return this;
    }

    public MpxAssetUpdateBuilder updateSeriesType(String seriesType) {
        builder.append("\"pl1$seriesType\": \"" + seriesType + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateSeriesCategory(String seriesCategory) {
        builder.append("\"pl1$seriesCategory\": \"" + seriesCategory + "\",");
        return this;
    }

    public MpxAssetUpdateBuilder updateShortDescription(String shortDescription) {
        builder.append("\"pl1$shortDescription\": \"" + shortDescription + "\",");
        return this;
    }

    /**
     * Return String (update request body) which in future should be passed to
     * MPX API call for put request of updating MPX Media object
     *
     *
     * @return - String (body of update request)
     *
     */
    public String build() {
        builder.deleteCharAt(builder.length() - 1);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("assetId", mpxId);
        parameters.put("body", builder.toString());
        return Freemarker.getStringFromTemplate("MpxAssetUpdate.ftl",
                parameters);
    }

}
