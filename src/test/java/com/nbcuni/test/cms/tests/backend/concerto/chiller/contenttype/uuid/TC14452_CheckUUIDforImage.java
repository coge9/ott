package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.uuid;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by alekca on 12.05.2016.
 */
public class TC14452_CheckUUIDforImage extends BaseAuthFlowTest {
    /**
     * Pre-Conditions:Make sure drupal 'Devel' module is switch on
     Steps:
     Input    Expected Result
     1.Go To CMS as Admin
     Verify: The admin panel is present

     2.Go To the asset library page
     Verify: There is a list of images

     3.Select an image on edit
     Verify:The edit Image page is opened

     4.Look into tab Devel
     Verify: There is 'uuid' field with value like '0c3cf275-b64b-412f-8adf-7063f1238c18'

     5.Make sure there is no any UUID field at the UI
     Verify: There is no any UUID field
     * */

    @Test(groups = {"uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkUUIDImage(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        AssetLibraryPage libraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        //Step 3
        EditMultipleFilesPage multipleFilesPage = libraryPage.clickRandomAsset();

        //Step 4
        DevelPage develPage = multipleFilesPage.openDevelPage();
        softAssert.assertTrue(develPage.uuidIsPresent(), "The uuid field is not present", "The uuid field is present and not empty");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

}
