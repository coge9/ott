package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.node;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 2/15/16.
 */
public class TC12910_RepublishFunctionalityIsAvailable extends BaseAuthFlowTest {

    /****
     * Step 1. Go TO CMS As admin
     * Verify: Admin Panel is present
     * <p/>
     * Step 2. Go to content page
     * Verify: The list of Videos exist
     * <p/>
     * Step 3. Check available options
     * Verify: There is available Operation drop-down
     * <p/>
     * Step 4: Check available Operation for admin
     * Verify: THere is 'Publish To API' operation
     */

    @Test(groups = {"roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkVideoNodePublishing(final String brand) {

        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        softAssert.assertTrue(contentPage.isOperationDDLPresent(), "The operation DDL is not preset", "The operation DDL is present", webDriver);
        softAssert.assertTrue(contentPage.getAvailableOperations().contains(ContentPage.Operation.PUBLISH_TO_API.getValue()),
                "The operation DDL is not contain operation: " + ContentPage.Operation.PUBLISH_TO_API.getValue(),
                "The operation DDL is contain operation: " + ContentPage.Operation.PUBLISH_TO_API.getValue(), webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
