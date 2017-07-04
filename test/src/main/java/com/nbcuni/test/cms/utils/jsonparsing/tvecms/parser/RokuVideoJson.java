package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.*;

public class RokuVideoJson implements Metadata, Cloneable {

    @SerializedName("itemType")
    protected String itemType;

    @SerializedName("title")
    protected String title;

    @SerializedName("id")
    protected String id;

    @SerializedName("mpxId")
    protected String mpxId;

    @SerializedName("description")
    protected String description;

    @SerializedName("showId")
    private String showId;

    @SerializedName("showName")
    private String showName;

    @SerializedName("seriesCategory")
    private String seriesCategory;

    @SerializedName("seriesType")
    private String seriesType;

    @SerializedName("publishState")
    private Boolean publishState;

    @SerializedName("daypart")
    private String dayPart;

    @SerializedName("images")
    private List<Image> images = new ArrayList<>();

    @SerializedName("dateModified")
    private String dateModified;

    @SerializedName("fullEpisode")
    private Boolean fullEpisode;

    @SerializedName("seasonNumber")
    private String seasonNumber;

    @SerializedName("episodeNumber")
    private String episodeNumber;

    @SerializedName("expirationDate")
    private String expirationDate;

    @SerializedName("airDate")
    private String airDate;

    @SerializedName("availableDate")
    private String availableDate;

    @SerializedName("duration")
    private Integer duration;

    @SerializedName("externalAdvertiserId")
    private String externalAdvertiserId;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("entitlement")
    private String entitlement;

    @SerializedName("closedCaptionFlag")
    private Boolean closedCaptionFlag;

    @SerializedName("ratings")
    private List<RokuRatingJson> ratings;

    @SerializedName("mediaCategories")
    private List<String> mediaCategories;


    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeriesCategories() {
        return seriesCategory;
    }

