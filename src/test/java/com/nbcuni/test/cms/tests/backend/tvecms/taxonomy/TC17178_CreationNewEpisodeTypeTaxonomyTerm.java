package com.nbcuni.test.cms.tests.backend.tvecms.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm.BaseTaxonomyTermTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 1/19/2017.
 */

/**
 * Pre-Conditions:
 * 1. Login in CMS for Concerto brand as admin
 * 2. Go to /admin/structure/taxonomy/episode_type/add
 * Steps:
 * 1. Fill required fields and save
 * New Taxonomy term is created
 * 2. Open created term for edit
 * Edit page is opened
 * Validation    Check data
 * Data of term matches with expected
 */

public class TC17178_CreationNewEpisodeTypeTaxonomyTerm extends BaseTaxonomyTermTest {

    private TaxonomyTerm term = TaxonomyTermCreator.getEpisodeTypeTerm("AQA episode type");

    @Test(groups = {"taxonomy_term"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkPublishingTag(final String brand) {
        this.brand = brand;

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createTerm(term.getTaxonomyType().getName(), term);
        TaxonomyTerm actualTaxonomyData = navigateToTaxonomyTerm(term.getTaxonomyType().getName(), term).getData();

        softAssert.assertEquals(term, actualTaxonomyData, "Episode type taxonomy data is not matched",
                "Episode type taxonomy data is not matched", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTaxonomy() {
        deleteTaxonomyTerm(term.getTaxonomyType().getName(), term);
    }
}
