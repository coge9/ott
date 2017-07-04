package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.program;

import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.tests.backend.tvecms.mpx.BaseMPXAccountSet;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import org.testng.annotations.Test;

import java.util.List;

public class TC15166_ValidatePublishSchemeForImagesOnIOSAfterSetMPX extends BaseMPXAccountSet {

    /**
     * Pre-Conditions:
     * Make Sure IOS Concerto API instance is configured on brand
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Navigate to the content
     * Verify: There is a list of Programs
     * Step 3: Select a Program and open on Edit
     * Verify: There is a tab with IOS images
     * Step 4: Publish Program to Program dev
     * Verify:  1 The POST request for Programs send to IOS Amazon Endpoint
     * 2 POST requests for Images are send to IOS Amazon Endpoint
     * 1. for 1965x1108 image source
     * 2. for 1600x900 image source
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

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider",
            enabled = false)
    public void CheckImageAfterPublishByCronIngest(String brand) {
        this.brand = brand;

        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Actual Post Request
        List<LocalApiJson> jsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        for (LocalApiJson imageJson : jsons) {
            softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateImageBySchema(imageJson.getRequestData().toString()),
                    "The validation has failed", "The validation has passed", webDriver);
        }
        softAssert.assertAll();
    }

}
