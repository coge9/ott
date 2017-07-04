package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.images;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ImageTransformer;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.tvecms.concertoapi.ImageListConcertoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

public class TC15861_PublishImageManually extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make Sure IOS Concerto API instance is configured on brand
     * There is a Video 1
     * <p>
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Navigate to the content
     * Verify: There is a list of Video
     * Step 3: Select Video 1 and open on Edit
     * Verify: There is a tab with IOS images
     * There are images:
     * 1. original image source
     * Step 4: Update Video and Publish Video to Amazon API
     * Verify:  1 The POST request for Video send to IOS Amazon Endpoint
     * 2 POST requests for Images are send to IOS Amazon Endpoint
     * 1. for original image source
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

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void validateImageByScheme(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Pre-Conditions
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Video");

        //Step 1-2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        String randomVideoTitle = contentPage.getRandomAsset(ContentType.TVE_VIDEO);
        //Step 3
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(randomVideoTitle);
        videoContentPage.clickSave();

        videoContentPage.elementPublishBlock().publishByTabName();

        String url = videoContentPage.getLogURL(brand);
        softAssert.assertTrue(videoContentPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);

        NodeApi NodeApi = new NodeApi(brand);
        GlobalVideoEntity globalVideoEntity = VideoJsonTransformer.getGlobalVideoEntity(NodeApi.getVideoNodeByName(randomVideoTitle), brand);
        List<ImagesJson> expectedImagesJsons = ImageTransformer.getConcertoApiForVideo(globalVideoEntity.getImageSources(), brand);

        //Get Actual Post Request
        List<ImagesJson> actualImagesJsons = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        softAssert.assertEquals(expectedImagesJsons, actualImagesJsons,
                "Image jsons are not equals", "Image jsons are equals", new ImageListConcertoVerificator());
        softAssert.assertAll();
    }
}