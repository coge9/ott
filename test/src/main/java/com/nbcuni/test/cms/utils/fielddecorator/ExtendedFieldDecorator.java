package com.nbcuni.test.cms.utils.fielddecorator;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.elements.Element;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ivan on 15.03.2016.
 */

/**
 * Custom Field Decorator for our Elements and Containers
 */

/**
 * Custom decorator for use with PageFactory. Will decorate 1) all of the
 * Element/Container fields and 2) List of Elements (List of Containers) fields that have {@literal @FindBy}, {@literal @FindBys},
 * or {@literal @FindAll} annotation with a proxy
 */
public class ExtendedFieldDecorator extends DefaultFieldDecorator {

    private ElementFactory elementFactory = new DefaultElementFactory();
    private ContainerFactory containerFactory = new DefaultContainerFactory();
    private CustomWebDriver driver;

    public ExtendedFieldDecorator(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
        if (searchContext instanceof CustomWebDriver) {
            driver = (CustomWebDriver) searchContext;
        }
    }

    public static boolean isDecoratableElementList(Field field, Class<? extends Element> elementType) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }
        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        Class<?> tempClass = ((Class) listType).getSuperclass();
        if (tempClass == null) {
            return false;
        }
        boolean isContainer = false;
        while (tempClass != Object.class) {
            if (tempClass.equals(elementType)) {
                isContainer = true;
                break;
            }
            tempClass = tempClass.getSuperclass();
        }

        if (!isContainer) {
            return false;
        }
        if (field.getAnnotation(FindBy.class) == null &&
                field.getAnnotation(FindBys.class) == null &&
                field.getAnnotation(FindAll.class) == null) {
            return false;
        }

        return true;
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {

        if (Container.class.isAssignableFrom(field.getType())) {
            return decorateContainer(loader, field);
        }
        if (Element.class.isAssignableFrom(field.getType())) {
            return decorateElement(loader, field);
        }
        if (isDecoratableElementList(field, AbstractContainer.class)) {
            return decorateContainerList(loader, field);
        }
        if (isDecoratableElementList(field, AbstractElement.class)) {
            return decorateElementList(loader, field);
        }
        return super.decorate(loader, field);
    }

    public void setDriver(CustomWebDriver driver) {
        this.driver = driver;
    }

    private Element decorateElement(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        Element element = elementFactory.create((Class<? extends Element>) field.getType(), wrappedElement);
        element.setName(field.getName());
        element.setDriver(driver);
        return element;
    }

    private ElementLocator createLocator(final Field field) {
        return factory.createLocator(field);
    }

    private Container decorateContainer(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        final Container container = containerFactory.create((Class<? extends Container>) field.getType(), wrappedElement);
        container.setDriver(driver);
        container.setName(field.getName());
        ExtendedFieldDecorator decorator = new ExtendedFieldDecorator(wrappedElement);
        decorator.setDriver(driver);
        PageFactory.initElements(decorator, container);
        return container;
    }

    private List<? extends Element> decorateElementList(final ClassLoader loader, final Field field) {
        return proxyForElementLocator(loader, createLocator(field), field);
    }

    protected List<Element> proxyForElementLocator(ClassLoader loader, ElementLocator locator, Field field) {
        LocatorHandlerElementList handler = new LocatorHandlerElementList(locator);
        handler.setField(field);
        handler.setDriver(driver);
        List<Element> proxy;
        proxy = (List<Element>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }

    private List<? extends Container> decorateContainerList(final ClassLoader loader, final Field field) {
        return proxyForContainerLocator(loader, createLocator(field), field);
    }

    protected List<Container> proxyForContainerLocator(ClassLoader loader, ElementLocator locator, Field field) {
        LocatorHandlerContainderList handler = new LocatorHandlerContainderList(locator);
        handler.setField(field);
        handler.setDriver(driver);
        List<Container> proxy;
        proxy = (List<Container>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }


}
