package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 16-Sep-15.
 */
public class TC9726_AdminIsAbleToRemoveFixedShelfFromOTTPage extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private DraftModuleTab draftModuleTab;
    private FeatureCarouselForm featureCarouselForm;
    private EditPageWithPanelizer editPage;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf
     * 3. Add Shelf as fixed to page
     *
     * Test case:
     * Step 1: go to Roku CMS as Admin
     * Verify: Admin menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * make sure that there is fixed shelf in "OTT MODULES" list
     * Verify: fixed shelf is added in list
     * <>p</>
     * Step 5: remove fixed shelf's name from autocomplete field
     * Verify: OTT Module" autocomplete field is empty
     * <>p</>
     * Step 6: click on " Save OTT page" button
     * Verify: OTT page is saved
     * <>p</>
     * Step 7: check that fixed shelf is removed
     * Verify: fixed shelf is removed
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatAdminIsAbleToRemoveLockedFeatureCarouselFromPage(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to add locked shelf to page");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

//      Step 2
        featureCarouselForm = CreateFactoryFeatureCarousel.createDefaultFeatureCarousel();
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm);

//      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.setModule(featureCarouselForm.getTitle());
        editPage.getModuleBlock(featureCarouselForm.getTitle()).changeBlock().lockModule();

        //step 4
        softAssert.assertTrue(editPage.getModuleBlock(featureCarouselForm.getTitle()).isModuleLock(), "The module is not locked",
                "The module is locked");
        editPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

//      Step 5
        editPage.getModuleBlock(featureCarouselForm.getTitle()).deleteBlock();

//      Step 6-7
        editPage.save();
        softAssert.assertFalse(editPage.getModulesName().contains(featureCarouselForm.getTitle()), "Shelf '" + featureCarouselForm.getTitle() + "' still added to Page",
                "Shelf is removed from page", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9726() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
