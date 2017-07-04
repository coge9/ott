package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory.ProgramCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.chiller.SeriesVerificator;
import com.nbcuni.test.cms.verification.roku.ios.ProgramVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Go To CMS as User
 * Make some changes within random Program on MPX
 * Steps:
 * 1. Run Cron
 * Verify: The cron run success. The current Program from pre-condition is ingested. Here is a link on Publish Log
 * <p/>
 * 2. Check Publish Log
 * Verify: The POST request for Programs send to IOS Amazon Endpoint.The response status is success. The POST request for Programs send to Development. The response status is 200
 * <p/>
 * 3. Get POST request for Programs send to IOS Amazon Endpoint
 * Verify: The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/series
 * <p/>
 * 4. Get POST request for Programs send to Development API
 * Verify: Check fields according to scheme:
 */

public class TC15159_CheckProgramPublishingAfterCronRunning extends BaseAuthFlowTest {
    private String initialTitle;
    private String mpxIdOfTheProgram;
    private String initialDescription;
    private MPXLayer mpxLayer;

    @Autowired
    @Qualifier("seriesForIos")
    private ProgramCreationStrategy seriesCreationStrategy;
    private String updatedDescription;

    private void updateAssetInMpx() {
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        initialTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        mpxIdOfTheProgram = contentPage.getMPXId(initialTitle);
        //update program title in MPX API
        mpxLayer = new MPXLayer(brand, Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE), mpxIdOfTheProgram);
        initialDescription = mpxLayer.getMPXAsset().getDescription();
        updatedDescription = initialDescription + "_new";
        mpxLayer.updateAsset(new MpxAssetUpdateBuilder(mpxIdOfTheProgram).updateDescription(updatedDescription));
    }

    @Test(groups = {"node_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkProgramNodePublishing(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        updateAssetInMpx();
        //Step 1
        mainRokuAdminPage.runCron(brand);
        String url = rokuBackEndLayer.getLogURL(brand);

        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);
        //Step 5
        GlobalProgramEntity programInfo = rokuBackEndLayer.getProgram(initialTitle);
        RokuProgramJson programExpectedObject = ProgramJsonTransformer.forSerialApi(programInfo);
        RokuProgramJson actualProgramJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PROGRAM);
        softAssert.assertTrue(new ProgramVerificator().verify(programExpectedObject, actualProgramJson), "The actual data is not matched", "The JSON data is matched");
        SeriesJson expectedSeriesJson = ProgramJsonTransformer.forConcertoApi(programInfo);
        SeriesJson actualSeriesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROGRAM);
        softAssert.assertEquals(expectedSeriesJson, actualSeriesJson, "The actual data is not matched", "The JSON data is matched", new SeriesVerificator());

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void returnUpdatedProgramTitle() {
        try {
            mpxLayer.updateAsset(new MpxAssetUpdateBuilder(mpxIdOfTheProgram).updateDescription(initialDescription));
        } catch (Throwable e) {
            Utilities.logSevereMessage("There is an error in tear down method " + Utilities.convertStackTraceToString(e));
        }

    }
}
