package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.collections;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 6/1/2016.
 */
public class TC14792_ValidateCollectionGroupJson extends BaseAuthFlowTest {
    /**
     * Pre-Conditions:
     * Make sure there is a Collection Group
     * Steps:
     * <p>
     * 1.Go To CMS as Editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go to Collection and select Collection Group from pre-condition
     * Verify: The Edit Collection Group Page is opened
     * <p>
     * 3.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 4.Validate Scheme of POST request for Collection Group
     * Verify: The JSON scheme of send Collection Group is matched with scheme available by URL below:
     * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/list
     */

    private Collection collection;

    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionGroupCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionGroupCreationStrategy.createCollection();
    }

    @Test(groups = {"collection_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateCollectionGroupScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //create event with data
        CollectionAbstractPage collectionGroupPage = rokuBackEndLayer.createCollection(collection);

        softAssert.assertTrue(collectionGroupPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        //publishing
        collectionGroupPage.publish();
        String url = collectionGroupPage.getLogURL(brand);
        softAssert.assertTrue(collectionGroupPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateCollectionsBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void delete() {
        rokuBackEndLayer.deleteCollection(collection);
    }
}
