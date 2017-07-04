package com.nbcuni.test.cms.backend.tvecms.pages.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

/**
 * Created by Alena_Aukhukova
 */
public class TaxonomyViewPortPage extends MainRokuAdminPage {

    private TextField height = new TextField(webDriver, By.xpath("//input[contains(@id,'field-viewport-width')]"));
    private TextField weight = new TextField(webDriver, By.xpath("//input[contains(@id,'field-viewport-height')]"));


    public TaxonomyViewPortPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public TextField elementHeight() {
        return height;
    }


    public TextField elementWeight() {
        return weight;
    }
}
