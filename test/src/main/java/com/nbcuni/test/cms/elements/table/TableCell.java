package com.nbcuni.test.cms.elements.table;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class TableCell extends AbstractElement {

    public String LIST_XPATH = ".//ul/li";

    public TableCell(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TableCell(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TableCell(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public TableCell(WebElement webElement) {
        super(webElement);
    }


    public void clickLinkByName(String linkText) {
        Utilities.logInfoMessage("Clicking link in row by text " + linkText);
        element().findElement(By.linkText(linkText)).click();
    }

    public String getLinkByName(String linkText) {
        Utilities.logInfoMessage("Getting link in row by text " + linkText);
        return element().findElement(By.linkText(linkText)).getAttribute(HtmlAttributes.HREF.get());
    }

    public WebElement geCellElement() {
        return element;
    }

    public String getCellInnerText() {
        return element.getText().trim();
    }

    public List<String> getCellListOfInnerText() {
        List<String> values = new ArrayList<>();
        List<WebElement> elements = element.findElements(By.xpath(LIST_XPATH));
        for (WebElement element : elements) {
            values.add(element.getText().trim());
        }
        return values;
    }

    public WebElement getInnerElementInCell(By byLocator) {
        return element.findElement(byLocator);
    }

    public boolean isInnerElementInCellPresent(By byLocator) {
        return element.findElements(byLocator).size() != 0;
    }

    public WebElement getInnerElementInCell(String xpath) {
        return element.findElement(By.xpath(xpath));
    }

    public <T extends AbstractElement> T getElementObjectInCell(Class<T> classOfT, By byLocator) {
        try {
            Constructor<T> constructor = classOfT.getConstructor(CustomWebDriver.class, By.class);
            constructor.setAccessible(true);
            T elementObject = constructor.newInstance(driver, byLocator);
            return elementObject;
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("Unable to create object of " + classOfT.getName() + " " + Utilities.convertStackTraceToString(e));
        }
        return null;
    }

    public <T extends AbstractElement> T getElementObjectInCell(Class<T> classOfT, String locator) {
        try {
            Constructor<T> constructor = classOfT.getConstructor(CustomWebDriver.class, WebElement.class);
            constructor.setAccessible(true);
            T elementObject = constructor.newInstance(driver, element().findElement(By.xpath(locator)));
            return elementObject;
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("Unable to create object of " + classOfT.getName() + " " + Utilities.convertStackTraceToString(e));
        }
        return null;
    }
}
