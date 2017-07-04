package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 6/16/16.
 */
public class TC15025_SaveAndPublishNewCollectionsGroup extends BaseAuthFlowTest {
    /**
     * Steps:
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2. Click on Collection -> New <content type>
     * Verify: "Create <content type>" page is opened
     * <p>
     * 3. Fill all required fields
     * Verify: All required fields are filled
     * <p>
     * 4. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p>
     * 5. Choose API Service instance.Click on "Publish" button
     * Verify: node is published
     * <p>
     * 6. Go to Collection list. Check node's state
     * Verify: state is "Published"
     */

    private Collection collectionGroup;


    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionGroupCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collectionGroup = collectionGroupCreationStrategy.createCollection();

    }

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishNewCollectionsGroup(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        rokuBackEndLayer.createAndPublishCollection(collectionGroup);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 5
        String url = mainRokuAdminPage.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        // Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertEquals(localApiJson.getResponseStatus(), ResponseData.SUCCESS.getResponseStatus(), "The action message attribute are not present",
                "The action message attribute are present");

        //Step 6
        CollectionsContentPage collectionsContentPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), collectionsContentPage.getNodePublishState(collectionGroup.getTitle()), "The node state is not Published"
                , "The node state is Published", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollectionGroupTC15025() {
        try {
            rokuBackEndLayer.deleteCollection(collectionGroup);
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't delete the content");
        }
    }
}
