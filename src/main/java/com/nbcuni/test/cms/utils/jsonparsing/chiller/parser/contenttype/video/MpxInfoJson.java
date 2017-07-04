package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video;

import com.google.common.base.MoreObjects;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuRatingJson;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/27/16.
 */
public class MpxInfoJson extends MpxAsset implements Cloneable {

    private String mpxId;
    private String mediaPid;
    private String mpxGuid;
    private String showId;
    private Long airDate;
    private Long availableDate;
    private Long expirationDate;
    private String dayPart;
    private String externalAdvertiserid;
    private String entitlement;
    private String shortDescription;
    private String programmingType;
    private Boolean closedCaptioning;
    private String seriesName;
    private String seriesType;
    private String seriesCategory;
    private String advertisingGenre;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private Integer duration;
    private List<String> mediaCategories;
    private String tmsId;

    public String getTmsId() {
        return tmsId;
    }

    public void setTmsId(String tmsId) {
        this.tmsId = tmsId;
    }

    public String getMpxId() {
        return mpxId;
    }

    public void setMpxId(String mpxId) {
        this.mpxId = mpxId;
    }

    public String getMediaPid() {
        return mediaPid;
    }

    public void setMediaPid(String mediaPid) {
        this.mediaPid = mediaPid;
    }

    public String getMpxGuid() {
        return mpxGuid;
    }

