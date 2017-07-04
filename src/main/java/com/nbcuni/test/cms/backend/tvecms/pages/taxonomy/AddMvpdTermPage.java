package com.nbcuni.test.cms.backend.tvecms.pages.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

public class AddMvpdTermPage extends MainRokuAdminPage {

    private final TextField name = new TextField(webDriver, By.id("edit-name"));
    private final TextField mvpdId = new TextField(webDriver, By.id("edit-field-mvpd-id-und-0-value"));
    private final TextField mvpdLogo = new TextField(webDriver, By.id("edit-field-mvpd-logo-und-0-value"));
    private final Button save = new Button(webDriver, By.id("edit-submit"));
    private final Button delete = new Button(webDriver, By.id("edit-delete"));

    public AddMvpdTermPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public TextField elementName() {
        return name;
    }

    public TextField elementMvpdId() {
        return mvpdId;
    }

    public TextField elementMvpdLogo() {
        return mvpdLogo;
    }

    public Button elementSave() {
        return save;
    }

    public Button elementDelete() {
        return delete;
    }

    public void fillValuesAndSave(String termName, String mvpdId,
                                  String logoPath) {
        if (termName != null) {
            elementName().enterText(termName);
        }
        if (mvpdId != null) {
            elementMvpdId().enterText(mvpdId);
        }
        if (logoPath != null) {
            elementMvpdLogo().enterText(logoPath);
        }
        elementSave().click();
    }

    public boolean verifyFieldValues(String termName, String mvpdId, String logo) {
        boolean status = elementName().verifyValue(termName);
        status = elementMvpdId().verifyValue(mvpdId) && status;
        status = elementMvpdLogo().verifyValue(logo) && status;
        return status;
    }

    public void deleteTerm() {
        delete.click();
        save.click();
    }
}
