package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
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
 * TC17666
 * Pre-condition:Go to chiller CMS and create a Season with Required fields
 * <p>
 * Steps:
 * 1.Go to CMS as Editor
 * The Editor Navigation Panel is present
 * <p>
 * 2.Go to Content and select the Season from Pre-condition
 * The edit Page of the content item has opened
 * <p>
 * 3.Expand Media tab and add one Image or Video
 * Media item is added
 * <p>
 * 4.Leave the media items with default 'Empty' value
 * The default empty value is selected
 * <p>
 * 5.Click "Save", "Save and Publish or 'Save and Unpublish' buttons
 * The warning Massage has triggered that impossible to save Node without selected usage NOT empty value
 * <p>
 * 6.Select a usages for all added to Media items and Save
 * The node was saved without Issue
 * <p>
 * 7.Publish Node to API
 * and take a look at the Publish log of Node POST request
 * <p>
 * The Node POST request contains selected Usage values within mediaItems[] array
 */
public class TC17664_CreationReqSeriesWithImageWithoutUsage extends BaseAuthFlowTest {

    private Series series;

    @Autowired
    @Qualifier("requiredSeriesWithImageWithoutUsage")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = (Series) seriesCreationStrategy.createContentType();
    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationSeriesWithEmptyUsage(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createAndPublishContentType(series);

        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().save();
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().unPublish();
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.onMediaTab().onMediaBlock().selectRandomUsageForAllImages();
        contentTypePage.publish();
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        series.setSlugInfo(contentTypePage.getSlugInfo());
        series.setMediaImages(contentTypePage.onMediaTab().onMediaBlock().getMediaImages());
        rokuBackEndLayer.updateContentByUuid(series);

        //set Episode uuid
        SeriesJson expectedSeriesJson = new SeriesJson(series);

        //Get Actual Post Request
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertTrue(new SeriesVerificator().verify(expectedSeriesJson, actualSeriesJson),
                "The actual data is not matched", "The JSON data is matched", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteEventTC17664() {
        try {
            rokuBackEndLayer.deleteContentType(series);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
