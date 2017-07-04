package com.nbcuni.test.cms.backend.chiller.pages.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FileBlock;
import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FiltersBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.table.Pager;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 15-Apr-16.
 */
public class AssetLibraryPage extends MainRokuAdminPage {

    public static final String PAGE_TITLE = "Asset Library";
    private static final String TEST_JPG = "modalImage.jpg";

    @FindBy(linkText = "Add file")
    private Link addFile;

    @FindBy(id = "edit-delete")
    private Button deleteSelectedFiles;

    @FindBy(xpath = ".//*[@id='edit-submit']")
    private Button confirmationDelete;

    @FindBy(xpath = ".//div[@class='fieldset-wrapper' or contains(@class, 'views-exposed-widgets')]")
    private FiltersBlock filtersBlock;

    @FindBy(xpath = ".//*[@id='media-browser-library-list']/li")
    private List<FileBlock> fileBlocks;

    @FindBy(id = "asset-library-deselect-all")
    private Link deselectAll;

    @FindBy(id = "asset-library-select-all")
    private Link selectAll;

    @FindBy(xpath = ".//a[text()='Submit']")
    private Button submit;

    @FindBy(className = "pager")
    private Pager pager;

    public AssetLibraryPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void clickSubmitButton() {
        submit.click();
        webDriver.switchTo().defaultContent();
    }

    public EditMultipleFilesPage uploadFiles(List<File> files) {
        this.addFile.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        AddFilePage addFilePage = new AddFilePage(webDriver, aid);
        return addFilePage.addFiles(files);
    }

    public List<String> uploadRandomFiles(int count) {
        List<File> files = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            files.add(this.getRandomImage());
        }
        this.uploadFiles(files);
        List<String> filesNames = new LinkedList<>();
        for (File file : files) {
            filesNames.add(file.getName().toLowerCase());
            file.delete();
        }
        return filesNames;
    }

    public List<String> uploadRandomFiles() {
        return this.uploadRandomFiles(1);
    }

    private File getRandomImage() {
        String fileName = "TEMP_FILE" + System.currentTimeMillis() + ".png";
        String resultPath = Config.getInstance().getPathToTempFiles() + fileName;
        File file = new File(resultPath);
        ImageUtil.createRandomImage(1920, 1080, file);
        return file;
    }

    public void filterByName(String fileName) {
        filtersBlock.filterByName(fileName);
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void deleteSelectedFiles() {
        this.deleteSelectedFiles.click();
        this.confirmationDelete.click();
    }

    public List<FileBlock> getFiles() {
        return fileBlocks;
    }

    public List<String> getFilesNames() {
        List<String> filesNames = new LinkedList<>();
        for (FileBlock fileBlock : this.fileBlocks) {
            filesNames.add(fileBlock.getName());
        }
        return filesNames;
    }

    public boolean isDeleteSelectedFilesEnable() {
        return this.deleteSelectedFiles.isEnable();
    }

    public void deselectAll() {
        this.deselectAll.click();
    }

    public void selectAll() {
        this.selectAll.click();
    }

    public boolean isImageInLibrary(String imageName) {
        filtersBlock.filterByName(imageName);
        return fileBlocks.get(0).getName().equalsIgnoreCase(imageName);

    }

    public EditMultipleFilesPage clickRandomAsset() {
        if (!fileBlocks.isEmpty()) {
            fileBlocks.get(random.nextInt(fileBlocks.size())).divSelect();
            WaitUtils.perform(webDriver).waitForPageLoad();
            return new EditMultipleFilesPage(webDriver, aid);
        }
        Assertion.fail("There is no any image block within Asset library");
        throw new TestRuntimeException("There is no any image block within Asset library");
    }

    public EditMultipleFilesPage clickAssetByIndex(int i) {
        fileBlocks.get(i).divSelect();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new EditMultipleFilesPage(webDriver, aid);
    }

    public EditMultipleFilesPage clickAsset(String assetName) {
        filtersBlock.filterByName(assetName);
        fileBlocks.get(0).divSelect();
        return new EditMultipleFilesPage(webDriver, aid);
    }

    public String uploadCustomFile() {
        String path = Config.getInstance().getPathToImages() + TEST_JPG;
        File file = new File(path);
        EditMultipleFilesPage editFilesPage = uploadFiles(Arrays.asList(file));
        return editFilesPage.getPageData().getImageGeneralInfo().getTitle();

    }

    public AssetLibraryPage deleteFile(String asset) {
        filterByName(asset);
        getFiles().get(0).check();
        deleteSelectedFiles();
        return this;
    }

    public AssetLibraryPage deleteFile(FilesMetadata file) {
        return deleteFile(file.getImageGeneralInfo().getTitle());
    }
}
