package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

public class TC9981_RokuCmsVerifyHeaderCanBeGeneratedForAllMvpds extends
        BaseAuthFlowTest {

    Map<String, Map<String, String>> mvpds;
    /**
     * TC9981 - Roku CMS: Verify header can be generated for all MVPDs
     *
     * precondition 1 - create test OTT page precondition 2 - Collect
     * information about all MVPDs
     *
     * Step 1: Go to Roku CMS Step 2: Go to OTT » OTT Header Image Generation
     * (/admin/ott/image_generation/header) Step 3: Set ALL MVPDS, page. Step 4:
     * Click submit Step 5: go to list of OTT pages (/admin/ott/pages). Click on
     * "edit" next to test OTT page Step 6: Check headers
     *
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private PageForm pageForm = CreateFactoryPage.createDefaultPage();
    private String allMvpdsDdlValue = "All MVPDs";
    private File mvpdLogoFile = new File(Config.getInstance()
            .getPathToTempFiles() + File.separator + "mvpdLogoTC9981.png");
    private File cmsResult = new File(Config.getInstance().getPathToTempFiles()
            + File.separator + "cmsResultTC9981.png");
    ;
    private File generatedResult = new File(Config.getInstance()
            .getPathToTempFiles()
            + File.separator
            + "generatedResultTC9981.png");

    public void createPageAll() {
        // precondition 1
        rokuBackEndLayer.createPage(pageForm);
    }

    public void collectMvpdTerms() {
        // precondition 2
        cmsResult.deleteOnExit();
        mvpdLogoFile.deleteOnExit();
        generatedResult.deleteOnExit();
        mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration();
    }

    @Test(groups = {"header_burn_in_all", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void headerImageGenerationForOneMvpd(final String brand) {
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPageAll();
        collectMvpdTerms();
        // step 2
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 3
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        headerImageGenerationPage.elementSelectMvpd().select(
                allMvpdsDdlValue);
        // step 4
        headerImageGenerationPage.elementSubmit().clickWithProgressBarWait();
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 5
        headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        Map<String, String> headers = headerImageGenerationPage
                .getOttPageHeaders();
        softAssert.assertEquals(mvpds.size(), headers.size(),
                "Number of headers is wrong", webDriver);
        // step 6
        for (Entry<String, String> header : headers.entrySet()) {
            softAssert.assertTrue(validateHeader(header),
                    "There is error during validation " + header.getKey());
        }
        softAssert.assertAll();
    }

    private boolean validateHeader(Entry<String, String> header) {
        boolean status = true;
        try {
            Utilities.logInfoMessage("Validate header for " + header.getKey());
            String headerUrl = header.getValue();
            Assertion.assertTrue(headerUrl != null,
                    "header for mvpd " + header.getKey() + " not present",
                    webDriver);
            ImageUtil.loadImage(headerUrl, cmsResult);
            String mvpdLogo = null;
            for (Map<String, String> entry : mvpds.values()) {
                if (entry.containsKey(header.getKey())) {
                    mvpdLogo = entry.get(header.getKey());
                }
            }
            ImageUtil.loadImage(mvpdLogo, mvpdLogoFile);
            ImageUtil.createRokuHeaderImage(mvpdLogoFile, generatedResult, brand);
            Assertion.assertTrue(ImageUtil.compareScreenshots(generatedResult,
                    cmsResult, 90), "Images are not quiteSimilar");
            Assertion.assertTrue(ImageUtil.compareScreenshotsPartially(
                    generatedResult, cmsResult, 1120, 40, 1250, 80, 70),
                    "Partial images are not quiteSimilar");
        } catch (Throwable e) {
            status = false;
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        if (status = true) {
            Utilities.logInfoMessage("Validation successful");
        }
        Utilities.logSevereMessageThenFail("Expected result:");
        WebDriverUtil.getInstance(webDriver).attachImage(generatedResult);
        Utilities.logSevereMessageThenFail("Actual result:");
        WebDriverUtil.getInstance(webDriver).attachImage(cmsResult);
        cmsResult.delete();
        generatedResult.delete();
        mvpdLogoFile.delete();
        return status;
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageAll() {
        // postcondition 2
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(pageForm.getTitle()).clickSubmit();
        mainRokuAdminPage.logOut(brand);
    }
}
