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

public class TC14173_CheckPublishingUpdate extends BaseAuthFlowTest {

    private Collection collection;
    private Collection updatedCollection;
    private Content series;
    private Content season;
    private Content episode;
    private Content event;
    private Content post;
    private Content video;
    private Content mediaGallery;
    private List<String> assets = new ArrayList<>();

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("fullCuratedCollection")
    private CollectionCreationStrategy updatedCollectionCreationStrategy;

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
        updatedCollection = updatedCollectionCreationStrategy.createCollection();

        updatedCollection.getAssociations().getChannelReferenceAssociations().getChannelReference()
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
        updatedCollection.getCollectionInfo().setItems(assets);
    }

    @Test(groups = {"collection_publishing", "regression_fix"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(final String brand) {
        //Pre-condition
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
        updatedCollection.getCollectionInfo().addItem(video.getTitle());
        CollectionAbstractPage editCollectionPage = rokuBackEndLayer.createCollection(collection);

        editCollectionPage.getActionBlock().publish();
        softAssert.assertTrue(editCollectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        editCollectionPage = rokuBackEndLayer.updateCollection(collection, updatedCollection);
        //set Data for expected json
        updatedCollection.getCollectionInfo().setContentItems(Arrays.asList(series, season, episode, event, mediaGallery, post, video));

        editCollectionPage.getActionBlock().publish();
        String url = editCollectionPage.getLogURL(brand);
        //Sett UUID channel references
        rokuBackEndLayer.updateChannelReferenceByUuid(updatedCollection, series, season, episode);

        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(updatedCollection);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteContentType(event);
            rokuBackEndLayer.deleteContentType(mediaGallery);
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(episode);
            rokuBackEndLayer.deleteContentType(post);
            rokuBackEndLayer.deleteCollection(updatedCollection);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
