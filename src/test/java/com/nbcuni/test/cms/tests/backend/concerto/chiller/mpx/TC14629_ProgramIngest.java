package com.nbcuni.test.cms.tests.backend.concerto.chiller.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.tests.backend.tvecms.mpx.BaseMPXAccountSet;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 16-May-16.
 */

/**
 * TC14632
 *
 * Pre-Conditions:
 * 1. Delete MPX account
 * 2. Set MPX account for Chiller
 *
 * 1. Go to CMS as admin
 * The Admin Dashboard are present
 * 2. Go to MPX Updater page
 * MPX Updater page is opened
 * 3. Update single Program by MPX ID
 * Program is updated
 * 4. Go to Content page
 * Content page is opened
 * Validation: Find program from step 3
 * Program is not present
 */
public class TC14629_ProgramIngest extends BaseMPXAccountSet {

    @Test(groups = {"roku_ingest_chiller_hard"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void programIngest(String brand) throws InterruptedException {
        Utilities.logInfoMessage("Check that user cannot ingest MPX Programs content on roku " + brand);

        mainRokuAdminPage.runCron(brand, 5);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertFalse(contentPage.searchByType(ContentType.TVE_PROGRAM).apply(), "Program are not present",
                "Program are present", webDriver);

        softAssert.assertAll();
    }
}
