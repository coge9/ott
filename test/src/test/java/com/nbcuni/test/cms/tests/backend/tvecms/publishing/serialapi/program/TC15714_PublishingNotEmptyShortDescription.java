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
public class TC15714_PublishingNotEmptyShortDescription extends BaseAuthFlowTest {
    /**
     * <p>
     * Step 1: Go to MPX
     * <p>
     * Step 2: Choose any Program
     * <p>
     * Step 3: Fill ShortDescription ("shortDescription") and Save
     * <p>
     * Step 4: Go to Roku CMS
     * <p>
     * Step 5: Update by MPX Updater chosen program
     * <p>
     * Validation: Check Short description node in publishing json
     * Expected result: Short description node is "shortDescription"
     */


    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkPublishNotEmptyShortDescription(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
//      Step 1 and 2
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);

        MpxAssetUpdateBuilder builder = mpxLayer.getMpxAssetUpdateBuilder();

        String shortDescription = SimpleUtils.getRandomStringWithRandomLength(15);

        builder.updateShortDescription(shortDescription);

//      Step 3
        mpxLayer.mpxAssetUpdater(builder);

//      Step 4
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 5
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

//      Validation
        List<LocalApiJson> actualMetadata = requestHelper.getLocalApiJsons(mainRokuAdminPage.getLogURL(brand));
        Assertion.assertEquals(shortDescription, actualMetadata.get(0).getRequestData().get("shortDescription").getAsString(), "Short description is not correct");
        String emptyShortDescription = "";
        builder.updateShortDescription(emptyShortDescription);

        mpxLayer.mpxAssetUpdater(builder);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

        List<LocalApiJson> actualMetadata2 = requestHelper.getLocalApiJsons(mainRokuAdminPage.getLogURL(brand));
        Assertion.assertEquals(shortDescription, actualMetadata2.get(0).getRequestData().get("shortDescription").getAsString(), "Short description is not correct");

    }
}

