package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.CronPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.EditFilePage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 *
 * Pre-Conditions: Go to brand site as Admin. Kill all content
 * (kill_all_content)<br>
 *
 * Steps:<br>
 * 1. Go to brand site as admin<br>
 * Verify: Admin menu is appeared<br>
 *
 * 2.Go to Configuration -> Media -> Media: thePlatform mpx settings<br>
 * Verify: Media: thePlatform mpx settings page is opened with:<br>
 * - "Add Account" button,<br>
 * - "Connect to MPX" button<br>
 *
 * 3. Provide:
 * - Username for New Account,
 * - Password for New Account.
 * Click on "Connect to MPX" button
 * Verify:
 * Login successful for new account with username [username]." message is appeared
 *"ADD ACCOUNTS" menu is appeared.
 * "ACCOUNTS LIST" menu is appeared.
 * "IMPORT ACCOUNTS" menu with "Save Import Account settings" button is appeared.
 * "Cron settings" menu is appeared "Publisher7 SETTINGS" menu with "Save Configuration" button is appeared
 *
 * 4. Provide: - Select Import Account for account = [any value from dropdown],<br>
 * - Import players from this account = checked,<br>
 * - Import videos from this account = checked,<br>
 * Click on "Save Import Account settings"<br>
 * Verify: "Import Account settings updated." message is appeared.<br>
 * "Default player" dropdown is active<br>
 *
 * 5. Set Default player = "[Brand] VOD Player". Click on "Save Import Account settings"<br>
 * Verify: "Import Account settings updated." message is appeared<br>
 *
 * 6. Go to "CRON SETTINGS" menu. Provide:<br>
 * - Sync mpxPlayers on Cron = checked,<br>
 * - Sync mpxMedia on Cron = checked,<br>
 * - Media Processing Limit = 50 - Media Feed Request Timeout = 240 - Cron Queue Processing Time =
 * 240,<br>
 * - Display MPX Debug Message Level = Basic,<br>
 * - Authentication Token TTL = 10,<br>
 * Click on "Save Configuration"<br>
 * Verify: "The configuration options have been saved.." message is appeared<br>
 *
 * 7. Go to Configuration -> System -> Cron (/admin/config/system/cron)
 * Verify: Cron page is opened
 *
 * 8. Click on "Run Cron". Wait for "Cron run successfully" message<br>
 * Verify: "Cron run successfully" message is appeared<br>
 *
 * 9. Execute Step 8 until at least one OTT Program will be ingested
 * Verify: at least one OTT Program is ingested
 *
 * 10. Go to Content (/admin/content)<br>
 * Click on "Edit" next to test OTT Program
 * Verify: "Edit OTT Program [program name]" page is opened
 *
 * 11.Check that related file link is present
 * Verify: The related file link from each node was created is present
 *
 * 12. Check OTT tab's fields
 * Verify: There are fields:
 *
 * 13. Follow by the file link to Edit File Page and check fields
 * Verify: There are fields:
 *
 *
 */
public class TC9590_Roku_MPXIngest_OTTProgramIngest extends BaseMPXAccountSet {


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
        Assertion.assertTrue(contentPage.searchByType(ContentType.TVE_SERIES).apply(),
                "OTT Program content was not ingested to P7");
        Utilities.logInfoMessage("OTT Program content was successfully ingested");

        //Step 10
        contentPage.searchByType(ContentType.TVE_PROGRAM).apply();
        final EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(contentPage.getFullTitleOfElement(1));

        //Step 11
        softAssert.assertTrue(programContentPage.isFileLinkPresent(), "The related file link is not present", "The related file link is present", webDriver);
        softAssert.assertFalse(programContentPage.getFileLinkHref().isEmpty(), "The related file's href is empty", "The related file link has not empty href", webDriver);

        //Step 12
        softAssert.assertTrue(programContentPage.verifyPage().isEmpty(), "Some fields are missed from the page", "All the fields are present in the Edit Program Page", webDriver);

        //Step 13
        final EditFilePage filePage = programContentPage.clickFileLink();
        softAssert.assertTrue(filePage.verifyPage().isEmpty(), "Not All required fields are present at the File Edit Page",
                "All of the required fields are present at the File Edit Page", webDriver);
        softAssert.assertAll();

    }
}
