package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.appletv;

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
 * TC17214
 * <p>
 * Step 1: Go to CMS as Admin
 * <p>
 * Step 2:
 * Go to Dashboard -> Pages -> Panelizer -> List (/admin/ott/pages/manage/ott_page/panelizer/page_manager/list)
 * <p>
 * Step 3: Click on Add button
 * Fill "Administrative title" field
 * Click on "Save"
 * <p>
 * Step 4: Expand "OPERATIONS" dropdown next to created layout
 * Click on "Layout" option
 * Set "Category = "Page layouts: AppleTV"
 * Select "All Shows" radiobutton
 * Click on "Continue"
 * <p>
 * Step 5: Check layout
 * Check drag'n'drop modules functionality
 * <p>
 * Validation: One row is present and named as "All shows"w
 * <p>
 * Can be possible to drag'n'drop modules
 */

public class TC17214_CheckAppleTVShowsPageLayout extends BaseAuthFlowTest {

    PanelizerTemplates panelizerTemplate = PanelizerTemplates.APPLETV_ALL_SHOWS;
    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private PanelizerManagerPage panelizerManagerPage;
    private String panelizerName;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkAppleTvShowsPageLayout(final String brand) {
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

        contentPanelizerBlock.checkMoveBlock(softAssert);

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
