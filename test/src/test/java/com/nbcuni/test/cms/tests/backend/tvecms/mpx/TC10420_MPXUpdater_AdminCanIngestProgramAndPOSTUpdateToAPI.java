package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.theplatform.MPXLoginControl;
import com.nbcuni.test.cms.backend.theplatform.MPXMetadata;
import com.nbcuni.test.cms.backend.theplatform.MPXSearch;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.*;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

/**
 *TC3251
 * Check that Admin can specify MPX ID of an asset and update directly
 * Steps:<br>
 *1.go to brand site as admin
 * verify:admin menu is appeared
 *
 * 2. go to Content choose published episode (test episode) take current MPX data in mind
 * verify:Current MPX data: - Title = [current title] - Full episodic = [current fullepisodic] - Entitlement = [current entitlement]
 *
 * 3. go to MPX account find test episode check MPX data
 * verify:Current MPX data: - Title = [current title] - Full episodic = [current fullepisodic] - Entitlement = [current entitlement]
 *
 * 4. Set new MPX data for test episode: - Title = [new title] - Full episodic = [new fullepisodic] - Entitlement = [new entitlement] Save
 * verify:MPX data is saved
 *
 * 5. go to brand site as admin
 * verify:admin menu is appeared
 *
 * 6. go to TVE - Player and MPX - MPX Updater
 * verify:MPX Updater menu is appeared
 *
 * 7. set test episode's MPX ID in "MPX Asset" field
 * verify:Autocomplete offers to choose asset
 *
 * 8. choose test asset
 * verify:test episode is chosen
 *
 * 9. Click on "Fetch updates"
 * verify:"Updated asset metadata by MPX ID: [MPX ID]" message is displayed
 *
 * 10. go to Content find test episode check MPX data
 * verify:Current MPX data: - Title = [new title] - Full episodic = [new fullepisodic] - Entitlement = [new entitlement]
 */

public class TC10420_MPXUpdater_AdminCanIngestProgramAndPOSTUpdateToAPI extends BaseAuthFlowTest {
    private static final String MPX_AUTOMATION_PROGRAM = "AQA_OTT_PROGRAM";
    private String brand;
    private String mpxId;
    private MainRokuAdminPage mainAdminPage;
    private RokuProgramJson actualProgram;
    private RokuProgramJson expectedProgram;


