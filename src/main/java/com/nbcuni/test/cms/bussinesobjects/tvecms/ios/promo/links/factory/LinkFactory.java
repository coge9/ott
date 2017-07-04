package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.factory;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo.LinkUsage;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */
public class LinkFactory {

    private static final String displayText = "AQA Display Text%s";
    private static final String androidUrlPattern = "%s://?%s=%s";
    private static final String iosUrlPattern = "%s://%s";

    private LinkFactory(){
        super();
    }

    public static Link createLinkWithUrl() {
        String postfix = SimpleUtils.getRandomString(6);
        Link link = new Link();

        link.setIsContent(false);
        link.setDisplayText(String.format(displayText, postfix));
        String iosUrl = String.format(iosUrlPattern, SimpleUtils.getRandomString(5).toLowerCase(),
                SimpleUtils.getRandomStringWithRandomLength(10));
        String androidUrl = String.format(androidUrlPattern, SimpleUtils.getRandomString(5).toLowerCase(),
                SimpleUtils.getRandomString(5), SimpleUtils.getRandomStringWithRandomLength(10));
        link.setUrlContentItem(iosUrl);
        link.setUsage(LinkUsage.getRandomUsage());

        return link;
    }

    public static Link createLinkWithContent() {
        String postfix = SimpleUtils.getRandomString(6);
        Link link = new Link();

        link.setIsContent(true);
        link.setDisplayText(String.format(displayText, postfix));
        link.setUsage(LinkUsage.getRandomUsage());

        return link;
    }
}
