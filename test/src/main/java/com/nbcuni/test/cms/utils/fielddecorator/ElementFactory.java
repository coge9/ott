package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * Interface for Element Creation
 */
public interface ElementFactory {
    <E extends Element> E create(Class<E> elementClass, WebElement wrappedElement);
}
