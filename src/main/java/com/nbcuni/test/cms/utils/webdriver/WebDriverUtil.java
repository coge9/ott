package com.nbcuni.test.cms.utils.webdriver;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BoundingClientRect;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.DropBoxUtil;
import com.nbcuni.test.cms.utils.FileUtil;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverUtil {
    public static final String USER_DIR = "user.dir";
    private static ThreadLocal<WebDriverUtil> webDriverUtils = new ThreadLocal<WebDriverUtil>() {
        @Override
        protected WebDriverUtil initialValue() {
            return new WebDriverUtil();
        }
    };
    private final long DEFAULT_IMPLICITY_WAIT = 15;
    private CustomWebDriver webDriver = null;
    private String firstWinHandle = null;
    private String winHandle;

    protected WebDriverUtil() {
    }

    public static synchronized WebDriverUtil getInstance(final CustomWebDriver driver) {
        webDriverUtils.get().webDriver = driver;
        return webDriverUtils.get();
    }

    public static CustomWebDriver getPrivateDriver(String browser) {
        DesiredCapabilities capabilities = null;
        switch (browser) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
            break;
            case "firefox": {
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.privatebrowsing.autostart", true);
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            }
            break;
        }
        try {
            return new CustomWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities, 15);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        throw new RuntimeException("Cant use this function for browser: [" + browser + "].");
    }

    public void openUrl(String url) {
        try {
            webDriver.openURL(url);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

    public void getNextOpenedHandle() {
        if (webDriver.getWindowHandles().size() > 1) {
            // WaitUtils.perform(instance.webDriver).waitForNumberOfWindowsToEqual(2);
            final Set<String> handles = webDriver.getWindowHandles();
            firstWinHandle = webDriver.getWindowHandle();
            handles.remove(firstWinHandle);
            winHandle = String.valueOf(handles.iterator().next());
            if (!winHandle.equals(firstWinHandle)) {
                webDriver.switchTo().window(winHandle);
            }
        }
    }

    public Set<String> getNextOpenedHandle(Set<String> handles) {
        if (webDriver.getWindowHandles().size() > 1) {
            firstWinHandle = webDriver.getWindowHandle();
            handles.remove(firstWinHandle);
            winHandle = String.valueOf(handles.iterator().next());
            if (!winHandle.equals(firstWinHandle)) {
                webDriver.switchTo().window(winHandle);
            }
        }
        return handles;
    }

    public int getNumberOfWindows() {
        return webDriver.getWindowHandles().size();

    }

    public Set<String> getListOfwindows() {
        final Set<String> handles = webDriver.getWindowHandles();
        return handles;

    }

    public void getFirstHandle() {
        firstWinHandle = webDriver.getWindowHandle();
        webDriver.switchTo().window(firstWinHandle);
    }

    public boolean isProgressPresent() {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(WaitUtils.UPDATE_PROGRESS);
    }

    public boolean isElementPresent(final String elementLocator) {
        return this.isElementPresent(elementLocator, DEFAULT_IMPLICITY_WAIT);
    }

    public boolean isElementPresent(WebElement baseElement, final String elementLocator) {
        return this.isElementPresent(baseElement, elementLocator, DEFAULT_IMPLICITY_WAIT);
    }

    public boolean isElementPresent(final String elementLocator, long time) {
        webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        final int numberOfElements = webDriver.findElements(
                new ByXPath(elementLocator)).size();
        if (numberOfElements > 0) {
            return true;
        }
        webDriver.manage().timeouts()
                .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return false;
    }

    public boolean isElementPresent(WebElement baseElement, final String elementLocator, long time) {
        webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        final int numberOfElements = baseElement.findElements(
                new ByXPath(elementLocator)).size();
        if (numberOfElements > 0) {
            return true;
        }
        webDriver.manage().timeouts()
                .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return false;
    }

    public boolean isElementNotPresent(final String elementLocator) {
        boolean status = true;
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (webDriver.findElements(new ByXPath(elementLocator)).size() != 0) {
            status = !webDriver.isVisible(elementLocator);
        }
        webDriver.manage().timeouts()
                .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return status;
    }

    public boolean isElementNotPresent(final WebElement element) {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        final boolean status = !element.isDisplayed();
        webDriver.manage().timeouts()
                .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return status;
    }

    public boolean isElementVisible(String xpath) {
        return this.isElementVisible(xpath, DEFAULT_IMPLICITY_WAIT);
    }

    public boolean isElementVisible(String xpath, long timeout) {
        try {
            WaitUtils.perform(webDriver).waitElementVisible(xpath, timeout);
        } catch (TimeoutException e) {
            e.getMessage();
        } catch (final NoSuchElementException e) {
            Utilities.logWarningMessage(e.getMessage());
        }
        return webDriver.isVisible(xpath);
    }

    public boolean isAlertPresent() {
        try {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            webDriver.switchTo().alert();
            return true;
        } catch (final NoAlertPresentException e) {
            return false;
        } catch (final UnhandledAlertException e) {
            return false;
        }
    }

    public boolean isDialogPresent() {
        try {
            webDriver.getTitle();
            return false;
        } catch (final UnhandledAlertException e) {
            return true;
        }
    }

    public boolean isFramePresent(final String id) {
        webDriver.switchTo().defaultContent();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        final List<WebElement> frames = webDriver.findElements(By
                .xpath("//iframe[contains(@id,'" + id + "')]"));
        webDriver.manage().timeouts()
                .implicitlyWait(DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        if (!frames.isEmpty()) {
            return true;
        } else {
            Utilities.logInfoMessage("Frame not found by id " + id);
        }
        return false;
    }

    public void scrollIntoView(final WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    public void scrollPageDown() {
        ((JavascriptExecutor) webDriver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollPageUp() {
        ((JavascriptExecutor) webDriver).executeScript("scroll(250, 0)");
    }

    public void click(final WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();",
                element);
    }

    public void selectFromDropdownByValue(String objectLocator,
                                          String valueToSelect) {
        WebElement obj = webDriver.findElement(By.xpath(objectLocator));
        if (webDriver.isVisible(obj)) {
            List<WebElement> options = webDriver
                    .getSelectValuesObject(objectLocator);

            for (WebElement option : options) {
                if (option.getAttribute("value").equals(valueToSelect)
                        && (!option.isSelected())) {
                    option.click();
                    break;
                }
            }
        }
    }

    public String getSelectedValueAttributeFromDropdown(String objectLocator) {
        WebElement obj = webDriver.findElement(By.xpath(objectLocator));
        String valueText = "";
        if (webDriver.isVisible(obj)) {
            List<WebElement> options = webDriver
                    .getSelectValuesObject(objectLocator);

            for (WebElement option : options) {
                if (option.isSelected()) {
                    valueText = option.getAttribute("value");
                    break;
                }
            }

        }
        return valueText;
    }

    public void click(final String xpath) {
        String scr = "document.evaluate(\"%s\", document, null, 9, null).singleNodeValue.click()";
        webDriver.executeScript(String.format(scr, xpath));
//        WebElement element = webDriver.findElement(By.xpath(xpath));
//
//        ((JavascriptExecutor) instance.webDriver).executeScript(
//                "arguments[0].click();", element);
    }

    public String getText(final WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].innerHTML", element);
    }

    public String getText(final String xpath) {
        WebElement element = webDriver.findElement(By.xpath(xpath));
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].innerHTML", element);
    }

    public String getTextValue(final WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].value", element);
    }

    public String getTextValue(final String xpath) {
        WebElement element = webDriver.findElement(By.xpath(xpath));
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].value", element);
    }

    public String getInnerText(final WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].innerText", element);
    }

    public void setText(final WebElement element, final String text) { // usefull            // for
        // text
        // area
        ((JavascriptExecutor) webDriver).executeScript(
                String.format("arguments[0].value='%s'", text), element);
    }

    public String getAttributeJs(final String xPath, final String attribute) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].getAttribute('" + attribute + "');",
                webDriver.findElement(By.xpath(xPath)));
    }

    public String getAttributeJs(final WebElement element,
                                 final String attribute) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].getAttribute('" + attribute + "');",
                element);
    }

    public void attachFullscreenScreenshot(String... screenshotName) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            Utilities.logSevereMessage("Error during taking screenshot" + Utilities.convertStackTraceToString(e));
        }
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
                Toolkit.getDefaultToolkit().getScreenSize()));

        if (Config.getInstance().getScreenshotStorage()
                .equalsIgnoreCase("remote")) {
            String fileName = null;
            if (screenshotName.length != 0) {
                fileName = screenshotName[0] + ".png";
            } else {
                fileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                        .format(new Date()) + ".png";
            }
            String filePath = System.getProperty(USER_DIR)
                    + Config.getInstance().getPathToScreenshots() + fileName;
            File scrFile = new File(filePath);
            try {
                ImageIO.write(screenShot, "PNG", scrFile);
            } catch (IOException e) {
                Utilities.logSevereMessage("Input/Output exception during taking screenshot"
                        + e.getMessage());
            }
            String sharedUrl = DropBoxUtil.uploadFile(scrFile, fileName);
            scrFile.delete();
            if (sharedUrl != null) {
                String screenShotIconPath = Config.getInstance().getProperty(
                        "screenshot.icon.path");
                String screenShotIconHeight = Config.getInstance().getProperty(
                        "screenshot.icon.height");
                String screenShotIconWidth = Config.getInstance().getProperty(
                        "screenshot.icon.width");
                Utilities.logInfoMessage("Screenshot saved into storage:"
                        + "<br><a target='_blank' href='" + sharedUrl
                        + "'> <img height='" + screenShotIconHeight
                        + "' weight='" + screenShotIconWidth + "'src='"
                        + screenShotIconPath + "'/> </a>");
            } else {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        } else {
            try {
                String filePath = null;
                if (screenshotName.length != 0) {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + screenshotName[0] + ".png";
                } else {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                            .format(new Date()) + ".png";
                }
                Utilities.logInfoMessage("Screenshot saved to " + filePath);
                File scrFile = new File(filePath);
                ImageIO.write(screenShot, "PNG", scrFile);
                Reporter.log("<br><a href='" + filePath
                        + "'> <img src='file:\\" + filePath
                        + "' height='500' width='400'/> </a>", 1);
            } catch (final Exception e) {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        }
    }

    public void attachPartialScreenshot(WebElement element,
                                        String... screenshotName) {
        final WebDriver augmentedDriver = new Augmenter().augment(webDriver);
        File scrFile = ((TakesScreenshot) augmentedDriver)
                .getScreenshotAs(OutputType.FILE);

        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        BufferedImage img;
        try {
            img = ImageIO.read(scrFile);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width,
                    height);
            ImageIO.write(dest, "png", scrFile);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to take partial screenshot " + Utilities.convertStackTraceToString(e));
        }

        if (Config.getInstance().getScreenshotStorage()
                .equalsIgnoreCase("remote")) {

            String fileName = null;
            if (screenshotName.length != 0) {
                fileName = screenshotName[0] + ".png";
            } else {
                fileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                        .format(new Date()) + ".png";
            }
            String sharedUrl = DropBoxUtil.uploadFile(scrFile, fileName);
            if (sharedUrl != null) {
                String screenShotIconPath = Config.getInstance().getProperty(
                        "screenshot.icon.path");
                String screenShotIconHeight = Config.getInstance().getProperty(
                        "screenshot.icon.height");
                String screenShotIconWidth = Config.getInstance().getProperty(
                        "screenshot.icon.width");
                Utilities.logInfoMessage("Screenshot saved into storage:"
                        + "<br><a target='_blank' href='" + sharedUrl
                        + "'> <img height='" + screenShotIconHeight
                        + "' weight='" + screenShotIconWidth + "'src='"
                        + screenShotIconPath + "'/> </a>");
            } else {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        } else {
            try {
                String filePath = null;
                if (screenshotName.length != 0) {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + screenshotName[0] + ".png";
                } else {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                            .format(new Date()) + ".png";
                }
                Utilities.logInfoMessage("Screenshot saved to " + filePath);
                FileUtils.copyFile(scrFile, new File(filePath));
                Reporter.log("<br><a href='" + filePath
                        + "'> <img src='file:\\" + filePath
                        + "' height='500' width='400'/> </a>", 1);
            } catch (final Exception e) {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        }

    }

    public void takePartialScreenshotLocally(WebElement graphElement, File targetFile) {
        final WebDriver augmentedDriver = new Augmenter().augment(webDriver);
        File scrFile = ((TakesScreenshot) augmentedDriver)
                .getScreenshotAs(OutputType.FILE);
        Point p = graphElement.getLocation();
        int width = graphElement.getSize().getWidth();
        int height = graphElement.getSize().getHeight();
        BufferedImage img;
        try {
            img = ImageIO.read(scrFile);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width,
                    height);
            ImageIO.write(dest, "jpg", targetFile);
            scrFile.delete();
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to take partial screenshot " + Utilities.convertStackTraceToString(e));
            throw new TestRuntimeException("Unable to take partial screenshot");
        }
    }

    public String attachScreenshot(String... screenshotName) {
        final WebDriver augmentedDriver = new Augmenter().augment(webDriver);
        if (Config.getInstance().getScreenshotStorage()
                .equalsIgnoreCase("remote")) {
            File scrFile = null;
            try {
                scrFile = ((TakesScreenshot) augmentedDriver)
                        .getScreenshotAs(OutputType.FILE);
            } catch (Throwable e) {
                Utilities.logSevereMessage("Unable to take screenshot " + Utilities.convertStackTraceToString(e));
            }
            String fileName = null;
            if (screenshotName.length != 0) {
                fileName = screenshotName[0] + ".png";
            } else {
                fileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                        .format(new Date()) + ".png";
            }
            String sharedUrl = null;
            if (scrFile == null) {
                sharedUrl = Config.getInstance().getDefaultScreenShotUrl();
            } else {
                sharedUrl = DropBoxUtil.uploadFile(scrFile, fileName);
            }
            String screenShotIconPath = Config.getInstance().getProperty(
                    "screenshot.icon.path");
            String screenShotIconHeight = Config.getInstance().getProperty(
                    "screenshot.icon.height");
            String screenShotIconWidth = Config.getInstance().getProperty(
                    "screenshot.icon.width");
            Utilities.logInfoMessage("Screenshot saved into storage:"
                    + "<br><a target='_blank' href='" + sharedUrl
                    + "'> <img height='" + screenShotIconHeight
                    + "' weight='" + screenShotIconWidth + "'src='"
                    + screenShotIconPath + "'/> </a>");
            return sharedUrl;
        } else {
            try {
                String filePath = null;
                if (screenshotName.length != 0) {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + screenshotName[0] + ".png";
                } else {
                    filePath = System.getProperty(USER_DIR)
                            + Config.getInstance().getPathToScreenshots()
                            + new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                            .format(new Date()) + ".png";
                }
                Utilities.logInfoMessage("Screenshot saved to " + filePath);
                final File scrFile = ((TakesScreenshot) augmentedDriver)
                        .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(filePath));
                Reporter.log("<br><a href='" + filePath
                        + "'> <img src='file:\\" + filePath
                        + "' height='500' width='400'/> </a>", 1);
                return filePath;
            } catch (final Exception e) {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        }
        return null;

    }

    public String attachImage(File file, String... screenshotName) {
        if (Config.getInstance().getScreenshotStorage()
                .equalsIgnoreCase("remote")) {
            String fileName = null;
            if (screenshotName.length != 0) {
                fileName = screenshotName[0] + ".png";
            } else {
                fileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                        .format(new Date()) + ".png";
            }
            String sharedUrl = DropBoxUtil.uploadFile(file, fileName);
            if (sharedUrl != null) {
                String screenShotIconPath = Config.getInstance().getProperty(
                        "screenshot.icon.path");
                String screenShotIconHeight = Config.getInstance().getProperty(
                        "screenshot.icon.height");
                String screenShotIconWidth = Config.getInstance().getProperty(
                        "screenshot.icon.width");
                Utilities.logInfoMessage("Screenshot saved into storage:"
                        + "<br><a target='_blank' href='" + sharedUrl
                        + "'> <img height='" + screenShotIconHeight
                        + "' weight='" + screenShotIconWidth + "'src='"
                        + screenShotIconPath + "'/> </a>");
                return sharedUrl;
            } else {
                Utilities.logInfoMessage("Failed to capture screenshot");
            }
        }
        return null;
    }

    public boolean isImageVisible(final WebElement image) {
        try {
            if (image != null) {
                final String attribute = image.getAttribute(HtmlAttributes.SRC
                        .get());
                if (attribute != null && !attribute.isEmpty()) {
                    final URL url = new URL(attribute);
                    if (url != null) {
                        final RenderedImage img = ImageIO.read(url);
                        if (img != null)
                            return img.getData().getWidth() != 0;
                        else
                            return false;
                    }
                }
            }
            return true;
        } catch (final IOException e) {
            try {
                final Iterator<ImageReader> readers = ImageIO
                        .getImageReadersByFormatName("JPEG");
                ImageReader reader = null;
                while (readers.hasNext()) {
                    reader = readers.next();
                    if (reader.canReadRaster()) {
                        break;
                    }
                }
                final Object source = new URL(
                        image.getAttribute(HtmlAttributes.SRC.get()))
                        .openStream();
                final ImageInputStream input = ImageIO
                        .createImageInputStream(source);
                reader.setInput(input, true);
                return reader.getWidth(0) != 0;
            } catch (final IOException e1) {
                Utilities.logWarningMessage("Could not parse image "
                        + image.getAttribute(HtmlAttributes.SRC.get())
                        + e1.getMessage());
                return false;
            }
        }
    }

    public void acceptAlert(final int timeSec) {
        Utilities.logInfoMessage("Accept alert");
        WaitUtils.perform(webDriver).waiterAlertInit(timeSec);
        webDriver.switchTo().alert().accept();
        webDriver.switchTo().defaultContent();
    }

    public void dismissAlert(final int timeSec) {
        Utilities.logInfoMessage("Dismiss alert");
        WaitUtils.perform(webDriver).waiterAlertInit(timeSec);
        webDriver.switchTo().alert().dismiss();
        webDriver.switchTo().defaultContent();
    }

    public List<String> getListOfWindowHandles() {
        final Set<String> handles = webDriver.getWindowHandles();
        List<String> handleList = new ArrayList<String>();
        for (String handle : handles) {
            handleList.add(handle);
        }
        return handleList;
    }

    public void switchToWindowByNumber(final int numberOfWindow) {
        final List<String> windows = getListOfWindowHandles();
        if (numberOfWindow <= 0 || numberOfWindow > windows.size()) {
            throw new IllegalArgumentException(
                    "The number must be in valid range (e.g. more than zero).");
        }
        webDriver.switchTo().window(windows.get(numberOfWindow - 1));
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void changeWindowSize(int height, int width) {
        Utilities.logInfoMessage("Change browser window size to " + width + "X" + height);
        webDriver.manage().window().setSize(new Dimension(width, height));
        scrollPageUp();
    }

    public Long getElementPositionParameter(String xpath,
                                            BoundingClientRect parameter) {
        String script = "return document.evaluate(\"%s\",document,null,9,null).singleNodeValue.getBoundingClientRect().%s;";
        return (Long) webDriver.executeScript(String.format(script, xpath,
                parameter.getName()));
    }

    public String getTextFirstNode(WebElement element) {
        String text;
        String js = FileUtil.readFileToSingleString(new File(Config.getInstance().getPathToJS() + "GetTextFirstNode.txt"));
        text = (String) webDriver.executeScript(js, element);
        return text;
    }

    public List<String> getTextNodes(WebElement element) {
        String js = FileUtil.readFileToSingleString(new File(Config.getInstance().getPathToJS() + "GetTextNodes.txt"));
        List<String> text = (List<String>) webDriver.executeScript(js, element);
        return text;
    }
}