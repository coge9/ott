package com.nbcuni.test.cms.utils.mpx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Fields;
import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Query;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.httpclient.CustomHttpClient;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.MpxThumbnailUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetGetByFieldsBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetGetByQueryBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetRangeBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetSortBuilder;
import com.nbcuni.test.cms.utils.mpx.deserializer.MpxThumbnailDeserializer;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.mpx.objects.MpxThumbnail;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MpxMagic {

    /**
     * @author Dzianis Kulesh
     * <p/>
     * This class is represent Service for working with MPX ThePlatfrom
     * by API calls
     */

    private static final String AUTH_URL = "https://identity.auth.theplatform.com/idm/web/Authentication/signIn?schema=1.0&form=json&_idleTimeout=";
    private static final String GET_ASSET_URL = "http://data.media.theplatform.com/media/data/Media/%s?schema=1.2&form=json&token=%s";
    private static final String GET_ASSET_URL_BY_TITLE = "http://data.media.theplatform.com/media/data/Media?schema=1.8.0&searchSchema=1.0&form=cjson&pretty=true&byTitle=%s&token=%s";
    private static final String GET_THUMBNAIL_URL = "http://data.media.theplatform.com/media/data/MediaFile/%s?schema=1.8.0&searchSchema=1.0&form=cjson&pretty=true&token=%s";
    private static final String UPDATE_ASSET_URL = "http://data.media.theplatform.com/media/data/Media?schema=1.8.0&searchSchema=1.0&form=cjson&pretty=true&token=%s";
    private static final String UPDATE_THUMBNAIL_URL = "http://data.media.theplatform.com/media/data/MediaFile?schema=1.8.0&searchSchema=1.0&form=cjson&pretty=true&token=%s";
    private static final String GET_FOR_ALL_ASSETS = "http://data.media.theplatform.com/media/data/Media?schema=1.8.0&searchSchema=1.0&form=json&pretty=true&fields=id,title&byOwnerId=%s&token=%s";
    private static final String GET_FOR_ASSETS =
            "http://data.media.theplatform.com/media/data/Media%s?schema=1.8.0&searchSchema=1.0&form=json&pretty=true%s%s%s%s&token=%s";

    private static final Integer SUCCESS_STATUS_CODE = 200;

    private CustomHttpClient httpClient = new CustomHttpClient();
    private String userName;
    private String userPassword;

    public MpxMagic(String userName, String userPassword) {
        super();
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Allow to obtain asset data from MPX by asset ID (as JSON)
     *
     * @param assetId - mpxId of asset you are going to get
     * @return - JsonElement represented mpx asset
     */
    public JsonElement getMpxAssetAsJson(String assetId) {
        HttpResponse httpResponse = httpClient.httpGet(String.format(
                GET_ASSET_URL, assetId, getToken()));
        HttpEntity responseEntity = httpResponse.getEntity();

        JsonElement element = null;
        try {
            element = JsonParserHelper.getInstance().getJsonFromInputStream(
                    responseEntity.getContent());
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting asset " + assetId + " from MPX "
                    + e.getMessage());
        }
        return element;
    }

    /**
     * Allow to obtain asset data from MPX by asset Title (as JSON)
     *
     * @param assetTitle - Title of asset of asset you are going to get
     * @return - JsonElement represented mpx asset
     */
    public JsonElement getMpxAssetAsJsonByTitle(String assetTitle) {
        HttpResponse httpResponse = httpClient.httpGet(String.format(
                GET_ASSET_URL_BY_TITLE, SimpleUtils.encodeStringToHTML(assetTitle), getToken()));
        HttpEntity responseEntity = httpResponse.getEntity();

        JsonElement element = null;
        try {
            element = JsonParserHelper.getInstance().getJsonFromInputStream(
                    responseEntity.getContent());
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting asset " + assetTitle + " from MPX "
                    + e.getMessage());
        }
        return element;
    }

    /**
     * Allow to obtain asset data from MPX by asset ID (as Java Object
     * (MpxAsset))
     *
     * @param assetId - mpxId of asset you are going to get
     * @return - MpxAsset object represented mpx asset
     */
    public MpxAsset getMpxAssetAsJavaObject(String assetId) {
        JsonElement assetJson = getMpxAssetAsJson(assetId);
        MpxAsset asset = null;
        if (assetJson != null) {
            asset = JsonParserHelper.getInstance().getJavaObjectFromJson(
                    assetJson, MpxAsset.class);
        } else {
            Utilities.logSevereMessage("Error durnig parsing asset from MPX");
        }
        return asset;
    }

    /**
     * Allow to obtain asset data from MPX by asset title (as Java Object
     * (MpxAsset))
     *
     * @param assetTitle - title of asset you are going to get
     * @return - MpxAsset object represented mpx asset
     */
    public MpxAsset getMpxAssetAsJavaObjectByTitle(String assetTitle) {
        JsonElement assetJson = getMpxAssetAsJsonByTitle(assetTitle);
        MpxAsset asset = null;
        if (assetJson != null) {
            asset = JsonParserHelper.getInstance().getJavaObjectFromJson(
                    assetJson, MpxAsset.class);
        } else {
            Utilities.logSevereMessage("Error durnig parsing asset from MPX");
        }
        return asset;
    }

    /**
     * Allow to obtain thumbnail data from MPX by thumbnail ID (as JSON)
     *
     * @param thumbnailId - mpxId of thubnail you are going to get
     * @return - JsonElement represented mpx thumbnail
     */
    public JsonElement getMpxThumbnailAsJson(String thumbnailId) {
        HttpResponse httpResponse = httpClient.httpGet(String.format(
                GET_THUMBNAIL_URL, thumbnailId, getToken()));
        HttpEntity responseEntity = httpResponse.getEntity();
        JsonElement element = null;
        try {
            element = JsonParserHelper.getInstance().getJsonFromInputStream(
                    responseEntity.getContent());
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting asset " + thumbnailId
                    + " from MPX " + e.getMessage());
        }
        return element;
    }

    public MpxThumbnail getMpxThumbnailAsJavaObject(String thumbnailId) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(MpxThumbnail.class, new MpxThumbnailDeserializer()).create();
        JsonElement thumbnailJson = getMpxThumbnailAsJson(thumbnailId);
        MpxThumbnail thumbnail = null;
        if (thumbnailJson != null) {
            thumbnail = JsonParserHelper.getInstance().getJavaObjectFromJson(gson,
                    thumbnailJson, MpxThumbnail.class);
        } else {
            Utilities.logSevereMessage("Error durnig parsing asset from MPX");
        }
        return thumbnail;
    }

    /**
     * Get all Assets from MPX by Owner ID as JsonArray
     *
     * @param ownerId - mpxId of owner
     * @return - assests by owner
     */
    public JsonArray getAllAssetsAsArrayByOwner(String ownerId) {
        HttpResponse httpResponse = httpClient.httpGet(String.format(
                GET_FOR_ALL_ASSETS, ownerId, getToken()));
        HttpEntity responseEntity = httpResponse.getEntity();
        JsonElement element = null;
        try {
            element = JsonParserHelper.getInstance().getJsonFromInputStream(
                    responseEntity.getContent());
            return element.getAsJsonObject().get("entries")
                    .getAsJsonArray();
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error during getting assets for owner " + ownerId
                    + " from MPX " + e.getMessage());
        }
        throw new TestRuntimeException("Error during getting assets for owner " + ownerId
                + " from MPX ");
    }

    /**
     * Get Assets from MPX by as JsonArray
     *
     * @param fieldsBuilder - required fields.
     * @param queryBuilder - query
     * @param rangeBuilder - range rules
     * @param sortBuilder - sorting rules
     * @param assetId - assed ID
     * @return - asset as array
     */
    public JsonArray getAssetsAsArray(MpxAssetGetByFieldsBuilder fieldsBuilder, MpxAssetGetByQueryBuilder queryBuilder,
                                      MpxAssetRangeBuilder rangeBuilder, MpxAssetSortBuilder sortBuilder, String... assetId) {
        String body = GET_FOR_ASSETS;
        String id = assetId.length != 0 ? "/" + assetId[0] : "";
        String fields = fieldsBuilder.build();
        String query = queryBuilder.build();
        String range = rangeBuilder.build();
        String sort = sortBuilder.build();
        String httpGet = String.format(body, id, fields, query, range, sort, getToken());

        HttpResponse httpResponse = httpClient.httpGet(httpGet);
        HttpEntity responseEntity = httpResponse.getEntity();
        JsonElement element = null;
        try {
            element = JsonParserHelper.getInstance().getJsonFromInputStream(
                    responseEntity.getContent());
            return element.getAsJsonObject().get("entries")
                    .getAsJsonArray();
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error during getting assets from MPX " + Utilities.convertStackTraceToString(e));
        }
        throw new TestRuntimeException("Error during getting assets from MPX ");
    }

    /**
     * Allow to obtain all asset IDs by ownerID
     *
     * @param ownerId - mpxId of owner
     * @return - list of asset IDs
     */
    public Map<String, String> getAllAssetsByOwner(String ownerId) {
        Map<String, String> assets = new HashMap<String, String>();
        JsonArray entriesArray = getAllAssetsAsArrayByOwner(ownerId);
        for (JsonElement entry : entriesArray) {
            String id = entry.getAsJsonObject().get("id").getAsString();
            String title = entry.getAsJsonObject().get("title")
                    .getAsString();
            id = id.substring(id.lastIndexOf("/") + 1);
            assets.put(id, title);
        }

        return assets;
    }

    /**
     * Allow to obtain asset IDs of those assets which doesn't have video file attached in MPX
     *
     * @param ownerId - mpxId of owner
     * @return - list of asset IDs
     */
    public List<String> getListOfAssetsWithoutVideoFile(String ownerId) {
        // prepare query which will return all assets
        MpxAssetGetByQueryBuilder queryBuilder = new MpxAssetGetByQueryBuilder();
        queryBuilder.by(Query.BY_APPROVED, "true").by(Query.BY_AVAILABILITY_STATE, "notYetAvailable%7Cavailable%7Cexpired").by(Query.BY_OWNER_ID, ownerId);
        // define query to return only ID field.
        MpxAssetGetByFieldsBuilder fields = new MpxAssetGetByFieldsBuilder().get(Fields.ID);
        ;
        // retrieve all MPX assets for the ownerId
        JsonArray assets = this.getAssetsAsArray(fields, queryBuilder, new MpxAssetRangeBuilder().getRange(1, 9999999), new MpxAssetSortBuilder());
        // parse JSON and collect ids to the list.
        List<String> assetIds = new ArrayList<String>();
        assets.forEach(item -> assetIds.add(item.getAsJsonObject().get("id").getAsString()));
        // modify query to return only assests which has video file attached.
        queryBuilder.by(Query.BY_CONTENT, "byExists%3Dtrue%26byHasReleases%3Dtrue");
        // retrieve all MPX assets with video file attached for the ownerId
        assets = this.getAssetsAsArray(fields, queryBuilder, new MpxAssetRangeBuilder().getRange(1, 9999999), new MpxAssetSortBuilder());
        // parse JSON and collect ids to the list.
        List<String> assetIdsWithVideo = new ArrayList<String>();
        assets.forEach(item -> assetIdsWithVideo.add(item.getAsJsonObject().get("id").getAsString()));
        // from all items remove those that does have video object attached.
        assetIds.removeAll(assetIdsWithVideo);
        // IDs in JSON are represents in form  http://data.media.theplatform.com/media/data/Media/553956419685
        // next code is going to parse that string and leave only ID value (553956419685)
        List<String> toReturn = new ArrayList<String>();
        assetIds.forEach(item -> toReturn.add(item.substring(item.lastIndexOf("/") + 1)));
        return toReturn;
    }

    /**
     * Allow to update data in MPX for the asset
     *
     * @param update - object of buider (MpxAssetUpdateBuilder) represented update
     *               data
     * @return - boolean if operation was successful
     */
    public Boolean updateMpxAsset(MpxAssetUpdateBuilder update) {
        // Checking availability of UPDATE
        MpxAsset asset = getMpxAssetAsJavaObject(update.getMpxId());
//        if (asset != null && !asset.getTitle().startsWith("AQA")) {
//            Utilities.logSevereMessage("Episode "
//                    + asset.getTitle()
//                    + " doesn't start with AQA. Seems it is not TEST episode, so update will not be performed");
//            return false;
//        }
        return updateAssetUrlByToken(update);
    }

    /**
     * Allow to update data in MPX for the asset without checking for title starts witn "AQA"
     *
     * @param update - object of buider (MpxAssetUpdateBuilder) represented update
     *               data
     * @return - boolean if operation was successful
     */

    public Boolean updateMpxAssetWithoutChecking(MpxAssetUpdateBuilder update) {
        MpxAsset asset = getMpxAssetAsJavaObject(update.getMpxId());
        if (asset.getTitle().startsWith("AQA")) {
            Utilities.logWarningMessage("Episode starts with AQA. After using title must be renamed to the original name.");
        }
        return updateAssetUrlByToken(update);
    }

    private Boolean updateAssetUrlByToken(MpxAssetUpdateBuilder update) {
        Boolean status = false;
        HttpResponse httpResponse = httpClient.httpPut(
                String.format(UPDATE_ASSET_URL, getToken()), update.build());
        if (httpResponse != null
                && httpResponse.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE) {
            status = true;
        } else {
            Utilities.logSevereMessage("Error during Updating MPX asset");
        }
        return status;
    }


    /**
     * Allow to update data in MPX for the asset thumbnail
     *
     * @param update - object of buider (MpxThumbnailUpdateBuilder) represented
     *               update data
     * @return - boolean if operation was successful
     */
    public Boolean updateMpxThumbnail(MpxThumbnailUpdateBuilder update) {
        // Checking availability of UPDATE
        JsonElement thumbnail = getMpxThumbnailAsJson(update.getMpxId());
        String mediaId = thumbnail.getAsJsonObject().get("mediaId")
                .getAsString();
        mediaId = mediaId.substring(mediaId.lastIndexOf("/") + 1);
        MpxAsset asset = getMpxAssetAsJavaObject(mediaId);

        Boolean status = false;
        HttpResponse httpResponse = httpClient
                .httpPut(String.format(UPDATE_THUMBNAIL_URL, getToken()),
                        update.build());
        if (httpResponse != null
                && httpResponse.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE) {
            status = true;
        } else {
            Utilities.logSevereMessage("Error during Updating MPX asset");
        }
        return status;
    }

    /**
     * Method do login to MPX and get secret token, which is used in future for
     * API calls
     *
     * @return - String : secret token
     */
    private String getToken() {
        HttpResponse httpResponse = httpClient.httpGetWithAuth(AUTH_URL,
                userName, userPassword);
        String token;
        try {
            HttpEntity responseEntity = httpResponse.getEntity();
            JsonElement element = JsonParserHelper.getInstance()
                    .getJsonFromInputStream(responseEntity.getContent());
            token = element.getAsJsonObject().get("signInResponse")
                    .getAsJsonObject().get("token").getAsString();
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error during Auth to MPX " + Utilities.convertStackTraceToString(e));
            throw new RuntimeException("Unable to get MPX auth Token");
        }
        return token;
    }
}
