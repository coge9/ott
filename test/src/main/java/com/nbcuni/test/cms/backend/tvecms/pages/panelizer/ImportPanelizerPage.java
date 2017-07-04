package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

public class ImportPanelizerPage extends MainRokuAdminPage {

    private TextField textArea = new TextField(webDriver, By.id("edit-import"));
    private CheckBox allowImport = new CheckBox(webDriver, By.id("edit-overwrite"));
    private Button continueButton = new Button(webDriver, By.id("edit-next"));
    private Button cancelButton = new Button(webDriver, By.id("edit-cancel"));

    public ImportPanelizerPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void inputTitle(String defaultCode) {
        textArea.enterText(defaultCode);
    }

    public void cancel() {
        cancelButton.click();
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void checkAllowImport(boolean status) {
        if (status) {
            allowImport.check();
        } else allowImport.uncheck();
    }
}
