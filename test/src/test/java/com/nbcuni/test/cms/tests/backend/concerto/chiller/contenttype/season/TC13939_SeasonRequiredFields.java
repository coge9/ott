package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.season;

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
 * Created by Ivan_Karnilau on 07-Apr-16.
 */

/**
 * TC13939
 *
 * 1. Go to CMS as admin
 * 2. Navigate to add new Season
 * 3. Fill required fields:
 * Mediaum Description
 * Long Description
 * Program
 * Start Date
 * End Date
 * 4. Save as draft
 * Validation: Check content list
 * New Season doesn't exists
 */
public class TC13939_SeasonRequiredFields extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("withoutRequiredSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"season"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void mediaGalleryRequiredFields(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);
        softAssert.assertContains(contentTypePage.getErrorMessage(), MessageConstants.SEASON_REQUIRED_FIELDS, "Error message is not contains expected",
                "Error message is contains validation", webDriver);
        softAssert.assertAll();
    }
}
