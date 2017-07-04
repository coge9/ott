package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Aliaksei_Dzmitrenka
 * Steps:
 * Step 1: Open content page
 * Step 2: Open edit page for random video
 * Step 3: Save video
 * Step 4: Open edit page for this video again
 * Step 5: Publish video
 * Verify lastUpdateDatee field at json response 
 *
 */


public class TC11697_LastUpdateDateFieldForVideoNode extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void tc11696logic(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String assetTitle = contentPage.clickEditLinkForRandomAsset();
        EditTVEVideoContentPage editOTTvideoContentPage = new EditTVEVideoContentPage(webDriver, aid);
        editOTTvideoContentPage.clickSave();
        mainRokuAdminPage.openContentPage(brand).clickEditLink(EditTVEVideoContentPage.class, assetTitle);
        Date currentDate = DateUtil.getCurrentDateInUtc();
        editOTTvideoContentPage.elementPublishBlock().publishByTabName();
        String url = editOTTvideoContentPage.getLogURL(brand);
        RokuVideoJson videoFromJson = getActualVideoResponse(url);

        Date actualDate = DateUtil.parseStringToUtcDate(videoFromJson.getDateModified());
        Utilities.logInfoMessage("Actual time of publishing [" + actualDate + "]");
        Utilities.logInfoMessage("Time before click publish button [" + currentDate + "]");
        softAssert.assertTrue(actualDate.getTime() - currentDate.getTime() < 10000, "Data Modified field value is not correct. ", "Data Modified field value is correct", webDriver);
        softAssert.assertAll();

    }

    private RokuVideoJson getActualVideoResponse(String url) {

        List<LocalApiJson> localApiJson = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.VIDEO);
        List<RokuVideoJson> programs = new ArrayList<>();
        try {
            programs = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(programs.size(), 1, "The Response list does not match the expected size, returned response is: " + programs);
        String status = localApiJson.get(1).getResponseStatus();
        softAssert.assertEquals(status, ResponseData.UPDATED.getResponseStatus(), "The response code actual: " + status
                + " is not matched with expected 200", "The response code actual is matched with expected ", webDriver);
        String message = localApiJson.get(1).getResponseMessage();
        softAssert.assertEquals(message, ResponseData.UPDATED.getResponseMessage(), "The response message actual: " + message
                + " is not matched with expected OK", "The response message actual is matched with expected ", webDriver);
        return programs.get(0);

    }

}
