package com.nbcuni.test.cms.utils.fielddecorator;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public interface ContainerListFactory {
    <C extends Container> List<C> create(Class<C> elementClass, List<WebElement> wrappedElementList);
}
