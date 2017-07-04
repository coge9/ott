package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm.BaseTaxonomyTermTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
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
 * The Edit Taxonomy Page is opened
 * 2. Check message attributes
 * There are attributes:
 * action = 'post'
 * entityType = 'taxonomyTerms'
 */

public class TC17181_CheckMessageAttributesForEpisodeTypeTaxonomyPOSTRequest extends BaseTaxonomyTermTest {

    private TaxonomyTerm term = TaxonomyTermCreator.getEpisodeTypeTerm("AQA episode type");

    @Test(groups = {"taxonomy_term_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkPublishingTag(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createTerm(term.getTaxonomyType().getName(), term);

        String url = rokuBackEndLayer.getLogURL(brand);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.TAXONOMY_TERM.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTaxonomy() {
        deleteTaxonomyTerm(term.getTaxonomyType().getName(), term);
    }
}
