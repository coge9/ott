package com.nbcuni.test.cms.backend.theplatform;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SikuliUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class MPXFilesTab {
    private final SikuliUtil util;
    private final String imagePath;

    public MPXFilesTab() {
        util = new SikuliUtil(Config.getInstance(), new Screen());
        imagePath = Config.getInstance().getPathToImages();
    }

    public void enterSearchTxt(final String txt) {
        Utilities.logInfoMessage("Enter '" + txt + "' in the 'Search' text box");
        util.waitForImgPresent(imagePath + "Search/Search_Loupe_Btn.png");
        final Pattern pattern = new Pattern(imagePath
                + "Search/Search_Loupe_Btn.png").targetOffset(60, 3);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        util.clickInRegion(pattern);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        util.clickInRegion(pattern);
        util.type(txt);
    }

    public void clickImageWithCertainSize(String size) {
        Utilities.logInfoMessage("Click Asset with size" + size);
        final Pattern pattern = new Pattern(imagePath + "Files/" + size
                + ".png").targetOffset(2, 1);
        if (util.isImagePresent(imagePath + "Files/" + size + ".png")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            }
            util.clickInRegion(pattern);
        } else {
            util.click(imagePath + "Files/file_name.png");
            util.scroll("Down", 300);
            util.clickInRegion(pattern);
        }
    }

    public void openFilePropertiesTab() {
        Utilities.logInfoMessage("Open file properties tab");
        final Pattern pattern = new Pattern(imagePath
                + "Files/file_properties.png").targetOffset(2, 1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        util.clickInRegion(pattern);
    }

    public void setNewSize(String size) {
        util.type(imagePath + "Files/width.png", size.split("X")[0]);
        util.type(imagePath + "Files/height.png", size.split("X")[1]);
        clickSaveBtn(true);
    }

    public void clickSaveBtn(final Boolean withPause) {
        Utilities.logInfoMessage("Click the 'Save' button");
        util.waitForImgPresent(imagePath + "Common/Save_Btn.png");
        util.click(imagePath + "Common/Save_Btn.png");
        util.waitForImgNotPresent(imagePath + "Common/Spinner.png");
        if (withPause) {
            try {
                Thread.sleep(10000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage(e.getLocalizedMessage());
            }
        }
    }

    public void exchangeImagesDimension(String firstDimension,
                                        String secondDimension) {
        changeImageDimension(firstDimension, "0X1");
        changeImageDimension(secondDimension, firstDimension);
        changeImageDimension("0X1", secondDimension);
    }

    private void changeImageDimension(String oldDimension, String newDimension) {
        clickImageWithCertainSize(oldDimension);
        openFilePropertiesTab();
        setNewSize(newDimension);
    }
}
