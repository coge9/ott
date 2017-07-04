package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.header;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuHeaderJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC10263_AlreadyPostedHeaderAndNewAddedHeaderPost extends
        BaseAuthFlowTest {

    /**
     * class TC10263 - Roku CMS: API Publish for entity Update(header): Already posted header and new added header POST
     *
     * precondition 1 - Create new OTT App.
     * precondition 2 - Genererate one header
     * precondition 3 - Publish Page.
     * precondition 4 - Genenerate one more header
     *
     * Step 1: Regenerate previous one
     * Step 2: Publish page
     * Step 3: Check published headers
     *
     * postcondition 1 - delele test OTT page
     *
     * */


    private String pageAlias;
    private String pageTitle;
    private String appName;
    private String mpvdName1;
    private String mpvdId1;
    private String headerUrl1;
    private String mpvdName2;
    private String mpvdId2;
    private String headerUrl2;
    private SoftAssert softAssert = new SoftAssert();
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();

    public void createPage() {
        // precondition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        pageTitle = pageInfo.getTitle();
        pageAlias = pageInfo.getAlias().getSlugValue();
        appName = pageInfo.getPlatform().getAppName();
        // precondition 2
        String idAndName = rokuBackEndLayer
                .getRandomMvpdForHeaderGeneration(CmsPlatforms.ROKU.getAppName());
        mpvdId1 = idAndName.split(";")[0];
        mpvdName1 = idAndName.split(";")[1];
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle,
                headerStyle, mpvdName1);
        headerUrl1 = headerGeneration.getOttPageHeaders(pageTitle).get(mpvdId1);
        // precondition 3
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        editPage.elementPublishBlock().publishByTabName();
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after publishing");
        editPage.save();
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after saving");
        // precondition 4
        String idAndName2 = idAndName;
        int count = 0;
        while (idAndName2.equals(idAndName)) {
            if (count > 5) {
                throw new RuntimeException(
                        "Unable to get second mvpd for header generation");
            }
            idAndName2 = rokuBackEndLayer.getRandomMvpdForHeaderGeneration(appName);
            count++;
        }
        mpvdId2 = idAndName2.split(";")[0];
        mpvdName2 = idAndName2.split(";")[1];
        headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle,
                headerStyle, mpvdName2);
        headerUrl2 = headerGeneration.getOttPageHeaders(pageTitle).get(mpvdId2);
    }

    @Test(groups = {"header_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void noHeaderPublishingIfNoHeader(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPage();
        // Step 1
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle,
                headerStyle, mpvdName1);
        // Step 2
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        editPage.elementPublishBlock().publishByTabName();
        // Step 3
        String url = editPage.getLogURL(brand);

        List<RokuHeaderJson> createHeaders = requestHelper.getParsedResponse(url, SerialApiPublishingTypes.HEADER);

        RokuHeaderJson rokuHeaderJson1 = new RokuHeaderJson(
                SimpleUtils.getMd5(headerUrl1), CmsPlatforms.getAppIdBYAppName(appName, brand), mpvdId1, pageAlias,
                headerUrl1);
        RokuHeaderJson rokuHeaderJson2 = new RokuHeaderJson(
                SimpleUtils.getMd5(headerUrl2), CmsPlatforms.getAppIdBYAppName(appName, brand), mpvdId2, pageAlias,
                headerUrl2);
        softAssert.assertTrue(createHeaders.size() == 1,
                "Number of created headers are differ from 1");
        softAssert.assertTrue(createHeaders.size() == 1,
                "Number of updated headers are differ from 1");
        softAssert.assertTrue(createHeaders.contains(rokuHeaderJson1),
                "There is no published header for " + mpvdId2);
        softAssert.assertTrue(createHeaders.contains(rokuHeaderJson2),
                "There is no published header for " + mpvdId1);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10212() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageTitle);
        mainRokuAdminPage.logOut(brand);
    }
}
