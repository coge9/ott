package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 07-Jun-16.
 */
public class ImageGeneralInfo {

    private String title = null;
    private String altText = null;
    private String source = null;
    private String credit = null;
    private String copyright = null;
    private String description = null;
    private String caption = null;
    private String uuid = null;
    private int revision;
    private boolean published = false;
    private String itemType = ItemTypes.IMAGE.getItemType();
    private String imageHref;
    private List<? extends ThumbnailsCutInterface> chillerThumbnailsList;
    private ProgramConcertoImageSourceType imageSourceType;
    private List<MediaImage> imageStyles;

    private Boolean highResolution = null;

    public String getTitle() {
        return title;
    }

    public ImageGeneralInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAltText() {
        return altText;
    }

    public ImageGeneralInfo setAltText(String altText) {
        this.altText = altText;
        return this;
    }

    public String getSource() {
        return source;
    }

    public ImageGeneralInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public String getCredit() {
        return credit;
    }

    public ImageGeneralInfo setCredit(String credit) {
        this.credit = credit;
        return this;
    }

    public String getCopyright() {
        return copyright;
    }

    public ImageGeneralInfo setCopyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ImageGeneralInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public ImageGeneralInfo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Boolean getHighResolution() {
        return highResolution;
    }

    public ImageGeneralInfo setHighResolution(Boolean highResolution) {
        this.highResolution = highResolution;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public ImageGeneralInfo setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public int getRevision() {
        return revision;
    }

    public ImageGeneralInfo setRevision(int revision) {
        this.revision = revision;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public ImageGeneralInfo setPublished(boolean published) {
        this.published = published;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public ImageGeneralInfo setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public String getImageHref() {
        return imageHref;
    }

    public ImageGeneralInfo setImageHref(String imageHref) {
        this.imageHref = imageHref;
        return this;
    }

    public ProgramConcertoImageSourceType getImageSourceType() {
        return imageSourceType;
    }

    public ImageGeneralInfo setImageSourceType(ProgramConcertoImageSourceType imageSourceType) {
        this.imageSourceType = imageSourceType;
        return this;
    }

    public List<? extends ThumbnailsCutInterface> getChillerThumbnailsList() {
        return chillerThumbnailsList;
    }

    public ImageGeneralInfo setChillerThumbnailsList(List<? extends ThumbnailsCutInterface> chillerThumbnailsList) {
        this.chillerThumbnailsList = chillerThumbnailsList;
        return this;
    }

    public List<MediaImage> getImageStyles() {
        return imageStyles;
    }

    public ImageGeneralInfo setImageStyles(List<MediaImage> imageStyles) {
        this.imageStyles = imageStyles;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("altText", altText)
                .add("source", source)
                .add("credit", credit)
                .add("copyright", copyright)
                .add("description", description)
                .add("caption", caption)
                .add("uuid", uuid)
                .add("revision", revision)
                .add("published", published)
                .add("itemType", itemType)
                .add("imageHref", imageHref)
                .add("chillerThumbnailsList", chillerThumbnailsList)
                .add("highResolution", highResolution)
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

        ImageGeneralInfo that = (ImageGeneralInfo) o;

        return Objects.equal(this.title, that.title) &&
                Objects.equal(this.altText, that.altText) &&
                Objects.equal(this.source, that.source) &&
                Objects.equal(this.credit, that.credit) &&
                Objects.equal(this.copyright, that.copyright) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.caption, that.caption) &&
                Objects.equal(this.uuid, that.uuid) &&
                Objects.equal(this.revision, that.revision) &&
                Objects.equal(this.published, that.published) &&
                Objects.equal(this.itemType, that.itemType) &&
                Objects.equal(this.imageHref, that.imageHref) &&
                Objects.equal(this.chillerThumbnailsList, that.chillerThumbnailsList) &&
                Objects.equal(this.highResolution, that.highResolution);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, altText, source, credit, copyright, description,
                caption, uuid, revision, published, itemType,
                imageHref, chillerThumbnailsList, highResolution);
    }
}
