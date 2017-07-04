package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetaDataEntity;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/25/2016.
 */
public class MetadataJson extends AbstractEntity implements Cloneable {
    @SerializedName("uuid")
    private String uuid;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("revision")
    private int revision;

    @SerializedName("prefix")
    private String prefix;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("middleName")
    private String middleName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("suffix")
    private String suffix;

    @SerializedName("bio")
    private String bio;

    @SerializedName("slug")
    private String slug;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("published")
    private boolean published;

    @SerializedName("links")
    private List<ExternalLinksJson> links;

    public MetadataJson() {
    }

    public MetadataJson(MetaDataEntity metadata) {
        getObject(metadata);
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    private MetadataJson getObject(MetaDataEntity metadata) {
        //data
        this.itemType = metadata.getMetadataInfo().getItemType();
        this.uuid = metadata.getGeneralInfo().getUuid();
        this.revision = metadata.getGeneralInfo().getRevision();
        this.published = true;
        //general info
        this.prefix = setValueOrEmptyString(metadata.getMetadataInfo().getPrefix());
        this.firstName = metadata.getMetadataInfo().getFirstName();
        this.middleName = setValueOrEmptyString(metadata.getMetadataInfo().getMiddleName());
        this.lastName = setValueOrEmptyString(metadata.getMetadataInfo().getLastName());
        this.suffix = setValueOrEmptyString(metadata.getMetadataInfo().getSuffix());

        if (metadata.getMetadataInfo().getBio() != null && !metadata.getMetadataInfo().getBio().isEmpty()) {
            this.bio = String.format("<p>%s</p>", metadata.getMetadataInfo().getBio());
        } else {
            this.bio = setValueOrEmptyString(metadata.getMetadataInfo().getBio());
        }

        //links info
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(metadata.getExternalLinksInfo())) {
            for (ExternalLinksInfo info : metadata.getExternalLinksInfo()) {
                ExternalLinksJson externalLinksJson = new ExternalLinksJson();
                externalLinksJson.setObject(info);
                externalLinksJsons.add(externalLinksJson);
            }
        }
        this.links = externalLinksJsons;
        //slug info
        this.slug = metadata.getSlugInfo().getSlugValue();
        //media info
        this.images = new ArrayList<>();
        for (MediaImage mediaImage : metadata.getMediaImages()) {
            images.add(mediaImage.getUuid());
        }
        return this;
    }

    private String setValueOrEmptyString(String valueForSetting) {
        if (valueForSetting != null) {
            return valueForSetting;
        } else return "";
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
                .add("prefix", prefix)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("lastName", lastName)
                .add("suffix", suffix)
                .add("bio", bio)
                .add("slug", slug)
                .add("images", images)
                .add("published", published)
                .add("links", links)
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
        MetadataJson that = (MetadataJson) o;
        return revision == that.revision &&
                published == that.published &&
                Objects.equal(uuid, that.uuid) &&
                Objects.equal(itemType, that.itemType) &&
                Objects.equal(prefix, that.prefix) &&
                Objects.equal(firstName, that.firstName) &&
                Objects.equal(middleName, that.middleName) &&
                Objects.equal(lastName, that.lastName) &&
                Objects.equal(suffix, that.suffix) &&
                Objects.equal(bio, that.bio) &&
                Objects.equal(slug, that.slug) &&
                Objects.equal(images, that.images) &&
                Objects.equal(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, itemType, revision, prefix, firstName, middleName, lastName, suffix, bio, slug, images, published, links);
    }
}

