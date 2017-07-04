package com.nbcuni.test.cms.tests.backend.tvecms.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/23/15.
 */
public class TC11621_AssignPanelizerToThePage extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private String panelizerName;
    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;

    /**
     * Pre-Condition:
     * <p/>
     * Step 1: Go To roku CMS as Admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2: Navigate to the OTT Page list and create a Page
     * Verify: The testPage is created
     * <p/>
     * Step 3: Navigate to the Panelizer Management Page (admin/ott/pages/manage/ott_page/panelizer/page_manager)
     * Verify: The list of Panelizers is present
     * <p/>
     * Step 4: Create a Panelizer
     * Verify: The testPanelizer is created
     * <p/>
     * Step 5: Go To List of Pages admin/ott/pages
     * Verify: The list of Pages is exist. There is created in pre-condition testPage
     * <p/>
     * Step 6: Open testPage on edit
     * Verify: Edit Page is opened. There is section 'panelizer'
     * <p/>
     * Step 7: Collapse out section 'panelizer' and check values within drop-down
     * Verify: There is value 'testPanelizer'
     * It's possible to apply to the Page
     */

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatPossibleAssignPanelizerToPage(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 3
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);

        //Step 4
        panelizerName = backEndLayer.createPanelizer(panelizerManagerPage);

        //Step 5
        TVEPage TVEPage = mainRokuAdminPage.openOttPage(brand);
        AddNewPage addNewPage = TVEPage.clickAddNewPage();
        softAssert.assertTrue(addNewPage.isPanelizerPresentAtDDL(panelizerName), "The created panelizer in pre-condition is not present at the OTT Page DDL",
                "The created panelizer in pre-condition is present at the OTT Page DDL", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizerTC11621() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            mainRokuAdminPage = backEndLayer.openAdminPage();
            panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
            panelizerManagerPage.deletePanelizer(panelizerName);
        } catch (Exception e) {
            Utilities.logWarningMessage("The panelizer wasn't delete successfully");
        }
        Utilities.logInfoMessage("The panelizer was delete successfully");
    }
}
