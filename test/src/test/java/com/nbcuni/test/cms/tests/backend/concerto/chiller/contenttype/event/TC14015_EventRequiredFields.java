package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.event;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */

/**
 * TC14015
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Event
 * Add new Event page is opened
 * 3. Fill required fields:
 * Mediaum Description
 * Long Description
 * Genre
 * Rating
 * Unscripted
 * Status
 * Syndicated
 * Related Series
 * Type (Movie)
 * Channel Original
 * Air Time
 * Fields is filled
 * 4. Save as draft
 * Error message is presented
 * Validation: Check content list
 * New Event doesn't exists
 */
public class TC14015_EventRequiredFields extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("withoutRequiredEvent")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"event"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void mediaGalleryRequiredFields(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);

        softAssert.assertAll();
    }
}
