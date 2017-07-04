package com.nbcuni.test.cms.utils.mpx.objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuRatingJson;

import java.util.ArrayList;
import java.util.List;

public class MpxAsset extends AbstractEntity {

    protected String description;
    private String title;
    private String guid;
    private String id;
    private String ownerId;
    private Long pubDate;
    private String pid;
    private String keywords;
    private String mpxAssetUuid;
    private List<InnerFields> content;
    private boolean closedCaptions;
    private List<RokuRatingJson> ratings = new ArrayList<>();

    @SerializedName("pl1$entitlement")
    private String entitlement;

    @SerializedName("pl1$dayPart")
    private String dayPart;

    @SerializedName("media$availableDate")
    private Long availableDate;

    @SerializedName("media$expirationDate")
    private Long expirationDate;

    @SerializedName("pl1$episodeNumber")
    private Integer episodeNumber;

    @SerializedName("pl1$seasonNumber")
    private Integer seasonNumber;

    @SerializedName("pl1$fullEpisode")
    private Boolean fullEpisode;

    @SerializedName("pl1$seriesCategory")
    private String seriesCategory;

    @SerializedName("pl1$seriesType")
    private String seriesType;

    @SerializedName("pl1$show")
    private String seriesTitle;

    @SerializedName("pl1$shortDescription")
    private String shortDescription;

    @SerializedName("nbcu$advertisingGenre")
    private String advertisingGenre;

    @SerializedName("nbcu$programmingType")
    private String programmingType;

    @SerializedName("pl1$externalAdvertiserId")
    private String advertisingId;

    @SerializedName("media$thumbnails")
    private List<MpxThumbnail> thumbnails;

    @SerializedName("media$categories")
    private List<MpxCategory> categories;
    private String episodeTmsId;
    private String seriesTmsId;

    @SerializedName("pl1$event")
    private String event;

    public MpxThumbnail getThumbnailByDimension(Integer height, Integer width) {
        for (MpxThumbnail thumbnail : thumbnails) {
            if (thumbnail.getHeight().equals(height)
                    && thumbnail.getWidth().equals(width)) {
                return thumbnail;
            }
        }
        return null;
    }

    public MpxThumbnail getThumbnailByDimensionAndAssetType(Integer height,
                                                            Integer width, List<String> assetTypes) {
        for (MpxThumbnail thumbnail : thumbnails) {
            if (thumbnail.getHeight().equals(height)
                    && thumbnail.getWidth().equals(width)
                    && thumbnail.getAssetTypes().containsAll(assetTypes)) {
                return thumbnail;
            }
        }
        return null;
    }

