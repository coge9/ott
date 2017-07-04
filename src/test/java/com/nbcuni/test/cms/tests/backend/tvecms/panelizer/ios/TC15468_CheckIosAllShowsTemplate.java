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
 * TC15468
 *
 * Step 1: Go to Roku as Admin
 *
 * Step 2: Go to panelizer template list (admin/config/content/panelizer/ott_page/ott_page.page_manager/list)
 *
 * Step 3: Open content for IOS all shows template
 *
 * Validation: Check Header area
 * Check 4 column
 * Expected result: Header area is present
 * 4 column is present
 */
public class TC15468_CheckIosAllShowsTemplate extends BaseAuthFlowTest {

    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private PanelizerTemplates panelizerTemplates = PanelizerTemplates.IOS_ALL_SHOWS;

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

        MainContentPanelizerPage contentPanelizerPage = new MainContentPanelizerPage(webDriver, aid, PanelizerTemplates.ANDROID_ALL_SHOWS);

        //Validation
        contentPanelizerPage.checkLayout(softAssert);

        softAssert.assertAll();
    }
}
