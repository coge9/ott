package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class TC10470_GeneratedHeaderWithoutPageTitleForOneMvpd extends
        BaseAuthFlowTest {

    private static PageForm pageForm = null;
    Map<String, Map<String, String>> mvpds;
    /**
     * TC10470 - Roku CMS: Generated header without Page title for one MVPD
     *
     * precondition 1 - create test OTT page
     *
     * Step 1: Go to OTT » OTT Header Image Generation
     * Step 2: Select page created in precondition
     * Step 3: Select "Header without page title" in Header style DDL
     * Step 4: Select any mvpd
     * Step 5: Click Generate Headers
     * Step 6: Verify generated headers
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private String name = "Verizon";
    private String id = "Verizon";
    private File verizonExpHeader = null;

    public void createPageAll() {
        // precondition 1
        verizonExpHeader = new File(Config.getInstance()
                .getPathToRokuHeaderGenerationImages(brand)
                + "Verizon_WithMenuWithoutTitle.png");
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
                HeaderStyle.WITHOUT_TITLE_WITH_MENU, name);
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 6
        Map<String, String> headers = headerImageGenerationPage
                .getOttPageHeaders();
        Assertion.assertTrue(
                ImageUtil.compareScreenshotAndUrl(verizonExpHeader,
                        headers.get(id), 100.0), "Header for " + name
                        + "doesn't much with expected");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageAll() {
        // postcondition 1
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(pageForm.getTitle()).clickSubmit();
        mainRokuAdminPage.logOut(brand);
    }
}
