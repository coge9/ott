package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.webdriver.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/31/16.
 */
public class MediaGalleryJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String itemType;
    private int revision;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;
    private String title;
    private String shortDescription;
    private String mediumDescription;
    private String longDescription;
    private List<String> categories;
    private List<String> tags;
    private String coverImage;
    private List<MediaGalleryItemsJson> mediaGalleryItems;
    private ChannelReferencesJson program;
    private boolean published;
    private String slug;
    private String subhead;
    private Long datePublished;

    public MediaGalleryJson(MediaGallery mediaGallery) {
        this.uuid = mediaGallery.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.MEDIA_GALLERY.getItemType();
        this.title = mediaGallery.getTitle();
        this.revision = mediaGallery.getGeneralInfo().getRevision();
        this.promoKicker = mediaGallery.getPromotional().getPromotionalKicker();
        this.promoDescription = mediaGallery.getPromotional().getPromotionalDescription();
        this.promoTitle = mediaGallery.getPromotional().getPromotionalTitle();
        this.shortDescription = mediaGallery.getGeneralInfo().getShortDescription();
        this.mediumDescription = mediaGallery.getGeneralInfo().getMediumDescription();
        this.longDescription = mediaGallery.getGeneralInfo().getLongDescription();
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.coverImage = mediaGallery.getCoverMediaUUId();
        List<MediaGalleryItemsJson> medias = new ArrayList<>();
        for (MediaImage image : mediaGallery.getMediaImages()) {
            MediaGalleryItemsJson mediaJson = new MediaGalleryItemsJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.mediaGalleryItems = medias;
        this.published = true;
        this.program = new ChannelReferencesJson().getObject(mediaGallery.getAssociations().getChannelReferenceAssociations().getChannelReference());
        this.slug = mediaGallery.getSlugInfo().getSlugValue();
        this.subhead = mediaGallery.getGeneralInfo().getSubhead();
        this.coverImage = mediaGallery.getCoverMediaUUId();
        this.datePublished = Calendar.getInstance().getTime().getTime();
    }

    public Long getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Long datePublished) {
        this.datePublished = datePublished;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<MediaGalleryItemsJson> getMediaGalleryItems() {
        return mediaGalleryItems;
    }

    public void setMediaGalleryItems(List<MediaGalleryItemsJson> mediaGalleryItems) {
        this.mediaGalleryItems = mediaGalleryItems;
    }

    public ChannelReferencesJson getProgram() {
        return program;
    }

    public void setProgram(ChannelReferencesJson program) {
        this.program = program;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
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
