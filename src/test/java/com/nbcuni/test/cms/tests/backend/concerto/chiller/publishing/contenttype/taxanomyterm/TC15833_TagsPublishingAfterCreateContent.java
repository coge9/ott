package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.TaxonomyTermJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 18-Aug-16.
 */

/**
 * TC15833
 *
 * 1. Go to Chiller CMS as admin
 * Admin panel is present
 * 2. Open add new any content type
 * Add new content page is opened
 * 3. Fill required filds
 * Fields is filled
 * 4. Add new tags and save
 * Content is created.
 * Tags are published
 */
public class TC15833_TagsPublishingAfterCreateContent extends BaseTaxonomyTermTest {

    private Content content;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"tags"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(content);

        String url = rokuBackEndLayer.getLogURL(brand);

        List<TaxonomyTermJson> actualTagsJson = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.TAXONOMY_TERM);

        List<TaxonomyTermJson> expectedTagsJson = new ArrayList<>();

        for (Tag tag : content.getAssociations().getTags()) {
            TaxonomyTermJson taxonomyTermJson = new TaxonomyTermJson().getObject(tag);
            taxonomyTermJson.setUuid(getTaxonomyTermUuid(tag));
            expectedTagsJson.add(taxonomyTermJson);
        }

        for (int i = 0; i < actualTagsJson.size(); i++) {
            softAssert.assertTrue(expectedTagsJson.get(i).verifyObject(actualTagsJson.get(i)), "Tags are not equal", "Tags are equal");
        }

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logSevereMessage("Could not delete the content");
        }
    }
}
