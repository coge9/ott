package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.roku;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 1/29/2016.
 */
public class TC12443_WindowIsNotShownAfterSwitchingBetweenSavedPages extends BaseAuthFlowTest {
    /**
     * Pre-condition
     * 1. Login in TVE CMS as Admin
     * 2. Go to Pages page
     * 3. Choose random page
     *
     * Step 1:  Open page for edit from panelizer
     * Verify:  Page title is Edit [page title]
     *          Current page tub name is [page title]
     *
     * Step 2: Click on random another page title [new page title]
     * Verify:  Confirmation popup isn't shown.
     *          Page title is 'Edit [new page title]'
     *          Current page tub name is [new page title]
     */
    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void checkTc12343(final String brand) {
        Utilities.logInfoMessage("Check that Window isn't Shown After Switching Between Saved Pages");
        SoftAssert softAssert = new SoftAssert();

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //Step 1
        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.openEditRandomPageWithPanelizer();
        String activePageTitle = editPageWithPanelizer.elementPageTab().getActiveLink().getText();
        softAssert.assertEquals(editPageWithPanelizer.getExpectedPageTitle(activePageTitle), editPageWithPanelizer.getPageTitle(), "Page title value");

        //Step 2
        Link randomTabLink = editPageWithPanelizer.elementPageTab().getRandomLink();
        String randomAnotherPageTitle = randomTabLink.getText();
        randomTabLink.click();
        WebDriverUtil alertUtil = WebDriverUtil.getInstance(webDriver);
        softAssert.assertFalse(alertUtil.isAlertPresent(), "Confirmation popup isn't shown.");
        softAssert.assertEquals(editPageWithPanelizer.getExpectedPageTitle(randomAnotherPageTitle), editPageWithPanelizer.getPageTitle(), "Page title value");
        softAssert.assertEquals(randomAnotherPageTitle, editPageWithPanelizer.elementPageTab().getActiveLink().getText(), "Active Tab name");
        softAssert.assertAll();
    }
}
