package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
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
public class TC14684_PublishIngestedVideo extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To CMS as Editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go to Content -> find Video Content type
     * Verify: The edit video Page is present
     * <p>
     * 3.Fill all the fields per all sections and save
     * Verify: The Video content type is saved
     * All fields are filled
     * <p>
     * 4.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 5.Analize POST request for Video
     * Verify:The JSON scheme of Event llok like below with all filled fields:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/video
     * scheme  - http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/video
     */

    private Content series;
    private Content season;
    private Content episode;
    private Video video;
    private RegistryServiceEntity serviceEntity;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultEpisode")
    private ContentTypeCreationStrategy episodeCreationStrategy;

    @Autowired
    @Qualifier("customFieldsVideo")
    private ContentTypeCreationStrategy videoTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);
        ((Episode) episode).getEpisodeInfo().setSeasonNumber(((Season) season).getSeasonInfo().getSeasonNumber());

        content = videoTypeCreationStrategy.createContentType();
        video = (Video) content;
        video.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
    }

    @Test(groups = {"video_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testIngestedVideoPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Precondition
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        video.setTitle(contentPage.getRandomAsset(ContentType.TVE_VIDEO));

        //Step 3
        ChillerVideoPage editPage = (ChillerVideoPage) rokuBackEndLayer.updateContent(video, video);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

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
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
