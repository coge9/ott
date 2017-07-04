package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TC9798_RokuCmsRemovedOttPapgeIsNotReflectedOnOttHeaderImageGeneration
        extends BaseAuthFlowTest {

    private PageForm pageForm = CreateFactoryPage.createDefaultPage();


    /**
     * TC9798 - Roku CMS: Removed OTT page is not reflected on "OTT Header Image Generation" form
     *
     *
     * Step 1: Go to Roku CMS
     * Step 2: Delete test OTT Page
     * Step 3: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 4: Check "select Page" dropdown for new Page (PAGE IS NOT PRESENT)
     *
     * */

    @Test(groups = {"header_burn_in", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void verifyNewPagePresentOnImageGenerationPage(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // step 2
        rokuBackEndLayer.createPage(pageForm);
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(pageForm.getTitle()).clickSubmit();

        // step 3
        Assertion.assertFalse(tvePage.isPageExist(pageForm.getTitle()), "Page was not deleted");
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 4
        Assertion.assertFalse(headerImageGenerationPage.elementSelectPage()
                .isValuePresentInDropDown(pageForm.getTitle()), "Page " + pageForm.getTitle()
                + " is present in Drop down after deleting");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9798() {
        // postcondition 1
        mainRokuAdminPage.logOut(brand);
    }

}
