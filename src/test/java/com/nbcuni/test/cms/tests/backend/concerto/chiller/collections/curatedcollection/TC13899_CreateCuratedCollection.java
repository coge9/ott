package com.nbcuni.test.cms.tests.backend.concerto.chiller.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
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

import java.util.Arrays;
import java.util.List;

/**
 * @author Aliaksei_Dzmitrenka
 * Step 1: Open add collection page
 * Step 2: Fill info and save
 * Step 3: Go to content collection page
 * Step 4: Make sure that collection was added
 */

public class TC13899_CreateCuratedCollection extends BaseAuthFlowTest {

    private Collection collection;
    private Content series;
    private Content season;
    private Content episode;
    private Content event;
    private Content mediaGallery;

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

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = eventCreationStrategy.createContentType();
        mediaGallery = mediaGalleryCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeason((Season) season);

        collection = collectionCreationStrategy.createCollection();
        collection.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
        List<String> itemsForAdding = Arrays.asList(
                series.getTitle(),
                season.getTitle(),
                episode.getTitle(),
                event.getTitle(),
                mediaGallery.getTitle()
        );
        collection.getCollectionInfo().setItems(itemsForAdding).setItemsCount(itemsForAdding.size());
    }

    @Test(groups = {"curated_collection"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        //Login and created content types for Association tab and items list
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(mediaGallery);
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        CollectionAbstractPage editPage = rokuBackEndLayer.createCollection(collection);

        Collection actualCollection = editPage.getPageInfo();

        softAssert.assertEquals(collection, actualCollection, "Actual Page info differ from Input data info", "Actual Page info equals Input data info", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContent() {
        rokuBackEndLayer.deleteCollection(collection);
        rokuBackEndLayer.deleteContentType(event);
        rokuBackEndLayer.deleteContentType(mediaGallery);
        rokuBackEndLayer.deleteContentType(series);
        rokuBackEndLayer.deleteContentType(season);
        rokuBackEndLayer.deleteContentType(episode);
    }

}
