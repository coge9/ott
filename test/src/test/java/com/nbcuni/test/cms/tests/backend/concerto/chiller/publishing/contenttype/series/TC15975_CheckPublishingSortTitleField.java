package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 8/31/2016.
 */
public class TC15975_CheckPublishingSortTitleField extends BaseAuthFlowTest {

    /**
     * Pre-conditon:
     * 1.Prepare list of series names witch have specific words we must delete or replace.
     * 2.Go to TVE CMS
     * Steps:
     * 1. Create new series with required fields.
     * Verify: Series created.
     * 2. Rename the series we have created with name from our list from pre-condition and save changes.
     * Verify: Season is renamed and saved.
     * 3. Publish the season.
     * Verify: Season is published. log url is present.
     * 4. Check "sortTitle" field in publishing message.
     * Verify: "sortTitle" is correctly published. All specific words are replaced/deleted.
     */

    private Series series;
    private List<String> specificWords = new ArrayList<>();
    private String seriesTitle;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBuisnessObject() {
        specificWords.add("AN ");
        specificWords.add("The ");
        specificWords.add("a ");
        specificWords.add("Episode Seven ");

        series = (Series) seriesCreationStrategy.createContentType();
        seriesTitle = series.getTitle();
        series.getGeneralInfo().setTitle(specificWords.get(SimpleUtils.getRandomInt(specificWords.size())) + seriesTitle);
    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSortTitlePublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        SeriesPage seriesPage = (SeriesPage) rokuBackEndLayer.createContentType(series).publish();

        String url = rokuBackEndLayer.getLogURL(brand);
        series.setSlugInfo(seriesPage.onSlugTab().getSlug());
        SeriesJson expectedSeries = new SeriesJson(series);

        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        rokuBackEndLayer.deleteContentType(series);
    }
}
