package com.nbcuni.test.cms.backend.theplatform;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SikuliUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class MPXSearch {
    private final SikuliUtil util;
    private final String imagePath;

    public MPXSearch(final Config config) {
        util = new SikuliUtil(config, new Screen());
        imagePath = config.getPathToImages();
    }

    public void selectStageSubAccount(String brand) {
        String account = null;
        switch (brand.toLowerCase()) {
            case "bravo":
                account = "Search/Account_Stage_Bravo.png";
                break;
            case "eonline":
                account = "Search/Account_Stage_Eonline.png";
                break;
            case "sprout":
                account = "Search/Account_Stage_Sprout.png";
                break;
            case "syfy":
                account = "Search/Account_Stage_Syfy.png";
                break;
            case "esquire":
                account = "Search/Account_Stage_Esquire.png";
                break;
            case "telemundo":
                account = "Search/Account_Stage_Telemundo.png";
                break;
            case "cnbc":
                account = "Search/Account_Stage_CNBC.png";
                break;
            case "mun2":
                account = "Search/Account_Stage_Mun2.png";
                break;
            case "oxygen":
                account = "Search/Account_Stage_Oxygen.png";
                break;
        }
        Utilities.logInfoMessage("Select Stage sub Account");
        util.waitForImgPresent(imagePath + "Search/Account_txt.png");
        final Pattern pattern = new Pattern(imagePath + "Search/Account_txt.png").targetOffset(35, 3);
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
        util.type("NBCU TVE Stage");
        util.waitForImgPresent(imagePath + account);
        final Pattern select = new Pattern(imagePath + account).targetOffset(1, 1);
        util.clickInRegion(select);
    }

    public void enterSearchTxt(final String txt) {
        Utilities.logInfoMessage("Enter '" + txt + "' in the 'Search' text box");
        util.waitForImgPresent(imagePath + "Search/Search_Loupe_Btn.png");
        final Pattern pattern = new Pattern(imagePath + "Search/Search_Loupe_Btn.png").targetOffset(60, 3);
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

    public void clickSearchByTitleLnk() {
        Utilities.logInfoMessage("Click the 'Search by Title' link");
        util.waitForImgPresent(imagePath + "Search/Search_Loupe_Btn.png");
        final Pattern pattern = new Pattern(imagePath + "Search/Search_Loupe_Btn.png").targetOffset(2, 1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        util.clickInRegion(pattern);
        util.waitForImgPresent(imagePath + "Search/Titles_Lnk_MAC.png");//"Search/Titles_Lnk.png");
        util.click(imagePath + "Search/Titles_Lnk.png");
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessageThenFail("InterruptedException exception" + e.getLocalizedMessage());
        }
    }

    public boolean isItemsFound() {
        return !util.isImagePresent(imagePath + "Common/NoItemsToShow_Txt.png");
    }
}
