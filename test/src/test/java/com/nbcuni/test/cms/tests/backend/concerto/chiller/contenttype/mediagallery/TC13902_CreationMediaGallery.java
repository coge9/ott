package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.mediagallery;

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
 * TC13902
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Media Gallery
 * Add new Media Gallery page is opened
 * 3. Fill all fields:
 * Title
 * Subhead
 * Short Description
 * Mediaum Description
 * Long Description
 * Fields is filled
 * 4. Save as draft
 * Success message is presented
 * Validation    Check content list
 * New Media Gallery exists
 */
public class TC13902_CreationMediaGallery extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"media_gallery", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
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
        contentPage.searchByType(ContentType.TVE_MEDIA_GALLERY).searchByTitle(content.getTitle()).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Media Gallery name is empty", webDriver);

        softAssert.assertAll();
    }
}
