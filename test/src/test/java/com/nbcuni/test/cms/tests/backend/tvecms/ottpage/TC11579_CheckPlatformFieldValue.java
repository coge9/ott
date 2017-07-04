package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Alena_Aukhukova
 */
public class TC11579_CheckPlatformFieldValue extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;

    /**
     * Pre-condition
     * 1. Login in Roku CMS as Admin
     * 2. Go to /admin/ott/apps
     * 3. Remember [list of platforms]
     *
     * Step 1: Go to /admin/ott/pages
     * Verify: Page title is 'Add shelf module'
     *
     * Step 2: Open Platform drop down
     * Verify: All values from [list of platforms] is presented
     */

    @Test(groups = "master_config", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc11579(final String brand) {
        Utilities.logInfoMessage("Check that All values from [list of platforms] is presented");
        //Pre-condition
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        TvePlatformsPage platformsPage = mainRokuAdminPage.openTvePlatformPage(brand);
        List<String> expectedPlatformNames = platformsPage.getAllPlatformTitlesOnPage();
        //Step 1
        AddNewPage addOttPage = mainRokuAdminPage.openOttPage(brand).clickAddNewPage();
        softAssert.assertEquals(addOttPage.PAGE_TITLE, addOttPage.getPageTitle(), "Page title");
        //Step 2
        List<String> actualViewportNames = addOttPage.elementAddPlatform().getValuesToSelect();
        actualViewportNames.remove(0);
        softAssert.assertEquals(expectedPlatformNames, actualViewportNames, "All values from [list of platforms]");
        softAssert.assertAll();
    }
}
