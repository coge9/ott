package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.collectiongroup;

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

/**
 * Pre-condition:
 *  1. Login in CMS D7 as admin
 2. Go to /admin/content/queues/add/collection_group
 3. Fill collections title [collection name]
 4. Click on Save button
 5. Open [collection name] for edit

 Step 1: Click on Publish button
 Verify:  Link to the publishing log is present

 Step 2:  Verify publishing log
 Verify: publishing scheme

 */
public class TC14420_CheckPublishingCollectionWithEventAssociation extends BaseAuthFlowTest {

    private Collection fullCollectionGroup;
    private Collection requiredCollectionOne;
    private Collection requiredCollectionTwo;
    private Event event;
    private Content season;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("fullCollectionGroup")
    private CollectionCreationStrategy fullCollectionGroupCreationStrategy;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = (Event) eventCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(event.getTitle());

        requiredCollectionOne = collectionCreationStrategy.createCollection();
        requiredCollectionTwo = collectionCreationStrategy.createCollection();

        fullCollectionGroup = fullCollectionGroupCreationStrategy.createCollection();
        fullCollectionGroup.getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(event.getTitle())
                .setItemType(event.getType().getItemType())
                .setSeason(season.getTitle());
        fullCollectionGroup.getCollectionInfo().setItems(Arrays.asList(
                requiredCollectionOne.getTitle(), requiredCollectionTwo.getTitle()
        ));
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkCollectionGroupWithEventAssociation(final String brand) {
        //Pre-condition - creation
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(event);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createCollection(requiredCollectionOne);
        rokuBackEndLayer.createCollection(requiredCollectionTwo);

        CollectionAbstractPage collectionPage = rokuBackEndLayer.createCollection(fullCollectionGroup);

        rokuBackEndLayer.updateChannelReferenceByUuid(fullCollectionGroup, event, season);
        //set Data for expected json
        fullCollectionGroup.getCollectionInfo().setContentCollectionItems(Arrays.asList(requiredCollectionOne, requiredCollectionTwo));

        collectionPage.publish();

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = collectionPage.getLogURL(brand);

        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(fullCollectionGroup);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = (CollectionJson) requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS).get(0);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        rokuBackEndLayer.deleteCollection(fullCollectionGroup);
        rokuBackEndLayer.deleteCollection(requiredCollectionOne);
        rokuBackEndLayer.deleteCollection(requiredCollectionTwo);

        rokuBackEndLayer.deleteContentType(event);
        rokuBackEndLayer.deleteContentType(season);
    }

}


