package com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Aliaksei_Dzmitrenka on 2/21/2017.
 */
public class GlobalNodeJson {

    /**
     * @author Aliaksei Dzmitrenka
     * <p/>
     * This class is represent concrete node json for Content API
     */

    private String title;
    private String vid;
    private String uid;
    private String vuuid;
    private String uuid;
    private String nid;
    private String type;
    private String slug;
    private String revision;
    private String featureCarouselCta;
    private String featureCarouselHeadline;
    private String showPageCta;
    private String longDescription;
    private MpxAsset mpxAsset;
    private List<ImageSource> imageSources;
    private String genre;
    private Boolean urlPath;
    private Boolean published;
    private ZonedDateTime updatedDate;
    private String showColor;
    private String gradient;
    private String programStatus;
    private String relatedShowId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVuuid() {
        return vuuid;
    }

    public void setVuuid(String vuuid) {
        this.vuuid = vuuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getFeatureCarouselCta() {
        return featureCarouselCta;
    }

    public void setFeatureCarouselCta(String featureCarouselCta) {
        this.featureCarouselCta = featureCarouselCta;
    }

    public String getFeatureCarouselHeadline() {
        return featureCarouselHeadline;
    }

    public void setFeatureCarouselHeadline(String featureCarouselHeadline) {
        this.featureCarouselHeadline = featureCarouselHeadline;
    }

    public String getShowPageCta() {
        return showPageCta;
    }

    public void setShowPageCta(String showPageCta) {
        this.showPageCta = showPageCta;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Boolean getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(Boolean urlPath) {
        this.urlPath = urlPath;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public MpxAsset getMpxAsset() {
        return mpxAsset;
    }

    public void setMpxAsset(MpxAsset mpxAsset) {
        this.mpxAsset = mpxAsset;
    }

    public List<ImageSource> getImageSources() {
        return imageSources;
    }

    public void setImageSources(List<ImageSource> imageSources) {
        this.imageSources = imageSources;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getGradient() {
        return gradient;
    }

    public void setGradient(String gradient) {
        this.gradient = gradient;
    }

    public String getShowColor() {
        return showColor;
    }

    public void setShowColor(String showColor) {
        this.showColor = showColor;
    }


    public String getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ImageSource getSourceByType(SourceMatcher type) {
        return getImageSources().stream().filter(source -> source.getMachineName().equals(type.getMachineName())).findFirst().get();
    }

    public String getRelatedShowId() {
        return relatedShowId;
    }

    public void setRelatedShowId(String relatedShowId) {
        this.relatedShowId = relatedShowId;
    }

    @Override
    public String toString() {
        return "GlobalNodeJson{" +
                "title='" + title + '\'' +
                ", vid='" + vid + '\'' +
                ", uid='" + uid + '\'' +
                ", vuuid='" + vuuid + '\'' +
                ", nid='" + nid + '\'' +
                ", type='" + type + '\'' +
                ", slug='" + slug + '\'' +
                ", revision='" + revision + '\'' +
                ", featureCarouselCta='" + featureCarouselCta + '\'' +
                ", featureCarouselHeadline='" + featureCarouselHeadline + '\'' +
                ", showPageCta='" + showPageCta + '\'' +
                ", gradient='" + gradient + '\'' +
                ", mpxAsset=" + mpxAsset +
                ", imageSources=" + imageSources +
                ", urlPath=" + urlPath +
                ", published=" + published +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
