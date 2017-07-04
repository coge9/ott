package com.nbcuni.test.cms.backend.tvecms.pages.queue;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.TextFieldWithSimpleAutocompleteGroup;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

public class AddQueuePageWithElements extends MainRokuAdminPage {

    private final TextField title = new TextField(webDriver, By.id("edit-title"));
    private final TextFieldWithSimpleAutocompleteGroup itemsFieldGroup = new TextFieldWithSimpleAutocompleteGroup(webDriver, "//input[contains(@id,'edit-field') and @type='text']");
    private final Button addAnotherItem = new Button(webDriver, "//input[contains(@id,'edit-field-qt-program-video') and @type='submit']");
    private final Button saveQueue = new Button(webDriver, ".//input[@id='edit-submit']");
    public AddQueuePageWithElements(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public TextField elementTitle() {
        return title;
    }

    public TextFieldWithSimpleAutocompleteGroup elementItemTitle() {
        return itemsFieldGroup;
    }

    public Button elementAddAnotherItem() {
        return addAnotherItem;
    }

    public Button elementSaveQueue() {
        return saveQueue;
    }

    public TextFieldWithSimpleAutocompleteGroup elementItemsFieldGroup() {
        return itemsFieldGroup;
    }

    public void fillWithRandomValues(int numberOfItems) {
        itemsFieldGroup.clearAllFields();
        while (itemsFieldGroup.getNumberOfElements() < numberOfItems) {
            addAnotherItem.click();
        }
        itemsFieldGroup.fillAllFieldsWithRandomText();
    }
}
