package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 21-Jul-16.
 */
public class TC15510_CheckSeriesWithSeason extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1. Go To CMS as Editor
     * The Editor panel is present
     * 2. Create new Series [new_series]
     * Series is created
     * 3. Create new Season [new_season] and add relaited series [new_series]
     * Season is created
     * 4. Open for edit [new_series]
     * Edit page is opened
     * 5. Add current season [new_series] in association
     * Season is added
     * 6. Save and Publish [new_series]
     * Series is published.
     * currentSeason field contains UUID [new_season]
     */

    private Series series;
    private Season season;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = (Series) seriesCreationStrategy.createContentType();
        season = (Season) seasonTypeCreationStrategy.createContentType();
        season.getSeasonInfo().setParentProgram(series);
    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeriesPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);

        series.getAssociations().setSeason(season);
        ContentTypePage editPage = rokuBackEndLayer.updateContent(series, series);

        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        SeriesJson expectedSeries = new SeriesJson(series);

        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        rokuBackEndLayer.deleteContentType(series);
        rokuBackEndLayer.deleteContentType(season);
    }
}
