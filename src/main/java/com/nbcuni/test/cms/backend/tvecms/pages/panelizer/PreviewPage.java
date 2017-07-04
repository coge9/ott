package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.block.pagepreview.ModulePreviewBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 22-Jan-16.
 */
public class PreviewPage extends MainRokuAdminPage {

    @FindBy(xpath = "//ul[@class='ott-page-preview-edit-links']/li[contains(@class,'edit')]/a")
    private Link preview;

    @FindBy(xpath = "//div[@class='pane-content']")
    private List<ModulePreviewBlock> modulePreviewBlocks;


    public PreviewPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public boolean isPreviewButtonPresent() {
        return preview.isPresent();
    }

    public void clickPreviewLink() {
        preview.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (ModulePreviewBlock moduleBlock : modulePreviewBlocks) {
            titles.add(moduleBlock.getLabel());
        }
        return titles;
    }

    public List<String> getAllImagesNames() {
        List<String> imagesNames = new ArrayList<>();
        for (ModulePreviewBlock modulePreviewBlock : modulePreviewBlocks) {
            imagesNames.addAll(modulePreviewBlock.getImagesNames());
        }
        return imagesNames;
    }

    private ModulePreviewBlock getModulePreviewByName(String moduleName) {
        for (ModulePreviewBlock previewBlock : modulePreviewBlocks) {
            if (previewBlock.getLabel().contains(moduleName)) {
                return previewBlock;
            }
        }
        throw new TestRuntimeException("There is no any Preview block that contains module with name: " + moduleName);
    }

    /**
     * The method to get source of the Image from particular module and Asset
     *
     * @param type   - type of Content for each we need to get Image Info
     * @param module - module where content is searching for
     * @return - source of the image
     */
    public String getUrlOfImage(ContentType type, Module module) {
        switch (type) {
            case TVE_PROGRAM:
                getModulePreviewByName(module.getTitle()).getProgramImageSource();
                break;
            case TVE_VIDEO:
                getModulePreviewByName(module.getTitle()).getVideoImageSource();
                break;
        }
        throw new TestRuntimeException("the given content type: " + type + " doesn't support for Page preview form");
    }

}
