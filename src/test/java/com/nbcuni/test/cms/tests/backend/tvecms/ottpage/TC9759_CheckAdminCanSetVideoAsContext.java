package com.nbcuni.test.cms.tests.backend.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 8/28/15.
 */
public class TC9759_CheckAdminCanSetVideoAsContext extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private AddNewPage addNewPage;
    private PageForm text;
    private TVEPage tvePage;
    private DraftModuleTab draftModuleTab;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2: Go to /admin/ott/pages
     * Verify: "Pages" menu is appeared. List of pages should be presented
     * <p/>
     * Step 3: Click ADD new OTT Page
     * Verify: New page form should be presented
     * <p/>
     * Step 4: Click on "Context" autocomplete field, start to type any Video title
     * Verify: Autocomplete is offering all appropriate nodes
     * <p/>
     * Step 5: Select needed Video node from autocomplete list
     * Verify: Video node is selected
     * <p/>
     * Step 6: Fill all required fields. Click on "Save OTT Page"
     * Verify: New page should be presented in the list with appropriate name and path
     * <p/>
     * Step 7: Click on "edit" next to created page. Check "Context" value
     * Verify: Context field is set according to step 5
     */

    @Test(groups = {"roku_page"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkAdminCanSetProgramAsContext(final String brand) {
        Utilities.logInfoMessage("Check that admin can set Program as Context for new page");
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;

        //Step 1
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-condition get an Item as context
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByPublishedState(PublishState.YES).searchByType(ContentType.TVE_VIDEO).apply();
        String context = contentPage.getFullTitleOfFirstElement();

        //Step 2
        tvePage = mainRokuAdminPage.openOttPage(brand);

        //Step 3
        addNewPage = tvePage.clickAddNewPage();
        softAssert.assertContains(webDriver.getTitle(), AddNewPage.PAGE_TITLE,
                "The page title of the Add New Page is not matched",
                "The Add New OTT Page is opened", webDriver);

        //Step 4-5
        // addNewPage.setContext(context);

        //Step 6
        text = addNewPage.setRequiredFieldsForNewPageAndSave();
        WaitUtils.perform(webDriver).waitForPageLoad();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");
        softAssert.assertTrue(tvePage.isPageExist(text.getTitle()), "The new page is not added or page title: " + text.getTitle() + " is not matched with existen in the table",
                "The new page is created", webDriver);

        //Step 7
        EditPageWithPanelizer addNewPage = tvePage.clickEdit(text.getTitle());
        // softAssert.assertContains(addNewPage.getContext(), context, "The context value  is not matched", "The context value is matched with expected", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPageAndShelf() {
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(text.getTitle()).clickSubmit();
    }

}
