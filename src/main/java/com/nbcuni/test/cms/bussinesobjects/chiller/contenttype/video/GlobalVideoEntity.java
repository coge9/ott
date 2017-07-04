package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 *
 * Business object which is going to represent data for
 * VIDEO in CMS.
 *
 */
public class GlobalVideoEntity extends Content {
    private MpxAsset mpxAsset = new MpxAsset();
    private ZonedDateTime updatedDate;
    private List<ImageSource> imageSources;
    private String relatedShowGuid;
    private String gradient;

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    public GlobalVideoEntity setTitle(String title) {
        getGeneralInfo().setTitle(title);
        return this;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.VIDEO;
    }

    @Override
    public Class<? extends Page> getPage() {
        return ChillerVideoPage.class;
    }

    public MpxAsset getMpxAsset() {
        return mpxAsset;
    }

    public void setMpxAsset(MpxAsset mpxAsset) {
        this.mpxAsset = mpxAsset;
    }


    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ImageSource> getImageSources() {
        return imageSources;
    }

    public void setImageSources(List<ImageSource> imageSources) {
        this.imageSources = imageSources;
    }

    public String getRelatedShowGuid() {
        return relatedShowGuid;
    }

    public void setRelatedShowGuid(String relatedShowGuid) {
        this.relatedShowGuid = relatedShowGuid;
    }

    public String getGradient() {
        return gradient;
    }

    public void setGradient(String gradient) {
        this.gradient = gradient;
    }
}
