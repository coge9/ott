package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.program;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.ConcertoJsonValidatorApiary;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 7/6/2016.
 */
public class TC15265_ValidateProgramSchemeAfterManualPublishing extends BaseAuthFlowTest {
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
     Verify: The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/series
     except:
     3. Get POST Program to Development request and validate by scheme
     */
    @Test(groups = {"node_publishing", "ios_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkSchemeAfterManualPublishing(String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        String title = rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_PROGRAM, brand);
        EditTVEProgramContentPage programContentPage = new EditTVEProgramContentPage(webDriver, aid);
        //Step 1
        programContentPage.clickSave();
        programContentPage.elementPublishBlock().publishByTabName();
        //Step 2
        String url = programContentPage.getLogURL(brand);
        GlobalProgramEntity programData = rokuBackEndLayer.getProgram(title);
        SeriesJson expectedJson = ProgramJsonTransformer.forConcertoApi(programData);
        List<LocalApiJson> devProgramJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(ConcertoJsonValidatorApiary.validateJson(devProgramJson.get(0).getRequestData().toString(), ItemTypes.SERIES), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);
        SeriesJson actualJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROGRAM);
        softAssert.assertEquals(expectedJson, actualJson, "Expected and actual data doesn't match", "Expected and actual are matched", new SeriesVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
