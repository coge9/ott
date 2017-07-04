package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TC9805_RokuCmsMvpdHeaderLineDoesNotDuplicateAfterRepeatedGenerationForAllMvpds extends
        BaseAuthFlowTest {

    /**
     * TC9805 - Roku CMS: MVPD header line does not duplicate after repeated generation for one ALL MVPDS
     *
     * precondition 1 - create test OTT page
     *
     * Step 1: Go to Roku CMS
     * Step 2: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 3: Set ALL MVPDS, page.
     * Step 4: Click submit
     * Step 5: go to list of OTT pages (/admin/ott/pages). Click on "edit" next to test OTT page
     * Step 6: Check headers
     * Step 7: Repeat steps 2..4
     * Step 8: Check that there are no duplicates
     *
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private PageForm pageForm = CreateFactoryPage.createDefaultPageWithMachineNameAndAlias();
    private String allMvpdsDdlValue = "All MVPDs";

    public void createPageTC9805() {
        // precondition 1
        rokuBackEndLayer.createPage(pageForm);
    }

    @Test(groups = {"header_burn_in"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void headerImageGenerationForOneMvpd(String brand) {
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createPageTC9805();
        generateHeaders();
        // step 5
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        // step 6
        int headerSize = headerImageGenerationPage.getOttPageHeaders().size();
        for (int i = 0; i < 3; i++) {
            generateHeaders();
        }
        headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(pageForm.getTitle());
        softAssert.assertEquals(headerSize, headerImageGenerationPage.getOttPageHeaders().size(), "Humber of headers is wrong", webDriver);
        softAssert.assertFalse(headerImageGenerationPage.areAnyDuplicatesInHeaders(), "There are some duplicates in headers", webDriver);
        softAssert.assertAll();
    }

    private void generateHeaders() {
        // step 2
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 3
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
        headerImageGenerationPage.elementSelectMvpd().select(allMvpdsDdlValue);
        // step 4
        headerImageGenerationPage.elementSubmit().clickWithProgressBarWait();
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9805() {
        // postcondition 1
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(pageForm.getTitle()).clickSubmit();
        mainRokuAdminPage.logOut(brand);
    }
}
