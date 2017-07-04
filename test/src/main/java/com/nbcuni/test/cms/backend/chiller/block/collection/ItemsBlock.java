package com.nbcuni.test.cms.backend.chiller.block.collection;

import com.nbcuni.test.cms.elements.TextFieldWithSimpleAutocomplete;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.ActionsUtil;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class ItemsBlock extends AbstractContainer {

    private static final String ITEMS_FIELDS_XPATH = ".//tr//input[@type='text']";
    private static final String DRAG_LINKS_XPATH = ".//a[@class='tabledrag-handle']";
    private static final String ADD_ANOTHER_ITEM_XPATH = ".//*[contains(@id,'-add-more') and @type='submit']";

    public void addItems(List<String> items) {
        for (String item : items) {
            List<WebElement> elements = getWebDriver().findElementsByXPath(ITEMS_FIELDS_XPATH);
            TextFieldWithSimpleAutocomplete field = new TextFieldWithSimpleAutocomplete(getWebDriver(), elements.get(elements.size() - 1));
            field.clickFirstValueInAutocompleteTable(item);
            clickAddAnotherItem();
        }
    }

    public void addItems(int itemsCount) {
        for (int i = 0; i < itemsCount; i++) {
            List<WebElement> elements = getWebDriver().findElementsByXPath(ITEMS_FIELDS_XPATH);
            TextFieldWithSimpleAutocomplete field = new TextFieldWithSimpleAutocomplete(getWebDriver(), elements.get(elements.size() - 1));
            field.enterRandonValueFromAutoComplete();
            clickAddAnotherItem();
        }

    }

    public List<String> getItems() {
        List<String> toReturn = new ArrayList<>();
        List<WebElement> elements = getWebDriver().findElementsByXPath(ITEMS_FIELDS_XPATH);
        for (WebElement element : elements) {
            String value = element.getAttribute(HtmlAttributes.VALUE.get()).replaceFirst("\\d+\\s[.]", "");
            if (!value.isEmpty()) {
                toReturn.add(value.split(" \\(")[0].trim());
            }
        }
        return toReturn;
    }

    private void clickAddAnotherItem() {
        WebDriverUtil.getInstance(getWebDriver()).scrollPageDown();
        getWebDriver().findElementByXPath(ADD_ANOTHER_ITEM_XPATH).click();
        WaitUtils.perform(getWebDriver()).waitForThrobberNotPresent(10);
    }

    public void dragAndDropFirstToLast() {
        List<WebElement> drags = getWebDriver().findElementsByXPath(DRAG_LINKS_XPATH);
        ActionsUtil.perform(getWebDriver()).dragAndDrop(drags.get(1), drags.get(drags.size() - 1));
    }
}
