package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Created series [related series name] with all required fields
 * 3. Create Series [series name] with all fields
 * 4. Open created[series name] for edit
 * <p>
 * Steps:
 * 1. Click on Publish button
 * Verify: The API log present 'success' status message of POST request
 * <p>
 * 2. Analize POST request for Series
 * Verify: The JSON scheme of Series like below with all filled fields:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/series
 * <p>
 * 3. Update all fields for [series name] and assign  [related series name] to the [series name].
 * Verify: All fields are filled
 * <p>
 * 4. Click on Save button
 * Verify: [series name] is saved. Status message is presented
 * <p>
 * 5. Click on Publish button
 * Verify: The API log present 'success' status message of POST request
 * <p>
 * 6. Analize POST request for Series
 * Verify: The JSON scheme of Series like below with all filled fields:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/series
 */

public class TC14970_CheckUpdatedSeriesPublishing extends BaseAuthFlowTest {

    private Content series;
    private Content updatedSeries;

    private Content relatedSeries;

    private Series seriesEntity;
    private Series updatedSeriesEntity;
    private Series relatedSeriesEntity;

    @Autowired
    @Qualifier("fullSeries")
    private ContentTypeCreationStrategy fullSeriesCreationStrategy;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        relatedSeriesEntity = (Series) relatedSeries;
        series = fullSeriesCreationStrategy.createContentType();
        seriesEntity = (Series) series;
        seriesEntity.getSeriesInfo().setRelatedSeries(relatedSeries.getTitle());
        seriesEntity.getSeriesInfo().setRelatedSeries(relatedSeriesEntity.getTitle());
        updatedSeries = fullSeriesCreationStrategy.createContentType();
        updatedSeriesEntity = (Series) updatedSeries;

    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPublishingUpdate(String brand) {
        //Pre-condition

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Create related Series and update seriesEntity
        rokuBackEndLayer.createContentType(relatedSeries);
        //Create series
        rokuBackEndLayer.createContentType(series);
        //first publishing
        SeriesPage editPage = new SeriesPage(webDriver, aid);
        seriesEntity.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());
        editPage.publish();

        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        //set Related series
        updateByUuid(seriesEntity);
        //Get Expected result
        SeriesJson expectedSeries = new SeriesJson(seriesEntity);
        //Get Actual Post Request
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        //update series
        editPage = (SeriesPage) rokuBackEndLayer.updateContent(series, updatedSeries);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        updatedSeriesEntity.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());
        //publishing
        editPage.publish();
        url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //set Related series
        updateByUuid(updatedSeriesEntity);

        //Get Expected result
        SeriesJson updatedExpectedSeries = new SeriesJson(updatedSeriesEntity);

        //Get Actual Post Request
        actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(updatedExpectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypes() {
        try {
            rokuBackEndLayer.deleteContentType(relatedSeries);
            rokuBackEndLayer.deleteContentType(updatedSeries);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

    public void updateByUuid(Series series) {
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        series = (Series) rokuBackEndLayer.updateContentByUuid(series);
        RegistryServiceEntity serviceEntity = new RegistryServiceEntity(ItemTypes.SERIES, relatedSeries);
        series.getSeriesInfo().setRelatedSeries(serviceHelper.getUuid(serviceEntity));
    }

}
