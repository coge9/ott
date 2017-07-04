package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory.WithoutRequiredPostCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */

/**
 * TC14128
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Episode
 * Add new Episode page is opened
 * 3. Fill required fields:
 * Mediaum Description
 * Long Description
 * Secondary Episode Number
 * Original Air Date
 * TV Rating
 * Production Number
 * Supplementary Airing
 * Fields is filled
 * 4. Save as draft
 * Error message is presented
 * Validation: Check content list
 * New Episode doesn't exists
 */
public class TC14128_PostRequiredFields extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("withoutRequiredPost")
    private WithoutRequiredPostCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"post"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void mediaGalleryRequiredFields(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);
        softAssert.assertEquals(MessageConstants.ERROR_REQUIRED_FIELDS_FOR_POST_CONTENT_TYPE, contentTypePage.getErrorMessage(),
                "Error message text is incorrect", "Error message text is correct", webDriver);

        softAssert.assertAll();
    }
}
