package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */

/**
 * TC13907
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Series
 * Add new Series page is opened
 * 3. Fill required fields:
 * Mediaum Description
 * Long Description
 * Genre
 * Rating
 * Unscripted
 * Status
 * Syndicated
 * Related Series
 * Programming Timeframe
 * Regularly Scheduled Duration
 * Fields is filled
 * 4. Save as draft
 * Error message is presented
 * Validation: Check content list
 * New Series doesn't exists
 */
public class TC13907_SeriesRequiredFields extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("withoutRequiredSeries")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"series"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void seriesRequiredFields(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);
        softAssert.assertContains(contentTypePage.getErrorMessage(), MessageConstants.SERIES_REQUIRED_FIELDS, "Error message is not contains expected",
                "Error message is contains validation", webDriver);
        softAssert.assertAll();
    }
}
