package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

public class AddPanelizerPage extends MainRokuAdminPage {

    protected String newTitle;
    private TextField title = new TextField(webDriver, By.id("edit-title"));
    private Button save = new Button(webDriver, By.id("edit-submit"));

    public AddPanelizerPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public AddPanelizerPage inputTitle(String... panelizerTitle) {
        if (panelizerTitle.length > 0) {
            title.enterText(panelizerTitle[0]);
        } else {
            newTitle = "AQAPanelizer" + SimpleUtils.getRandomString(2);
            title.enterText(newTitle);
        }
        return this;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String save() {
        save.click();
        return newTitle;
    }
}
