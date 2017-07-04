package com.nbcuni.test.cms.utils.fielddecorator;

import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * This class creates new container instance and initializes internal elements (Default implementation)
 */
public class DefaultContainerFactory implements ContainerFactory {
    @Override
    public <C extends Container> C create(final Class<C> containerClass, final WebElement wrappedElement) {
        final C container = createInstanceOf(containerClass);
        container.init(wrappedElement);
        return container;
    }

    private <C extends Container> C createInstanceOf(final Class<C> containerClass) {
        try {
            return containerClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
