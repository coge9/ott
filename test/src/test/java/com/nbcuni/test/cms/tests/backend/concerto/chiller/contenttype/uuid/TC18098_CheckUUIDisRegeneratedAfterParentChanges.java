package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.uuid;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeasonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by alekca on 12.05.2016.
 */
public class TC18098_CheckUUIDisRegeneratedAfterParentChanges extends BaseAuthFlowTest {

    private RegistryServiceEntity serviceEntity;
    /**
     * Pre-Conditions:Make sure drupal 'Devel' module is switch on
     Steps:
     Input    Expected Result
     1.Go To CMS as Admin
     Verify: The admin panel is present

     2.Create a Season content types
     Verify: Season has created
     Edit Season Page is present

     3.Look into tab Devel
     Verify: There is 'uuid' field with value like '0c3cf275-b64b-412f-8adf-7063f1238c18'

     4.Make sure there is no any UUID field at the UI
     Verify: There is no any UUID field
     * */

    private Content series1;
    private Content series2;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series1 = seriesCreationStrategy.createContentType();
        content = (Season) contentTypeCreationStrategy.createContentType();
        ((Season) content).getSeasonInfo().setParentProgram(series1);
        serviceEntity = new RegistryServiceEntity(ItemTypes.SEASON, content);
        series2 = seriesCreationStrategy.createContentType();

    }

    @Test(groups = {"uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkUUID(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        rokuBackEndLayer.createContentType(series1);
        rokuBackEndLayer.createContentType(series2);
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        DevelPage develPage = contentTypePage.openDevelPage();

        //Step 4
        softAssert.assertTrue(develPage.uuidIsPresent(), "The uuid field is not present", "The uuid field is present");
        String actualUuid = develPage.getUuid();
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        softAssert.assertEquals(serviceHelper.getUuid(serviceEntity),
                actualUuid, "The uuid field's value is not matched", "The uuid field's value is matched");


        ((Season) content).getSeasonInfo().setParentProgram(series2);
        serviceEntity = new RegistryServiceEntity(ItemTypes.SEASON, content);
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_SEASON).apply();
        contentTypePage = contentPage.clickEditLink(SeasonPage.class, content.getTitle());
        contentTypePage.enterContentTypeData(content).saveAsDraft();

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        develPage = contentTypePage.openDevelPage();

        //Step 4
        softAssert.assertTrue(develPage.uuidIsPresent(), "The uuid field is not present", "The uuid field is present");
        actualUuid = develPage.getUuid();
        serviceHelper = new RegistryServiceHelper(brand);
        softAssert.assertEquals(serviceHelper.getUuid(serviceEntity),
                actualUuid, "The uuid field's value is not matched", "The uuid field's value is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14447() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
