package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.roku;

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
 * Created by Ivan_Karnilau on 16-Feb-16.
 */
public class TC12911_CheckRokuAllShowsPageLayout extends BaseAuthFlowTest {

    PanelizerTemplates panelizerTemplate = PanelizerTemplates.ROKU_ALL_SHOWS;
    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PanelizerManagerPage panelizerManagerPage;
    private String panelizerName;

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatPossibleToCreatePanelizer(final String brand) {
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
