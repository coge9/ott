package com.nbcuni.test.cms.backend.tvecms.pages.queue;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.ActionsUtil;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddQueuePage extends MainRokuAdminPage {

    private static final String TITLE_FIELD = ".//*[@id='edit-title']";
    private static final String SEARCH_AUTOCOMPLETE_LIST = "//div[@id='autocomplete']";
    private static final String AUTOCOMPLETE_VALUE = ".//div[@id='autocomplete']//div[contains(@class, 'reference-autocomplete')]";
    private static final String AUTOCOMPLETE_VALUE_GENERALIZED = "(//div[@id='autocomplete']/table/tbody/tr[contains(@class,'result')])[%s]";
    private static final String ADD_ANOTHER_ITEM_BUTTON_XPATH = "//input[contains(@id,'edit-field-qt-program-video') and @type='submit']";
    private static final String ITEM_FIELD = "//input[contains(@id,'edit-field') and @type='text']";
    private static final String ITEM_FIELD_GENERALIZED = "(//input[contains(@id,'edit-field') and @type='text'])[%s]";
    private static final String SAVE_QUEUE_BUTTON = ".//input[@id='edit-submit']";
    private static final String DRAGGABLE_ELEMENT_GENERALIZED = "(//table[contains(@id,'field-qt-program-video-values')]//a[@class ='tabledrag-handle'])[%s]";
    public AddQueuePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void enterTitle(final String title) {
        Utilities.logInfoMessage("Enter " + title + " into title field.");
        webDriver.type(TITLE_FIELD, title);
    }

    public String getTitle() {
        return webDriver.getValue(TITLE_FIELD);
    }

    public int getNumberOfAutocompleteValues() {
        if (!isAutocompletePresent()) {
            Utilities.logSevereMessageThenFail("Autocomplete list is not shown");
            return 0;
        } else
            return webDriver.getObjectCount(AUTOCOMPLETE_VALUE);

    }

    public boolean isAutocompletePresent() {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(
                SEARCH_AUTOCOMPLETE_LIST);
    }

    public void clickValueInAutocompleteList(final String fullName) {
        WaitUtils.perform(webDriver).waitElementPresent(
                SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = webDriver.getText(String.format(
                    AUTOCOMPLETE_VALUE_GENERALIZED, i));
            if (value.contains(fullName)) {
                webDriver.click(String
                        .format(AUTOCOMPLETE_VALUE_GENERALIZED, i));
                Utilities.logInfoMessage("Clicking value" + value + " in autocomplete list");
            }
        }
    }

    public String clickValueInAutocompleteListByNumber(final int number) {
        WaitUtils.perform(webDriver).waitElementPresent(
                SEARCH_AUTOCOMPLETE_LIST, 10);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        if (numberOfAutocompleteValues > 0) {
            String title = getItemTitle(number);
            webDriver.click(String.format(AUTOCOMPLETE_VALUE_GENERALIZED,
                    number));
            WaitUtils.perform(webDriver).waitElementNotPresent(
                    SEARCH_AUTOCOMPLETE_LIST);
            Utilities.logInfoMessage("Clicking value in autocomplete list");
            return title;
        } else {
            Utilities.logSevereMessage("No more than " + numberOfAutocompleteValues
                    + " values in the autocomplete table exist");
            return null;
        }
    }

    public int getNumberOfItems() {
        return webDriver.getObjectCount(ITEM_FIELD);

    }

    public void enterItem(String title, int index) {
        webDriver.type(String.format(ITEM_FIELD_GENERALIZED, index), title);
    }

    public void addRandomAssets(int number) {
        for (int i = 0; i < number; i++) {
            List<WebElement> assetFieldsList = webDriver
                    .findElementsByXPath(ITEM_FIELD);
            WebElement lastField = assetFieldsList
                    .get(assetFieldsList.size() - 1);
            if (!lastField.getAttribute(HtmlAttributes.VALUE.get()).isEmpty()) {
                clickAddAnotherItem();
            }
            assetFieldsList = webDriver.findElementsByXPath(ITEM_FIELD);
            lastField = assetFieldsList.get(assetFieldsList.size() - 1);
            lastField.sendKeys("a");
            clickRandomValueInAutocompleteList();
        }
    }

    public void fillWithRandomAssets(int number) {
        clearAllAssets();
        for (int i = 1; i <= number; i++) {
            if (getNumberOfItems() < i) {
                clickAddAnotherItem();
            }
            WebElement field = webDriver.findElement(By.xpath(String.format(
                    ITEM_FIELD_GENERALIZED, i)));
            field.sendKeys("a");
            clickRandomValueInAutocompleteList();
        }
    }

    public void clickRandomValueInAutocompleteList() {
        WaitUtils.perform(webDriver).waitElementPresent(
                SEARCH_AUTOCOMPLETE_LIST, 7);
        List<WebElement> autocompleteValues = webDriver
                .findElementsByXPath(AUTOCOMPLETE_VALUE);
        int index = new Random().nextInt(autocompleteValues.size());
        WebElement value = autocompleteValues.get(index);
        String gettedText = value.getText();
        WebDriverUtil.getInstance(webDriver).click(value);
        value.click();
        Utilities.logInfoMessage("Clicking value" + gettedText + " in autocomplete list");
    }

    public void clickAddAnotherItem() {
        Utilities.logInfoMessage("Click Add another item");
        webDriver.click(ADD_ANOTHER_ITEM_BUTTON_XPATH);
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
    }

    public void clickSaveQueueButton() {
        Utilities.logInfoMessage("Clicking save button");
        webDriver.click(SAVE_QUEUE_BUTTON);
    }

    private String getItemTitle(int lineNumber) {
        return webDriver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED,
                lineNumber) + "/td[2]");
    }

    public void deleteFirstAsset() {
        webDriver.findElement(
                By.xpath(String.format(ITEM_FIELD_GENERALIZED, 1))).clear();
    }

    public void clearAllAssets() {
        List<WebElement> assetFieldsList = webDriver
                .findElementsByXPath(ITEM_FIELD);
        for (WebElement asset : assetFieldsList) {
            asset.clear();
        }
    }

    public void clearAsset(int assetNumber) {
        List<WebElement> elements = webDriver.findElements(By.xpath(String
                .format(ITEM_FIELD_GENERALIZED, assetNumber)));
        if (elements.isEmpty()) {
            Utilities.logSevereMessageThenFail("There is no field with index " + assetNumber);
        } else {
            elements.get(0).clear();
        }
    }

    public void dragAndDropElement(int sourceIndex, int targetIndex) {
        ActionsUtil.perform(webDriver).dragAndDrop(
                String.format(DRAGGABLE_ELEMENT_GENERALIZED, sourceIndex),
                String.format(DRAGGABLE_ELEMENT_GENERALIZED, targetIndex));
    }

    public List<String> getAssets() {
        List<String> assetsNames = new ArrayList<String>();
        List<WebElement> assetFieldsList = webDriver
                .findElementsByXPath(ITEM_FIELD);
        for (WebElement asset : assetFieldsList) {
            if (!"".equals(asset.getAttribute(HtmlAttributes.VALUE.get()))) {
                assetsNames.add(asset.getAttribute(HtmlAttributes.VALUE.get()));
            }
        }
        return assetsNames;
    }

    public String getFieldValueByIndex(int index) {
        return webDriver.getValue(String.format(ITEM_FIELD_GENERALIZED, index));
    }
}
