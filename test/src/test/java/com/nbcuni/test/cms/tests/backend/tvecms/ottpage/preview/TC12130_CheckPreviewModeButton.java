package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 * TC12130
 *
 * Ste 1: Go To roku CMS as Admin
 *
 * Step 2: Go to Pages list page(admin/ott/pages)
 *
 * Step 3: Click edit random page
 *
 * Validation:    Check Preview button
 * Expected result: Preview button is present
 */
public class TC12130_CheckPreviewModeButton extends BaseAuthFlowTest {

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkPreviewModeButton(final String brand) {
        this.brand = brand;

        //Step 1
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //Step 2 - 4
        EditPageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openEditRandomPageWithPanelizer();

        //Validation
        softAssert.assertTrue(pageWithPanelizer.isPreviewLinkPresent(), "Preview Link is not present",
                "Preview Link is present");

        softAssert.assertTrue(pageWithPanelizer.isEditLinkPresent(), "Edit Link is not present",
                "Edit Link is present");

        softAssert.assertAll();
    }
}
