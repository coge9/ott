package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.mediagallery;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.MediaGalleryPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery.MediaGalleryJson;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.MediaGalleryVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Steps:
 * 1.Go To CMS as Editor
 * Verify: The Editor panel is present
 * <p/>
 * 2.Go to Content -> Add TVE Media Gallery Content type
 * Verify: The Create TVE Media Gallery Page is present
 * <p/>
 * 3.Fill all the fields per all sections and save
 * Verify: The new TVE Media Gallery content type is created
 * All fields are filled
 * <p/>
 * 4.Go To the edit page of created TVE Media Gallery
 * Verify: The edit Page is present
 * <p/>
 * 5.Click button 'Publish' and send POST request to the Amazon API
 * Verify: The API log present 'success' status message of POST request
 * <p/>
 * 6.Analize POST request for TVE Media Gallery
 * Verify: The JSON scheme of Event llok like below with all filled fields:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/media-gallery
 */

public class TC14691_PublishCreatedMediaGallery extends BaseAuthFlowTest {
    private Content series;
    private Content season;
    private Content episode;
    private MediaGallery mediaGallery;
    private RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
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
    @Qualifier("fullMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(series.getTitle());

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);

        ((Episode) episode).getEpisodeInfo().setSeasonNumber(((Season) season).getSeasonInfo().getSeasonNumber());
        content = mediaGalleryCreationStrategy.createContentType();
        mediaGallery = (MediaGallery) content;
        mediaGallery.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setItemType(series.getType().getItemType())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
    }

    @Test(groups = {"mediagallery_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void createdMediaGalleryPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Precondition
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        //create event with whole data
        MediaGalleryPage editPage = (MediaGalleryPage) rokuBackEndLayer.createContentType(content);

        //setMediaImages
        mediaGallery.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());

        //setSlug
        mediaGallery.setSlugInfo(editPage.onSlugTab().getSlug());

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //set Media Gallery uuid
        mediaGallery = (MediaGallery) rokuBackEndLayer.updateContentByUuid(mediaGallery);

        //Sett UUID channel references
        rokuBackEndLayer.updateChannelReferenceByUuid(mediaGallery, series, season, episode);

        //get expected Json
        MediaGalleryJson expectedMediaGallery = new MediaGalleryJson(mediaGallery);

        //Get Actual Post Request
        MediaGalleryJson actualChillerJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MEDIA_GALLERY);
        softAssert.assertTrue(new MediaGalleryVerificator().verify(expectedMediaGallery, actualChillerJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteGalleryTC14691() {
        try {
            rokuBackEndLayer.deleteContentType(mediaGallery);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
