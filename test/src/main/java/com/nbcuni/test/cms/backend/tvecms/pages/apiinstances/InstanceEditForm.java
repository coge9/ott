package com.nbcuni.test.cms.backend.tvecms.pages.apiinstances;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class InstanceEditForm extends MainRokuAdminPage {

    @FindBy(id = "edit-delete")
    private Button delete;

    @FindBy(id = "edit-submit")
    private Button save;

    @FindBy(id = "edit-instance-type")
    private DropDownList type;

    @FindBy(id = "edit-admin-title")
    private TextField adminTitle;

    @FindBy(id = "edit-url")
    private TextField address;

    @FindBy(id = "edit-api-key")
    private TextField appiKey;

    public InstanceEditForm(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void setTitle(String value) {
        adminTitle.enterText(value);
    }

    public void setAddress(String value) {
        address.enterText(value);
    }

    public void setApikey(String value) {
        appiKey.enterText(value);
    }

    public void clickSave() {
        save.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void createApiInstance(ApiInstanceEntity entity) {
        Utilities.logInfoMessage("Create API Instance: ");
        setTitle(entity.getTitle());
        setAddress(entity.getUrl());
        setApikey(entity.getApiKey());
        selectAPIType(entity.getType());
        clickSave();
    }

    public void selectAPIType(String typeName) {
        type.selectFromDropDown(typeName);
    }

}
