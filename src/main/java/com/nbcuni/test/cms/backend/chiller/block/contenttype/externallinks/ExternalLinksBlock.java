package com.nbcuni.test.cms.backend.chiller.block.contenttype.externallinks;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DragAndDrop;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class ExternalLinksBlock extends BaseTabBlock {

    @FindBy(xpath = ".//*[contains(@id,'field-external-link-values')]/tbody/tr")
    public List<LinkPairBlock> linkPair;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-external-link-und-add-more')]")
    public Button add;

    @FindBy(id = "field-external-link-values")
    public DragAndDrop dragAndDrop;

    public List<ExternalLinksInfo> getLinkPairs() {
        List<ExternalLinksInfo> externalLinksInfos = new ArrayList<>();
        for (LinkPairBlock pairBlock : linkPair) {
            if (pairBlock.getLinkTitle() != null && pairBlock.getLinkUrl() != null &&
                    !pairBlock.getLinkTitle().isEmpty() && !pairBlock.getLinkUrl().isEmpty()) {
                ExternalLinksInfo info = new ExternalLinksInfo();
                info.setExtrenalLinkTitle(pairBlock.getLinkTitle());
                info.setExtrenalLinkUrl(pairBlock.getLinkUrl());
                externalLinksInfos.add(info);
            }
        }
        return externalLinksInfos;
    }

    public void setLinkPairs(List<ExternalLinksInfo> linksInfo) {
        for (int i = 0; i < linksInfo.size(); i++) {
            String linkTitle = linksInfo.get(i).getExtrenalLinkTitle();
            String linkUrl = linksInfo.get(i).getExtrenalLinkUrl();
            linkPair.get(i).setPairOfLink(linkTitle, linkUrl);
            addItem();
        }
    }

    public void addItem() {
        add.clickWithAjaxWait();
    }

    public void dragAndDrop(int indexFrom, int indexTo) {
        dragAndDrop.perform(indexFrom, indexTo);
    }

    public void removeItemByIndex(int index) {
        linkPair.get(index - 1).remove();
    }

    public void removeFirstItem() {
        linkPair.get(0).remove();
    }

    public void removeLastItem() {
        linkPair.get(linkPair.size() - 1).remove();
    }
}
