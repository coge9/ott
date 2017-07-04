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
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuHeaderJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC10209_PublishPageWithHeadersOneHeaderForPageWithJustCreatedPlatform
        extends BaseAuthFlowTest {

    /**
     * TC10209 - Roku CMS: Publishing: Publish page with headers (one header) for page with just created app.
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
    private String headerUrl;
    private SoftAssert softAssert = new SoftAssert();
    private PlatformEntity platformEntity;
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();
    private String mpvdId;
    private String mpvdName;

    public void createAppTC10209() {
        // precondition 1
        platformEntity = rokuBackEndLayer.createPlatform();
    }

    public void createPageTC10209() {
        pageInfo = rokuBackEndLayer.createPage();
        String idAndName = rokuBackEndLayer.getRandomMvpdForHeaderGeneration(pageInfo.getPlatform().getAppName());
        mpvdId = idAndName.split(";")[0];
        mpvdName = idAndName.split(";")[1];
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        headerGeneration.generateOttPageHeaders(pageInfo.getTitle(), headerStyle, mpvdName);
        headerUrl = headerGeneration.getOttPageHeaders(pageInfo.getTitle()).get(mpvdId);
    }


    @Test(groups = "header_publish", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void noHeaderPublishingIfNoHeader(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createAppTC10209();
        createPageTC10209();
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);

        List<LocalApiJson> apis = requestHelper.getLocalApiJsons(url,
                SerialApiPublishingTypes.HEADER);
        List<RokuHeaderJson> publishedHeaderOperations = requestHelper.getParsedResponse(url, SerialApiPublishingTypes.HEADER);
        RokuHeaderJson rokuHeaderJson = new RokuHeaderJson(SimpleUtils.getMd5(headerUrl), CmsPlatforms.getAppIdBYAppName(pageInfo.getPlatform().getAppName(), brand), mpvdId, pageInfo.getAlias().getSlugValue(),
                headerUrl);
        softAssert.assertTrue(publishedHeaderOperations.size() == 1, "Number of published headers are differ from 1");
        softAssert.assertTrue(publishedHeaderOperations.get(0).equals(rokuHeaderJson), "Data in JSON differ from expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10212() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        mainRokuAdminPage.logOut(brand);
    }
}
