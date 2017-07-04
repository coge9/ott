package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CollectionVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 4/25/2016.
 */

/**
 * Pre-condition:
 *  1. Login in CMS D7 as admin
 2. Go to /admin/content/queues/add/curated_collection
 3. Fill collections title [collection name]
 4. Click on Save button
 5. Open [collection name] for edit

 Step 1: Click on Publish button
 Verify:  Link to the publishing log is present

 Step 2:  Verify publishing log
 Verify: publishing scheme

 */
public class TC14171_CheckPublishingWithAllFilledFields extends BaseAuthFlowTest {

    private Collection collection;
    private Content series;
    private Content post;
    private Content video;
    private Content season;
    private Content episode;
    private Content event;
    private Content mediaGallery;
    private List<String> assets = new ArrayList<>();

    @Autowired
    @Qualifier("fullCuratedCollection")
    private CollectionCreationStrategy collectionCreationStrategy;

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
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryCreationStrategy;

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        post = postCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(series.getTitle());

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);

        event = eventCreationStrategy.createContentType();
        mediaGallery = mediaGalleryCreationStrategy.createContentType();

        collection = collectionCreationStrategy.createCollection();

        collection.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setItemType(series.getType().getItemType())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());

        assets.addAll(Arrays.asList(
                series.getTitle(),
                season.getTitle(),
                episode.getTitle(),
                event.getTitle(),
                mediaGallery.getTitle(),
                post.getTitle()));
        collection.getCollectionInfo().setItems(assets);
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(final String brand) {
        //Pre-condition - creation
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);
        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(mediaGallery);
        rokuBackEndLayer.createContentType(post);

        video = rokuBackEndLayer.getRandomVideoDataChiller();
        collection.getCollectionInfo().addItem(video.getTitle());
        CollectionAbstractPage collectionPage = rokuBackEndLayer.createCollection(collection);
        //set Data for expected json
        collection.getCollectionInfo().setContentItems(Arrays.asList(series, season, episode, event, mediaGallery, post, video));

        collectionPage.publish();

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = collectionPage.getLogURL(brand);

        //Sett UUID channel references
        rokuBackEndLayer.updateChannelReferenceByUuid(collection, series, season, episode);

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
        rokuBackEndLayer.deleteCollection(collection);
        rokuBackEndLayer.deleteContentType(event);
        rokuBackEndLayer.deleteContentType(mediaGallery);
        rokuBackEndLayer.deleteContentType(series);
        rokuBackEndLayer.deleteContentType(season);
        rokuBackEndLayer.deleteContentType(episode);
        rokuBackEndLayer.deleteContentType(post);
    }

}


