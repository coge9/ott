package com.nbcuni.test.cms.tests.backend.tvecms.publishing.bulkpublishing;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 4/18/17.
 */
public class TC18073_BulkPublishingConcertoAndSerialAPI extends BaseAuthFlowTest {

    private String title;

    /**
     * Pre-Conditions:
     * Make Sure:
     * Serial API is configured as Primary
     * Concerto API is configured as Secondary to Serial API
     * <p>
     * Steps:
     * 1.Go to CMS as Admin
     * Verify: The Admin panel is present
     * <p>
     * 2.Go to Content
     * Verify: The list of content items is present
     * <p>
     * 3.Check an item from the content list
     * Select operation 'Publish to Service instance'  in the bottom of the content page and execute it
     * Verify: The page with List of configured API instances at CMS is present for selection (Checkboxes)
     * <p>
     * 4.Select Concerto API and Serial API and Perform publishing
     * Verify: The confirmation Page with list of the items to be performed is present
     * <p>
     * 5.Confirm list of item and press 'Next'
     * Verify: The Post request is send to bot Concerto API and Serial API
     */

    @Test(groups = {"republishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkThatNodePublishesToConcertoAndSerial(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        title = new NodeApi(brand).getRandomVideoNode().getTitle();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 3
        contentPage.checkAnItem(title);
        SelectInstancePage instancePage = contentPage.selectOperation(ContentPage.Operation.PUBLISH_TO_API).clickExecute();

        //Step 4
        instancePage.publishToAllInstances();

        //Step 5
        String url = contentPage.getLogURL(brand);
        softAssert.assertTrue(contentPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(contentPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        softAssert.assertTrue(!requestHelper.getParsedResponse(url, SerialApiPublishingTypes.VIDEO).isEmpty(),
                "There is no any post request to the serial API",
                "There is post request to the serial API");

        softAssert.assertTrue(!requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.VIDEO).isEmpty(),
                "There is no any post request to the serial API",
                "There is post request to the serial API");

        softAssert.assertAll();
    }
}
