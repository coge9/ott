package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.concertoapi;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;


public class TC15637_ValidatePublishSchemeForVideoImagesOnIOS extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make Sure IOS Concerto API instance is configured on brand
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Navigate to the content
     * Verify: There is a list of Videos
     * Step 3: Select a Video and open on Edit
     * Verify: There is a tab with IOS images
     * Step 4: Publish Video to Video dev
     * Verify:  1 The POST request for Videos send to IOS Amazon Endpoint
     * 2 POST requests for Images are send to IOS Amazon Endpoint
     * 1. for Original image image source
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

    @Test(groups = {"image_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkPublishImage(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Conditions
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Video");

        //Step 1-2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String randomAsset = new NodeApi(brand).getRandomVideoNode().getTitle();
        //Step 3
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(randomAsset);

        //Step 4
        videoContentPage.clickSave();
        videoContentPage.elementPublishBlock().publishByTabName();

        String url = videoContentPage.getLogURL(brand);
        softAssert.assertTrue(videoContentPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        List<LocalApiJson> jsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        for (LocalApiJson imageJson : jsons) {
            softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateImageBySchema(imageJson.getRequestData().toString()),
                    "The validation has failed", "The validation has passed", webDriver);
        }
        softAssert.assertAll();
    }

}
