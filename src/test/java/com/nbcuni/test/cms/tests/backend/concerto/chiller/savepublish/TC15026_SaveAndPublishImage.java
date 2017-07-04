package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by alekca on 22.06.2016.
 */
public class TC15026_SaveAndPublishImage extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go to CMS
     * Verify: user is in CMS
     * <p>
     * 2.click on Dashboard -> Asset library
     * Verify: Asset Library page is opened
     * <p>
     * 3.Click on "Add file"
     * Verify: "Add file" form is opened
     * <p>
     * 4.Add file. Click "Next"
     * Verify: "Edit image" page is opened
     * <p>
     * 5.check page's button
     * Verify: Only three buttons are present:"Save Draft","Save and Publish","Cancel"
     * <p>
     * 6.click on "Cancel" button
     * Verify:user is redirected to Asset library
     * <p>
     * 7.Click on "Add file"
     * Verify: "Add file" form is opened
     * <p>
     * 8.Add file.Click "Next"
     * Verify:"Edit image" page is opened
     * <p>
     * 9.click on "Save Draft"
     * Verify: Image is saved
     * <p>
     * 10.click on "Cancel" button
     * Verify: user is redirected to Asset library
     * <p>
     * 11.open added image
     * Verify: "Edit image" page is opened
     * <p>
     * 12.click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p>
     * 13.click on "Cancel" button
     * Verify:image's page is opened
     * <p>
     * 14.click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p>
     * 15.Choose API Service instance. Click on "Publish" button
     * Verify: node is published
     */

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishImage(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        //Step 3-4
        assetLibraryPage.uploadRandomFiles().get(0);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);

        //Step 5
        softAssert.assertTrue(editMultipleFilesPage.isActionButtonsPresent(Arrays.asList(ActionButtons.SAVE_AS_DRAFT, ActionButtons.SAVE_AND_PUBLISH, ActionButtons.CANCEL),
                true), "Not all of action button present", webDriver);

        softAssert.assertTrue(editMultipleFilesPage.isActionButtonsPresent(Arrays.asList(ActionButtons.DELETE), false)
                , "Not all of action button present", webDriver);

        //Step 6
        editMultipleFilesPage.cancel();
        softAssert.assertContains(webDriver.getTitle(), AssetLibraryPage.PAGE_TITLE, "Metadata page is not opened",
                "Metadata page is opened", webDriver);
        //Step 7-8
        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);

        //Step 9
        editMultipleFilesPage.save();

        //Step 10
        editMultipleFilesPage.cancel();

        //Step 11
        editMultipleFilesPage = assetLibraryPage.clickAsset(fileName);

        //Step 12
        editMultipleFilesPage.cancelPublish();

        //Step 13
        softAssert.assertContains(webDriver.getTitle(), fileName, "Metadata page is not opened",
                "Metadata page is opened", webDriver);

        //Step 14
        editMultipleFilesPage.publish();

        //Step 15-16
        String url = editMultipleFilesPage.getLogURL(brand);
        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

}
