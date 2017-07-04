package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14715_ValidateVideoJson extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure there is a Video content type
     * Steps:
     * <p>
     * 1.Go To CMS as Editor
     * Verify:The Editor panel is present
     * <p>
     * 2.Go to Content and select Video from pre-condition
     * Verify: The Edit Video Page is opened
     * <p>
     * 3.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 4.Validate Scheme of POST request for Event
     * Veriy: The JSON scheme of send Event is matched with scheme available by URL below:
     * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/json+schema/video
     */


    @Test(groups = {"video_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateVideoScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();


        //create event with data
        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String title = contentPage.getFullTitleOfFirstElement();

        //Step 3
        ChillerVideoPage editPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.VIDEO).get(0);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateVideoBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

}
