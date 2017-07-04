package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.uuid;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * @Lyoha
 */
public class TC18292_WebCmsImageEntitesByUuid extends BaseAuthFlowTest {

    /**
     *
     Steps:
     Input    Expected Result
     1.Go To CMS as Admin
     Verify: The admin panel is present

     2.Go to asset library page
     Verify: User is on assetlibrary page

     3.Open random image edit page
     Verify: User is on edit image page

     3.Look into tab Devel
     Verify: get uuid

     4.Open entity page by getted UUID
     Verify: the same image edit page opened
     * */

    @Test(groups = {"uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkUUID(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        EditMultipleFilesPage editMultipleFilesPage = assetLibraryPage.clickRandomAsset();
        String expectedUrl = webDriver.getCurrentUrl();
        String uuid = editMultipleFilesPage.openDevelPage().getUuid();
        rokuBackEndLayer.openEditEntityPageByUuid(uuid);

        softAssert.assertContains(expectedUrl, webDriver.getCurrentUrl(), "User was not redirected by uuid", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
