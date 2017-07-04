package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.ModuleAddPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.elements.Expander;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 7/29/16.
 */
public class ModuleInfoBlock extends AbstractContainer {

    @FindBy(xpath = ".//*[@class='pane-title']//a")
    private Link editShelf;

    @FindBy(xpath = ".//*[contains(@class,'toggle')]")
    private Expander expandShelf;

    @FindBy(xpath = ".//a[contains(@class,'ctools-dropdown-link')]")
    private Link actionLink;

    @FindBy(xpath = ".//span[@class='text']")
    private Link title;

    @FindBy(xpath = ".//a[contains(text(),'Settings')]")
    private Link settingsLink;

    @FindBy(xpath = ".//a[contains(text(),'Remove')]")
    private Link removeLink;

    @FindBy(xpath = ".//a[contains(text(),'Change')]")
    private Link changeLink;

    @FindBy(xpath = ".//*[contains(@class,'lock first')]")
    private Label lockText;

    public String getShelfId() {
        try {
            expandBlock();
            return editShelf.getLinkUrl().split("\\D+")[1];
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    public void expandBlock() {
        expandShelf.expand();
    }

    public DraftModuleTab editShelf() {
        expandBlock();
        editShelf.click();
        return new DraftModuleTab(webDriver, new AppLib(webDriver, System.getProperty("env")));
    }

    public String getModuleName() {
        String name = title.getText().replace("Module: ", "");
        name = name.replace("*", "").trim();
        return name;
    }

    public Module getModuleInfo() {
        Module module = new Module();
        module.setTitle(getModuleName());
        module.setId(getShelfId());
        return module;
    }

    public void deleteBlock() {
        Utilities.logInfoMessage("Delete block");
        actionLink.click();
        removeLink.click();
        WebDriverUtil.getInstance(webDriver).acceptAlert(5);
    }

    public ModuleAddPage editBlock() {
        Utilities.logInfoMessage("Edit block");
        actionLink.click();
        settingsLink.click();
        return new ModuleAddPage(webDriver, new AppLib(webDriver, System.getProperty("env")));
    }

    public void changeBlock() {
        Utilities.logInfoMessage("Lock/Unlock block");
        actionLink.click();
        changeLink.clickJS();
    }

    public Boolean isChangeBlockVisible() {
        Utilities.logInfoMessage("Lock/Unlock block visibility");
        actionLink.click();
        return changeLink.isVisible();
    }

    public Boolean isLocked() {
        Utilities.logInfoMessage("Is block locked");
        actionLink.click();
        if (lockText.getText().equalsIgnoreCase("Locked")) {
            return true;
        }
        return false;
    }
}
