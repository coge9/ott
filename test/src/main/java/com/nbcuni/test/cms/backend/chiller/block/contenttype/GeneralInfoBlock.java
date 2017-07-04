package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.elements.TextField;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public class GeneralInfoBlock extends BaseGeneralInfoBlock {

    @FindBy(xpath = ".//input[contains(@id,'edit-field-subhead')]")
    private TextField subhead;

    public void enterSubhead(String subhead) {
        this.subhead.enterText(subhead);
    }

    public String getSubhead() {
        return this.subhead.getValue();
    }
}
