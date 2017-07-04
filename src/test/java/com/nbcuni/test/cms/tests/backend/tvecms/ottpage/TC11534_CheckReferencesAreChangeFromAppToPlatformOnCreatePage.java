package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova
 */
public class TC11534_CheckReferencesAreChangeFromAppToPlatformOnCreatePage extends BaseAuthFlowTest {
    /**
     * Step 1: go to Roku CMS
     *
     * Step 2: go to Pages menu
     *
     * Step 3: click "add new page"
     * Verify: page title
     *
     * Step 4: check for "Platform" control
     * Verify: there is Platform" control
     *
     */

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = "master_config", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc11534(final String brand) {
        Utilities.logInfoMessage("Check that all references in CMS Pages are changed from 'application' to 'Platform' for existent page");
        String expectedPlatformLabel = "Platform *";

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2
        AddNewPage randomPage = mainRokuAdminPage.openOttPage(brand).clickAddNewPage();
        softAssert.assertEquals(randomPage.PAGE_TITLE, randomPage.getPageTitle(), "Add new page title");
        softAssert.assertEquals(expectedPlatformLabel, randomPage.elementPlatformLabel().getText(), "Platform label text");
        softAssert.assertAll();
    }
}
