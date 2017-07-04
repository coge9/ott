package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.CronPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.DataUtil;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Pre-Conditions: Go to brand site as Admin.
 * Make sure that last versions of content are ingested<br>
 *
 * Steps:<br>
 * 1. Go to brand site as admin<br>
 * Verify: Admin menu is appeared<br>
 *
 * 2. Go to Content (/admin/content) <br>
 * Verify: Content menu is opened <br>
 *
 * 3. Choose OTT Video asset.Click Edit next to it
 * Verify: Edit OTT Video menu is opened
 *
 * 4. Keep in mind options values
 * Verify: Values are:
 * MPX Media Title = [current title]
 * Short description= [current description]
 * Category= [current category]
 * Genre= [current genre]
 * Episode number= [current episode number]
 * Season number= [current season number]
 * Tune in time= [current tune in time]
 * Entitlement= [current entitlement]
 * External ID= [current external ID]
 * Entitlement = [current entitlement]
 * Full episode = [current episode type (0/1)]
 * Programming type= [current programming type]
 * Day Part= [current day part]
 * Video Length= [current video length]
 * Siries title = [current siries title]
 * MPX Media ID = [current MPX Media ID]
 * MPX Media GUID = [current MPX Media GUID]
 * MPX description = [current MPX description]
 * MPX air date= [current MPX air date]
 * MPX available date= [current MPX available date]
 * MPX expiration date= [current MPX expiration date]
 *
 * 5. Go to MPX account
 * Verify: MPX webinterface is appeared
 *
 * 6. Find episode with Title = [current title]
 * Verify: Episode is chosen
 *
 * 7. Provide new values for the fields from the step 4 and save
 * Title = [new title]
 * Description = [new description] , etc
 * Verify: OTT Video is saved
 *
 * 8. Go to brand site as admin
 * Verify: Admin menu is appeared
 *
 * 9. Update content with Cron
 * Verify: Cron run successfully
 *
 * 10. Go to Content (/admin/content)
 * Verify: Content menu is opened
 *
 * 11. Choose OTT Video . Click Edit next to it
 * Verify: Edit OTT Video menu is opened
 *
 * 12. open additional Information tab.Check updated fields
 * Verify: Values are matched with those that was set in the MPX:
 * Title = [new title]
 * ...... = [new description]
 * ....... = [new category]
 *
 */
public class TC9580_Roku_ContentUpdate_OTTVideoIngest extends BaseAuthFlowTest {

    private static final String MPX_AUTOMATION_EPISODE = "AQA_OTT_VIDEO";
    private MainRokuAdminPage mainAdminPage;
    private SoftAssert softAssert;
    private RokuBackEndLayer backEndLayer;

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void mpxVideoContentUpdate() throws InterruptedException {
        Utilities.logInfoMessage("Check that OTT Video is updated by whole metadata after ingest from MPX Programs on roku " + brand);
        //Step 1
        softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainAdminPage = backEndLayer.openAdminPage();

        // Step 2
        ContentPage contentPage = mainAdminPage.openContentPage(brand);
        String episodeForFullUpdate = null;

        //Step 3
        if (contentPage.searchByTitle(config.getAvailableEpisode()).searchByType(ContentType.TVE_VIDEO).apply()) {
            episodeForFullUpdate = contentPage.getContentFullTitle(config.getAvailableEpisode());

        } else {
            contentPage.searchByTitle(MPX_AUTOMATION_EPISODE).searchByType(ContentType.TVE_VIDEO).apply();
            episodeForFullUpdate = contentPage.getContentFullTitle(MPX_AUTOMATION_EPISODE);
        }
        if (episodeForFullUpdate == null) {
            Utilities.logSevereMessage(
                    "There is no TV Episode with title contains: " + MPX_AUTOMATION_EPISODE + " or " + config.getAvailableEpisode());
        }
        Utilities.logInfoMessage("The episode : " + episodeForFullUpdate + "exist");

        // Step 3
        EditTVEVideoContentPage editPage = contentPage.openEditTVEVideoPage(episodeForFullUpdate);
        if (!webDriver.getTitle().contains(EditTVEVideoContentPage.PAGE_TITLE)) {
            Utilities.logSevereMessage("Invalid page title on "
                    + webDriver.getCurrentUrl());
        } else {
            Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
        }

        // Step 4
        final Map<String, String> oldMPXData = editPage.collectMPXData();
        final String newTitle = MPX_AUTOMATION_EPISODE + SimpleUtils.getRandomString(2);
        Utilities.logInfoMessage("New title for TV Episode: " + newTitle);
        Map<String, String> expectedMPXData = new HashMap<>();

        expectedMPXData = DataUtil.updateMPXEpisodeDataRoku(oldMPXData, newTitle);
        editPage.clickCancel();

        // Step 5
        Utilities.logInfoMessage("Open the MPX The Platform");
        backEndLayer.loginAndSearchAssetInMPX(episodeForFullUpdate);

        // Step 7
        backEndLayer.updateMpxData(expectedMPXData);

        // Step 8-9
        final CronPage cronPage = mainAdminPage.openCronPage(brand);
        cronPage.runCron(1, 10);
        Assertion.assertTrue(mainAdminPage.isStatusMessageShown(), "The status message is not present after cron run is finished");

        // Step 10
        contentPage = mainAdminPage.openContentPage(brand);

        boolean isPresent = contentPage.searchByTitle(newTitle).apply();
        int counter = 0;
        while (!isPresent && counter < 5) {
            mainAdminPage.openCronPage(brand);
            cronPage.runCron(1, 10);
            contentPage = mainAdminPage.openContentPage(brand);
            isPresent = contentPage.searchByTitle(newTitle).apply();
            counter++;
        }
        Assertion.assertTrue(isPresent
                , "There is no TV Episode with title: "
                        + newTitle);

        // Step 11
        editPage = contentPage.openEditTVEVideoPage(newTitle);
        if (!webDriver.getTitle().contains(EditTVEVideoContentPage.PAGE_TITLE)) {
            Utilities.logSevereMessage("Invalid page title on "
                    + webDriver.getCurrentUrl());
        } else {
            Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
        }
        Map<String, String> actualMPXData = new HashMap<>();

        actualMPXData = editPage.collectMPXData();

        // Step 12.1 - remove difference in days between P7 and ThePlatform systems
        actualMPXData.put("Airdate", DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Airdate")));
        actualMPXData.put("Available date", DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Available date")));
        actualMPXData.put("Expiration date",
                DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Expiration date")));

        expectedMPXData.put("Airdate", DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Airdate")));
        expectedMPXData.put("Available date",
                DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Available date")));
        expectedMPXData.put("Expiration date",
                DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Expiration date")));

        // Step 12.2
        softAssert.assertEquals(actualMPXData, expectedMPXData,
                "OTT Video was not properly updated in Roku CMS after update on MPX thePlatform.");
        Utilities.logInfoMessage("OTT Video was properly updated in Roku CMS after update on MPX thePlatform.");
        softAssert.assertAll();

    }
}
