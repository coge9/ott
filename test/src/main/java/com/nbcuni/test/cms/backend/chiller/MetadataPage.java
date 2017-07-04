package com.nbcuni.test.cms.backend.chiller;

import com.nbcuni.test.cms.backend.ContentAbstractPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by alekca on 20.06.2016.
 */
public class MetadataPage extends ContentAbstractPage {

    public static final String PAGE_TITLE = "Metadata";

    @FindBy(id = "edit-submit-tve-metadata")
    private Button applyButton;

    public MetadataPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public boolean apply() {
        applyButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return isContentPresent();
    }

    public String getNodePublishState(String title) {
        searchByTitle(title);
        apply();
        return table.getRowByTextInColumn(title, "TITLE").getCellByColumnTitle("STATUS").getCellInnerText();
    }

    @Override
    public boolean isPageOpened() {
        return this.getPageTitle().equalsIgnoreCase(PAGE_TITLE);
    }
}
