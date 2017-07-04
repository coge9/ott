package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 6/16/16.
 */
public class TC15024_SaveAndPublishCollection extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2. Click on Collection -> New <content type>
     * Verify:""Create <content type>" page is opened
     * <p/>
     * 3. Check page's button
     * Verify: Only three buttons are present: "Save Draft","Save and Publish","Cancel"
     * <p/>
     * 4. Click on "Cancel" button
     * Verify: user is redirected to Collection content list (/admin/collection)
     * <p/>
     * 5. Click on Collection -> New <content type>
     * Verify: "Create <content type>" page is opened
     * <p/>
     * 6. Fill all required fields
     * Verify: all required fields are filled
     * <p/>
     * 7. Click on "Save Draft" button
     * Verify: Collection saved
     * <p/>
     * 8.Go to Content Collection list.Check state
     * Verify: state is "Not Published"
     * <p/>
     * 9. Open created item
     * Verify: item's page is opened
     * <p/>
     * 10. Click on "Cancel" button
     * Verify: user is redirected to Collection content list (/admin/collection)
     * <p/>
     * 11. Open created collection
     * Verify: item's page is opened
     * <p/>
     * 12. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 13.Choose API Service instance
     * Verify: Click on "Publish" button.Collection is published
     * <p/>
     * 14. Go to Content list.check item's state
     * Verify: state is "Published"
     * <p/>
     * 15.Open created collection
     * Verify: Collection's page is opened
     * <p/>
     * 16.click on "Save Draft" button
     * Verify: node saved
     * <p/>
     * 17.Go to Collection list.Check item's state
     * Verify: state is "Not Published"
     * <p/>
     * 18. Open created collection
     * Verify: Collection's page is opened
     * <p/>
     * 19. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 20. Click on "Cancel" button
     * Verify: Collection's page is opened
     * <p/>
     * 21. Go to Collection list. Check node's state
     * Verify: state is "Not Published"
     */

    private Collection collection;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionCreationStrategy.createCollection();

    }

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishCollection(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        CollectionAbstractPage collectionPage = (CollectionAbstractPage) mainRokuAdminPage.openPage(collection.getPage(), brand);

        //Step 3
        softAssert.assertTrue(collectionPage.isActionButtonsPresent(Arrays.asList(ActionButtons.SAVE_AS_DRAFT, ActionButtons.SAVE_AND_PUBLISH, ActionButtons.CANCEL), true)
                , "Not all of action button present", webDriver);

        //Step 4
        softAssert.assertTrue(collectionPage.cancel().isPageOpened(), "Queues list page is not opened",
                "Queues list page is opened", webDriver);

        //Step 5-7
        rokuBackEndLayer.createCollection(collection);
        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(collectionPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 8
        CollectionsContentPage collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), collectionsPage.getNodePublishState(collection.getTitle()), "The node state is Published", "The node state is Not Published", webDriver);

        //Step 9
        collectionPage = collectionsPage.openEditCuratedCollectionPage(collection);

        //Step 10
        softAssert.assertTrue(collectionPage.cancel().isPageOpened(), "Collection page is not opened",
                "Colection page is opened", webDriver);

        //Step 11
        collectionPage = collectionsPage.openEditCuratedCollectionPage(collection);

        //Step 12
        collectionPage.publish();
        String url = collectionPage.getLogURL(brand);
        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        //Step 13 Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        //Step 14
        collectionsPage = collectionPage.cancel();
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), collectionsPage.getNodePublishState(collection.getTitle()), "The node state is not Published"
                , "The node state is Published", webDriver);

        //Step 15
        collectionPage = collectionsPage.openEditCuratedCollectionPage(collection);

        //Step 16
        collectionPage.saveAsDraft();

        //Step 17
        collectionsPage = collectionPage.cancel();
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), collectionsPage.getNodePublishState(collection.getTitle()), "The node state is Published after draft save"
                , "The node state is Not Published after draft saving", webDriver);

        //Step 18
        collectionPage = collectionsPage.openEditCuratedCollectionPage(collection);

        //Step 19-20
        collectionPage.publish();

        //Step 21
        collectionsPage = collectionPage.cancel();
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), collectionsPage.getNodePublishState(collection.getTitle())
                , "The node state is not Published after draft save"
                , "The node state is Published after draft saving", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteCollection(collection);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
