package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alena_Aukhukova on 2/1/2016.
 */
public class LinkGroup extends AbstractElement {

    public static final String LINKS_XPATH = ".//a";
    public static final String ACTIVE_LINK_XPATH = ".//a[contains(@class, 'active')]";
    public static final String LINKS_EXCEPT_ACTIVE = ".//a[not (contains(@class, 'active'))]";
    public static final String LINK_GENERALIZED = ".//a[text()='%s']";

    public LinkGroup(CustomWebDriver driver, String xpath) {
        super(driver, xpath);
    }

    public LinkGroup(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public LinkGroup(WebElement webElement) {
        super(webElement);
    }

    public List<Link> getAllLinks() {
        List<Link> links = new ArrayList<Link>();
        for (WebElement element : getAllLinkElements()) {
            links.add(new Link(driver, element));
        }
        return links;
    }

    public List<WebElement> getAllLinkElements() {
        return element().findElements(By.xpath(LINKS_XPATH));
    }

    public Link getActiveLink() {
        WebElement activeLink = element().findElement(By.xpath(ACTIVE_LINK_XPATH));
        if (activeLink != null) {
            return new Link(driver, activeLink);
        } else {
            Utilities.logSevereMessageThenFail("Active link isn't found");
        }
        return null;
    }

    public Link getRandomLink() {
        List<WebElement> linkElements = element().findElements(By.xpath(LINKS_EXCEPT_ACTIVE));
        return new Link(driver, linkElements.get(new Random().nextInt(linkElements.size())));
    }

}
