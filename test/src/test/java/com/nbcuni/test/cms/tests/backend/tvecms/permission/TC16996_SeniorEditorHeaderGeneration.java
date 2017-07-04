package com.nbcuni.test.cms.tests.backend.tvecms.permission;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/22/16.
 */
public class TC16996_SeniorEditorHeaderGeneration extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Create a user with senior editor Role
     * <p>
     * Steps:
     * 1.Login as user with senior editor role
     * The user is logged in to admin site.
     * Verify: Navigation toolbar is present
     * <p>
     * 2.Go to Dashboard->Generate Headers
     * Verify: The Header generation page is present
     * <p>
     * 3.Select a Page
     * Select 'Header with Page Title'
     * Select a MVPD (or all MVPDs)
     * Verify: The header is generated without error with selected style and available by URL
     * <p>
     * 4.Repeat step #3 with other 3 header styles
     * Verify: The header is generated without error with selected style and available by URL
     */
    private PageForm pageForm = CreateFactoryPage.createDefaultPage();
    private String name = "Optimum";

    private void createPage() {
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createPage(pageForm);
        mainRokuAdminPage.logOut(brand);
    }

    @Test(groups = {"permissions", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkHeaderGenerationAccessForSeniorEditor(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Pre-Condition
        createPage();

        //Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsSeniorEditor();

        // step 2
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        softAssert.assertTrue(headerImageGenerationPage.elementSelectPage().isVisible(), "Select page drop-down is not found", webDriver);

        // step 3
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(pageForm.getTitle());
        softAssert.assertTrue(headerImageGenerationPage.elementSelectMvpd().isVisible(), "Select mvpd drop-down is not found", webDriver);
        softAssert.assertTrue(headerImageGenerationPage.elementSelectHeaderStyle().isVisible(), "Select header Style is visible", webDriver);

        // step 4
        headerImageGenerationPage.generateOttPageHeaders(pageForm.getTitle(),
                HeaderStyle.WITHOUT_TITLE_WITHOUT_MENU, name);
        softAssert.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown", "The status message is Shown after header generation with " + HeaderStyle.WITHOUT_TITLE_WITHOUT_MENU);

        headerImageGenerationPage.generateOttPageHeaders(pageForm.getTitle(),
                HeaderStyle.WITH_TITLE_WITH_MENU, name);
        softAssert.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown", "The status message is Shown after header generation with " + HeaderStyle.WITH_TITLE_WITH_MENU);

        headerImageGenerationPage.generateOttPageHeaders(pageForm.getTitle(),
                HeaderStyle.WITH_TITLE_WITHOUT_MENU, name);
        softAssert.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown", "The status message is Shown after header generation with " + HeaderStyle.WITH_TITLE_WITHOUT_MENU);

        headerImageGenerationPage.generateOttPageHeaders(pageForm.getTitle(),
                HeaderStyle.WITHOUT_TITLE_WITH_MENU, name);
        softAssert.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown", "The status message is Shown after header generation with " + HeaderStyle.WITHOUT_TITLE_WITH_MENU);
        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
        Utilities.logInfoMessage("TEST PASSED");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPageAndShelfTC16996() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }
}
