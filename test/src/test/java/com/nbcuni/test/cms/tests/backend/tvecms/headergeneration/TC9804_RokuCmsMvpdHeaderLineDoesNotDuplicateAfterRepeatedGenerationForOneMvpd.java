package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class TC9804_RokuCmsMvpdHeaderLineDoesNotDuplicateAfterRepeatedGenerationForOneMvpd extends
        BaseAuthFlowTest {

    /**
     * TC9804 - Roku CMS: MVPD header line does not duplicate after repeated generation for one MVPD
     *
     * precondition 1 - create test taxonomy MVPD term
     * precondition 2 - create test OTT page
     *
     * Step 1: Go to Roku CMS
     * Step 2: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 3: Set mvpd, page.
     * Step 4: Click submit
     * Step 5: go to list of OTT pages (/admin/ott/pages). Click on "edit" next to test OTT page
     * Step 6: Check headers
     * Step 7: Repeat steps 2..4
     * Step 8: Check that there are no duplicates
     *
     *
     * postcondition 1 - delete test taxonomy MVPD term
     * postcondition 2 - delele test OTT page
     *
     * */

    private String name;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage();

    public void getTermTC9804() {
        Map<String, Map<String, String>> mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration();
        Set<String> ids = mvpds.keySet();
        int index = random.nextInt(ids.size());
        name = (String) ids.toArray()[index];
    }

    public void createPageTC9804() {
        // precondition 2
        rokuBackEndLayer.createPage(pageForm);
    }

    @Test(groups = {"header_burn_in", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void headerImageGenerationForOneMvpd(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        getTermTC9804();
        createPageTC9804();
        SoftAssert softAssert = new SoftAssert();
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
        // step 8
        headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(
                pageForm.getTitle());
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
        headerImageGenerationPage.elementSelectMvpd().select(name);
        // step 4
        headerImageGenerationPage.elementSubmit().clickWithProgressBarWait();
        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9804() {
        // postcondition 2
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable throwable) {
            Utilities.logWarningMessage("Couldn't clean up the content");
        }
    }
}
