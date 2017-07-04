package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.program;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.cms.verification.roku.ios.ProgramVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Pre-condition
 * 1. Login in CMS as admin
 * 2. Make Sure IOS Concerto API instance is configured on brand
 * 3. Navigate to the content
 * 4. Choose random Program, get [mpx id]
 * 5. Navigate to the content-> files
 * 6. Delete file for [mpx id]
 * 7. Navigate to MPX Updater page
 * <p>
 * Steps:
 * 1. Update current program by [mpx id]
 * Verify: Status mesage is present. Error message isn't present. Status message contains publishing report link
 * <p>
 * 2. Get POST request for Programs send to IOS Amazon Endpoint
 * Verify: The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/series
 * <p>
 * 3. Get POST request for Programs send to Development API
 * Verify: Check fields according to scheme:
 */

public class TC15160_CheckProgramAutoPublishingWithMpxUpdater extends BaseAuthFlowTest {

    private Series series = new Series();

    @Test(groups = {"node_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkProgramNodePublishing(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        String mpxId = rokuBackEndLayer.deleteRandomContentAsset(ContentType.TVE_PROGRAM, brand);
        rokuBackEndLayer.updateMPXAssetById(mpxId);
        String url = rokuBackEndLayer.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        EditTVEProgramContentPage programEditPage = mainRokuAdminPage.openContentPage(brand).openEditOTTProgramPageById(mpxId);
        String programTitle = programEditPage.onBasicBlock().getData().getTitle();
        GlobalProgramEntity programInfo = rokuBackEndLayer.getProgram(programTitle);

        if (RokuBrandNames.getBrandByName(brand).isHasRoku()) {

            Utilities.logInfoMessage("Validation for serial API");
            RokuProgramJson actualProgramJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PROGRAM);
            RokuProgramJson programExpectedObject = ProgramJsonTransformer.forSerialApi(programInfo);
            softAssert.assertTrue(new ProgramVerificator().verify(programExpectedObject, actualProgramJson), "The actual data is not matched", "The JSON data is matched");
        }

        if (RokuBrandNames.getBrandByName(brand).isHasConcerto()) {
            Utilities.logInfoMessage("Validation for concerto API");
            SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROGRAM);
            SeriesJson expectedSeriesJson = ProgramJsonTransformer.forConcertoApi(programInfo);
            softAssert.assertEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched", new SeriesVerificator());
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
