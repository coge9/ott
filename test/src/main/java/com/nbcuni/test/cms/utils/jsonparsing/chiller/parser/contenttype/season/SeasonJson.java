package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.PromoMedia;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 24-May-16.
 */
public class SeasonJson extends AbstractEntity implements Cloneable {

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
    private int productionNumber;
    private int seasonNumber;
    private long seasonStartDate;
    private long seasonEndDate;
    private Program program;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;
    private PromoMedia promoMedia;


    public SeasonJson(Season season) {
        getObject(season);
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

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int productionNumber) {
        this.productionNumber = productionNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public long getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonStartDate(long seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public long getSeasonEndDate() {
        return seasonEndDate;
    }

    public void setSeasonEndDate(long seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
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

    public PromoMedia getPromoMedia() {
        return promoMedia;
    }

    public void setPromoMedia(PromoMedia promoMedia) {
        this.promoMedia = promoMedia;
    }

    private SeasonJson getObject(Season season) {
        this.uuid = season.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.SEASON.getItemType();
        this.revision = season.getGeneralInfo().getRevision();
        this.title = season.getTitle();
        this.slug = season.getSlugInfo().getSlugValue();
        this.subhead = season.getGeneralInfo().getSubhead();
        this.mediumDescription = season.getGeneralInfo().getMediumDescription();
        this.shortDescription = season.getGeneralInfo().getShortDescription();
        this.longDescription = season.getGeneralInfo().getLongDescription();
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : season.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.media = medias;
        this.categories = season.getAssociations().getCategories();
        this.tags = new ArrayList<>();
        this.tags.add("");
        this.published = true;
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(season.getExternalLinksInfo())) {
            for (ExternalLinksInfo info : season.getExternalLinksInfo()) {
                ExternalLinksJson externalLinksJson = new ExternalLinksJson();
                externalLinksJson.setObject(info);
                externalLinksJsons.add(externalLinksJson);
            }
        }
        this.links = externalLinksJsons;

        this.productionNumber = season.getSeasonInfo().getProductionNumber();

        this.seasonNumber = season.getSeasonInfo().getSeasonNumber();
        long startDate = DateUtil.parseStringToDateInCertainTimeZone(
                DateUtil.getStringRepresentationForCertainTimeZone(season.getSeasonInfo().getStartDate(), GlobalConstants.DATE_FORMAT, GlobalConstants.NY_ZONE_NAME),
                GlobalConstants.DATE_FORMAT, GlobalConstants.NY_ZONE_NAME).getTime();
        this.seasonStartDate = startDate;

        long endDate = DateUtil.parseStringToDateInCertainTimeZone(
                DateUtil.getStringRepresentationForCertainTimeZone(season.getSeasonInfo().getEndDate(), GlobalConstants.DATE_FORMAT, GlobalConstants.NY_ZONE_NAME),
                GlobalConstants.DATE_FORMAT, GlobalConstants.NY_ZONE_NAME).getTime();
        this.seasonEndDate = endDate;

        this.program = new Program();
        this.program.setProgramUuid(season.getSeasonInfo().getProgram());
        this.program.setProgramItemType("series");

        this.promoKicker = season.getPromotional().getPromotionalKicker() != null ? season.getPromotional().getPromotionalKicker() : "";
        this.promoDescription = season.getPromotional().getPromotionalDescription() != null ? season.getPromotional().getPromotionalDescription() : "";
        this.promoTitle = season.getPromotional().getPromotionalTitle() != null ? season.getPromotional().getPromotionalTitle() : "";
        this.promoMedia = null;

        return this;
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
