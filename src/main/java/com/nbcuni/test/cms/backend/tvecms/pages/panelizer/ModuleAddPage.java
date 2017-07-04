package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ModuleAddPage extends Page {

    List<AbstractElement> elements = new ArrayList<>();
    @FindBy(xpath = ".//input[@value='Finish']")
    private Button finish;
    @FindBy(xpath = ".//div[@id='modal-content']//*[contains(@id,'edit-cancel')]")
    private Button cancel;
    @FindBy(id = "edit-module-id")
    private RadioButtonsGroup radioButtonsGroup;

    public ModuleAddPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public EditPageWithPanelizer clickCancel() {
        cancel.click();
        return new EditPageWithPanelizer(webDriver, aid);
    }

    public EditPageWithPanelizer clickFinish() {
        finish.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        pause(2);
        return new EditPageWithPanelizer(webDriver, aid);
    }

    public ModuleAddPage selectShelf(String shelfName) {
        radioButtonsGroup.selectRadioButtonByName(shelfName);
        return this;
    }

    public boolean isModuleExistWithinList(String moduleName) {
        return radioButtonsGroup.getRadioButtonsNames().contains(moduleName);
    }

    @Override
    public List<String> verifyPage() {
        List<String> missedElements = new ArrayList<>();
        for (AbstractElement element : elements) {
            if (!element.isPresent()) {
                missedElements.add("The Element: " + element + " is missed");
            }
        }
        return missedElements;
    }
}
