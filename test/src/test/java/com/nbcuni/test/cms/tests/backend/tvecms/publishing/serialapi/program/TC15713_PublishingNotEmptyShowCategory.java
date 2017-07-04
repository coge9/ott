package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 8/16/2016.
 */
public class TC15713_PublishingNotEmptyShowCategory extends BaseAuthFlowTest {
    /**
     * <p>
     * Step 1: Go to MPX
     * <p>
     * Step 2: Choose any Program
     * <p>
     * Step 3: Fill Show category ("showCategory") and Save
     * <p>
     * Step 4: Go to Roku CMS
     * <p>
     * Step 5: Update by MPX Updater chosen program
     * <p>
     * Validation: Check Show category Type node in publishing json
     * Expected result: Show category node is "seriesCategory"
     *
     */

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkPublishNotEmptyShowCategory(String brand) {

        String emptySeriesCategory = "";

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
//      Step 1 and 2
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);

        MpxAssetUpdateBuilder builder = mpxLayer.getMpxAssetUpdateBuilder();

        String seriesCategoryExpected = SimpleUtils.getRandomStringWithRandomLength(15);

        builder.updateSeriesCategory(seriesCategoryExpected);

//      Step 3
        mpxLayer.mpxAssetUpdater(builder);

//      Step 4
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 5
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

//      Validation
        List<LocalApiJson> actualMetadata1 = requestHelper.getLocalApiJsons(mainRokuAdminPage.getLogURL(brand));
        Assertion.assertEquals(seriesCategoryExpected, actualMetadata1.get(1).getRequestData().get("seriesCategory").getAsString(), "Series category is not correct");

        builder = mpxLayer.getMpxAssetUpdateBuilder();

        builder.updateSeriesCategory(emptySeriesCategory);

        mpxLayer.mpxAssetUpdater(builder);

        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());
        List<LocalApiJson> actualMetadata2 = requestHelper.getLocalApiJsons(mainRokuAdminPage.getLogURL(brand));
        Assertion.assertEquals(emptySeriesCategory, actualMetadata2.get(0).getRequestData().get("seriesCategory").getAsString(), "Series category is not correct");

    }
}
