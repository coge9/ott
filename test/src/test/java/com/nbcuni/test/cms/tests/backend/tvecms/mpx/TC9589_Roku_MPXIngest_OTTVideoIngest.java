package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.CronPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.EditFilePage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 08-Sep-15.
 */
public class TC9589_Roku_MPXIngest_OTTVideoIngest extends BaseMPXAccountSet {

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void mpxShowContentIngest() throws InterruptedException {
        Utilities.logInfoMessage("Check that user can ingest MPX Programs content on roku " + brand);

        // Step 8
        final CronPage cronPage = mainRokuAdminPage.openCronPage(brand);
        softAssert.assertContains(webDriver.getTitle(), CronPage.PAGE_TITLE,
                "Invalid page title on " + webDriver.getCurrentUrl(), "Page title is valid");
        cronPage.runCron(1, 10);

        // Step 9
        final ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        softAssert.assertContains(webDriver.getTitle(), ContentPage.PAGE_TITLE,
                "Invalid page title on " + webDriver.getCurrentUrl(), "Content Page is opened", webDriver);
        Assertion.assertTrue(contentPage.searchByType(ContentType.TVE_EPISODE).apply(),
                "OTT Program content was not ingested to P7");
        Utilities.logInfoMessage("OTT Program content was successfully ingested");

        //Step 10
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        final EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(contentPage.getFullTitleOfElement(1));

        //Step 11
        softAssert.assertTrue(videoContentPage.isFileLinkPresent(), "The related file link is not present", "The related file link is present", webDriver);
        softAssert.assertFalse(videoContentPage.getFileLinkHref().isEmpty(), "The related file's href is empty", "The related file link has not empty href", webDriver);

        //Step 12
        softAssert.assertTrue(videoContentPage.verifyPage().isEmpty(), "Some fields are missed from the page", "All the fields are present in the Edit Program Page", webDriver);

        //Step 13
        final EditFilePage filePage = videoContentPage.openEditFilePage();
        softAssert.assertTrue(filePage.verifyPage().isEmpty(), "Not All required fields are present at the File Edit Page",
                "All of the required fields are present at the File Edit Page", webDriver);
        softAssert.assertAll();
    }
}
