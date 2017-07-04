package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuShelfJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Date;


/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 * Precondition:
 * Create shelf
 * Create page
 * Steps:
 * Step 1: Add tested shelf to tested page
 * Step 2: save
 * Step 3: Publish tested page
 * Verify lastUpdateDatee field at json response  for shelf and page
 *
 */

public class TC11698_LastUpdateDateField extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();
    private Shelf shelf;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11698asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        shelf = EntityFactory.getShelfsList().get(0);
        DraftModuleTab addShelfOttModulePage = mainRokuAdminPage.openAddShelfPage(brand);
        addShelfOttModulePage.createShelf(shelf);
        pageForm = backEndLayer.createPage(pageForm);
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);
        editPage.setModule(shelf.getTitle());
        editPage.save();
        Date currentDate = DateUtil.getCurrentDateInUtc();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        RokuPageJson pageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);
        RokuShelfJson shelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.MODULE);

        Date actualDatePage = DateUtil.parseStringToUtcDate(pageJson.getDateModified());
        Date actualDateShelf = DateUtil.parseStringToUtcDate(shelfJson.getDateModified());
        Utilities.logInfoMessage("Actual time of publishing page [" + actualDatePage + "]");
        Utilities.logInfoMessage("Actual time of publishing Shelf [" + actualDateShelf + "]");
        Utilities.logInfoMessage("Time before click publish button [" + currentDate + "]");
        softAssert.assertTrue(actualDatePage.getTime() - currentDate.getTime() < 30000, "Data Modified field value is not correct ", "Data Modified field value is correct", webDriver);
        softAssert.assertTrue(actualDateShelf.getTime() - currentDate.getTime() < 30000, "Data Modified field value is not correct. ", "Data Modified field value is correct", webDriver);
        softAssert.assertAll();

    }

    @AfterMethod(alwaysRun = true)
    public void deletePage11698() {
        try {
            rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            if (pageForm.getTitle() != null) {
                rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            }
            if (shelf.getTitle() != null) {
                rokuBackEndLayer.deleteModule(shelf.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logWarningMessage("Teer-down method can't be performed " + e.getMessage());
        }
    }
}
