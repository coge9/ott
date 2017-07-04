package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.event;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */

/**
 * TC14014
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Event
 * Add new Event page is opened
 * 3. Fill all fields:
 * Title
 * Subhead
 * Short Description
 * Mediaum Description
 * Long Description
 * Genre
 * Rating
 * Unscripted
 * Status
 * Syndicated
 * Related Series
 * Type
 * Release Year
 * Channel Original
 * Air Time
 * Fields is filled
 * 4. Save as draft
 * Success message is presented
 * Validation    Check content list
 * New Event exists
 */
public class TC14014_CreationEvent extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"event", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content, false);

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_EVENT).searchByTitle(content.getTitle()).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Event name is empty", webDriver);

        softAssert.assertAll();
    }
}
