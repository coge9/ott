package com.nbcuni.test.cms.tests.backend.tvecms.modules.maxcount;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by alekca on 29.01.16.
 */
public class TC12487_Shelf_CheckNegativeMaxCountValue extends BaseAuthFlowTest {

    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private int maxCount = 0;

    /**
     * Step 1. Go To roku as admin
     * Verify: The admin panel is present
     *
     * Step 2. Go to Modules and select Shelf module created in pre-condition
     * Verify: The Edit module Page is opened
     *
     * Step 3. Add a Program to the Content list
     * Verify: The item is added.
     *
     * Step 4. Check show latest option for program
     * Verify: max count field is present next to Program
     *
     * Step 4. Set max Count value to 0 and Save
     * Verify: error message is present. Impossible to set value less than 1
     * */

    @Test(groups = {"max_count"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkNegativeMaxCountForShelf(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String program = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);

        //Step 3 Create test feature carousel module
        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(Arrays.asList(program));
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

        //Step 4
        draftModuleTab.checkLatestEpisodeByName(program);
        softAssert.assertTrue(draftModuleTab.isMaxCountPresent(program), "The Max Count field is not visible for :" + program, "Max Count field is present for :" + program, webDriver);
        draftModuleTab.setMaxCount(program, String.valueOf(maxCount));
        draftModuleTab.clickSave();
        softAssert.assertContains(mainRokuAdminPage.getErrorMessage(), MessageConstants.EXPECTED_ERROR_MAXCOUNT_FOR_SHELF, "The is no any error message that impossible to save max count less than 1 ",
                "The error message is present that impossible to save max count less than 1", webDriver);
        softAssert.assertAll("Test failed");
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void logoutTC12487() {
        mainRokuAdminPage.logOut(brand);
    }
}