    public void setSeriesCategories(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getDayPart() {
        return dayPart;
    }

    public void setDayPart(String dayPart) {
        this.dayPart = dayPart;
    }

    @Override
    public String getMpxId() {
        return mpxId;
    }

    public void setMpxId(String mpxId) {
        this.mpxId = mpxId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getFullEpisode() {
        return fullEpisode;
    }

    public void setFullEpisode(Boolean fullEpisode) {
        this.fullEpisode = fullEpisode;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getExternalAdvertiserId() {
        return externalAdvertiserId;
    }

    public void setExternalAdvertiserId(String externalAdvertiserId) {
        this.externalAdvertiserId = externalAdvertiserId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public Boolean getClosedCaptionFlag() {
        return closedCaptionFlag;
    }

    public void setClosedCaptionFlag(Boolean closedCaptionFlag) {
        this.closedCaptionFlag = closedCaptionFlag;
    }

    public List<RokuRatingJson> getRatings() {
        return ratings;
    }

    public void setRatings(List<RokuRatingJson> ratings) {
        this.ratings = ratings;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getSeriesCategory() {
        return seriesCategory;
    }

    public void setSeriesCategory(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }

    public Boolean getPublishState() {
        return publishState;
    }

    public void setPublishState(Boolean publishState) {
        this.publishState = publishState;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return itemType;
    }

    public void setType(String type) {
        this.itemType = type;
    }

    public List<String> getMediaCategories() {
        return mediaCategories;
    }

    public void setMediaCategories(List<String> mediaCategories) {
        this.mediaCategories = mediaCategories;
    }

    public boolean verify(RokuVideoJson other) {
        Boolean status = true;
        Utilities.logInfoMessage("Validation of programs");
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Utilities.logInfoMessage("Validation of the field " + field.getAnnotation(SerializedName.class).value());
            try {
                Object expected = field.get(other);
                Object actual = field.get(this);
                if (expected == null || actual == null) {
                    if (actual != null || expected != null) {
                        Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                        status = false;
                    } else {
                        Utilities.logInfoMessage("OK!");
                    }
                } else if (!actual.equals(expected)) {
                    Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                    status = false;
                } else {
                    Utilities.logInfoMessage("OK!");
                }
            } catch (IllegalArgumentException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            } catch (IllegalAccessException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            }
        }
        if (status) {
            Utilities.logInfoMessage("Validation passed");
        } else {
            Utilities.logSevereMessage("Validation failed");
        }
        return status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((publishState == null) ? 0 : publishState.hashCode());
        result = prime * result
                + ((title == null) ? 0 : title.hashCode());
        result = prime * result
                + ((itemType == null) ? 0 : itemType.hashCode());
        result = prime
                * result
                + ((showName == null) ? 0 : showName
                .hashCode());
        result = prime
                * result
                + ((seriesCategory == null) ? 0
                : seriesCategory.hashCode());
        result = prime * result + ((dayPart == null) ? 0 : dayPart.hashCode());
        result = prime * result + ((seriesType == null) ? 0 : seriesType.hashCode());
        result = prime * result
                + ((mpxId == null) ? 0 : mpxId.hashCode());
        result = prime * result
                + ((fullEpisode == null) ? 0 : fullEpisode.hashCode());

        result = prime * result
                + ((closedCaptionFlag == null) ? 0 : closedCaptionFlag.hashCode());
        result = prime * result
                + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result
                + ((airDate == null) ? 0 : airDate.hashCode());
        result = prime * result
                + ((availableDate == null) ? 0 : availableDate.hashCode());
        result = prime * result
                + ((externalAdvertiserId == null) ? 0 : externalAdvertiserId.hashCode());
        result = prime * result
                + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result
                + ((entitlement == null) ? 0 : entitlement.hashCode());
        result = prime * result
                + ((ratings == null) ? 0 : ratings.hashCode());
        result = prime * result
                + ((mediaCategories == null) ? 0 : mediaCategories.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RokuVideoJson other = (RokuVideoJson) obj;
        if (itemType == null) {
            if (other.itemType != null) {
                Utilities.logSevereMessage("itemType1: [" + itemType + "] itemType2: [" + other.itemType + "]");
                return false;
            }
        } else if (!itemType.equals(other.itemType)) {
            Utilities.logSevereMessage("itemType1: [" + itemType + "] itemType2: [" + other.itemType + "]");
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                Utilities.logSevereMessage("id1: [" + id + "] id2: [" + other.id + "]");
                return false;
            }
        } else if (!id.equals(other.id)) {
            Utilities.logSevereMessage("id1: [" + id + "] id2: [" + other.id + "]");
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                Utilities.logSevereMessage("title1: [" + title + "] title2: [" + other.title + "]");
                return false;
            }
        } else if (!title.equals(other.title)) {
            Utilities.logSevereMessage("title1: [" + title + "] title2: [" + other.title + "]");
            return false;
        }
        if (showId == null) {
            if (other.showId != null) {
                Utilities.logSevereMessage("showId1: [" + showId + "] showId2: [" + other.showId + "]");
                return false;
            }
        } else if (!showId.equals(other.showId)) {
            Utilities.logSevereMessage("showId1: [" + showId + "] showId2: [" + other.showId + "]");
            return false;
        }
        if (showName == null) {
            if (other.showName != null) {
                Utilities.logSevereMessage("showName1: [" + showName + "] showName2: [" + other.showName + "]");
                return false;
            }
        } else if (!showName.equals(other.showName)) {
            Utilities.logSevereMessage("showName1: [" + showName + "] showName2: [" + other.showName + "]");
            return false;
        }
        if (seriesCategory == null) {
            if (other.seriesCategory != null) {
                Utilities.logSevereMessage("seriesCategory1: [" + seriesCategory + "] seriesCategory2: [" + other.seriesCategory + "]");
                return false;
            }
        } else if (!seriesCategory.equals(other.seriesCategory)) {
            Utilities.logSevereMessage("seriesCategory1: [" + seriesCategory + "] seriesCategory2: [" + other.seriesCategory + "]");
            return false;
        }
        if (seriesType == null) {
            if (other.seriesType != null) {
                Utilities.logSevereMessage("seriesType1: [" + seriesType + "] seriesType2: [" + other.seriesType + "]");
                return false;
            }
        } else if (!seriesType.equals(other.seriesType)) {
            Utilities.logSevereMessage("seriesType1: [" + seriesType + "] seriesType2: [" + other.seriesType + "]");
            return false;
        }
        if (publishState == null) {
            if (other.publishState != null) {
                Utilities.logSevereMessage("publishState1: [" + publishState + "] publishState2: [" + other.publishState + "]");
                return false;
            }
        } else if (!publishState.equals(other.publishState)) {
            Utilities.logSevereMessage("publishState1: [" + publishState + "] publishState2: [" + other.publishState + "]");
            return false;
        }
        if (images == null) {
            if (other.images != null) {
                Utilities.logSevereMessage("images1: [" + images + "] images2: [" + other.images + "]");
                return false;
            }
        } else if (other.images.size() > images.size()) {
            return new SoftAssert().assertContainsAll(other.images, images, "Not all images are published").isNotAnyError();
        } else {
            return new SoftAssert().assertContainsAll(images, other.images, "Not all images are published").isNotAnyError();
        }
        if (mpxId == null) {
            if (other.mpxId != null) {
                Utilities.logSevereMessage("mpxId1: [" + mpxId + "] mpxId2: [" + other.mpxId + "]");
                return false;
            }
        } else if (!mpxId.equals(other.mpxId)) {
            Utilities.logSevereMessage("mpxId1: [" + mpxId + "] mpxId2: [" + other.mpxId + "]");
            return false;
        }
        if (fullEpisode == null) {
            if (other.fullEpisode != null) {
                Utilities.logSevereMessage("fullEpisode1: [" + fullEpisode + "] fullEpisode2: [" + other.fullEpisode + "]");
                return false;
            }
        } else if (fullEpisode != other.fullEpisode) {
            Utilities.logSevereMessage("fullEpisode1: [" + fullEpisode + "] fullEpisode2: [" + other.fullEpisode + "]");
            return false;
        }
        if (episodeNumber == null) {
            if (other.episodeNumber != null) {
                Utilities.logSevereMessage("episodeNumber1: [" + episodeNumber + "] episodeNumber2: [" + other.episodeNumber + "]");
                return false;
            }
        } else if (!episodeNumber.equalsIgnoreCase(other.episodeNumber)) {
            Utilities.logSevereMessage("episodeNumber1: [" + episodeNumber + "] episodeNumber2: [" + other.episodeNumber + "]");
            return false;
        }

        if (seasonNumber == null) {
            if (other.seasonNumber != null) {
                Utilities.logSevereMessage("seasonNumber1: [" + seasonNumber + "] seasonNumber2: [" + other.seasonNumber + "]");
                return false;
            }
        } else if (!seasonNumber.equalsIgnoreCase(other.seasonNumber)) {
            Utilities.logSevereMessage("seasonNumber1: [" + seasonNumber + "] seasonNumber2: [" + other.seasonNumber + "]");
            return false;
        }
        if (expirationDate == null) {
            if (other.expirationDate != null) {
                Utilities.logSevereMessage("expirationDate1: [" + expirationDate + "] expirationDate2: [" + other.expirationDate + "]");
                return false;
            }
        } else if (!expirationDate.equalsIgnoreCase(other.expirationDate)) {
            Utilities.logSevereMessage("expirationDate1: [" + expirationDate + "] expirationDate2: [" + other.expirationDate + "]");
            return false;
        }
        if (airDate == null) {
            if (other.airDate != null) {
                Utilities.logSevereMessage("airDate1: [" + airDate + "] airDate2: [" + other.airDate + "]");
                return false;
            }
        } else if (!airDate.equalsIgnoreCase(other.airDate)) {
            Utilities.logSevereMessage("The actual air data: " + other.airDate + " with expected: " + airDate);
            // return false;
        }
        if (availableDate == null) {
            if (other.availableDate != null) {
                return false;
            }
        } else if (!availableDate.equals(other.availableDate)) {
            Utilities.logSevereMessage("The actual air data: " + other.availableDate + " with expected: " + availableDate);
        }
        if (!Objects.equals(duration, other.duration)) {
            Utilities.logSevereMessage("duration1: [" + duration + "] duration2: [" + other.duration + "]");
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                Utilities.logSevereMessage("description1: [" + description + "] description2: [" + other.description + "]");
                return false;
            }
        } else if (!Objects.equals(description, other.description)) {
            Utilities.logSevereMessage("description1: [" + description + "] description2: [" + other.description + "]");
            return false;
        }
        if (dayPart == null) {
            if (other.dayPart != null) {
                Utilities.logSevereMessage("dayPart1: [" + dayPart + "] dayPart2: [" + other.dayPart + "]");
                return false;
            }
        } else if (!dayPart.equals(other.dayPart)) {
            Utilities.logSevereMessage("dayPart1: [" + dayPart + "] dayPart2: [" + other.dayPart + "]");
            return false;
        }
        if (externalAdvertiserId == null) {
            if (other.externalAdvertiserId != null) {
                Utilities.logSevereMessage("externalAdvertiserId1: [" + externalAdvertiserId + "] externalAdvertiserId2: [" + other.externalAdvertiserId + "]");
                return false;
            }
        } else if (!Objects.equals(externalAdvertiserId, other.externalAdvertiserId)) {
            Utilities.logSevereMessage("externalAdvertiserId1: [" + externalAdvertiserId + "] externalAdvertiserId2: [" + other.externalAdvertiserId + "]");
            return false;
        }
        if (keywords == null) {
            if (other.keywords != null) {
                Utilities.logSevereMessage("keywords1: [" + keywords + "] keywords2: [" + other.keywords + "]");
                return false;
            }
        } else if (!Objects.equals(keywords, other.keywords)) {
            Utilities.logSevereMessage("keywords1: [" + keywords + "] keywords2: [" + other.keywords + "]");
            return false;
        }
        if (entitlement == null) {
            if (other.entitlement != null) {
                Utilities.logSevereMessage("entitlement1: [" + entitlement + "] entitlement2: [" + other.entitlement + "]");
                return false;
            }
        } else if (!Objects.equals(entitlement, other.entitlement)) {
            Utilities.logSevereMessage("entitlement1: [" + entitlement + "] entitlement2: [" + other.entitlement + "]");
            return false;
        }
        if (closedCaptionFlag == null) {
            if (other.closedCaptionFlag != null) {
                Utilities.logSevereMessage("closedCaptionFlag1: [" + closedCaptionFlag + "] closedCaptionFlag2: [" + other.closedCaptionFlag + "]");
                return false;
            }
        } else if (closedCaptionFlag != other.closedCaptionFlag) {
            Utilities.logSevereMessage("closedCaptionFlag1: [" + closedCaptionFlag + "] closedCaptionFlag2: [" + other.closedCaptionFlag + "]");
            return false;
        }
        if (ratings == null) {
            if (other.ratings != null) {
                Utilities.logSevereMessage("ratings1: [" + ratings + "] ratings2: [" + other.ratings + "]");
                return false;
            }
        } else if (ratings.size() != other.ratings.size()
                ) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        }
        if (mediaCategories == null) {
            if (other.mediaCategories != null) {
                Utilities.logSevereMessage("mediaCategories1: [" + mediaCategories + "] mediaCategories2: [" + other.mediaCategories + "]");
                return false;
            }
        } else if (!mediaCategories.equals(other.mediaCategories)) {
            Utilities.logSevereMessage("The media categories list are not matched");
            return false;
        }
        return true;
    }


    public Map<String, String> getFieldsByName(String... names) {
        Utilities.logInfoMessage("Get Field by name from list");
        Map<String, String> resultMap = new HashMap<String, String>();
        Random rand = new Random();
        Class c = getClass();
        for (String name : names) {
            try {
                if ("images".equals(name) || "ratings".equals(name)) {
                    @SuppressWarnings("unchecked")
                    List<String> array = (List<String>) c.getDeclaredField(name).get(this);
                    resultMap.put(name, array.get(rand.nextInt(array.size())));
                } else {
                    resultMap.put(name, (String) c.getDeclaredField(name).get(this));
                }
            } catch (NoSuchFieldException e) {
                Utilities.logSevereMessageThenFail("Havent field with this name!!!");
                e.printStackTrace();
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
            }
        }
        return resultMap;
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

    @Override
    public String toString() {
        return "RokuVideoJson{" +
                "showName='" + showName + '\'' +
                ", seriesCategory='" + seriesCategory + '\'' +
                ", seriesType='" + seriesType + '\'' +
                ", dayPart='" + dayPart + '\'' +
                ", fullEpisode=" + fullEpisode +
                ", seasonNumber=" + seasonNumber +
                ", episodeNumber=" + episodeNumber +
                ", expirationDate='" + expirationDate + '\'' +
                ", airDate='" + airDate + '\'' +
                ", availableDate='" + availableDate + '\'' +
                ", duration=" + duration +
                ", externalAdvertiserId='" + externalAdvertiserId + '\'' +
                ", keywords='" + keywords + '\'' +
                ", entitlement='" + entitlement + '\'' +
                ", closedCaptionFlag=" + closedCaptionFlag +
                ", ratings=" + ratings +
                ", mediaCategories=" + mediaCategories +
                ", images= [" + images + "]" +
                '}';
    }
}
