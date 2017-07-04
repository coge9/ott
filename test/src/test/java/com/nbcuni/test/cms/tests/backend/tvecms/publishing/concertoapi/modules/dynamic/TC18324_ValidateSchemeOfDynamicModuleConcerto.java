package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.dynamic;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.factory.CreationFactoryDynamicModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TC18324_ValidateSchemeOfDynamicModuleConcerto extends BaseAuthFlowTest {

    /**
     * Go to CMS and create a Page with IOS platform!
     * Make sure:
     * <p/>
     * There is a primary (e.g Development )API Endpoint
     * There is an Amazon endpoint
     * Amazon Endponit is configured to be point to the primary Development
     * <p/>
     * Steps:
     * 1.Go to CMS  as user
     * Verify: The CMS User Panel is present
     * <p/>
     * 2.Go to TVE Modules page /admin/ott/modules and create a Dynamic Module
     * Verify: The Dynamic module has Created
     * <p/>
     * 3.Go to Pages page /admin/ott/pages
     * Verify: The list of Pages is present
     * There is Page craeted in Precondition
     * <p/>
     * 4.Open Page on edit and add created in step #2 Dynamic module. Save
     * Verify: The Pages is saved with added Dynamic module
     * <p/>
     * 5.Go to the Module Edit page created in step #2
     * Verify: The Module Edit Page has Opened
     * There is Enabled Publishing Control
     * Only Primary, not Amazon endpoint is available(e.g Development)
     * <p/>
     * 6.Publish Module to Amazon API by clicking on Primary(Dev endpoint)
     * The POST request has sent
     * 7.Validate scheme of JSON
     * Verify: The JSON scheme is matched scheme available by link below:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/list
     */

    private static PageForm pageForm = null;
    private Dynamic dynamic = CreationFactoryDynamicModule.createDynamicForVideo();

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void validateSchemeOfDynamicModuleConcerto(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        rokuBackEndLayer.createModule(dynamic);

        //Step 3
        List<CmsPlatforms> platforms = RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms();
        CmsPlatforms concertoPlatform = platforms.get(random.nextInt(platforms.size()));
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(concertoPlatform);
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(dynamic));
        editPage.save();

        //Step 5
        DynamicModulePage dynamicModulePage = mainRokuAdminPage.openOttModulesPage(brand).editModule(dynamic.getTitle(), DynamicModulePage.class);

        //Step 6
        dynamicModulePage.publish();
        softAssert.assertTrue(dynamicModulePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(dynamicModulePage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 7
        String url = dynamicModulePage.getLogURL(brand);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateCollectionsBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(dynamic.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }

}
