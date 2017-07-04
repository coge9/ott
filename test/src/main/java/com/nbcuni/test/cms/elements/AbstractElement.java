package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbstractElement implements Element {

    protected CustomWebDriver driver;
    protected By byLocator;
    protected String name;
    protected WebElement element;
    protected AbstractElement parentElement;
    protected Random random = new Random();

    public AbstractElement(CustomWebDriver driver, String xPath) {
        this.driver = driver;
        this.byLocator = By.xpath(xPath);
    }

    public AbstractElement(CustomWebDriver driver, By byLocator) {
        this.driver = driver;
        this.byLocator = byLocator;
    }

    public AbstractElement(CustomWebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    public AbstractElement(AbstractElement parentElement, String xPath) {
        this.parentElement = parentElement;
        this.driver = parentElement.driver;
        this.byLocator = By.xpath(xPath);
    }

    public AbstractElement(AbstractElement parentElement, By byLocator) {
        this.parentElement = parentElement;
        this.byLocator = byLocator;
        this.driver = parentElement.driver;
    }

    public AbstractElement(WebElement webElement) {
        this.element = webElement;
    }

    public By getByLocator() {
        return byLocator;
    }

    public WebElement element() {
        if (element != null) {
            return element;
        } else if (byLocator != null) {
            List<WebElement> elements = this.findCurrentElements(byLocator);
            if (!elements.isEmpty()) {
                return elements.get(0);
            }
            if (elements.isEmpty()) {
                throw new NoSuchElementException("Element with locator " + byLocator.toString() + "not found");
            }
        }
        throw new TestRuntimeException("Impossible get element. Locator is NULL");
    }

    public List<WebElement> elements() {
        return this.findCurrentElements(byLocator);
    }

    public <T extends AbstractElement> List<T> customElements() {
        List<T> abstractElements = new ArrayList<>();
        try {
            Constructor<? extends AbstractElement> constructor = this
                    .getClass().getDeclaredConstructor(CustomWebDriver.class,
                            WebElement.class);
            List<WebElement> webElements = elements();
            for (WebElement webElement : webElements) {
                abstractElements.add((T) constructor.newInstance(driver,
                        webElement));
            }
        } catch (NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        return abstractElements;
    }

    public WaitUtils waitFor() {
        return WaitUtils.perform(driver);
    }

    public WebDriverUtil util() {
        return WebDriverUtil.getInstance(driver);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setElement(WebElement element) {
        this.element = element;
    }

    public CustomWebDriver getDriver() {
        return driver;
    }

    @Override
    public void setDriver(CustomWebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean isVisible() {
        return isPresent() && element().isDisplayed();
    }

    @Override
    public boolean isEnable() {
        return isPresent() && element().isEnabled();
    }

    @Override
    public boolean isPresent() {
        try {
            WebElement currentElement = element();
            if (currentElement != null) {
                currentElement.getTagName();
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement findCurrentElement(By by) {
        if (parentElement != null) {
            return parentElement.element().findElement(by);
        } else {
            return driver.findElement(by);
        }
    }

    private List<WebElement> findCurrentElements(By by) {
        if (parentElement != null) {
            return parentElement.element().findElements(by);
        } else {
            return driver.findElements(by);
        }
    }

    private List<WebElement> findCurrentElements(String xPath) {
        return this.findCurrentElements(By.xpath(xPath));
    }

    public void pause(int seconds) {
        pauseMiliseconds(seconds * 1000);
    }

    public void pauseMiliseconds(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

    public void waitAttributeNotPresent(final String attributeName, final long timeOutSec) {
        waitFor().waitAttributeNotPresent(element, attributeName, timeOutSec);
    }
}
