package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.header;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuHeaderJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC10818_PublishPageWithOneGeneratedHeaderHeaderWithPageTitleWithoutMenu
        extends BaseAuthFlowTest {


    /**
     * TC10818 - Roku CMS: Publishing: Header: Publish page with one generated header (Header With Page Title without menu)
     *
     * precondition 1 - Create OTT Page
     * precondition 2 - Genererate one header
     *
     * Step 1: Publish page
     * Step 2: Check published headers
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private String pageAlias;
    private String pageTitle;
    private String appName;
    private String mpvdName;
    private String mpvdId;
    private String headerUrl;
    private SoftAssert softAssert = new SoftAssert();
    private HeaderStyle headerStyle = HeaderStyle.WITH_TITLE_WITHOUT_MENU;

    public void createPage() {
        // precondition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        pageTitle = pageInfo.getTitle();
        pageAlias = pageInfo.getAlias().getSlugValue();
        appName = pageInfo.getPlatform().getAppName();
        // precondition 2
        String idAndName = rokuBackEndLayer.getRandomMvpdForHeaderGeneration(appName);
        mpvdId = idAndName.split(";")[0];
        mpvdName = idAndName.split(";")[1];
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageTitle, headerStyle, mpvdName);
        headerUrl = headerGeneration.getOttPageHeaders(pageTitle).get(mpvdId);
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
        editPage.elementPublishBlock().publishByTabName();
        // step 2
        String url = editPage.getLogURL(brand);
        List<RokuHeaderJson> publishedHeaderOperations = requestHelper.getParsedResponse(url, SerialApiPublishingTypes.HEADER);
        RokuHeaderJson rokuHeaderJson = new RokuHeaderJson(SimpleUtils.getMd5(headerUrl), CmsPlatforms.getAppIdBYAppName(appName, brand), mpvdId, pageAlias,
                headerUrl);
        softAssert.assertTrue(publishedHeaderOperations.size() == 1, "Number of published headers are differ from 1");
        softAssert.assertTrue(publishedHeaderOperations.get(0).equals(rokuHeaderJson), "Data in JSON differ from expected");
        softAssert.assertAll();
    }


    @AfterMethod(alwaysRun = true)
    public void deletePageTC10810() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageTitle);
        mainRokuAdminPage.logOut(brand);
    }
}
