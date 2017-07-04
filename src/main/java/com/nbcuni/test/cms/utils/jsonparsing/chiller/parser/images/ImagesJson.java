package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/12/2016.
 */
public class ImagesJson extends AbstractEntity implements Cloneable {
    @SerializedName("uuid")
    private String uuid;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("revision")
    private int revision;

    @SerializedName("title")
    private String title;

    @SerializedName("href")
    private String href;

    @SerializedName("imageStyle")
    private List<ImageStyleJson> imageStyles;

    @SerializedName("alt")
    private String alt;

    @SerializedName("caption")
    private String caption;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("copyright")
    private String copyright;

    @SerializedName("credit")
    private String credit;

    @SerializedName("description")
    private String description;

    @SerializedName("highRes")
    private boolean highRes;

    @SerializedName("source")
    private String source;

    @SerializedName("published")
    private boolean published;

    @SerializedName("programs")
    private List<ChannelReferencesJson> programs;

    public ImagesJson(FilesMetadata filesMetadata) {
        getObject(filesMetadata);
    }

    public ImagesJson() {
    }

    public String getUuid() {
        return uuid;
    }

    public ImagesJson setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public ImagesJson setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public int getRevision() {
        return revision;
    }

    public ImagesJson setRevision(int revision) {
        this.revision = revision;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ImagesJson setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ImagesJson setHref(String href) {
        this.href = href;
        return this;
    }

    public String getAlt() {
        return alt;
    }

    public ImagesJson setAlt(String alt) {
        this.alt = alt;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public ImagesJson setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public ImagesJson setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public ImagesJson setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getCopyright() {
        return copyright;
    }

    public ImagesJson setCopyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHighRes() {
        return highRes;
    }

    public void setHighRes(boolean highRes) {
        this.highRes = highRes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isPublished() {
        return published;
    }

    public ImagesJson setPublished(boolean published) {
        this.published = published;
        return this;
    }

    public List<ImageStyleJson> getImageStyles() {
        return imageStyles;
    }

    public ImagesJson setImageStyles(List<ImageStyleJson> imageStyles) {
        this.imageStyles = imageStyles;
        return this;
    }

    public List<ChannelReferencesJson> getPrograms() {
        return programs;
    }

    public ImagesJson setPrograms(List<ChannelReferencesJson> programs) {
        this.programs = programs;
        return this;
    }

    private void getObject(FilesMetadata filesMetadata) {
        this.title = filesMetadata.getImageGeneralInfo().getTitle();
        this.alt = filesMetadata.getImageGeneralInfo().getAltText();
        this.source = filesMetadata.getImageGeneralInfo().getSource();
        this.credit = filesMetadata.getImageGeneralInfo().getCredit();
        this.copyright = filesMetadata.getImageGeneralInfo().getCopyright();
        this.description = filesMetadata.getImageGeneralInfo().getDescription();
        this.caption = filesMetadata.getImageGeneralInfo().getCaption();
        this.published = true;
        this.itemType = filesMetadata.getImageGeneralInfo().getItemType();
        this.href = filesMetadata.getImageGeneralInfo().getImageHref();
        this.uuid = filesMetadata.getImageGeneralInfo().getUuid();
        this.revision = filesMetadata.getImageGeneralInfo().getRevision();
        this.highRes = filesMetadata.getImageGeneralInfo().getHighResolution();
        this.categories = filesMetadata.getAssociations().getCategories();
        //convert Tags as List<Tag> to Tags as List<String>
        this.tags = new ArrayList<>();
        List<Tag> tags = filesMetadata.getAssociations().getTags();
        if (CollectionUtils.isNotEmpty(tags)) {
            for (Tag tag : tags) {
                this.tags.add(tag.getTag());
            }
        }
        this.programs = new ArrayList<>();
        List<MediaImage> imageStyleJsons = filesMetadata.getImageGeneralInfo().getImageStyles();
        if (imageStyleJsons != null) {
            imageStyles = new ArrayList<>();
            for (MediaImage mediaImage : imageStyleJsons) {
                ImageStyleJson imageStyleJson = new ImageStyleJson(mediaImage);
                imageStyles.add(imageStyleJson);
            }
        }
    }

    public MediaJson getMediaJsonForSeries() {
        MediaJson media = new MediaJson();
        media.setUuid(this.uuid);
        media.setItemType(this.itemType);
        media.setUsage(ProgramConcertoImageSourceType.getUsageBySourceType(this.title));
        return media;
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
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("itemType", itemType)
                .add("revision", revision)
                .add("title", title)
                .add("href", href)
                .add("imageStyles", imageStyles)
                .add("alt", alt)
                .add("caption", caption)
                .add("categories", categories)
                .add("tags", tags)
                .add("copyright", copyright)
                .add("credit", credit)
                .add("description", description)
                .add("highRes", highRes)
                .add("source", source)
                .add("published", published)
                .add("programs", programs)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagesJson that = (ImagesJson) o;
        return revision == that.revision &&
                highRes == that.highRes &&
                published == that.published &&
                Objects.equal(uuid, that.uuid) &&
                Objects.equal(itemType, that.itemType) &&
                Objects.equal(title, that.title) &&
                Objects.equal(href, that.href) &&
                Objects.equal(imageStyles, that.imageStyles) &&
                Objects.equal(alt, that.alt) &&
                Objects.equal(caption, that.caption) &&
                Objects.equal(categories, that.categories) &&
                Objects.equal(tags, that.tags) &&
                Objects.equal(copyright, that.copyright) &&
                Objects.equal(credit, that.credit) &&
                Objects.equal(description, that.description) &&
                Objects.equal(source, that.source) &&
                Objects.equal(programs, that.programs);
    }

    @Override

    public int hashCode() {
        return Objects.hashCode(uuid, itemType, revision, title, href, imageStyles,
                alt, caption, categories, tags, copyright,
                credit, description, highRes, source, published,
                programs);
    }
}
