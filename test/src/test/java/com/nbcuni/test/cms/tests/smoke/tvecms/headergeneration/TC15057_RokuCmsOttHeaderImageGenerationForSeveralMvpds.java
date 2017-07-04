package com.nbcuni.test.cms.tests.smoke.tvecms.headergeneration;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.OTTHeaderImageGenerationPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

/**
 * Created by Ivan_Karnilau on 17-Jun-16.
 */
public class TC15057_RokuCmsOttHeaderImageGenerationForSeveralMvpds extends BaseAuthFlowTest {
    /**
     * TC15057 - Roku CMS: OTT Header Image Generation for several MVPDs
     * <p>
     * precondition 1 - create test taxonomy MVPD term
     * precondition 2 - create test OTT page
     * <p>
     * Step 1 : Go to Roku CMS
     * Step 2: Go to OTT » OTT Header Image Generation (/admin/ott/image_generation/header)
     * Step 3: Set mvpds, page.
     * Step 4: Click submit
     * Step 5: Open url from test mvpd line and check correctness of the header
     * <p>
     * <p>
     * postcondition 1 - delete test taxonomy MVPD term
     * postcondition 2 - delele test OTT page
     */
    private MainRokuAdminPage mainRokuAdminPage;
    private List<String> ids;
    private List<String> names;
    private Random random = new Random();
    private PageForm pageInfo;
    private List<File> generatedResults;
    private int countMVPDs = 3;

    public void createTermTC15057() {
        generatedResults = new ArrayList<>();
        ids = new ArrayList<>();
        names = new ArrayList<>();
        Map<String, Map<String, String>> mvpds = rokuBackEndLayer.getExpectedMvpdsForHeaderGeneration();
        // precondition 1
        for (int i = 0; i < countMVPDs; i++) {
            long timestamp = System.currentTimeMillis();
            File logoImage = new File(Config.getInstance().getPathToTempFiles()
                    + File.separator + "logoImage_" + timestamp + ".png");
            generatedResults.add(new File(Config.getInstance().getPathToTempFiles()
                    + File.separator + "generatedResults_" + timestamp + ".png"));
            logoImage.deleteOnExit();
            generatedResults.get(i).deleteOnExit();
            Set<String> ids = mvpds.keySet();
            int index = random.nextInt(ids.size());
            names.add((String) ids.toArray()[index]);
            this.ids.add((String) mvpds.get(names.get(i)).keySet().toArray()[0]);
            String url = mvpds.get(names.get(i)).get(this.ids.get(i));
            ImageUtil.loadImage(url, logoImage);
            ImageUtil.createRokuHeaderImage(logoImage, generatedResults.get(i), brand);
        }
    }

    @Test(groups = {"header_burn_in", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void headerImageGenerationForOneMvpd(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createTermTC15057();
        SoftAssert softAssert = new SoftAssert();
        // precondition 2
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        //Step 1
        OTTHeaderImageGenerationPage headerImageGenerationPage = mainRokuAdminPage
                .openOTTHeaderImageGenerationPage(brand);
        // step 3 - 4
        headerImageGenerationPage.generateOttPageHeaders(pageInfo.getTitle(), HeaderStyle.WITHOUT_TITLE_WITH_MENU, names);

        Assertion.assertTrue(headerImageGenerationPage.isStatusMessageShown(),
                "Status message is not shown");
        // step 5
        List<String> headers = headerImageGenerationPage.getOttPageHeaders(pageInfo.getTitle(), ids);
        softAssert.assertEquals(countMVPDs, headers.size(),
                "Number of headers is wrong", webDriver);
        softAssert.assertTrue(!CollectionUtils.isEmpty(headers), "header for mvpds " + ids
                + " not present", webDriver);
        softAssert.assertTrue(ImageUtil.compareListUrlsAndListFiles(headers, generatedResults, 95), "");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC15057() {
        // postcondition 2
        try {
            if (pageInfo != null) {
                rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform teer-down method - " + e.getMessage());
        }

    }

}
