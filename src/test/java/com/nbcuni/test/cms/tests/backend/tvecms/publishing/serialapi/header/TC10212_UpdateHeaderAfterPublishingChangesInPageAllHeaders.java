package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.header;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TC10212_UpdateHeaderAfterPublishingChangesInPageAllHeaders extends
        BaseAuthFlowTest {

    /**
     * TC10212 - Roku CMS: Publishing: Update header after publishing changes in
     * page (all headers)
     *
     * precondition 1 - Create new OTT page.
     * precondition 2 - Generate headers for All Mvpds.
     * precondition 3 - Publish page.
     *
     * Step 1: Go to Page, created in precondition
     * Step 2: Do some updates in page
     * Step 3: Publish to same api servies instance
     * Step 4: Check warning message are shown about headers was not published.
     * Step 5: Check no publish header operations was not called.
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private String pageTitle;
    private String appName;
    private String allMvpds = "All MVPDs";
    private SoftAssert softAssert = new SoftAssert();
    private String warningMessage = "Current state of OTT page header image for %s MVPD has been already published to Development - skipping data sending.";
    private Map<String, Map<String, String>> mvpds;
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();

    public void createPage() {
        // precondition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        pageTitle = pageInfo.getTitle();
        appName = pageInfo.getPlatform().getAppName();
        mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration(appName);
        // precondition 2
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle,
                headerStyle, allMvpds);
        Assertion.assertTrue(headerGeneration.isStatusMessageShown(),
                "Status message is not shown after header generation");
        Map<String, String> headers = headerGeneration.getOttPageHeaders(
                pageTitle);
        Assertion.assertFalse(headers.isEmpty(),
                "There are no generated headers", webDriver);
        softAssert.assertEquals(mvpds.size(), headers.size(),
                "Number of Generated headers is wrong");
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        // precondition 3
        editPage.elementPublishBlock().publishByTabName();
        WaitUtils.perform(webDriver).waitForPageLoad(100);
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after publishing");
    }

    @Test(groups = {"header_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void noHeaderPublishingIfNoHeader(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPage();
        // step 1
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        // step 2
        pageTitle = editPage.updateFields(new ArrayList<Module>()).getTitle();
        editPage.save();
        softAssert.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after saving");
        // step 3
        editPage.elementPublishBlock().publishByTabName();
        // step 4
        List<String> warnings = editPage.getAllWarningMessages();
        WebDriverUtil.getInstance(webDriver).attachScreenshot();
        for (Map<String, String> map : mvpds.values()) {
            String mvpdName = map.keySet().iterator().next();
            softAssert.assertTrue(
                    warnings.contains(String.format(warningMessage, mvpdName)),
                    "Warning message for " + mvpdName + " is absent");
        }
        // step 5
        String url = editPage.getLogURL(brand);
        List<LocalApiJson> apis = requestHelper.getLocalApiJsons(url,
                SerialApiPublishingTypes.HEADER);
        softAssert.assertTrue(apis.isEmpty(), "There are " + apis.size()
                + " request for header posting");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10212() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageTitle);
        mainRokuAdminPage.logOut(brand);
    }
}
