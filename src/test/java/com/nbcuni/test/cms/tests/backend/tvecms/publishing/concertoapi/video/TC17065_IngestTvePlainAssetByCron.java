package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.AdvancedIngestionOptions;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.mpx.MpxMagic;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import com.nbcuni.test.cms.verification.roku.VideoIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TC17065_IngestTvePlainAssetByCron extends BaseAuthFlowTest {

    /**
     * TC17066 - CMS D7: Ingest TVE Plain asset by MPX Updater
     * <p>
     * <p>
     * 1. In advanced ingestion options enable ingestion of look ahead videos.
     * 2. In MPX select asset with no video file attached.
     * 3. Run cron (probably several time to let item appear in the log).
     * 4. Notice publishing URL.
     * 5. Collect data from UI.
     * 6. Compare data in UI and publishing log both for concerto and serial API.
     */

    private Map<AdvancedIngestionOptions, Boolean> optionsToSet;
    private Map<AdvancedIngestionOptions, Boolean> initialIngestOptions;

    @BeforeClass(alwaysRun = true)
    private void prepareAdvancedIngestionOptions() {
        optionsToSet = new HashMap<AdvancedIngestionOptions, Boolean>();
        optionsToSet.put(AdvancedIngestionOptions.INGEST_FOR_LOOK_AHEAD, true);
    }

    @Test(groups = {"video_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void testIngestedVideoPublishing(String brand) {
        // step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        initialIngestOptions = rokuBackEndLayer.setAdvancedIngestionOptions(optionsToSet);
        // step 2
        // TODO: Think about moving this to the separate method in roku backendLayer.
        MpxMagic mpxMagic = new MpxMagic(Config.getInstance().getRokuMPXUsername(brand), Config.getInstance().getRokuMPXPassword(brand));
        List<String> assetsWithoutVideo = mpxMagic.getListOfAssetsWithoutVideoFile(Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE));
        if (assetsWithoutVideo.isEmpty()) {
            throw new SkipException("There are no assets in MPX witohut video content.");
        }
        // get random asset
        MpxAsset mpxAsset = mpxMagic.getMpxAssetAsJavaObject(assetsWithoutVideo.get(random.nextInt(assetsWithoutVideo.size())));
        mpxMagic.updateMpxAsset(new MpxAssetUpdateBuilder(mpxAsset.getId()).updateDescription(String.valueOf(System.currentTimeMillis())));
        // step 3 - 4
        String url = runCron(brand, mpxAsset.getId());
        // step 5 - 6
        // collect data for Concerto API
        Video video = rokuBackEndLayer.getVideoData(mpxAsset.getTitle());
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        VideoJson concertoApiExpected = new VideoJson().getIosObject(video);
        VideoJson concertoApiActual = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.VIDEO);
        // compare actual data with publishing log.
        softAssert.assertTrue(new VideoIosVerificator().verify(concertoApiExpected, concertoApiActual), "The actual data is not matched", "The JSON data is matched", webDriver);
        // collect data for Serial API
        RokuVideoJson serialApiExpected = rokuBackEndLayer.getVideoDataForSerialApi(video.getTitle());
        RokuVideoJson seririalApiActual = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.VIDEO);
        // compare actual data with publishing log.
        softAssert.assertTrue(new RokuVideoJsonVerificator().verify(serialApiExpected, seririalApiActual), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }


    private String runCron(String brand, String mpxId) {
        for (int i = 0; i < 15; i++) {
            mainRokuAdminPage.runCron(brand);
            String url = mainRokuAdminPage.getLogURL(brand);
            ////List<VideoJson> videos = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.VIDEO);
            /*for (VideoJson video : videos) {
                if (video.getMpxMetadata().getMpxId().equals(mpxId)) {
                    return url;
                }*/
            // }
        }
        throw new TestRuntimeException("After running cron there publish log for asset");
    }

    @AfterMethod(alwaysRun = true)
    public void revertAdvancedIngestionOptions() {
        rokuBackEndLayer.setAdvancedIngestionOptions(initialIngestOptions);
    }
}
