package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekca on 27.05.2016.
 */
public class VideoJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String slug;
    private String title;
    private String itemType;
    private int revision;
    private boolean published;
    private boolean fullEpisode;
    private ChannelReferencesJson program;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;
    private List<MediaJson> images;
    private MpxInfoJson mpxMetadata;
    private List<String> categories;
    private List<String> tags;
    private String mdsuuid;
    private List<String> episodeType = new ArrayList<>();
    private CustomFields customFields = new CustomFields();

    public String getMdsuuid() {
        return mdsuuid;
    }

    public void setMdsuuid(String mdsuuid) {
        this.mdsuuid = mdsuuid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isFullEpisode() {
        return fullEpisode;
    }

    public void setFullEpisode(boolean fullEpisode) {
        this.fullEpisode = fullEpisode;
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

    public List<MediaJson> getImages() {
        return images;
    }

    public void setImages(List<MediaJson> images) {
        this.images = images;
    }

    public MpxInfoJson getMpxMetadata() {
        return mpxMetadata;
    }

    public void setMpxMetadata(MpxInfoJson mpxMetadata) {
        this.mpxMetadata = mpxMetadata;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getEpisodeType() {
        return episodeType;
    }

    public void setEpisodeType(List<String> episodeType) {
        this.episodeType = episodeType;
    }

    public String getGradient() {
        return customFields.getGradient();
    }

    public void setGradient(String gradient) {
        this.customFields.setGradient(gradient);
    }

    public VideoJson getObject(Video video) {
        this.uuid = video.getMpxAsset().getGuid();
        this.title = video.getTitle();
        this.slug = video.getSlugInfo().getSlugValue();
        this.itemType = ItemTypes.VIDEO.getItemType();
        this.revision = video.getGeneralInfo().getRevision();
        this.published = true;
        this.fullEpisode = video.getMpxAsset().getFullEpisode();
        this.program = new ChannelReferencesJson().getObject(video.getAssociations().getChannelReferenceAssociations().getChannelReference());
        this.promoKicker = video.getPromotional().getPromotionalKicker();
        this.promoDescription = video.getPromotional().getPromotionalDescription();
        this.promoTitle = video.getPromotional().getPromotionalTitle();
        this.mpxMetadata = new MpxInfoJson().getObject(video.getMpxAsset());
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : video.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.tags = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.images = medias;
        return this;
    }

    public VideoJson getIosObject(Video video) {
        this.uuid = video.getMpxAsset().getGuid();
        this.title = video.getTitle();
        this.slug = video.getSlugInfo().getSlugValue();
        this.itemType = ItemTypes.VIDEO.getItemType();
        this.revision = video.getGeneralInfo().getRevision();
        this.published = video.getPublished();
        this.fullEpisode = video.getMpxAsset().getFullEpisode();
        this.program = new ChannelReferencesJson().getObject(video.getAssociations().getChannelReferenceAssociations().getChannelReference());
        this.promoKicker = video.getPromotional().getPromotionalKicker();
        this.promoDescription = video.getPromotional().getPromotionalDescription();
        this.promoTitle = video.getPromotional().getPromotionalTitle();

        video.getMpxAsset().setAvailableDate(video.getMpxAsset().getAvailableDate() * 1000);
        video.getMpxAsset().setExpirationDate(video.getMpxAsset().getExpirationDate() * 1000);
        video.getMpxAsset().setPubDate(video.getMpxAsset().getPubDate() * 1000);

        this.mpxMetadata = new MpxInfoJson().getObject(video.getMpxAsset());
        this.mpxMetadata.setShowId(program.getProgramUuid());
        List<MediaJson> medias = new ArrayList<>();
        for (MediaImage image : video.getMediaImages()) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(image);
            medias.add(mediaJson);
        }
        this.tags = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.images = medias;
        this.mdsuuid = null;
        this.episodeType = new ArrayList<>();
        return this;
    }

    @Override
    public Object clone() {
        try {
            VideoJson clone = (VideoJson) super.clone();
            clone.setMpxMetadata((MpxInfoJson) mpxMetadata.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class CustomFields {
        public String gradient;

        public String getGradient() {
            return gradient;
        }

        public void setGradient(String gradient) {
            this.gradient = gradient;
        }

        @Override
        public String toString() {
            return "CustomFields{" +
                    "gradient='" + gradient + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomFields that = (CustomFields) o;

            return gradient != null ? gradient.equals(that.gradient) : that.gradient == null;

        }

        @Override
        public int hashCode() {
            return gradient != null ? gradient.hashCode() : 0;
        }
    }
}
