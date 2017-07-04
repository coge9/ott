package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
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
 * Step 3: Clean Series Category and Save
 *
 * Step 4: Go to Roku CMS
 *
 * Step 5: Update by MPX Updater chosen program
 *
 * Validation: Check Series Category node in publishing json
 * Expected result: Series Category node is "" (empty string)
 */
public class TC11544_PublishingEmptySeriesCategory extends BaseAuthFlowTest {

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishNotEmptySeriesCategory(String brand) {

//      Step 1 and 2
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);

        MpxAssetUpdateBuilder builder = mpxLayer.getMpxAssetUpdateBuilder();

        String seriesCategory = "";

        builder.updateSeriesCategory(seriesCategory);

//      Step 3
        mpxLayer.mpxAssetUpdater(builder);

//      Step 4
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 5
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

//      Validation
        RokuProgramJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.PROGRAM);

        Assertion.assertEquals(seriesCategory, actualMetadata.getSeriesCategory(), "Series Category is not corrected");
    }
}
