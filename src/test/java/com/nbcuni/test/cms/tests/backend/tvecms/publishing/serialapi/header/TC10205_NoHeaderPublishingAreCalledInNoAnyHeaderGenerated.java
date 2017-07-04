package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.header;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC10205_NoHeaderPublishingAreCalledInNoAnyHeaderGenerated extends
        BaseAuthFlowTest {


    /**
     * TC10205 - Roku CMS: Publishing: No header publishing are called in no any header generated.
     *
     * precondition 1 - Create new OTT App.
     *
     * Step 1: Open created page for editing
     * Step 2: Publish page
     * Step 3: Check no headers were published
     *
     * postcondition 1 - delele test OTT page
     *
     * */

    private String pageTitle;

    public void createPage() {
        // precondition 1
        pageTitle = rokuBackEndLayer.createPage(CmsPlatforms.ROKU).getTitle();
    }

    @Test(groups = {"header_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void noHeaderPublishingIfNoHeader(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPage();
        // Step 1
        EditPageWithPanelizer editPage = mainRokuAdminPage.openOttPage(brand).clickEdit(
                pageTitle);
        // Step 2
        editPage.elementPublishBlock().publishByTabName();
        // Step 3
        String url = editPage.getLogURL(brand);
        List<LocalApiJson> apis = requestHelper.getLocalApiJsons(url,
                SerialApiPublishingTypes.HEADER);
        Assertion.assertTrue(apis.isEmpty(), "There are some headers which were published");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC10212() {
        // postcondition 1
        rokuBackEndLayer.deleteOttPage(pageTitle);
        mainRokuAdminPage.logOut(brand);
    }
}
