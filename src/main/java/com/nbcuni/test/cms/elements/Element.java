package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;

/**
 * Created by Ivan on 16.03.2016.
 */
public interface Element {
    boolean isVisible();

    boolean isPresent();

    String getName();

    void setName(String name);

    void setDriver(CustomWebDriver driver);

    boolean isEnable();
}
