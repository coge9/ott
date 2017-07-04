package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.ios;

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
 * TC15469
 *
 * Step 1: Go to Roku as Admin
 *
 * Step 2: Go to panelizer template list (admin/config/content/panelizer/ott_page/ott_page.page_manager/list)
 *
 * Step 3: Open content for IOS home page template
 *
 * Validation: Check Header area
 * Check 4 column
 * Expected result: Header area is present
 * 4 column is present
 */

public class TC15469_CheckIosHomePageTemplate extends BaseAuthFlowTest {

    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private PanelizerTemplates panelizerTemplates = PanelizerTemplates.IOS_HOME_PAGE;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkAndroidHomePageTemplate(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);

        //Step 3
        panelizerManagerPage.clickContentForPanelizer(panelizerTemplates.getTitle());

        MainContentPanelizerPage contentPanelizerBlock = new MainContentPanelizerPage(webDriver, aid, panelizerTemplates);

        //Validation
        contentPanelizerBlock.checkLayout(softAssert);

        softAssert.assertAll();
    }
}
