package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.collectiongroup;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionGroupPage;
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

import java.util.Arrays;

/**
 * Created by Alena_Aukhukova on 4/25/2016.
 */

public class TC14422_CheckPublishingUpdate extends BaseAuthFlowTest {

    private Collection requiredCollectionOne;
    private Collection requiredCollectionTwo;
    private Collection collectionGroup;

    private Collection updatedCollectionGroup;
    private Content series;
    private Content season;
    private Content episode;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionGroupCreationStrategy;

    @Autowired
    @Qualifier("fullCollectionGroup")
    private CollectionCreationStrategy updatedCollectionGroupCreationStrategy;

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
        ((Season) season).getSeasonInfo().setProgram(series.getTitle());

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);

        requiredCollectionOne = collectionCreationStrategy.createCollection();
        requiredCollectionTwo = collectionCreationStrategy.createCollection();

        collectionGroup = collectionGroupCreationStrategy.createCollection();
        updatedCollectionGroup = updatedCollectionGroupCreationStrategy.createCollection();

        updatedCollectionGroup.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setItemType(series.getType().getItemType())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());

        updatedCollectionGroup.getCollectionInfo().setItems(Arrays.asList(
                requiredCollectionOne.getTitle(),
                requiredCollectionTwo.getTitle()));
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(final String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        rokuBackEndLayer.createCollection(requiredCollectionOne);
        rokuBackEndLayer.createCollection(requiredCollectionTwo);

        CollectionAbstractPage editCollectionPage = rokuBackEndLayer.createCollection(collectionGroup);

        editCollectionPage.getActionBlock().publish();
        softAssert.assertTrue(editCollectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        CollectionAbstractPage updatePage = (CollectionGroupPage) rokuBackEndLayer.updateCollection(collectionGroup, updatedCollectionGroup);
        //set Data for expected json

        updatedCollectionGroup.getCollectionInfo().setContentCollectionItems(Arrays.asList(requiredCollectionOne, requiredCollectionTwo));

        updatePage.getActionBlock().publish();
        String url = updatePage.getLogURL(brand);

        //Sett UUID channel references
        rokuBackEndLayer.updateChannelReferenceByUuid(updatedCollectionGroup, series, season, episode);

        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(updatedCollectionGroup);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = (CollectionJson) requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS).get(0);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteCollection(updatedCollectionGroup);
            rokuBackEndLayer.deleteCollection(requiredCollectionOne);
            rokuBackEndLayer.deleteCollection(requiredCollectionTwo);

            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
