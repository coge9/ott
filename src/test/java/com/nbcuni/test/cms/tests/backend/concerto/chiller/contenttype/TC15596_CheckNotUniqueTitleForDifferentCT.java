package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 27-Jul-16.
 */

/**
 * TC15572
 *
 * 1. Go to CMS as Editor
 * Admin panel is present
 * 2. Create new Series with required fields [content_name]
 * Series is created
 * 3. Try create new Event with required fields [content_name]
 * Event is created
 * 4. Try create new Season with required fields [content_name]
 * Season is created
 * 5. Try create new Episode with required fields [content_name]
 * Episode is created
 * 6. Try create new Post with required fields [content_name]
 * Post is created
 * 7. Try create new Media Gallery with required fields [content_name]
 * Media Gallery is created
 * 8. Try create new Collection with required fields [content_name]
 * Collection is created
 * 9. Try create new Collection Group with required fields [content_name]
 * Collection Group is created
 */

public class TC15596_CheckNotUniqueTitleForDifferentCT extends BaseAuthFlowTest {

    private String commonTitle;

    private Content series;
    private Content event;
    private Content season;
    private Content episode;
    private Content post;
    private Content mediaGallery;
    private Collection collection;
    private Collection collectionGroup;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonTypeCreationStrategy;

    @Autowired
    @Qualifier("withRequiredEpisode")
    private ContentTypeCreationStrategy episodeTypeCreationStrategy;

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy postTypeCreationStrategy;

    @Autowired
    @Qualifier("requiredMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryTypeCreationStrategy;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionTypeCreationStrategy;

    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionGroupTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesTypeCreationStrategy.createContentType();

        commonTitle = series.getTitle();

        event = eventTypeCreationStrategy.createContentType();
        event.getGeneralInfo().setTitle(commonTitle);

        season = seasonTypeCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        season.getGeneralInfo().setTitle(commonTitle);

        episode = episodeTypeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo().setParentSeason((Season) season);
        episode.getGeneralInfo().setTitle(commonTitle);

        post = postTypeCreationStrategy.createContentType();
        post.getGeneralInfo().setTitle(commonTitle);

        mediaGallery = mediaGalleryTypeCreationStrategy.createContentType();
        mediaGallery.getGeneralInfo().setTitle(commonTitle);

        collection = collectionTypeCreationStrategy.createCollection();
        collection.getGeneralInfo().setTitle(commonTitle);

        collectionGroup = collectionGroupTypeCreationStrategy.createCollection();
        collectionGroup.getGeneralInfo().setTitle(commonTitle);
    }

    @Test(groups = {"content_type"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkTitles(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);
        rokuBackEndLayer.createContentType(post);
        rokuBackEndLayer.createContentType(mediaGallery);
        rokuBackEndLayer.createCollection(collection);
        rokuBackEndLayer.createCollection(collectionGroup);

        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(series), "Content is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(event), "Content is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(season), "Content is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(episode), "Content is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(post), "Content is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkContentIsPresent(mediaGallery), "Content is not corrected");

        softAssert.assertTrue(rokuBackEndLayer.checkCollectionIsPresent(collection), "Collection is not corrected");
        softAssert.assertTrue(rokuBackEndLayer.checkCollectionIsPresent(collectionGroup), "Collection is not corrected");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentTypesByBulkOperation(series, event, season, episode, post, mediaGallery);
            rokuBackEndLayer.deleteCollection(collection);
            rokuBackEndLayer.deleteCollection(collectionGroup);
        } catch (Exception e) {
            Utilities.logWarningMessage(e.getMessage());
        }
    }
}
