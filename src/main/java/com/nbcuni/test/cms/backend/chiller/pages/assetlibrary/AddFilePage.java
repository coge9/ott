package com.nbcuni.test.cms.backend.chiller.pages.assetlibrary;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 15-Apr-16.
 */
public class AddFilePage extends MainRokuAdminPage {

    @FindBy(xpath = ".//input[contains(@id,'_html5')]")
    private TextField pathField;

    @FindBy(id = "edit-next")
    private Button next;

    @FindBy(xpath = ".//input[@id='edit-upload-upload']")
    private TextField uploadField;

    @FindBy(xpath = ".//*[@id='edit-upload-upload-button']")
    private Button startUpload;

    @FindBy(xpath = ".//*[@id='edit-submit']")
    private Button submit;

    public AddFilePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    private void clickNextButton() {
        next.clickWithAjaxWait();
        webDriver.switchTo().defaultContent();
    }

    public void uploadFiles(List<File> files) {
        this.showTextField();
        for (File file : files) {
            pathField.enterText(file.getAbsolutePath());
        }
        clickNextButton();
    }

    public EditMultipleFilesPage addFiles(List<File> files) {
        uploadFiles(files);
        return new EditMultipleFilesPage(webDriver, aid);
    }

    public void uploadCoverImage(File file) {
        uploadField.enterText(file.getAbsolutePath());
        startUpload.clickWithAjaxWait();
        next.clickWithAjaxWait();
        submit.clickWithAjaxWait();
    }


    private void showTextField() {
        webDriver.executeScript("jQuery(\"[id$='_html5_container']\").css('position', 'static').css('opacity', '1')" +
                ".css('background', 'initial').css('overflow', 'initial').css('font-size', 'inherit')" +
                ".css('height', 'initial').css('width', 'initial')");
        webDriver.executeScript("jQuery(\"[id$='_html5']\").css('position', 'static').css('opacity', '1')" +
                ".css('background', 'initial').css('overflow', 'initial').css('font-size', 'inherit')" +
                ".css('height', 'initial').css('width', 'initial')");
        webDriver.executeScript("jQuery( \"#mediaBrowser\" ).contents().find(\"[id$='_html5_container']\").css('position', 'static').css('opacity', '1')" +
                ".css('background', 'initial').css('overflow', 'initial').css('font-size', 'inherit')" +
                ".css('height', 'initial').css('width', 'initial')");
        webDriver.executeScript("jQuery( \"#mediaBrowser\" ).contents().find(\"[id$='_html5']\").css('position', 'static').css('opacity', '1')" +
                ".css('background', 'initial').css('overflow', 'initial').css('font-size', 'inherit')" +
                ".css('height', 'initial').css('width', 'initial')");
    }
}
