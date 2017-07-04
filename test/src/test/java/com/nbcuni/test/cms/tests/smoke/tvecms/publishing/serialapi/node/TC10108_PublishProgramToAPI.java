package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.node;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 2/12/16.
 */
public class TC10108_PublishProgramToAPI extends BaseAuthFlowTest {

    /**
     *Step 1. Go TO CMS As admin
     * Verify: Admin Panel is present
     *
     * Step 2. Go to content page
     * Verify: The list of Programs exist
     *
     * Step 3. Open edit page for any program.
     * Go To MPXInfoTab
     * Verify: The list of MPX metadata for node is present
     *
     * Step 4. Click publish to development
     * Verify: Status message of publishing is present
     *
     * Step 5.    Verify published data with any of available way:Status message, Logs in Roku CMS, API service
     * Verify:  all required fields are posted
     * all posted values are matched with OTT program data
     * all custom video thumbnails fields are present
     * all posted custom video thumbnails fields are matched with OTT video data
     * List of all the custom program thumbnails:
     * 1- tile,1tile_program_1,1tile_program_2,3-tile,3tile_program_1,3tile_program_3
     * each of the images has following structure:
     * {"name": "",
     * "imageUrl": "",
     * "width": ,
     * "height": ,
     * "update": true,
     * "categories": ["",""]}
     * */

    @Test(groups = {"node_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void checkProgramNodePublishing(final String brand) {

        //The test script put as not available, because of duplicate logic from TC
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        GlobalProgramEntity globalProgramEntity = rokuBackEndLayer.getRandomProgram();
        RokuProgramJson expectedProgramJson = ProgramJsonTransformer.forSerialApi(globalProgramEntity);
        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.openEditOTTProgramPage(globalProgramEntity.getTitle());

        //Step 3
        EditTVEProgramContentPage editPage = new EditTVEProgramContentPage(webDriver, aid);

        //Step 4
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        List<LocalApiJson> devProgramJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateSeriesBySchema(devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);
        devProgramJson = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateProgramBySchema(devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);

        RokuProgramJson actualProgramJson = (RokuProgramJson) requestHelper.getParsedResponse(url, SerialApiPublishingTypes.PROGRAM).get(0);
        softAssert.assertEquals(expectedProgramJson, actualProgramJson, "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
