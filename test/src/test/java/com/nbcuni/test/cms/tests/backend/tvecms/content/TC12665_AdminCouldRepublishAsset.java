package com.nbcuni.test.cms.tests.backend.tvecms.content;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 2/15/16.
 */
public class TC12665_AdminCouldRepublishAsset extends BaseAuthFlowTest {

    /**
     *Step 1. Go TO CMS As admin
     * Verify: Admin Panel is present
     *
     * Step 2. Go to content page
     * Verify: The list of Videos exist
     *
     * Step 3. Select an item by checkbox
     * Verify: The item is selected
     *
     * Step 4:  Select operation 'Publisg To API instance'
     * Verify: The New page with API endpoints selecter is present
     *
     * Step 5. Select an Instance and click Next
     * Verify: Status message of publishing is present. There is log to publish log
     *
     * Step 6.    Verify published data with any of available way:Status message, Logs in Roku CMS, API service
     * Verify:  all required fields are posted
     * all posted values are matched with OTT video data
     * all custom video thumbnails fields are present
     * all posted custom video thumbnails fields are matched with OTT video data\
     * */

    @Test(groups = {"republishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkVideoNodePublishing(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String video = contentPage.getRandomAsset(ContentType.TVE_VIDEO);

        GlobalVideoEntity videoEntity = rokuBackEndLayer.getVideo(video);
        RokuVideoJson videoExpectedObjects = VideoJsonTransformer.forSerialApi(videoEntity);

        //Step 3
        contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.checkAnItem(video);

        //Step 4
        SelectInstancePage instancePage = contentPage.selectOperation(ContentPage.Operation.PUBLISH_TO_API).clickExecute();

        //Step 5
        contentPage = instancePage.publishToInstance(Config.getInstance().getRokuApiInstance());

        //Step 6
        String url = contentPage.getLogURL(brand);
        softAssert.assertTrue(contentPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(contentPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        RokuVideoJson actualVideoJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.VIDEO);
        softAssert.assertEquals(videoExpectedObjects, actualVideoJson, "The actual/\n: " + actualVideoJson + " /\ndata is not matched with expected/\n: " + videoExpectedObjects, "The JSON data is matched", new RokuVideoJsonVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
