package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * Interface for blocks
 */
public interface Container extends Element {

    void init(WebElement wrappedElement);

    @Override
    void setDriver(CustomWebDriver driver);
}
