package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ProgrammingTimeframe;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.MpxMetadata;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class SeriesJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String slug;
    private String itemType;
    private int revision;
    private String title;
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
    private String seriesType;
    private String sortTitle;
    private List<String> relatedSeries;
    private int seriesEpisodeLength;
    private String daypart;
    private String programStatus;
    private String currentSeason;
    private String collection;
    private Long datePublished;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;
    private MpxMetadata mpxMetadata;
    private String showColor;

    public SeriesJson() {
        super();
    }

    public SeriesJson(Series series) {
        getObject(series);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public List<String> getRelatedSeries() {
        return relatedSeries;
    }

    public void setRelatedSeries(List<String> relatedSeries) {
        this.relatedSeries = relatedSeries;
    }

    public int getSeriesEpisodeLength() {
        return seriesEpisodeLength;
    }

    public void setSeriesEpisodeLength(int seriesEpisodeLength) {
        this.seriesEpisodeLength = seriesEpisodeLength;
    }

    public String getDaypart() {
        return daypart;
    }

    public void setDaypart(String daypart) {
        this.daypart = daypart;
    }

    public String getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }

    public String getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Long getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Long datePublished) {
        this.datePublished = datePublished;
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

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTltle) {
        this.sortTitle = sortTltle;
    }

    public MpxMetadata getMpxMetadata() {
        return mpxMetadata;
    }

    public void setMpxMetadata(MpxMetadata mpxMetadata) {
        this.mpxMetadata = mpxMetadata;
    }

    public String getShowColor() {
        return showColor;
    }

    public void setShowColor(String showColor) {
        this.showColor = showColor;
    }

    private void setCommonFields(Series series) {
        this.uuid = series.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.SERIES.getItemType();
        this.revision = series.getGeneralInfo().getRevision();
        this.title = series.getTitle();
        this.slug = series.getSlugInfo().getSlugValue();
        this.subhead = series.getGeneralInfo().getSubhead();
        this.sortTitle = series.getGeneralInfo().getSortTitle();
        setMedia(series);
        this.categories = series.getAssociations().getCategories();
        this.tags = new ArrayList<>();
        this.tags.add("");
        this.published = series.getPublished();
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(series.getExternalLinksInfo())) {
            for (ExternalLinksInfo info : series.getExternalLinksInfo()) {
                ExternalLinksJson externalLinksJson = new ExternalLinksJson();
                externalLinksJson.setObject(info);
                externalLinksJsons.add(externalLinksJson);
            }
        }
        this.links = externalLinksJsons;
        this.genre = series.getSeriesInfo().getGenre() != null ? Arrays.asList(series.getSeriesInfo().getGenre().getValue()) :
                new ArrayList<>();
        this.unscripted = series.getSeriesInfo().getUnscripted() != null ? series.getSeriesInfo().getUnscripted() : false;
        this.syndicated = series.getSeriesInfo().getSyndicated() != null ? series.getSeriesInfo().getSyndicated() : false;


        this.programStatus = series.getSeriesInfo().getStatus() != null ? series.getSeriesInfo().getStatus().getValue() : null;


        this.relatedSeries = series.getSeriesInfo().getRelatedSeries() != null ? Arrays.asList(series.getSeriesInfo().getRelatedSeries()) :
                new ArrayList<>();
        this.collection = "";

        this.currentSeason = series.getAssociations().getSeason() != null ?
                series.getAssociations().getSeason().getGeneralInfo().getUuid() : null;
        this.mpxMetadata = new MpxMetadata().setMpxGuid(null).setTmsId(null);
        if (series.getPublishedDate() != null) {
            this.datePublished = series.getPublishedDate() * 1000L;
        }
    }

    private void setMedia(Series series) {
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : series.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.media = medias;
    }

    protected SeriesJson getObject(Series series) {
        setCommonFields(series);
        setDescription(series);
        this.contentRating = series.getSeriesInfo().getRating() != null ? series.getSeriesInfo().getRating().getValue() : null;
        this.promoKicker = series.getPromotional().getPromotionalKicker() != null ? series.getPromotional().getPromotionalKicker() : null;
        this.promoDescription = series.getPromotional().getPromotionalDescription() != null ? series.getPromotional().getPromotionalDescription() : null;
        this.promoTitle = series.getPromotional().getPromotionalTitle() != null ? series.getPromotional().getPromotionalTitle() : null;
        this.daypart = null;
        if (series.getSeriesInfo().getType() != null) {
            this.seriesType = series.getSeriesInfo().getType().getValue();
            if (seriesType.equals(Type.TV_SERIES.getValue())) {
                this.daypart = series.getSeriesInfo().getProgrammingTimeframe().getValue() != null ? series.getSeriesInfo().getProgrammingTimeframe().getValue() : "";
                this.seriesEpisodeLength = series.getSeriesInfo().getRegularlyScheduledDuration();
            }
        }
        return this;
    }

    private void setDescription(Series series) {
        this.mediumDescription = series.getGeneralInfo().getMediumDescription() != null ?
                series.getGeneralInfo().getMediumDescription() : null;
        this.shortDescription = series.getGeneralInfo().getShortDescription() != null ?
                series.getGeneralInfo().getShortDescription() : null;
        this.longDescription = series.getGeneralInfo().getLongDescription() != null ?
                String.format("<p>%s</p>", series.getGeneralInfo().getLongDescription()) : null;
    }

    /**
     * This method update Series json according to required fields only for Ios series
     *
     * @param series initial Series object
     * @return SeriesJson object
     */
    public SeriesJson updateSeriesJsonForIos(Series series) {
        this.mediumDescription = series.getGeneralInfo().getMediumDescription();
        this.shortDescription = series.getGeneralInfo().getShortDescription();
        this.longDescription = series.getGeneralInfo().getLongDescription();
        this.contentRating = series.getSeriesInfo().getRating() != null ? series.getSeriesInfo().getRating().getValue() : null;

        this.promoKicker = series.getPromotional().getPromotionalKicker();
        this.promoDescription = series.getPromotional().getPromotionalDescription();
        this.promoTitle = series.getPromotional().getPromotionalTitle();
        this.sortTitle = series.getGeneralInfo().getSortTitle();
        ProgrammingTimeframe dayPart = series.getSeriesInfo().getProgrammingTimeframe();
        if (dayPart != null) {
            this.daypart = series.getSeriesInfo().getProgrammingTimeframe().getValue();
        } else {
            this.daypart = null;
        }
        if (series.getSeriesInfo().getType() != null) {
            this.seriesType = series.getSeriesInfo().getType().getValue();
        } else {
            this.seriesType = null;
        }
        this.seriesEpisodeLength = series.getSeriesInfo().getRegularlyScheduledDuration();

        setMpxMetadata(new MpxMetadata()
                .setMpxGuid(series.getSeriesInfo().getGuid())
                .setTmsId(series.getSeriesInfo().getTmsId().isEmpty() ? null : series.getSeriesInfo().getTmsId()));
        this.setCollection(null);
        this.setPromoDescription(null);
        this.setContentRating(null);
        this.tags = new ArrayList<>();
        if (series.getPublishedDate() != null) {
            this.datePublished = series.getPublishedDate() * 1000L;
        }
        return this;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return null;
    }
}
