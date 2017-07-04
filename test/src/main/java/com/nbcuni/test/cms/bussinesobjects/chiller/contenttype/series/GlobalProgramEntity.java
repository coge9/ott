package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Business object which is going to represent data for
 * Program in CMS.
 */
public class GlobalProgramEntity extends Content {

    private SeriesInfo seriesInfo = new SeriesInfo();
    private MpxAsset mpxAsset = new MpxAsset();
    private ZonedDateTime updatedDate;
    private List<ImageSource> imageSources;
    private String relatedShowGuid;
    private String showColor;
    private String gradient;


    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    public GlobalProgramEntity setTitle(String title) {
        getGeneralInfo().setTitle(title);
        return this;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.SERIES;
    }

    public GlobalProgramEntity setSubhead(String subhead) {
        getGeneralInfo().setSubhead(subhead);
        return this;
    }

    @Override
    public Class<? extends Page> getPage() {
        return EditTVEProgramContentPage.class;
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

    public SeriesInfo getSeriesInfo() {
        return seriesInfo;
    }

    public void setSeriesInfo(SeriesInfo seriesInfo) {
        this.seriesInfo = seriesInfo;
    }

    public String getShowColor() {
        return showColor;
    }

    public void setShowColor(String showColor) {
        this.showColor = showColor;
    }

    public String getGradient() {
        return gradient;
    }

    public void setGradient(String gradient) {
        this.gradient = gradient;
    }
}
