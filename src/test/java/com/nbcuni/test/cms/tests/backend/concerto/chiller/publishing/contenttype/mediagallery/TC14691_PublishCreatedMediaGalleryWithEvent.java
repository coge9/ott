package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.mediagallery;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.MediaGalleryPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
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
 * Pre-Condition:
 * Create an Event
 * Carete a Season
 * Steps:
 * 1.Go To CMS as Editor
 * Verify: The Editor panel is present
 * <p/>
 * 2.Go to Content -> Add TVE Media Gallery Content type
 * Verify: The Create TVE Media Gallery Page is present
 * <p/>
 * 3.Fill all the fields per all sections and save and Associate with Event and season from Pre-condition
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

public class TC14691_PublishCreatedMediaGalleryWithEvent extends BaseAuthFlowTest {
    private Event event;
    private Season season;
    private MediaGallery mediaGallery;
    private RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
    private RegistryServiceEntity serviceEntity;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("fullMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = (Event) eventCreationStrategy.createContentType();
        season = (Season) seasonCreationStrategy.createContentType();
        season.getSeasonInfo().setProgram(event.getTitle());

        content = mediaGalleryCreationStrategy.createContentType();
        mediaGallery = (MediaGallery) content;
        mediaGallery.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(event.getTitle())
                .setItemType(event.getType().getItemType())
                .setSeason(season.getTitle())
                .setEpisode(null);
    }

    @Test(groups = "mediagallery_publishing", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void publishingMediaGalleryEventAssociated(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Precondition
        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(season);

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
        rokuBackEndLayer.updateChannelReferenceByUuid(mediaGallery, event, season);

        //get expected Json
        MediaGalleryJson expectedMediaGallery = new MediaGalleryJson(mediaGallery);

        //Get Actual Post Request
        MediaGalleryJson actualChillerJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MEDIA_GALLERY);
        softAssert.assertTrue(new MediaGalleryVerificator().verify(actualChillerJson, expectedMediaGallery), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteGalleryTC14691() {
        try {
            rokuBackEndLayer.deleteContentType(mediaGallery);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(event);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
