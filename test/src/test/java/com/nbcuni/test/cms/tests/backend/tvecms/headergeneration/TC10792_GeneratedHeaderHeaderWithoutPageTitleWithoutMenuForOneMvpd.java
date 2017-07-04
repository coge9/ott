package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class TC10792_GeneratedHeaderHeaderWithoutPageTitleWithoutMenuForOneMvpd extends
        BaseAuthFlowTest {


    private static PageForm pageForm = null;
    Map<String, Map<String, String>> mvpds;
    /**
     * TC10792 - Roku CMS: Generated header "Header without Page Title without menu" for one MVPD
     * <p>
     * precondition 1 - create test OTT page
     * <p>
     * Step 1: Go to OTT » OTT Header Image Generation
     * Step 2: Select page created in precondition
     * Step 3: Select "Header without Page Title without menu" in Header style DDL
     * Step 4: Select any mvpd
     * Step 5: Click Generate Headers
     * Step 6: Verify generated headers
     * <p>
     * postcondition 1 - delele test OTT page
     */

    private String pageTitle = "HG Without T Without M";
    private String name = "Verizon";
    private String id = "Verizon";
    private File verizonExpHeader = null;

    public void createPageAll() {
        // precondition 1
        verizonExpHeader = new File(Config.getInstance()
                .getPathToRokuHeaderGenerationImages(brand)
                + "Verizon_WithoutMenuWithoutTitle.png");
        pageForm = CreateFactoryPage.createDefaultPageWithMachineNameAndAlias()
                .setTitle(pageTitle).setPlatform(CmsPlatforms.ROKU);

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
        headerImageGenerationPage.generateOttPageHeaders(pageTitle,
                HeaderStyle.WITHOUT_TITLE_WITHOUT_MENU, name);
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
        tvePage.clickDelete(pageTitle).clickSubmit();
        mainRokuAdminPage.logOut(brand);
    }
}
