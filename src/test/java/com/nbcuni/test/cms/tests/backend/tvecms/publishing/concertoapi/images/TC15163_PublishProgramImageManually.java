package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.images;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ImageTransformer;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.tvecms.concertoapi.ImageListConcertoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 7/6/2016.
 */
public class TC15163_PublishProgramImageManually extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make Sure IOS Concerto API instance is configured on brand
     * There is a Program 1
     * <p/>
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Navigate to the content
     * Verify: There is a list of Programs
     * Step 3: Select Program 1 and open on Edit
     * Verify: There is a tab with IOS images
     * There are images:
     * 1. 1965x1108 image source
     * 2. 1600x900 image source
     * Step 4: Update Program and Publish Program to Amazon API
     * Verify:  1 The POST request for Programs send to IOS Amazon Endpoint
     * 2 POST requests for Images are send to IOS Amazon Endpoint
     * 1. for 1965x1108 image source
     * 2. for 1600x900 image source
     * 3. for 3tile source roku
     * The response status for both is success
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

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void ValidateProgramImageByScheme(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Pre-Conditions
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");

        //Step 1-2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String randomProgramTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);

        //Step 3
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(randomProgramTitle);

        programContentPage.clickSave();
        programContentPage.elementPublishBlock().publishByTabName();

        String url = programContentPage.getLogURL(brand);

        NodeApi NodeApi = new NodeApi(brand);
        GlobalProgramEntity globalProgramEntity = ProgramJsonTransformer.getGlobalProgramEntity(NodeApi.getProgramNodeByName(randomProgramTitle), brand);
        List<ImagesJson> expectedImagesJsons = ImageTransformer.getConcertoApiForProgram(globalProgramEntity.getImageSources(), brand);

        //Get Actual Post Request
        List<ImagesJson> actualImagesJsons = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        softAssert.assertEquals(expectedImagesJsons, actualImagesJsons,
                "Image jsons are not equals", "Image jsons are equals", new ImageListConcertoVerificator());
        softAssert.assertAll();

        Utilities.logInfoMessage("The test passed");
    }
}