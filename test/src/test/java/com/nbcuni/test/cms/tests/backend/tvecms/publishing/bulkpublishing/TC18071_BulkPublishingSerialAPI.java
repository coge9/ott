package com.nbcuni.test.cms.tests.backend.tvecms.publishing.bulkpublishing;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by aleksandra_lishaeva on 4/18/17.
 */
public class TC18071_BulkPublishingSerialAPI extends BaseAuthFlowTest {

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
     * 4.Select Serial API and Perform publishing
     * Verify: The confirmation Page with list of the items to be performed is present
     * <p>
     * 5.Confirm list of item and press 'Next'
     * Verify: The Post request is send to Serial API ONLY!
     */

    @Test(groups = {"republishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkThatNodePublishesToSerialOnly(final String brand) {

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
        instancePage.publishToInstance(config.getRokuApiInstance());

        //Step 5
        String url = contentPage.getLogURL(brand);
        softAssert.assertTrue(contentPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(contentPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        List<LocalApiJson> localApiJsons = requestHelper.getLocalApiJsons(url);
        for (LocalApiJson localApiJson : localApiJsons) {
            softAssert.assertTrue(localApiJson.getEndpoint().contains(requestHelper.SERIAL_ENDPOINT_IDENTIFIER),
                    "The bulk publishing was happen for not Serial API only, but to: " + localApiJson.getEndpoint(),
                    "The bulk publishing was happen for Serial API only");
        }

        softAssert.assertAll();
    }
}
