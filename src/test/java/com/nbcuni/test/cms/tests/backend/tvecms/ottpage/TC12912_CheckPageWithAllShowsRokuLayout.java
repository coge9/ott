package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
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
public class TC12912_CheckPageWithAllShowsRokuLayout extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private PanelizerTemplates panelizerTemplates = PanelizerTemplates.ROKU_ALL_SHOWS;
    private String templateName;

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkThatPossibleAssignPanelizerToPage(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MainRokuAdminPage mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        templateName = rokuBackEndLayer.createPanelizerTemplate(panelizerTemplates);

        //Step 2 - 4
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        AddNewPage addNewPage = tvePage.clickAddNewPage();

        addNewPage.setAllRequiredFields();
        addNewPage.selectPanelizerTemplate(templateName);
        addNewPage.submit();
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);
        pageInfo = editPage.getAllFieldsForPage();
        editPage.save();


        EditPageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle(), panelizerTemplates);

        //Validation
        pageWithPanelizer.checkLayout(softAssert);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizer() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        PanelizerManagerPage panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
        panelizerManagerPage.deletePanelizer(templateName);
        Assertion.assertFalse(panelizerManagerPage.isPanelizerExist(templateName), "The panelizer still present", webDriver);
        Utilities.logInfoMessage("The panelizer was delete successfully");
    }
}
