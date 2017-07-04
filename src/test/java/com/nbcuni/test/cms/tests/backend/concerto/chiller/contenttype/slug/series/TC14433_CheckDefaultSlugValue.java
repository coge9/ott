package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.slug.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC14433_CheckDefaultSlugValue extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     *     1) Login in TVE CMS as admin
     2) Create Series [series]
     3) Open created series for edit
     Step 1: Navigate to 'URL path settings' tab
     Verify: 'url alias' default value contains current title

     Step 2: Go to Basic tab, update title and click on 'Save as draft'
     Verify: Content is saved

     Step 3: Open [series] for edit
     Verify: [series] is opened

     Step 4: Navigate to 'URL path settings' tab
     Verify: 'url alias' default value is updated (new title value)

     */

    private Content content;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"chiller_slug"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void seriesRequiredFields(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message isn't presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_SERIES).searchByTitle(content.getTitle()).apply();

        contentTypePage = rokuBackEndLayer.editContentType(content);
        Slug actualSlug = contentTypePage.onSlugTab().getSlug();
        Slug expectedSlug = content.getSlugInfo();
        softAssert.assertEquals(expectedSlug, actualSlug, "Check slug value", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteSeries() {
        rokuBackEndLayer.deleteContentType(content);
    }

}
