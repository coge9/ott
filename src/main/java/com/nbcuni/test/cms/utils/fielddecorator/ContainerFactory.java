package com.nbcuni.test.cms.utils.fielddecorator;

import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * Interface for Container Creation
 */
public interface ContainerFactory {
    <C extends Container> C create(Class<C> containerClass, WebElement wrappedElement);
}
