package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 8/28/15.
 */
public class TC9499_CheckAdminIsAbleToCreatePageWithAllFields extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm;
    private TVEPage tvePage;
    private DraftModuleTab draftModuleTab;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2:Go to OOT Pages
     * Verify: user should be on OTT pages tab, List of pages should be preesented
     * <p/>
     * Step 3: Click ADD new OTT Page
     * Verify: New page form should be presented
     * <p/>
     * Step 4: fill title,type and Status,Short Description,Settings Add a shelf and an Item to Context,Save page
     * Verify: New page should be presented in the list with appropriate name and parameters
     */

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatAdminCouldCreatePageWithAllFields(final String brand) {
        Utilities.logInfoMessage("Check that user is able to create an admin Page with all settings");
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition create Shelf and get an Item
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        //Step 2
        pageForm = CreateFactoryPage.createDefaultPageWithMachineNameAndAlias().setModules(Arrays.asList(shelf)).setPlatform(CmsPlatforms.IOS);
        rokuBackEndLayer.createPage(pageForm);

        tvePage = mainRokuAdminPage.openOttPage(brand);
        softAssert.assertTrue(tvePage.isPageExist(pageForm.getTitle()), "The new page is not added or page title: " + pageForm.getTitle() + " is not matched with existen in the table",
                "The new page is created", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPageAndShelf() {
        try {
            tvePage = mainRokuAdminPage.openOttPage(brand);
            tvePage.clickDelete(pageForm.getTitle()).clickSubmit();
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }

}
