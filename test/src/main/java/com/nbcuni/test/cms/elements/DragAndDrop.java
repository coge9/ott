package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.utils.webdriver.ActionsUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class DragAndDrop extends AbstractElement {

    private ActionsUtil actionsUtil;

    private String dragAndDrop = ".//div[@class='handle']";

    public DragAndDrop(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
        actionsUtil = ActionsUtil.perform(driver);
    }

    public DragAndDrop(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
        actionsUtil = ActionsUtil.perform(driver);
    }

    public DragAndDrop(CustomWebDriver driver, WebElement element) {
        super(driver, element);
        actionsUtil = ActionsUtil.perform(driver);
    }

    public DragAndDrop(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
        actionsUtil = ActionsUtil.perform(driver);
    }

    public DragAndDrop(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
        actionsUtil = ActionsUtil.perform(driver);
    }

    public DragAndDrop(WebElement webElement) {
        super(webElement);
    }

    public void perform(final String sourceLocator, final String targetLocator) {
        actionsUtil.dragAndDrop(sourceLocator, targetLocator);
    }

    public void perform(final WebElement source, final WebElement target) {
        actionsUtil.dragAndDrop(source, target);
    }

    public void perform(final String sourceLocator, int x, int y) {
        actionsUtil.dragAndDrop(sourceLocator, x, y);
    }

    public void perform(final WebElement element, int x, int y) {
        actionsUtil.dragAndDrop(element, x, y);
    }

    private List<WebElement> getListOfDragAndDrops() {
        return element.findElements(By.xpath(dragAndDrop));
    }

    /**
     * @param indexFrom index of element to be moved ,should be no less than 2
     * @param indexTo   index of target element ,should be no less than 1
     */
    public void perform(int indexFrom, int indexTo) {
        actionsUtil = ActionsUtil.perform(driver);
        WebElement elementFrom = getListOfDragAndDrops().get(indexFrom - 1);
        WebElement elementTo = getListOfDragAndDrops().get(indexTo - 1);
        perform(elementFrom, elementTo);
    }
}
