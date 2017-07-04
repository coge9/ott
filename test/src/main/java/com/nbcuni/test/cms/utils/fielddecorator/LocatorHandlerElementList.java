package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.Element;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class LocatorHandlerElementList implements InvocationHandler {

    private final ElementLocator locator;
    private ElementListFactory elementListFactory = new DefaultElementListFactory();
    private Field field;
    private CustomWebDriver driver;

    public LocatorHandlerElementList(ElementLocator locator) {
        this.locator = locator;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setDriver(CustomWebDriver driver) {
        this.driver = driver;
    }


    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        List<WebElement> webElements = locator.findElements();
        List<? extends Element> customElements = elementListFactory.create((Class<? extends Element>) (
                        (ParameterizedType) field.getGenericType()).getActualTypeArguments()[0],
                webElements);
        int i = 1;
        for (Element tempElement : customElements) {
            tempElement.setName(field.getName() + " [" + i + "]");
            tempElement.setDriver(driver);
            i++;
        }
        try {
            return method.invoke(customElements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }


}
