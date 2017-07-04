package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.program;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Steps:
 * Step 1: Open content page
 * Step 2: Open edit page for random program
 * Step 3: Save program
 * Step 4: Open edit page for this program again
 * Step 5: Publish program
 * Verify lastUpdateDatee field at json response 
 *
 */

public class TC11696_LastUpdateDateFieldForProgramNode extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void tc11696logic(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(ContentType.TVE_PROGRAM).apply();
        String assetTitle = contentPage.clickEditLinkForRandomAsset();
        EditTVEProgramContentPage editTVEProgramContentPage = new EditTVEProgramContentPage(webDriver, aid);
        editTVEProgramContentPage.clickSave();
        mainRokuAdminPage.openContentPage(brand).clickEditLink(EditTVEProgramContentPage.class, assetTitle);
        Date currentDate = DateUtil.getCurrentDateInUtc();
        editTVEProgramContentPage.elementPublishBlock().publishByTabName();
        String url = editTVEProgramContentPage.getLogURL(brand);
        RokuProgramJson programFromJson = getActualProgramResponse(url);

        Date actualDate = DateUtil.parseStringToUtcDate(programFromJson.getDateModified());
        Utilities.logInfoMessage("Actual time of publishing [" + actualDate + "]");
        Utilities.logInfoMessage("Time before click publish button [" + currentDate + "]");
        softAssert.assertTrue(actualDate.getTime() - currentDate.getTime() < 10000, "Data Modified field value is not correct. ", "Data Modified field value is correct", webDriver);
        softAssert.assertAll();
    }

    private RokuProgramJson getActualProgramResponse(String url) {
        List<LocalApiJson> localApiJson = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.PROGRAM);
        List<RokuProgramJson> programs = new ArrayList<>();
        try {
            programs = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.PROGRAM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String status = localApiJson.get(1).getResponseStatus();
        softAssert.assertEquals(status, ResponseData.UPDATED.getResponseStatus(), "The response code actual: " + status
                + " is not matched with expected 200", "The response code actual is matched with expected ", webDriver);
        String message = localApiJson.get(1).getResponseMessage();
        softAssert.assertEquals(message, ResponseData.UPDATED.getResponseMessage(), "The response message actual: " + message
                + " is not matched with expected OK", "The response message actual is matched with expected ", webDriver);
        return programs.get(0);

    }

}