    public MpxThumbnail getThumbnailByTitleStarts(String titleStarts) {
        for (MpxThumbnail thumbnail : thumbnails) {
            if (thumbnail.getTitle().startsWith(titleStarts)) {
                return thumbnail;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public String getDayPart() {
        return dayPart;
    }

    public void setDayPart(String dayPart) {
        this.dayPart = dayPart;
    }

    public Long getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Long availableDate) {
        this.availableDate = availableDate;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Boolean getFullEpisode() {
        return fullEpisode;
    }

    public void setFullEpisode(Boolean fullEpisode) {
        this.fullEpisode = fullEpisode;
    }

    public String getSeriesCategory() {
        return seriesCategory;
    }

    public void setSeriesCategory(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAdvertisingGenre() {
        return advertisingGenre;
    }

    public void setAdvertisingGenre(String advertisingGenre) {
        this.advertisingGenre = advertisingGenre;
    }

    public List<MpxThumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<MpxThumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<MpxCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MpxCategory> categories) {
        this.categories = categories;
    }

    public List<String> getMediaCategories() {
        List<MpxCategory> categories = getCategories();
        List<String> mediaCategories = new ArrayList<>();
        for (MpxCategory category : categories) {
            mediaCategories.add(category.getName());
        }
        return mediaCategories;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    public List<InnerFields> getContent() {
        return content;
    }

    public void setContent(List<InnerFields> content) {
        this.content = content;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getProgrammingType() {
        return programmingType;
    }

    public void setProgrammingType(String programmingType) {
        this.programmingType = programmingType;
    }

    public boolean getClosedCaptions() {
        return closedCaptions;
    }

    public void setClosedCaptions(boolean closedCaptions) {
        this.closedCaptions = closedCaptions;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public Integer getDuration() {
        return getContent().get(0).getDuration();
    }

    public void setDuration(int duration) {
        List<InnerFields> content = new ArrayList<>();
        InnerFields innerFields = new InnerFields();
        innerFields.setDuration(duration);
        content.add(innerFields);
        this.content = content;
    }

    public List<RokuRatingJson> getRatings() {
        return ratings;
    }

    public void setRatings(List<RokuRatingJson> ratings) {
        this.ratings = ratings;
    }

    public String getEpisodeTmsId() {
        return episodeTmsId;
    }

    public void setEpisodeTmsId(String episodeTmsId) {
        this.episodeTmsId = episodeTmsId;
    }

    public String getSeriesTmsId() {
        return seriesTmsId;
    }

    public void setSeriesTmsId(String seriesTmsId) {
        this.seriesTmsId = seriesTmsId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMpxAssetUuid() {
        return mpxAssetUuid;
    }

    public void setMpxAssetUuid(String mpxAssetUuid) {
        this.mpxAssetUuid = mpxAssetUuid;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("description", description)
                .add("guid", guid)
                .add("id", id)
                .add("ownerId", ownerId)
                .add("pubDate", pubDate)
                .add("pid", pid)
                .add("keywords", keywords)
                .add("content", content)
                .add("closedCaptions", closedCaptions)
                .add("ratings", ratings)
                .add("entitlement", entitlement)
                .add("dayPart", dayPart)
                .add("availableDate", availableDate)
                .add("expirationDate", expirationDate)
                .add("episodeNumber", episodeNumber)
                .add("seasonNumber", seasonNumber)
                .add("fullEpisode", fullEpisode)
                .add("seriesCategory", seriesCategory)
                .add("seriesType", seriesType)
                .add("seriesTitle", seriesTitle)
                .add("shortDescription", shortDescription)
                .add("advertisingGenre", advertisingGenre)
                .add("programmingType", programmingType)
                .add("advertisingId", advertisingId)
                .add("thumbnails", thumbnails)
                .add("categories", categories)
                .add("episodeTmsId", episodeTmsId)
                .add("seriesTmsId", seriesTmsId)
                .add("event", event)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MpxAsset mpxAsset = (MpxAsset) o;
        return closedCaptions == mpxAsset.closedCaptions &&
                Objects.equal(title, mpxAsset.title) &&
                Objects.equal(description, mpxAsset.description) &&
                Objects.equal(guid, mpxAsset.guid) &&
                Objects.equal(id, mpxAsset.id) &&
                Objects.equal(ownerId, mpxAsset.ownerId) &&
                Objects.equal(pubDate, mpxAsset.pubDate) &&
                Objects.equal(pid, mpxAsset.pid) &&
                Objects.equal(keywords, mpxAsset.keywords) &&
                Objects.equal(content, mpxAsset.content) &&
                Objects.equal(ratings, mpxAsset.ratings) &&
                Objects.equal(entitlement, mpxAsset.entitlement) &&
                Objects.equal(dayPart, mpxAsset.dayPart) &&
                Objects.equal(availableDate, mpxAsset.availableDate) &&
                Objects.equal(expirationDate, mpxAsset.expirationDate) &&
                Objects.equal(episodeNumber, mpxAsset.episodeNumber) &&
                Objects.equal(seasonNumber, mpxAsset.seasonNumber) &&
                Objects.equal(fullEpisode, mpxAsset.fullEpisode) &&
                Objects.equal(seriesCategory, mpxAsset.seriesCategory) &&
                Objects.equal(seriesType, mpxAsset.seriesType) &&
                Objects.equal(seriesTitle, mpxAsset.seriesTitle) &&
                Objects.equal(shortDescription, mpxAsset.shortDescription) &&
                Objects.equal(advertisingGenre, mpxAsset.advertisingGenre) &&
                Objects.equal(programmingType, mpxAsset.programmingType) &&
                Objects.equal(advertisingId, mpxAsset.advertisingId) &&
                Objects.equal(thumbnails, mpxAsset.thumbnails) &&
                Objects.equal(categories, mpxAsset.categories) &&
                Objects.equal(episodeTmsId, mpxAsset.episodeTmsId) &&
                Objects.equal(seriesTmsId, mpxAsset.seriesTmsId) &&
                Objects.equal(event, mpxAsset.event);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, description, guid, id, ownerId, pubDate, pid, keywords, content, closedCaptions,
                ratings, entitlement, dayPart, availableDate, expirationDate, episodeNumber, seasonNumber, fullEpisode,
                seriesCategory, seriesType, seriesTitle, shortDescription, advertisingGenre, programmingType, advertisingId, thumbnails, categories, episodeTmsId, seriesTmsId, event);
    }

    @Override
    public boolean verifyObject(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MpxAsset other = (MpxAsset) obj;
        if (advertisingGenre == null) {
            if (other.advertisingGenre != null)
                return false;
        } else if (!advertisingGenre.equals(other.advertisingGenre))
            return false;
        if (categories == null) {
            if (other.categories != null)
                return false;
        } else if (!categories.equals(other.categories))
            return false;
        if (dayPart == null) {
            if (other.dayPart != null)
                return false;
        } else if (!dayPart.equals(other.dayPart))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (entitlement == null) {
            if (other.entitlement != null)
                return false;
        } else if (!entitlement.equals(other.entitlement))
            return false;
        if (episodeNumber == null) {
            if (other.episodeNumber != null)
                return false;
        } else if (!episodeNumber.equals(other.episodeNumber))
            return false;
        if (fullEpisode == null) {
            if (other.fullEpisode != null)
                return false;
        } else if (!fullEpisode.equals(other.fullEpisode))
            return false;
        if (guid == null) {
            if (other.guid != null)
                return false;
        } else if (!guid.equals(other.guid))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else {
            String firstID = id.split("/")[id.split("/").length - 1];
            String secondID = other.id.split("/")[other.id.split("/").length - 1];
            if (!firstID.equals(secondID))
                return false;
        }
        if (seasonNumber == null) {
            if (other.seasonNumber != null)
                return false;
        } else if (!seasonNumber.equals(other.seasonNumber))
            return false;
        if (seriesType == null) {
            if (other.seriesType != null)
                return false;
        } else if (!seriesType.equals(other.seriesType))
            return false;
        if (shortDescription == null) {
            if (other.shortDescription != null)
                return false;
        } else if (!shortDescription.equals(other.shortDescription))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (episodeTmsId == null) {
            if (other.episodeTmsId != null)
                return false;
        } else if (!episodeTmsId.equals(other.episodeTmsId))
            return false;
        if (seriesTmsId == null) {
            if (other.seriesTmsId != null)
                return false;
        } else if (!seriesTmsId.equals(other.seriesTmsId))
            return false;
        if (event == null) {
            if (other.event != null)
                return false;
        } else if (!event.equals(other.event))
            return false;
        return true;
    }

    public class InnerFields {
        private int duration;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

    }

}
