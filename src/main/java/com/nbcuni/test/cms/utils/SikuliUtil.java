package com.nbcuni.test.cms.utils;

import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;
import org.testng.Assert;

public class SikuliUtil {
    private final Screen sikuli;
    private Config config = null;

    public SikuliUtil(final Config config, final Screen screen) {
        this.config = config;
        sikuli = screen;
        Settings.OcrTextRead = true;
    }

    public void waitForAllAssetsToLoad() {
        waitForImgPresent(config.getPathToImages() + "Common/SaveCancel_Btns.png");
        try {
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage("InterruptedException exception: " + e.getLocalizedMessage());
        }
    }

    public void type(final String text) {
        clearInput();
        sikuli.type(text);
    }

    public void type(final String inputImgPath, final String text) {
        try {
            sikuli.doubleClick(inputImgPath);
            clearInput();
            sikuli.type(text);
        } catch (final FindFailed e) {
            Utilities.logSevereMessage("Input represented by " + inputImgPath + " image was not found on the screen to type in it");
        }
    }

    public void clickInRegion(final Pattern pattern) {
        final Region region = sikuli.exists(pattern, 1);
        clickInRegion(region);
    }

    public void clickInRegion(final Region region) {
        try {
            sikuli.click(region, 1);
        } catch (final FindFailed e) {
            Utilities.logSevereMessage("Region was not found on the screen to make a click on it: " + e.getLocalizedMessage());
        }
    }

    public String getTextInRegion(final Pattern pattern) {
        final Region region = sikuli.exists(pattern, .85);
        return getTextInRegion(region);
    }

    public String getTextInRegion(final Region region) {
        return region.text();
    }

    public void click(final String elementImgPath) {
        try {
            sikuli.click(elementImgPath);
        } catch (final FindFailed e) {
            Utilities.logSevereMessage("Element represented by " + elementImgPath
                    + " image was not found on the screen to make a click");
        }
    }

    public void waitForImgPresent(final String imgPath) {
        for (int second = 0; ; second++) {
            if (second >= config.getSikuliImageWaitTime()) {
                Assert.fail("Image '" + imgPath + "' is not present after timeout");
            }
            if (sikuli.exists(imgPath, .9) != null) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage("InterruptedException exception: " + e.getLocalizedMessage());
            }
        }
        try {
            Thread.sleep(config.getMPXAssetBufferPause());
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage("InterruptedException exception: " + e.getLocalizedMessage());
        }
    }

    public void waitForImgNotPresent(final String imgPath) {
        for (int second = 0; ; second++) {
            if (second >= config.getSikuliImageWaitTime()) {
                Utilities.logSevereMessage("Image '" + imgPath + "' is still present after timeout");
            }
            if (sikuli.exists(imgPath, .9) == null) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage("InterruptedException exception: " + e.getLocalizedMessage());
            }
        }
        try {
            Thread.sleep(config.getMPXAssetBufferPause());
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage("InterruptedException exception: " + e.getLocalizedMessage());
        }
    }

    public void scrollDownForImgPresent(final String imgPath) {
        for (int duration = 0; ; duration++) {
            if (duration >= 10) {
                Utilities.logSevereMessage("MPX image '" + imgPath + "' is not present after 10 scrolls down");
            }
            if (sikuli.exists(imgPath, 1) == null) {
                if (System.getProperty("os.name").contains("Windows")) {
                    sikuli.wheel(Button.WHEEL_DOWN, 15);
                } else {
                    sikuli.wheel(Button.WHEEL_UP, 15);
                }
            } else {
                break;
            }
        }
    }

    public void scrollDownForImgPresent(final String imgPath, final String imgPath2) {
        for (int duration = 0; ; duration++) {
            if (duration >= 10) {
                Utilities.logSevereMessage("MPX image '" + imgPath + "' is not present after 10 scrolls down");
            }
            if (sikuli.exists(imgPath, 1) == null && sikuli.exists(imgPath2, 1) == null) {
                if (System.getProperty("os.name").contains("Windows")) {
                    sikuli.wheel(Button.WHEEL_DOWN, 15);
                } else {
                    sikuli.wheel(Button.WHEEL_UP, 15);
                }
            } else {
                break;
            }
        }
    }

    public boolean isImagePresent(final String imgPath) {
        return sikuli.exists(imgPath, .9) != null;
    }

    public void scroll(final String downOrUp, final int wheelGradiant) {
        final boolean isWindows = System.getProperty("os.name").contains("Windows");
        if (downOrUp == "Down") {
            if (isWindows) {
                sikuli.wheel(Button.WHEEL_DOWN, wheelGradiant);
            } else {
                sikuli.wheel(Button.WHEEL_UP, wheelGradiant);
            }
        } else {
            if (isWindows) {
                sikuli.wheel(Button.WHEEL_UP, wheelGradiant);
            } else {
                sikuli.wheel(Button.WHEEL_DOWN, wheelGradiant);
            }
        }
    }

    public void pressEnter() {
        sikuli.type(Key.ENTER);
    }

    public void clearInput() {
        if (System.getProperty("os.name").contains("Windows")) {
            sikuli.type("a", KeyModifier.CTRL);
        } else {
            sikuli.type("a", KeyModifier.CMD);
        }
        sikuli.type(Key.BACKSPACE);
    }

    public Region getRegionBelow(final Pattern pattern, final int... height) {
        try {
            if (height != null && height.length > 0)
                return sikuli.find(pattern).below(height[0]);
            else
                return sikuli.find(pattern).below();
        } catch (final FindFailed e) {
            Utilities.logSevereMessage("Region based on element represented by " + pattern.getFilename()
                    + " image was not found on the screen to make a click on it");
        }
        return null;
    }
}