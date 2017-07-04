package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * TC12148
 *
 * Step 1: Go To roku CMS as Admin
 *
 * Step 2: Go to Pages list page(admin/ott/pages)
 *
 * Step 3: Click edit random page
 * Remember all assigned module's titles
 *
 * Step 4: Click Preview button
 *
 * Validation:     Check assigned modules
 * Expected result: All content from modules should be presented on the view
 */
public class TC12148_CheckAllAssignedModulesInThePreviewView extends BaseAuthFlowTest {

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkPreviewModeButton(final String brand) {
        this.brand = brand;

        //Step 1
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //Step 2 - 3
        EditPageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openEditRandomPageWithPanelizer();

        List<String> expectedModules = pageWithPanelizer.getContentBlockNames();

        //Step 4
        PreviewPage previewPage = pageWithPanelizer.clickPreviewLink();

        List<String> actualModules = previewPage.getTitles();

        //Validation
        softAssert.assertEquals(expectedModules, actualModules, "Modules are not corrected", "Modules are corrected", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test script is passed");
    }
}
