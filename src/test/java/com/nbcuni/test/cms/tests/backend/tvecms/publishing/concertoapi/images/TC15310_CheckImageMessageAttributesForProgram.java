package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.images;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * TC15310
 *
 * Pre-Conditions:
 * 1. Go To CMS as editor
 * 2. Open random TVE Program for edit
 *
 * Steps:
 * 1. Click 'Publish' button and send POST request to Development
 * The POST request for image styles file is send to the Amazon SQS
 * API Log present 'success' status message per request
 * 2. Check POST request for each file (Amazon SQS): analize Header of POST request
 * There are attributes:
 * action = 'post'
 * entityType = 'images'
 */
public class TC15310_CheckImageMessageAttributesForProgram extends BaseAuthFlowTest {

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void testImagePublishingAttributes(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //create event with whole data
        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        String randomAsset = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        //Step 3
        EditTVEProgramContentPage editPage = contentPage.openEditOTTProgramPage(randomAsset);

        //publishing
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        List<LocalApiJson> jsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        for (LocalApiJson localApiJson : jsons) {
            String action = localApiJson.getAttributes().getAction().getStringValue();
            softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                    "The action message attribute are matched");
            String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
            softAssert.assertEquals(ItemTypes.IMAGE.getEntityType(), entityType, "The entityType message attribute are not matched",
                    "The entityType message attribute are matched");
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
