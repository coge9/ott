package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.GradientColorInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class Series extends Content {

    private GradientColorInfo gradientColorInfo = new GradientColorInfo();
    private SeriesInfo seriesInfo = new SeriesInfo();

    public Series() {
    }

    public Series(Series seriesForCopy) {
        this.seriesInfo = seriesForCopy.getSeriesInfo();
    }

    public GradientColorInfo getGradientColorInfo() {
        return gradientColorInfo;
    }

    public void setGradientColorInfo(GradientColorInfo gradientColorInfo) {
        this.gradientColorInfo = gradientColorInfo;
    }

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.SERIES;
    }

    @Override
    public Class<? extends Page> getPage() {
        return SeriesPage.class;
    }

    public SeriesInfo getSeriesInfo() {
        return seriesInfo;
    }

    public void setSeriesInfo(SeriesInfo seriesInfo) {
        this.seriesInfo = seriesInfo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
