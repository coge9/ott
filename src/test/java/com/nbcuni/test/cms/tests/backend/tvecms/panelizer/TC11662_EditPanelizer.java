package com.nbcuni.test.cms.tests.backend.tvecms.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.SettingsPanelizerPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/23/15.
 */
public class TC11662_EditPanelizer extends BaseAuthFlowTest {


    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private String panelizerName;
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
     * Step 3: Click link 'Settings' next to panelizer name
     * Verify: The Settings tab of panelizer is opened
     * <p/>
     * Step 4: Update title and value for the settings and save
     * Verify: The values was saved
     *
     * Step 5: Go To Panelizer Management Page and check title
     * Verify: There is panelizer with updated title within the list
     */


    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatPossibleToEditPanelizer(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-Condition and Step 2
        panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
        panelizerName = backEndLayer.createPanelizer(panelizerManagerPage);

        //Step 3
        SettingsPanelizerPage settingsPanelizerPage = panelizerManagerPage.clickSettingForPanelizer(panelizerName);

        //Step 4
        settingsInfo = settingsPanelizerPage.updateSettings();

        //Step 5
        mainRokuAdminPage.openPanelizerManagementPage(brand);
        softAssert.assertTrue(panelizerManagerPage.isPanelizerExist(settingsInfo.getTitle()), "There is no panelizer with updated Info",
                "Thre is a panelizer with updated info", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present", "There is no error messages", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizerTC11662() {
        try {
            backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            mainRokuAdminPage = backEndLayer.openAdminPage();
            panelizerManagerPage.deletePanelizer(settingsInfo.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("The panelizer wasn't delete successfully");
        }
        Utilities.logInfoMessage("The panelizer was delete successfully");
    }
}
