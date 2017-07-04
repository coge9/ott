package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public interface ElementListFactory {
    <E extends Element> List<E> create(Class<E> elementClass, List<WebElement> wrappedElementList);
}
