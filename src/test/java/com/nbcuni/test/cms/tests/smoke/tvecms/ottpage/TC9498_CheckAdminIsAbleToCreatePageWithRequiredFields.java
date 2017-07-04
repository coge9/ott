package com.nbcuni.test.cms.tests.smoke.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 8/28/15.
 */
public class TC9498_CheckAdminIsAbleToCreatePageWithRequiredFields extends BaseAuthFlowTest {
    public PageForm pageForm = null;
    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2:Go to OOT Pages
     * Verify: user should be on OTT pages tab, List of pages should be presented
     * <p/>
     * Step 3: Click ADD new OTT Page
     * Verify: New page form should be presented
     * <p/>
     * Step 4: fill all required fields and click submit button
     * Verify: New page should be presented in the list with appropriate name
     */

    @Test(groups = {"create_roku_page", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldCreateNewPage(final String brand) {
        Utilities.logInfoMessage("Check that user is able to create a Page with required fields");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        pageForm = backEndLayer.createPage();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage9498() {
        try {
            backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            backEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
