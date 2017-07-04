package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 9/23/2016.
 */

/**
 * TC15293
 * <p>
 * Pre-Conditions:
 * 1. Login in CMS as admin
 * 2. Go to /admin/structure/taxonomy
 * 3. Add new item for Tags
 * 4. Open created tag for edit
 * <p>
 * Steps:
 * 1. Click on Delete button
 * <p>
 * Expected:
 * Image is deleted.
 * Publish delete message
 */

public class TC15293_DeleteMessageTaxonomyTerm extends BaseTaxonomyTermTest {

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
        term.setUuid(getTaxonomyTermUuid(termName, term));
        deleteTaxonomyTerm(termName, term);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(term);
        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.TAXONOMY_TERM).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(term.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}