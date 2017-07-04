package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class TC9801_RokuCmsOttHeaderImageGenerationForSeveralMvpds extends
        BaseAuthFlowTest {

    /**
     * TC9801 - Roku CMS: OTT Header Image Generation for several MVPDs
     *
     * precondition 1 - find out any two mvpd taxonomy terms
     * precondition 2 - create test OTT page
     *
     * Step 1 : Go to Roku CMS
     * Step 2: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 3: set: "select MVPD" = "All MVPDs" and "select page" = test page
     * Step 4: Click submit
     * Step 5: go to list of OTT pages (/admin/ott/pages). Click on "edit" next to test OTT page
     * Step 6: Open url for each test mvpd line and check correctness of the header
     *
     * postcondition 1 - delele test OTT page
     *
     * */


    private File generatedResult2;
    private String firstId;
    private String secondId;
    private File generatedResult1;
    private String allMvpdsDdlValue = "All MVPDs";
    private PageForm pageForm = CreateFactoryPage.createDefaultPage();

    public void getMvpdLogosTC9801() {
        Map<String, Map<String, String>> mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration();
        Set<String> ids = mvpds.keySet();
        int index = random.nextInt(ids.size());
        String name = (String) ids.toArray()[index];
        firstId = (String) mvpds.get(name).keySet().toArray()[0];
        String firstLogoPath = mvpds.get(name).get(firstId);
        index = random.nextInt(ids.size());
        name = (String) ids.toArray()[index];
        secondId = (String) mvpds.get(name).keySet().toArray()[0];
        String secondLogoPath = mvpds.get(name).get(secondId);
        File logoFile1 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "logo1.png");
        generatedResult1 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "generatedResult1.png");
        generatedResult1.deleteOnExit();
        ImageUtil.loadImage(firstLogoPath, logoFile1);
        ImageUtil.createRokuHeaderImage(logoFile1, generatedResult1, brand);
        logoFile1.delete();
        generatedResult2 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "generatedResult2.png");
        generatedResult2.deleteOnExit();
        File logoFile2 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "logo2.png");
        ImageUtil.loadImage(secondLogoPath, logoFile2);
        ImageUtil.createRokuHeaderImage(logoFile2, generatedResult2, brand);
        logoFile2.delete();
    }

    public void createPageTC9801() {
        // precondition 2
        rokuBackEndLayer.createPage(pageForm);
    }

    @Test(groups = {"header_burn_in"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void headerImageGenerationForOneMvpd(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        getMvpdLogosTC9801();
        createPageTC9801();
        SoftAssert softAssert = new SoftAssert();
        // step 2
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 3
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        headerImageGenerationPage.elementSelectMvpd().select(allMvpdsDdlValue);
        // step 4
        headerImageGenerationPage.elementSubmit().clickWithProgressBarWait(320);
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 5
        headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        // step 6
        Map<String, String> headers = headerImageGenerationPage.getOttPageHeaders();
        String headerUrl1 = headers.get(firstId);
        String headerUrl2 = headers.get(secondId);
        softAssert.assertTrue(headerUrl1 != null, "header for mvpd " + firstId
                + " not present", webDriver);
        softAssert.assertTrue(headerUrl2 != null, "header for mvpd " + secondId
                + " not present", webDriver);
        File cmsResult1 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "cmsResult1.png");
        cmsResult1.deleteOnExit();
        File cmsResult2 = new File(Config.getInstance().getPathToTempFiles()
                + File.separator + "cmsResult2.png");
        cmsResult2.deleteOnExit();
        ImageUtil.loadImage(headerUrl1, cmsResult1);
        ImageUtil.loadImage(headerUrl2, cmsResult2);
        softAssert.assertTrue(
                ImageUtil.compareScreenshots(generatedResult1, cmsResult1, 95),
                "Images are not quiteSimilar");
        softAssert.assertTrue(ImageUtil.compareScreenshotsPartially(
                generatedResult1, cmsResult1, 1120, 40, 1250, 80, 70),
                "Partial images are not quiteSimilar");
        softAssert.assertTrue(
                ImageUtil.compareScreenshots(generatedResult2, cmsResult2, 95),
                "Images are not quiteSimilar");
        softAssert.assertTrue(ImageUtil.compareScreenshotsPartially(
                generatedResult2, cmsResult2, 1120, 40, 1250, 80, 70),
                "Partial images are not quiteSimilar");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9801() {
        // postcondition 1
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't clean up the content");
        }
    }
}
