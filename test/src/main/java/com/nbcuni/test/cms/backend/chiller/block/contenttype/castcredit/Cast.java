package com.nbcuni.test.cms.backend.chiller.block.contenttype.castcredit;


import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class Cast extends AbstractContainer {

    @FindBy(xpath = ".//select[contains(@id,'field-person-und')]")
    private DropDownList person;

    @FindBy(xpath = ".//select[contains(@id,'field-role-und')]")
    private DropDownList role;

    @FindBy(xpath = ".//*[@value='Remove']")
    private Button remove;

    public void selectPerson(String valueToSelect) {
        person.selectFromDropDown(valueToSelect);
    }

    public void selectRole(String valueToSelect) {
        role.selectFromDropDown(valueToSelect);
    }

    public String getPerson() {
        return person.getSelectedValue();
    }

    public String getRole() {
        return role.getSelectedValue();
    }

    public void removeItem() {
        remove.clickWithAjaxWait();
    }
}
