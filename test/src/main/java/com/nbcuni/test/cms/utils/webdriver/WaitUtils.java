package com.nbcuni.test.cms.utils.webdriver;

import com.google.common.base.Function;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WaitUtils {
    public static final String UPDATE_PROGRESS = "//div[@id= 'updateprogress']";
    public static final long DEFAULT_IMPLICITY_WAIT = 15;
    private static ThreadLocal<WaitUtils> waitUtils = new ThreadLocal<WaitUtils>() {
        @Override
        protected WaitUtils initialValue() {
            return new WaitUtils();
        }
    };
    public final String THROBBER_XPATH = "//div[contains(@class, 'ajax_progress_throbber')]|//div[@class='throbber']";
    public final long ATTRIBUTE_PRESENCE_STATE_TIMEOUT = 5;
    private CustomWebDriver webDriver = null;


    private WaitUtils() {
    }

    public static synchronized WaitUtils perform(final CustomWebDriver driver) {
        waitUtils.get().webDriver = driver;
        return waitUtils.get();
    }

    public void waitValueInDropDownEquals(final String dropDownLocator,
                                          final String value) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(dropDownLocator)).size();
                String dropDownValue = null;
                if (numberOfElements != 0) {
                    final WebElement dropDownElement = driver.findElement(By
                            .xpath(dropDownLocator));
                    if (dropDownElement.isDisplayed()) {
                        final List<WebElement> options = dropDownElement
                                .findElements(By.tagName(HtmlAttributes.OPTION
                                        .get()));
                        for (final WebElement option : options) {
                            if (option.isSelected()) {
                                dropDownValue = option.getText();
                                break;
                            }
                        }
                    }
                }
                if (null != dropDownValue)
                    return (dropDownValue.equals(value));
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitTextOfElementContains(final String elementLocator,
                                          final String textInElement, final long timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final String text = driver
                        .findElement(new ByXPath(elementLocator)).getText();
                return (text.contains(textInElement));
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributeNotPresent(final String elementLocator,
                                        final String attributeName) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                WebElement element;
                element = driver.findElement(new ByXPath(elementLocator));
                final String attribute = element.getAttribute(attributeName);
                return (attribute == null);
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributeNotPresent(final String elementLocator,
                                        final String attributeName, final long timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                WebElement element;
                element = driver.findElement(new ByXPath(elementLocator));
                final String attribute = element.getAttribute(attributeName);
                return (attribute == null);
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributeNotPresent(final WebElement element, final String attributeName, final long timeOutSec) {
        final Function<WebElement, Boolean> e = new Function<WebElement, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebElement input) {
                return input.getAttribute(attributeName) == null;
            }
        };
        final FluentWait wait = new FluentWait<WebElement>(element).withTimeout(timeOutSec, TimeUnit.SECONDS).
                pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributePresent(final WebElement element, final String attributeName, final long timeOutSec) {
        final Function<WebElement, Boolean> e = new Function<WebElement, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebElement input) {
                return input.getAttribute(attributeName) != null;
            }
        };
        final FluentWait wait = new FluentWait<WebElement>(element).withTimeout(timeOutSec, TimeUnit.SECONDS).
                pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitElementPresent(final String elementLocator) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts()
                        .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(
                        new ByXPath(elementLocator)).size();
                return (numberOfElements != 0);
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                DEFAULT_IMPLICITY_WAIT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitElementPresent(final String elementLocator,
                                   final long timeOutSec) {
        this.waitElementPresent(By.xpath(elementLocator), timeOutSec, TimeUnit.SECONDS);
    }

    public void waitElementPresent(final By by) {
        this.waitElementPresent(by, DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
    }

    public void waitElementPresent(final String elementLocator,
                                   final long timeOutSec, final TimeUnit unit) {
        this.waitElementPresent(By.xpath(elementLocator), timeOutSec, unit);
    }

    public void waitElementPresent(final By by,
                                   final long timeOutSec, final TimeUnit unit) {
        long timeStart = System.currentTimeMillis();
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts()
                        .implicitlyWait(1, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(
                        by).size();
                driver.manage().timeouts()
                        .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
                return (numberOfElements != 0);
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);
        try {
            wait.until(new ExpectedConditionAdapter(e));
        } catch (Exception exception) {
            long timeEnd = System.currentTimeMillis();
            Utilities.logInfoMessage("Time between start to wait and ending: " + String.valueOf(timeEnd - timeStart));
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(exception.getMessage());
        }
    }

    public void waitElementVisible(final By by, final long timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                boolean status = false;
                final int numberOfElements = driver.findElements(
                        by).size();
                if (numberOfElements != 0) {
                    if (driver.findElement(by)
                            .isDisplayed()) {
                        status = true;
                    }
                }
                return status;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitElementVisible(final String elementLocator, final long timeOutSec) {
        this.waitElementVisible(By.xpath(elementLocator), timeOutSec);
    }

    public void waitElementVisible(final String elementLocator) {
        this.waitElementVisible(elementLocator, DEFAULT_IMPLICITY_WAIT);
    }

    public void waitElementVisible(final By by) {
        this.waitElementVisible(by, DEFAULT_IMPLICITY_WAIT);
    }

    public void waitElementVisible(WebElement element) {
        final Function<WebElement, Boolean> e = new Function<WebElement, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebElement input) {
                return input.isDisplayed();
            }
        };
        final FluentWait wait = new FluentWait<WebElement>(element).withTimeout(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS).
                pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitForNumberOfWindowsToEqual(final int numberOfWindows) {
        new WebDriverWait(webDriver, 20) {
        }.until(new ExpectedConditionAdapter(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                return (driver.getWindowHandles().size() == numberOfWindows);
            }
        }));
    }

    public void waitElementNotPresent(final String elementLocator) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                driver.manage()
                        .timeouts()
                        .implicitlyWait(DEFAULT_IMPLICITY_WAIT,
                                TimeUnit.SECONDS);
                if (numberOfElements == 0)
                    return true;
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT, 2)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    /////////////////////////////////////////////////////////////////////

    public void waitElementNotPresent(final String elementLocator, final long timeSec) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(By.xpath(elementLocator)).size();
                driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
                if (numberOfElements == 0) return true;
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver, timeSec, ATTRIBUTE_PRESENCE_STATE_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    public void waitElementInvisible(final String elementLocator) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                if (numberOfElements == 0
                        || !driver.findElement(By.xpath(elementLocator))
                        .isDisplayed())
                    return true;
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT, 2)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    public void waitElementInvisible(final String elementLocator,
                                     final int timeOutSec) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                if (numberOfElements == 0
                        || !driver.findElement(By.xpath(elementLocator))
                        .isDisplayed())
                    return true;
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec, 2).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    public void waiterAlertInit(final int timeSec) {
        final WebDriverWait wait = new WebDriverWait(webDriver, timeSec);
        try {
            wait.until(new ExpectedConditionAdapter(ExpectedConditions.alertIsPresent()));
        } catch (final NoAlertPresentException e) {
            Utilities.logSevereMessageThenFail("Alert was not present");
        }
    }

    public void waitAttributeValuePresent(final String elementLocator,
                                          final String attributeName, final String attributeValue) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                if (numberOfElements != 0)
                    return driver.findElement(By.xpath(elementLocator))
                            .getAttribute(attributeName).equals(attributeValue);
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributeValuePresent(final String elementLocator,
                                          final String attributeName, final String attributeValue,
                                          final int timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                if (numberOfElements != 0)
                    return driver.findElement(By.xpath(elementLocator))
                            .getAttribute(attributeName).equals(attributeValue);
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitAttributeValueContains(final String elementLocator,
                                           final String attributeName, final String value, final int timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(elementLocator)).size();
                if (numberOfElements != 0)
                    return driver.findElement(By.xpath(elementLocator))
                            .getAttribute(attributeName).contains(value);
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitValueInFieldEquals(final String fieldLocator,
                                       final String value) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(fieldLocator)).size();
                if (numberOfElements != 0) {
                    final String fieldValue = driver.findElement(
                            By.xpath(fieldLocator)).getText();
                    return (fieldValue.equals(value));
                }
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                ATTRIBUTE_PRESENCE_STATE_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitValueInFieldEquals(final String fieldLocator,
                                       final String value, final int timeOutSec) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                final int numberOfElements = driver.findElements(
                        By.xpath(fieldLocator)).size();
                if (numberOfElements != 0) {
                    final String fieldValue = driver.findElement(
                            By.xpath(fieldLocator)).getText();
                    return (fieldValue.equals(value));
                }
                return false;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitForPageLoad() {
        waitForPageLoad(50);
    }

    public void waitForPageLoad(int seconds) {
        final ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                String state = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
                return state.equals("complete");
            }
        };

        final WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(new ExpectedConditionAdapter(pageLoadCondition));
    }

    public void waitForThrobberNotPresent(final long... timeout) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(
                        By.xpath(THROBBER_XPATH)).size();
                driver.manage()
                        .timeouts()
                        .implicitlyWait(DEFAULT_IMPLICITY_WAIT,
                                TimeUnit.SECONDS);
                if (numberOfElements == 0)
                    return true;
                return false;
            }
        };
        WebDriverWait wait = null;
        if (timeout != null && timeout.length != 0) {
            wait = new WebDriverWait(webDriver, timeout[0], 2);
        } else {
            wait = new WebDriverWait(webDriver,
                    ATTRIBUTE_PRESENCE_STATE_TIMEOUT, 2);
        }
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    public void waitForProgressNotPresent(final long... timeout) {
        final ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                final int numberOfElements = driver.findElements(
                        By.xpath(UPDATE_PROGRESS)).size();
                driver.manage()
                        .timeouts()
                        .implicitlyWait(DEFAULT_IMPLICITY_WAIT,
                                TimeUnit.SECONDS);
                if (numberOfElements == 0)
                    return true;
                return false;
            }
        };
        WebDriverWait wait = null;
        if (timeout != null && timeout.length != 0) {
            wait = new WebDriverWait(webDriver, timeout[0], 2);
        } else {
            wait = new WebDriverWait(webDriver,
                    ATTRIBUTE_PRESENCE_STATE_TIMEOUT, 2);
        }
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(condition));
    }

    public void waitFileExistsInTheSystem(final String filePath,
                                          final int timeOutSec) {
        final File file = new File(filePath);
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                return file.exists();
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitLocationIsChanged(final int timeOutSec, final String previousLocation) {
        final ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                if (webDriver.getLocation().equals(previousLocation)) {
                    return false;
                }
                return true;
            }
        };
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(webDriver,
                timeOutSec).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedConditionAdapter(e));
    }

    public void waitTitlePresent() {
        perform(webDriver).waitElementPresent("//html//title");
    }

    public void waitForElementClickable(final String elementLocator, long timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(new ExpectedConditionAdapter(ExpectedConditions.elementToBeClickable(By.xpath(elementLocator))));
    }

    public void waitForElementClickable(final WebElement element, long timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(new ExpectedConditionAdapter(ExpectedConditions.elementToBeClickable(element)));
    }

    public void waitForElementClickable(final String elementLocator) {
        this.waitForElementClickable(elementLocator, DEFAULT_IMPLICITY_WAIT);
    }

    public void waitForElementEnable(final String elementLocator, long timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(new ExpectedConditionAdapter(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath(elementLocator)).isEnabled();
            }
        }));
    }

    public void waitForElementEnable(final String elementLocator) {
        this.waitForElementEnable(elementLocator, DEFAULT_IMPLICITY_WAIT);
    }
}
