package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ivan_Karnilau on 23-Nov-15.
 */

/**
 * TC10679
 * <p>
 * Pre-Conditions:
 * 1. Go To MPX
 * 2.Select an EPISODE
 * 3.Set empty values for 'Expiration,Available and AIR' fields
 * 4. save
 * <p>
 * Step 1: Go To Roku AS Admin
 * <p>
 * Step 2: Go to Content->MPX Updater
 * <p>
 * Step 3: Put MPX Id of the asset from pre-condition and click 'Update'
 * <p>
 * Step 4: Using session id of the POST request from step 3, call the internal API service(AQA)
 * [site]/services/api/v1/log/parameters[session_id]=ID
 * <p>
 * Step 5: Look into Date's field values of the 'Request Data' object
 * Expected: Next data is present:
 * Expiration date = current date+10 year
 * Available date = Node update date (see in the Content page 'Updated' column)
 * Air date = Node update date (see in the Content page 'Updated' column)
 */
public class TC10679_CheckPOSTTheEmptyValuesOfTheDatesFields extends BaseAuthFlowTest {

    private static final String VIDEO_TITLE = "AQA_OTT_VIDEO";

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPOSTTheEmptyValuesOfTheDatesFields(String brand) throws ParseException {

        SoftAssert softAssert = new SoftAssert();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);

        mpxLayer.updateAvailableExpirationAirDate(new Long("0"), new Long("0"), new Long("0"));

        Date expectedExpirationDate = DateUtil.getDateAfterCurrentByYearNumber(10);

        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(Config.getInstance().getRokuMPXVideoID(brand, Instance.STAGE));

        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByTitle(VIDEO_TITLE).searchByType(ContentType.TVE_VIDEO).apply();

        String string = contentPage.getNodeUpdatedDate(1);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy - HH:mm a", Locale.US);
        Date date = format.parse(string);

        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        Date actualAvailableDate = format.parse(actualMetadata.getAvailableDate());
        Date actualAirDate = format.parse(actualMetadata.getAirDate());
        Date actualExpirationDate = format.parse(actualMetadata.getExpirationDate());

        softAssert.assertEquals(expectedExpirationDate, actualExpirationDate, "Expiration date is not corrected",
                "Expiration date is corrected");

        softAssert.assertEquals(date, actualAvailableDate, "Available date is not corrected",
                "Available date is corrected");

        softAssert.assertEquals(date, actualAirDate, "Air date is not corrected",
                "Air date is corrected");

        softAssert.assertAll();
    }
}
