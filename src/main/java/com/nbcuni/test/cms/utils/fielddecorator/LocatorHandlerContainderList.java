package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.List;

/*
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class LocatorHandlerContainderList implements InvocationHandler {

    private final ElementLocator locator;
    private ContainerListFactory containerListFactory = new DefaultContainerListFactory();
    private Field field;
    private CustomWebDriver driver;

    public LocatorHandlerContainderList(ElementLocator locator) {
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
        List<WebElement> elements = locator.findElements();
        List<? extends Container> containers = containerListFactory.create((Class<? extends Container>) (
                        (ParameterizedType) field.getGenericType()).getActualTypeArguments()[0],
                elements);
        for (int i = 0; i < elements.size(); i++) {
            ExtendedFieldDecorator decorator = new ExtendedFieldDecorator(elements.get(i));
            decorator.setDriver(driver);
            PageFactory.initElements(decorator, containers.get(i));
        }
        int i = 0;
        for (Container tempContainder : containers) {
            tempContainder.setDriver(driver);
            tempContainder.setName(field.getName() + " [" + i + "]");
            i++;
        }
        try {
            return method.invoke(containers, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

}
