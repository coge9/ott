package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.taxanomyterm;

import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Pre-Conditions:
 * 1.Login in CMS as admin
 * <p>
 * 2.Go to /admin/structure/taxonomy
 * <p>
 * 3.Add new item for Tag
 * <p>
 * 4.Open created CTag for edit
 * <p>
 * Steps:
 * 1.Click on Publish button
 * Verify: Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * <p>
 * 2.Verify publishing log
 * Verify: All fields are present and values are correct according
 * http://docs.concertoapiingestmaster.apiary.io/#reference/taxonomyterm/post-taxonomyterm-in-json-schema-format/generate-taxonomyterm-body-in-json-schema-format
 */
public class TC14828_ValidateTagScheme extends BaseTaxonomyTermTest {

    @BeforeMethod(alwaysRun = true)
    public void createData() {
        createData("AQA tag");
    }

    @Test(groups = {"taxonomy_term_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateTagScheme(String brand) {
        this.brand = brand;
        validateTermScheme("Tags", brand);
    }

}
