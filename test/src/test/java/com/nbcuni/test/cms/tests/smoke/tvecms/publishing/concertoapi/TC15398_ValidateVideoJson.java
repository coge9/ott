package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.concertoapi;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.ConcertoJsonValidatorApiary;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

public class TC15398_ValidateVideoJson extends BaseAuthFlowTest {

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


    @Test(groups = {"video_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void validateVideoScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String randomAsset = contentPage.getRandomAsset(ContentType.TVE_VIDEO);

        EditTVEVideoContentPage editPage = contentPage.openEditTVEVideoPage(randomAsset);

        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        List<LocalApiJson> jsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.VIDEO);

        softAssert.assertTrue(ConcertoJsonValidatorApiary.validateJson(jsons.get(0).getRequestData().toString(), ItemTypes.VIDEO),
                "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

}
