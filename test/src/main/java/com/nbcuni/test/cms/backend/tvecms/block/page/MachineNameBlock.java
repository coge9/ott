package com.nbcuni.test.cms.backend.tvecms.block.page;

import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan on 18.03.2016.
 */
public class MachineNameBlock extends AbstractContainer {

    @FindBy(className = "machine-name-value")
    private Label value;

    @FindBy(xpath = ".//span[@class='admin-link']//a")
    private Link edit;

    public void edit() {
        this.edit.click();
    }

    public String getValue() {
        return this.value.getText();
    }
}
