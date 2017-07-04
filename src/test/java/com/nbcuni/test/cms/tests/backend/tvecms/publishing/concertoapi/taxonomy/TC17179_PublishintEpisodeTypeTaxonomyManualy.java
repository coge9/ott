package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm.BaseTaxonomyTermTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.TaxonomyTermJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 1/19/2017.
 */

/**
 * Pre-Conditions:
 * 1. Create new Episode type taxonomy term
 * Steps:
 * 1. Open created term for edit and publish
 * Term is published
 * Validation    Check publishing json
 * {
 * "uuid": "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
 * "itemType": "taxonomyTerm",
 * "title": "Human Rights",
 * "revision": 654,
 * "vocabularyType": "episodeType",
 * "parentUuids": [
 * "70db102c-52d8-4a78-8cb0-0cdf79713b3f"
 * ],
 * "description": "description of the taxonomyTerm",
 * "weight": 654
 * }
 */

public class TC17179_PublishintEpisodeTypeTaxonomyManualy extends BaseTaxonomyTermTest {

    private TaxonomyTerm term = TaxonomyTermCreator.getEpisodeTypeTerm("AQA episode type");

    @Test(groups = {"taxonomy_term_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkPublishingTag(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createTerm(term.getTaxonomyType().getName(), term);

        String url = rokuBackEndLayer.getLogURL(brand);

        TaxonomyTermJson actualTermJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.TAXONOMY_TERM);
        TaxonomyTermJson expectedTermJson = new TaxonomyTermJson().getObject(term);

        String uuid = getTaxonomyTermUuid(term.getTaxonomyType().getName(), term);
        expectedTermJson.setVocabularyType(term.getTaxonomyType().getVocabularyType());
        expectedTermJson.setUuid(uuid);

        softAssert.assertTrue(actualTermJson.verifyObject(expectedTermJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTaxonomy() {
        deleteTaxonomyTerm(term.getTaxonomyType().getName(), term);
    }
}