    public void setMpxGuid(String mpxGuid) {
        this.mpxGuid = mpxGuid;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public Long getAirDate() {
        return airDate;
    }

    public void setAirDate(Long airDate) {
        this.airDate = airDate;
    }

    @Override
    public Long getAvailableDate() {
        return this.availableDate;
    }

    @Override
    public void setAvailableDate(Long availableDate) {
        this.availableDate = availableDate;
    }

    @Override
    public Long getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getDayPart() {
        return this.dayPart;
    }

    @Override
    public void setDayPart(String dayPart) {
        this.dayPart = dayPart;
    }

    public String getExternalAdvertiserid() {
        return externalAdvertiserid;
    }

    public void setExternalAdvertiserid(String externalAdvertiserid) {
        this.externalAdvertiserid = externalAdvertiserid;
    }

    @Override
    public String getEntitlement() {
        return this.entitlement;
    }

    @Override
    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    @Override
    public String getShortDescription() {
        return this.shortDescription;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String getProgrammingType() {
        return programmingType;
    }

    @Override
    public void setProgrammingType(String programmingType) {
        this.programmingType = programmingType;
    }

    public Boolean getClosedCaptioning() {
        return closedCaptioning;
    }

    public void setClosedCaptioning(Boolean closedCaptioning) {
        this.closedCaptioning = closedCaptioning;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    @Override
    public String getSeriesType() {
        return this.seriesType;
    }

    @Override
    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    @Override
    public String getSeriesCategory() {
        return this.seriesCategory;
    }

    @Override
    public void setSeriesCategory(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }
/*
    @Override
    public String getAdvertisingGenre() {
        return advertisingGenre;
    }

    @Override
    public void setAdvertisingGenre(String advertisingGenre) {
        this.advertisingGenre = advertisingGenre;
    }*/

    @Override
    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @Override
    public Integer getEpisodeNumber() {
        return this.episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public List<String> getMediaCategories() {
        return mediaCategories;
    }

    public void setMediaCategories(List<String> mediaCategories) {
        this.mediaCategories = mediaCategories;
    }


    @Override
    public boolean equals(Object obj) {
        SoftAssert softAssert = new SoftAssert();
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        MpxInfoJson other = (MpxInfoJson) obj;
        softAssert.assertEquals(other.mpxId, mpxId, "The mpx Id doesn't matched");
        softAssert.assertEquals(other.mediaPid, mediaPid, "The media Pid doesn't matched");
        softAssert.assertEquals(other.mpxGuid, mpxGuid, "The mpx Guid doesn't matched");
        softAssert.assertEquals(other.showId, showId, "The show Id doesn't matched");
        softAssert.assertEquals(other.airDate, airDate, "The AirDate doesn't matched");
        softAssert.assertEquals(other.availableDate, availableDate, "The Available Date doesn't matched");
        softAssert.assertEquals(other.expirationDate, expirationDate, "The Expiration Date doesn't matched");
        softAssert.assertEquals(other.dayPart, dayPart, "The DayPart doesn't matched");
        softAssert.assertEquals(other.externalAdvertiserid, externalAdvertiserid, "The External Advertiser Id doesn't matched");
        softAssert.assertEquals(other.entitlement, entitlement, "The Entitlement doesn't matched");
        softAssert.assertEquals(other.getKeywords(), getKeywords(), "The Keywords don't matched");
        softAssert.assertEquals(other.description, description, "The Description doesn't matched");
        softAssert.assertEquals(other.shortDescription, shortDescription, "The Short description doesn't matched");
        softAssert.assertEquals(other.programmingType, programmingType, "The Programming Type doesn't matched");
        softAssert.assertEquals(other.closedCaptioning, closedCaptioning, "The Closed Caption doesn't matched");
        softAssert.assertEquals(other.seriesName, seriesName, "The Series Name doesn't matched");
        softAssert.assertEquals(other.seriesType, seriesType, "The Series Type doesn't matched");
        softAssert.assertEquals(other.seriesCategory, seriesCategory, "The Series Category doesn't matched");
        softAssert.assertEquals(other.advertisingGenre, advertisingGenre, "The Advertising Genre doesn't matched");
        softAssert.assertEquals(other.seasonNumber, seasonNumber, "The Season Number doesn't matched");
        softAssert.assertEquals(other.episodeNumber, episodeNumber, "The Episode Number doesn't matched");
        softAssert.assertEquals(other.duration, duration, "The Duration doesn't matched");
        softAssert.assertEquals(other.mediaCategories, mediaCategories, "The Media Categories don't matched");
        softAssert.assertEquals(other.tmsId, tmsId, "TMS ID doesn't match");
        return softAssert.getTempStatus();
    }

    public MpxInfoJson getObject(MpxAsset asset) {
        this.mpxId = asset.getId();
        this.mediaPid = asset.getPid();
        this.mpxGuid = asset.getGuid();
        this.showId = null;
        this.airDate = asset.getPubDate();
        this.availableDate = asset.getAvailableDate();
        this.expirationDate = asset.getExpirationDate();
        this.dayPart = StringUtils.isEmpty(asset.getDayPart()) ? null : asset.getDayPart();
        this.externalAdvertiserid = asset.getAdvertisingId();
        this.entitlement = StringUtils.isEmpty(asset.getEntitlement()) ? null : asset.getEntitlement();
        setKeywords(StringUtils.isEmpty(asset.getKeywords()) ? null : asset.getKeywords());
        this.description = StringUtils.isEmpty(asset.getDescription()) ? null : asset.getDescription();
        this.programmingType = StringUtils.isEmpty(asset.getProgrammingType()) ? null : asset.getProgrammingType();
        this.closedCaptioning = asset.getClosedCaptions();
        this.seriesName = StringUtils.isEmpty(asset.getSeriesTitle()) ? null : asset.getSeriesTitle();
        this.seriesType = StringUtils.isEmpty(asset.getSeriesType()) ? null : asset.getSeriesType();
        this.advertisingGenre = asset.getAdvertisingGenre(); //TODO disscuss.
        this.seasonNumber = asset.getSeasonNumber() == null ? null : asset.getSeasonNumber();
        this.episodeNumber = asset.getSeasonNumber() == null ? null : asset.getEpisodeNumber();
        this.duration = asset.getDuration() == 0 ? null : asset.getDuration();
        List<RokuRatingJson> rokuRatingJsons = asset.getRatings();
        for (RokuRatingJson rating : rokuRatingJsons) {
            rating.setScheme("urn:" + rating.getScheme());
        }
        setRatings(rokuRatingJsons);
        this.mediaCategories = asset.getMediaCategories().isEmpty() ? new ArrayList<String>() : asset.getMediaCategories();
        this.shortDescription = StringUtils.isEmpty(asset.getShortDescription()) ? null : asset.getShortDescription();
        this.tmsId = StringUtils.isEmpty(asset.getEpisodeTmsId()) ? null : asset.getEpisodeTmsId();
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tmsId", tmsId)
                .add("mpxId", mpxId)
                .add("mediaPid", mediaPid)
                .add("mpxGuid", mpxGuid)
                .add("showId", showId)
                .add("airDate", airDate)
                .add("availableDate", availableDate)
                .add("expirationDate", expirationDate)
                .add("dayPart", dayPart)
                .add("externalAdvertiserid", externalAdvertiserid)
                .add("entitlement", entitlement)
                .add("shortDescription", shortDescription)
                .add("programmingType", programmingType)
                .add("closedCaptioning", closedCaptioning)
                .add("seriesName", seriesName)
                .add("seriesType", seriesType)
                .add("seriesCategory", seriesCategory)
                .add("advertisingGenre", advertisingGenre)
                .add("seasonNumber", seasonNumber)
                .add("episodeNumber", episodeNumber)
                .add("duration", duration)
                .add("mediaCategories", mediaCategories)
                .toString();
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
