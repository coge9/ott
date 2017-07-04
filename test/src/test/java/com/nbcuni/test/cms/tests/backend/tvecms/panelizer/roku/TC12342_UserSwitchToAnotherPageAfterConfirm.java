package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.roku;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
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
public class TC12342_UserSwitchToAnotherPageAfterConfirm extends BaseAuthFlowTest {
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
     * Step 2:  Update any field and Click on random another page title [page title]
     * Verify:  Confirmation popup is shown.
     *
     * Step 3: Click on Ok button
     * Verify:  Confirmation popup isn't shown.
     *          Page title is 'Edit [new page title]'
     *          Current page tub name is [new page title]
     */
    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkTc12342(final String brand) {
        Utilities.logInfoMessage("Check that User switch to another page after confirm submitting");
        SoftAssert softAssert = new SoftAssert();

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //Step 1
        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.openEditRandomPageWithPanelizer();
        String activePageTitle = editPageWithPanelizer.elementPageTab().getActiveLink().getText();
        softAssert.assertEquals(editPageWithPanelizer.getExpectedPageTitle(activePageTitle), editPageWithPanelizer.getPageTitle(), "Page title value");

        //Step 2
        editPageWithPanelizer.setAllRequiredFields(CreateFactoryPage.createDefaultPage());
        Link randomTabLink = editPageWithPanelizer.elementPageTab().getRandomLink();
        String randomAnotherPageTitle = randomTabLink.getText();
        randomTabLink.click();
        WebDriverUtil alertUtil = WebDriverUtil.getInstance(webDriver);

        //Step 3
        alertUtil.acceptAlert(5);
        softAssert.assertFalse(alertUtil.isAlertPresent(), "Confirmation popup isn't shown.");
        softAssert.assertEquals(editPageWithPanelizer.getExpectedPageTitle(randomAnotherPageTitle), editPageWithPanelizer.getPageTitle(), "Page title value");
        softAssert.assertEquals(randomAnotherPageTitle, editPageWithPanelizer.elementPageTab().getActiveLink().getText(), "Active Tab name");
        softAssert.assertAll();
    }
}
