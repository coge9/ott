package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 27-Jul-16.
 */

/**
 * TC15572
 *
 * 1. Go to CMS as Editor
 * Admin panel is present
 * 2. Create new Series with required fields [series_name]
 * Series is created
 * 3. Try create new Series with required fields [series_name]
 * Error message "Title is not unique" is present. New Series is not created
 * 4. Change title and save
 * Error message is not present
 * Content with new title is created
 */

public class TC15572_Series_CheckUniqueTitle extends BaseAuthFlowTest {

    private Content content;
    private Content newContent;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
        newContent = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"series"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationTitle(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(content);

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);

        softAssert.assertContains(contentTypePage.getErrorMessage(), String.format(MessageConstants.TITLE_IS_ALREADY_USED_FOR_NODE,
                content.getTitle()), "Error message is incorrect", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByTitle(content.getTitle()).apply();
        softAssert.assertTrue(contentPage.isContentOnlyOne(), "The search result on given name is not 1", webDriver);

        contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertContains(contentTypePage.getErrorMessage(), String.format(MessageConstants.TITLE_IS_ALREADY_USED_FOR_NODE,
                content.getTitle()), "Error message is incorrect", webDriver);

        contentTypePage.create(newContent);

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByTitle(content.getTitle()).apply();
        softAssert.assertTrue(contentPage.isContentOnlyOne(), "The search result on given name is not 1", webDriver);

        contentPage.searchByTitle(newContent.getTitle()).apply();
        softAssert.assertTrue(contentPage.isContentOnlyOne(), "The search result on given name is not 1", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentTypesByBulkOperation(content, newContent);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
