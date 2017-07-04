package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.SeriesInfoBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.gradientcolor.GradientColorBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.GradientColorInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public class SeriesPage extends ContentTypePage {

    @FindBy(id = "edit-group_programming")
    private SeriesInfoBlock seriesInfoBlock;

    @FindBy(id = "edit-group_basic")
    private GradientColorBlock gradientColorBlock;

    public SeriesPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public SeriesPage enterContentTypeData(Content content) {
        this.enterBasicData(content.getGeneralInfo());
        enterGradientColorData(((Series) content).getGradientColorInfo());
        this.enterProgrammingData(((Series) content).getSeriesInfo());
        enterPromotionalData(content.getPromotional());
        enterExternalLinksData(content.getExternalLinksInfo());
        enterMediaData(content.getMediaImages());
        enterAssociationData(content.getAssociations());
        return this;
    }

    private SeriesPage enterBasicData(GeneralInfo generalInfo) {
        generalInfoBlock.expandTab();
        generalInfoBlock.enterTitle(generalInfo.getTitle());
        generalInfoBlock.enterSubhead(generalInfo.getSubhead());
        generalInfoBlock.enterShortDescription(generalInfo.getShortDescription());
        generalInfoBlock.enterMediumDescription(generalInfo.getMediumDescription());
        generalInfoBlock.enterLongDescription(generalInfo.getLongDescription());
        return this;
    }

    private SeriesPage enterGradientColorData(GradientColorInfo gradientColorInfo) {
        generalInfoBlock.expandTab();
        gradientColorBlock.setGradient(gradientColorInfo.getGradient());
        gradientColorBlock.setShowColor(gradientColorInfo.getColor());
        return this;
    }

    private SeriesPage enterProgrammingData(SeriesInfo seriesInfo) {
        if (seriesInfo.isObjectNull()) {
            return this;
        }
        seriesInfoBlock.expandTab();
        seriesInfoBlock.selectGenre(seriesInfo.getGenre());
        seriesInfoBlock.selectRating(seriesInfo.getRating());
        seriesInfoBlock.checkUnscripted(seriesInfo.getUnscripted());
        seriesInfoBlock.selectStatus(seriesInfo.getStatus());
        seriesInfoBlock.checkSyndicated(seriesInfo.getSyndicated());
        seriesInfoBlock.selectRelatedSeries(seriesInfo.getRelatedSeries());
        seriesInfoBlock.checkType(seriesInfo.getType());
        if (seriesInfo.getType() != null && seriesInfo.getType().equals(Type.TV_SERIES)) {
            seriesInfoBlock.selectProgrammingTimeframe(seriesInfo.getProgrammingTimeframe());
            seriesInfoBlock.enterRegularlyScheduledDuration(seriesInfo.getRegularlyScheduledDuration());
        }
        return this;
    }

    @Override
    public SeriesPage create(Content content) {
        return (SeriesPage) this.enterContentTypeData(content).saveAsDraft();
    }

    @Override
    public SeriesPage createAndPublish(Content content) {
        return (SeriesPage) this.enterContentTypeData(content).publish();
    }

    @Override
    public Series getPageData() {
        return null;
    }
}
