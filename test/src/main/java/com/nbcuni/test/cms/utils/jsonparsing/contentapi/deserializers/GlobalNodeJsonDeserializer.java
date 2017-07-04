package com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers;

import com.google.gson.*;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.mpx.objects.MpxCategory;
import com.nbcuni.test.cms.utils.mpx.objects.MpxThumbnail;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/21/2017.
 */
public class GlobalNodeJsonDeserializer implements JsonDeserializer<GlobalNodeJson> {

    /**
     * @author Aliaksei Dzmitrenka
     *
     * This class is parser json with node data into GlobalNodeJson class
     */

    @Override
    public GlobalNodeJson deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject condition = jsonElement.getAsJsonObject().get("output").getAsJsonObject().get("TveAqaApiGetNode").getAsJsonObject();
        String key = null;
        for (Map.Entry<String, JsonElement> entry : condition.entrySet()) {
            key = entry.getKey();
        }
        condition = condition.get(key).getAsJsonObject();

        GlobalNodeJson globalNodeJson = new GlobalNodeJson();
        globalNodeJson.setTitle(condition.get("title") != null ? condition.get("title").getAsString() : null);
        Utilities.logInfoMessage("TITLE: [" + globalNodeJson.getTitle() + "]");


        globalNodeJson.setPublished("1".equals(condition.get("status").getAsString()) ? true : false);
        globalNodeJson.setVid(condition.get("vid") != null ? condition.get("vid").getAsString() : null);
        globalNodeJson.setUid(condition.get("uid") != null ? condition.get("uid").getAsString() : null);
        globalNodeJson.setVuuid(condition.get("vuuid") != null ? condition.get("vuuid").getAsString() : null);
        globalNodeJson.setUuid(condition.get("uuid") != null ? condition.get("uuid").getAsString() : null);
        globalNodeJson.setNid(condition.get("nid") != null ? condition.get("nid").getAsString() : null);
        globalNodeJson.setType(condition.get("type") != null ? condition.get("type").getAsString() : null);
        globalNodeJson.setSlug(condition.get("slug") != null ? condition.get("slug").getAsString() : null);
        globalNodeJson.setShowColor(condition.get("field_show_color") != null ? condition.get("field_show_color").getAsJsonObject().get("und").getAsJsonArray().get(0).getAsJsonObject().get("rgb").getAsString() == null ? "" : condition.get("field_show_color").getAsJsonObject().get("und").getAsJsonArray().get(0).getAsJsonObject().get("rgb").getAsString() : null);
        globalNodeJson.setUrlPath(condition.get("path") != null ? "1".equals(condition.get("path").getAsJsonObject().get("pathauto").getAsString()) ? true : false : false);
        globalNodeJson.setProgramStatus(condition.get("field_program_status") != null ? getUndValue(condition.get("field_program_status")) : null);
        globalNodeJson.setGenre(condition.get("field_genre") == null || condition.get("field_genre").isJsonArray() ? "" : getUndValues(condition.get("field_genre")).get(0));

        globalNodeJson.setUpdatedDate(ZonedDateTime.ofInstant(Instant.ofEpochSecond(condition.get("changed").getAsLong()), GlobalConstants.NY_ZONE));
        globalNodeJson.setMpxAsset(condition.get("field_mpx_file") != null ? getMpxAsset(condition, globalNodeJson) : null);
        globalNodeJson.setImageSources(getVideoImageSources(condition));

        globalNodeJson.setLongDescription(condition.get("field_long_description") != null ? getUndValue(condition.get("field_long_description")) : null);
        globalNodeJson.setShowPageCta(condition.get("field_program_hero_cta") != null ? getUndValue(condition.get("field_program_hero_cta")) : null);

        if (condition.get("field_program_ft_carousel_cta") != null) {
            globalNodeJson.setFeatureCarouselCta(getUndValue(condition.get("field_program_ft_carousel_cta")));
        } else if (condition.get("field_video_ft_carousel_cta") != null) {
            globalNodeJson.setFeatureCarouselCta(getUndValue(condition.get("field_video_ft_carousel_cta")));
        }

        if (condition.get("field_program_ft_carousel_hl") != null) {
            globalNodeJson.setFeatureCarouselHeadline(getUndValue(condition.get("field_program_ft_carousel_hl")));
        } else if (condition.get("field_video_ft_carousel_headline") != null) {
            globalNodeJson.setFeatureCarouselHeadline(getUndValue(condition.get("field_video_ft_carousel_headline")));
        }

