package com.nbcuni.test.cms.backend.tvecms.pages.panelizer.contentpage;

import com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock.LayoutPanelizerBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Ivan_Karnilau on 22-Jan-16.
 */
public class MainContentPanelizerPage extends MainRokuAdminPage {

    protected Button save = new Button(webDriver, By.id("panels-dnd-save"));
    protected Button cancel = new Button(webDriver, By.id("edit-cancel"));

    @FindBy(id = "panels-dnd-main")
    protected WebElement panelizer;

    protected LayoutPanelizerBlock panelizerBlock;

    public MainContentPanelizerPage(CustomWebDriver webDriver, AppLib aid, PanelizerTemplates panelizerTemplates) {
        super(webDriver, aid);
        try {
            Constructor constr = panelizerTemplates.getClazz().getConstructor(WebElement.class);
            constr.setAccessible(true);
            //panelizerBlock = (LayoutPanelizerBlock) constr.newInstance(webDriver, "//div[@id='panels-dnd-main']");
            panelizerBlock = (LayoutPanelizerBlock) constr.newInstance(panelizer);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

    public void save() {
        this.save.clickWithAjaxWait();
    }

    public void cancel() {
        this.cancel.clickWithAjaxWait();
    }

    public void checkLayout(SoftAssert softAssert) {
        panelizerBlock.checkLayout(softAssert);
    }

    public void checkMoveBlock(SoftAssert softAssert) {
        panelizerBlock.checkMoveBlock(softAssert);
        save();
    }
}
