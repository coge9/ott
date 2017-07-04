package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

/**
 * Created by Alena_Aukhukova on 1/13/2016.
 */
public class CheckBoxGroup extends AbstractElement {
    private static final String CHECKBOX_XPATH = ".//input";
    private static final String LABEL_XPATH = ".//label";
    private String checkboxXpathByLabelName = ".//label[text()='%s ']/preceding-sibling::input";

    public CheckBoxGroup(CustomWebDriver driver, String xpath) {
        super(driver, xpath);
    }

    public CheckBoxGroup(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public CheckBoxGroup(WebElement webElement) {
        super(webElement);
    }

    public int getNumberOfCheckboxesInTheGroup() {
        return element().findElements(By.xpath(CHECKBOX_XPATH)).size();
    }


    public List<String> getLabelNames() {
        List<String> labels = new ArrayList<>();
        List<WebElement> labelList = element().findElements(By.xpath(LABEL_XPATH));
        for (WebElement label : labelList) {
            labels.add(label.getText().trim());
        }
        return labels;
    }

    public void selectByLabel(String labelName, boolean state) {
        String checkBoxXpath = String.format(checkboxXpathByLabelName, labelName);
        new CheckBox(driver, checkBoxXpath).selectStatus(state);
    }

    public void selectByIndex(int index, boolean state) {
        getCheckBoxes().get(index - 1).selectStatus(state);
    }

    public Boolean isCheckSelected(String labelName) {
        String checkBoxXpath = String.format(checkboxXpathByLabelName, labelName);
        return new CheckBox(driver, checkBoxXpath).isSelected();
    }

    public Map<String, Boolean> selectRandomCheck() {
        List<CheckBox> checkBoxes = getCheckBoxes();
        checkBoxes.get(new Random().nextInt(checkBoxes.size())).check();
        return getCheckBoxGroup();
    }

    public void checkAll() {
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            checkBox.check();
        }
    }

    public void uncheckAll() {
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            checkBox.uncheck();
        }
    }

    /**
     * Method to uncheck all checkboxes,
     * label of each met search criteria
     *
     * @param criteria - search word to be ignored
     */
    public void uncheckContains(String criteria) {
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            if (StringUtils.containsIgnoreCase(checkBox.getName(), criteria))
                checkBox.uncheck();
        }
    }

    public void checkRandom() {
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            checkBox.selectStatus(random.nextBoolean());
        }
    }

    public Map<String, Boolean> getCheckBoxGroup() {
        Map<String, Boolean> checkboxGroup = new HashMap<String, Boolean>();
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            checkboxGroup.put(checkBox.getLabel().getText(), checkBox.isSelected());
        }
        return checkboxGroup;
    }

    public void setCheckBoxGroup(Map<String, Boolean> checkBoxMap) {
        for (Map.Entry<String, Boolean> checkboxWithLabel : checkBoxMap.entrySet()) {
            String labelName = checkboxWithLabel.getKey();
            selectByLabel(labelName, checkboxWithLabel.getValue());
        }
    }

    public List<String> getListOfChecked() {
        List<String> checkboxes = new ArrayList<>();
        List<CheckBox> checkBoxes = getCheckBoxes();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkboxes.add(checkBox.getLabel().getText());
            }
        }
        return checkboxes;
    }

    public List<String> getUnchecked() {
        List<String> toRetun = new ArrayList<String>();
        Map<String, Boolean> checkboxGroup = getCheckBoxGroup();
        for (Map.Entry<String, Boolean> item : checkboxGroup.entrySet()) {
            if (!item.getValue()) {
                toRetun.add(item.getKey());
            }
        }
        return toRetun;
    }

    public void invert() {
        List<CheckBox> checkboxes = this.getCheckBoxes();
        for (CheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkbox.uncheck();
            } else {
                checkbox.check();
            }
        }
    }

    public List<CheckBox> getCheckBoxes() {
        List<CheckBox> checkBoxes = new ArrayList<>();
        List<WebElement> elements = element().findElements(By.xpath(CHECKBOX_XPATH));
        for (WebElement element : elements) {
            checkBoxes.add(new CheckBox(driver, element));
        }
        return checkBoxes;
    }

    public CheckBox getCheckBoxByIndex(int index) {
        List<CheckBox> checkBoxes = getCheckBoxes();
        return checkBoxes.get(index - 1);
    }

}
