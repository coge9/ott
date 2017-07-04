package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * This class creates new element instance (Default implementation)
 */
public class DefaultElementFactory implements ElementFactory {
    @Override
    public <E extends Element> E create(final Class<E> elementClass, final WebElement wrappedElement) {
        try {
            return elementClass
                    .getDeclaredConstructor(WebElement.class)
                    .newInstance(wrappedElement);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

