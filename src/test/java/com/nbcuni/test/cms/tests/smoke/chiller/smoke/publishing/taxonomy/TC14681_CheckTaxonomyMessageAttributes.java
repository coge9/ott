package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;
import com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm.BaseTaxonomyTermTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC14681_CheckTaxonomyMessageAttributes extends BaseTaxonomyTermTest {

    /**
     * Steps:
     * 1. Go To CMS as editor
     * The editor Panel is present
     * 2. Go To Structure->Taxonomy Page and select a Taxonomy (e.g Tag, Categories, etc)
     * The Edit Taxonomy Page is opened
     * 3. Click 'Publish' button and send POST request to Amazon API
     * The POST request is send to the Amazon queue<br/>
     * API Log present 'success' status message per requests
     * 4. Go To Amazon consol and analize Header of POST request of Taxonomy
     * There are attributes:
     * action = 'post'
     * entityType = 'taxonomyTerms'
     */

    private TaxonomyTerm term;
    private String termName = TaxonomyType.TAGS.getName();

    @BeforeMethod(alwaysRun = true)
    public void createData() {
        term = TaxonomyTermCreator.getRandomTerm("AQA tag");
    }

    @Test(groups = {"taxonomy_term_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkPublishingTag(final String brand) {
        this.brand = brand;

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        createTerm(termName, term);

        String url = rokuBackEndLayer.getLogURL(brand);

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.TAXONOMY_TERM).get(0);

        softAssert.assertEquals(Action.POST.getAction(), localApiJson.getAttributes().getAction().getStringValue(),
                "The action message attribute are not matched",
                "The action message attribute are matched");

        softAssert.assertEquals(term.getType().getEntityType(), localApiJson.getAttributes().getEntityType().getStringValue(),
                "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTaxonomy() {
        try {
            deleteTaxonomyTerm(termName, term);
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear-down method " + Utilities.convertStackTraceToString(e));
        }
    }
}
