package com.nbcuni.test.cms.tests.backend.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TC9799_RokuCmsOttHeaderImageGenerationMenuReview extends
        BaseAuthFlowTest {

    private String allMvpdsName = "All MVPDs";


    /**
     * TC9799 - Roku CMS: "OTT Header Image Generation" menu review
     *
     * Step 1: Go to Roku CMS
     * Step 2: Collect all pages
     * Step 3: Collect all taxonomy MVPD terms
     * Step 4: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 5: Check elements on the page
     * Step 6: Review "select page" dropdown
     * Step 7: Review "select mvpd" dropdown
     *
     * */

    @Test(groups = {"header_burn_in"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void verifyNewPagePresentOnImageGenerationPage(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // step 2
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        List<String> expectedPages = tvePage.getAllPagesTitles();
        // step 3
        List<String> expectedMvpds = new ArrayList<String>();
        expectedMvpds.addAll(rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration().keySet());
        expectedMvpds.add(allMvpdsName);
        expectedMvpds.add("Sign out");
        // step 4
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage.openOTTHeaderImageGenerationPage(brand);
        softAssert.assertTrue(headerImageGenerationPage.elementSelectPage().isVisible(), "Select page drop-down is not found", webDriver);
        // step 5
        headerImageGenerationPage.elementSelectPage().selectFromDropDown(1);
        softAssert.assertTrue(headerImageGenerationPage.elementSelectMvpd().isVisible(), "Select mvpd drop-down is not found", webDriver);
        softAssert.assertTrue(headerImageGenerationPage.elementSubmit().isVisible(), "Submit button is not found", webDriver);
        // step 6
        List<String> actualPages = headerImageGenerationPage.elementSelectPage().getValuesToSelect()
                .stream().filter(page -> !page.equalsIgnoreCase("- select -")).collect(Collectors.toList());
        softAssert.assertEquals(expectedPages, actualPages, "Pages from drop-down differ from expected");
        // step 7
        List<String> actualMvpds = headerImageGenerationPage.getListOfMVPDs();
        validation(expectedMvpds, actualMvpds);
        softAssert.assertAll();
        Utilities.logInfoMessage("TEST PASSED");
    }

    private void validation(List<String> expectedMvpds, List<String> actualMvpds) {
        List<String> expectedMvpdsProcessed = new ArrayList<String>();
        List<String> actualMvpdsProcessed = new ArrayList<String>();
        expectedMvpds.forEach(item -> expectedMvpdsProcessed.add(StringUtils.normalizeSpace(item).trim()));
        actualMvpds.forEach(item -> actualMvpdsProcessed.add(StringUtils.normalizeSpace(item).trim()));
        softAssert.assertEquals(expectedMvpdsProcessed, actualMvpdsProcessed, "Mvpds from drop-down differ from expected");
    }
}
