package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TC9509_CheckAdminIsAbleToCancelPageDelete extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm;
    private TVEPage tvePage;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2:Go to OOT Pages
     * Verify: user should be on OTT pages tab, List of pages should be preesented
     * <p/>
     * Step 3: find already created page and click delete link
     * Verify: Confirmation page should be presented
     * <p/>
     * Step 4: Click cancel button
     * Verify: Page shouldn't be deleted
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkThatAdminCouldCancelDeletingPage(final String brand) {
        Utilities.logInfoMessage("Check that user is able to cancel deleting an Page");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        pageForm = rokuBackEndLayer.createPage();

        //Step 3
        tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        tvePage.clickDelete(pageForm.getTitle()).clickCancel();
        softAssert.assertTrue(tvePage.isPageExist(pageForm.getTitle()), "The new page is not present after cancel delete",
                "The new page is not deleted", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9509() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }
}
