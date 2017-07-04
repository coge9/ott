package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.TaxonomyTermJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 19-Aug-16.
 */

/**
 * TC15834
 *
 * 1. Go to Chiller CMS as admin
 * Admin panel is present
 * 2. Open add new Tag page
 * Add new Tag page is opened
 * 3. Create new tag and save
 * Tag is published
 */

public class TC15834_TagsPublishingAfterCreation extends BaseTaxonomyTermTest {

    private TaxonomyTerm term;

    @BeforeMethod(alwaysRun = true)
    public void createData() {
        term = TaxonomyTermCreator.getRandomTerm("AQA tag");
    }

    @Test(groups = {"taxonomy_term_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkPublishingTag(final String brand) {
        this.brand = brand;
        String termName = TaxonomyType.TAGS.getName();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createTerm(termName, term);

        String url = rokuBackEndLayer.getLogURL(brand);

        TaxonomyTermJson actualTermJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.TAXONOMY_TERM);
        TaxonomyTermJson expectedTermJson = new TaxonomyTermJson().getObject(term);

        String uuid = getTaxonomyTermUuid(termName, term);
        expectedTermJson.setVocabularyType(termName.toLowerCase());
        expectedTermJson.setUuid(uuid);

        softAssert.assertTrue(expectedTermJson.verifyObject(actualTermJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
