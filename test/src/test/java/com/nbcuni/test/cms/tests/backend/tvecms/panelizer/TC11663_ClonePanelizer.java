package com.nbcuni.test.cms.tests.backend.tvecms.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.AddPanelizerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.SettingsPanelizerPage;
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
public class TC11663_ClonePanelizer extends BaseAuthFlowTest {


    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private String initPanelizerName;
    private String newPanelizer;
    private SettingsPanelizerPage.Info settingsInfo;

    /**
     * Pre-condition
     * Create a Panelizer test Panelizer
     *
     * Step 1: Go To roku CMS as Admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2: Navigate to the Panelizer Management Page (admin/ott/pages/manage/ott_page/panelizer/page_manager)
     * Verify: The Page with list of existing Panelizer is present
     * testPanelizer ,created in pre-condition is present at the list
     * <p/>
     * Step 3: Click link 'Clone' next to panelizer name
     * Verify: The Page with title setup fpr panelizer is present
     * <p/>
     * Step 4: Update title and save
     * Verify: There is a new panelizer within list of panelizers
     */


    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatPossibleToClonePanelizer(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-Condition and Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
        initPanelizerName = backEndLayer.createPanelizer(panelizerManagerPage);

        //Step 3
        AddPanelizerPage addPanelizerPage = panelizerManagerPage.clonePanelizer(initPanelizerName);

        //Step 4
        newPanelizer = addPanelizerPage.inputTitle().save();
        mainRokuAdminPage.openPanelizerManagementPage(brand);
        softAssert.assertTrue(panelizerManagerPage.isPanelizerExist(newPanelizer), "The new cloned panelizer is not present at the list",
                "The new cloned panelizer is present at the list", webDriver);
        softAssert.assertTrue(panelizerManagerPage.isPanelizerExist(initPanelizerName), "The initial panelizer, from each clone operation was performed is not present at the list",
                "The initial panelizer, from each clone operation was performed is present at the list", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizer() {
        try {
            backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            mainRokuAdminPage = backEndLayer.openAdminPage();
            panelizerManagerPage.deletePanelizer(initPanelizerName);
            Assertion.assertFalse(panelizerManagerPage.isPanelizerExist(initPanelizerName), "The panelizer still present", webDriver);
            Utilities.logInfoMessage("The panelizer was delete successfully");
            panelizerManagerPage.deletePanelizer(newPanelizer);
            Assertion.assertFalse(panelizerManagerPage.isPanelizerExist(newPanelizer), "The panelizer still present", webDriver);
            Utilities.logInfoMessage("The panelizer was delete successfully");
        } catch (Throwable throwable) {
            Utilities.logSevereMessage("Shit happens" + throwable.getStackTrace());
        }
    }
}
