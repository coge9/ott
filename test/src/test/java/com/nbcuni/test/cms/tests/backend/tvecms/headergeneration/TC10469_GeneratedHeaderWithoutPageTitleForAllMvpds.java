package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class TC10469_GeneratedHeaderWithoutPageTitleForAllMvpds extends
        BaseAuthFlowTest {

    Map<String, Map<String, String>> mvpds;
    /**
     * TC10469 - Roku CMS: Generated header without Page title for several MVPD
     *
     * precondition 1 - create test OTT page
     *
     * Step 1: Go to OTT » OTT Header Image Generation
     * Step 2: Select page created in precondition
     * Step 3: Select "Header without page title" in Header style DDL
     * Step 4: Select "All mvpd"
     * Step 5: Click Generate Headers
     * Step 6: Verify generated headers
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private String allMvpdsDdlValue = "All MVPDs";
    private String name1 = "Verizon";
    private String id1 = "Verizon";
    private String name2 = "Optimum";
    private String id2 = "Cablevision";
    private File verizonExpHeader = null;
    private File optimumExpHeader = null;
    private PageForm pageForm = null;

    public void createPageAll() {
        // precondition 1
        verizonExpHeader = new File(Config.getInstance()
                .getPathToRokuHeaderGenerationImages(brand)
                + "Verizon_WithMenuWithoutTitle.png");
        optimumExpHeader = new File(Config.getInstance()
                .getPathToRokuHeaderGenerationImages(brand)
                + "Cablevision_WithMenuWithoutTitle.png");
        pageForm = CreateFactoryPage.createDefaultPageWithMachineNameAndAlias();

        rokuBackEndLayer.createPage(pageForm);
    }

    @Test(groups = {"header_burn_in", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void headerImageGenerationForOneMvpd(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createPageAll();
        // step 1
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 2 - 5
        headerImageGenerationPage.generateOttPageHeaders(pageForm.getTitle(),
                HeaderStyle.WITHOUT_TITLE_WITH_MENU, allMvpdsDdlValue);
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 6
        Map<String, String> headers = headerImageGenerationPage.getOttPageHeaders();
        softAssert.assertTrue(
                ImageUtil.compareScreenshotAndUrl(verizonExpHeader,
                        headers.get(id1), 100.0), "Header for " + name1
                        + "doesn't much with expected");
        softAssert.assertTrue(
                ImageUtil.compareScreenshotAndUrl(optimumExpHeader,
                        headers.get(id2), 100.0), "Header for " + name2
                        + "doesn't much with expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10469() {
        // postcondition 1
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't clean up the content");
        }
    }
}
