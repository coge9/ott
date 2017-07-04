package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype;


import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public abstract class Content extends AbstractEntity {

    private GeneralInfo generalInfo = new GeneralInfo();
    private Associations associations = new Associations();
    private Promotional promotional = new Promotional();
    private List<MediaImage> mediaImages = new ArrayList<>();
    private Slug slugInfo = new Slug();
    private List<ExternalLinksInfo> externalLinksInfos = new ArrayList<>();
    private List<CastEntity> castAndCredit = new ArrayList<>();
    private Boolean published = true;
    private Integer publishedDate = null;

    public abstract Class<? extends Page> getPage();

    public abstract String getTitle();

    public List<ExternalLinksInfo> getExternalLinksInfo() {
        return externalLinksInfos;
    }

    public Content setExternalLinksInfo(List<ExternalLinksInfo> externalLinksInfo) {
        this.externalLinksInfos = externalLinksInfo;
        return this;
    }

    public Associations getAssociations() {
        return associations;
    }

    public Content setAssociations(Associations associations) {
        this.associations = associations;
        return this;
    }

    public Promotional getPromotional() {
        return promotional;
    }

    public void setPromotional(Promotional promotional) {
        this.promotional = promotional;
    }

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    public Content setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
        return this;
    }

    public Slug getSlugInfo() {
        return slugInfo;
    }

    public void setSlugInfo(Slug slugInfo) {
        this.slugInfo = slugInfo;
    }

    public List<MediaImage> getMediaImages() {
        return mediaImages;
    }

    public void setMediaImages(List<MediaImage> mediaImages) {
        this.mediaImages = mediaImages;
    }

    public List<CastEntity> getCastAndCredit() {
        return castAndCredit;
    }

    public void setCastAndCredit(List<CastEntity> castAndCredit) {
        this.castAndCredit = castAndCredit;
    }

    public abstract ItemTypes getType();

    public boolean getPublished() {
        return this.published;
    }

    public Content setPublished(Boolean published) {
        this.published = published;
        return this;
    }

    public Integer getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Integer publishedDate) {
        this.publishedDate = publishedDate;
    }
}
