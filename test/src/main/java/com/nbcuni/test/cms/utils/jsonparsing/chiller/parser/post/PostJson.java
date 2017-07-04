package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.post;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.webdriver.Utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PostJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String itemType;
    private int revision;
    private String title;
    private String slug;
    private String byLine;
    private String shortDescription;
    private String mediumDescription;
    private String longDescription;
    private List<MediaJson> media;
    private String promoTitle;
    private String promoKicker;
    private String promoDescription;
    private List<String> tags;
    private List<String> categories;
    private List<String> author;
    @SerializedName("program")
    private ChannelReferencesJson channelReference;
    private List<Blurb> blurb;
    private boolean published;
    private Long availableDate;
    private Long datePublished;

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

    public String getByLine() {
        return byLine;
    }

    public void setByLine(String byLine) {
        this.byLine = byLine;
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

    public List<Blurb> getBlurb() {
        return blurb;
    }

    public void setBlurb(List<Blurb> blurb) {
        this.blurb = blurb;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getPromoKicker() {
        return promoKicker;
    }

    public void setPromoKicker(String promoKicker) {
        this.promoKicker = promoKicker;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public ChannelReferencesJson getChannelReference() {
        return channelReference;
    }

    public void setChannelReference(ChannelReferencesJson channelReference) {
        this.channelReference = channelReference;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Long getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Long availableDate) {
        this.availableDate = availableDate;
    }

    public Long getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Long datePublished) {
        this.datePublished = datePublished;
    }

    public PostJson getObject(Post post) {
        this.itemType = "post";
        this.uuid = post.getGeneralInfo().getUuid();
        this.title = post.getGeneralInfo().getTitle() == null ? "" : post.getGeneralInfo().getTitle();
        this.slug = post.getSlugInfo().getSlugValue();
        this.revision = post.getGeneralInfo().getRevision();
        this.byLine = post.getGeneralInfo().getByline() == null ? "" : post.getGeneralInfo().getByline();
        this.shortDescription = post.getGeneralInfo().getShortDescription() == null ? ""
                : post.getGeneralInfo().getShortDescription();
        this.mediumDescription = post.getPostInfo().getMediumDescription().getBlurb().getText() == null ? ""
                : post.getPostInfo().getMediumDescription().getBlurb().getText();
        this.longDescription = post.getPostInfo().getLongDescription().getBlurb().getText() == null ? ""
                : post.getPostInfo().getLongDescription().getBlurb().getText();

        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : post.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.media = medias;
        this.blurb = post.getPostInfo() == null ? new ArrayList<>() : post.getPostInfo().getBlurbs();
        this.promoTitle = post.getPromotional().getPromotionalTitle() == null ? ""
                : post.getPromotional().getPromotionalTitle();
        this.promoKicker = post.getPromotional().getPromotionalKicker() == null ? ""
                : post.getPromotional().getPromotionalKicker();
        this.promoDescription = post.getPromotional().getPromotionalDescription() == null ? ""
                : post.getPromotional().getPromotionalDescription();
        this.author = new ArrayList<>();
        if (post.getAssociations() != null) {
            ChannelReference tmpRef = post.getAssociations().getChannelReferenceAssociations().getChannelReference();
            this.channelReference = new ChannelReferencesJson();
            this.channelReference.setProgramUuid(tmpRef.getSeries());
            this.channelReference.setSeasonUuid(tmpRef.getSeason());
            this.channelReference.setEpisodeUuid(tmpRef.getEpisode());

            this.tags = new ArrayList<>();
            for (Tag tag : post.getAssociations().getTags()) {
                this.tags.add(tag.getTagUuid());
            }

            this.categories = new ArrayList<>();
            for (String cat : post.getAssociations().getCategories()) {
                this.categories.add(Category.get(cat).getUuid());
            }
        }
        this.published = true;
        DateTimeFormatter.ofPattern(post.getGeneralInfo().getDateLine());
        LocalDate localDate = LocalDate.parse(post.getGeneralInfo().getDateLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, LocalTime.of(0, 0), ZoneId.of("America/New_York"));

        this.availableDate = zonedDateTime.toInstant().toEpochMilli();
        this.datePublished = post.getPublishedDate() * 1000L;
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
