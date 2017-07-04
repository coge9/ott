package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TC12073
 *
 * Step 1: Go to roku admin side as Admin
 *
 * Step 2: Go to Platforms list page and remember existing platforms
 *
 * Step 3: Go to Pages list page
 *
 * Step 4: Remember pages setup for the platforms from Step 2 and edit random page
 *
 * Step 5: Check drop down for platform switching
 *
 * Validation: Switch platforms and check pages setup for platforms
 * Expected result: For each platform attache page from Step 4
 */

public class TC12073_CheckPagesSetupForThePlatform extends BaseAuthFlowTest {

    /**
     * Test put in false because of could not be run in parallel due the cause that require have stable list of pages+platforms during run
     */
    private List<String> existingPlatforms;

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkPagesSetupForThePlatform(final String brand) {

        SoftAssert softAssert = new SoftAssert();

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MainRokuAdminPage mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Step 1-2
        existingPlatforms = mainRokuAdminPage.openTvePlatformPage(brand).getAllPlatformTitlesOnPage();

        //Step 3
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);

        // Step 4
        Map<String, Set<String>> expectedPagesTitlesMap = tvePage.getAttachedPageForPlatforms(existingPlatforms);

        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.openEditRandomPageWithPanelizer();

        //Step 5
        softAssert.assertTrue(editPageWithPanelizer.isPlatformsDropDown(), "Platforms drop down is not present",
                "Platforms drop down is present");

        //Validation
        Map<String, Set<String>> actualPagesTitlesMap = editPageWithPanelizer.getPagesTitlesMap();

        softAssert.assertEquals(expectedPagesTitlesMap, actualPagesTitlesMap, "All pages is not present",
                "All pages is present");

        softAssert.assertAll();

    }
}
