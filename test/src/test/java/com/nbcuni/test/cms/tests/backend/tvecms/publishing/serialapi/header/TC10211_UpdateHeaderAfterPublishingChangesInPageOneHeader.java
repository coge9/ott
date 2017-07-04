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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TC10211_UpdateHeaderAfterPublishingChangesInPageOneHeader extends
        BaseAuthFlowTest {

    /**
     * TC10211 - Roku CMS: Publishing: Update header after publishing changes in page (one header)
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
    private SoftAssert softAssert = new SoftAssert();
    private String warningMessage = "Current state of OTT page header image for %s MVPD has been already published to Development - skipping data sending.";
    private String mpvdName;
    private String mpvdId;
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();

    public void createPage() {
        // precondition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        pageTitle = pageInfo.getTitle();
        appName = pageInfo.getPlatform().getAppName();
        // precondition 2
        String idAndName = rokuBackEndLayer
                .getRandomMvpdForHeaderGeneration(appName);
        mpvdId = idAndName.split(";")[0];
        mpvdName = idAndName.split(";")[1];
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle,
                headerStyle, mpvdName);
        // precondition 3
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        editPage.elementPublishBlock().publishByTabName();
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after publishing");
    }

    @Test(groups = {"header_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void noHeaderPublishingIfNoHeader(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPage();
        // step 1
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageTitle);
        // step 2
        PageForm pageInfo = editPage.updateFields(new ArrayList<Module>());
        pageTitle = pageInfo.getTitle();
        editPage.save();
        softAssert.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after saving");
        // step 3
        editPage.elementPublishBlock().publishByTabName();
        // step 4
        String warning = editPage.getWarningMessage();
        softAssert.assertContains(warning,
                String.format(warningMessage, mpvdId), "Warning message for "
                        + mpvdId + " is absent", webDriver);
        // step 5
        String url = editPage.getLogURL(brand);
        List<LocalApiJson> apis = requestHelper.getLocalApiJsons(url,
                SerialApiPublishingTypes.HEADER);
        softAssert.assertTrue(apis.isEmpty(), "There are " + apis.size()
                + " request for header posting");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10211() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageTitle);
        mainRokuAdminPage.logOut(brand);
    }
}
