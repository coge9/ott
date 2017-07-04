package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.event;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;

import java.util.*;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class EventJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String itemType;
    private int revision;
    private String title;
    private String slug;
    private String subhead;
    private String shortDescription;
    private String mediumDescription;
    private String longDescription;
    private List<MediaJson> media;
    private List<String> categories;
    private List<String> tags;
    private boolean published;
    private List<ExternalLinksJson> links;
    private List<String> genre;
    private String contentRating;
    private boolean unscripted;
    private boolean syndicated;
    private String airTimes;
    private boolean channelOriginal;
    private String eventType;
    private String programStatus;
    private int releaseYear;
    private List<String> relatedSeries;
    private String castCredit;
    private String collection;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getMediumDescription() {
        return mediumDescription;
    }

    public void setMediumDescription(String mediumDescription) {
        this.mediumDescription = mediumDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<MediaJson> getMedia() {
        return media;
    }

    public void setMedia(List<MediaJson> media) {
        this.media = media;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<ExternalLinksJson> getLinks() {
        return links;
    }

    public void setLinks(List<ExternalLinksJson> links) {
        this.links = links;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public boolean isUnscripted() {
        return unscripted;
    }

    public void setUnscripted(boolean unscripted) {
        this.unscripted = unscripted;
    }

    public boolean isSyndicated() {
        return syndicated;
    }

    public void setSyndicated(boolean syndicated) {
        this.syndicated = syndicated;
    }

    public String getAirTimes() {
        return airTimes;
    }

    public void setAirTimes(String airTimes) {
        this.airTimes = airTimes;
    }

    public boolean isChannelOriginal() {
        return channelOriginal;
    }

    public void setChannelOriginal(boolean channelOriginal) {
        this.channelOriginal = channelOriginal;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getRelatedSeries() {
        return relatedSeries;
    }

    public void setRelatedSeries(List<String> relatedSeries) {
        this.relatedSeries = relatedSeries;
    }

    public String getCastCredit() {
        return castCredit;
    }

    public void setCastCredit(String castCredit) {
        this.castCredit = castCredit;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getPromoKicker() {
        return promoKicker;
    }

    public void setPromoKicker(String promoKicker) {
        this.promoKicker = promoKicker;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public EventJson getObject(Event event) {
        this.uuid = event.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.EVENT.getItemType();
        this.revision = event.getGeneralInfo().getRevision();
        this.title = event.getTitle();
        this.slug = event.getSlugInfo().getSlugValue();
        this.subhead = event.getGeneralInfo().getSubhead();
        this.mediumDescription = event.getGeneralInfo().getMediumDescription();
        this.shortDescription = event.getGeneralInfo().getShortDescription();
        this.longDescription = String.format("<p>%s</p>", event.getGeneralInfo().getLongDescription());
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : event.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.media = medias;
        this.categories = event.getAssociations().getCategories();
        this.tags = new ArrayList<>();
        this.tags.add("");
        this.published = true;
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        for (ExternalLinksInfo info : event.getExternalLinksInfo()) {
            ExternalLinksJson externalLinksJson = new ExternalLinksJson();
            externalLinksJson.setObject(info);
            externalLinksJsons.add(externalLinksJson);
        }
        this.links = externalLinksJsons;
        this.genre = Arrays.asList(event.getEventInfo().getGenre().getValue());
        this.contentRating = event.getEventInfo().getRating().getValue() != null ? event.getEventInfo().getRating().getValue() : "";
        this.unscripted = event.getEventInfo().getUnscripted();
        this.syndicated = event.getEventInfo().getSyndicated();
        this.channelOriginal = event.getEventInfo().getChanelOriginal() != null ? event.getEventInfo().getChanelOriginal() : false;
        Date airDateInNY = DateUtil.getDateInCertainTimeZone(TimeZone.getTimeZone("America/New_York"), event.getEventInfo().getAirTime());
        this.airTimes = String.valueOf(airDateInNY.getTime());
        this.programStatus = event.getEventInfo().getStatus().getValue() != null ? event.getEventInfo().getStatus().getValue() : "";
        this.releaseYear = event.getEventInfo().getReleaseYear() != null ?
                Integer.parseInt(DateUtil.getDate(event.getEventInfo().getReleaseYear(), "yyyy")) :
                Integer.parseInt(DateUtil.getDate(new Date(), "yyyy"));
        this.eventType = event.getEventInfo().getEventType().getValue() != null ?
                event.getEventInfo().getEventType().getValue().toLowerCase() : "";
        this.relatedSeries = event.getEventInfo().getRelatedSeriesUuid() != null ?
                Arrays.asList(event.getEventInfo().getRelatedSeriesUuid()) : new ArrayList<>();
        this.castCredit = "";
        this.collection = "";
        this.promoKicker = event.getPromotional().getPromotionalKicker() != null ? event.getPromotional().getPromotionalKicker() : "";
        this.promoDescription = event.getPromotional().getPromotionalDescription() != null ? event.getPromotional().getPromotionalDescription() : "";
        this.promoTitle = event.getPromotional().getPromotionalTitle() != null ? event.getPromotional().getPromotionalTitle() : "";
        return this;
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
