package com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup;

import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 2/17/2016.
 */
public class AddItemsPopupForShowBlock extends AddItemsPopupAbstract {

    private static final String ASSET_TITLE = ".//div[@class='ott-list-icon-title']//label//span";

    @Override
    public void checkAsset(String name) {
        CheckBox assetCheck = new CheckBox(this.currentElement,
                ".//span[text()=\"" + name + "\"]//ancestor::div[@class='ott-list-icon-title']//input");
        assetCheck.check();
        Utilities.logInfoMessage("Asset [" + name + "] is checked");
    }

    @Override
    public String checkRandomAsset() {
        String randomTitle = getRandomTitle();
        checkAsset(randomTitle);
        Utilities.logInfoMessage("Random asset [" + randomTitle + "] is checked");
        return randomTitle;
    }

    public List<String> getAssetTitles() {
        List<WebElement> titleElements = elementTableContent().getElement().findElements(By.xpath(ASSET_TITLE));
        List<String> titles = new ArrayList<>();
        for (WebElement title : titleElements) {
            titles.add(title.getText());
        }
        return titles;
    }

    private String getRandomTitle() {
        List<String> titles = getAssetTitles();
        int number = random.nextInt(titles.size());
        return titles.get(number);
    }
}
