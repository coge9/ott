package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Pre-Conditions:
 * <p> 
 * 1.Login in CMS as admin
 * <p> 
 * 2.Go to /admin/structure/taxonomy
 * <p> 
 * 3.Add new item for Categories
 * <p> 
 * 4.Open created categories for edit
 *
 * Steps:
 * 1.Click on Publish button
 * Verify: Link to the publishing log is present. * 
 * The API log present 'success' status message of POST request
 * <p>
 * 2.Update category: Fill all fields and click on Save button
 * Verify: Status message is present with text '[category] has been saved.'
 * <p>
 * 3.Click on publish button
 * Verify: Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * <p>
 * 4.Verify publishing log
 * Verify: All fields are present and values are correct according
 * http://docs.concertoapiingestmaster.apiary.io/#reference/taxonomyterm/post-taxonomyterm/generate-message-body-to-create-or-update-taxonomyterm
 */
public class TC14831_CheckPublishingUpdatedCategory extends BaseTaxonomyTermTest {

    @BeforeMethod(alwaysRun = true)
    public void createData() {
        createData("AQA categoty");
    }

    @Test(groups = {"taxonomy_term_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkPublishingUpdatedCategory(String brand) {
        checkPublishingUpdatedTerm("Categories", brand);
    }

}
