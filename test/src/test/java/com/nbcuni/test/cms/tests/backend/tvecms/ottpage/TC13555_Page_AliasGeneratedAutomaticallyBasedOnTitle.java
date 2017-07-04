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
public class TC13555_Page_AliasGeneratedAutomaticallyBasedOnTitle extends BaseAuthFlowTest {

    private PageForm pageForm;

    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkThatAliasGeneratesAutomatically(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        pageForm = rokuBackEndLayer.createPage(CreateFactoryPage.createDefaultPage());
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "Error message is shown",
                "Error message is not shown", webDriver);

        softAssert.assertEquals(pageForm.getTitle().toLowerCase(), editPage.getAlias().getSlugValue(),
                "Alias is not corrected", "Alias is corrected");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePagesTC13555() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }
}
