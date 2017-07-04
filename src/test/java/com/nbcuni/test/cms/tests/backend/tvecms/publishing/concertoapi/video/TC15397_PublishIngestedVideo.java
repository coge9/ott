package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.roku.VideoIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

public class TC15397_PublishIngestedVideo extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To CMS as Editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go to Content -> find Video Content type
     * Verify: The edit video Page is present
     * <p>
     * 3.Fill all the fields per all sections and save
     * Verify: The Video content type is saved
     * All fields are filled
     * <p>
     * 4.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 5.Analize POST request for Video
     * Verify:The JSON scheme of Event llok like below with all filled fields:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/video
     * scheme  - http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/video
     */


    @Test(groups = {"video_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void testIngestedVideoPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        GlobalVideoEntity globalVideoEntity = rokuBackEndLayer.getRandomVideo();

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        EditTVEVideoContentPage editPage = contentPage.openEditTVEVideoPage(globalVideoEntity.getTitle());
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);

        String url = editPage.getLogURL(brand);

        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);
        //Step 5
        VideoJson expectedVideoJson = VideoJsonTransformer.forConcertoApiAll(rokuBackEndLayer.getVideo(globalVideoEntity.getTitle()));
        VideoJson actualVideoJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.VIDEO);

        softAssert.assertEquals(expectedVideoJson, actualVideoJson, "The actual data is not matched with expected", "The JSON data is matched", new VideoIosVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
