package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan on 18.03.2016.
 */
public class TC13554_Page_TitleFieldIsNotUniqe extends BaseAuthFlowTest {

    private PageForm firstPageForm = CreateFactoryPage.createDefaultPage();
    private PageForm secondPageForm = CreateFactoryPage.createDefaultPageWithMachineNameAndAlias()
            .setTitle(firstPageForm.getTitle());

    @Test(groups = {"roku_page", "rregression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatTitleFieldIsNotUniqe(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        firstPageForm = rokuBackEndLayer.createPage(firstPageForm);
        EditPageWithPanelizer firstPageWithPanelizer = new EditPageWithPanelizer(webDriver, aid);

        softAssert.assertTrue(firstPageWithPanelizer.isStatusMessageShown(), "Status message is not shown",
                "Status message is shown", webDriver);
        softAssert.assertFalse(firstPageWithPanelizer.isErrorMessagePresent(), "Error message is shown",
                "Error message is not shown", webDriver);

        secondPageForm = rokuBackEndLayer.createPage(secondPageForm);
        EditPageWithPanelizer secondPageWithPanelizer = new EditPageWithPanelizer(webDriver, aid);

        softAssert.assertTrue(secondPageWithPanelizer.isStatusMessageShown(), "Status message is not shown",
                "Status message is shown", webDriver);
        softAssert.assertFalse(secondPageWithPanelizer.isErrorMessagePresent(), "Error message is shown",
                "Error message is not shown", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePages() {
        try {
            rokuBackEndLayer.deleteOttPage(firstPageForm.getTitle());
            rokuBackEndLayer.deleteOttPage(secondPageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }

    }
}
