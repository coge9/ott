package com.nbcuni.test.cms.tests.backend.tvecms.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/23/15.
 */
public class TC11620_CreationPanelizer extends BaseAuthFlowTest {


    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private String panelizerName;

    /**
     * Step 1: Go To roku CMS as Admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2: Navigate to the Panelizer Management Page (admin/ott/pages/manage/ott_page/panelizer/page_manager)
     * Verify: The Page with list of existing Panelizer is present
     * <p/>
     * Step 3: Click link 'Add'
     * Verify: The Add Panelizer Page is present
     * <p/>
     * Step 4: Fill all fields and save
     * Verify: The new Panelizer is saved and present in the table
     */


    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatPossibleToCreatePanelizer(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);

        //Step 3
        panelizerName = backEndLayer.createPanelizer(panelizerManagerPage);

        //Step 4
        softAssert.assertTrue(panelizerManagerPage.isPanelizerExist(panelizerName), "Panelizer is not present at the list",
                "The new panelizer was created", webDriver);
        softAssert.assertTrue(panelizerManagerPage.isAddPanelizerLinkExist(), "The Add Panelizer link is not Present", "The Add Panelizer link is Present", webDriver);
        softAssert.assertTrue(panelizerManagerPage.isImportPanelizerLinkExist(), "The Import Panelizer link is not Present", "The Import Panelizer link is Present", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizer() {
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        panelizerManagerPage.deletePanelizer(panelizerName);
        Assertion.assertFalse(panelizerManagerPage.isPanelizerExist(panelizerName), "The panelizer still present", webDriver);
        Utilities.logInfoMessage("The panelizer was delete successfully");
    }
}
