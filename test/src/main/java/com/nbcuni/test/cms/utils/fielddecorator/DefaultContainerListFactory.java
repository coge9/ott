package com.nbcuni.test.cms.utils.fielddecorator;

import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public class DefaultContainerListFactory implements ContainerListFactory {

    private ContainerFactory containerListFactory = new DefaultContainerFactory();

    @Override
    public <C extends Container> List<C> create(Class<C> elementClass, List<WebElement> wrappedElementList) {
        List<C> containerList = new LinkedList<>();
        for (WebElement wrappedElement : wrappedElementList) {
            containerList.add(containerListFactory.create(elementClass, wrappedElement));
        }
        return containerList;
    }
}
