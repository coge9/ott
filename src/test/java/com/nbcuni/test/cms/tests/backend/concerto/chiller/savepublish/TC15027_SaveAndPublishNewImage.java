package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 6/22/16.
 */
public class TC15027_SaveAndPublishNewImage extends BaseAuthFlowTest {

    /**
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2.Click on Dashboard -> Asset library
     * Verify: Asset Library page is opened
     * <p/>
     * 3.Click on "Add file"
     * Verify: "Add file" form is opened
     * <p/>
     * 4. Add file. Click "Next"
     * Verify: "Edit image" page is opened
     * <p/>
     * 5.Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 6.Choose API Service instance. click on "Publish" button
     * Verify: Image is published
     */

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishNewImage(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        //Step 3-4
        assetLibraryPage.uploadRandomFiles().get(0);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);

        //Step 5
        editMultipleFilesPage.publish();

        //Step 6
        String url = mainRokuAdminPage.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        // Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertEquals(localApiJson.getResponseStatus(), ResponseData.SUCCESS.getResponseStatus(), "The action message attribute are not present",
                "The action message attribute are present");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed successfully");
    }
}
