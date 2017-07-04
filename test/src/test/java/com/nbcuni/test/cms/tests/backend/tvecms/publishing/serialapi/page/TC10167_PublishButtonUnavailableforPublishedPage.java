package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class TC10167_PublishButtonUnavailableforPublishedPage extends BaseAuthFlowTest {

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
     * Step 4: Publish Page to an instance
     * Verify: The Page is published without errors
     * <p/>
     * Step 5: Check OTT Publishing section
     * Verify: For this tab
     * - Publish to ${instance} button is disabled
     * - Revert to ${instance} button is disabled
     */

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkPublishButtonForJustPublishedPage(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        pageInfo = rokuBackEndLayer.createPage(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));

        //Step 3
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 4
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);

        //Step 5
        softAssert.assertTrue(editPage.elementPublishBlock().isPresent(), "The publish block is absent", "The publish block is present itself", webDriver);
        softAssert.assertFalse(editPage.elementPublishBlock().isPublishEnable(), "The Publish button is available for just published", "The publish button is disabled for just published", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10167() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }

}
