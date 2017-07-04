package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.images;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Step 1.    Go To CMS As Admin
 * Verify:    The admin Panel is present
 * <p>
 * Step 2. Go To Dashboard -> Asset Library and select an Image
 * Verify: The Edit Image Page is opened
 * <p>
 * Step 3. Click button 'publish'
 * Verify:    The POST request is send to API. Response message is 'success'
 * <p>
 * Step 4. Go To Amazon consol and analize Header of POST request
 * Verify: There are attributes: action = 'post'; entityType = 'images'
 */

public class TC14677_CheckImageMessageAttributes extends BaseAuthFlowTest {

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testEpisodePublishingAttributes(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //open random image
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        //reopen image
        rokuBackEndLayer.openForEditImageFromAssetLibrary(fileName);
        //publish
        editMultipleFilesPage.publish();

        String url = editMultipleFilesPage.getLogURL(brand);
        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.IMAGE.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
