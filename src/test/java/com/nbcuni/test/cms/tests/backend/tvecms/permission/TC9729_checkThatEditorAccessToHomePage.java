package com.nbcuni.test.cms.tests.backend.tvecms.permission;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PageNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;


public class TC9729_checkThatEditorAccessToHomePage extends BaseAuthFlowTest {
    private RokuBackEndLayer backEndLayer = null;
    private MainRokuAdminPage mainRokuAdminPage = null;
    private SoftAssert softAssert = new SoftAssert();
    private PageForm textExpected;
    private PageForm textActual;

    /**
     * Steps:
     * Step 1. Go to roku admin side as Editor
     * Verify: User is logged in as admin. Admin panel is present
     * <p/>
     * Step 2: Go to admin/ott/pages page
     * Verify:The list of pages is present.Home page exist in the list of pages
     * <p/>
     * Step 3. Select Home Page and click 'edit'
     * Verify: The edit home Page is opened
     * <p/>
     * Step 4: Check fields
     * Verify: All fields including Shelf section are present
     * <p/>
     * step 5: Updated data in the home Page and save
     * Verify: The data is updated
     */

    @Test(groups = {"permissions", "regression"}, enabled = true, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatHomePageAvailableForAdmin(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Pre-condition Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        Assertion.assertTrue(tvePage.isPageExist(PageNames.HOME.getName()), "The Home Page is not present at the Pages list", webDriver);
        Utilities.logInfoMessage("The Home Page is present at the pages list");

        //Step 3
        EditPageWithPanelizer editPage = tvePage.clickEdit(PageNames.HOME.getName());
        softAssert.assertContains(webDriver.getTitle(), PageNames.HOME.getName(), "The Edit Home Page is opened", "The Edit HomePage is not opened", webDriver);


        //Step 4
        softAssert.assertTrue(editPage.verifyPage().isEmpty(), "Some Elements are missed from the Edit Home Page", "The Edit Page contains all appropriate fields", webDriver);
        softAssert.assertAll();

    }
}
