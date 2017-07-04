package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Metadata;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandra_lishaeva on 10/19/15.
 */
public class TC10382_CheckPOSTOfProgramAndProgramByIngest extends BaseMPXAccountSet {

    private static final int PROGRAMCOUNT = 3;
    private int actualProgramCount;
    private ContentPage contentPage;
    private String urlLog;
    private List<LocalApiJson> localApiJsons = new ArrayList<>();
    private List<RokuProgramJson> actualPrograms = new ArrayList<>();
    private List<RokuVideoJson> actualVideos = new ArrayList<>();

    /**
     * pre-condition:
     * 1)Go to roku site as admin
     * 2)Kill Content
     * 3)Set up new MPX account
     * 4)Set MPX and Cron settings and save
     * 5)Run cron to ingest a content
     * <p/>
     * Steps:
     * Step 1: Go to roku as admin
     * Verify: The admin panel is present
     * <p/>
     * Step 2: Navigate to the Content admin/content
     * Verify: The table with Ingested content is present
     * <p/>
     * Step 3: Insure that at least 50 published videos and 10 published programs are exist
     * run cron till condition will true
     * Verify:The content has at least 50 published videos and 10 published programs are ingested
     * <p/>
     * Step 4: Get List of 50 published video iteams
     * Verify: The list of name of the items are collect
     * <p/>
     * Step 5: Collect metadata for the each of video
     * Verify: The metadata from MPX of each of nodes is collect
     * <p/>
     * Step 6: Get List of 10 published programiteams
     * Verify: The list of name of the items are collect
     * <p/>
     * Step 7: Collect metadata for the each of program
     * Verify: The metadata from MPX of each of nodes is collect
     * <p/>
     * Step 8: Go to Shelf module and create a shelf 'VideoShelf' with all 50 videos from step 4
     * Verify: The shelf 'VideoShelf' is created with whole videos
     * <p/>
     * Step 9: Go to Edit page of 'VideoShelf' from step 8 and publish the shelf to API (e.g. DEV endpoit)
     * Verify: The status message shown
     * Status messahe displayed:
     * new Shelf id POST request is created
     * each of 50 video nodes are POST with update status
     * Link for local API service with whole data request
     * <p/>
     * Step 10: Make call to the local API (AQA) service for the links displayed in the status message and compare metadata for all the videos
     * Verify: The response from service contains:
     * Response status:
     * Response message
     * POST Json with video metadata that matched with those from step 5
     * <p/>
     * Step 11: Go to Shelf module and create a shelf 'ProgramShelf' with all 10 programs from step 6
     * Verify: The shelf 'ProgramShelf' is created with whole programs
     * <p/>
     * Step 12: Go to Edit page of 'ProgramShelf' from step 11 and publish the shelf to API (e.g. DEV endpoit)
     * Verify: The status message shown
     * Status messahe displayed:
     * new Shelf id POST request is created
     * each of 10 program nodes are POST with update status
     * Link for local API service with whole data request
     * <p/>
     * Step 13: Make call to the local API (AQA) service for the links displayed in the status message and compare metadata for all the programs
     * Verify: The response from service contains:
     * Response status:
     * Response message
     * POST Json with program metadata that matched with those from step 7
     */

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void testPublishingTheVideo() {
        Utilities.logInfoMessage("Check that " + PROGRAMCOUNT + " OTT Videos are automatically publish to API " + brand);
        softAssert = new SoftAssert();

        //Step 1- 3
        int counterOfRun = 0;
        boolean trigger = false;
        do {
            mainRokuAdminPage.runCron(brand);
            urlLog = config.getAqaAPIUrlBuilderBySession(brand, mainRokuAdminPage.getSessionIdFromLogUrl());
            localApiJsons.addAll(requestHelper.getLocalApiJsons(urlLog, SerialApiPublishingTypes.PROGRAM));
            contentPage = mainRokuAdminPage.openContentPage(brand);
            contentPage.searchByType(ContentType.TVE_PROGRAM).searchByPublishedState(PublishState.YES).apply();
            actualProgramCount = contentPage.getCountEntries();
            counterOfRun++;
            if (counterOfRun >= 5) {
                Assertion.fail("Couldn't to ingest whole content by 10 run of cron");
            }
            trigger = (actualProgramCount >= PROGRAMCOUNT);
        }
        while (!trigger);
        try {
            actualPrograms = requestHelper.getParsedResponse(urlLog, SerialApiPublishingTypes.PROGRAM);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //Step 4
        List<String> programsId = getAssetMPXID(actualVideos);
        //Step 5
        contentPage = mainRokuAdminPage.openContentPage(brand);
        List<RokuProgramJson> programExpectedObjects = contentPage.getProgramObjects(programsId);

        //Step 7
        softAssert.assertEquals(programExpectedObjects, actualPrograms, "The objects not matched", "The objects are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    private List<String> getAssetMPXID(List<? extends Metadata> list) {
        List<String> titles = new ArrayList<>();
        for (Metadata rokuJson : list) {
            titles.add(rokuJson.getMpxId());
        }
        return titles;
    }

}
