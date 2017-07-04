package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
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
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14424_CheckSeriesPublishingWithAllFields extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To CMS as Editor
     * Verify: The Editor panel is present
     * <p>
     * 2. Go to Content -> Add Series Content type
     * Verify: The Create Series Page is present
     * <p>
     * 3.Fill all the fields per all sections and save
     * Verify:The new Series content type is created<br/>
     * All fields are filled<br/>
     * <p>
     * 4.Go To the edit page of created Series
     * Verify:The edit Page is present
     * <p>
     * 5.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 6.Analize POST request for Series
     * Verify:The JSON scheme of Series like below with all filled fields:
     * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/series
     */

    private Content series;
    private Content relatedSeries;
    private Series seriesEntity;

    @Autowired
    @Qualifier("fullSeries")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        series = contentTypeCreationStrategy.createContentType();
        seriesEntity = (Series) series;
        seriesEntity.getSeriesInfo().setRelatedSeries(relatedSeries.getTitle());

    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeriesPublishing(String brand) {


        Series relatedSeriesEntity = (Series) relatedSeries;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        rokuBackEndLayer.createContentType(relatedSeries);

        SeriesPage editPage = (SeriesPage) rokuBackEndLayer.createContentType(seriesEntity);
        //setSlug
        seriesEntity.setSlugInfo(editPage.onSlugTab().getSlug());
        seriesEntity.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());
        rokuBackEndLayer.updateContentByUuid(seriesEntity);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        seriesEntity.getSeriesInfo().setRelatedSeries(relatedSeries.getGeneralInfo().getUuid());
        SeriesJson expectedSeries = new SeriesJson(seriesEntity);

        //Get Actual Post Request
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(relatedSeries);
            rokuBackEndLayer.deleteContentType(series);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
