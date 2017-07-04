package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 1/15/16.
 */
public class TC10169_RevertAvailableAfterPublishPageUpdates extends BaseAuthFlowTest {


    private PageForm pageInfo;

    /**
     * Pre-Condition:
     * 1. Create new Ott Page with required fields.
     * 2. Save page [page_name].
     * 3. Open current page for edit and Publish
     * <p/>
     * Steps:
     * Step 1: Open created page for editing with panelizer
     * Verify: Page is open for editing
     * <p/>
     * Step 2: Focus on OTT publishing section
     * Verify: There tabs with configured API services instances
     * <p/>
     * Step 3: Make changes and save whiting the page
     * Verify: The changes are saved
     * <p/>
     * Step 4: Validate Revert button is available for the Publish section of each publishing was made in precondition (e.g Development)
     * Verify: The 'Revert' button is available<br/>. The 'Publish' button is available<br/>
     */

    @Test(groups = {"page_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void checkRevertForPublishPageUpdates(String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        //Step 2
        EditPageWithPanelizer editPage = new TVEPage(webDriver, aid).clickEdit(pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();

        //Step 3
        editPage.setAllRequiredFields(pageInfo).save();

        //Step 4
        Assertion.assertTrue(editPage.elementPublishBlock().isRevertEnable(), "The Revert button is disabled ", webDriver);
        Utilities.logInfoMessage("The Revert button is enabled ");
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10169() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("This page can't be deleted!");
        }
    }
}
