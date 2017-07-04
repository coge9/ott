package com.nbcuni.test.cms.tests.smoke.tvecms.ottpage;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 8/28/15.
 */
public class TC9500_CheckAdminIsAbleToEditPage extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private PageForm pageForm;
    private PageForm expectedText;
    private Shelf shelf;
    private DraftModuleTab draftModuleTab;

    /**
     * Step 1: Log to admin site as admin
     * Verify: User is able to log in
     * <p/>
     * Step2:Go to OOT Pages
     * Verify: user should be on OTT pages tab, List of pages should be preesented
     * <p/>
     * Step 3: find already created page and click edit
     * Verify: edit page form should be presented
     * <p/>
     * Step 4: Change all fields and status and click submit
     * Verify: Page should be edited
     * Updated date should be changed and valid
     * Creation date shouldn't be changed
     */


    @Test(groups = {"edit_roku_page", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldEditPage(final String brand) {
        Utilities.logInfoMessage("Check that user is able to edit an Page");
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;
        //Step 1
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();


        //Precondition
        pageForm = backEndLayer.createPage();

        //Step 1
        shelf = EntityFactory.getShelfsList().get(1);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        //Step 2
        EditPageWithPanelizer editPage = backEndLayer.openEditOttPage(pageForm.getTitle());

        //Step 4
        expectedText = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");
        PageForm actualText = editPage.getAllFieldsForPage();
        softAssert.assertEquals(expectedText, actualText, "The actual text is not matched with expected",
                "The actual text is matched with expected", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage9500() {
        try {
            if (!StringUtils.isEmpty(expectedText.getTitle())) {
                backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
                backEndLayer.deleteModule(shelf.getTitle());
                backEndLayer.deleteOttPage(expectedText.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
