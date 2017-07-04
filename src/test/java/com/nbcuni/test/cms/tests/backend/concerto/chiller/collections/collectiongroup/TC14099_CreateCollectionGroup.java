package com.nbcuni.test.cms.tests.backend.concerto.chiller.collections.collectiongroup;

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

/**
 * @author Aliaksei_Dzmitrenka
 * Step 1: Open add collection page
 * Step 2: Fill info and save
 * Step 3: Go to content collection page
 * Step 4: Make sure that collection was added
 */


public class TC14099_CreateCollectionGroup extends BaseAuthFlowTest {

    private Collection collectionFirst;
    private Collection collectionSecond;
    private Collection collectionGroup;
    private Content series;
    private Content season;
    private Content episode;

    @Autowired
    @Qualifier("fullCollectionGroup")
    private CollectionCreationStrategy collectionGroupCreationStrategy;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
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

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeason((Season) season);

        collectionFirst = collectionCreationStrategy.createCollection();
        collectionSecond = collectionCreationStrategy.createCollection();

        collectionGroup = collectionGroupCreationStrategy.createCollection();
        collectionGroup.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
        collectionGroup.getCollectionInfo().setItems(Arrays.asList(collectionFirst.getTitle(), collectionSecond.getTitle()));
        collectionGroup.getCollectionInfo().setItemsCount(collectionGroup.getCollectionInfo().getItems().size());
    }

    @Test(groups = {"collection_group", "regress_test"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationCollectionGroup(final String brand) {
        //Login and created content types for Association tab and items list
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        rokuBackEndLayer.createCollection(collectionFirst);
        rokuBackEndLayer.createCollection(collectionSecond);

        CollectionAbstractPage editPage = rokuBackEndLayer.createCollection(collectionGroup);

        Collection actualCollectionGroup = editPage.getPageInfo();

        softAssert.assertEquals(collectionGroup, actualCollectionGroup, "Actual Page info differ from Input data info", "Actual Page info equals Input data info", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteCollection(collectionGroup);
            rokuBackEndLayer.deleteCollection(collectionFirst);
            rokuBackEndLayer.deleteCollection(collectionSecond);

            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
