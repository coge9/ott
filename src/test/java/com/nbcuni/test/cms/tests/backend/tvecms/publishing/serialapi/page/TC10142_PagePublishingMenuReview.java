package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class TC10142_PagePublishingMenuReview extends BaseAuthFlowTest {

    private PageForm pageInfo;

    /**
     * Step 1: Login into roku cms
     * Verify: User is logged in
     * <p/>
     * Step 2: Create new page (or open existed one)
     * Verify: Page is open for editing
     * <p/>
     * Step 3: Open Edit Page with New Panelizer UI
     * Verify: Edit Page with Panelizer is opened
     * <p/>
     * Step 4: Check page elements
     * Verify: OTT Publishing section is present
     * Publish button is present
     * Revert button is disabled
     * <p/>
     * Step 5: Check OTT Publishing section
     * Verify: 1) There is tabs area with different API services instances.
     * Number of tabs correspond to configured API services at Home » Administration » Configuration » Web services » API Services instances
     * 2) User is able to navigate between tabs.
     * 3) Each Tab contains following elements:
     * "Publish to ${instance} button"
     * Text Current revision will be marked as ${instance}, all current values will be pushed to ${instance}. Text under the button.
     * "Revert to ${instance} revision"
     * Text A new revision will be created, replicating current ${instance} revision. Text under the button.
     */

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPagePublishMenuRoku(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step to get configured API instances

        //Step 2
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        //Step 3
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 4-5
        softAssert.assertTrue(editPage.elementPublishBlock().isPresent(), "The publish block is absent", "The publish block is present itself", webDriver);
        softAssert.assertTrue(editPage.elementPublishBlock().isPublishEnable(), "The Publish button is disabled ", "The publish button is enabled ", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkPagePublishMenuAndroid(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        //Step 3
        EditPageWithPanelizer editPage = new TVEPage(webDriver, aid).clickEdit(pageInfo.getTitle());

        //Step 4
        softAssert.assertTrue(editPage.elementPublishBlock().isPresent(), "The publish block is absent", "The publish block is present itself", webDriver);
        softAssert.assertTrue(editPage.elementPublishBlock().isPublishEnable(), "The Publish button is disabled", "The publish button is enabled", webDriver);
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10142() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }

}
