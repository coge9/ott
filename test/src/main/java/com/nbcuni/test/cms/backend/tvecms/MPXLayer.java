package com.nbcuni.test.cms.backend.tvecms;

import com.google.common.collect.ComparisonChain;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Fields;
import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Query;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.mpx.MpxMagic;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.MpxThumbnailUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetGetByFieldsBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetGetByQueryBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetRangeBuilder;
import com.nbcuni.test.cms.utils.mpx.builders.assetsgetting.MpxAssetSortBuilder;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.mpx.objects.MpxCategory;
import com.nbcuni.test.cms.utils.mpx.objects.MpxThumbnail;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxProgramMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxVideoMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.TileSource;
import com.nbcuni.test.webdriver.Utilities;

import java.util.*;

/**
 * Created by Ivan_Karnilau on 05-Nov-15.
 */
public class MPXLayer {

    public static final String N_A = "n/a";
    public static final String SERIES = "Series";
    Config config = Config.getInstance();
    private String brand;
    private MpxMagic mpxMagic;
    private String ownerID;
    private String assetID;

    public MPXLayer(String brand, String ownerID, String assetID) {
        super();
        this.brand = brand;
        this.ownerID = ownerID;
        this.assetID = assetID;
        mpxMagic = new MpxMagic(config.getRokuMPXUsername(brand), config.getRokuMPXPassword(brand));
    }

    public MPXLayer(String brand, String ownerID, ContentType assetType) {
        super();
        this.brand = brand;
        this.ownerID = ownerID;
        switch (assetType) {
            case TVE_PROGRAM:
                this.assetID = config.getRokuMPXProgramID(brand, Instance.STAGE);
                break;
            case TVE_VIDEO:
                this.assetID = config.getRokuMPXVideoID(brand, Instance.STAGE);
                break;
            default:
                this.assetID = config.getRokuMPXProgramID(brand, Instance.STAGE);
                break;
        }
        mpxMagic = new MpxMagic(config.getRokuMPXUsername(brand), config.getRokuMPXPassword(brand));
    }

