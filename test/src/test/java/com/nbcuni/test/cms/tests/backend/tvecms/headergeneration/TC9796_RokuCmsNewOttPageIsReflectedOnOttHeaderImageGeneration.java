package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


public class TC9796_RokuCmsNewOttPageIsReflectedOnOttHeaderImageGeneration extends BaseAuthFlowTest {

    private PageForm pageForm = CreateFactoryPage.createDefaultPage();


    /**
     * TC9796 - Roku CMS: New OTT page is reflected on "OTT Header Image Generation" form
     *
     *
     * Step 1: Go to Roku CMS
     * Step 2: Create new OTT Page
     * Step 3: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 4: Check "select Page" dropdown for new Page
     * Step 5: Select new Page value
     * Step 6. Verify Value is selected
     *
     * */

    @Test(groups = {"header_burn_in", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void verifyNewPagePresentOnImageGenerationPage(String brand) {
        // step 2
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createPage(pageForm);
        // step 3
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 4
        Assertion.assertTrue(headerImageGenerationPage.elementSelectPage()
                .isValuePresentInDropDown(pageForm.getTitle()), "Page " + pageForm.getTitle()
                + " is not present in Drop down");
        // step 5
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(pageForm.getTitle());
        // step 6
        Assertion.assertEquals(headerImageGenerationPage.elementSelectPage()
                .getSelectedValue(), pageForm.getTitle(), "Selected value is wrong");
    }

    @AfterClass(alwaysRun = true)
    public void deletePageTC9796() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable throwable) {
            Utilities.logInfoMessage("Impossible to delete page " + pageForm.getTitle());
        }
    }
}
