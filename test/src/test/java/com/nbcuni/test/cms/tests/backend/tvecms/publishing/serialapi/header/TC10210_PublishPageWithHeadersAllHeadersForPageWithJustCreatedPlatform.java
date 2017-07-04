package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.header;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuHeaderJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class TC10210_PublishPageWithHeadersAllHeadersForPageWithJustCreatedPlatform
        extends BaseAuthFlowTest {

    /**
     * TC10210 - Roku CMS: Publishing: Publish page with headers (all headers) for page with just created app.
     *
     * precondition 1 - Create new OTT App.
     * precondition 2 - Create new OTT page with app from step 1.
     * precondition 3 - Generate headers for All Mvpds.
     *
     * Step 1: Go to Page, created in precondition
     * Step 2: Publish to same api servies instance
     * Step 3: Check published headers
     *
     * postcondition 1 - delele test OTT page
     *
     * */


    private PageForm pageInfo;
    private String appName;
    private String allMvpds = "All MVPDs";
    private SoftAssert softAssert = new SoftAssert();
    private Map<String, Map<String, String>> mvpds;
    private Map<String, String> headers;
    private PlatformEntity platformEntity;
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();

    public void createApp() {
        // precondition 1
        platformEntity = rokuBackEndLayer.createPlatform();
    }

    public void createPage() {
        // precondition 2
        appName = platformEntity.getName();
        pageInfo = rokuBackEndLayer.createPage();

        mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration(appName);
        // precondition 3
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageInfo.getTitle(),
                headerStyle, allMvpds);
        Assertion.assertTrue(headerGeneration.isStatusMessageShown(),
                "Status message is not shown after header generation");
        headers = headerGeneration.getOttPageHeaders(pageInfo.getTitle());
        Assertion.assertFalse(headers.isEmpty(),
                "There are no generated headers", webDriver);
        softAssert.assertEquals(mvpds.size(), headers.size(),
                "Number of Generated headers is wrong");
    }

    @Test(groups = {"header_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void noHeaderPublishingIfNoHeader(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createApp();
        createPage();
        // step 1
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageInfo.getTitle());
        // step 2
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after publishing", webDriver);
        // step 3
        String url = editPage.getLogURL(brand);
        List<LocalApiJson> apis = requestHelper.getLocalApiJsons(url,
                SerialApiPublishingTypes.HEADER);

        List<RokuHeaderJson> publishedHeaders = requestHelper
                .getParsedResponse(url, SerialApiPublishingTypes.HEADER);
        softAssert.assertEquals(headers.size(), publishedHeaders.size(),
                "Wrong number of published headers");
        for (RokuHeaderJson header : publishedHeaders) {
            softAssert.resetTempStatus();
            Utilities.logInfoMessage("Validate header for " + header.getMvpdId());
            String headerUrl = headers.get(header.getMvpdId());
            softAssert.assertTrue(headerUrl != null, "There is no such header");
            if (headerUrl != null) {
                RokuHeaderJson rokuHeaderJson = new RokuHeaderJson(SimpleUtils.getMd5(headerUrl), CmsPlatforms.getAppIdBYAppName(appName, brand), header.getMvpdId(), pageInfo.getAlias().getSlugValue(),
                        headerUrl);
                softAssert.assertEquals(rokuHeaderJson, header, "Header is differ from expected");
            }
            if (softAssert.getTempStatus()) {
                Utilities.logInfoMessage("Validation passed.");
            }
        }
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10212() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        mainRokuAdminPage.logOut(brand);
    }
}
