package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.event;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.event.EventJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.EventVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC17666
 * Pre-condition:Go to chiller CMS and create a Event with Required fields
 * <p>
 * Steps:
 * 1.Go to CMS as Editor
 * The Editor Navigation Panel is present
 * <p>
 * 2.Go to Content and select the Event from Pre-condition
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
public class TC17668_CreationReqEventWithImageWithoutUsage extends BaseAuthFlowTest {

    private Event content;

    @Autowired
    @Qualifier("requiredEventWithImageWithoutUsage")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = (Event) contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"event_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationEventWithEmptyUsage(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createAndPublishContentType(content);
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().save();
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().unPublish();
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not presented",
                "Validation message is presented", webDriver);

        contentTypePage.onMediaTab().onMediaBlock().selectRandomUsageForAllImages();
        contentTypePage.publish();
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        content.setSlugInfo(contentTypePage.getSlugInfo());
        content.setMediaImages(contentTypePage.onMediaTab().onMediaBlock().getMediaImages());
        rokuBackEndLayer.updateContentByUuid(content);

        //set Episode uuid
        EventJson expectedEventJson = new EventJson().getObject(content);

        //Get Actual Post Request
        EventJson actualEventJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.EVENT);
        softAssert.assertTrue(new EventVerificator().verify(expectedEventJson, actualEventJson),
                "The actual data is not matched", "The JSON data is matched", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteEventTC17668() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logWarningMessage(e.getMessage());
        }
    }
}