    public MPXLayer(String brand, ContentType assetType) {
        this(brand, Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE), assetType);
    }

    public MPXLayer(String brand) {
        super();
        this.brand = brand;
        this.ownerID = Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE);
        mpxMagic = new MpxMagic(config.getRokuMPXUsername(brand), config.getRokuMPXPassword(brand));
    }

    public String getAssetID() {
        return assetID;
    }

    public void updateImageDimensionsByID(String imageID, String width, String height) {
        MpxThumbnailUpdateBuilder updateBuilder = new MpxThumbnailUpdateBuilder(imageID);
        updateBuilder.updateWidth(width).updateHeight(height);
        mpxMagic.updateMpxThumbnail(updateBuilder);
    }

    public void updateImageAssetTypeByID(String imageID, String... assetTypes) {
        MpxThumbnailUpdateBuilder updateBuilder = new MpxThumbnailUpdateBuilder(imageID);
        updateBuilder.updateAsseTypes(assetTypes);
        mpxMagic.updateMpxThumbnail(updateBuilder);
    }

    public void updateImageAssetTypeByTitle(String imageTitle, String... assetTypes) {
        if (assetTypes.length == 0) {
            this.updateImageAssetTypeByID(this.getImageIDByTitle(imageTitle), "");
        } else {
            updateImageAssetTypeByID(this.getImageIDByTitle(imageTitle), assetTypes);
        }
    }

    public void updateVideoAssetData(String episodeTitle, String series, Integer seasonNumber, Integer episodeNumber) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder.updateTitle(episodeTitle).updateCategories(series).updateEpisodeNumber(episodeNumber).updateSeasonNumber(seasonNumber);
        mpxMagic.updateMpxAsset(updateBuilder);
    }

    public void updateProgramAssetData(String programTitle) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder.updateTitle(programTitle);
        mpxMagic.updateMpxAsset(updateBuilder);
    }

    public MpxThumbnail getMpxThumbnailById(String thumbnailId) {
        return mpxMagic.getMpxThumbnailAsJavaObject(thumbnailId);
    }

    public MPXLayer updateSeriesType(String seriesType) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder.updateSeriesType(seriesType);
        mpxMagic.updateMpxAsset(updateBuilder);
        return this;
    }

    public String getImageUrlById(String thumbanailId) {
        return mpxMagic.getMpxThumbnailAsJavaObject(thumbanailId).getUrl();
    }

    public String getImageUrlByTitle(String imageTitle) {
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        List<MpxThumbnail> thumbnailList = mpxAsset.getThumbnails();
        for (MpxThumbnail thumbnail : thumbnailList) {
            if (thumbnail.getTitle().equalsIgnoreCase(imageTitle)) {
                return thumbnail.getUrl();
            }
        }
        Utilities.logSevereMessageThenFail("Image " + imageTitle + " not found");
        return null;
    }

    public void updateCategories(String categories) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder.updateCategories(categories);
        mpxMagic.updateMpxAsset(updateBuilder);
    }

    /**
     * Find image ID by its title
     *
     * @param imageTitle - title of image
     * @return Image ID
     */
    public String getImageIDByTitle(String imageTitle) {
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        List<MpxThumbnail> thumbnailList = mpxAsset.getThumbnails();
        for (MpxThumbnail thumbnail : thumbnailList) {
            if (thumbnail.getTitle().equalsIgnoreCase(imageTitle)) {
                return thumbnail.getId();
            }
        }
        Utilities.logSevereMessageThenFail("Image " + imageTitle + " not found");
        return null;
    }

    public String getMpxThumbnailUrlByTitle(String imageTitle) {
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        List<MpxThumbnail> thumbnailList = mpxAsset.getThumbnails();
        for (MpxThumbnail thumbnail : thumbnailList) {
            if (thumbnail.getTitle().equalsIgnoreCase(imageTitle)) {
                return thumbnail.getUrl();
            }
        }
        return null;
    }

    public String getUrlOfThumbnail(String imageID) {
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        List<MpxThumbnail> thumbnailList = mpxAsset.getThumbnails();
        for (MpxThumbnail thumbnail : thumbnailList) {
            if (thumbnail.getId().equalsIgnoreCase(imageID)) {
                return thumbnail.getUrl();
            }
        }
        Utilities.logSevereMessageThenFail("Image " + imageID + " not found");
        return null;
    }

    /**
     * @param assetTypes can be n/a that means assetType is not important
     * @param width      can be n/a that means dimensions are not important
     * @param height     can be n/a that means dimensions are not important
     * @return id of thumbnail
     */

    public String getImageIDByAssetTypeAndDimensions(String assetTypes, String width, String height) {
        List<String> assetTypesList = Arrays.asList(assetTypes.split(","));
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        List<MpxThumbnail> thumbnailList = mpxAsset.getThumbnails();
        for (MpxThumbnail thumbnail : thumbnailList) {
            if ((assetTypes.equals(N_A) || thumbnail.getAssetTypes().containsAll(assetTypesList))
                    && (width.equals(N_A) || thumbnail.getWidth().toString().equals(width))
                    && (height.equals(N_A) || thumbnail.getHeight().toString().equals(height))) {
                return thumbnail.getId();
            }
        }
        Utilities.logSevereMessageThenFail("Image with asset type [" + assetTypes + "], width [" + width + "], height [" + height + "] not found");
        return null;
    }

    public void updateImageDimensionsByTitle(String imageTitle, String width, String height) {
        this.updateImageDimensionsByID(this.getImageIDByTitle(imageTitle), width, height);
    }

    public void updateAsset(MpxAssetUpdateBuilder builder) {
        mpxMagic.updateMpxAsset(builder);
    }

    public void updateAssetTitle(String title) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder.updateTitle(title);
        mpxMagic.updateMpxAsset(updateBuilder);
    }

    public void updateAssetTitleByAssetId(String title, String id) {
        Utilities.logInfoMessage("Update title with value [" + title + "]");
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(id);
        updateBuilder.updateTitle(title);
        mpxMagic.updateMpxAssetWithoutChecking(updateBuilder);
    }

    public void updateAvailableExpirationAirDate(Long availableDate, Long expirationDate, Long airDate) {
        MpxAssetUpdateBuilder updateBuilder = new MpxAssetUpdateBuilder(assetID);
        updateBuilder
                .updateAvailableDate(availableDate)
                .updateExpirationDate(expirationDate)
                .updateAirDate(airDate);

        mpxMagic.updateMpxAsset(updateBuilder);
    }

    public String getAssetTitle() {
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetID);
        return mpxAsset.getTitle();
    }

    public String getAssetGuid(String title) {
        JsonElement asset = mpxMagic.getMpxAssetAsJsonByTitle(title);
        return asset.getAsJsonObject().get("entries").getAsJsonArray().get(0).getAsJsonObject().get("guid").getAsString();
    }

    /**
     * Method return List of Assets from MPX where pl1$serverSideAdStitched exist and match with input parameter
     *
     * @param serverSideAdStitched - true or false, field of some Episodes within MPX
     * @return list of assets ids.
     */
    public List<String> getAssetsByServerSideAdStitchedValue(boolean serverSideAdStitched) {
        List<String> assets = new ArrayList<>();
        String serverSideAdStitchedOrigin = null;
        JsonArray entriesArray = mpxMagic.getAllAssetsAsArrayByOwner(Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE));
        for (JsonElement entry : entriesArray) {
            String category = entry.getAsJsonObject().get("categories").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
            if (!category.equalsIgnoreCase(SERIES)) {
                try {
                    serverSideAdStitchedOrigin = entry.getAsJsonObject().get("pl1$serverSideAdStitched").getAsString();
                } catch (Exception e) {
                    Utilities.logInfoMessage("The asset doesn't contain field pl1$serverSideAdStitched to be getted");
                }
                if (serverSideAdStitchedOrigin.equals(String.valueOf(serverSideAdStitched))) {
                    String id = entry.getAsJsonObject().get("id").getAsString();
                    id = id.substring(id.lastIndexOf("/") + 1);
                    assets.add(id);
                }
            }
        }
        return assets;
    }

    /**
     * @return List of Episodes from MPX where pl1$serverSideAdStitched field is absent
     */
    public List<String> getAssetsByEmptyServerSideAdStitchedValue() {
        List<String> assets = new ArrayList<>();
        String id = null;
        JsonArray entriesArray = mpxMagic.getAllAssetsAsArrayByOwner(Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE));
        for (JsonElement entry : entriesArray) {
            id = entry.getAsJsonObject().get("id").getAsString();
            String category = entry.getAsJsonObject().get("categories").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
            if (!category.equalsIgnoreCase(SERIES)) {
                try {
                    entry.getAsJsonObject().get("pl1$serverSideAdStitched");
                } catch (Exception e) {
                    id = id.substring(id.lastIndexOf("/") + 1);
                    assets.add(id);
                }
            }
        }
        return assets;
    }

    public boolean mpxAssetUpdater(MpxAssetUpdateBuilder updateBuilder) {
        return mpxMagic.updateMpxAsset(updateBuilder);
    }

    public boolean mpxAssetUpdaterWithoutChecking(MpxAssetUpdateBuilder updateBuilder) {
        return mpxMagic.updateMpxAssetWithoutChecking(updateBuilder);
    }

    public boolean mpxThumbnailUpdater(MpxThumbnailUpdateBuilder updateBuilder) {
        return mpxMagic.updateMpxThumbnail(updateBuilder);
    }

    public MpxAssetUpdateBuilder getMpxAssetUpdateBuilder() {
        return new MpxAssetUpdateBuilder(assetID);
    }

    /**
     * Method return List of Assets from MPX where pl1$fullEpisode exist and match with input parameter
     *
     * @param fullEpisode - true or false, field of some Episodes within MPX
     * @return list of MPX assets.
     */
    public List<MpxAsset> getAssetsByFullEpisodeValue(boolean fullEpisode) {
        List<MpxAsset> assets = new ArrayList<>();
        JsonArray entriesArray = mpxMagic.getAllAssetsAsArrayByOwner(Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE));
        for (JsonElement entry : entriesArray) {
            MpxAsset asset = null;
            if (entry != null) {
                asset = JsonParserHelper.getInstance().getJavaObjectFromJson(
                        entry, MpxAsset.class);
            } else {
                Utilities.logSevereMessageThenFail("Error durnig parsing asset from MPX");
            }
            List<MpxCategory> categories = asset.getCategories();
            if (categories != null && !categories.get(0).getName().equals(SERIES) && asset.getFullEpisode().equals(fullEpisode)) {
                assets.add(asset);
            }
        }
        return assets;
    }

    public List<MpxAsset> getProgramAssets() {
        List<MpxAsset> assets = new ArrayList<>();
        JsonArray entriesArray = mpxMagic.getAllAssetsAsArrayByOwner(Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE));
        for (JsonElement entry : entriesArray) {
            MpxAsset asset = null;
            if (entry != null) {
                asset = JsonParserHelper.getInstance().getJavaObjectFromJson(
                        entry, MpxAsset.class);
            } else {
                Utilities.logSevereMessageThenFail("Error durnig parsing asset from MPX");
            }
            List<MpxCategory> categories = asset.getCategories();
            if (categories != null && categories.get(0).getName().equals(SERIES)) {
                assets.add(asset);
            }
        }
        return assets;
    }

    public List<MpxAsset> getSortAssets(MpxAssetGetByFieldsBuilder fieldsBuilder, MpxAssetGetByQueryBuilder queryBuilder,
                                        String... assetId) {
        List<MpxAsset> assets = new ArrayList<>();
        JsonArray entriesArray = mpxMagic.getAssetsAsArray(fieldsBuilder, queryBuilder, new MpxAssetRangeBuilder(), new MpxAssetSortBuilder());
        for (JsonElement entry : entriesArray) {
            MpxAsset asset = null;
            if (entry != null) {
                asset = JsonParserHelper.getInstance().getJavaObjectFromJson(
                        entry, MpxAsset.class);
            } else {
                Utilities.logSevereMessageThenFail("Error during parsing asset from MPX");
            }
            assets.add(asset);
        }
        return assets;
    }

    /**
     *
     * @param programName - program name to get related episodes
     * @param maxCount - count of latest episodes of program to be returned
     * @return -list of latest episodes as mpxAssets
     */
    public List<MpxAsset> getLatestEpisodes(String programName, int maxCount) {
        MpxAssetGetByFieldsBuilder fieldsBuilder = new MpxAssetGetByFieldsBuilder()
                .get(Fields.TITLE)
                .get(Fields.GUID)
                .get(Fields.AIR_DATE);
        MpxAssetGetByQueryBuilder queryBuilder = new MpxAssetGetByQueryBuilder()
                .by(Query.BY_OWNER_ID, ownerID).by(Query.BY_CATEGORIES, "Series%2F" + SimpleUtils.encodeStringToHTML(programName));

        List<MpxAsset> sortEpisodes = this.getSortAssets(fieldsBuilder, queryBuilder);

        List<MpxAsset> latestEpisodes = new ArrayList<>();
        Collections.sort(sortEpisodes, new Comparator<MpxAsset>() {
            @Override
            public int compare(MpxAsset o1, MpxAsset o2) {

                return ComparisonChain.start()
                        .compare(o1.getSeasonNumber(), o2.getSeasonNumber())
                        .compare(o1.getEpisodeNumber(), o2.getEpisodeNumber())
                        .compare(o1.getPubDate(), o2.getPubDate())
                        .result();
            }
        });

        for (int i = 0; i < maxCount; i++) {
            latestEpisodes.add(sortEpisodes.get(i));
        }
        return latestEpisodes;
    }

    public void updateVideoMetadata(MpxVideoMetadata metadata) {
        updateVideoAssetData(metadata.getEpisodeTitle(), metadata.getRelatedShowTitle(), metadata.getSeason(), metadata.getEpisode());
    }

    public void updateProgramMetadata(MpxProgramMetadata metadata) {
        updateProgramAssetData(metadata.getEpisodeTitle());
    }

    public void updateSourceWithTestData(TileSource source) {
        String[] assetTypes = source.getAssetTypes();
        updateImageAssetTypeByID(source.getSecondSourceMpxId(), "");
        if (!assetTypes[0].equals(N_A)) {
            updateImageAssetTypeByID(source.getTestSourceMpxId(), assetTypes);
        }
        String dimmensions = source.getDimensions();
        if (!dimmensions.equals(N_A)) {
            updateImageDimensionsByID(source.getTestSourceMpxId(), dimmensions.split("x")[0], dimmensions.split("x")[1]);
            updateImageDimensionsByID(source.getSecondSourceMpxId(), "5", "5");
        }
    }

    public void updateSourceWithOldData(TileSource source) {
        String[] assetTypes = source.getAssetTypes();
        updateImageAssetTypeByID(source.getTestSourceMpxId(), "");
        if (!assetTypes[0].equals(N_A)) {
            updateImageAssetTypeByID(source.getSecondSourceMpxId(), assetTypes);
        }
        String dimmensions = source.getDimensions();
        if (!dimmensions.equals(N_A)) {
            updateImageDimensionsByID(source.getSecondSourceMpxId(), dimmensions.split("x")[0], dimmensions.split("x")[1]);
            updateImageDimensionsByID(source.getTestSourceMpxId(), "5", "5");
        }
    }

    public MpxAsset getMPXAsset() {
        return mpxMagic.getMpxAssetAsJavaObject(assetID);
    }

    public MpxThumbnail getThumbnailByAssetType(String assetType) {
        List<MpxThumbnail> mpxThumbnails = this.getMPXAsset().getThumbnails();
        for (MpxThumbnail mpxThumbnail : mpxThumbnails) {
            if (mpxThumbnail.getAssetTypes().contains(assetType)) {
                return mpxThumbnail;
            }
        }
        throw new TestRuntimeException("Asset " + assetID + "doesn't contain thumbnail with asset type " + assetType);
    }
}


