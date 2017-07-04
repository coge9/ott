package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan on 17.03.2016.
 */

/**
 * Parent class for blocks
 */
public abstract class AbstractContainer implements Container {

    protected CustomWebDriver webDriver;
    protected Random random = new Random();
    @FindBy(xpath = ".")
    protected AbstractElement currentElement;
    private WebElement wrappedElement;
    private String name;

    public AbstractContainer() {
    }

    public AbstractContainer(WebElement element) {
        ExtendedFieldDecorator decorator = new ExtendedFieldDecorator(element);
        decorator.setDriver(webDriver);
        PageFactory.initElements(decorator, this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public CustomWebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public void setDriver(CustomWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public final void init(final WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    @Override
    public boolean isVisible() {
        return currentElement.isVisible();
    }

    // identify if element visible in certain period of time in seconds.
    public boolean isVisible(int timeout) {
        webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        boolean isVisible = currentElement.isVisible();
        webDriver.manage().timeouts().implicitlyWait(WaitUtils.DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return isVisible;
    }

    @Override
    public boolean isEnable() {
        return currentElement.isEnable();
    }

    public WaitUtils waitFor() {
        return WaitUtils.perform(webDriver);
    }

    @Override
    public boolean isPresent() {
        return currentElement.isPresent();
    }

    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page for missed elements");
        final ArrayList<String> missedElements = new ArrayList<>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                AbstractElement element = (AbstractElement) field.get(this);
                if (element.isPresent()) {
                    missedElements.add("Element: " + field.getName());
                    WebDriverUtil.getInstance(webDriver).attachScreenshot();
                    missedElements.trimToSize();
                }

            } catch (final IllegalArgumentException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return missedElements;
    }

    public void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebDriverUtil util() {
        return WebDriverUtil.getInstance(webDriver);
    }

    public WebElement getElement() {
        return currentElement.element();
    }
}
