package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */

/**
 * TC14126
 * <p>
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Episode
 * Add new Episode page is opened
 * 3. Fill all fields:
 * Title
 * Subhead
 * Short Description
 * Mediaum Description
 * Long Description
 * Series
 * Season
 * Episode Type
 * Episode Number
 * Secondary Episode Number
 * Original Air Date
 * TV Rating
 * Production Number
 * Supplementary Airing
 * Fields is filled
 * 4. Save as draft
 * Success message is presented
 * Validation    Check content list
 * New Episode exists
 */
public class TC14126_CreationPost extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultPost")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"post", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationPost(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content, false);

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_POST).searchByTitle(content.getTitle()).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Post name is empty", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePostTC14126() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage("Unable to perform tear-down method " + Utilities.convertStackTraceToString(e));
        }

    }
}
