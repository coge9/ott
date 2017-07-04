package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.episode;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Ivan_Karnilau on 16-Jun-16.
 */
public class EpisodeJson extends AbstractEntity implements Cloneable {

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
    private String contentRating;
    private int episodeNumber;
    private String episodeType;
    private Long datePublished;
    private int secondaryEpisodeNumber;
    private long supplementaryAiring;
    private int productionNumber;
    private ChannelReferencesJson program;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;

    public EpisodeJson(Episode episode) {
        getObject(episode);
    }

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

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeType() {
        return episodeType;
    }

    public void setEpisodeType(String episodeType) {
        this.episodeType = episodeType;
    }

    public Long getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Long datePublished) {
        this.datePublished = datePublished;
    }

    public int getSecondaryEpisodeNumber() {
        return secondaryEpisodeNumber;
    }

    public void setSecondaryEpisodeNumber(int secondaryEpisodeNumber) {
        this.secondaryEpisodeNumber = secondaryEpisodeNumber;
    }

    public long getSupplementaryAiring() {
        return supplementaryAiring;
    }

    public void setSupplementaryAiring(long supplementaryAiring) {
        this.supplementaryAiring = supplementaryAiring;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int productionNumber) {
        this.productionNumber = productionNumber;
    }

    public ChannelReferencesJson getProgram() {
        return program;
    }

    public void setProgram(ChannelReferencesJson program) {
        this.program = program;
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

    private EpisodeJson getObject(Episode episode) {
        this.uuid = episode.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.EPISODE.getItemType();
        this.revision = episode.getGeneralInfo().getRevision();
        this.title = episode.getTitle();
        this.slug = episode.getSlugInfo().getSlugValue();
        this.subhead = episode.getGeneralInfo().getSubhead();
        this.mediumDescription = episode.getGeneralInfo().getMediumDescription() != null ? episode.getGeneralInfo().getMediumDescription() : "";
        this.shortDescription = episode.getGeneralInfo().getShortDescription() != null ? episode.getGeneralInfo().getShortDescription() : "";
        if (episode.getGeneralInfo().getLongDescription() != null && !episode.getGeneralInfo().getLongDescription().isEmpty()) {
            this.longDescription = String.format("<p>%s</p>", episode.getGeneralInfo().getLongDescription()).trim();
        } else {
            this.longDescription = "";
        }
        getMedia(episode);
        this.categories = episode.getAssociations().getCategories();
        this.tags = new ArrayList<>();
        this.tags.add("");
        this.published = true;
        getExternalLinks(episode);
        this.contentRating = episode.getEpisodeInfo().getRating().getValue();
        this.episodeNumber = episode.getEpisodeInfo().getEpisodeNumber();
        this.episodeType = episode.getEpisodeInfo().getEpisodeType().getValue();
        this.episodeType = "TV Episode";
        this.secondaryEpisodeNumber = episode.getEpisodeInfo().getSecondaryEpisodeNumber();
        this.supplementaryAiring = DateUtil.getDateInCertainTimeZone(TimeZone.getTimeZone(GlobalConstants.NY_ZONE)
                , episode.getEpisodeInfo().getSupplementaryAirDate(), "MM/dd/yyyy HH:mm").getTime();
        this.productionNumber = episode.getEpisodeInfo().getProductionNumber();

        this.program = new ChannelReferencesJson();
        this.program.setProgramUuid(episode.getEpisodeInfo().getParentSeries().getGeneralInfo().getUuid());
        this.program.setProgramItemType("series");
        this.program.setSeasonUuid(episode.getEpisodeInfo().getParentSeason().getGeneralInfo().getUuid());

        this.promoKicker = episode.getPromotional().getPromotionalKicker() != null ? episode.getPromotional().getPromotionalKicker() : "";
        this.promoDescription = episode.getPromotional().getPromotionalDescription() != null ? episode.getPromotional().getPromotionalDescription() : "";
        this.promoTitle = episode.getPromotional().getPromotionalTitle() != null ? episode.getPromotional().getPromotionalTitle() : "";
        if (episode.getPublishedDate() != null) {
            this.datePublished = episode.getPublishedDate() * 1000L;
        }
        return this;
    }

    private void getExternalLinks(Episode episode) {
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(episode.getExternalLinksInfo())) {
            for (ExternalLinksInfo info : episode.getExternalLinksInfo()) {
                ExternalLinksJson externalLinksJson = new ExternalLinksJson();
                externalLinksJson.setObject(info);
                externalLinksJsons.add(externalLinksJson);
            }
        }
        this.links = externalLinksJsons;
    }

    private void getMedia(Episode episode) {
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : episode.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.media = medias;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Utilities.logSevereMessageThenFail("Error during clone operation: " + Utilities.convertStackTraceToString(e));
        }
        return null;
    }
}
