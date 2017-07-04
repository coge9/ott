package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.program;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.ConcertoJsonValidatorApiary;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 7/6/2016.
 */
public class TC15266_ValidateProgramSchemeAfterReingest extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     2. Go To CMS as User
     3. Navigate to the content
     4. Select a Program and open on Edit

     Steps:
     1. Publish Program to Development
     Verify: The POST request for Programs send to IOS Amazon Endpoint (with status success)
     The POST request for Programs send to Development Endpoint(200)

     2. Get POST Program to Amazon request and validate by scheme
     The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/series
     except:

     "promoTitle": "The X-Files",
     "promoDescription": "Full Series and extras available."
     "currentSeason": "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     "collection": "70db102c-52d8-4a78-8cb0-0cdf79713b3f"


     3.
     Get POST Program to Development request and validate by scheme
     "itemType": {
     "type": "string"
     },
     "posterurl": {
     "type": "string"
     },
     "seriesType": {
     "type": "string"
     },
     "seriesCategory": {
     "type": "string"
     },
     "showColor": {
     "type": "string"
     },
     "images": {
     "type": "array",
     "items": {
     "type": "string"},
     "description": "Collection of Image Types which should be included."
     },
     "id": {
     "type": "string"
     },
     "mpxId": {
     "type": "string"
     },
     "title": {
     "type": "string"
     },
     "description": {
     "type": "String"
     }
     },
     "required": [

     "itemType",
     "posterurl",
     "seriesType",
     "seriesCategory",
     "showColor",
     "images",
     "id",
     "mpxId",
     "title",
     "description"
     ]
     */
    @Test(groups = {"node_publishing", "ios_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkPublishNotEmptySeriesType(String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        String mpxId = rokuBackEndLayer.deleteRandomContentAsset(ContentType.TVE_PROGRAM, brand);
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxId);

        //Step 2
        String url = mainRokuAdminPage.getLogURL(brand);

        List<LocalApiJson> devProgramJson = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateProgramBySchema(devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);

        List<LocalApiJson> amazonProgramJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(ConcertoJsonValidatorApiary.validateJson(amazonProgramJson.get(0).getRequestData().toString(), ItemTypes.SERIES), "The series scheme validation has failed", "The series scheme validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
