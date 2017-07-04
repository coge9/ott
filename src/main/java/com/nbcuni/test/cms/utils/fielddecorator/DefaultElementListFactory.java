package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public class DefaultElementListFactory implements ElementListFactory {

    private ElementFactory elementFactory = new DefaultElementFactory();

    @Override
    public <E extends Element> List<E> create(Class<E> elementClass, List<WebElement> wrappedElementList) {
        List<E> elementList = new LinkedList<>();
        for (WebElement wrappedElement : wrappedElementList) {
            elementList.add(elementFactory.create(elementClass, wrappedElement));
        }
        return elementList;
    }
}
