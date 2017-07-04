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
 * TC11966
 * <p>
 * Step 1: Go to Roku as Admin
 * <p>
 * Step 2: Go to panelizer template list (admin/config/content/panelizer/ott_page/ott_page.page_manager/list)
 * <p>
 * Step 3: Expand "OPERATIONS" dropdown next to AppleTV AllShows layout
 * Click on "Content" option
 * <p>
 * Validation:     Check rows
 * Expected result: One row is present and named as "All shows
 */
public class TC17216_CheckAppleTVAllShowsTemplate extends BaseAuthFlowTest {

    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private PanelizerTemplates panelizerTemplates = PanelizerTemplates.APPLETV_ALL_SHOWS;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
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

        MainContentPanelizerPage contentPanelizerPage = new MainContentPanelizerPage(webDriver, aid, PanelizerTemplates.APPLETV_ALL_SHOWS);

        //Validation
        contentPanelizerPage.checkLayout(softAssert);

        softAssert.assertAll();
    }
}
