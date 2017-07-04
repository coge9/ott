package com.nbcuni.test.cms.tests.smoke.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
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
import java.util.Random;
import java.util.Set;

public class TC9800_RokuCmsOttHeaderImageGenerationForOneMvpd extends BaseAuthFlowTest {

    /**
     * TC9800 - Roku CMS: OTT Header Image Generation for one MVPD
     * <p>
     * precondition 1 - create test taxonomy MVPD term
     * precondition 2 - create test OTT page
     * <p>
     * Step 1 : Go to Roku CMS
     * Step 2: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 3: Set mvpd, page.
     * Step 4: Click submit
     * Step 5: go to list of OTT pages (/admin/ott/pages). Click on "edit" next to test OTT page
     * Step 6: Open url from test mvpd line and check correctness of the header
     * <p>
     * <p>
     * postcondition 1 - delete test taxonomy MVPD term
     * postcondition 2 - delele test OTT page
     */
    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private String id;
    private String name;
    private Random random = new Random();
    private PageForm pageInfo;
    private File generatedResult;

    public void createTermTC9800() {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        Map<String, Map<String, String>> mvpds = backEndLayer.getExpectedMvpdsForHeaderGeneration();
        // precondition 1
        File logoImage = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "logoImage_" + timeStamp + ".png");
        generatedResult = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "generatedResult_" + timeStamp + ".png");
        logoImage.deleteOnExit();
        generatedResult.deleteOnExit();
        Set<String> ids = mvpds.keySet();
        int index = random.nextInt(ids.size());
        name = (String) ids.toArray()[index];
        id = (String) mvpds.get(name).keySet().toArray()[0];
        String url = mvpds.get(name).get(id);
        ImageUtil.loadImage(url, logoImage);
        ImageUtil.createRokuHeaderImage(logoImage, generatedResult, brand);
    }

    @Test(groups = {"header_burn_in", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void headerImageGenerationForOneMvpd(String brand) {
        this.brand = brand;
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        createTermTC9800();
        SoftAssert softAssert = new SoftAssert();
        // precondition 2
        pageInfo = backEndLayer.createPage(CmsPlatforms.ROKU);

        //Step 1
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 3 - 4
        headerImageGenerationPage.generateOttPageHeaders(pageInfo.getTitle(), HeaderStyle.WITHOUT_TITLE_WITH_MENU, name);

        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 5 - 6
        Map<String, String> headers = headerImageGenerationPage.getOttPageHeaders(pageInfo.getTitle());
        softAssert.assertEquals(1, headers.size(),
                "Number of headers is wrong", webDriver);
        String headerUrl = headers.get(id);
        softAssert.assertTrue(headerUrl != null, "header for mvpd " + id
                + " not present", webDriver);
        File cmsResult = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "cmsResult_" + System.currentTimeMillis() + ".png");
        ImageUtil.loadImage(headerUrl, cmsResult);
        cmsResult.deleteOnExit();
        boolean status = ImageUtil.compareScreenshots(generatedResult, cmsResult, 95);
        if (!status) {
            Utilities.logWarningMessage("Expected result:");
            WebDriverUtil.getInstance(webDriver).attachImage(generatedResult);
            Utilities.logWarningMessage("Actual result:");
            WebDriverUtil.getInstance(webDriver).attachImage(cmsResult);
        }
        softAssert.assertTrue(status,
                "Images are not quiteSimilar");
        softAssert.assertTrue(ImageUtil.compareScreenshotsPartially(
                generatedResult, cmsResult, 1120, 40, 1250, 80, 70),
                "Partial images are not quiteSimilar");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9800() {
        try {
            // postcondition 2
            if (pageInfo != null) {
                rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear-down method " + e.getMessage());
        }

    }
}
