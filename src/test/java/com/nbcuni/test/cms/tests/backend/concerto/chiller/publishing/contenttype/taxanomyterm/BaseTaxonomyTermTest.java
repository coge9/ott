package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.AddTaxonomyTermPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTermCreator;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.CustomBrandNames;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.TaxonomyTermJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.Arrays;

public class BaseTaxonomyTermTest extends BaseAuthFlowTest {

    private TaxonomyTerm parentTerm;

    private TaxonomyTerm childTerm;

    private String globalPrefix;

    public void createData(String prefix) {
        globalPrefix = prefix;
        parentTerm = TaxonomyTermCreator.getRandomTerm(prefix);
        childTerm = TaxonomyTermCreator.getRandomTerm(prefix);
        childTerm.setParents(Arrays.asList(parentTerm.getTitle()));
    }

    protected void checkPublishingTerm(String termName, String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        // Create parent and child terms and get their UUIDs 
        createTerm(termName, parentTerm);
        String parentUuid = getTaxonomyTermUuid(termName, parentTerm);
        createTerm(termName, childTerm);
        String childUuid = getTaxonomyTermUuid(termName, childTerm);

        // Publish child term        
        String url = publishTerm(termName, childTerm);
        TaxonomyTermJson actualTermJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.TAXONOMY_TERM);
        TaxonomyTermJson expectedTermJson = new TaxonomyTermJson().getObject(childTerm);

        // Populate expected JSON with UUIDs and vocabulary type
        expectedTermJson.setVocabularyType(termName.toLowerCase());
        expectedTermJson.setParentUuids(Arrays.asList(parentUuid));
        expectedTermJson.setUuid(childUuid);

        softAssert.assertTrue(expectedTermJson.verifyObject(actualTermJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    protected void validateTermScheme(String termName, String... brands) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brands.length > 0 ? brands[0] : brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        // Create parent and child terms
        createTerm(termName, parentTerm);
        createTerm(termName, childTerm);

        // Publish child term       
        String url = publishTerm(termName, childTerm);
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateTaxonomyTermBySchema(localApiJson.getRequestData().toString()),
                "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    protected void checkPublishingUpdatedTerm(String termName, String brand) {
        TaxonomyTerm newParentTerm = TaxonomyTermCreator.getRandomTerm(globalPrefix);
        TaxonomyTerm newChildTerm = TaxonomyTermCreator.getRandomTerm(globalPrefix);
        newChildTerm.setParents(Arrays.asList(newParentTerm.getTitle()));

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        // Create terms
        AddTaxonomyTermPage addTermPage = createTerm(termName, parentTerm);
        createTerm(termName, childTerm);
        addTermPage = createTerm(termName, newParentTerm);
        String newParentUuid = getTaxonomyTermUuid(termName, newParentTerm);

        // Publish child term
        publishTerm(termName, childTerm);

        // Update child term
        navigateToTaxonomyTerm(termName, childTerm);
        addTermPage.openEditPage();
        addTermPage.createTaxonomyTerm(newChildTerm);
        softAssert.assertTrue(addTermPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        webDriver.navigate().refresh();
        String newChildUuid = getTaxonomyTermUuid(termName, newChildTerm);

        // Publish updated child term        
        String url = publishTerm(termName, newChildTerm);

        // Get and verify JSON
        TaxonomyTermJson actualTermJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.TAXONOMY_TERM);
        TaxonomyTermJson expectedTermJson = new TaxonomyTermJson().getObject(newChildTerm);

        expectedTermJson.setVocabularyType(termName.toLowerCase());
        expectedTermJson.setParentUuids(Arrays.asList(newParentUuid));
        expectedTermJson.setUuid(newChildUuid);

        softAssert.assertTrue(expectedTermJson.verifyObject(actualTermJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    private String publishTerm(String termName, TaxonomyTerm term) {
        AddTaxonomyTermPage addTermPage = navigateToTaxonomyTerm(termName, term);
        addTermPage.openEditPage();
        addTermPage.getActionBlock().publish();
        softAssert.assertTrue(addTermPage.isStatusMessageShown(),
                "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        return addTermPage.getLogURL(brand);
    }

    protected AddTaxonomyTermPage createTerm(String termName, TaxonomyTerm term) {
        TaxonomyPage taxonomyPage = mainRokuAdminPage.openPage(TaxonomyPage.class, brand);
        AddTaxonomyTermPage addTermPage = taxonomyPage.clickAddTermsLinkForTerm(termName);
        addTermPage.createTaxonomyTerm(term);
        softAssert.assertTrue(addTermPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        return addTermPage;
    }

    protected String getTaxonomyTermUuid(String termName, TaxonomyTerm term) {
        navigateToTaxonomyTerm(termName, term);
        AddTaxonomyTermPage addTermPage = new AddTaxonomyTermPage(webDriver, aid);
        return addTermPage.openDevelPage().getUuid();
    }

    protected String getTaxonomyTermUuid(TaxonomyTerm term) {
        return getTaxonomyTermUuid(term.getTaxonomyType().getName(), term);
    }

    protected AddTaxonomyTermPage navigateToTaxonomyTerm(String termName, TaxonomyTerm term) {
        String partUrl = termName + "/" + term.getTitle();
        partUrl = CustomBrandNames.CHILLER.equals(CustomBrandNames.getBrandByName(brand)) ? partUrl.replaceAll(" ", "-") :
                partUrl.replaceAll(" ", "");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + partUrl);
        mainRokuAdminPage.openEditPage();
        return new AddTaxonomyTermPage(webDriver, aid);
    }

    protected void deleteTaxonomyTerm(String termName, TaxonomyTerm term) {
        navigateToTaxonomyTerm(termName, term).openEditPage();
        AddTaxonomyTermPage taxonomyTermPage = new AddTaxonomyTermPage(webDriver, aid);
        taxonomyTermPage.getActionBlock().delete();
        ConfirmationPage confirmationPage = new ConfirmationPage(webDriver, aid);
        confirmationPage.clickSubmit();
    }
}
