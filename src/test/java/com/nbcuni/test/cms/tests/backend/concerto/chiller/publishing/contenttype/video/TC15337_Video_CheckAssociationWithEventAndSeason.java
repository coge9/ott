package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.chiller.VideoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by alekca on 27.05.2016.
 */
public class TC15337_Video_CheckAssociationWithEventAndSeason extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * 1. Create new Event
     * 2. Create new Season and assign to Event
     *
     * Steps:
     * 1. Go to CMS as admin
     * Admin panel is present
     * 2. Go To Content page
     * There is a list of TVE Video
     * 3. Select a video on edit and set Event and Season on association tab
     * Event is selected
     * 4. Publish video and check log
     * Video is publised with Event and Season CT
     */

    private Content event;
    private Content season;
    private Video video;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(event);

        video = new Video();
        video.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(event.getTitle())
                .setItemType(event.getType().getItemType())
                .setSeason(season.getTitle());
    }

    @Test(groups = {"video_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testIngestedVideoPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Precondition
        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(season);

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        video.setTitle(contentPage.getRandomAsset(ContentType.TVE_VIDEO));

        //Step 3
        ChillerVideoPage editPage = (ChillerVideoPage) rokuBackEndLayer.updateContent(video, video);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        //publishing
        editPage.publish();
        // Step 4
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //set Video uuid
        GlobalVideoEntity videoInfo = rokuBackEndLayer.getVideo(video.getTitle());
        //get expected Json
        VideoJson expectedVideo = VideoJsonTransformer.forConcertoApiChiller(videoInfo);

        //Get Actual Post Request
        VideoJson actualVideoJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.VIDEO);
        softAssert.assertTrue(new VideoVerificator().verify(expectedVideo, actualVideoJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC14683() {
        try {
            rokuBackEndLayer.deleteContentType(event);
            rokuBackEndLayer.deleteContentType(season);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
