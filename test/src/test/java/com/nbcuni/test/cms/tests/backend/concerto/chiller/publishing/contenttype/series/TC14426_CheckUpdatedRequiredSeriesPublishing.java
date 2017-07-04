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
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14426_CheckUpdatedRequiredSeriesPublishing extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * 1. Login in CMS D7 as admin
     * 2. Create two Series [series name] and [related series name] with all required fields
     * 3. Open created[series name] for edit
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


    private Content series;
    private Content updatedSeries;
    private Content relatedSeries;
    private Series seriesEntity;

    @Autowired
    @Qualifier("fullSeries")
    private ContentTypeCreationStrategy updatedSeriesCreationStrategy;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();
        updatedSeries = updatedSeriesCreationStrategy.createContentType();
    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPublishingUpdate(String brand) {
        seriesEntity = (Series) updatedSeries;
        Series relatedSeriesEntity = (Series) relatedSeries;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        rokuBackEndLayer.createContentType(relatedSeries);

        SeriesPage editPage = (SeriesPage) rokuBackEndLayer.createContentType(series);
        editPage.publish();

        //create event with whole data
        seriesEntity.getSeriesInfo().setRelatedSeries(relatedSeriesEntity.getTitle());
        editPage = (SeriesPage) rokuBackEndLayer.updateContent(series, updatedSeries);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        seriesEntity.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //setMediaImages
        updateByUuid();

        //Get Expected result
        SeriesJson expectedSeries = new SeriesJson(seriesEntity);

        //Get Actual Post Request
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeries, actualSeriesJson), "The actual data is not matched", "The JSON data is matched", webDriver);
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

    public void updateByUuid() {
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        seriesEntity = (Series) rokuBackEndLayer.updateContentByUuid(seriesEntity);
        RegistryServiceEntity serviceEntity = new RegistryServiceEntity(ItemTypes.SERIES, relatedSeries);
        seriesEntity.getSeriesInfo().setRelatedSeries(serviceHelper.getUuid(serviceEntity));
    }
}
