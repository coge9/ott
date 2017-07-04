package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxFileMediaPage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Sep-15.
 */

/**
 * TC9570<br>
 * P7: MPX Ingest. MPX content ingest<br>
 * Check that user can ingest MPX content<br>
 *
 * Pre-Conditions: Go to brand site as Admin. Kill all content
 * (/admin/config/system/tve_mpx_debug/kill_all_content)<br>
 *
 * Steps:<br>
 * 1. Go to brand site as admin<br>
 * Verify: Admin menu is appeared<br>
 *
 * 2.Go to Configuration -> Media -> Media: thePlatform mpx settings (/admin/config/media/theplatform)
 * Verify: Media: thePlatform mpx settings page is opened with:
 * - Username for New Account (required),
 * - Password for New Account (required)
 * - "connect to MPX" button
 *
 * 3. Provide:
 * - Username for New Account,
 * - Password for New Account.
 * Click on "Connect to MPX" button
 * Verify: "Login successful for new account with username [username]." message is appeared
 * "ADD ACCOUNTS" menu is appeared.
 * "ACCOUNTS LIST" menu is appeared.
 * "IMPORT ACCOUNTS" menu with "Save Import Account settings" button is appeared.
 * "Cron settings" menu is appeared "Publisher7 SETTINGS" menu with "Save Configuration" button is appeared
 *
 * 4. Provide:
 - Select Import Account for account = [any value from dropdown],
 - Import players from this account = checked,
 - Import videos from this account = checked,
 Click on "Save Import Account settings"
 * Verify: "Login successful for new account with username mpx/Pub7Sprout." message is appeared.<br>
 * "CRON SETTINGS" menu with "Save Configuration" button is appeared<br>
 * "IMPORT ACCOUNTS" menu with "Save Import Account settings" button is appeared.<br>
 * "Default player" dropdown is inactive<br>
 *
 * 5. Provide: - Select Import Account for account = [any value from dropdown],<br>
 * - Import players from this account = checked,<br>
 * - Import videos from this account = checked,<br>
 * Click on "Save Import Account settings"<br>
 * Verify: "Import Account settings updated." message is appeared.<br>
 * "Default player" dropdown is active<br>
 *
 * 6. Set Default player = "[Brand] VOD Player". Click on "Save Import Account settings"<br>
 * Verify: "Import Account settings updated." message is appeared<br>
 *
 * 7. Go to "CRON SETTINGS" menu. Provide:<br>
 * - Sync mpxPlayers on Cron = checked,<br>
 * - Sync mpxMedia on Cron = checked,<br>
 * - Media Processing Limit = 50 - Media Feed Request Timeout = 240 - Cron Queue Processing Time =
 * 240,<br>
 * - Display MPX Debug Message Level = Basic,<br>
 * - Authentication Token TTL = 10,<br>
 * Click on "Save Configuration"<br>
 * Verify: "The configuration options have been saved.." message is appeared<br>
 *
 * 8. Go to TVE -> AUTOMATIC CONTENT CREATION (/admin/tve/mpx_conversion)<br>
 * Verify: "AUTOMATIC CONTENT CREATION SETTINGS" menu is appeared<br>
 *
 * 9. Provide:<br>
 * - Enable automatic TV Episode creation = checked,<br>
 * - Enable automatic TV Show creation = checked,<br>
 * - Enable automatic TV Season creation = checked,<br>
 * - Automatically unpublish free full episodic content. = unchecked,<br>
 * - Automatically unpublish shortform content. = unchecked,<br>
 * Click on "Save settings"<br>
 * Verify: "Automatic content creation settings were updated" message is appeared<br>
 *
 * 10. Go to Configuration -> System -> Cron (/admin/config/system/cron)<br>
 * Verify: Cron page is opened<br>
 *
 * 11. Click on "Run Cron". Wait for "Cron run successfully" message<br>
 * Verify: "Cron run successfully" message is appeared<br>
 *
 * 12. Go to Content -> Files -> mpxMedia (/admin/content/file/mpxmedia). Make sure that files are
 * ingested.<br>
 * Verify: Files are ingested<br>
 *
 * 13. Go to Content (/admin/content)<br>
 * Verify: MPX content is ingested<br>
 *
 * 14. Execute Step 11 until all content will be ingested<br>
 * Verify: all Shows and Episodes are created<br>
 *
 * 15. Go to Content (/admin/content). Filter content with filter: Type = "Episode"<br>
 * Verify: Results are displayed<br>
 *
 * 16. Go to Content (/admin/content). Filter content with filter: Type = "Season"<br>
 * Verify: Results are displayed<br>
 *
 * 17. Go to Content (/admin/content). Filter content with filter: Type = "Show"<br>
 * Verify: Results are displayed<br>
 *
 */

public class TC9570_MPXIngest_MPXContentIngest extends BaseMPXAccountSet {

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void mpxContentIngest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();

        Utilities.logInfoMessage("Check that user can ingest MPX content on Roku " + brand);

        // Step 7-8
        mainRokuAdminPage.runAllCron(brand);

        // Step 9
        final MpxFileMediaPage mpxMediaPage = mainRokuAdminPage.openMpxMediaPage(brand);

        softAssert.assertTrue(mpxMediaPage.areFilesPresent(), "Files were not ingested",
                "MPX media files are ingested as expected", webDriver);

        // Step 10
        final ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        // Step 11-14
        softAssert.assertTrue(contentPage.searchByType(ContentType.TVE_VIDEO).apply(),
                "TV Episode content was not ingested to P7", "TV Episode content was successfully ingested");
        ;

        softAssert.assertTrue(contentPage.searchByType(ContentType.TVE_PROGRAM).apply(),
                "TV Show content was not ingested to P7", "TV Show content was successfully ingested");

        softAssert.assertAll();
    }
}
