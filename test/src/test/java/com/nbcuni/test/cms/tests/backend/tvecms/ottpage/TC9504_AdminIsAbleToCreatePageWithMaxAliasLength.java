package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TC9504_AdminIsAbleToCreatePageWithMaxAliasLength extends BaseAuthFlowTest {
    public Slug alias = new Slug().setAutoSlug(false).setSlugValue(SimpleUtils.getRandomString(128));
    private MainRokuAdminPage mainRokuAdminPage;
    private TVEPage tvePage;
    private PageForm pageForm;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2:Go to OOT Pages
     * Verify: user should be on OTT pages tab, List of pages should be presented
     * <p/>
     * Step 3: click ADD new OTT Page
     * Verify: New page form should be presented
     * <p/>
     * Step 4: Fill title and 128 symbols in Alias field. Submit
     * Verify: Page should be present at the page list
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkAdminCouldCreatePageWithMaxAliasLength(final String brand) {
        Utilities.logInfoMessage("Check that user is able to create an Page with max Type Length");
        SoftAssert softAssert = new SoftAssert();
        pageForm = CreateFactoryPage.createDefaultPage().setAlias(alias).setPlatform(CmsPlatforms.IOS);

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2-3
        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

        //Step 4
        tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        softAssert.assertTrue(tvePage.isPageExist(pageForm.getTitle()), "The new page is not added or page title: " + pageForm.getTitle() + " is not matched with existen in the table",
                "The new page is created", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9504() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }
}
