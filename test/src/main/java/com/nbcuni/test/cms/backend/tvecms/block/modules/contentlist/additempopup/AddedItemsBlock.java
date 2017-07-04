package com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 2/15/2016.
 */
public class AddedItemsBlock extends AbstractContainer {

    @FindBy(xpath = ".//input[contains(@class,'form-submit')]")
    private Button clearSelection;

    public void clearSelection() {
        clearSelection.click();
    }

    public List<String> getSelectedItems() {
        List<String> result = new ArrayList<>();
        if (!this.isPresent()) {
            return result;
        }
        List<String> selectedItemTitles = WebDriverUtil.getInstance(getWebDriver()).getTextNodes(this.currentElement.element());
        for (String item : selectedItemTitles) {
            item = item.trim();
            String[] items = item.split(", ");
            for (String i : items) {
                i = StringUtils.normalizeSpace(i);
                result.add(i.trim());
            }
        }
        return result;
    }

}
