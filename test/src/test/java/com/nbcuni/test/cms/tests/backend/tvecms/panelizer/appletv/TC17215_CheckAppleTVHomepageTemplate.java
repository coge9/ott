package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.appletv;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.contentpage.MainContentPanelizerPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 * TC17215
 * <p>
 * Step 1: Go to CMS as Admin
 * <p>
 * Step 2:
 * Go to panelizer template list (admin/config/content/panelizer/ott_page/ott_page.page_manager/list)
 * Verify:Page panelizer template list is present
 * <p>
 * Step 3: Expand "OPERATIONS" dropdown next to AppleTV Homepage layout
 Click on "Content" option
 Verify: Content page is open
 * <p>
 * Step 4: Check rows
 * Validation: First row is present and named as  "Feature carousel"
 other rows are present and named as: First row, Second row, ... Twentieth row
 * <p>
 */

public class TC17215_CheckAppleTVHomepageTemplate extends BaseAuthFlowTest {

    PanelizerTemplates panelizerTemplate = PanelizerTemplates.APPLETV_HOMEPAGE;
    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private PanelizerManagerPage panelizerManagerPage;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkAppleTvHomePageTemplate(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);

        //Step 3
        panelizerManagerPage.clickContentForPanelizer(panelizerTemplate.getTitle());

        MainContentPanelizerPage contentPanelizerBlock = new MainContentPanelizerPage(webDriver, aid, panelizerTemplate);

        //Validation
        contentPanelizerBlock.checkLayout(softAssert);

        softAssert.assertAll();
    }
}
