package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.header;

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
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.RequestHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuHeaderJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC10206_PublishPageWithOneHeader
        extends BaseAuthFlowTest {

    /**
     * TC10208 - Roku CMS: Publishing: Page is available for publishing after
     * headers are regenerated.
     * <p>
     * precondition 1 - Create new OTT page.
     * precondition 2 - Publish page.
     * <p>
     * Step 1: Go to OTT -> OTT Header Image Generation
     * Step 2: Generate header for one one all MVPDs
     * Step 3: Go to Edit page
     * Step 4: Publish button is available (button is enabled).
     * <p>
     * <p>
     * postcondition 1 - delele test OTT page
     */

    private PageForm pageInfo;
    private String mpvdName;
    private String mpvdId;
    private String headerUrl;
    private HeaderStyle headerStyle = HeaderStyle.randomHeaderStyle();

    public void createPage() {
        // precondition 1
        pageInfo = rokuBackEndLayer.createPage();
        String idAndName = rokuBackEndLayer
                .getRandomMvpdForHeaderGeneration(pageInfo.getPlatform().getAppName());
        mpvdName = idAndName.split(";")[1];
        mpvdId = idAndName.split(";")[0];
    }

    @Test(groups = {"header_publish", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void publishOneHeaderForPage(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPage();
        // step 1
        OTTHeaderImageGenerationPage headerGeneration = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 2
        headerGeneration.generateOttPageHeaders(pageInfo.getTitle(),
                headerStyle, mpvdName);
        headerUrl = headerGeneration.getOttPageHeaders(pageInfo.getTitle())
                .get(mpvdId);
        Assertion.assertTrue(headerGeneration.isStatusMessageShown(),
                "Status message is not shown after header generation");
        // step 3
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        // step 4
        editPage.elementPublishBlock().publishByTabName();
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                "Status message is not shown after publishing");

        //Step 5
        String url = editPage.getLogURL(brand);
        RequestHelper helper = new RequestHelper();

        List<RokuHeaderJson> publishedHeaderOperations = helper.getParsedResponse(url, SerialApiPublishingTypes.HEADER);
        RokuHeaderJson rokuHeaderJson = new RokuHeaderJson(
                SimpleUtils.getMd5(headerUrl), pageInfo.getPlatform().getAppName(), mpvdId, pageInfo.getAlias().getSlugValue(),
                headerUrl);
        softAssert.assertTrue(publishedHeaderOperations.size() == 1,
                "Number of published headers are differ from 1");
        rokuHeaderJson.setAppId(CmsPlatforms.getAppIdBYAppName(rokuHeaderJson.getAppId(), brand));
        softAssert.assertTrue(rokuHeaderJson.equals(publishedHeaderOperations.get(0)), "Data in JSON differ from expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10206() {
        try {
            if (StringUtils.isEmpty(pageInfo.getTitle())) {
                // postcondition 1
                rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear-down method " + Utilities.convertStackTraceToString(e));
        }

    }
}
