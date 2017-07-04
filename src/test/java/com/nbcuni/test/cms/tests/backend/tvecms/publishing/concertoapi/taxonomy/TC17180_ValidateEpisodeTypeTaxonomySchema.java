package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.taxonomy;

import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;
import com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm.BaseTaxonomyTermTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.BeforeMethod;
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
 * Validation    Validate Scheme of POST request for Taxonomy term
 * The JSON scheme of send Taxonomy is matched with scheme available by URL below:
 * http://docs.concertoapiingestmaster.apiary.io/#reference/taxonomyterm/post-taxonomyterm/generate-taxonomyterm-body-in-json-schema-format
 */

public class TC17180_ValidateEpisodeTypeTaxonomySchema extends BaseTaxonomyTermTest {

    @BeforeMethod(alwaysRun = true)
    public void createData() {
        createData("AQA category");
    }

    @Test(groups = {"taxonomy_term_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void validateCategoryScheme(String brand) {
        this.brand = brand;
        validateTermScheme(TaxonomyType.EPISODE_TYPE.getName());
    }
}
