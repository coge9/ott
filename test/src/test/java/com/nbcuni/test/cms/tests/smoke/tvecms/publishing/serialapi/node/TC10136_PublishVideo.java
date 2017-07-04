package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.node;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import com.nbcuni.test.cms.verification.roku.VideoIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 2/12/16.
 */
public class TC10136_PublishVideo extends BaseAuthFlowTest {

    /**
     * Step 1. Go TO CMS As admin
     * Verify: Admin Panel is present
     * <p>
     * Step 2. Go to content page
     * Verify: The list of Videos exist
     * <p>
     * Step 3. Open edit page for any Video.
     * Go To MPXInfoTab
     * Verify: The list of MPX metadata for node is present
     * <p>
     * Step 4. Click publish to development
     * Verify: Status message of publishing is present
     * <p>
     * Step 5.    Verify published data with any of available way:Status message, Logs in Roku CMS, API service
     * Verify:  all required fields are posted
     * all posted values are matched with OTT video data
     * all custom video thumbnails fields are present
     * all posted custom video thumbnails fields are matched with OTT video data\
     */

    @Test(groups = {"node_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkVideoNodePublishing(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Step 2
        GlobalVideoEntity globalVideoEntity = rokuBackEndLayer.getRandomVideo();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.openEditTVEVideoPage(globalVideoEntity.getTitle());
        EditTVEVideoContentPage editPage = new EditTVEVideoContentPage(webDriver, aid);
        //Step 4
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);

        globalVideoEntity = rokuBackEndLayer.getVideo(globalVideoEntity.getTitle());
        RokuVideoJson expectedSerialVideoJson = VideoJsonTransformer.forSerialApi(globalVideoEntity);
        VideoJson expectedConcertoVideoJson = VideoJsonTransformer.forConcertoApiAll(globalVideoEntity);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);
        //Step 5
        RokuVideoJson actualSerialVideoJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.VIDEO);
        VideoJson actualConcertoVideoJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.VIDEO);

        softAssert.assertEquals(expectedSerialVideoJson, actualSerialVideoJson, "The actual Serial data is not matched with expected", "The JSON data is matched for Serial", new RokuVideoJsonVerificator());
        softAssert.assertTrue(new VideoIosVerificator().verify(expectedConcertoVideoJson, actualConcertoVideoJson), "The actual Concerto data is not matched", "The JSON data is matched for Concerto", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
