package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.node;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory.ProgramCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC15161_CheckManualProgramPublishing extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     1. Login in CMS as admin
     2. Navigate to the content
     3. Select random Program and open on Edit
     Steps:

     1. Click on Publish to Development
     Verified: Status message is present
     The POST request for Programs send to IOS Amazon Endpoint
     The response status is success
     The POST request for Programs send to Development
     The response status is 200

     2. Get POST request for Programs send to IOS Amazon Endpoint
     Verified: The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/series

     3. Get POST request for Programs send to Development API
     Verified: Check fields according to scheme:
     */
    private Series series;


    @Autowired
    @Qualifier("seriesForIos")
    private ProgramCreationStrategy seriesCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createProgramForIos();
    }

    @Test(groups = {"node_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkProgramNodePublishing(final String brand) {

        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        EditTVEProgramContentPage editPage = mainRokuAdminPage.openContentPage(brand).openEditOTTProgramPageById(rokuBackEndLayer.getRandomProgram().getMpxAsset().getId());
        String programTitle = editPage.onBasicBlock().getData().getTitle();

        editPage.updateBasicTabForIos(series);
        editPage.clickSave();
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);


        if (RokuBrandNames.getBrandByName(brand).isHasConcerto()) {
            GlobalProgramEntity seriesInfo = rokuBackEndLayer.getProgram(programTitle);
            SeriesJson expectedSeriesJson = ProgramJsonTransformer.forConcertoApi(seriesInfo);

            List<LocalApiJson> devProgramJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROGRAM);
            softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateProgramBySchema(
                    devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);

            SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROGRAM);
            expectedSeriesJson.setCategories(actualSeriesJson.getCategories());
            softAssert.assertEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched", new SeriesVerificator());
        }
        if (RokuBrandNames.getBrandByName(brand).isHasSerial()) {
            GlobalProgramEntity globalProgramEntity = rokuBackEndLayer.getRandomProgram();
            RokuProgramJson expectedProgramJson = ProgramJsonTransformer.forSerialApi(globalProgramEntity);
            List<LocalApiJson> devProgramJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROGRAM);
            softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateSeriesBySchema(devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);
            devProgramJson = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.PROGRAM);
            softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateProgramBySchema(devProgramJson.get(0).getRequestData().toString()), "The program scheme validation has failed", "The program scheme validation has passed", webDriver);

            RokuProgramJson actualProgramJson = (RokuProgramJson) requestHelper.getParsedResponse(url, SerialApiPublishingTypes.PROGRAM).get(0);
            softAssert.assertEquals(expectedProgramJson, actualProgramJson, "The actual data is not matched", "The JSON data is matched", webDriver);

        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
