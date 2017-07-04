package com.nbcuni.test.cms.tests.smoke.tvecms.shelfmanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ivan_Karnilau on 23-Sep-15.
 */
public class TC9760_AdminCanSeeShelfsDataAfterItHasBeenAdded extends BaseAuthFlowTest {

    private static String PAGE_TITLE = "AQA_Page" + SimpleUtils.getRandomString(2);
    private static String ALIAS = "AQA_Alias" + SimpleUtils.getRandomString(2);
    private static String TYPE = SimpleUtils.getSpecialCharacters(5);
    private static String TITLE_SHELF_PAGE = "Edit %s";
    private MainRokuAdminPage mainRokuAdminPage;
    private AddNewPage addNewPage;
    private TVEPage tvePage;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf
     *
     * Test case:
     * Step 1: go to Roku CMS as Admin
     * Verify: Admin menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click on "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * add test shelf with autocomplete functionality
     * Verify: test shelf is added
     * <>p</>
     * Step 5: click on " Save OTT page" button
     * Verify: user friendly message is displayed
     * OTT page is saved
     * <>p</>
     * Step 6: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <>p</>
     * Step 7: check for added shelf
     * Verify: shelf is added
     * there is shelf's data table above configuration block
     * <>p</>
     * Step 8: check shelf's data table
     * Verify: There are columns: TITLE, TYPE, LIST, TILE VARIANT, IMAGE STYLE, DISPLAY TITLE, UPDATED
     * <>p</>
     * Step 9: check data in table
     * Verify: all data matches with shelf's data
     * <>p</>
     * Step 10: click on TITLE link
     * Verify: shelf's page is opened in new tab
     */


    @Test(groups = {"roku_page"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void checkAdminCanSeeShelfsDataAfterItHasBeenAdded(final String brand) {
        Utilities.logInfoMessage("Check Admin can see shelf's data after it has been added");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        tvePage = mainRokuAdminPage.openOttPage(brand);

//      Step 1
        addNewPage = tvePage.clickAddNewPage();

        addNewPage.setRequiredFieldsForNewPageAndSave();
        WaitUtils.perform(webDriver).waitForPageLoad();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

//      Step 2
        Shelf shelf = EntityFactory.getShelfsList().get(2);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        shelf.setAssets(contentPage.getPublishedElementsByCount(shelf.getAssetsCount()));

        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);

        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        String creationDate = getDate();

        ModulesPage modulePage = mainRokuAdminPage.openOttModulesPage(brand);

        modulePage.clickEditLink(shelf.getTitle());
        draftModuleTab = new DraftModuleTab(webDriver, aid);
        Shelf expectedShelfData = draftModuleTab.getShelfInfo();

        mainRokuAdminPage.logOut(brand);

//      Test steps:
//      Step 1
        mainRokuAdminPage = backEndLayer.openAdminPage();

//      Step 2
        tvePage = mainRokuAdminPage.openOttPage(brand);

//      Step 3
        tvePage.getTable().getPager().goToLastPage();
       /* addNewPage = ottPage.clickEdit(PAGE_TITLE);

//      Step 4
        List<OttModuleForm> ottModuleForms = new LinkedList<>();
        ottModuleForms.add(CreateFactoryOttModule.createOttModule(shelf.getTitle(), true, false));
        addNewPage.typeOTTModules(ottModuleForms);

//      Step 5
        addNewPage.submit();
        softAssert.assertTrue(addNewPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present");

//      Step 6
        ottPage = mainRokuAdminPage.openOttPage(brand);
        addNewPage = ottPage.clickEdit(PAGE_TITLE);

//      Step 7
        softAssert.assertTrue(addNewPage.isOTTModule(shelf.getTitle()),
                        "Shelf '" + shelf.getTitle() + "' isn't present", "Shelf is present", webDriver);

//      Step 8-9
        OttModuleBlock ottModuleBlock = addNewPage.getOTTModule(shelf.getTitle());

        Shelf actualShelfData = ottModuleBlock.getShelfData();

        softAssert.assertEquals(expectedShelfData, actualShelfData, "Shelves isn't equals", "Shelves is equals");

//      Step 10
        ottModuleBlock.clickByShelfLink();

        WebDriverUtil.getInstance(webDriver).switchToWindowByNumber(2);

        addNewPage.pause(5);*/

        //TODO update test case due the logic on new Edit UI

        softAssert.assertContains(webDriver.getTitle(), String.format(TITLE_SHELF_PAGE, shelf.getTitle()),
                "Title isn't corrected", "Title is corrected");

        softAssert.assertAll();
    }

    public String getDate() {
        TimeZone pstZone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar cal = Calendar.getInstance();
        Date dateInTimeZone;
        long timeStamp;
        dateInTimeZone = DateUtil.getDateInCertainTimeZone(pstZone, cal.getTime());
        timeStamp = DateUtil.getGmtTimeStampFromAnotherTimeZone(pstZone, dateInTimeZone);
        String data = DateUtil.getDateStringInEstFromGmtTimeStamp(timeStamp, new SimpleDateFormat("MM/dd/yyyy - hh:mm a"));
        return data;
    }

}
