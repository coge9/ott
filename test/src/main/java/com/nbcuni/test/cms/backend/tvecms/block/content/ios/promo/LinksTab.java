package com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Links;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class LinksTab extends AbstractContainer {

    @FindBy(xpath = ".//*[contains(@id,'field-promo-links-values')]/tbody/tr")
    private List<LinkBlock> linksBlocks;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-promo-links-und-add')]")
    private Button addAnotherItem;

    public void enterData(Links links) {
        for (int i = 0; i < links.getLinks().size(); i++) {
            Link currentLink = links.getLinks().get(i);
            if (linksBlocks.size() < i + 1) {
                addAnotherItem.clickWithAjaxWait();
            }
            LinkBlock currentBlock = linksBlocks.get(i);
            currentBlock.enterDisplayText(currentLink.getDisplayText());
            if (currentLink.isContent()) {
                currentBlock.selectContentItem(currentLink.getUrlContentItem());
            } else {
                currentBlock.enterUrl(currentLink.getUrlContentItem());
            }
            currentBlock.selectUsage(currentLink.getUsage());
        }
    }

    public Links getData() {
        Links links = new Links();
        for (LinkBlock linkBlock : linksBlocks) {
            Link link = new Link();
            link.setDisplayText(linkBlock.getDisplayText());
            link.setUsage(linkBlock.getUsage());
            if (!linkBlock.getUrl().isEmpty()) {
                if (linkBlock.getUrl().contains("(")) {
                    link.setIsContent(true);
                    String content = linkBlock.getContentItem();
                    link.setUrlContentItem(content.substring(0, content.lastIndexOf("(") - 1));
                } else {
                    link.setUrlContentItem(linkBlock.getUrl());
                }
            }
            if ((link.getDisplayText() != null && !link.getDisplayText().isEmpty()) ||
                    (link.getUrlContentItem() != null && !link.getUrlContentItem().isEmpty())) {
                links.addLink(link);
            }
        }
        return links;
    }
}
