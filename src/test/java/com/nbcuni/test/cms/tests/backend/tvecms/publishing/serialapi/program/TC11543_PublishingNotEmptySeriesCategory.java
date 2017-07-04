package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 17-Dec-15.
 */

/**
 * TC11543
 *
 * Step 1: Go to MPX
 *
 * Step 2: Choose any Program
 *
 * Step 3: Fill Series Category ("seriesCategory") and Save
 *
 * Step 4: Go to Roku CMS
 *
 * Step 5: Update by MPX Updater chosen program
 *
 * Validation: Check Series Category node in publishing json
 * Expected result: Series Category node is "seriesCategory"
 */
public class TC11543_PublishingNotEmptySeriesCategory extends BaseAuthFlowTest {

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishNotEmptySeriesCategory(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
//      Step 1 and 2
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);

        MpxAssetUpdateBuilder builder = mpxLayer.getMpxAssetUpdateBuilder();

        String seriesCategory = SimpleUtils.getRandomStringWithRandomLength(15);

        builder.updateSeriesCategory(seriesCategory);

//      Step 3
        mpxLayer.mpxAssetUpdater(builder);

//      Step 4
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 5
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

//      Validation
        RokuProgramJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.PROGRAM);

        Assertion.assertEquals(seriesCategory, actualMetadata.getSeriesCategory(), "Series Category is not corrected");
    }
}
