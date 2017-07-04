package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.images;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory.ProgramCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by Aliaksei_Klimenka1 on 7/6/2016.
 */
public class TC16727_PublishProgram3tileSourceToConcertoManually extends BaseAuthFlowTest {

    /**
     Pre-Conditions:
     Make sure:

     iOS platform is configured at CMS (of particular brand) (it's preferable for testing Roku platform also is configured)
     Concerto API is configured
     get a Series and updated it within MPX account

     Steps:

     Input     Expected Result
     1.
     1. Go To CMS (admin/editor)
     Navigation CMS panel is present
     2.
     Run Cron
     The cron job's run is finished
     there is POST request for Series from pre-condition
     3.
     Check POST request
     The series is updated
     The POST request is sent to API

     There is POST request for Series to Concerto API
     There is a file for '3 tile' source within POST request to Concerto API


     4.
     Check scheme for File
     the scheme is matched with scheme by the url:
     http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/image
     5.
     Check image styles within POST request
     ImageStyles array contains image styles for:
     3 Tile source:
     imageStyle": [

     {
     "width": 340,
     "height": 191,
     "type": "3tile_program_1",
     "href": ""http://dev.tvecms.bravo.nbcuni.com/sites/bravo/files/styles/3tile_program_1/public/2016/10/mdl-ka-ching-2560x1440-logo_153261.jpg?itok=tYhJMP2G&c=1479297682""
     },

     {
     "width": 2560,
     "height": 1440,
     "type":"program_source_withlogo",
     "href":"http://dev.tvecms.bravo.nbcuni.com/sites/bravo/files/styles/program_source_withlogo/public/2016/10/mdl-ka-ching-2560x1440-logo_153261.jpg?itok=PkDWIznv&c=1479297682""

     }]

     6.
     1. Check Media section of Series Post request to Concerto API
     The Media array contains next objects:
     "media": [

     {
     "uuid": "3tile_source_uuid",
     "itemType": "image",
     "usage": "Roku-Small"
     }

     ],
     */


    private MainRokuAdminPage mainRokuAdminPage;
    private Series series;


    @Autowired
    @Qualifier("seriesForIos")
    private ProgramCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initFileMetadata() {
        series = seriesCreationStrategy.createProgramForIos();
    }

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void ValidateProgram3tileStylesInConcerto(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Pre-Conditions
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");

        //Step 1
        mainRokuAdminPage.openContentPage(brand);

        rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_PROGRAM, brand);
        EditTVEProgramContentPage editPage = new EditTVEProgramContentPage(webDriver, aid);

        //Step 2
        editPage.updateBasicTabForIos(series);
        editPage.updateSeriesForIos(series);


        //Step 3
        SeriesJson expectedSeriesJson = new SeriesJson(series);
        //set Values only for Ios Series publishing
        expectedSeriesJson.updateSeriesJsonForIos(series);
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();

        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertReflectEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("The test passed");
    }
}