package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Aleksandra_Lishaeva on 12/23/15.
 */
public class SettingsPanelizerPage extends AddPanelizerPage {

    CheckBox disableBlock = new CheckBox(webDriver, "//input[@id='edit-no-blocks']");

    public SettingsPanelizerPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void checkDisableBlock(boolean status) {
        Utilities.logInfoMessage("Set disable drupal block to: " + status);
        if (status) {
            disableBlock.check();
        } else disableBlock.uncheck();
    }

    public Info updateSettings() {
        inputTitle();
        boolean status = SimpleUtils.getRandomBoolean();
        checkDisableBlock(status);
        save();
        Assertion.assertTrue(messageStatus.isShown(), "The message status is not shown", webDriver);
        Utilities.logInfoMessage("The settings are updated status message is present");
        Assertion.assertFalse(errorMessage.isPresent(), "The error message is shown", webDriver);
        Utilities.logInfoMessage("There is no error messages");
        return new Info(newTitle, status);
    }

    public class Info {
        public String title;
        public boolean disableDrupalBlock;

        Info(String title, boolean disableDrupalBlock) {
            this.disableDrupalBlock = disableDrupalBlock;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isDisableDrupalBlock() {
            return disableDrupalBlock;
        }

        public void setDisableDrupalBlock(boolean disableDrupalBlock) {
            this.disableDrupalBlock = disableDrupalBlock;
        }
    }
}
