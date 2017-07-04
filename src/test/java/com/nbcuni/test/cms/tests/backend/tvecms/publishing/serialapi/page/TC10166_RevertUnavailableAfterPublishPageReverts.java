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
public class TC10166_RevertUnavailableAfterPublishPageReverts extends BaseAuthFlowTest {

    private PageForm pageInfo;

    /**
     * Pre-Condition:
     * 1. Create new Ott Page with required fields.
     * 2. Publish it to any instance from panelizer
     *
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
     *
     * Step 4: Do revert to published version.
     * Verify: The changes are reverted to previous state
     *
     * Step 5: Validate Revert button is available for the Publish section of each publishing was made in precondition (e.g Development)
     * Verify: The 'Revert' button is available<br/>. The 'Publish' button is available<br/>
     */

    @Test(groups = {"page_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkRevertAfterPublishPageReverts(String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = new TVEPage(webDriver, aid).clickEdit(pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();

        //Step2-3
        editPage.setAllRequiredFields(pageInfo).save();

        //Step 4
        editPage.elementPublishBlock().revertByTabName();

        //Step 5
        Assertion.assertFalse(editPage.elementPublishBlock().isRevertEnable(), "The Revert button is still available after reverting ", webDriver);
        Utilities.logInfoMessage("The Revert button disabled after reverting page ");

    }

    @AfterMethod(alwaysRun = true)
    public void deletePage10166() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
