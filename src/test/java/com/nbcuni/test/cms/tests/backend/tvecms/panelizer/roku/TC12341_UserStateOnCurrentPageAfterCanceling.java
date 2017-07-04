package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.roku;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 1/29/2016.
 *
 * Pre-condition
 * 1. Login in TVE CMS as Admin
 * 2. Go to Pages page
 * 3. Choose random page
 *
 * Step 1:  Open page for edit from panelizer
 * Verify:  Page title is Edit [page title]
 *          Current page tub name is [page title]
 *
 * Step 2:  Update any field and Click on random another page title [page title]
 * Verify:  Confirmation popup is shown.
 *          'Are you sure you want to proceed? Your changes will be lost.' text is presented.
 *
 * Step 3: Click on Cancel button
 * Verify:  Confirmation popup isn't shown.
 *          Current page tub name is [page title]
 *          Page title is Edit [page title]
 *
 */

public class TC12341_UserStateOnCurrentPageAfterCanceling extends BaseAuthFlowTest {

    private PageForm pageForm = CreateFactoryPage.createDefaultPage();

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkTc12341(final String brand) {
        Utilities.logInfoMessage("Check that user is able to create an admin Page with all settings");
        SoftAssert softAssert = new SoftAssert();

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //Step 1
        rokuBackEndLayer.createPage(pageForm);

        EditPageWithPanelizer editPageWithPanelizer = new EditPageWithPanelizer(webDriver, aid);
        String activePageTitle = editPageWithPanelizer.elementPageTab().getActiveLink().getText();
        String expectedPageTitle = editPageWithPanelizer.getExpectedPageTitle(activePageTitle);
        softAssert.assertEquals(expectedPageTitle, editPageWithPanelizer.getPageTitle(), "Page title value");

        //Step 2
        editPageWithPanelizer.setAllRequiredFields(CreateFactoryPage.createDefaultPage());
        Link randomTabLink = editPageWithPanelizer.elementPageTab().getRandomLink();
        randomTabLink.click();
        WebDriverUtil alertUtil = WebDriverUtil.getInstance(webDriver);

        //Step 3
        alertUtil.dismissAlert(5);
        softAssert.assertFalse(alertUtil.isAlertPresent(), "Confirmation popup isn't shown.");
        softAssert.assertEquals(expectedPageTitle, editPageWithPanelizer.getPageTitle(), "Page title value");
        softAssert.assertEquals(activePageTitle, editPageWithPanelizer.elementPageTab().getActiveLink().getText(), "Active Tab name");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC12341() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
