package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.images;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aliaksei_Klimenka1 on 7/6/2016.
 */
public class TC16730_PublishProgram3tileSourceToConcertoCronIngest extends BaseAuthFlowTest {

    /**
     * Pre-conditions:
     *  iOS platform is configured at CMS (of particular brand)
     *  (it's preferable for testing Roku platform also is configured)
     *  Concerto API is configured
     *
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Run Cron
     * Verify: The cron run success
     *          The Program 1 from pre-condition is ingested
     *          There is a link on Publish Log
     * Step 3: Check publish log
     * Verify:  1 The POST request for Program 1 send to IOS Amazon Endpoint
     *          2 POST requests for program related Images are send to IOS Amazon Endpoint
     *              1.for 1965x1108 image source
     *              2.for 1600x900 image source
     *          The response status for both is success
     * Validation:
     * Get POST Image request and validate by scheme
     * Expected result:
     * The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/image
     * {
     * uuid: "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     * itemType: "image",
     * revision: 3541,
     * title: "file_name",
     * href: "href for original image",
     * imageStyle: [{type: null,href: null}],
     * alt: "alt label for images object",
     * caption: null,
     * categories: [null],
     * tags: [null],
     * copyright: null,
     * credit: null,
     * description: null,
     * highRes: true,
     * source: "image source",
     * published: true,
     * programs: [{programUuid: null,
     * programItemType: "series",seasonUuid: null,
     * episodeUuid: null}]
     * }
     */


    private MainRokuAdminPage mainRokuAdminPage;
    private String mpxIdOfTheProgram;
    private String updatedTitle;
    private String initialTitle;
    private MPXLayer mpxLayer;
    private Series series = new Series();


    private void getMPXIdOfTheProgram() {
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        initialTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        mpxIdOfTheProgram = contentPage.getMPXId(initialTitle);
        //update program title in MPX API
        mpxLayer = new MPXLayer(brand, Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE), mpxIdOfTheProgram);
        updatedTitle = initialTitle + "_new";
        mpxLayer.updateAssetTitleByAssetId(updatedTitle, mpxIdOfTheProgram);
    }

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void CheckImageAfterPublishByCronIngest(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
        //Pre-condition
        getMPXIdOfTheProgram();
        //Step 1-2
        mainRokuAdminPage.runCron(brand);
        String url = rokuBackEndLayer.getLogURL(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(updatedTitle);

        programContentPage.updateSeriesForIos(series);
        //Get Actual Post Request
        SeriesJson expectedSeriesJson = new SeriesJson(series);
        expectedSeriesJson.updateSeriesJsonForIos(series);

        //Step 4
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SERIES);
        softAssert.assertReflectEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("The test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void returnUpdatedProgramTitle() {
        mpxLayer.updateAssetTitleByAssetId(initialTitle, mpxIdOfTheProgram);
        rokuBackEndLayer.updateMPXAssetById(mpxIdOfTheProgram);
    }
}
