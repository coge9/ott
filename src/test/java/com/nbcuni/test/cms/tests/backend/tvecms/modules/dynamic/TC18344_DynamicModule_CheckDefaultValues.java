package com.nbcuni.test.cms.tests.backend.tvecms.modules.dynamic;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.factory.CreationFactoryDynamicModule;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * 1. Go to CMS
 * 2. Open create new Dynamic module page
 * 3. Check default values
 * Values:
 * Title: Dynamic
 * Display title: true
 * Content type: Video
 * Video type: All
 * Maximum content items: 0
 * Sort by: Air date
 * Order: Desc
 * Programs: All
 * Alias: dynamicCollection
 */

public class TC18344_DynamicModule_CheckDefaultValues extends BaseAuthFlowTest {

    private Dynamic expectedDynamic = CreationFactoryDynamicModule.createDynamicDefault();

    @Test(groups = {"dynamic"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void publishAllVideosSortByAirDate(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        DynamicModulePage dynamicModulePage = mainRokuAdminPage.openPage(DynamicModulePage.class, brand);
        Dynamic actualDynamic = dynamicModulePage.getData();

        softAssert.assertReflectEquals(actualDynamic, expectedDynamic,
                "Default data of dynamic module is not matched",
                "Default data of dynamic module is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
