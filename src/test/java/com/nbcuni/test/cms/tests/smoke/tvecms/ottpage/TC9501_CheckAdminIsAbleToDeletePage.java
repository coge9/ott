package com.nbcuni.test.cms.tests.smoke.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

public class TC9501_CheckAdminIsAbleToDeletePage extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo;
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
     * Step 4: Confirm deletion
     * Verify: Page should be deleted
     */


    @Test(groups = {"roku_page", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldDeletePage(final String brand) {

        Utilities.logInfoMessage("Check that user is able to dalete an Page");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        backEndLayer.openAdminPage();

        //Step 2
        pageInfo = backEndLayer.createPage();

        //3
        tvePage = backEndLayer.deleteOttPage(pageInfo.getTitle());
        softAssert.assertFalse(tvePage.isPageExist(pageInfo.getTitle()), "The new page is still present after delete",
                "The new page is deleted", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");

    }

}