        if (condition.get("field_program_template") != null) {
            globalNodeJson.setGradient(getUndValue(condition.get("field_program_template")));
        } else if (condition.get("field_video_template") != null) {
            globalNodeJson.setGradient(getUndValue(condition.get("field_video_template")));
        }
        return globalNodeJson;

    }

    /**
     * Allow to parse mpx info into MpxAsset class
     *
     * @param object - json object to parse
     * @param globalNodeJson - object to parse to
     * @return MpxAsset object
     */

    public MpxAsset getMpxAsset(JsonObject object, GlobalNodeJson globalNodeJson) {
        JsonObject mpxObject = object.get("field_mpx_file").getAsJsonObject().get("und").getAsJsonArray().get(0).getAsJsonObject();
        MpxAsset mpxAsset = new MpxAsset();
        mpxAsset.setTitle(mpxObject.get("filename").getAsString());
        mpxAsset.setDescription(getUndValue(mpxObject.get("field_mpx_description")));
        mpxAsset.setMpxAssetUuid(mpxObject.get("uuid").getAsString());
        mpxAsset.setGuid(getUndValue(mpxObject.get("field_mpx_guid")));
        mpxAsset.setId(getUndValue(mpxObject.get("field_mpx_id")));
        mpxAsset.setPubDate(null == getUndValue(mpxObject.get("field_mpx_airdate")) || "0".equals(getUndValue(mpxObject.get("field_mpx_airdate"))) ? null : Long.valueOf(getUndValue(mpxObject.get("field_mpx_airdate"))));
        mpxAsset.setPid(getUndValue(mpxObject.get("field_mpx_main_released_file_pid")));
        mpxAsset.setKeywords(getUndValue(mpxObject.get("field_mpx_keywords")));
        mpxAsset.setClosedCaptions("1".equals(getUndValue(mpxObject.get("field_mpx_cc_available"))) ? true : false);
        mpxAsset.setEntitlement(getUndValue(mpxObject.get("field_mpx_entitlement")));
        mpxAsset.setDayPart(getUndValue(mpxObject.get("field_mpx_day_part")));
        mpxAsset.setDuration(null == getUndValue(mpxObject.get("field_mpx_video_length")) ? null : Integer.valueOf(getUndValue(mpxObject.get("field_mpx_video_length"))));
        mpxAsset.setAvailableDate(mpxObject.get("mpx_video_data").getAsJsonObject().get("available_date").getAsLong());
        mpxAsset.setExpirationDate(mpxObject.get("mpx_video_data").getAsJsonObject().get("expiration_date").getAsLong());
        mpxAsset.setEpisodeNumber(null == getUndValue(mpxObject.get("field_mpx_episode_number")) ? null : Integer.valueOf(getUndValue(mpxObject.get("field_mpx_episode_number"))));
        mpxAsset.setSeasonNumber(null == getUndValue(mpxObject.get("field_mpx_season_number")) ? null : Integer.valueOf(getUndValue(mpxObject.get("field_mpx_season_number"))));
        mpxAsset.setFullEpisode("1".equals(getUndValue(mpxObject.get("field_mpx_full_episode"))) ? true : false);
        mpxAsset.setSeriesCategory(getUndValue(mpxObject.get("field_mpx_series_category")));
        mpxAsset.setSeriesType(getUndValue(mpxObject.get("field_mpx_series_type")));
        mpxAsset.setSeriesTitle(getUndValue(mpxObject.get("field_mpx_series_title")));
        mpxAsset.setShortDescription(getUndValue(mpxObject.get("field_mpx_short_description")));
        mpxAsset.setAdvertisingGenre(getUndValue(mpxObject.get("field_mpx_advertising_genre")));
        mpxAsset.setAdvertisingId(getUndValue(mpxObject.get("field_mpx_external_advertiser_id")));
        mpxAsset.setProgrammingType(getUndValue(mpxObject.get("field_mpx_programming_type")));
        mpxAsset.setSeriesTmsId(getUndValue(mpxObject.get("field_mpx_series_tmsid")));
        mpxAsset.setEpisodeTmsId(getUndValue(mpxObject.get("field_mpx_episode_tmsid")));
        mpxAsset.setThumbnails(mpxObject.get("field_mpx_thumbnails").isJsonObject() ?
                getMpxThumbnails(mpxObject.get("field_mpx_thumbnails").getAsJsonObject()) : null);
        if (mpxObject.get("field_mpx_media_categories") != null && getUndValues(mpxObject.get("field_mpx_media_categories")) != null) {
            List<MpxCategory> categoriesList = new ArrayList<>();
            for (String category : getUndValues(mpxObject.get("field_mpx_media_categories"))) {
                MpxCategory mpxCategory = new MpxCategory();
                mpxCategory.setName(category);
                mpxCategory.setLabel("");
                mpxCategory.setScheme("");
                categoriesList.add(mpxCategory);
            }
            mpxAsset.setCategories(categoriesList);
        } else {
            mpxAsset.setCategories(null);
        }

        if (mpxObject.get("field_mpx_series_title").isJsonObject()) {
            NodeApi nodeApi = new NodeApi(System.getProperty("brand"));
            String programName = getUndValue(mpxObject.get("field_mpx_series_title"));
            if (nodeApi.isProgramExist(programName))
                globalNodeJson.setRelatedShowId(nodeApi.getProgramNodeByName(programName).getUuid());
        }

        return mpxAsset;
    }

    /**
     * Allow to get value ignoring UND array
     *
     * @param element - json element
     * @return String value
     */

    private String getUndValue(JsonElement element) {
        try {
            JsonObject object = null;
            if (element.isJsonObject()) {
                object = element.getAsJsonObject();
                return object.get("und").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
            } else {
                return null;
            }
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Allow to get list of values ignoring UND array
     *
     * @param element - json element
     * @return list of String values
     */

    private List<String> getUndValues(JsonElement element) {
        try {
            List<String> toReturn = new ArrayList<>();
            JsonObject object = null;
            if (element.isJsonObject()) {
                object = element.getAsJsonObject();
                for (JsonElement undElement : object.get("und").getAsJsonArray()) {
                    toReturn.add(undElement.getAsJsonObject().get("value").getAsString());
                }
                return toReturn;
            } else {
                return null;
            }
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Allow to parse json thumbnails into MpxThumbnail class
     *
     * @param object - image json object
     * @return list of MpxThumbnail
     */

    private List<MpxThumbnail> getMpxThumbnails(JsonObject object) {
        JsonArray array = object.get("und").getAsJsonArray().get(0).getAsJsonObject().get("mpx_thumbnails_data").getAsJsonArray();
        List<MpxThumbnail> mpxThumbnails = new ArrayList<>();
        for (JsonElement element : array) {
            JsonObject thumbnailObject = element.getAsJsonObject();
            MpxThumbnail mpxThumbnail = new MpxThumbnail();
            mpxThumbnail.setWidth(thumbnailObject.get("width").getAsInt());
            mpxThumbnail.setHeight(thumbnailObject.get("height").getAsInt());
            mpxThumbnail.setUrl(thumbnailObject.get("url").getAsString());
            List<String> assetTypes = new ArrayList<>();
            for (JsonElement assetType : thumbnailObject.get("asset_types").getAsJsonArray()) {
                assetTypes.add(assetType.getAsString());
            }
            mpxThumbnail.setAssetTypes(assetTypes);
            mpxThumbnails.add(mpxThumbnail);
        }
        return mpxThumbnails;
    }


    /**
     * Allow to parse json images into ImageSource class
     *
     * @param object - image json object
     * @return list of ImageSource
     */

    public List<ImageSource> getVideoImageSources(JsonObject object) {
        Set<ImageSource> imageSources = new HashSet<ImageSource>();
        for (SourceMatcher source : SourceMatcher.getSourcesSet()) {
            if (object.get(source.getMachineName()) != null && object.get(source.getMachineName()).isJsonObject()) {
                try {
                    JsonObject sourceObject = object.get(source.getMachineName()).getAsJsonObject().get("und").getAsJsonArray().get(0).getAsJsonObject();
                    ImageSource imageSource = new ImageSource();
                    imageSource.setMachineName(source.getMachineName());
                    imageSource.setImageName(sourceObject.get("filename").getAsString());
                    imageSource.setImageUrl(sourceObject.get("uri").getAsString());
                    imageSource.setOverriden("1".equals(getUndValue(sourceObject.get("ott_media_override_image"))) ? true : false);
                    imageSource.setUuid(sourceObject.get("uuid").getAsString());
                    imageSource.setVid(sourceObject.get("vid").getAsString());
                    imageSource.setPlatform(source.getPlatform());
                    imageSource.setWidth(sourceObject.get("metadata").getAsJsonObject().get("width").getAsInt());
                    imageSource.setHeight(sourceObject.get("metadata").getAsJsonObject().get("height").getAsInt());
                    imageSources.add(imageSource);
                } catch (Exception ex) {
                    Utilities.logInfoMessage("Source: " + source.getSourceName() + " is empty");
                }
            }
        }
        return new ArrayList<>(imageSources);
    }

}

