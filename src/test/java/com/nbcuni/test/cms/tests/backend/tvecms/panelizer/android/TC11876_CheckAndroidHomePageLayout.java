package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.android;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.LayoutPanelizerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.contentpage.MainContentPanelizerPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * TC11876
 *
 * Step 1: Go to Roku as Admin
 *
 * Step 2: Go To Panelizer Page Configuration » Content Authoring » Panelizer
 *
 * Step 3: Navigate to OTT PAGE TYPE and click "list"
 *
 * Step 4: Click Add button, type panelizer title and save
 *
 * Step 5: Go to Layout for our template and select Android Home page layout, click Continue » Save
 *
 * Validation: Check Header area
 * Check 4 column
 * Expected result: Header area is present
 * 4 column is present
 */

public class TC11876_CheckAndroidHomePageLayout extends BaseAuthFlowTest {

    PanelizerTemplates panelizerTemplate = PanelizerTemplates.ANDROID_HOME_PAGE;
    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private String panelizerName;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatPossibleToCreatePanelizer(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);

        //Step 3 - 5
        panelizerName = backEndLayer.createPanelizer(panelizerManagerPage);

        //Validation
        LayoutPanelizerPage layoutPanelizerPage = panelizerManagerPage.clickLayoutForPanelizer(panelizerName);

        MainContentPanelizerPage contentPanelizerBlock = layoutPanelizerPage.createTemplate(panelizerTemplate);

        contentPanelizerBlock.checkLayout(softAssert);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizer() {
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
        panelizerManagerPage.deletePanelizer(panelizerName);
        Assertion.assertFalse(panelizerManagerPage.isPanelizerExist(panelizerName), "The panelizer still present", webDriver);
        Utilities.logInfoMessage("The panelizer was delete successfully");
    }
}
