package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock.popup;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 02-Mar-16.
 */
public class LockModulePopup extends AbstractContainer {

    @FindBy(xpath = ".//div[@id='edit-type']")
    private RadioButtonsGroup lockType;

    @FindBy(xpath = ".//input[@id='edit-next']")
    private Button save;

    public LockModulePopup() {
        super();
    }

    public LockModulePopup(WebElement element) {
        super(element);
    }

    public void selectLockType(LockType lockType) {
        this.lockType.selectRadioButtonByName(lockType.getName());
    }

    public void clickSave() {
        save.clickWithAjaxWait();
    }

    public void lockModule() {
        this.selectLockType(LockType.LOCKED);
        this.clickSave();
    }

    public void unlockModule() {
        this.selectLockType(LockType.NO_LOCK);
        this.clickSave();
    }

    private enum LockType {
        NO_LOCK("No lock"),
        LOCKED("Locked");

        private String name;

        LockType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
