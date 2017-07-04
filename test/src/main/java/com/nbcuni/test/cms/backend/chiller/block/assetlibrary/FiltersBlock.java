package com.nbcuni.test.cms.backend.chiller.block.assetlibrary;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public class FiltersBlock extends AbstractContainer {

    @FindBy(xpath = ".//input[contains(@id,'-filename')]")
    private TextField title;

    @FindBy(xpath = ".//*[@value='Apply']")
    private Button apply;

    @FindBy(id = "edit-custom-filters-reset")
    private Button reset;

    public void filterByName(String fileName) {
        this.title.enterText(fileName);
        this.apply.click();
    }

    public void reset() {
        reset.click();
    }
}
