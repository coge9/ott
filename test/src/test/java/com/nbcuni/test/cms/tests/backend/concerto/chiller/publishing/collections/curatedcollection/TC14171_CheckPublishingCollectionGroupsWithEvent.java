package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.cms.verification.chiller.CollectionVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;


public class TC14171_CheckPublishingCollectionGroupsWithEvent extends BaseAuthFlowTest {

    private Collection collection;
    private Event event;
    private Content season;
    private Content mediaGallery;

    @Autowired
    @Qualifier("fullCuratedCollection")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = (Event) eventCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(event.getTitle());

        mediaGallery = mediaGalleryCreationStrategy.createContentType();

        collection = collectionCreationStrategy.createCollection();

        collection.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(event.getTitle())
                .setItemType(event.getType().getItemType())
                .setSeason(season.getTitle());

        collection.getCollectionInfo().setItems(Arrays.asList(
                event.getTitle(),
                season.getTitle(),
                mediaGallery.getTitle()));
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkCollectionWithEventAssociation(final String brand) {
        //Pre-condition - creation
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(mediaGallery);

        CollectionAbstractPage collectionPage = rokuBackEndLayer.createCollection(collection);
        //set Data for expected json
        collection.getCollectionInfo().setContentItems(Arrays.asList(event, season, mediaGallery));

        collectionPage.publish();

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = collectionPage.getLogURL(brand);

        //Sett UUID channel references
        rokuBackEndLayer.updateChannelReferenceByUuid(collection, event, season);

        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(collection);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteCollection(collection);
            rokuBackEndLayer.deleteContentType(event);
            rokuBackEndLayer.deleteContentType(mediaGallery);
            rokuBackEndLayer.deleteContentType(season);
        } catch (Throwable e) {
            Utilities.logSevereMessageThenFail("Unable to perform delete of content by reason in exception below");
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            e.getCause();
        }

    }

}


