package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Created by Ivan_Karnilau on 28-Sep-15.
 */
public class FileUploader extends AbstractElement {

    private static final String PATH_INPUT = ".//input[@type='file']";
    private static final String UPLOAD_BUTTON = ".//input[@type='submit']";
    private static final String LABEL = ".//label";
    private static final String REMOVE_BUTTON = ".//input[@value='Remove']";
    private static final String UPLOADED_FILE = ".//span[@class='file']//a";

    public FileUploader(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public FileUploader(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public FileUploader(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public FileUploader(WebElement webElement) {
        super(webElement);
    }

    private Button getUploadButton() {
        WebElement uploadButton = element().findElement(By.xpath(UPLOAD_BUTTON));
        return new Button(driver, uploadButton);
    }

    private Button getRemoveButton() {
        WebElement removeButton = element().findElement(By.xpath(REMOVE_BUTTON));
        return new Button(driver, removeButton);
    }

    private TextField getPathField() {
        WebElement pathField = element().findElement(By.xpath(PATH_INPUT));
        return new TextField(driver, pathField);
    }

    private Label getLabel() {
        WebElement label = element().findElement(By.xpath(LABEL));
        return new Label(driver, label);
    }

    public String uploadFile(String path) {
        Utilities.logInfoMessage("Type path " + path + " for " + this.getLabel().getText().trim());
        this.getPathField().enterText(path);
        Utilities.logInfoMessage("Click upload file " + path + " for " + this.getLabel().getText().trim());
        this.getUploadButton().click();
        waitFor().waitForThrobberNotPresent(10);
        return new File(path).getName();
    }

    public String setFileName(String path) {
        Utilities.logInfoMessage("Type path " + path + " for " + this.getLabel().getText().trim());
        this.getPathField().enterText(path);
        return this.getLabel().getText().trim();
    }

    public String uploadRandomImageFile(int width, int height) {
        String fileName = "TEMP_FILE" + System.currentTimeMillis() + ".png";
        String resultPath = Config.getInstance().getPathToTempFiles() + fileName;
        File file = new File(resultPath);
        ImageUtil.createRandomImage(width, height, file);

        this.uploadFile(file.getAbsolutePath());
        file.delete();
        return fileName;
    }

    public String uploadRandomImageFile(int width, int height, String extension) {
        String fileName = "TEMP_FILE" + System.currentTimeMillis() + extension;
        String resultPath = Config.getInstance().getPathToTempFiles() + fileName;
        File file = new File(resultPath);
        ImageUtil.createRandomImage(width, height, file);

        this.uploadFile(file.getAbsolutePath());
        file.delete();
        if (element().findElements(By.xpath(UPLOADED_FILE)).size() < 1) {
            throw new RuntimeException("File was not uploaded");
        }
        return fileName;
    }

    public String getURLUploadedFile() {
        return element().findElement(By.xpath(UPLOADED_FILE)).getAttribute(HtmlAttributes.HREF.get());
    }

    public String getUploadedFileName() {
        String[] pathSplit = this.getURLUploadedFile().split("/");
        return pathSplit[pathSplit.length - 1];
    }

    public void removeFile() {
        this.getRemoveButton().click();
        waitFor().waitForThrobberNotPresent(10);
    }

    public boolean isFileUploaded() {
        return !element().findElements(By.xpath(REMOVE_BUTTON)).isEmpty();
    }
}