    @Test(groups = {"roku_ingest"}, alwaysRun = true, enabled = true)
    @Parameters({"brand"})
    public void specifyMPXIDAndUpdateDirectly(final String brandInUse) {
        brand = brandInUse;
        Utilities.logInfoMessage("Check that Admin can Specify MPX ID of a Program, Update it directly and POST to API" + brand);
        String episodeForFullUpdate = null;
        boolean isPresent = false;
        int counter;
        //Step 1
        softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainAdminPage = rokuBackEndLayer.openAdminPage();

        // Step 2
        ContentPage contentPage = mainAdminPage.openContentPage(brand);

        //Step 3
        if (contentPage.searchByTitle(MPX_AUTOMATION_PROGRAM).searchByType(ContentType.TVE_PROGRAM).apply()) {
            episodeForFullUpdate = contentPage.getContentFullTitle(MPX_AUTOMATION_PROGRAM);

        } else {
            contentPage.searchByTitle(config.getAvailableEpisode()).searchByType(ContentType.TVE_PROGRAM).apply();
            episodeForFullUpdate = contentPage.getContentFullTitle(config.getAvailableEpisode());
        }
        if (episodeForFullUpdate == null) {
            Utilities.logSevereMessage(
                    "There is no TV Episode with title contains: " + MPX_AUTOMATION_PROGRAM + " or " + config.getAvailableEpisode());
        }
        Utilities.logInfoMessage("The episode : " + episodeForFullUpdate + "exist");
        mpxId = contentPage.getMPXId(episodeForFullUpdate);

        EditTVEProgramContentPage editPage = contentPage.openEditOTTProgramPage(episodeForFullUpdate);
        if (!webDriver.getTitle().contains(EditTVEProgramContentPage.PAGE_TITLE)) {
            Utilities.logSevereMessage("Invalid page title on "
                    + webDriver.getCurrentUrl());
        } else {
            Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
        }

        //collect current data from asset
        final Map<String, String> oldMPXData = editPage.collectMPXData();
        //set new data for asset
        final String newTitle = MPX_AUTOMATION_PROGRAM + SimpleUtils.getRandomString(10);
        Utilities.logInfoMessage("New title for TV Episode: " + newTitle);
        final Map<String, String> expectedMPXData = DataUtil.updateMPXEpisodeDataRoku(oldMPXData, newTitle);
        editPage.clickCancel();

        // Step 3
        Utilities.logInfoMessage("Open the MPX The Platform");
        webDriver.get(config.getMPXUrl());
        //final MPXLogin login = new MPXLogin(Config.getInstance());
        final MPXLoginControl login = new MPXLoginControl(webDriver, aid, Config.getInstance());
        login.login(config.getMPXUsername(brand), config.getMPXPassword(brand));
        // isSearch the asset by title
        final MPXSearch mpxSearch = new MPXSearch(config);
        mpxSearch.enterSearchTxt(episodeForFullUpdate);
        mpxSearch.clickSearchByTitleLnk();

        // Step 4
        final MPXMetadata metadata = new MPXMetadata(config);
        metadata.giveFocusToMediaItem();
        metadata.updateEpisodeData(expectedMPXData);
        login.signOut();
        webDriver.manage().deleteAllCookies();

        //Step 5-6
        MpxUpdaterPage mpxUpdater = mainAdminPage.openMpxUpdaterPage(brand);
        if (!webDriver.getTitle().contains(MpxUpdaterPage.PAGE_TITLE)) {
            Utilities.logSevereMessage(
                    "Invalid page title on " + webDriver.getCurrentUrl());
        } else {
            Utilities.logInfoMessage("User on MPXUpdater page " + webDriver.getCurrentUrl());
        }

        //Step 7
        mpxUpdater.setAssetIdIntoField(mpxId);

        //Step 8-9
        mpxUpdater.clickUpdateAssetByMpxId();
        WaitUtils.perform(webDriver).waitForPageLoad();
        if (mainAdminPage.isStatusMessageShown()) {
            Utilities.logSevereMessage(
                    "The status message is not present");
        } else {
            Utilities.logInfoMessage("The status message is present");
        }
        String url = mainAdminPage.getLogURL(brand);

        //Step 10
        contentPage = mainAdminPage.openContentPage(brand);

        Assertion.assertTrue(contentPage.searchByTitle(newTitle).apply(), "The updated asset is not present in content after update", webDriver);
        Utilities.logInfoMessage("The updated asset is present in the content");

        editPage = contentPage.openEditOTTProgramPage(newTitle);
        if (!webDriver.getTitle().contains(EditTVEProgramContentPage.PAGE_TITLE)) {
            Utilities.logSevereMessage("Invalid page title on "
                    + webDriver.getCurrentUrl());
        } else {
            Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
        }

        final Map<String, String> actualMPXData;
        actualMPXData = editPage.collectMPXData();
        RokuProgramJson programExpectedObjects = contentPage.getProgramObject(newTitle);
        RokuProgramJson programActualObjects = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PROGRAM);

        softAssert.assertEquals(programExpectedObjects, programActualObjects, "The Program object in the CMS is not matched with those that send",
                "The Program object in the CMS is matched with those that send", webDriver);
        // Step 10.1 - remove difference in days between P7 and ThePlatform systems
        actualMPXData.put("Airdate", DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Airdate")));
        actualMPXData.put("Available date", DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Available date")));
        actualMPXData.put("Expiration date",
                DataUtil.convertDateToMonthYearFormat(actualMPXData.get("Expiration date")));

        expectedMPXData.put("Airdate", DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Airdate")));
        expectedMPXData.put("Available date",
                DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Available date")));
        expectedMPXData.put("Expiration date",
                DataUtil.convertDateToMonthYearFormat(expectedMPXData.get("Expiration date")));

        //Step 10.2
        softAssert.assertEquals(expectedMPXData, actualMPXData,
                "OTT Video was not properly updated in Roku CMS after update on thePlatform.");
        softAssert.assertAll();
        Utilities.logInfoMessage("OTT Video was properly updated in  Roku CMS after update on thePlatform.");
    }


}

