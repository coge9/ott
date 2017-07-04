package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock.popup.LockModulePopup;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.ModuleAddPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 12/28/15.
 */
public class PanelizerContentBlock extends AbstractContainer {

    @FindBy(xpath = ".//*[contains(@id,'ctools-dropdown')]//a[contains(@class,'ctools-dropdown-link')]")
    private Link blockActions;

    @FindBy(xpath = ".//div[@class='ctools-dropdown-container-wrapper']//a[contains(text(),'Add module')]")
    private Link addContent;

    @FindBy(xpath = ".//a[contains(@class,'ctools-dropdown-link')]")
    private Link actionLink;

    @FindBy(xpath = ".//h2[@class='label']")
    private Label panelLabel;

    @FindBy(xpath = "//div[@id='modalContent']")
    private LockModulePopup lockModulePopup;

    @FindBy(xpath = ".//div[contains(@id,'panel-pane')]")
    private ModuleInfoBlock infoBlock;

    public boolean isBlockPresent() {
        return panelLabel.isPresent();
    }

    public void deleteBlock() {
        Utilities.logInfoMessage("Clean up block");
        infoBlock.deleteBlock();
    }

    public ModuleAddPage editBlock() {
        Utilities.logInfoMessage("Edit block");
        return infoBlock.editBlock();
    }

    public DraftModuleTab editShelf() {
        return infoBlock.editShelf();
    }

    public ModuleAddPage clickAddContent() {
        Utilities.logInfoMessage("Add New Content");
        clickBlockActionLink();
        addContent.click();
        return new ModuleAddPage(webDriver, new AppLib(webDriver, System.getProperty("env")));
    }

    public void clickBlockActionLink() {
        Utilities.logInfoMessage("Expand block settings");
        blockActions.click();
    }

    public LockModulePopup changeBlock() {
        Utilities.logInfoMessage("Edit block");
        infoBlock.changeBlock();
        return lockModulePopup;
    }

    /**
     * Method checks rather for user is visible/available Lock/Unlock functionality
     * @return boolean value for locking actions visibility
     */
    public Boolean isLockingBlockPresent() {
        Utilities.logInfoMessage("Check rather Lock/Unlock action visibility");
        return infoBlock.isChangeBlockVisible();
    }

    public boolean isInfoBlockVisible() {
        return infoBlock.isVisible();
    }

    public Module getModuleInfo() {
        return infoBlock.getModuleInfo();
    }

    public void lockModule() {
        this.changeBlock().lockModule();
    }

    public void unlockModule() {
        this.changeBlock().unlockModule();
    }

    /**
     * Method get info about module, rather it locked or not
     * @return boolean value for the locking of particular module
     */
    public Boolean isModuleLock() {
        return this.infoBlock.isLocked();
    }

    public String getModuleName() {
        if (infoBlock.isVisible(2)) {
            return infoBlock.getModuleName();
        }
        return null;
    }

    public String getPanelLabel() {
        return panelLabel.getText();
    }
}
