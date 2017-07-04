package com.nbcuni.test.cms.utils.configuration;

import com.nbcuni.test.webdriver.definition.WebDriverDefinition;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by Dzianis_Kulesh on 4/19/2017.
 *
 * BeaPost processor which add extra capabilites to the webDriver.
 */
public class AddExtraCapabilitiesPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // in case bean is of WebDriverDefinition class add extra capabilities.
        if (bean instanceof WebDriverDefinition) {
            WebDriverDefinition def = (WebDriverDefinition) bean;
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("screenResolution", "1920x1080");
            def.setExtraCapabilities(caps);
        }
        return bean;
    }
}
