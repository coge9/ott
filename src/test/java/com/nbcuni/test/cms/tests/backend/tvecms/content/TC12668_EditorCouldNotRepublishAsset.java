package com.nbcuni.test.cms.tests.backend.tvecms.content;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 2/15/16.
 */
public class TC12668_EditorCouldNotRepublishAsset extends BaseAuthFlowTest {

    /**
     *Step 1. Go TO CMS As editor
     * Verify: Editor Panel is present
     *
     * Step 2. Go to content page
     * Verify: The list of Videos exist
     *
     * Step 3. Check available options for editor
     * Verify: There is no DRop Down operation. User could not to republish content
     * */

    @Test(groups = {"republishing", "permissions", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkVideoNodePublishing(final String brand) {

        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        softAssert.assertFalse(contentPage.isOperationDDLPresent(), "The operation DDL is preset for Editor", "The operation DDL is not present for Editor", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
