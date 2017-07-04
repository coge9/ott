package com.nbcuni.test.cms.utils.webdriver;

import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class ActionsUtil {
    private static ThreadLocal<ActionsUtil> actionUtils = new ThreadLocal<ActionsUtil>() {
        @Override
        protected ActionsUtil initialValue() {
            return new ActionsUtil(null);
        }
    };
    private CustomWebDriver webDriver = null;
    private Actions actions = null;

    private ActionsUtil(final CustomWebDriver driver) {
        webDriver = driver;
    }

    public static synchronized ActionsUtil perform(final CustomWebDriver driver) {
        if (actionUtils.get() == null) {
            actionUtils.set(new ActionsUtil(driver));
        }
        actionUtils.get().webDriver = driver;
        actionUtils.get().actions = new Actions(driver);
        return actionUtils.get();
    }

    public static void pressCtrlU() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_U);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_U);
        } catch (AWTException e) {
            Utilities.logSevereMessage("There is an exception catched: "+Utilities.convertStackTraceToString(e));
        }
    }

    public void dragAndDrop(final String sourceLocator, final String targetLocator) {
        final WebElement element = webDriver.findElement(By.xpath(sourceLocator));
        final WebElement target = webDriver.findElement(By.xpath(targetLocator));
        actions.dragAndDrop(element, target).perform();
    }

    public void dragAndDrop(final WebElement source, final WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    public void dragAndDrop(final String sourceLocator, int x, int y) {
        final WebElement element = webDriver.findElement(By
                .xpath(sourceLocator));
        actions.dragAndDropBy(element, x, y).perform();
    }

    public void dragAndDrop(final WebElement element, int x, int y) {
        actions.dragAndDropBy(element, x, y).perform();
    }

    public void selectFromDropDown(WebElement ddl, String text) {
        Select dropdown = new Select(ddl);
        dropdown.selectByVisibleText(text);
    }

    public void clickPressingCtrl(WebElement element) {
        actions.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL)
                .perform();
    }

    public void pressCtrl() {
        actions.keyDown(Keys.CONTROL).perform();
    }

    public void releaseCtrl() {
        actions.keyUp(Keys.CONTROL).perform();
    }

    public void mouseOn(final String elementXpath) {
        actions.moveToElement(webDriver.findElementByXPath(elementXpath))
                .perform();
    }

    public void selectFromMultiselect(String elementXpath, List<String> values) {
        Select select = new Select(
                webDriver.findElement(By.xpath(elementXpath)));
        select.deselectAll();
        List<WebElement> options = select.getOptions();
        for (String value : values) {
            WebElement tempOption = null;
            for (WebElement option : options) {
                if (option.getText().equals(value)) {
                    tempOption = option;
                    break;
                }
            }
            if (tempOption != null) {
                clickPressingCtrl(tempOption);
            }
        }
    }

}