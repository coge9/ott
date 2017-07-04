package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.images;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aliaksei_Klimenka1 on 7/6/2016.
 */
public class TC16729_PublishProgram3tileSourceToConcertoMpxUpdater extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure:
     * iOS platform is configured at CMS (of particular brand) (it's preferable for testing Roku platform also is configured)
     * Concerto API is configured
     * <p>
     * Steps:
     * <p>
     * Input     Expected Result
     * 1. Go To CMS (admin/editor)
     * Verify: Navigation CMS panel is present
     * <p>
     * 2.Go to Content page and get a Series MPX id
     * Verify: The series mpx id is 'test_mpx_id'
     * <p>
     * 3.Go to MPX updater page and update asset by 'test_mpx_id'
     * Verify: The series is updated
     * The POST request is sent to API
     * <p>
     * There is POST request for Series to Concerto API
     * There is a file for '3 tile' source within POST request to Concerto API
     * <p>
     * 4.Check scheme for File
     * Verify: the scheme is matched with scheme by the url:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/image
     * <p>
     * 5.Check image styles within POST request
     * Verify: ImageStyles array contains image styles for:
     * 3 Tile source:
     * imageStyle": [
     * <p>
     * {
     * "width": 340,
     * "height": 191,
     * "type": "3tile_program_1",
     * "href": ""http://dev.tvecms.bravo.nbcuni.com/sites/bravo/files/styles/3tile_program_1/public/2016/10/mdl-ka-ching-2560x1440-logo_153261.jpg?itok=tYhJMP2G&c=1479297682""
     * },
     * <p>
     * {
     * "width": 2560,
     * "height": 1440,
     * "type":"program_source_withlogo",
     * "href":"http://dev.tvecms.bravo.nbcuni.com/sites/bravo/files/styles/program_source_withlogo/public/2016/10/mdl-ka-ching-2560x1440-logo_153261.jpg?itok=PkDWIznv&c=1479297682""
     * <p>
     * }]
     * <p>
     * 6.
     * 1. Check Media section of Series Post request to Concerto API
     * The Media array contains next objects:
     * "media": [
     * <p>
     * {
     * "uuid": "3tile_source_uuid",
     * "itemType": "image",
     * "usage": "Roku-Small"
     * }
     * <p>
     * ],
     */


    private MainRokuAdminPage mainRokuAdminPage;
    private String MPXIdOfTheProgramm;
    private String url;
    private String randomProgramTitle;
    private Series series = new Series();

    public void getMPXIdOfTheProgram(final String brandInUse) {
        this.brand = brandInUse;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        //Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Pre-Conditions
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        randomProgramTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);

        //Step 2
        MPXIdOfTheProgramm = contentPage.getMPXId(randomProgramTitle);

        //Step 3
        rokuBackEndLayer.updateMPXAssetById(MPXIdOfTheProgramm);
        url = contentPage.getLogURL(brandInUse);
    }

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkImageAfterPublishByMPXUpdater(String brand) {
        getMPXIdOfTheProgram(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(randomProgramTitle);

        //Step 2
        programContentPage.updateSeriesForIos(series);

        programContentPage.onIosImagesTab().isAllIosImagesStylesPresent(softAssert);
        softAssert.assertTrue(programContentPage.onIosImagesTab().isAllIosImagesSourcePresent(), "All Ios Images Source isn't Present", webDriver);


        //Step 3 Get Expected Post Request
        SeriesJson expectedSeriesJson = new SeriesJson(series);
        expectedSeriesJson.updateSeriesJsonForIos(series);

        //Step 4
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertReflectEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("The test passed");
    }
}
