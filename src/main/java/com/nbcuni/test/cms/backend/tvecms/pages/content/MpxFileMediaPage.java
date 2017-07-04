package com.nbcuni.test.cms.backend.tvecms.pages.content;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

public class MpxFileMediaPage extends MainRokuAdminPage {

    private static final String TITLE_FIELD_XPATH = "//input[@id='edit-title']";

    @FindBy(xpath = ".//*[@id='block-system-main']//table//tr")
    private Table content;
    private TextField title = new TextField(webDriver, "//input[@id='edit-title']");
    private Button apply = new Button(webDriver, "//input[@id='edit-submit-content-files']");
    private Link options = new Link(webDriver, "//a[@class='ctools-twisty ctools-text']");
    private Button delete = new Button(webDriver, "//div[@class='ctools-content']//a[text()='Delete']");
    private Button deleteConfirmation = new Button(webDriver, "//input[@id='edit-submit']");

    public MpxFileMediaPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    public boolean areFilesPresent() {
        return this.content.isPresent();
    }

    public MpxFileMediaPage searchByTitle(final String title) {
        WaitUtils.perform(webDriver).waitElementVisible(TITLE_FIELD_XPATH);
        this.title.enterText(title);
        return this;
    }

    public boolean apply() {
        this.apply.click();
        return areFilesPresent();
    }


    public void clickDeleteButton(final String... title) {
        if (title.length > 0) {
            searchByTitle(title[0]);
            apply();
        }
        this.options.click();
        this.delete.click();
        this.deleteConfirmation.click();
    }

}
